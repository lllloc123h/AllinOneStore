import { createRouter, createWebHistory } from 'vue-router'

// Import modular route arrays
import adminRoutes from './Admin/AdminRouter.js'
import userRoutes from './User/UserRouter.js'

// Merge all routes
const routes = [
    ...adminRoutes,
    ...userRoutes,
    {
        path: '/',
        redirect: '/Admin/Accounts'
    }

]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router
