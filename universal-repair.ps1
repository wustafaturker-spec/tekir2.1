$baseDir = "C:\Users\wusta\Desktop\tekir2-master\tekir2-master\tekir-modern"
$files = Get-ChildItem -Recurse -Filter *.java -Path $baseDir

$patterns = @{
    '\.divide\(v1,\s*2,\s*java\.math\.RoundingMode\.HALF_UP\)' = '/v1';
    '\.divide\(Banka,\s*2,\s*java\.math\.RoundingMode\.HALF_UP\)' = '/Banka';
    '\.divide\(BCrypt,\s*2,\s*java\.math\.RoundingMode\.HALF_UP\)' = '/BCrypt';
    '\.divide\(min,\s*2,\s*java\.math\.RoundingMode\.HALF_UP\)' = '/min';
    '\.divide\(Out,\s*2,\s*java\.math\.RoundingMode\.HALF_UP\)' = '/Out';
    '\.subtract\(movements\)' = '-movements';
    '\.subtract\(limit\)' = '-limit';
    '\.subtract\(Remaining\)' = '-Remaining';
    '\.subtract\(After\)' = '-After';
    '\.subtract\(requests\)' = '-requests';
    '\.subtract\(per\)' = '-per';
    '\.subtract\(minute\)' = '-minute';
    '\.subtract\(upgrade\)' = '-upgrade';
    '\.subtract\(based\)' = '-based';
    '\.subtract\(tenant\)' = '-tenant';
    '\.subtract\(Money\)' = '-Money';
    '\.subtract\(encoded\)' = '-encoded';
    '\.subtract\(char\)' = '-char';
    '\.subtract\(currency\)' = '-currency';
    '\.add\(filtre\)' = '+filtre';
    '\.add\(productHome\)' = '+productHome'
}

foreach ($file in $files) {
    $content = Get-Content $file.FullName -Raw
    $original = $content

    foreach ($pattern in $patterns.Keys) {
        $content = $content -replace $pattern, $patterns[$pattern]
    }

    if ($content -ne $original) {
        Set-Content $file.FullName $content -Encoding UTF8
        Write-Host "Fixed -> $($file.FullName)" -ForegroundColor Green
    }
}
