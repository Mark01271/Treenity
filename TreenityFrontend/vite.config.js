import { defineConfig } from 'vite';
import { resolve } from 'path';

export default defineConfig({
  root: './', // setting root to the project directory
  build: {
    outDir: 'dist', // specify the output directory
    rollupOptions: {
      input: {
        main: resolve(__dirname, 'index.html'),
        requestInfo: resolve(__dirname, 'public/requestInfo.html'),
        // add other HTML files here
      },
    },
  },
  server: {
    port: 3000,
    open: '/', // open index.html automatically
    historyApiFallback: true, // serves index.html for unknown paths
    proxy: {
      '/api': { // path prefix for API requests
        target: 'http://localhost:1700', // backend server URL
        changeOrigin: true,
        secure: false, // to use HTTP instead of HTTPS
        rewrite: (path) => path.replace(/^\/api/, ''), // Remove /api prefix
      },

    },
  },
  css: {
    preprocessorOptions: {
      scss: {
      }
    }
  }
});

// 1700 porta backend