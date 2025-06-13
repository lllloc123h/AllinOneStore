
import HomeView from '../../components/User/HomeView.vue'
import ProductsView from '../../components/User/ProductsView.vue'
import ProductDetails from '../../components/User/ProductDetails.vue'
import UserInfo from '../../components/User/UserInfo.vue'
import Cart from '../../components/User/Cart.vue'
import OrderStatus from '../../components/User/OrderStatus.vue'

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
        path: 'productdetail',
        // route level code-splitting
        // this generates a separate chunk (About.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: ProductDetails
    },
    {
        path: 'login',
        name: 'login',
        component: () => import('../../components/Module/LoginView.vue')
    },
    {
        path: 'register',
        name: 'register',
        component: () => import('../../components/Module/RegisterView.vue')
    },  {
        path: 'otp',
        name: 'otp',
        component: () => import('../../components/Module/OTPView.vue')
    },
     {
        path: 'cart',
        name: 'cart',
        component: Cart
    }, {
        path: '/load',
        name: 'load',
        component: () => import('../../components/Module/ImageUpload.vue')
    },
    {
        path: '/UserInfo',
        name: 'UserInfo',
        component: UserInfo
    },

    {
        path: '/OrderStatus',
        name: 'OrderStatus',
        component: OrderStatus
    },

    {
        path: '/Customizer',
        name: 'Customizer',
        component: () => import ('../../components/User/CustomizerView.vue')
    },

    {
        path: '/Crop',
        name: 'CropCrop',
        component: () => import ('../../components/User/CropImage.vue')
    }

]
