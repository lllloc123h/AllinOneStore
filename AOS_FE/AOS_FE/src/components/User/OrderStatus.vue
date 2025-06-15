<template>
  <div v-if="order" class="container">
    <h2>Trạng thái đơn hàng</h2>

    <div class="section">
      <h3>Thông tin đơn hàng</h3>
      <p><strong>Mã đơn:</strong> {{ order.maDon }}</p>
      <p><strong>Ngày đặt:</strong> {{ formatDate(order.ngayDat) }}</p>
      <p><strong>Khách hàng:</strong> {{ order.khachHang.ten }}</p>
    </div>

    <div class="section order-status">
      <div v-for="(step, idx) in steps" :key="idx"
           :class="['step', { done: idx < statusIndex, active: idx === statusIndex }]">
        <div class="circle"><i :class="step.icon"></i></div>
        <div>{{ step.label }}</div>
      </div>
    </div>

    <div class="section products">
      <h3>Danh sách sản phẩm</h3>
      <table>
        <thead><tr><th>Ảnh</th><th>Tên</th><th>Số lượng</th><th>Đơn giá</th><th>Tổng</th></tr></thead>
        <tbody>
          <tr v-for="(sp, i) in order.sanPham" :key="i">
            <td><img :src="sp.anh"/></td>
            <td>{{ sp.ten }}</td>
            <td>{{ sp.soLuong }}</td>
            <td>{{ formatMoney(sp.gia) }}</td>
            <td>{{ formatMoney(sp.gia * sp.soLuong) }}</td>
          </tr>
        </tbody>
      </table>
    </div>

    <div class="section">
      <h3>Thông tin giao hàng</h3>
      <p><strong>Người nhận:</strong> {{ order.khachHang.ten }}</p>
      <p><strong>Địa chỉ:</strong> {{ order.khachHang.diaChi }}</p>
      <p><strong>Điện thoại:</strong> {{ order.khachHang.sdt }}</p>
      <p><strong>Đơn vị vận chuyển:</strong> {{ order.vanChuyen.ten }}</p>
      <p><strong>Mã vận đơn:</strong> {{ order.vanChuyen.maVanDon }}</p>
    </div>

    <div class="section">
      <h3>Thông tin thanh toán</h3>
      <p><strong>PT:</strong> {{ order.thanhToan.phuongThuc }}</p>
      <p><strong>TT:</strong> {{ order.thanhToan.trangThai }}</p>
      <p><strong>Tổng:</strong> {{ formatMoney(order.tongTien) }}</p>
    </div>

    <div class="section" v-if="order.ghiChu">
      <h3>Ghi chú</h3>
      <textarea readonly class="note">{{ order.ghiChu }}</textarea>
    </div>

    <div class="section">
      <h3>Lịch sử xử lý</h3>
      <table class="history-table">
        <thead><tr><th>Thời gian</th><th>Nội dung</th></tr></thead>
        <tbody>
          <tr v-for="(log,i) in order.lichSu" :key="i">
            <td>{{ formatDateTime(log.thoiGian) }}</td>
            <td>{{ log.noiDung }}</td>
          </tr>
        </tbody>
      </table>
    </div>

    <div class="actions">
      <button class="cancel">Hủy đơn</button>
      <button class="review">Đánh giá</button>
      <button>Mua lại</button>
      <button>In hóa đơn</button>
    </div>
  </div>
  <div v-else class="container"><p>Đang tải...</p></div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import fakeOrder from '../../assets/fakeOrder.json'


const order = ref(null)
const statusIndex = ref(0)
const steps = [
  { label: 'Đã đặt', icon: 'fa-solid fa-receipt' },
  { label: 'Đang xử lý', icon: 'fas fa-cogs' },
  { label: 'Đang giao', icon: 'fas fa-truck' },
  { label: 'Hoàn tất', icon: 'fas fa-box-open' }
]
const statusMap = { 'Đã đặt': 0, 'Đang xử lý': 1, 'Đang giao': 2, 'Hoàn tất': 3 }

onMounted(async () => {
  try {
    // Tạm dùng fakeOrder thay vì gọi API
    // const { data } = await axios.get('/api/orders/ALLINONE123')
    // order.value = data
    // statusIndex.value = statusMap[data.trangThai] ?? 0

    order.value = fakeOrder
    statusIndex.value = statusMap[fakeOrder.trangThai] ?? 0
  } catch (e) {
    console.error('Lỗi tải:', e)
  }
})

const formatDate = d => new Date(d).toLocaleDateString('vi-VN')
const formatDateTime = d => new Date(d).toLocaleString('vi-VN')
const formatMoney = v => Number(v).toLocaleString('vi-VN') + 'đ'
</script>




<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Quicksand:wght@300..700&display=swap');

body {
  font-family: 'Quicksand', sans-serif;
  margin: 0;
  background-color: #f8f6f4; 
  color: #333;
}

.container {
  max-width: 1000px;
  margin: 40px auto;
  background-color: white;
  padding: 30px;
  border-radius: 15px; 
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.08); 
  border: none; 
}

.section {
  margin-bottom: 30px; 
  padding: 20px;
  border: 1px solid #e0e0e0; 
  border-radius: 12px;
  background-color: #ffffff; 
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.03); 
}

h2 {
  color: #5d4a3e; 
  margin-bottom: 25px; 
  text-align: center;
  font-weight: 700;
}

h3 {
  color: #6a5647;
  margin-bottom: 15px;
  font-weight: 600;
}

p {
  line-height: 1.6;
  color: #4a4a4a;
}

p strong {
  color: #333;
}

.order-status {
  display: flex;
  justify-content: space-between;
  position: relative;
  margin: 30px 0;
  padding: 30px 20px;
  background-color: #f9f8f7;
  border: 1px solid #eee;
}

.order-status::before {
  content: "";
  position: absolute;
  top: 50%;
  left: 0;
  height: 3px;
  width: 100%;
  background: #e7ded9;
  z-index: 0;
}

.step {
  position: relative;
  z-index: 1;
  text-align: center;
  flex: 1;
  color: #888;
  font-weight: 500;
}

.step .circle {
  width: 35px;
  height: 35px;
  border-radius: 50%;
  background: #d4c8c0;
  margin: 0 auto 10px;
  color: white;
  line-height: 35px;
  font-size: 16px;
  font-weight: bold;
  display: flex;
  justify-content: center;
  align-items: center;
}

.step .circle i {
  font-family: "Font Awesome 6 Free";
  font-weight: 900;
  font-size: 16px;
}

.step.active {
  color: #b59b8a;
}

.step.active .circle {
  background: #b59b8a;
}

.step.done {
  color: #7b6d61;
}

.step.done .circle {
  background: #7b6d61;
}

table,
.history-table {
  width: 100%;
  border-collapse: separate;
  border-spacing: 0;
  margin-top: 15px;
  background-color: white;
  border-radius: 10px; 
  overflow: hidden; 
}

th,
td,
.history-table th,
.history-table td {
  border: 1px solid #f0f0f0;
  padding: 12px;
  text-align: center;
  font-size: 0.95rem;
  color: #4a4a4a;
}

th,
.history-table th {
  background-color: #fdfaf8;
  color: #6a5647;
  font-weight: 600;
  border-bottom: 2px solid #e7ded9;
}

table thead tr:first-child th:first-child {
  border-top-left-radius: 10px;
}
table thead tr:first-child th:last-child {
  border-top-right-radius: 10px;
}

table tbody tr:last-child td:first-child {
  border-bottom-left-radius: 10px;
}
table tbody tr:last-child td:last-child {
  border-bottom-right-radius: 10px;
}

.products img {
  width: 60px; 
  height: 60px;
  object-fit: cover;
  border-radius: 8px; 
  border: 1px solid #e0e0e0;
}

.actions {
  display: flex;
  gap: 15px; 
  flex-wrap: wrap;
  justify-content: center;
  margin-top: 40px;
}

.actions button {
  background-color: #b59b8a; 
  color: white;
  border: none;
  padding: 12px 25px; 
  border-radius: 25px; 
  cursor: pointer;
  font-size: 1rem;
  font-weight: 600;
  transition: background-color 0.3s ease, transform 0.2s ease;
  box-shadow: 0 3px 8px rgba(0, 0, 0, 0.1);
}

.actions button:hover {
  background-color: #a08c7e;
  transform: translateY(-2px); 
}

.actions button.cancel {
  background-color: #e28e8e; 
}

.actions button.cancel:hover {
  background-color: #d17b7b;
}

.actions button.review {
  background-color: #f7d6a5; 
  color: #8a6a4d; 
}

.actions button.review:hover {
  background-color: #f5c790;
}

textarea.note {
  width: 100%;
  min-height: 100px;
  padding: 15px;
  border: 1px solid #e0e0e0;
  border-radius: 12px;
  resize: vertical;
  font-size: 1rem;
  font-family: 'Quicksand', sans-serif;
  background-color: whitewhite;
  color: #333;
  box-sizing: border-box;
}

@media (max-width: 600px) {
  .order-status {
    flex-direction: column;
    gap: 20px;
    padding: 20px 10px;
  }

  .order-status::before {
    display: none;
  }

  .actions {
    flex-direction: column;
    align-items: center;
  }

  th,
  td {
    padding: 8px;
    font-size: 0.85rem;
  }

  .products img {
    width: 40px;
    height: 40px;
  }

  .container {
    margin: 20px auto;
    padding: 20px;
  }

  .order-status .step .circle i {
  color: white !important;
}
}
</style>