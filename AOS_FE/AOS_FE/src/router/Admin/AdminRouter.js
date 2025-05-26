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
        "PaymetMethods",
        "Promotions",
        "ProductItems", 
]

export default [
    {
        path: '/Admin/Accounts',
        component: ModuleQuanLy,
        name: 'QuanLyTaiKhoan',
        props: { id: "-1", TableName: 'Accounts',listDashBoard:listDashBoard }
    },
    {
        path: '/Admin/Accounts/view/:id',
        component: ModuleQuanLy,
        name: 'AdminViewAccount',
        props: route => ({ id: route.params.id, action: 'view', TableName: 'Accounts',listDashBoard:listDashBoard })
    },
    {
        path: '/Admin/Accounts/update/:id',
        component: ModuleQuanLy,
        name: 'AdminUpdateAccount',
        props: route => ({ id: route.params.id, action: 'update', TableName: 'Accounts' ,listDashBoard:listDashBoard})
    },
    {
        path: '/Admin/Accounts/delete/:id',
        component: ModuleQuanLy,
        name: 'AdminDeleteAccount',
        props: route => ({ id: route.params.id, action: 'delete', TableName: 'Accounts' ,listDashBoard:listDashBoard})
    }

    ,{
        path: '/Admin/ProductItems',
        component: ModuleQuanLy,
        name: 'QuanLySanPham',
        props: {id:"-1", TableName: 'ProductItems' ,listDashBoard:listDashBoard }
    }
    ,
    {
        path: '/Admin/ProductItems/view/:id',
        component: ModuleQuanLy,
        name: 'AdminViewAccount',
        props: route => ({ id: route.params.id, action: 'view', TableName: 'ProductItems' ,listDashBoard:listDashBoard})
    },
    // {
    //     path: '/Admin/ProductItems/update/:id',
    //     component: ModuleQuanLy,
    //     name: 'AdminUpdateAccount',
    //     props: route => ({ id: route.params.id, action: 'update', TableName: 'ProductItems' ,listDashBoard:listDashBoard})
    // },
    // {
    //     path: '/Admin/ProductItems/delete/:id',
    //     component: ModuleQuanLy,
    //     name: 'AdminDeleteAccount',
    //     props: route => ({ id: route.params.id, action: 'delete', TableName: 'ProductItems',listDashBoard:listDashBoard })
    // }
    // ,{
    //     path: '/Admin/PaymentMethods',
    //     component: QuanLyPhuongThucThanhToan,
    //     name: 'QuanLyTaiKhoan',
    //     props: {id:"-1", TableName: 'PaymentMethods' }
    // },
    // {
    //     path: '/Admin/PaymentMethods/view/:id',
    //     component: QuanLyPhuongThucThanhToan,
    //     name: 'AdminViewAccount',
    //     props: route => ({ id: route.params.id, action: 'view', TableName: 'PaymentMethods' })
    // },
    // {
    //     path: '/Admin/PaymentMethods/update/:id',
    //     component: QuanLyPhuongThucThanhToan,
    //     name: 'AdminUpdateAccount',
    //     props: route => ({ id: route.params.id, action: 'update', TableName: 'PaymentMethods' })
    // },
    // {
    //     path: '/Admin/PaymentMethods/delete/:id',
    //     component: QuanLyPhuongThucThanhToan,
    //     name: 'AdminDeleteAccount',
    //     props: route => ({ id: route.params.id, action: 'delete', TableName: 'PaymentMethods' })
    // }
    // ,{
    //     path: '/Admin/ProductImages',
    //     component: QuanLyHinhAnhSanPham,
    //     name: 'QuanLyTaiKhoan',
    //     props: {id:"-1", TableName: 'ProductImages' }
    // },
    // {
    //     path: '/Admin/ProductImages/view/:id',
    //     component: QuanLyHinhAnhSanPham,
    //     name: 'AdminViewAccount',
    //     props: route => ({ id: route.params.id, action: 'view', TableName: 'ProductImages' })
    // },
    // {
    //     path: '/Admin/ProductImages/update/:id',
    //     component: QuanLyHinhAnhSanPham,
    //     name: 'AdminUpdateAccount',
    //     props: route => ({ id: route.params.id, action: 'update', TableName: 'ProductImages' })
    // },
    // {
    //     path: '/Admin/ProductImages/delete/:id',
    //     component: QuanLyHinhAnhSanPham,
    //     name: 'AdminDeleteAccount',
    //     props: route => ({ id: route.params.id, action: 'delete', TableName: 'ProductImages' })
    // }
]
