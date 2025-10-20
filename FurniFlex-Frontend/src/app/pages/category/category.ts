import { Component, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-category',
  standalone: true,
  templateUrl: './category.html',
  styleUrl: './category.scss'
})
export class CategoryPage {
  private route = inject(ActivatedRoute);
  get title() {
    return (this.route.snapshot.paramMap.get('category') ?? 'Category').replace('-', ' ');
  }
}
