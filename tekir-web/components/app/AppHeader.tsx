'use client'

import { useRouter, usePathname } from 'next/navigation'
import { Dropdown, Button, Tag, Typography } from 'antd'
import { UserOutlined, LogoutOutlined, SettingOutlined, DownOutlined } from '@ant-design/icons'
import type { MenuProps } from 'antd'
import useAuthStore from '@/store/authStore'

const pageTitleMap: Record<string, string> = {
  '/dashboard': 'Genel Bakış',
  '/invoices': 'Faturalar',
  '/contacts': 'Cari Hesaplar',
  '/products': 'Ürünler',
  '/orders': 'Siparişler',
  '/payments': 'Ödemeler',
  '/bank': 'Banka',
  '/reports': 'Raporlar',
  '/settings': 'Ayarlar',
  '/accounting': 'Muhasebe',
}

function getPageTitle(pathname: string): string {
  for (const [key, value] of Object.entries(pageTitleMap)) {
    if (pathname.startsWith(key)) return value
  }
  return 'Tekir ERP'
}

export default function AppHeader() {
  const pathname = usePathname()
  const router = useRouter()
  const { tenantId, logout } = useAuthStore()

  const handleLogout = () => {
    logout()
    router.push('/login')
  }

  const userMenuItems: MenuProps['items'] = [
    {
      key: 'profile',
      icon: <UserOutlined />,
      label: 'Profil',
    },
    {
      key: 'settings',
      icon: <SettingOutlined />,
      label: 'Ayarlar',
      onClick: () => router.push('/settings'),
    },
    { type: 'divider' },
    {
      key: 'logout',
      icon: <LogoutOutlined />,
      label: 'Çıkış Yap',
      danger: true,
      onClick: handleLogout,
    },
  ]

  return (
    <header
      style={{
        height: 64,
        background: '#0f172a',
        borderBottom: '1px solid #1e293b',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'space-between',
        padding: '0 24px',
        position: 'sticky',
        top: 0,
        zIndex: 40,
      }}
    >
      {/* Page title */}
      <Typography.Title
        level={4}
        style={{ margin: 0, color: '#f1f5f9', fontWeight: 600 }}
      >
        {getPageTitle(pathname)}
      </Typography.Title>

      {/* Right side */}
      <div style={{ display: 'flex', alignItems: 'center', gap: 12 }}>
        {tenantId && (
          <Tag
            style={{
              background: 'rgba(99, 102, 241, 0.15)',
              border: '1px solid rgba(99, 102, 241, 0.4)',
              color: '#a5b4fc',
              borderRadius: 6,
              fontSize: 12,
              padding: '2px 10px',
            }}
          >
            Kiracı #{tenantId}
          </Tag>
        )}

        <Dropdown menu={{ items: userMenuItems }} placement="bottomRight" trigger={['click']}>
          <Button
            type="text"
            style={{
              color: '#94a3b8',
              display: 'flex',
              alignItems: 'center',
              gap: 6,
              height: 36,
              padding: '0 12px',
              borderRadius: 8,
              border: '1px solid #1e293b',
            }}
          >
            <UserOutlined />
            <span style={{ fontSize: 13 }}>Hesabım</span>
            <DownOutlined style={{ fontSize: 10 }} />
          </Button>
        </Dropdown>
      </div>
    </header>
  )
}
