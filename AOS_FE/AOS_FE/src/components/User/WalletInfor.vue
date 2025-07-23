<template>
    <div class="container mt-4">
        <h2>Ví của tôi</h2>
        <p class="text-muted">Quản lý số dư và giao dịch của bạn</p>

        <div class="row text-center mb-4">
            <div v-if="walletNotFound" class="col-md-4 text-center">
                <p class="text-danger">Bạn chưa có ví điện tử.</p>

                <button class="btn btn-primary" @click="showModal = true">Tạo ví mới</button>
            </div>

            <div v-else="walletNotFound" class="col-md-4">
                <div class="card shadow-sm p-3">
                    <h4 class="text-primary">{{ formatCurrency(wallet.balance || 0) }}</h4>
                    <button class="btn btn-outline-primary btn-sm mt-2" @click="MomoTopUp">+ Nạp tiền</button>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card shadow-sm p-3">
                    <h5>Tổng nạp</h5>
                    <p class="text-success">{{ formatCurrency(totalTopup) }}</p>
                    <small>Cập nhật: {{ topupUpdated }}</small>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card shadow-sm p-3">
                    <h5>Tổng chi tiêu</h5>
                    <p class="text-danger">{{ formatCurrency(totalSpent) }}</p>
                    <small>Cập nhật: {{ spentUpdated }}</small>
                </div>
            </div>
        </div>
        <div v-if="showModal" class="modal d-block" tabindex="-1">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Tạo ví mới</h5>
                        <button type="button" class="btn-close" @click="showModal = false"></button>
                    </div>
                    <div class="modal-body">
                        <input type="text" v-model="formDataWallet.id" class="form-control mb-3"
                            placeholder="Nhập ID ví (tùy chọn)">
                        <p>Bạn có chắc chắn muốn tạo ví mới?</p>
                    </div>
                    <div class="modal-footer">
                        <button class="btn btn-secondary" @click="showModal = false">Hủy</button>
                        <button class="btn btn-primary" @click="createWallet">Tạo ví</button>
                    </div>
                </div>
            </div>
        </div>
        <!-- Thông tin ví -->
        <div class="card mb-4">
            <div class="card-header">Tổng quan ví</div>
            <div class="card-body row">
                <div class="col-md-6">
                    <p><strong>ID Ví:</strong> {{ wallet.id }}</p>
                    <p><strong>Trạng thái:</strong>
                        <span class="badge" :class="wallet.active === true ? 'bg-success' : 'bg-warning'">
                            {{ wallet.active === true ? 'Đang hoạt động' : 'Chưa xác minh' }}
                        </span>
                    </p>

                    <!-- Show verify button if not active -->
                    <div v-if="wallet.active === false">
                        <button class="btn btn-outline-primary btn-sm mt-2" @click="showVerifyModal = true">
                            Xác minh ví
                        </button>
                    </div>
                    <p><strong>Ngày tạo:</strong> {{ formatDate(wallet.createdAt) }}</p>
                    <p><strong>Cập nhật lần cuối:</strong> {{ formatDate(wallet.updatedAt) }}</p>
                </div>
                <!-- Verify Modal -->
                <div v-if="showVerifyModal" class="modal d-block" tabindex="-1">
                    <div class="modal-dialog modal-dialog-centered">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title">Xác minh ví</h5>
                                <button type="button" class="btn-close" @click="showVerifyModal = false"></button>
                            </div>
                            <div class="modal-body">
                                <p>Nhập mã xác minh đã gửi đến email/SMS:</p>
                                <input type="text" v-model="verifyCode" class="form-control"
                                    placeholder="Mã xác minh" />
                            </div>
                            <div class="modal-footer">
                                <button class="btn btn-secondary" @click="showVerifyModal = false">Hủy</button>
                                <button class="btn btn-warning" :disabled="timeLineOTP > 0" @click="ReSendOTP">
                                    Gửi lại OTP
                                    <span v-if="timeLineOTP > 0">({{ timeLineOTP }}s)</span>
                                </button>
                                <button class="btn btn-primary" @click="verifyWallet">Xác minh</button>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-md-6">
                    <h6>Giao dịch gần đây</h6>
                    <ul class="list-group">
                        <li class="list-group-item" v-for="(txn, index) in recentTransactions" :key="index">
                            <div class="d-flex justify-content-between">
                                <span>{{ txn.description }} ({{ formatDate(txn.date) }})</span>
                                <span> {{ txn.status === 'PENDING' ? 'Giao dịch quá hạn' : "Nạp tiền thành công"
                                }}</span>
                                <strong :class="txn.amount > 0 ? 'text-success' : 'text-danger'">
                                    {{ formatCurrency(txn.amount) }}
                                </strong>
                            </div>
                        </li>
                    </ul>
                    <a href="#" class="btn btn-link mt-2">Xem tất cả giao dịch</a>
                </div>
            </div>
        </div>

        <!-- Ghi chú -->
        <div class="alert alert-info">
            <ul class="mb-0">
                <li>Số dư được sử dụng để thanh toán dịch vụ trên nền tảng.</li>
                <li>Giao dịch nạp tiền thường xử lý trong 24 giờ làm việc.</li>
                <li>Liên hệ nếu bạn cần hỗ trợ về các vấn đề liên quan đến ví.</li>
            </ul>
        </div>
    </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import api from '../../Configs/api'
import { notification } from "ant-design-vue";
const wallet = ref({})
const totalTopup = ref(0)
const totalSpent = ref(0)
const recentTransactions = ref([])
const topupUpdated = ref('')
const spentUpdated = ref('')
const walletNotFound = ref(false)
const router = useRouter()
const showModal = ref(false)
const showVerifyModal = ref(false)
const verifyCode = ref('')
const timeLineOTP = ref(0);
let otpTimer = null;
const formDataWallet = ref(
    {
        id: '',
        createdAt: '',
        accounts: '',
        walletType: 'REAL',
        isActive: false,
        codeActivce: ''
    }
)
async function verifyWallet() {
    if (!verifyCode.value) {
        notification.warning({
            message: "Thiếu mã",
            description: "Vui lòng nhập mã xác minh.",
        })
        return
    }

    try {
        const response = await api.post(`/user/VerifyEWallets`, {
            codeActivce: verifyCode.value,
            accounts: ''
        })
        wallet.value = response.data
        showVerifyModal.value = false
        verifyCode.value = ''

        notification.success({
            message: "Thành công",
            description: "Ví đã được xác minh thành công.",
        })
    } catch (error) {
        console.error('Lỗi xác minh:', error)
        notification.error({
            message: "Lỗi",
            description: "Mã xác minh không hợp lệ hoặc đã hết hạn.",
        })
    }
}
async function createWallet() {
    try {
        if (!formDataWallet.value.id || !/^[a-zA-Z0-9_-]+$/.test(formDataWallet.value.id)) {
            notification.error({
                message: "Lỗi",
                description: `ID KHÔNG HỢP LỆ. Vui lòng nhập ID chỉ chứa chữ cái, số, dấu gạch ngang hoặc gạch dưới.`,
            });
            return
        }
        const response = await api.post(`/user/EWallets`, { ...formDataWallet.value }) // adjust body if needed
        wallet.value = response.data
        walletNotFound.value = false
        showModal.value = false
    } catch (error) {
        console.error('Lỗi khi tạo ví:', error)
        alert('Không thể tạo ví. Vui lòng thử lại.')
    }
}
function ReSendOTP() {
    if (timeLineOTP.value > 0) return;
    try {
        api.get(`/user/resendOTP`);
        timeLineOTP.value = 60;
        otpTimer = setInterval(() => {
            if (timeLineOTP.value > 0) {
                timeLineOTP.value--;
            } else {
                clearInterval(otpTimer);
            }
        }, 1000);
    }
    catch (error) {
        console.error('Lỗi khi gửi lại OTP:', error)
        notification.error({
            message: "Lỗi",
            description: "Không thể gửi lại mã xác minh. Vui lòng thử lại sau.",
        })
    }
}
function formatDate(dateStr) {
    if (!dateStr) return ''
    const date = new Date(dateStr)
    return date.toLocaleString('vi-VN')
}

function formatCurrency(amount) {
    return new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND',
    }).format(amount)
}

async function fetchInfor() {
    try {
        const response = await api.get(`EWallets`)
        const transactionResponse = await api.get(`EWalletTransactions`)
        walletNotFound.value = false
        showVerifyModal.value = !response.data.active
        const transactions = transactionResponse.data
        totalTopup.value = transactions
            .filter(txn => txn.transactionType === 'TOP_UP')
            .reduce((sum, txn) => sum + txn.amount, 0)
        topupUpdated.value = formatDate(new Date())
        totalSpent.value = transactions
            .filter(txn => txn.transactionType === 'SPENT')
            .reduce((sum, txn) => sum + txn.amount, 0)
        spentUpdated.value = formatDate(new Date())

        recentTransactions.value = transactions.slice(0, 5).map(txn => ({
            description: txn.description || txn.transactionType,
            amount: txn.amount,
            date: txn.createdAt,
        }))
        // console.log(response.data)
        // console.log(transactionResponse.data)
        wallet.value = response.data
    } catch (error) {
        if (error.response && error.response.data.message === "404") {
            walletNotFound.value = true
        } else {
            console.error("Lỗi khi lấy dữ liệu ví:", error)
        }
    }
}

function MomoTopUp() {
    router.push({ name: "momotopup" })
}

onMounted(fetchInfor)
</script>

<style scoped>
.wallet-info {
    max-width: 600px;
}

.card {
    background: #f8f9fa;
    border-radius: 12px;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
}

.list-group-item {
    font-weight: 500;
}

.modal {
    background-color: rgba(0, 0, 0, 0.4);
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    z-index: 1050;
}

.modal-dialog {
    margin: auto;
}
</style>
