$baseDir = "C:\Users\wusta\Desktop\tekir2-master\tekir2-master\tekir-modern"
$files = Get-ChildItem -Recurse -Filter *.java -Path $baseDir

foreach ($file in $files) {
    $content = Get-Content $file.FullName -Raw
    $original = $content

    # Fix corrupted strings (anything inside quotes)
    # This specifically looks for .subtract(), .divide(), .multiply(), .add() patterns inside quotes
    if ($content -match '"[^"]*(\.subtract\(|\.divide\(|\.multiply\(|\.add\()[^"]*"') {
        Write-Host "Fixing corrupted strings in -> $($file.FullName)" -ForegroundColor Cyan
        $content = [regex]::Replace($content, '"([^"]*)\.subtract\((\w+)\)([^"]*)"', '"$1-$2$3"')
        $content = [regex]::Replace($content, '"([^"]*)\.divide\((\w+),\s*2,\s*java\.math\.RoundingMode\.HALF_UP\)([^"]*)"', '"$1/$2$3"')
        $content = [regex]::Replace($content, '"([^"]*)\.multiply\((\w+)\)([^"]*)"', '"$1*$2$3"')
        $content = [regex]::Replace($content, '"([^"]*)\.add\((\w+)\)([^"]*)"', '"$1+$2$3"')
    }

    # Fix corrupted comments
    if ($content -match '//.*(\.subtract\(|\.divide\(|\.multiply\(|\.add\()') {
         Write-Host "Fixing corrupted comments in -> $($file.FullName)" -ForegroundColor Yellow
         $content = [regex]::Replace($content, '//(.*)\.subtract\((\w+)\)(.*)', '//$1-$2$3')
         $content = [regex]::Replace($content, '//(.*)\.divide\((\w+),\s*2,\s*java\.math\.RoundingMode\.HALF_UP\)(.*)', '//$1/$2$3')
         $content = [regex]::Replace($content, '//(.*)\.multiply\((\w+)\)(.*)', '//$1*$2$3')
         $content = [regex]::Replace($content, '//(.*)\.add\((\w+)\)(.*)', '//$1+$2$3')
    }

    if ($content -ne $original) {
        # Save without BOM
        $utf8NoBom = New-Object System.Text.UTF8Encoding $false
        [System.IO.File]::WriteAllText($file.FullName, $content, $utf8NoBom)
        Write-Host "Cleaned -> $($file.FullName)" -ForegroundColor Green
    }
}
