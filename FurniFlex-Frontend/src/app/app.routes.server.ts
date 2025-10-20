import { RenderMode, ServerRoute } from '@angular/ssr';

export const serverRoutes: ServerRoute[] = [
  // Render parameterized routes on the server at request time
  { path: 'category/:category', renderMode: RenderMode.Server },
  // Prerender everything else
  { path: '**', renderMode: RenderMode.Prerender }
];
