import { Component } from '@angular/core';
import { NgFor } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-careers',
  standalone: true,
  imports: [NgFor, RouterLink],
  templateUrl: './careers.html',
  styleUrl: './careers.scss'
})
export class CareersPage {}
