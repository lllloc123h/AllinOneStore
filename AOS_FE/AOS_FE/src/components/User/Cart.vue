<template>
  <div class="cart-container">
    <div class="container-fluid px-3 px-lg-5 py-4">
      <!-- Header -->
      <div class="row mb-4">
        <div class="col-12">
          <div class="d-flex align-items-center justify-content-between mb-3">
            <h2 class="cart-title mb-0">
              <i class="bi bi-cart3 me-2 text-primary"></i>
              Giỏ hàng của bạn
            </h2>
            <div class="cart-stats">
              <span class="badge bg-primary rounded-pill px-3 py-2">
                {{ cart.length }} sản phẩm
              </span>
            </div>
          </div>
          <!-- Select All -->
          <div class="select-all-section bg-light rounded-3 p-3 mb-4">
            <div class="form-check">
              <input
                class="form-check-input"
                type="checkbox"
                id="selectAll"
                :checked="isAllSelected"
                @change="toggleSelectAll"
              />
              <label class="form-check-label fw-semibold" for="selectAll">
                Chọn tất cả sản phẩm ({{ validSelectedItemsCount }}/{{ cart.length }})
              </label>
            </div>
          </div>
        </div>
      </div>

      <div class="row">
        <!-- Giỏ hàng -->
        <div class="col-lg-8">
          <div class="cart-items-container">
            <!-- Combo Groups -->
            <template v-for="(items, groupKey) in comboGroups" :key="'combo-' + groupKey">
              <div class="combo-card-wrapper mb-4">
                <div
                  class="card combo-card border-0 shadow-sm rounded-4 overflow-visible"
                >
                  <!-- Combo Header -->
                  <div class="combo-header position-relative">
                    <div class="combo-gradient-bg"></div>
                    <div class="position-relative p-4">
                      <div class="d-flex align-items-center justify-content-between">
                        <div class="d-flex align-items-center">
                          <div class="form-check me-3">
                            <input
                              class="form-check-input combo-checkbox"
                              type="checkbox"
                              :checked="isComboSelected(items)"
                              @change="toggleSelectCombo(items)"
                              :disabled="
                                !items[0].promotions ||
                                !isPromotionValid(items[0].promotions)
                              "
                            />
                          </div>
                          <div class="combo-icon-wrapper me-3">
                            <i class="bi bi-gift-fill text-white"></i>
                          </div>
                          <div>
                            <div class="d-flex align-items-center mb-1">
                              <span class="combo-badge">COMBO</span>
                              <h5 class="combo-title mb-0 ms-2">
                                {{
                                  items[0].promotions
                                    ? items[0].promotions.name
                                    : "Combo không còn tồn tại"
                                }}
                              </h5>
                            </div>
                            <p class="combo-description mb-0">
                              {{
                                items[0].promotions
                                  ? items[0].promotions.description
                                  : "Combo này đã bị xóa hoặc không còn khả dụng"
                              }}
                            </p>
                            <div
                              v-if="
                                !items[0].promotions ||
                                !isPromotionValid(items[0].promotions)
                              "
                              class="mt-2"
                            >
                              <span
                                class="status-badge"
                                :class="{
                                  'status-expired':
                                    getPromotionStatusMessage(items[0].promotions) ===
                                    'Ưu đãi đã hết hạn',
                                  'status-out-of-stock':
                                    getPromotionStatusMessage(items[0].promotions) ===
                                    'Ưu đãi đã hết hàng',
                                  'status-inactive':
                                    getPromotionStatusMessage(items[0].promotions) ===
                                    'Ưu đãi đã ngừng hoạt động',
                                  'status-not-started':
                                    getPromotionStatusMessage(items[0].promotions) ===
                                    'Ưu đãi chưa bắt đầu',
                                  'status-deleted':
                                    getPromotionStatusMessage(items[0].promotions) ===
                                    'Ưu đãi đã bị xóa hoặc đã thay đổi',
                                }"
                              >
                                {{ getPromotionStatusMessage(items[0].promotions) }}
                              </span>
                            </div>
                          </div>
                        </div>
                        <button
                          class="btn btn-outline-danger btn-sm combo-remove-btn"
                          @click="removeComboGroupId(items)"
                          :disabled="false"
                        >
                          <i class="bi bi-trash3"></i>
                        </button>
                      </div>
                    </div>
                  </div>

                  <!-- Combo Controls -->
                  <div class="combo-controls bg-light border-top p-3">
                    <div class="row align-items-center">
                      <div class="col-md-6">
                        <div class="d-flex align-items-center">
                          <span class="me-3 fw-semibold text-muted">Số lượng combo:</span>
                          <div class="quantity-controls">
                            <button
                              class="btn btn-outline-secondary btn-sm"
                              @click="decreaseComboGroupQty(items)"
                              :disabled="
                                items[0].comboQty <= 1 ||
                                !items[0].promotions ||
                                !isPromotionValid(items[0].promotions)
                              "
                            >
                              <i class="bi bi-dash"></i>
                            </button>
                            <span class="quantity-display">{{ items[0].comboQty }}</span>
                            <button
                              class="btn btn-outline-secondary btn-sm"
                              @click="increaseComboGroupQty(items)"
                              :disabled="
                                !items[0].promotions ||
                                !isPromotionValid(items[0].promotions)
                              "
                            >
                              <i class="bi bi-plus"></i>
                            </button>
                          </div>
                        </div>
                      </div>
                      <div class="col-md-6 text-end">
                        <div class="combo-total">
                          <span class="total-label">Tổng cộng:</span>
                          <template
                            v-if="
                              items[0].promotions && items[0].promotions.comboPrice > 0
                            "
                          >
                            <span class="total-price">
                              {{
                                (
                                  items[0].promotions.comboPrice * items[0].comboQty
                                ).toLocaleString()
                              }}₫
                            </span>
                          </template>
                          <template v-else-if="items[0].promotions">
                            <span class="total-tbd">Tính tại quầy</span>
                          </template>
                          <template v-else>
                            <span class="total-unavailable text-danger"
                              >Không khả dụng</span
                            >
                          </template>
                        </div>
                      </div>
                    </div>
                  </div>

                  <!-- Combo Items -->
                  <div class="combo-items">
                    <div
                      v-for="item in items"
                      :key="item.id"
                      class="combo-item"
                      :class="{
                        'combo-item-gift': item.isGift,
                        'combo-item-invalid':
                          !items[0].promotions || !isPromotionValid(items[0].promotions),
                      }"
                    >
                      <div class="row align-items-center p-3">
                        <div class="col-auto">
                          <div class="item-image-wrapper position-relative">
                            <img
                              :src="item.image"
                              class="item-image"
                              alt="Product Image"
                            />
                            <div v-if="item.isGift" class="gift-overlay">
                              <i class="bi bi-gift-fill"></i>
                            </div>
                          </div>
                        </div>
                        <div class="col">
                          <div class="item-info">
                            <div class="d-flex align-items-center flex-wrap mb-1 gap-2">
                              <h6 class="item-name mb-0">{{ item.name }}</h6>
                              <span v-if="item.isGift" class="gift-badge">
                                <i class="bi bi-gift-fill me-1"></i>
                                Quà tặng
                              </span>
                              <a
                                v-if="item.custom"
                                class="custom-badge"
                                :href="`/Customizer/create/${item.productItemId}`"
                                style="text-decoration: none"
                              >
                                <i class="bi bi-palette me-1"></i>
                                Có thể tùy chỉnh
                              </a>
                            </div>
                            <p class="item-sku text-muted mb-0">{{ item.sku }}</p>
                          </div>
                        </div>
                        <div class="col-auto">
                          <div class="item-quantity">
                            <span class="quantity-badge">{{ item.quantity }}</span>
                          </div>
                        </div>
                        <div class="col-auto">
                          <div class="item-price text-end">
                            <template v-if="items[0].promotions.comboPrice > 0">
                              <template v-if="item.isGift">
                                <div class="gift-price">
                                  <del class="original-price"
                                    >{{
                                      (item.price * item.quantity).toLocaleString()
                                    }}₫</del
                                  >
                                  <span class="free-price">Miễn phí</span>
                                </div>
                              </template>
                              <template v-else>
                                <span class="price"
                                  >{{
                                    (item.price * item.quantity).toLocaleString()
                                  }}₫</span
                                >
                              </template>
                            </template>
                            <template v-else>
                              <span class="price-tbd">Tính tại quầy</span>
                            </template>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </template>
            <!-- Single Products -->
            <div
              v-for="item in singleProducts"
              :key="item.id"
              class="product-card-wrapper mb-3"
            >
              <div class="card product-card border-0 shadow-sm rounded-3">
                <div class="card-body p-4">
                  <div class="row align-items-center">
                    <!-- Checkbox -->
                    <div class="col-auto">
                      <div class="form-check">
                        <input
                          class="form-check-input"
                          type="checkbox"
                          v-model="selectedItems"
                          :value="item.id"
                        />
                      </div>
                    </div>

                    <!-- Product Image -->
                    <div class="col-auto">
                      <div class="product-image-wrapper position-relative">
                        <img
                          :src="item.image"
                          class="product-image"
                          alt="Product Image"
                        />

                        <!-- Promotion Badge - Corner overlay -->
                        <div
                          v-if="item.promotions.type === 'COMBO'"
                          class="promotion-badge-corner"
                        >
                          <button
                            data-bs-toggle="modal"
                            data-bs-target="#exampleModalToggle"
                            class="btn btn-xs btn-danger rounded-pill shadow-sm promotion-btn-hover"
                            @click="openPromotionModal(item)"
                            type="button"
                            title="Click để xem ưu đãi combo"
                          >
                            <i class="bi bi-gift-fill me-1"></i>
                            <span class="d-none d-sm-inline">Ưu đãi</span>
                            <i class="bi bi-chevron-right ms-1"></i>
                          </button>
                        </div>

                        <div
                          v-else-if="item.promotions.type === 'DISCOUNT'"
                          class="promotion-badge-corner"
                        >
                          <span class="badge bg-warning text-dark rounded-pill shadow-sm">
                            <i class="bi bi-tag-fill me-1"></i>-{{
                              Math.round(
                                (item.promotions.discountValue / item.price) * 100
                              )
                            }}%
                          </span>
                        </div>
                      </div>
                    </div>

                    <!-- Product Info -->
                    <div class="col">
                      <div class="product-info">
                        <div class="d-flex align-items-center flex-wrap mb-1 gap-2">
                          <h6 class="product-name mb-0">{{ item.name }}</h6>
                          <span v-if="item.custom" class="custom-badge">
                            <i class="bi bi-palette me-1"></i>
                            Có thể tùy chỉnh
                          </span>
                        </div>
                        <p class="product-sku text-muted mb-2">{{ item.sku }}</p>

                        <!-- Price -->
                        <div class="product-price">
                          <template v-if="item.promotions.type == 'DISCOUNT'">
                            <span
                              class="original-price text-muted text-decoration-line-through me-2"
                            >
                              {{ item.price.toLocaleString() }}₫
                            </span>
                            <span class="current-price fw-bold text-danger">
                              {{
                                (
                                  item.price - item.promotions.discountValue
                                ).toLocaleString()
                              }}₫
                            </span>
                          </template>
                          <template v-else>
                            <span class="current-price fw-bold"
                              >{{ item.price.toLocaleString() }}₫</span
                            >
                          </template>
                        </div>
                      </div>
                    </div>

                    <!-- Quantity Controls -->
                    <div class="col-auto">
                      <div class="quantity-controls-single">
                        <button
                          class="btn btn-outline-secondary btn-sm"
                          @click="decreaseQty(item)"
                        >
                          <i class="bi bi-dash"></i>
                        </button>
                        <span class="quantity-display mx-3">{{ item.quantity }}</span>
                        <button
                          class="btn btn-outline-secondary btn-sm"
                          @click="increaseQty(item)"
                        >
                          <i class="bi bi-plus"></i>
                        </button>
                      </div>
                    </div>

                    <!-- Total Price -->
                    <div class="col-auto">
                      <div class="item-total text-end">
                        <template v-if="item.promotions.type == 'DISCOUNT'">
                          <span class="total-price fw-bold">
                            {{
                              (
                                (item.price - item.promotions.discountValue) *
                                item.quantity
                              ).toLocaleString()
                            }}₫
                          </span>
                        </template>
                        <template v-else>
                          <span class="total-price fw-bold">
                            {{ (item.price * item.quantity).toLocaleString() }}₫
                          </span>
                        </template>
                      </div>
                    </div>

                    <!-- Remove Button -->
                    <div class="col-auto">
                      <button
                        class="btn btn-outline-danger btn-sm"
                        @click="removeItem(item)"
                      >
                        <i class="bi bi-trash3"></i>
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- Thanh toán -->
        <div class="col-lg-4">
          <div class="checkout-sticky">
            <div class="checkout-summary card border-0 shadow-lg rounded-4">
              <div class="card-header bg-gradient-primary text-white rounded-top-4 p-4">
                <h5 class="mb-0 d-flex align-items-center">
                  <i class="bi bi-receipt me-2"></i>
                  Tổng thanh toán
                </h5>
              </div>
              <div class="card-body p-4">
                <div class="summary-details">
                  <div
                    class="summary-item d-flex justify-content-between align-items-center py-3 border-bottom"
                  >
                    <span class="summary-label">
                      <i class="bi bi-calculator text-muted me-2"></i>
                      Tạm tính
                    </span>
                    <span class="summary-value"
                      >{{ (selectedTotal + totalDiscount).toLocaleString() }}₫</span
                    >
                  </div>
                  <div
                    class="summary-item d-flex justify-content-between align-items-center py-3 border-bottom"
                  >
                    <span class="summary-label text-success">
                      <i class="bi bi-tag text-success me-2"></i>
                      Đã giảm giá
                    </span>
                    <span class="summary-value text-success"
                      >-{{ totalDiscount.toLocaleString() }}₫</span
                    >
                  </div>
                  <div
                    class="summary-item d-flex justify-content-between align-items-center py-3"
                  >
                    <span class="summary-label fw-bold fs-5">
                      <i class="bi bi-credit-card text-primary me-2"></i>
                      Tổng cộng
                    </span>
                    <span class="summary-value fw-bold fs-4 text-primary"
                      >{{ selectedTotal.toLocaleString() }}₫</span
                    >
                  </div>
                </div>

                <div class="checkout-actions mt-4">
                  <button
                    class="btn btn-primary btn-lg w-100 rounded-3 shadow-sm"
                    :disabled="validSelectedItemsCount === 0"
                    @click="checkout"
                  >
                    <i class="bi bi-credit-card me-2"></i>
                    Thanh toán
                    <span v-if="validSelectedItemsCount > 0" class="ms-2">
                      ({{ validSelectedItemsCount }} sản phẩm)
                    </span>
                  </button>

                  <div v-if="validSelectedItemsCount === 0" class="text-center mt-3">
                    <small class="text-muted">
                      <i class="bi bi-info-circle me-1"></i>
                      Vui lòng chọn sản phẩm hợp lệ để thanh toán
                    </small>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Modals -->
      <div class="modals-container">
        <!-- Modal của Ưu đãi -->
        <div
          class="modal fade"
          id="exampleModalToggle"
          aria-hidden="true"
          aria-labelledby="exampleModalToggleLabel"
          tabindex="-1"
        >
          <div class="modal-dialog modal-lg modal-dialog-centered">
            <div class="modal-content rounded-4 border-0 shadow-lg">
              <div class="modal-header bg-primary text-white rounded-top-4">
                <h1 class="modal-title fs-4" id="exampleModalToggleLabel">
                  <i class="bi bi-gift-fill me-2"></i>
                  Ưu đãi hiện có
                </h1>
                <button
                  type="button"
                  class="btn-close btn-close-white"
                  data-bs-dismiss="modal"
                  aria-label="Close"
                ></button>
              </div>
              <div class="modal-body p-4">
                <div
                  v-for="item in promotions"
                  :key="item.id"
                  class="promotion-item mb-4 border rounded-3 overflow-hidden shadow-sm"
                >
                  <div class="row g-0">
                    <div class="col-10">
                      <div class="card-body p-4">
                        <!-- Header: badge + tên + giá -->
                        <div
                          class="d-flex justify-content-between align-items-start mb-3"
                        >
                          <div class="flex-grow-1">
                            <div class="d-flex align-items-center flex-wrap gap-2">
                              <span class="badge bg-warning text-dark me-2 px-3 py-2">
                                <i class="bi bi-gift-fill me-1"></i> Ưu đãi
                              </span>
                              <h6 class="fw-bold mb-0">{{ item.name }}</h6>
                              <span v-if="item.custom" class="custom-badge">
                                <i class="bi bi-palette me-1"></i>
                                Có thể tùy chỉnh
                              </span>
                            </div>
                          </div>
                          <div class="promotion-price fw-bold text-danger fs-5">
                            {{ item.comboPrice.toLocaleString() }}₫
                          </div>
                        </div>

                        <!-- Mô tả -->
                        <div
                          class="promotion-description text-muted mb-3"
                          style="white-space: pre-line"
                        >
                          {{ item.description }}
                        </div>

                        <!-- Info phụ -->
                        <div class="d-flex justify-content-between align-items-center">
                          <span
                            class="badge bg-success bg-opacity-25 text-success px-3 py-2"
                          >
                            <i class="bi bi-box-seam me-1"></i>
                            Còn lại: {{ item.qty }}
                          </span>
                          <span
                            class="badge bg-secondary bg-opacity-25 text-secondary px-3 py-2"
                          >
                            <i class="bi bi-calendar-event me-1"></i>
                            HSD: {{ new Date(item.endAt).toLocaleDateString() }}
                          </span>
                        </div>
                      </div>
                    </div>
                    <!-- Nút chuyển tab -->
                    <div class="col-2 d-flex align-items-center">
                      <button
                        class="btn btn-outline-primary h-100 w-100 rounded-0 rounded-end-3"
                        @click="openSpecificPromotionModal(item.id)"
                        data-bs-target="#exampleModalToggle2"
                        data-bs-toggle="modal"
                      >
                        <i class="bi bi-arrow-right"></i>
                        <div class="small">Xem thêm</div>
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- Modal chi tiết combo -->
        <div
          class="modal fade"
          id="exampleModalToggle2"
          aria-hidden="true"
          aria-labelledby="exampleModalToggleLabel2"
          tabindex="-1"
        >
          <div class="modal-dialog modal-xl modal-dialog-centered">
            <div class="modal-content rounded-4 border-0 shadow-lg">
              <div class="modal-header bg-primary text-white rounded-top-4">
                <h1 class="modal-title fs-4" id="exampleModalToggleLabel2">
                  <i class="bi bi-box-seam me-2"></i>
                  Chi tiết combo
                </h1>
                <button
                  type="button"
                  class="btn-close btn-close-white"
                  data-bs-dismiss="modal"
                  aria-label="Close"
                ></button>
              </div>
              <div class="modal-body p-4">
                <!-- Hiển thị combo details theo nhóm sản phẩm -->
                <div v-if="groupProducts.length" class="combo-details">
                  <!-- Sản phẩm bắt buộc -->
                  <div class="required-section mb-5">
                    <div class="section-header mb-4">
                      <div class="d-flex align-items-center">
                        <div class="section-icon me-3">
                          <i class="bi bi-check-circle-fill text-primary fs-4"></i>
                        </div>
                        <div>
                          <h4 class="text-primary mb-1 fw-bold">Sản phẩm bắt buộc</h4>
                          <p class="text-muted mb-0 small">
                            Các sản phẩm này sẽ được thêm vào combo của bạn
                          </p>
                        </div>
                      </div>
                    </div>

                    <div class="row g-4">
                      <template
                        v-for="(group, groupIdx) in groupProducts"
                        :key="'group-' + groupIdx"
                      >
                        <div
                          v-for="item in group.items.filter((i) => !i.isGift)"
                          :key="'required-' + item.id"
                          class="col-lg-6"
                        >
                          <div
                            class="required-product-card card h-100 shadow-sm border-0"
                          >
                            <div class="card-body p-4">
                              <div class="d-flex align-items-start">
                                <div class="product-image-wrapper me-4 flex-shrink-0">
                                  <div class="image-container">
                                    <img
                                      :src="item.imageUrl"
                                      alt="Ảnh sản phẩm"
                                      class="combo-product-image"
                                    />
                                    <div class="image-overlay">
                                      <i class="bi bi-eye text-white"></i>
                                    </div>
                                  </div>
                                </div>
                                <div class="product-info flex-grow-1 min-width-0">
                                  <div class="mb-3">
                                    <h5 class="product-name mb-2 text-dark fw-semibold">
                                      {{ group.baseProduct.name }}
                                    </h5>
                                    <div class="product-sku mb-2">
                                      <span class="badge bg-light text-dark border">
                                        <i class="bi bi-upc-scan me-1"></i>{{ item.sku }}
                                      </span>
                                    </div>
                                  </div>
                                  <div class="product-meta">
                                    <div
                                      class="d-flex align-items-center justify-content-between"
                                    >
                                      <div class="price-info">
                                        <span class="price text-danger fw-bold fs-5">
                                          {{ item.price.toLocaleString() }}₫
                                        </span>
                                      </div>
                                      <div class="quantity-info">
                                        <span
                                          class="badge bg-primary bg-gradient px-3 py-2"
                                        >
                                          <i class="bi bi-box me-1"></i
                                          >{{ item.qty || 1 }} sản phẩm
                                        </span>
                                      </div>
                                    </div>
                                  </div>
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                      </template>
                    </div>
                  </div>

                  <!-- Nhóm quà tặng theo gift options -->
                  <template
                    v-for="giftGroup in giftGroups"
                    :key="'gift-group-' + giftGroup.option"
                  >
                    <div class="gift-section mb-5">
                      <div class="section-header mb-4">
                        <div class="d-flex align-items-center justify-content-between">
                          <div class="d-flex align-items-center">
                            <div class="section-icon me-3">
                              <i class="bi bi-gift-fill text-success fs-4"></i>
                            </div>
                            <div>
                              <h4 class="text-success mb-1 fw-bold">
                                {{ giftGroup.displayName }}
                              </h4>
                              <p class="text-muted mb-0 small">
                                {{ giftGroup.description }}
                              </p>
                            </div>
                          </div>
                          <div class="selection-progress">
                            <div class="progress-badge">
                              <span class="badge bg-info bg-gradient px-4 py-2 fs-6">
                                <i class="bi bi-check2-circle me-1"></i>
                                {{ getSelectedGiftsCount(giftGroup) }}/{{
                                  giftGroup.maxSelection
                                }}
                              </span>
                            </div>
                          </div>
                        </div>
                      </div>

                      <!-- Checkbox chọn tất cả cho gift option "all" -->
                      <div v-if="giftGroup.option.toLowerCase() === 'all'" class="mb-4">
                        <div class="form-check">
                          <input
                            class="form-check-input"
                            type="checkbox"
                            :checked="isAllGiftsSelected(giftGroup)"
                            :id="`select-all-${giftGroup.option}`"
                            @change="
                              toggleGiftSelection(giftGroup.items[0], giftGroup, $event)
                            "
                          />
                          <label
                            class="form-check-label fw-semibold text-primary"
                            :for="`select-all-${giftGroup.option}`"
                          >
                            <i class="bi bi-check-all me-2"></i>
                            Chọn tất cả sản phẩm quà tặng
                          </label>
                        </div>
                      </div>

                      <div class="row g-4">
                        <div
                          v-for="item in giftGroup.items"
                          :key="'gift-' + item.id"
                          class="col-lg-6"
                        >
                          <div
                            class="gift-product-card card h-100 shadow-sm border-0 position-relative"
                            :class="{ 'selected-gift': selectedComboItems[item.id] > 0 }"
                          >
                            <!-- Selection checkbox (ẩn khi gift option là "all") -->
                            <div
                              v-if="giftGroup.option.toLowerCase() !== 'all'"
                              class="selection-checkbox"
                            >
                              <input
                                type="checkbox"
                                :checked="selectedComboItems[item.id] > 0"
                                class="form-check-input shadow-sm"
                                :id="`gift-${item.id}`"
                                @change="toggleGiftSelection(item, giftGroup, $event)"
                                :disabled="
                                  !canSelectMoreGifts(giftGroup) &&
                                  selectedComboItems[item.id] === 0
                                "
                              />
                            </div>

                            <!-- Selected indicator -->
                            <div
                              v-if="selectedComboItems[item.id] > 0"
                              class="selected-indicator"
                            >
                              <div class="selected-badge">
                                <i class="bi bi-check-lg text-white"></i>
                              </div>
                            </div>

                            <div class="card-body p-4">
                              <div class="d-flex align-items-start">
                                <div class="product-image-wrapper me-4 flex-shrink-0">
                                  <div class="image-container">
                                    <img
                                      :src="item.imageUrl || '/default-product.png'"
                                      alt="Ảnh quà tặng"
                                      class="combo-product-image"
                                    />
                                    <div class="image-overlay">
                                      <i class="bi bi-gift text-white"></i>
                                    </div>
                                  </div>
                                </div>
                                <div class="product-info flex-grow-1 min-width-0">
                                  <div class="mb-3">
                                    <h5 class="product-name mb-2 text-dark fw-semibold">
                                      {{ item.baseProduct?.name || "Sản phẩm quà tặng" }}
                                    </h5>
                                    <div class="product-sku mb-2">
                                      <span class="badge bg-light text-dark border">
                                        <i class="bi bi-upc-scan me-1"></i>{{ item.sku }}
                                      </span>
                                    </div>
                                  </div>
                                  <div class="product-meta">
                                    <div
                                      class="d-flex align-items-center justify-content-between"
                                    >
                                      <div class="price-info">
                                        <span class="price text-success fw-bold fs-5">
                                          <i class="bi bi-gift me-1"></i>Miễn phí
                                        </span>
                                      </div>
                                      <div class="quantity-info">
                                        <span
                                          class="badge bg-success bg-gradient px-3 py-2"
                                        >
                                          <i class="bi bi-box me-1"></i
                                          >{{ item.qty || 1 }} sản phẩm
                                        </span>
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
                </div>
              </div>
              <div class="modal-footer bg-light rounded-bottom-4 p-4">
                <button
                  type="button"
                  class="btn btn-primary btn-lg px-4"
                  data-bs-dismiss="modal"
                  :disabled="!isComboSelectionComplete"
                  @click="handleProcessCombo()"
                >
                  <i class="bi bi-check-circle me-2"></i>
                  Hoàn tất
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Sản phẩm gợi ý -->
      <div class="suggestions-section mt-5">
        <div class="row">
          <div class="col-12">
            <div class="suggestions-header text-center mb-5">
              <h3 class="suggestions-title">
                <i class="bi bi-heart-fill text-danger me-2"></i>
                Bạn cũng có thể thích
              </h3>
              <p class="suggestions-subtitle text-muted">
                Khám phá những sản phẩm tương tự
              </p>
            </div>
          </div>
        </div>
        <div class="row g-4">
          <div
            class="col-6 col-md-3"
            v-for="(product, index) in suggestions"
            :key="'suggestion-' + index"
          >
            <div
              class="suggestion-card card border-0 shadow-sm h-100 rounded-3 overflow-hidden"
            >
              <div class="suggestion-image-wrapper position-relative">
                <img :src="product.image" class="suggestion-image card-img-top" />
                <div class="suggestion-overlay">
                  <button class="btn btn-primary btn-sm rounded-pill">
                    <i class="bi bi-cart-plus me-1"></i>
                    Thêm vào giỏ
                  </button>
                </div>
              </div>
              <div class="card-body text-center p-3">
                <h6 class="suggestion-name mb-2">{{ product.name }}</h6>
                <div class="suggestion-pricing">
                  <small
                    class="suggestion-old-price text-muted text-decoration-line-through me-2"
                  >
                    80.000₫
                  </small>
                  <span class="suggestion-current-price fw-bold text-danger">
                    {{ product.price.toLocaleString() }}₫
                  </span>
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
import { ref, computed, onMounted, watch } from "vue";
import { useRouter } from "vue-router";
import api, { authService, cartService } from "../../Configs/api";
import {
  finalHandleCartProgress,
  handleUpdateQuantityCartWhileLogin,
} from "../../Configs/cart";
const router = useRouter();
const cart = ref([]);
const selectedItems = ref([]);
const promotions = ref([]);
const groupProducts = ref([]);
const productItemIdRef = ref({});
const promotonIdRef = ref(null);

// Computed property để nhóm quà tặng theo gift options
const giftGroups = computed(() => {
  const groups = {};

  groupProducts.value.forEach((group) => {
    group.items.forEach((item) => {
      if (item.isGift && item.giftOption) {
        const option = item.giftOption;
        if (!groups[option]) {
          groups[option] = {
            option: option,
            displayName: formatGiftOptionDisplay(option),
            description: getGiftOptionDescription(option),
            maxSelection: 0, // Sẽ được tính lại sau
            items: [],
            baseProduct: group.baseProduct,
          };
        }
        groups[option].items.push({
          ...item,
          baseProduct: group.baseProduct,
        });
      }
    });
  });

  // Tính lại maxSelection sau khi có đủ items
  Object.values(groups).forEach((group) => {
    if (group.option.toLowerCase() === "all") {
      group.maxSelection = group.items.length; // Phải chọn tất cả items
    } else {
      group.maxSelection = getMaxSelectionFromOption(group.option);
    }
  });

  return Object.values(groups);
});

const comboGroups = computed(() => {
  // Gom nhóm combo theo comboGroupId (bao gồm cả khi promotions null)
  const groups = {};
  cart.value.forEach((item) => {
    if (item.comboGroupId) {
      // Nhóm theo comboGroupId bất kể promotions có null hay không
      const groupKey = item.comboGroupId;
      if (!groups[groupKey]) groups[groupKey] = [];
      groups[groupKey].push(item);
    }
  });
  return groups;
});

// Hàm kiểm tra promotion có còn hợp lệ không
function isPromotionValid(promotion) {
  if (!promotion) return false;

  // Kiểm tra active
  if (!promotion.active) return false;

  // Kiểm tra số lượng
  if (promotion.qty <= 0) return false;

  // Kiểm tra thời gian
  const now = new Date();
  const startAt = new Date(promotion.startAt);
  const endAt = new Date(promotion.endAt);

  if (now < startAt || now > endAt) return false;

  return true;
}

// Hàm lấy thông báo trạng thái promotion
function getPromotionStatusMessage(promotion) {
  if (!promotion) return "Ưu đãi đã bị xóa hoặc đã thay đổi";

  // Kiểm tra active
  if (!promotion.active) return "Ưu đãi đã ngừng hoạt động";

  // Kiểm tra số lượng
  if (promotion.qty <= 0) return "Ưu đãi đã hết hàng";

  // Kiểm tra thời gian
  const now = new Date();
  const startAt = new Date(promotion.startAt);
  const endAt = new Date(promotion.endAt);

  if (now < startAt) return "Ưu đãi chưa bắt đầu";
  if (now > endAt) return "Ưu đãi đã hết hạn";

  return "Đang hoạt động";
}
const singleProducts = computed(() => cart.value.filter((item) => !item.comboGroupId));
const totalDiscount = computed(() => {
  let sum = 0;
  const countedComboGroups = new Set();
  // Gom nhóm combo theo comboGroupId
  const allComboGroups = {};
  cart.value.forEach((item) => {
    if (item.comboGroupId && item.promotions && item.promotions.type === "COMBO") {
      const groupKey = item.comboGroupId;
      if (!allComboGroups[groupKey]) allComboGroups[groupKey] = [];
      allComboGroups[groupKey].push(item);
    }
  });

  cart.value.forEach((item) => {
    if (!selectedItems.value.includes(item.id)) return;

    // DISCOUNT - chỉ áp dụng nếu promotion hợp lệ
    if (
      item.promotions &&
      item.promotions.type === "DISCOUNT" &&
      isPromotionValid(item.promotions)
    ) {
      sum += item.promotions.discountValue * item.quantity;
      return;
    }

    // COMBO: chỉ tính giảm giá cho 1 lần duy nhất mỗi comboGroupId và promotion hợp lệ
    if (
      item.promotions &&
      item.promotions.type === "COMBO" &&
      item.promotions.comboPrice > 0 &&
      isPromotionValid(item.promotions)
    ) {
      const groupKey = item.comboGroupId;
      if (
        !countedComboGroups.has(groupKey) &&
        allComboGroups[groupKey] &&
        allComboGroups[groupKey].every((i) => selectedItems.value.includes(i.id))
      ) {
        const comboQty = item.comboQty || 1;
        const totalOrigin = allComboGroups[groupKey].reduce(
          (s, i) => s + i.price * (i.quantity || comboQty),
          0
        );
        const totalCombo = item.promotions.comboPrice * comboQty;
        sum += totalOrigin - totalCombo;
        countedComboGroups.add(groupKey);
      }
    }
  });
  return sum;
});
function isComboSelected(items) {
  console.log("lựa chọn item : ", selectedItems.value);
  // Nếu promotion null hoặc không hợp lệ thì không thể chọn
  if (!items[0].promotions || !isPromotionValid(items[0].promotions)) {
    return false;
  }
  // Trả về true nếu tất cả sản phẩm trong combo đều đã được chọn
  return items.every((item) => selectedItems.value.includes(item.id));
}
function toggleSelectCombo(items) {
  // Nếu promotion null hoặc không hợp lệ thì không cho phép thay đổi
  if (!items[0].promotions || !isPromotionValid(items[0].promotions)) {
    return;
  }

  // toggleSelectCombo
  const allSelected = isComboSelected(items);
  if (allSelected) {
    selectedItems.value = selectedItems.value.filter(
      (id) => !items.some((item) => item.id === id)
    );
  } else {
    const idsToAdd = items
      .map((item) => item.id)
      .filter((id) => !selectedItems.value.includes(id));
    selectedItems.value = [...selectedItems.value, ...idsToAdd];
  }
}
function increaseComboGroupQty(items) {
  console.log("items combo: ", items);

  // Lấy id cart của các item trong comboGroup này
  const cartIds = items.map((i) => i.id);
  cart.value.forEach((item) => {
    if (item.comboGroup === items[0].comboGroup && cartIds.includes(item.id)) {
      const tempQty = item.quantity / item.comboQty; // 3/1
      item.comboQty = (item.comboQty || 1) + 1;
      item.quantity = tempQty + item.quantity;
      authService.updateCart(tempQty);
    }
  });
  if (authService.isLogged()) {
    api
      .put("/cart/updateComboQty", {
        comboQty: items[0].comboQty,
        comboGroupId: items[0].comboGroupId, // Gửi comboGroupId nếu có
        type: "increase",
      })
      .then((response) => {
        console.log("Combo quantity updated successfully:", response.data);
        // Cập nhật lại giỏ hàng sau khi gửi
        // loadCart();
      })
      .catch((error) => {
        console.error("Error updating combo quantity:", error);
      });
  } else {
    let tempLocalList = JSON.parse(localStorage.getItem("cart")) ?? [];
    tempLocalList = tempLocalList.map((cartItem) => {
      if (cartIds.includes(cartItem.id)) {
        const tempQty = cartItem.qty / cartItem.comboQty; // 3/1
        cartItem.comboQty = cartItem.comboQty - 1;
        cartItem.qty = cartItem.qty - tempQty;
      }
      return cartItem;
    });
    localStorage.setItem("cart", JSON.stringify(tempLocalList));
  }
}

function decreaseComboGroupQty(items) {
  if (items[0].comboQty > 1) {
    const cartIds = items.map((i) => i.id);
    cart.value.forEach((item) => {
      if (item.comboGroup === items[0].comboGroup && cartIds.includes(item.id)) {
        const tempQty = item.quantity / item.comboQty; // 3/1
        item.comboQty = item.comboQty - 1;
        item.quantity = item.quantity - tempQty;
        authService.updateCart(-tempQty); // Giảm số lượng trong giỏ hàng
      }
    });
    if (authService.isLogged()) {
      api
        .put("/cart/updateComboQty", {
          comboQty: items[0].comboQty,
          comboGroupId: items[0].comboGroupId, // Gửi comboGroupId nếu có
          type: "decrease",
        })
        .then((response) => {
          console.log("Combo quantity updated successfully:", response.data);
          // Cập nhật lại giỏ hàng sau khi gửi
          // loadCart();
        })
        .catch((error) => {
          console.error("Error updating combo quantity:", error);
        });
    } else {
      let tempLocalList = JSON.parse(localStorage.getItem("cart")) ?? [];
      tempLocalList = tempLocalList.map((cartItem) => {
        if (cartIds.includes(cartItem.id)) {
          const tempQty = cartItem.qty / cartItem.comboQty; // 3/1
          cartItem.comboQty = cartItem.comboQty - 1;
          cartItem.qty = cartItem.qty - tempQty;
        }
        return cartItem;
      });
      localStorage.setItem("cart", JSON.stringify(tempLocalList));
    }
  }
}
function removeComboGroupId(items) {
  console.log("Removing combo group:", items);
  const cartIds = items.map((i) => i.id);
  cart.value = cart.value.filter((item) => !cartIds.includes(item.id));
  if (authService.isLogged()) {
    api
      .delete(`/cart/deleteCombo/${items[0].comboGroupId}`)
      .then((response) => {
        console.log("Combo group removed successfully:", response.data);
        // Cập nhật lại giỏ hàng sau khi gửi
        loadCart();
      })
      .catch((error) => {
        console.error("Error removing combo group:", error);
      });
  } else {
    let tempLocalList = JSON.parse(localStorage.getItem("cart")) ?? [];
    tempLocalList = tempLocalList.filter((item) => !cartIds.includes(item.id));
    localStorage.setItem("cart", JSON.stringify(tempLocalList));
  }
}
const cartIdOfOpenPromotionModal = ref(null);
function openPromotionModal(item) {
  promotions.value = [];
  cartIdOfOpenPromotionModal.value = item.id;
  productItemIdRef.value = item.productItemId;
  console.log("Open promotion modal for item:", item.productItemId);
  api
    .get(`/Promotions?productItemId=${item.productItemId}`)
    .then((response) => {
      promotions.value = response.data;
      console.log("Promotions for item:", item.productItemId, promotions.value);
    })
    .catch((error) => {
      console.error("Error fetching promotions:", error);
    });
}
function openSpecificPromotionModal(promotionId) {
  groupProducts.value = [];
  promotonIdRef.value = promotionId;
  console.log("Open specific promotion modal for promotion:", productItemIdRef.value);
  api
    .get(`/Promotions/${promotionId}`)
    .then((response) => {
      groupProducts.value = response.data;
      console.log("Group products for promotion:", promotionId, groupProducts.value);

      // Reset selectedComboItems
      selectedComboItems.value = {};

      // Set số lượng cho từng sản phẩm
      groupProducts.value.forEach((group) => {
        group.items.forEach((item) => {
          if (item.isGift) {
            // Sản phẩm quà tặng: khởi tạo = 0, để user tự chọn theo giftOption
            selectedComboItems.value[item.id] = 0;
          } else {
            // Sản phẩm thường: set cố định = qty
            selectedComboItems.value[item.id] = item.qty || 1;
          }
        });
      });

      console.log("Set selectedComboItems:", selectedComboItems.value);
    })
    .catch((error) => {
      console.error("Error fetching specific promotion:", error);
    });
}
const isComboSelectionComplete = computed(() => {
  // Kiểm tra tất cả sản phẩm bắt buộc đã có đủ số lượng
  const requiredItemsValid = groupProducts.value.every((group) => {
    return group.items
      .filter((item) => !item.isGift)
      .every((item) => {
        const selectedQty = selectedComboItems.value[item.id] || 0;
        const requiredQty = item.requireQty || 1;
        return selectedQty >= requiredQty;
      });
  });

  // Kiểm tra tất cả gift groups đã chọn đủ số lượng theo yêu cầu
  const giftGroupsValid = giftGroups.value.every((giftGroup) => {
    const selectedGiftsCount = getSelectedGiftsCount(giftGroup);
    const requiredCount = giftGroup.maxSelection;

    console.log(
      `Gift group ${giftGroup.displayName}: selected=${selectedGiftsCount}, required=${requiredCount}`
    );
    return selectedGiftsCount >= requiredCount;
  });

  console.log(
    `Required items valid: ${requiredItemsValid}, Gift groups valid: ${giftGroupsValid}`
  );
  return requiredItemsValid && giftGroupsValid;
});
const selectedComboItems = ref({}); // { [itemId]: số lượng đã chọn }

function getBaseProductTotalQty(group) {
  // Tính tổng số lượng đã chọn của tất cả item trong baseProduct này
  return group.items.reduce(
    (sum, item) => sum + (selectedComboItems.value[item.id] || 0),
    0
  );
}

function increaseComboQty(item, group) {
  if (!selectedComboItems.value[item.id]) selectedComboItems.value[item.id] = 0;

  if (item.isGift) {
    // Cho gift product, kiểm tra tổng gift đã chọn trong group
    const currentGiftTotal = calculateCurrentGiftTotal(group);
    const maxGiftAllowed = getMaxGiftAllowed(group);

    if (
      currentGiftTotal < maxGiftAllowed &&
      selectedComboItems.value[item.id] < item.qty
    ) {
      selectedComboItems.value[item.id]++;
    }
  } else {
    // Cho regular product, không cho phép tăng (đã set cố định ở require qty)
    return;
  }
}

// Helper functions cho gift options
function formatGiftOptionDisplay(option) {
  if (!option) return "Quà tặng";

  // Nếu gift option là "all" thì hiển thị "Chọn tất cả"
  if (option.toLowerCase() === "all") {
    return "Chọn tất cả";
  }

  // Ví dụ: "2_trong_3" -> "Chọn 2 trong 3"
  const parts = option.split("_");
  if (parts.length >= 3 && parts[1] === "trong") {
    return `Chọn ${parts[0]} trong ${parts[2]}`;
  }

  return option;
}

function getGiftOptionDescription(option) {
  if (!option) return "Chọn quà tặng miễn phí";

  // Nếu gift option là "all" thì mô tả phải chọn tất cả
  if (option.toLowerCase() === "all") {
    return "Bạn cần chọn tất cả sản phẩm quà tặng trong nhóm này";
  }

  const parts = option.split("_");
  if (parts.length >= 3 && parts[1] === "trong") {
    return `Bạn có thể chọn ${parts[0]} sản phẩm quà tặng từ ${parts[2]} sản phẩm có sẵn`;
  }

  return "Chọn quà tặng miễn phí";
}

function getMaxSelectionFromOption(option) {
  if (!option) return 1;

  const parts = option.split("_");
  if (parts.length >= 1) {
    const num = parseInt(parts[0]);
    return isNaN(num) ? 1 : num;
  }

  return 1;
}

function getSelectedGiftsCount(giftGroup) {
  // Đếm số sản phẩm được chọn (có selectedComboItems > 0), không phải tổng số lượng
  return giftGroup.items.reduce((count, item) => {
    return count + (selectedComboItems.value[item.id] > 0 ? 1 : 0);
  }, 0);
}

function canSelectMoreGifts(giftGroup) {
  const currentCount = getSelectedGiftsCount(giftGroup);
  return currentCount < giftGroup.maxSelection;
}

// Kiểm tra xem tất cả gifts trong group có được chọn hay không (cho trường hợp "all")
function isAllGiftsSelected(giftGroup) {
  if (giftGroup.option.toLowerCase() !== "all") return false;

  return giftGroup.items.every((item) => {
    return selectedComboItems.value[item.id] > 0;
  });
}

function toggleGiftSelection(item, giftGroup, event) {
  const isChecked = event.target.checked;

  // Nếu gift option là "all", thì tick/untick tất cả gifts trong group
  if (giftGroup.option.toLowerCase() === "all") {
    if (isChecked) {
      // Tick tất cả gifts trong group này
      giftGroup.items.forEach((giftItem) => {
        selectedComboItems.value[giftItem.id] = giftItem.qty || 1;
      });
    } else {
      // Untick tất cả gifts trong group này
      giftGroup.items.forEach((giftItem) => {
        selectedComboItems.value[giftItem.id] = 0;
      });
    }
    return;
  }

  // Logic cũ cho các trường hợp khác (1_trong_2, 2_trong_3, etc.)
  if (isChecked) {
    // Kiểm tra xem còn có thể chọn thêm gift không
    if (canSelectMoreGifts(giftGroup)) {
      selectedComboItems.value[item.id] = item.qty || 1;
    } else {
      // Không cho chọn thêm, revert checkbox
      event.target.checked = false;
    }
  } else {
    // Bỏ chọn
    selectedComboItems.value[item.id] = 0;
  }
}

function handleProcessCombo() {
  // Lấy danh sách item đã chọn và số lượng, theo đúng thứ tự hiển thị
  const selectedList = [];
  groupProducts.value.forEach((group) => {
    group.items.forEach((item) => {
      const qty = selectedComboItems.value[item.id] || 0;
      if (qty > 0) {
        selectedList.push({
          itemId: item.id,
          quantity: qty,
          promotionId: promotonIdRef.value, // Lấy promotionId từ modal
        });
      }
    });
  });
  const finalData = {
    cartId: cartIdOfOpenPromotionModal.value, // Lấy id từ cart nếu có
    items: selectedList,
  };
  console.log("Dữ liệu combo:", finalData);

  api
    .post("/cart/addCombo", finalData)
    .then((response) => {
      console.log("Combo processed successfully:", response.data);
      // Cập nhật lại giỏ hàng sau khi gửi
      loadCart();
    })
    .catch((error) => {
      console.error("Error processing combo:", error);
    });
  // Gửi selectedList về backend ở đây
}

// Helper functions cho gift selection
function calculateCurrentGiftTotal(group) {
  let total = 0;
  group.items.forEach((item) => {
    if (item.isGift) {
      total += selectedComboItems.value[item.id] || 0;
    }
  });
  return total;
}

function getMaxGiftAllowed(group) {
  // Tìm giftOption trong group
  const giftItem = group.items.find((item) => item.giftOption);
  if (giftItem && giftItem.giftOption) {
    // Parse format "2_trong_3" để lấy số 2
    const match = giftItem.giftOption.match(/(\d+)_trong_\d+/);
    return match ? parseInt(match[1]) : 1;
  }
  return 1;
}

function hasGiftInGroup(group) {
  return group.items.some((item) => item.isGift);
}

function getCurrentGiftSelection(group) {
  return calculateCurrentGiftTotal(group);
}

// ...existing code...

const isAllSelected = computed(() => {
  if (cart.value.length === 0) return false;

  // Lọc ra những item có thể chọn được (không phải combo null hoặc combo không hợp lệ)
  const selectableItems = cart.value.filter((item) => {
    // Nếu không có comboGroupId thì có thể chọn
    if (!item.comboGroupId) return true;

    // Nếu có comboGroupId nhưng promotions null hoặc không hợp lệ thì không thể chọn
    if (!item.promotions || !isPromotionValid(item.promotions)) return false;

    return true;
  });

  return (
    selectableItems.length > 0 &&
    selectableItems.every((item) => selectedItems.value.includes(item.id))
  );
});

// Computed để đếm số lượng sản phẩm hợp lệ được chọn
const validSelectedItemsCount = computed(() => {
  return selectedItems.value.filter((selectedId) => {
    const item = cart.value.find((cartItem) => cartItem.id === selectedId);
    if (!item) return false;

    // Nếu không có comboGroupId thì có thể chọn
    if (!item.comboGroupId) return true;

    // Nếu có comboGroupId nhưng promotions null hoặc không hợp lệ thì không thể chọn
    if (!item.promotions || !isPromotionValid(item.promotions)) return false;

    return true;
  }).length;
});

function toggleSelectAll(e) {
  if (e.target.checked) {
    // Chỉ chọn những item có thể chọn được
    const selectableItems = cart.value.filter((item) => {
      // Nếu không có comboGroupId thì có thể chọn
      if (!item.comboGroupId) return true;

      // Nếu có comboGroupId nhưng promotions null hoặc không hợp lệ thì không thể chọn
      if (!item.promotions || !isPromotionValid(item.promotions)) return false;

      return true;
    });
    selectedItems.value = selectableItems.map((item) => item.id);
  } else {
    selectedItems.value = [];
  }
}
async function loadCart() {
  try {
    const response = await cartService.getCart();

    console.log("Cart loaded:", response);
    if (authService.isLogged()) {
      cart.value = response.map((item) => ({
        id: item.id,
        productItemId: item.productItems,
        name: item.name,
        price: item.price,
        quantity: item.qty,
        image: item.mainImageUrl,
        promotions: item.promotions || "",
        sku: item.sku || "", // Thêm sku nếu có
        comboGroup: item.comboGroup, // Thêm comboGroup nếu có
        comboQty: item.comboQty, // Thêm comboQty nếu có
        comboGroupId: item.comboGroupId, // Thêm comboGroupId nếu có
        isGift: item.isGift || false, // Thêm isGift nếu có
        custom: item.custom || false, // Thêm custom nếu có
      }));
      authService.setCart(0);
      authService.updateCart(cart.value.reduce((sum, item) => sum + item.quantity, 0));

      // Chỉ chọn những item có thể chọn được (không phải combo null hoặc combo không hợp lệ)
      const selectableItems = cart.value.filter((item) => {
        // Nếu không có comboGroupId thì có thể chọn
        if (!item.comboGroupId) return true;
        // Nếu có comboGroupId nhưng promotions null hoặc không hợp lệ thì không thể chọn
        if (!item.promotions || !isPromotionValid(item.promotions)) return false;
        return true;
      });
      selectedItems.value = selectableItems.map((item) => item.id);
    } else {
      cart.value = response.map((item) => ({
        id: item.id,
        productItemId: item.productItems,
        name: item.name,
        price: item.price,
        quantity: item.qty,
        image: item.mainImageUrl,
        promotions: item.promotions || "",
        sku: item.sku || "", // Thêm sku nếu có
        comboGroup: item.comboGroup, // Thêm comboGroup nếu có
        comboQty: item.comboQty, // Thêm comboQty nếu có
        comboGroupId: item.comboGroupId, // Thêm comboGroupId nếu co
        isGift: item.isGift || false, // Thêm isGift nếu có
        custom: item.custom || false, // Thêm custom nếu có
      }));

      // Chỉ chọn những item có thể chọn được (không phải combo null hoặc combo không hợp lệ)
      const selectableItems = cart.value.filter((item) => {
        // Nếu không có comboGroupId thì có thể chọn
        if (!item.comboGroupId) return true;
        // Nếu có comboGroupId nhưng promotions null hoặc không hợp lệ thì không thể chọn
        if (!item.promotions || !isPromotionValid(item.promotions)) return false;
        return true;
      });
      selectedItems.value = selectableItems.map((item) => item.productItems);
    }
  } catch (error) {
    console.error("Failed to load cart:", error);
  }
}

// Xóa sản phẩm
function removeItem(item) {
  cart.value = cart.value.filter((i) => i.id !== item.id);
  selectedItems.value = selectedItems.value.filter((id) => id !== item.id);
  cart.value = cart.value.filter((i) => i.productItemId !== item.productItemId);
  selectedItems.value = selectedItems.value.filter((id) => id !== item.productItemId);

  if (authService.isLogged()) {
    //✅ Call API to remove from backend
    api
      .delete(`/cart/${item.id}`)
      .then(() => {
        console.log("Item removed successfully");
        // Cập nhật lại giỏ hàng sau khi xóa
        authService.updateCart(-item.quantity);
        loadCart();
      })
      .catch((error) => {
        console.error("Error removing item:", error);
      });
  } else {
    let tempCart = JSON.parse(localStorage.getItem("cart")) ?? [];
    tempCart = tempCart.filter((i) => i.productItems !== item.productItemId);
    localStorage.setItem("cart", JSON.stringify(tempCart));
  }
}
// Tăng số lượng
// Tăng số lượng sản phẩm đơn lẻ
function increaseQty(item) {
  // Nếu là sản phẩm combo thì không xử lý ở đây
  if (item.comboGroup) return;

  if (authService.isLogged()) {
    handleUpdateQuantityCartWhileLogin(item, "increase");
    // Tìm đúng item theo id cart
    const cartItem = cart.value.find((c) => c.id === item.id);
    if (cartItem) {
      cartItem.quantity++;
      authService.updateCart(1);
    }
  } else {
    let tempLocalList = JSON.parse(localStorage.getItem("cart")) ?? [];
    tempLocalList = tempLocalList.map((cartItem) => {
      if (cartItem.id === item.id) {
        cartItem.qty++;
      }
      return cartItem;
    });
    localStorage.setItem("cart", JSON.stringify(tempLocalList));
    const cartItem = cart.value.find((c) => c.id === item.id);
    if (cartItem) cartItem.quantity++;
  }
}

// Giảm số lượng hoặc xóa sản phẩm đơn lẻ
function decreaseQty(item) {
  // Nếu là sản phẩm combo thì không xử lý ở đây
  if (item.comboGroup) return;

  if (item.quantity > 1) {
    if (authService.isLogged()) {
      handleUpdateQuantityCartWhileLogin(item, "decrease");
      const cartItem = cart.value.find((c) => c.id === item.id);
      if (cartItem) {
        cartItem.quantity--;
        authService.updateCart(-1);
      }
    } else {
      let tempLocalList = JSON.parse(localStorage.getItem("cart")) ?? [];
      tempLocalList = tempLocalList.map((cartItem) => {
        if (cartItem.id === item.id && cartItem.qty > 1) {
          cartItem.qty--;
        }
        return cartItem;
      });
      localStorage.setItem("cart", JSON.stringify(tempLocalList));
      const cartItem = cart.value.find((c) => c.id === item.id);
      if (cartItem) cartItem.quantity--;
    }
  } else {
    handleUpdateQuantityCartWhileLogin(item, "decrease");
    removeItem(item);
  }
}

const selectedTotal = computed(() => {
  let sum = 0;
  const countedComboGroups = new Set();
  // Gom nhóm combo theo comboGroupId
  const allComboGroups = {};
  cart.value.forEach((item) => {
    if (item.comboGroupId && item.promotions && item.promotions.type === "COMBO") {
      const groupKey = item.comboGroupId;
      if (!allComboGroups[groupKey]) allComboGroups[groupKey] = [];
      allComboGroups[groupKey].push(item);
    }
  });

  cart.value.forEach((item) => {
    if (!selectedItems.value.includes(item.id)) return;

    // SẢN PHẨM LẺ: không có comboGroupId
    if (!item.comboGroupId) {
      // Nếu là DISCOUNT và promotion hợp lệ
      if (
        item.promotions &&
        item.promotions.type === "DISCOUNT" &&
        isPromotionValid(item.promotions)
      ) {
        sum += (item.price - item.promotions.discountValue) * item.quantity;
      } else {
        sum += item.price * item.quantity;
      }
      return;
    }

    // SẢN PHẨM COMBO
    if (
      item.promotions &&
      item.promotions.type === "COMBO" &&
      item.promotions.comboPrice > 0 &&
      isPromotionValid(item.promotions)
    ) {
      const groupKey = item.comboGroupId;
      if (countedComboGroups.has(groupKey)) return;

      // Nếu đủ combo: cộng comboPrice * comboQty
      if (
        allComboGroups[groupKey] &&
        allComboGroups[groupKey].every((i) => selectedItems.value.includes(i.id))
      ) {
        sum += item.promotions.comboPrice * (item.comboQty || 1);
        countedComboGroups.add(groupKey);
        return;
      } else {
        // Nếu chưa đủ combo: cộng giá lẻ từng sản phẩm đã chọn trong nhóm này
        allComboGroups[groupKey].forEach((i) => {
          if (selectedItems.value.includes(i.id)) {
            sum += i.price * i.quantity;
          }
        });
        countedComboGroups.add(groupKey);
        return;
      }
    } else if (item.comboGroupId) {
      // Trường hợp combo không hợp lệ (hết hạn/hết hàng): tính giá lẻ
      const groupKey = item.comboGroupId;
      if (!countedComboGroups.has(groupKey)) {
        const allComboGroups = {};
        cart.value.forEach((cartItem) => {
          if (
            cartItem.comboGroupId &&
            cartItem.promotions &&
            cartItem.promotions.type === "COMBO"
          ) {
            const gKey = cartItem.comboGroupId;
            if (!allComboGroups[gKey]) allComboGroups[gKey] = [];
            allComboGroups[gKey].push(cartItem);
          }
        });

        if (allComboGroups[groupKey]) {
          allComboGroups[groupKey].forEach((i) => {
            if (selectedItems.value.includes(i.id)) {
              sum += i.price * i.quantity;
            }
          });
        }
        countedComboGroups.add(groupKey);
      }
    }
  });
  return sum;
});
// Gửi dữ liệu thanh toán
function checkout() {
  // Chỉ lấy những sản phẩm hợp lệ được chọn
  const selectedProducts = cart.value.filter((item) => {
    if (!selectedItems.value.includes(item.id)) return false;

    // Nếu không có comboGroupId thì có thể chọn
    if (!item.comboGroupId) return true;

    // Nếu có comboGroupId nhưng promotions null hoặc không hợp lệ thì không thể chọn
    if (!item.promotions || !isPromotionValid(item.promotions)) return false;

    return true;
  });

  router.push({
    name: "CheckoutPage",
    query: {
      products: JSON.stringify(selectedProducts),
    },
  });
}

// Tải giỏ hàng khi trang được mount
onMounted(() => {
  loadCart();
});

// watch(() => selectedItems.value, (newvalue) => {
//   console.log(selectedProducts[newvalue])
// })
</script>

<style scoped>
/* Container và Layout */
.cart-container {
  background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
  min-height: 100vh;
}

.cart-title {
  font-weight: 700;
  color: #2c3e50;
  font-size: 2rem;
}

.cart-stats .badge {
  font-size: 0.9rem;
  font-weight: 700;
  background: linear-gradient(135deg, #0d6efd 0%, #6610f2 100%);
  padding: 0.6rem 1.2rem;
  box-shadow: 0 4px 15px rgba(13, 110, 253, 0.3);
  border: 2px solid rgba(255, 255, 255, 0.2);
  animation: count-badge 3s ease-in-out infinite;
}

@keyframes count-badge {
  0%,
  100% {
    transform: scale(1);
  }
  50% {
    transform: scale(1.05);
  }
}

/* Select All Section */
.select-all-section {
  border: 2px dashed #dee2e6;
  transition: all 0.3s ease;
}

.select-all-section:hover {
  border-color: #0d6efd;
  background-color: #f8f9ff !important;
}

.form-check-input:checked {
  background-color: #0d6efd;
  border-color: #0d6efd;
}

/* Cart Items Container */
.cart-items-container {
  max-height: 80vh;
  overflow-y: auto;
  overflow-x: hidden;
  padding-right: 0.5rem;
}

.cart-items-container::-webkit-scrollbar {
  width: 6px;
}

.cart-items-container::-webkit-scrollbar-track {
  background: #f1f1f1;
  border-radius: 10px;
}

.cart-items-container::-webkit-scrollbar-thumb {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 10px;
}

/* Combo Card Styles */
.combo-card-wrapper {
  transition: box-shadow 0.2s ease;
}

.combo-card {
  border: 1px solid rgba(0, 0, 0, 0.08);
  transition: border-color 0.2s ease;
  background: white;
}

.combo-header {
  background: linear-gradient(135deg, #ff6b6b 0%, #ee5a24 100%);
  color: white;
  position: relative;
  overflow: hidden;
}

.combo-gradient-bg {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: linear-gradient(45deg, rgba(255, 255, 255, 0.1) 0%, transparent 100%);
}

.combo-icon-wrapper {
  width: 40px;
  height: 40px;
  background: rgba(255, 255, 255, 0.2);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.2rem;
}

.combo-badge {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  padding: 0.5rem 1.2rem;
  border-radius: 30px;
  font-weight: 700;
  font-size: 0.8rem;
  text-transform: uppercase;
  letter-spacing: 1px;
  box-shadow: 0 4px 15px rgba(102, 126, 234, 0.3);
  border: 2px solid rgba(255, 255, 255, 0.2);
}

.combo-title {
  color: white;
  font-weight: 700;
  font-size: 1.1rem;
}

.combo-description {
  color: rgba(255, 255, 255, 0.9);
  font-size: 0.9rem;
}

/* Status Badges */
.status-badge {
  padding: 0.5rem 1rem;
  border-radius: 25px;
  font-size: 0.75rem;
  font-weight: 700;
  text-transform: uppercase;
  letter-spacing: 0.8px;
  box-shadow: 0 3px 10px rgba(0, 0, 0, 0.2);
  border: 2px solid rgba(255, 255, 255, 0.3);
}

.status-expired {
  background: linear-gradient(135deg, #ff6b6b 0%, #ee5a24 100%);
  color: white;
  animation: pulse-danger 2s ease-in-out infinite;
}

.status-out-of-stock {
  background: linear-gradient(135deg, #feca57 0%, #ff9ff3 100%);
  color: #333;
  animation: flash 1.5s ease-in-out infinite;
}

.status-inactive {
  background: linear-gradient(135deg, #778ca3 0%, #2d3436 100%);
  color: white;
}

.status-not-started {
  background: linear-gradient(135deg, #74b9ff 0%, #0984e3 100%);
  color: white;
  animation: glow-blue 2s ease-in-out infinite alternate;
}

.status-deleted {
  background: linear-gradient(135deg, #636e72 0%, #2d3436 100%);
  color: white;
  position: relative;
  overflow: hidden;
}

.status-deleted::before {
  content: "";
  position: absolute;
  top: 0;
  left: -100%;
  width: 100%;
  height: 100%;
  background: linear-gradient(
    45deg,
    transparent 40%,
    rgba(255, 255, 255, 0.3) 50%,
    transparent 60%
  );
  animation: sweep 2s ease-in-out infinite;
}

@keyframes sweep {
  0% {
    left: -100%;
  }
  50% {
    left: 100%;
  }
  100% {
    left: 100%;
  }
}

@keyframes pulse-danger {
  0%,
  100% {
    box-shadow: 0 3px 10px rgba(255, 107, 107, 0.3);
  }
  50% {
    box-shadow: 0 5px 20px rgba(255, 107, 107, 0.6);
  }
}

@keyframes flash {
  0%,
  100% {
    opacity: 1;
  }
  50% {
    opacity: 0.7;
  }
}

@keyframes glow-blue {
  0% {
    box-shadow: 0 3px 10px rgba(116, 185, 255, 0.3);
  }
  100% {
    box-shadow: 0 5px 20px rgba(116, 185, 255, 0.6);
  }
}

.combo-remove-btn {
  border-radius: 50%;
  width: 40px;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: rgba(255, 255, 255, 0.1);
  border: 2px solid rgba(255, 255, 255, 0.2);
  color: white;
}

/* Combo Controls */
.combo-controls {
  background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
}

.quantity-controls {
  display: flex;
  align-items: center;
  background: white;
  border-radius: 25px;
  padding: 0.2rem;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.quantity-controls button {
  width: 35px;
  height: 35px;
  border-radius: 50%;
  border: none;
  display: flex;
  align-items: center;
  justify-content: center;
}

.quantity-display {
  min-width: 40px;
  text-align: center;
  font-weight: 700;
  color: #2c3e50;
  font-size: 1.1rem;
}

.combo-total .total-label {
  color: #6c757d;
  font-weight: 600;
}

.combo-total .total-price {
  color: #dc3545;
  font-weight: 700;
  font-size: 1.3rem;
}

.combo-total .total-tbd {
  color: #6c757d;
  font-style: italic;
}

/* Combo Items */
.combo-item {
  border-bottom: 1px solid #e9ecef;
}

.combo-item:last-child {
  border-bottom: none;
}

.combo-item-gift {
  background: linear-gradient(135deg, #d4edda 0%, #c3e6cb 100%);
  border-left: 4px solid #28a745;
}

.combo-item-invalid {
  background: linear-gradient(135deg, #f8d7da 0%, #f5c6cb 100%);
  opacity: 0.7;
}

.item-image-wrapper {
  position: relative;
  overflow: visible;
  border-radius: 12px;
}

.item-image {
  width: 100px;
  height: 125px; /* Tỷ lệ 4:5 cho ảnh 600x750 */
  object-fit: cover;
  border-radius: 12px;
}

.gift-overlay {
  position: absolute;
  top: -8px;
  right: -8px;
  width: 28px;
  height: 28px;
  background: linear-gradient(135deg, #28a745 0%, #20c997 100%);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-size: 0.75rem;
  box-shadow: 0 4px 12px rgba(40, 167, 69, 0.4);
  border: 3px solid white;
  animation: sparkle-rotate 3s ease-in-out infinite;
}

@keyframes sparkle-rotate {
  0%,
  100% {
    transform: rotate(0deg) scale(1);
    box-shadow: 0 4px 12px rgba(40, 167, 69, 0.4);
  }
  50% {
    transform: rotate(10deg) scale(1.1);
    box-shadow: 0 6px 18px rgba(40, 167, 69, 0.6);
  }
}

.gift-badge {
  background: linear-gradient(135deg, #28a745 0%, #20c997 100%);
  color: white;
  padding: 0.15rem 0.5rem;
  border-radius: 12px;
  font-size: 0.6rem;
  font-weight: 600;
  text-transform: uppercase;
  letter-spacing: 0.2px;
  box-shadow: 0 1px 4px rgba(40, 167, 69, 0.2);
  border: 1px solid rgba(255, 255, 255, 0.1);
  animation: sparkle 4s ease-in-out infinite;
  display: inline-block;
  white-space: nowrap;
}

.custom-badge {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  padding: 0.15rem 0.5rem;
  border-radius: 12px;
  font-size: 0.6rem;
  font-weight: 600;
  text-transform: uppercase;
  letter-spacing: 0.2px;
  box-shadow: 0 1px 4px rgba(102, 126, 234, 0.2);
  border: 1px solid rgba(255, 255, 255, 0.1);
  animation: glow-custom 4s ease-in-out infinite;
  display: inline-block;
  white-space: nowrap;
}

@keyframes glow-custom {
  0%,
  100% {
    box-shadow: 0 1px 4px rgba(102, 126, 234, 0.2);
    transform: scale(1);
  }
  50% {
    box-shadow: 0 2px 6px rgba(102, 126, 234, 0.3);
    transform: scale(1.01);
  }
}

@keyframes sparkle {
  0%,
  100% {
    transform: scale(1);
    box-shadow: 0 1px 4px rgba(40, 167, 69, 0.2);
  }
  50% {
    transform: scale(1.01);
    box-shadow: 0 2px 6px rgba(40, 167, 69, 0.3);
  }
}

.quantity-badge {
  background: linear-gradient(135deg, #0d6efd 0%, #6610f2 100%);
  color: white;
  padding: 0.6rem 1.2rem;
  border-radius: 25px;
  font-weight: 800;
  font-size: 1rem;
  box-shadow: 0 4px 15px rgba(13, 110, 253, 0.3);
  border: 2px solid rgba(255, 255, 255, 0.2);
  min-width: 50px;
  text-align: center;
}

.gift-price .original-price {
  font-size: 0.8rem;
  color: #6c757d;
}

.gift-price .free-price {
  color: #28a745;
  font-weight: 700;
  font-size: 0.9rem;
}

/* Product Card Styles */
.product-card-wrapper {
  transition: box-shadow 0.2s ease;
}

.product-card {
  transition: border-color 0.2s ease;
  background: white;
  border: 1px solid rgba(0, 0, 0, 0.08);
}

.product-card:hover {
  border-color: #0d6efd;
}

.product-image-container {
  position: relative;
}

@keyframes pulse-glow {
  0%,
  100% {
    box-shadow: 0 0 0 0 rgba(220, 53, 69, 0.4);
    transform: scale(1);
  }
  50% {
    box-shadow: 0 0 0 8px rgba(220, 53, 69, 0);
    transform: scale(1.02);
  }
}

.product-image-wrapper {
  width: 100px;
  height: 125px; /* Tỷ lệ 4:5 cho ảnh 600x750 */
  border-radius: 12px;
  overflow: visible;
  position: relative;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
}

.product-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 12px;
}

/* Promotion Badge - Corner positioning */
.promotion-badge-corner {
  position: absolute;
  top: -12px;
  right: -12px;
  z-index: 20;
}

.promotion-badge-corner .btn {
  font-size: 0.7rem;
  padding: 0.3rem 0.7rem;
  border: 2px solid white;
  background: linear-gradient(135deg, #dc3545 0%, #c82333 100%);
  box-shadow: 0 4px 12px rgba(220, 53, 69, 0.4);
  font-weight: 700;
  text-transform: uppercase;
  letter-spacing: 0.5px;
  position: relative;
  z-index: 21;
  transition: all 0.3s ease;
}

.promotion-badge-corner .btn:hover {
  background: linear-gradient(135deg, #c82333 0%, #a71e2a 100%);
  transform: scale(1.05);
  box-shadow: 0 6px 20px rgba(220, 53, 69, 0.6);
}

/* Enhanced promotion button hover effect */
.promotion-btn-hover {
  position: relative;
  cursor: pointer;
  font-size: 0.7rem !important;
  padding: 0.3rem 0.6rem !important;
  animation: bounce-gentle 2s ease-in-out infinite;
  transition: all 0.3s ease;
}

.promotion-btn-hover:hover {
  background: linear-gradient(135deg, #e74c3c 0%, #c0392b 100%) !important;
  transform: scale(1.15) translateY(-3px) !important;
  box-shadow: 0 10px 30px rgba(220, 53, 69, 0.8) !important;
  border-color: #fff !important;
  animation: none;
}

.promotion-btn-hover:hover .bi-chevron-right {
  transform: translateX(4px);
  transition: transform 0.2s ease;
}

.promotion-btn-hover:hover .bi-gift-fill {
  animation: wiggle 0.5s ease-in-out;
}

/* Size class for extra small button */
.btn-xs {
  padding: 0.3rem 0.6rem;
  font-size: 0.7rem;
  line-height: 1.2;
}

@keyframes bounce-gentle {
  0%,
  20%,
  50%,
  80%,
  100% {
    transform: translateY(0);
  }
  40% {
    transform: translateY(-2px);
  }
  60% {
    transform: translateY(-1px);
  }
}

@keyframes wiggle {
  0%,
  100% {
    transform: rotate(0deg);
  }
  25% {
    transform: rotate(-5deg);
  }
  75% {
    transform: rotate(5deg);
  }
}

@keyframes pulse-promotion {
  0%,
  100% {
    box-shadow: 0 4px 15px rgba(220, 53, 69, 0.4);
  }
  50% {
    box-shadow: 0 6px 20px rgba(220, 53, 69, 0.6);
  }
}

.promotion-badge-corner .badge {
  font-size: 0.7rem;
  padding: 0.4rem 0.7rem;
  border: 2px solid white;
  font-weight: 700;
  background: linear-gradient(135deg, #ffc107 0%, #e0a800 100%);
  color: #333;
  text-transform: uppercase;
  letter-spacing: 0.5px;
  box-shadow: 0 4px 12px rgba(255, 193, 7, 0.4);
  position: relative;
  z-index: 21;
}

/* Legacy promotion badge for backward compatibility */
.promotion-badge {
  position: absolute;
  top: -5px;
  right: -5px;
  z-index: 10;
}

.combo-promo button {
  background: linear-gradient(135deg, #dc3545 0%, #c82333 100%);
  border: none;
  box-shadow: 0 4px 15px rgba(220, 53, 69, 0.4);
  font-size: 0.75rem;
  font-weight: 600;
  padding: 0.4rem 0.8rem;
  text-transform: uppercase;
  letter-spacing: 0.5px;
  animation: pulse-glow 3s ease-in-out infinite;
}

.combo-promo button:hover {
  background: linear-gradient(135deg, #c82333 0%, #a71e2a 100%);
  transform: scale(1.05);
  box-shadow: 0 6px 20px rgba(220, 53, 69, 0.5);
}

.discount-promo .badge {
  background: linear-gradient(135deg, #ffc107 0%, #e0a800 100%);
  color: #333;
  font-size: 0.75rem;
  font-weight: 700;
  padding: 0.5rem 0.8rem;
  text-transform: uppercase;
  letter-spacing: 0.5px;
  box-shadow: 0 4px 15px rgba(255, 193, 7, 0.4);
  border: 2px solid rgba(255, 255, 255, 0.8);
  animation: shake 2s ease-in-out infinite;
}

@keyframes pulse-glow {
  0%,
  100% {
    transform: scale(1);
    box-shadow: 0 4px 15px rgba(220, 53, 69, 0.4);
  }
  50% {
    transform: scale(1.05);
    box-shadow: 0 6px 25px rgba(220, 53, 69, 0.6);
  }
}

@keyframes shake {
  0%,
  100% {
    transform: translateX(0);
  }
  10%,
  30%,
  50%,
  70%,
  90% {
    transform: translateX(-2px);
  }
  20%,
  40%,
  60%,
  80% {
    transform: translateX(2px);
  }
}

.product-name {
  color: #2c3e50;
  font-weight: 600;
  font-size: 1rem;
}

.product-sku {
  font-family: "Courier New", monospace;
  background: #f8f9fa;
  padding: 0.2rem 0.5rem;
  border-radius: 5px;
  font-size: 0.8rem;
}

.original-price {
  font-size: 0.9rem;
}

.current-price {
  font-size: 1.1rem;
}

.quantity-controls-single {
  display: flex;
  align-items: center;
  background: white;
  border-radius: 25px;
  padding: 0.2rem;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.quantity-controls-single button {
  width: 35px;
  height: 35px;
  border-radius: 50%;
  border: none;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.3s ease;
}

.quantity-controls-single button:hover:not(:disabled) {
  transform: scale(1.1);
  background-color: #0d6efd !important;
  color: white !important;
}

.item-total .total-price {
  color: #dc3545;
  font-size: 1.2rem;
}

/* Checkout Section */
.checkout-sticky {
  position: sticky;
  top: 2rem;
}

.checkout-summary {
  background: white;
}

.bg-gradient-primary {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%) !important;
}

.summary-label {
  color: #6c757d;
  font-weight: 500;
}

.summary-value {
  font-weight: 700;
  color: #2c3e50;
}

.checkout-actions button {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border: none;
  font-weight: 600;
  letter-spacing: 0.5px;
  transition: all 0.3s ease;
}

.checkout-actions button:hover:not(:disabled) {
  background: linear-gradient(135deg, #5a6fd8 0%, #6a42a0 100%);
  transform: translateY(-2px);
  box-shadow: 0 8px 25px rgba(102, 126, 234, 0.4);
}

.checkout-actions button:disabled {
  background: linear-gradient(135deg, #6c757d 0%, #495057 100%);
  cursor: not-allowed;
}

/* Modal Styles */
.modal-content {
  border: none;
  overflow: hidden;
}

.promotion-item {
  transition: all 0.3s ease;
}

.promotion-item:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 25px rgba(0, 0, 0, 0.1) !important;
}

.promotion-price {
  text-shadow: 0 2px 4px rgba(220, 53, 69, 0.2);
}

.combo-base-image {
  width: 100px;
  height: 125px;
  object-fit: cover;
  border-radius: 12px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
}

.combo-base-name {
  color: #2c3e50;
  font-weight: 700;
}

.quantity-controls-combo {
  background: white;
  border-radius: 25px;
  padding: 0.2rem;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.quantity-controls-combo button {
  width: 30px;
  height: 30px;
  border-radius: 50%;
  border: none;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.3s ease;
}

.quantity-controls-combo button:hover:not(:disabled) {
  transform: scale(1.1);
  background-color: #0d6efd !important;
  color: white !important;
}

/* Suggestions Section */
.suggestions-section {
  background: linear-gradient(135deg, #f8f9fa 0%, #ffffff 100%);
  border-radius: 20px;
  padding: 3rem 2rem;
}

.suggestions-title {
  color: #2c3e50;
  font-weight: 700;
  font-size: 2rem;
}

.suggestions-subtitle {
  font-size: 1.1rem;
}

.suggestion-card {
  transition: all 0.3s ease;
  background: linear-gradient(145deg, #ffffff 0%, #f8f9fa 100%);
}

.suggestion-card:hover {
  transform: translateY(-8px);
  box-shadow: 0 15px 35px rgba(0, 0, 0, 0.1) !important;
}

.suggestion-image-wrapper {
  position: relative;
  overflow: hidden;
}

.suggestion-image {
  width: 100%;
  height: 200px;
  object-fit: cover;
  transition: transform 0.3s ease;
}

.suggestion-overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: linear-gradient(
    135deg,
    rgba(102, 126, 234, 0.8) 0%,
    rgba(118, 75, 162, 0.8) 100%
  );
  display: flex;
  align-items: center;
  justify-content: center;
  opacity: 0;
  transition: all 0.3s ease;
}

.suggestion-card:hover .suggestion-overlay {
  opacity: 1;
}

.suggestion-card:hover .suggestion-image {
  transform: scale(1.1);
}

.suggestion-name {
  color: #2c3e50;
  font-weight: 600;
}

.suggestion-old-price {
  font-size: 0.85rem;
}

.suggestion-current-price {
  font-size: 1.1rem;
}

/* Responsive */
@media (max-width: 768px) {
  .cart-title {
    font-size: 1.5rem;
  }

  .combo-header {
    padding: 1rem !important;
  }

  .combo-title {
    font-size: 1rem;
  }

  .product-image,
  .item-image {
    width: 80px;
    height: 100px; /* Tỷ lệ 4:5 cho mobile */
  }

  .checkout-sticky {
    position: relative;
    top: 0;
    margin-top: 2rem;
  }

  .suggestions-section {
    padding: 2rem 1rem;
  }

  .suggestions-title {
    font-size: 1.5rem;
  }
}

/* Empty Cart State */
.empty-cart {
  text-align: center;
  padding: 4rem 2rem;
  color: #6c757d;
}

.empty-cart-icon {
  font-size: 4rem;
  color: #dee2e6;
  margin-bottom: 1rem;
}

/* Loading States */
.loading-shimmer {
  background: linear-gradient(90deg, #f0f0f0 25%, transparent 37%, #f0f0f0 63%);
  background-size: 400% 100%;
  animation: shimmer 1.5s ease-in-out infinite;
}

@keyframes shimmer {
  0% {
    background-position: 100% 0;
  }
  100% {
    background-position: -100% 0;
  }
}

/* Badge Improvements */
.badge {
  transition: all 0.3s ease;
}

.badge:hover {
  transform: translateY(-2px);
}

/* Modal Badge Improvements */
.modal .badge {
  font-weight: 700;
  letter-spacing: 0.5px;
}

.modal .badge.bg-warning {
  background: linear-gradient(135deg, #ffc107 0%, #e0a800 100%) !important;
  color: #333;
  box-shadow: 0 3px 10px rgba(255, 193, 7, 0.3);
}

.modal .badge.bg-success {
  background: linear-gradient(135deg, #28a745 0%, #20c997 100%) !important;
  box-shadow: 0 3px 10px rgba(40, 167, 69, 0.3);
}

.modal .badge.bg-secondary {
  background: linear-gradient(135deg, #6c757d 0%, #495057 100%) !important;
  box-shadow: 0 3px 10px rgba(108, 117, 125, 0.3);
}

.modal .badge.bg-info {
  background: linear-gradient(135deg, #17a2b8 0%, #138496 100%) !important;
  box-shadow: 0 3px 10px rgba(23, 162, 184, 0.3);
}

/* Additional Badge Effects */
.badge.bg-success.bg-opacity-25 {
  background: linear-gradient(
    135deg,
    rgba(40, 167, 69, 0.15) 0%,
    rgba(32, 201, 151, 0.15) 100%
  ) !important;
  color: #28a745 !important;
  border: 1px solid rgba(40, 167, 69, 0.3);
}

.badge.bg-secondary.bg-opacity-25 {
  background: linear-gradient(
    135deg,
    rgba(108, 117, 125, 0.15) 0%,
    rgba(73, 80, 87, 0.15) 100%
  ) !important;
  color: #6c757d !important;
  border: 1px solid rgba(108, 117, 125, 0.3);
}

/* Combo Details Styles */
.combo-product-image {
  width: 120px;
  height: 150px;
  object-fit: cover;
  border-radius: 12px;
  transition: all 0.3s ease;
}

/* Image containers */
.image-container {
  position: relative;
  overflow: hidden;
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
  width: 120px;
  height: 150px;
  flex-shrink: 0;
}

.image-overlay {
  display: none;
}

/* Required product cards */
.required-product-card {
  border-radius: 12px;
  background: #ffffff;
  border: 1px solid #e9ecef;
}

/* Gift product cards */
.gift-product-card {
  border-radius: 12px;
  cursor: pointer;
  background: #ffffff;
  border: 1px solid #e9ecef;
  transition: all 0.3s ease;
}

.gift-product-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
  border-color: #28a745;
}

.gift-product-card.selected-gift {
  background: #f8fff9;
  border: 2px solid #28a745;
  box-shadow: 0 2px 10px rgba(40, 167, 69, 0.15);
}

/* Selection elements */
.selection-checkbox {
  position: absolute;
  top: 15px;
  left: 15px;
  z-index: 10;
}

.selection-checkbox .form-check-input {
  width: 1.3em;
  height: 1.3em;
  border: 2px solid #dee2e6;
  border-radius: 6px;
  cursor: pointer;
  transition: all 0.2s ease;
}

.selection-checkbox .form-check-input:hover {
  border-color: #28a745;
  box-shadow: 0 0 0 0.1rem rgba(40, 167, 69, 0.25);
}

.selection-checkbox .form-check-input:checked {
  background-color: #28a745;
  border-color: #28a745;
}

.selection-checkbox .form-check-input:disabled {
  cursor: not-allowed;
  opacity: 0.5;
}

.selected-indicator {
  position: absolute;
  top: 15px;
  right: 15px;
  z-index: 10;
}

.selected-badge {
  width: 28px;
  height: 28px;
  background: #28a745;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
}

/* Section headers */
.section-header {
  padding: 20px;
  background: #f8f9fa;
  border-radius: 8px;
  border-left: 4px solid;
  margin-bottom: 1.5rem;
}

.required-section .section-header {
  border-left-color: #007bff;
}

.gift-section .section-header {
  border-left-color: #28a745;
}

.section-icon {
  width: 45px;
  height: 45px;
  background: #ffffff;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  flex-shrink: 0;
}

/* Progress badge */
.progress-badge .badge {
  font-size: 0.85rem;
  font-weight: 600;
  border-radius: 6px;
}

/* Typography improvements */
.product-name {
  font-size: 1rem;
  line-height: 1.4;
  color: #2c3e50;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  line-clamp: 2;
  -webkit-box-orient: vertical;
}

.product-sku .badge {
  font-size: 0.75rem;
  border-radius: 6px;
}

.price {
  font-size: 1rem;
  font-weight: 700;
}

/* Product info container */
.product-info {
  min-width: 0;
  flex: 1;
  overflow: hidden;
}

.product-info.min-width-0 {
  min-width: 0;
}

.product-image-wrapper {
  flex-shrink: 0;
}

.product-meta {
  margin-top: auto;
}

/* Card body responsive */
.card-body {
  display: flex;
  flex-direction: column;
  height: 100%;
  min-height: 180px;
}

.d-flex.align-items-start {
  height: 100%;
  gap: 1rem;
}

/* Badges and labels */
.badge.bg-gradient {
  border-radius: 10px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
}

/* Section spacing */
.required-section,
.gift-section {
  margin-bottom: 2.5rem;
}

.gift-section:last-child {
  margin-bottom: 0;
}

/* Responsive adjustments */
@media (max-width: 992px) {
  .image-container {
    width: 100px;
    height: 125px;
  }

  .combo-product-image {
    width: 100px;
    height: 125px;
  }

  .card-body {
    min-height: 160px;
  }

  /* Cart images for tablet */
  .product-image-wrapper,
  .item-image-wrapper {
    width: 90px;
    height: 112px;
  }

  .product-image,
  .item-image {
    width: 90px;
    height: 112px;
  }

  .combo-base-image {
    width: 90px;
    height: 112px;
  }
}

@media (max-width: 768px) {
  .image-container {
    width: 90px;
    height: 112px;
  }

  .combo-product-image {
    width: 90px;
    height: 112px;
  }

  .section-header {
    padding: 15px;
  }

  .section-icon {
    width: 35px;
    height: 35px;
  }

  .product-name {
    font-size: 0.9rem;
  }

  .price {
    font-size: 0.9rem;
  }

  .selection-checkbox {
    top: 10px;
    left: 10px;
  }

  .selected-indicator {
    top: 10px;
    right: 10px;
  }

  .card-body {
    min-height: 140px;
    padding: 1rem;
  }

  .d-flex.align-items-start {
    gap: 0.75rem;
  }
}

/* Layout fixes */
.card-body {
  display: flex;
  flex-direction: column;
  height: 100%;
  min-height: 180px;
}

.d-flex.align-items-start {
  height: 100%;
  gap: 1rem;
}

.product-image-wrapper {
  flex-shrink: 0;
}

/* Simplified styling - remove unnecessary animations */
.required-product-card,
.gift-product-card {
  transition: transform 0.2s ease, box-shadow 0.2s ease, border-color 0.2s ease;
}

/* Accessibility */
@media (prefers-reduced-motion: reduce) {
  *,
  *::before,
  *::after {
    animation-duration: 0.01ms !important;
    animation-iteration-count: 1 !important;
    transition-duration: 0.01ms !important;
  }
}

/* High contrast mode */
@media (prefers-contrast: high) {
  .combo-card,
  .product-card,
  .suggestion-card {
    border: 2px solid #000;
  }

  .combo-header {
    background: #000 !important;
    color: #fff !important;
  }
}
</style>
