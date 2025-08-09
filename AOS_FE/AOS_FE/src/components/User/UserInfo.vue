<template>
  <!-- Header Section -->
  <div class="page-header">
    <div class="header-content">
      <h1 class="page-title">Cài đặt tài khoản</h1>
      <p class="page-subtitle">WEAR WHAT MAKES YOU FEEL CONFIDENT</p>
    </div>
  </div>

  <!-- Main Container -->
  <div class="main-container">
    <!-- Profile Card -->
    <div class="profile-card">
      <div class="profile-header">
        <div class="avatar-section">
          <div class="avatar-wrapper">
            <img
              v-if="user?.avatarUrl && user.avatarUrl !== 'null' && user.avatarUrl !== ''"
              :src="user.avatarUrl"
              alt="avatar"
              class="avatar-img"
              :crossorigin="
                user.avatarUrl.includes('googleusercontent.com') ? 'anonymous' : undefined
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
              alt="default avatar"
              class="avatar-img"
            />
            <button
              class="avatar-upload-btn"
              @click="openPopupAvatar(true)"
              title="Đổi ảnh đại diện"
            >
              <i class="bi bi-camera-fill"></i>
            </button>
            <input
              type="file"
              ref="avatarInput"
              @change="handleAvatarChange"
              accept="image/*"
              hidden
            />
          </div>
          <div class="profile-info">
            <h3 class="username">{{ user.fullname }}</h3>
            <p class="user-email">{{ user.email }}</p>
            <span class="member-badge">
              <i class="bi bi-award"></i>
              {{ user.userRank || "Thành viên" }}
            </span>
            <span class="member-badge">
              <i class="bi bi-calendar"></i>
              Thành viên: {{ daysDiff + " ngày" || "Chưa xác định" }}
            </span>
          </div>
        </div>
      </div>

      <!-- Stats Section -->
      <div class="stats-section">
        <div class="stat-card">
          <div class="stat-icon wallet"><i class="bi bi-wallet2"></i></div>
          <div class="stat-content">
            <h4 class="stat-value">{{ user.totalSpent.toLocaleString() }}₫</h4>
            <p class="stat-label">Tổng chi tiêu</p>
          </div>
        </div>
        <div class="stat-card">
          <div class="stat-icon wallet"><i class="bi bi-wallet2"></i></div>
          <div class="stat-content">
            <h4 class="stat-value">{{ user.averageOrderValue.toLocaleString() }}₫</h4>
            <p class="stat-label">Trung bình chi tiêu cho các đơn hàng</p>
          </div>
        </div>
        <div class="stat-card">
          <div class="stat-icon points"><i class="bi bi-star-fill"></i></div>
          <div class="stat-content">
            <h4 class="stat-value">{{ user.loyaltyPoint }}</h4>
            <p class="stat-label">Điểm tích lũy</p>
          </div>
        </div>
        <div class="stat-card">
          <div class="stat-icon orders"><i class="bi bi-bag-fill"></i></div>
          <div class="stat-content">
            <h4 class="stat-value">{{ user.totalOrder || 0 }}</h4>
            <p class="stat-label">Đơn hàng</p>
          </div>
        </div>
      </div>
    </div>

    <!-- Action Cards -->
    <div class="action-cards">
      <div class="action-card" @click="openPopupTaiKhoan">
        <div class="action-icon profile"><i class="bi bi-person-fill"></i></div>
        <div class="action-content">
          <h3>Thông tin tài khoản</h3>
          <p>Cập nhật thông tin cá nhân</p>
        </div>
        <span class="action-arrow">→</span>
      </div>

      <div class="action-card" @click="openPopupDoiMatKhau">
        <div class="action-icon security"><i class="bi bi-lock-fill"></i></div>
        <div class="action-content">
          <h3>Đổi mật khẩu</h3>
          <p>Bảo mật tài khoản của bạn</p>
        </div>
        <span class="action-arrow">→</span>
      </div>

      <div class="action-card" @click="openPopupTopUp">
        <div class="action-icon wallet"><i class="bi bi-credit-card-fill"></i></div>
        <div class="action-content">
          <h3>Nạp tiền</h3>
          <p>Thêm tiền vào ví của bạn</p>
        </div>
        <span class="action-arrow">→</span>
      </div>

      <div class="action-card" @click="redirectOrder">
        <div class="action-icon orders"><i class="bi bi-box-seam-fill"></i></div>
        <div class="action-content">
          <h3>Đơn hàng</h3>
          <p>Đơn hàng chờ thanh toán trực tuyến</p>
        </div>
        <span class="action-arrow">→</span>
      </div>

      <div class="action-card" @click="openPopupDiaChi">
        <div class="action-icon address"><i class="bi bi-geo-alt-fill"></i></div>
        <div class="action-content">
          <h3>Địa chỉ nhận hàng</h3>
          <p>Quản lý địa chỉ giao hàng</p>
        </div>
        <span class="action-arrow">→</span>
      </div>
    </div>
  </div>

  <!-- Profile Update Modal -->
  <div v-if="showPopupTaiKhoan" class="modal-overlay">
    <div class="modern-modal">
      <div class="modal-header">
        <h3>Cập nhật thông tin</h3>
        <button class="close-btn" @click="clearDto">×</button>
      </div>
      <form class="modal-form">
        <div class="form-grid">
          <div class="form-group">
            <label>Họ và tên</label>
            <input type="text" v-model="dto.fullname" placeholder="Nhập họ và tên" />
          </div>
          <div class="form-group">
            <label>Số điện thoại</label>
            <input type="text" v-model="dto.phone" placeholder="Nhập số điện thoại" />
          </div>
          <div class="form-group full-width">
            <label>Giới tính</label>
            <div class="radio-group">
              <label class="radio-option">
                <input type="radio" v-model="dto.gender" value="true" />
                <span class="radio-custom">Nam</span>
              </label>
              <label class="radio-option">
                <input type="radio" v-model="dto.gender" value="false" />
                <span class="radio-custom">Nữ</span>
              </label>
            </div>
          </div>
          <div class="form-group full-width">
            <label>Ngày sinh</label>
            <input type="datetime-local" v-model="dto.birthday" />
          </div>
        </div>
        <div class="modal-actions">
          <button type="button" class="btn-secondary" @click="clearDto">Hủy</button>
          <button type="button" class="btn-primary" @click="updateProfile">
            Lưu thay đổi
          </button>
        </div>
      </form>
    </div>
  </div>
  <!-- Avatar Update Modal -->
  <div v-if="showPopupUpdateAvatar" class="modal-overlay">
    <div class="modern-modal avatar-modal">
      <div class="modal-header">
        <h3>
          <i class="bi bi-camera-fill me-2"></i>
          Cập nhật ảnh đại diện
        </h3>
        <button class="close-btn" @click="openPopupAvatar(false)">×</button>
      </div>
      <div class="modal-form avatar-form">
        <div class="upload-container">
          <uploadAvatar
            ref="uploadRef"
            :maxFiles="1"
            :aspectRatio="'1:1'"
            @update:images="handleAvatarUpdate"
            @delete-image="handleAvatarDelete"
            :folderName="'profiles'"
          />
        </div>
        <div class="modal-actions">
          <button type="button" class="btn-secondary" @click="openPopupAvatar(false)">
            <i class="bi bi-x-lg me-2"></i>
            Hủy
          </button>
        </div>
      </div>
    </div>
  </div>

  <!-- Password Change Modal -->
  <div v-if="showPopupDoiMatKhau" class="modal-overlay">
    <div class="modern-modal">
      <div class="modal-header">
        <h3>Đổi mật khẩu</h3>
        <button class="close-btn" @click="showPopupDoiMatKhau = false">×</button>
      </div>
      <form class="modal-form">
        <div class="form-grid single-column">
          <div class="form-group">
            <label>Mật khẩu hiện tại</label>
            <div class="password-input">
              <input
                :type="showPasswordCurrent ? 'text' : 'password'"
                v-model="user.CurrentPassword"
                placeholder="Nhập mật khẩu hiện tại"
              />
              <button
                type="button"
                class="password-toggle"
                @click="togglePasswordCurrent"
              >
                {{ showPasswordCurrent ? "Hide" : "Show" }}
              </button>
            </div>
          </div>

          <div class="form-group">
            <label>Mật khẩu mới</label>
            <div class="password-input">
              <input
                :type="showPasswordNew ? 'text' : 'password'"
                v-model="user.NewPassword"
                placeholder="Nhập mật khẩu mới"
              />
              <button type="button" class="password-toggle" @click="togglePasswordNew">
                {{ showPasswordNew ? "Hide" : "Show" }}
              </button>
            </div>
          </div>

          <div class="form-group">
            <label>Xác nhận mật khẩu</label>
            <div class="password-input">
              <input
                :type="showPasswordConfirm ? 'text' : 'password'"
                v-model="user.ConfirmPassword"
                placeholder="Xác nhận mật khẩu mới"
              />
              <button
                type="button"
                class="password-toggle"
                @click="togglePasswordConfirm"
              >
                {{ showPasswordConfirm ? "Hide" : "Show" }}
              </button>
            </div>
          </div>
        </div>
        <div class="modal-actions">
          <button
            type="button"
            class="btn-secondary"
            @click="showPopupDoiMatKhau = false"
          >
            Hủy
          </button>
          <button type="button" class="btn-primary" @click="changePassword">
            Đổi mật khẩu
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, nextTick } from "vue";
import { useRouter } from "vue-router";
import { authService } from "../../Configs/api";
import { notification } from "ant-design-vue";
import dayjs from "dayjs";
import uploadAvatar from "../Module/upload-images.vue";
const router = useRouter();
// 👤 Dữ liệu người dùng
const user = ref({
  fullname: "",
  email: "",
  phone: "",
  avatarUrl: "",
  userRank: "",
  totalSpent: 0,
  totalOrder: 0,
  loyaltyPoint: 0,
  NewPassword: "",
  ConfirmPassword: "",
  averageOrderValue: 0,
  gender: "",
  birthday: "",
  createdAt: "",
  updateAt: "",
});

// 🔄 Trạng thái popup
const showPopupTaiKhoan = ref(false);
const showPopupDoiMatKhau = ref(false);
const showPopupUpdateAvatar = ref(false);
const uploadRef = ref(null);

// 🧭 Các nút điều hướng
const openPopupAvatar = async (value) => {
  showPopupUpdateAvatar.value = value;

  // Load ảnh avatar khi mở modal và có ảnh (tương tự Form.vue)
  if (
    value &&
    user.value.avatarUrl &&
    user.value.avatarUrl !== "null" &&
    user.value.avatarUrl !== ""
  ) {
    // Sử dụng nextTick để đảm bảo component đã được render
    await nextTick();
    // Thêm một delay nhỏ để đảm bảo component upload đã mount hoàn toàn
    if (uploadRef.value && uploadRef.value.loadFromUrls) {
      console.log("Loading avatar:", user.value.avatarUrl);
      uploadRef.value.loadFromUrls([user.value.avatarUrl]);
    } else {
      console.warn("uploadRef.value hoặc loadFromUrls không tồn tại");
    }
  }
};
const openPopupTaiKhoan = () => {
  showPopupTaiKhoan.value = true;
};
const openPopupDoiMatKhau = () => {
  showPopupDoiMatKhau.value = true;
};
const openPopupTopUp = () => {
  router.push({ name: "wallet" });
};
const openPopupDiaChi = () => {
  router.push({ name: "shippingaddress" });
};
const redirectOrder = () => {
  router.push({ name: "user-orders" });
};
const showPasswordNew = ref(false);
const showPasswordConfirm = ref(false);
const showPasswordCurrent = ref(false);

const togglePasswordNew = () => {
  showPasswordNew.value = !showPasswordNew.value;
};

const togglePasswordConfirm = () => {
  showPasswordConfirm.value = !showPasswordConfirm.value;
};
const togglePasswordCurrent = () => {
  showPasswordCurrent.value = !showPasswordCurrent.value;
};
const dto = ref({
  fullname: "",
  avatarUrl: "",
  phone: "",
  gender: "",
  birthday: "",
});
// ✅ Lấy thông tin người dùng từ API qua authService
const daysDiff = ref(0);
onMounted(async () => {
  await nextTick(); // Đợi DOM được render như Form.vue
  try {
    const data = await authService.getProfile();
    console.log("Thông tin người dùng:", data);
    // Format ngày tạo tài khoản - tính số ngày từ khi tạo
    if (data.createdAt) {
      const createdDate = dayjs(data.createdAt);
      const today = dayjs();
      daysDiff.value = today.diff(createdDate, "day");
    }

    user.value = { ...user.value, ...data };
    dto.value = {
      fullname: data.fullname,
      avatarUrl: data.avatarUrl,
      phone: data.phone,
      gender: data.gender,
      birthday: data.birthday,
    };
    // KHÔNG load ảnh trong onMounted vì uploadRef chưa sẵn sàng
    // Sẽ load khi user mở modal avatar
  } catch (err) {
    console.error("Không thể lấy thông tin tài khoản", err);
  }
});
const handleAvatarUpdate = async (images) => {
  try {
    dto.value.avatarUrl = images[0].cloudinaryUrl || images[0].url;
    const response = await authService.updateProfile(dto.value);
    console.log("response ", response);
    user.value = {
      ...user.value,
      fullname: dto.value.fullname,
      avatarUrl: dto.value.avatarUrl,
      phone: dto.value.phone,
      gender: dto.value.gender,
      birthday: dto.value.birthday,
      createAt: response.createAt, // Giữ nguyên ngày tạo nếu không có
      updateAt: response.updateAt, // Giữ nguyên ngày cập nhật nếu không có
    };
    authService.setUserHeader(user.value);
    setTimeout(
      notification.success({
        message: "Cập nhật thành công",
        description: "Ảnh đại diện đã được xóa ",
      }),
      200
    );
  } catch (error) {
    notification.error({
      message: "Cập nhật thất bại",
      description: error.response?.data?.message || "Lỗi cập nhật ảnh đại diện",
    });
  }
};

const handleAvatarDelete = async (index) => {
  try {
    dto.value.avatarUrl = null;
    const response = await authService.updateProfile(dto.value);
    console.log("response ", response);
    user.value = {
      ...user.value,
      fullname: dto.value.fullname,
      avatarUrl: dto.value.avatarUrl,
      phone: dto.value.phone,
      gender: dto.value.gender,
      birthday: dto.value.birthday,
      createAt: response.createAt, // Giữ nguyên ngày tạo nếu không có
      updateAt: response.updateAt, // Giữ nguyên ngày cập nhật nếu không có
    };
    authService.setUserHeader(user.value);
    setTimeout(
      notification.success({
        message: "Cập nhật thành công",
        description: "Ảnh đại diện đã được xóa ",
      }),
      200
    );
  } catch (error) {
    notification.error({
      message: "Cập nhật thất bại",
      description: error.response?.data?.message || "Lỗi cập nhật ảnh đại diện",
    });
  }
};
// ✅ Cập nhật thông tin người dùng
const updateProfile = async () => {
  try {
    const response = await authService.updateProfile(dto.value);
    notification.success({
      message: "Cập nhật thành công",
      description: "Thông tin tài khoản đã được cập nhật.",
    });
    user.value = {
      ...user.value,
      fullname: dto.value.fullname,
      avatarUrl: dto.value.avatarUrl,
      phone: dto.value.phone,
      gender: dto.value.gender,
      birthday: dto.value.birthday,
      createAt: response.createAt, // Giữ nguyên ngày tạo nếu không có
      updateAt: response.updateAt, // Giữ nguyên ngày cập nhật nếu không có
      // Không cập nhật email vì không cho phép thay đổi
    };
    authService.setUserHeader(user.value);
  } catch (err) {
    notification.error({
      message: "Cập nhật thất bại",
      description: err.response?.data?.message || "Lỗi cập nhật thông tin",
    });
  }
};

// ✅ Đổi mật khẩu người dùng
const changePassword = async () => {
  if (user.value.NewPassword !== user.value.ConfirmPassword) {
    alert("Mật khẩu xác nhận không khớp");
    return;
  }

  try {
    const dto = {
      currentPassword: user.value.CurrentPassword,
      newPassword: user.value.NewPassword,
      confirmPassword: user.value.ConfirmPassword,
    };

    await authService.changePassword(dto);
    alert("Đổi mật khẩu thành công. Vui lòng đăng nhập lại.");

    authService.logout();
  } catch (err) {
    const message = err.response?.data || "Lỗi khi đổi mật khẩu";
    alert(typeof message === "string" ? message : message.message);
  }
};
const clearDto = () => {
  dto.value = {
    fullname: user.value.fullname,
    email: user.value.email,
    phone: user.value.phone,
  };
  showPopupTaiKhoan.value = false;
};
const avatarInput = ref(null);
</script>

<style scoped>
/* Page Header */
.page-header {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 3rem 1rem;
  text-align: center;
  margin-bottom: 2rem;
}

.header-content {
  max-width: 600px;
  margin: 0 auto;
}

.page-title {
  color: white;
  font-size: 2.5rem;
  font-weight: 700;
  margin-bottom: 0.5rem;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.3);
}

.page-subtitle {
  color: rgba(255, 255, 255, 0.9);
  font-size: 1.1rem;
  margin: 0;
  font-weight: 400;
}

/* Main Container */
.main-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 1rem;
}

/* Profile Card */
.profile-card {
  background: white;
  border-radius: 20px;
  padding: 2rem;
  margin-bottom: 2rem;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
}

.profile-header {
  margin-bottom: 2rem;
}

.avatar-section {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 1rem;
}

.avatar-wrapper {
  position: relative;
  display: inline-block;
}

.avatar-img {
  width: 120px;
  height: 120px;
  border-radius: 50%;
  object-fit: cover;
  border: 4px solid #f8f9fa;
  box-shadow: 0 8px 25px rgba(0, 0, 0, 0.15);
}

.avatar-upload-btn {
  position: absolute;
  bottom: 5px;
  right: 5px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border: none;
  border-radius: 50%;
  width: 36px;
  height: 36px;
  color: white;
  font-size: 1rem;
  cursor: pointer;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.3);
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
}

.avatar-upload-btn:hover {
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.4);
}

.profile-info {
  text-align: center;
}

.username {
  font-size: 1.8rem;
  font-weight: 700;
  color: #2c3e50;
  margin: 0 0 0.5rem 0;
}

.user-email {
  color: #7f8c8d;
  font-size: 1rem;
  margin: 0 0 1rem 0;
}

.member-badge {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  padding: 0.5rem 1rem;
  border-radius: 25px;
  font-size: 0.9rem;
  font-weight: 600;
  display: inline-flex;
  align-items: center;
  gap: 0.5rem;
  margin: 0.2rem;
}

/* Stats Section */
.stats-section {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 1.5rem;
  margin-top: 2rem;
}

.stat-card {
  background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
  border-radius: 15px;
  padding: 1.5rem;
  display: flex;
  align-items: center;
  gap: 1rem;
  transition: box-shadow 0.3s ease;
  border: 1px solid rgba(0, 0, 0, 0.1);
  min-width: 0; /* Allow content to shrink */
  overflow: hidden; /* Prevent overflow */
}

.stat-card:hover {
  box-shadow: 0 8px 25px rgba(0, 0, 0, 0.1);
}

.stat-icon {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.8rem;
  color: white;
  flex-shrink: 0;
}

.stat-icon.wallet {
  background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
}

.stat-icon.points {
  background: linear-gradient(135deg, #ffecd2 0%, #fcb69f 100%);
  color: yellow;
}

.stat-icon.orders {
  background: linear-gradient(135deg, #a8edea 0%, #fed6e3 100%);
  color: #6610f2;
}

.stat-content {
  flex: 1;
  min-width: 0; /* Allow content to shrink */
  overflow: hidden; /* Prevent overflow */
}

.stat-value {
  font-size: 1.5rem;
  font-weight: 700;
  color: #2c3e50;
  margin: 0 0 0.2rem 0;
  word-break: break-word; /* Break long numbers */
  line-height: 1.2;
  overflow-wrap: break-word; /* Better text wrapping */
}

.stat-label {
  color: #7f8c8d;
  font-size: 0.9rem;
  margin: 0;
  line-height: 1.3;
  word-break: break-word; /* Break long text */
}

/* Action Cards */
.action-cards {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 1rem;
}

.action-card {
  background: white;
  border-radius: 15px;
  padding: 1.5rem;
  display: flex;
  align-items: center;
  gap: 1rem;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
  border: 2px solid transparent;
}

.action-card:hover {
  box-shadow: 0 8px 25px rgba(0, 0, 0, 0.15);
  border-color: #667eea;
}

.action-icon {
  width: 50px;
  height: 50px;
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.8rem;
  color: white;
  flex-shrink: 0;
}

.action-icon.profile {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.action-icon.security {
  background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
}

.action-icon.wallet {
  background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
}

.action-icon.orders {
  background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%);
}

.action-icon.address {
  background: linear-gradient(135deg, #fa709a 0%, #fee140 100%);
}

.action-content {
  flex: 1;
}

.action-content h3 {
  font-size: 1.1rem;
  font-weight: 600;
  color: #2c3e50;
  margin: 0 0 0.3rem 0;
}

.action-content p {
  color: #7f8c8d;
  font-size: 0.9rem;
  margin: 0;
}

.action-arrow {
  color: #bdc3c7;
  font-size: 1.2rem;
  transition: color 0.3s ease;
}

.action-card:hover .action-arrow {
  color: #667eea;
}

/* Modal Styling */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.6);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
  padding: 1rem;
}

.modern-modal {
  background: white;
  border-radius: 20px;
  max-width: 500px;
  width: 100%;
  max-height: 90vh;
  overflow-y: auto;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
  animation: modalSlideIn 0.3s ease;
}

/* Avatar Modal Specific Styling */
.avatar-modal {
  max-width: 600px;
  min-height: 500px;
}

.avatar-form {
  padding: 1rem 2rem 2rem 2rem;
}

.upload-container {
  min-height: 350px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  margin-bottom: 1.5rem;
}

@keyframes modalSlideIn {
  from {
    opacity: 0;
    transform: translateY(-30px) scale(0.95);
  }
  to {
    opacity: 1;
    transform: translateY(0) scale(1);
  }
}

.modal-header {
  padding: 1.5rem 2rem;
  border-bottom: 1px solid #e9ecef;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.modal-header h3 {
  margin: 0;
  font-size: 1.3rem;
  font-weight: 600;
  color: #2c3e50;
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.close-btn {
  background: none;
  border: none;
  font-size: 1.2rem;
  color: #7f8c8d;
  cursor: pointer;
  padding: 0.5rem;
  border-radius: 50%;
  transition: all 0.3s ease;
}

.close-btn:hover {
  background: #f8f9fa;
  color: #e74c3c;
}

.modal-form {
  padding: 2rem;
}

.form-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 1.5rem;
  margin-bottom: 2rem;
}

.form-grid.single-column {
  grid-template-columns: 1fr;
}

.form-group {
  display: flex;
  flex-direction: column;
}

.form-group.full-width {
  grid-column: span 2;
}

.form-group label {
  font-weight: 600;
  color: #2c3e50;
  margin-bottom: 0.5rem;
  display: flex;
  align-items: center;
  gap: 0.5rem;
  font-size: 1rem;
}

.form-group input {
  padding: 0.8rem 1rem;
  border: 2px solid #e9ecef;
  border-radius: 10px;
  font-size: 1rem;
  transition: all 0.3s ease;
}

.form-group input:focus {
  outline: none;
  border-color: #667eea;
  box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
}

/* Radio Button Styling */
.radio-group {
  display: flex;
  gap: 1rem;
  margin-top: 0.5rem;
}

.radio-option {
  display: flex;
  align-items: center;
  cursor: pointer;
  position: relative;
}

.radio-option input[type="radio"] {
  position: absolute;
  opacity: 0;
  cursor: pointer;
}

.radio-custom {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  padding: 0.8rem 1.2rem;
  border: 2px solid #e9ecef;
  border-radius: 10px;
  background: white;
  transition: all 0.3s ease;
  font-weight: 500;
  color: #2c3e50;
  min-width: 80px;
  justify-content: center;
}

.radio-option input[type="radio"]:checked + .radio-custom {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border-color: #667eea;
  box-shadow: 0 4px 15px rgba(102, 126, 234, 0.3);
}

.radio-option:hover .radio-custom {
  border-color: #667eea;
  box-shadow: 0 2px 8px rgba(102, 126, 234, 0.2);
}

.password-input {
  position: relative;
}

.password-toggle {
  position: absolute;
  right: 12px;
  top: 50%;
  transform: translateY(-50%);
  background: none;
  border: none;
  color: #7f8c8d;
  cursor: pointer;
  padding: 0.5rem;
  border-radius: 4px;
  transition: all 0.3s ease;
  font-size: 1.2rem;
}

.password-toggle:hover {
  color: #667eea;
  background: rgba(102, 126, 234, 0.1);
}

.modal-actions {
  display: flex;
  gap: 1rem;
  justify-content: flex-end;
}

.btn-primary,
.btn-secondary {
  padding: 0.8rem 1.5rem;
  border: none;
  border-radius: 10px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.btn-primary {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
}

.btn-primary:hover {
  box-shadow: 0 8px 25px rgba(102, 126, 234, 0.3);
}

.btn-secondary {
  background: #f8f9fa;
  color: #6c757d;
  border: 2px solid #e9ecef;
}

.btn-secondary:hover {
  background: #e9ecef;
  color: #495057;
}

/* Responsive Design */
@media (max-width: 768px) {
  .page-header {
    padding: 2rem 1rem;
  }

  .page-title {
    font-size: 2rem;
  }

  .profile-card {
    padding: 1.5rem;
  }

  .stats-section {
    grid-template-columns: 1fr;
    gap: 1rem;
  }

  .stat-card {
    padding: 1rem;
    flex-direction: column;
    text-align: center;
    gap: 0.8rem;
  }

  .stat-value {
    font-size: 1.3rem;
  }

  .stat-label {
    font-size: 0.85rem;
  }

  .action-cards {
    grid-template-columns: 1fr;
  }

  .form-grid {
    grid-template-columns: 1fr;
  }

  .form-group.full-width {
    grid-column: span 1;
  }

  .modal-actions {
    flex-direction: column;
  }

  .radio-group {
    flex-direction: column;
    gap: 0.8rem;
  }

  .radio-custom {
    justify-content: flex-start;
    min-width: auto;
  }
}

@media (max-width: 480px) {
  .modern-modal {
    margin: 1rem;
    border-radius: 15px;
  }

  .avatar-modal {
    max-width: 95vw;
    min-height: 400px;
    margin: 0.5rem;
  }

  .modal-header,
  .modal-form {
    padding: 1rem;
  }

  .avatar-form {
    padding: 1rem;
  }

  .upload-container {
    min-height: 280px;
  }

  .stats-section {
    grid-template-columns: 1fr;
    gap: 0.8rem;
  }

  .stat-card {
    padding: 0.8rem;
    gap: 0.6rem;
  }

  .stat-value {
    font-size: 1.2rem;
    word-break: break-all; /* More aggressive breaking for very small screens */
  }

  .stat-label {
    font-size: 0.8rem;
    line-height: 1.2;
  }

  .stat-icon {
    width: 40px;
    height: 40px;
    font-size: 1.4rem;
  }
}
</style>
