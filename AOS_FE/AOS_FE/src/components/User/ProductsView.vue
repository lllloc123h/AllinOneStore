<template>
  <div class="products-container">
    <!-- Header Section -->
    <div class="page-header">
      <div class="container">
        <div class="header-content">
          <h1 class="page-title">
            <i class="bi bi-grid me-3"></i>
            Sản phẩm của chúng tôi
          </h1>
          <p class="page-subtitle">KHÁM PHÁ BỘ SƯU TẬP ĐA DẠNG VÀ CHẤT LƯỢNG</p>
        </div>
      </div>
    </div>

    <div class="container-fluid px-5">
      <div class="row mt-4 g-4">
        <!-- start categories -->
        <div class="col-lg-3">
          <div class="filter-sidebar">
            <div class="filter-header">
              <h3>
                <i class="bi bi-funnel me-2"></i>
                <span class="line"></span>Bộ lọc sản phẩm
              </h3>
            </div>

            <!-- Search Box -->
            <div class="search-section">
              <div class="modern-searchBox">
                <i class="bi bi-search search-icon"></i>
                <input
                  class="modern-searchInput"
                  type="text"
                  v-model="keyWord"
                  placeholder="Tìm kiếm sản phẩm..."
                />
                <button class="search-clear" v-if="keyWord" @click="keyWord = ''">
                  <i class="bi bi-x"></i>
                </button>
              </div>
            </div>

            <div class="filter-section">
              <div
                v-for="(items, groupName, index) in dropDowncatalogCategory"
                :key="groupName"
                class="filter-group"
              >
                <div class="modern-filter-card">
                  <div
                    class="filter-card-header"
                    @click="toggleAccordion('category-' + index)"
                  >
                    <h4 class="filter-title">
                      <i class="bi bi-tags me-2"></i>
                      <span class="line"></span> {{ groupName }}
                    </h4>
                    <i
                      class="bi bi-chevron-down toggle-icon"
                      :class="{ rotated: isExpanded('category-' + index) }"
                    ></i>
                  </div>
                  <div
                    class="filter-card-content"
                    :class="{ expanded: isExpanded('category-' + index) }"
                  >
                    <div class="filter-options">
                      <label
                        v-for="item in items"
                        :key="item.id"
                        class="modern-filter-option"
                      >
                        <input
                          type="checkbox"
                          :name="groupName"
                          :value="item.name"
                          v-model="selectedCatalogCategory[groupName]"
                        />
                        <span class="checkmark"></span>
                        <span class="option-text">{{ item.name }}</span>
                      </label>
                    </div>
                  </div>
                </div>
              </div>
              <div
                v-for="(items, groupName, index) in mapVarriants"
                :key="groupName"
                class="filter-group"
              >
                <div class="modern-filter-card">
                  <div
                    class="filter-card-header"
                    @click="toggleAccordion('variant-' + index)"
                  >
                    <h4 class="filter-title">
                      <i class="bi bi-palette me-2" v-if="groupName.includes('Màu')"></i>
                      <i class="bi bi-rulers me-2" v-else></i>
                      <span class="line"></span> {{ groupName }}
                    </h4>
                    <i
                      class="bi bi-chevron-down toggle-icon"
                      :class="{ rotated: isExpanded('variant-' + index) }"
                    ></i>
                  </div>
                  <div
                    class="filter-card-content"
                    :class="{ expanded: isExpanded('variant-' + index) }"
                  >
                    <div class="filter-options">
                      <label
                        v-for="item in items"
                        :key="item.id"
                        class="modern-filter-option"
                      >
                        <input
                          type="checkbox"
                          :name="groupName"
                          :value="item.signalSku"
                          v-model="selected[groupName]"
                        />
                        <span class="checkmark"></span>
                        <span class="option-text">{{ item.description }}</span>
                      </label>
                    </div>
                  </div>
                </div>
              </div>

              <div class="filter-group">
                <div class="modern-filter-card">
                  <div class="filter-card-header" @click="toggleAccordion('price')">
                    <h4 class="filter-title">
                      <i class="bi bi-currency-dollar me-2"></i>
                      <span class="line"></span> Khoảng giá
                    </h4>
                    <i
                      class="bi bi-chevron-down toggle-icon"
                      :class="{ rotated: isExpanded('price') }"
                    ></i>
                  </div>
                  <div
                    class="filter-card-content"
                    :class="{ expanded: isExpanded('price') }"
                  >
                    <div class="price-options">
                      <label class="modern-price-option">
                        <input
                          type="radio"
                          name="price"
                          v-model="selectedPrice"
                          value="100-200"
                        />
                        <span class="radio-mark"></span>
                        <span class="price-range">100k - 200k</span>
                      </label>
                      <label class="modern-price-option">
                        <input
                          type="radio"
                          name="price"
                          v-model="selectedPrice"
                          value="200-500"
                        />
                        <span class="radio-mark"></span>
                        <span class="price-range">200k - 500k</span>
                      </label>
                      <label class="modern-price-option">
                        <input
                          type="radio"
                          name="price"
                          v-model="selectedPrice"
                          value="500-700"
                        />
                        <span class="radio-mark"></span>
                        <span class="price-range">500k - 700k</span>
                      </label>
                      <label class="modern-price-option">
                        <input
                          type="radio"
                          name="price"
                          v-model="selectedPrice"
                          value=">700"
                        />
                        <span class="radio-mark"></span>
                        <span class="price-range">&gt; 700k</span>
                      </label>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <!-- end categories -->
        <div class="col-lg-9">
          <div class="products-section">
            <!-- Products Header -->
            <div class="products-header">
              <div class="header-info">
                <h2 class="products-title">
                  <i class="bi bi-grid-3x3-gap me-2"></i>
                  Sản phẩm của chúng tôi
                </h2>
                <div class="modern-searchBox-mobile d-lg-none">
                  <i class="bi bi-search search-icon"></i>
                  <input
                    class="modern-searchInput"
                    type="text"
                    v-model="keyWord"
                    placeholder="Tìm kiếm sản phẩm..."
                  />
                  <button class="search-clear" v-if="keyWord" @click="keyWord = ''">
                    <i class="bi bi-x"></i>
                  </button>
                </div>
              </div>
            </div>

            <!-- Products Grid -->
            <div class="product-flatform row mt-4 g-4">
              <div
                v-for="product in products"
                :key="product.id"
                class="col-lg-4 col-md-6 col-sm-12"
              >
                <div class="product-card-wrapper">
                  <ProductCard :product="product" @view-detail="openModal" />
                </div>
              </div>

              <!-- Empty State -->
              <div v-if="products.length === 0" class="empty-state col-12">
                <div class="empty-content">
                  <div class="empty-icon">
                    <i class="bi bi-inbox"></i>
                  </div>
                  <h3>Không tìm thấy sản phẩm</h3>
                  <p>Thử điều chỉnh bộ lọc hoặc từ khóa tìm kiếm</p>
                </div>
              </div>

              <!-- Pagination -->
              <div class="col-12 pagination-wrapper">
                <PageNavigative
                  :totalPage="data"
                  v-model:currentPage="pageIndex"
                  v-model:currentSize="pageSize"
                />
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Product Modal -->
    <div v-if="showModal" class="modern-modal-overlay" @click.self="closeModal">
      <div class="modern-modal">
        <!-- Modal Header -->
        <div class="modal-header">
          <div class="modal-title-section">
            <h2 class="modal-title">
              <i class="bi bi-eye-fill me-2"></i>
              Chi tiết sản phẩm
            </h2>
            <p class="modal-subtitle">{{ selectedProductItem?.baseProducts?.name }}</p>
          </div>
          <button class="modal-close" @click="closeModal">
            <i class="bi bi-x-lg"></i>
          </button>
        </div>

        <!-- Modal Body -->
        <div class="modal-body">
          <div class="product-layout">
            <!-- Left Side - Gallery -->
            <div class="gallery-section">
              <div class="product-gallery">
                <!-- Main Image -->
                <div class="main-image-container">
                  <img :src="currentImage" class="main-image" alt="Product Image" />

                  <!-- Navigation Arrows -->
                  <button
                    class="gallery-nav gallery-nav-prev"
                    @click="prevImage"
                    v-if="images.length > 1"
                  >
                    <i class="bi bi-chevron-left"></i>
                  </button>
                  <button
                    class="gallery-nav gallery-nav-next"
                    @click="nextImage"
                    v-if="images.length > 1"
                  >
                    <i class="bi bi-chevron-right"></i>
                  </button>

                  <!-- Image Counter -->
                  <div class="image-counter" v-if="images.length > 1">
                    {{ currentImageIndex + 1 }} / {{ images.length }}
                  </div>
                </div>

                <!-- Thumbnail Gallery -->
                <div class="thumbnail-gallery" v-if="images.length > 1">
                  <div
                    v-for="(img, idx) in images"
                    :key="idx"
                    :class="['thumbnail-item', { active: currentImageIndex === idx }]"
                    @click="selectImage(idx)"
                  >
                    <img :src="img.imageUrl" :alt="`Ảnh ${idx + 1}`" />
                    <div class="thumbnail-overlay"></div>
                  </div>
                </div>
              </div>
            </div>

            <!-- Right Side - Product Info -->
            <div class="info-section">
              <!-- Product Header -->
              <div class="product-header">
                <h1 class="product-title">
                  {{ selectedProductItem?.baseProducts?.name }}
                </h1>

                <!-- Rating -->
                <div class="rating-section">
                  <div class="stars">
                    <i
                      v-for="i in 5"
                      :key="i"
                      :class="[
                        'bi',
                        i <= (selectedProductItem?.baseProducts?.rating || 0)
                          ? 'bi-star-fill'
                          : 'bi-star',
                      ]"
                    ></i>
                  </div>
                  <span class="rating-text">{{
                    (selectedProductItem?.baseProducts?.rating || 0).toFixed(1)
                  }}</span>
                  <span class="rating-count"
                    >({{ Math.floor(Math.random() * 100) + 10 }} đánh giá)</span
                  >
                </div>

                <!-- Price Section -->
                <div class="price-section">
                  <template v-if="discountedPrice !== currentPrice">
                    <div class="price-row">
                      <span class="current-price">{{
                        formatPrice(discountedPrice)
                      }}</span>
                      <span class="original-price">{{ formatPrice(currentPrice) }}</span>
                    </div>
                    <div class="discount-badge">
                      <i class="bi bi-percent me-1"></i>
                      Giảm {{ promotion?.promotions?.discountPercent }}%
                    </div>
                  </template>
                  <template v-else>
                    <div class="price-row">
                      <span class="current-price">{{
                        formatPrice(selectedProductItem?.price || 0)
                      }}</span>
                    </div>
                  </template>
                </div>
              </div>

              <!-- Variant Selection -->
              <div
                class="variant-section"
                v-if="Object.keys(mapVarriantsOfListBaseProduct).length > 0"
              >
                <div
                  v-for="(items, groupName, index) in mapVarriantsOfListBaseProduct"
                  :key="groupName"
                  class="variant-group"
                >
                  <h5 class="variant-label">
                    <i
                      class="bi bi-palette-fill me-2"
                      v-if="groupName.includes('Màu')"
                    ></i>
                    <i class="bi bi-rulers me-2" v-else></i>
                    {{ groupName }}
                  </h5>
                  <div class="variant-options">
                    <label
                      v-for="item in items"
                      :key="item.id"
                      :class="[
                        'variant-button',
                        {
                          active: selectedVariant[groupName] === item.signalSku,
                          disabled: item.isActive === false,
                        },
                      ]"
                    >
                      <input
                        type="radio"
                        :name="groupName"
                        :value="item.signalSku"
                        :disabled="item.isActive === false"
                        v-model="selectedVariant[groupName]"
                        class="d-none"
                      />
                      <span class="variant-content">{{ item.description }}</span>
                    </label>
                  </div>
                </div>
              </div>

              <!-- Product Details -->
              <div class="product-details">
                <h6 class="details-title">
                  <i class="bi bi-info-circle-fill me-2"></i>
                  Thông tin sản phẩm
                </h6>
                <div class="details-grid">
                  <div class="detail-card">
                    <div class="detail-icon">
                      <i class="bi bi-tag-fill"></i>
                    </div>
                    <div class="detail-content">
                      <span class="detail-label">Mã sản phẩm</span>
                      <span class="detail-value">{{ selectedProductItem?.sku }}</span>
                    </div>
                  </div>

                  <div class="detail-card">
                    <div class="detail-icon">
                      <i class="bi bi-gem"></i>
                    </div>
                    <div class="detail-content">
                      <span class="detail-label">Chất liệu</span>
                      <span class="detail-value">{{
                        selectedProductItem?.baseProducts?.material
                      }}</span>
                    </div>
                  </div>

                  <div class="detail-card">
                    <div class="detail-icon">
                      <i class="bi bi-box-seam"></i>
                    </div>
                    <div class="detail-content">
                      <span class="detail-label">Tình trạng</span>
                      <span
                        :class="[
                          'detail-value',
                          'stock-status',
                          (selectedProductItem?.qty || 0) > 0
                            ? 'in-stock'
                            : 'out-of-stock',
                        ]"
                      >
                        {{
                          (selectedProductItem?.qty || 0) > 0
                            ? `Còn ${selectedProductItem?.qty} sản phẩm`
                            : "Hết hàng"
                        }}
                      </span>
                    </div>
                  </div>
                </div>
              </div>

              <!-- Quantity & Actions -->
              <div class="action-section">
                <div class="quantity-section">
                  <label class="quantity-label">
                    <i class="bi bi-123 me-2"></i>
                    Số lượng
                  </label>
                  <div class="quantity-controls">
                    <button
                      @click="decreaseQty"
                      class="qty-btn qty-minus"
                      :disabled="quantityForAddToCartPorductItem <= 1"
                    >
                      <i class="bi bi-dash-lg"></i>
                    </button>
                    <input
                      type="number"
                      v-model="quantityForAddToCartPorductItem"
                      class="qty-input"
                      min="1"
                      :max="selectedProductItem?.qty || 1"
                    />
                    <button
                      @click="increaseQty"
                      class="qty-btn qty-plus"
                      :disabled="
                        quantityForAddToCartPorductItem >= (selectedProductItem?.qty || 1)
                      "
                    >
                      <i class="bi bi-plus-lg"></i>
                    </button>
                  </div>
                </div>

                <div class="action-buttons">
                  <button
                    @click="addToCart"
                    class="btn-action btn-add-cart"
                    :disabled="(selectedProductItem?.qty || 0) <= 0"
                  >
                    <i class="bi bi-bag-plus-fill me-2"></i>
                    <span>Thêm vào giỏ hàng</span>
                  </button>

                  <button class="btn-action btn-buy-now">
                    <i class="bi bi-lightning-charge-fill me-2"></i>
                    <span>Mua ngay</span>
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- Modal Footer -->
        <div class="modal-footer">
          <div class="footer-features">
            <div class="feature-item">
              <i class="bi bi-shield-check"></i>
              <span>Đảm bảo chất lượng</span>
            </div>
            <div class="feature-item">
              <i class="bi bi-arrow-repeat"></i>
              <span>Đổi trả miễn phí</span>
            </div>
            <div class="feature-item">
              <i class="bi bi-truck"></i>
              <span>Giao hàng nhanh</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script setup>
import { ref, onMounted, watch, computed } from "vue";
import axios from "axios";
import api, { authService } from "../../Configs/api";
import PageNavigative from "../Module/PageNavigative.vue";
import { finalHandleCartProgress } from "../../Configs/cart";
import { notification } from "ant-design-vue";
import ProductCard from "../Module/ProductCard.vue";
import { catchUserEvent } from "../../Configs/handleCatchUserProductEvent";
const mapVarriants = ref({});
const mapVarriantsOfListBaseProduct = ref({});

const quantityForAddToCartPorductItem = ref(1);
const data = ref([]);
const selected = ref([]);
const selectedVariant = ref([]);
const selectedCatalogCategory = ref({});
const skuColorLike = ref("");
const categoriesFilter = ref("");
const catalogsFilter = ref([]);
const skuSizeLike = ref("");
const minPriceReq = ref(1);
const maxPriceReq = ref(200000000);
const selectedPrice = ref("");
const products = ref([]);
const pageIndex = ref(0);
const pageSize = ref(5);
const totalPages = ref(0);
const showModal = ref(false);
const selectedProduct = ref(null);
const selectedProductItem = ref(null);
const quantity = ref(1);
const dropDowncatalogCategory = ref([]);
const timeSpent = ref(0);
const keyWord = ref("");
const currentPrice = ref(0);
const SkuSizeList = ref();
const SkuColorList = ref();
const listMapIfSelect = ref([]);
const expandedSections = ref(["category-0", "variant-0", "price"]); // Default expanded sections
const currentImageIndex = ref(0);
const currentImage = ref("");
const images = ref([]);
const productItemBaseOnId = ref(null);
// Function to toggle accordion sections
const toggleAccordion = (sectionId) => {
  const index = expandedSections.value.indexOf(sectionId);
  if (index > -1) {
    expandedSections.value.splice(index, 1);
  } else {
    expandedSections.value.push(sectionId);
  }
};

// Function to check if section is expanded
const isExpanded = (sectionId) => {
  return expandedSections.value.includes(sectionId);
};
function increaseQty() {
  quantityForAddToCartPorductItem.value++;
}

const discountedPrice = computed(() => {
  if (selectedProductItem.value?.promotions[0]?.discountValue) {
    return Math.round(
      currentPrice.value *
        (1 - selectedProductItem.value.promotions[0].discountValue / 100)
    );
  }
  return currentPrice.value;
});
function decreaseQty() {
  if (quantityForAddToCartPorductItem.value > 1) quantityForAddToCartPorductItem.value--;
}
const prevImage = () => {
  if (images.value.length === 0) return;
  currentImageIndex.value =
    (currentImageIndex.value - 1 + images.value.length) % images.value.length;
  currentImage.value = images.value[currentImageIndex.value].imageUrl;
};

const nextImage = () => {
  if (images.value.length === 0) return;
  currentImageIndex.value = (currentImageIndex.value + 1) % images.value.length;
  currentImage.value = images.value[currentImageIndex.value].imageUrl;
};
function selectImage(idx) {
  currentImageIndex.value = idx;
  currentImage.value = images.value[idx].imageUrl;
}

function formatPrice(price) {
  return price.toLocaleString("vi-VN") + "₫";
}

function formatDate(dateStr) {
  const d = new Date(dateStr);
  return d.toLocaleDateString("vi-VN");
}
let timer = null;
const openModal = (product) => {
  fetchProductData(product.id);

  // selectedProductItem.value = product;
  showModal.value = true;
  itemCart.value.productItems = product.productItemId;
  itemCart.value.qty = quantity.value;
};
const closeModal = () => {
  showModal.value = false;
};
onMounted(() => {
  timer = setInterval(() => {
    timeSpent.value++;
  }, 1000);
  api
    .get("/VariantValues")
    .then((resp) => {
      mapVarriants.value = resp.data;
      mapVarriantsOfListBaseProduct.value = resp.data;
      for (const groupName in resp.data) {
        selected.value[groupName] = [];
        selectedVariant.value[groupName] = [];
      }
    })
    .catch((error) => console.log(error));
  api
    .get("/CatalogCategoriesFilter")
    .then(async (resp) => {
      dropDowncatalogCategory.value = resp.data;
      for (const groupName in dropDowncatalogCategory.value) {
        selectedCatalogCategory.value[groupName] = [];
      }
    })
    .catch((error) => console.log(error));
});

const itemCart = ref({
  id: "",
  accounts: "",
  productItems: "",
  promotions: null,
  comboGroup: "",
  comboQty: "",
  comboGroupId: "",
  name: "",
  mainImageUrl: "",
  price: "",
  sku: "",
  qty: "",
  createdAt: "",
  updatedAt: "",
});
const addToCart = () => {
  if (!selectedProductItem.value || quantityForAddToCartPorductItem.value <= 0) return;
  if (quantityForAddToCartPorductItem.value < selectedProductItem.value.safetyStock) {
    authService.updateCart(quantityForAddToCartPorductItem.value);
    clearInterval(timer);

    let payLoad = {
      id: "",
      eventType: "ADDTOCART",
      positionInList: "",
      timeSpentSeconds: timeSpent.value,
      productItemId: selectedProductItem.value.id,
    };
    catchUserEvent(payLoad);

    const itemUpdate = {
      id: "",
      accounts: authService.getUserName(),
      productItems: selectedProductItem.value.id,
      qty: quantity.value,
      promotions: selectedProductItem.value.promotions[0],
      comboGroup: "",
      comboQty: "",
      comboGroupId: "",
      name: selectedProductItem.value.name,
      mainImageUrl: selectedProductItem.value.baseProducts.mainImageUrl,
      price: selectedProductItem.value.price,
      sku: selectedProductItem.value.sku,
      createdAt: "",
      updatedAt: "",
    };
    console.log(itemUpdate);
    authService.updateCart(quantityForAddToCartPorductItem.value);

    if (quantityForAddToCartPorductItem.value <= selectedProductItem.value.qty) {
      console.log("Adding to cart:", itemUpdate);
      finalHandleCartProgress(itemUpdate);
    } else {
      notification.error({
        message: "Thất bại",
        description: `Số lượng tồn kho chỉ còn ${selectedProductItem.value.qty} sản phẩm! ${quantityForAddToCartPorductItem.value}`,
        duration: 4.5,
      });
    }
  }
};
const fetchProductData = async (id) => {
  try {
    const res = await api.get(`/ProductItems/detail/${id}`);
    console.log("Product data:", res.data.content);
    productItemBaseOnId.value = res.data.content;
    SkuColorList.value = new Set(
      productItemBaseOnId.value.map((e) => {
        return e.sku.split("-")[e.sku.split("-").length - 2];
      })
    );
    SkuSizeList.value = new Set(
      productItemBaseOnId.value.map((e) => {
        return e.sku.split("-")[e.sku.split("-").length - 1];
      })
    );
    listMapIfSelect.value = productItemBaseOnId.value.map((e) => {
      return e.sku.split("-");
    });
    const filteredMap = {};
    for (const [groupName, items] of Object.entries(
      mapVarriantsOfListBaseProduct.value
    )) {
      if (groupName === "Màu sắc") {
        let filteredItems = items.filter((item) =>
          SkuColorList.value.has(item.signalSku)
        );
        filteredItems.map((setIsActive) => {
          return { ...setIsActive, isActice: true };
        });
        if (filteredItems.length) filteredMap[groupName] = { ...filteredItems };
      } else if (groupName === "Kích thước") {
        const filteredItems = items.filter((item) =>
          SkuSizeList.value.has(item.signalSku)
        );
        if (filteredItems.length) filteredMap[groupName] = { ...filteredItems };
      }
    }
    // Update the mapVarriants
    mapVarriantsOfListBaseProduct.value = filteredMap;
    selectedProductItem.value = productItemBaseOnId.value[0];

    images.value = productItemBaseOnId.value.flatMap((p) => p.images);
    currentImage.value = images.value[0];
  } catch (err) {
    console.error("Lỗi tải chi tiết sản phẩm:", err);
  }
};

const fetchData = async () => {
  try {
    skuColorLike.value = selected.value["Màu sắc"].join("-");
    skuSizeLike.value = selected.value["Kích thước"].join("-");
    categoriesFilter.value = "";
    for (const groupName in selectedCatalogCategory.value) {
      if (selectedCatalogCategory.value[groupName].length > 0) {
        categoriesFilter.value =
          categoriesFilter.value +
          (categoriesFilter.value.length <= 0 ? "" : "-") +
          selectedCatalogCategory.value[groupName].join("-");
      }
    }
    if (selectedPrice.value.includes("-")) {
      const [min, max] = selectedPrice.value.split("-").map((p) => p.trim());
      minPriceReq.value = min + "000";
      maxPriceReq.value = max + "000";
    } else if (selectedPrice.value.startsWith(">")) {
      maxPriceReq.value = 10000000000;
      minPriceReq.value = selectedPrice.value.replace(">", "").trim() + "000";
    }
    // console.log(minPriceReq.value, maxPriceReq.value)
    const response = await axios.get(
      `http://localhost:8080/api/Product/MultiplrFilter?page=${pageIndex.value}&size=${pageSize.value}&skuColorLikeReq=${skuColorLike.value}&skuSizeLikeReq=${skuSizeLike.value}&minPriceReq=${minPriceReq.value}&maxPriceReq=${maxPriceReq.value}&categories=${categoriesFilter.value}&keyWord=${keyWord.value}`
    );
    data.value = response.data.totalPages;
    products.value = response.data.content;
    console.log("load data, ", response.data.content);
    const rawProducts = response.data.content;
    //handle img with firebase storage
    const updatedProducts = await Promise.all(
      rawProducts.map(async (product) => {
        const imageUrl = product.mainImage;
        return { ...product, imageUrl };
      })
    );
    products.value = updatedProducts;
  } catch (error) {
    console.error("Error fetching variants:", error);
  }
};
watch(() => selected.value["Kích thước"], fetchData);
watch(() => selected.value["Màu sắc"], fetchData);
watch(() => selectedCatalogCategory.value, fetchData, { deep: true });
watch(() => keyWord.value, fetchData);
watch(() => selectedPrice.value, fetchData);
watch(() => pageIndex.value, fetchData);
const listColorBaseOnSelectedSize = ref();
watch(
  () => selectedVariant.value["Kích thước"],
  () => {
    selectedVariant.value["Màu sắc"] = [];
    const filteredMap = {};
    listColorBaseOnSelectedSize.value = listMapIfSelect.value
      .filter(
        (findColor) =>
          selectedVariant.value["Kích thước"] === findColor[findColor.length - 1]
      )
      .map((color) => {
        return color[color.length - 2];
      });
    for (const [groupName, items] of Object.entries(
      mapVarriantsOfListBaseProduct.value
    )) {
      if (groupName === "Màu sắc") {
        const itemList = Array.isArray(items) ? items : Object.values(items);
        const safeColorList = Array.isArray(listColorBaseOnSelectedSize.value)
          ? listColorBaseOnSelectedSize.value
          : [];
        const filteredItems = itemList.map((item) => ({
          ...item,
          isActive: safeColorList.includes(item.signalSku),
        }));
        filteredMap[groupName] = filteredItems;
      } else if (groupName === "Kích thước") {
        const itemList = Array.isArray(items) ? items : Object.values(items);
        const filteredItems = itemList.map((item) => ({
          ...item,
          isActive: true,
        }));
        filteredMap[groupName] = filteredItems;
      }
    }
    mapVarriantsOfListBaseProduct.value = filteredMap;
  }
);
watch(
  () => selectedVariant.value["Màu sắc"],
  () => {
    const color = selectedVariant.value["Màu sắc"];
    const size = selectedVariant.value["Kích thước"];

    if (color && size && size.length !== 0) {
      const targetSku = `${color}-${size}`;
      selectedProductItem.value =
        productItemBaseOnId.value.find((pro) => pro.sku.includes(targetSku)) ||
        productItemBaseOnId.value[0];
    } else {
      const itemList = Array.isArray(productItemBaseOnId.value)
        ? productItemBaseOnId.value
        : [productItemBaseOnId.value];
      selectedProductItem.value = itemList[0];
    }
  }
);
watch(() => pageSize.value, fetchData);
watch(
  () => quantity.value,
  () => (itemCart.value.qty = quantity.value)
);

watch(images, (newImages) => {
  if (newImages.length > 0) {
    currentImage.value = newImages[0].imageUrl;
    currentImageIndex.value = 0;
  }
});
</script>
<style scoped>
/* Page Header */
.page-header {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 4rem 0;
  text-align: center;
  margin-bottom: 2rem;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
}

.header-content {
  max-width: 800px;
  margin: 0 auto;
}

.page-title {
  color: white;
  font-size: 2.8rem;
  font-weight: 700;
  margin-bottom: 1rem;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.3);
  display: flex;
  align-items: center;
  justify-content: center;
}

.page-subtitle {
  color: rgba(255, 255, 255, 0.9);
  font-size: 1.1rem;
  margin: 0;
  font-weight: 400;
  letter-spacing: 2px;
}

/* Main Layout */
.products-container {
  min-height: 100vh;
  background: linear-gradient(to bottom, #f8f9fa 0%, #e9ecef 100%);
}

/* Filter Sidebar */
.filter-sidebar {
  background: white;
  border-radius: 20px;
  padding: 2rem;
  box-shadow: 0 15px 35px rgba(0, 0, 0, 0.1);
  position: sticky;
  top: 2rem;
  max-height: calc(100vh - 4rem);
  overflow-y: auto;
  border: 1px solid rgba(102, 126, 234, 0.1);
}

.variant-group {
  margin-bottom: 1.5rem;
}

.variant-options {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(60px, 1fr));
  gap: 10px;
}

.variant-button {
  display: flex;
  justify-content: center;
  align-items: center;
  color: black;
  padding: 10px 0;
  border: 1px solid #ccc;
  text-align: center;
  border-radius: 8px;
  background-color: #f8f9fa;
  cursor: pointer;
  transition: all 0.2s ease-in-out;
  user-select: none;
  font-weight: 500;
}

.variant-button.active {
  background-color: black;
  color: white;
  border-color: black;
}

.product-details {
  margin-bottom: 30px;
}

.detail-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 12px 0;
  border-bottom: 1px solid #e5e7eb;
}

.detail-item:last-child {
  border-bottom: none;
}

.label {
  font-weight: 600;
  color: #374151;
}

.value {
  color: #1f2937;
}

.stock-status.in-stock {
  color: #059669;
  font-weight: 600;
}

.stock-status.out-of-stock {
  color: #dc2626;
  font-weight: 600;
}

.price-section {
  margin-bottom: 30px;
}

.price-container {
  display: flex;
  align-items: baseline;
  gap: 15px;
  margin-bottom: 10px;
}

.current-price {
  font-size: 2.25rem;
  font-weight: 700;
  color: #dc2626;
}

.original-price {
  font-size: 1.5rem;
  color: #9ca3af;
  text-decoration: line-through;
}

.discount-badge {
  display: inline-flex;
  align-items: center;
  background: linear-gradient(135deg, #ef4444, #dc2626);
  color: white;
  padding: 8px 16px;
  border-radius: 20px;
  font-size: 14px;
  font-weight: 600;
}

/* ==================== PROMOTION BANNER ==================== */
.promotion-banner {
  background: linear-gradient(135deg, #fef3c7, #fde68a);
  border: 2px solid #f59e0b;
  border-radius: 15px;
  padding: 20px;
  margin-bottom: 30px;
}

.promotion-content {
  display: flex;
  align-items: center;
  gap: 15px;
}

.promotion-icon {
  font-size: 24px;
  color: #d97706;
}

.promotion-title {
  color: #92400e;
  font-weight: 600;
  margin: 0;
}

.promotion-period {
  color: #a16207;
  font-size: 14px;
  margin: 0;
}

/* ==================== ACTION SECTION ==================== */
.action-section {
  margin-bottom: 30px;
}

.quantity-selector {
  margin-bottom: 20px;
}

.quantity-label {
  font-weight: 600;
  color: #374151;
  margin-bottom: 10px;
  display: block;
}

.quantity-controls {
  display: flex;
  align-items: center;
  background: #f9fafb;
  border: 2px solid #e5e7eb;
  border-radius: 12px;
  overflow: hidden;
  width: fit-content;
}

.qty-btn {
  width: 45px;
  height: 45px;
  border: none;
  background: white;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: all 0.3s ease;
  font-size: 18px;
  color: #374151;
}

.qty-btn:hover:not(:disabled) {
  background: #667eea;
  color: white;
}

.qty-btn:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.qty-input {
  width: 60px;
  height: 45px;
  border: none;
  text-align: center;
  font-weight: 600;
  font-size: 16px;
  background: transparent;
  outline: none;
}

.action-buttons {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.btn-add-cart,
.btn-buy-now {
  padding: 18px 30px;
  border-radius: 15px;
  font-weight: 600;
  font-size: 16px;
  border: none;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
  text-decoration: none;
}

.btn-add-cart {
  background: linear-gradient(135deg, #667eea, #764ba2);
  color: white;
}

.btn-add-cart:hover:not(:disabled) {
  transform: translateY(-2px);
  box-shadow: 0 10px 25px rgba(102, 126, 234, 0.4);
}

.btn-add-cart:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.btn-buy-now {
  background: linear-gradient(135deg, #fbbf24, #f59e0b);
  color: #92400e;
}

.btn-buy-now:hover {
  transform: translateY(-2px);
  box-shadow: 0 10px 25px rgba(251, 191, 36, 0.4);
}

/* ==================== SHIPPING INFO ==================== */
.shipping-info {
  background: #f8fafc;
  border-radius: 15px;
  padding: 20px;
}

.shipping-item {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 12px;
  color: #374151;
  font-size: 14px;
}

.shipping-item:last-child {
  margin-bottom: 0;
}

.shipping-item i {
  color: #667eea;
  font-size: 16px;
  width: 20px;
}

.filter-header h3 {
  color: #2c3e50;
  font-size: 1.4rem;
  font-weight: 600;
  margin-bottom: 2rem;
  display: flex;
  align-items: center;
  padding-bottom: 1rem;
  border-bottom: 3px solid #667eea;
}

/* Search Section */
.search-section {
  margin-bottom: 2rem;
}

/* ==================== PRODUCT GALLERY ==================== */
.product-gallery {
  position: relative;
}

.main-image-container {
  position: relative;
  border-radius: 20px;
  overflow: hidden;
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
  margin-bottom: 20px;
  background: white;
  max-width: 500px;
  /* Giới hạn kích thước tối đa */
  margin: 0 auto 20px auto;
  /* Căn giữa */
}

.selected-variant {
  background-color: #f0f8ff;
  border-left: 4px solid #007bff;
}

.main-image {
  width: 100%;
  aspect-ratio: 4/5;
  height: auto;
  object-fit: cover;
  transition: transform 0.3s ease;
}

.gallery-nav {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  background: rgba(255, 255, 255, 0.9);
  border: none;
  width: 50px;
  height: 50px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 20px;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
  z-index: 10;
}

.gallery-nav:hover {
  background: white;
  transform: translateY(-50%) scale(1.1);
}

.gallery-nav-prev {
  left: 20px;
}

.gallery-nav-next {
  right: 20px;
}

.image-indicators {
  position: absolute;
  bottom: 20px;
  left: 50%;
  transform: translateX(-50%);
  display: flex;
  gap: 8px;
  z-index: 10;
}

.indicator {
  width: 12px;
  height: 12px;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.5);
  cursor: pointer;
  transition: all 0.3s ease;
}

.indicator.active {
  background: white;
  transform: scale(1.2);
}

.thumbnail-gallery {
  display: flex;
  gap: 12px;
  overflow-x: auto;
  padding: 10px 0;
  justify-content: center;
  /* Căn giữa thumbnail */
  max-width: 500px;
  /* Cùng kích thước với main image */
  margin: 0 auto;
  /* Căn giữa */
}

.filter-group h3 {
  font-size: 18px;
  font-weight: bold;
  display: flex;
  align-items: center;
  margin-top: 30px;
  margin-bottom: 16px;
  color: #2e2e2e;
}

.line {
  width: 3px;
  height: 20px;
  background-color: #2e2e2e;
  margin-right: 10px;
}

.filter-list {
  list-style: none;
  padding-left: 0;
  margin-bottom: 20px;
}

.filter-list li {
  margin-bottom: 12px;
}

.accordion-button:focus {
  box-shadow: none;
  outline: none;
}

.thumbnail-item {
  flex-shrink: 0;
  width: 90px;
  height: 112px;
  /* Tỷ lệ 4:5 cho thumbnail */
  border-radius: 12px;
  overflow: hidden;
  cursor: pointer;
  border: 3px solid transparent;
  transition: all 0.3s ease;
}

.thumbnail-item.active {
  border-color: #667eea;
  transform: scale(1.05);
}

.thumbnail-item img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.modern-searchBox {
  position: relative;
  display: flex;
  align-items: center;
  background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
  border-radius: 25px;
  padding: 0.5rem;
  border: 2px solid transparent;
  transition: all 0.3s ease;
}

.modern-searchBox:focus-within {
  border-color: #667eea;
  box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
  background: white;
}

.search-icon {
  position: absolute;
  left: 1.2rem;
  color: #7f8c8d;
  font-size: 1.1rem;
  z-index: 2;
}

.modern-searchInput {
  width: 100%;
  padding: 1rem 3rem 1rem 3rem;
  border: none;
  background: transparent;
  font-size: 1rem;
  color: #2c3e50;
  outline: none;
}

.modern-searchInput::placeholder {
  color: #95a5a6;
}

.search-clear {
  position: absolute;
  right: 1rem;
  background: #e74c3c;
  color: white;
  border: none;
  border-radius: 50%;
  width: 28px;
  height: 28px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  font-size: 0.9rem;
  transition: all 0.3s ease;
}

.search-clear:hover {
  background: #c0392b;
  transform: scale(1.1);
}

/* Modern Filter Cards */
.filter-section {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.modern-filter-card {
  border: 2px solid #f1f3f4;
  border-radius: 15px;
  overflow: hidden;
  transition: all 0.3s ease;
  background: white;
}

.filter-card-header {
  padding: 1.2rem 1.5rem;
  background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: space-between;
  transition: all 0.3s ease;
  border-bottom: 1px solid #e9ecef;
}

.filter-card-header:hover {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
}

.filter-title {
  font-weight: 600;
  font-size: 1rem;
  display: flex;
  align-items: center;
  color: #2c3e50;
}

.filter-card-header:hover .filter-title {
  color: white;
}

.toggle-icon {
  transition: transform 0.3s ease;
  font-size: 1rem;
  color: #7f8c8d;
}

.filter-card-header:hover .toggle-icon {
  color: white;
}

.toggle-icon.rotated {
  transform: rotate(180deg);
}

.filter-card-content {
  max-height: 0;
  overflow: hidden;
  transition: max-height 0.4s ease;
}

.filter-card-content.expanded {
  max-height: 500px;
}

.filter-options {
  padding: 1.5rem;
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.modern-filter-option {
  display: flex;
  align-items: center;
  gap: 1rem;
  cursor: pointer;
  padding: 0.8rem;
  border-radius: 10px;
  transition: background-color 0.2s ease;
  border: 1px solid transparent;
}

.modern-filter-option:hover {
  background: #f8f9fa;
}

.modern-filter-option input[type="checkbox"] {
  display: none;
}

.checkmark {
  width: 22px;
  height: 22px;
  border: 2px solid #dee2e6;
  border-radius: 6px;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.3s ease;
  position: relative;
  background: white;
}

.modern-filter-option input[type="checkbox"]:checked + .checkmark {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-color: #667eea;
  transform: scale(1.1);
}

.modern-filter-option input[type="checkbox"]:checked + .checkmark::after {
  content: "✓";
  color: white;
  font-size: 0.9rem;
  font-weight: bold;
}

.option-text {
  color: #2c3e50;
  font-weight: 500;
  font-size: 0.95rem;
}

/* Price Options */
.price-options {
  padding: 1.5rem;
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.modern-price-option {
  display: flex;
  align-items: center;
  gap: 1rem;
  cursor: pointer;
  padding: 1rem;
  border-radius: 12px;
  transition: background-color 0.2s ease;
  border: 2px solid transparent;
  background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
}

.modern-price-option:hover {
  background: white;
}

.modern-price-option input[type="radio"] {
  display: none;
}

.radio-mark {
  width: 22px;
  height: 22px;
  border: 2px solid #dee2e6;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.3s ease;
  position: relative;
  background: white;
}

.modern-price-option input[type="radio"]:checked + .radio-mark {
  border-color: #667eea;
  transform: scale(1.1);
}

.modern-price-option input[type="radio"]:checked + .radio-mark::after {
  content: "";
  width: 12px;
  height: 12px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 50%;
}

.price-range {
  color: #2c3e50;
  font-weight: 600;
  font-size: 1rem;
}

/* Products Section */
.products-section {
  background: white;
  border-radius: 20px;
  padding: 2.5rem;
  box-shadow: 0 15px 35px rgba(0, 0, 0, 0.1);
  border: 1px solid rgba(102, 126, 234, 0.1);
}

.products-header {
  margin-bottom: 2rem;
  padding-bottom: 1.5rem;
  border-bottom: 3px solid #f8f9fa;
}

.products-title {
  color: #2c3e50;
  font-size: 2rem;
  font-weight: 700;
  margin-bottom: 1rem;
  display: flex;
  align-items: center;
}

.products-count {
  color: #7f8c8d;
  font-size: 1rem;
  font-weight: 500;
  display: flex;
  align-items: center;
  background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
  padding: 0.8rem 1.2rem;
  border-radius: 25px;
  border: 1px solid #e9ecef;
  width: fit-content;
}

/* Mobile Search Box */
.modern-searchBox-mobile {
  margin-top: 1rem;
  position: relative;
  display: flex;
  align-items: center;
  background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
  border-radius: 25px;
  padding: 0.5rem;
  border: 2px solid transparent;
  transition: all 0.3s ease;
}

.modern-searchBox-mobile:focus-within {
  border-color: #667eea;
  box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
  background: white;
}

/* Empty State */
.empty-state {
  padding: 4rem 2rem;
}

.empty-content {
  text-align: center;
  max-width: 400px;
  margin: 0 auto;
}

.empty-icon {
  font-size: 5rem;
  margin-bottom: 1.5rem;
  color: #bdc3c7;
}

.empty-content h3 {
  color: #2c3e50;
  font-size: 1.5rem;
  margin-bottom: 0.8rem;
  font-weight: 600;
}

.empty-content p {
  color: #7f8c8d;
  font-size: 1rem;
}

/* Pagination */
.pagination-wrapper {
  margin-top: 3rem;
  display: flex;
  justify-content: center;
}

/* ==================== MODERN MODAL STYLES ==================== */
.modern-modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.7);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1050;
  padding: 1rem;
  animation: overlayFadeIn 0.2s ease;
}

@keyframes overlayFadeIn {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}

.modern-modal {
  background: white;
  border-radius: 16px;
  max-width: 1400px;
  width: 100%;
  max-height: 95vh;
  overflow: hidden;
  box-shadow: 0 15px 35px rgba(0, 0, 0, 0.15);
  animation: modalSlideIn 0.3s ease;
  display: flex;
  flex-direction: column;
}

@keyframes modalSlideIn {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

/* Modal Header */
.modal-header {
  padding: 2rem 2.5rem;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.modal-title-section {
  flex: 1;
}

.modal-title {
  font-size: 1.8rem;
  font-weight: 600;
  margin: 0 0 0.5rem 0;
  display: flex;
  align-items: center;
  color: white;
}

.modal-subtitle {
  font-size: 1rem;
  opacity: 0.9;
  margin: 0;
  font-weight: 400;
}

.modal-close {
  background: rgba(255, 255, 255, 0.15);
  border: 2px solid rgba(255, 255, 255, 0.2);
  border-radius: 12px;
  width: 48px;
  height: 48px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: all 0.2s ease;
  color: white;
  font-size: 1.2rem;
}

.modal-close:hover {
  background: rgba(255, 255, 255, 0.25);
}

/* Modal Body */
.modal-body {
  padding: 0;
  flex: 1;
  overflow-y: auto;
}

.product-layout {
  display: grid;
  grid-template-columns: 1.2fr 1fr;
  min-height: 650px;
}

/* Gallery Section */
.gallery-section {
  padding: 2.5rem;
  background: #f8f9fa;
  border-right: 1px solid #e5e7eb;
}

.product-gallery {
  position: sticky;
  top: 2rem;
}

.main-image-container {
  position: relative;
  border-radius: 16px;
  overflow: hidden;
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.1);
  margin-bottom: 1.5rem;
  background: white;
  aspect-ratio: 1;
}

.main-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.3s ease;
}

.main-image-container:hover .main-image {
  transform: scale(1.03);
}

.gallery-nav {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  background: rgba(255, 255, 255, 0.9);
  border: none;
  width: 44px;
  height: 44px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 18px;
  cursor: pointer;
  transition: all 0.2s ease;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  color: #374151;
}

.gallery-nav:hover {
  background: white;
  color: #667eea;
}

.gallery-nav-prev {
  left: 20px;
}
.gallery-nav-next {
  right: 20px;
}

.image-counter {
  position: absolute;
  top: 20px;
  right: 20px;
  background: rgba(0, 0, 0, 0.7);
  color: white;
  padding: 8px 16px;
  border-radius: 20px;
  font-size: 14px;
  font-weight: 600;
  backdrop-filter: blur(10px);
}

.thumbnail-gallery {
  display: flex;
  gap: 16px;
  overflow-x: auto;
  padding: 12px 0;
  scrollbar-width: none;
  -ms-overflow-style: none;
}

.thumbnail-gallery::-webkit-scrollbar {
  display: none;
}

.thumbnail-item {
  position: relative;
  flex-shrink: 0;
  width: 80px;
  height: 80px;
  border-radius: 12px;
  overflow: hidden;
  cursor: pointer;
  border: 2px solid transparent;
  transition: all 0.2s ease;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.thumbnail-item.active {
  border-color: #667eea;
  transform: translateY(-2px);
}

.thumbnail-item img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.thumbnail-overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(102, 126, 234, 0.1);
  opacity: 0;
  transition: opacity 0.2s ease;
}

.thumbnail-item.active .thumbnail-overlay {
  opacity: 1;
}

/* Info Section */
.info-section {
  padding: 2.5rem;
  background: white;
  overflow-y: auto;
}

.product-header {
  margin-bottom: 2rem;
  padding-bottom: 1.5rem;
  border-bottom: 1px solid #e5e7eb;
}

.product-title {
  font-size: 2rem;
  font-weight: 600;
  color: #1f2937;
  margin-bottom: 1rem;
  line-height: 1.3;
}

.rating-section {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 1.5rem;
}

.stars {
  display: flex;
  gap: 3px;
  color: #fbbf24;
  font-size: 18px;
}

.rating-text {
  font-weight: 600;
  color: #374151;
  font-size: 16px;
  background: #f3f4f6;
  padding: 4px 12px;
  border-radius: 16px;
}

.rating-count {
  color: #6b7280;
  font-size: 14px;
  font-weight: 400;
}

/* Price Section */
.price-section {
  margin-bottom: 2rem;
}

.price-row {
  display: flex;
  align-items: baseline;
  gap: 16px;
  margin-bottom: 8px;
}

.current-price {
  font-size: 2.5rem;
  font-weight: 700;
  color: #dc2626;
}

.original-price {
  font-size: 1.5rem;
  color: #9ca3af;
  text-decoration: line-through;
  opacity: 0.8;
}

.discount-badge {
  display: inline-flex;
  align-items: center;
  background: #dc2626;
  color: white;
  padding: 6px 12px;
  border-radius: 16px;
  font-size: 14px;
  font-weight: 600;
}

/* Variant Section */
.variant-section {
  margin-bottom: 2rem;
}

.variant-group {
  margin-bottom: 1.5rem;
}

.variant-label {
  font-size: 1.1rem;
  font-weight: 600;
  color: #374151;
  margin-bottom: 0.8rem;
  display: flex;
  align-items: center;
}

.variant-options {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.variant-button {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 12px 20px;
  border: 2px solid #e5e7eb;
  border-radius: 12px;
  background: #f9fafb;
  cursor: pointer;
  transition: all 0.2s ease;
  user-select: none;
  font-weight: 500;
  min-width: 70px;
}

.variant-button:hover:not(.disabled) {
  border-color: #667eea;
  background: #f0f2ff;
}

.variant-button.active {
  background: #667eea;
  color: white;
  border-color: #667eea;
}

.variant-button.disabled {
  opacity: 0.4;
  cursor: not-allowed;
  background: #f3f4f6;
  color: #9ca3af;
}

.variant-content {
  position: relative;
}

/* Product Details */
.product-details {
  margin-bottom: 2.5rem;
}

.details-title {
  font-size: 1.2rem;
  font-weight: 700;
  color: #374151;
  margin-bottom: 1.5rem;
  display: flex;
  align-items: center;
}

.details-grid {
  display: grid;
  gap: 16px;
}

.detail-card {
  display: flex;
  align-items: center;
  gap: 16px;
  padding: 16px 20px;
  background: #f8f9fa;
  border-radius: 12px;
  border: 1px solid #e5e7eb;
  transition: all 0.2s ease;
}

.detail-card:hover {
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.detail-icon {
  width: 40px;
  height: 40px;
  background: #667eea;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-size: 18px;
  flex-shrink: 0;
}

.detail-content {
  flex: 1;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.detail-label {
  font-weight: 600;
  color: #6b7280;
  font-size: 15px;
}

.detail-value {
  font-weight: 700;
  color: #1f2937;
  font-size: 16px;
}

.stock-status.in-stock {
  color: #059669;
  background: #ecfdf5;
  padding: 6px 16px;
  border-radius: 20px;
  font-size: 14px;
  font-weight: 700;
}

.stock-status.out-of-stock {
  color: #dc2626;
  background: #fef2f2;
  padding: 6px 16px;
  border-radius: 20px;
  font-size: 14px;
  font-weight: 700;
}

/* Action Section */
.action-section {
  margin-bottom: 2rem;
}

.quantity-section {
  margin-bottom: 2rem;
}

.quantity-label {
  font-weight: 700;
  color: #374151;
  margin-bottom: 16px;
  display: flex;
  align-items: center;
  font-size: 18px;
}

.quantity-controls {
  display: flex;
  align-items: center;
  background: #f9fafb;
  border: 2px solid #e5e7eb;
  border-radius: 12px;
  overflow: hidden;
  width: fit-content;
}

.qty-btn {
  width: 44px;
  height: 44px;
  border: none;
  background: white;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: all 0.2s ease;
  font-size: 18px;
  color: #374151;
  font-weight: 600;
}

.qty-btn:hover:not(:disabled) {
  background: #667eea;
  color: white;
}

.qty-btn:disabled {
  opacity: 0.4;
  cursor: not-allowed;
}

.qty-input {
  width: 80px;
  height: 44px;
  border: none;
  text-align: center;
  font-weight: 600;
  font-size: 16px;
  background: transparent;
  outline: none;
  color: #1f2937;
}

.action-buttons {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.btn-action {
  padding: 16px 24px;
  border-radius: 12px;
  font-weight: 600;
  font-size: 16px;
  border: none;
  cursor: pointer;
  transition: all 0.2s ease;
  display: flex;
  align-items: center;
  justify-content: center;
  text-decoration: none;
}

.btn-add-cart {
  background: #667eea;
  color: white;
  box-shadow: 0 4px 12px rgba(102, 126, 234, 0.3);
}

.btn-add-cart:hover:not(:disabled) {
  transform: translateY(-1px);
  box-shadow: 0 6px 16px rgba(102, 126, 234, 0.4);
}

.btn-add-cart:disabled {
  opacity: 0.6;
  cursor: not-allowed;
  transform: none;
}

.btn-buy-now {
  background: #f59e0b;
  color: white;
  box-shadow: 0 4px 12px rgba(245, 158, 11, 0.3);
}

.btn-buy-now:hover {
  transform: translateY(-1px);
  box-shadow: 0 6px 16px rgba(245, 158, 11, 0.4);
}

/* Modal Footer */
.modal-footer {
  padding: 1.5rem 2.5rem;
  background: #f8f9fa;
  border-top: 1px solid #e5e7eb;
}

.footer-features {
  display: flex;
  justify-content: center;
  gap: 2rem;
}

.feature-item {
  display: flex;
  align-items: center;
  gap: 8px;
  color: #6b7280;
  font-size: 14px;
  font-weight: 500;
}

.feature-item i {
  color: #667eea;
  font-size: 16px;
}

/* Responsive Design */
@media (max-width: 1200px) {
  .product-layout {
    grid-template-columns: 1fr;
  }

  .gallery-section {
    border-right: none;
    border-bottom: 1px solid #e5e7eb;
  }
}

@media (max-width: 768px) {
  .modern-modal {
    margin: 0.5rem;
    border-radius: 20px;
  }

  .modal-header {
    padding: 1.5rem;
  }

  .modal-title {
    font-size: 1.5rem;
  }

  .gallery-section,
  .info-section {
    padding: 2rem 1.5rem;
  }

  .current-price {
    font-size: 2.2rem;
  }

  .variant-options {
    justify-content: center;
  }

  .footer-features {
    flex-direction: column;
    gap: 1rem;
    text-align: center;
  }
}

/* Line accent */
.line {
  width: 4px;
  height: 20px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  margin-right: 12px;
  border-radius: 2px;
}

/* Responsive Design */
@media (max-width: 992px) {
  .page-title {
    font-size: 2.2rem;
    flex-direction: column;
    gap: 0.8rem;
  }

  .product-preview {
    grid-template-columns: 1fr;
    gap: 2rem;
  }

  .filter-sidebar {
    position: relative;
    top: 0;
    max-height: none;
    margin-bottom: 2rem;
  }
}

@media (max-width: 768px) {
  .page-header {
    padding: 2.5rem 0;
  }

  .page-title {
    font-size: 1.8rem;
  }

  .products-section,
  .filter-sidebar {
    padding: 1.5rem;
    border-radius: 15px;
  }

  .modal-header,
  .modal-body,
  .modal-footer {
    padding: 1.5rem;
  }

  .modern-modal {
    border-radius: 20px;
    margin: 1rem;
  }

  .modal-footer {
    flex-direction: column;
  }

  .btn {
    width: 100%;
    justify-content: center;
  }
}

@media (max-width: 480px) {
  .page-title {
    font-size: 1.5rem;
  }

  .container-fluid {
    padding: 0 1rem !important;
  }

  .filter-options,
  .price-options {
    padding: 1rem;
  }

  .modern-filter-card,
  .modern-modal {
    border-radius: 12px;
  }
}

/* Custom scrollbar for sidebar */
.filter-sidebar::-webkit-scrollbar {
  width: 6px;
}

.filter-sidebar::-webkit-scrollbar-track {
  background: #f1f1f1;
  border-radius: 3px;
}

.filter-sidebar::-webkit-scrollbar-thumb {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 3px;
}

.filter-sidebar::-webkit-scrollbar-thumb:hover {
  background: linear-gradient(135deg, #5a67d8 0%, #6b46c1 100%);
}
</style>
