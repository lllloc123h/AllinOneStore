<template>
  <!-- Header Section -->
  <div class="page-header">
    <div class="header-content">
      <h1 class="page-title">Quản lý đơn hàng</h1>
      <p class="page-subtitle">THEO DÕI VÀ QUẢN LÝ TẤT CẢ ĐƠN HÀNG CỦA BẠN</p>
    </div>
  </div>

  <!-- Main Container -->
  <div class="main-container">
    <!-- Tabs Navigation -->
    <div class="tabs-container">
      <div class="tabs-nav">
        <button
          v-for="tab in tabs"
          :key="tab"
          class="tab-button"
          :class="{ active: currentTab === tab }"
          @click="currentTab = tab"
        >
          <i class="bi" :class="getTabIcon(tab)"></i>
          {{ getTabLabel(tab) }}
          <span v-if="ordersData[tab]" class="tab-count">{{
            ordersData[tab].length
          }}</span>
        </button>
      </div>
    </div>

    <!-- Orders Content -->
    <div class="orders-content">
      <!-- Loading State -->
      <div v-if="loading" class="loading-state">
        <div class="spinner">
          <i class="bi bi-arrow-clockwise"></i>
        </div>
        <p>Đang tải đơn hàng...</p>
      </div>

      <!-- Empty State -->
      <div
        v-else-if="!ordersData[currentTab] || ordersData[currentTab].length === 0"
        class="empty-state"
      >
        <div class="empty-icon">
          <i class="bi bi-inbox"></i>
        </div>
        <h3>Không có đơn hàng</h3>
        <p>Chưa có đơn hàng nào trong danh mục này.</p>
      </div>

      <!-- Orders List -->
      <div v-else class="orders-list">
        <div v-for="order in ordersData[currentTab]" :key="order.id" class="order-card">
          <!-- Order Header -->
          <div class="order-header">
            <div class="order-info">
              <h3 class="order-title">
                <i class="bi bi-receipt me-2"></i>Đơn hàng #{{ order.id }}
              </h3>
              <div class="order-meta">
                <span class="order-code">
                  <i class="bi bi-hash me-1"></i>{{ order.orderCode || "N/A" }}
                </span>
                <span class="order-date">
                  <i class="bi bi-calendar3 me-1"></i>{{ formatDate(order.createdAt) }}
                </span>
              </div>
            </div>
            <div class="order-actions">
              <button
                v-if="order.paymentStatus === 'Chưa thanh toán'"
                class="btn-pay"
                @click="pay(order)"
              >
                <i class="bi bi-credit-card me-2"></i>Thanh toán
              </button>
            </div>
          </div>

          <!-- Order Status -->
          <div class="status-section">
            <div class="status-item">
              <span class="status-label">Thanh toán:</span>
              <span
                class="status-badge"
                :class="getStatusBadgeClass(order.paymentStatus)"
              >
                <i class="bi bi-circle-fill me-1"></i>{{ order.paymentStatus }}
              </span>
            </div>
            <div class="status-item">
              <span class="status-label">Vận chuyển:</span>
              <span
                class="status-badge"
                :class="getShippingStatusClass(order.shippingStatus)"
              >
                <i class="bi bi-circle-fill me-1"></i>{{ order.shippingStatus }}
              </span>
            </div>
          </div>

          <!-- Order Details -->
          <div class="order-details">
            <h4 class="details-title">
              <i class="bi bi-info-square me-2"></i>Thông tin đơn hàng
            </h4>
            <div class="details-cards">
              <div class="detail-card highlight">
                <div class="detail-icon total">
                  <i class="bi bi-currency-dollar"></i>
                </div>
                <div class="detail-content">
                  <span class="detail-label">Tổng tiền</span>
                  <span class="detail-value total-amount"
                    >{{ order.finalTotal.toLocaleString() }} VND</span
                  >
                </div>
              </div>

              <div class="detail-card">
                <div class="detail-icon payment">
                  <i class="bi bi-credit-card"></i>
                </div>
                <div class="detail-content">
                  <span class="detail-label">Phương thức thanh toán</span>
                  <span class="detail-value">{{ order.paymentMethodName }}</span>
                </div>
              </div>

              <div class="detail-card">
                <div class="detail-icon shipping">
                  <i class="bi bi-truck"></i>
                </div>
                <div class="detail-content">
                  <span class="detail-label">Phương thức vận chuyển</span>
                  <span class="detail-value">{{ order.shippingMethodName }}</span>
                </div>
              </div>

              <div class="detail-card">
                <div class="detail-icon info">
                  <i class="bi bi-person-fill"></i>
                </div>
                <div class="detail-content">
                  <span class="detail-label">Thông tin người nhận</span>
                  <span class="detail-value">{{
                    getCustomerName(order.orderInfor)
                  }}</span>
                </div>
              </div>

              <div class="detail-card">
                <div class="detail-icon contact">
                  <i class="bi bi-telephone-fill"></i>
                </div>
                <div class="detail-content">
                  <span class="detail-label">Số điện thoại</span>
                  <span class="detail-value">{{ getPhoneNumber(order.orderInfor) }}</span>
                </div>
              </div>

              <div class="detail-card full-width">
                <div class="detail-icon address">
                  <i class="bi bi-geo-alt-fill"></i>
                </div>
                <div class="detail-content">
                  <span class="detail-label">Địa chỉ giao hàng</span>
                  <span class="detail-value address-text">{{
                    getAddress(order.orderInfor)
                  }}</span>
                </div>
              </div>

              <div v-if="order.note" class="detail-card full-width">
                <div class="detail-icon note">
                  <i class="bi bi-chat-text"></i>
                </div>
                <div class="detail-content">
                  <span class="detail-label">Ghi chú đặc biệt</span>
                  <span class="detail-value note-text">{{ order.note }}</span>
                </div>
              </div>
            </div>
          </div>

          <!-- Order Footer -->
          <div class="order-footer">
            <span class="footer-text">Cập nhật: {{ formatDate(order.updatedAt) }}</span>
          </div>
        </div>
      </div>
    </div>

    <!-- Payment Popup -->
    <div v-if="payURL && showPopUp" class="modal-overlay" @click.self="closePopup">
      <div class="payment-modal">
        <div class="modal-header">
          <h3><i class="bi bi-credit-card me-2"></i>Thanh toán MoMo</h3>
          <button class="close-btn" @click="closePopup">×</button>
        </div>
        <div class="modal-body">
          <p>Nhấn vào nút bên dưới để mở trang thanh toán MoMo.</p>
          <div class="payment-actions">
            <a :href="payURL" target="_blank" class="btn-payment">
              <i class="bi bi-credit-card me-2"></i>Mở trang thanh toán
            </a>
            <button class="btn-cancel" @click="closePopup">
              <i class="bi bi-x-circle me-2"></i>Đóng
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, watch, onMounted } from "vue";
import api from "../../Configs/api";
const ordersList = ref();

// Helper function to format dates
const formatDate = (dateString) => {
  if (!dateString) return "N/A";
  const date = new Date(dateString);

  const months = [
    "Tháng 1",
    "Tháng 2",
    "Tháng 3",
    "Tháng 4",
    "Tháng 5",
    "Tháng 6",
    "Tháng 7",
    "Tháng 8",
    "Tháng 9",
    "Tháng 10",
    "Tháng 11",
    "Tháng 12",
  ];

  const day = date.getDate();
  const month = months[date.getMonth()];
  const year = date.getFullYear();
  const hours = date.getHours().toString().padStart(2, "0");
  const minutes = date.getMinutes().toString().padStart(2, "0");

  return `${day} ${month} ${year}, ${hours}:${minutes}`;
};

const showPopUp = ref(false);
const payURL = ref("");

function closePopup() {
  showPopUp.value = false;
  payURL.value = "";
}
async function pay(orderID) {
  try {
    console.log("Order ID:", orderID);
    console.log("Order ID:", orderID.id);
    const payLoad = { orderId: orderID.id, finalToTal: orderID.finalTotal };

    console.log("Order ID:", payLoad);
    console.log("Order ID:", orderID.id);
    const response = await api.post(`/e-wallet/orderpay`, payLoad);
    console.log("Payment URL:", response.data);
    payURL.value = response.data.payUrl;
    showPopUp.value = true;
  } catch (err) {
    console.error();
  }
}

const getStatusBadgeClass = (status) => {
  switch (status) {
    case "Chưa thanh toán":
      return "status-pending";
    case "Đã thanh toán":
      return "status-paid";
    default:
      return "status-default";
  }
};

const getShippingStatusClass = (status) => {
  switch (status) {
    case "Chờ xác nhận":
      return "status-waiting";
    case "Đang xử lý":
      return "status-processing";
    case "Đang giao hàng":
      return "status-shipping";
    case "Đã giao hàng":
      return "status-delivered";
    case "Đã hủy":
      return "status-cancelled";
    default:
      return "status-default";
  }
};

const getTabIcon = (tab) => {
  switch (tab) {
    case "Payment Pending":
      return "bi-credit-card";
    case "Shipping Pending":
      return "bi-truck";
    case "Sent":
      return "bi-check-circle";
    default:
      return "bi-list";
  }
};

const getTabLabel = (tab) => {
  switch (tab) {
    case "Payment Pending":
      return "Chờ thanh toán";
    case "Shipping Pending":
      return "Chờ vận chuyển";
    case "Sent":
      return "Đã gửi";
    default:
      return tab;
  }
};

// Parse customer info from orderInfor string
const getCustomerName = (orderInfor) => {
  if (!orderInfor) return "N/A";
  // Format: "Tran Huu Loc - 0969214372 - 95 ap 1, Phường Trúc Bạch, Quận Ba Đình, Hà Nội"
  const parts = orderInfor.split(" - ");
  return parts[0] || "N/A";
};

const getPhoneNumber = (orderInfor) => {
  if (!orderInfor) return "N/A";
  const parts = orderInfor.split(" - ");
  return parts[1] || "N/A";
};

const getAddress = (orderInfor) => {
  if (!orderInfor) return "N/A";
  const parts = orderInfor.split(" - ");
  return parts.slice(2).join(" - ") || "N/A";
};

const tabs = ["Payment Pending", "Shipping Pending", "Sent"];
const currentTab = ref(tabs[0]);
const ordersData = ref({}); // To store fetched orders for each tab

const tabKeys = {
  "Payment Pending": "Chưa thanh toán",
  "Shipping Pending": "pending_shipping",
  Sent: "sent",
};
const loading = ref(false);

async function fetchDataForTab(tabName) {
  const keyToSend = tabKeys[tabName];
  if (!keyToSend) {
    ordersData.value[tabName] = [];
    return;
  }

  loading.value = true;
  try {
    const response = await api.get(`/user/Orders/paypending?KEY=${keyToSend}`);
    ordersData.value[tabName] = response.data;
  } catch (err) {
    console.error(`Error fetching data for ${tabName}:`, err);
    ordersData.value[tabName] = [];
  } finally {
    loading.value = false;
  }
}

watch(
  currentTab,
  (newTab) => {
    if (!ordersData.value[newTab]) {
      fetchDataForTab(newTab);
    }
  },
  { immediate: true }
);
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

/* Tabs */
.tabs-container {
  margin-bottom: 2rem;
}

.tabs-nav {
  display: flex;
  gap: 0.5rem;
  background: white;
  padding: 0.5rem;
  border-radius: 15px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
  overflow-x: auto;
}

.tab-button {
  background: transparent;
  border: none;
  padding: 1rem 1.5rem;
  border-radius: 10px;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  gap: 0.5rem;
  font-weight: 600;
  color: #7f8c8d;
  white-space: nowrap;
  position: relative;
}

.tab-button:hover {
  background: #f8f9fa;
  color: #667eea;
}

.tab-button.active {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  box-shadow: 0 4px 15px rgba(102, 126, 234, 0.3);
}

.tab-count {
  background: rgba(255, 255, 255, 0.2);
  border-radius: 12px;
  padding: 0.2rem 0.6rem;
  font-size: 0.8rem;
  font-weight: 700;
  min-width: 20px;
  text-align: center;
}

.tab-button.active .tab-count {
  background: rgba(255, 255, 255, 0.3);
}

/* Loading State */
.loading-state {
  text-align: center;
  padding: 4rem 2rem;
  background: white;
  border-radius: 20px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
}

.spinner {
  font-size: 2rem;
  color: #667eea;
  margin-bottom: 1rem;
  animation: spin 1s linear infinite;
}

@keyframes spin {
  from {
    transform: rotate(0deg);
  }
  to {
    transform: rotate(360deg);
  }
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
  margin: 0;
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
  margin-bottom: 1.5rem;
  padding-bottom: 1rem;
  border-bottom: 2px solid #f8f9fa;
}

.order-info {
  flex: 1;
}

.order-title {
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

.order-code,
.order-date {
  color: #7f8c8d;
  font-size: 0.9rem;
  display: flex;
  align-items: center;
}

.order-actions {
  display: flex;
  gap: 1rem;
}

.btn-pay {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border: none;
  padding: 0.8rem 1.5rem;
  border-radius: 25px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
}

.btn-pay:hover {
  box-shadow: 0 8px 25px rgba(102, 126, 234, 0.3);
}

/* Status Section */
.status-section {
  display: flex;
  gap: 2rem;
  margin-bottom: 1.5rem;
  flex-wrap: wrap;
}

.status-item {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.status-label {
  color: #7f8c8d;
  font-weight: 600;
  font-size: 0.9rem;
}

.status-badge {
  padding: 0.4rem 1rem;
  border-radius: 20px;
  font-size: 0.85rem;
  font-weight: 600;
  display: flex;
  align-items: center;
}

.status-pending {
  background: #fff3cd;
  color: #856404;
}

.status-paid {
  background: #d4edda;
  color: #155724;
}

.status-waiting {
  background: #cce7ff;
  color: #004085;
}

.status-processing {
  background: #e7f3ff;
  color: #0066cc;
}

.status-shipping {
  background: #fff2e6;
  color: #cc7a00;
}

.status-delivered {
  background: #d4edda;
  color: #155724;
}

.status-cancelled {
  background: #f8d7da;
  color: #721c24;
}

.status-default {
  background: #f8f9fa;
  color: #6c757d;
}

/* Order Details */
.order-details {
  margin-bottom: 1.5rem;
}

.details-title {
  color: #2c3e50;
  font-size: 1.1rem;
  font-weight: 600;
  margin-bottom: 1.5rem;
  display: flex;
  align-items: center;
  padding-bottom: 0.5rem;
  border-bottom: 2px solid #f8f9fa;
}

.details-cards {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 1rem;
}

.detail-card {
  background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
  border-radius: 15px;
  padding: 1.5rem;
  display: flex;
  align-items: center;
  gap: 1rem;
  border: 2px solid transparent;
  position: relative;
  overflow: hidden;
}

.detail-card.highlight {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  box-shadow: 0 8px 25px rgba(102, 126, 234, 0.3);
}

.detail-card.full-width {
  grid-column: 1 / -1;
}

.detail-icon {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.5rem;
  color: white;
  flex-shrink: 0;
}

.detail-icon.total {
  background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
}

.detail-icon.payment {
  background: linear-gradient(135deg, #a8edea 0%, #fed6e3 100%);
  color: #6610f2;
}

.detail-icon.shipping {
  background: linear-gradient(135deg, #ffecd2 0%, #fcb69f 100%);
  color: #d63384;
}

.detail-icon.info {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.detail-icon.contact {
  background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%);
  color: #28a745;
}

.detail-icon.address {
  background: linear-gradient(135deg, #fa709a 0%, #fee140 100%);
  color: #dc3545;
}

.detail-icon.note {
  background: linear-gradient(135deg, #fa709a 0%, #fee140 100%);
  color: #dc3545;
}

.detail-card.highlight .detail-icon {
  background: rgba(255, 255, 255, 0.2);
  color: white;
}

.detail-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 0.3rem;
}

.detail-label {
  color: #7f8c8d;
  font-weight: 600;
  font-size: 0.9rem;
  text-transform: uppercase;
  letter-spacing: 0.5px;
}

.detail-card.highlight .detail-label {
  color: rgba(255, 255, 255, 0.8);
}

.detail-value {
  color: #2c3e50;
  font-weight: 700;
  font-size: 1rem;
  line-height: 1.3;
}

.detail-card.highlight .detail-value {
  color: white;
}

.total-amount {
  color: #e74c3c;
  font-size: 1.5rem;
  font-weight: 800;
}

.detail-card.highlight .total-amount {
  color: #fff;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
}

.note-text {
  font-style: italic;
  color: #5a6c7d;
  font-weight: 500;
}

.address-text {
  line-height: 1.4;
  word-break: break-word;
}

/* Order Footer */
.order-footer {
  padding-top: 1rem;
  border-top: 1px solid #f8f9fa;
  text-align: right;
}

.footer-text {
  color: #7f8c8d;
  font-size: 0.85rem;
}

/* Payment Modal */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.6);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
  padding: 1rem;
}

.payment-modal {
  background: white;
  border-radius: 20px;
  max-width: 400px;
  width: 100%;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
  animation: modalSlideIn 0.3s ease;
}

@keyframes modalSlideIn {
  from {
    opacity: 0;
    transform: translateY(-30px) scale(0.95);
  }
  to {
    opacity: 1;
    transform: translateY(0) scale(1);
  }
}

.modal-header {
  padding: 1.5rem 2rem;
  border-bottom: 1px solid #e9ecef;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.modal-header h3 {
  margin: 0;
  font-size: 1.3rem;
  font-weight: 600;
  color: #2c3e50;
  display: flex;
  align-items: center;
}

.close-btn {
  background: none;
  border: none;
  font-size: 1.5rem;
  color: #7f8c8d;
  cursor: pointer;
  padding: 0.5rem;
  border-radius: 50%;
  transition: all 0.3s ease;
}

.close-btn:hover {
  background: #f8f9fa;
  color: #e74c3c;
}

.modal-body {
  padding: 2rem;
}

.modal-body p {
  color: #7f8c8d;
  margin-bottom: 2rem;
  text-align: center;
}

.payment-actions {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.btn-payment {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  text-decoration: none;
  padding: 1rem 2rem;
  border-radius: 25px;
  font-weight: 600;
  text-align: center;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
}

.btn-payment:hover {
  color: white;
  text-decoration: none;
  box-shadow: 0 8px 25px rgba(102, 126, 234, 0.3);
}

.btn-cancel {
  background: #f8f9fa;
  color: #6c757d;
  border: 2px solid #e9ecef;
  padding: 1rem 2rem;
  border-radius: 25px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
}

.btn-cancel:hover {
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

  .status-section {
    flex-direction: column;
    gap: 1rem;
  }

  .details-cards {
    grid-template-columns: 1fr;
  }

  .detail-card {
    padding: 1rem;
  }

  .detail-icon {
    width: 40px;
    height: 40px;
    font-size: 1.2rem;
  }

  .detail-value {
    text-align: left;
  }

  .tabs-nav {
    justify-content: flex-start;
  }

  .tab-button {
    padding: 0.8rem 1rem;
    font-size: 0.9rem;
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

  .payment-modal {
    margin: 1rem;
    border-radius: 15px;
  }

  .modal-header,
  .modal-body {
    padding: 1rem;
  }
}
</style>
