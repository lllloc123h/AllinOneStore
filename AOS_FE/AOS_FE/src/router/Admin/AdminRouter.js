
const listDashBoard = [
    "Accounts",
    "Authorities",
    "BaseProducts",
    "Cancels",
    "CartItems",
    "Catalogs",
    "Categories",
    "CostHistories",
    "Coupons",
    "Custom",
    "News",
    "OrderItems",
    "Orders",
    "PaymentMethods",
    "PriceHistories",
    "ProductImages",
    "ProductItems",
    "PromotionProduct",
    "Promotions",
    "PurchaseOrderItems",
    "PurchaseOrders",
    "Returns",
    "Reviews",
    "Roles",
    "ShippingMethods",
    "UserAddresses",
    "UserLogs",
    "VariantValues",
    "Variants",
]
export default [
  	{
        path: '/Accounts',
        component: import('../../components/Admin/Accounts/index.vue'),
        props: route => ({ TableName: 'Accounts'}),
         meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/Accounts/create',
        component:  () => import('../../components/Admin/Accounts/Form.vue'),
        props: route => ({ TableName: 'Accounts', action: 'create'}),
         meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/Accounts/view/:id',
        component:  () => import('../../components/Admin/Accounts/Form.vue'),
        props: route => ({ TableName: 'Accounts', action: 'view', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/Accounts/update/:id',
        component:  () => import('../../components/Admin/Accounts/Form.vue'),
        props: route => ({ TableName: 'Accounts', action: 'update', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
  	{
        path: '/Authorities',
        component: import('../../components/Admin/Authorities/index.vue'),
        props: route => ({ TableName: 'Authorities'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/Authorities/create',
        component:  () => import('../../components/Admin/Authorities/Form.vue'),
        props: route => ({ TableName: 'Authorities', action: 'create'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/Authorities/view/:id',
        component:  () => import('../../components/Admin/Authorities/Form.vue'),
        props: route => ({ TableName: 'Authorities', action: 'view', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/Authorities/update/:id',
        component:  () => import('../../components/Admin/Authorities/Form.vue'),
        props: route => ({ TableName: 'Authorities', action: 'update', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
  	{
        path: '/BaseProducts',
        component: import('../../components/Admin/BaseProducts/index.vue'),
        props: route => ({ TableName: 'BaseProducts'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/BaseProducts/create',
        component:  () => import('../../components/Admin/BaseProducts/Form.vue'),
        props: route => ({ TableName: 'BaseProducts', action: 'create'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/BaseProducts/view/:id',
        component:  () => import('../../components/Admin/BaseProducts/Form.vue'),
        props: route => ({ TableName: 'BaseProducts', action: 'view', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/BaseProducts/update/:id',
        component:  () => import('../../components/Admin/BaseProducts/Form.vue'),
        props: route => ({ TableName: 'BaseProducts', action: 'update', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
  	{
        path: '/Cancels',
        component: import('../../components/Admin/Cancels/index.vue'),
        props: route => ({ TableName: 'Cancels'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/Cancels/create',
        component:  () => import('../../components/Admin/Cancels/Form.vue'),
        props: route => ({ TableName: 'Cancels', action: 'create'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/Cancels/view/:id',
        component:  () => import('../../components/Admin/Cancels/Form.vue'),
        props: route => ({ TableName: 'Cancels', action: 'view', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/Cancels/update/:id',
        component:  () => import('../../components/Admin/Cancels/Form.vue'),
        props: route => ({ TableName: 'Cancels', action: 'update', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
  	{
        path: '/CartItems',
        component: import('../../components/Admin/CartItems/index.vue'),
        props: route => ({ TableName: 'CartItems'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/CartItems/create',
        component:  () => import('../../components/Admin/CartItems/Form.vue'),
        props: route => ({ TableName: 'CartItems', action: 'create'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/CartItems/view/:id',
        component:  () => import('../../components/Admin/CartItems/Form.vue'),
        props: route => ({ TableName: 'CartItems', action: 'view', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/CartItems/update/:id',
        component:  () => import('../../components/Admin/CartItems/Form.vue'),
        props: route => ({ TableName: 'CartItems', action: 'update', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
  	{
        path: '/Catalogs',
        component: import('../../components/Admin/Catalogs/index.vue'),
        props: route => ({ TableName: 'Catalogs'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/Catalogs/create',
        component:  () => import('../../components/Admin/Catalogs/Form.vue'),
        props: route => ({ TableName: 'Catalogs', action: 'create'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/Catalogs/view/:id',
        component:  () => import('../../components/Admin/Catalogs/Form.vue'),
        props: route => ({ TableName: 'Catalogs', action: 'view', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/Catalogs/update/:id',
        component:  () => import('../../components/Admin/Catalogs/Form.vue'),
        props: route => ({ TableName: 'Catalogs', action: 'update', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
  	{
        path: '/Categories',
        component: import('../../components/Admin/Categories/index.vue'),
        props: route => ({ TableName: 'Categories'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/Categories/create',
        component:  () => import('../../components/Admin/Categories/Form.vue'),
        props: route => ({ TableName: 'Categories', action: 'create'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/Categories/view/:id',
        component:  () => import('../../components/Admin/Categories/Form.vue'),
        props: route => ({ TableName: 'Categories', action: 'view', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/Categories/update/:id',
        component:  () => import('../../components/Admin/Categories/Form.vue'),
        props: route => ({ TableName: 'Categories', action: 'update', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
  	{
        path: '/CostHistories',
        component: import('../../components/Admin/CostHistories/index.vue'),
        props: route => ({ TableName: 'CostHistories'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/CostHistories/create',
        component:  () => import('../../components/Admin/CostHistories/Form.vue'),
        props: route => ({ TableName: 'CostHistories', action: 'create'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/CostHistories/view/:id',
        component:  () => import('../../components/Admin/CostHistories/Form.vue'),
        props: route => ({ TableName: 'CostHistories', action: 'view', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/CostHistories/update/:id',
        component:  () => import('../../components/Admin/CostHistories/Form.vue'),
        props: route => ({ TableName: 'CostHistories', action: 'update', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
  	{
        path: '/Coupons',
        component: import('../../components/Admin/Coupons/index.vue'),
        props: route => ({ TableName: 'Coupons'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/Coupons/create',
        component:  () => import('../../components/Admin/Coupons/Form.vue'),
        props: route => ({ TableName: 'Coupons', action: 'create'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/Coupons/view/:id',
        component:  () => import('../../components/Admin/Coupons/Form.vue'),
        props: route => ({ TableName: 'Coupons', action: 'view', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/Coupons/update/:id',
        component:  () => import('../../components/Admin/Coupons/Form.vue'),
        props: route => ({ TableName: 'Coupons', action: 'update', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
  	{
        path: '/Custom',
        component: import('../../components/Admin/Custom/index.vue'),
        props: route => ({ TableName: 'Custom'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/Custom/create',
        component:  () => import('../../components/Admin/Custom/Form.vue'),
        props: route => ({ TableName: 'Custom', action: 'create'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/Custom/view/:id',
        component:  () => import('../../components/Admin/Custom/Form.vue'),
        props: route => ({ TableName: 'Custom', action: 'view', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/Custom/update/:id',
        component:  () => import('../../components/Admin/Custom/Form.vue'),
        props: route => ({ TableName: 'Custom', action: 'update', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
  	{
        path: '/News',
        component: import('../../components/Admin/News/index.vue'),
        props: route => ({ TableName: 'News'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/News/create',
        component:  () => import('../../components/Admin/News/Form.vue'),
        props: route => ({ TableName: 'News', action: 'create'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/News/view/:id',
        component:  () => import('../../components/Admin/News/Form.vue'),
        props: route => ({ TableName: 'News', action: 'view', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/News/update/:id',
        component:  () => import('../../components/Admin/News/Form.vue'),
        props: route => ({ TableName: 'News', action: 'update', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
  	{
        path: '/OrderItems',
        component: import('../../components/Admin/OrderItems/index.vue'),
        props: route => ({ TableName: 'OrderItems'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/OrderItems/create',
        component:  () => import('../../components/Admin/OrderItems/Form.vue'),
        props: route => ({ TableName: 'OrderItems', action: 'create'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/OrderItems/view/:id',
        component:  () => import('../../components/Admin/OrderItems/Form.vue'),
        props: route => ({ TableName: 'OrderItems', action: 'view', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/OrderItems/update/:id',
        component:  () => import('../../components/Admin/OrderItems/Form.vue'),
        props: route => ({ TableName: 'OrderItems', action: 'update', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
  	{
        path: '/Orders',
        component: import('../../components/Admin/Orders/index.vue'),
        props: route => ({ TableName: 'Orders'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/Orders/create',
        component:  () => import('../../components/Admin/Orders/Form.vue'),
        props: route => ({ TableName: 'Orders', action: 'create'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/Orders/view/:id',
        component:  () => import('../../components/Admin/Orders/Form.vue'),
        props: route => ({ TableName: 'Orders', action: 'view', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/Orders/update/:id',
        component:  () => import('../../components/Admin/Orders/Form.vue'),
        props: route => ({ TableName: 'Orders', action: 'update', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
  	{
        path: '/PaymentMethods',
        component: import('../../components/Admin/PaymentMethods/index.vue'),
        props: route => ({ TableName: 'PaymentMethods'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/PaymentMethods/create',
        component:  () => import('../../components/Admin/PaymentMethods/Form.vue'),
        props: route => ({ TableName: 'PaymentMethods', action: 'create'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/PaymentMethods/view/:id',
        component:  () => import('../../components/Admin/PaymentMethods/Form.vue'),
        props: route => ({ TableName: 'PaymentMethods', action: 'view', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/PaymentMethods/update/:id',
        component:  () => import('../../components/Admin/PaymentMethods/Form.vue'),
        props: route => ({ TableName: 'PaymentMethods', action: 'update', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
  	{
        path: '/PriceHistories',
        component: import('../../components/Admin/PriceHistories/index.vue'),
        props: route => ({ TableName: 'PriceHistories'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/PriceHistories/create',
        component:  () => import('../../components/Admin/PriceHistories/Form.vue'),
        props: route => ({ TableName: 'PriceHistories', action: 'create'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/PriceHistories/view/:id',
        component:  () => import('../../components/Admin/PriceHistories/Form.vue'),
        props: route => ({ TableName: 'PriceHistories', action: 'view', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/PriceHistories/update/:id',
        component:  () => import('../../components/Admin/PriceHistories/Form.vue'),
        props: route => ({ TableName: 'PriceHistories', action: 'update', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
  	{
        path: '/ProductImages',
        component: import('../../components/Admin/ProductImages/index.vue'),
        props: route => ({ TableName: 'ProductImages'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/ProductImages/create',
        component:  () => import('../../components/Admin/ProductImages/Form.vue'),
        props: route => ({ TableName: 'ProductImages', action: 'create'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/ProductImages/view/:id',
        component:  () => import('../../components/Admin/ProductImages/Form.vue'),
        props: route => ({ TableName: 'ProductImages', action: 'view', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/ProductImages/update/:id',
        component:  () => import('../../components/Admin/ProductImages/Form.vue'),
        props: route => ({ TableName: 'ProductImages', action: 'update', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
  	{
        path: '/ProductItems',
        component: import('../../components/Admin/ProductItems/index.vue'),
        props: route => ({ TableName: 'ProductItems'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/ProductItems/create',
        component:  () => import('../../components/Admin/ProductItems/Form.vue'),
        props: route => ({ TableName: 'ProductItems', action: 'create'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/ProductItems/view/:id',
        component:  () => import('../../components/Admin/ProductItems/Form.vue'),
        props: route => ({ TableName: 'ProductItems', action: 'view', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/ProductItems/update/:id',
        component:  () => import('../../components/Admin/ProductItems/Form.vue'),
        props: route => ({ TableName: 'ProductItems', action: 'update', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
  	{
        path: '/PromotionProduct',
        component: import('../../components/Admin/PromotionProduct/index.vue'),
        props: route => ({ TableName: 'PromotionProduct'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/PromotionProduct/create',
        component:  () => import('../../components/Admin/PromotionProduct/Form.vue'),
        props: route => ({ TableName: 'PromotionProduct', action: 'create'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/PromotionProduct/view/:id',
        component:  () => import('../../components/Admin/PromotionProduct/Form.vue'),
        props: route => ({ TableName: 'PromotionProduct', action: 'view', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/PromotionProduct/update/:id',
        component:  () => import('../../components/Admin/PromotionProduct/Form.vue'),
        props: route => ({ TableName: 'PromotionProduct', action: 'update', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
  	{
        path: '/Promotions',
        component: import('../../components/Admin/Promotions/index.vue'),
        props: route => ({ TableName: 'Promotions'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/Promotions/create',
        component:  () => import('../../components/Admin/Promotions/Form.vue'),
        props: route => ({ TableName: 'Promotions', action: 'create'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/Promotions/view/:id',
        component:  () => import('../../components/Admin/Promotions/Form.vue'),
        props: route => ({ TableName: 'Promotions', action: 'view', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/Promotions/update/:id',
        component:  () => import('../../components/Admin/Promotions/Form.vue'),
        props: route => ({ TableName: 'Promotions', action: 'update', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
  	{
        path: '/PurchaseOrderItems',
        component: import('../../components/Admin/PurchaseOrderItems/index.vue'),
        props: route => ({ TableName: 'PurchaseOrderItems'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/PurchaseOrderItems/create',
        component:  () => import('../../components/Admin/PurchaseOrderItems/Form.vue'),
        props: route => ({ TableName: 'PurchaseOrderItems', action: 'create'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/PurchaseOrderItems/view/:id',
        component:  () => import('../../components/Admin/PurchaseOrderItems/Form.vue'),
        props: route => ({ TableName: 'PurchaseOrderItems', action: 'view', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/PurchaseOrderItems/update/:id',
        component:  () => import('../../components/Admin/PurchaseOrderItems/Form.vue'),
        props: route => ({ TableName: 'PurchaseOrderItems', action: 'update', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
  	{
        path: '/PurchaseOrders',
        component: import('../../components/Admin/PurchaseOrders/index.vue'),
        props: route => ({ TableName: 'PurchaseOrders'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/PurchaseOrders/create',
        component:  () => import('../../components/Admin/PurchaseOrders/Form.vue'),
        props: route => ({ TableName: 'PurchaseOrders', action: 'create'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/PurchaseOrders/view/:id',
        component:  () => import('../../components/Admin/PurchaseOrders/Form.vue'),
        props: route => ({ TableName: 'PurchaseOrders', action: 'view', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/PurchaseOrders/update/:id',
        component:  () => import('../../components/Admin/PurchaseOrders/Form.vue'),
        props: route => ({ TableName: 'PurchaseOrders', action: 'update', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
  	{
        path: '/Returns',
        component: import('../../components/Admin/Returns/index.vue'),
        props: route => ({ TableName: 'Returns'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/Returns/create',
        component:  () => import('../../components/Admin/Returns/Form.vue'),
        props: route => ({ TableName: 'Returns', action: 'create'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/Returns/view/:id',
        component:  () => import('../../components/Admin/Returns/Form.vue'),
        props: route => ({ TableName: 'Returns', action: 'view', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/Returns/update/:id',
        component:  () => import('../../components/Admin/Returns/Form.vue'),
        props: route => ({ TableName: 'Returns', action: 'update', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
  	{
        path: '/Reviews',
        component: import('../../components/Admin/Reviews/index.vue'),
        props: route => ({ TableName: 'Reviews'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/Reviews/create',
        component:  () => import('../../components/Admin/Reviews/Form.vue'),
        props: route => ({ TableName: 'Reviews', action: 'create'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/Reviews/view/:id',
        component:  () => import('../../components/Admin/Reviews/Form.vue'),
        props: route => ({ TableName: 'Reviews', action: 'view', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/Reviews/update/:id',
        component:  () => import('../../components/Admin/Reviews/Form.vue'),
        props: route => ({ TableName: 'Reviews', action: 'update', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
  	{
        path: '/Roles',
        component: import('../../components/Admin/Roles/index.vue'),
        props: route => ({ TableName: 'Roles'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/Roles/create',
        component:  () => import('../../components/Admin/Roles/Form.vue'),
        props: route => ({ TableName: 'Roles', action: 'create'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/Roles/view/:id',
        component:  () => import('../../components/Admin/Roles/Form.vue'),
        props: route => ({ TableName: 'Roles', action: 'view', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/Roles/update/:id',
        component:  () => import('../../components/Admin/Roles/Form.vue'),
        props: route => ({ TableName: 'Roles', action: 'update', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
  	{
        path: '/ShippingMethods',
        component: import('../../components/Admin/ShippingMethods/index.vue'),
        props: route => ({ TableName: 'ShippingMethods'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/ShippingMethods/create',
        component:  () => import('../../components/Admin/ShippingMethods/Form.vue'),
        props: route => ({ TableName: 'ShippingMethods', action: 'create'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/ShippingMethods/view/:id',
        component:  () => import('../../components/Admin/ShippingMethods/Form.vue'),
        props: route => ({ TableName: 'ShippingMethods', action: 'view', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/ShippingMethods/update/:id',
        component:  () => import('../../components/Admin/ShippingMethods/Form.vue'),
        props: route => ({ TableName: 'ShippingMethods', action: 'update', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
  	{
        path: '/UserAddresses',
        component: import('../../components/Admin/UserAddresses/index.vue'),
        props: route => ({ TableName: 'UserAddresses'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/UserAddresses/create',
        component:  () => import('../../components/Admin/UserAddresses/Form.vue'),
        props: route => ({ TableName: 'UserAddresses', action: 'create'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/UserAddresses/view/:id',
        component:  () => import('../../components/Admin/UserAddresses/Form.vue'),
        props: route => ({ TableName: 'UserAddresses', action: 'view', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/UserAddresses/update/:id',
        component:  () => import('../../components/Admin/UserAddresses/Form.vue'),
        props: route => ({ TableName: 'UserAddresses', action: 'update', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
  	{
        path: '/UserLogs',
        component: import('../../components/Admin/UserLogs/index.vue'),
        props: route => ({ TableName: 'UserLogs'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/UserLogs/create',
        component:  () => import('../../components/Admin/UserLogs/Form.vue'),
        props: route => ({ TableName: 'UserLogs', action: 'create'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/UserLogs/view/:id',
        component:  () => import('../../components/Admin/UserLogs/Form.vue'),
        props: route => ({ TableName: 'UserLogs', action: 'view', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/UserLogs/update/:id',
        component:  () => import('../../components/Admin/UserLogs/Form.vue'),
        props: route => ({ TableName: 'UserLogs', action: 'update', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
  	{
        path: '/VariantValues',
        component: import('../../components/Admin/VariantValues/index.vue'),
        props: route => ({ TableName: 'VariantValues'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/VariantValues/create',
        component:  () => import('../../components/Admin/VariantValues/Form.vue'),
        props: route => ({ TableName: 'VariantValues', action: 'create'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/VariantValues/view/:id',
        component:  () => import('../../components/Admin/VariantValues/Form.vue'),
        props: route => ({ TableName: 'VariantValues', action: 'view', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/VariantValues/update/:id',
        component:  () => import('../../components/Admin/VariantValues/Form.vue'),
        props: route => ({ TableName: 'VariantValues', action: 'update', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
  	{
        path: '/Variants',
        component: import('../../components/Admin/Variants/index.vue'),
        props: route => ({ TableName: 'Variants'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/Variants/create',
        component:  () => import('../../components/Admin/Variants/Form.vue'),
        props: route => ({ TableName: 'Variants', action: 'create'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/Variants/view/:id',
        component:  () => import('../../components/Admin/Variants/Form.vue'),
        props: route => ({ TableName: 'Variants', action: 'view', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: '/Variants/update/:id',
        component:  () => import('../../components/Admin/Variants/Form.vue'),
        props: route => ({ TableName: 'Variants', action: 'update', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
]
