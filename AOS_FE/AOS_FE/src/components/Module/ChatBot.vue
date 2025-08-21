<template>
  <div class="chatbot-container">
    <!-- Chat Toggle Button -->
    <div class="chat-toggle" @click="toggleOpen" :class="{ active: open }">
      <div class="toggle-icon">
        <i v-if="!open" class="bi bi-chat-dots-fill"></i>
        <i v-else class="bi bi-x-lg"></i>
      </div>
      <div v-if="!open && hasNewMessage" class="notification-dot"></div>
    </div>

    <!-- Chat Window -->
    <transition name="chat-slide">
      <div v-if="open" class="chat-window">
        <!-- Chat Header -->
        <div class="chat-header">
          <div class="header-content">
            <div class="bot-avatar">
              <i class="bi bi-robot"></i>
            </div>
            <div class="header-info">
              <h6 class="bot-name">AOS Assistant</h6>
              <p class="bot-status">
                <span class="status-dot"></span>
                Đang hoạt động
              </p>
            </div>
          </div>
          <div class="header-actions">
            <button class="minimize-btn" @click="toggleOpen">
              <i class="bi bi-dash-lg"></i>
            </button>
          </div>
        </div>

        <!-- Chat Messages -->
        <div class="chat-body" ref="chatContainer">
          <div class="messages-container">
            <!-- Welcome Message -->
            <div class="welcome-message">
              <div class="welcome-avatar">
                <i class="bi bi-stars"></i>
              </div>
              <div class="welcome-text">
                <h6>Chào mừng đến với AOS Fashion! 👋</h6>
                <p>
                  Tôi là trợ lý ảo, sẵn sàng hỗ trợ bạn về sản phẩm, đơn hàng và mọi thắc
                  mắc khác.
                </p>
              </div>
            </div>

            <!-- Chat Messages -->
            <div v-for="(msg, index) in messages" :key="index" class="message-wrapper">
              <div
                :class="['message', msg.from === 'user' ? 'user-message' : 'bot-message']"
              >
                <div v-if="msg.from === 'bot'" class="message-avatar">
                  <i class="bi bi-robot"></i>
                </div>
                <div class="message-content">
                  <div class="message-bubble" v-html="msg.text"></div>
                  <div class="message-time">
                    {{ formatTime(msg.timestamp || new Date()) }}
                  </div>
                </div>
              </div>
            </div>

            <!-- Loading Indicator -->
            <div v-if="loading" class="message bot-message">
              <div class="message-avatar">
                <i class="bi bi-robot"></i>
              </div>
              <div class="message-content">
                <div class="message-bubble typing-indicator">
                  <div class="typing-dots">
                    <span></span>
                    <span></span>
                    <span></span>
                  </div>
                  <span class="typing-text">Đang soạn tin...</span>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- Quick Actions -->
        <div class="quick-actions" v-if="!loading">
          <!-- <div class="quick-action-chips">
            <button
              v-for="action in quickActions"
              :key="action.id"
              class="quick-chip"
              @click="sendQuickMessage(action.message)"
            >
              <i :class="action.icon"></i>
              {{ action.text }}
            </button>
          </div> -->
          <div class="quick-action-divider">
            <select name="" id="" v-model="selectedColor">
              <option value="">Chọn màu sắc</option>
              <option
                v-for="color in listColor"
                :key="color.id"
                :value="color.description"
              >
                {{ color.description }}
              </option>
            </select>
            <select style="margin-left: 10px" v-model="selectedSize">
              <option value="">Chọn size</option>
              <option v-for="size in listSize" :key="size.id" :value="size.description">
                {{ size.description }}
              </option>
            </select>
          </div>
        </div>

        <!-- Chat Input -->
        <div class="chat-footer">
          <form @submit.prevent="sendMessage" class="message-form">
            <div class="input-container">
              <input
                v-model="input"
                type="text"
                class="message-input"
                placeholder="Nhập tin nhắn của bạn..."
                :disabled="loading"
              />
              <button
                type="submit"
                class="send-button"
                :disabled="!input.trim() || loading"
              >
                <i class="bi bi-send-fill"></i>
              </button>
            </div>
          </form>
        </div>
      </div>
    </transition>
  </div>
</template>

<script setup>
import { ref, nextTick, onMounted, watch } from "vue";
import api from "../../Configs/api";

const open = ref(false);
const input = ref("");
const loading = ref(false);
const hasNewMessage = ref(false);
const messages = ref([]);
const chatContainer = ref(null);
const COLOR = "Màu sắc";
const SIZE = "Kích thước";
const listColor = ref([]);
const listSize = ref([]);
const selectedColor = ref("");
const selectedSize = ref("");
onMounted(async () => {
  try {
    const colorData = await api.get("/VariantValues/values?name=" + COLOR);
    const sizeData = await api.get("/VariantValues/values?name=" + SIZE);
    console.log(colorData);
    console.log(sizeData);
    listColor.value = colorData.data;
    listSize.value = sizeData.data;
  } catch (error) {
    console.error("Error fetching variant values:", error);
  }
});
// Quick action buttons
const quickActions = ref([
  {
    id: 1,
    text: "Sản phẩm",
    message: "Cho tôi xem các sản phẩm mới nhất",
    icon: "bi bi-bag-heart",
  },
  {
    id: 2,
    text: "Giá cả",
    message: "Thông tin về giá sản phẩm",
    icon: "bi bi-tags",
  },
  {
    id: 3,
    text: "Hỗ trợ",
    message: "Tôi cần hỗ trợ",
    icon: "bi bi-headset",
  },
]);

const scrollToBottom = () => {
  nextTick(() => {
    if (chatContainer.value) {
      chatContainer.value.scrollTop = chatContainer.value.scrollHeight;
    }
  });
};

const formatTime = (date) => {
  return new Date(date).toLocaleTimeString("vi-VN", {
    hour: "2-digit",
    minute: "2-digit",
  });
};

const sendMessage = () => {
  const text = input.value.trim();
  if (!text || loading.value) return;

  // Add user message
  messages.value.push({
    from: "user",
    text,
    timestamp: new Date(),
  });

  input.value = "";
  loading.value = true;
  scrollToBottom();

  // Simulate API call with delay for better UX
  setTimeout(() => {
    api
      .post("/openai/chat", { message: text })
      .then((response) => {
        messages.value.push({
          from: "bot",
          text: response.data.replace(/\n/g, "<br>"),
          timestamp: new Date(),
        });
        hasNewMessage.value = !open.value;
      })
      .catch((error) => {
        messages.value.push({
          from: "bot",
          text: "Xin lỗi, tôi đang gặp sự cố kỹ thuật. Vui lòng thử lại sau! 😔",
          timestamp: new Date(),
        });
        console.error("Error sending message:", error);
      })
      .finally(() => {
        loading.value = false;
        scrollToBottom();
      });
  }, 800);
};

const sendQuickMessage = (message) => {
  input.value = message;
  sendMessage();
};

const toggleOpen = () => {
  selectedColor.value = "";
  selectedSize.value = "";
  open.value = !open.value;
  hasNewMessage.value = false;

  if (open.value) {
    scrollToBottom();
  }
};

// Initialize with welcome message
onMounted(() => {
  // Auto-open after 3 seconds if no interaction
  setTimeout(() => {
    if (!open.value && messages.value.length === 0) {
      hasNewMessage.value = true;
    }
  }, 3000);
});
</script>

<style scoped>
/* Select Box đồng bộ giao diện chatbot */
.quick-action-divider {
  margin-top: 12px;
  display: flex;
  justify-content: flex-start;
  padding-left: 0; /* bỏ padding */
}

.quick-action-divider select {
  appearance: none;
  -webkit-appearance: none;
  -moz-appearance: none;

  background: #f8f9fa;
  border: 1px solid #e9ecef;
  border-radius: 20px;
  padding: 8px 32px 8px 12px;

  font-size: 13px;
  color: #2c3e50;
  cursor: pointer;

  transition: all 0.2s ease;
  outline: none;

  /* Custom arrow */
  background-image: url("data:image/svg+xml;utf8,<svg fill='%23667eea' height='20' viewBox='0 0 24 24' width='20' xmlns='http://www.w3.org/2000/svg'><path d='M7 10l5 5 5-5z'/></svg>");
  background-repeat: no-repeat;
  background-position: right 10px center;
  background-size: 14px;
}

.quick-action-divider select:hover {
  border-color: #667eea;
}

.quick-action-divider select:focus {
  border-color: #667eea;
  box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.15);
}

.chatbot-container {
  position: fixed;
  bottom: 20px;
  right: 20px;
  z-index: 9999;
  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, sans-serif;
}

/* Chat Toggle Button */
.chat-toggle {
  position: relative;
  width: 60px;
  height: 60px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  box-shadow: 0 8px 25px rgba(102, 126, 234, 0.4);
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  color: white;
  border: 3px solid rgba(255, 255, 255, 0.2);
}

.chat-toggle:hover {
  transform: translateY(-2px) scale(1.05);
  box-shadow: 0 12px 35px rgba(102, 126, 234, 0.5);
}

.chat-toggle.active {
  background: linear-gradient(135deg, #e74c3c 0%, #c0392b 100%);
  box-shadow: 0 8px 25px rgba(231, 76, 60, 0.4);
}

.toggle-icon {
  font-size: 24px;
  transition: transform 0.3s ease;
}

.chat-toggle:hover .toggle-icon {
  transform: scale(1.1);
}

.notification-dot {
  position: absolute;
  top: 8px;
  right: 8px;
  width: 12px;
  height: 12px;
  background: #e74c3c;
  border-radius: 50%;
  border: 2px solid white;
  animation: pulse 2s infinite;
}

@keyframes pulse {
  0% {
    transform: scale(1);
    opacity: 1;
  }
  50% {
    transform: scale(1.2);
    opacity: 0.7;
  }
  100% {
    transform: scale(1);
    opacity: 1;
  }
}

/* Chat Window */
.chat-window {
  position: absolute;
  bottom: 80px;
  right: 0;
  width: 580px;
  height: 720px;
  background: white;
  border-radius: 20px;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.15);
  display: flex;
  flex-direction: column;
  overflow: hidden;
  border: 1px solid rgba(0, 0, 0, 0.1);
}

/* Chat Header */
.chat-header {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  padding: 20px;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.header-content {
  display: flex;
  align-items: center;
  gap: 12px;
}

.bot-avatar {
  width: 40px;
  height: 40px;
  background: rgba(255, 255, 255, 0.2);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 18px;
}

.header-info {
  flex: 1;
}

.bot-name {
  margin: 0;
  font-size: 16px;
  font-weight: 600;
}

.bot-status {
  margin: 0;
  font-size: 12px;
  color: rgba(255, 255, 255, 0.8);
  display: flex;
  align-items: center;
  gap: 6px;
}

.status-dot {
  width: 8px;
  height: 8px;
  background: #2ecc71;
  border-radius: 50%;
  animation: blink 2s infinite;
}

@keyframes blink {
  0%,
  50% {
    opacity: 1;
  }
  51%,
  100% {
    opacity: 0.3;
  }
}

.minimize-btn {
  background: rgba(255, 255, 255, 0.2);
  border: none;
  color: white;
  width: 32px;
  height: 32px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: background 0.2s ease;
}

.minimize-btn:hover {
  background: rgba(255, 255, 255, 0.3);
}

/* Chat Body */
.chat-body {
  flex: 1;
  overflow-y: auto;
  background: #f8f9fa;
  padding: 20px;
}

.messages-container {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

/* Welcome Message */
.welcome-message {
  display: flex;
  gap: 12px;
  padding: 16px;
  background: linear-gradient(135deg, #e3f2fd 0%, #f3e5f5 100%);
  border-radius: 16px;
  border: 1px solid rgba(102, 126, 234, 0.1);
}

.welcome-avatar {
  width: 36px;
  height: 36px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-size: 14px;
  flex-shrink: 0;
}

.welcome-text h6 {
  margin: 0 0 6px 0;
  font-size: 14px;
  font-weight: 600;
  color: #2c3e50;
}

.welcome-text p {
  margin: 0;
  font-size: 12px;
  color: #6c757d;
  line-height: 1.4;
}

/* Messages */
.message-wrapper {
  display: flex;
  flex-direction: column;
}

.message {
  display: flex;
  gap: 10px;
  margin-bottom: 12px;
}

.user-message {
  flex-direction: row-reverse;
}

.message-avatar {
  width: 32px;
  height: 32px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-size: 12px;
  flex-shrink: 0;
}

.message-content {
  max-width: 75%;
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.user-message .message-content {
  align-items: flex-end;
}

.message-bubble {
  padding: 12px 16px;
  border-radius: 18px;
  font-size: 14px;
  line-height: 1.4;
  word-wrap: break-word;
}

.bot-message .message-bubble {
  background: white;
  color: #2c3e50;
  border: 1px solid #e9ecef;
  border-bottom-left-radius: 6px;
}

.user-message .message-bubble {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border-bottom-right-radius: 6px;
}

.message-time {
  font-size: 11px;
  color: #95a5a6;
  padding: 0 4px;
}

/* Typing Indicator */
.typing-indicator {
  display: flex;
  align-items: center;
  gap: 8px;
  background: white !important;
  border: 1px solid #e9ecef !important;
}

.typing-dots {
  display: flex;
  gap: 4px;
}

.typing-dots span {
  width: 6px;
  height: 6px;
  background: #667eea;
  border-radius: 50%;
  animation: typing 1.4s infinite ease-in-out;
}

.typing-dots span:nth-child(1) {
  animation-delay: 0s;
}
.typing-dots span:nth-child(2) {
  animation-delay: 0.2s;
}
.typing-dots span:nth-child(3) {
  animation-delay: 0.4s;
}

@keyframes typing {
  0%,
  60%,
  100% {
    transform: translateY(0);
    opacity: 0.4;
  }
  30% {
    transform: translateY(-10px);
    opacity: 1;
  }
}

.typing-text {
  font-size: 12px;
  color: #6c757d;
}

/* Quick Actions */
.quick-actions {
  padding: 12px 20px;
  background: white;
  border-top: 1px solid #e9ecef;
}

.quick-action-chips {
  display: flex;
  gap: 8px;
  flex-wrap: wrap;
}

.quick-chip {
  background: #f8f9fa;
  border: 1px solid #e9ecef;
  border-radius: 20px;
  padding: 6px 12px;
  font-size: 12px;
  color: #6c757d;
  cursor: pointer;
  transition: all 0.2s ease;
  display: flex;
  align-items: center;
  gap: 6px;
}

.quick-chip:hover {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border-color: transparent;
  transform: translateY(-1px);
}

.quick-chip i {
  font-size: 10px;
}

/* Chat Footer */
.chat-footer {
  padding: 16px 20px;
  background: white;
  border-top: 1px solid #e9ecef;
}

.message-form {
  width: 100%;
}

.input-container {
  display: flex;
  gap: 8px;
  align-items: center;
  background: #f8f9fa;
  border-radius: 25px;
  padding: 4px;
  border: 1px solid #e9ecef;
  transition: border-color 0.2s ease;
}

.input-container:focus-within {
  border-color: #667eea;
  box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
}

.message-input {
  flex: 1;
  border: none;
  background: transparent;
  padding: 12px 16px;
  font-size: 14px;
  color: #2c3e50;
  outline: none;
}

.message-input::placeholder {
  color: #95a5a6;
}

.message-input:disabled {
  opacity: 0.6;
}

.send-button {
  width: 40px;
  height: 40px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border: none;
  border-radius: 50%;
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: all 0.2s ease;
  font-size: 14px;
}

.send-button:hover:not(:disabled) {
  transform: scale(1.05);
  box-shadow: 0 4px 12px rgba(102, 126, 234, 0.3);
}

.send-button:disabled {
  opacity: 0.5;
  cursor: not-allowed;
  transform: none;
}

/* Animations */
.chat-slide-enter-active,
.chat-slide-leave-active {
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
}

.chat-slide-enter-from {
  opacity: 0;
  transform: translateY(20px) scale(0.95);
}

.chat-slide-leave-to {
  opacity: 0;
  transform: translateY(20px) scale(0.95);
}

/* Scrollbar Styling */
.chat-body::-webkit-scrollbar {
  width: 4px;
}

.chat-body::-webkit-scrollbar-track {
  background: transparent;
}

.chat-body::-webkit-scrollbar-thumb {
  background: #cbd5e0;
  border-radius: 2px;
}

.chat-body::-webkit-scrollbar-thumb:hover {
  background: #a0aec0;
}

/* Mobile Responsive */
@media (max-width: 480px) {
  .chatbot-container {
    bottom: 15px;
    right: 15px;
  }

  .chat-window {
    width: calc(100vw - 30px);
    height: 70vh;
    bottom: 70px;
    right: -15px;
  }

  .chat-toggle {
    width: 50px;
    height: 50px;
  }

  .toggle-icon {
    font-size: 20px;
  }
}
</style>
