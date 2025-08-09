<!-- src/components/ProductList.vue -->
<template>
    <div class="product-grid">
        <ProductCard v-for="(product, index) in products" :key="product.id || index" :product="product"
            @view-detail="$emit('view-detail', product)" />
        <!-- Modal chi tiết sản phẩm -->
        <div v-if="showModal" class="modal fade show d-block" tabindex="-1"
            style="background-color: rgba(0, 0, 0, 0.5)">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">{{ selectedProduct?.name }}</h5>
                        <button type="button" class="btn-close" @click="closeModal"></button>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-md-6">
                                <img :src="selectedProduct?.image" class="img-fluid mb-3 rounded" alt="Product image" />
                            </div>
                            <div class="col-md-6">
                                <p><strong>Giá gốc:</strong> <del>{{ formatPrice(selectedProduct?.originalPrice)
                                }}</del></p>
                                <p><strong>Giá giảm:</strong> {{ formatPrice(selectedProduct?.salePrice) }}</p>
                                <p><strong>Giảm giá:</strong> {{ selectedProduct?.discountPercent }}%</p>
                                <p><strong>Vật liệu:</strong> {{ selectedProduct?.material || '---' }}</p>
                                <p><strong>Biến thể:</strong> {{ selectedProduct?.sku || '---' }}</p>
                                <p>
                                    <strong>Đánh giá:</strong> {{ selectedProduct?.averageRating }} ⭐
                                    ({{ selectedProduct?.totalReviews }} đánh giá)
                                </p>
                                <div class="mb-3">
                                    <label for="qtyInput" class="form-label"><strong>Số lượng:</strong></label>
                                    <input v-model="quantity" type="number" id="qtyInput" min="1"
                                        class="form-control w-50" />
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button class="btn btn-secondary" @click="closeModal">Đóng</button>
                        <button class="btn btn-primary" @click="addToCart">Thêm vào giỏ</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import ProductCard from './ProductCard.vue'; // hoặc đường dẫn phù hợp

const props = defineProps({
    products: {
        type: Array,
        required: true
    }
});

const emit = defineEmits(['openPopup']);
</script>

<style scoped>
.product-grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
    gap: 24px;
    padding: 24px;
}

@media (max-width: 1200px) {
    .product-grid {
        grid-template-columns: repeat(3, 1fr);
    }
}

@media (max-width: 992px) {
    .product-grid {
        grid-template-columns: repeat(2, 1fr);
    }
}

@media (max-width: 576px) {
    .product-grid {
        grid-template-columns: 1fr;
    }
}
</style>
