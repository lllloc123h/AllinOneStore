<template>
  <div class="container-fluid row">
    <div class="aside col-2">
      <Dashboard :listDashBoard="listDashBoard"></Dashboard>
    </div>
    <div class="article col-10">
      <!-- Header Section -->
      <div class="stats-header">
        <div class="container-fluid">
          <div class="header-content">
            <div class="header-text">
              <h1 class="stats-title">
                <i class="bi bi-graph-up me-3"></i>
                Thống Kê Tổng Quát
              </h1>
              <p class="stats-subtitle">Tổng quan về doanh thu và hoạt động kinh doanh</p>
            </div>
            <div class="header-actions">
              <button class="btn btn-refresh" @click="refreshStats" :disabled="loading">
                <i class="bi bi-arrow-clockwise me-2" :class="{ spin: loading }"></i>
                Làm mới
              </button>
            </div>
          </div>
        </div>
      </div>

      <div class="container-fluid">
        <!-- Loading State -->
        <div v-if="loading" class="loading-state">
          <div class="loading-spinner">
            <div class="spinner-border text-primary" role="status">
              <span class="visually-hidden">Đang tải...</span>
            </div>
            <p class="mt-3 text-muted">Đang tải dữ liệu thống kê...</p>
          </div>
        </div>

        <!-- Error State -->
        <div v-else-if="error" class="error-state">
          <div class="error-card">
            <div class="error-icon">
              <i class="bi bi-exclamation-triangle"></i>
            </div>
            <h4>Không thể tải dữ liệu</h4>
            <p class="text-muted">{{ error }}</p>
            <button class="btn btn-primary" @click="fetchStats">
              <i class="bi bi-arrow-clockwise me-2"></i>
              Thử lại
            </button>
          </div>
        </div>

        <!-- Main Stats Content -->
        <div v-else class="stats-content">
          <!-- Revenue Overview -->
          <div class="stats-section mb-5">
            <div class="section-header">
              <h3 class="section-title">
                <i class="bi bi-currency-dollar me-2"></i>
                Tổng Quan Doanh Thu
              </h3>
            </div>
            <div class="row g-4">
              <!-- Row 1: Main Revenue Stats -->
              <div class="col-12">
                <div class="row g-4">
                  <!-- Gross Revenue -->
                  <div class="col-xl-4 col-lg-6">
                    <div class="stat-card revenue-card">
                      <div class="stat-icon gross-revenue-icon">
                        <i class="bi bi-cash-stack"></i>
                      </div>
                      <div class="stat-content">
                        <h6 class="stat-label">Doanh Thu Gộp</h6>
                        <div class="stat-value gross-revenue-value">
                          {{ formatCurrency(stats.grossRevenue) }}
                        </div>
                        <div class="stat-description">
                          <span class="text-muted"
                            >Tổng doanh thu ước tính
                            <strong style="color: red"
                              >(chưa và đã nhận đơn hàng, chưa chiết trừ chi phí, giảm
                              giá, phí vận chuyển)</strong
                            ></span
                          >
                        </div>
                      </div>
                    </div>
                  </div>

                  <!-- Raw Net Revenue -->
                  <div class="col-xl-4 col-lg-6">
                    <div class="stat-card raw-revenue-card">
                      <div class="stat-icon raw-revenue-icon">
                        <i class="bi bi-cash"></i>
                      </div>
                      <div class="stat-content">
                        <h6 class="stat-label">Doanh Thu Các Đơn Đã Giao</h6>
                        <div class="stat-value raw-revenue-value">
                          {{ formatCurrency(stats.rawNetRevenue) }}
                        </div>
                        <div class="stat-description">
                          <span class="text-muted"
                            >Tổng doanh thu khi nhận được hàng</span
                          >
                        </div>
                      </div>
                    </div>
                  </div>

                  <!-- Net Revenue -->
                  <div class="col-xl-4 col-lg-6">
                    <div class="stat-card revenue-card">
                      <div class="stat-icon net-revenue-icon">
                        <i class="bi bi-graph-up-arrow"></i>
                      </div>
                      <div class="stat-content">
                        <h6 class="stat-label">Doanh Thu Ròng</h6>
                        <div class="stat-value net-revenue-value">
                          {{ formatCurrency(stats.netRevenue) }}
                        </div>
                        <div class="stat-description">
                          <span class="text-muted"
                            >Doanh thu sau khi giao thành công và trừ các khoản
                            <strong>(chiết khấu, giảm giá, phí vận chuyển)</strong></span
                          >
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>

              <!-- Row 2: Cost and Profit Analysis -->
              <div class="col-12">
                <div class="row g-4">
                  <!-- Total Cost Products -->
                  <div class="col-xl-4 col-lg-6">
                    <div class="stat-card cost-products-card">
                      <div class="stat-icon cost-products-icon">
                        <i class="bi bi-box-seam"></i>
                      </div>
                      <div class="stat-content">
                        <h6 class="stat-label">Tổng Chi Phí Sản Phẩm</h6>
                        <div class="stat-value cost-products-value">
                          {{ formatCurrency(stats.totalCostProducts) }}
                        </div>
                        <div class="stat-description">
                          <span class="text-muted"
                            >Chi phí của các sản phẩm trong đơn hàng đã giao thành
                            công</span
                          >
                        </div>
                      </div>
                    </div>
                  </div>

                  <!-- Total Cost For Discount -->
                  <div class="col-xl-4 col-lg-6">
                    <div class="stat-card cost-discount-card">
                      <div class="stat-icon cost-discount-icon">
                        <i class="bi bi-calculator-fill"></i>
                      </div>
                      <div class="stat-content">
                        <h6 class="stat-label">Tổng Chiết Trừ Khuyến Mãi</h6>
                        <div class="stat-value cost-discount-value">
                          {{ formatCurrency(stats.totalCostForDiscount) }}
                        </div>
                        <div class="stat-description">
                          <span class="text-muted"
                            >Tổng chiết trừ giảm giá, vận chuyển</span
                          >
                        </div>
                      </div>
                    </div>
                  </div>

                  <!-- Profit -->
                  <div class="col-xl-4 col-lg-6">
                    <div class="stat-card profit-card">
                      <div class="stat-icon profit-icon">
                        <i class="bi bi-trophy-fill"></i>
                      </div>
                      <div class="stat-content">
                        <h6 class="stat-label">Lợi Nhuận</h6>
                        <div class="stat-value profit-value">
                          {{
                            formatCurrency(
                              stats.netRevenue -
                                stats.totalCostForDiscount -
                                stats.totalCostProducts
                            )
                          }}
                        </div>
                        <div class="stat-description">
                          <span class="text-muted"
                            >Doanh thu ròng - Chi phí sản phẩm - Chiết trừ</span
                          >
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- Returns Overview -->
          <div class="stats-section mb-5">
            <div class="section-header">
              <h3 class="section-title">
                <i class="bi bi-arrow-return-left me-2"></i>
                Thống Kê Hoàn Trả
              </h3>
            </div>
            <div class="row g-4">
              <!-- Total Returns -->
              <div class="col-xl-4 col-lg-6">
                <div class="stat-card return-card">
                  <div class="stat-icon return-icon">
                    <i class="bi bi-box-arrow-in-left"></i>
                  </div>
                  <div class="stat-content">
                    <h6 class="stat-label">Tổng Số Đơn Hoàn Trả</h6>
                    <div class="stat-value return-count-value">
                      {{ formatNumber(stats.countReturnedOrders) }} đơn
                    </div>
                    <div class="stat-description">
                      <span class="text-muted">Số lượng đơn hàng được hoàn trả</span>
                    </div>
                  </div>
                </div>
              </div>

              <!-- Total Order Returned -->
              <div class="col-xl-4 col-lg-6">
                <div class="stat-card return-order-card">
                  <div class="stat-icon return-order-icon">
                    <i class="bi bi-arrow-repeat"></i>
                  </div>
                  <div class="stat-content">
                    <h6 class="stat-label">Tổng Đơn Hoàn Trả</h6>
                    <div class="stat-value return-order-value">
                      {{ formatCurrency(stats.totalOrderReturned) }}
                    </div>
                    <div class="stat-description">
                      <span class="text-muted">Tổng giá trị đơn hàng hoàn trả</span>
                    </div>
                  </div>
                </div>
              </div>

              <!-- Total Return Amount -->
              <div class="col-xl-4 col-lg-6">
                <div class="stat-card return-amount-card">
                  <div class="stat-icon return-amount-icon">
                    <i class="bi bi-currency-exchange"></i>
                  </div>
                  <div class="stat-content">
                    <h6 class="stat-label">Tổng Tiền Hoàn Trả</h6>
                    <div class="stat-value return-amount-value">
                      {{ formatCurrency(stats.totalReturnedAmount) }}
                    </div>
                    <div class="stat-description">
                      <span class="text-muted">Tổng tiền thực tế hoàn trả vào ví</span>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- Order Statistics -->
          <div class="stats-section mb-5">
            <div class="section-header">
              <h3 class="section-title">
                <i class="bi bi-clipboard-check me-2"></i>
                Thống Kê Đơn Hàng
              </h3>
            </div>
            <div class="row g-4">
              <!-- Delivered Orders -->
              <div class="col-xl-12 col-lg-12">
                <div class="stat-card order-card">
                  <div class="stat-icon delivered-order-icon">
                    <i class="bi bi-check-circle-fill"></i>
                  </div>
                  <div class="stat-content">
                    <h6 class="stat-label">Tổng Số Đơn Hàng Đã Giao</h6>
                    <div class="stat-value delivered-order-value">
                      {{ formatNumber(stats.countDeliveredOrders) }} đơn
                    </div>
                    <div class="stat-description">
                      <span class="text-muted">Số lượng đơn hàng đã giao thành công</span>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- Shipping Overview -->
          <div class="stats-section mb-5">
            <div class="section-header">
              <h3 class="section-title">
                <i class="bi bi-truck me-2"></i>
                Thống Kê Vận Chuyển
              </h3>
            </div>
            <div class="row g-4">
              <!-- Estimated Shipping -->
              <div class="col-xl-3 col-lg-6">
                <div class="stat-card shipping-card">
                  <div class="stat-icon estimated-shipping-icon">
                    <i class="bi bi-calculator"></i>
                  </div>
                  <div class="stat-content">
                    <h6 class="stat-label">Phí Vận Chuyển Ước Tính</h6>
                    <div class="stat-value estimated-shipping-value">
                      {{ formatCurrency(stats.totalEstimatedShippingFee) }}
                    </div>
                    <div class="stat-description">
                      <span class="text-muted"
                        >Tổng phí vận chuyển trên hóa đơn ước tính</span
                      >
                    </div>
                  </div>
                </div>
              </div>

              <!-- Actual Shipping -->
              <div class="col-xl-3 col-lg-6">
                <div class="stat-card shipping-card">
                  <div class="stat-icon actual-shipping-icon">
                    <i class="bi bi-check-circle"></i>
                  </div>
                  <div class="stat-content">
                    <h6 class="stat-label">Phí Vận Chuyển sau áp mã giảm giá ước tính</h6>
                    <div class="stat-value actual-shipping-value">
                      {{ formatCurrency(stats.totalActualShippingFee) }}
                    </div>
                    <div class="stat-description">
                      <span class="text-muted"
                        >Tổng phí vận chuyển trên hóa đơn ước tính</span
                      >
                    </div>
                  </div>
                </div>
              </div>
              <!-- Shipping Difference -->
              <div class="col-xl-3 col-lg-6">
                <div class="stat-card shipping-difference-card">
                  <div class="stat-icon shipping-difference-icon">
                    <i class="bi bi-plus-minus"></i>
                  </div>
                  <div class="stat-content">
                    <h6 class="stat-label">Chênh Lệch Vận Chuyển Ước Tính</h6>
                    <div class="stat-value shipping-difference-value">
                      {{
                        formatCurrency(
                          stats.totalActualShippingFee - stats.totalEstimatedShippingFee
                        )
                      }}
                    </div>
                    <div class="stat-description">
                      <span class="text-muted">Thực tế - Ước tính</span>
                    </div>
                  </div>
                </div>
              </div>
              <!-- Actual Shipping Delivered -->
              <div class="col-xl-3 col-lg-6">
                <div class="stat-card shipping-delivered-card">
                  <div class="stat-icon shipping-delivered-icon">
                    <i class="bi bi-truck-flatbed"></i>
                  </div>
                  <div class="stat-content">
                    <h6 class="stat-label">Chênh lệch thực tế phải chịu</h6>
                    <div class="stat-value shipping-delivered-value">
                      {{ formatCurrency(stats.totalActualShippingFeeDelivered) }}
                    </div>
                    <div class="stat-description">
                      <span class="text-muted"
                        >Tổng phí vận chuyển đã giao thành công</span
                      >
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- Discount Overview -->
          <div class="stats-section mb-5">
            <div class="section-header">
              <h3 class="section-title">
                <i class="bi bi-percent me-2"></i>
                Thống Kê Khuyến Mãi
              </h3>
            </div>
            <div class="row g-4">
              <!-- Total Estimated Discount -->
              <div class="col-xl-4 col-lg-6">
                <div class="stat-card discount-card">
                  <div class="stat-icon discount-icon">
                    <i class="bi bi-gift"></i>
                  </div>
                  <div class="stat-content">
                    <h6 class="stat-label">Tổng Chiết Trừ Ước Tính</h6>
                    <div class="stat-value discount-value">
                      {{ formatCurrency(stats.totalEstimatedDiscountValue) }}
                    </div>
                    <div class="stat-description">
                      <span class="text-muted">Tổng giảm giá ước tính trên đơn hàng</span>
                    </div>
                  </div>
                </div>
              </div>

              <!-- Total Delivered Discount -->
              <div class="col-xl-4 col-lg-6">
                <div class="stat-card discount-delivered-card">
                  <div class="stat-icon discount-delivered-icon">
                    <i class="bi bi-check2-square"></i>
                  </div>
                  <div class="stat-content">
                    <h6 class="stat-label">Tổng Chiết Trừ Thực Tế</h6>
                    <div class="stat-value discount-delivered-value">
                      {{ formatCurrency(stats.totalDiscountValueDilivered) }}
                    </div>
                    <div class="stat-description">
                      <span class="text-muted"
                        >Tổng giảm giá các đơn đã giao thành công</span
                      >
                    </div>
                  </div>
                </div>
              </div>

              <!-- Discount Ratio -->
              <div class="col-xl-4 col-lg-6">
                <div class="stat-card discount-ratio-card">
                  <div class="stat-icon discount-ratio-icon">
                    <i class="bi bi-graph-down"></i>
                  </div>
                  <div class="stat-content">
                    <h6 class="stat-label">Tỷ Lệ Chiết Trừ</h6>
                    <div class="stat-value discount-ratio-value">
                      {{ calculateDiscountRatio() }}%
                    </div>
                    <div class="stat-description">
                      <span class="text-muted">Chiết trừ ước tính / Doanh thu gộp</span>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- Summary Section -->
          <div class="stats-section">
            <div class="section-header">
              <h3 class="section-title">
                <i class="bi bi-clipboard-data me-2"></i>
                Tổng Kết Hoạt Động
              </h3>
            </div>
            <div class="summary-card">
              <div class="row g-4">
                <div class="col-md-6">
                  <div class="summary-item">
                    <div class="summary-icon revenue-summary-icon">
                      <i class="bi bi-trophy"></i>
                    </div>
                    <div class="summary-content">
                      <h6>Hiệu Quả Kinh Doanh</h6>
                      <p class="summary-description">
                        Tỷ lệ doanh thu ròng đạt
                        <strong>{{ calculateRevenueRatio() }}%</strong>
                        so với doanh thu gộp, thể hiện hiệu quả quản lý chi phí.
                      </p>
                    </div>
                  </div>
                </div>
                <div class="col-md-6">
                  <div class="summary-item">
                    <div class="summary-icon shipping-summary-icon">
                      <i class="bi bi-speedometer2"></i>
                    </div>
                    <div class="summary-content">
                      <h6>Quản Lý Vận Chuyển</h6>
                      <p class="summary-description">
                        Độ chênh lệch phí vận chuyển:
                        <strong>{{
                          formatCurrency(
                            Math.abs(
                              stats.totalActualShippingFee -
                                stats.totalEstimatedShippingFee
                            )
                          )
                        }}</strong>
                        {{
                          stats.totalActualShippingFee -
                            stats.totalEstimatedShippingFee >=
                          0
                            ? "(Vượt dự kiến)"
                            : "(Tiết kiệm được)"
                        }}
                      </p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import Dashboard from "../../Module/DashBoard.vue";
import api from "../../../Configs/api.js";

// Reactive data
const stats = ref({
  grossRevenue: 0,
  rawNetRevenue: 0,
  totalCostForDiscount: 0,
  netRevenue: 0,
  countDeliveredOrders: 0,
  countReturnedOrders: 0,
  totalOrderReturned: 0,
  totalReturnedAmount: 0,
  totalActualShippingFee: 0,
  totalEstimatedShippingFee: 0,
  totalActualShippingFeeDelivered: 0,
  totalEstimatedDiscountValue: 0,
  totalDiscountValueDilivered: 0,
  totalCostProducts: 0,
});

const loading = ref(false);
const error = ref(null);

// Dashboard list
const listDashBoard = [
  "Accounts",
  "Authorities",
  "BaseProducts",
  "ProductItems",
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
];

// Methods
const fetchStats = async () => {
  try {
    loading.value = true;
    error.value = null;

    const response = await api.get("/admin/Orders/general-stats");
    stats.value = response.data;

    console.log("General stats loaded:", stats.value);
  } catch (err) {
    console.error("Failed to fetch general stats:", err);
    error.value = err.response?.data?.message || "Không thể tải dữ liệu thống kê";
  } finally {
    loading.value = false;
  }
};

const refreshStats = () => {
  fetchStats();
};

// Utility functions
const formatCurrency = (amount) => {
  if (!amount && amount !== 0) return "0 VND";
  return new Intl.NumberFormat("vi-VN", {
    style: "currency",
    currency: "VND",
  }).format(amount);
};

const formatNumber = (number) => {
  if (!number && number !== 0) return "0";
  return new Intl.NumberFormat("vi-VN").format(number);
};

const calculateRevenueRatio = () => {
  if (!stats.value.grossRevenue || stats.value.grossRevenue === 0) return "0.0";
  return ((stats.value.netRevenue / stats.value.grossRevenue) * 100).toFixed(1);
};

const calculateDiscountRatio = () => {
  if (!stats.value.grossRevenue || stats.value.grossRevenue === 0) return "0.0";
  return (
    (stats.value.totalEstimatedDiscountValue / stats.value.grossRevenue) *
    100
  ).toFixed(1);
};

// Lifecycle
onMounted(() => {
  fetchStats();
});
</script>

<style scoped>
/* Layout Styles */
.container-fluid.row {
  margin: 0;
  padding: 0;
}

.aside.col-2 {
  background-color: #f8f9fa;
  border-right: 1px solid #dee2e6;
  padding: 0;
}

.article.col-10 {
  background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
  min-height: 100vh;
  padding: 0;
}

/* Header Styles */
.stats-header {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  padding: 2rem;
  border-radius: 16px;
  margin-bottom: 2rem;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
}

.header-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 2rem;
}

.stats-title {
  font-size: 2.5rem;
  font-weight: 700;
  margin: 0;
  margin-bottom: 0.5rem;
}

.stats-subtitle {
  font-size: 1.1rem;
  margin: 0;
  opacity: 0.9;
}

.btn-refresh {
  background: rgba(255, 255, 255, 0.2);
  border: 2px solid rgba(255, 255, 255, 0.3);
  color: white;
  padding: 0.75rem 1.5rem;
  border-radius: 12px;
  font-weight: 600;
  backdrop-filter: blur(10px);
  transition: all 0.3s ease;
}

.btn-refresh:hover {
  background: rgba(255, 255, 255, 0.3);
  border-color: rgba(255, 255, 255, 0.5);
  color: white;
  transform: translateY(-2px);
}

.spin {
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

/* Loading & Error States */
.loading-state,
.error-state {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 60vh;
}

.loading-spinner,
.error-card {
  text-align: center;
  padding: 3rem;
  background: white;
  border-radius: 16px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
}

.error-icon {
  font-size: 4rem;
  color: #dc3545;
  margin-bottom: 1rem;
}

/* Section Styles */
.stats-section {
  margin-bottom: 2rem;
}

.section-header {
  margin-bottom: 2rem;
}

.section-title {
  color: #2c3e50;
  font-weight: 600;
  font-size: 1.5rem;
  margin: 0;
  display: flex;
  align-items: center;
}

/* Stat Card Styles */
.stat-card {
  background: white;
  border-radius: 12px;
  padding: 1.5rem;
  height: 100%;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  border: 1px solid #e9ecef;
  transition: all 0.3s ease;
  position: relative;
  overflow: hidden;
}

.stat-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.15);
}

.stat-icon {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0 auto 1rem;
  color: white;
  font-size: 1.5rem;
}

/* Revenue Icons */
.gross-revenue-icon {
  background: linear-gradient(135deg, #4caf50, #45a049);
}

.net-revenue-icon {
  background: linear-gradient(135deg, #2196f3, #1976d2);
}

.raw-revenue-icon {
  background: linear-gradient(135deg, #00bcd4, #0097a7);
}

.cost-products-icon {
  background: linear-gradient(135deg, #9c27b0, #7b1fa2);
}

.cost-discount-icon {
  background: linear-gradient(135deg, #ff9800, #f57c00);
}

.profit-icon {
  background: linear-gradient(135deg, #ffd700, #ffb300);
}

/* Return Icons */
.return-icon {
  background: linear-gradient(135deg, #f44336, #d32f2f);
}

.return-order-icon {
  background: linear-gradient(135deg, #ff5722, #d84315);
}

.return-amount-icon {
  background: linear-gradient(135deg, #e91e63, #c2185b);
}

/* Order Icons */
.delivered-order-icon {
  background: linear-gradient(135deg, #4caf50, #388e3c);
}

/* Shipping Icons */
.estimated-shipping-icon {
  background: linear-gradient(135deg, #00bcd4, #0097a7);
}

.actual-shipping-icon {
  background: linear-gradient(135deg, #009688, #00695c);
}

.shipping-delivered-icon {
  background: linear-gradient(135deg, #4caf50, #388e3c);
}

.shipping-difference-icon {
  background: linear-gradient(135deg, #795548, #5d4037);
}

/* Discount Icons */
.discount-icon {
  background: linear-gradient(135deg, #ff5722, #d84315);
}

.discount-delivered-icon {
  background: linear-gradient(135deg, #4caf50, #388e3c);
}

.discount-ratio-icon {
  background: linear-gradient(135deg, #607d8b, #455a64);
}

.stat-label {
  color: #6c757d;
  font-size: 0.9rem;
  font-weight: 600;
  margin-bottom: 0.5rem;
}

.stat-value {
  font-size: 1.5rem;
  font-weight: 700;
  color: #2c3e50;
  margin-bottom: 0.5rem;
}

.stat-description {
  font-size: 0.85rem;
}

/* Summary Section */
.summary-card {
  background: white;
  border-radius: 12px;
  padding: 2rem;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  border: 1px solid #e9ecef;
}

.summary-item {
  display: flex;
  gap: 1.5rem;
  align-items: flex-start;
}

.summary-icon {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.5rem;
  color: white;
  flex-shrink: 0;
}

.revenue-summary-icon {
  background: linear-gradient(135deg, #ffd700, #ffb300);
}

.shipping-summary-icon {
  background: linear-gradient(135deg, #3f51b5, #303f9f);
}

.summary-content h6 {
  color: #2c3e50;
  font-weight: 600;
  margin-bottom: 0.5rem;
}

.summary-description {
  color: #6c757d;
  line-height: 1.6;
  margin: 0;
}

/* Responsive Design */
@media (max-width: 768px) {
  .stats-title {
    font-size: 2rem;
  }

  .header-content {
    flex-direction: column;
    text-align: center;
  }

  .stat-card {
    padding: 1.5rem;
  }

  .stat-value {
    font-size: 1.5rem;
  }

  .summary-item {
    flex-direction: column;
    text-align: center;
  }
}

@media (max-width: 576px) {
  .stats-header {
    padding: 1.5rem 0;
  }

  .stat-card {
    padding: 1rem;
  }

  .stat-value {
    font-size: 1.3rem;
  }

  .section-title {
    font-size: 1.3rem;
  }
}
</style>
