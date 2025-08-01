<!-- src/components/ProductCard.vue -->
<template>
  <div class="product-card">
    <div class="modern-card">
      <!-- Discount Badge -->
      <div class="discount-badge">
        <i class="bi bi-lightning-charge me-1"></i>
        Giảm giá
      </div>

      <!-- Image Container with 4:5 Aspect Ratio -->
      <div class="image-container">
        <img
          class="product-image"
          :src="props.product?.imageUrl || '../../assets/imgs/ao_bomber_nu.webp'"
          :alt="props.product?.name || 'Sản phẩm'"
        />
        <div class="image-overlay">
          <button class="quick-view-btn" @click="goToDetailPage(props.product.id)">
            <i class="bi bi-eye"></i>
            <span>Xem nhanh</span>
          </button>
        </div>
      </div>

      <!-- Product Content -->
      <div class="product-content">
        <!-- Rating & Price Row -->
        <div class="rating-price-row">
          <div class="stars">
            <i
              v-for="i in 5"
              :key="i"
              :class="i <= props.product.rating ? 'bi bi-star-fill' : 'bi bi-star'"
              class="star-icon"
            ></i>
          </div>
          <span class="current-price">{{ displayPrice }}</span>
        </div>

        <!-- Product Name -->
        <h3 class="product-name">{{ props.product.name }}</h3>

        <!-- Product Description -->
        <p class="product-description">{{ props.product.descriptionOfSku }}</p>

        <!-- Stock Status Tag -->
        <div class="stock-tag">
          <i class="bi bi-check-circle me-1"></i>
          Còn hàng
        </div>

        <!-- Action Buttons -->
        <div class="action-section">
          <div class="button-row">
            <button class="cart-button" @click="openPopup(props.product)">
              <i class="bi bi-cart-plus me-1"></i>
              Giỏ hàng
              <span class="cart-count">{{ props.product.turnBuy }}</span>
            </button>

            <button class="detail-button" @click="goToDetailPage(props.product.id)">
              <i class="bi bi-info-circle me-1"></i>
              Chi tiết
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from "vue";
import { useRouter } from "vue-router";
import { catchUserEvent } from "../../Configs/handleCatchUserProductEvent";
const props = defineProps({
  product: Object,
});

const router = useRouter();
const listPrice = ref([]);
const displayPrice = ref([]);

onMounted(() => {
  listPrice.value = props.product.listPrice;
  const sortedPrices = Array.from(
    new Set(
      String(listPrice.value) // Ensure it's a string
        .split(",") // Split to array
        .map((str) => Number(str.trim())) // Convert to numbers
        .filter((n) => !isNaN(n)) // Remove invalid numbers
    )
  ).sort((a, b) => a - b);

  // listPrice.value = listStringPriceToNumber.split(",").stringList.map(Number).sort((a, b) => a - b)

  displayPrice.value =
    sortedPrices.length > 1
      ? `${sortedPrices[0]}₫ - ${sortedPrices[sortedPrices.length - 1]}₫`
      : `${sortedPrices[0]}₫`;
});
function goToDetailPage(productId) {
  let payLoad = {
    id: "",
    eventType: "VIEWDETAIL",
    positionInList: "",
    timeSpentSeconds: 0,
    productItemId: productId,
  };
  catchUserEvent(payLoad);
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
  height: 100%;
  transition: all 0.3s ease;
}

.product-card:hover {
  transform: translateY(-5px);
}

.modern-card {
  background: linear-gradient(135deg, #ffffff 0%, #f8f9fa 100%);
  border-radius: 20px;
  overflow: hidden;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
  border: 1px solid rgba(102, 126, 234, 0.1);
  transition: all 0.3s ease;
  height: 100%;
  display: flex;
  flex-direction: column;
  position: relative;
}

.modern-card:hover {
  box-shadow: 0 20px 50px rgba(102, 126, 234, 0.15);
  border-color: rgba(102, 126, 234, 0.2);
}

/* Discount Badge */
.discount-badge {
  position: absolute;
  top: 15px;
  left: 15px;
  background: linear-gradient(135deg, #ff6b6b 0%, #ee5a52 100%);
  color: white;
  padding: 8px 15px;
  border-radius: 25px;
  font-size: 0.85rem;
  font-weight: 600;
  z-index: 10;
  box-shadow: 0 4px 15px rgba(255, 107, 107, 0.3);
  display: flex;
  align-items: center;
  animation: pulse 2s infinite;
}

@keyframes pulse {
  0%,
  100% {
    transform: scale(1);
  }
  50% {
    transform: scale(1.05);
  }
}

/* Image Container with 4:5 Aspect Ratio */
.image-container {
  position: relative;
  width: 100%;
  aspect-ratio: 4/5;
  overflow: hidden;
  border-radius: 20px 20px 0 0;
  background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
}

.product-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: all 0.3s ease;
}

.modern-card:hover .product-image {
  transform: scale(1.05);
}

.image-overlay {
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

.modern-card:hover .image-overlay {
  opacity: 1;
}

.quick-view-btn {
  background: rgba(255, 255, 255, 0.95);
  border: none;
  border-radius: 50px;
  padding: 12px 25px;
  font-weight: 600;
  color: #667eea;
  backdrop-filter: blur(10px);
  box-shadow: 0 8px 25px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  gap: 8px;
  transform: translateY(20px);
}

.modern-card:hover .quick-view-btn {
  transform: translateY(0);
}

.quick-view-btn:hover {
  background: white;
  transform: scale(1.05);
  box-shadow: 0 12px 35px rgba(0, 0, 0, 0.15);
}

/* Product Content */
.product-content {
  padding: 20px;
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 12px;
}

/* Rating & Price Row */
.rating-price-row {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.stars {
  display: flex;
  gap: 2px;
}

.star-icon {
  color: #ffc107;
  font-size: 0.85rem;
}

/* Product Name */
.product-name {
  font-size: 1rem;
  font-weight: 700;
  color: #2d3748;
  margin: 0;
  line-height: 1.3;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

/* Product Description */
.product-description {
  color: #6c757d;
  font-size: 0.85rem;
  line-height: 1.4;
  margin: 0;
  display: -webkit-box;
  -webkit-line-clamp: 1;
  line-clamp: 1;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.current-price {
  font-size: 1.1rem;
  font-weight: 700;
  color: #667eea;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

/* Stock Tag */
.stock-tag {
  background: linear-gradient(135deg, #d1fae5 0%, #a7f3d0 100%);
  color: #065f46;
  border: 1px solid #10b981;
  border-radius: 20px;
  padding: 4px 12px;
  font-size: 0.75rem;
  font-weight: 600;
  display: inline-flex;
  align-items: center;
  width: fit-content;
  box-shadow: 0 2px 6px rgba(16, 185, 129, 0.1);
}

/* Action Section */
.action-section {
  margin-top: auto;
}

.button-row {
  display: flex;
  gap: 8px;
}

/* Buttons */
.cart-button {
  background: linear-gradient(135deg, #f59e0b 0%, #d97706 100%);
  color: white;
  border: none;
  border-radius: 8px;
  padding: 10px 16px;
  font-weight: 600;
  font-size: 0.8rem;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 4px;
  flex: 1;
  box-shadow: 0 2px 8px rgba(245, 158, 11, 0.2);
}

.cart-count {
  background: rgba(255, 255, 255, 0.25);
  border-radius: 10px;
  padding: 1px 6px;
  font-size: 0.7rem;
  min-width: 18px;
  text-align: center;
}

.detail-button {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border: none;
  border-radius: 8px;
  padding: 10px 16px;
  font-weight: 600;
  font-size: 0.8rem;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 4px;
  flex: 1;
  box-shadow: 0 2px 8px rgba(102, 126, 234, 0.2);
}

.cart-button:hover,
.detail-button:hover {
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

/* Responsive Design */
@media (max-width: 768px) {
  .product-content {
    padding: 15px;
    gap: 10px;
  }

  .product-name {
    font-size: 0.95rem;
  }

  .current-price {
    font-size: 1rem;
  }

  .button-row {
    gap: 6px;
  }

  .cart-button,
  .detail-button {
    padding: 8px 12px;
    font-size: 0.75rem;
  }
}
</style>
