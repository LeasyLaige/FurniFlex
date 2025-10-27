import { Component } from '@angular/core';
import { NgFor } from '@angular/common';

@Component({
  selector: 'app-support',
  standalone: true,
  imports: [NgFor],
  templateUrl: './support.html',
  styleUrl: './support.scss'
})
export class SupportPage {}
