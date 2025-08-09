<template>
  <nav class="navbar navbar-expand-lg modern-navbar sticky-top">
    <div class="container-fluid px-4">
      <RouterLink class="navbar-brand modern-brand" to="/">
        <div class="brand-container">
          <img src="/src/assets/imgs/logo.png" class="brand-logo" alt="Logo" />
          <span class="brand-text">ALL IN ONE</span>
        </div>
      </RouterLink>
      <button
        class="navbar-toggler modern-toggler"
        type="button"
        data-bs-toggle="collapse"
        data-bs-target="#navbarSupportedContent"
        aria-controls="navbarSupportedContent"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span class="toggler-icon"></span>
        <span class="toggler-icon"></span>
        <span class="toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
          <li class="nav-item">
            <RouterLink
              class="nav-link modern-nav-link"
              aria-current="page"
              to="/products"
            >
              <i class="bi bi-grid-fill me-2"></i>Sản phẩm
            </RouterLink>
          </li>
          <li class="nav-item">
            <RouterLink
              class="nav-link modern-nav-link"
              aria-current="page"
              to="/Customizer"
            >
              <i class="bi bi-palette-fill me-2"></i>Customizer
            </RouterLink>
          </li>
          <li class="nav-item">
            <RouterLink
              class="nav-link modern-nav-link"
              aria-current="page"
              to="/order-list"
            >
              <i class="bi bi-list-check me-2"></i>Đơn hàng của bạn
            </RouterLink>
          </li>
        </ul>
        <ul class="navbar-nav ms-auto align-items-center">
          <!-- Cart Icon -->
          <li class="nav-item me-3">
            <RouterLink
              class="nav-link position-relative modern-icon-link"
              aria-current="page"
              to="/cart"
            >
              <i class="bi bi-bag-heart-fill fs-5"></i>
              <span
                class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-gradient-danger modern-badge"
              >
                {{ cartSize }}
              </span>
            </RouterLink>
          </li>

          <!-- Notification Icon -->
          <li class="nav-item dropdown me-3">
            <a
              class="nav-link dropdown-toggle modern-icon-link position-relative"
              href="#"
              role="button"
              data-bs-toggle="dropdown"
              aria-expanded="false"
            >
              <i class="bi bi-bell-fill fs-5"></i>
              <span
                class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-gradient-primary modern-badge"
              >
                {{ messageCount }}
              </span>
            </a>
            <ul
              class="dropdown-menu dropdown-menu-end modern-dropdown notification-dropdown"
            >
              <li class="dropdown-header">
                <i class="bi bi-bell me-2"></i>Notifications
              </li>
              <hr class="dropdown-divider" />
              <li
                v-for="item in messages"
                :key="item.id"
                class="dropdown-item modern-notification-item"
              >
                <span v-html="formatCell(item)"></span>
              </li>
              <hr class="dropdown-divider" v-if="messages.length" />
              <li class="dropdown-footer text-center">
                <a href="#" class="btn btn-link btn-sm">View All</a>
              </li>
            </ul>
          </li>

          <!-- User Profile -->
          <li class="nav-item dropdown">
            <a
              class="nav-link dropdown-toggle modern-user-link"
              href="#"
              role="button"
              data-bs-toggle="dropdown"
              aria-expanded="false"
            >
              <div class="user-profile-container" v-if="!isLogged">
                <div class="user-avatar-placeholder">
                  <i class="bi bi-person-fill"></i>
                </div>
                <span class="user-text d-none d-md-inline">Đăng nhập</span>
              </div>
              <div class="user-profile-container" v-else>
                <div class="user-avatar">
                  <img
                    v-if="
                      user?.avatarUrl &&
                      user.avatarUrl !== 'null' &&
                      user.avatarUrl !== ''
                    "
                    :src="user.avatarUrl"
                    :alt="user?.fullname"
                    :crossorigin="
                      user.avatarUrl.includes('googleusercontent.com')
                        ? 'anonymous'
                        : undefined
                    "
                    :referrerpolicy="
                      user.avatarUrl.includes('googleusercontent.com')
                        ? 'no-referrer'
                        : undefined
                    "
                    @error="handleAvatarError"
                  />
                  <img
                    v-else
                    src="https://res.cloudinary.com/da2v8uqir/image/upload/v1754018153/baib6i5rkev8n2gpmswv.jpg"
                    :alt="user?.fullname"
                  />
                </div>
                <div class="user-info d-none d-md-block">
                  <span class="user-name">{{ user?.fullname || "User" }}</span>
                </div>
              </div>
            </a>
            <ul class="dropdown-menu dropdown-menu-end modern-dropdown">
              <li class="dropdown-header user-dropdown-header" v-if="isLogged">
                <div class="user-profile-header">
                  <div class="user-avatar-large">
                    <img
                      v-if="
                        user?.avatarUrl &&
                        user.avatarUrl !== 'null' &&
                        user.avatarUrl !== ''
                      "
                      :src="user.avatarUrl"
                      :alt="user?.fullname"
                      :crossorigin="
                        user.avatarUrl.includes('googleusercontent.com')
                          ? 'anonymous'
                          : undefined
                      "
                      :referrerpolicy="
                        user.avatarUrl.includes('googleusercontent.com')
                          ? 'no-referrer'
                          : undefined
                      "
                      @error="handleAvatarError"
                    />
                    <img
                      v-else
                      src="https://res.cloudinary.com/da2v8uqir/image/upload/v1754018153/baib6i5rkev8n2gpmswv.jpg"
                      :alt="user?.fullname"
                    />
                  </div>
                  <div class="user-info-header">
                    <div class="user-fullname">{{ user?.fullname || "User" }}</div>
                    <small class="user-email">{{ user?.email }}</small>
                  </div>
                </div>
              </li>
              <hr class="dropdown-divider" v-if="isLogged" />
              <li v-if="isAdmin">
                <RouterLink
                  class="dropdown-item modern-dropdown-item"
                  to="/admin/Accounts"
                >
                  <i class="bi bi-shield-check me-2"></i>Trang quản trị
                </RouterLink>
              </li>
              <li v-if="isAdmin">
                <button
                  class="dropdown-item modern-dropdown-item"
                  @click="handleUpdateData"
                >
                  <i class="bi bi-shield-check me-2"></i> Đồng bộ dữ liệu
                </button>
              </li>
              <li v-if="isLogged">
                <RouterLink class="dropdown-item modern-dropdown-item" to="/UserInfo">
                  <i class="bi bi-gear me-2"></i>Cài đặt tài khoản
                </RouterLink>
              </li>
              <hr class="dropdown-divider" />
              <li v-if="isLogged">
                <button
                  class="dropdown-item modern-dropdown-item text-danger"
                  @click="logout"
                >
                  <i class="bi bi-box-arrow-right me-2"></i>Đăng xuất
                </button>
              </li>
              <li v-else>
                <RouterLink class="dropdown-item modern-dropdown-item" to="/login">
                  <i class="bi bi-box-arrow-in-right me-2"></i>Đăng nhập
                </RouterLink>
              </li>
              <li v-if="!isLogged">
                <RouterLink class="dropdown-item modern-dropdown-item" to="/register">
                  <i class="bi bi-person-plus me-2"></i>Đăng ký
                </RouterLink>
              </li>
            </ul>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>
<script setup>
// ...existing code...
import { computed, onMounted, watch, ref } from "vue";
import api, { authService } from "../../Configs/api";
import { notification } from "ant-design-vue";
const isLogged = computed(() => {
  return !!authService.isLogged();
});
const user = computed(() => {
  return authService.getUserHeader();
});

const isAdmin = computed(() => {
  return !!authService.isAdmin();
});
const cartSize = computed(() => {
  return authService.getCartSize();
});
const messages = ref([]);
const messageCount = ref(0);
const logout = () => {
  authService.logout();
};
async function handleUpdateData() {
  const userReload = await authService.getProfile();
  authService.setUserHeader(userReload);
  notification.success({
    message: "Cập nhật thành công",
    description: `${userReload} Người dùng đã được cập nhật thành công.`,
    duration: 3,
  });
}
function formatCell(item) {
  switch (item.keyMessage) {
    case "MessagePromotion":
      return `<span class="text-success"><i class="bi bi-megaphone-fill"></i> ${item.notification}</span>`;

    case "MessageOrder":
      return `<span class="text-primary"><i class="bi bi-bag-check-fill"></i> ${item.notification}</span>`;

    case "MessageReturn":
      return `<span class="text-warning"><i class="bi bi-arrow-counterclockwise"></i> ${item.notification}</span>`;

    case "MessageCancle":
      return `<span class="text-danger"><i class="bi bi-x-circle-fill"></i> ${item.notification}</span>`;

    case "MessageEwallet":
      return `<span class="text-info"><i class="bi bi-wallet-fill"></i> ${item.notification}</span>`;

    default:
      return `<span>${item.notification}</span>`;
  }
}
async function getNotification() {
  try {
    const response = await api.get(`/user/Message`);
    messages.value = response.data.content;
    messageCount.value = response.data.totalElements;
  } catch (error) {
    console.error("Error fetching notifications:", error);
  }
}
onMounted(getNotification);

const handleAvatarError = (event) => {
  event.target.style.display = "none";
  const fallback = event.target.nextElementSibling;
  if (fallback) {
    fallback.style.display = "block";
  }
};
</script>
<style scoped>
/* Modern Navbar Styling */
.modern-navbar {
  background: white;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  border-bottom: 1px solid rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
}

.modern-navbar:hover {
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
}

/* Brand Styling */
.modern-brand {
  text-decoration: none !important;
  color: #333 !important;
  font-weight: 700;
  transition: all 0.3s ease;
  border-radius: 8px;
  padding: 5px;
}

.modern-brand:hover {
  color: #667eea !important;
  background: rgba(102, 126, 234, 0.1);
}

.brand-container {
  display: flex;
  align-items: center;
  gap: 12px;
}

.brand-logo {
  height: 50px;
  width: auto;
  border-radius: 8px;
  transition: all 0.3s ease;
}

.brand-text {
  font-size: 1.5rem;
  font-weight: 700;
  background: linear-gradient(45deg, #667eea, #764ba2);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.modern-brand:hover .brand-logo {
  transform: scale(1.02);
}

/* Modern Toggler */
.modern-toggler {
  border: none;
  padding: 8px 12px;
  background: rgba(0, 0, 0, 0.1);
  border-radius: 8px;
  transition: all 0.3s ease;
}

.modern-toggler:focus {
  box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.2);
}

.toggler-icon {
  display: block;
  width: 22px;
  height: 2px;
  background: #333;
  margin: 4px 0;
  border-radius: 2px;
  transition: all 0.3s ease;
}

/* Navigation Links */
.modern-nav-link {
  color: #555 !important;
  font-weight: 500;
  padding: 12px 16px !important;
  border-radius: 8px;
  margin: 0 4px;
  transition: all 0.3s ease;
}

.modern-nav-link:hover {
  color: #667eea !important;
  background: rgba(102, 126, 234, 0.1);
}

.modern-nav-link.router-link-active {
  background: rgba(102, 126, 234, 0.1);
  color: #667eea !important;
}

/* Icon Links */
.modern-icon-link {
  color: #555 !important;
  padding: 12px !important;
  border-radius: 8px;
  transition: all 0.3s ease;
}

.modern-icon-link:hover {
  color: #667eea !important;
  background: rgba(102, 126, 234, 0.1);
}

/* Modern Badges */
.modern-badge {
  font-size: 0.7rem;
  padding: 2px 6px;
  min-width: 16px;
  height: 16px;
  line-height: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
  animation: pulse-badge 2s infinite;
}

.bg-gradient-danger {
  background: linear-gradient(45deg, #ff416c, #ff4b2b) !important;
}

.bg-gradient-primary {
  background: linear-gradient(45deg, #667eea, #764ba2) !important;
}

@keyframes pulse-badge {
  0%,
  100% {
    transform: scale(1);
  }

  50% {
    transform: scale(1.05);
  }
}

/* User Profile */
.modern-user-link {
  text-decoration: none !important;
  padding: 8px 12px !important;
  border-radius: 8px;
  transition: all 0.3s ease;
  color: #555 !important;
}

.modern-user-link:hover {
  background: rgba(102, 126, 234, 0.1);
  color: #667eea !important;
}

/* Hide dropdown arrows */
.dropdown-toggle::after {
  display: none !important;
}

.user-profile-container {
  display: flex;
  align-items: center;
  gap: 8px;
  min-height: 36px;
}

/* Avatar */
.user-avatar {
  position: relative;
  width: 32px;
  height: 32px;
  border-radius: 50%;
  overflow: hidden;
  border: 2px solid #e9ecef;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
}

.user-avatar:hover {
  border-color: #667eea;
}

.user-avatar img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.online-indicator {
  position: absolute;
  bottom: 1px;
  right: 1px;
  width: 8px;
  height: 8px;
  background: #28a745;
  border: 2px solid white;
  border-radius: 50%;
}

.user-avatar-placeholder {
  width: 32px;
  height: 32px;
  border-radius: 50%;
  background: #f8f9fa;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #6c757d;
  font-size: 14px;
  border: 2px solid #e9ecef;
  transition: all 0.3s ease;
  flex-shrink: 0;
}

.user-avatar-placeholder:hover {
  background: #e9ecef;
  border-color: #667eea;
}

.user-info {
  display: flex;
  flex-direction: column;
  gap: 1px;
}

.user-name {
  font-weight: 600;
  color: #333;
  font-size: 13px;
  line-height: 1.2;
  max-width: 100px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.user-status {
  color: #28a745;
  font-size: 10px;
  font-weight: 500;
  text-transform: uppercase;
  letter-spacing: 0.3px;
}

.user-text {
  font-weight: 500;
  color: #555;
  font-size: 13px;
}

/* Modern Dropdown */
.modern-dropdown {
  border-radius: 12px;
  border: none;
  box-shadow: 0 8px 25px rgba(0, 0, 0, 0.1);
  padding: 0;
  margin-top: 8px;
  min-width: 240px;
  background: white;
  overflow: hidden;
  position: absolute;
  z-index: 1050;
  max-width: 300px;
  right: 0;
  left: auto;
}

.dropdown-header {
  padding: 12px 16px;
  font-weight: 600;
  color: #495057;
  border-bottom: 1px solid #e9ecef;
  margin-bottom: 4px;
}

/* User Dropdown Header Styling */
.user-dropdown-header {
  padding: 16px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-bottom: none;
  margin-bottom: 0;
}

.user-profile-header {
  display: flex;
  align-items: center;
  gap: 12px;
}

.user-avatar-large {
  position: relative;
  width: 48px;
  height: 48px;
  border-radius: 50%;
  overflow: hidden;
  border: 3px solid rgba(255, 255, 255, 0.3);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.user-avatar-large img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.online-indicator-large {
  position: absolute;
  bottom: 2px;
  right: 2px;
  width: 12px;
  height: 12px;
  background: #28a745;
  border: 3px solid white;
  border-radius: 50%;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
}

.user-info-header {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 2px;
}

.user-fullname {
  color: white;
  font-weight: 700;
  font-size: 16px;
  line-height: 1.2;
  margin: 0;
}

.user-email {
  color: rgba(255, 255, 255, 0.8);
  font-size: 12px;
  font-weight: 400;
  margin: 0;
}

.user-status-badge {
  display: inline-block;
  background: rgba(40, 167, 69, 0.9);
  color: white;
  font-size: 9px;
  font-weight: 600;
  padding: 2px 6px;
  border-radius: 8px;
  text-transform: uppercase;
  letter-spacing: 0.5px;
  margin-top: 4px;
  align-self: flex-start;
}

.dropdown-footer {
  padding: 8px 16px;
  border-top: 1px solid #e9ecef;
  margin-top: 4px;
}

.modern-dropdown-item {
  padding: 12px 20px;
  font-size: 14px;
  font-weight: 500;
  transition: all 0.3s ease;
  border-radius: 0;
  margin: 0;
  display: flex;
  align-items: center;
  border-bottom: 1px solid rgba(0, 0, 0, 0.05);
}

.modern-dropdown-item:last-child {
  border-bottom: none;
}

.modern-dropdown-item:hover {
  background: #f8f9fa;
  color: #667eea;
}

.modern-dropdown-item.text-danger:hover {
  background: #f8d7da;
  color: #dc3545;
}

/* Notification Dropdown */
.notification-dropdown {
  min-width: 300px;
  max-height: 400px;
  overflow-y: auto;
  right: 0;
  left: auto;
}

/* Responsive positioning */
@media (max-width: 767.98px) {
  .modern-dropdown {
    min-width: 260px;
    max-width: 90vw;
    right: 0;
    left: auto;
    transform: translateX(0);
  }

  .notification-dropdown {
    min-width: 280px;
    max-width: 90vw;
    right: 0;
    left: auto;
  }
}

.modern-notification-item {
  white-space: normal;
  padding: 12px 16px;
  border-bottom: 1px solid rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
  border-radius: 6px;
  margin: 2px 8px;
}

.modern-notification-item:hover {
  background: #f8f9fa;
}

/* Responsive Design */
@media (max-width: 991.98px) {
  .modern-navbar {
    padding: 0.5rem 1rem;
  }

  .brand-logo {
    height: 40px;
  }

  .brand-text {
    font-size: 1.25rem;
  }

  .user-avatar,
  .user-avatar-placeholder {
    width: 32px;
    height: 32px;
  }

  .online-indicator {
    width: 8px;
    height: 8px;
  }
}

@media (max-width: 767.98px) {
  .modern-dropdown {
    min-width: 280px;
  }

  .notification-dropdown {
    min-width: 300px;
  }
}

/* Footer and other existing styles */
#footer {
  padding: 60px 0;
  background-color: rgb(28, 28, 28);
}

.table-cell {
  padding: 1rem 0.8rem;
  text-align: center;
  vertical-align: middle;
  border: none;
  color: #495057;
  font-size: 0.9rem;
  min-width: 120px;
  word-wrap: break-word;
  overflow-wrap: break-word;
}

.cell-content {
  font-weight: 500;
  max-width: 250px;
  display: inline-block;
  word-wrap: break-word;
  overflow-wrap: break-word;
  white-space: normal;
  line-height: 1.4;
}

section .section-title {
  text-align: center;
  color: #007b5e;
  margin-bottom: 50px;
  text-transform: uppercase;
}

#footer h5 {
  padding-left: 10px;
  border-left: 3px solid #eeeeee;
  padding-bottom: 6px;
  margin-bottom: 20px;
  color: #ffffff;
}

#footer a {
  color: #ffffff;
  text-decoration: none !important;
  background-color: transparent;
  -webkit-text-decoration-skip: objects;
}

#footer ul.social li {
  padding: 3px 0;
}

#footer ul.social li a i {
  margin-right: 5px;
  font-size: 25px;
  -webkit-transition: 0.5s all ease;
  -moz-transition: 0.5s all ease;
  transition: 0.5s all ease;
}

#footer ul.social li:hover a i {
  font-size: 30px;
  margin-top: -10px;
}

#footer ul.social li a,
#footer ul.quick-links li a {
  color: #ffffff;
}

#footer ul.social li a:hover {
  color: #eeeeee;
}

#footer ul.quick-links li {
  padding: 3px 0;
  -webkit-transition: 0.5s all ease;
  -moz-transition: 0.5s all ease;
  transition: 0.5s all ease;
}

#footer ul.quick-links li:hover {
  padding: 3px 0;
  margin-left: 5px;
  font-weight: 700;
}

#footer ul.quick-links li a i {
  margin-right: 5px;
}

#footer ul.quick-links li:hover a i {
  font-weight: 700;
}

@media (max-width: 767px) {
  #footer h5 {
    padding-left: 0;
    border-left: transparent;
    padding-bottom: 0px;
    margin-bottom: 10px;
  }
}

/* Search Box Styling */
.searchBox {
  display: flex;
  width: 530px;
  align-items: center;
  justify-content: space-between;
  gap: 8px;
  background: #cecccc;
  border-radius: 50px;
  position: relative;
}

.searchButton {
  color: white;
  position: absolute;
  right: 8px;
  width: 50px;
  height: 50px;
  border-radius: 50%;
  background: var(--gradient-2, linear-gradient(90deg, #2af598 0%, #009efd 100%));
  border: 0;
  display: inline-block;
  transition: all 300ms cubic-bezier(0.23, 1, 0.32, 1);
}

.searchButton:hover {
  color: #fff;
  background-color: #1a1a1a;
  box-shadow: rgba(0, 0, 0, 0.5) 0 10px 20px;
  transform: translateY(-3px);
}

.searchButton:active {
  box-shadow: none;
  transform: translateY(0);
}

.searchInput {
  border: none;
  background: none;
  outline: none;
  color: white;
  font-size: 15px;
  padding: 24px 46px 24px 26px;
}

/* Footer */
#footer {
  padding: 60px 0;
  background-color: rgb(28, 28, 28);
}

.table-cell {
  padding: 1rem 0.8rem;
  text-align: center;
  vertical-align: middle;
  border: none;
  color: #495057;
  font-size: 0.9rem;
  min-width: 120px;
  word-wrap: break-word;
  overflow-wrap: break-word;
}

.cell-content {
  font-weight: 500;
  max-width: 250px;
  display: inline-block;
  word-wrap: break-word;
  overflow-wrap: break-word;
  white-space: normal;
  line-height: 1.4;
}

section .section-title {
  text-align: center;
  color: #007b5e;
  margin-bottom: 50px;
  text-transform: uppercase;
}

#footer h5 {
  padding-left: 10px;
  border-left: 3px solid #eeeeee;
  padding-bottom: 6px;
  margin-bottom: 20px;
  color: #ffffff;
}

#footer a {
  color: #ffffff;
  text-decoration: none !important;
  background-color: transparent;
  -webkit-text-decoration-skip: objects;
}

#footer ul.social li {
  padding: 3px 0;
}

.notification-dropdown {
  width: 350px;
  max-height: 400px;
  overflow-y: auto;
  border-radius: 12px;
  font-size: 1rem;
}

.notification-item {
  white-space: normal;
  padding: 12px 16px;
  border-bottom: 1px solid #f0f0f0;
}

.notification-item:last-child {
  border-bottom: none;
}

.notification-item:hover {
  background-color: #f8f9fa;
  cursor: pointer;
}

#footer ul.social li a i {
  margin-right: 5px;
  font-size: 25px;
  -webkit-transition: 0.5s all ease;
  -moz-transition: 0.5s all ease;
  transition: 0.5s all ease;
}

#footer ul.social li:hover a i {
  font-size: 30px;
  margin-top: -10px;
}

#footer ul.social li a,
#footer ul.quick-links li a {
  color: #ffffff;
}

#footer ul.social li a:hover {
  color: #eeeeee;
}

#footer ul.quick-links li {
  padding: 3px 0;
  -webkit-transition: 0.5s all ease;
  -moz-transition: 0.5s all ease;
  transition: 0.5s all ease;
}

#footer ul.quick-links li:hover {
  padding: 3px 0;
  margin-left: 5px;
  font-weight: 700;
}

#footer ul.quick-links li a i {
  margin-right: 5px;
}

#footer ul.quick-links li:hover a i {
  font-weight: 700;
}

@media (max-width: 767px) {
  #footer h5 {
    padding-left: 0;
    border-left: transparent;
    padding-bottom: 0px;
    margin-bottom: 10px;
  }
}

/* From Uiverse.io by OnlyCodeChannel */
.searchBox {
  display: flex;
  width: 530px;
  align-items: center;
  justify-content: space-between;
  gap: 8px;
  background: #cecccc;
  border-radius: 50px;
  position: relative;
}

.searchButton {
  color: white;
  position: absolute;
  right: 8px;
  width: 50px;
  height: 50px;
  border-radius: 50%;
  background: var(--gradient-2, linear-gradient(90deg, #2af598 0%, #009efd 100%));
  border: 0;
  display: inline-block;
  transition: all 300ms cubic-bezier(0.23, 1, 0.32, 1);
}

/*hover effect*/
.searchButton:hover {
  color: #fff;
  background-color: #1a1a1a;
  box-shadow: rgba(0, 0, 0, 0.5) 0 10px 20px;
  transform: translateY(-3px);
}

/*button pressing effect*/
.searchButton:active {
  box-shadow: none;
  transform: translateY(0);
}

.searchInput {
  border: none;
  background: none;
  outline: none;
  color: white;
  font-size: 15px;
  padding: 24px 46px 24px 26px;
}
</style>
