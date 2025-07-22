<template>
  <div class="container-fluid row">
    <div class="aside col-3">
      <Dashboard :listDashBoard="listDashBoard"></Dashboard>
    </div>
    <div class="article col-9">
      <div class="stats-header mb-4">
        <h1 class="stats-title">
          <i class="bi bi-bar-chart me-2"></i>
          Product Statistics
        </h1>
        <p class="stats-subtitle">Thống kê và quản lý sản phẩm</p>
      </div>

      <!-- Base Product Selection -->
      <div class="selection-section">
        <h3 class="section-title">
          <i class="bi bi-box me-2"></i>
          Chọn Base Product
        </h3>
        <div class="dropdown mb-4">
          <button
            class="btn btn-outline-primary dropdown-toggle w-100 selection-btn"
            type="button"
            data-bs-toggle="dropdown"
          >
            <span v-if="selectedBaseProduct">
              <img
                :src="selectedBaseProduct.mainImage"
                alt=""
                width="40"
                height="40"
                class="me-3 rounded"
              />
              <div class="product-info">
                <strong>{{ selectedBaseProduct.name }}</strong>
                <br />
                <small class="text-muted">{{ selectedBaseProduct.material }}</small>
              </div>
            </span>
            <span v-else class="select-placeholder">
              <i class="bi bi-search me-2"></i>
              Chọn một sản phẩm cơ sở
            </span>
          </button>
          <ul class="dropdown-menu w-100" style="max-height: 400px; overflow-y: auto">
            <li
              v-for="product in dropDownListBaseProduct"
              :key="product.id"
              @click="selectBaseProduct(product)"
              class="dropdown-item d-flex align-items-center product-item"
              style="cursor: pointer"
            >
              <img
                :src="product.mainImage"
                alt=""
                width="50"
                height="50"
                class="me-3 rounded"
              />
              <div class="flex-grow-1">
                <strong>{{ product.name }}</strong>
                <br />
                <small class="text-muted">{{ product.material }}</small>
                <br />
                <small class="badge bg-info">Qty: {{ product.qty }}</small>
              </div>
            </li>
          </ul>
        </div>
      </div>

      <!-- Base Product Information Display -->
      <div v-if="selectedBaseProduct" class="info-section">
        <div class="card shadow-sm border-0 rounded-4">
          <div class="card-header bg-primary text-white rounded-top-4">
            <h5 class="mb-0">
              <i class="bi bi-info-circle me-2"></i>
              Thông tin Base Product
            </h5>
          </div>
          <div class="card-body p-4">
            <div class="row">
              <div class="col-md-3 text-center">
                <img
                  :src="selectedBaseProduct.mainImage"
                  alt="Product Image"
                  class="img-fluid rounded-3 shadow-sm"
                  style="max-height: 200px; object-fit: cover"
                />
              </div>
              <div class="col-md-9">
                <div class="row">
                  <div class="col-md-6 mb-3">
                    <label class="info-label">Tên sản phẩm:</label>
                    <div class="info-value">{{ selectedBaseProduct.name }}</div>
                  </div>
                  <div class="col-md-6 mb-3">
                    <label class="info-label">Chất liệu:</label>
                    <div class="info-value">{{ selectedBaseProduct.material }}</div>
                  </div>
                  <div class="col-md-6 mb-3">
                    <label class="info-label">Số lượng:</label>
                    <div class="info-value">
                      <span class="badge bg-success fs-6">{{
                        selectedBaseProduct.qty
                      }}</span>
                    </div>
                  </div>
                  <div class="col-md-6 mb-3">
                    <label class="info-label">Danh mục:</label>
                    <div class="info-value">
                      {{
                        categoriesDropDownList.find(
                          (c) => c.id === selectedBaseProduct.categoryId
                        )?.name || "Chưa xác định"
                      }}
                    </div>
                  </div>
                  <div class="col-12">
                    <label class="info-label">Mô tả:</label>
                    <div class="info-value">
                      {{ selectedBaseProduct.description || "Không có mô tả" }}
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Product Item Statistics -->
      <div v-if="selectedProductItem" class="statistics-section">
        <div class="card shadow-sm border-0 rounded-4">
          <div
            class="card-header bg-success text-white rounded-top-4 d-flex justify-content-between align-items-center"
          >
            <h5 class="mb-0">
              <i class="bi bi-bar-chart me-2"></i>
              Thống kê Product Item: {{ selectedProductItem.sku }}
            </h5>
            <button
              class="btn btn-light btn-sm"
              @click="selectedProductItem = null"
              title="Quay lại danh sách"
            >
              <i class="bi bi-arrow-left me-1"></i>
              Quay lại
            </button>
          </div>
          <div class="card-body p-4">
            <!-- Basic Item Info -->
            <div class="row mb-4">
              <div class="col-md-3 text-center">
                <img
                  :src="selectedProductItem.imageUrl || selectedBaseProduct.mainImage"
                  alt="Product Item Image"
                  class="img-fluid rounded-3 shadow-sm"
                  style="max-height: 200px; object-fit: cover"
                />
              </div>
              <div class="col-md-9">
                <div class="row">
                  <div class="col-md-6 mb-3">
                    <label class="info-label">Tên sản phẩm:</label>
                    <div class="info-value">
                      {{ selectedProductItem.name || selectedBaseProduct.name }}
                    </div>
                  </div>
                  <div class="col-md-6 mb-3">
                    <label class="info-label">SKU:</label>
                    <div class="info-value">
                      <span class="badge bg-secondary fs-6">{{
                        selectedProductItem.sku
                      }}</span>
                    </div>
                  </div>
                  <div class="col-md-6 mb-3">
                    <label class="info-label">Giá bán:</label>
                    <div class="info-value text-success fs-5">
                      {{ formatPrice(selectedProductItem.price) }}
                    </div>
                  </div>
                  <div class="col-md-6 mb-3">
                    <label class="info-label">Giá gốc:</label>
                    <div class="info-value text-danger">
                      {{ formatPrice(selectedProductItem.cost) }}
                    </div>
                  </div>
                  <div class="col-md-6 mb-3">
                    <label class="info-label">Số lượng tồn:</label>
                    <div class="info-value">
                      <span
                        :class="getQuantityBadgeClass(selectedProductItem.qty) + ' fs-6'"
                        >{{ selectedProductItem.qty }}</span
                      >
                    </div>
                  </div>
                  <div class="col-md-6 mb-3">
                    <label class="info-label">Lợi nhuận:</label>
                    <div class="info-value">
                      <span class="badge bg-warning text-dark fs-6">{{
                        formatPrice(selectedProductItem.price - selectedProductItem.cost)
                      }}</span>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <!-- Statistics Cards -->
            <div class="row">
              <div class="col-md-3 mb-3">
                <div class="stat-card text-center">
                  <div class="stat-icon bg-primary">
                    <i class="bi bi-cart"></i>
                  </div>
                  <h6 class="stat-title">Lượt mua</h6>
                  <div class="stat-value">{{ selectedProductItem.turnBuy || 0 }}</div>
                  <small class="text-muted">Tổng số lần mua</small>
                </div>
              </div>

              <div class="col-md-3 mb-3">
                <div class="stat-card text-center">
                  <div class="stat-icon bg-success">
                    <i class="bi bi-cash-coin"></i>
                  </div>
                  <h6 class="stat-title">Doanh thu ước tính</h6>
                  <div class="stat-value">
                    {{
                      formatPrice(
                        (selectedProductItem.turnBuy || 0) * selectedProductItem.price
                      )
                    }}
                  </div>
                  <small class="text-muted">Turnbuy × Giá bán</small>
                </div>
              </div>

              <div class="col-md-3 mb-3">
                <div class="stat-card text-center">
                  <div class="stat-icon bg-warning">
                    <i class="bi bi-graph-up"></i>
                  </div>
                  <h6 class="stat-title">Lợi nhuận ước tính</h6>
                  <div class="stat-value">
                    {{
                      formatPrice(
                        (selectedProductItem.turnBuy || 0) *
                          (selectedProductItem.price - selectedProductItem.cost)
                      )
                    }}
                  </div>
                  <small class="text-muted">Turnbuy × Lợi nhuận</small>
                </div>
              </div>

              <div class="col-md-3 mb-3">
                <div class="stat-card text-center">
                  <div class="stat-icon bg-info">
                    <i class="bi bi-percent"></i>
                  </div>
                  <h6 class="stat-title">Tỷ lệ lợi nhuận</h6>
                  <div class="stat-value">
                    {{
                      calculateProfitMargin(
                        selectedProductItem.price,
                        selectedProductItem.cost
                      )
                    }}%
                  </div>
                  <small class="text-muted">Margin</small>
                </div>
              </div>
            </div>

            <!-- Additional Info -->
            <div class="row mt-4">
              <div class="col-md-6">
                <h6 class="text-primary">
                  <i class="bi bi-calendar me-2"></i>
                  Thời gian bán
                </h6>
                <div class="info-grid">
                  <div class="info-item">
                    <label>Bắt đầu bán:</label>
                    <span>{{ formatDateTime(selectedProductItem.sellStart) }}</span>
                  </div>
                  <div class="info-item">
                    <label>Kết thúc bán:</label>
                    <span>{{ formatDateTime(selectedProductItem.sellEnd) }}</span>
                  </div>
                </div>
              </div>

              <div class="col-md-6">
                <h6 class="text-primary">
                  <i class="bi bi-shield-check me-2"></i>
                  Thông tin kho
                </h6>
                <div class="info-grid">
                  <div class="info-item">
                    <label>Safety Stock:</label>
                    <span>{{ selectedProductItem.safetyStock || "Chưa đặt" }}</span>
                  </div>
                  <div class="info-item">
                    <label>Trạng thái:</label>
                    <span>
                      <span v-if="selectedProductItem.qty <= 0" class="badge bg-danger"
                        >Hết hàng</span
                      >
                      <span
                        v-else-if="
                          selectedProductItem.qty <=
                          (selectedProductItem.safetyStock || 5)
                        "
                        class="badge bg-warning"
                        >Sắp hết</span
                      >
                      <span v-else class="badge bg-success">Còn hàng</span>
                    </span>
                  </div>
                </div>
              </div>
            </div>

            <!-- Description -->
            <div class="row mt-4" v-if="selectedProductItem.description">
              <div class="col-12">
                <h6 class="text-primary">
                  <i class="bi bi-text-paragraph me-2"></i>
                  Mô tả sản phẩm
                </h6>
                <div class="description-box">
                  {{ selectedProductItem.description }}
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Product Items List -->
      <div
        v-if="selectedBaseProduct && productItemsList.length > 0 && !selectedProductItem"
        class="items-section"
      >
        <div class="section-header">
          <h3 class="section-title">
            <i class="bi bi-grid me-2"></i>
            Product Items cho {{ selectedBaseProduct.name }}
          </h3>
          <div class="items-count">
            <span class="badge bg-primary fs-6">{{ productItemsList.length }} items</span>
          </div>
        </div>

        <div class="row">
          <div
            v-for="item in productItemsList"
            :key="item.id"
            class="col-md-6 col-lg-4 mb-4"
          >
            <div class="card product-item-card shadow-sm border-0 h-100">
              <div class="card-header bg-light border-0">
                <div class="d-flex justify-content-between align-items-center">
                  <h6 class="mb-0 text-primary">
                    {{ item.name || selectedBaseProduct.name }}
                  </h6>
                  <span class="badge bg-secondary">{{ item.sku }}</span>
                </div>
              </div>
              <div class="card-body">
                <div class="text-center mb-3">
                  <img
                    :src="item.imageUrl || selectedBaseProduct.mainImage"
                    alt="Product Item"
                    class="item-image rounded-3"
                  />
                </div>

                <div class="item-details">
                  <div class="detail-row">
                    <span class="detail-label">
                      <i class="bi bi-tag me-1"></i>
                      Giá bán:
                    </span>
                    <span class="detail-value price">{{ formatPrice(item.price) }}</span>
                  </div>

                  <div class="detail-row">
                    <span class="detail-label">
                      <i class="bi bi-cash me-1"></i>
                      Giá gốc:
                    </span>
                    <span class="detail-value cost">{{ formatPrice(item.cost) }}</span>
                  </div>

                  <div class="detail-row">
                    <span class="detail-label">
                      <i class="bi bi-box me-1"></i>
                      Số lượng:
                    </span>
                    <span class="detail-value">
                      <span :class="getQuantityBadgeClass(item.qty)">{{ item.qty }}</span>
                    </span>
                  </div>

                  <div class="detail-row" v-if="item.description">
                    <span class="detail-label">
                      <i class="bi bi-text-left me-1"></i>
                      Mô tả:
                    </span>
                    <span class="detail-value">{{ item.description }}</span>
                  </div>
                </div>
              </div>

              <div class="card-footer bg-transparent border-0">
                <div class="action-buttons">
                  <button class="btn btn-sm btn-outline-primary">
                    <i class="bi bi-eye me-1"></i>
                    Xem
                  </button>
                  <button class="btn btn-sm btn-outline-warning">
                    <i class="bi bi-pencil me-1"></i>
                    Sửa
                  </button>
                  <button
                    class="btn btn-sm btn-outline-info"
                    @click="selectProductItem(item)"
                  >
                    <i class="bi bi-bar-chart me-1"></i>
                    Thống kê
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Empty State -->
      <div
        v-else-if="selectedBaseProduct && productItemsList.length === 0"
        class="empty-state"
      >
        <div class="text-center py-5">
          <i class="bi bi-inbox display-1 text-muted"></i>
          <h4 class="text-muted mt-3">Không có Product Items</h4>
          <p class="text-muted">Base Product này chưa có item nào được tạo.</p>
          <button class="btn btn-primary">
            <i class="bi bi-plus me-2"></i>
            Tạo Product Item mới
          </button>
        </div>
      </div>

      <!-- No Selection State -->
      <div v-else-if="!selectedBaseProduct" class="no-selection-state">
        <div class="text-center py-5">
          <i class="bi bi-search display-1 text-muted"></i>
          <h4 class="text-muted mt-3">Chọn Base Product</h4>
          <p class="text-muted">
            Vui lòng chọn một Base Product để xem thông tin chi tiết.
          </p>
        </div>
      </div>

      <!-- No Item Selected State -->
      <div
        v-else-if="
          selectedBaseProduct && !selectedProductItem && productItemsList.length > 0
        "
        class="no-item-selection-state"
      >
        <div class="text-center py-5">
          <i class="bi bi-graph-up display-1 text-muted"></i>
          <h4 class="text-muted mt-3">Chọn Product Item để xem thống kê</h4>
          <p class="text-muted">
            Click vào nút "Thống kê" trên product item để xem thông tin chi tiết.
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import Dashboard from "../../Module/DashBoard.vue";
import createCrudService from "../../../Configs/reusableCRUDService.js";
import { dropDown } from "../../../Configs/DropDownList.js";
import api from "../../../Configs/api.js";
import "bootstrap/dist/js/bootstrap.bundle.min.js";
import "bootstrap/dist/css/bootstrap.min.css";

// Reactive data
const selectedBaseProduct = ref(null);
const selectedProductItem = ref(null);
const dropDownListBaseProduct = ref([]);
const productItemsList = ref([]);
const categoriesDropDownList = ref([]);

// Services
const categoriesService = createCrudService("Categories");

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
async function selectBaseProduct(product) {
  selectedBaseProduct.value = product;
  selectedProductItem.value = null; // Reset selected item when changing base product
  await getProductItems(product.id);
}

async function selectProductItem(item) {
  selectedProductItem.value = item;
}

async function getProductItems(baseProductId) {
  try {
    const response = await api.get(
      `/admin/ProductItems/ByBaseProductId/${baseProductId}`
    );
    console.log("Product items response:", response.data);

    // Handle both content wrapper and direct array response
    const items = response.data.content || response.data || [];

    productItemsList.value = items.map((item) => ({
      ...item,
      name: item.baseProducts?.name || selectedBaseProduct.value?.name,
    }));
  } catch (error) {
    console.error("Get product items failed:", error);
    productItemsList.value = [];
  }
}

function formatPrice(price) {
  if (!price) return "0 VND";
  return `${price.toLocaleString("vi-VN")} VND`;
}

function getQuantityBadgeClass(qty) {
  if (qty <= 0) return "badge bg-danger";
  if (qty <= 5) return "badge bg-warning text-dark";
  if (qty <= 20) return "badge bg-warning";
  return "badge bg-success";
}

function calculateProfitMargin(price, cost) {
  if (!price || !cost) return 0;
  return (((price - cost) / price) * 100).toFixed(2);
}

function formatDateTime(dateTime) {
  if (!dateTime) return "Chưa đặt";
  return new Date(dateTime).toLocaleString("vi-VN");
}

// Lifecycle
onMounted(async () => {
  try {
    // Load base products dropdown
    dropDownListBaseProduct.value = await dropDown("BaseProducts");
    console.log("Base products loaded:", dropDownListBaseProduct.value?.length, "items");

    // Load categories dropdown
    const responseCategories = await categoriesService.getAll(0, 1000);
    const categoriesData =
      responseCategories.data?.content || responseCategories.data || [];
    categoriesDropDownList.value = categoriesData.map((category) => ({
      id: category.id,
      name: category.name,
    }));
    console.log("Categories loaded:", categoriesDropDownList.value?.length, "items");
  } catch (error) {
    console.error("Error loading data:", error);
  }
});
</script>

<style scoped>
/* Header Styles */
.stats-header {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  padding: 2rem;
  border-radius: 16px;
  margin-bottom: 2rem;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
}

.stats-title {
  font-size: 2.5rem;
  font-weight: 700;
  margin-bottom: 0.5rem;
}

.stats-subtitle {
  font-size: 1.1rem;
  opacity: 0.9;
  margin-bottom: 0;
}

/* Section Styles */
.selection-section,
.info-section,
.items-section,
.statistics-section {
  margin-bottom: 2rem;
}

.section-title {
  color: #2c3e50;
  font-weight: 600;
  margin-bottom: 1rem;
}

.section-header {
  display: flex;
  justify-content: between;
  align-items: center;
  margin-bottom: 1.5rem;
  padding-bottom: 1rem;
  border-bottom: 2px solid #e9ecef;
}

.items-count {
  margin-left: auto;
}

/* Dropdown Styles */
.selection-btn {
  padding: 1rem;
  font-size: 1rem;
  text-align: left;
  display: flex;
  align-items: center;
  justify-content: space-between;
  border-radius: 12px;
  border: 2px solid #e9ecef;
  transition: all 0.3s ease;
}

.selection-btn:hover {
  border-color: #0d6efd;
  box-shadow: 0 4px 12px rgba(13, 110, 253, 0.15);
}

.product-info {
  flex-grow: 1;
}

.select-placeholder {
  display: flex;
  align-items: center;
  color: #6c757d;
  font-style: italic;
}

.product-item {
  padding: 1rem;
  transition: background-color 0.2s ease-in-out;
  border-radius: 8px;
  margin: 0.25rem;
}

.product-item:hover {
  background-color: #f8f9fa;
  transform: translateX(4px);
}

/* Info Section Styles */
.info-label {
  font-weight: 600;
  color: #495057;
  font-size: 0.9rem;
  margin-bottom: 0.25rem;
  display: block;
}

.info-value {
  color: #2c3e50;
  font-size: 1rem;
  margin-bottom: 0.5rem;
}

/* Product Item Card Styles */
.product-item-card {
  transition: all 0.3s ease;
  border-radius: 16px;
  overflow: hidden;
}

.product-item-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 12px 24px rgba(0, 0, 0, 0.1);
}

.item-image {
  width: 100%;
  height: 150px;
  object-fit: cover;
  border: 2px solid #e9ecef;
}

.item-details {
  margin-top: 1rem;
}

.detail-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 0.75rem;
  padding-bottom: 0.5rem;
  border-bottom: 1px solid #f8f9fa;
}

.detail-label {
  font-weight: 500;
  color: #6c757d;
  font-size: 0.9rem;
}

.detail-value {
  font-weight: 600;
  color: #2c3e50;
}

.detail-value.price {
  color: #28a745;
  font-size: 1.1rem;
}

.detail-value.cost {
  color: #dc3545;
}

.action-buttons {
  display: flex;
  gap: 0.5rem;
  justify-content: center;
}

.action-buttons .btn {
  flex: 1;
  border-radius: 8px;
  font-size: 0.85rem;
  padding: 0.5rem;
}

/* Empty States */
.empty-state,
.no-selection-state,
.no-item-selection-state {
  background: #f8f9fa;
  border-radius: 16px;
  margin: 2rem 0;
}

.empty-state i,
.no-selection-state i,
.no-item-selection-state i {
  font-size: 4rem;
  color: #dee2e6;
}

/* Statistics Section Styles */
.stat-card {
  background: white;
  border-radius: 12px;
  padding: 1.5rem;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
  border: 1px solid #e9ecef;
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

.stat-title {
  color: #6c757d;
  font-size: 0.9rem;
  margin-bottom: 0.5rem;
  font-weight: 600;
}

.stat-value {
  font-size: 1.5rem;
  font-weight: 700;
  color: #2c3e50;
  margin-bottom: 0.5rem;
}

.info-grid {
  background: #f8f9fa;
  border-radius: 8px;
  padding: 1rem;
}

.info-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0.5rem 0;
  border-bottom: 1px solid #e9ecef;
}

.info-item:last-child {
  border-bottom: none;
}

.info-item label {
  font-weight: 600;
  color: #6c757d;
  font-size: 0.9rem;
}

.info-item span {
  color: #2c3e50;
  font-weight: 500;
}

.description-box {
  background: #f8f9fa;
  border-radius: 8px;
  padding: 1rem;
  border-left: 4px solid #0d6efd;
  color: #2c3e50;
  line-height: 1.6;
}

/* Responsive */
@media (max-width: 768px) {
  .stats-title {
    font-size: 2rem;
  }

  .section-header {
    flex-direction: column;
    gap: 1rem;
    align-items: flex-start;
  }

  .items-count {
    margin-left: 0;
  }

  .action-buttons {
    flex-direction: column;
  }
}

/* Bootstrap icon adjustments */
.bi {
  font-size: 1rem;
}

.display-1.bi {
  font-size: 4rem !important;
}
</style>
