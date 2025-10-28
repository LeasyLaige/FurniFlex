export interface Review {
  id?: number;
  productId: number;
  author: string;
  rating: number; // 1-5
  comment: string;
  created?: string;
}
