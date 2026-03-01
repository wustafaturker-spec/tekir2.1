'use client'

import { useState } from 'react'
import { Modal, Upload, Button, Table, Typography, Alert, Progress, Space } from 'antd'
import { InboxOutlined, DownloadOutlined } from '@ant-design/icons'
import type { UploadFile, UploadProps } from 'antd/es/upload'
import api from '@/lib/axios'

const { Dragger } = Upload
const { Text } = Typography

interface ImportError {
  rowNumber: number
  code: string
  message: string
}

interface ImportResult {
  totalRows: number
  successCount: number
  errorCount: number
  errors: ImportError[]
}

interface ImportModalProps {
  open: boolean
  planId: number
  onCancel: () => void
  onSuccess: () => void
}

export default function ImportModal({ open, planId, onCancel, onSuccess }: ImportModalProps) {
  const [fileList, setFileList] = useState<UploadFile[]>([])
  const [uploading, setUploading] = useState(false)
  const [result, setResult] = useState<ImportResult | null>(null)

  const handleDownloadTemplate = async () => {
    try {
      const resp = await api.get('/accounting/plans/import/template', {
        responseType: 'blob',
      })
      const url = window.URL.createObjectURL(new Blob([resp.data]))
      const link = document.createElement('a')
      link.href = url
      link.setAttribute('download', 'hesap-plani-sablonu.xlsx')
      document.body.appendChild(link)
      link.click()
      link.remove()
    } catch {
      // silently fail
    }
  }

  const handleUpload = async () => {
    if (fileList.length === 0) return
    const formData = new FormData()
    formData.append('file', fileList[0] as unknown as File)

    setUploading(true)
    try {
      const resp = await api.post<ImportResult>(
        `/accounting/plans/${planId}/import`,
        formData,
        { headers: { 'Content-Type': 'multipart/form-data' } }
      )
      setResult(resp.data)
      if (resp.data.successCount > 0) onSuccess()
    } catch (e: unknown) {
      const err = e as { response?: { data?: { message?: string } } }
      setResult({
        totalRows: 0,
        successCount: 0,
        errorCount: 1,
        errors: [{ rowNumber: 0, code: '', message: err.response?.data?.message ?? 'Yükleme başarısız' }],
      })
    } finally {
      setUploading(false)
    }
  }

  const uploadProps: UploadProps = {
    fileList,
    beforeUpload: (file) => {
      setFileList([file as unknown as UploadFile])
      setResult(null)
      return false
    },
    onRemove: () => {
      setFileList([])
      setResult(null)
    },
    accept: '.xlsx,.xls',
    maxCount: 1,
  }

  const errorColumns = [
    { title: 'Satır', dataIndex: 'rowNumber', width: 60 },
    { title: 'Kod', dataIndex: 'code', width: 80 },
    { title: 'Hata', dataIndex: 'message' },
  ]

  const handleClose = () => {
    setFileList([])
    setResult(null)
    onCancel()
  }

  return (
    <Modal
      open={open}
      title="Excel'den Hesap Planı Yükle"
      onCancel={handleClose}
      footer={
        result ? (
          <Button onClick={handleClose}>Kapat</Button>
        ) : (
          <Space>
            <Button onClick={handleClose}>İptal</Button>
            <Button
              type="primary"
              onClick={handleUpload}
              loading={uploading}
              disabled={fileList.length === 0}
              style={{ background: '#6366f1', borderColor: '#6366f1' }}
            >
              Yükle
            </Button>
          </Space>
        )
      }
      width={680}
      destroyOnClose
    >
      <Space direction="vertical" style={{ width: '100%' }} size={16}>
        <div style={{ display: 'flex', justifyContent: 'flex-end' }}>
          <Button
            size="small"
            icon={<DownloadOutlined />}
            onClick={handleDownloadTemplate}
            style={{ color: '#6366f1', borderColor: '#6366f1' }}
          >
            Excel Şablonu İndir
          </Button>
        </div>

        {!result && (
          <Dragger {...uploadProps}>
            <p className="ant-upload-drag-icon">
              <InboxOutlined style={{ color: '#6366f1', fontSize: 40 }} />
            </p>
            <p style={{ color: '#f1f5f9' }}>
              Dosyayı sürükleyin ya da tıklayın
            </p>
            <p style={{ color: '#64748b', fontSize: 12 }}>
              Yalnızca .xlsx ve .xls dosyaları desteklenmektedir
            </p>
          </Dragger>
        )}

        {uploading && (
          <Progress percent={99} status="active" strokeColor="#6366f1" />
        )}

        {result && (
          <Space direction="vertical" style={{ width: '100%' }} size={12}>
            <Alert
              type={result.errorCount === 0 ? 'success' : result.successCount > 0 ? 'warning' : 'error'}
              message={
                <span>
                  Toplam <strong>{result.totalRows}</strong> satır işlendi. &nbsp;
                  <Text style={{ color: '#4ade80' }}>✓ {result.successCount} başarılı</Text>
                  {result.errorCount > 0 && (
                    <> &nbsp; <Text type="danger">✗ {result.errorCount} hatalı</Text></>
                  )}
                </span>
              }
            />
            {result.errors.length > 0 && (
              <Table
                columns={errorColumns}
                dataSource={result.errors.map((e, i) => ({ ...e, key: i }))}
                size="small"
                pagination={{ pageSize: 10, hideOnSinglePage: true }}
                scroll={{ y: 200 }}
              />
            )}
          </Space>
        )}
      </Space>
    </Modal>
  )
}
