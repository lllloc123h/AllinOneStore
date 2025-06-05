import { createRouter, createWebHistory } from 'vue-router'

// Import modular route arrays
import adminRoutes from './Admin/AdminRouter.js'
import userRoutes from './User/UserRouter.js'
import authService from '../services/header-injectable-JWT.js'
import MainLayout from '../Layouts/MainLayout.vue'
import UserLayout from '../Layouts/UserLayout.vue'

// Merge all routes
const routes = [
  {
    path: '/',
    component: UserLayout,
    children: [
      ...userRoutes,
    ]
  },
  {
    path: '/',
    component: MainLayout,
    children: [
      ...adminRoutes,
    ]
  }

]

const router = createRouter({
  history: createWebHistory(),
  routes
})
router.beforeEach((to, from, next) => {
  const isAuthenticated = authService.getToken() != null
  if (to.meta.requiresAuth && !isAuthenticated) {
    next({ name: 'login' })
  } else {
    next()
  }
})


export default router
