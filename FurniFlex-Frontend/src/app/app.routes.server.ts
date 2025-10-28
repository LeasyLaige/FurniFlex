import { RenderMode, ServerRoute } from '@angular/ssr';

export const serverRoutes: ServerRoute[] = [
  // Render parameterized routes on the server at request time
  { path: 'product/:id', renderMode: RenderMode.Server },
  { path: 'category/:category/:sub', renderMode: RenderMode.Server },
  { path: 'category/:category', renderMode: RenderMode.Server },
  { path: 'order/:id', renderMode: RenderMode.Server },
  // Prerender everything else
  { path: '**', renderMode: RenderMode.Prerender }
];
