<template>
  <div class="checkout-page">
    <div class="hero-section">
      <div class="container">
        <div class="hero-content">
          <h1 class="hero-title">
            <i class="bi bi-bag-check me-3"></i>
            Thanh toán đơn hàng
          </h1>
          <p class="hero-subtitle">Hoàn tất đơn hàng của bạn chỉ với vài bước đơn giản</p>
        </div>
      </div>
    </div>

    <div class="main-content">
      <div class="container">
        <!-- Progress Steps -->
        <div class="progress-container">
          <div class="progress-steps">
            <div
              v-for="(step, index) in steps"
              :key="index"
              :class="[
                'step-item',
                {
                  active: currentTab === index,
                  completed: currentTab > index,
                },
              ]"
              @click="navigateToStep(index)"
            >
              <div class="step-circle">
                <i v-if="currentTab > index" class="bi bi-check-lg"></i>
                <span v-else>{{ index + 1 }}</span>
              </div>
              <div class="step-label">{{ step.title }}</div>
            </div>
          </div>
        </div>

        <div class="row g-4">
          <!-- Main Form Column -->
          <div class="col-lg-8">
            <div class="form-card">
              <!-- Step 1: Personal Information -->
              <div v-if="currentTab === 0" class="step-content">
                <div class="step-header">
                  <h3 class="step-title">
                    <i class="bi bi-person-circle me-3"></i>
                    Thông tin giao hàng
                  </h3>
                  <p class="step-description">
                    Vui lòng kiểm tra và xác nhận thông tin giao hàng
                  </p>
                </div>

                <!-- Address Section -->
                <div class="address-section">
                  <div class="section-label">
                    <i class="bi bi-geo-alt-fill me-2"></i>
                    Địa chỉ nhận hàng
                  </div>

                  <div class="address-card">
                    <div v-if="defaultAddressData" class="address-info">
                      <div class="recipient-info">
                        <h5 class="recipient-name">
                          {{ defaultAddressData.recipientName }}
                        </h5>
                        <span class="recipient-phone">
                          <i class="bi bi-telephone me-1"></i>
                          (+84) {{ defaultAddressData.phone }}
                        </span>
                      </div>
                      <div class="address-details">
                        <i class="bi bi-house me-2"></i>
                        {{ defaultAddressData.street }}, {{ defaultAddressData.ward }},
                        {{ defaultAddressData.district }},
                        {{ defaultAddressData.province }}
                      </div>
                      <div class="address-actions">
                        <span v-if="defaultAddressData?.default" class="default-badge">
                          <i class="bi bi-star-fill me-1"></i>
                          Mặc định
                        </span>
                        <button class="change-address-btn" @click="goToAddress">
                          <i class="bi bi-pencil-square me-1"></i>
                          Thay đổi
                        </button>
                      </div>
                      <div v-if="leadtime">
                        <i class="bi bi-clock-history"></i>
                        Giao hàng dự kiến: <strong>{{ formattedLeadtime }}</strong>
                      </div>
                    </div>
                    <div v-else class="no-address">
                      <i class="bi bi-exclamation-triangle"></i>
                      <span> Chưa có địa chỉ giao hàng</span>
                      <button class="next-step-btn mt-2" @click="goToAddress">
                        Thêm địa chỉ
                      </button>
                    </div>
                  </div>
                </div>

                <!-- Coupon Section -->
                <div class="coupon-section card p-4 mb-4 shadow-sm rounded-4">
                  <h5 class="mb-3"><i class="bi bi-percent me-2"></i> Chọn Mã Ưu Đãi</h5>

                  <div class="row g-3 mb-3">
                    <div class="col-md-6">
                      <button
                        @click="openFreeshipModal"
                        class="btn btn-outline-primary w-100 shadow-sm"
                        style="border-width: 2px; font-weight: 600"
                      >
                        <i class="bi bi-truck me-2"></i>Chọn mã miễn phí vận chuyển
                      </button>
                    </div>
                    <div class="col-md-6">
                      <button
                        @click="openDiscountModal"
                        class="btn btn-outline-success w-100 shadow-sm"
                        style="border-width: 2px; font-weight: 600"
                      >
                        <i class="bi bi-percent me-2"></i>Chọn mã giảm giá đơn hàng
                      </button>
                    </div>
                  </div>

                  <div
                    v-if="selectedFreeshipCoupon || selectedDiscountCoupon"
                    class="alert alert-info border-0 shadow-sm mt-3"
                    style="
                      font-size: 0.95rem;
                      background: linear-gradient(135deg, #e3f2fd 0%, #f3e5f5 100%);
                    "
                  >
                    <div v-if="selectedFreeshipCoupon" class="mb-2">
                      <i class="bi bi-truck text-primary me-2"></i>
                      <span class="text-primary fw-semibold"
                        >Đã áp dụng mã freeship:</span
                      >
                      <strong class="text-primary">{{
                        selectedFreeshipCoupon.code
                      }}</strong
                      ><br />
                      <small class="text-muted"
                        >Miễn phí vận chuyển lên đến
                        <strong class="text-success">{{
                          formatCurrency(selectedFreeshipCoupon.discountValue)
                        }}</strong></small
                      >
                    </div>
                    <div v-if="selectedDiscountCoupon">
                      <i class="bi bi-percent text-success me-2"></i>
                      <span class="text-success fw-semibold"
                        >Đã áp dụng mã giảm giá:</span
                      >
                      <strong class="text-success">{{
                        selectedDiscountCoupon.code
                      }}</strong
                      ><br />
                      <small class="text-muted"
                        >Giảm tối đa
                        <strong class="text-success">{{
                          formatCurrency(selectedDiscountCoupon.discountValue)
                        }}</strong></small
                      >
                    </div>
                  </div>
                </div>

                <CouponModal
                  v-if="showFreeshipModal"
                  title="Chọn mã freeship"
                  :coupons="freeshipCoupons"
                  :selected-coupon="selectedFreeshipCoupon"
                  @close="closeModals"
                  @select="selectCouponFromModal"
                  @remove="removeFreeshipCoupon"
                />

                <CouponModal
                  v-if="showDiscountModal"
                  title="Chọn mã giảm giá"
                  :coupons="discountCoupons"
                  :selected-coupon="selectedDiscountCoupon"
                  @close="closeModals"
                  @select="selectCouponFromModal"
                  @remove="removeDiscountCoupon"
                />

                <button class="next-step-btn" @click="currentTab = 1">
                  <span>Tiếp tục</span>
                  <i class="bi bi-arrow-right ms-2"></i>
                </button>
              </div>

              <!-- Step 2: Payment Method -->
              <div v-if="currentTab === 1" class="step-content">
                <div class="step-header">
                  <h3 class="step-title">
                    <i class="bi bi-credit-card me-3"></i>
                    Phương thức thanh toán
                  </h3>
                  <p class="step-description">
                    Chọn phương thức thanh toán phù hợp với bạn
                  </p>
                </div>

                <div class="payment-methods">
                  <div
                    v-for="method in dropdownPaymentMethods"
                    :key="method.id"
                    :class="[
                      'payment-method-card',
                      { selected: paymentMethod?.id === method.id },
                    ]"
                    @click="paymentMethod = method"
                  >
                    <div class="payment-method-content">
                      <div class="payment-method-info">
                        <div class="payment-icon">
                          <i :class="getPaymentIcon(method.name)"></i>
                        </div>
                        <div class="payment-details">
                          <h5 class="payment-name">{{ method.name }}</h5>
                          <p class="payment-description">
                            {{ getPaymentDescription(method.name) }}
                          </p>
                        </div>
                      </div>
                      <div class="payment-radio">
                        <input
                          type="radio"
                          :value="method"
                          v-model="paymentMethod"
                          :id="`payment-${method.id}`"
                        />
                        <label :for="`payment-${method.id}`" class="radio-label"></label>
                      </div>
                    </div>
                  </div>
                </div>

                <div class="step-actions">
                  <button class="prev-step-btn" @click="currentTab = 0">
                    <i class="bi bi-arrow-left me-2"></i>
                    Quay lại
                  </button>
                  <button class="next-step-btn" @click="currentTab = 2">
                    <span>Xem lại đơn hàng</span>
                    <i class="bi bi-arrow-right ms-2"></i>
                  </button>
                </div>
              </div>

              <!-- Step 3: Order Confirmation -->
              <div v-if="currentTab === 2" class="step-content">
                <div class="step-header">
                  <h3 class="step-title">
                    <i class="bi bi-clipboard-check me-3"></i>
                    Xác nhận đơn hàng
                  </h3>
                  <p class="step-description">
                    Kiểm tra lại thông tin trước khi đặt hàng
                  </p>
                </div>

                <div class="confirmation-sections">
                  <!-- Order Info -->
                  <div class="confirmation-card">
                    <h4 class="confirmation-title">
                      <i class="bi bi-info-circle me-2"></i>
                      Thông tin đơn hàng
                    </h4>
                    <div class="confirmation-content">
                      <div class="info-row">
                        <span class="info-label">Người nhận:</span>
                        <span class="info-value">{{
                          defaultAddressData?.recipientName || "—"
                        }}</span>
                      </div>
                      <div class="info-row">
                        <span class="info-label">Số điện thoại:</span>
                        <span class="info-value">{{
                          defaultAddressData?.phone || "—"
                        }}</span>
                      </div>
                      <div class="info-row">
                        <span class="info-label">Địa chỉ:</span>
                        <span class="info-value">{{ fullAddress }}</span>
                      </div>
                      <div class="info-row">
                        <span class="info-label">Giảm giá:</span>
                        <span class="info-value">{{
                          selectedCoupon?.code || "Không áp dụng"
                        }}</span>
                      </div>
                      <div class="info-row">
                        <span class="info-label">Thanh toán:</span>
                        <span class="info-value">{{ paymentMethod?.name || "—" }}</span>
                      </div>
                      <!-- Ghi chú cho cửa hàng -->
                      <div class="mb-2">
                        <label for="note" class="form-label">
                          <i class="bi bi-chat-left-text me-1"></i>
                          Ghi chú cho cửa hàng
                        </label>
                        <textarea
                          id="note"
                          class="form-control"
                          rows="3"
                          placeholder="Nhập ghi chú (ví dụ: giao buổi sáng, không gọi điện)..."
                          v-model="orderNote"
                        ></textarea>
                      </div>
                    </div>
                  </div>

                  <!-- Product List -->
                  <div class="confirmation-card">
                    <h1 class="confirmation-title">
                      <i class="bi bi-box-seam me-2"></i>
                      Sản phẩm đã chọn
                    </h1>
                    <!-- COMBO -->
                    <div
                      v-for="(group, groupId) in groupedProducts.comboGroups"
                      :key="groupId"
                      class="mb-4 border border-primary border-2 p-4 rounded-3 shadow-sm bg-light"
                    >
                      <div class="fw-bold text-white bg-primary rounded px-3 py-2 mb-3">
                        <i class="bi bi-gift me-2"></i>Combo:
                        <span>{{ group.items[0].promotions.name }}</span>
                      </div>

                      <!-- Sản phẩm trong combo -->
                      <ul class="list-unstyled ps-3 small">
                        <li
                          v-for="item in group.items.filter((i) => !i.isGift)"
                          :key="item.id"
                          class="d-flex align-items-start mb-2"
                        >
                          <img
                            :src="item.image"
                            alt="ảnh sản phẩm"
                            class="me-3 rounded"
                            style="width: 80px; height: 100px; object-fit: cover"
                          />
                          <div>
                            {{ item.name }} <br />
                            <span class="text-muted small">{{ item.sku }}</span
                            ><br />
                            <div>
                              {{ item.price.toLocaleString() }}₫
                              <span class="ms-3">× {{ item.quantity }}</span>
                            </div>
                          </div>
                        </li>
                      </ul>

                      <!-- Nếu có sản phẩm tặng -->
                      <div v-if="group.items.some((i) => i.isGift)" class="mt-3 ps-3">
                        <div class="fw-bold text-success mb-2">
                          <i class="bi bi-stars me-2"></i>Sản phẩm tặng kèm
                        </div>
                        <ul class="list-unstyled ps-3 small">
                          <li
                            v-for="gift in group.items.filter((i) => i.isGift)"
                            :key="gift.id"
                            class="d-flex align-items-start mb-2"
                          >
                            <img
                              :src="gift.image"
                              alt="ảnh quà tặng"
                              class="me-3 rounded"
                              style="width: 70px; height: 90px; object-fit: cover"
                            />
                            <div>
                              {{ gift.name }} <br />
                              <span class="text-muted small">{{ gift.sku }}</span
                              ><br />
                              <span class="badge bg-success">Tặng kèm</span>
                            </div>
                          </li>
                        </ul>
                      </div>

                      <div class="ps-3 mt-2">
                        <i class="bi bi-box me-2"></i>Số lượng combo:
                        <strong>{{ group.comboQty }}</strong
                        ><br />
                        <i class="bi bi-currency-dollar me-2"></i>Giá gốc:
                        <s>{{ group.originalTotal.toLocaleString() }}₫</s><br />
                        <i class="bi bi-tag me-2"></i>Tiết kiệm:
                        <span class="fw-semibold text-success">
                          {{
                            (
                              group.originalTotal -
                              group.comboPrice * group.comboQty
                            ).toLocaleString()
                          }}₫ </span
                        ><br />
                        <i class="bi bi-check-circle me-2"></i>Tổng :
                        <strong class="text-success">
                          {{ (group.comboPrice * group.comboQty).toLocaleString() }}₫
                        </strong>
                      </div>
                    </div>

                    <!-- DISCOUNT -->
                    <div v-if="groupedProducts.discountItems.length" class="mt-3">
                      <div
                        v-for="item in groupedProducts.discountItems"
                        :key="item.id"
                        class="mb-4 border border-warning border-2 p-4 rounded-3 shadow-sm bg-light"
                      >
                        <div class="fw-bold text-white bg-warning rounded px-3 py-2 mb-3">
                          <i class="bi bi-lightning-fill me-2"></i>Khuyến mãi:
                          {{ item.promotions.name }}
                        </div>
                        <ul class="list-unstyled ps-3 small">
                          <li class="d-flex align-items-start mb-2">
                            <img
                              :src="item.image"
                              alt="ảnh sản phẩm"
                              class="me-3 rounded"
                              style="width: 80px; height: 100px; object-fit: cover"
                            />
                            <div>
                              {{ item.name }} <br />
                              <span class="text-muted small">{{ item.sku }}</span
                              ><br />
                              <div>
                                {{ item.price.toLocaleString() }}₫
                                <span class="ms-3">× {{ item.quantity }}</span>
                              </div>
                            </div>
                          </li>
                        </ul>
                        <div class="ps-3">
                          <i class="bi bi-currency-dollar me-2"></i>Giá gốc:
                          <s>{{ (item.price * item.quantity).toLocaleString() }}₫</s
                          ><br />
                          <i class="bi bi-tag me-2"></i>Tiết kiệm:
                          <span class="fw-semibold text-success">
                            {{
                              (
                                item.promotions.discountValue * item.quantity
                              ).toLocaleString()
                            }}₫ </span
                          ><br />
                          <i class="bi bi-check-circle me-2"></i>Tổng:
                          <strong class="text-success">
                            {{
                              (
                                (item.price - item.promotions.discountValue) *
                                item.quantity
                              ).toLocaleString()
                            }}₫
                          </strong>
                        </div>
                      </div>
                    </div>

                    <!-- NORMAL ITEMS -->
                    <div v-if="groupedProducts.normalItems.length" class="mt-3">
                      <div
                        v-for="item in groupedProducts.normalItems"
                        :key="item.id"
                        class="mb-3 border p-3 rounded"
                      >
                        <ul class="list-unstyled ps-3 small">
                          <li class="d-flex align-items-start mb-2">
                            <img
                              :src="item.image"
                              alt="ảnh sản phẩm"
                              class="me-3 rounded"
                              style="width: 80px; height: 100px; object-fit: cover"
                            />
                            <div>
                              <strong style="font-size: larger">{{ item.name }} </strong>
                              <br />
                              <span class="text-muted large">{{ item.sku }}</span
                              ><br />
                              <div>
                                {{ item.price.toLocaleString() }}₫
                                <span class="ms-3">× {{ item.quantity }}</span>
                              </div>
                            </div>
                          </li>
                        </ul>
                        <div class="ps-3">
                          <i class="bi bi-check-circle me-2 text-success"></i>Thành tiền:
                          <strong style="color: rgb(102, 126, 234)"
                            >{{ (item.price * item.quantity).toLocaleString() }}₫</strong
                          >
                        </div>
                      </div>
                    </div>
                    <!-- CUSTOM PRODUCTS -->

                    <div
                      v-if="customProductsData && customProductsData.length"
                      class="mt-4"
                    >
                      <hr />
                      <div class="fw-bold mb-3" style="font-size: 24px">
                        <i class="bi bi-palette me-2"></i>
                        Sản phẩm tùy chỉnh
                      </div>

                      <div
                        v-for="customProduct in customProductsData"
                        :key="customProduct.id"
                        class="mb-4 border p-4 rounded-3 shadow-sm bg-light"
                      >
                        <!-- Thông tin sản phẩm gốc -->
                        <div class="d-flex align-items-start mb-3">
                          <img
                            :src="customProduct.image"
                            :alt="customProduct.name"
                            class="product-img me-3 rounded"
                          />
                          <div>
                            <strong>{{ customProduct.name }}</strong
                            ><br />
                            <span class="text-muted large">{{ customProduct.sku }}</span
                            ><br />
                          </div>
                        </div>

                        <!-- Danh sách các phác thảo đã chọn -->
                        <div class="mt-3">
                          <div class="fw-bold mb-2">
                            <i class="bi bi-brush me-2"></i>
                            Phác thảo đã chọn:
                          </div>

                          <div class="row g-3">
                            <div
                              v-for="draft in customProduct.drafts"
                              :key="draft.customId"
                              class="col-md-6 col-lg-4"
                            >
                              <div class="draft-card border rounded p-2 bg-white">
                                <div class="position-relative">
                                  <img
                                    :src="draft.imageUrl"
                                    :alt="draft.name"
                                    class="draft-img w-100 rounded"
                                  />
                                  <span
                                    class="badge position-absolute top-0 end-0 m-2"
                                    style="
                                      background: linear-gradient(
                                        135deg,
                                        rgb(102, 126, 234) 0%,
                                        rgb(118, 75, 162) 100%
                                      );
                                    "
                                  >
                                    {{ draft.quantity }}
                                  </span>
                                </div>
                                <div class="text-center mt-2">
                                  <div class="fw-semibold small">{{ draft.name }}</div>
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>

                        <!-- Tổng số lượng phác thảo -->
                        <div class="mt-3 pt-3 border-top">
                          <div class="d-flex justify-content-between align-items-center">
                            <div>
                              <i class="bi bi-palette me-2"></i>
                              Tổng số phác thảo:
                              <strong
                                class="text-primary"
                                style="color: rgb(102, 126, 234)"
                              >
                                {{
                                  customProduct.drafts.reduce(
                                    (sum, draft) => sum + draft.quantity,
                                    0
                                  )
                                }}
                              </strong>
                            </div>
                            <div class="fw-bold" style="color: rgb(102, 126, 234)">
                              <i class="bi bi-check-circle me-2 text-success"></i>
                              Đã xác nhận thiết kế
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>

                <div class="step-actions">
                  <button class="prev-step-btn" @click="currentTab = 1">
                    <i class="bi bi-arrow-left me-2"></i>
                    Quay lại
                  </button>
                  <button class="confirm-order-btn" @click="confirmOrder">
                    <i class="bi bi-check-circle me-2"></i>
                    Xác nhận đặt hàng
                  </button>
                </div>
              </div>
            </div>
          </div>

          <!-- Order Summary Column -->
          <div class="col-lg-4">
            <div class="summary-card">
              <div class="summary-header">
                <h4 class="summary-title">
                  <i class="bi bi-receipt me-2"></i>
                  Chi tiết thanh toán
                </h4>
              </div>

              <div class="summary-content">
                <!-- Product Items -->
                <div class="summary-section">
                  <h6 class="section-title">Sản phẩm đã chọn</h6>
                  <!-- Combo -->
                  <div
                    v-for="(group, groupId) in groupedProducts.comboGroups"
                    :key="groupId"
                    class="mb-2 small"
                  >
                    <ul class="list-unstyled mb-1">
                      <li
                        v-for="item in group.items.filter((i) => !i.isGift)"
                        :key="item.id"
                      >
                        <span>{{ item.name }} x{{ item.quantity }}</span>
                      </li>
                    </ul>
                    <div class="d-flex justify-content-between ps-3">
                      <span><i class="bi bi-gift-fill"></i> Combo:</span>
                      <span
                        >{{ (group.comboPrice * group.comboQty).toLocaleString() }}₫</span
                      >
                    </div>
                  </div>

                  <!-- Discount -->
                  <div
                    v-for="item in groupedProducts.discountItems"
                    :key="item.id"
                    class="mb-2 small"
                  >
                    <div class="d-flex justify-content-between">
                      <span>{{ item.name }} x{{ item.quantity }}</span>
                      <span
                        >{{
                          (
                            (item.price - item.promotions.discountValue) *
                            item.quantity
                          ).toLocaleString()
                        }}₫</span
                      >
                    </div>
                  </div>

                  <!-- Sản phẩm thường -->
                  <div
                    v-for="item in groupedProducts.normalItems"
                    :key="item.id"
                    class="mb-2 small"
                  >
                    <div class="d-flex justify-content-between">
                      <span>{{ item.name }} x{{ item.quantity }}</span>
                      <span>{{ (item.price * item.quantity).toLocaleString() }}₫</span>
                    </div>
                  </div>
                </div>

                <!-- Price Breakdown -->
                <div class="summary-section">
                  <div class="price-row">
                    <span class="price-label">Tạm tính:</span>
                    <span class="price-value">{{ totalPrice.toLocaleString() }}₫</span>
                  </div>
                  <div class="price-row">
                    <span class="price-label">Phí vận chuyển:</span>
                    <span class="price-value">{{ shippingFee.toLocaleString() }}₫</span>
                  </div>
                  <div class="price-row discount" v-if="freeshipDiscount > 0">
                    <span class="price-label">Giảm phí vận chuyển:</span>
                    <span class="price-value"
                      >-{{ freeshipDiscount.toLocaleString() }}₫</span
                    >
                  </div>

                  <div class="price-row discount" v-if="discountAmount > 0">
                    <span class="price-label">Giảm giá sản phẩm:</span>
                    <span class="price-value"
                      >-{{ discountAmount.toLocaleString() }}₫</span
                    >
                  </div>
                </div>

                <!-- Total -->
                <div class="summary-total">
                  <div class="total-row">
                    <span class="total-label">Tổng cộng:</span>
                    <span class="total-value">{{ finalPrice.toLocaleString() }}₫</span>
                  </div>
                </div>

                <!-- Security Info -->
                <div class="security-info">
                  <div class="security-item">
                    <i class="bi bi-shield-check"></i>
                    <span>Thanh toán an toàn</span>
                  </div>
                  <div class="security-item">
                    <i class="bi bi-truck"></i>
                    <span>Giao hàng nhanh chóng</span>
                  </div>
                  <div class="security-item">
                    <i class="bi bi-arrow-repeat"></i>
                    <span>Đổi trả miễn phí</span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Success Modal -->
    <div v-if="showSuccess" class="success-modal">
      <div class="success-modal-backdrop" @click="showSuccess = false"></div>
      <div class="success-modal-content">
        <div class="success-icon">
          <i class="bi bi-check-circle-fill"></i>
        </div>
        <h3 class="success-title">Đặt hàng thành công!</h3>
        <p class="success-message">
          Cảm ơn bạn đã đặt hàng. Chúng tôi sẽ xử lý đơn hàng và giao đến bạn sớm nhất có
          thể.
        </p>
        <div class="success-actions">
          <button class="continue-shopping-btn" @click="router.push('/')">
            <i class="bi bi-house me-2"></i>
            Về trang chủ
          </button>
          <button class="view-orders-btn" @click="router.push('/order-list')">
            <i class="bi bi-list-check me-2"></i>
            Xem đơn hàng
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, watch } from "vue";
import { useRoute, useRouter } from "vue-router";
import api, { authService } from "../../Configs/api";
import { onBeforeRouteLeave } from "vue-router";
import { dropDown } from "../../Configs/DropDownList";
import { catchUserEvent } from "../../Configs/handleCatchUserProductEvent";
import CouponModal from "../Module/CouponModal.vue";
const dropdownShippingMethods = ref([]);
const shippingMethod = ref(null);
const router = useRouter();
const route = useRoute();
const currentTab = ref(0);
const selectedProducts = ref([]);
const selectedCoupon = ref(null);
const paymentMethod = ref(null);
const showSuccess = ref(false);
const defaultAddressData = ref(null);
const dropdownPaymentMethods = ref([]);
const couponCodeInput = ref("");
const couponError = ref("");
const timeSpent = ref(0);
const selectedFreeshipCoupon = ref(null);
const selectedDiscountCoupon = ref(null);
const showFreeshipModal = ref(false);
const showDiscountModal = ref(false);
const orderNote = ref("");
const freeshipCoupons = ref([]);
const discountCoupons = ref([]);
let timer = null;

const openFreeshipModal = async () => {
  showFreeshipModal.value = true;
  await fetchCoupons();
};

const openDiscountModal = async () => {
  showDiscountModal.value = true;
  await fetchCoupons();
};

const closeModals = () => {
  showFreeshipModal.value = false;
  showDiscountModal.value = false;
};

const removeFreeshipCoupon = () => {
  selectedFreeshipCoupon.value = null;
  couponCodeInput.value = "";
  couponError.value = "";
};

const removeDiscountCoupon = () => {
  selectedDiscountCoupon.value = null;
  couponCodeInput.value = "";
  couponError.value = "";
};

function calculateSellingPrice(item, groupedProducts) {
  // Nếu là quà tặng => sellingPrice = 0
  if (item.isGift) {
    return 0;
  }

  if (item.promotions?.type === "DISCOUNT" && item.promotions.discountValue) {
    return item.price - item.promotions.discountValue;
  }

  if (item.promotions?.type === "COMBO" && item.comboGroupId) {
    const group = groupedProducts.comboGroups[item.comboGroupId];

    // Tổng số lượng sản phẩm trong combo group (trừ quà tặng nếu có)
    const totalUnits = group.items.reduce(
      (sum, i) => sum + (i.isGift ? 0 : i.quantity),
      0
    );

    const comboQty = item.comboQty || 1;
    const totalComboPrice = group.comboPrice * comboQty;

    // Giá chia đều cho mỗi đơn vị sp thường
    const unitPrice = totalComboPrice / totalUnits;

    return unitPrice;
  }

  return item.price;
}

const fetchCoupons = async () => {
  try {
    const normalTotal = groupedProducts.value.normalItems.reduce(
      (total, item) => total + item.price * item.quantity,
      0
    );

    const res = await api.get("/Coupons/available", {
      params: {
        hasCombo: Object.keys(groupedProducts.value.comboGroups).length > 0,
        hasPromotionItems: groupedProducts.value.discountItems.length > 0,
        normalTotal: normalTotal,
        totalPrice: totalPrice.value,
      },
    });

    console.log("✅ Đã lấy mã giảm giá:", res.data);
    freeshipCoupons.value = res.data.freeshipCoupons;
    discountCoupons.value = res.data.discountCoupons;
  } catch (err) {
    console.error("Lỗi khi lấy mã giảm giá:", err);
  }
};

function formatCurrency(value) {
  if (!value && value !== 0) return "0đ";
  return new Intl.NumberFormat("vi-VN", {
    style: "currency",
    currency: "VND",
  }).format(value);
}

// Steps configuration
const steps = ref([
  { title: "Thông tin giao hàng", icon: "person-circle" },
  { title: "Phương thức thanh toán", icon: "credit-card" },
  { title: "Xác nhận đơn hàng", icon: "clipboard-check" },
]);

// ==== Methods ====
function navigateToStep(index) {
  if (index <= currentTab.value || index === currentTab.value + 1) {
    currentTab.value = index;
  }
}

function getPaymentIcon(methodName) {
  const iconMap = {
    COD: "bi-cash-coin",
    Banking: "bi-credit-card",
    Momo: "bi-phone",
    VNPay: "bi-credit-card-2-front",
    PayPal: "bi-paypal",
  };
  return iconMap[methodName] || "bi-credit-card";
}

function getPaymentDescription(methodName) {
  const descMap = {
    COD: "Thanh toán khi nhận hàng",
    Banking: "Chuyển khoản ngân hàng",
    Momo: "Ví điện tử Momo",
    VNPay: "Cổng thanh toán VNPay",
    PayPal: "Thanh toán qua PayPal",
  };
  return descMap[methodName] || "Phương thức thanh toán an toàn";
}

// ==== Computed Properties ====
const totalPrice = computed(() => {
  const comboTotal = Object.values(groupedProducts.value.comboGroups).reduce(
    (sum, group) => sum + group.comboPrice * group.comboQty,
    0
  );

  const discountTotal = groupedProducts.value.discountItems.reduce(
    (sum, item) =>
      sum + (item.price - (item.promotions?.discountValue || 0)) * item.quantity,
    0
  );

  const normalTotal = groupedProducts.value.normalItems.reduce(
    (sum, item) => sum + item.price * item.quantity,
    0
  );

  return comboTotal + discountTotal + normalTotal;
});

// Helper đọc boolean an toàn từ nhiều kiểu trả về
function readBool(obj, ...keys) {
  for (const k of keys) {
    const v = obj?.[k];
    if (typeof v === "boolean") return v;
    if (typeof v === "string") {
      const s = v.toLowerCase();
      if (s === "true") return true;
      if (s === "false") return false;
    }
    if (typeof v === "number") return v === 1; // phòng khi BE trả 0/1
  }
  return false;
}

const discountAmount = computed(() => {
  const coupon = selectedDiscountCoupon.value;
  if (!coupon) return 0;

  // đọc allowVoucher
  const allowVoucher = readBool(
    coupon,
    "isAllowVoucher",
    "allowVoucher",
    "is_allow_voucher"
  );

  // chọn baseTotal đúng
  const normalSum = groupedProducts.value.normalItems.reduce(
    (sum, item) => sum + item.price * item.quantity,
    0
  );
  const baseTotal = allowVoucher ? totalPrice.value : normalSum;

  if (!allowVoucher && normalSum <= 0) return 0;

  // minOrder check
  const minOrder = coupon?.minOrderAmount ?? 0;
  if (!coupon || totalPrice.value < minOrder) return 0;

  if (coupon.discountType === "PERCENT") {
    const normalPrice = groupedProducts.value.normalItems.reduce(
      (sum, item) => sum + item.price * item.quantity,
      0
    );
    const discount = ((coupon.discountValue ?? 0) / 100) * normalPrice;
    return coupon.maxDiscountAmount != null
      ? Math.min(discount, coupon.maxDiscountAmount)
      : discount;
  }

  return coupon.discountValue ?? 0;
});

const freeshipDiscount = computed(() => {
  const coupon = selectedFreeshipCoupon.value;
  const minOrder = coupon?.minOrderAmount ?? 0;
  if (!coupon || totalPrice.value < minOrder) return 0;
  return Math.min(coupon.discountValue ?? 0, shippingFee.value);
});

const finalPrice = computed(
  () =>
    totalPrice.value - discountAmount.value + shippingFee.value - freeshipDiscount.value
);

const fullAddress = computed(() => {
  const addr = defaultAddressData.value;
  return addr ? `${addr.street}, ${addr.ward}, ${addr.district}, ${addr.province}` : "";
});

// ==== Methods ====
function goToAddress() {
  router.push({ name: "shippingaddress", query: { fromCheckout: "1" } });
}
const leadtime = ref(null); // Unix timestamp từ GHN

const formattedLeadtime = computed(() => {
  if (!leadtime.value) return "";
  const date = new Date(leadtime.value * 1000); // GHN trả về giây
  return date.toLocaleDateString("vi-VN", {
    weekday: "long",
    year: "numeric",
    month: "long",
    day: "numeric",
  });
});

const selectCouponFromModal = (coupon) => {
  if (coupon.discountType === "FREESHIP") {
    selectedFreeshipCoupon.value = coupon;
  } else {
    selectedDiscountCoupon.value = coupon;
  }
  couponCodeInput.value = coupon.code;
  couponError.value = "";
  closeModals();
};

function buildOrderPayload() {
  return {
    address: defaultAddressData.value.id,
    discountCouponCode: selectedDiscountCoupon.value?.code || null,
    freeshipCouponCode: selectedFreeshipCoupon.value?.code || null,
    paymentMethodId: paymentMethod.value.id,
    shippingMethodId: shippingMethod.value.id,
    estimatedShippingFee: shippingFee.value,
    actualShippingFee: shippingFee.value - freeshipDiscount.value,
    discountValue: discountAmount.value || 0,
    finalTotal: finalPrice.value,
    note: orderNote.value,
    orderInfor: `${defaultAddressData.value.recipientName} - ${defaultAddressData.value.phone} - ${fullAddress.value}`,
    products: selectedProducts.value.map((item) => {
      const sellingPrice = calculateSellingPrice(item, groupedProducts.value);
      const total = sellingPrice * item.quantity;
      return {
        productItemId: item.productItemId,
        quantity: item.quantity,
        isGift: item.isGift || false,
        comboId: item.comboId || null,
        comboQty: item.comboQty || null,
        comboGroup: item.comboGroup || null,
        comboGroupId: item.comboGroupId || null,
        couponCode: item.couponCode || null,
        promotions: item.promotions?.id || null,
        priceAtBuy: item.originalPrice || item.price,
        sellingPrice,
        total,
      };
    }),
  };
}

async function confirmOrder() {
  const token = authService.getToken();
  if (!token) {
    alert("Bạn cần đăng nhập trước khi đặt hàng.");
    router.push({ name: "login" });
    return;
  }

  try {
    clearInterval(timer);

    // 🎯 Gửi sự kiện theo dõi từng sản phẩm
    selectedProducts.value.forEach((product) => {
      catchUserEvent({
        id: "",
        eventType: "ORDER",
        positionInList: "",
        timeSpentSeconds: timeSpent.value,
        productItemId: product.id,
      });
    });
    const sumQuantitySelectedProducts = selectedProducts.value.reduce(
      (sum, product) => sum + product.quantity,
      0
    );
    // 📦 Chuẩn bị payload
    const payload = buildOrderPayload();

    console.log("📦 Payload gửi lên:", payload);

    const response = await api.post("/user/Orders", {
      customs: requestCustomData.value,
      order: payload,
    });
    // 🧹 Dọn dẹp sau khi đặt hàng
    localStorage.removeItem("selectedCoupon");
    localStorage.removeItem("checkoutProducts");
    authService.updateCart(-sumQuantitySelectedProducts);
    showSuccess.value = true;
  } catch (err) {
    console.error("🔥 Lỗi đặt hàng:", err.response?.data || err.message);
    alert("Lỗi đặt hàng!");
  }
}
// ==== Lifecycle ====
const customProductsData = ref([]);
const requestCustomData = ref([]);
onMounted(async () => {
  timer = setInterval(() => {
    timeSpent.value++;
  }, 1000);
  dropdownPaymentMethods.value = (await dropDown("PaymentMethods")).content;
  if (route.query.customs) {
    try {
      customProductsData.value = JSON.parse(route.query.customs);
      console.log("Dữ liệu tùy chỉnh:", customProductsData.value);
      customProductsData.value.forEach((item) => {
        requestCustomData.value.push(
          ...item.drafts.map((draft) => ({
            customId: draft.customId,
            qty: draft.quantity,
          }))
        );
      });
      console.log("Dữ liệu yêu cầu:", requestCustomData.value);
    } catch (err) {
      console.error("Lỗi phân tích dữ liệu tùy chỉnh:", err);
    }
  }
  if (route.query.products) {
    try {
      selectedProducts.value = JSON.parse(route.query.products);
      localStorage.setItem("checkoutProducts", JSON.stringify(selectedProducts.value));
    } catch (err) {
      console.error("Lỗi phân tích sản phẩm:", err);
    }
  } else if (!selectedProducts.value.length && localStorage.getItem("checkoutProducts")) {
    selectedProducts.value = JSON.parse(localStorage.getItem("checkoutProducts"));
  }
  // ✅ Load coupon từ localStorage nếu có
  if (localStorage.getItem("selectedCoupon")) {
    selectedCoupon.value = JSON.parse(localStorage.getItem("selectedCoupon"));
  }
  try {
    const { data: addresses } = await api.get("/UserAddresses");
    defaultAddressData.value = addresses.find((addr) => addr.default) || addresses[0];
  } catch (err) {
    console.error("Lỗi lấy địa chỉ:", err);
  }
  const savedTab = localStorage.getItem("checkoutTab");
  if (savedTab !== null) {
    currentTab.value = parseInt(savedTab);
  }
  const savedMethod = localStorage.getItem("paymentMethod");
  if (savedMethod) {
    paymentMethod.value = savedMethod;
  }
  dropdownShippingMethods.value = (await dropDown("ShippingMethods")).content;
  if (dropdownShippingMethods.value.length > 0) {
    shippingMethod.value = dropdownShippingMethods.value[0]; // Gán mặc định
  }

  // Set default payment method
  if (dropdownPaymentMethods.value.length > 0 && !paymentMethod.value) {
    paymentMethod.value = dropdownPaymentMethods.value[0];
  }
});
onBeforeRouteLeave((to, from, next) => {
  const isGoingToShipping = to.name === "shippingaddress";

  // Nếu KHÔNG phải đi đến trang địa chỉ (tức là rời khỏi hoàn toàn checkout)
  if (!isGoingToShipping) {
    // 🧹 Dọn sạch mọi thứ nếu thoát checkout
    localStorage.removeItem("selectedCoupon");
    localStorage.removeItem("checkoutProducts");

    selectedCoupon.value = null;
    selectedProducts.value = [];
    paymentMethod.value = "COD";
    currentTab.value = 0;
  }
  next();
});
const shippingFee = ref(0);

async function fetchShippingFee() {
  if (!defaultAddressData.value || selectedProducts.value.length === 0) return;
  // Kiểm tra xem đã có phí vận chuyển hay không
  if (shippingFee.value > 0) return;
  // Kiểm tra xem địa chỉ đã được chọn chưa

  if (!defaultAddressData.value.ghnDistrictId || !defaultAddressData.value.ghnWardCode) {
    console.warn("❗ Chưa chọn địa chỉ giao hàng đầy đủ.");
    return;
  }
  // Kiểm tra xem có sản phẩm nào được chọn không
  if (selectedProducts.value.length === 0) {
    console.warn("❗ Chưa có sản phẩm nào được chọn.");
    return;
  }
  // Kiểm tra xem có sản phẩm nào có số lượng > 0 không
  if (selectedProducts.value.every((item) => item.quantity <= 0)) {
    console.warn("❗ Tất cả sản phẩm đều có số lượng 0.");
    return;
  }
  // Tạo payload để gửi đến API tính phí vận chuyển
  console.log("📦 Sản phẩm được chọn:", selectedProducts.value);

  try {
    const items = selectedProducts.value.map((item) => ({
      quantity: item.quantity,
      weight: 100,
      length: 10,
      width: 10,
      height: 10,
    }));

    const payload = {
      to_district_id: defaultAddressData.value.ghnDistrictId,
      to_ward_code: defaultAddressData.value.ghnWardCode,
      items: items,
    };

    console.log("📦 Payload gửi tính phí:", payload);

    const { data } = await api.post("/shipping/fee", payload);
    shippingFee.value = data.total;
    leadtime.value = data.leadtime ?? null;
    console.log("✅ Phí vận chuyển:", shippingFee.value);
  } catch (err) {
    console.error("❌ Lỗi lấy phí vận chuyển:", err.response?.data || err.message);
    shippingFee.value = 0;
  }
}

const groupedProducts = computed(() => {
  const comboGroups = {};
  const discountItems = [];
  const normalItems = [];

  for (const item of selectedProducts.value) {
    const promo = item.promotions;

    if (promo && promo.type === "COMBO" && item.comboGroupId) {
      if (!comboGroups[item.comboGroupId]) {
        comboGroups[item.comboGroupId] = {
          items: [],
          comboPrice: promo.comboPrice,
          comboQty: item.comboQty,
        };
      }
      comboGroups[item.comboGroupId].items.push(item);
    } else if (promo && promo.type === "DISCOUNT") {
      discountItems.push(item);
    } else {
      normalItems.push(item);
    }
  }

  // Filter comboGroups: loại nhóm chưa đủ số lượng
  for (const [groupId, group] of Object.entries(comboGroups)) {
    const totalComboItemQty = group.items.reduce((sum, item) => sum + item.quantity, 0);
    const requiredQty = group.comboQty ?? totalComboItemQty;

    if (totalComboItemQty < requiredQty) {
      group.items.forEach((item) => normalItems.push(item));
      delete comboGroups[groupId];
    } else {
      group.originalTotal = group.items.reduce(
        (sum, item) => sum + item.price * item.quantity,
        0
      );
    }
  }

  return {
    comboGroups,
    discountItems,
    normalItems,
  };
});

watch(
  [defaultAddressData, selectedProducts, shippingMethod],
  () => {
    fetchShippingFee();
  },
  { deep: true }
);
watch(
  () => selectedProducts.value,
  () => {
    fetchCoupons();
  },
  { deep: true }
);
</script>

<style scoped>
/* CSS tối giản cho phần custom */
.draft-card {
  transition: transform 0.2s, box-shadow 0.2s;
}

.draft-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

/* Ảnh sản phẩm gốc - tỷ lệ 4:5 */
.product-img {
  width: 80px;
  aspect-ratio: 4/5;
  object-fit: cover;
  border: 1px solid #dee2e6;
}

/* Ảnh phác thảo - tỷ lệ 4:5 */
.draft-img {
  max-width: 205px;
  height: auto;
  aspect-ratio: 4/5;
  object-fit: cover;
  border: 1px solid #dee2e6;
}

/* Responsive cho mobile */
@media (max-width: 768px) {
  .product-img {
    width: 70px;
  }

  .draft-img {
    height: 140px;
  }
}

/* ==================== GLOBAL STYLES ==================== */
.checkout-page {
  font-family: "Inter", "Segoe UI", sans-serif;
  background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
  min-height: 100vh;
}

/* ==================== HERO SECTION ==================== */
.hero-section {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 3rem 0 2rem;
  color: white;
  position: relative;
  overflow: hidden;
}

.hero-section::before {
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: url('data:image/svg+xml,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1000 100" fill="rgba(255,255,255,0.1)"><path d="M0,20 Q250,80 500,20 T1000,20 L1000,0 L0,0 Z"/></svg>')
    repeat-x;
  background-size: 1000px 100px;
  animation: wave 10s infinite linear;
}

@keyframes wave {
  0% {
    background-position-x: 0;
  }

  100% {
    background-position-x: 1000px;
  }
}

.hero-content {
  text-align: center;
  position: relative;
  z-index: 1;
}

.hero-title {
  font-size: 2.5rem;
  font-weight: 700;
  margin-bottom: 1rem;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.hero-subtitle {
  font-size: 1.2rem;
  opacity: 0.9;
  margin: 0;
}

/* ==================== MAIN CONTENT ==================== */
.main-content {
  padding: 2rem 0;
  margin-top: -1rem;
  position: relative;
  z-index: 2;
}

/* ==================== PROGRESS STEPS ==================== */
.progress-container {
  margin-bottom: 3rem;
}

.progress-steps {
  display: flex;
  justify-content: center;
  align-items: center;
  max-width: 600px;
  margin: 0 auto;
  position: relative;
}

.progress-steps::before {
  content: "";
  position: absolute;
  top: 30px;
  left: 20%;
  right: 20%;
  height: 3px;
  background: linear-gradient(to right, #ddd 0%, #ddd 100%);
  z-index: 1;
}

.step-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  flex: 1;
  cursor: pointer;
  transition: all 0.3s ease;
  position: relative;
  z-index: 2;
}

.step-circle {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  background: white;
  border: 3px solid #ddd;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: 700;
  font-size: 1.2rem;
  color: #6c757d;
  transition: all 0.3s ease;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
}

.step-item.active .step-circle {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-color: #667eea;
  color: white;
  transform: scale(1.1);
}

.step-item.completed .step-circle {
  background: linear-gradient(135deg, #10b981 0%, #059669 100%);
  border-color: #10b981;
  color: white;
}

.step-label {
  margin-top: 0.75rem;
  font-weight: 600;
  color: #6c757d;
  text-align: center;
  font-size: 0.9rem;
  transition: color 0.3s ease;
}

.step-item.active .step-label,
.step-item.completed .step-label {
  color: #2d3748;
}

/* ==================== FORM CARD ==================== */
.form-card {
  background: white;
  border-radius: 20px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  border: 1px solid rgba(102, 126, 234, 0.1);
}

.step-content {
  padding: 2.5rem;
}

.step-header {
  margin-bottom: 2rem;
  text-align: center;
}

.step-title {
  font-size: 1.75rem;
  font-weight: 700;
  color: #2d3748;
  margin-bottom: 0.5rem;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.step-description {
  color: #6c757d;
  font-size: 1rem;
  margin: 0;
}

/* ==================== SECTIONS ==================== */
.section-label {
  font-weight: 700;
  color: #2d3748;
  margin-bottom: 1rem;
  display: flex;
  align-items: center;
  font-size: 1.1rem;
}

/* ==================== ADDRESS SECTION ==================== */
.address-section {
  margin-bottom: 2rem;
}

.address-card {
  background: linear-gradient(135deg, #f8f9fa 0%, #ffffff 100%);
  border: 2px solid #e9ecef;
  border-radius: 15px;
  padding: 1.5rem;
  transition: all 0.3s ease;
}

.address-card:hover {
  border-color: rgba(102, 126, 234, 0.3);
  box-shadow: 0 8px 25px rgba(0, 0, 0, 0.1);
}

.address-info {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.recipient-info {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.recipient-name {
  font-size: 1.2rem;
  font-weight: 700;
  color: #2d3748;
  margin: 0;
}

.recipient-phone {
  color: #667eea;
  font-weight: 600;
  display: flex;
  align-items: center;
}

.address-details {
  color: #6c757d;
  line-height: 1.5;
  display: flex;
  align-items: flex-start;
  gap: 0.5rem;
}

.address-actions {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 1rem;
  padding-top: 1rem;
  border-top: 1px solid #e9ecef;
}

.default-badge {
  background: linear-gradient(135deg, #fef3c7 0%, #fde68a 100%);
  color: #92400e;
  padding: 0.25rem 0.75rem;
  border-radius: 20px;
  font-size: 0.8rem;
  font-weight: 600;
  display: flex;
  align-items: center;
  border: 1px solid #f59e0b;
}

.change-address-btn {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border: none;
  padding: 0.5rem 1rem;
  border-radius: 8px;
  font-weight: 600;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
}

.change-address-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 25px rgba(102, 126, 234, 0.3);
}

/* ==================== COUPON SECTION ==================== */
.coupon-section {
  margin-bottom: 2rem;
}

.coupon-input-group {
  margin-bottom: 1rem;
}

.input-wrapper {
  position: relative;
  display: flex;
  align-items: center;
  background: white;
  border: 2px solid #e9ecef;
  border-radius: 12px;
  transition: all 0.3s ease;
  overflow: hidden;
}

.input-wrapper:focus-within {
  border-color: #667eea;
  box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
}

.input-icon {
  position: absolute;
  left: 15px;
  color: #6c757d;
  z-index: 2;
}

.coupon-input {
  flex: 1;
  border: none;
  padding: 15px 15px 15px 45px;
  font-size: 1rem;
  background: transparent;
  outline: none;
}

.apply-coupon-btn {
  background: linear-gradient(135deg, #10b981 0%, #059669 100%);
  color: white;
  border: none;
  padding: 15px 25px;
  font-weight: 600;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
}

.apply-coupon-btn:hover {
  background: linear-gradient(135deg, #059669 0%, #047857 100%);
}

.coupon-message {
  display: flex;
  align-items: center;
  padding: 0.75rem 1rem;
  border-radius: 8px;
  font-weight: 500;
  margin-top: 0.5rem;
}

.coupon-message.success {
  background: linear-gradient(135deg, #d1fae5 0%, #a7f3d0 100%);
  color: #065f46;
  border: 1px solid #10b981;
}

.coupon-message.error {
  background: linear-gradient(135deg, #fee2e2 0%, #fecaca 100%);
  color: #991b1b;
  border: 1px solid #ef4444;
}

.coupon-message.warning {
  background: linear-gradient(135deg, #fef3c7 0%, #fde68a 100%);
  color: #92400e;
  border: 1px solid #f59e0b;
}

/* ==================== PAYMENT METHODS ==================== */
.payment-methods {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  margin-bottom: 2rem;
}

.payment-method-card {
  background: white;
  border: 2px solid #e9ecef;
  border-radius: 15px;
  padding: 1.5rem;
  cursor: pointer;
  transition: all 0.3s ease;
  position: relative;
}

.payment-method-card:hover {
  border-color: rgba(102, 126, 234, 0.3);
  box-shadow: 0 8px 25px rgba(0, 0, 0, 0.1);
}

.payment-method-card.selected {
  border-color: #667eea;
  background: linear-gradient(
    135deg,
    rgba(102, 126, 234, 0.05) 0%,
    rgba(118, 75, 162, 0.05) 100%
  );
}

.payment-method-content {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.payment-method-info {
  display: flex;
  align-items: center;
  gap: 1rem;
}

.payment-icon {
  width: 50px;
  height: 50px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-size: 1.5rem;
}

.payment-details h5 {
  font-weight: 700;
  color: #2d3748;
  margin: 0 0 0.25rem 0;
}

.payment-details p {
  color: #6c757d;
  margin: 0;
  font-size: 0.9rem;
}

.payment-radio input {
  position: absolute;
  opacity: 0;
}

.radio-label {
  width: 24px;
  height: 24px;
  border: 2px solid #ddd;
  border-radius: 50%;
  position: relative;
  cursor: pointer;
  transition: all 0.3s ease;
}

.payment-method-card.selected .radio-label {
  border-color: #667eea;
}

.payment-method-card.selected .radio-label::after {
  content: "";
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 12px;
  height: 12px;
  background: #667eea;
  border-radius: 50%;
}

/* ==================== CONFIRMATION ==================== */
.confirmation-sections {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
  margin-bottom: 2rem;
}

.confirmation-card {
  background: linear-gradient(135deg, #f8f9fa 0%, #ffffff 100%);
  border: 1px solid #e9ecef;
  border-radius: 15px;
  padding: 1.5rem;
}

.confirmation-title {
  font-size: 24px;
  font-weight: 700;
  color: #2d3748;
  margin-bottom: 1rem;
  display: flex;
  align-items: center;
}

.confirmation-content {
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
}

.info-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0.5rem 0;
  border-bottom: 1px solid #f1f3f4;
}

.info-row:last-child {
  border-bottom: none;
}

.info-label {
  font-weight: 600;
  color: #6c757d;
}

.info-value {
  font-weight: 600;
  color: #2d3748;
  text-align: right;
}

.product-list {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.product-item {
  background: white;
  border: 1px solid #e9ecef;
  border-radius: 10px;
  padding: 1rem;
}

.product-info h6 {
  font-weight: 700;
  color: #2d3748;
  margin: 0 0 0.5rem 0;
}

.product-details {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.product-quantity {
  color: #6c757d;
  font-weight: 500;
}

.product-price {
  color: #667eea;
  font-weight: 700;
}

/* ==================== BUTTONS ==================== */
.step-actions {
  display: flex;
  gap: 1rem;
  justify-content: space-between;
  margin-top: 2rem;
}

.next-step-btn,
.confirm-order-btn {
  flex: 1;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border: none;
  padding: 15px 30px;
  border-radius: 12px;
  font-weight: 700;
  font-size: 1rem;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
}

.next-step-btn:hover,
.confirm-order-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 25px rgba(102, 126, 234, 0.3);
}

.prev-step-btn {
  background: transparent;
  color: #6c757d;
  border: 2px solid #e9ecef;
  padding: 15px 30px;
  border-radius: 12px;
  font-weight: 600;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
}

.prev-step-btn:hover {
  border-color: #667eea;
  color: #667eea;
}

/* ==================== SUMMARY CARD ==================== */
.summary-card {
  background: white;
  border-radius: 20px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  border: 1px solid rgba(102, 126, 234, 0.1);
  position: sticky;
  top: 6rem;
}

.summary-header {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  padding: 1.5rem;
}

.summary-title {
  font-size: 1.3rem;
  font-weight: 700;
  margin: 0;
  display: flex;
  align-items: center;
}

.summary-content {
  padding: 1.5rem;
}

.summary-section {
  margin-bottom: 1.5rem;
  padding-bottom: 1.5rem;
  border-bottom: 1px solid #f1f3f4;
}

.summary-section:last-child {
  border-bottom: none;
  margin-bottom: 0;
  padding-bottom: 0;
}

.section-title {
  font-weight: 700;
  color: #2d3748;
  margin-bottom: 1rem;
  font-size: 1rem;
}

.product-summary-list {
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
}

.product-summary-item {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  gap: 1rem;
}

.product-summary-info {
  display: flex;
  flex-direction: column;
  gap: 0.25rem;
  flex: 1;
}

.product-summary-name {
  font-weight: 600;
  color: #2d3748;
  font-size: 0.9rem;
  line-height: 1.3;
}

.product-summary-qty {
  color: #6c757d;
  font-size: 0.85rem;
}

.product-summary-price {
  font-weight: 700;
  color: #667eea;
  white-space: nowrap;
}

.price-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 0.75rem;
}

.price-row:last-child {
  margin-bottom: 0;
}

.price-label {
  color: #6c757d;
  font-weight: 500;
}

.price-value {
  font-weight: 700;
  color: #2d3748;
}

.price-row.discount .price-value {
  color: #10b981;
}

.summary-total {
  background: linear-gradient(
    135deg,
    rgba(102, 126, 234, 0.1) 0%,
    rgba(118, 75, 162, 0.1) 100%
  );
  border-radius: 12px;
  padding: 1rem;
  margin: 1.5rem 0;
}

.total-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.total-label {
  font-size: 1.2rem;
  font-weight: 700;
  color: #2d3748;
}

.total-value {
  font-size: 1.5rem;
  font-weight: 800;
  color: #667eea;
}

.security-info {
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
  margin-top: 1.5rem;
  padding-top: 1.5rem;
  border-top: 1px solid #f1f3f4;
}

.security-item {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  color: #6c757d;
  font-size: 0.9rem;
}

.security-item i {
  color: #10b981;
  font-size: 1.1rem;
}

/* ==================== SUCCESS MODAL ==================== */
.success-modal {
  position: fixed;
  inset: 0;
  z-index: 9999;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 2rem;
}

.success-modal-backdrop {
  position: absolute;
  inset: 0;
  background: rgba(0, 0, 0, 0.7);
  backdrop-filter: blur(5px);
}

.success-modal-content {
  background: white;
  border-radius: 20px;
  padding: 3rem;
  max-width: 500px;
  width: 100%;
  text-align: center;
  position: relative;
  z-index: 1;
  box-shadow: 0 20px 50px rgba(0, 0, 0, 0.2);
}

.success-icon {
  font-size: 4rem;
  color: #10b981;
  margin-bottom: 1.5rem;
}

.success-title {
  font-size: 2rem;
  font-weight: 700;
  color: #2d3748;
  margin-bottom: 1rem;
}

.success-message {
  color: #6c757d;
  line-height: 1.6;
  margin-bottom: 2rem;
  font-size: 1.1rem;
}

.success-actions {
  display: flex;
  gap: 1rem;
  justify-content: center;
}

.continue-shopping-btn,
.view-orders-btn {
  padding: 12px 24px;
  border-radius: 12px;
  font-weight: 600;
  border: none;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
}

.continue-shopping-btn {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
}

.view-orders-btn {
  background: transparent;
  color: #667eea;
  border: 2px solid #667eea;
}

.continue-shopping-btn:hover,
.view-orders-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 25px rgba(102, 126, 234, 0.3);
}

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-content {
  background: white;
  padding: 24px;
  border-radius: 16px;
  width: 90%;
  max-width: 500px;
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.25);
}

/* ==================== RESPONSIVE DESIGN ==================== */
@media (max-width: 768px) {
  .hero-title {
    font-size: 2rem;
  }

  .hero-subtitle {
    font-size: 1rem;
  }

  .step-content {
    padding: 1.5rem;
  }

  .step-title {
    font-size: 1.5rem;
  }

  .progress-steps {
    flex-direction: column;
    gap: 1rem;
  }

  .progress-steps::before {
    display: none;
  }

  .step-actions {
    flex-direction: column;
  }

  .payment-method-content {
    flex-direction: column;
    align-items: flex-start;
    gap: 1rem;
  }

  .address-actions {
    flex-direction: column;
    align-items: flex-start;
    gap: 1rem;
  }

  .success-actions {
    flex-direction: column;
  }

  .summary-card {
    position: static;
    margin-top: 2rem;
  }
}
</style>
