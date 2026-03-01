-- ==========================================================================
-- Test Seed: Multi-Tenant Login Flow
-- Aynı e-posta ile 2 farklı tenant'ta kullanıcı oluşturur.
-- Sadece test/geliştirme ortamı için kullanılmalıdır.
-- ==========================================================================

-- 2. test tenant
INSERT INTO TENANT (NAME, SLUG, STATUS, ACTIVE, CREATE_DATE, UPDATE_DATE)
VALUES ('Demo Şirketi B', 'demo-b', 'ACTIVE', true, NOW(), NOW())
ON CONFLICT (SLUG) DO NOTHING;

-- admin kullanıcısının e-postasını bul ve 2. tenant'ta klonla
INSERT INTO USERS (USER_NAME, PASSWORD, EMAIL, FULL_NAME, ISACTIVE, TENANT_ID, CREATE_DATE, UPDATE_DATE)
SELECT
    'admin_b',
    u.PASSWORD,
    u.EMAIL,
    u.FULL_NAME,
    true,
    CAST((SELECT ID FROM TENANT WHERE SLUG = 'demo-b') AS VARCHAR),
    NOW(),
    NOW()
FROM USERS u
WHERE u.USER_NAME = 'admin'
ON CONFLICT (USER_NAME) DO NOTHING;
