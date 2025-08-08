<template>
  <div class="product-detail-container" v-if="selectedProduct">
    <!-- Hero Section -->
    <div class="hero-section">
      <div class="container py-5">
        <!-- Breadcrumb -->
        <nav class="breadcrumb-modern mb-4">
          <router-link to="/products" class="breadcrumb-link">
            <i class="bi bi-house-door me-2"></i>Trang chủ
          </router-link>
          <i class="bi bi-chevron-right breadcrumb-separator"></i>
          <router-link to="/products" class="breadcrumb-link">Sản phẩm</router-link>
          <i class="bi bi-chevron-right breadcrumb-separator"></i>
          <span class="breadcrumb-current">{{ selectedProduct.baseProducts.name }}</span>
        </nav>

        <!-- Product Content -->
        <div class="row g-5">
          <!-- Image Gallery -->
          <div class="col-lg-7">
            <div class="product-gallery">
              <!-- Main Image -->
              <div class="main-image-container">
                <img :src="currentImage" class="main-image" />

                <!-- Navigation Arrows -->
                <button class="gallery-nav gallery-nav-prev" @click="prevImage" v-if="images.length > 1">
                  <i class="bi bi-chevron-left"></i>
                </button>
                <button class="gallery-nav gallery-nav-next" @click="nextImage" v-if="images.length > 1">
                  <i class="bi bi-chevron-right"></i>
                </button>

                <!-- Image Indicators -->
                <div class="image-indicators" v-if="images.length > 1">
                  <span v-for="(img, idx) in images" :key="idx"
                    :class="['indicator', { active: currentImageIndex === idx }]" @click="selectImage(idx)"></span>
                </div>
              </div>

              <!-- Thumbnail Gallery -->
              <div class="thumbnail-gallery" v-if="images.length > 1">
                <div v-for="(img, idx) in images" :key="idx"
                  :class="['thumbnail-item', { active: currentImageIndex === idx }]" @click="selectImage(idx)">
                  <img :src="img.imageUrl" :alt="`Ảnh ${idx + 1}`" />
                </div>
              </div>
            </div>
          </div>

          <!-- Product Info -->
          <div class="col-lg-5">
            <div class="product-info">
              <!-- Product Title & Rating -->
              <div class="product-header">
                <h1 class="product-title">{{ selectedProduct.baseProducts.name }}</h1>

                <div v-for="(items, groupName, index) in mapVarriants" :key="groupName" class="variant-group">
                  <h5 class="mb-2">Select {{ groupName }}</h5>
                  <div class="variant-options">
                    <label v-for="item in items" :key="item.id" class="variant-button"
                      :class="{ active: selected[groupName] === item.signalSku }">
                      <input type="radio" :name="groupName" :value="item.signalSku" :disabled="item.isActive === false"
                        v-model="selected[groupName]" class="d-none" />
                      {{ item.description }}
                    </label>
                  </div>
                </div>

                <div class="rating-section">
                  <div class="stars">
                    <i v-for="i in 5" :key="i"
                      :class="['bi', i <= selectedProduct.baseProducts.rating ? 'bi-star-fill' : 'bi-star']"></i>
                  </div>
                  <span class="rating-text">{{ selectedProduct.baseProducts.rating.toFixed(1) }}</span>
                  <span class="reviews-count">({{ totalReviews }} đánh giá)</span>
                </div>
              </div>

              <!-- Price Section -->
              <div class="price-section">
                <template v-if="discountedPrice !== currentPrice">
                  <div class="price-container">
                    <span class="current-price">{{ formatPrice(discountedPrice) }}</span>
                    <span class="original-price">{{ formatPrice(currentPrice) }}</span>
                  </div>
                  <div class="discount-badge">
                    <i class="bi bi-tag-fill me-1"></i>
                    Giảm {{ promotion.promotions.discountPercent }}%
                  </div>
                </template>
                <template v-else>
                  <div class="price-container">
                    <span class="current-price">{{ formatPrice(selectedProduct.price) }}</span>
                  </div>
                </template>
              </div>

              <!-- Promotion Banner -->
              <!-- <div class="promotion-banner" v-if="promotion && promotion.promotions">
                <div class="promotion-content">
                  <i class="bi bi-gift-fill promotion-icon"></i>
                  <div class="promotion-details">
                    <h6 class="promotion-title">{{ promotion.promotions.name }}</h6>
                    <p class="promotion-period">
                      Từ {{ formatDate(promotion.promotions.startDate) }} đến
                      {{ formatDate(promotion.promotions.endDate) }}
                    </p>
                  </div>
                </div>
              </div> -->

              <!-- Product Details -->
              <div class="product-details">
                <div class="detail-item">
                  <span class="label">Chất liệu:</span>
                  <span class="value">{{ selectedProduct.baseProducts.material }}</span>
                </div>
                <div class="detail-item">
                  <span class="label">Mã sản phẩm:</span>
                  <span class="value">{{ selectedProduct.sku }}</span>
                </div>
                <div class="detail-item">
                  <span class="label">Tình trạng:</span>
                  <span :class="[
                    'value',
                    'stock-status',
                    selectedProduct.qty > 0 ? 'in-stock' : 'out-of-stock',
                  ]">
                    {{ selectedProduct.qty > 0 ? `Còn ${selectedProduct.qty} sản phẩm` : "Hết hàng" }}
                  </span>
                </div>
              </div>

              <!-- Quantity & Actions -->
              <div class="action-section">
                <div class="quantity-selector">
                  <label class="quantity-label">Số lượng:</label>
                  <div class="quantity-controls">
                    <button @click="decreaseQty" class="qty-btn qty-btn-minus" :disabled="quantity <= 1">
                      <i class="bi bi-dash"></i>
                    </button>
                    <input type="number" v-model="quantity" class="qty-input" min="1" :max="selectedProduct.qty" />
                    <button @click="increaseQty" class="qty-btn qty-btn-plus"
                      :disabled="quantity >= selectedProduct.qty">
                      <i class="bi bi-plus"></i>
                    </button>
                  </div>
                </div>

                <div class="action-buttons">
                  <button @click="addToCart" class="btn-add-cart" :disabled="selectedProduct.qty <= 0">
                    <i class="bi bi-bag-plus me-2"></i>
                    Thêm vào giỏ hàng
                  </button>

                  <button class="btn-buy-now">
                    <i class="bi bi-lightning-fill me-2"></i>
                    Mua ngay
                  </button>
                </div>
              </div>

              <!-- Shipping Info -->
              <div class="shipping-info">
                <div class="shipping-item">
                  <i class="bi bi-truck"></i>
                  <span>Miễn phí giao hàng cho đơn từ 1.000.000₫</span>
                </div>
                <div class="shipping-item">
                  <i class="bi bi-clock"></i>
                  <span>Giao hàng trong 3-7 ngày làm việc</span>
                </div>
                <div class="shipping-item">
                  <i class="bi bi-arrow-repeat"></i>
                  <span>Đổi trả miễn phí trong 30 ngày</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Product Details Tabs -->
    <div class="details-section">
      <div class="container">
        <div class="tabs-container">
          <ul class="modern-tabs">
            <li class="tab-item">
              <button :class="['tab-button', { active: activeTab === 'desc' }]" @click="activeTab = 'desc'">
                <i class="bi bi-info-circle me-2"></i>
                Mô tả sản phẩm
              </button>
            </li>
            <li class="tab-item">
              <button :class="['tab-button', { active: activeTab === 'review' }]" @click="activeTab = 'review'">
                <i class="bi bi-chat-square-text me-2"></i>
                Đánh giá ({{ totalReviews }})
              </button>
            </li>
          </ul>

          <div class="tab-content-modern">
            <!-- Description Tab -->
            <div v-show="activeTab === 'desc'" class="tab-panel">
              <div class="description-content">
                <div class="row g-4">
                  <div class="col-lg-8">
                    <h4 class="section-title">Thông tin sản phẩm</h4>
                    <div class="description-text">
                      <p>
                        {{
                          product.description ||
                          "Sản phẩm chất lượng cao, được làm từ chất liệu tốt nhất."
                        }}
                      </p>
                    </div>

                    <h5 class="features-title">Đặc điểm nổi bật</h5>
                    <ul class="features-list">
                      <li>
                        <i class="bi bi-check-circle-fill"></i> Chất liệu cao cấp, bền đẹp
                      </li>
                      <li>
                        <i class="bi bi-check-circle-fill"></i> Thiết kế hiện đại, thời
                        trang
                      </li>
                      <li>
                        <i class="bi bi-check-circle-fill"></i> Dễ dàng bảo quản và giặt
                        ủi
                      </li>
                      <li>
                        <i class="bi bi-check-circle-fill"></i> Phù hợp với nhiều dịp khác
                        nhau
                      </li>
                    </ul>
                  </div>

                  <div class="col-lg-4">
                    <div class="specs-card">
                      <h5 class="specs-title">Thông số kỹ thuật</h5>
                      <div class="spec-row">
                        <span class="spec-label">Chất liệu:</span>
                        <span class="spec-value">{{ selectedProduct.baseProducts.material }}</span>
                      </div>
                      <div class="spec-row">
                        <span class="spec-label">Mã sản phẩm:</span>
                        <span class="spec-value">{{ selectedProduct.sku }}</span>
                      </div>
                      <div class="spec-row">
                        <span class="spec-label">Tồn kho:</span>
                        <span class="spec-value">{{ selectedProduct.qty }} sản phẩm</span>
                      </div>
                      <div class="spec-row">
                        <span class="spec-label">Lượt mua:</span>
                        <span class="spec-value">{{ selectedProduct.turnBuy }} lượt</span>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <!-- Reviews Tab -->
            <div v-show="activeTab === 'review'" class="tab-panel">
              <div class="reviews-content">
                <!-- Rating Summary -->
                <div class="rating-summary">
                  <div class="rating-overview">
                    <div class="average-rating">
                      <span class="big-rating">{{ averageRating.toFixed(1) }}</span>
                      <div class="rating-stars">
                        <i v-for="i in 5" :key="i" :class="['bi', i <= averageRating ? 'bi-star-fill' : 'bi-star']"></i>
                      </div>
                      <p class="rating-count">{{ totalReviews }} đánh giá</p>
                    </div>
                  </div>
                </div>

                <!-- Review Form -->
                <div class="review-form-card">
                  <h5 class="form-title">Viết đánh giá của bạn</h5>
                  <form @submit.prevent="submitReview" class="review-form">
                    <div class="rating-input">
                      <label class="form-label">Đánh giá của bạn:</label>
                      <div class="star-rating">
                        <i v-for="star in 5" :key="star" @click="newReview.rating = star" :class="[
                          'bi',
                          star <= newReview.rating ? 'bi-star-fill' : 'bi-star',
                        ]" class="star-button"></i>
                      </div>
                    </div>
                    <div class="form-group">
                      <label class="form-label">Nội dung đánh giá:</label>
                      <textarea class="form-textarea" rows="4" v-model="newReview.text"
                        placeholder="Chia sẻ trải nghiệm của bạn về sản phẩm này..." required></textarea>
                    </div>

                    <div class="form-group">
                      <label class="form-label">Hình ảnh (tùy chọn):</label>
                      <CloudinaryUploader :key="uploaderKey" @uploaded="handleImageUploaded" />
                    </div>

                    <button type="submit" class="submit-review-btn">
                      <i class="bi bi-send me-2"></i>
                      Gửi đánh giá
                    </button>
                  </form>
                </div>

                <!-- Reviews List -->
                <div class="reviews-list">
                  <div v-for="review in reviews" :key="review.id" class="review-card">
                    <div class="review-header">
                      <div class="reviewer-info">
                        <div class="reviewer-avatar">
                          {{ review.accountName.charAt(0).toUpperCase() }}
                        </div>
                        <div class="reviewer-details">
                          <h6 class="reviewer-name">{{ review.accountName }}</h6>
                          <div class="review-rating">
                            <i v-for="i in 5" :key="i" :class="[
                              'bi',
                              i <= review.rating ? 'bi-star-fill' : 'bi-star',
                            ]"></i>
                          </div>
                        </div>
                      </div>
                      <span class="review-date">{{
                        formatTimeAgo(review.createdAt)
                      }}</span>
                    </div>

                    <div class="review-content">
                      <p>{{ review.comment }}</p>

                      <div class="review-images" v-if="review.imageUrl1 || review.imageUrl2 || review.imageUrl3">
                        <img v-if="review.imageUrl1" :src="review.imageUrl1" alt="Ảnh đánh giá 1"
                          class="review-image" />
                        <img v-if="review.imageUrl2" :src="review.imageUrl2" alt="Ảnh đánh giá 2"
                          class="review-image" />
                        <img v-if="review.imageUrl3" :src="review.imageUrl3" alt="Ảnh đánh giá 3"
                          class="review-image" />
                      </div>
                    </div>
                  </div>

                  <!-- Pagination -->
                  <div class="pagination-container" v-if="totalPages > 1">
                    <button class="pagination-btn" @click="changePage(currentPage - 1)" :disabled="currentPage === 0">
                      <i class="bi bi-chevron-left"></i>
                      Trước
                    </button>

                    <div class="page-numbers">
                      <button v-for="page in totalPages" :key="page" @click="changePage(page - 1)"
                        :class="['page-btn', { active: page - 1 === currentPage }]">
                        {{ page }}
                      </button>
                    </div>

                    <button class="pagination-btn" @click="changePage(currentPage + 1)"
                      :disabled="currentPage === totalPages - 1">
                      Tiếp
                      <i class="bi bi-chevron-right"></i>
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- Related Products -->
    <div class="related-products-section" v-if="relatedItems.length > 0">
      <div class="container">
        <div class="section-header">
          <h3 class="section-title">Sản phẩm liên quan</h3>
          <p class="section-subtitle">Khám phá thêm những sản phẩm tương tự</p>
        </div>

        <div class="related-products-grid">
          <div class="product-card" v-for="item in relatedItems.slice(0, 4)" :key="item.id">
            <div class="product-image-container">
              <img :src="item.imageUrl" :alt="item.name" class="product-image" />
              <div class="product-overlay">
                <router-link :to="`/product/${item.id}`" class="view-product-btn">
                  <i class="bi bi-eye me-2"></i>
                  Xem chi tiết
                </router-link>
              </div>
            </div>

            <div class="product-card-body">
              <h6 class="product-card-title">{{ item.name }}</h6>
              <div class="product-card-price">
                {{ formatPrice(item.price) }}
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>

  <div v-else class="loading-container">
    <div class="loading-content">
      <div class="spinner"></div>
      <p>Đang tải chi tiết sản phẩm...</p>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref, computed, watch } from "vue";
import { useRoute } from "vue-router";
import api, { authService } from "../../Configs/api";
import { finalHandleCartProgress } from "../../Configs/cart";
import { notification } from "ant-design-vue";
import CloudinaryUploader from "../Module/Cloudinary.vue";

const route = useRoute();
const productId = ref(route.params.id);

const product = ref(null);
const images = ref([]);
const promotion = ref(null);

const currentImage = ref("");
const currentPrice = ref(0);
const quantity = ref(1);
const activeTab = ref("desc");

const reviews = ref([]);
const newReview = ref({ text: "", rating: 5 });

const relatedItems = ref([]);

const selected = ref([]);
const listMapIfSelect = ref([]);
const mapVarriants = ref({});
const currentPage = ref(0);
const pageSize = ref(5);
const totalPages = ref(0);
const uploaderKey = ref(Date.now());

const discountedPrice = computed(() => {

  if (selectedProduct.value?.promotions[0]?.discountValue) {
    return Math.round(
      currentPrice.value * (1 - selectedProduct.value.promotions[0].discountValue / 100)
    );
  }
  return currentPrice.value;
});
const SkuSizeList = ref();
const SkuColorList = ref();
const selectedProduct = ref();
// Hàm load toàn bộ data sản phẩm
const fetchProductData = async (id) => {
  try {
    const res = await api.get(`/ProductItems/detail/${id}`);
    console.log("Product data:", res.data.content);
    product.value = res.data.content;
    SkuColorList.value = new Set(product.value.map(e => {
      return e.sku.split('-')[e.sku.split('-').length - 2]
    }))
    SkuSizeList.value = new Set(product.value.map(e => {
      return e.sku.split('-')[e.sku.split('-').length - 1]
    }))
    listMapIfSelect.value = product.value.map(e => {
      return e.sku.split('-')
    })
    const filteredMap = {};
    for (const [groupName, items] of Object.entries(mapVarriants.value)) {
      if (groupName === 'Màu sắc') {
        let filteredItems = items.filter(item => SkuColorList.value.has(item.signalSku));
        filteredItems.map(setIsActive => {
          return { ...setIsActive, isActice: true }
        })
        if (filteredItems.length) filteredMap[groupName] = { ...filteredItems };
      } else if (groupName === 'Kích thước') {
        const filteredItems = items.filter(item => SkuSizeList.value.has(item.signalSku));
        if (filteredItems.length) filteredMap[groupName] = { ...filteredItems };
      }
    }
    // Update the mapVarriants
    mapVarriants.value = filteredMap;
    images.value = product.value.flatMap(p => p.images);
    currentImage.value = images.value[0]
    const defaultSelected = {};
    for (const [groupName, items] of Object.entries(mapVarriants.value)) {
      if (items.length > 0) {
        defaultSelected[groupName] = items[0].signalSku;
      }
    }
    selected.value = defaultSelected;
    selectedProduct.value = product.value[0]
  } catch (err) {
    console.error("Lỗi tải chi tiết sản phẩm:", err);
  }

  try {
    const relatedRes = await api.get(`/ProductItems/related/${id}`);
    relatedItems.value = relatedRes.data || [];
  } catch (err) {
    console.error("Lỗi tải sản phẩm liên quan:", err);
  }
};

// Gọi khi component mount
onMounted(async () => {
  api
    .get("/VariantValues")
    .then((resp) => {
      mapVarriants.value = resp.data;
      for (const groupName in resp.data) {
        selected.value[groupName] = [];
      }
    })
    .catch((error) => console.log(error));
  await fetchProductData(productId.value);
  await fetchReviews(); // Gọi luôn khi đã có product
  await fetchAverageRating();
  await fetchTotalReviews();
});

// Gọi lại khi ID trên URL thay đổi
watch(
  () => route.params.id,
  (newId) => {
    productId.value = newId;
    fetchProductData(newId);
    fetchReviews();
    fetchAverageRating();
  },

);
const listColorBaseOnSelectedSize = ref()
watch(() => selected.value["Kích thước"], () => {
  selected.value['Màu sắc'] = []
  const filteredMap = {};
  listColorBaseOnSelectedSize.value = listMapIfSelect.value.filter(findColor => selected.value["Kích thước"] === (findColor[findColor.length - 1])).map(color => {
    return color[color.length - 2]
  })
  for (const [groupName, items] of Object.entries(mapVarriants.value)) {
    if (groupName === 'Màu sắc') {
      const itemList = Array.isArray(items) ? items : Object.values(items);
      const safeColorList = Array.isArray(listColorBaseOnSelectedSize.value) ? listColorBaseOnSelectedSize.value : [];
      const filteredItems = itemList.map(item => ({
        ...item,
        isActive: safeColorList.includes(item.signalSku)
      }));
      filteredMap[groupName] = filteredItems;
    } else if (groupName === 'Kích thước') {
      const itemList = Array.isArray(items) ? items : Object.values(items);
      const filteredItems = itemList.map(item => ({
        ...item,
        isActive: true
      }));
      filteredMap[groupName] = filteredItems;
    }
  }
  mapVarriants.value = filteredMap;
});
watch(() => selected.value["Màu sắc"], () => {
  const color = selected.value["Màu sắc"];
  const size = selected.value["Kích thước"];

  if (color && size && size.length !== 0) {
    const targetSku = `${color}-${size}`;
    selectedProduct.value = product.value.find(pro => pro.sku.includes(targetSku)) || product.value[0];
  } else {
    const itemList = Array.isArray(product.value)
      ? product.value
      : [product.value];
    selectedProduct.value = itemList[0];
  }
});
function increaseQty() {
  quantity.value++;
}

function decreaseQty() {
  if (quantity.value > 1) quantity.value--;
}

async function submitReview() {
  if (!newReview.value.text) return;

  try {
    await api.post("/user/Reviews", {
      productItems: product.value.id,
      rating: newReview.value.rating,
      comment: newReview.value.text,
      imageUrl1: reviewImageUrl.value || null,
    });

    notification.success({
      message: "Gửi đánh giá thành công",
      description: "Cảm ơn bạn đã đánh giá sản phẩm!",
      duration: 2.5,
    });

    // Reset form
    newReview.value.text = "";
    newReview.value.rating = 5;
    reviewImageUrl.value = "";
    uploaderKey.value = Date.now();
    await fetchReviews();
  } catch (err) {
    notification.error({
      message: "Lỗi gửi đánh giá",
      description: "Vui lòng thử lại sau!",
      duration: 4.5,
    });
    console.error(err);
  }
}

function formatPrice(price) {
  return price.toLocaleString("vi-VN") + "₫";
}

function formatDate(dateStr) {
  const d = new Date(dateStr);
  return d.toLocaleDateString("vi-VN");
}


const addToCart = () => {
  if (!product.value || quantity.value <= 0) return;

  const itemUpdate = {
    id: '',
    accounts: authService.getUserName(),
    productItems: selectedProduct.value.id,
    qty: quantity.value,
    promotions: selectedProduct.value.promotions[0],
    comboGroup: "",
    comboQty: "",
    comboGroupId: "",
    name: selectedProduct.value.name,
    mainImageUrl: selectedProduct.value.baseProducts.mainImageUrl,
    price: selectedProduct.value.price,
    sku: selectedProduct.value.sku,
    createdAt: "",
    updatedAt: "",
  }
  console.log(itemUpdate)
  authService.updateCart(quantity.value);

  if (quantity.value <= selectedProduct.value.qty) {
    finalHandleCartProgress(itemUpdate);
    notification.success({
      message: "Thành công",
      description: `Đã thêm ${quantity.value} x ${selectedProduct.value.name} vào giỏ hàng`,
      duration: 4.5,
    });

  } else {
    notification.error({
      message: "Thất bại",
      description: `Số lượng tồn kho chỉ còn ${selectedProduct.value.qty} sản phẩm! ${quantity.value}`,
      duration: 4.5,
    });
  }
};
const currentImageIndex = ref(0);
watch(images, (newImages) => {
  if (newImages.length > 0) {
    currentImage.value = newImages[0].imageUrl;
    currentImageIndex.value = 0;
  }
});

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

const fetchReviews = async () => {
  try {
    const res = await api.get(`/reviews/product/${selectedProduct.value.id}`, {
      params: {
        page: currentPage.value,
        size: pageSize.value,
      },
    });
    reviews.value = res.data.content || [];
    totalPages.value = res.data.totalPages || 0;
  } catch (err) {
    console.error("Lỗi tải đánh giá:", err);
  }
};

const averageRating = ref(0);

const fetchAverageRating = async () => {
  try {
    const res = await api.get(`/reviews/product/average-rating/${selectedProduct.value.id}`);
    averageRating.value = res.data.averageRating || 0;
  } catch (err) {
    console.error("Lỗi lấy điểm trung bình:", err);
  }
};

const totalReviews = ref(0);

const fetchTotalReviews = async () => {
  try {
    const res = await api.get(`/reviews/product/count/${productId.value}`);
    totalReviews.value = res.data.total || 0;
  } catch (err) {
    console.error("Lỗi lấy tổng số đánh giá:", err);
  }
};

function formatTimeAgo(dateStr) {
  const now = new Date();
  const past = new Date(dateStr);
  const diff = Math.floor((now - past) / 1000); // giây

  if (diff < 60) return "Vừa xong";
  if (diff < 3600) return `${Math.floor(diff / 60)} phút trước`;
  if (diff < 86400) return `${Math.floor(diff / 3600)} giờ trước`;
  return past.toLocaleDateString("vi-VN");
}

const changePage = async (page) => {
  if (page < 0) {
    currentPage.value = totalPages.value - 1;
  } else if (page >= totalPages.value) {
    currentPage.value = 0;
  } else {
    currentPage.value = page;
  }
  await fetchReviews();
};

const reviewImageUrl = ref("");

const handleImageUploaded = (url) => {
  reviewImageUrl.value = url;
  console.log("Ảnh review đã upload:", url);
};

const fileInputRef = ref(null);
</script>

<style scoped>
/* ==================== GLOBAL STYLES ==================== */
.product-detail-container {
  background: linear-gradient(135deg, #f8fafc 0%, #e2e8f0 100%);
  min-height: 100vh;
}

/* ==================== HERO SECTION ==================== */
.hero-section {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
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
  background: url('data:image/svg+xml,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 100 100"><circle cx="50" cy="50" r="2" fill="white" opacity="0.1"/></svg>') repeat;
  animation: float 20s ease-in-out infinite;
}

@keyframes float {

  0%,
  100% {
    transform: translateY(0px);
  }

  50% {
    transform: translateY(-20px);
  }
}

/* ==================== BREADCRUMB ==================== */
.breadcrumb-modern {
  display: flex;
  align-items: center;
  font-size: 14px;
  margin-bottom: 2rem;
}

.breadcrumb-link {
  color: rgba(255, 255, 255, 0.8);
  text-decoration: none;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
}

.breadcrumb-link:hover {
  color: white;
  text-decoration: underline;
}

.breadcrumb-separator {
  margin: 0 12px;
  color: rgba(255, 255, 255, 0.6);
}

.breadcrumb-current {
  color: white;
  font-weight: 500;
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

/* ==================== PRODUCT INFO ==================== */
.product-info {
  background: white;
  border-radius: 20px;
  padding: 40px;
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
  position: relative;
  z-index: 5;
}

.product-header {
  margin-bottom: 30px;
}

.product-title {
  font-size: 2.5rem;
  font-weight: 700;
  color: #1a202c;
  margin-bottom: 15px;
  line-height: 1.2;
}

.rating-section {
  display: flex;
  align-items: center;
  gap: 15px;
  margin-bottom: 10px;
}

.stars {
  display: flex;
  gap: 3px;
  color: #fbbf24;
  font-size: 18px;
}

.rating-text {
  font-weight: 600;
  color: #1a202c;
  font-size: 16px;
}

.reviews-count {
  color: #6b7280;
  font-size: 14px;
}

/* ==================== PRICE SECTION ==================== */
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

/* ==================== PRODUCT DETAILS ==================== */
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

/* ==================== DETAILS SECTION ==================== */
.details-section {
  background: white;
  padding: 60px 0;
}

.tabs-container {
  max-width: 1200px;
  margin: 0 auto;
}

.modern-tabs {
  display: flex;
  list-style: none;
  margin: 0;
  padding: 0;
  border-bottom: 2px solid #e5e7eb;
  margin-bottom: 40px;
}

.tab-item {
  margin-right: 40px;
}

.tab-button {
  background: none;
  border: none;
  padding: 15px 0;
  font-size: 16px;
  font-weight: 600;
  color: #6b7280;
  cursor: pointer;
  transition: all 0.3s ease;
  border-bottom: 3px solid transparent;
  display: flex;
  align-items: center;
}

.tab-button.active {
  color: #667eea;
  border-bottom-color: #667eea;
}

.tab-content-modern {
  min-height: 400px;
}

.tab-panel {
  animation: fadeIn 0.3s ease;
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(20px);
  }

  to {
    opacity: 1;
    transform: translateY(0);
  }
}

/* ==================== DESCRIPTION TAB ==================== */
.description-content {
  padding: 20px 0;
}

.section-title {
  color: #1a202c;
  font-weight: 700;
  margin-bottom: 20px;
}

.description-text {
  color: #4a5568;
  line-height: 1.6;
  margin-bottom: 30px;
}

.features-title {
  color: #2d3748;
  font-weight: 600;
  margin-bottom: 15px;
}

.features-list {
  list-style: none;
  padding: 0;
}

.features-list li {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
  color: #4a5568;
}

.features-list i {
  color: #48bb78;
  margin-right: 10px;
}

.specs-card {
  background: #f7fafc;
  border-radius: 15px;
  padding: 25px;
}

.specs-title {
  color: #2d3748;
  font-weight: 600;
  margin-bottom: 20px;
}

.spec-row {
  display: flex;
  justify-content: space-between;
  margin-bottom: 12px;
  padding-bottom: 8px;
  border-bottom: 1px solid #e2e8f0;
}

.spec-row:last-child {
  border-bottom: none;
  margin-bottom: 0;
}

.spec-label {
  font-weight: 600;
  color: #4a5568;
}

.spec-value {
  color: #2d3748;
}

/* ==================== REVIEWS TAB ==================== */
.reviews-content {
  padding: 20px 0;
}

.rating-summary {
  background: linear-gradient(135deg, #667eea, #764ba2);
  color: white;
  border-radius: 20px;
  padding: 30px;
  margin-bottom: 40px;
  text-align: center;
}

.big-rating {
  font-size: 3rem;
  font-weight: 700;
  display: block;
  margin-bottom: 10px;
}

.rating-stars {
  font-size: 24px;
  margin-bottom: 10px;
}

.rating-count {
  opacity: 0.9;
  margin: 0;
}

.review-form-card {
  background: #f8fafc;
  border-radius: 20px;
  padding: 30px;
  margin-bottom: 40px;
}

.form-title {
  color: #1a202c;
  font-weight: 600;
  margin-bottom: 25px;
}

.rating-input {
  margin-bottom: 20px;
}

.star-rating {
  display: flex;
  gap: 5px;
  margin-top: 8px;
}

.star-button {
  font-size: 24px;
  color: #d1d5db;
  cursor: pointer;
  transition: all 0.3s ease;
}

.star-button.bi-star-fill {
  color: #fbbf24;
}

.star-button:hover {
  transform: scale(1.1);
}

.form-group {
  margin-bottom: 20px;
}

.form-label {
  display: block;
  font-weight: 600;
  color: #374151;
  margin-bottom: 8px;
}

.form-textarea {
  width: 100%;
  padding: 15px;
  border: 2px solid #e5e7eb;
  border-radius: 12px;
  font-size: 14px;
  resize: vertical;
  transition: border-color 0.3s ease;
}

.form-textarea:focus {
  outline: none;
  border-color: #667eea;
}

.submit-review-btn {
  background: linear-gradient(135deg, #667eea, #764ba2);
  color: white;
  border: none;
  padding: 15px 30px;
  border-radius: 12px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
}

.submit-review-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 20px rgba(102, 126, 234, 0.4);
}

.reviews-list {
  margin-top: 40px;
}

.review-card {
  background: white;
  border-radius: 15px;
  padding: 25px;
  margin-bottom: 20px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.08);
  transition: all 0.3s ease;
}

.review-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 25px rgba(0, 0, 0, 0.12);
}

.review-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 15px;
}

.reviewer-info {
  display: flex;
  align-items: center;
  gap: 15px;
}

.reviewer-avatar {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  background: linear-gradient(135deg, #667eea, #764ba2);
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: 600;
  font-size: 18px;
}

.reviewer-name {
  color: #1a202c;
  font-weight: 600;
  margin: 0 0 5px 0;
}

.review-rating {
  color: #fbbf24;
  font-size: 14px;
}

.review-date {
  color: #6b7280;
  font-size: 14px;
}

.review-content p {
  color: #4a5568;
  line-height: 1.6;
  margin-bottom: 15px;
}

.review-images {
  display: flex;
  gap: 10px;
  flex-wrap: wrap;
}

.review-image {
  width: 120px;
  height: 150px;
  /* Tỷ lệ 4:5 */
  object-fit: cover;
  border-radius: 10px;
  cursor: pointer;
  transition: transform 0.3s ease;
}

.review-image:hover {
  transform: scale(1.05);
}

/* ==================== PAGINATION ==================== */
.pagination-container {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 15px;
  margin-top: 40px;
}

.pagination-btn {
  background: white;
  border: 2px solid #e5e7eb;
  color: #6b7280;
  padding: 10px 16px;
  border-radius: 10px;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  gap: 5px;
}

.pagination-btn:hover:not(:disabled) {
  border-color: #667eea;
  color: #667eea;
}

.pagination-btn:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.page-numbers {
  display: flex;
  gap: 8px;
}

.page-btn {
  width: 40px;
  height: 40px;
  border: 2px solid #e5e7eb;
  background: white;
  color: #6b7280;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
}

.page-btn.active {
  background: #667eea;
  border-color: #667eea;
  color: white;
}

.page-btn:hover:not(.active) {
  border-color: #667eea;
  color: #667eea;
}

/* ==================== RELATED PRODUCTS ==================== */
.related-products-section {
  background: #f8fafc;
  padding: 80px 0;
}

.section-header {
  text-align: center;
  margin-bottom: 50px;
}

.section-title {
  font-size: 2.5rem;
  font-weight: 700;
  color: #1a202c;
  margin-bottom: 10px;
}

.section-subtitle {
  color: #6b7280;
  font-size: 1.1rem;
}

.related-products-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 30px;
}

.product-card {
  background: white;
  border-radius: 20px;
  overflow: hidden;
  box-shadow: 0 8px 25px rgba(0, 0, 0, 0.08);
  transition: all 0.3s ease;
}

.product-card:hover {
  transform: translateY(-8px);
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.15);
}

.product-image-container {
  position: relative;
  overflow: hidden;
}

.product-image {
  width: 100%;
  aspect-ratio: 4/5;
  height: auto;
  object-fit: cover;
  transition: transform 0.3s ease;
}

.product-card:hover .product-image {
  transform: scale(1.1);
}

.product-overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.7);
  display: flex;
  align-items: center;
  justify-content: center;
  opacity: 0;
  transition: opacity 0.3s ease;
}

.product-card:hover .product-overlay {
  opacity: 1;
}

.view-product-btn {
  background: white;
  color: #1a202c;
  padding: 12px 24px;
  border-radius: 25px;
  text-decoration: none;
  font-weight: 600;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
}

.view-product-btn:hover {
  background: #667eea;
  color: white;
  transform: scale(1.05);
}

.product-card-body {
  padding: 25px;
}

.product-card-title {
  color: #1a202c;
  font-weight: 600;
  margin-bottom: 10px;
  line-height: 1.4;
}

.product-card-price {
  color: #dc2626;
  font-size: 1.25rem;
  font-weight: 700;
}

/* ==================== LOADING STATE ==================== */
.loading-container {
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 60vh;
  background: #f8fafc;
}

.loading-content {
  text-align: center;
}

.spinner {
  width: 50px;
  height: 50px;
  border: 4px solid #e5e7eb;
  border-top: 4px solid #667eea;
  border-radius: 50%;
  animation: spin 1s linear infinite;
  margin: 0 auto 20px;
}

@keyframes spin {
  0% {
    transform: rotate(0deg);
  }

  100% {
    transform: rotate(360deg);
  }
}

@media (max-width: 480px) {
  .variant-options {
    grid-template-columns: repeat(auto-fit, minmax(50px, 1fr));
    gap: 10px;
  }

  .variant-button {
    font-size: 14px;
    padding: 8px 0;
  }
}

/* ==================== RESPONSIVE DESIGN ==================== */
@media (max-width: 768px) {
  .product-title {
    font-size: 2rem;
  }

  .current-price {
    font-size: 1.75rem;
  }

  .product-info {
    padding: 25px;
  }

  .main-image {
    aspect-ratio: 4/5;
    /* Giữ tỷ lệ trên mobile */
  }

  .section-title {
    font-size: 2rem;
  }

  .related-products-grid {
    grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
    gap: 20px;
  }

  .gallery-nav {
    width: 40px;
    height: 40px;
    font-size: 16px;
  }

  .gallery-nav-prev {
    left: 10px;
  }

  .gallery-nav-next {
    right: 10px;
  }

  .action-buttons {
    gap: 12px;
  }

  .btn-add-cart,
  .btn-buy-now {
    padding: 15px 20px;
    font-size: 14px;
  }

  .thumbnail-item {
    width: 70px;
    height: 87px;
    /* Tỷ lệ 4:5 trên mobile */
  }
}

@media (max-width: 480px) {
  .hero-section {
    padding: 40px 0;
  }

  .product-info {
    padding: 20px;
  }

  .main-image {
    aspect-ratio: 4/5;
    /* Giữ tỷ lệ trên điện thoại */
  }

  .thumbnail-item {
    width: 60px;
    height: 75px;
    /* Tỷ lệ 4:5 cho điện thoại */
  }

  .quantity-controls {
    margin: 0 auto;
  }

  .review-form-card {
    padding: 20px;
  }

  .review-card {
    padding: 20px;
  }

  .related-products-grid {
    grid-template-columns: 1fr;
  }

  .review-image {
    width: 100px;
    height: 125px;
    /* Tỷ lệ 4:5 cho điện thoại */
  }
}
</style>
