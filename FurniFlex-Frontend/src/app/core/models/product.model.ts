export interface Product {
  id?: number;
  name: string;
  description?: string;
  type?: string; // e.g., 'Sofa', 'Bed', 'Dining Table'
  price: number;
  created?: string;
  lastUpdated?: string;
  image?: string; // backend-provided image URL
}
