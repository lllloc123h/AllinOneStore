<!-- src/components/ProductCard.vue -->
<template>
    <div class="product-card">
        <div class="card position-relative overflow-hidden rounded-4" style="border: 0px">
            <!-- Label giảm giá -->
            <!-- <div v-if="product.discountPercent > 0"
                class="position-absolute top-0 start-0 bg-danger text-white px-3 py-1 m-3 shadow-sm"
                style="border-radius: 12px; font-size: 0.85rem; z-index: 10">
                -{{ product.discountPercent }}%
            </div>

            <img class="card-img rounded-4 custom-shadow" style="height: 450px; object-fit: cover" :src="product.image"
                alt="Product image" />

            <div class="card-body">
                <div class="card-title">
                    <template v-for="i in 5">
                        <i v-if="i <= Math.floor(product.averageRating)" class="bi bi-star-fill text-warning"
                            :key="i + '-fill'"></i>
                        <i v-else-if="i - product.averageRating < 1" class="bi bi-star-half text-warning"
                            :key="i + '-half'"></i>
                        <i v-else class="bi bi-star text-warning" :key="i + '-empty'"></i>
                    </template>
<span> ({{ product.totalReviews }} reviews)</span>
</div>

<h5 class="card-text d-flex align-items-center gap-2">
    <del v-if="product.discountPercent > 0" class="text-muted">
        {{ formatPrice(product.originalPrice) }}
    </del>
    <span>{{ formatPrice(product.salePrice) }}</span>
</h5>

<p class="card-text">{{ product.name }}</p>

<div class="d-flex justify-content-end">
    <div class="main-section rounded-4">
        <button class="first-button">Còn hàng</button>
        <button class="second-button" @click="$emit('view-detail', product)">
            <svg viewBox="0 0 24 24" width="20" height="20" stroke="#ffd300" stroke-width="2" fill="none"
                stroke-linecap="round" stroke-linejoin="round">
                <circle cx="9" cy="21" r="1"></circle>
                <circle cx="20" cy="21" r="1"></circle>
                <path d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6"></path>
            </svg>
            {{ product.sold }} đã bán
        </button>
    </div>
</div>
</div> -->
            <div style="border: 0px" class="card position-relative overflow-hidden rounded-4">
                <!-- Label Giảm giá -->
                <div class="position-absolute top-0 start-0 bg-danger text-white px-3 py-1 m-3 shadow-sm"
                    style="border-radius: 12px; font-size: 0.85rem; z-index: 10">
                    Giảm giá
                </div>

                <!-- Bo góc ảnh luôn -->
                <img class="card-img rounded-4 custom-shadow" style="height: 450px; object-fit: cover"
                    :src="props.product?.imageUrl || 'https://firebasestorage.googleapis.com/v0/b/datn-cube.firebasestorage.app/o/products%2Fao_bomber_nu.webp?alt=media&token=1e7dafd1-5898-4893-92cb-72342f849d07'"
                    alt="Card
              image" />

                <!-- Nội dung -->
                <div class="card-body">
                    <div class="card-title">
                        <i class="bi bi-star-fill text-warning"></i>
                        <i class="bi bi-star-fill text-warning"></i>
                        <i class="bi bi-star-fill text-warning"></i>
                        <i class="bi bi-star-half text-warning"></i>
                        <i class="bi bi-star text-warning"></i>
                        <span> (5,4k) revivews</span>
                    </div>
                    <h5 class="card-text"><del>450 000 VND</del> 300 000 VND</h5>

                    <p class="card-text">{{ props.product.name }}</p>

                    <div class="d-flex justify-content-end">
                        <!-- From Uiverse.io by AKAspidey01 -->

                        <div class="main-section rounded-4">
                            <button class="first-button">Còn hàng</button>
                            <button class="second-button" @click="openPopup(props.product)">
                                <svg viewBox="0 0 24 24" width="20" height="20" stroke="#ffd300" stroke-width="2"
                                    fill="none" stroke-linecap="round" stroke-linejoin="round" class="css-i6dzq1">
                                    <circle cx="9" cy="21" r="1"></circle>
                                    <circle cx="20" cy="21" r="1"></circle>
                                    <path d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6"></path>
                                </svg>
                                3,4k đã bán
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
const props = defineProps({
    product: Object
});


const emit = defineEmits(['view-detail']);

async function openPopup(product) {
    emit('view-detail', product);
}
function formatPrice(price) {
    return price.toLocaleString('vi-VN') + ' VND';
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
</style>
