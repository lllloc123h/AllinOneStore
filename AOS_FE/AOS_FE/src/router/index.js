import { createRouter, createWebHistory } from 'vue-router'

// Import modular route arrays
import adminRoutes from './Admin/AdminRouter.js'
import userRoutes from './User/UserRouter.js'

// Merge all routes
const routes = [
    ...adminRoutes,
    ...userRoutes

]

const router = createRouter({
    history: createWebHistory(),
    routes
})
router.beforeEach((to, from, next) => {
  const isAuthenticated = localStorage.getItem('token') !== null
    console.log(isAuthenticated);
  if (to.meta.requiresAuth && !isAuthenticated) {
    next('/login')
  } else {
    next()// tiếp tục như bình thường
  }
})


export default router
