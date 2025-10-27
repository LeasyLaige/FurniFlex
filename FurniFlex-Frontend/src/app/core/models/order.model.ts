import { Customer } from './customer.model';
import { Product } from './product.model';

export interface Order {
  id?: number;
  customer: any | Customer; // backend returns CustomerData; use any to be lenient
  product: any | Product;   // backend returns ProductData
  quantity: number;
  status?: string;
  created?: string;
  lastUpdated?: string;
}

export interface CreateOrderPayload {
  customer: any | Customer;
  product: any | Product;
  quantity: number;
  status?: string;
}
