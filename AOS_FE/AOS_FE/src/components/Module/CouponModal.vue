<template>
    <div class="modal-overlay" @click.self="onClose">
        <div class="modal-box">
            <!-- Header -->
            <div class="modal-header">
                <h5 class="modal-title">{{ title }}</h5>
                <button class="close-btn" @click="onClose" aria-label="Đóng hộp thoại">&times;</button>
            </div>

            <!-- Body -->
            <div class="modal-body">
                <template v-if="coupons.length > 0">
                    <ul class="coupon-list">
                        <li v-for="coupon in coupons" :key="coupon.id" class="coupon-item">
                            <div class="coupon-info">
                                <span class="coupon-code">{{ coupon.code }}</span>
                                <small class="coupon-description">{{ coupon.description }}</small>
                            </div>
                            <button class="apply-btn" @click="applyCoupon(coupon)">Áp dụng</button>
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
                <button class="close-footer-btn" @click="onClose" aria-label="Đóng">Đóng</button>
            </div>
        </div>
    </div>
</template>

<script setup>
const props = defineProps({
    title: String,
    coupons: Array
});

const emit = defineEmits(['close', 'select']);

function applyCoupon(coupon) {
    emit('select', coupon);
    emit('close');
}

function onClose() {
    emit('close');
}
</script>

<style scoped>
.modal-overlay {
    position: fixed;
    inset: 0;
    background-color: rgba(0, 0, 0, 0.4);
    display: flex;
    align-items: center;
    justify-content: center;
    z-index: 9999;
}

.modal-box {
    background: #ffffff;
    width: 90%;
    max-width: 500px;
    border-radius: 16px;
    box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1);
    overflow: hidden;
    display: flex;
    flex-direction: column;
    max-height: 80vh;
}

.modal-header {
    padding: 16px 20px;
    background-color: #f5f5f5;
    border-bottom: 1px solid #ddd;
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.modal-title {
    font-size: 1.1rem;
    font-weight: 600;
    color: #333;
}

.close-btn {
    background: none;
    border: none;
    font-size: 1.4rem;
    color: #888;
    cursor: pointer;
    transition: color 0.2s;
}

.close-btn:hover {
    color: #000;
}

.modal-body {
    padding: 16px 20px;
    overflow-y: auto;
    flex-grow: 1;
}

.no-coupon-message {
    text-align: center;
    color: #777;
    font-size: 1rem;
    margin-top: 20px;
    padding: 10px;
    background-color: #fdfdfd;
    border: 1px dashed #ccc;
    border-radius: 8px;
}

.coupon-list {
    list-style: none;
    padding: 0;
    margin: 0;
}

.coupon-item {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 12px 0;
    border-bottom: 1px solid #eee;
}

.coupon-info {
    display: flex;
    flex-direction: column;
}

.coupon-code {
    font-weight: 600;
    color: #1a73e8;
}

.coupon-description {
    font-size: 0.85rem;
    color: #666;
}

.apply-btn {
    background-color: #28a745;
    color: white;
    border: none;
    padding: 6px 12px;
    font-size: 0.9rem;
    border-radius: 6px;
    cursor: pointer;
    transition: background-color 0.2s;
}

.apply-btn:hover {
    background-color: #218838;
}

.modal-footer {
    padding: 12px 20px;
    background-color: #f9f9f9;
    border-top: 1px solid #ddd;
}

.close-footer-btn {
    width: 100%;
    padding: 10px;
    background-color: #ccc;
    border: none;
    border-radius: 6px;
    font-weight: bold;
    color: #333;
    cursor: pointer;
    transition: background-color 0.2s;
}

.close-footer-btn:hover {
    background-color: #bbb;
}
</style>
