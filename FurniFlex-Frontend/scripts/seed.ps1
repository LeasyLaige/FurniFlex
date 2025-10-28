# Seed FurniFlex backend with sample data
# Usage: Run after starting Spring Boot backend on port 8090
#   powershell -ExecutionPolicy Bypass -File .\scripts\seed.ps1

$ErrorActionPreference = 'Stop'

$base = 'http://localhost:8090/api'

function PostJson($url, $body) {
  $json = $body | ConvertTo-Json -Depth 6
  Invoke-RestMethod -Method Post -Uri $url -Body $json -ContentType 'application/json'
}

function GetJson($url) {
  Invoke-RestMethod -Method Get -Uri $url -ContentType 'application/json'
}

Write-Host "Seeding FurniFlex backend at $base ..." -ForegroundColor Cyan

# 1) Suppliers (expanded realistic list)
$suppliers = @(
  @{ name = 'Herman Miller'; contactInfo = 'contact@hermanmiller.example'; address = '855 E Main Ave, Zeeland, MI' },
  @{ name = 'IKEA'; contactInfo = 'support@ikea.example'; address = '4200 Ikea Way, Burbank, CA' },
  @{ name = 'West Elm'; contactInfo = 'care@westelm.example'; address = '325 Henry St, Brooklyn, NY' },
  @{ name = 'Article'; contactInfo = 'hello@article.example'; address = '1010 Hamilton St, Vancouver, BC' },
  @{ name = 'Crate & Barrel'; contactInfo = 'help@crateandbarrel.example'; address = '1250 Techny Rd, Northbrook, IL' },
  @{ name = 'Pottery Barn'; contactInfo = 'support@potterybarn.example'; address = '3250 Van Ness Ave, San Francisco, CA' },
  @{ name = 'CB2'; contactInfo = 'hello@cb2.example'; address = '1315 N State Pkwy, Chicago, IL' },
  @{ name = 'Burrow'; contactInfo = 'help@burrow.example'; address = '47 W 28th St, New York, NY' },
  @{ name = 'Joybird'; contactInfo = 'contact@joybird.example'; address = '7324 E Rosewood St, Tucson, AZ' },
  @{ name = 'La-Z-Boy'; contactInfo = 'support@lazboy.example'; address = '1284 N Telegraph Rd, Monroe, MI' },
  @{ name = 'Wayfair'; contactInfo = 'partner@wayfair.example'; address = '4 Copley Pl, Boston, MA' },
  @{ name = 'Design Within Reach'; contactInfo = 'care@dwr.example'; address = '711 Canal St, Stamford, CT' }
)

$supplierResults = @()
foreach ($s in $suppliers) {
  $res = PostJson "$base/supplier" $s
  $supplierResults += $res
}
Write-Host "  Created $($supplierResults.Count) suppliers"

# 2) Products (auto-discover from assets)
$assetsRoot = Join-Path $PSScriptRoot '..\src\assets\products'
Write-Host "  Scanning assets at $assetsRoot"
$imgFiles = Get-ChildItem -Path $assetsRoot -Recurse -Include *.jpg,*.jpeg,*.png,*.webp,*.avif

function Convert-TitleCase($s) { return -join (($s -split '[-_]') | ForEach-Object { ($_ -replace '\.\w+$','')[0].ToString().ToUpper() + $_.Substring(1) }) -replace '([A-Z])', ' $1' -replace '^ ', '' }

function Get-FriendlyType($path) {
  $folder = Split-Path $path -Parent | Split-Path -Leaf
  switch ($folder.ToLower()) {
    'sofas' { 'Sofas' }
    'sectionals' { 'Sectionals' }
    'armchairs' { 'Armchairs' }
    'coffee-tables' { 'Coffee Tables' }
    'beds' { 'Beds' }
    'mattresses' { 'Mattresses' }
    'wardrobes' { 'Wardrobes' }
    'nightstands' { 'Nightstands' }
    'dining-tables' { 'Dining Tables' }
    'dining-chairs' { 'Dining Chairs' }
    'bar-stools' { 'Bar Stools' }
    'sideboards' { 'Sideboards' }
    'desks' { 'Desks' }
    'office-chairs' { 'Office Chairs' }
    'bookcases' { 'Bookcases' }
    'storage' { 'Storage' }
    default { Convert-TitleCase $folder }
  }
}

$productResults = @()
foreach ($f in $imgFiles) {
  $relPath = $f.FullName.Replace((Resolve-Path .).Path, '')
  $relPath = $relPath -replace "^\\", '' -replace "^.*?src\\", ''
  $relPath = $relPath -replace "^.*?assets\\", '/assets\'  # ensure starts with root-relative /assets

  $type = Get-FriendlyType $f.FullName
  $nameBase = [System.IO.Path]::GetFileNameWithoutExtension($f.Name) -replace '[-_]', ' '
  $name = Convert-TitleCase $nameBase
  if (-not $name) { $name = "$type Item" }
  $price = [decimal](Get-Random -Minimum 79 -Maximum 1299)

  $p = @{ name = $name; description = "$type crafted for everyday use"; type = $type; price = $price; image = $relPath.Replace('\\','/') }
  try {
    $res = PostJson "$base/product" $p
    $productResults += $res
  } catch { Write-Warning "Failed to create product from $($f.Name): $_" }
}
Write-Host "  Created $($productResults.Count) products from assets"

# 3) Inventory (link to products)
$inventoryResults = @()
foreach ($p in $productResults) {
  # Set higher stock so items are available
  $qty = Get-Random -Minimum 25 -Maximum 150
  $reserved = Get-Random -Minimum 0 -Maximum 3
  $inv = @{ name = "$($p.name) Stock"; quantity = $qty; reservedQuantity = $reserved; product = @{ id = $p.id } }
  $res = PostJson "$base/inventory" $inv
  $inventoryResults += $res
}
Write-Host "  Created $($inventoryResults.Count) inventory items"

# 4) Customers
$customers = @(
  @{ name='Ava Stone'; email='ava@example.com'; phone='555-0111'; password='Password123!' },
  @{ name='Mika Reed'; email='mika@example.com'; phone='555-0222'; password='Password123!' }
)

$customerResults = @()
foreach ($c in $customers) {
  $res = PostJson "$base/customer" $c
  $customerResults += $res
}
Write-Host "  Created $($customerResults.Count) customers"

# 5) Orders (multi-item orders)
$orderResults = @()
if ($productResults.Count -ge 6 -and $customerResults.Count -ge 2) {
  $multiOrders = @(
    @{ customer = @{ id = $customerResults[0].id }; items = @(
        @{ product = @{ id = $productResults[0].id }; quantity = 1 },
        @{ product = @{ id = $productResults[1].id }; quantity = 2 }
      ); status = 'Ordered'; recipientName = $customerResults[0].name; addressLine1 = '123 Forest Blvd'; city = 'Austin'; state = 'TX'; postalCode = '73301'; country = 'United States'; phone = '555-0111'; shippingMethod = 'standard'; shippingCost = 0 },
    @{ customer = @{ id = $customerResults[1].id }; items = @(
        @{ product = @{ id = $productResults[2].id }; quantity = 1 },
        @{ product = @{ id = $productResults[3].id }; quantity = 1 },
        @{ product = @{ id = $productResults[4].id }; quantity = 3 }
      ); status = 'Ordered'; recipientName = $customerResults[1].name; addressLine1 = '45 Clay St'; city = 'Phoenix'; state = 'AZ'; postalCode = '85001'; country = 'United States'; shippingMethod = 'express'; shippingCost = 19.99 }
  )
  foreach ($o in $multiOrders) {
    try { $res = PostJson "$base/order" $o; $orderResults += $res } catch { Write-Warning $_ }
  }
}
Write-Host "  Created $($orderResults.Count) orders"

# Summary
Write-Host "Seed complete:" -ForegroundColor Green
Write-Host "  Suppliers: $($supplierResults.Count)"
Write-Host "  Products:  $($productResults.Count)"
Write-Host "  Inventory: $($inventoryResults.Count)"
Write-Host "  Customers: $($customerResults.Count)"
Write-Host "  Orders:    $($orderResults.Count)"
