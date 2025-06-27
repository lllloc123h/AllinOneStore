<template>


    <div class="container mt-5">
        <h3 class="mb-4">Top-Up Form</h3>
        <form @submit.prevent="momoTopUp">
            <div class="mb-3">
                <label for="amount" class="form-label">Amount</label>
                <input type="number" class="form-control" id="amount" v-model="topUpAmount" required min="10000" />
            </div>
            <div class="mb-3">
                <label for="paymentMethod" class="form-label">Payment Method</label>
                <select class="form-select" v-model="paymentMethod" required>
                    <option value="" disabled>Select a method</option>
                    <option value="momo" selected>MoMo</option>
                    <option value="bank">Bank Transfer</option>
                    <option value="card">Credit/Debit Card</option>
                </select>
            </div>
            <div class="button-holder">
                <button type="submit" class="btn btn-primary">
                    <i class="bi bi-wallet2 me-2"></i> Top Up
                </button>
            </div>
        </form>
    </div>
    <div v-if="payURL && showPopUp" class="popup-overlay" @click.self="closePopup">
        <div class="popup-iframe text-center">
            <h5 class="mb-3">MoMo Payment</h5>
            <a :href="payURL" target="_blank" class="btn btn-primary">Open Payment</a>
            <button class="btn btn-danger mt-3" @click="closePopup">Close</button>
        </div>
    </div>

</template>

<script setup>
import { ref } from 'vue'
import api from '../../Configs/api'

const topUpAmount = ref('')
const paymentMethod = ref('')
const message = ref('')
const payURL = ref('')
const showPopUp = ref(false)
function closePopup() {
    showPopUp.value = false
    payURL.value = ''
}

async function momoTopUp() {
    try {
        const response = await api.post(`/e-wallet/topup`, { userId: - 1, amount: topUpAmount.value })
        console.log(response.data)
        payURL.value = response.data;
        showPopUp.value = true
    } catch (err) {
        console.error()
    }

}
</script>

<style scoped>
.container {
    max-width: 500px;
}

.container {
    max-width: 500px;
    background: #f8f9fa;
    padding: 2rem;
    border-radius: 1rem;
    box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
}

h3 {
    font-weight: 600;
    color: #343a40;
    text-align: center;
}

.form-label {
    font-weight: 500;
    color: #495057;
}

.form-control,
.form-select {
    border-radius: 0.5rem;
    transition: all 0.3s ease;
}

.form-control:focus,
.form-select:focus {
    border-color: #6c63ff;
    box-shadow: 0 0 0 0.2rem rgba(108, 99, 255, 0.25);
}

.button-holder {
    width: 150px;
    margin: 0 auto;
}

.btn-primary {
    background: linear-gradient(135deg, #6c63ff, #8b79f5);
    border: none;
    border-radius: 0.5rem;
    padding: 0.5rem 1.5rem;
    font-weight: 600;

    transition: background 0.3s ease;
}

.btn-primary:hover {
    background: linear-gradient(135deg, #8b79f5, #6c63ff);
}

.alert {
    font-weight: 500;
    border-radius: 0.5rem;
    text-align: center;
}

.popup-overlay {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.5);
    display: flex;
    align-items: center;
    justify-content: center;
    z-index: 1000;
}

.popup-iframe {
    background: white;
    padding: 2rem;
    border-radius: 1rem;
    width: 90%;
    max-width: 700px;
    box-shadow: 0 10px 30px rgba(0, 0, 0, 0.3);
    text-align: center;
}

.popup-iframe iframe {
    width: 100%;
    height: 500px;
    border: none;
    border-radius: 0.5rem;
}
</style>
