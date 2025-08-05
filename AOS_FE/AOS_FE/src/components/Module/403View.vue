<template>
  <div class="error-page">
    <div class="error-container">
      <!-- Error Icon -->
      <div class="error-icon">
        <div class="shield-icon">
          <i class="bi bi-shield-exclamation"></i>
        </div>
        <div class="error-code">403</div>
      </div>

      <!-- Error Content -->
      <div class="error-content">
        <h1 class="error-title">Không có quyền truy cập</h1>
        <p class="error-description">
          Xin lỗi, bạn không có quyền truy cập vào trang này. Vui lòng liên hệ quản trị
          viên hoặc quay lại trang chủ.
        </p>

        <!-- Action Buttons -->
        <div class="error-actions">
          <button class="btn-primary" @click="goHome">
            <i class="bi bi-house-door me-2"></i>
            Về trang chủ
          </button>
          <button class="btn-secondary" @click="goBack">
            <i class="bi bi-arrow-left me-2"></i>
            Quay lại
          </button>
        </div>

        <!-- Help Section -->
        <div class="help-section">
          <p class="help-text">Cần hỗ trợ?</p>
          <a href="mailto:support@example.com" class="help-link">
            <i class="bi bi-envelope me-1"></i>
            Liên hệ hỗ trợ
          </a>
        </div>
      </div>

      <!-- Decorative Elements -->
      <div class="floating-elements">
        <div class="float-element float-1"></div>
        <div class="float-element float-2"></div>
        <div class="float-element float-3"></div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from "vue-router";

const router = useRouter();

const goHome = () => {
  router.push("/");
};

const goBack = () => {
  router.go(-1);
};
</script>

<style scoped>
.error-page {
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 2rem;
  position: relative;
  overflow: hidden;
}

.error-container {
  background: white;
  border-radius: 24px;
  padding: 3rem;
  max-width: 500px;
  width: 100%;
  text-align: center;
  box-shadow: 0 25px 80px rgba(0, 0, 0, 0.2);
  position: relative;
  z-index: 10;
  animation: slideUp 0.6s ease;
}

@keyframes slideUp {
  from {
    opacity: 0;
    transform: translateY(30px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.error-icon {
  margin-bottom: 2rem;
  position: relative;
}

.shield-icon {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  width: 120px;
  height: 120px;
  background: linear-gradient(135deg, #ef4444 0%, #dc2626 100%);
  border-radius: 50%;
  margin-bottom: 1rem;
  box-shadow: 0 15px 40px rgba(239, 68, 68, 0.3);
  animation: pulse 2s infinite;
}

.shield-icon i {
  font-size: 3rem;
  color: white;
}

@keyframes pulse {
  0%,
  100% {
    transform: scale(1);
  }
  50% {
    transform: scale(1.05);
  }
}

.error-code {
  font-size: 6rem;
  font-weight: 800;
  color: #1e293b;
  line-height: 1;
  background: linear-gradient(135deg, #ef4444 0%, #dc2626 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  text-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.error-content {
  margin-bottom: 2rem;
}

.error-title {
  font-size: 2.5rem;
  font-weight: 700;
  color: #1e293b;
  margin-bottom: 1rem;
  line-height: 1.2;
}

.error-description {
  font-size: 1.1rem;
  color: #64748b;
  line-height: 1.6;
  margin-bottom: 2rem;
}

.error-actions {
  display: flex;
  gap: 1rem;
  justify-content: center;
  margin-bottom: 2rem;
  flex-wrap: wrap;
}

.btn-primary,
.btn-secondary {
  padding: 1rem 2rem;
  border-radius: 12px;
  font-weight: 600;
  font-size: 1rem;
  border: none;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  text-decoration: none;
  min-width: 150px;
  justify-content: center;
}

.btn-primary {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  box-shadow: 0 4px 15px rgba(102, 126, 234, 0.3);
}

.btn-primary:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 25px rgba(102, 126, 234, 0.4);
}

.btn-secondary {
  background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
  color: #495057;
  border: 2px solid #dee2e6;
}

.btn-secondary:hover {
  transform: translateY(-2px);
  background: white;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
}

.help-section {
  border-top: 1px solid #e2e8f0;
  padding-top: 1.5rem;
}

.help-text {
  color: #64748b;
  margin-bottom: 0.5rem;
  font-weight: 500;
}

.help-link {
  color: #667eea;
  text-decoration: none;
  font-weight: 600;
  display: inline-flex;
  align-items: center;
  transition: color 0.3s ease;
}

.help-link:hover {
  color: #5a52d5;
  text-decoration: underline;
}

/* Floating Elements */
.floating-elements {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  pointer-events: none;
  z-index: 1;
}

.float-element {
  position: absolute;
  background: rgba(255, 255, 255, 0.1);
  border-radius: 50%;
  animation: float 6s ease-in-out infinite;
}

.float-1 {
  width: 80px;
  height: 80px;
  top: 20%;
  left: 10%;
  animation-delay: 0s;
}

.float-2 {
  width: 120px;
  height: 120px;
  top: 60%;
  right: 15%;
  animation-delay: 2s;
}

.float-3 {
  width: 60px;
  height: 60px;
  bottom: 20%;
  left: 20%;
  animation-delay: 4s;
}

@keyframes float {
  0%,
  100% {
    transform: translateY(0px) rotate(0deg);
    opacity: 0.3;
  }
  50% {
    transform: translateY(-20px) rotate(180deg);
    opacity: 0.6;
  }
}

/* Responsive Design */
@media (max-width: 768px) {
  .error-container {
    padding: 2rem;
    margin: 1rem;
  }

  .error-code {
    font-size: 4rem;
  }

  .error-title {
    font-size: 2rem;
  }

  .error-description {
    font-size: 1rem;
  }

  .error-actions {
    flex-direction: column;
    align-items: center;
  }

  .btn-primary,
  .btn-secondary {
    width: 100%;
    max-width: 250px;
  }

  .shield-icon {
    width: 100px;
    height: 100px;
  }

  .shield-icon i {
    font-size: 2.5rem;
  }
}

@media (max-width: 480px) {
  .error-container {
    padding: 1.5rem;
  }

  .error-code {
    font-size: 3rem;
  }

  .error-title {
    font-size: 1.5rem;
  }
}
</style>
