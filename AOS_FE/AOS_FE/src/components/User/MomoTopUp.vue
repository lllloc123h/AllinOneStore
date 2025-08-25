<template>


    <div class="container mt-5">
        <h3 class="mb-4">Top-Up Form</h3>
        <div class="mb-3">
            <label for="amount" class="form-label">Amount</label>
            <input type="number" class="form-control" id="amount" v-model="topUpAmount" required min="10000"
                max="500000" />
        </div>
        <div>
            <p>Your amount is too small. Please choose:</p>
            <button class="btn btn-outline-primary" @click="() => (topUpAmount = topUpAmount * 1000)">{{ topUpAmount
                * 1000 }}</button>
            <button class="btn btn-outline-primary" @click="() => (topUpAmount = topUpAmount * 100000)">{{
                topUpAmount
                * 100000 }}</button>
        </div>
        <div class="mb-3">
            <label class="form-label">Payment Method</label>
            <div class="d-flex gap-3">
                <button type="button" class="btn"
                    :class="paymentMethod === 'momo' ? 'btn-outline-primary' : 'btn-outline-secondary'"
                    @click="paymentMethod = 'momo'">
                    <i class="bi bi-phone" style="font-size: 1.5rem;"></i><br />
                    MoMo
                </button>

                <button type="button" class="btn"
                    :class="paymentMethod === 'bank' ? 'btn-outline-primary' : 'btn-outline-secondary'"
                    @click="paymentMethod = 'bank'">
                    <i class="bi bi-bank" style="font-size: 1.5rem;"></i><br />
                    Bank
                </button>

                <button type="button" class="btn"
                    :class="paymentMethod === 'card' ? 'btn-outline-primary' : 'btn-outline-secondary'"
                    @click="paymentMethod = 'card'">
                    <i class="bi bi-credit-card" style="font-size: 1.5rem;"></i><br />
                    Card
                </button>
            </div>
        </div>

        <div class="button-holder">
            <button type="submit" class="btn btn-primary" @click="momoTopUp">
                <i class="bi bi-wallet2 me-2"></i> Top Up
            </button>
        </div>
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
const showAmountOptions = ref(false);
function closePopup() {
    showPopUp.value = false
    payURL.value = ''
}
function submitAmount(selectedAmount) {
    api.post(`/e-wallet/topup`, {
        userId: -1,
        amount: selectedAmount
    }).then((res) => {
        payURL.value = res.data.payUrl;
        showPopUp.value = true;
        showAmountOptions.value = false;
    });
}
async function momoTopUp() {
    try {
        const response = await api.post(`/e-wallet/topup`, { userId: - 1, amount: topUpAmount.value })
        console.log(response.data.payUrl)
        payURL.value = response.data.payUrl;
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

.container {
    max-width: 500px;
    margin: auto;
    padding: 20px 25px;
    background: #f9f9f9;
    border-radius: 12px;
    box-shadow: 0 8px 24px rgba(0, 0, 0, 0.1);
}

h3 {
    text-align: center;
    font-weight: 600;
    color: #333;
}

.form-label {
    font-weight: 500;
    color: #555;
}

.form-control,
.form-select {
    border: 1px solid #ccc;
    border-radius: 8px;
    padding: 10px 12px;
    font-size: 15px;
    transition: border 0.2s ease;
}

.form-control:focus,
.form-select:focus {
    border-color: #007bff;
    box-shadow: none;
}

.button-holder {
    display: flex;
    justify-content: center;
    margin-top: 20px;
}

.btn-primary {
    background-color: #007bff;
    border-color: #007bff;
    font-weight: 500;
    padding: 10px 20px;
    border-radius: 8px;
    display: flex;
    align-items: center;
}

.btn-primary:hover {
    background-color: #0056b3;
    border-color: #004b9d;
}
</style>
