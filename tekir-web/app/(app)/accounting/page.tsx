'use client'

import { useEffect, useState, useCallback } from 'react'
import {
  Button,
  Card,
  Col,
  Row,
  Typography,
  Tag,
  Dropdown,
  Modal,
  Form,
  Input,
  Select,
  DatePicker,
  message,
  Spin,
  Empty,
  Tooltip,
} from 'antd'
import {
  PlusOutlined,
  BookOutlined,
  MoreOutlined,
  CloudUploadOutlined,
  ThunderboltOutlined,
  ExclamationCircleOutlined,
} from '@ant-design/icons'
import { useRouter } from 'next/navigation'
import api from '@/lib/axios'
import ImportModal from '@/components/app/accounting/ImportModal'

const { Title, Text } = Typography
const { TextArea } = Input
const { confirm } = Modal

interface AccountPlanDTO {
  id: number
  code: string
  name: string
  description?: string
  planType: string
  isDefault: boolean
  isActive: boolean
  effectiveDate?: string
  accountCount: number
}

const PLAN_TYPE_LABELS: Record<string, { label: string; color: string }> = {
  STANDARD_TDHP: { label: 'TDHP Standart', color: 'blue' },
  CUSTOM:        { label: 'Özel Plan',      color: 'purple' },
  IMPORTED:      { label: 'İçe Aktarılmış', color: 'cyan' },
}

export default function AccountingPage() {
  const router = useRouter()
  const [plans, setPlans] = useState<AccountPlanDTO[]>([])
  const [loading, setLoading] = useState(true)
  const [tdhpLoading, setTdhpLoading] = useState(false)
  const [createModalOpen, setCreateModalOpen] = useState(false)
  const [importModalOpen, setImportModalOpen] = useState(false)
  const [selectedPlanId, setSelectedPlanId] = useState<number | null>(null)
  const [saving, setSaving] = useState(false)
  const [form] = Form.useForm()

  const fetchPlans = useCallback(async () => {
    setLoading(true)
    try {
      const resp = await api.get<AccountPlanDTO[]>('/api/v1/accounting/plans')
      setPlans(resp.data)
    } catch {
      message.error('Hesap planları yüklenemedi')
    } finally {
      setLoading(false)
    }
  }, [])

  useEffect(() => {
    fetchPlans()
  }, [fetchPlans])

  const handleLoadTdhp = () => {
    confirm({
      title: 'Standart TDHP Yükle',
      icon: <ExclamationCircleOutlined />,
      content: 'Türk Tekdüzen Hesap Planı (TDHP) bu tenant için yüklenecek. Devam etmek istiyor musunuz?',
      okText: 'Evet, Yükle',
      cancelText: 'İptal',
      okButtonProps: { style: { background: '#6366f1', borderColor: '#6366f1' } },
      onOk: async () => {
        setTdhpLoading(true)
        try {
          await api.post('/api/v1/accounting/plans/load-tdhp')
          message.success('TDHP başarıyla yüklendi')
          fetchPlans()
        } catch (e: unknown) {
          const err = e as { response?: { data?: { message?: string } } }
          message.error(err.response?.data?.message ?? 'TDHP yüklenemedi')
        } finally {
          setTdhpLoading(false)
        }
      },
    })
  }

  const handleCreate = async (values: Record<string, unknown>) => {
    setSaving(true)
    try {
      await api.post('/api/v1/accounting/plans', {
        ...values,
        effectiveDate: values.effectiveDate
          ? (values.effectiveDate as { format: (s: string) => string }).format('YYYY-MM-DD')
          : undefined,
      })
      message.success('Plan oluşturuldu')
      setCreateModalOpen(false)
      form.resetFields()
      fetchPlans()
    } catch (e: unknown) {
      const err = e as { response?: { data?: { message?: string } } }
      message.error(err.response?.data?.message ?? 'Plan oluşturulamadı')
    } finally {
      setSaving(false)
    }
  }

  const handleActivate = async (planId: number) => {
    try {
      await api.post(`/api/v1/accounting/plans/${planId}/activate`)
      message.success('Varsayılan plan güncellendi')
      fetchPlans()
    } catch {
      message.error('İşlem başarısız')
    }
  }

  const handleDelete = (plan: AccountPlanDTO) => {
    confirm({
      title: `"${plan.name}" planını silmek istiyor musunuz?`,
      content: plan.accountCount > 0
        ? `Bu plan ${plan.accountCount} hesap içeriyor ve silinemez.`
        : 'Bu işlem geri alınamaz.',
      okText: 'Sil',
      cancelText: 'İptal',
      okButtonProps: { danger: true, disabled: plan.accountCount > 0 },
      onOk: async () => {
        try {
          await api.delete(`/api/v1/accounting/plans/${plan.id}`)
          message.success('Plan silindi')
          fetchPlans()
        } catch (e: unknown) {
          const err = e as { response?: { data?: { message?: string } } }
          message.error(err.response?.data?.message ?? 'Plan silinemedi')
        }
      },
    })
  }

  const getMenuItems = (plan: AccountPlanDTO) => [
    {
      key: 'activate',
      label: 'Varsayılan Yap',
      disabled: plan.isDefault,
      onClick: () => handleActivate(plan.id),
    },
    {
      key: 'import',
      label: 'Excel İçe Aktar',
      onClick: () => { setSelectedPlanId(plan.id); setImportModalOpen(true) },
    },
    { type: 'divider' as const },
    {
      key: 'delete',
      label: 'Planı Sil',
      danger: true,
      onClick: () => handleDelete(plan),
    },
  ]

  return (
    <div>
      {/* Header */}
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: 24 }}>
        <div>
          <Title level={4} style={{ margin: 0, color: '#f1f5f9' }}>Hesap Planları</Title>
          <Text style={{ color: '#64748b', fontSize: 13 }}>
            Muhasebe hesap planlarını yönetin
          </Text>
        </div>
        <div style={{ display: 'flex', gap: 8 }}>
          <Tooltip title="Türk Tekdüzen Hesap Planını bu tenant için yükle">
            <Button
              icon={<ThunderboltOutlined />}
              onClick={handleLoadTdhp}
              loading={tdhpLoading}
              style={{ borderColor: '#6366f1', color: '#a5b4fc' }}
            >
              Standart TDHP Yükle
            </Button>
          </Tooltip>
          <Button
            type="primary"
            icon={<PlusOutlined />}
            onClick={() => setCreateModalOpen(true)}
            style={{ background: '#6366f1', borderColor: '#6366f1' }}
          >
            Yeni Plan Oluştur
          </Button>
        </div>
      </div>

      {/* Plan grid */}
      {loading ? (
        <div style={{ textAlign: 'center', padding: 60 }}>
          <Spin size="large" />
        </div>
      ) : plans.length === 0 ? (
        <Empty
          image={Empty.PRESENTED_IMAGE_SIMPLE}
          description={
            <span style={{ color: '#64748b' }}>
              Henüz hesap planı yok. TDHP yükleyin veya yeni bir plan oluşturun.
            </span>
          }
          style={{ marginTop: 60 }}
        />
      ) : (
        <Row gutter={[16, 16]}>
          {plans.map((plan) => (
            <Col key={plan.id} xs={24} sm={12} lg={8}>
              <Card
                style={{
                  background: '#1e293b',
                  border: plan.isDefault ? '1px solid #6366f1' : '1px solid #334155',
                  borderRadius: 12,
                  cursor: 'pointer',
                }}
                bodyStyle={{ padding: 20 }}
                hoverable
                onClick={() => router.push(`/accounting/${plan.id}`)}
              >
                <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start' }}>
                  <div style={{ display: 'flex', gap: 12, alignItems: 'flex-start', flex: 1 }}>
                    <div
                      style={{
                        width: 40,
                        height: 40,
                        borderRadius: 10,
                        background: 'rgba(99,102,241,0.15)',
                        display: 'flex',
                        alignItems: 'center',
                        justifyContent: 'center',
                        flexShrink: 0,
                      }}
                    >
                      <BookOutlined style={{ color: '#6366f1', fontSize: 18 }} />
                    </div>
                    <div style={{ flex: 1 }}>
                      <div style={{ color: '#f1f5f9', fontWeight: 600, fontSize: 15, lineHeight: 1.3 }}>
                        {plan.name}
                      </div>
                      <div style={{ color: '#64748b', fontSize: 12, marginTop: 2 }}>
                        {plan.code}
                      </div>
                    </div>
                  </div>
                  <Dropdown
                    menu={{ items: getMenuItems(plan) }}
                    trigger={['click']}
                    placement="bottomRight"
                  >
                    <Button
                      type="text"
                      size="small"
                      icon={<MoreOutlined />}
                      style={{ color: '#64748b' }}
                      onClick={(e) => e.stopPropagation()}
                    />
                  </Dropdown>
                </div>

                <div style={{ marginTop: 16, display: 'flex', flexDirection: 'column', gap: 8 }}>
                  {plan.description && (
                    <Text style={{ color: '#94a3b8', fontSize: 12 }} ellipsis>
                      {plan.description}
                    </Text>
                  )}
                  <div style={{ display: 'flex', gap: 6, flexWrap: 'wrap', alignItems: 'center' }}>
                    {plan.planType && PLAN_TYPE_LABELS[plan.planType] && (
                      <Tag color={PLAN_TYPE_LABELS[plan.planType].color} style={{ fontSize: 11 }}>
                        {PLAN_TYPE_LABELS[plan.planType].label}
                      </Tag>
                    )}
                    {plan.isDefault && (
                      <Tag color="gold" style={{ fontSize: 11 }}>Varsayılan</Tag>
                    )}
                    {!plan.isActive && (
                      <Tag color="default" style={{ fontSize: 11 }}>Pasif</Tag>
                    )}
                  </div>
                  <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginTop: 4 }}>
                    <Text style={{ color: '#6366f1', fontWeight: 600, fontSize: 14 }}>
                      {plan.accountCount} hesap
                    </Text>
                    <Button
                      type="link"
                      size="small"
                      style={{ color: '#94a3b8', padding: 0, fontSize: 12 }}
                      onClick={(e) => { e.stopPropagation(); router.push(`/accounting/${plan.id}`) }}
                    >
                      Görüntüle →
                    </Button>
                  </div>
                </div>
              </Card>
            </Col>
          ))}
        </Row>
      )}

      {/* Create Plan Modal */}
      <Modal
        open={createModalOpen}
        title="Yeni Hesap Planı Oluştur"
        onCancel={() => { setCreateModalOpen(false); form.resetFields() }}
        onOk={() => form.validateFields().then(handleCreate)}
        okText="Oluştur"
        cancelText="İptal"
        confirmLoading={saving}
        destroyOnClose
      >
        <Form form={form} layout="vertical" style={{ paddingTop: 8 }}>
          <Form.Item
            name="code"
            label="Plan Kodu"
            rules={[{ required: true, message: 'Plan kodu zorunludur' }, { max: 20 }]}
          >
            <Input placeholder="Örn: OZEL-2024" maxLength={20} />
          </Form.Item>
          <Form.Item
            name="name"
            label="Plan Adı"
            rules={[{ required: true, message: 'Plan adı zorunludur' }]}
          >
            <Input placeholder="Plan adı" maxLength={255} />
          </Form.Item>
          <Form.Item name="planType" label="Plan Türü" initialValue="CUSTOM">
            <Select>
              <Select.Option value="CUSTOM">Özel Plan</Select.Option>
              <Select.Option value="IMPORTED">İçe Aktarılmış</Select.Option>
            </Select>
          </Form.Item>
          <Form.Item name="effectiveDate" label="Geçerlilik Tarihi">
            <DatePicker style={{ width: '100%' }} format="DD.MM.YYYY" />
          </Form.Item>
          <Form.Item name="description" label="Açıklama">
            <TextArea rows={3} placeholder="Opsiyonel açıklama" />
          </Form.Item>
        </Form>
      </Modal>

      {/* Import Modal */}
      {selectedPlanId && (
        <ImportModal
          open={importModalOpen}
          planId={selectedPlanId}
          onCancel={() => { setImportModalOpen(false); setSelectedPlanId(null) }}
          onSuccess={() => { setImportModalOpen(false); setSelectedPlanId(null); fetchPlans() }}
        />
      )}

      {/* Floating import button for Excel upload to first plan */}
      <Button
        icon={<CloudUploadOutlined />}
        style={{
          position: 'fixed',
          bottom: 32,
          right: 32,
          borderColor: '#334155',
          color: '#94a3b8',
          background: '#1e293b',
        }}
        onClick={() => {
          if (plans.length > 0) {
            setSelectedPlanId(plans[0].id)
            setImportModalOpen(true)
          } else {
            message.warning('Önce bir hesap planı oluşturun')
          }
        }}
      >
        Excel&apos;den Yükle
      </Button>
    </div>
  )
}
