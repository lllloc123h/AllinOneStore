import ModuleQuanLy from '../../components/Admin/ModuleQuanLy.vue'
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
    "VariantValues",
    "Variants",
    "TraceXeActionMap",
    "TraceXeEventMap",
]

export default [

    {
        path: '/Admin/Accounts',
        component: ModuleQuanLy,
        props: { id: "-1", TableName: 'Accounts', isEditing: false, listDashBoard: listDashBoard }
    },
    {
        path: '/Admin/Accounts/view/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'view', TableName: 'Accounts', isEditing: false, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/Accounts/update/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'update', TableName: 'Accounts', isEditing: true, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/Accounts/delete/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'delete', TableName: 'Accounts', isEditing: false, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/Authorities',
        component: ModuleQuanLy,
        props: { id: "-1", TableName: 'Authorities', isEditing: false, listDashBoard: listDashBoard }
    },
    {
        path: '/Admin/Authorities/view/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'view', TableName: 'Authorities', isEditing: false, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/Authorities/update/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'update', TableName: 'Authorities', isEditing: true, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/Authorities/delete/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'delete', TableName: 'Authorities', isEditing: false, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/BaseProducts',
        component: ModuleQuanLy,
        props: { id: "-1", TableName: 'BaseProducts', isEditing: false, listDashBoard: listDashBoard }
    },
    {
        path: '/Admin/BaseProducts/view/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'view', TableName: 'BaseProducts', isEditing: false, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/BaseProducts/update/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'update', TableName: 'BaseProducts', isEditing: true, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/BaseProducts/delete/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'delete', TableName: 'BaseProducts', isEditing: false, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/Cancels',
        component: ModuleQuanLy,
        props: { id: "-1", TableName: 'Cancels', isEditing: false, listDashBoard: listDashBoard }
    },
    {
        path: '/Admin/Cancels/view/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'view', TableName: 'Cancels', isEditing: false, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/Cancels/update/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'update', TableName: 'Cancels', isEditing: true, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/Cancels/delete/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'delete', TableName: 'Cancels', isEditing: false, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/CartItems',
        component: ModuleQuanLy,
        props: { id: "-1", TableName: 'CartItems', isEditing: false, listDashBoard: listDashBoard }
    },
    {
        path: '/Admin/CartItems/view/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'view', TableName: 'CartItems', isEditing: false, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/CartItems/update/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'update', TableName: 'CartItems', isEditing: true, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/CartItems/delete/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'delete', TableName: 'CartItems', isEditing: false, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/Catalogs',
        component: ModuleQuanLy,
        props: { id: "-1", TableName: 'Catalogs', isEditing: false, listDashBoard: listDashBoard }
    },
    {
        path: '/Admin/Catalogs/view/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'view', TableName: 'Catalogs', isEditing: false, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/Catalogs/update/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'update', TableName: 'Catalogs', isEditing: true, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/Catalogs/delete/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'delete', TableName: 'Catalogs', isEditing: false, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/Categories',
        component: ModuleQuanLy,
        props: { id: "-1", TableName: 'Categories', isEditing: false, listDashBoard: listDashBoard }
    },
    {
        path: '/Admin/Categories/view/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'view', TableName: 'Categories', isEditing: false, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/Categories/update/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'update', TableName: 'Categories', isEditing: true, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/Categories/delete/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'delete', TableName: 'Categories', isEditing: false, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/CostHistories',
        component: ModuleQuanLy,
        props: { id: "-1", TableName: 'CostHistories', isEditing: false, listDashBoard: listDashBoard }
    },
    {
        path: '/Admin/CostHistories/view/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'view', TableName: 'CostHistories', isEditing: false, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/CostHistories/update/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'update', TableName: 'CostHistories', isEditing: true, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/CostHistories/delete/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'delete', TableName: 'CostHistories', isEditing: false, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/Coupons',
        component: ModuleQuanLy,
        props: { id: "-1", TableName: 'Coupons', isEditing: false, listDashBoard: listDashBoard }
    },
    {
        path: '/Admin/Coupons/view/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'view', TableName: 'Coupons', isEditing: false, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/Coupons/update/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'update', TableName: 'Coupons', isEditing: true, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/Coupons/delete/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'delete', TableName: 'Coupons', isEditing: false, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/News',
        component: ModuleQuanLy,
        props: { id: "-1", TableName: 'News', isEditing: false, listDashBoard: listDashBoard }
    },
    {
        path: '/Admin/News/view/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'view', TableName: 'News', isEditing: false, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/News/update/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'update', TableName: 'News', isEditing: true, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/News/delete/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'delete', TableName: 'News', isEditing: false, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/OrderItems',
        component: ModuleQuanLy,
        props: { id: "-1", TableName: 'OrderItems', isEditing: false, listDashBoard: listDashBoard }
    },
    {
        path: '/Admin/OrderItems/view/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'view', TableName: 'OrderItems', isEditing: false, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/OrderItems/update/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'update', TableName: 'OrderItems', isEditing: true, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/OrderItems/delete/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'delete', TableName: 'OrderItems', isEditing: false, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/Orders',
        component: ModuleQuanLy,
        props: { id: "-1", TableName: 'Orders', isEditing: false, listDashBoard: listDashBoard }
    },
    {
        path: '/Admin/Orders/view/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'view', TableName: 'Orders', isEditing: false, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/Orders/update/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'update', TableName: 'Orders', isEditing: true, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/Orders/delete/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'delete', TableName: 'Orders', isEditing: false, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/PaymentMethods',
        component: ModuleQuanLy,
        props: { id: "-1", TableName: 'PaymentMethods', isEditing: false, listDashBoard: listDashBoard }
    },
    {
        path: '/Admin/PaymentMethods/view/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'view', TableName: 'PaymentMethods', isEditing: false, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/PaymentMethods/update/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'update', TableName: 'PaymentMethods', isEditing: true, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/PaymentMethods/delete/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'delete', TableName: 'PaymentMethods', isEditing: false, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/PriceHistories',
        component: ModuleQuanLy,
        props: { id: "-1", TableName: 'PriceHistories', isEditing: false, listDashBoard: listDashBoard }
    },
    {
        path: '/Admin/PriceHistories/view/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'view', TableName: 'PriceHistories', isEditing: false, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/PriceHistories/update/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'update', TableName: 'PriceHistories', isEditing: true, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/PriceHistories/delete/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'delete', TableName: 'PriceHistories', isEditing: false, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/ProductImages',
        component: ModuleQuanLy,
        props: { id: "-1", TableName: 'ProductImages', isEditing: false, listDashBoard: listDashBoard }
    },
    {
        path: '/Admin/ProductImages/view/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'view', TableName: 'ProductImages', isEditing: false, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/ProductImages/update/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'update', TableName: 'ProductImages', isEditing: true, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/ProductImages/delete/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'delete', TableName: 'ProductImages', isEditing: false, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/ProductItems',
        component: ModuleQuanLy,
        props: { id: "-1", TableName: 'ProductItems', isEditing: false, listDashBoard: listDashBoard }
    },
    {
        path: '/Admin/ProductItems/view/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'view', TableName: 'ProductItems', isEditing: false, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/ProductItems/update/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'update', TableName: 'ProductItems', isEditing: true, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/ProductItems/delete/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'delete', TableName: 'ProductItems', isEditing: false, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/PromotionProduct',
        component: ModuleQuanLy,
        props: { id: "-1", TableName: 'PromotionProduct', isEditing: false, listDashBoard: listDashBoard }
    },
    {
        path: '/Admin/PromotionProduct/view/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'view', TableName: 'PromotionProduct', isEditing: false, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/PromotionProduct/update/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'update', TableName: 'PromotionProduct', isEditing: true, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/PromotionProduct/delete/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'delete', TableName: 'PromotionProduct', isEditing: false, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/Promotions',
        component: ModuleQuanLy,
        props: { id: "-1", TableName: 'Promotions', isEditing: false, listDashBoard: listDashBoard }
    },
    {
        path: '/Admin/Promotions/view/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'view', TableName: 'Promotions', isEditing: false, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/Promotions/update/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'update', TableName: 'Promotions', isEditing: true, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/Promotions/delete/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'delete', TableName: 'Promotions', isEditing: false, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/PurchaseOrderItems',
        component: ModuleQuanLy,
        props: { id: "-1", TableName: 'PurchaseOrderItems', isEditing: false, listDashBoard: listDashBoard }
    },
    {
        path: '/Admin/PurchaseOrderItems/view/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'view', TableName: 'PurchaseOrderItems', isEditing: false, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/PurchaseOrderItems/update/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'update', TableName: 'PurchaseOrderItems', isEditing: true, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/PurchaseOrderItems/delete/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'delete', TableName: 'PurchaseOrderItems', isEditing: false, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/PurchaseOrders',
        component: ModuleQuanLy,
        props: { id: "-1", TableName: 'PurchaseOrders', isEditing: false, listDashBoard: listDashBoard }
    },
    {
        path: '/Admin/PurchaseOrders/view/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'view', TableName: 'PurchaseOrders', isEditing: false, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/PurchaseOrders/update/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'update', TableName: 'PurchaseOrders', isEditing: true, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/PurchaseOrders/delete/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'delete', TableName: 'PurchaseOrders', isEditing: false, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/Returns',
        component: ModuleQuanLy,
        props: { id: "-1", TableName: 'Returns', isEditing: false, listDashBoard: listDashBoard }
    },
    {
        path: '/Admin/Returns/view/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'view', TableName: 'Returns', isEditing: false, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/Returns/update/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'update', TableName: 'Returns', isEditing: true, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/Returns/delete/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'delete', TableName: 'Returns', isEditing: false, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/Reviews',
        component: ModuleQuanLy,
        props: { id: "-1", TableName: 'Reviews', isEditing: false, listDashBoard: listDashBoard }
    },
    {
        path: '/Admin/Reviews/view/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'view', TableName: 'Reviews', isEditing: false, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/Reviews/update/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'update', TableName: 'Reviews', isEditing: true, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/Reviews/delete/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'delete', TableName: 'Reviews', isEditing: false, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/Roles',
        component: ModuleQuanLy,
        props: { id: "-1", TableName: 'Roles', isEditing: false, listDashBoard: listDashBoard }
    },
    {
        path: '/Admin/Roles/view/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'view', TableName: 'Roles', isEditing: false, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/Roles/update/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'update', TableName: 'Roles', isEditing: true, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/Roles/delete/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'delete', TableName: 'Roles', isEditing: false, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/ShippingMethods',
        component: ModuleQuanLy,
        props: { id: "-1", TableName: 'ShippingMethods', isEditing: false, listDashBoard: listDashBoard }
    },
    {
        path: '/Admin/ShippingMethods/view/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'view', TableName: 'ShippingMethods', isEditing: false, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/ShippingMethods/update/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'update', TableName: 'ShippingMethods', isEditing: true, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/ShippingMethods/delete/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'delete', TableName: 'ShippingMethods', isEditing: false, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/UserAddresses',
        component: ModuleQuanLy,
        props: { id: "-1", TableName: 'UserAddresses', isEditing: false, listDashBoard: listDashBoard }
    },
    {
        path: '/Admin/UserAddresses/view/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'view', TableName: 'UserAddresses', isEditing: false, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/UserAddresses/update/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'update', TableName: 'UserAddresses', isEditing: true, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/UserAddresses/delete/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'delete', TableName: 'UserAddresses', isEditing: false, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/VariantValues',
        component: ModuleQuanLy,
        props: { id: "-1", TableName: 'VariantValues', isEditing: false, listDashBoard: listDashBoard }
    },
    {
        path: '/Admin/VariantValues/view/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'view', TableName: 'VariantValues', isEditing: false, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/VariantValues/update/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'update', TableName: 'VariantValues', isEditing: true, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/VariantValues/delete/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'delete', TableName: 'VariantValues', isEditing: false, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/Variants',
        component: ModuleQuanLy,
        props: { id: "-1", TableName: 'Variants', isEditing: false, listDashBoard: listDashBoard }
    },
    {
        path: '/Admin/Variants/view/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'view', TableName: 'Variants', isEditing: false, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/Variants/update/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'update', TableName: 'Variants', isEditing: true, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/Variants/delete/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'delete', TableName: 'Variants', isEditing: false, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/TraceXeActionMap',
        component: ModuleQuanLy,
        props: { id: "-1", TableName: 'TraceXeActionMap', isEditing: false, listDashBoard: listDashBoard }
    },
    {
        path: '/Admin/TraceXeActionMap/view/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'view', TableName: 'TraceXeActionMap', isEditing: false, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/TraceXeActionMap/update/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'update', TableName: 'TraceXeActionMap', isEditing: true, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/TraceXeActionMap/delete/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'delete', TableName: 'TraceXeActionMap', isEditing: false, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/TraceXeEventMap',
        component: ModuleQuanLy,
        props: { id: "-1", TableName: 'TraceXeEventMap', isEditing: false, listDashBoard: listDashBoard }
    },
    {
        path: '/Admin/TraceXeEventMap/view/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'view', TableName: 'TraceXeEventMap', isEditing: false, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/TraceXeEventMap/update/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'update', TableName: 'TraceXeEventMap', isEditing: true, listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/TraceXeEventMap/delete/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'delete', TableName: 'TraceXeEventMap', isEditing: false, listDashBoard: listDashBoard })
    },



]
