import { createRouter, createWebHistory } from 'vue-router'

// Import modular route arrays
import adminRoutes from './Admin/AdminRouter.js'
import userRoutes from './User/UserRouter.js'
import authService from '../services/header-injectable-JWT.js'

// Merge all routes
const routes = [
    ...adminRoutes,
    ...userRoutes,
    {
        path: '/login',
        name: 'login',
        component: () => import('../components/Module/Login.vue')
    }

]

const router = createRouter({
    history: createWebHistory(),
    routes
})
router.beforeEach((to, from, next) => {
  const isAuthenticated = authService.getToken()!=null
  if (to.meta.requiresAuth && !isAuthenticated) {
    next({name:'login'})
  } else {
    next()// tiếp tục như bình thường
  }
})


export default router
