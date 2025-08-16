<template>
  <footer class="modern-footer">
    <!-- Main Footer Content -->
    <div class="footer-content">
      <div class="container">
        <div class="row">
          <!-- Brand Section -->
          <div class="col-lg-4 col-md-6 mb-4">
            <div class="footer-brand">
              <div class="brand-logo">
                <img
                  src="../../assets/imgs/Vàng nhạt và Xanh ngọc lam Nghệ thuật sắp chữ In đậm Tiệm cà phê Biểu trưng Cà phê.png"
                  alt="AOS Fashion Logo"
                  class="footer-logo"
                />
              </div>
              <p class="brand-description">
                Thời trang hiện đại, chất lượng cao với giá cả phải chăng. Mang đến phong
                cách độc đáo cho mọi khách hàng.
              </p>
              <div class="contact-info">
                <div class="contact-item">
                  <i class="bi bi-geo-alt-fill"></i>
                  <span>123 Đường Thời Trang, Quận 1, TP.HCM</span>
                </div>
                <div class="contact-item">
                  <i class="bi bi-telephone-fill"></i>
                  <span>0123 456 789</span>
                </div>
                <div class="contact-item">
                  <i class="bi bi-envelope-fill"></i>
                  <span>contact@aosfashion.com</span>
                </div>
              </div>
            </div>
          </div>

          <!-- Quick Links -->
          <div class="col-lg-2 col-md-6 mb-4">
            <div class="footer-section">
              <h5 class="footer-title">Sản phẩm</h5>
              <ul class="footer-links">
                <li><router-link to="/products">Tất cả sản phẩm</router-link></li>
                <li>
                  <router-link to="/products?category=nam">Thời trang nam</router-link>
                </li>
                <li>
                  <router-link to="/products?category=nu">Thời trang nữ</router-link>
                </li>
                <li>
                  <router-link to="/products?category=sale"
                    >Sản phẩm giảm giá</router-link
                  >
                </li>
                <li>
                  <router-link to="/products?category=new">Hàng mới về</router-link>
                </li>
              </ul>
            </div>
          </div>

          <!-- Customer Service -->
          <div class="col-lg-2 col-md-6 mb-4">
            <div class="footer-section">
              <h5 class="footer-title">Hỗ trợ</h5>
              <ul class="footer-links">
                <li><router-link to="/help">Trung tâm trợ giúp</router-link></li>
                <li><router-link to="/shipping">Chính sách giao hàng</router-link></li>
                <li><router-link to="/returns">Đổi trả & Hoàn tiền</router-link></li>
                <li><router-link to="/size-guide">Hướng dẫn chọn size</router-link></li>
                <li><router-link to="/contact">Liên hệ</router-link></li>
              </ul>
            </div>
          </div>

          <!-- Account -->
          <div class="col-lg-2 col-md-6 mb-4">
            <div class="footer-section">
              <h5 class="footer-title">Tài khoản</h5>
              <ul class="footer-links">
                <li><router-link to="/profile">Thông tin cá nhân</router-link></li>
                <li><router-link to="/orders">Đơn hàng của tôi</router-link></li>
                <li><router-link to="/wishlist">Danh sách yêu thích</router-link></li>
                <li><router-link to="/addresses">Địa chỉ giao hàng</router-link></li>
                <li><router-link to="/loyalty">Điểm tích lũy</router-link></li>
              </ul>
            </div>
          </div>

          <!-- Newsletter -->
          <div class="col-lg-2 col-md-12 mb-4">
            <div class="footer-section">
              <h5 class="footer-title">Theo dõi chúng tôi</h5>
              <p class="newsletter-text">Đăng ký nhận thông tin khuyến mãi mới nhất</p>

              <!-- Newsletter Form -->
              <div class="newsletter-form">
                <div class="input-group">
                  <input
                    type="email"
                    class="form-control newsletter-input"
                    placeholder="Email của bạn"
                    v-model="email"
                  />
                  <button class="newsletter-btn" @click="subscribeNewsletter">
                    <i class="bi bi-send-fill"></i>
                  </button>
                </div>
              </div>

              <!-- Social Links -->
              <div class="social-links">
                <a href="#" class="social-link facebook">
                  <i class="bi bi-facebook"></i>
                </a>
                <a href="#" class="social-link instagram">
                  <i class="bi bi-instagram"></i>
                </a>
                <a href="#" class="social-link tiktok">
                  <i class="bi bi-tiktok"></i>
                </a>
                <a href="#" class="social-link youtube">
                  <i class="bi bi-youtube"></i>
                </a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Footer Bottom -->
    <div class="footer-bottom">
      <div class="container">
        <div class="row align-items-center">
          <div class="col-md-6">
            <p class="copyright">
              © 2025 <strong>AOS Fashion</strong>. Tất cả quyền được bảo lưu.
            </p>
          </div>
          <div class="col-md-6">
            <div class="footer-bottom-links">
              <router-link to="/privacy">Chính sách bảo mật</router-link>
              <router-link to="/terms">Điều khoản sử dụng</router-link>
              <router-link to="/cookies">Cookie Policy</router-link>
            </div>
          </div>
        </div>
      </div>
    </div>
  </footer>
</template>

<script setup>
import { ref } from "vue";
import { notification } from "ant-design-vue";

const email = ref("");

const subscribeNewsletter = () => {
  if (!email.value) {
    notification.warning({
      message: "Thông báo",
      description: "Vui lòng nhập email của bạn",
    });
    return;
  }

  // Validate email
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  if (!emailRegex.test(email.value)) {
    notification.error({
      message: "Email không hợp lệ",
      description: "Vui lòng nhập đúng định dạng email",
    });
    return;
  }

  // TODO: Implement newsletter subscription API
  notification.success({
    message: "Đăng ký thành công!",
    description: "Cảm ơn bạn đã đăng ký nhận thông tin từ AOS Fashion",
  });
  email.value = "";
};
</script>

<style scoped>
.modern-footer {
  background: linear-gradient(135deg, #2c3e50 0%, #34495e 100%);
  color: white;
}

.footer-content {
  padding: 3rem 0 2rem 0;
}

.footer-brand {
  margin-bottom: 2rem;
}

.footer-logo {
  max-height: 120px;
  width: auto;
  margin-bottom: 1rem;
  filter: brightness(1.1);
}

.brand-description {
  color: rgba(255, 255, 255, 0.8);
  line-height: 1.6;
  margin-bottom: 1.5rem;
  font-size: 0.95rem;
}

.contact-info {
  display: flex;
  flex-direction: column;
  gap: 0.8rem;
}

.contact-item {
  display: flex;
  align-items: center;
  gap: 0.8rem;
  color: rgba(255, 255, 255, 0.9);
  font-size: 0.9rem;
}

.contact-item i {
  color: #3498db;
  font-size: 1.1rem;
  width: 20px;
  text-align: center;
}

.footer-section {
  margin-bottom: 2rem;
}

.footer-title {
  color: white;
  font-size: 1.2rem;
  font-weight: 600;
  margin-bottom: 1.5rem;
  position: relative;
  padding-bottom: 0.5rem;
}

.footer-title::after {
  content: "";
  position: absolute;
  bottom: 0;
  left: 0;
  width: 40px;
  height: 2px;
  background: linear-gradient(90deg, #3498db, #2980b9);
  border-radius: 1px;
}

.footer-links {
  list-style: none;
  padding: 0;
  margin: 0;
}

.footer-links li {
  margin-bottom: 0.8rem;
}

.footer-links a {
  color: rgba(255, 255, 255, 0.8);
  text-decoration: none;
  font-size: 0.9rem;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  position: relative;
  padding-left: 0;
}

.footer-links a:hover {
  color: #3498db;
  padding-left: 10px;
}

.footer-links a::before {
  content: "→";
  position: absolute;
  left: -15px;
  opacity: 0;
  transition: all 0.3s ease;
}

.footer-links a:hover::before {
  opacity: 1;
  left: -10px;
}

.newsletter-text {
  color: rgba(255, 255, 255, 0.8);
  font-size: 0.9rem;
  margin-bottom: 1rem;
  line-height: 1.5;
}

.newsletter-form {
  margin-bottom: 1.5rem;
}

.input-group {
  display: flex;
  border-radius: 25px;
  overflow: hidden;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
}

.newsletter-input {
  border: none;
  padding: 0.8rem 1rem;
  background: rgba(255, 255, 255, 0.95);
  color: #2c3e50;
  flex: 1;
  font-size: 0.9rem;
}

.newsletter-input:focus {
  outline: none;
  background: white;
  box-shadow: none;
}

.newsletter-input::placeholder {
  color: #95a5a6;
}

.newsletter-btn {
  background: linear-gradient(135deg, #3498db, #2980b9);
  border: none;
  padding: 0.8rem 1.2rem;
  color: white;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
}

.newsletter-btn:hover {
  background: linear-gradient(135deg, #2980b9, #1f618d);
  transform: translateY(-1px);
}

.newsletter-btn i {
  font-size: 1rem;
}

.social-links {
  display: flex;
  gap: 1rem;
  flex-wrap: wrap;
}

.social-link {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 40px;
  height: 40px;
  border-radius: 50%;
  color: white;
  text-decoration: none;
  transition: all 0.3s ease;
  font-size: 1.2rem;
}

.social-link.facebook {
  background: #3b5998;
}

.social-link.instagram {
  background: linear-gradient(
    45deg,
    #f09433 0%,
    #e6683c 25%,
    #dc2743 50%,
    #cc2366 75%,
    #bc1888 100%
  );
}

.social-link.tiktok {
  background: #000000;
}

.social-link.youtube {
  background: #ff0000;
}

.social-link:hover {
  transform: translateY(-3px);
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.3);
}

.footer-bottom {
  background: rgba(0, 0, 0, 0.3);
  padding: 1.5rem 0;
  border-top: 1px solid rgba(255, 255, 255, 0.1);
}

.copyright {
  margin: 0;
  color: rgba(255, 255, 255, 0.8);
  font-size: 0.9rem;
}

.footer-bottom-links {
  display: flex;
  gap: 2rem;
  justify-content: flex-end;
  flex-wrap: wrap;
}

.footer-bottom-links a {
  color: rgba(255, 255, 255, 0.7);
  text-decoration: none;
  font-size: 0.85rem;
  transition: color 0.3s ease;
}

.footer-bottom-links a:hover {
  color: #3498db;
}

/* Responsive Design */
@media (max-width: 768px) {
  .footer-content {
    padding: 2rem 0 1.5rem 0;
  }

  .footer-logo {
    max-height: 80px;
  }

  .footer-title {
    font-size: 1.1rem;
    margin-bottom: 1rem;
  }

  .social-links {
    justify-content: center;
    margin-top: 1rem;
  }

  .footer-bottom-links {
    justify-content: center;
    margin-top: 1rem;
    gap: 1rem;
  }

  .footer-bottom .row {
    text-align: center;
  }

  .contact-info {
    align-items: center;
  }

  .contact-item {
    justify-content: center;
  }
}

@media (max-width: 576px) {
  .footer-content {
    padding: 1.5rem 0 1rem 0;
  }

  .footer-brand {
    text-align: center;
    margin-bottom: 2rem;
  }

  .footer-section {
    text-align: center;
    margin-bottom: 2rem;
  }

  .footer-title::after {
    left: 50%;
    transform: translateX(-50%);
  }

  .footer-links a {
    justify-content: center;
  }

  .footer-links a:hover {
    padding-left: 0;
  }

  .footer-links a::before {
    display: none;
  }

  .input-group {
    flex-direction: column;
    border-radius: 12px;
  }

  .newsletter-input {
    border-radius: 12px 12px 0 0;
  }

  .newsletter-btn {
    border-radius: 0 0 12px 12px;
  }

  .footer-bottom-links {
    flex-direction: column;
    gap: 0.5rem;
  }
}

/* Animation for smooth appearance */
@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(30px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.footer-section {
  animation: fadeInUp 0.6s ease forwards;
}

.footer-section:nth-child(1) {
  animation-delay: 0.1s;
}
.footer-section:nth-child(2) {
  animation-delay: 0.2s;
}
.footer-section:nth-child(3) {
  animation-delay: 0.3s;
}
.footer-section:nth-child(4) {
  animation-delay: 0.4s;
}
.footer-section:nth-child(5) {
  animation-delay: 0.5s;
}
</style>
