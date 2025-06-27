<template>
    <div class="container mt-5 wallet-info">
        <h3 class="text-center mb-4">User Wallet Information</h3>

        <div class="card p-4">
            <p><strong>Wallet Owner:</strong> {{ wallet.accounts?.fullname || "" }}</p>
            <p><strong>Wallet ID:</strong> {{ wallet.id }}</p>
            <p><strong>Balance:</strong> {{ wallet.balance }} VND</p>
        </div>
        <div class="button-holder">
            <button @click="MomoTopUp()" class="btn btn-primary">
                <i class="bi bi-wallet2 me-2"></i> Top Up
            </button>
        </div>
        <h5 class="mt-4">Transaction History</h5>
        <ul class="list-group">
            <li class="list-group-item" v-for="(tx, index) in wallet.transactions" :key="index">
                {{ formatDate(tx.date) }} - {{ tx.type }}: {{ tx.amount.toLocaleString() }} VND
            </li>
        </ul>
    </div>

</template>

<script setup>
import { onMounted } from 'vue'
import { ref } from 'vue'
import api from '../../Configs/api'
import { useRouter } from 'vue-router'
const wallet = ref({})
const router = useRouter()
function formatDate(dateStr) {
    const date = new Date(dateStr)
    return date.toLocaleString('vi-VN')
}
async function fetchInfor() {
    try {
        const response = await api.get(`EWallets`)
        console.log(response.data)
        wallet.value = response.data
    } catch (error) {
        console.error()
    }
}
async function MomoTopUp() {
    router.push({
        name: "momotopup"
    })
}
onMounted(async () => {
    fetchInfor()
});
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
