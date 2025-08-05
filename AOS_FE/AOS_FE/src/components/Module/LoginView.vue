<template>
  <div class="login-container">
    <div class="login-card">
      <!-- Left Side - Form -->
      <div class="form-section">
        <div class="form-content">
          <!-- Header -->
          <div class="login-header">
            <h2 class="login-title">
              <i class="bi bi-person-circle me-2"></i>
              Đăng Nhập
            </h2>
            <p class="login-subtitle">Chào mừng bạn quay trở lại!</p>
            <div class="register-link">
              <span>Chưa có tài khoản? </span>
              <RouterLink to="/register" class="register-btn">Đăng ký ngay</RouterLink>
            </div>
          </div>

          <!-- Login Form -->
          <form
            @submit.prevent="handleLogin"
            class="login-form"
            v-if="!showForgotPassword"
          >
            <div class="form-group">
              <label for="emailInput" class="form-label">
                <i class="bi bi-envelope me-2"></i>
                Email
              </label>
              <div class="input-wrapper">
                <input
                  type="email"
                  id="emailInput"
                  class="form-input"
                  v-model="formData.email"
                  placeholder="Nhập email của bạn"
                  required
                />
              </div>
            </div>

            <div class="form-group">
              <label for="passwordInput" class="form-label">
                <i class="bi bi-lock me-2"></i>
                Mật khẩu
              </label>
              <div class="input-wrapper password-wrapper">
                <input
                  :type="showPassword ? 'text' : 'password'"
                  id="passwordInput"
                  v-model="formData.password"
                  class="form-input"
                  placeholder="Nhập mật khẩu"
                  required
                />
                <button
                  type="button"
                  class="password-toggle"
                  @click="showPassword = !showPassword"
                >
                  <i :class="showPassword ? 'bi bi-eye-slash' : 'bi bi-eye'"></i>
                </button>
              </div>
            </div>

            <button type="submit" class="login-btn" :disabled="isLoading">
              <span v-if="!isLoading">
                <i class="bi bi-box-arrow-in-right me-2"></i>
                Đăng Nhập
              </span>
              <span v-else>
                <i class="bi bi-arrow-repeat spin me-2"></i>
                Đang đăng nhập...
              </span>
            </button>
          </form>

          <!-- Social Login -->
          <div class="social-login" v-if="!showForgotPassword">
            <div class="divider">
              <span>hoặc</span>
            </div>

            <div class="social-buttons">
              <a
                class="social-btn facebook-btn"
                href="http://localhost:8080/oauth2/authorization/facebook"
              >
                <div class="social-icon facebook-icon">
                  <i class="bi bi-facebook"></i>
                </div>
                <span>Đăng nhập với Facebook</span>
              </a>

              <a
                class="social-btn google-btn"
                href="http://localhost:8080/oauth2/authorization/google"
              >
                <div class="social-icon google-icon">
                  <svg width="20" height="20" viewBox="0 0 24 24" fill="none">
                    <path
                      d="M22.56 12.25c0-.78-.07-1.53-.2-2.25H12v4.26h5.92c-.26 1.37-1.04 2.53-2.21 3.31v2.77h3.57c2.08-1.92 3.28-4.74 3.28-8.09z"
                      fill="#4285F4"
                    />
                    <path
                      d="M12 23c2.97 0 5.46-.98 7.28-2.66l-3.57-2.77c-.98.66-2.23 1.06-3.71 1.06-2.86 0-5.29-1.93-6.16-4.53H2.18v2.84C3.99 20.53 7.7 23 12 23z"
                      fill="#34A853"
                    />
                    <path
                      d="M5.84 14.09c-.22-.66-.35-1.36-.35-2.09s.13-1.43.35-2.09V7.07H2.18C1.43 8.55 1 10.22 1 12s.43 3.45 1.18 4.93l2.85-2.22.81-.62z"
                      fill="#FBBC05"
                    />
                    <path
                      d="M12 5.38c1.62 0 3.06.56 4.21 1.64l3.15-3.15C17.45 2.09 14.97 1 12 1 7.7 1 3.99 3.47 2.18 7.07l3.66 2.84c.87-2.6 3.3-4.53 6.16-4.53z"
                      fill="#EA4335"
                    />
                  </svg>
                </div>
                <span>Đăng nhập với Google</span>
              </a>
            </div>
          </div>

          <!-- Forgot Password Link -->
          <div class="forgot-password" v-if="!showForgotPassword">
            <button type="button" class="forgot-btn" @click="showForgotPassword = true">
              <i class="bi bi-question-circle me-1"></i>
              Quên mật khẩu?
            </button>
          </div>

          <!-- Forgot Password Form -->
          <div v-if="showForgotPassword" class="forgot-password-form">
            <div class="forgot-header">
              <button type="button" class="back-btn" @click="resetForgotPassword">
                <i class="bi bi-arrow-left me-2"></i>
                Quay lại
              </button>
              <h3 class="forgot-title">
                <i class="bi bi-key me-2"></i>
                Khôi phục mật khẩu
              </h3>
              <p class="forgot-subtitle">Nhập email để nhận mã xác thực</p>
            </div>

            <!-- Step 1: Email Input -->
            <div v-if="!otpSent" class="forgot-step">
              <div class="form-group">
                <label class="form-label">
                  <i class="bi bi-envelope me-2"></i>
                  Email khôi phục
                </label>
                <div class="input-wrapper">
                  <input
                    v-model="forgotEmail"
                    type="email"
                    class="form-input"
                    placeholder="Nhập email của bạn"
                    required
                  />
                </div>
              </div>
              <button
                type="button"
                @click="sendOtp"
                class="forgot-btn-action"
                :disabled="!forgotEmail || isLoading"
              >
                <i class="bi bi-send me-2"></i>
                Gửi mã OTP
              </button>
            </div>

            <!-- Step 2: OTP Verification -->
            <div v-if="otpSent && !otpVerified" class="forgot-step">
              <div class="otp-info">
                <i class="bi bi-info-circle me-2"></i>
                Mã OTP đã được gửi đến <strong>{{ forgotEmail }}</strong>
              </div>
              <div class="form-group">
                <label class="form-label">
                  <i class="bi bi-shield-lock me-2"></i>
                  Mã OTP
                </label>
                <div class="input-wrapper">
                  <input
                    v-model="enteredOtp"
                    type="text"
                    class="form-input otp-input"
                    placeholder="Nhập mã 6 số"
                    maxlength="6"
                    required
                  />
                </div>
              </div>
              <div class="otp-actions">
                <button
                  type="button"
                  @click="verifyOtp"
                  class="forgot-btn-action"
                  :disabled="!enteredOtp || isLoading"
                >
                  <i class="bi bi-check-circle me-2"></i>
                  Xác minh OTP
                </button>
                <button
                  type="button"
                  @click="sendOtp"
                  class="resend-btn"
                  :disabled="isLoading"
                >
                  <i class="bi bi-arrow-clockwise me-1"></i>
                  Gửi lại
                </button>
              </div>
            </div>

            <!-- Step 3: New Password -->
            <div v-if="otpVerified" class="forgot-step">
              <div class="success-info">
                <i class="bi bi-check-circle-fill me-2"></i>
                OTP xác thực thành công! Đặt mật khẩu mới.
              </div>
              <div class="form-group">
                <label class="form-label">
                  <i class="bi bi-lock me-2"></i>
                  Mật khẩu mới
                </label>
                <div class="input-wrapper password-wrapper">
                  <input
                    v-model="newPassword"
                    :type="showNewPassword ? 'text' : 'password'"
                    class="form-input"
                    placeholder="Nhập mật khẩu mới"
                    required
                  />
                  <button
                    type="button"
                    class="password-toggle"
                    @click="showNewPassword = !showNewPassword"
                  >
                    <i :class="showNewPassword ? 'bi bi-eye-slash' : 'bi bi-eye'"></i>
                  </button>
                </div>
              </div>
              <button
                type="button"
                @click="resetPassword"
                class="forgot-btn-action"
                :disabled="!newPassword || isLoading"
              >
                <i class="bi bi-shield-check me-2"></i>
                Đặt lại mật khẩu
              </button>
            </div>
          </div>
        </div>
      </div>

      <!-- Right Side - Image/Branding -->
      <div class="image-section">
        <div class="brand-content">
          <div class="brand-logo">
            <i class="bi bi-shop"></i>
          </div>
          <h3 class="brand-title">AllInStore</h3>
          <p class="brand-subtitle">Nền tảng tùy chỉnh áo thun hàng đầu Việt Nam</p>
          <div class="features">
            <div class="feature-item">
              <i class="bi bi-palette"></i>
              <span>Thiết kế tự do</span>
            </div>
            <div class="feature-item">
              <i class="bi bi-truck"></i>
              <span>Giao hàng nhanh</span>
            </div>
            <div class="feature-item">
              <i class="bi bi-award"></i>
              <span>Chất lượng cao</span>
            </div>
          </div>
        </div>
        <div class="decorative-shapes">
          <div class="shape shape-1"></div>
          <div class="shape shape-2"></div>
          <div class="shape shape-3"></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from "vue";
import { RouterLink, useRouter } from "vue-router";
import { authService } from "../../Configs/api";
import { syncLocalCartToServer } from "../../Configs/cart";
import { toast } from "vue3-toastify";

const router = useRouter();

const formData = reactive({
  email: "adminCUDE@gmail.com",
  password: "123",
});

const showPassword = ref(false);
const showNewPassword = ref(false);
const isLoading = ref(false);
const showForgotPassword = ref(false);
const forgotEmail = ref("");
const enteredOtp = ref("");
const otpSent = ref(false);
const otpVerified = ref(false);
const newPassword = ref("");

async function handleLogin() {
  isLoading.value = true;
  try {
    await authService.login(formData.email, formData.password);
    await syncLocalCartToServer();
  } catch (error) {
  } finally {
    isLoading.value = false;
  }
}

function resetForgotPassword() {
  showForgotPassword.value = false;
  forgotEmail.value = "";
  enteredOtp.value = "";
  newPassword.value = "";
  otpSent.value = false;
  otpVerified.value = false;
}

function sendOtp() {
  isLoading.value = true;
  fetch("http://localhost:8080/api/forgot-password/request", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ email: forgotEmail.value }),
  })
    .then((res) => {
      if (!res.ok) throw new Error("Gửi OTP thất bại");
      toast.success("Đã gửi mã OTP đến email.");
      otpSent.value = true;
    })
    .catch(() => toast.error("Không gửi được mã OTP."))
    .finally(() => (isLoading.value = false));
}

function verifyOtp() {
  isLoading.value = true;
  try {
    if (!enteredOtp.value) {
      toast.error("Vui lòng nhập mã OTP.");
      return;
    }

    const trimmedOtp = enteredOtp.value.trim();
    const payload = {
      email: forgotEmail.value,
      otpCode: trimmedOtp.toString(),
    };

    fetch("http://localhost:8080/api/forgot-password/verify", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload),
    })
      .then(async (res) => {
        const msg = await res.text();

        if (!res.ok) {
          toast.error("❌ " + msg);
          return;
        }

        if (msg.includes("thành công")) {
          toast.success(msg);
          otpVerified.value = true;
        } else {
          toast.error(msg);
        }
      })
      .catch((err) => {
        console.error("❌ Lỗi kết nối:", err);
        toast.error("Lỗi xác minh OTP.");
      })
      .finally(() => (isLoading.value = false));
  } catch (error) {
    console.error("🔥 Lỗi trong verifyOtp:", error);
    isLoading.value = false;
  }
}

function resetPassword() {
  isLoading.value = true;
  fetch("http://localhost:8080/api/forgot-password/change", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      email: forgotEmail.value,
      otp: enteredOtp.value.toString(),
      newPassword: newPassword.value,
    }),
  })
    .then((res) => res.text())
    .then((msg) => {
      toast.success(msg);
      resetForgotPassword();
    })
    .catch(() => toast.error("Không thể đặt lại mật khẩu."))
    .finally(() => (isLoading.value = false));
}
</script>

<style scoped>
/* Container và Layout */
.login-container {
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 2rem 1rem;
  font-family: "Inter", -apple-system, BlinkMacSystemFont, sans-serif;
}

.login-card {
  background: white;
  border-radius: 25px;
  box-shadow: 0 25px 60px rgba(0, 0, 0, 0.15);
  overflow: hidden;
  max-width: 1000px;
  width: 100%;
  display: grid;
  grid-template-columns: 1fr 1fr;
  min-height: 600px;
  animation: slideUp 0.6s ease-out;
}

@keyframes slideUp {
  from {
    opacity: 0;
    transform: translateY(40px) scale(0.95);
  }
  to {
    opacity: 1;
    transform: translateY(0) scale(1);
  }
}

/* Form Section */
.form-section {
  padding: 3rem 2.5rem;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.form-content {
  max-width: 400px;
  margin: 0 auto;
  width: 100%;
}

.login-header {
  text-align: center;
  margin-bottom: 2.5rem;
}

.login-title {
  font-size: 2.2rem;
  font-weight: 700;
  color: #2d3748;
  margin-bottom: 0.5rem;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 0.5rem;
}

.login-subtitle {
  color: #718096;
  font-size: 1rem;
  margin-bottom: 1.5rem;
  font-weight: 400;
}

.register-link {
  font-size: 0.9rem;
  color: #718096;
}

.register-btn {
  color: #667eea;
  text-decoration: none;
  font-weight: 600;
  transition: all 0.3s ease;
}

.register-btn:hover {
  color: #764ba2;
  text-decoration: underline;
}

/* Form Styles */
.login-form {
  margin-bottom: 2rem;
}

.form-group {
  margin-bottom: 1.5rem;
}

.form-label {
  display: block;
  color: #4a5568;
  font-weight: 600;
  margin-bottom: 0.5rem;
  font-size: 0.9rem;
  display: flex;
  align-items: center;
}

.input-wrapper {
  position: relative;
}

.form-input {
  width: 100%;
  padding: 1rem 1.25rem;
  border: 2px solid #e2e8f0;
  border-radius: 15px;
  font-size: 1rem;
  transition: all 0.3s ease;
  background: #f7fafc;
  font-family: inherit;
}

.form-input:focus {
  outline: none;
  border-color: #667eea;
  background: white;
  box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
  transform: translateY(-1px);
}

.password-wrapper {
  position: relative;
}

.password-toggle {
  position: absolute;
  right: 1rem;
  top: 50%;
  transform: translateY(-50%);
  background: none;
  border: none;
  color: #718096;
  font-size: 1.1rem;
  cursor: pointer;
  transition: color 0.3s ease;
  padding: 0.25rem;
}

.password-toggle:hover {
  color: #4a5568;
}

.login-btn {
  width: 100%;
  padding: 1rem 1.5rem;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border: none;
  border-radius: 15px;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 0.5rem;
  font-family: inherit;
}

.login-btn:hover:not(:disabled) {
  transform: translateY(-2px);
  box-shadow: 0 15px 35px rgba(102, 126, 234, 0.4);
}

.login-btn:disabled {
  opacity: 0.7;
  cursor: not-allowed;
  transform: none;
}

.spin {
  animation: spin 1s linear infinite;
}

@keyframes spin {
  from {
    transform: rotate(0deg);
  }
  to {
    transform: rotate(360deg);
  }
}

/* Social Login */
.social-login {
  margin-bottom: 1.5rem;
}

.divider {
  text-align: center;
  margin: 1.5rem 0;
  position: relative;
  color: #718096;
  font-size: 0.9rem;
}

.divider::before {
  content: "";
  position: absolute;
  top: 50%;
  left: 0;
  right: 0;
  height: 1px;
  background: #e2e8f0;
}

.divider span {
  background: white;
  padding: 0 1rem;
  position: relative;
}

.social-buttons {
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
}

.social-btn {
  display: flex;
  align-items: center;
  justify-content: flex-start;
  padding: 1rem 1.5rem;
  border: 2px solid transparent;
  border-radius: 12px;
  text-decoration: none;
  font-weight: 600;
  transition: all 0.3s ease;
  position: relative;
  overflow: hidden;
  gap: 1rem;
  font-size: 0.95rem;
  min-height: 48px;
  width: 100%;
}

.social-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 25px rgba(0, 0, 0, 0.15);
  text-decoration: none;
}

.social-icon {
  width: 24px;
  height: 24px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 6px;
  font-size: 16px;
  flex-shrink: 0;
}

.social-btn span {
  flex: 1;
  text-align: left;
  white-space: nowrap;
}

.facebook-btn {
  background: linear-gradient(135deg, #1877f2 0%, #42a5f5 100%);
  color: white;
  border-color: #1877f2;
}

.facebook-btn:hover {
  background: linear-gradient(135deg, #166fe5 0%, #1976d2 100%);
  color: white;
  box-shadow: 0 8px 25px rgba(24, 119, 242, 0.4);
}

.facebook-icon {
  background: rgba(255, 255, 255, 0.2);
  color: white;
}

.google-btn {
  background: white;
  color: #5f6368;
  border-color: #dadce0;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.google-btn:hover {
  background: #f8f9fa;
  color: #3c4043;
  border-color: #5f6368;
  box-shadow: 0 8px 25px rgba(0, 0, 0, 0.15);
}

.google-icon {
  background: transparent;
}

/* Forgot Password */
.forgot-password {
  text-align: center;
}

.forgot-btn {
  background: none;
  border: none;
  color: #667eea;
  font-size: 0.9rem;
  cursor: pointer;
  transition: color 0.3s ease;
  font-family: inherit;
}

.forgot-btn:hover {
  color: #764ba2;
  text-decoration: underline;
}

/* Forgot Password Form */
.forgot-password-form {
  animation: fadeIn 0.3s ease-in;
}

@keyframes fadeIn {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}

.forgot-header {
  text-align: center;
  margin-bottom: 2rem;
}

.back-btn {
  background: none;
  border: none;
  color: #667eea;
  cursor: pointer;
  margin-bottom: 1rem;
  font-size: 0.9rem;
  transition: color 0.3s ease;
  font-family: inherit;
}

.back-btn:hover {
  color: #764ba2;
}

.forgot-title {
  font-size: 1.5rem;
  font-weight: 700;
  color: #2d3748;
  margin-bottom: 0.5rem;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 0.5rem;
}

.forgot-subtitle {
  color: #718096;
  font-size: 0.9rem;
}

.forgot-step {
  margin-bottom: 1.5rem;
}

.forgot-btn-action {
  width: 100%;
  padding: 1rem 1.5rem;
  background: linear-gradient(135deg, #10b981 0%, #059669 100%);
  color: white;
  border: none;
  border-radius: 12px;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 0.5rem;
  margin-top: 1rem;
  font-family: inherit;
}

.forgot-btn-action:hover:not(:disabled) {
  transform: translateY(-2px);
  box-shadow: 0 10px 25px rgba(16, 185, 129, 0.4);
}

.forgot-btn-action:disabled {
  opacity: 0.6;
  cursor: not-allowed;
  transform: none;
}

.otp-info,
.success-info {
  background: #e6fffa;
  border: 1px solid #81e6d9;
  border-radius: 10px;
  padding: 1rem;
  margin-bottom: 1rem;
  color: #234e52;
  font-size: 0.9rem;
  display: flex;
  align-items: center;
}

.success-info {
  background: #f0fff4;
  border-color: #9ae6b4;
  color: #22543d;
}

.otp-input {
  text-align: center;
  font-size: 1.1rem;
  letter-spacing: 0.1em;
  font-weight: 600;
}

.otp-actions {
  display: flex;
  gap: 0.5rem;
  margin-top: 1rem;
}

.resend-btn {
  padding: 0.75rem 1rem;
  background: #f7fafc;
  color: #4a5568;
  border: 2px solid #e2e8f0;
  border-radius: 10px;
  cursor: pointer;
  font-size: 0.9rem;
  transition: all 0.3s ease;
  flex: 1;
  font-family: inherit;
}

.resend-btn:hover:not(:disabled) {
  background: #edf2f7;
  border-color: #cbd5e0;
}

.forgot-btn-action {
  flex: 2;
}

/* Image Section */
.image-section {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  overflow: hidden;
}

.brand-content {
  text-align: center;
  z-index: 2;
  position: relative;
  padding: 2rem;
}

.brand-logo {
  font-size: 4rem;
  margin-bottom: 1rem;
  animation: float 3s ease-in-out infinite;
}

@keyframes float {
  0%,
  100% {
    transform: translateY(0px);
  }
  50% {
    transform: translateY(-10px);
  }
}

.brand-title {
  font-size: 2.5rem;
  font-weight: 700;
  margin-bottom: 1rem;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.brand-subtitle {
  font-size: 1.1rem;
  opacity: 0.9;
  margin-bottom: 2rem;
  line-height: 1.6;
}

.features {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.feature-item {
  display: flex;
  align-items: center;
  gap: 1rem;
  background: rgba(255, 255, 255, 0.1);
  padding: 1rem;
  border-radius: 12px;
  backdrop-filter: blur(10px);
  transition: transform 0.3s ease;
}

.feature-item:hover {
  transform: translateX(5px);
}

.feature-item i {
  font-size: 1.5rem;
}

/* Decorative Shapes */
.decorative-shapes {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  overflow: hidden;
  z-index: 1;
}

.shape {
  position: absolute;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.1);
  animation: float 6s ease-in-out infinite;
}

.shape-1 {
  width: 100px;
  height: 100px;
  top: 20%;
  left: 10%;
  animation-delay: 0s;
}

.shape-2 {
  width: 150px;
  height: 150px;
  top: 60%;
  right: 15%;
  animation-delay: 2s;
}

.shape-3 {
  width: 80px;
  height: 80px;
  bottom: 20%;
  left: 20%;
  animation-delay: 4s;
}

/* Responsive Design */
@media (max-width: 968px) {
  .login-card {
    grid-template-columns: 1fr;
    max-width: 500px;
  }

  .image-section {
    order: -1;
    min-height: 200px;
  }

  .brand-content {
    padding: 2rem 1rem;
  }

  .brand-title {
    font-size: 2rem;
  }

  .features {
    flex-direction: row;
    justify-content: center;
    flex-wrap: wrap;
  }

  .feature-item {
    flex: 0 1 auto;
    min-width: 120px;
  }
}

@media (max-width: 640px) {
  .login-container {
    padding: 1rem;
  }

  .form-section {
    padding: 2rem 1.5rem;
  }

  .login-title {
    font-size: 1.8rem;
  }

  .social-buttons {
    flex-direction: column;
  }

  .social-btn {
    padding: 0.875rem 1.25rem;
  }

  .otp-actions {
    flex-direction: column;
  }

  .brand-logo {
    font-size: 3rem;
  }

  .brand-title {
    font-size: 1.5rem;
  }
}

/* Focus và Accessibility */
.form-input:focus,
.login-btn:focus,
.social-btn:focus,
.forgot-btn:focus,
.forgot-btn-action:focus,
.back-btn:focus,
.password-toggle:focus {
  outline: 2px solid #667eea;
  outline-offset: 2px;
}

/* Dark mode support (optional) */
@media (prefers-color-scheme: dark) {
  .login-card {
    background: #1a202c;
    color: #e2e8f0;
  }

  .login-title,
  .forgot-title {
    color: #e2e8f0;
  }

  .form-input {
    background: #2d3748;
    border-color: #4a5568;
    color: #e2e8f0;
  }

  .form-input:focus {
    background: #2d3748;
    border-color: #667eea;
  }
}
</style>
