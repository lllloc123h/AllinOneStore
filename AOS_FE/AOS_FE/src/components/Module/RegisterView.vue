<template>
  <div class="register-container">
    <div class="register-card">
      <!-- Left Side - Form -->
      <div class="form-section">
        <div class="form-content">
          <!-- Header -->
          <div class="register-header">
            <h2 class="register-title">
              <i class="bi bi-person-plus me-2"></i>
              Đăng Ký
            </h2>
            <p class="register-subtitle">Tạo tài khoản mới để bắt đầu</p>
            <div class="login-link">
              <span>Đã có tài khoản? </span>
              <RouterLink to="/login" class="login-btn-link">Đăng nhập ngay</RouterLink>
            </div>
          </div>

          <!-- Register Form -->
          <form @submit.prevent="handleLogin" class="register-form">
            <div class="form-group">
              <label for="emailInput" class="form-label">
                <i class="bi bi-envelope me-2"></i>
                Email
              </label>
              <div class="input-wrapper">
                <input type="email" id="emailInput" class="form-input" v-model="userRegister.email"
                  placeholder="Nhập email của bạn" required />
              </div>
            </div>

            <div class="form-group">
              <label for="fullnameInput" class="form-label">
                <i class="bi bi-person me-2"></i>
                Họ và tên
              </label>
              <div class="input-wrapper">
                <input type="text" id="fullnameInput" class="form-input" v-model="userRegister.fullname"
                  placeholder="Nhập họ và tên" required />
              </div>
            </div>

            <div class="form-group">
              <label for="phoneInput" class="form-label">
                <i class="bi bi-telephone me-2"></i>
                Số điện thoại
              </label>
              <div class="input-wrapper">
                <input type="tel" id="phoneInput" class="form-input" v-model="userRegister.phone"
                  placeholder="Nhập số điện thoại" required />
              </div>
            </div>

            <div class="form-group">
              <label class="form-label">
                <i class="bi bi-person-badge me-2"></i>
                Giới tính
              </label>
              <div class="radio-group">
                <label v-for="option in genderOptions" :key="option.value" class="radio-option">
                  <input type="radio" :value="option.value" v-model="userRegister.gender" name="gender" />
                  <span class="radio-custom">
                    {{ option.label }}
                  </span>
                </label>
              </div>
            </div>

            <div class="form-group">
              <label for="passwordInput" class="form-label">
                <i class="bi bi-lock me-2"></i>
                Mật khẩu
              </label>
              <div class="input-wrapper password-wrapper">
                <input :type="showPassword ? 'text' : 'password'" id="passwordInput" v-model="userRegister.password"
                  class="form-input" placeholder="Nhập mật khẩu" required />
                <button type="button" class="password-toggle" @click="showPassword = !showPassword">
                  <i :class="showPassword ? 'bi bi-eye-slash' : 'bi bi-eye'"></i>
                </button>
              </div>
            </div>

            <div class="form-group">
              <label for="confirmPasswordInput" class="form-label">
                <i class="bi bi-lock-fill me-2"></i>
                Nhập lại mật khẩu
              </label>
              <div class="input-wrapper password-wrapper">
                <input :type="showConfirmPassword ? 'text' : 'password'" id="confirmPasswordInput"
                  v-model="userRegister.confirmPassword" class="form-input" placeholder="Nhập lại mật khẩu" required />
                <button type="button" class="password-toggle" @click="showConfirmPassword = !showConfirmPassword">
                  <i :class="showConfirmPassword ? 'bi bi-eye-slash' : 'bi bi-eye'"></i>
                </button>
              </div>
            </div>

            <button type="submit" class="register-btn" :disabled="isLoading">
              <span v-if="!isLoading">
                <i class="bi bi-person-check me-2"></i>
                Đăng Ký
              </span>
              <span v-else>
                <i class="bi bi-arrow-repeat spin me-2"></i>
                Đang xử lý...
              </span>
            </button>
          </form>
        </div>
      </div>

      <!-- Right Side - Image/Branding -->
      <div class="image-section">
        <div class="brand-content">
          <div class="brand-logo">
            <i class="bi bi-person-plus-fill"></i>
          </div>
          <h3 class="brand-title">Tham gia cùng chúng tôi</h3>
          <p class="brand-subtitle">Khám phá thế giới thiết kế áo thun không giới hạn</p>
          <div class="features">
            <div class="feature-item">
              <i class="bi bi-gift"></i>
              <span>Ưu đãi đặc biệt</span>
            </div>
            <div class="feature-item">
              <i class="bi bi-shield-check"></i>
              <span>Bảo mật an toàn</span>
            </div>
            <div class="feature-item">
              <i class="bi bi-headset"></i>
              <span>Hỗ trợ 24/7</span>
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

  <OTPView :show="showOption" @verified="verified" @resend="sendOTP" @close="closeModal"></OTPView>
</template>
<script setup>
import { ref } from "vue";
import { toast } from "vue3-toastify";
import api, { authService } from "../../Configs/api";
import OTPView from "./OTPView.vue";
import { RouterLink } from "vue-router";
import { notification } from "ant-design-vue";
import { getGenderOptions } from "./CommonsFunctions";

const genderOptions = getGenderOptions();

const userRegister = ref({
  email: "tranhuuloc123@gmail.com",
  fullname: "Tran Huu Loc",
  phone: "0901234567",
  password: "123",
  confirmPassword: "123",
  gender: true, // Default to Nam (true)
});

const showOption = ref(false);
const showPassword = ref(false);
const showConfirmPassword = ref(false);
const isLoading = ref(false);

const handleLogin = () => {
  if (userRegister.value.password !== userRegister.value.confirmPassword) {
    toast.warning("Mật khẩu xác nhận không khớp!");
    return;
  }

  if (userRegister.value.password.length < 3) {
    toast.warning("Mật khẩu phải có ít nhất 3 ký tự!");
    return;
  }

  // gui OTP o day
  sendOTP();
};

const closeModal = () => {
  showOption.value = false;
};

const sendOTP = () => {
  isLoading.value = true;
  api
    .post("/Accounts/register", {
      email: userRegister.value.email,
      password: userRegister.value.password,
      fullname: userRegister.value.fullname,
      phone: userRegister.value.phone,
      gender: userRegister.value.gender,
    })
    .then((resp) => {
      toast.success("Mã OTP đã được gửi: " + resp.data.OTP);
      notification.success({
        message: "Mã OTP đã được gửi",
        description: "Vui lòng kiểm tra email của bạn để nhận mã OTP. " + resp.data.OTP,
      });
      showOption.value = true;
    })
    .catch((error) => {
      notification.error({
        message: "Đăng ký thất bại",
        description: error.response?.data?.message || "Vui lòng thử lại sau.",
      });
    })
    .finally(() => {
      isLoading.value = false;
    });
};

const verified = () => {
  setTimeout(() => {
    authService.login(userRegister.value.email, userRegister.value.password);
    userRegister.value.email = "";
    userRegister.value.password = "";
    userRegister.value.fullname = "";
    userRegister.value.confirmPassword = "";
    userRegister.value.phone = "";
    userRegister.value.gender = true; // Reset to default
    showOption.value = false;
    toast.success("Đăng ký thành công!");
  }, 1000);
};
</script>
<style scoped>
/* Container và Layout */
.register-container {
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 2rem 1rem;
  font-family: "Inter", -apple-system, BlinkMacSystemFont, sans-serif;
}

.register-card {
  background: white;
  border-radius: 25px;
  box-shadow: 0 25px 60px rgba(0, 0, 0, 0.15);
  overflow: hidden;
  max-width: 1000px;
  width: 100%;
  display: grid;
  grid-template-columns: 1fr 1fr;
  min-height: 650px;
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
  padding: 2.5rem 2rem;
  display: flex;
  flex-direction: column;
  justify-content: center;
  overflow-y: auto;
}

.form-content {
  max-width: 400px;
  margin: 0 auto;
  width: 100%;
}

.register-header {
  text-align: center;
  margin-bottom: 2rem;
}

.register-title {
  font-size: 2rem;
  font-weight: 700;
  color: #2d3748;
  margin-bottom: 0.5rem;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 0.5rem;
}

.register-subtitle {
  color: #718096;
  font-size: 1rem;
  margin-bottom: 1.5rem;
  font-weight: 400;
}

.login-link {
  font-size: 0.9rem;
  color: #718096;
}

.login-btn-link {
  color: #667eea;
  text-decoration: none;
  font-weight: 600;
  transition: all 0.3s ease;
}

.login-btn-link:hover {
  color: #764ba2;
  text-decoration: underline;
}

/* Form Styles */
.register-form {
  margin-bottom: 1rem;
}

.form-group {
  margin-bottom: 1.25rem;
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
  padding: 0.875rem 1rem;
  border: 2px solid #e2e8f0;
  border-radius: 12px;
  font-size: 0.95rem;
  transition: all 0.3s ease;
  background: #f7fafc;
  font-family: inherit;
}

.form-input:focus {
  outline: none;
  border-color: #10b981;
  background: white;
  box-shadow: 0 0 0 3px rgba(16, 185, 129, 0.1);
  transform: translateY(-1px);
}

.password-wrapper {
  position: relative;
}

.password-toggle {
  position: absolute;
  right: 0.875rem;
  top: 50%;
  transform: translateY(-50%);
  background: none;
  border: none;
  color: #718096;
  font-size: 1rem;
  cursor: pointer;
  transition: color 0.3s ease;
  padding: 0.25rem;
}

.password-toggle:hover {
  color: #4a5568;
}

/* Radio Button Styles */
.radio-group {
  display: flex;
  gap: 1rem;
  margin-top: 0.5rem;
  flex-wrap: wrap;
}

.radio-option {
  display: flex;
  align-items: center;
  cursor: pointer;
  position: relative;
  flex: 1;
  min-width: 0;
}

.radio-option input[type="radio"] {
  position: absolute;
  opacity: 0;
  cursor: pointer;
  width: 0;
  height: 0;
}

.radio-custom {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 0.5rem;
  padding: 0.875rem 1rem;
  border: 2px solid #e2e8f0;
  border-radius: 12px;
  background: #f7fafc;
  transition: all 0.3s ease;
  font-weight: 500;
  color: #4a5568;
  width: 100%;
  text-align: center;
  font-size: 0.95rem;
}

.radio-option input[type="radio"]:checked+.radio-custom {
  background: linear-gradient(135deg, #10b981 0%, #059669 100%);
  color: white;
  border-color: #10b981;
  box-shadow: 0 4px 15px rgba(16, 185, 129, 0.3);
  transform: translateY(-1px);
}

.radio-option:hover .radio-custom {
  border-color: #10b981;
  box-shadow: 0 2px 8px rgba(16, 185, 129, 0.2);
  transform: translateY(-1px);
}

.register-btn {
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
  font-family: inherit;
  margin-top: 0.5rem;
}

.register-btn:hover:not(:disabled) {
  transform: translateY(-2px);
  box-shadow: 0 15px 35px rgba(16, 185, 129, 0.4);
}

.register-btn:disabled {
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

/* Image Section */
.image-section {
  background: linear-gradient(135deg, #10b981 0%, #059669 100%);
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
  .register-card {
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

  /* Mobile radio styles */
  .radio-group {
    flex-direction: column;
    gap: 0.75rem;
  }

  .radio-custom {
    justify-content: flex-start;
    padding: 1rem;
  }
}

@media (max-width: 640px) {
  .register-container {
    padding: 1rem;
  }

  .form-section {
    padding: 2rem 1.5rem;
  }

  .register-title {
    font-size: 1.8rem;
  }

  .form-group {
    margin-bottom: 1rem;
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
.register-btn:focus,
.login-btn-link:focus,
.password-toggle:focus {
  outline: 2px solid #667eea;
  outline-offset: 2px;
}

/* Dark mode support (optional) */
@media (prefers-color-scheme: dark) {
  .register-card {
    background: #1a202c;
    color: #e2e8f0;
  }

  .register-title {
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
