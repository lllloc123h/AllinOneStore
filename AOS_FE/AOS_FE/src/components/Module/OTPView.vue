<template>
  <!-- Modal Overlay -->
  <transition name="fade">
    <div v-if="show" class="modal-overlay">
      <div class="modal-content">
        <form @submit.prevent="submitOtp" class="form">
          <div class="title">OTP Verification</div>
          <p class="message">We’ve sent a verification code to your phone</p>
          <div class="inputs">
            <input
              v-for="(digit, index) in otp"
              :key="index"
              v-model="otp[index]"
              maxlength="1"
              type="text"
              class="otp-input"
              :ref="(el) => (otpRefs[index] = el)"
              @input="onInput(index)"
              @keydown.backspace="onBackspace(index, $event)"
              @paste="onPaste($event)"
            />
          </div>
          <button class="action">Verify</button>
          <p class="resend-note">
            Didn’t receive the code?
            <button type="button" class="resend-btn" @click="resendOtp">Resend</button>
          </p>
        </form>
      </div>
    </div>
  </transition>
</template>

<script setup>
import { ref, nextTick, watch } from "vue";
import { toast } from "vue3-toastify";
import "vue3-toastify/dist/index.css";

const props = defineProps({
  show: Boolean,
});

const emit = defineEmits(["close", "verified", "resend"]);

const otp = ref(["", "", "", "", ""]);
const otpRefs = [];

// Focus input[0] when modal shows
watch(
  () => props.show,
  (val) => {
    if (val) {
      nextTick(() => otpRefs[0]?.focus());
    } else {
      otp.value = ["", "", "", "", ""];
    }
  }
);

const onInput = (index) => {
  if (otp.value[index].length > 1) {
    otp.value[index] = otp.value[index][0];
  }
  if (otp.value[index] && index < otp.value.length - 1) {
    otpRefs[index + 1]?.focus();
  }
};

const onBackspace = (index, e) => {
  if (otp.value[index] === "" && index > 0) {
    otpRefs[index - 1]?.focus();
  }
};

const onPaste = (e) => {
  const paste = e.clipboardData.getData("text").trim();
  if (/^\d{5}$/.test(paste)) {
    for (let i = 0; i < 5; i++) {
      otp.value[i] = paste[i];
    }
    nextTick(() => otpRefs[4]?.focus());
  }
  e.preventDefault();
};

const submitOtp = () => {
  const code = otp.value.join("");
  if (code.length < 5) {
    toast.error("OTP phải gồm 5 chữ số");
    return;
  }
  toast.success("OTP xác thực thành công!");
  emit("verified", code);
};

const resendOtp = () => {
  toast.info("OTP mới đã được gửi!");
  emit("resend");
};
</script>

<style scoped>
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}
.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
.fade-enter-to,
.fade-leave-from {
  opacity: 1;
}

.modal-overlay {
  position: fixed;
  inset: 0;
  background: rgba(0, 0, 0, 0.6);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 9999;
}

.modal-content {
  background: #fff;
  padding: 2rem;
  border-radius: 12px;
  width: 400px;
  text-align: center;
  animation: scaleUp 0.3s ease;
}

@keyframes scaleUp {
  from {
    transform: scale(0.95);
    opacity: 0;
  }
  to {
    transform: scale(1);
    opacity: 1;
  }
}

.form {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.title {
  font-size: 20px;
  font-weight: bold;
}

.message {
  color: #666;
  font-size: 14px;
}

.inputs {
  display: flex;
  justify-content: center;
  gap: 10px;
}

.otp-input {
  width: 40px;
  height: 48px;
  text-align: center;
  border: 1px solid #ccc;
  border-radius: 6px;
  font-size: 20px;
  transition: 0.2s;
}
.otp-input:focus {
  border-color: royalblue;
  outline: none;
  transform: scale(1.1);
}

.action {
  padding: 10px;
  background: royalblue;
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
}

.resend-note {
  font-size: 13px;
}
.resend-btn {
  background: none;
  border: none;
  color: royalblue;
  cursor: pointer;
  font-weight: bold;
}
</style>
