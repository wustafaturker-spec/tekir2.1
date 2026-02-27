$baseDir = "C:\Users\wusta\Desktop\tekir2-master\tekir2-master\tekir-modern"
$files = Get-ChildItem -Recurse -Filter *.java -Path $baseDir

$utf8NoBom = New-Object System.Text.UTF8Encoding $false

foreach ($file in $files) {
    $bytes = [System.IO.File]::ReadAllBytes($file.FullName)
    if ($bytes.Length -ge 3 -and $bytes[0] -eq 0xEF -and $bytes[1] -eq 0xBB -and $bytes[2] -eq 0xBF) {
        Write-Host "Removing BOM from -> $($file.FullName)" -ForegroundColor Cyan
        $newBytes = New-Object byte[] ($bytes.Length - 3)
        [System.Buffer]::BlockCopy($bytes, 3, $newBytes, 0, $newBytes.Length)
        [System.IO.File]::WriteAllBytes($file.FullName, $newBytes)
    }
}
