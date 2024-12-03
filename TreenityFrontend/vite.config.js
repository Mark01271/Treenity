import { defineConfig } from 'vite';

export default defineConfig({
  server: {
    proxy: {
      '/api': { // path prefix for api requests
        target: 'http://localhost:8080', // backend server url
        changeOrigin: true,
        secure: false, // to proxy to an http target
      },
    },
  },
});
