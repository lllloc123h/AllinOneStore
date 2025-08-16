<!-- src/components/ProductCard.vue -->
<template>
  <div class="product-card">
    <div class="modern-card">
      <!-- Discount Badge -->
      <div
        v-if="props.product.promotions && props.product.promotions.length > 0"
        class="discount-badge"
      >
        <i class="bi bi-lightning-charge me-1"></i>
        Ưu đãi
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
            <span>Xem chi tiết</span>
          </button>
        </div>
        <!-- Custom Info Bottom Right -->
        <div
          v-if="props.product.custom"
          class="custom-info-badge"
          title="Sản phẩm này có thể tùy chỉnh màu sắc, kích thước và thiết kế theo yêu cầu của bạn"
        >
          <i class="bi bi-palette"></i>
          <span>Tùy chỉnh</span>
        </div>
      </div>

      <!-- Product Content -->
      <div class="product-content">
        <!-- Rating & Price Row -->
        <div class="rating-price-row">
          <div class="rating-sales">
            <div class="stars">
              <i
                v-for="i in 5"
                :key="i"
                :class="i <= props.product.rating ? 'bi bi-star-fill' : 'bi bi-star'"
                class="star-icon"
              ></i>
            </div>
            <div class="sales-info">
              <i class="bi bi-graph-up-arrow me-1"></i>
              <span class="sales-count">{{ props.product.turnBuy }} đã bán</span>
            </div>
          </div>
          <span class="current-price">{{ displayPrice }}</span>
        </div>

        <!-- Product Name -->
        <h3 class="product-name">{{ props.product.name }}</h3>

        <!-- Product Description -->
        <p class="product-description">{{ props.product.descriptionOfSku }}</p>

        <!-- Stock Status Tag -->
        <div class="stock-tag" :class="stockStatus.class">
          <i :class="stockStatus.icon" class="me-1"></i>
          {{ stockStatus.text }}
        </div>

        <!-- Action Buttons -->
        <div class="action-section">
          <div class="button-row">
            <button class="cart-button" @click="openPopup(props.product)">
              <i class="bi bi-cart-plus me-1"></i>
              Thêm vào giỏ hàng
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
import { ref, onMounted, watch, computed } from "vue";
import { useRouter } from "vue-router";
import { catchUserEvent } from "../../Configs/handleCatchUserProductEvent";
import api from "../../Configs/api";
const props = defineProps({
  product: Object,
});

// Khởi tạo promotions để tránh lỗi
if (!props.product.promotions) {
  props.product.promotions = [];
}

// Chỉ gọi API nếu product có id
if (props.product?.id) {
  api
    .get(`/Promotions/baseproducts?baseProductId=${props.product.id}`)
    .then((res) => {
      if (res.data.length > 0) {
        props.product.promotions = res.data;
        console.log("load promotions ", props.product.promotions.length);
        // props.product.discountedPrice = Math.round(
        //   props.product.price * (1 - res.data[0].discountValue / 100)
        // );
      } else {
        props.product.promotions = [];
        props.product.discountedPrice = props.product.price;
      }
    })
    .catch((error) => {
      console.error("Error fetching promotions:", error);
      // Đảm bảo promotions vẫn là array nếu API lỗi
      props.product.promotions = [];
    });
}
const router = useRouter();
const listPrice = ref([]);
const displayPrice = ref([]);

// Computed property for stock status
const stockStatus = computed(() => {
  const qty = props.product.qty || 0;

  if (qty === 0) {
    return {
      class: "stock-out",
      icon: "bi bi-x-circle",
      text: "Hết hàng",
    };
  } else if (qty <= 5) {
    return {
      class: "stock-low",
      icon: "bi bi-exclamation-triangle",
      text: `Chỉ còn ${qty} sản phẩm`,
    };
  } else if (qty <= 20) {
    return {
      class: "stock-medium",
      icon: "bi bi-dash-circle",
      text: `Còn ${qty} sản phẩm`,
    };
  } else {
    return {
      class: "stock-high",
      icon: "bi bi-check-circle",
      text: `Còn ${qty} sản phẩm`,
    };
  }
});

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
      ? `${sortedPrices[0].toLocaleString()}đ - ${sortedPrices[
          sortedPrices.length - 1
        ].toLocaleString()}đ`
      : `${sortedPrices[0].toLocaleString()}đ`;
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

.rating-sales {
  display: flex;
  align-items: center;
  gap: 8px;
}

.stars {
  display: flex;
  gap: 2px;
}

.star-icon {
  color: #ffc107;
  font-size: 0.85rem;
}

.sales-info {
  display: flex;
  align-items: center;
  gap: 4px;
  color: #10b981;
  font-size: 0.95rem;
  font-weight: 600;
}

.sales-count {
  color: #10b981;
  font-weight: bold;
}

/* Product Name */
.product-name {
  font-size: 1.1rem;
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
  font-size: 0.9rem;
  line-height: 1.4;
  margin: 0;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.current-price {
  font-size: 1.2rem;
  font-weight: 700;
  color: #e53e3e;
  background: linear-gradient(135deg, #f56565 0%, #e53e3e 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

/* Stock Tag */
.stock-tag {
  border-radius: 20px;
  padding: 6px 14px;
  font-size: 0.8rem;
  font-weight: 600;
  display: inline-flex;
  align-items: center;
  width: fit-content;
  transition: all 0.3s ease;
}

/* Stock High (>20) - Green */
.stock-high {
  background: linear-gradient(135deg, #d1fae5 0%, #a7f3d0 100%);
  color: #065f46;
  border: 1px solid #10b981;
  box-shadow: 0 2px 6px rgba(16, 185, 129, 0.1);
}

/* Stock Medium (6-20) - Yellow */
.stock-medium {
  background: linear-gradient(135deg, #fef3c7 0%, #fde68a 100%);
  color: #92400e;
  border: 1px solid #f59e0b;
  box-shadow: 0 2px 6px rgba(245, 158, 11, 0.1);
}

/* Stock Low (1-5) - Orange */
.stock-low {
  background: linear-gradient(135deg, #fed7aa 0%, #fdba74 100%);
  color: #9a3412;
  border: 1px solid #ea580c;
  box-shadow: 0 2px 6px rgba(234, 88, 12, 0.1);
}

/* Stock Out (0) - Red */
.stock-out {
  background: linear-gradient(135deg, #fecaca 0%, #fca5a5 100%);
  color: #991b1b;
  border: 1px solid #dc2626;
  box-shadow: 0 2px 6px rgba(220, 38, 38, 0.1);
}

/* Custom Info Badge */
.custom-info-badge {
  position: absolute;
  bottom: 10px;
  right: 10px;
  background: linear-gradient(135deg, #8b5cf6 0%, #7c3aed 100%);
  color: white;
  padding: 4px 8px;
  border-radius: 12px;
  font-size: 0.65rem;
  font-weight: 500;
  display: flex;
  align-items: center;
  gap: 3px;
  cursor: help;
  transition: all 0.2s ease;
  z-index: 5;
  backdrop-filter: blur(10px);
  box-shadow: 0 2px 8px rgba(139, 92, 246, 0.3);
}

.custom-info-badge:hover {
  box-shadow: 0 4px 12px rgba(139, 92, 246, 0.4);
}

.custom-info-badge i {
  font-size: 0.65rem;
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
  padding: 12px 18px;
  font-weight: 600;
  font-size: 0.85rem;
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
  padding: 12px 18px;
  font-weight: 600;
  font-size: 0.85rem;
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
