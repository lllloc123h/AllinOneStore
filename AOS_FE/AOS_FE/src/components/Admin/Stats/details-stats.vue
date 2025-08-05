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
                  <h6 class="stat-title">
                    Doanh thu ước tính <br />
                    <strong>(Chưa trừ giảm giá, chiết khấu, hoàn tiền)</strong>
                  </h6>
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
            <div class="row mt-4">
              <div class="col-12">
                <h6 class="text-primary">
                  <i class="bi bi-graph-up-arrow me-2"></i>
                  Biểu đồ thống kê bán hàng
                  <strong class="text-black"
                    >(Lịch sử thay đổi giá, doanh thu, lịch sử khuyến mãi)</strong
                  >
                </h6>
                <div class="col-4 shadow-sm border-0 rounded-4 p-3">
                  <div class="row">
                    <div class="col-md-6">
                      <label class="form-label">Từ ngày:</label>
                      <input
                        type="date"
                        @change="filterByDate"
                        v-model="startAt"
                        class="form-control mb-3"
                      />
                    </div>
                    <div class="col-md-6">
                      <label class="form-label">Đến hết ngày:</label>
                      <input
                        type="date"
                        @change="filterByDate"
                        v-model="endAt"
                        class="form-control mb-3"
                      />
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-12">
                      <button
                        @click="resetDateFilter"
                        class="btn btn-outline-secondary btn-sm w-100"
                        title="Hiển thị toàn bộ dữ liệu"
                      >
                        <i class="bi bi-arrow-clockwise me-1"></i>
                        Reset Filter
                      </button>
                    </div>
                  </div>
                </div>
                <div class="chart-container">
                  <apexchart
                    :key="chartKey"
                    type="line"
                    height="350"
                    :options="chartOptions"
                    :series="series"
                  ></apexchart>

                  <!-- Chart Legend -->
                  <div class="chart-legend mt-3">
                    <div class="row">
                      <div class="col-md-3">
                        <div class="legend-item">
                          <span
                            class="legend-dot"
                            style="background-color: #ff6b35; opacity: 0.3"
                          ></span>
                          <span>Combo Sale Period</span>
                        </div>
                      </div>
                      <div class="col-md-3">
                        <div class="legend-item">
                          <span
                            class="legend-dot"
                            style="background-color: #dc3545; opacity: 0.3"
                          ></span>
                          <span>Discount Season</span>
                        </div>
                      </div>
                      <div class="col-md-3">
                        <div class="legend-item">
                          <span
                            class="legend-marker"
                            style="border: 2px solid #00e396"
                          ></span>
                          <span>Mục tiêu</span>
                        </div>
                      </div>
                      <div class="col-md-3">
                        <div class="legend-item">
                          <span
                            class="legend-marker"
                            style="border: 3px solid #ff4560; background-color: #fff"
                          ></span>
                          <span>Sự kiện đặc biệt</span>
                        </div>
                      </div>
                    </div>
                    <div class="row mt-2">
                      <div class="col-md-3">
                        <div class="legend-item">
                          <span
                            class="legend-marker"
                            style="border: 3px solid #00e396; background-color: #fff"
                          ></span>
                          <span>Lịch sử giá gốc</span>
                        </div>
                      </div>
                      <div class="col-md-3">
                        <div class="legend-item">
                          <span
                            class="legend-marker"
                            style="border: 3px solid #feb019; background-color: #fff"
                          ></span>
                          <span>Lịch sử giá bán</span>
                        </div>
                      </div>
                      <div class="col-md-3">
                        <div class="legend-item">
                          <span
                            class="legend-dot"
                            style="background-color: #e74c3c; opacity: 0.3"
                          ></span>
                          <span>Khuyến mãi</span>
                        </div>
                      </div>
                      <div class="col-md-3">
                        <small class="text-muted">
                          <i class="bi bi-info-circle me-1"></i>
                          Khu vực tô màu = thời gian khuyến mãi
                        </small>
                      </div>
                    </div>
                  </div>
                </div>
                <!-- Detailed Events Section -->
                <div
                  v-if="
                    startAt &&
                    endAt &&
                    (filteredEvents.costHistories.length > 0 ||
                      filteredEvents.priceHistories.length > 0 ||
                      filteredEvents.promotions.length > 0)
                  "
                  class="row mt-4"
                >
                  <div class="col-12">
                    <h6 class="text-primary">
                      <i class="bi bi-list-check me-2"></i>
                      Chi tiết sự kiện trong khoảng thời gian đã chọn
                      <span class="badge bg-info ms-2"
                        >{{ formatDate(startAt) }} - {{ formatDate(endAt) }}</span
                      >
                    </h6>

                    <!-- Combined Price History Details -->
                    <div
                      v-if="combinedPriceHistories.length > 0"
                      class="event-section mb-4"
                    >
                      <div class="event-header">
                        <h6 class="text-primary">
                          <i class="bi bi-currency-exchange me-2"></i>
                          Lịch sử thay đổi giá ({{ combinedPriceHistories.length }} lần)
                          <small class="text-muted ms-2">
                            - Giá gốc: {{ filteredEvents.costHistories.length }} lần, Giá
                            bán: {{ filteredEvents.priceHistories.length }} lần
                          </small>
                        </h6>
                      </div>
                      <div class="table-responsive">
                        <table class="table table-sm table-striped">
                          <thead class="table-primary">
                            <tr>
                              <th>Thời gian</th>
                              <th>Giá gốc</th>
                              <th>Giá bán</th>
                            </tr>
                          </thead>
                          <tbody>
                            <tr
                              v-for="(history, index) in combinedPriceHistories"
                              :key="index"
                            >
                              <td>
                                <span class="fw-bold">{{ history.displayTime }}</span>
                              </td>
                              <td>
                                <span v-if="history.cost" class="fw-bold text-success">
                                  <i class="bi bi-cash-stack me-1"></i>
                                  {{ history.cost.formatted }}
                                </span>
                                <span v-else class="text-muted">
                                  <i class="bi bi-dash"></i>
                                  Không có
                                </span>
                              </td>
                              <td>
                                <span v-if="history.price" class="fw-bold text-warning">
                                  <i class="bi bi-tag me-1"></i>
                                  {{ history.price.formatted }}
                                </span>
                                <span v-else class="text-muted">
                                  <i class="bi bi-dash"></i>
                                  Không có
                                </span>
                              </td>
                            </tr>
                          </tbody>
                        </table>
                      </div>
                    </div>

                    <!-- Promotions Details -->
                    <div
                      v-if="filteredEvents.promotions.length > 0"
                      class="event-section mb-4"
                    >
                      <div class="event-header">
                        <h6 class="text-danger">
                          <i class="bi bi-megaphone me-2"></i>
                          Chương trình khuyến mãi ({{ filteredEvents.promotions.length }}
                          chương trình)
                        </h6>
                      </div>
                      <div class="row">
                        <div
                          v-for="(promo, index) in filteredEvents.promotions"
                          :key="index"
                          class="col-md-6 mb-3"
                        >
                          <div class="card border-0 shadow-sm promotion-detail-card">
                            <div
                              class="card-header"
                              :class="promo.type === 'COMBO' ? 'bg-warning' : 'bg-danger'"
                              style="color: white"
                            >
                              <div
                                class="d-flex justify-content-between align-items-center"
                              >
                                <h6 class="mb-0">
                                  <i
                                    :class="
                                      promo.type === 'COMBO'
                                        ? 'bi bi-gift'
                                        : 'bi bi-percent'
                                    "
                                    class="me-2"
                                  ></i>
                                  {{ promo.name }}
                                </h6>
                                <span
                                  class="badge"
                                  :class="
                                    promo.type === 'COMBO'
                                      ? 'bg-light text-dark'
                                      : 'bg-light text-dark'
                                  "
                                >
                                  {{ promo.type }}
                                </span>
                              </div>
                            </div>
                            <div class="card-body">
                              <div class="promotion-details">
                                <div class="detail-item">
                                  <strong>Thời gian:</strong>
                                  <div class="text-muted">
                                    <i class="bi bi-calendar-event me-1"></i>
                                    {{ formatDateTime(promo.startAt) }}
                                    <br />
                                    <i class="bi bi-calendar-x me-1"></i>
                                    {{ formatDateTime(promo.endAt) }}
                                  </div>
                                </div>
                                <div class="detail-item mt-2">
                                  <strong>Mô tả:</strong>
                                  <p class="text-muted mb-1">
                                    {{ promo.description || "Không có mô tả" }}
                                  </p>
                                </div>
                                <div class="detail-item mt-2" v-if="promo.discountValue">
                                  <strong>Giá trị giảm:</strong>
                                  <span class="text-success fw-bold">{{
                                    formatPrice(promo.discountValue)
                                  }}</span>
                                </div>
                                <div class="detail-item mt-2" v-if="promo.comboPrice">
                                  <strong>Giá combo:</strong>
                                  <span class="text-warning fw-bold">{{
                                    formatPrice(promo.comboPrice)
                                  }}</span>
                                </div>
                                <div
                                  class="detail-item mt-2"
                                  v-if="promo.discountPercent"
                                >
                                  <strong>Phần trăm giảm:</strong>
                                  <span class="text-info fw-bold"
                                    >{{ promo.discountPercent }}%</span
                                  >
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>

                    <!-- Summary Statistics -->
                    <div class="event-summary mt-4">
                      <div class="row">
                        <div class="col-md-4">
                          <div class="summary-card bg-light">
                            <div class="summary-icon text-success">
                              <i class="bi bi-graph-up"></i>
                            </div>
                            <div class="summary-content">
                              <h6>Tổng thay đổi giá gốc</h6>
                              <p class="mb-0">
                                {{ filteredEvents.costHistories.length }} lần
                              </p>
                            </div>
                          </div>
                        </div>
                        <div class="col-md-4">
                          <div class="summary-card bg-light">
                            <div class="summary-icon text-warning">
                              <i class="bi bi-tag"></i>
                            </div>
                            <div class="summary-content">
                              <h6>Tổng thay đổi giá bán</h6>
                              <p class="mb-0">
                                {{ filteredEvents.priceHistories.length }} lần
                              </p>
                            </div>
                          </div>
                        </div>
                        <div class="col-md-4">
                          <div class="summary-card bg-light">
                            <div class="summary-icon text-danger">
                              <i class="bi bi-megaphone"></i>
                            </div>
                            <div class="summary-content">
                              <h6>Tổng khuyến mãi</h6>
                              <p class="mb-0">
                                {{ filteredEvents.promotions.length }} chương trình
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
import { ref, onMounted, computed, nextTick } from "vue";
import Dashboard from "../../Module/DashBoard.vue";
import createCrudService from "../../../Configs/reusableCRUDService.js";
import { dropDown } from "../../../Configs/DropDownList.js";
import api from "../../../Configs/api.js";
import "bootstrap/dist/js/bootstrap.bundle.min.js";
import "bootstrap/dist/css/bootstrap.min.css";
import { formatDateTimeLocal } from "../../Module/CommonsFunctions.js";
const startAt = ref("21-07-2025");
const endAt = ref();
const chartKey = ref(0); // For force re-render

// Event details
const filteredEvents = ref({
  costHistories: [],
  priceHistories: [],
  promotions: [],
});

async function filterByDate() {
  if (!selectedProductItem.value) {
    console.warn("No product item selected");
    return;
  }

  if (!startAt.value || !endAt.value) {
    // If no date range selected, show original data
    chartOptions.value.title.text = "Thống kê bán hàng với Annotations (Có thể zoom)";
    return;
  }

  const startTime = new Date(startAt.value).getTime();
  const endTime = new Date(endAt.value).setHours(23, 59, 59, 999); // End of day

  console.log("Fetching data for product:", selectedProductItem.value.id);
  console.log(
    "Date range:",
    formatDateTimeLocal(startTime),
    "to",
    formatDateTimeLocal(endTime)
  );

  // Call API to get filtered data
  const filteredSeries = ref({
    name: "Lượt mua",
    data: [],
  });
  api
    .get(
      `/admin/products/details/${
        selectedProductItem.value.id
      }?startAt=${formatDateTimeLocal(startTime)}&endAt=${formatDateTimeLocal(endTime)}`
    )
    .then(async (response) => {
      const apiData = response.data?.data || [];
      filteredSeries.value.data = apiData.map((item) => [
        new Date(item.date).getTime(),
        item.turnBuy || 0,
      ]);
      const costHistories = response.data?.costHistories || [];
      const priceHistories = response.data?.priceHistories || [];
      const promotions = response.data?.promotions || [];

      // Store filtered events for detailed display
      filteredEvents.value = {
        costHistories: costHistories,
        priceHistories: priceHistories,
        promotions: promotions,
      };

      console.log("filtered data:", filteredSeries.value.data);
      console.log("cost history: ", response.data?.costHistories || []);
      console.log("price history: ", response.data?.priceHistories || []);
      console.log("promotions: ", response.data?.promotions || []);

      // Ensure annotations object exists and has proper structure
      if (!chartOptions.value.annotations) {
        chartOptions.value.annotations = {
          xaxis: [],
          yaxis: [],
          points: [],
        };
      }

      // Clear existing annotations
      chartOptions.value.annotations.xaxis = [];
      chartOptions.value.annotations.points = [];

      // Combine and sort all histories by date to handle overlapping
      const allHistories = [
        ...costHistories.map((h) => ({
          ...h,
          type: "cost",
          timestamp: new Date(h.createdAt).getTime(),
          value: h.cost,
          color: "#00e396",
          text: `Giá gốc ${h.cost.toLocaleString("vi-VN")} VND`,
        })),
        ...priceHistories.map((h) => ({
          ...h,
          type: "price",
          timestamp: new Date(h.createdAt).getTime(),
          value: h.price,
          color: "#feb019",
          text: `Giá bán ${h.price.toLocaleString("vi-VN")} VND`,
        })),
        ...promotions.map((p) => ({
          ...p,
          type: p.type,
          timestamp: new Date(p.startAt).getTime(),
          endTimestamp: new Date(p.endAt).getTime(),
          value: p.discountValue || p.comboPrice || 0,
          color: p.type === "COMBO" ? "#ff6b35" : "red",
          text: p.name,
        })),
      ].sort((a, b) => a.timestamp - b.timestamp);

      // Group annotations by time proximity (within 1 day)
      const groupedAnnotations = [];
      const timeThreshold = 24 * 60 * 60 * 1000; // 1 day in milliseconds

      allHistories.forEach((history, index) => {
        // Check if there's an existing group within time threshold
        const existingGroup = groupedAnnotations.find(
          (group) => Math.abs(group.timestamp - history.timestamp) < timeThreshold
        );

        if (existingGroup) {
          // Add to existing group
          existingGroup.items.push(history);
          // Update combined text
          existingGroup.combinedText = existingGroup.items
            .map((item) => item.text)
            .join(" | ");
        } else {
          // Create new group
          groupedAnnotations.push({
            timestamp: history.timestamp,
            items: [history],
            combinedText: history.text,
          });
        }
      });

      // Create annotations from grouped data
      groupedAnnotations.forEach((group, groupIndex) => {
        if (group.items.length === 1) {
          // Single annotation
          const item = group.items[0];

          if (item.type === "COMBO") {
            // Handle promotion as area annotation (time range)
            chartOptions.value.annotations.xaxis.push({
              x: item.timestamp,
              x2: item.endTimestamp,
              fillColor: item.color,
              opacity: 0.2,
              label: {
                borderColor: item.color,
                style: {
                  color: "#fff",
                  background: item.color,
                  fontSize: "11px",
                  fontWeight: "500",
                },
                text: item.text,
                orientation: "vertical",
                position: "top",
              },
            });
          } else {
            chartOptions.value.annotations.xaxis.push({
              x: item.timestamp,
              x2: item.endTimestamp,
              fillColor: item.color,
              opacity: 0.2,
              label: {
                borderColor: item.color,
                style: {
                  color: "#fff",
                  background: item.color,
                  fontSize: "11px",
                  fontWeight: "500",
                },
                text: item.text,
                orientation: "vertical",
                position: "top",
              },
            });
          }
        } else {
          // Multiple annotations at similar time - create stacked labels
          group.items.forEach((item, itemIndex) => {
            const offsetY = itemIndex * 25; // Vertical offset for stacking

            if (item.type === "COMBO") {
              // Handle promotion in group
              chartOptions.value.annotations.xaxis.push({
                x: item.timestamp,
                x2: item.endTimestamp,
                fillColor: item.color,
                opacity: 0.15,
                strokeDashArray: itemIndex === 0 ? 0 : 5,
                label: {
                  borderColor: item.color,
                  style: {
                    color: "#fff",
                    background: item.color,
                    fontSize: "10px",
                    fontWeight: "500",
                  },
                  text: item.text,
                  orientation: "vertical",
                  position: "top",
                  offsetY: offsetY,
                },
              });
            } else {
              chartOptions.value.annotations.xaxis.push({
                x: item.timestamp,
                x2: item.endTimestamp,
                fillColor: item.color,
                opacity: 0.15,
                strokeDashArray: itemIndex === 0 ? 0 : 5,
                label: {
                  borderColor: item.color,
                  style: {
                    color: "#fff",
                    background: item.color,
                    fontSize: "10px",
                    fontWeight: "500",
                  },
                  text: item.text,
                  orientation: "vertical",
                  position: "top",
                  offsetY: offsetY,
                },
              });
            }
          });
        }
      });

      // Update series correctly - assign the data array directly
      series.value[0].data = filteredSeries.value.data;
      // Update chart title to show filtered period
      chartOptions.value.title.text = `Thống kê bán hàng từ ${formatDate(
        startAt.value
      )} đến ${formatDate(endAt.value)}`;

      // Force chart re-render
      await nextTick();
      chartKey.value++;

      console.log("Updated series:", series.value);
      console.log("Updated chart options:", chartOptions.value.title.text);
    })
    .catch((error) => {
      console.error("Error fetching filtered data:", error);
      // Clear data on error
      series.value[0].data = [];
      filteredEvents.value = {
        costHistories: [],
        priceHistories: [],
        promotions: [],
      };
      chartOptions.value.title.text = `Thống kê bán hàng từ ${formatDate(
        startAt.value
      )} đến ${formatDate(endAt.value)} (Không có dữ liệu)`;
    });
}

function formatDate(dateStr) {
  if (!dateStr) return "";
  const date = new Date(dateStr);
  return date.toLocaleDateString("vi-VN");
}

function resetDateFilter() {
  startAt.value = null;
  endAt.value = null;

  // Reset chart data to empty
  series.value[0].data = [];

  // Clear filtered events
  filteredEvents.value = {
    costHistories: [],
    priceHistories: [],
    promotions: [],
  };

  // Clear all annotations
  if (chartOptions.value.annotations) {
    chartOptions.value.annotations.xaxis = [];
    chartOptions.value.annotations.points = [];
    chartOptions.value.annotations.yaxis = [];
  }

  chartOptions.value.title.text = "Thống kê bán hàng với Annotations (Có thể zoom)";

  // Force chart re-render
  chartKey.value++;

  console.log("Date filter reset, chart key:", chartKey.value);
}
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

// Chart data
const series = ref([
  {
    name: "Lượt mua",
    data: [],
  },
]);
console.log("Initial series data:", series.value[0].name);
const chartOptions = ref({
  chart: {
    height: 350,
    type: "line",
    id: "product-stats-chart",
    zoom: {
      enabled: true,
      type: "x",
      autoScaleYaxis: true,
      zoomedArea: {
        fill: {
          color: "#90CAF9",
          opacity: 0.4,
        },
        stroke: {
          color: "#0D47A1",
          opacity: 0.4,
          width: 1,
        },
      },
    },
    toolbar: {
      show: true,
      autoSelected: "zoom",
    },
  },
  dataLabels: {
    enabled: false,
  },
  stroke: {
    width: [4, 3, 3],
    curve: "smooth",
    dashArray: [0, 5, 5],
  },
  title: {
    text: "Thống kê bán hàng với Annotations (Có thể zoom 1)",
    align: "left",
    style: {
      fontSize: "16px",
      fontWeight: "600",
      color: "#2c3e50",
    },
  },
  legend: {
    position: "top",
    horizontalAlign: "right",
  },
  markers: {
    hover: {
      sizeOffset: 3,
    },
  },
  xaxis: {
    type: "datetime",
    labels: {
      format: "dd/MM",
      style: {
        fontSize: "11px",
      },
    },
    title: {
      text: "Thời gian (Có thể zoom để xem chi tiết)",
    },
  },
  yaxis: [
    {
      title: {
        text: "Lượt mua",
      },
      labels: {
        formatter: function (val) {
          return val ? val.toLocaleString("vi-VN") : "0";
        },
      },
    },
  ],
  tooltip: {
    shared: true,
    intersect: false,
    x: {
      format: "dd/MM/yyyy HH:mm",
    },
    y: {
      formatter: function (val) {
        return val + " lượt";
      },
    },
  },
  colors: ["#28a745", "#007bff", "#ff6b35"],
  grid: {
    borderColor: "#f1f1f1",
    padding: {
      right: 30,
      left: 20,
    },
  },
  annotations: {
    yaxis: [],
    xaxis: [],
    points: [],
  },
});
// Methods
async function selectBaseProduct(product) {
  selectedBaseProduct.value = product;
  selectedProductItem.value = null; // Reset selected item when changing base product
  await getProductItems(product.id);
}

function selectProductItem(item) {
  selectedProductItem.value = item;

  // Reset date filters when selecting new item
  startAt.value = null;
  endAt.value = null;

  // Reset chart data to empty
  series.value[0].data = [];

  // Clear filtered events
  filteredEvents.value = {
    costHistories: [],
    priceHistories: [],
    promotions: [],
  };

  // Clear all annotations
  if (chartOptions.value.annotations) {
    chartOptions.value.annotations.xaxis = [];
    chartOptions.value.annotations.points = [];
    chartOptions.value.annotations.yaxis = [];
  }

  chartOptions.value.title.text = "Thống kê bán hàng với Annotations (Có thể zoom)";

  // Force chart re-render
  chartKey.value++;

  console.log("Selected product item:", item.sku, "ID:", item.id);
  console.log("Chart key updated to:", chartKey.value);
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

function calculateChangePercentage(newValue, oldValue) {
  if (!oldValue || oldValue === 0) return 0;
  return (((newValue - oldValue) / oldValue) * 100).toFixed(1);
}

// Computed property to combine and sort price histories
const combinedPriceHistories = computed(() => {
  if (
    !filteredEvents.value.costHistories.length &&
    !filteredEvents.value.priceHistories.length
  ) {
    return [];
  }

  // Group by timestamp (same time) and merge cost/price data
  const timeGroups = {};

  // Process cost histories
  filteredEvents.value.costHistories.forEach((cost) => {
    const timestamp = new Date(cost.createdAt).getTime();
    const timeKey = Math.floor(timestamp / (60 * 1000)) * (60 * 1000); // Group by minute

    if (!timeGroups[timeKey]) {
      timeGroups[timeKey] = {
        timestamp: timeKey,
        displayTime: formatDateTime(cost.createdAt),
        cost: null,
        price: null,
      };
    }
    timeGroups[timeKey].cost = {
      value: cost.cost,
      formatted: formatPrice(cost.cost),
    };
  });

  // Process price histories
  filteredEvents.value.priceHistories.forEach((price) => {
    const timestamp = new Date(price.createdAt).getTime();
    const timeKey = Math.floor(timestamp / (60 * 1000)) * (60 * 1000); // Group by minute

    if (!timeGroups[timeKey]) {
      timeGroups[timeKey] = {
        timestamp: timeKey,
        displayTime: formatDateTime(price.createdAt),
        cost: null,
        price: null,
      };
    }
    timeGroups[timeKey].price = {
      value: price.price,
      formatted: formatPrice(price.price),
    };
  });

  // Convert to array and sort by timestamp
  return Object.values(timeGroups).sort((a, b) => a.timestamp - b.timestamp);
});

// Lifecycle
onMounted(async () => {
  try {
    // Load base products dropdown
    dropDownListBaseProduct.value = await dropDown("BaseProducts");
    console.log("Base products loaded:", dropDownListBaseProduct.value, "items");

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

/* Chart Styles */
.chart-container {
  background: white;
  border-radius: 12px;
  padding: 1.5rem;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  border: 1px solid #e9ecef;
  margin-top: 1rem;
}

/* Promotion Card Styles */
.promotion-card {
  background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
  border-radius: 12px;
  padding: 1rem;
  display: flex;
  align-items: center;
  gap: 1rem;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  border-left: 4px solid;
  margin-bottom: 1rem;
}

.combo-card {
  border-left-color: #ff6b35;
}

.discount-card {
  border-left-color: #dc3545;
}

.promotion-icon {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.5rem;
  color: white;
}

.combo-card .promotion-icon {
  background-color: #ff6b35;
}

.discount-card .promotion-icon {
  background-color: #dc3545;
}

.promotion-info h6 {
  margin: 0;
  color: #2c3e50;
  font-weight: 600;
}

.promotion-info p {
  margin: 0.25rem 0;
  color: #6c757d;
  font-size: 0.9rem;
}

/* Chart Legend Styles */
.chart-legend {
  background: #f8f9fa;
  border-radius: 8px;
  padding: 1rem;
  border-top: 1px solid #dee2e6;
}

.legend-item {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  margin-bottom: 0.5rem;
  font-size: 0.9rem;
  color: #6c757d;
}

.legend-dot {
  width: 20px;
  height: 12px;
  border-radius: 2px;
  display: inline-block;
}

.legend-marker {
  width: 12px;
  height: 12px;
  border-radius: 50%;
  display: inline-block;
  background-color: white;
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

/* Event Details Styles */
.event-section {
  background: white;
  border-radius: 12px;
  border: 1px solid #e9ecef;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.event-header {
  background: #f8f9fa;
  padding: 1rem;
  border-bottom: 1px solid #e9ecef;
}

.event-header h6 {
  margin: 0;
  font-weight: 600;
}

.table-responsive {
  border-radius: 0 0 12px 12px;
  overflow: hidden;
}

.table th {
  border-top: none;
  font-weight: 600;
  font-size: 0.9rem;
  vertical-align: middle;
}

.table td {
  vertical-align: middle;
  font-size: 0.9rem;
}

.promotion-detail-card {
  border-radius: 12px;
  overflow: hidden;
  transition: all 0.3s ease;
}

.promotion-detail-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.15);
}

.promotion-detail-card .card-header {
  border-bottom: none;
  font-weight: 600;
}

.promotion-details .detail-item {
  padding: 0.5rem 0;
  border-bottom: 1px solid #f8f9fa;
}

.promotion-details .detail-item:last-child {
  border-bottom: none;
}

.event-summary {
  background: #f8f9fa;
  border-radius: 12px;
  padding: 1.5rem;
  border: 1px solid #e9ecef;
}

.summary-card {
  display: flex;
  align-items: center;
  gap: 1rem;
  padding: 1rem;
  border-radius: 8px;
  background: white !important;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  margin-bottom: 1rem;
  transition: all 0.3s ease;
}

.summary-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.15);
}

.summary-icon {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.5rem;
  background-color: rgba(0, 0, 0, 0.1);
}

.summary-content h6 {
  margin: 0;
  font-size: 0.9rem;
  color: #6c757d;
  font-weight: 600;
}

.summary-content p {
  margin: 0;
  font-size: 1.1rem;
  font-weight: 700;
  color: #2c3e50;
}

/* Price change indicators */
.text-success .bi-arrow-up,
.text-danger .bi-arrow-down {
  font-size: 0.8rem;
  margin-right: 0.25rem;
}

/* Table hover effects */
.table tbody tr:hover {
  background-color: rgba(0, 123, 255, 0.05);
  transform: scale(1.01);
  transition: all 0.2s ease;
}

/* Badge improvements */
.badge {
  font-size: 0.75rem;
  padding: 0.35em 0.65em;
}

/* Responsive adjustments for event details */
@media (max-width: 768px) {
  .event-section {
    margin-bottom: 1rem;
  }

  .table-responsive {
    font-size: 0.8rem;
  }

  .summary-card {
    flex-direction: column;
    text-align: center;
    gap: 0.5rem;
  }

  .summary-icon {
    width: 40px;
    height: 40px;
    font-size: 1.2rem;
  }
}
</style>
