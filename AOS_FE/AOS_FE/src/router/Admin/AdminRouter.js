import QuanLyTaiKhoan from '../../components/Admin/QuanLyTaiKhoan.vue'


export default [
  {
    path: '/Admin/QuanLyTaiKhoan',
    component: QuanLyTaiKhoan,
    name: 'QuanLyTaiKhoan',
    props: { TableName: 'Accounts' }
  },
  {
    path: '/Admin/QuanLyTaiKhoan/view/:id',
    component: QuanLyTaiKhoan,
    name: 'AdminViewAccount',
    props: route => ({ id: route.params.id, action: 'view', TableName: 'Accounts' })
  },
  {
    path: '/Admin/QuanLyTaiKhoan/update/:id',
    component: QuanLyTaiKhoan,
    name: 'AdminUpdateAccount',
    props: route => ({ id: route.params.id, action: 'update', TableName: 'Accounts' })
  },
  {
    path: '/Admin/QuanLyTaiKhoan/delete/:id',
    component: QuanLyTaiKhoan,
    name: 'AdminDeleteAccount',
    props: route => ({ id: route.params.id, action: 'delete', TableName: 'Accounts' })
  }
]
