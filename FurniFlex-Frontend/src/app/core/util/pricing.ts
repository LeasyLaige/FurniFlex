import { FREE_SHIPPING_THRESHOLD, SHIPPING_FLAT, TAX_RATE } from '../config/pricing.config';

export interface Totals {
  subtotal: number;
  discount: number;
  shipping: number;
  tax: number;
  total: number;
}

export interface DiscountRule {
  type: 'percent' | 'amount';
  value: number; // 0.10 = 10% if percent; USD if amount
}

function round2(n: number): number { return Math.round(n * 100) / 100; }

export function calculateTotals(
  lines: Array<{ price: number; quantity: number }>,
  opts?: { taxRate?: number; shippingFlat?: number; freeShippingThreshold?: number; discount?: DiscountRule; shippingOverride?: number }
): Totals {
  const taxRate = opts?.taxRate ?? TAX_RATE;
  const flat = opts?.shippingFlat ?? SHIPPING_FLAT;
  const threshold = opts?.freeShippingThreshold ?? FREE_SHIPPING_THRESHOLD;

  const subtotal = round2(lines.reduce((s, l) => s + (l.price || 0) * (l.quantity || 0), 0));

  let discount = 0;
  if (opts?.discount) {
    discount = opts.discount.type === 'percent'
      ? subtotal * (opts.discount.value || 0)
      : (opts.discount.value || 0);
  }
  discount = round2(Math.max(0, Math.min(discount, subtotal)));

  const afterDiscount = subtotal - discount;
  const shipping = opts?.shippingOverride != null
    ? round2(Math.max(0, opts.shippingOverride))
    : round2(afterDiscount >= threshold || afterDiscount === 0 ? 0 : flat);
  const taxable = afterDiscount + shipping;
  const tax = round2(taxable * (taxRate || 0));
  const total = round2(afterDiscount + shipping + tax);

  return { subtotal, discount, shipping, tax, total };
}
