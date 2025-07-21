<template>
  <div class="container py-4" v-if="product">
    <!-- Breadcrumb -->
    <nav class="breadcrumb mb-4">
      <a class="breadcrumb-item" href="#">Danh sách sản phẩm</a>
      <span class="breadcrumb-item active">{{ product.name }}</span>
    </nav>

    <!-- Product Content -->
    <div class="row">
      <!-- Hình ảnh trái -->
      <div class="col-lg-7 col-md-12 mb-4">
        <div class="row">
          <div class="col-3 d-flex flex-column gap-3">
            <img
              v-for="(img, idx) in images"
              :key="idx"
              :src="img.imageUrl"
              @click="selectImage(idx)"
              :class="['img-thumbnail', { 'border-primary': currentImageIndex === idx }]"
              style="cursor: pointer; width: 100%; aspect-ratio: 1/1; object-fit: cover"
            />
          </div>
          <div class="col-9 position-relative overflow-hidden rounded" style="padding: 0 32px;">
            <img :src="currentImage" alt="main" class="img-fluid border rounded w-100" />
            
            <button
              class="btn btn-light position-absolute top-50 start-0 translate-middle-y shadow-sm"
              @click="prevImage"
              style="z-index: 10; transform: translateY(-50%) translateX(-50%);"
            >
              ‹
            </button>
            <button
              class="btn btn-light position-absolute top-50 end-0 translate-middle-y shadow-sm"
              @click="nextImage"
              style="z-index: 10; transform: translateY(-50%) translateX(50%);"
            >
              ›
            </button>
          </div>
        </div>
      </div>

      <!-- Thông tin phải -->
      <div class="col-lg-5 col-md-12">
        <h2 class="fw-semibold mb-1">{{ product.name }}</h2>

        <!-- Giá hiện tại và giảm giá -->
        <p class="fs-4 fw-bold">
        <!-- Nếu có giảm giá thực sự -->
        <template v-if="discountedPrice !== currentPrice">
          <span class="text-danger">{{ formatPrice(discountedPrice) }}</span>
          <del class="text-muted ms-2">{{ formatPrice(currentPrice) }}</del>
          <small class="text-danger ms-2">-{{ promotion.promotions.discountPercent }}%</small>
        </template>

        <!-- Nếu không có giảm giá (hoặc bằng nhau) -->
        <template v-else>
          <span class="text-dark">{{ formatPrice(currentPrice) }}</span>
        </template>

        <small class="text-muted">| {{ averageRating }} ★ ({{ reviews.length }} đánh giá)</small>
        </p>

        <p class="text-muted mb-3">{{ product.material }}</p>

        <div class="mb-2" v-if="promotion && promotion.promotions">
          <span class="badge bg-danger">
            KM: {{ promotion.promotions.name }} ({{ promotion.promotions.discountPercent }}%)
          </span>
          <div class="text-muted small">
            Áp dụng từ {{ formatDate(promotion.promotions.startDate) }} đến {{ formatDate(promotion.promotions.endDate) }}
          </div>
        </div>

        <!-- Số lượng và nút -->
        <div class="d-flex flex-wrap align-items-center gap-3 mb-3">
          <div class="d-flex align-items-center justify-content-center border rounded-pill"
              style="height: 48px; min-width: 120px; max-width: 160px; padding: 0 8px;">
            <button @click="decreaseQty"
                    class="btn btn-sm border-0 px-2 py-1 fs-5"
                    style="background-color: transparent;">−</button>
            <span class="fw-medium fs-5 mx-2">{{ quantity }}</span>
            <button @click="increaseQty"
                    class="btn btn-sm border-0 px-2 py-1 fs-5"
                    style="background-color: transparent;">+</button>
          </div>
          <button @click="addToCart" class="flex-grow-1 text-white add-to-cart-btn">
            Thêm vào giỏ
          </button>
        </div>

        <div class="mb-3">
          <button class="w-100" style="background-color: #fdf3ed; color: #000; border: 1px solid #000; border-radius: 999px; padding: 14px 0;">
            Mua ngay
          </button>
        </div>

        <div class="text-muted small">
          <div>🚚 Miễn phí giao hàng toàn quốc cho đơn hàng từ 1.000.000đ.</div>
          <div>⏰ Giao hàng: 3–7 ngày làm việc. Đổi trả linh hoạt.</div>
        </div>
      </div>
    </div>

    <!-- Tabs -->
    <div class="mt-5">
      <ul class="nav nav-tabs">
        <li class="nav-item">
          <button class="nav-link" :class="{ active: activeTab === 'desc' }" @click="activeTab = 'desc'">Mô tả</button>
        </li>
        <li class="nav-item">
          <button class="nav-link" :class="{ active: activeTab === 'review' }" @click="activeTab = 'review'">Đánh giá</button>
        </li>
      </ul>
      <div class="tab-content border border-top-0 p-4 bg-light-subtle">
        <div v-show="activeTab === 'desc'">
          <h6 class="fw-bold">{{ product.name }}</h6>
          <p><strong>Chất liệu:</strong> {{ product.material }}</p>
          <p><strong>Mô tả:</strong> {{ product.description || 'Chưa có mô tả' }}</p>
          <p><strong>Mã sản phẩm:</strong> {{ product.sku }}</p>
          <p><strong>Tồn kho:</strong> {{ product.qty }} sản phẩm</p>
          <p><strong>Lượt mua:</strong> {{ product.turnBuy }} lượt</p>
        </div>

        <div v-show="activeTab === 'review'">
          <div v-for="review in reviews" :key="review.name" class="mb-3 p-3 border rounded bg-white">
            <h6 class="mb-1">{{ review.accounts?.fullName || 'Ẩn danh' }}</h6>
            <p class="text-muted small mb-1">{{ review.comment }}</p>
            <div class="text-warning small">
              <span v-for="i in 5" :key="i">{{ i <= review.rating ? '★' : '☆' }}</span>
            </div>
            <small class="text-muted">{{ formatTimeAgo(review.createdAt) }}</small>
          </div>

          <!-- Form đánh giá -->
          <form @submit.prevent="submitReview" class="p-3 border rounded bg-white">
            <div class="row mb-2">
              <div class="col-md-6">
                <label class="form-label">Tên của bạn</label>
                <input type="text" class="form-control rounded-pill" v-model="newReview.name" required />
              </div>
              <div class="col-md-6">
                <label class="form-label">Email</label>
                <input type="email" class="form-control rounded-pill" placeholder="example@gmail.com" />
              </div>
            </div>
            <div class="mb-2">
            <label class="form-label">Hình ảnh (tùy chọn)</label>
            <input type="file" class="form-control" accept="image/*" @change="handleFileUpload" />
          </div>
            <div class="mb-2">
              <label class="form-label">Đánh giá</label>
              <textarea class="form-control rounded" rows="3" v-model="newReview.text" required></textarea>
            </div>
            <div class="d-flex justify-content-between align-items-center">
              <div class="d-flex align-items-center gap-1">
                <span v-for="star in 5" :key="star" @click="newReview.rating = star" style="cursor: pointer;">
                  <span :class="star <= newReview.rating ? 'text-warning' : 'text-secondary'">★</span>
                </span>
              </div>
              <button type="submit" class="btn btn-dark rounded-pill">Đăng bình luận</button>
            </div>
          </form>
          <div class="d-flex justify-content-center mt-3" v-if="totalPages > 1">
            <button
              class="btn btn-sm btn-outline-secondary me-2"
              :disabled="currentPage === 0"
              @click="changePage(currentPage - 1)">
              ← Trước
            </button>
            <button
              class="btn btn-sm btn-outline-secondary"
              :disabled="currentPage >= totalPages - 1"
              @click="changePage(currentPage + 1)">
              Tiếp →
            </button>
          </div>
          <!-- Danh sách đánh giá -->
        </div>
      </div>
    </div>
  </div>
  <p v-else>Đang tải chi tiết sản phẩm...</p>
  <!-- Sản phẩm liên quan -->
<div class="container mt-5" v-if="relatedItems.length > 0">
  <h5 class="mb-3">Sản phẩm liên quan</h5>
  <div class="row row-cols-2 row-cols-md-4 g-3">
    <div class="col" v-for="item in relatedItems.slice(0, 4)" :key="item.id">
      <div class="card h-100 shadow-sm related-card">
        <img :src="item.imageUrl" class="card-img-top" alt="..." />
        <div class="card-body p-2">
          <h6 class="card-title text-truncate mb-1">{{ item.name }}</h6>
          <p class="card-text text-danger fw-bold mb-2">{{ formatPrice(item.price) }}</p>
          <router-link :to="`/product/${item.id}`" class="btn btn-outline-dark btn-sm w-100">
            Xem chi tiết
          </router-link>
        </div>
      </div>
    </div>
  </div>
</div>

</template>

<script setup>
import { onMounted, ref, computed, watch } from 'vue';
import { useRoute } from 'vue-router';
import api from "../../Configs/api";
import { finalHandleCartProgress } from "../../Configs/cart";
import { notification } from "ant-design-vue";

const route = useRoute();
const productId = ref(route.params.id);

const product = ref(null);
const images = ref([]);
const priceHistories = ref([]);
const promotion = ref(null);

const currentImage = ref('');
const currentPrice = ref(0);
const quantity = ref(1);
const activeTab = ref('desc');

const reviews = ref([]);
const newReview = ref({ name: '', text: '', rating: 5 });

const relatedItems = ref([]);

const currentPage = ref(0);
const pageSize = ref(5);
const totalPages = ref(0);


const discountedPrice = computed(() => {
  if (promotion.value?.promotions?.discountPercent) {
    return Math.round(currentPrice.value * (1 - promotion.value.promotions.discountPercent / 100));
  }
  return currentPrice.value;
});

// Hàm load toàn bộ data sản phẩm
const fetchProductData = async (id) => {
  try {
    const res = await api.get(`/ProductItems/detail/${id}`);
    product.value = res.data.productItem;
    images.value = res.data.images || [];
    priceHistories.value = res.data.priceHistories || [];
    promotion.value = res.data.promotions?.[0] || null;

    currentImage.value = images.value?.[0]?.imageUrl || product.value.imageUrl;
    currentPrice.value = priceHistories.value?.[0]?.price || 0;

    // reviews.value = [
    //   { name: "Huy", text: "Sản phẩm tốt" },
    //   { name: "Ngọc", text: "Chất lượng ok, sẽ mua lần nữa" }
    // ];
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
  await fetchProductData(productId.value);
  await fetchReviews(); // Gọi luôn khi đã có product
});


// Gọi lại khi ID trên URL thay đổi
watch(() => route.params.id, (newId) => {
  productId.value = newId;
  fetchProductData(newId);
});

function increaseQty() {
  quantity.value++;
}

function decreaseQty() {
  if (quantity.value > 1) quantity.value--;
}

async function submitReview() {
  if (!newReview.value.name || !newReview.value.text) return;

  try {
    await api.post("/admin/Reviews", {
      accounts: 1,
      productItems: product.value.id,
      rating: newReview.value.rating,
      comment: newReview.value.text
    });

    notification.success({ message: "Gửi đánh giá thành công" });
    newReview.value.name = '';
    newReview.value.text = '';
    newReview.value.rating = 5;
    await fetchReviews(); // reload đánh giá mới
  } catch (err) {
    notification.error({ message: "Lỗi gửi đánh giá" });
    console.error(err);
  }
}


function formatPrice(price) {
  return price.toLocaleString('vi-VN') + '₫';
}

function formatDate(dateStr) {
  const d = new Date(dateStr);
  return d.toLocaleDateString('vi-VN');
}

const itemCart = ref({
  id: "",
  accounts: "",
  productItems: "",
  promotions: "",
  comboGroup: "",
  qty: "",
  createdAt: "",
  updatedAt: "",
});

const addToCart = () => {
  if (!product.value || quantity.value <= 0) return;

  const newCartItem = {
    productItems: product.value.id,
    qty: quantity.value,
    promotions: promotion.value?.promotions?.id || null,
  };

  if (quantity.value <= product.value.qty) {
    finalHandleCartProgress(newCartItem);
    notification.success({
      message: "Thành công",
      description: `Đã thêm ${quantity.value} x ${product.value.name} vào giỏ hàng`,
    });
  } else {
    notification.error({
      message: "Thất bại",
      description: `Số lượng tồn kho chỉ còn ${product.value.qty} sản phẩm!`,
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
  currentImageIndex.value =
    (currentImageIndex.value + 1) % images.value.length;
  currentImage.value = images.value[currentImageIndex.value].imageUrl;
};
function selectImage(idx) {
  currentImageIndex.value = idx;
  currentImage.value = images.value[idx].imageUrl;
}

const fetchReviews = async () => {
  try {
    const res = await api.get(`/reviews/product/${product.value.id}`, {
      params: {
        page: currentPage.value,
        size: pageSize.value
      }
    });

    reviews.value = res.data.content || [];
    totalPages.value = res.data.totalPages || 0;
  } catch (err) {
    console.error("Lỗi tải đánh giá:", err);
  }
};


const averageRating = computed(() => {
  if (reviews.value.length === 0) return 0;
  const total = reviews.value.reduce((sum, r) => sum + r.rating, 0);
  return (total / reviews.value.length).toFixed(1);
});

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
  currentPage.value = page;
  await fetchReviews();
};


</script>


<style scoped>
.img-thumbnail.border-primary {
  border-width: 3px !important;
}
del {
  font-size: 0.9em;
  opacity: 0.7;
}

.add-to-cart-btn {
  background-color: #e9cebd;
  color: white;
  border-radius: 999px;
  border: none;
  height: 48px;
  transition: all 0.3s ease;
  font-weight: 500;
}

.add-to-cart-btn:hover {
  filter: brightness(1.08);
  cursor: pointer;
}
.related-card img {
  width: 100%;
  height: 200px;
  object-fit: cover;
  object-position: center;
  border-top-left-radius: 0.375rem;
  border-top-right-radius: 0.375rem;
  transition: transform 0.3s ease;
}

.related-card:hover img {
  transform: scale(1.05);
}

.btn-light {
  background-color: rgba(255, 255, 255, 0.9);
  width: 48px;
  height: 48px;
  border-radius: 50%;
  font-size: 28px;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 0;
  border: none;
  transition: background-color 0.2s;
}

.btn-light:hover {
  background-color: rgba(255, 255, 255, 1);
  cursor: pointer;
}

</style>
