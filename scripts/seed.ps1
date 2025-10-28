# Seed FurniFlex backend with sample data
# Usage: Run while Spring Boot backend is running on http://localhost:8090

$ErrorActionPreference = 'Stop'
$base = 'http://localhost:8090/api'

function PostJson($url, $body) {
  $json = $body | ConvertTo-Json -Depth 6
  Invoke-RestMethod -Method Post -Uri $url -ContentType 'application/json' -Body $json
}

Write-Host 'Seeding suppliers...'
$supplier1 = PostJson "$base/supplier" @{ name = 'Earth & Oak Co.'; contactInfo = 'earthoak@example.com'; address = '123 Forest Blvd' }
$supplier2 = PostJson "$base/supplier" @{ name = 'Terracotta Goods'; contactInfo = 'terra@example.com'; address = '45 Clay St' }

Write-Host 'Seeding products...'
$products = @(
  @{ name='Linen Sofa 3-Seater'; description='Comfy linen sofa'; type='Sofa'; price=899.0 },
  @{ name='Oak Lounge Chair'; description='Solid oak frame'; type='Armchair'; price=289.0 },
  @{ name='Walnut Side Table'; description='Walnut veneer'; type='Side Table'; price=149.0 },
  @{ name='Rattan Accent Lamp'; description='Warm rattan shade'; type='Lamp'; price=79.0 },
  @{ name='Modern Sectional'; description='Spacious L-shape'; type='Sectional'; price=1299.0 },
  @{ name='Bed Frame Queen'; description='Walnut solid wood'; type='Bed'; price=649.0 },
  @{ name='Dining Table 6p'; description='Oak extendable'; type='Dining Table'; price=999.0 },
  @{ name='Office Chair'; description='Ergonomic mesh'; type='Office Chair'; price=199.0 }
) | ForEach-Object { PostJson "$base/product" $_ }

Write-Host 'Seeding inventory...'
$inv1 = PostJson "$base/inventory" @{ name='Main Warehouse'; quantity=100; reservedQuantity=0; product = @{ id = $products[0].id } }
$inv2 = PostJson "$base/inventory" @{ name='Secondary'; quantity=50; reservedQuantity=0; product = @{ id = $products[1].id } }

Write-Host 'Seeding customers...'
$customer1 = PostJson "$base/customer" @{ name='Alex Green'; email='alex@example.com'; phone='555-1000' }
$customer2 = PostJson "$base/customer" @{ name='Jamie Stone'; email='jamie@example.com' }

Write-Host 'Seeding orders (multi-item with address)...'
$order1 = PostJson "$base/order" @{ customer = @{ id = $customer1.id }; items = @(
    @{ product = @{ id = $products[0].id }; quantity = 1 },
    @{ product = @{ id = $products[1].id }; quantity = 2 }
  ); status = 'Ordered';
  recipientName = $customer1.name; addressLine1 = '123 Forest Blvd'; city = 'Austin'; state = 'TX'; postalCode = '73301'; country = 'United States'; phone = '555-1000'; shippingMethod = 'standard'; shippingCost = 0 }
$order2 = PostJson "$base/order" @{ customer = @{ id = $customer2.id }; items = @(
    @{ product = @{ id = $products[2].id }; quantity = 1 },
    @{ product = @{ id = $products[3].id }; quantity = 1 },
    @{ product = @{ id = $products[4].id }; quantity = 3 }
  ); status = 'Ordered';
  recipientName = $customer2.name; addressLine1 = '45 Clay St'; city = 'Phoenix'; state = 'AZ'; postalCode = '85001'; country = 'United States'; shippingMethod = 'express'; shippingCost = 19.99 }

Write-Host 'Done.' -ForegroundColor Green
