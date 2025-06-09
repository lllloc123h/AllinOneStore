<template>
  <!-- Modal Overlay -->
  <transition name="fade">
    <div v-if="show" class="modal-overlay">
      <div class="modal-content">
        <button class="close-btn" @click="closeModal">×</button>
        <form @submit.prevent="submitOtp" class="form">
          <div class="title">Xác minh OTP</div>
          <p class="message">Chúng tôi vừa gửi 1 mã OTP tới mail của bạn</p>
          <div class="inputs">
            <input
              v-for="(digit, index) in otp"
              :key="index"
              v-model="otp[index]"
              maxlength="1"
              type="number"
              class="otp-input"
              :ref="(el) => (otpRefs[index] = el)"
              @input="onInput(index)"
              @keydown.backspace="onBackspace(index, $event)"
              @paste="onPaste($event)"
            />
          </div>
          <button class="action">Verify</button>
          <p class="resend-note">
            Không nhận được mail?
            <button
              type="button"
              class="resend-btn"
              @click="resendOtp"
              :disabled="countdown > 0"
            >
              {{ countdown > 0 ? `Gửi lại (${countdown}s)` : "Gửi lại" }}
            </button>
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
import api from "../../Configs/api";
import router from "../../router";

// countdown

const countdown = ref(0);
let timer = null;
const countdownTime = () => {
  countdown.value = 30;

  if (timer) clearInterval(timer);

  timer = setInterval(() => {
    if (countdown.value > 0) {
      countdown.value--;
    } else {
      clearInterval(timer);
    }
  }, 1000);
};
countdownTime();

const resendOtp = () => {
  // Gửi lại OTP logic ở đây
  toast.success("OTP đã được gửi lại");
  emit("resend");
  countdownTime();
};

const props = defineProps({
  show: Boolean,
});

const emit = defineEmits(["close", "verified", "resend"]);

const otp = ref(["", "", "", "", "", ""]);
const otpRefs = [];

// Focus input[0] when modal shows
watch(
  () => props.show,
  (val) => {
    if (val) {
      nextTick(() => otpRefs[0]?.focus());
    } else {
      otp.value = ["", "", "", "", "", ""];
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
  if (/^\d{6}$/.test(paste)) {
    for (let i = 0; i < 6; i++) {
      otp.value[i] = paste[i];
    }
    nextTick(() => otpRefs[5]?.focus());
  }
  e.preventDefault();
};

const submitOtp = () => {
  const code = otp.value.join("");
  if (code.length < 6) {
    toast.error("OTP phải gồm 6 chữ số");
    return;
  }
  api
    .post("/Accounts/verify-otp", { otp: code })
    .then((resp) => {
      toast.success(resp.data.message);
      emit("verified");
    })
    .catch((error) => toast.error(error.response.data.message));
};

const closeModal = () => {
  emit("close");
};
</script>

<style scoped>
.close-btn {
  position: absolute;
  top: 10px;
  right: 10px;
  width: 30px;
  height: 30px;
  border: none;
  border-radius: 50%;
  background-color: transparent;
  color: #333;
  font-size: 20px;
  cursor: pointer;
  transition: background-color 0.2s ease;
}

.close-btn:hover {
  background-color: #eee;
}

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
  background-color: #fff2eb;
  color: black;
  border: 1px solid black;
  border-radius: 8px;
  cursor: pointer;
}
.action:hover {
  background-color: #edcdbb;
  border-color: #edcdbb;
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
