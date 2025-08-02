<template>
  <section class="container py-4">
    <h4 class="fw-bold mb-4">🔥 Sản phẩm đang giảm giá</h4>
    <div v-if="discountedProducts.length" class="row row-cols-2 row-cols-md-4 g-4">
      <div class="col" v-for="product in discountedProducts" :key="product.productItemId">
        <div class="card h-100 shadow-sm product-card">
          <div class="img-wrapper">
            <img :src="product.imageUrl" class="card-img-top" alt="Ảnh sản phẩm giảm giá" />
          </div>
          <div class="card-body">
            <h5 class="card-title text-truncate">{{ product.productName }}</h5>
            <p class="card-text">
              <del>{{ format(product.originalPrice) }}</del><br />
              <strong class="text-danger fs-5">{{ format(product.discountedPrice) }}</strong>
            </p>
            <span class="badge bg-warning text-dark">{{ product.promotionName }}</span>
          </div>
        </div>
      </div>
    </div>
    <div v-else class="text-center py-5">
        <p class="text-muted">Hiện chưa có sản phẩm giảm giá nào 🤷‍♂️</p>
    </div>
  </section>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { homeService } from '../../Configs/api'

const discountedProducts = ref([])

onMounted(async () => {
  try {
    discountedProducts.value = await homeService.getDiscountedProducts()
  } catch (error) {
    console.error('Lỗi khi tải sản phẩm giảm giá:', error)
  }
})

const format = (value) => {
  return value.toLocaleString('vi-VN') + '₫'
}
</script>

<style scoped>
.img-wrapper {
  height: 220px;
  display: flex;
  align-items: center;
  justify-content: center;
  overflow: hidden;
  background-color: #fff;
}
.card-img-top {
  height: 100%;
  width: auto;
  object-fit: cover;
  border-radius: 6px;
  transition: transform 0.3s ease;
}
.product-card:hover .card-img-top {
  transform: scale(1.05);
}
.card-title {
  font-size: 1rem;
  font-weight: 600;
}
</style>