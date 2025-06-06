
import HomeView from '../../components/User/HomeView.vue'
import ProductsView from '../../components/User/ProductsView.vue'
export default [
    {
        path: '',
        name: 'home',
        component: HomeView,
    },
    {
        path: 'products',
        // route level code-splitting
        // this generates a separate chunk (About.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: ProductsView
    },
    {
        path: 'login',
        name: 'login',
        component: () => import('../../components/Module/Login.vue')
    },
    {
        path: 'register',
        name: 'register',
        component: () => import('../../components/Module/Register.vue')
    },
    
]
