import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vite.dev/config/
export default defineConfig({
  plugins: [vue()],
  base: '/AllinOneStore/AOS_FE/AOS_FE/src/', // Tên repository của bạn
  build: {
    outDir: 'dist',
    assetsDir: 'assets',
    // // minify: 'terser', // Minify code cho production
    // sourcemap: false, // Tắt sourcemap cho production
    // rollupOptions: {
    //   output: {
    //     manualChunks: {
    //       vendor: ['vue'],
    //       antd: ['ant-design-vue'],
    //       utils: ['axios', 'bootstrap']
    //     }
    //   }
    // }
  },
  // define: {
  //   __VUE_PROD_DEVTOOLS__: false, // Tắt devtools trong production
  // }
})
