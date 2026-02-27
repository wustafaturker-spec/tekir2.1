Write-Host "BigDecimal operasyonlari duzeltiliyor..." -ForegroundColor Cyan

$files = Get-ChildItem -Recurse -Filter *.java

foreach ($file in $files) {

    $content = Get-Content $file.FullName -Raw
    $original = $content

    # çarpma
    $content = $content -replace '(\w+)\s*\*\s*(\w+)', '$1.multiply($2)'

    # toplama
    $content = $content -replace '(\w+)\s*\+\s*(\w+)', '$1.add($2)'

    # çıkarma
    $content = $content -replace '(\w+)\s*-\s*(\w+)', '$1.subtract($2)'

    # bölme
    $content = $content -replace '(\w+)\s*/\s*(\w+)', '$1.divide($2, 2, java.math.RoundingMode.HALF_UP)'

    if ($content -ne $original) {
        Set-Content $file.FullName $content -Encoding UTF8
        Write-Host "Duzenlendi -> $($file.Name)" -ForegroundColor Green
    }
}

Write-Host "Tamamlandi." -ForegroundColor Yellow
