<template>

    <body class="bg-light">
        <div class="container my-5">
            <div class="row">
                <!-- Form -->
                <div class="col-md-7">
                    <div class="form-container">
                        <div class="d-flex mb-4">
                            <div class="tab-header me-2">Cá nhân</div>
                            <div class="tab-header text-muted me-2">Thanh toán</div>
                            <div class="tab-header text-muted">Xác nhận</div>
                        </div>
                        <form>
                            <div class="mb-3">
                                <label class="form-label">Họ Và Tên Người Nhận</label>
                                <input type="text" class="form-control" />
                            </div>
                            <div class="row mb-3">
                                <div class="col">
                                    <label class="form-label">Địa Chỉ Email</label>
                                    <input type="email" class="form-control" />
                                </div>
                                <div class="col">
                                    <label class="form-label">Số Điện Thoại Người Nhận</label>
                                    <input type="text" class="form-control" />
                                </div>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Địa Chỉ</label>
                                <input type="text" class="form-control" />
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Tỉnh/Thành Phố</label>
                                <input type="text" class="form-control" />
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Quê Quán*</label>
                                <input type="text" class="form-control" />
                            </div>
                            <div class="mb-4">
                                <label class="form-label">Mã Bưu Điện *</label>
                                <input type="text" class="form-control" />
                            </div>
                            <button type="submit" class="btn-next">Bước tiếp theo</button>
                        </form>
                    </div>
                </div>

                <!-- Payment Summary -->
                <div class="col-md-5">
                    <div class="summary-box">
                        <h5 class="mb-3">Chi tiết thanh toán</h5>
                        <div class="d-flex justify-content-between fw-bold border-bottom pb-2 mb-2">
                            <span>Sản Phẩm</span><span>Số Lượng</span><span>Tổng</span>
                        </div>
                        <div class="d-flex justify-content-between mb-2">
                            <span>Double Bed & Dressing</span><span>01</span><span>$180</span>
                        </div>
                        <div class="d-flex justify-content-between mb-2">
                            <span>Double Bed & Dressing</span><span>01</span><span>$120</span>
                        </div>
                        <div class="d-flex justify-content-between mb-2">
                            <span>Double Bed & Dressing</span><span>01</span><span>$100</span>
                        </div>
                        <hr />
                        <div class="d-flex justify-content-between fw-bold">
                            <span>Tổng</span><span>$400</span>
                        </div>
                        <div class="d-flex justify-content-between">
                            <span>Phí Vận Chuyển</span><span>$100</span>
                        </div>
                        <hr />
                        <div class="d-flex justify-content-between fw-bold fs-5">
                            <span>Tổng Kết</span><span>$500</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>


</template>
<script setup>
import { ref } from 'vue'
import { useRoute } from 'vue-router'
const route = useRoute();
const selectedProducts = ref([]);
if (route.query.products) {
    try {
        selectedProducts.value = JSON.parse(route.query.products)
    } catch (error) {
        console.error('Failed to parse selectedProducts:', error)
    }
}
console.log(selectedProducts.value)
</script>
<style scoped>
.tab-header {
    background-color: #fdeee6;
    padding: 1rem;
    border-top-left-radius: 0.5rem;
    border-top-right-radius: 0.5rem;
    font-weight: bold;
}

.form-container {
    border: 1px solid #f0f0f0;
    border-radius: 0.5rem;
    padding: 1.5rem;
    background-color: white;
}

.summary-box {
    border: 1px solid #f0f0f0;
    border-radius: 0.5rem;
    padding: 1rem;
    background-color: #fffaf7;
}

.btn-next {
    background-color: #fbd3bd;
    border: none;
    color: white;
    padding: 0.5rem 1.5rem;
    border-radius: 1rem;
    font-weight: bold;
    cursor: pointer;
}

.btn-next:hover {
    background-color: #f9b795;
}
</style>