import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vite.dev/config/
export default defineConfig({
  plugins: [vue()],
  base: '/', // <-- đúng tên repo của bạn
  build: {
    outDir: 'dist',
    assetsDir: 'assets'
  }
})
