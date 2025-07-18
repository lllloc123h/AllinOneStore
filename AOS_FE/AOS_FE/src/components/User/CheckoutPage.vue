<template>
    <div class="checkout-page bg-light py-5">
        <div class="container">
            <div class="row">
                <!-- Cột form -->
                <div class="col-lg-7">
                    <div class="form-box p-4 bg-white rounded shadow-sm">
                        <!-- Tabs -->
                        <div class="d-flex mb-4 step-tabs">
                            <div class="tab" :class="{ active: currentTab === 0 }" @click="currentTab = 0">1. Cá nhân
                            </div>
                            <div class="tab" :class="{ active: currentTab === 1 }" @click="currentTab = 1">2. Thanh toán
                            </div>
                            <div class="tab" :class="{ active: currentTab === 2 }" @click="currentTab = 2">3. Xác nhận
                            </div>
                        </div>

                        <!-- Tab 1: Cá nhân -->
                        <div v-if="currentTab === 0">
                            <div class="mb-4">
                                <label class="form-label fw-bold text-danger d-flex align-items-center">
                                    <i class="bi bi-geo-alt-fill me-2"></i> Địa Chỉ Nhận Hàng
                                </label>

                                <div
                                    class="bg-white p-3 border rounded d-flex justify-content-between align-items-center flex-wrap">
                                    <div v-if="defaultAddressData">
                                        <div class="fw-bold">
                                            {{ defaultAddressData.recipientName }}
                                            <span class="ms-2">(+84) {{ defaultAddressData.phone }}</span>
                                        </div>
                                        <div>
                                            {{ defaultAddressData.street }}, {{ defaultAddressData.ward }},
                                            {{ defaultAddressData.district }}, {{ defaultAddressData.province }}
                                        </div>
                                    </div>
                                    <div class="d-flex align-items-center">
                                        <span class="badge bg-light text-danger border border-danger me-3"
                                            v-if="defaultAddressData?.default">
                                            Mặc Định
                                        </span>
                                        <button class="btn btn-link text-primary p-0" @click="goToAddress">Thay
                                            Đổi</button>
                                    </div>
                                </div>
                            </div>

                            <div class="mb-3">
                                <label class="form-label">Nhập mã giảm giá</label>
                                <div class="input-group">
                                    <input v-model="couponCodeInput" type="text" class="form-control"
                                        placeholder="Nhập mã...">
                                    <button class="btn btn-outline-secondary" type="button" @click="applyCoupon">Áp
                                        dụng</button>
                                </div>
                                <div v-if="couponError" class="text-danger mt-1">{{ couponError }}</div>
                                <div v-if="isCouponApplicable" class="text-success mt-1">
                                    Đã áp dụng mã: {{ selectedCoupon.code }} - Giảm {{ selectedCoupon.discountValue }}{{
                                        selectedCoupon.discountType === 'PERCENT' ? '%' : '₫' }}
                                </div>
                                <div v-else-if="selectedCoupon" class="text-warning mt-1">
                                    * Đơn hàng chưa đủ {{ selectedCoupon.minOrderAmount?.toLocaleString?.() || 0 }}₫ để
                                    áp dụng mã
                                </div>
                            </div>


                            <button class="btn btn-warning w-100 fw-bold" @click="currentTab = 1">Bước tiếp
                                theo</button>
                        </div>

                        <!-- Tab 2: Thanh toán -->
                        <div v-if="currentTab === 1">
                            <div class="mb-3">
                                <label class="form-label fw-bold">Phương thức thanh toán</label>
                                <div class="form-check" v-for="method in dropdownPaymentMethods" :key="method">
                                    <input type="radio" class="form-check-input" :value="method"
                                        v-model="paymentMethod" />
                                    <label class="form-check-label">
                                        {{ method.name }}
                                    </label>
                                </div>
                            </div>
                            <button class="btn btn-primary w-100 fw-bold" @click="currentTab = 2">Tiếp tục</button>
                        </div>

                        <!-- Tab 3: Xác nhận -->
                        <div v-if="currentTab === 2">
                            <h5 class="mb-3">Xác nhận đơn hàng</h5>
                            <ul class="list-unstyled">
                                <li><strong>Người nhận:</strong> {{ defaultAddressData?.recipientName || '—' }}</li>
                                <li><strong>Số điện thoại:</strong> {{ defaultAddressData?.phone || '—' }}</li>
                                <li><strong>Địa chỉ:</strong> {{ fullAddress }}</li>
                                <li><strong>Giảm giá:</strong> {{ selectedCoupon?.code || 'Không áp dụng' }}</li>
                                <li><strong>Thanh toán:</strong> {{ paymentMethod.name }}</li>
                            </ul>

                            <h6 class="mt-4">Danh sách sản phẩm</h6>
                            <ul class="list-unstyled">
                                <li v-for="item in selectedProducts" :key="item.id">
                                    {{ item.name }} - SL: {{ item.quantity }} - {{ (item.price *
                                        item.quantity).toLocaleString() }}₫
                                </li>
                            </ul>

                            <button class="btn btn-success mt-3 w-100 fw-bold" @click="confirmOrder">Xác nhận đặt
                                hàng</button>
                        </div>
                    </div>
                </div>

                <!-- Cột tóm tắt -->
                <div class="col-lg-5">
                    <div class="summary-box p-4 bg-white rounded shadow-sm">
                        <h5 class="mb-3">Chi tiết thanh toán</h5>
                        <div v-for="item in selectedProducts" :key="item.id"
                            class="d-flex justify-content-between mb-2">
                            <span>{{ item.name }} x{{ item.quantity }}</span>
                            <span>{{ (item.price * item.quantity).toLocaleString() }}₫</span>
                        </div>
                        <hr />
                        <div class="d-flex justify-content-between fw-bold">
                            <span>Tạm tính:</span>
                            <span>{{ totalPrice.toLocaleString() }}₫</span>
                        </div>
                        <div class="d-flex justify-content-between">
                            <span>Giảm giá:</span>
                            <span>-{{ discountAmount.toLocaleString() }}₫</span>
                        </div>
                        <hr />
                        <div class="d-flex justify-content-between fs-5 fw-bold text-danger">
                            <span>Tổng cộng:</span>
                            <span>{{ finalPrice.toLocaleString() }}₫</span>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Popup thành công -->
            <div v-if="showSuccess" class="popup-success">
                <div class="popup-content text-center">
                    <h4>Cảm ơn bạn đã đặt hàng!</h4>
                    <p>Chúng tôi sẽ xử lý đơn hàng sớm nhất.</p>
                    <button class="btn btn-primary" @click="router.push('/')">Về trang chủ</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, computed, onMounted, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import api, { authService } from '../../Configs/api'
import { onBeforeRouteLeave } from 'vue-router'
import { dropDown } from '../../Configs/DropDownList'

const router = useRouter()
const route = useRoute()

const currentTab = ref(0)
const selectedProducts = ref([])
const selectedCoupon = ref(null)
const paymentMethod = ref('COD')
const showSuccess = ref(false)
const defaultAddressData = ref(null)
const dropdownPaymentMethods = ref([]);
const couponCodeInput = ref('')
const couponError = ref('')

// ==== Computed Properties ====
const totalPrice = computed(() =>
    selectedProducts.value.reduce((acc, item) => acc + item.price * item.quantity, 0)
)

const discountAmount = computed(() => {
    const coupon = selectedCoupon.value;
    const minOrder = coupon?.minOrderAmount ?? 0;

    if (!coupon || totalPrice.value < minOrder) return 0;

    if (coupon.discountType === 'PERCENT') {
        const discount = ((coupon.discountValue ?? 0) / 100) * totalPrice.value;
        return coupon.maxDiscountAmount != null
            ? Math.min(discount, coupon.maxDiscountAmount)
            : discount;
    }

    return coupon.discountValue ?? 0;
});

const isCouponApplicable = computed(() => {
    const coupon = selectedCoupon.value;
    if (!coupon) return false;
    return totalPrice.value >= (coupon.minOrderAmount ?? 0);
});



const finalPrice = computed(() => totalPrice.value - discountAmount.value)

const fullAddress = computed(() => {
    const addr = defaultAddressData.value
    return addr ? `${addr.street}, ${addr.ward}, ${addr.district}, ${addr.province}` : ''
})

// ==== Methods ====

function goToAddress() {
    router.push({ name: 'shippingaddress', query: { fromCheckout: '1' } })
}

async function applyCoupon() {
    couponError.value = ''
    selectedCoupon.value = null

    if (!couponCodeInput.value) {
        couponError.value = 'Vui lòng nhập mã giảm giá.'
        return
    }

    const hasCombo = selectedProducts.value.some(item => item.isCombo === true)

    try {
        const { data } = await api.get('/Coupons/validate', {
            params: {
                code: couponCodeInput.value,
                hasCombo: hasCombo
            }
        })
        selectedCoupon.value = data
        localStorage.setItem('selectedCoupon', JSON.stringify(data))
    } catch (err) {
        couponError.value = err.response?.data || 'Mã giảm giá không hợp lệ.'
        console.error(err)
    }
}


async function confirmOrder() {
    const token = authService.getToken()

    if (!token) {
        alert('Bạn cần đăng nhập trước khi đặt hàng.')
        router.push({ name: 'login' })
        return
    }

    try {
        const payload = {
            address: defaultAddressData.value.id,
            // couponCode: selectedCoupon.value?.code || null,
            discountCouponCode: selectedCoupon.value?.code || null,
            paymentMethods: paymentMethod.value.name,
            products: selectedProducts.value,
            finalTotal: finalPrice.value
        }
        // const tempPayload = {
        //     id: '',
        //     actualShippingFee: '',
        //     discountCouponCode: '',
        //     discountValue: '',
        //     shippedDate: '',
        //     paymentStatus: '',
        //     note: '',
        //     point: '',
        //     finalTotal: '',
        //     orderInfor: '',
        //     createdAt: '',
        //     updatedAt: '',
        //     accounts: '',
        //     paymentMethods: '',
        //     shippingMethods: '',
        // }
        console.log('📦', payload);
        const response = await api.post('/user/Orders', { ...payload })
        console.log('✅ Đặt hàng thành công:', payload)
        alert('Đặt hàng thành công!')
        console.log(response.data)
        localStorage.removeItem('selectedCoupon')

        showSuccess.value = true
        localStorage.removeItem('checkoutProducts') // 🧹 Xoá khi đã đặt
    } catch (err) {
        console.error('🔥 Lỗi đặt hàng:', err.response?.data || err.message)
        alert('Lỗi đặt hàng!')
    }
}

// ==== Lifecycle ====

onMounted(async () => {
    dropdownPaymentMethods.value = (await dropDown('PaymentMethods')).content;
    console.log('✅ Đã tải danh sách phương thức thanh toán:', dropdownPaymentMethods.value)
    if (route.query.products) {
        try {
            selectedProducts.value = JSON.parse(route.query.products)
            localStorage.setItem('checkoutProducts', JSON.stringify(selectedProducts.value))
        } catch (err) {
            console.error('Lỗi phân tích sản phẩm:', err)
        }
    } else if (!selectedProducts.value.length && localStorage.getItem('checkoutProducts')) {
        selectedProducts.value = JSON.parse(localStorage.getItem('checkoutProducts'))
    }

    // ✅ Load coupon từ localStorage nếu có
    if (localStorage.getItem('selectedCoupon')) {
        selectedCoupon.value = JSON.parse(localStorage.getItem('selectedCoupon'))
    }

    try {
        const { data: addresses } = await api.get('/UserAddresses')
        defaultAddressData.value = addresses.find(addr => addr.default) || addresses[0]
    } catch (err) {
        console.error('Lỗi lấy địa chỉ:', err)
    }
    const savedTab = localStorage.getItem('checkoutTab')
    if (savedTab !== null) {
        currentTab.value = parseInt(savedTab)
    }

    const savedMethod = localStorage.getItem('paymentMethod')
    if (savedMethod) {
        paymentMethod.value = savedMethod
    }

})
onBeforeRouteLeave((to, from, next) => {
    const isGoingToShipping = to.name === 'shippingaddress'

    // Nếu KHÔNG phải đi đến trang địa chỉ (tức là rời khỏi hoàn toàn checkout)
    if (!isGoingToShipping) {
        // 🧹 Dọn sạch mọi thứ nếu thoát checkout
        localStorage.removeItem('selectedCoupon')
        localStorage.removeItem('checkoutProducts')

        selectedCoupon.value = null
        selectedProducts.value = []
        paymentMethod.value = 'COD'
        currentTab.value = 0
    }

    next()
})

</script>



<style scoped>
.checkout-page {
    font-family: 'Segoe UI', sans-serif;
}

.tab {
    flex: 1;
    padding: 1rem;
    background: #eee;
    text-align: center;
    border-radius: 10px;
    margin-right: 0.5rem;
    font-weight: 600;
    cursor: pointer;
}

.tab.active {
    background: #f38b4a;
    color: white;
}

.summary-box {
    background-color: #fffaf5;
}

.popup-success {
    position: fixed;
    inset: 0;
    background-color: rgba(0, 0, 0, 0.5);
    display: flex;
    align-items: center;
    justify-content: center;
}

.popup-content {
    background: white;
    padding: 2rem;
    border-radius: 1rem;
    max-width: 400px;
    width: 100%;
}
</style>
