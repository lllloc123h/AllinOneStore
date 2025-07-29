<template>
  <div class="container">
    <h2>Danh sách đơn hàng</h2>

    <div v-if="orders.length === 0" class="text-muted text-center py-4">
      Bạn chưa có đơn hàng nào.
    </div>

    <div v-for="(order, index) in orders" :key="index" class="section">
      <!-- Thông tin đơn -->
      <h3>Thông tin đơn hàng</h3>
      <p><strong>Mã vận đơn:</strong> {{ order.maVanDon }}</p>
      <p><strong>Ngày đặt:</strong> {{ formatDate(order.ngayDat) }}</p>
      <p><strong>Trạng thái:</strong> {{ order.trangThai }}</p>

      <!-- Sản phẩm -->
      <div class="products">
        <h3>Sản phẩm</h3>
        <table>
          <thead>
            <tr>
              <th>Ảnh</th>
              <th>Tên</th>
              <th>Số lượng</th>
              <th>Đơn giá</th>
              <th>Tổng</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(sp, i) in order.sanPham" :key="i">
              <td><img :src="sp.anh" /></td>
              <td>{{ sp.ten }}</td>
              <td>{{ sp.soLuong }}</td>
              <td>{{ formatMoney(sp.gia) }}</td>
              <td>{{ formatMoney(sp.gia * sp.soLuong) }}</td>
            </tr>
          </tbody>
        </table>
      </div>

      <div class="text-end mt-3">
        <strong>Tổng tiền: {{ formatMoney(order.tongTien) }}</strong>
      </div>

      <div class="actions mt-4">
        <button @click="goToOrder(order.id)">Xem chi tiết</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import api from '../../Configs/api'

const orders = ref([])
const router = useRouter()

// Gọi API lấy danh sách đơn hàng người dùng
const loadOrders = async () => {
  try {
    const res = await api.get('/user/Orders')  // res chứ không phải response
    console.log('Orders response:', res.data)

    // Nếu backend trả về dạng phân trang (Page), dùng res.data.content
    const data = Array.isArray(res.data) ? res.data : (res.data.content || [])

    orders.value = data.map(order => ({
    id: order.id,
    maDon: order.orderCode,
    ngayDat: order.createdAt,
    trangThai: order.shippingStatus,
    maVanDon: order.orderCode || 'Đang cập nhật',
    tongTien: order.finalTotal,
    sanPham: (order.products || []).map(i => ({
      anh: i.main_image_url || 'no-image.png',
      ten: i.name,
      soLuong: i.qty,
      gia: i.price
    }))
  }))

  } catch (err) {
    console.error('Lỗi khi tải đơn hàng:', err)
  }
}

const goToOrder = (id) => {
  router.push({ name: 'OrderStatus', params: { id } })
}

const formatDate = (d) => new Date(d).toLocaleDateString('vi-VN')
const formatMoney = (v) => Number(v).toLocaleString('vi-VN') + 'đ'
const goToDetail = (id) => router.push(`/orders/${id}`)

onMounted(loadOrders)
</script>

<style scoped>
.container {
  max-width: 1000px;
  margin: 40px auto;
  font-family: 'Quicksand', sans-serif;
}

h2 {
  text-align: center;
  font-weight: 700;
  color: #5d4a3e;
  margin-bottom: 30px;
}

.section {
  border: 1px solid #e0e0e0;
  border-radius: 12px;
  padding: 25px;
  margin-bottom: 30px;
  background-color: #fff;
  box-shadow: 0 3px 8px rgba(0, 0, 0, 0.05);
}

h3 {
  color: #6a5647;
  margin-bottom: 10px;
  font-weight: 600;
}

p strong {
  color: #333;
}

.products img {
  width: 60px;
  height: 60px;
  object-fit: cover;
  border-radius: 6px;
  border: 1px solid #ccc;
}
.products table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 10px;
}
.products th,
.products td {
  border: 1px solid #e0e0e0;
  padding: 10px;
  text-align: center;
  font-size: 0.95rem;
}

.actions {
  display: flex;
  justify-content: flex-end;
}
.actions button {
  background-color: #b59b8a;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 25px;
  cursor: pointer;
  font-weight: 600;
  transition: all 0.3s ease;
}
.actions button:hover {
  background-color: #a08976;
}
</style>
