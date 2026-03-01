'use client'

import { Table, Tag, Button, Space, Tooltip, Popconfirm } from 'antd'
import type { ColumnsType } from 'antd/es/table'
import { EditOutlined, DeleteOutlined, PlusOutlined } from '@ant-design/icons'

interface ChartAccountDTO {
  id: number
  code: string
  name: string
  accountType: string
  normalBalance: string
  level: number
  isDetail: boolean
  isActive: boolean
  isBlocked: boolean
  currency?: string
  taxCode?: string
  parentCode?: string
}

interface AccountTableProps {
  data: ChartAccountDTO[]
  loading?: boolean
  onAdd?: () => void
  onEdit?: (record: ChartAccountDTO) => void
  onDelete?: (id: number) => void
  parentCode?: string
}

const TYPE_LABELS: Record<string, { label: string; color: string }> = {
  ASSET:     { label: 'Varlık',      color: 'blue' },
  LIABILITY: { label: 'Borç',        color: 'red' },
  EQUITY:    { label: 'Özkaynak',    color: 'purple' },
  REVENUE:   { label: 'Gelir',       color: 'green' },
  EXPENSE:   { label: 'Gider',       color: 'orange' },
  COST:      { label: 'Maliyet',     color: 'gold' },
}

const BALANCE_LABELS: Record<string, string> = {
  DEBIT: 'B', CREDIT: 'A',
}

export default function AccountTable({
  data,
  loading,
  onAdd,
  onEdit,
  onDelete,
  parentCode,
}: AccountTableProps) {
  const columns: ColumnsType<ChartAccountDTO> = [
    {
      title: 'Kod',
      dataIndex: 'code',
      width: 100,
      render: (code: string) => (
        <span style={{ fontFamily: 'monospace', fontWeight: 600, color: '#a5b4fc' }}>{code}</span>
      ),
    },
    {
      title: 'Hesap Adı',
      dataIndex: 'name',
      render: (name: string, record) => (
        <span style={{ color: record.isActive ? '#f1f5f9' : '#64748b' }}>{name}</span>
      ),
    },
    {
      title: 'Tür',
      dataIndex: 'accountType',
      width: 110,
      render: (type: string) => {
        const t = TYPE_LABELS[type]
        return t ? <Tag color={t.color}>{t.label}</Tag> : <Tag>{type}</Tag>
      },
    },
    {
      title: 'NB',
      dataIndex: 'normalBalance',
      width: 50,
      align: 'center' as const,
      render: (nb: string) => (
        <span style={{ color: nb === 'DEBIT' ? '#38bdf8' : '#f472b6', fontWeight: 600 }}>
          {BALANCE_LABELS[nb] ?? nb}
        </span>
      ),
    },
    {
      title: 'Det.',
      dataIndex: 'isDetail',
      width: 50,
      align: 'center' as const,
      render: (v: boolean) => v ? <span style={{ color: '#4ade80' }}>✓</span> : <span style={{ color: '#475569' }}>-</span>,
    },
    {
      title: 'Döviz',
      dataIndex: 'currency',
      width: 70,
      render: (v?: string) => v ? <Tag>{v}</Tag> : null,
    },
    {
      title: 'Durum',
      dataIndex: 'isActive',
      width: 80,
      render: (active: boolean, record) => {
        if (record.isBlocked) return <Tag color="red">Bloklu</Tag>
        return active ? <Tag color="green">Aktif</Tag> : <Tag color="default">Pasif</Tag>
      },
    },
    {
      title: '',
      key: 'actions',
      width: 80,
      align: 'right' as const,
      render: (_: unknown, record: ChartAccountDTO) => (
        <Space size={4}>
          {onEdit && (
            <Tooltip title="Düzenle">
              <Button
                type="text"
                size="small"
                icon={<EditOutlined />}
                onClick={() => onEdit(record)}
                style={{ color: '#94a3b8' }}
              />
            </Tooltip>
          )}
          {onDelete && (
            <Popconfirm
              title="Bu hesabı silmek istiyor musunuz?"
              onConfirm={() => onDelete(record.id)}
              okText="Sil"
              cancelText="İptal"
              okButtonProps={{ danger: true }}
            >
              <Tooltip title="Sil">
                <Button
                  type="text"
                  size="small"
                  danger
                  icon={<DeleteOutlined />}
                />
              </Tooltip>
            </Popconfirm>
          )}
        </Space>
      ),
    },
  ]

  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: 12, height: '100%' }}>
      {onAdd && (
        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
          <span style={{ color: '#94a3b8', fontSize: 13 }}>
            {parentCode ? `${parentCode} altındaki hesaplar` : 'Tüm hesaplar'}
          </span>
          <Button
            type="primary"
            size="small"
            icon={<PlusOutlined />}
            onClick={onAdd}
            style={{ background: '#6366f1', borderColor: '#6366f1' }}
          >
            Alt Hesap Ekle
          </Button>
        </div>
      )}
      <Table
        columns={columns}
        dataSource={data}
        rowKey="id"
        loading={loading}
        size="small"
        pagination={{ pageSize: 20, showSizeChanger: false, hideOnSinglePage: true }}
        style={{ flex: 1 }}
        scroll={{ y: 'calc(100vh - 280px)' }}
        rowClassName={(record) => (!record.isActive ? 'opacity-50' : '')}
      />
    </div>
  )
}
