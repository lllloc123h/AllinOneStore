import { createApp } from 'vue'
import './assets/main.css'
import App from './App.vue'
import router from './router'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap/dist/js/bootstrap.bundle.min.js'
import authJWTPlugins from './pulgins/authJWTPlugins'
import '@fortawesome/fontawesome-free/css/all.min.css'
import Vue3Toastify, { toast } from 'vue3-toastify'
import 'vue3-toastify/dist/index.css'

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
app.mount('#app')
