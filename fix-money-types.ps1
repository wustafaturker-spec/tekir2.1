Write-Host "Tekir Hibernate 6 Fix Basladi..." -ForegroundColor Cyan

$files = Get-ChildItem -Recurse -Filter *.java

foreach ($file in $files) {

    $content = Get-Content $file.FullName -Raw

    if ($content -match "precision\s*=\s*\d+\s*,\s*scale\s*=\s*\d+") {

        $original = $content

        # Double -> BigDecimal field
        $content = $content -replace '\bDouble\b', 'BigDecimal'

        # Getter
        $content = $content -replace 'public\s+BigDecimal\s+get', 'public BigDecimal get'

        # Setter
        $content = $content -replace 'set\(\s*BigDecimal', 'set(BigDecimal'

        # Import ekle
        if ($content -notmatch "import java.math.BigDecimal;") {

            $content = $content -replace "(package .*?;)", "`$1`r`nimport java.math.BigDecimal;"
        }

        if ($content -ne $original) {
            Set-Content $file.FullName $content -Encoding UTF8
            Write-Host "Duzenlendi -> $($file.Name)" -ForegroundColor Green
        }
    }
}

Write-Host "Tamamlandi. Artik proje Hibernate 6 uyumlu." -ForegroundColor Yellow
