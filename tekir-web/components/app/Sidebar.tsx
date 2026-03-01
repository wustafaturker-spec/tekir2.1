'use client'

import { useRouter, usePathname } from 'next/navigation'
import { Menu } from 'antd'
import {
  DashboardOutlined,
  FileTextOutlined,
  UserOutlined,
  AppstoreOutlined,
  UnorderedListOutlined,
  CreditCardOutlined,
  BankOutlined,
  BarChartOutlined,
  SettingOutlined,
  LogoutOutlined,
  BookOutlined,
} from '@ant-design/icons'
import Link from 'next/link'
import useAuthStore from '@/store/authStore'

export default function Sidebar() {
  const pathname = usePathname()
  const router = useRouter()
  const logout = useAuthStore((s) => s.logout)

  const handleLogout = () => {
    logout()
    router.push('/login')
  }

  // Determine selected key (accounting sub-pages match '/accounting')
  const getSelectedKey = () => {
    if (pathname.startsWith('/accounting')) return '/accounting'
    const navKeys = ['/dashboard', '/invoices', '/contacts', '/products', '/orders', '/payments', '/bank', '/reports']
    return navKeys.find((k) => pathname.startsWith(k)) ?? '/dashboard'
  }

  const menuItems = [
    {
      key: '/dashboard',
      icon: <DashboardOutlined />,
      label: <Link href="/dashboard">Genel Bakış</Link>,
    },
    {
      key: '/invoices',
      icon: <FileTextOutlined />,
      label: <Link href="/invoices">Faturalar</Link>,
    },
    {
      key: '/contacts',
      icon: <UserOutlined />,
      label: <Link href="/contacts">Cari Hesaplar</Link>,
    },
    {
      key: '/products',
      icon: <AppstoreOutlined />,
      label: <Link href="/products">Ürünler</Link>,
    },
    {
      key: '/orders',
      icon: <UnorderedListOutlined />,
      label: <Link href="/orders">Siparişler</Link>,
    },
    {
      key: '/payments',
      icon: <CreditCardOutlined />,
      label: <Link href="/payments">Ödemeler</Link>,
    },
    {
      key: '/bank',
      icon: <BankOutlined />,
      label: <Link href="/bank">Banka</Link>,
    },
    {
      key: '/reports',
      icon: <BarChartOutlined />,
      label: <Link href="/reports">Raporlar</Link>,
    },
    {
      key: '/accounting',
      icon: <BookOutlined />,
      label: 'Muhasebe',
      children: [
        {
          key: '/accounting',
          label: <Link href="/accounting">Hesap Planları</Link>,
        },
      ],
    },
    { type: 'divider' as const },
    {
      key: '/settings',
      icon: <SettingOutlined />,
      label: <Link href="/settings">Ayarlar</Link>,
    },
    {
      key: '__logout',
      icon: <LogoutOutlined />,
      label: 'Çıkış Yap',
      danger: true,
      onClick: handleLogout,
    },
  ]

  return (
    <div
      style={{
        width: 240,
        height: '100vh',
        background: '#0f172a',
        borderRight: '1px solid #1e293b',
        display: 'flex',
        flexDirection: 'column',
        position: 'fixed',
        top: 0,
        left: 0,
        zIndex: 50,
        overflowY: 'auto',
      }}
    >
      {/* Logo */}
      <div
        style={{
          height: 64,
          display: 'flex',
          alignItems: 'center',
          padding: '0 20px',
          borderBottom: '1px solid #1e293b',
          flexShrink: 0,
        }}
      >
        <Link href="/dashboard" style={{ display: 'flex', alignItems: 'center', gap: 8 }}>
          <div
            style={{
              width: 34,
              height: 34,
              background: 'linear-gradient(135deg, #6366f1, #8b5cf6)',
              borderRadius: 8,
              display: 'flex',
              alignItems: 'center',
              justifyContent: 'center',
              color: 'white',
              fontWeight: 700,
              fontSize: 16,
              flexShrink: 0,
            }}
          >
            T
          </div>
          <span style={{ color: '#f1f5f9', fontWeight: 700, fontSize: 18 }}>Tekir</span>
        </Link>
      </div>

      {/* Navigation */}
      <Menu
        mode="inline"
        selectedKeys={[getSelectedKey()]}
        defaultOpenKeys={['/accounting']}
        items={menuItems}
        style={{
          background: 'transparent',
          border: 'none',
          flex: 1,
          paddingTop: 8,
          paddingBottom: 8,
        }}
        theme="dark"
      />
    </div>
  )
}
