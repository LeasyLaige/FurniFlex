export interface Product {
  id?: number;
  name: string;
  description?: string;
  type?: string; // e.g., 'Sofa', 'Bed', 'Dining Table'
  price: number;
  created?: string;
  lastUpdated?: string;
  image?: string; // backend-provided image URL
  // Specs
  sku?: string;
  dimensions?: string; // 84” W x 35” D x 30” H
  material?: string;   // Solid oak, Linen blend
  color?: string;      // Walnut, Charcoal
  weight?: string;     // 65 lb
}
