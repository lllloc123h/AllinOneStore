import ModuleQuanLy from '../../components/Admin/ModuleQuanLy.vue'
const listDashBoard = [
    "Accounts",
    "Authorities",
    "BaseProducts",
    "Cancels",
    "Catalogs",
    "Categories",
    "CostHistories",
    "Coupons",
    "News",
    "Orders",
    "PaymentMethods",
    "Promotions",
    "ProductItems",
]

export default [
    {
        path: '/Admin/Accounts',
        component: ModuleQuanLy,
        name: 'QuanLyTaiKhoan',
        props: { id: "-1", TableName: 'Accounts', listDashBoard: listDashBoard }
    },
    {
        path: '/Admin/Accounts/view/:id',
        component: ModuleQuanLy,
        name: 'AdminViewAccount',
        props: route => ({ id: route.params.id, action: 'view', TableName: 'Accounts', listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/Accounts/update/:id',
        component: ModuleQuanLy,
        name: 'AdminUpdateAccount',
        props: route => ({ id: route.params.id, action: 'update', TableName: 'Accounts', listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/Accounts/delete/:id',
        component: ModuleQuanLy,
        name: 'AdminDeleteAccount',
        props: route => ({ id: route.params.id, action: 'delete', TableName: 'Accounts', listDashBoard: listDashBoard })
    }

    , {
        path: '/Admin/ProductItems',
        component: ModuleQuanLy,
        props: { id: "-1", TableName: 'ProductItems', listDashBoard: listDashBoard }
    }
    ,
    {
        path: '/Admin/ProductItems/view/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'view', TableName: 'ProductItems', listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/ProductItems/update/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'update', TableName: 'ProductItems', listDashBoard: listDashBoard })
    },
    {
        path: '/Admin/ProductItems/delete/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'delete', TableName: 'ProductItems', listDashBoard: listDashBoard })
    }
    , {
        path: '/Admin/PaymentMethods',
        component: ModuleQuanLy,
        props: { id: "-1", TableName: 'PaymentMethods', listDashBoard: listDashBoard }
    },
    {
        path: '/Admin/PaymentMethods/view/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'view', TableName: 'PaymentMethods' ,listDashBoard:listDashBoard})
    },
    {
        path: '/Admin/PaymentMethods/update/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'update', TableName: 'PaymentMethods' ,listDashBoard:listDashBoard})
    },
    {
        path: '/Admin/PaymentMethods/delete/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'delete', TableName: 'PaymentMethods' ,listDashBoard:listDashBoard})
    }
    ,{
        path: '/Admin/ProductImages',
        component: ModuleQuanLy,
        props: {id:"-1", TableName: 'ProductImages' ,listDashBoard:listDashBoard}
    },
    {
        path: '/Admin/ProductImages/view/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'view', TableName: 'ProductImages',listDashBoard:listDashBoard })
    },
    {
        path: '/Admin/ProductImages/update/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'update', TableName: 'ProductImages' ,listDashBoard:listDashBoard})
    },
    {
        path: '/Admin/ProductImages/delete/:id',
        component: ModuleQuanLy,
        props: route => ({ id: route.params.id, action: 'delete', TableName: 'ProductImages' ,listDashBoard:listDashBoard})
    }
]
