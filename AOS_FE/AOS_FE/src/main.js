import { createApp } from 'vue'
import './assets/main.css'
import App from './App.vue'
import router from './router'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap/dist/js/bootstrap.bundle.min.js'
import authJWTPlugins from './pulgins/authJWTPlugins'
import '@fortawesome/fontawesome-free/css/all.min.css'
import 'bootstrap-icons/font/bootstrap-icons.css';
import Vue3Toastify, { toast } from 'vue3-toastify'
import 'vue3-toastify/dist/index.css'
import Antd from 'ant-design-vue';
import { notification } from 'ant-design-vue';
// import 'ant-design-vue/dist/antd.css';
import VueApexCharts from "vue3-apexcharts"

// Cấu hình notification global
notification.config({
  placement: 'top', // Vị trí: 'topLeft' | 'topRight' | 'bottomLeft' | 'bottomRight' | 'top' | 'bottom'
  top: '50px', // Khoảng cách từ trên xuống
  duration: 4.5, // Thời gian hiển thị (giây)
  maxCount: 3, // Số lượng notification tối đa hiển thị cùng lúc
  rtl: false,
});

const app = createApp(App)

app.use(router)
app.use(authJWTPlugins)
app.use(Vue3Toastify, {
  autoClose: 3000,
  position: 'top-center',
  rtl: false,
  limit: '3',
  style: {
    opacity: '1',
    userSelect: 'initial',
  },
  transition: toast.TRANSITIONS.SLIDE,
  progressStyle: {
    backgroundImage: 'linear-gradient(90deg, red, orange, yellow, green, blue, indigo, violet)',
  }
})
app.use(Antd);
app.use(VueApexCharts);
app.mount('#app')
