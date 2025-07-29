<!-- src/components/ProductCard.vue -->
<template>
  <div class="product-card">
    <div class="card position-relative overflow-hidden rounded-4" style="border: 0px">

      <div style="border: 0px" class="card position-relative overflow-hidden rounded-4">
        <!-- Label Giảm giá -->
        <div class="position-absolute top-0 start-0 bg-danger text-white px-3 py-1 m-3 shadow-sm"
          style="border-radius: 12px; font-size: 0.85rem; z-index: 10">
          Giảm giá
        </div>

        <!-- Bo góc ảnh luôn -->
        <img class="card-img rounded-4 custom-shadow" style="height: 450px; object-fit: cover"
          :src="props.product?.imageUrl || '../../assets/imgs/ao_bomber_nu.webp'" alt="Card
              image" />

        <!-- Nội dung -->
        <div class="card-body">
          <div class="card-title">
            <span v-for="i in 5" :key="i">
              <i :class="i <= props.product.rating ? 'bi bi-star-fill text-warning' : 'bi bi-star text-warning'"></i>
            </span>
          </div>
          <h5 class="card-text"> <!--<del>450 000 VND</del>  -->{{ props.product.price }}</h5>

          <p class="card-text">{{ props.product.name }}</p>

          <div class="d-flex justify-content-end">
            <!-- From Uiverse.io by AKAspidey01 -->
            <!-- GỢI Ý BỐ CỤC -->
            <div class="d-flex flex-column align-items-end gap-2 mt-3">
              <button class="detail-button" @click="goToDetailPage(props.product.id)">
                Chi tiết
              </button>

              <div class="main-section rounded-4">
                <button class="first-button">Còn hàng</button>
                <button class="second-button" @click="openPopup(props.product)">
                  <svg viewBox="0 0 24 24" width="20" height="20" stroke="#ffd300" stroke-width="2" fill="none"
                    stroke-linecap="round" stroke-linejoin="round" class="css-i6dzq1">
                    <circle cx="9" cy="21" r="1"></circle>
                    <circle cx="20" cy="21" r="1"></circle>
                    <path d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6"></path>
                  </svg>
                  {{ props.product.turnBuy }}
                </button>
                <button class="detail-button" @click="goToDetailPage(props.product.id)">
                  Chi tiết
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from "vue-router";

const props = defineProps({
  product: Object,
});

const router = useRouter();

function goToDetailPage(productId) {
  router.push(`/product/${productId}`);
}

const emit = defineEmits(["view-detail"]);

async function openPopup(product) {
  emit("view-detail", product);
}
function formatPrice(price) {
  return price.toLocaleString("vi-VN") + " VND";
}
</script>

<style scoped>
.product-card {
  display: flex;
  flex-direction: column;
}

.custom-shadow {
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.12);
}

/* ... giữ nguyên CSS button như cũ ... */
.first-button {
  background: #ffe8cd;
  color: #fff;
  border: none;
  font-weight: 700;
  font-size: 1em;
  min-height: 45px;
  width: 200px;
  gap: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
  transition-duration: 0.6s;
}

.main-section {
  display: flex;
  flex-direction: column;
  height: 45px;
  overflow: hidden;
  align-items: flex-start;
}

.main-section:hover .second-button,
.main-section:hover .first-button {
  transform: translateY(-45px);
}

.second-button {
  background: rgb(209, 15, 57);
  color: #fff;
  border: none;
  font-weight: 700;
  font-size: 1em;
  min-height: 45px;
  width: 200px;
  gap: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
  transition-duration: 0.6s;
}

.detail-button {
  background: #0b5ed7;
  color: white;
  border: none;
  font-weight: 600;
  font-size: 1em;
  height: 45px;
  width: 200px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 12px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
}

.detail-button:hover {
  background: #0846a8;
  transform: translateY(-2px);
}
</style>
