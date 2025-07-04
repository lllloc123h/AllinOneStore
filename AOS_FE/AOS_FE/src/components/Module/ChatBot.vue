<template>
  <div class="position-fixed bottom-0 end-0 m-4" style="z-index: 9999">
    <!-- Nút bật/tắt -->
    <div class="chat-toggle" @click="toggleOpen">
      <i :class="open ? 'bi bi-x-lg' : 'bi bi-chat-heart-fill'"></i>
    </div>

    <!-- Khung chat -->
    <div
      v-show="open"
      class="chat-window shadow-lg rounded-4 bg-white overflow-hidden animate__animated animate__fadeInUp mt-2"
    >
      <div
        class="chat-header text-black px-3 py-2 fw-semibold"
        style="background-color: rgb(255, 191, 0)"
      >
        🤖 Trợ lý ảo
      </div>

      <div class="chat-body p-3" ref="chatContainer">
        <div v-for="(msg, index) in messages" :key="index" class="mb-3">
          <!-- <i
            :class="msg.from === 'user' ? 'bi bi-chat-heart-fill' : 'bi bi-person-circle'"
          ></i> -->
          <div
            style="background-color: beige"
            :class="[
              'p-2 rounded-3',
              msg.from === 'user'
                ? 'bg-light shadow-sm text-end ms-auto w-75'
                : 'shadow-sm text-black w-75',
            ]"
          >
            {{ msg.text }}
          </div>
        </div>

        <!-- Spinner hiển thị như bot đang trả lời -->
        <div
          v-if="loading"
          class="mb-3 w-75 bg-primary text-white p-2 rounded-3 d-flex align-items-center gap-2"
        >
          <div class="spinner-border spinner-border-sm text-light" role="status"></div>
          <span>Đang nhập...</span>
        </div>
      </div>

      <div class="chat-footer border-top px-3 py-2">
        <form @submit.prevent="sendMessage" class="d-flex gap-2">
          <input
            v-model="input"
            type="text"
            class="form-control rounded-pill"
            placeholder="Nhập tin nhắn..."
          />
          <button
            class="btn btn-primary border-0 text-black rounded-pill px-3"
            type="submit"
            :disabled="!input.trim()"
            style="background-color: rgb(255, 191, 0)"
          >
            Gửi
          </button>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, nextTick } from "vue";

const open = ref(false);
const input = ref("");
const loading = ref(false);
const messages = ref([
  { from: "bot", text: "Xin chào! Tôi có thể giúp gì cho bạn hôm nay?" },
]);
const chatContainer = ref(null);

const scrollToBottom = () => {
  nextTick(() => {
    chatContainer.value.scrollTop = chatContainer.value.scrollHeight;
  });
};

const sendMessage = () => {
  const text = input.value.trim();
  if (!text) return;

  messages.value.push({ from: "user", text });
  input.value = "";
  loading.value = true;
  scrollToBottom();

  setTimeout(() => {
    messages.value.push({
      from: "bot",
      text: `Bạn vừa nói: "${text}". Tôi sẽ xử lý ngay!`,
    });
    loading.value = false;
    scrollToBottom();
  }, 1200);
};

const toggleOpen = () => {
  open.value = !open.value;
  scrollToBottom();
};
</script>

<style scoped>
:focus {
  outline: none !important;
  box-shadow: none !important;
}
.chat-toggle {
  color: white;
  background-color: rgb(255, 191, 0);
  width: 48px;
  height: 48px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  font-size: 1.25rem;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

.chat-window {
  width: 360px;
  height: 480px;
  display: flex;
  flex-direction: column;
  border-radius: 1rem;
}

.chat-body {
  flex: 1;
  overflow-y: auto;
  background-color: #f9f9f9;
}
</style>
