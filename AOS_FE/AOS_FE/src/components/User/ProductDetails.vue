<template>
    <div class="container py-4">
        <!-- Breadcrumb -->
        <nav class="breadcrumb mb-4">
            <a class="breadcrumb-item" href="#">Danh sách sản phẩm</a>
            <span class="breadcrumb-item active">Áo thun phông trắng</span>
        </nav>

        <!-- Product Content -->
        <div class="row">
            <!-- Hình ảnh trái (4/12) -->
            <div class="col-lg-7 col-md-12 mb-4">
                <div class="row">
                    <div class="col-3 d-flex flex-column gap-3">
                        <img v-for="(img, idx) in images" :key="idx" :src="img" @click="currentImage = img"
                            :class="['img-thumbnail', { 'border-primary': currentImage === img }]"
                            style="cursor: pointer; width: 100%; aspect-ratio: 1/1; object-fit: cover" />
                    </div>
                    <div class="col-9">
                        <img :src="currentImage" alt="main" class="img-fluid border rounded w-100" />
                    </div>
                </div>
            </div>

            <!-- Thông tin phải (7/12) -->
            <div class="col-lg-5 col-md-12">
                <h2 class="fw-semibold mb-1">Áo thun phông trắng</h2>
                <p class="text-danger fs-4 fw-bold">50.000đ <small class="text-muted">| ★★★★☆ (32 Lượt mua)</small></p>
                <p class="text-muted mb-3">
                    Đây là đoạn văn mô phỏng nội dung, dùng để trình bày bố cục văn bản hoặc giao diện một cách trực
                    quan.
                    Nội dung không mang ý nghĩa thực tế, chỉ nhằm mục đích minh họa.
                </p>
                <ul class="mb-4">
                    <li>Đây là một dòng mô phỏng tiêu đề hoặc mô tả ngắn.</li>
                    <li>Dòng này thể hiện nội dung minh họa cho phần danh sách sản phẩm, dịch vụ, hoặc tính năng.</li>
                    <li>Một dòng khác trong danh sách để làm mẫu định dạng.</li>
                </ul>

                <!-- Số lượng và nút Thêm vào giỏ: cùng 1 dòng -->
                <div class="d-flex flex-wrap align-items-center gap-3 mb-3">
                    <!-- Số lượng -->
                    <div class="d-flex align-items-center border rounded-pill px-3"
                        style="height: 48px; flex: 1 1 120px; min-width: 100px; max-width: 160px;">
                        <button @click="decreaseQty" class="btn btn-sm px-3 py-0 border-0">−</button>
                        <span class="mx-3 fw-medium">{{ quantity }}</span>
                        <button @click="increaseQty" class="btn btn-sm px-3 py-0 border-0">+</button>
                    </div>

                    <!-- Thêm vào giỏ -->
                    <button class="text-white flex-grow-1"
                        style="background-color: #e9cebd; border-radius: 999px; border: none; height: 48px; min-width: 150px;">
                        Thêm vào giỏ
                    </button>
                </div>

                <!-- Mua ngay -->
                <div class="mb-3">
                    <button class="w-100"
                        style="background-color: #fdf3ed; color: #000; border: 1px solid #000; border-radius: 999px; padding: 14px 0; font-size: 18px; font-weight: 600;">
                        Mua ngay
                    </button>
                </div>

                <!-- Thông tin giao hàng -->
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
                    <button class="nav-link" :class="{ active: activeTab === 'desc' }" @click="activeTab = 'desc'">
                        Mô tả
                    </button>
                </li>
                <li class="nav-item">
                    <button class="nav-link" :class="{ active: activeTab === 'review' }" @click="activeTab = 'review'">
                        Đánh giá
                    </button>
                </li>
            </ul>
            <div class="tab-content border border-top-0 p-4 bg-light-subtle">
                <div v-show="activeTab === 'desc'">
                    <h6 class="fw-bold">ÁO THUN TRẮNG</h6>
                    <ul class="list-unstyled">
                        <li>🌿 Chất liệu thun cotton pha polyester mềm mại co giãn tốt, thoáng mát. Áo form rộng unisex
                            phù hợp cho cả nam và nữ.</li>
                        <li>🌿 Áo chưa in hình nên bạn có thể mua về tự in hình hoặc vẽ lên theo ý thích.</li>
                        <li>🌿 Áo màu trắng, in hình lên rất nổi bật. Công nghệ in chuyển nhiệt, màu sắc tươi sáng rõ
                            nét.</li>
                        <li>🌿 Thoải mái kết hợp với jean, short, kaki, váy... phù hợp nhóm, gia đình.</li>
                    </ul>
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
                                <input type="text" class="form-control rounded-pill" v-model="newReview.name"
                                    required />
                            </div>
                            <div class="col-md-6">
                                <label class="form-label">Địa chỉ Email</label>
                                <input type="email" class="form-control rounded-pill" placeholder="example@gmail.com" />
                            </div>
                        </div>
                        <div class="mb-2">
                            <label class="form-label">Viết đánh giá của bạn</label>
                            <textarea class="form-control rounded" rows="3" v-model="newReview.text"
                                required></textarea>
                        </div>
                        <div class="d-flex justify-content-between align-items-center">
                            <div>
                                đánh giá ★☆☆☆☆
                            </div>
                            <button type="submit" class="btn btn-dark rounded-pill">Đăng Bình Luận</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <!-- Related Products -->
        <div class="mt-5">
            <h4 class="mb-4">Sản phẩm tương tự</h4>
            <div class="row row-cols-2 row-cols-md-4 g-4">
                <div v-for="(item, i) in related" :key="i" class="col">
                    <div class="card h-100 text-center border-0">
                        <div class="position-relative">
                            <img :src="item.img" class="card-img-top p-3"
                                style="aspect-ratio: 1 / 1; object-fit: contain" />
                            <span class="badge bg-dark position-absolute top-0 start-0 m-2">-13%</span>
                        </div>
                        <div class="card-body">
                            <h6 class="card-title mb-1">{{ item.name }}</h6>
                            <p class="mb-0">
                                <del class="text-muted me-1">80.000</del>
                                <strong>{{ item.price }}đ</strong>
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            images: [
                'https://c.animaapp.com/mbi6g3dpwNBQr4/img/image-1.png',
                'https://c.animaapp.com/mbi6g3dpwNBQr4/img/image-2.png',
                'https://c.animaapp.com/mbi6g3dpwNBQr4/img/image-3.png'
            ],
            currentImage: 'https://c.animaapp.com/mbi6g3dpwNBQr4/img/image-2.png',
            quantity: 1,
            activeTab: 'desc',
            reviews: [
                { name: 'Trần Lộc Lào', text: 'Sản phẩm quá tốt' },
                { name: 'Khả Mặt Hiền', text: 'sản phẩm chất lượng hơn cả mong đợi' }
            ],
            newReview: { name: '', text: '' },
            related: [
                { name: 'Áo thun phông đen', price: '50.000', img: 'https://c.animaapp.com/mbi6g3dpwNBQr4/img/image-4.png' },
                { name: 'Áo thun phông xanh', price: '50.000', img: 'https://c.animaapp.com/mbi6g3dpwNBQr4/img/image-5.png' },
                { name: 'Áo thun phông đỏ', price: '50.000', img: 'https://c.animaapp.com/mbi6g3dpwNBQr4/img/image-6.png' },
                { name: 'Áo thun phông hồng', price: '50.000', img: 'https://c.animaapp.com/mbi6g3dpwNBQr4/img/image-7.png' }
            ]
        };
    },
    methods: {
        increaseQty() {
            this.quantity++;
        },
        decreaseQty() {
            if (this.quantity > 1) this.quantity--;
        },
        submitReview() {
            this.reviews.push({ ...this.newReview });
            this.newReview.name = '';
            this.newReview.text = '';
        }
    }
};
</script>

<style scoped>
.img-thumbnail.border-primary {
    border-width: 3px !important;
}
</style>
