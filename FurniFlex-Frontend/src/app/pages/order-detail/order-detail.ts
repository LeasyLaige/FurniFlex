import { Component, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgIf } from '@angular/common';
import { OrderService } from '../../core/services/order.service';

@Component({
  selector: 'app-order-detail',
  standalone: true,
  imports: [NgIf],
  templateUrl: './order-detail.html',
  styleUrl: './order-detail.scss'
})
export class OrderDetailPage {
  private route = inject(ActivatedRoute);
  private orders = inject(OrderService);

  order: any | null = null;

  constructor() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (id) {
      this.orders.getById(id).subscribe(o => this.order = o);
    }
  }
}
