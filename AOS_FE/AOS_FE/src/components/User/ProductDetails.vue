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
              @click="currentImage = img.imageUrl"
              :class="['img-thumbnail', { 'border-primary': currentImage === img.imageUrl }]"
              style="cursor: pointer; width: 100%; aspect-ratio: 1/1; object-fit: cover"
            />
          </div>
          <div class="col-9">
            <img :src="currentImage" alt="main" class="img-fluid border rounded w-100" />
          </div>
        </div>
      </div>

      <!-- Thông tin phải -->
      <div class="col-lg-5 col-md-12">
        <h2 class="fw-semibold mb-1">{{ product.name }}</h2>

        <!-- Giá hiện tại và giảm giá -->
        <p class="fs-4 fw-bold">
          <span class="text-danger">{{ formatPrice(discountedPrice) }}</span>
          <del class="text-muted ms-2" v-if="promotion">{{ formatPrice(currentPrice) }}</del>
          <small class="text-danger ms-2" v-if="promotion">-{{ promotion.promotions.discountPercent }}%</small>
          <small class="text-muted">| ★★★★☆ ({{ reviews.length }} review)</small>
        </p>

        <p class="text-muted mb-3">{{ product.material }}</p>

        <div class="mb-2" v-if="promotion">
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
            <h6 class="mb-1">{{ review.name }}</h6>
            <p class="text-muted small mb-1">{{ review.text }}</p>
            <small class="text-muted">1 phút trước</small>
          </div>

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
              <label class="form-label">Đánh giá</label>
              <textarea class="form-control rounded" rows="3" v-model="newReview.text" required></textarea>
            </div>
            <div class="d-flex justify-content-between align-items-center">
              <div>Đánh giá ★☆☆☆☆</div>
              <button type="submit" class="btn btn-dark rounded-pill">Đăng bình luận</button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref, computed } from 'vue';
import { useRoute } from 'vue-router';
import api from "../../Configs/api";
import { finalHandleCartProgress } from "../../Configs/cart";
import { notification } from "ant-design-vue";

const route = useRoute();
const productId = route.params.id;

const product = ref(null);
const images = ref([]);
const priceHistories = ref([]);
const promotion = ref(null);

const currentImage = ref('');
const currentPrice = ref(0);
const quantity = ref(1);
const activeTab = ref('desc');

const reviews = ref([]);
const newReview = ref({ name: '', text: '' });

const discountedPrice = computed(() => {
  if (promotion.value && promotion.value.discountPercent) {
    return Math.round(currentPrice.value * (1 - promotion.value.discountPercent / 100));
  }
  return currentPrice.value;
});

onMounted(async () => {
  try {
    const res = await api.get(`/ProductItems/detail/${productId}`);
    console.log("Kết quả trả về từ API chi tiết sản phẩm:", res.data);

    product.value = res.data.productItem;
    images.value = res.data.images || [];
    priceHistories.value = res.data.priceHistories || [];
    promotion.value = res.data.promotions?.[0] || null;

    currentImage.value = images.value?.[0]?.imageUrl || product.value.imageUrl;
    currentPrice.value = priceHistories.value?.[0]?.price || 0;

    reviews.value = [
      { name: "Huy", text: "Sản phẩm tốt" },
      { name: "Ngọc", text: "Chất lượng ok, sẽ mua lần nữa" }
    ];
  } catch (err) {
    console.error("Lỗi tải chi tiết sản phẩm:", err);
  }
});


function increaseQty() {
  quantity.value++;
}

function decreaseQty() {
  if (quantity.value > 1) quantity.value--;
}

function submitReview() {
  if (newReview.value.name && newReview.value.text) {
    reviews.value.push({ ...newReview.value });
    newReview.value.name = '';
    newReview.value.text = '';
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
  accounts: "", // nếu cần tài khoản đăng nhập thì gắn ID user ở đây
  productItems: "",
  promotions: "",
  comboGroup: "",
  qty: "",
  createdAt: "",
  updatedAt: "",
});

const addToCart = () => {
  if (!product.value || quantity.value <= 0) return;

  // Set dữ liệu cho itemCart
  itemCart.value.productItems = product.value.id;
  itemCart.value.qty = quantity.value;

  if (quantity.value < product.value.safetyStock) {
    finalHandleCartProgress(itemCart.value);
    notification.success({
      message: "Thành công",
      description: `Đã thêm ${quantity.value} x ${product.value.name} vào giỏ hàng`,
    });
  } else {
    notification.error({
      message: "Thất bại",
      description: `Số lượng tồn không đủ!`,
    }); 
  }
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


</style>
