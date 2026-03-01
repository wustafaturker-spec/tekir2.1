'use client'

import { useEffect, useState, useCallback } from 'react'
import { useParams, useRouter } from 'next/navigation'
import { Button, Typography, Spin, message, Tag, Breadcrumb } from 'antd'
import { ArrowLeftOutlined, PlusOutlined } from '@ant-design/icons'
import api from '@/lib/axios'
import AccountTree from '@/components/app/accounting/AccountTree'
import AccountTable from '@/components/app/accounting/AccountTable'
import AccountForm from '@/components/app/accounting/AccountForm'

const { Title, Text } = Typography

interface AccountPlanDTO {
  id: number
  code: string
  name: string
  planType: string
  isDefault: boolean
  accountCount: number
}

interface AccountNode {
  id: number
  code: string
  name: string
  accountType: string
  normalBalance: string
  level: number
  parentId?: number
  isDetail: boolean
  isActive: boolean
  isBlocked: boolean
  currency?: string
  children?: AccountNode[]
}

interface ChartAccountDTO {
  id: number
  code: string
  name: string
  accountType: string
  normalBalance: string
  level: number
  parentId?: number
  parentCode?: string
  isDetail: boolean
  isActive: boolean
  isBlocked: boolean
  currency?: string
  taxCode?: string
  description?: string
}

export default function PlanDetailPage() {
  const params = useParams()
  const router = useRouter()
  const planId = Number(params.planId)

  const [plan, setPlan] = useState<AccountPlanDTO | null>(null)
  const [treeData, setTreeData] = useState<AccountNode[]>([])
  const [tableData, setTableData] = useState<ChartAccountDTO[]>([])
  const [flatData, setFlatData] = useState<ChartAccountDTO[]>([])
  const [loading, setLoading] = useState(true)
  const [tableLoading, setTableLoading] = useState(false)
  const [selectedNode, setSelectedNode] = useState<AccountNode | null>(null)
  const [formOpen, setFormOpen] = useState(false)
  const [editingAccount, setEditingAccount] = useState<ChartAccountDTO | null>(null)
  const [saving, setSaving] = useState(false)

  const fetchPlan = useCallback(async () => {
    try {
      const resp = await api.get<AccountPlanDTO>(`/accounting/plans/${planId}`)
      setPlan(resp.data)
    } catch {
      message.error('Plan bilgisi yüklenemedi')
    }
  }, [planId])

  const fetchTree = useCallback(async () => {
    setLoading(true)
    try {
      const resp = await api.get<AccountNode[]>(`/accounting/plans/${planId}/accounts`)
      setTreeData(resp.data)
    } catch {
      message.error('Hesap ağacı yüklenemedi')
    } finally {
      setLoading(false)
    }
  }, [planId])

  const fetchFlat = useCallback(async () => {
    try {
      const resp = await api.get<ChartAccountDTO[]>(
        `/accounting/plans/${planId}/accounts?flat=true`
      )
      setFlatData(resp.data)
    } catch {
      // ignore
    }
  }, [planId])

  useEffect(() => {
    fetchPlan()
    fetchTree()
    fetchFlat()
  }, [fetchPlan, fetchTree, fetchFlat])

  const handleNodeSelect = (node: AccountNode) => {
    setSelectedNode(node)
    setTableLoading(true)
    const children = (node.children ?? []).map((c) => ({
      id: c.id,
      code: c.code,
      name: c.name,
      accountType: c.accountType,
      normalBalance: c.normalBalance,
      level: c.level,
      parentId: c.parentId,
      isDetail: c.isDetail,
      isActive: c.isActive,
      isBlocked: c.isBlocked,
      currency: c.currency,
    })) as ChartAccountDTO[]
    setTableData(children)
    setTableLoading(false)
  }

  const refreshAll = () => {
    fetchPlan()
    fetchTree()
    fetchFlat()
    setSelectedNode(null)
    setTableData([])
  }

  const handleFormSubmit = async (values: Record<string, unknown>) => {
    setSaving(true)
    try {
      if (editingAccount) {
        await api.put(`/accounting/plans/${planId}/accounts/${editingAccount.id}`, values)
        message.success('Hesap güncellendi')
      } else {
        await api.post(`/accounting/plans/${planId}/accounts`, {
          ...values,
          parentId: selectedNode?.id ?? values.parentId ?? undefined,
        })
        message.success('Hesap eklendi')
      }
      setFormOpen(false)
      setEditingAccount(null)
      refreshAll()
    } catch (e: unknown) {
      const err = e as { response?: { data?: { message?: string } } }
      message.error(err.response?.data?.message ?? 'İşlem başarısız')
    } finally {
      setSaving(false)
    }
  }

  const handleDelete = async (id: number) => {
    try {
      await api.delete(`/accounting/plans/${planId}/accounts/${id}`)
      message.success('Hesap silindi')
      refreshAll()
    } catch (e: unknown) {
      const err = e as { response?: { data?: { message?: string } } }
      message.error(err.response?.data?.message ?? 'Hesap silinemedi')
    }
  }

  const openAddForm = () => {
    setEditingAccount(null)
    setFormOpen(true)
  }

  const openEditForm = (record: ChartAccountDTO) => {
    setEditingAccount(record)
    setFormOpen(true)
  }

  return (
    <div style={{ display: 'flex', flexDirection: 'column', height: 'calc(100vh - 128px)', gap: 16 }}>
      {/* Header */}
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', flexShrink: 0 }}>
        <div style={{ display: 'flex', alignItems: 'center', gap: 12 }}>
          <Button
            type="text"
            icon={<ArrowLeftOutlined />}
            onClick={() => router.push('/accounting')}
            style={{ color: '#94a3b8' }}
          />
          <div>
            <div style={{ display: 'flex', alignItems: 'center', gap: 8 }}>
              <Title level={4} style={{ margin: 0, color: '#f1f5f9' }}>
                {plan?.name ?? 'Yükleniyor...'}
              </Title>
              {plan?.isDefault && <Tag color="gold">Varsayılan</Tag>}
            </div>
            <Text style={{ color: '#64748b', fontSize: 12 }}>
              {plan?.code} · {plan?.accountCount ?? 0} hesap
            </Text>
          </div>
        </div>
        <Button
          type="primary"
          icon={<PlusOutlined />}
          onClick={openAddForm}
          style={{ background: '#6366f1', borderColor: '#6366f1' }}
        >
          Hesap Ekle
        </Button>
      </div>

      {/* Split view */}
      <div
        style={{
          display: 'flex',
          gap: 0,
          flex: 1,
          overflow: 'hidden',
          border: '1px solid #1e293b',
          borderRadius: 12,
        }}
      >
        {/* Left: Tree */}
        <div
          style={{
            width: 280,
            flexShrink: 0,
            background: '#0f172a',
            borderRight: '1px solid #1e293b',
            padding: 12,
            overflow: 'hidden',
            display: 'flex',
            flexDirection: 'column',
          }}
        >
          {loading ? (
            <div style={{ textAlign: 'center', paddingTop: 40 }}>
              <Spin />
            </div>
          ) : (
            <AccountTree
              data={treeData}
              onSelect={handleNodeSelect}
              selectedId={selectedNode?.id}
            />
          )}
        </div>

        {/* Right: Table */}
        <div
          style={{
            flex: 1,
            background: '#0f172a',
            padding: 16,
            overflow: 'hidden',
            display: 'flex',
            flexDirection: 'column',
          }}
        >
          {selectedNode ? (
            <AccountTable
              data={tableData}
              loading={tableLoading}
              onAdd={openAddForm}
              onEdit={openEditForm}
              onDelete={handleDelete}
              parentCode={selectedNode.code}
            />
          ) : (
            <div
              style={{
                flex: 1,
                display: 'flex',
                flexDirection: 'column',
                alignItems: 'center',
                justifyContent: 'center',
                color: '#475569',
                gap: 8,
              }}
            >
              <span style={{ fontSize: 32 }}>📋</span>
              <Text style={{ color: '#475569' }}>
                Sol panelden bir hesap seçin
              </Text>
              <Text style={{ color: '#334155', fontSize: 12 }}>
                Seçilen hesabın alt hesapları burada görünecek
              </Text>
            </div>
          )}
        </div>
      </div>

      {/* Account Form Modal */}
      <AccountForm
        open={formOpen}
        onCancel={() => { setFormOpen(false); setEditingAccount(null) }}
        onSubmit={handleFormSubmit}
        initialValues={editingAccount ?? undefined}
        treeData={treeData}
        loading={saving}
      />
    </div>
  )
}
