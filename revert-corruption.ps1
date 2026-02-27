$files = Get-ChildItem -Recurse -Filter *.java -Path "C:\Users\wusta\Desktop\tekir2-master\tekir2-master\tekir-modern\tekir-api"

foreach ($file in $files) {
    $content = Get-Content $file.FullName -Raw
    $original = $content

    # Revert divide
    $content = $content -replace '\.divide\((\w+),\s*2,\s*java\.math\.RoundingMode\.HALF_UP\)', '/$1'
    
    # Revert subtract
    $content = $content -replace '\.subtract\((\w+)\)', '-$1'
    
    # Revert multiply
    $content = $content -replace '\.multiply\((\w+)\)', '*$1'
    
    # Revert add (BE CAREFUL - only if not typical List.add)
    # Most corrupted .add() in tekir-api are in strings/comments or property names
    # Let's target ones that look like property names or inside quotes
    $content = $content -replace '\.add\((\w+)\)', '+$1'

    if ($content -ne $original) {
        Set-Content $file.FullName $content -Encoding UTF8
        Write-Host "Reverted -> $($file.FullName)" -ForegroundColor Green
    }
}
