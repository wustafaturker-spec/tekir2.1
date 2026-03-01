'use client'

import { useEffect } from 'react'
import {
  Modal,
  Form,
  Input,
  Select,
  Checkbox,
  Radio,
  TreeSelect,
} from 'antd'

const { TextArea } = Input

interface AccountNode {
  id: number
  code: string
  name: string
  children?: AccountNode[]
}

interface ChartAccountDTO {
  id: number
  planId: number
  code: string
  name: string
  description?: string
  accountType: string
  normalBalance: string
  level: number
  parentId?: number
  parentCode?: string
  isDetail: boolean
  isActive: boolean
  currency?: string
  taxCode?: string
  isBlocked: boolean
}

interface AccountFormProps {
  open: boolean
  onCancel: () => void
  onSubmit: (values: Record<string, unknown>) => void
  initialValues?: Partial<ChartAccountDTO>
  treeData: AccountNode[]
  loading?: boolean
}

const ACCOUNT_TYPE_OPTIONS = [
  { value: 'ASSET',     label: 'Varlık (Asset)' },
  { value: 'LIABILITY', label: 'Borç (Liability)' },
  { value: 'EQUITY',    label: 'Özkaynaklar (Equity)' },
  { value: 'REVENUE',   label: 'Gelir (Revenue)' },
  { value: 'EXPENSE',   label: 'Gider (Expense)' },
  { value: 'COST',      label: 'Maliyet (Cost)' },
]

const DEFAULT_BALANCE: Record<string, string> = {
  ASSET: 'DEBIT', LIABILITY: 'CREDIT', EQUITY: 'CREDIT',
  REVENUE: 'CREDIT', EXPENSE: 'DEBIT', COST: 'DEBIT',
}

function toTreeSelectData(nodes: AccountNode[]): unknown[] {
  return nodes.map((n) => ({
    value: n.id,
    title: `${n.code} - ${n.name}`,
    children: n.children ? toTreeSelectData(n.children) : undefined,
  }))
}

export default function AccountForm({
  open,
  onCancel,
  onSubmit,
  initialValues,
  treeData,
  loading,
}: AccountFormProps) {
  const [form] = Form.useForm()
  const isEdit = !!initialValues?.id

  useEffect(() => {
    if (open) {
      if (initialValues) {
        form.setFieldsValue({
          ...initialValues,
          parentId: initialValues.parentId ?? undefined,
          isDetail: initialValues.isDetail ?? false,
        })
      } else {
        form.resetFields()
        form.setFieldsValue({ isDetail: false, normalBalance: 'DEBIT' })
      }
    }
  }, [open, initialValues, form])

  const handleTypeChange = (type: string) => {
    form.setFieldValue('normalBalance', DEFAULT_BALANCE[type] ?? 'DEBIT')
  }

  return (
    <Modal
      open={open}
      title={isEdit ? 'Hesap Düzenle' : 'Yeni Hesap Ekle'}
      onCancel={onCancel}
      onOk={() => form.validateFields().then(onSubmit)}
      okText={isEdit ? 'Güncelle' : 'Ekle'}
      cancelText="İptal"
      confirmLoading={loading}
      width={600}
      destroyOnClose
    >
      <Form form={form} layout="vertical" style={{ paddingTop: 8 }}>
        <Form.Item
          name="code"
          label="Hesap Kodu"
          rules={[
            { required: true, message: 'Hesap kodu zorunludur' },
            { pattern: /^\d{1,20}$/, message: 'Sadece rakamlardan oluşmalıdır (max 20 hane)' },
          ]}
        >
          <Input placeholder="Örn: 1000" maxLength={20} disabled={isEdit} />
        </Form.Item>

        <Form.Item
          name="name"
          label="Hesap Adı"
          rules={[{ required: true, message: 'Hesap adı zorunludur' }]}
        >
          <Input placeholder="Hesap adını girin" maxLength={255} />
        </Form.Item>

        <Form.Item name="parentId" label="Üst Hesap">
          <TreeSelect
            treeData={toTreeSelectData(treeData) as never[]}
            placeholder="Üst hesap seçin (opsiyonel)"
            allowClear
            showSearch
            treeNodeFilterProp="title"
            style={{ width: '100%' }}
          />
        </Form.Item>

        <Form.Item
          name="accountType"
          label="Hesap Türü"
          rules={[{ required: true, message: 'Hesap türü zorunludur' }]}
        >
          <Select options={ACCOUNT_TYPE_OPTIONS} onChange={handleTypeChange} placeholder="Seçin" />
        </Form.Item>

        <Form.Item
          name="normalBalance"
          label="Normal Bakiye"
          rules={[{ required: true, message: 'Normal bakiye zorunludur' }]}
        >
          <Radio.Group>
            <Radio value="DEBIT">Borç (Debit)</Radio>
            <Radio value="CREDIT">Alacak (Credit)</Radio>
          </Radio.Group>
        </Form.Item>

        <Form.Item name="isDetail" valuePropName="checked">
          <Checkbox>Detay Hesap (Yevmiyeye yazılabilir)</Checkbox>
        </Form.Item>

        <Form.Item name="currency" label="Döviz (opsiyonel)">
          <Input placeholder="USD, EUR, GBP..." maxLength={3} style={{ width: 120 }} />
        </Form.Item>

        <Form.Item name="taxCode" label="KDV Kodu (opsiyonel)">
          <Input placeholder="KDV1, KDV8..." maxLength={10} style={{ width: 160 }} />
        </Form.Item>

        <Form.Item name="description" label="Açıklama">
          <TextArea rows={3} placeholder="Opsiyonel açıklama" maxLength={500} />
        </Form.Item>
      </Form>
    </Modal>
  )
}
