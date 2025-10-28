import { Customer } from './customer.model';
import { Product } from './product.model';

export interface Order {
  id?: number;
  customer: any | Customer; // backend returns CustomerData; use any to be lenient
  items: OrderItem[];
  status?: string;
  created?: string;
  lastUpdated?: string;
}

export interface CreateOrderPayload {
  customer: any | Customer;
  items: OrderItem[];
  status?: string;
}

export interface OrderItem {
  product: any | Product;
  quantity: number;
}
