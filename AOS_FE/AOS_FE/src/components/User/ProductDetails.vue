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
              v-for="(img, idx) in product.images"
              :key="idx"
              :src="img"
              @click="currentImage = img"
              :class="['img-thumbnail', { 'border-primary': currentImage === img }]"
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
        <p class="text-danger fs-4 fw-bold">
          {{ product.price.toLocaleString('vi-VN') }}đ
          <small class="text-muted">| ★★★★☆ ({{ reviews.length }} lượt mua)</small>
        </p>
        <p class="text-muted mb-3">{{ product.description }}</p>

        <ul class="mb-4">
          <li v-for="(feature, index) in product.features" :key="index">{{ feature }}</li>
        </ul>

        <!-- Số lượng và nút Thêm vào giỏ -->
        <div class="d-flex flex-wrap align-items-center gap-3 mb-3">
          <div class="d-flex align-items-center border rounded-pill px-3" style="height: 48px; flex: 1 1 120px; min-width: 100px; max-width: 160px;">
            <button @click="decreaseQty" class="btn btn-sm px-3 py-0 border-0">−</button>
            <span class="mx-3 fw-medium">{{ quantity }}</span>
            <button @click="increaseQty" class="btn btn-sm px-3 py-0 border-0">+</button>
          </div>

          <button class="text-white flex-grow-1" style="background-color: #e9cebd; border-radius: 999px; border: none; height: 48px; min-width: 150px;">
            Thêm vào giỏ
          </button>
        </div>

        <div class="mb-3">
          <button class="w-100" style="background-color: #fdf3ed; color: #000; border: 1px solid #000; border-radius: 999px; padding: 14px 0; font-size: 18px; font-weight: 600;">
            Mua ngay
          </button>
        </div>

        <div class="text-muted small">
          <div>🚚 Miễn phí giao hàng toàn quốc cho đơn hàng từ 1.000.000đ.</div>
          <div>⏰ Thời gian giao hàng: 3–7 ngày làm việc. Vận chuyển & đổi trả linh hoạt.</div>
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
          <p>{{ product.longDescription }}</p>
        </div>

        <div v-show="activeTab === 'review'">
          <div v-for="review in reviews" :key="review.name" class="mb-3 p-3 border rounded bg-white">
            <div class="d-flex justify-content-between align-items-start">
              <div>
                <h6 class="mb-0">{{ review.name }}</h6>
                <p class="text-muted small mb-1">{{ review.text }}</p>
                <small class="text-muted">Thích • Trả lời • 1 phút</small>
              </div>
              <div>★★★★★</div>
            </div>
          </div>

          <form @submit.prevent="submitReview" class="p-3 border rounded bg-white">
            <div class="row mb-2">
              <div class="col-md-6">
                <label class="form-label">Tên của bạn</label>
                <input type="text" class="form-control rounded-pill" v-model="newReview.name" required />
              </div>
              <div class="col-md-6">
                <label class="form-label">Địa chỉ Email</label>
                <input type="email" class="form-control rounded-pill" placeholder="example@gmail.com" />
              </div>
            </div>
            <div class="mb-2">
              <label class="form-label">Viết đánh giá của bạn</label>
              <textarea class="form-control rounded" rows="3" v-model="newReview.text" required></textarea>
            </div>
            <div class="d-flex justify-content-between align-items-center">
              <div>đánh giá ★☆☆☆☆</div>
              <button type="submit" class="btn btn-dark rounded-pill">Đăng Bình Luận</button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import { useRoute } from 'vue-router';

export default {
  data() {
    return {
      product: null,
      currentImage: '',
      quantity: 1,
      activeTab: 'desc',
      reviews: [],
      newReview: { name: '', text: '' }
    };
  },
  async mounted() {
    const route = useRoute();
    const productId = route.params.id;

    try {
      const response = await axios.get(`https://api.example.com/products/${productId}`);
      this.product = response.data;
      this.currentImage = this.product.images?.[0] || this.product.imageUrl;

      const reviewsResponse = await axios.get(`https://api.example.com/products/${productId}/reviews`);
      this.reviews = reviewsResponse.data;
    } catch (error) {
      console.error('Lỗi khi tải dữ liệu:', error);
    }
  },
  methods: {
    increaseQty() {
      this.quantity++;
    },
    decreaseQty() {
      if (this.quantity > 1) this.quantity--;
    },
    submitReview() {
      if (this.newReview.name && this.newReview.text) {
        this.reviews.push({ ...this.newReview });
        this.newReview.name = '';
        this.newReview.text = '';
      }
    }
  }
};
</script>

<style scoped>
.img-thumbnail.border-primary {
  border-width: 3px !important;
}
</style>
