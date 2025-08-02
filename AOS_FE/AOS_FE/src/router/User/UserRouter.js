
import HomeView from '../../components/User/HomeView.vue'
import ProductsView from '../../components/User/ProductsView.vue'
import ProductDetails from '../../components/User/ProductDetails.vue'
import UserInfo from '../../components/User/UserInfo.vue'
import Cart from '../../components/User/Cart.vue'
import OrderStatus from '../../components/User/OrderStatus.vue'
import ImageUpload from '../../components/Module/ImageUpload.vue'
import uploadProducts from '../../components/Module/upload-products.vue'
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
        path: '/OrderStatus/:id?',
        name: 'OrderStatus',
        component: OrderStatus
    },
    {
        path: '/Customizer/create/:id',
        name: 'CustomizerCreate',
        component: () => import('../../components/User/CustomizerView.vue'),
        props: route => ({ productItemId: route.params.id, action: 'create' })
    },
    {
        path: '/Customizer/update/:id',
        name: 'CustomizerUpdate',
        component: () => import('../../components/User/CustomizerView.vue'),
        props: route => ({ customId: route.params.id, action: 'update' })
    },
    {
        path: '/Checkoutpage',
        name: 'CheckoutPage',
        component: () => import('../../components/User/CheckoutPage.vue')
    },
    {
        path: '/shippingaddress',
        name: 'shippingaddress',
        component: () => import('../../components/Module/ShippingAddress.vue')
    },
    {
        path: '/momotopup',
        name: 'momotopup',
        component: () => import('../../components/User/MomoTopUp.vue')
    },
    {
        path: '/wallet',
        name: 'wallet',
        component: () => import('../../components/User/WalletInfor.vue')
    },
    {
        path: '/momo/return',
        name: 'momo-return',
        component: () => import('../../components/User/WalletInfor.vue')
    },
    {
        path: '/user-orders',
        name: 'user-orders',
        component: () => import('../../components/User/UserOrders.vue')
    },
    {
        path: '/order-list',
        name: 'order-list',
        component: ()=> import('../../components/User/OrderList.vue')
    }, 
    
]
