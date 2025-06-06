import { createApp } from 'vue'
import './assets/main.css'
import App from './App.vue'
import router from './router'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap/dist/js/bootstrap.bundle.min.js'
import AuthPlugin from './pulgins/authJWTPlugins';
import '@fortawesome/fontawesome-free/css/all.min.css';

createApp(App)
    .use(router)
    .use(AuthPlugin)
    .mount('#app')
