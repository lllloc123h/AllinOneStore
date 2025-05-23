import QuanLyTaiKhoan from '../../components/Admin/QuanLyTaiKhoan.vue'


export default [
    {
        path: '/Admin/Accounts',
        component: QuanLyTaiKhoan,
        name: 'QuanLyTaiKhoan',
        props: {id:"0", TableName: 'Accounts' }
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
]
