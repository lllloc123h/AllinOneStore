<template>
    <div class="product-grid">
        <div class="product-card" v-for="(product, index) in products" :key="product.id || index">
            <div class="card position-relative overflow-hidden rounded-4" style="border: 0px">
                <!-- Label giảm giá -->
                <div v-if="product.discountPercent > 0"
                    class="position-absolute top-0 start-0 bg-danger text-white px-3 py-1 m-3 shadow-sm"
                    style="border-radius: 12px; font-size: 0.85rem; z-index: 10">
                    -{{ product.discountPercent }}%
                </div>

                <!-- Hình ảnh sản phẩm -->
                <img class="card-img rounded-4 custom-shadow" style="height: 450px; object-fit: cover"
                    :src="product.image" alt="Product image" />

                <!-- Nội dung -->
                <div class="card-body">
                    <div class="card-title">
                        <template v-for="i in 5">
                            <i v-if="i <= Math.floor(product.averageRating)" class="bi bi-star-fill text-warning"
                                :key="i + '-fill'"></i>
                            <i v-else-if="i - product.averageRating < 1" class="bi bi-star-half text-warning"
                                :key="i + '-half'"></i>
                            <i v-else class="bi bi-star text-warning" :key="i + '-empty'"></i>
                        </template>
                        <span>({{ product.totalReviews }} reviews)</span>
                    </div>

                    <h5 class="card-text">
                        <del v-if="product.discountPercent > 0">{{ formatPrice(product.originalPrice) }}</del>
                        <span class="ms-2">{{ formatPrice(product.salePrice) }}</span>
                    </h5>

                    <p class="card-text">{{ product.name }}</p>

                    <div class="d-flex justify-content-end">
                        <div class="main-section rounded-4">
                            <button class="first-button">Còn hàng</button>
                            <button class="second-button">
                                <svg viewBox="0 0 24 24" width="20" height="20" stroke="#ffd300" stroke-width="2"
                                    fill="none" stroke-linecap="round" stroke-linejoin="round">
                                    <circle cx="9" cy="21" r="1"></circle>
                                    <circle cx="20" cy="21" r="1"></circle>
                                    <path d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6"></path>
                                </svg>
                                {{ product.sold }} đã bán
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
.product-grid {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    /* 12/3 = 4 cards per row */
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

.product-card {
    display: flex;
    flex-direction: column;
}

.custom-shadow {
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.12);
}

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


<script setup>
const products = [
    {
        id: 1,
        name: 'Áo thun nam',
        image: '/src/assets/imgs/logo.png',
        originalPrice: 450000,
        salePrice: 300000,
        discountPercent: 33,
        averageRating: 3.3,
        totalReviews: 128,
        sold: '3,4k'
    },
    {
        id: 2,
        name: 'Áo sơ mi trắng',
        image: '/src/assets/imgs/logo.png',
        originalPrice: 520000,
        salePrice: 410000,
        discountPercent: 21,
        averageRating: 4.2,
        totalReviews: 215,
        sold: '2,1k'
    },
    {
        id: 3,
        name: 'Quần jeans nam',
        image: '/src/assets/imgs/logo.png',
        originalPrice: 690000,
        salePrice: 480000,
        discountPercent: 30,
        averageRating: 4.5,
        totalReviews: 398,
        sold: '5,9k'
    },
    {
        id: 4,
        name: 'Áo khoác kaki',
        image: '/src/assets/imgs/logo.png',
        originalPrice: 820000,
        salePrice: 615000,
        discountPercent: 25,
        averageRating: 4.0,
        totalReviews: 302,
        sold: '4,4k'
    },
    {
        id: 5,
        name: 'Quần short thể thao',
        image: '/src/assets/imgs/logo.png',
        originalPrice: 350000,
        salePrice: 280000,
        discountPercent: 20,
        averageRating: 3.9,
        totalReviews: 92,
        sold: '1,3k'
    },
    {
        id: 6,
        name: 'Áo hoodie nam',
        image: '/src/assets/imgs/logo.png',
        originalPrice: 740000,
        salePrice: 555000,
        discountPercent: 25,
        averageRating: 4.6,
        totalReviews: 509,
        sold: '6,7k'
    },
    {
        id: 7,
        name: 'Áo ba lỗ thể thao',
        image: '/src/assets/imgs/logo.png',
        originalPrice: 250000,
        salePrice: 200000,
        discountPercent: 20,
        averageRating: 3.5,
        totalReviews: 73,
        sold: '920'
    },
    {
        id: 8,
        name: 'Áo len nam mùa đông',
        image: '/src/assets/imgs/logo.png',
        originalPrice: 890000,
        salePrice: 667000,
        discountPercent: 25,
        averageRating: 4.8,
        totalReviews: 342,
        sold: '7,2k'
    }
];

function formatPrice(price) {
    return price.toLocaleString('vi-VN') + ' VND';
}
</script>
