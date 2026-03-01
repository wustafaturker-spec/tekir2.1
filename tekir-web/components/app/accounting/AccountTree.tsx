'use client'

import { useState } from 'react'
import { Tree, Input, Typography } from 'antd'
import type { DataNode } from 'antd/es/tree'

const { Search } = Input
const { Text } = Typography

interface AccountNode {
  id: number
  code: string
  name: string
  accountType: string
  level: number
  isDetail: boolean
  isActive: boolean
  children?: AccountNode[]
}

interface AccountTreeProps {
  data: AccountNode[]
  onSelect: (node: AccountNode) => void
  selectedId?: number
}

const TYPE_COLOR: Record<string, string> = {
  ASSET: '#38bdf8', LIABILITY: '#f87171', EQUITY: '#a78bfa',
  REVENUE: '#4ade80', EXPENSE: '#fb923c', COST: '#fbbf24',
}

function toAntTree(nodes: AccountNode[]): DataNode[] {
  return nodes.map((n) => ({
    key: n.id,
    title: (
      <span style={{ display: 'flex', alignItems: 'center', gap: 6 }}>
        <Text
          style={{
            fontSize: 11,
            fontFamily: 'monospace',
            color: TYPE_COLOR[n.accountType] ?? '#94a3b8',
            minWidth: 40,
          }}
        >
          {n.code}
        </Text>
        <Text style={{ color: n.isActive ? '#f1f5f9' : '#64748b', fontSize: 13 }}>
          {n.name}
        </Text>
        {n.isDetail && (
          <span
            style={{
              fontSize: 10,
              background: 'rgba(99,102,241,0.2)',
              color: '#a5b4fc',
              borderRadius: 3,
              padding: '1px 5px',
            }}
          >
            D
          </span>
        )}
      </span>
    ),
    children: n.children && n.children.length > 0 ? toAntTree(n.children) : undefined,
    isLeaf: !n.children || n.children.length === 0,
    _data: n,
  }))
}

function filterTree(nodes: AccountNode[], query: string): AccountNode[] {
  if (!query) return nodes
  const q = query.toLowerCase()
  return nodes.reduce<AccountNode[]>((acc, node) => {
    const match = node.code.toLowerCase().includes(q) || node.name.toLowerCase().includes(q)
    const filteredChildren = filterTree(node.children ?? [], query)
    if (match || filteredChildren.length > 0) {
      acc.push({ ...node, children: filteredChildren })
    }
    return acc
  }, [])
}

export default function AccountTree({ data, onSelect, selectedId }: AccountTreeProps) {
  const [search, setSearch] = useState('')

  const filtered = filterTree(data, search)
  const treeData = toAntTree(filtered)

  const handleSelect = (_keys: unknown[], info: { node: { _data?: AccountNode } }) => {
    if (info.node._data) {
      onSelect(info.node._data)
    }
  }

  return (
    <div style={{ display: 'flex', flexDirection: 'column', height: '100%', gap: 8 }}>
      <Search
        placeholder="Kod veya hesap adı ara..."
        value={search}
        onChange={(e) => setSearch(e.target.value)}
        allowClear
        size="small"
        style={{ flexShrink: 0 }}
      />
      <div style={{ flex: 1, overflowY: 'auto' }}>
        <Tree
          treeData={treeData}
          selectedKeys={selectedId ? [selectedId] : []}
          onSelect={handleSelect as never}
          defaultExpandAll={data.length < 50}
          blockNode
          style={{
            background: 'transparent',
            fontSize: 13,
          }}
        />
      </div>
    </div>
  )
}
