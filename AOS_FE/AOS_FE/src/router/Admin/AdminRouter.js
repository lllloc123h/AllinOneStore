import QuanLyTaiKhoan from '../../components/Admin/QuanLyTaiKhoan.vue'
import QuanLyDonHang from '../../components/Admin/QuanLyDonHang.vue'
import QuanLyHinhAnhSanPham from '../../components/Admin/QuanLyHinhAnhSanPham.vue'
import QuanLyNhanVien from '../../components/Admin/QuanLyNhanVien.vue'
import QuanLyPhuongThucThanhToan from '../../components/Admin/QuanLyPhuongThucThanhToan.vue'
import QuanLySanPham from '../../components/Admin/QuanLySanPham.vue'


export default [
    {
        path: '/Admin/Accounts',
        component: QuanLyTaiKhoan,
        name: 'QuanLyTaiKhoan',
        props: {id:"-1", TableName: 'Accounts' }
    },
    {
        path: '/Admin/Accounts/view/:id',
        component: QuanLyTaiKhoan,
        name: 'AdminViewAccount',
        props: route => ({ id: route.params.id, action: 'view', TableName: 'Accounts' })
    },
    {
        path: '/Admin/Accounts/update/:id',
        component: QuanLyTaiKhoan,
        name: 'AdminUpdateAccount',
        props: route => ({ id: route.params.id, action: 'update', TableName: 'Accounts' })
    },
    {
        path: '/Admin/Accounts/delete/:id',
        component: QuanLyTaiKhoan,
        name: 'AdminDeleteAccount',
        props: route => ({ id: route.params.id, action: 'delete', TableName: 'Accounts' })
    }
    
    ,{
        path: '/Admin/ProductItems',
        component: QuanLySanPham,
        name: 'QuanLyTaiKhoan',
        props: {id:"-1", TableName: 'ProductItems' }
    },
    {
        path: '/Admin/ProductItems/view/:id',
        component: QuanLySanPham,
        name: 'AdminViewAccount',
        props: route => ({ id: route.params.id, action: 'view', TableName: 'ProductItems' })
    },
    {
        path: '/Admin/ProductItems/update/:id',
        component: QuanLySanPham,
        name: 'AdminUpdateAccount',
        props: route => ({ id: route.params.id, action: 'update', TableName: 'ProductItems' })
    },
    {
        path: '/Admin/ProductItems/delete/:id',
        component: QuanLySanPham,
        name: 'AdminDeleteAccount',
        props: route => ({ id: route.params.id, action: 'delete', TableName: 'ProductItems' })
    }
    ,{
        path: '/Admin/PaymentMethods',
        component: QuanLyPhuongThucThanhToan,
        name: 'QuanLyTaiKhoan',
        props: {id:"-1", TableName: 'PaymentMethods' }
    },
    {
        path: '/Admin/PaymentMethods/view/:id',
        component: QuanLyPhuongThucThanhToan,
        name: 'AdminViewAccount',
        props: route => ({ id: route.params.id, action: 'view', TableName: 'PaymentMethods' })
    },
    {
        path: '/Admin/PaymentMethods/update/:id',
        component: QuanLyPhuongThucThanhToan,
        name: 'AdminUpdateAccount',
        props: route => ({ id: route.params.id, action: 'update', TableName: 'PaymentMethods' })
    },
    {
        path: '/Admin/PaymentMethods/delete/:id',
        component: QuanLyPhuongThucThanhToan,
        name: 'AdminDeleteAccount',
        props: route => ({ id: route.params.id, action: 'delete', TableName: 'PaymentMethods' })
    }
    ,{
        path: '/Admin/Employees',
        component: QuanLyNhanVien,
        name: 'QuanLyTaiKhoan',
        props: {id:"-1", TableName: 'Employees' }
    },
    {
        path: '/Admin/Employees/view/:id',
        component: QuanLyNhanVien,
        name: 'AdminViewAccount',
        props: route => ({ id: route.params.id, action: 'view', TableName: 'Employees' })
    },
    {
        path: '/Admin/Employees/update/:id',
        component: QuanLyNhanVien,
        name: 'AdminUpdateAccount',
        props: route => ({ id: route.params.id, action: 'update', TableName: 'Employees' })
    },
    {
        path: '/Admin/Employees/delete/:id',
        component: QuanLyNhanVien,
        name: 'AdminDeleteAccount',
        props: route => ({ id: route.params.id, action: 'delete', TableName: 'Employees' })
    }
    ,{
        path: '/Admin/ProductImages',
        component: QuanLyHinhAnhSanPham,
        name: 'QuanLyTaiKhoan',
        props: {id:"-1", TableName: 'ProductImages' }
    },
    {
        path: '/Admin/ProductImages/view/:id',
        component: QuanLyHinhAnhSanPham,
        name: 'AdminViewAccount',
        props: route => ({ id: route.params.id, action: 'view', TableName: 'ProductImages' })
    },
    {
        path: '/Admin/ProductImages/update/:id',
        component: QuanLyHinhAnhSanPham,
        name: 'AdminUpdateAccount',
        props: route => ({ id: route.params.id, action: 'update', TableName: 'ProductImages' })
    },
    {
        path: '/Admin/ProductImages/delete/:id',
        component: QuanLyHinhAnhSanPham,
        name: 'AdminDeleteAccount',
        props: route => ({ id: route.params.id, action: 'delete', TableName: 'ProductImages' })
    }
]
