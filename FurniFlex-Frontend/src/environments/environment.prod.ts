export const environment = {
  production: true,
  // Use absolute base during prerender to avoid SSR dev-server handling '/api' itself.
  // Adjust to your production API URL as needed.
  API_BASE: 'http://localhost:8090/api'
};
