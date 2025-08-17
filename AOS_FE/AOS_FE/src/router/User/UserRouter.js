
import HomeView from '../../components/User/HomeView.vue'
import ProductsView from '../../components/User/ProductsView.vue'
import ProductDetails from '../../components/User/ProductDetails.vue'
import UserInfo from '../../components/User/UserInfo.vue'
import Cart from '../../components/User/Cart.vue'
import OrderStatus from '../../components/User/OrderStatus.vue'
import ImageUpload from '../../components/Module/ImageUpload.vue'
import uploadProducts from '../../components/Module/upload-images.vue'
export default [
    {
        path: '',
        name: 'home',
        component: HomeView,
    },
    // {
    //     path: 'uploads',
    //     name: 'uploads',
    //     component: Upload,
    // },
    {
        path: 'uploads',
        name: 'uploads',
        component: ImageUpload,
    },
    {
        path: 'upload1',
        name: 'upload1',
        component: uploadProducts,
    },
     {
        path: 'upload2',
        name: 'upload2',
        component: () => import('../../components/Module/upload-single-img-video.vue'),
    },
    {
        path: 'products',
        // route level code-splitting
        // this generates a separate chunk (About.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: ProductsView
    },
    {
        path: 'product/:id',
        // route level code-splitting
        // this generates a separate chunk (About.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        name: 'ProductDetails',
        component: ProductDetails
    },
        {
        path: 'news',
        name: 'news',
        component: () => import('../../components/User/NewsView.vue')
    },
     {
        path: 'news/:id',
        name: 'newsDetail',
        component: () => import('../../components/User/NewsDetailView.vue')
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
    }, {
        path: 'otp',
        name: 'otp',
        component: () => import('../../components/Module/OTPView.vue')
    },
    {
        path: 'cart',
        name: 'cart',
        component: Cart,
        meta: { requiresAuth: true, requiresRoles: ['USER', 'ADMIN'] }
    },
     {
        path: '/load',
        name: 'load',
        component: () => import('../../components/Module/ImageUpload.vue')
    },
    {
        path: '/UserInfo',
        name: 'UserInfo',
        component: UserInfo,
        meta: { requiresAuth: true, requiresRoles: ['USER', 'ADMIN'] }
    },
    {
        path: '/custom',
        name: 'custom',
        component: () => import('../../components/User/CustomView.vue'),
        meta: { requiresAuth: true, requiresRoles: ['USER', 'ADMIN'] }
    },
    {
        path: '/OrderStatus/:id?',
        name: 'OrderStatus',
        component: OrderStatus,
        meta : { requiresAuth: true ,requiresRoles: ['USER', 'ADMIN'] }
    },
    {
        path: '/Customizer/create/:id',
        name: 'CustomizerCreate',
        component: () => import('../../components/User/CustomizerView.vue'),
        props: route => ({ productItemId: route.params.id, action: 'create' }),
        meta : { requiresAuth: true ,requiresRoles: ['USER', 'ADMIN'] }
    },
    {
        path: '/Customizer/update/:id',
        name: 'CustomizerUpdate',
        component: () => import('../../components/User/CustomizerView.vue'),
        props: route => ({ customId: route.params.id, action: 'update' }),
        meta : { requiresAuth: true ,requiresRoles: ['USER', 'ADMIN'] }
    },
    {
        path: '/Checkoutpage',
        name: 'CheckoutPage',
        component: () => import('../../components/User/CheckoutPage.vue'),
        meta : { requiresAuth: true ,requiresRoles: ['USER', 'ADMIN'] }
    },
    {
        path: '/shippingaddress',
        name: 'shippingaddress',
        component: () => import('../../components/Module/ShippingAddress.vue'),
        meta : { requiresAuth: true ,requiresRoles: ['USER', 'ADMIN'] }
    },
    {
        path: '/momotopup',
        name: 'momotopup',
        component: () => import('../../components/User/MomoTopUp.vue'),
        meta : { requiresAuth: true ,requiresRoles: ['USER', 'ADMIN'] }
    },
    {
        path: '/wallet',
        name: 'wallet',
        component: () => import('../../components/User/WalletInfor.vue'),
        meta : { requiresAuth: true ,requiresRoles: ['USER', 'ADMIN'] }
    },
    {
        path: '/momo/return',
        name: 'momo-return',
        component: () => import('../../components/User/WalletInfor.vue'),
        meta : { requiresAuth: true ,requiresRoles: ['USER', 'ADMIN'] }
    },
    {
        path: '/user-orders',
        name: 'user-orders',
        component: () => import('../../components/User/UserOrders.vue'),
        meta : { requiresAuth: true ,requiresRoles: ['USER', 'ADMIN'] }
    },
    {
        path: '/order-list',
        name: 'order-list',
        component: ()=> import('../../components/User/OrderList.vue'),
        meta : { requiresAuth: true ,requiresRoles: ['USER', 'ADMIN'] }
    }, 
    
]
