<template>
  <!-- Header Section -->
  <div class="page-header">
    <div class="header-content">
      <h1 class="page-title">Đơn hàng của tôi</h1>
      <p class="page-subtitle">THEO DÕI TRẠNG THÁI ĐƠN HÀNG CỦA BẠN</p>
    </div>
  </div>

  <!-- Main Container -->
  <div class="main-container">
    <!-- Empty State -->
    <div v-if="orders.length === 0" class="empty-state">
      <div class="empty-icon">
        <i class="bi bi-bag-x"></i>
      </div>
      <h3>Chưa có đơn hàng nào</h3>
      <p>Bạn chưa có đơn hàng nào. Hãy bắt đầu mua sắm ngay!</p>
      <button class="btn-shop-now" @click="$router.push('/products')">
        <i class="bi bi-bag-plus me-2"></i>Mua sắm ngay
      </button>
    </div>

    <!-- Orders List -->
    <div v-else class="orders-list">
      <div v-for="(order, index) in orders" :key="index" class="order-card">
        <!-- Order Header -->
        <div class="order-header">
          <div class="order-info">
            <h3 class="order-code">
              <i class="bi bi-receipt me-2"></i>{{ order.maVanDon }}
            </h3>
            <div class="order-meta">
              <span class="order-date">
                <i class="bi bi-calendar3 me-1"></i>{{ formatDate(order.ngayDat) }}
              </span>
              <span class="order-status" :class="getStatusClass(order.trangThai)">
                <i class="bi bi-circle-fill me-1"></i>{{ order.trangThai }}
              </span>
            </div>
          </div>
          <div class="order-total">
            <span class="total-label">Tổng tiền</span>
            <span class="total-amount">{{ formatMoney(order.tongTien) }}</span>
          </div>
        </div>

        <!-- Products Section -->
        <div class="products-section">
          <h4 class="section-title">
            <i class="bi bi-box me-2"></i>Sản phẩm ({{ order.sanPham.length }})
          </h4>
          <div class="products-grid">
            <div v-for="(sp, i) in order.sanPham" :key="i" class="product-item">
              <div class="product-image">
                <img :src="sp.anh" :alt="sp.ten" />
              </div>
              <div class="product-info">
                <h5 class="product-name">{{ sp.ten }}</h5>
                <div class="product-details">
                  <span class="quantity">SL: {{ sp.soLuong }}</span>
                  <span class="price">{{ formatMoney(sp.gia) }}</span>
                </div>
                <div class="product-total">
                  Thành tiền: <strong>{{ formatMoney(sp.gia * sp.soLuong) }}</strong>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- Order Actions -->
        <div class="order-actions">
          <button class="btn-detail" @click="goToOrder(order.id)">
            <i class="bi bi-eye me-2"></i>Xem chi tiết
          </button>
          <button class="btn-reorder" @click="reorder(order)">
            <i class="bi bi-arrow-repeat me-2"></i>Đặt lại
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import api from "../../Configs/api";

const orders = ref([]);
const router = useRouter();

// Gọi API lấy danh sách đơn hàng người dùng
const loadOrders = async () => {
  try {
    const res = await api.get("/user/Orders"); // res chứ không phải response
    console.log("Orders response:", res.data);

    // Nếu backend trả về dạng phân trang (Page), dùng res.data.content
    const data = Array.isArray(res.data) ? res.data : res.data.content || [];

    orders.value = data.map((order) => ({
      id: order.id,
      maDon: order.orderCode,
      ngayDat: order.createdAt,
      trangThai: order.shippingStatus,
      maVanDon: order.orderCode || "Đang cập nhật",
      tongTien: order.finalTotal,
      sanPham: (order.products || []).map((i) => ({
        anh: i.main_image_url || "no-image.png",
        ten: i.name,
        soLuong: i.quantity,
        gia: i.price,
      })),
    }));
  } catch (err) {
    console.error("Lỗi khi tải đơn hàng:", err);
  }
};

const goToOrder = (id) => {
  router.push({ name: "OrderStatus", params: { id } });
};

const reorder = (order) => {
  // Logic để đặt lại đơn hàng
  console.log("Reorder:", order);
  // Có thể redirect đến trang cart với sản phẩm từ đơn hàng này
};

const getStatusClass = (status) => {
  const statusMap = {
    "Đang xử lý": "status-processing",
    "Đã xác nhận": "status-confirmed",
    "Đang giao": "status-shipping",
    "Đã giao": "status-delivered",
    "Đã hủy": "status-cancelled",
    "Hoàn trả": "status-returned",
  };
  return statusMap[status] || "status-default";
};

const formatDate = (d) => new Date(d).toLocaleDateString("vi-VN");
const formatMoney = (v) => Number(v).toLocaleString("vi-VN") + "đ";

onMounted(loadOrders);
</script>

<style scoped>
/* Page Header */
.page-header {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 3rem 1rem;
  text-align: center;
  margin-bottom: 2rem;
}

.header-content {
  max-width: 600px;
  margin: 0 auto;
}

.page-title {
  color: white;
  font-size: 2.5rem;
  font-weight: 700;
  margin-bottom: 0.5rem;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.3);
}

.page-subtitle {
  color: rgba(255, 255, 255, 0.9);
  font-size: 1.1rem;
  margin: 0;
  font-weight: 400;
}

/* Main Container */
.main-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 1rem;
}

/* Empty State */
.empty-state {
  text-align: center;
  padding: 4rem 2rem;
  background: white;
  border-radius: 20px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
}

.empty-icon {
  font-size: 4rem;
  color: #bdc3c7;
  margin-bottom: 1rem;
}

.empty-state h3 {
  color: #2c3e50;
  font-size: 1.5rem;
  margin-bottom: 0.5rem;
}

.empty-state p {
  color: #7f8c8d;
  margin-bottom: 2rem;
}

.btn-shop-now {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border: none;
  padding: 1rem 2rem;
  border-radius: 25px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
  display: inline-flex;
  align-items: center;
}

.btn-shop-now:hover {
  box-shadow: 0 8px 25px rgba(102, 126, 234, 0.3);
}

/* Orders List */
.orders-list {
  display: flex;
  flex-direction: column;
  gap: 2rem;
}

.order-card {
  background: white;
  border-radius: 20px;
  padding: 2rem;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
  border: 2px solid transparent;
}

/* Order Header */
.order-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 2rem;
  padding-bottom: 1rem;
  border-bottom: 2px solid #f8f9fa;
}

.order-info {
  flex: 1;
}

.order-code {
  color: #2c3e50;
  font-size: 1.3rem;
  font-weight: 700;
  margin: 0 0 0.5rem 0;
  display: flex;
  align-items: center;
}

.order-meta {
  display: flex;
  gap: 1rem;
  flex-wrap: wrap;
}

.order-date {
  color: #7f8c8d;
  font-size: 0.95rem;
  display: flex;
  align-items: center;
}

.order-status {
  padding: 0.4rem 1rem;
  border-radius: 20px;
  font-size: 0.85rem;
  font-weight: 600;
  display: flex;
  align-items: center;
}

.status-processing {
  background: #fff3cd;
  color: #856404;
}

.status-confirmed {
  background: #d4edda;
  color: #155724;
}

.status-shipping {
  background: #cce7ff;
  color: #004085;
}

.status-delivered {
  background: #d1ecf1;
  color: #0c5460;
}

.status-cancelled {
  background: #f8d7da;
  color: #721c24;
}

.status-returned {
  background: #e2e3e5;
  color: #383d41;
}

.status-default {
  background: #f8f9fa;
  color: #6c757d;
}

.order-total {
  text-align: right;
}

.total-label {
  display: block;
  color: #7f8c8d;
  font-size: 0.9rem;
  margin-bottom: 0.3rem;
}

.total-amount {
  display: block;
  color: #e74c3c;
  font-size: 1.5rem;
  font-weight: 700;
}

/* Products Section */
.products-section {
  margin-bottom: 2rem;
}

.section-title {
  color: #2c3e50;
  font-size: 1.1rem;
  font-weight: 600;
  margin-bottom: 1rem;
  display: flex;
  align-items: center;
}

.products-grid {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.product-item {
  display: flex;
  gap: 1rem;
  padding: 1rem;
  background: #f8f9fa;
  border-radius: 15px;
}

.product-image {
  flex-shrink: 0;
}

.product-image img {
  width: 80px;
  height: 100px;
  object-fit: cover;
  border-radius: 10px;
  border: 2px solid white;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.product-info {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.product-name {
  color: #2c3e50;
  font-size: 1rem;
  font-weight: 600;
  margin: 0 0 0.5rem 0;
  line-height: 1.3;
}

.product-details {
  display: flex;
  gap: 1rem;
  margin-bottom: 0.5rem;
}

.quantity {
  background: #667eea;
  color: white;
  padding: 0.2rem 0.8rem;
  border-radius: 15px;
  font-size: 0.85rem;
  font-weight: 600;
}

.price {
  color: #7f8c8d;
  font-size: 0.9rem;
}

.product-total {
  color: #2c3e50;
  font-size: 0.95rem;
}

/* Order Actions */
.order-actions {
  display: flex;
  gap: 1rem;
  justify-content: flex-end;
  flex-wrap: wrap;
}

.btn-detail,
.btn-reorder {
  padding: 0.8rem 1.5rem;
  border: none;
  border-radius: 25px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  font-size: 0.9rem;
}

.btn-detail {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
}

.btn-detail:hover {
  box-shadow: 0 8px 25px rgba(102, 126, 234, 0.3);
}

.btn-reorder {
  background: #f8f9fa;
  color: #6c757d;
  border: 2px solid #e9ecef;
}

.btn-reorder:hover {
  background: #e9ecef;
  color: #495057;
  border-color: #667eea;
}

/* Responsive Design */
@media (max-width: 768px) {
  .page-header {
    padding: 2rem 1rem;
  }

  .page-title {
    font-size: 2rem;
  }

  .order-card {
    padding: 1.5rem;
  }

  .order-header {
    flex-direction: column;
    gap: 1rem;
    align-items: flex-start;
  }

  .order-total {
    text-align: left;
  }

  .order-meta {
    flex-direction: column;
    gap: 0.5rem;
  }

  .product-item {
    flex-direction: column;
    text-align: center;
  }

  .product-details {
    justify-content: center;
  }

  .order-actions {
    justify-content: center;
  }
}

@media (max-width: 480px) {
  .main-container {
    padding: 0 0.5rem;
  }

  .order-card {
    padding: 1rem;
    border-radius: 15px;
  }

  .product-image img {
    width: 60px;
    height: 75px;
  }

  .order-actions {
    flex-direction: column;
  }

  .btn-detail,
  .btn-reorder {
    justify-content: center;
  }
}
</style>
