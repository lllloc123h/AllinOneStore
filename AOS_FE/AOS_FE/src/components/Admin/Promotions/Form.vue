<template>
  <div class="container-fluid row">
    <div class="aside col-3">
      <Dashboard :listDashBoard="listDashBoard"></Dashboard>
    </div>
    <div class="article col-9">
      <form
        @submit.prevent="props.action === 'create' ? submitForm() : submitUpdateForm()"
      >
        <div
          class="mb-3"
          :style="
            props.action === 'view' || props.action === 'create' ? ' display:none;' : ''
          "
        >
          <label for="id" class="form-label text-capitalize"></label>
          <input
            id="id"
            v-model="formData.id"
            v-if="props.action !== 'create'"
            :hidden="props.action === 'view'"
            :disabled="props.action === 'update'"
            type="number"
            class="form-control"
            placeholder="`Enter id`"
          />
        </div>
        <div class="mb-3">
          <label for="name" class="form-label text-capitalize">Tên khuyến mãi</label>
          <input
            id="name"
            v-model="formData.name"
            type="text"
            class="form-control"
            placeholder="Nhập tên khuyến mãi"
          />
        </div>

        <div class="mb-3">
          <label for="description" class="form-label text-capitalize">Mô tả</label>
          <input
            id="description"
            v-model="formData.description"
            type="text"
            class="form-control"
            placeholder="Nhập mô tả"
          />
        </div>

        <!-- <div class="mb-3">
          <label :for="type" class="form-label text-capitalize">type</label>
          <input :id="type" v-model="formData.type" type="text" class="form-control" :placeholder="`Enter type`" />
        </div>

        <div class="mb-3">
          <label :for="discountType" class="form-label text-capitalize">discountType</label>
          <input :id="discountType" v-model="formData.discountType" type="text" class="form-control"
            :placeholder="`Enter discountType`" />
        </div> -->
        <div class="mb-3">
          <label for="type" class="form-label text-capitalize">Loại khuyến mãi</label>
          <select
            id="type"
            v-model="formData.type"
            class="form-select"
            :disabled="props.action === 'update'"
          >
            <option disabled value="">Chọn loại khuyến mãi</option>
            <option
              v-for="item in dropdownTypePromotions"
              :key="item.id"
              :value="item.type"
            >
              {{ item.type }}
            </option>
          </select>
          <small v-if="props.action === 'update'" class="form-text text-muted">
            Không thể thay đổi loại khuyến mãi khi cập nhật
          </small>
        </div>

        <div class="mb-3">
          <label for="discountValue" class="form-label text-capitalize"
            >Giá trị giảm giá</label
          >
          <input
            id="discountValue"
            v-model="formData.discountValue"
            type="number"
            :disabled="formData.type === 'COMBO' || formData.turnBuy > 0"
            :max="minimumVariantPrice || undefined"
            class="form-control"
            :class="{ 'is-invalid': !discountValidation.isValid }"
            placeholder="Nhập giá trị giảm giá"
            min="0"
            step="1000"
          />

          <!-- Discount validation message -->
          <div
            v-if="formData.type === 'DISCOUNT' && selectedProductVariants.length > 0"
            class="form-text"
          >
            <small v-if="minimumVariantPrice" class="text-info">
              💡 Giá thấp nhất của biến thể đã chọn:
              {{ formatPrice(minimumVariantPrice) }}
            </small>
          </div>

          <div v-if="!discountValidation.isValid" class="invalid-feedback">
            {{ discountValidation.message }}
          </div>
        </div>

        <div class="mb-3">
          <label for="comboPrice" class="form-label text-capitalize">Giá combo</label>
          <input
            id="comboPrice"
            v-model="formData.comboPrice"
            type="number"
            :disabled="formData.type === 'DISCOUNT' || formData.turnBuy > 0"
            class="form-control"
            placeholder="Nhập giá combo"
          />
          <div
            v-if="formData.type === 'COMBO' && selectedProductVariants.length > 0"
            class="form-text"
          >
            <small class="text-info">
              💡 Tổng giá trị sản phẩm:
              <strong>{{ formatPrice(calculateTotalProductValue()) }}</strong>
            </small>
            <br />
            <small class="text-warning">
              ⚠️ Giá combo phải nhỏ hơn tổng giá trị sản phẩm để có ý nghĩa khuyến mãi
            </small>
          </div>
        </div>

        <div class="mb-3">
          <label for="usageLimit" class="form-label text-capitalize"
            >Số lượng sử dụng</label
          >
          <input
            id="usageLimit"
            v-model="formData.qty"
            type="number"
            class="form-control"
            placeholder="Nhập số lượng sử dụng"
          />
        </div>
        <div class="mb-3">
          <label for="usageLimit" class="form-label text-capitalize">Lượt mua</label>
          <input
            id="usageLimit"
            v-model="formData.turnBuy"
            type="number"
            class="form-control"
            placeholder="Nhập số lượt mua"
            disabled
          />
        </div>

        <div class="mb-3">
          <label for="startAt" class="form-label text-capitalize">Ngày bắt đầu</label>
          <input
            id="startAt"
            v-model="formData.startAt"
            type="datetime-local"
            class="form-control"
            placeholder="Chọn ngày bắt đầu"
            :disabled="formData.turnBuy > 0"
          />
        </div>

        <div class="mb-3">
          <label for="endAt" class="form-label text-capitalize">Ngày kết thúc</label>
          <input
            id="endAt"
            v-model="formData.endAt"
            type="datetime-local"
            class="form-control"
            placeholder="Chọn ngày kết thúc"
            :disabled="formData.turnBuy > 0"
          />
          <div v-if="formData.startAt && formData.endAt" class="form-text">
            <small
              class="text-warning"
              v-if="new Date(formData.startAt) >= new Date(formData.endAt)"
            >
              ⚠️ Ngày bắt đầu phải nhỏ hơn ngày kết thúc
            </small>
            <small class="text-success" v-else> ✅ Thời gian khuyến mãi hợp lệ </small>
          </div>
        </div>

        <div class="mb-3">
          <!-- <label class="form-label text-capitalize">isActive</label> -->
          <input
            class="form-check-input"
            type="radio"
            id="isActiveTrue"
            :value="true"
            v-model="formData.active"
          />
          <label class="form-check-label" for="isActiveTrue">Hoạt động</label>

          <input
            class="form-check-input"
            type="radio"
            id="isActiveFalse"
            :value="false"
            v-model="formData.active"
          />
          <label class="form-check-label" for="isActiveFalse">Không hoạt động</label>
        </div>
        <div
          class="container-fluid row"
          :class="{ 'no-pointer-events': formData.turnBuy > 0 }"
        >
          <!-- Base Product Search -->
          <div class="mb-3">
            <label class="form-label">Tìm kiếm sản phẩm gốc</label>
            <div class="search-container">
              <div class="search-input-wrapper">
                <input
                  type="text"
                  class="form-control"
                  v-model="searchQuery"
                  @keyup.enter="performSearch"
                  placeholder="Nhập tên sản phẩm để tìm kiếm..."
                />
                <button
                  type="button"
                  class="btn-search"
                  @click="performSearch"
                  :disabled="isSearching || searchQuery.trim().length < 2"
                >
                  <i v-if="!isSearching" class="bi bi-search"></i>
                  <div v-else class="spinner-border spinner-border-sm" role="status">
                    <span class="visually-hidden">Đang tìm kiếm...</span>
                  </div>
                </button>
              </div>
            </div>
          </div>

          <!-- Search Results - Now displayed as separate component below -->
          <div v-if="searchResults.length > 0" class="search-results-section">
            <div class="results-header">
              <span>Kết quả tìm kiếm ({{ searchResults.length }})</span>
              <button type="button" class="btn-close-search" @click="clearSearch">
                <i class="bi bi-x"></i>
              </button>
            </div>
            <div class="results-list">
              <div
                v-for="product in searchResults"
                :key="product.id"
                class="result-item"
                :class="{ 'already-selected': isProductAlreadySelected(product.id) }"
                @click="selectBaseProduct(product)"
              >
                <img :src="product.mainImageUrl" alt="Product" class="result-image" />
                <div class="result-content">
                  <h6 class="result-name">{{ product.name }}</h6>
                  <small class="result-material">{{ product.material }}</small>
                  <small class="result-category">{{
                    product.categories?.name || "Chưa phân loại"
                  }}</small>
                  <div class="result-stats">
                    <small class="result-rating">⭐ {{ product.rating }}/5</small>
                    <small class="result-turnbuy"
                      >🔥 {{ product.turnBuy }} lượt mua</small
                    >
                  </div>
                </div>
                <div
                  v-if="isProductAlreadySelected(product.id)"
                  class="selected-indicator"
                >
                  <i class="bi bi-check-circle-fill"></i>
                  <small>Đã chọn</small>
                </div>
              </div>
            </div>

            <!-- Pagination -->
            <div v-if="totalPages > 1" class="pagination-container">
              <button
                type="button"
                class="btn btn-sm btn-outline-primary"
                :disabled="currentPage === 0 || isSearching"
                @click="loadPreviousPage"
              >
                <span v-if="!isSearching">Trước</span>
                <div v-else class="spinner-border spinner-border-sm" role="status">
                  <span class="visually-hidden">Đang tải...</span>
                </div>
              </button>
              <span class="page-info">
                {{ currentPage + 1 }} / {{ totalPages }}
                <small v-if="searchResults.length > 0" class="text-muted">
                  ({{ searchResults.length }} sản phẩm)
                </small>
              </span>
              <button
                type="button"
                class="btn btn-sm btn-outline-primary"
                :disabled="currentPage >= totalPages - 1 || isSearching"
                @click="loadNextPage"
              >
                <span v-if="!isSearching">Sau</span>
                <div v-else class="spinner-border spinner-border-sm" role="status">
                  <span class="visually-hidden">Đang tải...</span>
                </div>
              </button>
            </div>
          </div>

          <!-- Selected Base Products Display -->
          <div v-if="selectedBaseProducts.length > 0" class="mb-3">
            <label class="form-label">Sản phẩm gốc đã chọn</label>
            <div class="selected-products">
              <div
                v-for="product in selectedBaseProducts"
                :key="product.id"
                class="selected-product-item"
              >
                <img
                  :src="product.mainImageUrl"
                  alt="Selected Product"
                  class="selected-image"
                />
                <div class="selected-content">
                  <h6 class="selected-name">{{ product.name }}</h6>
                  <div class="selected-details">
                    <small class="selected-material"
                      >Chất liệu: {{ product.material }}</small
                    >
                    <small class="selected-category"
                      >Danh mục:
                      {{
                        product.categories.name || product.categories || "Chưa phân loại"
                      }}</small
                    >
                  </div>
                  <div class="selected-stats">
                    <small class="selected-rating">⭐ {{ product.rating }}/5</small>
                    <small class="selected-turnbuy"
                      >🔥 {{ product.turnBuy }} lượt mua</small
                    >
                  </div>
                  <div class="selected-product-status">
                    <span
                      class="product-status-badge"
                      :class="{ active: product.active, inactive: !product.active }"
                    >
                      {{ product.active ? "Hoạt động" : "Không hoạt động" }}
                    </span>
                    <small class="selected-product-id">ID: {{ product.id }}</small>
                  </div>
                  <div class="variant-info">
                    <small class="text-info">
                      {{ getSelectedVariantsCount(product.id) }} biến thể đã chọn
                    </small>
                  </div>
                </div>
                <div class="product-actions">
                  <button
                    type="button"
                    class="btn-select-variants"
                    @click="openVariantsSelection(product)"
                    :disabled="isLoadingVariants"
                  >
                    <i class="bi bi-list-ul"></i>
                    <span>Chọn biến thể</span>
                  </button>
                  <button
                    type="button"
                    class="btn-remove-product"
                    @click="removeBaseProduct(product.id)"
                  >
                    <i class="bi bi-x"></i>
                  </button>
                </div>
              </div>
            </div>
          </div>

          <!-- Selected Product Variants with Options - Grouped by Base Product -->
          <div v-if="selectedProductVariants.length > 0" class="mb-3">
            <label class="form-label">Biến thể sản phẩm và tùy chọn</label>

            <!-- Loop through each base product group -->
            <div
              v-for="(group, baseProductId) in groupedVariantsByBaseProduct"
              :key="baseProductId"
              class="base-product-group"
            >
              <!-- Base Product Header -->
              <div class="base-product-header">
                <img
                  v-if="group.baseProduct?.mainImageUrl"
                  :src="group.baseProduct.mainImageUrl"
                  alt="Base Product"
                  class="base-product-image"
                />
                <div class="base-product-info">
                  <h6 class="base-product-name">{{ group.baseProduct?.name }}</h6>
                  <small class="base-product-material">{{
                    group.baseProduct?.material
                  }}</small>
                  <small class="base-product-variants-count"
                    >{{ group.variants.length }} biến thể đã chọn</small
                  >
                </div>
              </div>

              <!-- Variants under this base product -->
              <div class="selected-variants">
                <div
                  v-for="variant in group.variants"
                  :key="variant.idProductItem || variant.id"
                  class="variant-option-card"
                >
                  <div class="variant-info-section">
                    <img
                      v-if="variant.imageUrl"
                      :src="variant.imageUrl"
                      alt="Variant"
                      class="variant-option-image"
                    />
                    <div class="variant-info-content">
                      <h6 class="variant-option-name">
                        {{ variant.name || variant.sku }}
                      </h6>
                      <div class="variant-details">
                        <small class="variant-option-sku">SKU: {{ variant.sku }}</small>
                        <small class="variant-option-price">{{
                          formatPrice(variant.price)
                        }}</small>
                        <small class="variant-option-cost" v-if="variant.cost"
                          >Cost: {{ formatPrice(variant.cost) }}</small
                        >
                      </div>
                      <div class="variant-stock-info" v-if="variant.qty !== undefined">
                        <small class="variant-qty">Tồn kho: {{ variant.qty }}</small>
                        <small
                          class="variant-safety"
                          v-if="variant.safetyStock !== undefined"
                          >An toàn: {{ variant.safetyStock }}</small
                        >
                        <small class="variant-rating" v-if="variant.rating !== undefined"
                          >⭐ {{ variant.rating }}/5</small
                        >
                        <small
                          class="variant-turnbuy"
                          v-if="variant.turnBuy !== undefined"
                          >🔥 {{ variant.turnBuy }} lượt mua</small
                        >
                        <div
                          class="variant-promotions-info"
                          v-if="variant.inPromotions && variant.inPromotions.length > 0"
                        >
                          <small class="promotions-label"
                            >Đang tham gia các khuyến mãi:</small
                          >
                          <div class="promotions-list">
                            <div
                              v-for="promotion in variant.inPromotions"
                              :key="promotion.id"
                              class="promotion-tag"
                              :class="{
                                active: promotion.isActive,
                                inactive: !promotion.isActive,
                              }"
                            >
                              <div class="promotion-header">
                                <span class="promotion-name">{{ promotion.name }}</span>
                                <span class="promotion-id">ID: {{ promotion.id }}</span>
                              </div>
                              <div class="promotion-details">
                                <small class="promotion-type">{{ promotion.type }}</small>
                                <small class="promotion-dates">
                                  {{ formatPromotionDate(promotion.startAt) }} -
                                  {{ formatPromotionDate(promotion.endAt) }}
                                </small>
                              </div>
                            </div>
                          </div>
                        </div>
                        <small
                          class="variant-no-promotions"
                          v-else-if="
                            variant.inPromotions && variant.inPromotions.length === 0
                          "
                        >
                          Chưa tham gia khuyến mãi nào
                        </small>
                      </div>
                      <div class="variant-status" v-if="variant.active !== undefined">
                        <span
                          class="status-badge"
                          :class="{
                            active: variant.active === true || variant.active === 'true',
                            inactive:
                              variant.active === false || variant.active === 'false',
                          }"
                        >
                          {{
                            variant.active === true || variant.active === "true"
                              ? "Hoạt động"
                              : "Không hoạt động"
                          }}
                        </span>
                        <small class="variant-id">ID: {{ variant.idProductItem }}</small>
                      </div>
                      <div
                        class="variant-attributes"
                        v-if="
                          variant.attributes && Object.keys(variant.attributes).length > 0
                        "
                      >
                        <span
                          v-for="(value, key) in variant.attributes"
                          :key="key"
                          class="attribute-tag"
                        >
                          {{ key }}: {{ value }}
                        </span>
                      </div>
                    </div>
                  </div>

                  <div class="variant-options-section">
                    <!-- Quantity Selector - Only show for COMBO -->
                    <div class="option-row" v-if="formData.type === 'COMBO'">
                      <label class="option-label">Số lượng yêu cầu:</label>
                      <div class="quantity-selector">
                        <button
                          type="button"
                          class="qty-btn qty-decrease"
                          @click="decreaseQuantity(variant.idProductItem || variant.id)"
                          :disabled="
                            (variantOptions[variant.idProductItem || variant.id]
                              .require_qty || 1) <= 1
                          "
                        >
                          −
                        </button>
                        <input
                          type="number"
                          class="qty-input"
                          v-model.number="
                            variantOptions[variant.idProductItem || variant.id]
                              .require_qty
                          "
                          min="1"
                          max="999"
                          @input="validateQuantity(variant.idProductItem || variant.id)"
                        />
                        <button
                          type="button"
                          class="qty-btn qty-increase"
                          @click="increaseQuantity(variant.idProductItem || variant.id)"
                          :disabled="
                            (variantOptions[variant.idProductItem || variant.id]
                              .require_qty || 1) >= 999
                          "
                        >
                          +
                        </button>
                      </div>
                    </div>

                    <!-- Gift Option - Only show for COMBO -->
                    <div class="option-row" v-if="formData.type === 'COMBO'">
                      <div class="form-check">
                        <input
                          class="form-check-input"
                          type="checkbox"
                          :id="'isGift_' + (variant.idProductItem || variant.id)"
                          v-model="
                            variantOptions[variant.idProductItem || variant.id].is_gift
                          "
                        />
                        <label
                          class="form-check-label"
                          :for="'isGift_' + (variant.idProductItem || variant.id)"
                        >
                          Là quà tặng
                        </label>
                      </div>
                    </div>
                  </div>

                  <div class="variant-actions">
                    <button
                      type="button"
                      class="btn-remove-variant"
                      @click="removeVariant(variant.idProductItem)"
                    >
                      <i class="bi bi-x"></i>
                    </button>
                  </div>
                </div>
              </div>
            </div>

            <!-- Gift Distribution Validation Feedback - Only show for COMBO -->
            <div
              v-if="selectedProductVariants.length > 0 && formData.type === 'COMBO'"
              class="form-text mt-2"
            >
              <small
                class="text-danger"
                v-if="
                  Object.values(variantOptions).filter(
                    (option) => option?.is_gift === true
                  ).length === selectedProductVariants.length &&
                  selectedProductVariants.length > 0
                "
              >
                ❌ Không thể đặt tất cả sản phẩm làm quà tặng. Phải có ít nhất một sản
                phẩm bán kèm.
              </small>
              <small
                class="text-success"
                v-else-if="
                  Object.values(variantOptions).filter(
                    (option) => option?.is_gift === true
                  ).length < selectedProductVariants.length &&
                  Object.values(variantOptions).filter(
                    (option) => option?.is_gift === true
                  ).length > 0
                "
              >
                ✅ Phân phối sản phẩm hợp lệ:
                {{
                  Object.values(variantOptions).filter(
                    (option) => option?.is_gift === false
                  ).length
                }}
                sản phẩm bán kèm,
                {{
                  Object.values(variantOptions).filter(
                    (option) => option?.is_gift === true
                  ).length
                }}
                quà tặng
              </small>
              <small
                class="text-info"
                v-else-if="
                  Object.values(variantOptions).filter(
                    (option) => option?.is_gift === true
                  ).length === 0
                "
              >
                💡 Tất cả sản phẩm đều là sản phẩm bán kèm (không có quà tặng)
              </small>
            </div>
          </div>

          <!-- Global Gift Option Selection - Show when there are 2 or more gifts and type is COMBO -->
          <div v-if="getTotalGiftsCount() >= 2 && formData.type === 'COMBO'" class="mb-3">
            <label class="form-label">Tùy chọn quà tặng chung</label>
            <div class="gift-option-container">
              <select v-model="globalGiftOption" class="form-select">
                <option value="">Chọn tùy chọn quà tặng</option>
                <option
                  v-for="option in getAvailableGiftOptions()"
                  :key="option.value"
                  :value="option.value"
                >
                  {{ option.label }}
                </option>
              </select>
              <small class="form-text text-muted">
                Tùy chọn này áp dụng cho tất cả {{ getTotalGiftsCount() }} quà tặng đã
                chọn
              </small>
            </div>
          </div>

          <!-- Variants Selection Modal -->
          <div
            v-if="showVariantsModal"
            class="modal-backdrop"
            @click="showVariantsModal = false"
          >
            <div class="variants-modal" @click.stop>
              <div class="modal-header">
                <h5 class="modal-title">
                  Chọn biến thể cho: {{ currentBaseProduct?.name }}
                </h5>
                <button
                  type="button"
                  class="btn-close-modal"
                  @click="showVariantsModal = false"
                >
                  <i class="bi bi-x"></i>
                </button>
              </div>

              <div class="modal-body">
                <div v-if="isLoadingVariants" class="loading-container">
                  <div class="spinner-border" role="status">
                    <span class="visually-hidden">Đang tải biến thể...</span>
                  </div>
                  <p>Đang tải danh sách biến thể...</p>
                </div>

                <div
                  v-else-if="
                    currentBaseProduct &&
                    availableVariants[currentBaseProduct.id] &&
                    availableVariants[currentBaseProduct.id].length > 0
                  "
                  class="variants-list"
                >
                  <div
                    v-for="variant in availableVariants[currentBaseProduct.id]"
                    :key="variant.id"
                    class="variant-item"
                    :class="{
                      selected: isVariantSelectedForCurrentProduct(variant.idProductItem),
                      'conflict-disabled':
                        !checkPromotionConflict(variant).canAdd &&
                        !isVariantSelectedForCurrentProduct(variant.idProductItem),
                    }"
                  >
                    <div class="variant-checkbox">
                      <input
                        type="checkbox"
                        :checked="
                          isVariantSelectedForCurrentProduct(variant.idProductItem)
                        "
                        :disabled="
                          !checkPromotionConflict(variant).canAdd &&
                          !isVariantSelectedForCurrentProduct(variant.idProductItem)
                        "
                        @click.stop
                        @change="toggleProductVariant(variant)"
                      />
                    </div>
                    <img
                      v-if="variant.imageUrl"
                      :src="variant.imageUrl"
                      alt="Variant"
                      class="variant-image"
                    />
                    <div class="variant-content">
                      <h6 class="variant-name">{{ variant.name || variant.sku }}</h6>
                      <div class="variant-details">
                        <small class="variant-sku">SKU: {{ variant.sku }}</small>

                        <small class="variant-price">{{
                          formatPrice(variant.price)
                        }}</small>
                        <small class="variant-cost"
                          >Cost: {{ formatPrice(variant.cost) }}</small
                        >
                      </div>
                      <div class="variant-stock-info">
                        <small class="variant-qty">Tồn kho: {{ variant.qty }}</small>
                        <small class="variant-safety"
                          >An toàn: {{ variant.safetyStock }}</small
                        >
                        <small class="variant-rating">⭐ {{ variant.rating }}/5</small>
                        <small class="variant-turnbuy"
                          >🔥 {{ variant.turnBuy }} lượt mua</small
                        >
                        <div
                          class="variant-promotions-info"
                          v-if="variant.inPromotions && variant.inPromotions.length > 0"
                        >
                          <small class="promotions-label">Đang tham gia:</small>
                          <div class="promotions-list-compact">
                            <span
                              v-for="promotion in variant.inPromotions"
                              :key="promotion.id"
                              class="promotion-tag-compact"
                              :class="{
                                active: promotion.isActive,
                                inactive: !promotion.isActive,
                              }"
                              :title="`ID: ${promotion.id} - ${promotion.type} - ${promotion.name}`"
                            >
                              [{{ promotion.id }}] {{ promotion.type }} -
                              {{ promotion.name }}
                            </span>
                          </div>
                        </div>
                        <small
                          class="variant-no-promotions"
                          v-else-if="
                            variant.inPromotions && variant.inPromotions.length === 0
                          "
                        >
                          Chưa tham gia khuyến mãi
                        </small>
                      </div>

                      <!-- Conflict Warning -->
                      <div
                        v-if="
                          !checkPromotionConflict(variant).canAdd &&
                          !isVariantSelectedForCurrentProduct(variant.idProductItem)
                        "
                        class="conflict-warning"
                      >
                        <span class="warning-icon">⚠</span>
                        <span>Không thể chọn do xung đột loại khuyến mãi</span>
                      </div>

                      <div class="variant-status">
                        <span
                          class="status-badge"
                          :class="{ active: variant.active, inactive: !variant.active }"
                        >
                          {{ variant.active ? "Hoạt động" : "Không hoạt động" }}
                        </span>
                        <small class="variant-id">ID: {{ variant.idProductItem }}</small>
                      </div>
                      <div class="variant-attributes">
                        <span
                          v-for="(value, key) in variant.attributes"
                          :key="key"
                          class="attribute-tag"
                        >
                          {{ key }}: {{ value }}
                        </span>
                      </div>
                    </div>
                  </div>
                </div>

                <div v-else class="no-variants">
                  <p>Không có biến thể nào cho sản phẩm này.</p>
                </div>
              </div>

              <div class="modal-footer">
                <button
                  type="button"
                  class="btn btn-secondary"
                  @click="showVariantsModal = false"
                >
                  Đóng
                </button>
                <button
                  type="button"
                  class="btn btn-primary"
                  @click="showVariantsModal = false"
                >
                  Hoàn thành ({{ getSelectedVariantsCount(currentBaseProduct?.id) }} đã
                  chọn)
                </button>
              </div>
            </div>
          </div>
        </div>
        <button type="submit" :disable="props.action === 'view'" class="btn btn-primary">
          <span v-if="props.action === 'create'">Tạo mới</span>
          <span v-else-if="props.action === 'create'">Tạo mới</span>
          <span v-else>Cập nhật</span>
        </button>
      </form>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref, onMounted, watch, computed } from "vue";
import {
  formatDate,
  formatDateTimeLocal,
  toISOStringWithTimezone,
} from "../../Module/CommonsFunctions.js";
import Dashboard from "../../Module/DashBoard.vue";
import createCrudService from "../../../Configs/reusableCRUDService.js";
import { useRouter } from "vue-router";
import api from "../../../Configs/api.js";

const router = useRouter();
import "bootstrap/dist/js/bootstrap.bundle.min.js";
import "bootstrap/dist/css/bootstrap.min.css";
import { notification } from "ant-design-vue";
const props = defineProps({
  TableName: {
    type: String,
    required: true,
  },
  action: {
    type: String,
    required: true,
  },
  id: {
    type: String,
    required: true,
  },
});
const formTableService = createCrudService(props.TableName);

const formData = reactive({
  id: "",
  name: "KHUYẾN MÃI HÈ CỰC SỐC MUA 2 ÁO THUN UNISEX GIÁ 150.000 VND",
  description: "Mua 2 áo thun unisex bất kỳ, giá combo chỉ 150.000 VND",
  type: "COMBO",
  discountValue: "",
  comboPrice: "150000",
  qty: "200",
  turnBuy: 0,
  startAt: "2025-07-01T04:23:00",
  endAt: "2025-10-20T19:22:00",
  active: true,
  createdAt: "",
  updatedAt: "",
});

// Search functionality variables
const searchQuery = ref("");
const searchResults = ref([]);
const selectedBaseProducts = ref([]);
const currentPage = ref(0);
const totalPages = ref(0);
const isSearching = ref(false);

// Product variants functionality
const selectedProductVariants = ref([]); // Array of selected product items with options
const availableVariants = ref({}); // Object to store variants for each base product
const isLoadingVariants = ref(false);
const showVariantsModal = ref(false);
const currentBaseProduct = ref(null);
const variantOptions = ref({}); // Store options for each selected variant {variantId: {require_qty, is_gift}}
const globalGiftOption = ref("all"); // Global gift option for all gifts
const searchDebounceTimeout = ref(null);

// Track deleted items for update scenario
const deletedPromotionProducts = ref([]); // Track deleted promotion products with full data
const originalPromotionProducts = ref([]); // Store original promotion products for comparison

// Computed property to group variants by base product
const groupedVariantsByBaseProduct = computed(() => {
  const groups = {};

  selectedProductVariants.value.forEach((variant) => {
    const baseProductId = variant.baseProductId;
    if (!groups[baseProductId]) {
      groups[baseProductId] = {
        baseProduct: selectedBaseProducts.value.find((p) => p.id === baseProductId),
        variants: [],
      };
    }
    groups[baseProductId].variants.push(variant);
  });

  return groups;
});

// Computed property to get minimum price of selected variants for discount validation
const minimumVariantPrice = computed(() => {
  if (selectedProductVariants.value.length === 0) {
    return null;
  }

  const prices = selectedProductVariants.value
    .map((variant) => variant.price || 0)
    .filter((price) => price > 0);

  return prices.length > 0 ? Math.min(...prices) : null;
});

// Computed property to validate discount value
const discountValidation = computed(() => {
  if (
    formData.type !== "DISCOUNT" ||
    !formData.discountValue ||
    !minimumVariantPrice.value
  ) {
    return { isValid: true, message: "" };
  }

  const discountValue = parseFloat(formData.discountValue);
  const minPrice = minimumVariantPrice.value;

  if (discountValue > minPrice) {
    return {
      isValid: false,
      message: `❌ Giá trị giảm giá (${formatPrice(
        discountValue
      )}) không được lớn hơn giá thấp nhất của biến thể (${formatPrice(minPrice)})`,
    };
  }

  return { isValid: true, message: "" };
});

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
const dropdownTypePromotions = [
  { id: "0", type: "DISCOUNT" },
  { id: "1", type: "COMBO" },
];

async function submitUpdateForm() {
  try {
    // Validate discount value before proceeding
    if (!discountValidation.value.isValid) {
      alert(discountValidation.value.message);
      return;
    }

    // Validate combo price before proceeding
    if (!validateComboPrice()) {
      return;
    }

    // Validate dates before proceeding
    if (!validateDates()) {
      return;
    }

    // Validate gift distribution before proceeding
    if (!validateGiftDistribution()) {
      return;
    }

    formData.createdAt = formatDateTimeLocal(formData.createdAt);
    formData.updatedAt = formatDateTimeLocal(formData.updatedAt);

    // Prepare variant data with options for items to add/update
    const listToAdd = selectedProductVariants.value.map((v) => {
      const uniqueKey = v.idProductItem;
      const isGift = variantOptions.value[uniqueKey]?.is_gift || false;
      return {
        id: v.id || null,
        requireQty: variantOptions.value[uniqueKey]?.require_qty || 1,
        gift: isGift,
        giftOption: isGift ? globalGiftOption.value : "", // Nếu không phải quà tặng thì giftOption = ''
        productItem: { id: v.idProductItem }, // Use actual variant ID
        promotionId: props.id, // Use promotion ID
        createdAt: null,
        updatedAt: null,
      };
    });

    // Prepare list of items to delete - return null if empty
    const listToDelete =
      deletedPromotionProducts.value && deletedPromotionProducts.value.length > 0
        ? deletedPromotionProducts.value
        : null;

    console.log("List to add:", listToAdd);
    console.log("List to delete:", listToDelete);

    // Use update-specific API
    const checkResponse = await api.put("/admin/combos/checkcombo/update", {
      listToAdd: listToAdd,
      listToDelete: listToDelete,
      promotion: formData,
    });
    console.log("Check combo update response:", checkResponse.data);

    // const response = await formTableService.update(props.id, formData);
    console.log("Update successful:", checkResponse.data);
    notification.success({
      message: "Cập nhật thành công",
      description:
        checkResponse.data == "NO_CONFLICT"
          ? "Khuyến mãi đã được cập nhật thành công."
          : checkResponse.data,
      duration: 3,
    });
    router.push(`/Admin/${props.TableName}`);
  } catch (error) {
    console.error("Update failed:", error.response.data.message);
    notification.error({
      message: "Cập nhật thất bại",
      description:
        error.response.data.message || "Đã xảy ra lỗi khi cập nhật khuyến mãi.",
      duration: 3,
    });
  }
}

async function submitForm() {
  // Validate discount value before proceeding
  if (!discountValidation.value.isValid) {
    alert(discountValidation.value.message);
    return;
  }

  // Validate combo price before proceeding
  if (!validateComboPrice()) {
    return;
  }

  // Validate dates before proceeding
  if (!validateDates()) {
    return;
  }

  // Validate gift distribution before proceeding
  if (!validateGiftDistribution()) {
    return;
  }

  formData.startAt = toISOStringWithTimezone(formData.startAt);
  formData.endAt = toISOStringWithTimezone(formData.endAt);
  // Prepare variant data with options
  const checkSelectedProductVariants = selectedProductVariants.value.map((v) => {
    const uniqueKey = v.idProductItem || v.id;
    const isGift = variantOptions.value[uniqueKey]?.is_gift || false;
    return {
      id: null,
      requireQty: variantOptions.value[uniqueKey]?.require_qty || 1,
      gift: isGift,
      giftOption: isGift ? globalGiftOption.value : "", // Nếu không phải quà tặng thì giftOption = ''
      productItem: { id: v.idProductItem }, // Use actual variant ID
      promotionId: null, // Use newly created promotion ID
      createdAt: null,
      updatedAt: null,
    };
  });
  console.log("Selected product variants with options:", checkSelectedProductVariants);

  try {
    // Check if combo already exists
    const checkResponse = await api.post("/admin/combos/checkcombo", {
      listToAdd: checkSelectedProductVariants,
      promotion: formData,
    });
    console.log("Check combo response:", checkResponse.data);

    if (checkResponse.data != "NO_CONFLICT") {
      notification.error({
        message: "Lỗi ",
        description: checkResponse.data,
        duration: 3,
      });
      return;
    } else {
      notification.success({
        message: "Thành công",
        description: "Khuyến mãi hợp lệ, tạo mới thành công.",
        duration: 3,
      });
    }

    // // Create promotion
    // const createResponse = await formTableService.create(formData);
    // console.log("Create successful:", createResponse.data);

    // // Update promotion ID for variants
    // checkSelectedProductVariants.forEach((variant) => {
    //   variant.promotionId = createResponse.data.id;
    //   api
    //     .post("/admin/PromotionProducts", variant)
    //     .then((res) => {
    //       console.log("Promotion product created:", res.data);
    //       router.push(`/Admin/${props.TableName}`);
    //     })
    //     .catch((err) => {
    //       console.error("Error creating promotion product:", err);
    //       notification.error({
    //         message: "Lỗi",
    //         description: "Đã xảy ra lỗi khi tạo sản phẩm khuyến mãi.",
    //         duration: 3,
    //       });
    //     });
    // });
    // Create promotion products
  } catch (error) {
    console.error("Error in submitForm:", error);
    notification.error({
      message: "Lỗi",
      description: "Đã xảy ra lỗi khi tạo khuyến mãi.",
      duration: 3,
    });
  }
  // try {
  //   const response = await formTableService.create(formData);
  //   console.log("Create successful:", response.data);
  //   router.push(`/Admin/${props.TableName}`);
  //   // Add selected base products and variants to form data
  //   formData.selectedBaseProductIds = selectedBaseProducts.value.map((p) => p.id);

  //   // Add global gift option
  //   formData.globalGiftOption = globalGiftOption.value;
  //   console.log("Form data with variants and options:", formData);
  //   console.log("Selected base products:", selectedBaseProducts.value);
  //   console.log("Selected variants with options:", formData.selectedProductVariants);
  // } catch (error) {
  //   console.error("Create failed:", error);
  // }
}

async function performSearch() {
  if (searchQuery.value.trim().length < 2) {
    searchResults.value = [];
    totalPages.value = 0;
    currentPage.value = 0;
    return;
  }

  try {
    isSearching.value = true;
    currentPage.value = 0; // Reset to first page when searching

    const response = await api.get("/admin/BaseProducts/search", {
      params: {
        search: searchQuery.value.trim(),
        page: currentPage.value,
        size: 10,
      },
    });

    console.log("Initial search response:", response.data);

    // The API returns a Page object with content array
    if (response.data && response.data.content) {
      searchResults.value = response.data.content;
      totalPages.value = response.data.totalPages || 1;
    } else {
      searchResults.value = [];
      totalPages.value = 0;
    }

    console.log("Initial search results:", searchResults.value.length, "items");
    console.log("Total pages available:", totalPages.value);
  } catch (error) {
    console.error("Initial search failed:", error);
    searchResults.value = [];
    totalPages.value = 0;
    currentPage.value = 0;
  } finally {
    isSearching.value = false;
  }
}

function selectBaseProduct(product) {
  // Check if product is already selected
  const isAlreadySelected = selectedBaseProducts.value.some((p) => p.id === product.id);

  if (!isAlreadySelected) {
    selectedBaseProducts.value.push(product);
    // Load variants for this base product
    loadProductVariants(product.id);
  }

  // Don't clear search - keep results visible for multiple selections
  // clearSearch();
}

// Load product variants for a base product
async function loadProductVariants(baseId) {
  try {
    isLoadingVariants.value = true;

    const response = await api.get("/admin/products/productitems", {
      params: {
        baseId: baseId,
        currentPromotionId: props.id || null, // Truyền ID promotion hiện tại
        promotionType: formData.type || null, // Truyền kiểu promotion hiện tại
      },
    });

    console.log("Product variants response:", response.data);
    console.log("Sample variant data:", response.data[0]); // Log first variant to see structure

    if (response.data && Array.isArray(response.data)) {
      // Store variants for this base product and ensure baseProductId is set with full data
      const variantsWithBaseId = response.data.map((variant) => ({
        ...variant,
        baseProductId: baseId, // Ensure baseProductId is available
        // Ensure all fields have default values if missing
        name: variant.name || variant.sku,
        cost: variant.cost || 0,
        qty: variant.qty || 0,
        safetyStock: variant.safetyStock || 0,
        rating: variant.rating || 0,
        turnBuy: variant.turnBuy || 0,
        active: variant.active !== undefined ? variant.active : true,
        inPromotions: variant.inPromotions || [],
        attributes: variant.attributes || {},
      }));

      availableVariants.value[baseId] = variantsWithBaseId;
    } else {
      availableVariants.value[baseId] = [];
    }

    console.log(
      "Loaded variants for base product",
      baseId,
      ":",
      availableVariants.value[baseId].length,
      "variants"
    );
  } catch (error) {
    console.error("Failed to load product variants:", error);
    availableVariants.value[baseId] = [];
  } finally {
    isLoadingVariants.value = false;
  }
}

// Open variants selection modal
function openVariantsSelection(baseProduct) {
  currentBaseProduct.value = baseProduct;
  showVariantsModal.value = true;
}

// Check promotion type conflict before adding variant
function checkPromotionConflict(variant) {
  const currentPromotionType = formData.type; // 'DISCOUNT' hoặc 'COMBO'
  const currentPromotionId = props.id; // ID của promotion hiện tại (nếu đang update)

  if (!variant.inPromotions || variant.inPromotions.length === 0) {
    // Biến thể chưa thuộc promotion nào - OK để chọn
    return { canAdd: true };
  }

  // Kiểm tra từng promotion mà variant đang thuộc
  for (const promotion of variant.inPromotions) {
    // Skip nếu là chính promotion hiện tại (khi update)
    if (currentPromotionId && promotion.id === currentPromotionId) {
      continue;
    }

    // Nếu đang tạo DISCOUNT mà variant thuộc COMBO khác
    if (currentPromotionType === "DISCOUNT" && promotion.type === "COMBO") {
      return {
        canAdd: false,
        message: `❌ Không thể chọn biến thể "${
          variant.name || variant.sku
        }"!\n\nBiến thể này đang thuộc COMBO "${promotion.name}" (ID: ${
          promotion.id
        }).\n\nKhi tạo DISCOUNT, không được chọn biến thể đang thuộc COMBO khác.`,
      };
    }

    // Nếu đang tạo COMBO mà variant thuộc DISCOUNT khác
    if (currentPromotionType === "COMBO" && promotion.type === "DISCOUNT") {
      return {
        canAdd: false,
        message: `❌ Không thể chọn biến thể "${
          variant.name || variant.sku
        }"!\n\nBiến thể này đang thuộc DISCOUNT "${promotion.name}" (ID: ${
          promotion.id
        }).\n\nKhi tạo COMBO, không được chọn biến thể đang thuộc DISCOUNT khác.`,
      };
    }

    // Nếu đang tạo DISCOUNT mà variant thuộc DISCOUNT khác (không cho phép)
    if (currentPromotionType === "DISCOUNT" && promotion.type === "DISCOUNT") {
      return {
        canAdd: false,
        message: `❌ Không thể chọn biến thể "${
          variant.name || variant.sku
        }"!\n\nBiến thể này đã thuộc DISCOUNT khác: "${promotion.name}" (ID: ${
          promotion.id
        }).\n\nMỗi biến thể chỉ có thể thuộc một DISCOUNT tại một thời điểm.`,
      };
    }

    // Nếu đang tạo COMBO mà variant thuộc COMBO khác - CHO PHÉP (không return false)
    // COMBO có thể chọn biến thể từ COMBO khác
  }

  return { canAdd: true };
}

// Select/deselect a product variant
function toggleProductVariant(variant) {
  console.log(
    "Toggling variant:",
    variant.name || variant.sku,
    "ID:",
    variant.idProductItem
  );
  console.log("Current base product:", currentBaseProduct.value?.id);
  console.log("Variant baseProductId:", variant.baseProductId);

  const baseProductId = currentBaseProduct.value?.id || variant.baseProductId;
  const uniqueVariantKey = variant.idProductItem;

  // Find existing variant by both ID and baseProductId to avoid conflicts
  const existingIndex = selectedProductVariants.value.findIndex(
    (v) => v.idProductItem === variant.idProductItem && v.baseProductId === baseProductId
  );

  if (existingIndex > -1) {
    // Remove if already selected
    console.log("Removing variant from selection");
    selectedProductVariants.value.splice(existingIndex, 1);
    // Remove variant options using unique key
    delete variantOptions.value[uniqueVariantKey];
  } else {
    // Check for promotion type conflict before adding
    const conflictCheck = checkPromotionConflict(variant);
    if (!conflictCheck.canAdd) {
      // Show error message and prevent selection
      console.warn("Promotion conflict detected:", conflictCheck.message);
      alert(conflictCheck.message);
      return;
    }

    // Add if not selected - ensure baseProductId is set and copy all data
    console.log("Adding variant to selection");
    const variantWithBaseId = {
      ...variant, // Copy all original data
      baseProductId: baseProductId,
      idProductItem: uniqueVariantKey, // Ensure unique key for reference
      // Ensure all required fields are present with default values
      name: variant.name || variant.sku,
      sku: variant.sku,
      price: variant.price || 0,
      cost: variant.cost || 0,
      imageUrl: variant.imageUrl,
      qty: variant.qty || 0,
      safetyStock: variant.safetyStock || 0,
      rating: variant.rating || 0,
      turnBuy: variant.turnBuy || 0,
      active: variant.active !== undefined ? variant.active : true,
      inPromotions: variant.inPromotions || [],
      attributes: variant.attributes || {},
    };

    selectedProductVariants.value.push(variantWithBaseId);
    console.log("Added variant with full data:", variantWithBaseId);

    // Initialize variant options with unique key - different defaults based on promotion type
    const defaultOptions = {
      require_qty: 1, // Always default to 1
      is_gift: false, // Always default to false
      gift_option: "", // Always default to empty string
    };

    // For DISCOUNT: set fixed values and hide UI
    if (formData.type === "DISCOUNT") {
      defaultOptions.require_qty = 1; // Fixed at 1 for DISCOUNT
      defaultOptions.is_gift = false; // Fixed at false for DISCOUNT
      defaultOptions.gift_option = ""; // Fixed at empty for DISCOUNT
    }

    variantOptions.value[uniqueVariantKey] = defaultOptions;
  }

  console.log(
    "Current selected variants after toggle:",
    selectedProductVariants.value.length
  );
  console.log(
    "Selected variant details:",
    selectedProductVariants.value.map((v) => ({
      id: v.id,
      sku: v.sku,
      baseProductId: v.baseProductId,
      idProductItem: v.idProductItem,
      name: v.name,
    }))
  );

  // Gift options simplified to just true/false, no need for additional logic
}

// Remove a specific variant
function removeVariant(variantId) {
  // Find the variant to get its unique key
  const variantToRemove = selectedProductVariants.value.find(
    (v) => v.idProductItem === variantId
  );

  // If this is an existing promotion product (has promotionProductId), track it for deletion
  if (variantToRemove && variantToRemove.promotionProductId) {
    // Find the original promotion product data
    const originalItem = originalPromotionProducts.value.find(
      (item) => item.id === variantToRemove.promotionProductId
    );
    if (originalItem) {
      // Add to deleted list with full promotion product structure
      deletedPromotionProducts.value.push({
        id: originalItem.id,
        requireQty: originalItem.requireQty,
        gift: originalItem.gift,
        giftOption: originalItem.giftOption,
        productItem: { id: originalItem.productItem.id },
        promotionId: props.id,
        createdAt: null,
        updatedAt: null,
      });
      console.log("Marked for deletion:", originalItem.id);
    }
  }

  selectedProductVariants.value = selectedProductVariants.value.filter(
    (v) => v.idProductItem !== variantId
  );

  // Remove variant options using unique key if available
  if (variantToRemove && variantToRemove.idProductItem) {
    delete variantOptions.value[variantToRemove.idProductItem];
  } else {
    // Fallback to old method for backward compatibility
    delete variantOptions.value[variantId];
  }

  // Check and reset gift options if total gifts < 2
  checkAndResetGiftOptions();
}

// Check and reset gift options when total gifts < 2
function checkAndResetGiftOptions() {
  const totalGifts = getTotalGiftsCount();

  if (totalGifts < 2) {
    // Reset global gift option when less than 2 gifts
    globalGiftOption.value = "all";
  } else {
    // Check if current global gift option is still valid for the new gift count
    const validOptions = getAvailableGiftOptions().map((opt) => opt.value);
    if (globalGiftOption.value && !validOptions.includes(globalGiftOption.value)) {
      globalGiftOption.value = "all";
    }
  }
}

// Check if a variant is selected
function isVariantSelected(variantId) {
  return selectedProductVariants.value.some((v) => v.idProductItem === variantId);
}

// Check if a variant is selected for the current base product in modal
function isVariantSelectedForCurrentProduct(variantId) {
  if (!currentBaseProduct.value) return false;

  return selectedProductVariants.value.some(
    (v) =>
      v.idProductItem === variantId && v.baseProductId === currentBaseProduct.value.id
  );
}

// Get count of selected variants for a base product
function getSelectedVariantsCount(baseProductId) {
  if (!baseProductId) return 0;

  const count = selectedProductVariants.value.filter(
    (v) => v.baseProductId === baseProductId
  ).length;
  console.log(`Variants count for base product ${baseProductId}:`, count);

  return count;
}

// Format price for display
function formatPrice(price) {
  if (!price) return "0 ₫";
  return new Intl.NumberFormat("vi-VN", {
    style: "currency",
    currency: "VND",
  }).format(price);
}

// Format promotion date for display
function formatPromotionDate(dateString) {
  if (!dateString) return "";
  const date = new Date(dateString);
  return date.toLocaleDateString("vi-VN", {
    day: "2-digit",
    month: "2-digit",
    year: "numeric",
  });
}

// Count total gifts selected
function getTotalGiftsCount() {
  return Object.values(variantOptions.value).filter((option) => option?.is_gift === true)
    .length;
}

// Get available gift options based on current number of gifts
function getAvailableGiftOptions() {
  const totalGifts = getTotalGiftsCount();
  const options = [];

  if (totalGifts >= 2) {
    // Generate options based on number of gifts available
    for (let i = 1; i < totalGifts; i++) {
      // Add "i trong totalGifts" options (chỉ tạo khi i < totalGifts)
      options.push({
        value: `${i}_trong_${totalGifts}`,
        label: `${i} trong ${totalGifts} quà`,
      });
    }

    // Add "Tất cả" option for selecting all gifts
    options.push({
      value: "all",
      label: "Tất cả quà",
    });
  }

  return options;
}

// Calculate total value of all selected products
function calculateTotalProductValue() {
  return selectedProductVariants.value.reduce((total, variant) => {
    const uniqueKey = variant.idProductItem || variant.id;
    const requireQty = variantOptions.value[uniqueKey]?.require_qty || 1;
    const price = variant.price || 0;
    return total + price * requireQty;
  }, 0);
}

// Validate combo price against total product value
function validateComboPrice() {
  if (formData.type !== "COMBO" || !formData.comboPrice) {
    return true; // Skip validation if not combo or no combo price
  }

  const totalProductValue = calculateTotalProductValue();
  const comboPrice = parseFloat(formData.comboPrice);

  if (comboPrice >= totalProductValue) {
    notification.error({
      message: "Lỗi giá combo",
      description: `Giá combo (${formatPrice(
        comboPrice
      )}) không được lớn hơn hoặc bằng tổng giá trị sản phẩm (${formatPrice(
        totalProductValue
      )}).`,
      duration: 5,
    });
    return false;
  }

  return true;
}

// Validate start date and end date
function validateDates() {
  if (!formData.startAt || !formData.endAt) {
    notification.error({
      message: "Lỗi ngày tháng",
      description: "Vui lòng nhập đầy đủ ngày bắt đầu và ngày kết thúc.",
      duration: 3,
    });
    return false;
  }

  const startDate = new Date(formData.startAt);
  const endDate = new Date(formData.endAt);

  if (startDate >= endDate) {
    notification.error({
      message: "Lỗi ngày tháng",
      description: "Ngày bắt đầu phải nhỏ hơn ngày kết thúc.",
      duration: 3,
    });
    return false;
  }

  return true;
}

// Validate that not all products are gifts - must have at least one regular product
function validateGiftDistribution() {
  if (selectedProductVariants.value.length === 0) {
    return true; // No validation needed if no products selected
  }

  const totalProducts = selectedProductVariants.value.length;
  const giftCount = Object.values(variantOptions.value).filter(
    (option) => option?.is_gift === true
  ).length;

  if (giftCount === totalProducts) {
    notification.error({
      message: "Lỗi phân phối sản phẩm",
      description:
        "Không thể đặt tất cả sản phẩm làm quà tặng. Phải có ít nhất một sản phẩm bán kèm.",
      duration: 5,
    });
    return false;
  }

  return true;
}

// Check if product is already selected
function isProductAlreadySelected(productId) {
  return selectedBaseProducts.value.some((p) => p.id === productId);
}

// Remove all variants of a base product when base product is removed
function removeBaseProduct(productId) {
  // Find and track all variants of this base product for deletion
  const variantsToRemove = selectedProductVariants.value.filter(
    (v) => v.baseProductId === productId
  );

  variantsToRemove.forEach((variant) => {
    // If this is an existing promotion product (has promotionProductId), track it for deletion
    if (variant.promotionProductId) {
      // Find the original promotion product data
      const originalItem = originalPromotionProducts.value.find(
        (item) => item.id === variant.promotionProductId
      );
      if (originalItem) {
        // Add to deleted list with full promotion product structure
        deletedPromotionProducts.value.push({
          id: originalItem.id,
          requireQty: originalItem.requireQty,
          gift: originalItem.gift,
          giftOption: originalItem.giftOption,
          productItem: { id: originalItem.productItem.id },
          promotionId: props.id,
          createdAt: null,
          updatedAt: null,
        });
        console.log("Marked for deletion (base product removal):", originalItem.id);
      }
    }
    delete variantOptions.value[variant.idProductItem];
  });

  selectedBaseProducts.value = selectedBaseProducts.value.filter(
    (p) => p.id !== productId
  );

  selectedProductVariants.value = selectedProductVariants.value.filter(
    (v) => v.baseProductId !== productId
  );

  // Remove cached variants
  delete availableVariants.value[productId];
}

function clearSearch() {
  searchQuery.value = "";
  searchResults.value = [];
  currentPage.value = 0;
  totalPages.value = 0;
}

async function loadPreviousPage() {
  if (currentPage.value > 0) {
    currentPage.value--;
    await searchWithCurrentPage();
  }
}

async function loadNextPage() {
  if (currentPage.value < totalPages.value - 1) {
    currentPage.value++;
    await searchWithCurrentPage();
  }
}

async function searchWithCurrentPage() {
  if (searchQuery.value.trim().length < 2) {
    searchResults.value = [];
    return;
  }

  try {
    isSearching.value = true;

    const response = await api.get("/admin/BaseProducts/search", {
      params: {
        search: searchQuery.value.trim(),
        page: currentPage.value,
        size: 10,
      },
    });

    console.log("Pagination search response:", JSON.stringify(response.data, null, 2));

    // The API returns a Page object with content array
    if (response.data && response.data.content) {
      searchResults.value = response.data.content;
      totalPages.value = response.data.totalPages || 1;
    } else {
      searchResults.value = [];
      totalPages.value = 0;
    }

    console.log("Pagination results:", searchResults.value.length, "items");
    console.log("Current page:", currentPage.value + 1, "of", totalPages.value);
  } catch (error) {
    console.error("Pagination search failed:", error);
    searchResults.value = [];
    totalPages.value = 0;
  } finally {
    isSearching.value = false;
  }
}

const fetchData = async () => {
  if (!props.TableName) return;
  try {
    if (
      !props.action ||
      props.action === "view" ||
      (props.action === "update" && props.id)
    ) {
      const response = await formTableService.getById(props.id);
      console.log("=== PROMOTION DATA RESPONSE ===");
      console.log("Response data:", response.data);
      console.log("===============================");

      response.data.createdAt = formatDate(response.data.createdAt);
      response.data.updatedAt = formatDate(response.data.updatedAt);
      Object.assign(formData, response.data);
      api
        .get(`/admin/promotionproducts?promotionId=${props.id}`)
        .then(async (response) => {
          console.log("Promotion products response:", response.data);
          // Store original data for comparison
          originalPromotionProducts.value = [...response.data];

          // Map promotion products to selected variants with full data
          selectedProductVariants.value = response.data.map((item) => ({
            id: item.id,
            idProductItem: item.productItem.id,
            baseProductId: item.productItem.baseId,
            name: item.productItem.name || item.productItem.sku,
            sku: item.productItem.sku,
            price: item.productItem.price || 0,
            cost: item.productItem.cost || 0,
            imageUrl: item.productItem.imageUrl,
            qty: item.productItem.qty || 0,
            safetyStock: item.productItem.safetyStock || 0,
            rating: item.productItem.rating || 0,
            turnBuy: item.productItem.turnBuy || 0,
            active:
              item.productItem.active !== undefined ? item.productItem.active : true,
            inCombo: item.productItem.inCombo || 0,
            attributes: item.productItem.attributes || {},
            promotionProductId: item.id, // Store the promotion product ID for tracking deletions
          }));
          console.log("Promotion products response:", selectedProductVariants.value);

          // Initialize variant options
          variantOptions.value = response.data.reduce((acc, item) => {
            acc[item.productItem.id] = {
              require_qty: item.requireQty || 1,
              is_gift: item.gift || false,
              gift_option: item.giftOption || "", // Fill gift_option from server data
            };
            return acc;
          }, {});
          console.log("Variant options initialized:", variantOptions.value);

          // Set globalGiftOption based on existing data for update mode
          if (props.action === "update") {
            const giftOptions = response.data
              .filter((item) => item.gift) // Only consider gifts
              .map((item) => item.giftOption)
              .filter((option) => option); // Remove empty options

            if (giftOptions.length > 0) {
              // If all gifts have the same option, use that
              const uniqueOptions = [...new Set(giftOptions)];
              if (uniqueOptions.length === 1) {
                globalGiftOption.value = uniqueOptions[0];
              } else {
                // If mixed options, keep default "all"
                globalGiftOption.value = "all";
              }
            }
          }

          // Get unique base product IDs from the selected variants
          const uniqueBaseProductIds = [
            ...new Set(response.data.map((item) => item.productItem.baseId)),
          ];
          console.log("Unique base product IDs:", uniqueBaseProductIds);

          // Load base products information
          if (uniqueBaseProductIds.length > 0) {
            try {
              const baseProductPromises = uniqueBaseProductIds.map(async (baseId) => {
                const baseResponse = await api.get(`/admin/BaseProducts/${baseId}`);
                return baseResponse.data;
              });

              const baseProducts = await Promise.all(baseProductPromises);
              selectedBaseProducts.value = baseProducts;
              console.log("Loaded base products:", selectedBaseProducts.value);

              // Load variants for each base product
              for (const baseId of uniqueBaseProductIds) {
                await loadProductVariants(baseId);
              }
            } catch (error) {
              console.error("Error loading base products:", error);
            }
          }
        })
        .catch((error) => {
          console.error("Failed to load promotion products:", error);
        });
    }
  } catch (err) {
    console.error("Get failed:", err);
  }
};

onMounted(async () => {
  try {
    // Then fetch promotion data
    await fetchData();
  } catch (error) {
    console.error("Error loading data:", error);
  }
});
watch(() => props.id, fetchData);
watch(
  () => formData.type,
  (newVal) => {
    if (newVal === "COMBO") {
      formData.discountType = "";
      formData.discountValue = "";
    } else if (newVal === "DISCOUNT") {
      formData.comboPrice = "";
    }
  }
);

// Watch for changes in gift options to reset gift_option when needed
watch(
  () => variantOptions.value,
  () => {
    checkAndResetGiftOptions();
  },
  { deep: true }
);

// Watch for changes in search query to clear results when user starts typing new search
watch(
  () => searchQuery.value,
  (newValue, oldValue) => {
    // Clear previous timeout
    if (searchDebounceTimeout.value) {
      clearTimeout(searchDebounceTimeout.value);
    }

    // Only clear results if user is typing and there are existing results
    if (newValue !== oldValue && searchResults.value.length > 0) {
      // Debounce the clearing to avoid clearing too quickly while user is typing
      searchDebounceTimeout.value = setTimeout(() => {
        // Only clear if the search query has changed and is different from when results were loaded
        const currentSearchTerm = searchQuery.value.trim();
        const hasResults = searchResults.value.length > 0;

        // Clear results only if user is typing a completely different search
        if (hasResults && currentSearchTerm.length > 0) {
          searchResults.value = [];
          currentPage.value = 0;
          totalPages.value = 0;
        }
      }, 500); // 500ms delay before clearing
    }
  }
);

// Watch for changes in selected variants or quantities to validate combo price
watch(
  [
    () => selectedProductVariants.value,
    () => variantOptions.value,
    () => formData.comboPrice,
  ],
  () => {
    // Only validate if it's a combo and has combo price
    if (
      formData.type === "COMBO" &&
      formData.comboPrice &&
      selectedProductVariants.value.length > 0
    ) {
      const totalProductValue = calculateTotalProductValue();
      const comboPrice = parseFloat(formData.comboPrice);

      // Show warning if combo price is too high (but don't block)
      if (comboPrice >= totalProductValue) {
        console.warn(
          `Combo price (${comboPrice}) should be less than total product value (${totalProductValue})`
        );
      }
    }
  },
  { deep: true }
);

// Watch for changes in start and end dates to validate
watch([() => formData.startAt, () => formData.endAt], () => {
  // Only validate if both dates are provided
  if (formData.startAt && formData.endAt) {
    const startDate = new Date(formData.startAt);
    const endDate = new Date(formData.endAt);

    // Show warning if start date is greater than or equal to end date (but don't block)
    if (startDate >= endDate) {
      console.warn(
        `Start date (${formData.startAt}) should be less than end date (${formData.endAt})`
      );
    }
  }
});

// Watch for promotion type changes to reset variant options accordingly
watch(
  () => formData.type,
  (newType, oldType) => {
    if (newType !== oldType && Object.keys(variantOptions.value).length > 0) {
      console.log(
        `Promotion type changed from ${oldType} to ${newType}, updating variant options...`
      );

      // Update all existing variant options based on new type
      Object.keys(variantOptions.value).forEach((variantId) => {
        if (newType === "DISCOUNT") {
          // For DISCOUNT: fix values and hide UI
          variantOptions.value[variantId].require_qty = 1;
          variantOptions.value[variantId].is_gift = false;
          variantOptions.value[variantId].gift_option = "";
        }
        // For COMBO: keep existing values or set defaults if needed
        // (no action needed as COMBO allows user customization)
      });
    }
  }
);

// Watch for discount value changes to validate in real-time
watch([() => formData.discountValue, () => minimumVariantPrice.value], () => {
  if (
    formData.type === "DISCOUNT" &&
    formData.discountValue &&
    minimumVariantPrice.value
  ) {
    const discountValue = parseFloat(formData.discountValue);
    const minPrice = minimumVariantPrice.value;

    if (discountValue > minPrice) {
      console.warn(
        `Discount value (${discountValue}) exceeds minimum variant price (${minPrice})`
      );
    }
  }
});

// Quantity selector functions - Only for COMBO
const increaseQuantity = (variantId) => {
  // Only allow quantity changes for COMBO type
  if (formData.type !== "COMBO") return;

  const currentQty = variantOptions.value[variantId]?.require_qty || 1;
  if (currentQty < 999) {
    variantOptions.value[variantId].require_qty = currentQty + 1;
  }
};

const decreaseQuantity = (variantId) => {
  // Only allow quantity changes for COMBO type
  if (formData.type !== "COMBO") return;

  const currentQty = variantOptions.value[variantId]?.require_qty || 1;
  if (currentQty > 1) {
    variantOptions.value[variantId].require_qty = currentQty - 1;
  }
};

const validateQuantity = (variantId) => {
  // Only allow quantity changes for COMBO type
  if (formData.type !== "COMBO") {
    // For DISCOUNT, always keep qty at 1
    variantOptions.value[variantId].require_qty = 1;
    return;
  }

  let qty = variantOptions.value[variantId]?.require_qty;

  // Ensure it's a number
  if (isNaN(qty) || qty === null || qty === undefined) {
    qty = 1;
  }

  // Ensure it's within valid range
  if (qty < 1) {
    qty = 1;
  } else if (qty > 999) {
    qty = 999;
  }

  variantOptions.value[variantId].require_qty = parseInt(qty);
};
</script>
<style scoped>
.no-pointer-events {
  pointer-events: none;
  opacity: 0.6; /* tuỳ chọn, làm mờ giao diện */
}
.card {
  border-radius: 12px;
  border: 1px solid #dee2e6;
}

.form-control {
  border-radius: 0.75rem;
  border: 1px solid #dee2e6;
  transition: border-color 0.2s, box-shadow 0.2s;
}

.form-control:focus {
  border-color: #86b7fe;
  box-shadow: 0 0 0 0.25rem rgba(13, 110, 253, 0.25);
}

/* Search Container Styles */
.search-container {
  position: relative;
}

.search-input-wrapper {
  position: relative;
  display: flex;
  align-items: center;
}

.search-input-wrapper input {
  padding-right: 50px;
}

.btn-search {
  position: absolute;
  right: 8px;
  top: 50%;
  transform: translateY(-50%);
  background: #0d6efd;
  border: none;
  color: white;
  border-radius: 6px;
  width: 36px;
  height: 36px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: all 0.2s;
  z-index: 10;
}

.btn-search:hover:not(:disabled) {
  background: #0b5ed7;
  transform: translateY(-50%) scale(1.05);
}

.btn-search:disabled {
  background: #6c757d;
  cursor: not-allowed;
  opacity: 0.6;
}

.btn-search i {
  font-size: 0.9rem;
}

.search-loading {
  position: absolute;
  right: 12px;
  top: 50%;
  transform: translateY(-50%);
  color: #6c757d;
}

/* Search Results Section - Separate component below search input */
.search-results-section {
  margin-top: 1rem;
  background: white;
  border: 1px solid #dee2e6;
  border-radius: 0.75rem;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

.search-results-section .results-header {
  background: #e3f2fd;
  color: #1565c0;
  border-bottom: 1px solid #bbdefb;
  padding: 1rem 1.25rem;
  font-size: 0.95rem;
}

.search-results-section .results-list {
  max-height: 500px;
  overflow-y: auto;
}

.search-results-section .result-item {
  padding: 1.25rem 1.5rem;
}

.search-results-section .pagination-container {
  background: #e3f2fd;
  border-top: 1px solid #bbdefb;
  padding: 1rem 1.25rem;
}

/* Original dropdown search results */
.search-results {
  position: absolute;
  top: 100%;
  left: 0;
  right: 0;
  background: white;
  border: 1px solid #dee2e6;
  border-radius: 0.5rem;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  z-index: 1000;
  max-height: 400px;
  overflow: hidden;
}

.results-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0.75rem 1rem;
  background: #f8f9fa;
  border-bottom: 1px solid #dee2e6;
  font-weight: 600;
}

.btn-close-search {
  background: none;
  border: none;
  font-size: 1.2rem;
  cursor: pointer;
  color: #6c757d;
  padding: 0;
  width: 24px;
  height: 24px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.btn-close-search:hover {
  color: #dc3545;
}

.results-list {
  max-height: 300px;
  overflow-y: auto;
}

.result-item {
  display: flex;
  align-items: center;
  padding: 0.75rem 1rem;
  cursor: pointer;
  transition: background-color 0.2s;
  border-bottom: 1px solid #f1f3f4;
}

.result-item:hover {
  background-color: #f8f9fa;
}

.result-item.already-selected {
  background-color: #e8f5e8;
  border-left: 4px solid #28a745;
}

.result-item.already-selected:hover {
  background-color: #d4edda;
}

.result-item:last-child {
  border-bottom: none;
}

.selected-indicator {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.25rem;
  margin-left: 1rem;
  color: #28a745;
}

.selected-indicator i {
  font-size: 1.2rem;
}

.selected-indicator small {
  font-size: 0.7rem;
  font-weight: 600;
}

.result-image {
  width: 50px;
  height: 50px;
  border-radius: 8px;
  object-fit: cover;
  margin-right: 1rem;
}

.result-content {
  flex-grow: 1;
}

.result-name {
  margin: 0 0 0.25rem 0;
  font-size: 0.9rem;
  font-weight: 600;
}

.result-material {
  display: block;
  color: #6c757d;
  font-size: 0.8rem;
  margin-bottom: 0.1rem;
}

.result-category {
  display: block;
  color: #0d6efd;
  font-size: 0.75rem;
  font-weight: 500;
}

.result-stats {
  margin-top: 0.25rem;
  display: flex;
  gap: 1rem;
}

.result-rating,
.result-turnbuy {
  font-size: 0.7rem;
  color: #28a745;
  font-weight: 500;
}

.pagination-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0.75rem 1rem;
  background: #f8f9fa;
  border-top: 1px solid #dee2e6;
}

.page-info {
  font-size: 0.9rem;
  color: #6c757d;
}

/* Selected Products Styles */
.selected-products {
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
}

.selected-product-item {
  display: flex;
  align-items: center;
  background: #e7f3ff;
  border: 1px solid #b3d9ff;
  border-radius: 8px;
  padding: 0.75rem;
  min-width: 100%;
}

/* Selected Variants with Options Styles */
.base-product-group {
  margin-bottom: 2rem;
  border: 1px solid #e9ecef;
  border-radius: 12px;
  overflow: hidden;
  background: #ffffff;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
}

.base-product-header {
  background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
  padding: 1rem 1.5rem;
  border-bottom: 1px solid #dee2e6;
  display: flex;
  align-items: center;
  gap: 1rem;
}

.base-product-image {
  width: 50px;
  height: 50px;
  border-radius: 8px;
  object-fit: cover;
  border: 2px solid #ffffff;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.base-product-info {
  flex: 1;
}

.base-product-name {
  margin: 0 0 0.25rem 0;
  font-size: 1rem;
  font-weight: 600;
  color: #2c3e50;
}

.base-product-material {
  display: block;
  color: #6c757d;
  font-size: 0.8rem;
  margin-bottom: 0.25rem;
}

.base-product-variants-count {
  display: inline-block;
  background: #007bff;
  color: white;
  padding: 0.2rem 0.6rem;
  border-radius: 12px;
  font-size: 0.75rem;
  font-weight: 500;
}

.selected-variants {
  padding: 1rem;
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.variant-option-card {
  background: #f8f9fa;
  border: 1px solid #dee2e6;
  border-radius: 10px;
  padding: 1rem;
  display: flex;
  gap: 1rem;
  align-items: flex-start;
}

.variant-info-section {
  display: flex;
  align-items: center;
  min-width: 200px;
}

.variant-option-image {
  width: 60px;
  height: 60px;
  border-radius: 8px;
  object-fit: cover;
  margin-right: 0.75rem;
}

.variant-info-content {
  flex: 1;
}

.variant-option-name {
  margin: 0 0 0.25rem 0;
  font-size: 0.9rem;
  font-weight: 600;
  color: #333;
}

.variant-option-sku {
  display: block;
  color: #6c757d;
  font-size: 0.75rem;
  margin-bottom: 0.1rem;
}

.variant-option-price {
  display: block;
  color: #28a745;
  font-size: 0.8rem;
  font-weight: 600;
}

.variant-options-section {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
}

.option-row {
  display: flex;
  align-items: center;
  gap: 0.75rem;
}

.option-label {
  min-width: 140px;
  font-size: 0.85rem;
  font-weight: 500;
  color: #495057;
  margin: 0;
}

.option-input {
  flex: 1;
  max-width: 200px;
  font-size: 0.85rem;
}

.form-check {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.form-check-label {
  font-size: 0.85rem;
  font-weight: 500;
  color: #495057;
  margin: 0;
}

.variant-actions {
  display: flex;
  align-items: flex-start;
}

.btn-remove-variant {
  background: #dc3545;
  border: none;
  color: white;
  border-radius: 50%;
  width: 32px;
  height: 32px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: all 0.2s;
}

.btn-remove-variant:hover {
  background: #c82333;
  transform: scale(1.05);
}

.btn-remove-variant i {
  font-size: 0.8rem;
}

.gift-notice {
  font-size: 0.75rem;
  color: #6c757d;
  font-style: italic;
  display: flex;
  align-items: center;
  gap: 0.25rem;
  margin-left: 140px; /* Align with option inputs */
}

.gift-notice i {
  font-size: 0.7rem;
}

.gift-option-container {
  background: #e7f3ff;
  border: 1px solid #b3d9ff;
  border-radius: 8px;
  padding: 1rem;
}

.gift-option-container .form-select {
  margin-bottom: 0.5rem;
}

.gift-option-container .form-text {
  margin: 0;
  font-style: italic;
}

.selected-image {
  width: 50px;
  height: 50px;
  border-radius: 6px;
  object-fit: cover;
  margin-right: 0.75rem;
}

.selected-content {
  flex-grow: 1;
}

.selected-name {
  margin: 0 0 0.5rem 0;
  font-size: 0.9rem;
  font-weight: 600;
}

.selected-details {
  display: flex;
  gap: 1rem;
  margin-bottom: 0.4rem;
  flex-wrap: wrap;
}

.selected-material {
  color: #6c757d;
  font-size: 0.75rem;
  display: block;
}

.selected-category {
  color: #0d6efd;
  font-size: 0.75rem;
  font-weight: 500;
  display: block;
}

.selected-stats {
  display: flex;
  gap: 1rem;
  margin-bottom: 0.4rem;
  flex-wrap: wrap;
}

.selected-rating {
  color: #ffc107;
  font-size: 0.7rem;
  font-weight: 500;
}

.selected-turnbuy {
  color: #e83e8c;
  font-size: 0.7rem;
  font-weight: 500;
}

.selected-views {
  color: #6f42c1;
  font-size: 0.7rem;
  font-weight: 500;
}

.selected-product-status {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  margin-bottom: 0.4rem;
}

.product-status-badge {
  padding: 0.15rem 0.4rem;
  border-radius: 3px;
  font-size: 0.65rem;
  font-weight: 600;
  text-transform: uppercase;
}

.product-status-badge.active {
  background: #d1e7dd;
  color: #0f5132;
  border: 1px solid #a3cfbb;
}

.product-status-badge.inactive {
  background: #f8d7da;
  color: #58151c;
  border: 1px solid #f1aeb5;
}

.selected-product-id {
  color: #6c757d;
  font-size: 0.65rem;
  font-style: italic;
}

.variant-info {
  margin-top: 0.25rem;
}

.product-actions {
  display: flex;
  gap: 0.5rem;
  align-items: center;
}

.btn-select-variants {
  background: #28a745;
  border: none;
  color: white;
  border-radius: 6px;
  padding: 0.4rem 0.75rem;
  display: flex;
  align-items: center;
  gap: 0.4rem;
  cursor: pointer;
  transition: all 0.2s;
  font-size: 0.75rem;
}

.btn-select-variants:hover:not(:disabled) {
  background: #218838;
  transform: scale(1.02);
}

.btn-select-variants:disabled {
  background: #6c757d;
  cursor: not-allowed;
  opacity: 0.6;
}

.btn-select-variants i {
  font-size: 0.8rem;
}

.btn-remove-product {
  background: #dc3545;
  border: none;
  color: white;
  border-radius: 50%;
  width: 32px;
  height: 32px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: all 0.2s;
}

.btn-remove-product:hover {
  background: #c82333;
  transform: scale(1.05);
}

.btn-remove-product i {
  font-size: 0.8rem;
}

/* Modal Styles */
.modal-backdrop {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 9999;
}

.variants-modal {
  background: white;
  border-radius: 12px;
  width: 90%;
  max-width: 800px;
  max-height: 90vh;
  display: flex;
  flex-direction: column;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.2);
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1.25rem 1.5rem;
  border-bottom: 1px solid #dee2e6;
}

.modal-title {
  margin: 0;
  font-size: 1.1rem;
  font-weight: 600;
}

.btn-close-modal {
  background: none;
  border: none;
  font-size: 1.5rem;
  cursor: pointer;
  color: #6c757d;
  padding: 0;
  width: 32px;
  height: 32px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 6px;
  transition: all 0.2s;
}

.btn-close-modal:hover {
  background: #f8f9fa;
  color: #dc3545;
}

.modal-body {
  flex: 1;
  overflow-y: auto;
  padding: 1.5rem;
  max-height: 60vh;
}

.loading-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 3rem;
  text-align: center;
}

.loading-container p {
  margin-top: 1rem;
  color: #6c757d;
}

.variants-list {
  display: grid;
  gap: 0.75rem;
}

.variant-item {
  display: flex;
  align-items: center;
  padding: 1rem;
  border: 2px solid #e9ecef;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.2s;
}

.variant-item:hover {
  border-color: #0d6efd;
  background: #f8f9fa;
}

.variant-item.selected {
  border-color: #0d6efd;
  background: #e7f3ff;
}

.variant-item.conflict-disabled {
  border-color: #dc3545;
  background: #f8f9fa;
  opacity: 0.7;
  cursor: not-allowed;
}

.variant-item.conflict-disabled:hover {
  border-color: #dc3545;
  background: #f8f9fa;
}

.conflict-warning {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  color: #dc3545;
  font-size: 0.75rem;
  font-weight: 600;
  margin-top: 0.5rem;
  padding: 0.25rem 0.5rem;
  background: #f8d7da;
  border: 1px solid #f1aeb5;
  border-radius: 4px;
}

.conflict-warning i {
  font-size: 0.7rem;
}

.warning-icon {
  font-size: 0.8rem;
  font-weight: bold;
}

.variant-item.selected {
  border-color: #0d6efd;
  background: #e7f3ff;
}

.variant-checkbox {
  margin-right: 1rem;
}

.variant-checkbox input[type="checkbox"] {
  width: 18px;
  height: 18px;
  cursor: pointer;
}

.variant-image {
  width: 60px;
  height: 60px;
  border-radius: 6px;
  object-fit: cover;
  margin-right: 1rem;
}

.variant-content {
  flex: 1;
}

.variant-name {
  margin: 0 0 0.5rem 0;
  font-size: 0.95rem;
  font-weight: 600;
}

.variant-details {
  display: flex;
  gap: 1rem;
  margin-bottom: 0.5rem;
}

.variant-sku {
  color: #6c757d;
  font-size: 0.8rem;
}

.variant-combo-info {
  color: #6f42c1;
  font-size: 0.75rem;
  font-weight: 500;
  background: #f8f4ff;
  padding: 0.2rem 0.4rem;
  border-radius: 3px;
  border: 1px solid #e0cffc;
}

.variant-price {
  color: #28a745;
  font-size: 0.8rem;
  font-weight: 600;
}

.variant-cost {
  color: #fd7e14;
  font-size: 0.8rem;
  font-weight: 500;
}

.variant-stock-info {
  display: flex;
  gap: 1rem;
  margin-bottom: 0.5rem;
  flex-wrap: wrap;
}

.variant-qty {
  color: #0d6efd;
  font-size: 0.75rem;
  font-weight: 500;
}

.variant-safety {
  color: #dc3545;
  font-size: 0.75rem;
  font-weight: 500;
}

.variant-rating {
  color: #ffc107;
  font-size: 0.75rem;
  font-weight: 500;
}

.variant-turnbuy {
  color: #e83e8c;
  font-size: 0.75rem;
  font-weight: 500;
}

.variant-status {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  margin-bottom: 0.5rem;
}

.status-badge {
  padding: 0.2rem 0.5rem;
  border-radius: 4px;
  font-size: 0.7rem;
  font-weight: 600;
  text-transform: uppercase;
}

.variant-option-price {
  font-weight: 600;
  color: #28a745;
}

.variant-option-cost {
  color: #dc3545;
  font-weight: 500;
  margin-left: 0.5rem;
}

.variant-details {
  display: flex;
  flex-direction: column;
  gap: 0.2rem;
  margin-bottom: 0.5rem;
}

.variant-stock-info {
  display: flex;
  flex-wrap: wrap;
  gap: 0.5rem;
  margin-bottom: 0.5rem;
}

.variant-stock-info small {
  font-size: 0.75rem;
  color: #6c757d;
}

.variant-qty {
  color: #17a2b8 !important;
  font-weight: 500;
}

.variant-safety {
  color: #ffc107 !important;
  font-weight: 500;
}

.variant-rating {
  color: #28a745 !important;
  font-weight: 500;
}

.variant-turnbuy {
  color: #fd7e14 !important;
  font-weight: 500;
}

.variant-combo-info {
  color: #6f42c1 !important;
  font-weight: 500;
}

.variant-promotions-info {
  margin-top: 0.5rem;
  padding: 0.5rem;
  background: #f8f9fa;
  border-radius: 6px;
  border-left: 3px solid #6f42c1;
}

.promotions-label {
  font-weight: 600;
  color: #6f42c1;
  display: block;
  margin-bottom: 0.25rem;
}

.promotions-list {
  display: flex;
  flex-direction: column;
  gap: 0.25rem;
}

.promotion-tag {
  background: #fff;
  border: 1px solid #dee2e6;
  border-radius: 6px;
  padding: 0.4rem 0.6rem;
  display: flex;
  flex-direction: column;
  gap: 0.2rem;
}

.promotion-tag.active {
  border-color: #28a745;
  background: #f8fff9;
}

.promotion-tag.inactive {
  border-color: #dc3545;
  background: #fff5f5;
  opacity: 0.8;
}

.promotion-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 0.5rem;
}

.promotion-name {
  font-weight: 600;
  font-size: 0.8rem;
  color: #2c3e50;
  flex: 1;
}

.promotion-id {
  font-size: 0.7rem;
  color: #6c757d;
  background: #f8f9fa;
  padding: 0.1rem 0.3rem;
  border-radius: 3px;
  font-weight: 500;
}

.promotion-details {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 0.5rem;
}

.promotion-type {
  font-size: 0.7rem;
  color: #6c757d;
  font-weight: 600;
  background: #e9ecef;
  padding: 0.1rem 0.3rem;
  border-radius: 3px;
  text-transform: uppercase;
}

.promotion-dates {
  font-size: 0.65rem;
  color: #6c757d;
}

.promotions-list-compact {
  display: flex;
  flex-wrap: wrap;
  gap: 0.25rem;
}

.promotion-tag-compact {
  background: #6f42c1;
  color: white;
  padding: 0.2rem 0.4rem;
  border-radius: 4px;
  font-size: 0.7rem;
  font-weight: 500;
}

.promotion-tag-compact.active {
  background: #28a745;
}

.promotion-tag-compact.inactive {
  background: #dc3545;
  opacity: 0.8;
}

.variant-no-promotions {
  color: #6c757d !important;
  font-style: italic;
  font-size: 0.75rem;
}

.variant-status {
  margin-bottom: 0.5rem;
}

.status-badge {
  padding: 0.2rem 0.5rem;
  border-radius: 4px;
  font-size: 0.75rem;
  font-weight: 600;
  margin-right: 0.5rem;
}

.status-badge.active {
  background: #d1e7dd;
  color: #0f5132;
  border: 1px solid #a3cfbb;
}

.status-badge.inactive {
  background: #f8d7da;
  color: #58151c;
  border: 1px solid #f1aeb5;
}

.variant-id {
  color: #6c757d;
  font-size: 0.7rem;
  font-style: italic;
}

.variant-attributes {
  display: flex;
  flex-wrap: wrap;
  gap: 0.4rem;
}

.attribute-tag {
  background: #e9ecef;
  color: #495057;
  padding: 0.2rem 0.5rem;
  border-radius: 4px;
  font-size: 0.7rem;
  font-weight: 500;
}

.no-variants {
  text-align: center;
  padding: 3rem;
  color: #6c757d;
}

/* Quantity Selector Styles */
.quantity-selector {
  display: flex;
  align-items: center;
  background: #f8f9fa;
  border: 1px solid #dee2e6;
  border-radius: 8px;
  overflow: hidden;
  width: fit-content;
  min-width: 120px;
}

.qty-btn {
  background: #fff;
  border: none;
  width: 32px;
  height: 36px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  color: #495057;
  font-size: 18px;
  font-weight: bold;
  transition: all 0.2s ease;
  border-right: 1px solid #dee2e6;
  user-select: none;
}

.qty-btn:last-child {
  border-right: none;
  border-left: 1px solid #dee2e6;
}

.qty-btn:hover:not(:disabled) {
  background: #007bff;
  color: white;
  transform: translateY(-1px);
}

.qty-btn:active:not(:disabled) {
  transform: translateY(0);
  background: #0056b3;
}

.qty-btn:disabled {
  background: #e9ecef;
  color: #6c757d;
  cursor: not-allowed;
  opacity: 0.6;
}

.qty-input {
  border: none;
  background: transparent;
  width: 56px;
  height: 36px;
  text-align: center;
  font-size: 14px;
  font-weight: 600;
  color: #495057;
  outline: none;
  appearance: textfield; /* Standard property */
  -moz-appearance: textfield; /* Firefox */
  -webkit-appearance: none; /* Safari/Chrome */
}

.qty-input::-webkit-outer-spin-button,
.qty-input::-webkit-inner-spin-button {
  -webkit-appearance: none;
  margin: 0;
}

.qty-input:focus {
  background: #fff;
  box-shadow: inset 0 0 0 2px #007bff;
}

.qty-decrease {
  border-radius: 7px 0 0 7px;
}

.qty-increase {
  border-radius: 0 7px 7px 0;
}

.option-row {
  margin-bottom: 1rem;
}

.option-label {
  font-weight: 600;
  color: #495057;
  margin-bottom: 0.5rem;
  display: block;
}

.modal-footer {
  display: flex;
  justify-content: flex-end;
  gap: 0.75rem;
  padding: 1.25rem 1.5rem;
  border-top: 1px solid #dee2e6;
  background: #f8f9fa;
  border-radius: 0 0 12px 12px;
}
</style>
p
