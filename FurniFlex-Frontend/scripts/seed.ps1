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

# 1) Suppliers
$suppliers = @(
  @{ name = 'Walnut & Co'; contactInfo = 'hello@walnutco.example'; address = '120 Oak Ave, Portland' },
  @{ name = 'Stone Ridge'; contactInfo = 'contact@stoneridge.example'; address = '88 Marble Rd, Denver' }
)

$supplierResults = @()
foreach ($s in $suppliers) {
  $res = PostJson "$base/supplier" $s
  $supplierResults += $res
}
Write-Host "  Created $($supplierResults.Count) suppliers"

# 2) Products
$products = @(
  @{ name='Linen Sofa'; description='Soft linen 3-seater'; type='Sofas'; price=999.00; image='assets/products/living-room/sofas/sofa2.jpg' },
  @{ name='Walnut Coffee Table'; description='Solid walnut table'; type='Coffee Tables'; price=349.00; image='assets/products/living-room/coffee-tables/coffee-table2.webp' },
  @{ name='Oak Bed Frame'; description='Queen size'; type='Beds'; price=799.00; image='assets/products/bedroom/beds/bed3.jpg' },
  @{ name='Dining Chair Set (2)'; description='Curved back'; type='Dining Chairs'; price=259.00; image='assets/products/dining/dining-chairs/dining-chair4.jpg' },
  @{ name='Ergo Office Chair'; description='Mesh back support'; type='Office Chairs'; price=299.00; image='assets/products/office/office-chairs/office-chair4.jpg' },
  @{ name='Bookshelf Tall'; description='5-shelf unit'; type='Bookcases'; price=219.00; image='assets/products/office/bookcases/bookcase1.avif' }
)

$productResults = @()
foreach ($p in $products) {
  # Ensure price is numeric for backend BigDecimal
  $p.price = [decimal]$p.price
  $res = PostJson "$base/product" $p
  $productResults += $res
}
Write-Host "  Created $($productResults.Count) products"

# 3) Inventory (link to products)
$inventoryResults = @()
foreach ($p in $productResults) {
  $inv = @{ name = "$($p.name) Stock"; Quantity = 50; reservedQuantity = 0; product = @{ id = $p.id } }
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

# 5) Orders (link customer + product)
$orders = @(
  @{ customer = @{ id = $customerResults[0].id }; product = @{ id = $productResults[0].id }; quantity = 1; status = 'Ordered' },
  @{ customer = @{ id = $customerResults[0].id }; product = @{ id = $productResults[1].id }; quantity = 1; status = 'Ordered' },
  @{ customer = @{ id = $customerResults[1].id }; product = @{ id = $productResults[2].id }; quantity = 1; status = 'Ordered' }
)

$orderResults = @()
foreach ($o in $orders) {
  $res = PostJson "$base/order" $o
  $orderResults += $res
}
Write-Host "  Created $($orderResults.Count) orders"

# Summary
Write-Host "Seed complete:" -ForegroundColor Green
Write-Host "  Suppliers: $($supplierResults.Count)"
Write-Host "  Products:  $($productResults.Count)"
Write-Host "  Inventory: $($inventoryResults.Count)"
Write-Host "  Customers: $($customerResults.Count)"
Write-Host "  Orders:    $($orderResults.Count)"
