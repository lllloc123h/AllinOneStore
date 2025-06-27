<template>
    <div class="container mt-4">
        <h2>Ví của tôi</h2>
        <p class="text-muted">Quản lý số dư và giao dịch của bạn</p>

        <div class="row text-center mb-4">
            <div class="col-md-4">
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

        <!-- Thông tin ví -->
        <div class="card mb-4">
            <div class="card-header">Tổng quan ví</div>
            <div class="card-body row">
                <div class="col-md-6">
                    <p><strong>ID Ví:</strong> {{ wallet.id }}</p>
                    <p><strong>Trạng thái:</strong>
                        <span class="badge bg-success" v-if="wallet.status === 'active'">Đang hoạt động</span>
                    </p>
                    <p><strong>Ngày tạo:</strong> {{ formatDate(wallet.createdAt) }}</p>
                    <p><strong>Cập nhật lần cuối:</strong> {{ formatDate(wallet.updatedAt) }}</p>
                </div>

                <div class="col-md-6">
                    <h6>Giao dịch gần đây</h6>
                    <ul class="list-group">
                        <li class="list-group-item" v-for="(txn, index) in recentTransactions" :key="index">
                            <div class="d-flex justify-content-between">
                                <span>{{ txn.description }} ({{ formatDate(txn.date) }})</span>
                                <span> {{ txn.status === 'PENDING' ? 'Giao dịch quá hạn' : "Nạp tiền thành công" }}</span>
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

const wallet = ref({})
const totalTopup = ref(0)
const totalSpent = ref(0)
const recentTransactions = ref([])
const topupUpdated = ref('')
const spentUpdated = ref('')

const router = useRouter()

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
        console.error("Lỗi khi lấy dữ liệu ví:", error)
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
</style>
