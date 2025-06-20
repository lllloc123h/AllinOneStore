import { createRouter, createWebHistory } from 'vue-router'

// Import modular route arrays
import adminRoutes from './Admin/AdminRouter.js'
import userRoutes from './User/UserRouter.js'
import api from '../Configs/api.js'
import adminLayout from '../Layouts/AdminLayout.vue'
import UserLayout from '../Layouts/UserLayout.vue'
import Oauth2 from '../components/Module/Oauth2Success.vue'
import Unauthorized from '../components/Module/403View.vue'
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
    path: '/Admin',
    component: adminLayout,
    children: [
      ...adminRoutes,
    ]
  },
  {
    path: '/oauth2/redirect',
    name: 'oauth2',
    component: Oauth2,

  },
  {
    path: '/403',
    name: '403',
    component: Unauthorized,
  }


]

const router = createRouter({
  history: createWebHistory(),
  routes
})
// const router = createRouter({
//   history: createWebHistory('/AllinOneStore/'),
//   routes
// })

// router.beforeEach((to, from, next) => {
//   const isAuthenticated = authService.getToken() != null
//   if (to.meta.requiresAuth && !isAuthenticated) {
//     next({ name: 'login' })
//   } else {
//     next()
//   }
// })
// truoc khi router kiem tra vai tro va quyen
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('jwtToken');
  // dung khi meta o ngoai parent
  // const requiresAuth = to.matched.some(record => record.meta.requiresAuth)
  // const requiresRoles = to.matched
  // .filter(record => record.meta.requiresRoles)
  // .flatMap(record => record.meta.requiresRoles)

  const requiresAuth = to.meta.requiresAuth;
  const requiresRoles = to.meta.requiresRoles || [];


  if (requiresAuth) {
    localStorage.setItem('redirectTo', to.fullPath)
    if (token) {
      console.log('out of date ', api.parseJwt(token).exp.toLocaleString());
      // kiem tra co dc phep vao hay kh
      const userRoles = api.parseJwt(token)?.roles || [];
      const hasRole = requiresRoles.some(role => userRoles.includes(role))
      if (hasRole) {
        localStorage.setItem('email', parseJwt(token)?.sub);
        console.log('request with ', userRoles);
        return next()
      } else {
        console.log('ko co role phu hop -- index.js');
        return next('/403')
      }

    } else {
      return next('/login')
    }
  }
  next()
})

export default router
