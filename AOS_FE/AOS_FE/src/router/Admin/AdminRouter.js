
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
        path: 'Accounts',
        component: import('../../components/Admin/Accounts/index.vue'),
        props: route => ({ TableName: 'Accounts'})
    },
    {
        path: 'Accounts/create',
        component:  () => import('../../components/Admin/Accounts/Form.vue'),
        props: route => ({ TableName: 'Accounts', action: 'create'})
    },
    {
        path: 'Accounts/view/:id',
        component:  () => import('../../components/Admin/Accounts/Form.vue'),
        props: route => ({ TableName: 'Accounts', action: 'view', id: route.params.id,})
    },
    {
        path: 'Accounts/update/:id',
        component:  () => import('../../components/Admin/Accounts/Form.vue'),
        props: route => ({ TableName: 'Accounts', action: 'update', id: route.params.id,})
    },
  	{
        path: 'Authorities',
        component: import('../../components/Admin/Authorities/index.vue'),
        props: route => ({ TableName: 'Authorities'})
    },
    {
        path: 'Authorities/create',
        component:  () => import('../../components/Admin/Authorities/Form.vue'),
        props: route => ({ TableName: 'Authorities', action: 'create'})
    },
    {
        path: 'Authorities/view/:id',
        component:  () => import('../../components/Admin/Authorities/Form.vue'),
        props: route => ({ TableName: 'Authorities', action: 'view', id: route.params.id,})
    },
    {
        path: 'Authorities/update/:id',
        component:  () => import('../../components/Admin/Authorities/Form.vue'),
        props: route => ({ TableName: 'Authorities', action: 'update', id: route.params.id,})
    },
  	{
        path: 'BaseProducts',
        component: import('../../components/Admin/BaseProducts/index.vue'),
        props: route => ({ TableName: 'BaseProducts'})
    },
    {
        path: 'BaseProducts/create',
        component:  () => import('../../components/Admin/BaseProducts/Form.vue'),
        props: route => ({ TableName: 'BaseProducts', action: 'create'})
    },
    {
        path: 'BaseProducts/view/:id',
        component:  () => import('../../components/Admin/BaseProducts/Form.vue'),
        props: route => ({ TableName: 'BaseProducts', action: 'view', id: route.params.id,})
    },
    {
        path: 'BaseProducts/update/:id',
        component:  () => import('../../components/Admin/BaseProducts/Form.vue'),
        props: route => ({ TableName: 'BaseProducts', action: 'update', id: route.params.id,})
    },
  	{
        path: 'Cancels',
        component: import('../../components/Admin/Cancels/index.vue'),
        props: route => ({ TableName: 'Cancels'})
    },
    {
        path: 'Cancels/create',
        component:  () => import('../../components/Admin/Cancels/Form.vue'),
        props: route => ({ TableName: 'Cancels', action: 'create'})
    },
    {
        path: 'Cancels/view/:id',
        component:  () => import('../../components/Admin/Cancels/Form.vue'),
        props: route => ({ TableName: 'Cancels', action: 'view', id: route.params.id,})
    },
    {
        path: 'Cancels/update/:id',
        component:  () => import('../../components/Admin/Cancels/Form.vue'),
        props: route => ({ TableName: 'Cancels', action: 'update', id: route.params.id,})
    },
  	{
        path: 'CartItems',
        component: import('../../components/Admin/CartItems/index.vue'),
        props: route => ({ TableName: 'CartItems'})
    },
    {
        path: 'CartItems/create',
        component:  () => import('../../components/Admin/CartItems/Form.vue'),
        props: route => ({ TableName: 'CartItems', action: 'create'})
    },
    {
        path: 'CartItems/view/:id',
        component:  () => import('../../components/Admin/CartItems/Form.vue'),
        props: route => ({ TableName: 'CartItems', action: 'view', id: route.params.id,})
    },
    {
        path: 'CartItems/update/:id',
        component:  () => import('../../components/Admin/CartItems/Form.vue'),
        props: route => ({ TableName: 'CartItems', action: 'update', id: route.params.id,})
    },
  	{
        path: 'Catalogs',
        component: import('../../components/Admin/Catalogs/index.vue'),
        props: route => ({ TableName: 'Catalogs'})
    },
    {
        path: 'Catalogs/create',
        component:  () => import('../../components/Admin/Catalogs/Form.vue'),
        props: route => ({ TableName: 'Catalogs', action: 'create'})
    },
    {
        path: 'Catalogs/view/:id',
        component:  () => import('../../components/Admin/Catalogs/Form.vue'),
        props: route => ({ TableName: 'Catalogs', action: 'view', id: route.params.id,})
    },
    {
        path: 'Catalogs/update/:id',
        component:  () => import('../../components/Admin/Catalogs/Form.vue'),
        props: route => ({ TableName: 'Catalogs', action: 'update', id: route.params.id,})
    },
  	{
        path: 'Categories',
        component: import('../../components/Admin/Categories/index.vue'),
        props: route => ({ TableName: 'Categories'})
    },
    {
        path: 'Categories/create',
        component:  () => import('../../components/Admin/Categories/Form.vue'),
        props: route => ({ TableName: 'Categories', action: 'create'})
    },
    {
        path: 'Categories/view/:id',
        component:  () => import('../../components/Admin/Categories/Form.vue'),
        props: route => ({ TableName: 'Categories', action: 'view', id: route.params.id,})
    },
    {
        path: 'Categories/update/:id',
        component:  () => import('../../components/Admin/Categories/Form.vue'),
        props: route => ({ TableName: 'Categories', action: 'update', id: route.params.id,})
    },
  	{
        path: 'CostHistories',
        component: import('../../components/Admin/CostHistories/index.vue'),
        props: route => ({ TableName: 'CostHistories'})
    },
    {
        path: 'CostHistories/create',
        component:  () => import('../../components/Admin/CostHistories/Form.vue'),
        props: route => ({ TableName: 'CostHistories', action: 'create'})
    },
    {
        path: 'CostHistories/view/:id',
        component:  () => import('../../components/Admin/CostHistories/Form.vue'),
        props: route => ({ TableName: 'CostHistories', action: 'view', id: route.params.id,})
    },
    {
        path: 'CostHistories/update/:id',
        component:  () => import('../../components/Admin/CostHistories/Form.vue'),
        props: route => ({ TableName: 'CostHistories', action: 'update', id: route.params.id,})
    },
  	{
        path: 'Coupons',
        component: import('../../components/Admin/Coupons/index.vue'),
        props: route => ({ TableName: 'Coupons'})
    },
    {
        path: 'Coupons/create',
        component:  () => import('../../components/Admin/Coupons/Form.vue'),
        props: route => ({ TableName: 'Coupons', action: 'create'})
    },
    {
        path: 'Coupons/view/:id',
        component:  () => import('../../components/Admin/Coupons/Form.vue'),
        props: route => ({ TableName: 'Coupons', action: 'view', id: route.params.id,})
    },
    {
        path: 'Coupons/update/:id',
        component:  () => import('../../components/Admin/Coupons/Form.vue'),
        props: route => ({ TableName: 'Coupons', action: 'update', id: route.params.id,})
    },
  	{
        path: 'Custom',
        component: import('../../components/Admin/Custom/index.vue'),
        props: route => ({ TableName: 'Custom'})
    },
    {
        path: 'Custom/create',
        component:  () => import('../../components/Admin/Custom/Form.vue'),
        props: route => ({ TableName: 'Custom', action: 'create'})
    },
    {
        path: 'Custom/view/:id',
        component:  () => import('../../components/Admin/Custom/Form.vue'),
        props: route => ({ TableName: 'Custom', action: 'view', id: route.params.id,})
    },
    {
        path: 'Custom/update/:id',
        component:  () => import('../../components/Admin/Custom/Form.vue'),
        props: route => ({ TableName: 'Custom', action: 'update', id: route.params.id,})
    },
  	{
        path: 'News',
        component: import('../../components/Admin/News/index.vue'),
        props: route => ({ TableName: 'News'})
    },
    {
        path: 'News/create',
        component:  () => import('../../components/Admin/News/Form.vue'),
        props: route => ({ TableName: 'News', action: 'create'})
    },
    {
        path: 'News/view/:id',
        component:  () => import('../../components/Admin/News/Form.vue'),
        props: route => ({ TableName: 'News', action: 'view', id: route.params.id,})
    },
    {
        path: 'News/update/:id',
        component:  () => import('../../components/Admin/News/Form.vue'),
        props: route => ({ TableName: 'News', action: 'update', id: route.params.id,})
    },
  	{
        path: 'OrderItems',
        component: import('../../components/Admin/OrderItems/index.vue'),
        props: route => ({ TableName: 'OrderItems'})
    },
    {
        path: 'OrderItems/create',
        component:  () => import('../../components/Admin/OrderItems/Form.vue'),
        props: route => ({ TableName: 'OrderItems', action: 'create'})
    },
    {
        path: 'OrderItems/view/:id',
        component:  () => import('../../components/Admin/OrderItems/Form.vue'),
        props: route => ({ TableName: 'OrderItems', action: 'view', id: route.params.id,})
    },
    {
        path: 'OrderItems/update/:id',
        component:  () => import('../../components/Admin/OrderItems/Form.vue'),
        props: route => ({ TableName: 'OrderItems', action: 'update', id: route.params.id,})
    },
  	{
        path: 'Orders',
        component: import('../../components/Admin/Orders/index.vue'),
        props: route => ({ TableName: 'Orders'})
    },
    {
        path: 'Orders/create',
        component:  () => import('../../components/Admin/Orders/Form.vue'),
        props: route => ({ TableName: 'Orders', action: 'create'})
    },
    {
        path: 'Orders/view/:id',
        component:  () => import('../../components/Admin/Orders/Form.vue'),
        props: route => ({ TableName: 'Orders', action: 'view', id: route.params.id,})
    },
    {
        path: 'Orders/update/:id',
        component:  () => import('../../components/Admin/Orders/Form.vue'),
        props: route => ({ TableName: 'Orders', action: 'update', id: route.params.id,})
    },
  	{
        path: 'PaymentMethods',
        component: import('../../components/Admin/PaymentMethods/index.vue'),
        props: route => ({ TableName: 'PaymentMethods'})
    },
    {
        path: 'PaymentMethods/create',
        component:  () => import('../../components/Admin/PaymentMethods/Form.vue'),
        props: route => ({ TableName: 'PaymentMethods', action: 'create'})
    },
    {
        path: 'PaymentMethods/view/:id',
        component:  () => import('../../components/Admin/PaymentMethods/Form.vue'),
        props: route => ({ TableName: 'PaymentMethods', action: 'view', id: route.params.id,})
    },
    {
        path: 'PaymentMethods/update/:id',
        component:  () => import('../../components/Admin/PaymentMethods/Form.vue'),
        props: route => ({ TableName: 'PaymentMethods', action: 'update', id: route.params.id,})
    },
  	{
        path: 'PriceHistories',
        component: import('../../components/Admin/PriceHistories/index.vue'),
        props: route => ({ TableName: 'PriceHistories'})
    },
    {
        path: 'PriceHistories/create',
        component:  () => import('../../components/Admin/PriceHistories/Form.vue'),
        props: route => ({ TableName: 'PriceHistories', action: 'create'})
    },
    {
        path: 'PriceHistories/view/:id',
        component:  () => import('../../components/Admin/PriceHistories/Form.vue'),
        props: route => ({ TableName: 'PriceHistories', action: 'view', id: route.params.id,})
    },
    {
        path: 'PriceHistories/update/:id',
        component:  () => import('../../components/Admin/PriceHistories/Form.vue'),
        props: route => ({ TableName: 'PriceHistories', action: 'update', id: route.params.id,})
    },
  	{
        path: 'ProductImages',
        component: import('../../components/Admin/ProductImages/index.vue'),
        props: route => ({ TableName: 'ProductImages'})
    },
    {
        path: 'ProductImages/create',
        component:  () => import('../../components/Admin/ProductImages/Form.vue'),
        props: route => ({ TableName: 'ProductImages', action: 'create'})
    },
    {
        path: 'ProductImages/view/:id',
        component:  () => import('../../components/Admin/ProductImages/Form.vue'),
        props: route => ({ TableName: 'ProductImages', action: 'view', id: route.params.id,})
    },
    {
        path: 'ProductImages/update/:id',
        component:  () => import('../../components/Admin/ProductImages/Form.vue'),
        props: route => ({ TableName: 'ProductImages', action: 'update', id: route.params.id,})
    },
  	{
        path: 'ProductItems',
        component: import('../../components/Admin/ProductItems/index.vue'),
        props: route => ({ TableName: 'ProductItems'})
    },
    {
        path: 'ProductItems/create',
        component:  () => import('../../components/Admin/ProductItems/Form.vue'),
        props: route => ({ TableName: 'ProductItems', action: 'create'})
    },
    {
        path: 'ProductItems/view/:id',
        component:  () => import('../../components/Admin/ProductItems/Form.vue'),
        props: route => ({ TableName: 'ProductItems', action: 'view', id: route.params.id,})
    },
    {
        path: 'ProductItems/update/:id',
        component:  () => import('../../components/Admin/ProductItems/Form.vue'),
        props: route => ({ TableName: 'ProductItems', action: 'update', id: route.params.id,})
    },
  	{
        path: 'PromotionProduct',
        component: import('../../components/Admin/PromotionProduct/index.vue'),
        props: route => ({ TableName: 'PromotionProduct'})
    },
    {
        path: 'PromotionProduct/create',
        component:  () => import('../../components/Admin/PromotionProduct/Form.vue'),
        props: route => ({ TableName: 'PromotionProduct', action: 'create'})
    },
    {
        path: 'PromotionProduct/view/:id',
        component:  () => import('../../components/Admin/PromotionProduct/Form.vue'),
        props: route => ({ TableName: 'PromotionProduct', action: 'view', id: route.params.id,})
    },
    {
        path: 'PromotionProduct/update/:id',
        component:  () => import('../../components/Admin/PromotionProduct/Form.vue'),
        props: route => ({ TableName: 'PromotionProduct', action: 'update', id: route.params.id,})
    },
  	{
        path: 'Promotions',
        component: import('../../components/Admin/Promotions/index.vue'),
        props: route => ({ TableName: 'Promotions'})
    },
    {
        path: 'Promotions/create',
        component:  () => import('../../components/Admin/Promotions/Form.vue'),
        props: route => ({ TableName: 'Promotions', action: 'create'})
    },
    {
        path: 'Promotions/view/:id',
        component:  () => import('../../components/Admin/Promotions/Form.vue'),
        props: route => ({ TableName: 'Promotions', action: 'view', id: route.params.id,})
    },
    {
        path: 'Promotions/update/:id',
        component:  () => import('../../components/Admin/Promotions/Form.vue'),
        props: route => ({ TableName: 'Promotions', action: 'update', id: route.params.id,})
    },
  	{
        path: 'PurchaseOrderItems',
        component: import('../../components/Admin/PurchaseOrderItems/index.vue'),
        props: route => ({ TableName: 'PurchaseOrderItems'})
    },
    {
        path: 'PurchaseOrderItems/create',
        component:  () => import('../../components/Admin/PurchaseOrderItems/Form.vue'),
        props: route => ({ TableName: 'PurchaseOrderItems', action: 'create'})
    },
    {
        path: 'PurchaseOrderItems/view/:id',
        component:  () => import('../../components/Admin/PurchaseOrderItems/Form.vue'),
        props: route => ({ TableName: 'PurchaseOrderItems', action: 'view', id: route.params.id,})
    },
    {
        path: 'PurchaseOrderItems/update/:id',
        component:  () => import('../../components/Admin/PurchaseOrderItems/Form.vue'),
        props: route => ({ TableName: 'PurchaseOrderItems', action: 'update', id: route.params.id,})
    },
  	{
        path: 'PurchaseOrders',
        component: import('../../components/Admin/PurchaseOrders/index.vue'),
        props: route => ({ TableName: 'PurchaseOrders'})
    },
    {
        path: 'PurchaseOrders/create',
        component:  () => import('../../components/Admin/PurchaseOrders/Form.vue'),
        props: route => ({ TableName: 'PurchaseOrders', action: 'create'})
    },
    {
        path: 'PurchaseOrders/view/:id',
        component:  () => import('../../components/Admin/PurchaseOrders/Form.vue'),
        props: route => ({ TableName: 'PurchaseOrders', action: 'view', id: route.params.id,})
    },
    {
        path: 'PurchaseOrders/update/:id',
        component:  () => import('../../components/Admin/PurchaseOrders/Form.vue'),
        props: route => ({ TableName: 'PurchaseOrders', action: 'update', id: route.params.id,})
    },
  	{
        path: 'Returns',
        component: import('../../components/Admin/Returns/index.vue'),
        props: route => ({ TableName: 'Returns'})
    },
    {
        path: 'Returns/create',
        component:  () => import('../../components/Admin/Returns/Form.vue'),
        props: route => ({ TableName: 'Returns', action: 'create'})
    },
    {
        path: 'Returns/view/:id',
        component:  () => import('../../components/Admin/Returns/Form.vue'),
        props: route => ({ TableName: 'Returns', action: 'view', id: route.params.id,})
    },
    {
        path: 'Returns/update/:id',
        component:  () => import('../../components/Admin/Returns/Form.vue'),
        props: route => ({ TableName: 'Returns', action: 'update', id: route.params.id,})
    },
  	{
        path: 'Reviews',
        component: import('../../components/Admin/Reviews/index.vue'),
        props: route => ({ TableName: 'Reviews'})
    },
    {
        path: 'Reviews/create',
        component:  () => import('../../components/Admin/Reviews/Form.vue'),
        props: route => ({ TableName: 'Reviews', action: 'create'})
    },
    {
        path: 'Reviews/view/:id',
        component:  () => import('../../components/Admin/Reviews/Form.vue'),
        props: route => ({ TableName: 'Reviews', action: 'view', id: route.params.id,})
    },
    {
        path: 'Reviews/update/:id',
        component:  () => import('../../components/Admin/Reviews/Form.vue'),
        props: route => ({ TableName: 'Reviews', action: 'update', id: route.params.id,})
    },
  	{
        path: 'Roles',
        component: import('../../components/Admin/Roles/index.vue'),
        props: route => ({ TableName: 'Roles'})
    },
    {
        path: 'Roles/create',
        component:  () => import('../../components/Admin/Roles/Form.vue'),
        props: route => ({ TableName: 'Roles', action: 'create'})
    },
    {
        path: 'Roles/view/:id',
        component:  () => import('../../components/Admin/Roles/Form.vue'),
        props: route => ({ TableName: 'Roles', action: 'view', id: route.params.id,})
    },
    {
        path: 'Roles/update/:id',
        component:  () => import('../../components/Admin/Roles/Form.vue'),
        props: route => ({ TableName: 'Roles', action: 'update', id: route.params.id,})
    },
  	{
        path: 'ShippingMethods',
        component: import('../../components/Admin/ShippingMethods/index.vue'),
        props: route => ({ TableName: 'ShippingMethods'})
    },
    {
        path: 'ShippingMethods/create',
        component:  () => import('../../components/Admin/ShippingMethods/Form.vue'),
        props: route => ({ TableName: 'ShippingMethods', action: 'create'})
    },
    {
        path: 'ShippingMethods/view/:id',
        component:  () => import('../../components/Admin/ShippingMethods/Form.vue'),
        props: route => ({ TableName: 'ShippingMethods', action: 'view', id: route.params.id,})
    },
    {
        path: 'ShippingMethods/update/:id',
        component:  () => import('../../components/Admin/ShippingMethods/Form.vue'),
        props: route => ({ TableName: 'ShippingMethods', action: 'update', id: route.params.id,})
    },
  	{
        path: 'UserAddresses',
        component: import('../../components/Admin/UserAddresses/index.vue'),
        props: route => ({ TableName: 'UserAddresses'})
    },
    {
        path: 'UserAddresses/create',
        component:  () => import('../../components/Admin/UserAddresses/Form.vue'),
        props: route => ({ TableName: 'UserAddresses', action: 'create'})
    },
    {
        path: 'UserAddresses/view/:id',
        component:  () => import('../../components/Admin/UserAddresses/Form.vue'),
        props: route => ({ TableName: 'UserAddresses', action: 'view', id: route.params.id,})
    },
    {
        path: 'UserAddresses/update/:id',
        component:  () => import('../../components/Admin/UserAddresses/Form.vue'),
        props: route => ({ TableName: 'UserAddresses', action: 'update', id: route.params.id,})
    },
  	{
        path: 'UserLogs',
        component: import('../../components/Admin/UserLogs/index.vue'),
        props: route => ({ TableName: 'UserLogs'})
    },
    {
        path: 'UserLogs/create',
        component:  () => import('../../components/Admin/UserLogs/Form.vue'),
        props: route => ({ TableName: 'UserLogs', action: 'create'})
    },
    {
        path: 'UserLogs/view/:id',
        component:  () => import('../../components/Admin/UserLogs/Form.vue'),
        props: route => ({ TableName: 'UserLogs', action: 'view', id: route.params.id,})
    },
    {
        path: 'UserLogs/update/:id',
        component:  () => import('../../components/Admin/UserLogs/Form.vue'),
        props: route => ({ TableName: 'UserLogs', action: 'update', id: route.params.id,})
    },
  	{
        path: 'VariantValues',
        component: import('../../components/Admin/VariantValues/index.vue'),
        props: route => ({ TableName: 'VariantValues'})
    },
    {
        path: 'VariantValues/create',
        component:  () => import('../../components/Admin/VariantValues/Form.vue'),
        props: route => ({ TableName: 'VariantValues', action: 'create'})
    },
    {
        path: 'VariantValues/view/:id',
        component:  () => import('../../components/Admin/VariantValues/Form.vue'),
        props: route => ({ TableName: 'VariantValues', action: 'view', id: route.params.id,})
    },
    {
        path: 'VariantValues/update/:id',
        component:  () => import('../../components/Admin/VariantValues/Form.vue'),
        props: route => ({ TableName: 'VariantValues', action: 'update', id: route.params.id,})
    },
  	{
        path: 'Variants',
        component: import('../../components/Admin/Variants/index.vue'),
        props: route => ({ TableName: 'Variants'})
    },
    {
        path: 'Variants/create',
        component:  () => import('../../components/Admin/Variants/Form.vue'),
        props: route => ({ TableName: 'Variants', action: 'create'})
    },
    {
        path: 'Variants/view/:id',
        component:  () => import('../../components/Admin/Variants/Form.vue'),
        props: route => ({ TableName: 'Variants', action: 'view', id: route.params.id,})
    },
    {
        path: 'Variants/update/:id',
        component:  () => import('../../components/Admin/Variants/Form.vue'),
        props: route => ({ TableName: 'Variants', action: 'update', id: route.params.id,})
    },
]
