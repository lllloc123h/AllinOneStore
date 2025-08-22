<template>
    <div class="modal-overlay" @click.self="onClose">
        <div class="modal-box">
            <!-- Header -->
            <div class="modal-header">
                <h5 class="modal-title">{{ title }}</h5>
                <button class="close-btn" @click="onClose">&times;</button>
            </div>

            <!-- Body -->
            <div class="modal-body">
                <template v-if="coupons.length > 0">
                    <ul class="coupon-list">
                        <li v-for="coupon in coupons" :key="coupon.id" class="coupon-item"
                            :class="{ selected: selectedCoupon?.id === coupon.id }">
                            <div class="coupon-left">
                                <span class="coupon-code">{{ coupon.code }}</span>
                                <p class="coupon-description">
                                    {{ coupon.description || "Không có mô tả" }}
                                </p>
                                <small v-if="coupon.minOrderAmount">
                                    🎯 Áp dụng cho đơn từ {{ coupon.minOrderAmount.toLocaleString() }}đ
                                </small>
                            </div>

                            <div class="coupon-right">
                                <button v-if="selectedCoupon?.id === coupon.id" class="remove-btn"
                                    @click="removeCoupon">
                                    Hủy
                                </button>
                                <button v-else class="apply-btn" @click="applyCoupon(coupon)">
                                    Áp dụng
                                </button>
                            </div>
                        </li>
                    </ul>
                </template>
                <template v-else>
                    <div class="no-coupon-message">
                        🎉 Hiện bạn chưa có mã giảm giá nào khả dụng.
                    </div>
                </template>
            </div>

            <!-- Footer -->
            <div class="modal-footer">
                <button class="close-footer-btn" @click="onClose">Đóng</button>
            </div>
        </div>
    </div>
</template>

<script setup>
const props = defineProps({
    title: String,
    coupons: Array,
    selectedCoupon: Object,
});

const emit = defineEmits(["close", "select", "remove"]);

function applyCoupon(coupon) {
    emit("select", coupon);
    emit("close");
}

function removeCoupon() {
    emit("remove");
    emit("close");
}

function onClose() {
    emit("close");
}
</script>

<style scoped>
/* Overlay */
.modal-overlay {
    position: fixed;
    inset: 0;
    background-color: rgba(0, 0, 0, 0.45);
    display: flex;
    align-items: center;
    justify-content: center;
    z-index: 9999;
}

/* Box */
.modal-box {
    background: #fff;
    width: 95%;
    max-width: 520px;
    border-radius: 20px;
    box-shadow: 0 12px 32px rgba(0, 0, 0, 0.15);
    overflow: hidden;
    display: flex;
    flex-direction: column;
    max-height: 75vh;
    animation: fadeInUp 0.25s ease;
}

/* Header */
.modal-header {
    padding: 16px 20px;
    background: linear-gradient(90deg, #667eea, #764ba2);
    color: #fff;
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.modal-title {
    font-size: 1.2rem;
    font-weight: 700;
}

.close-btn {
    background: none;
    border: none;
    font-size: 1.6rem;
    color: #fff;
    cursor: pointer;
    transition: 0.2s;
}

.close-btn:hover {
    color: #ffd700;
}

/* Body */
.modal-body {
    padding: 16px 20px;
    overflow-y: auto;
    flex-grow: 1;
}

.coupon-list {
    list-style: none;
    padding: 0;
    margin: 0;
    display: flex;
    flex-direction: column;
    gap: 12px;
}

/* Coupon item */
.coupon-item {
    display: flex;
    justify-content: space-between;
    align-items: flex-start;
    padding: 14px 16px;
    border: 1px solid #eee;
    border-radius: 14px;
    background-color: #fff;
    transition: all 0.25s ease;
    cursor: pointer;
}

.coupon-item:hover {
    border-color: #667eea;
    background-color: #f3f4ff;
    transform: translateY(-2px);
}

.coupon-item.selected {
    border-color: #667eea;
    background: linear-gradient(135deg, #e0e7ff, #f3f4ff);
    box-shadow: 0 0 0 2px rgba(102, 126, 234, 0.3);
}

.coupon-left {
    flex: 1;
    display: flex;
    flex-direction: column;
    gap: 4px;
}

.coupon-code {
    font-weight: 700;
    color: #667eea;
    font-size: 1.05rem;
}

.coupon-description {
    font-size: 0.9rem;
    color: #444;
    margin: 0;
    width: 80%;
}

.coupon-left small {
    font-size: 0.8rem;
    color: #777;
}

/* Buttons */
.apply-btn,
.remove-btn {
    border: none;
    padding: 6px 14px;
    font-size: 0.9rem;
    font-weight: 600;
    border-radius: 8px;
    cursor: pointer;
    transition: all 0.2s ease;
}

/* Nút áp dụng */
.apply-btn {
    background-color: #667eea;
    color: #fff;
}

.apply-btn:hover {
    background-color: #5a67d8;
}

/* Nút hủy */
.remove-btn {
    background-color: #e53e3e;
    color: #fff;
}

.remove-btn:hover {
    background-color: #c53030;
}

/* Footer */
.modal-footer {
    padding: 14px 20px;
    background-color: #fafafa;
    border-top: 1px solid #eee;
}

.close-footer-btn {
    width: 100%;
    padding: 12px;
    background-color: #667eea;
    border: none;
    border-radius: 10px;
    font-weight: 600;
    color: #fff;
    cursor: pointer;
    transition: 0.2s;
}

.close-footer-btn:hover {
    background-color: #5a67d8;
}

/* Animations */
@keyframes fadeInUp {
    from {
        opacity: 0;
        transform: translateY(30px);
    }

    to {
        opacity: 1;
        transform: translateY(0);
    }
}
</style>
