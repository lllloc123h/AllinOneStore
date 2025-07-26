  <template>
    <div class="mt-2 mx-5">
      <h2><strong> Cài đặt tài khoản</strong></h2>
      <h6 class="mb-5">WEAR WHAT MAKES YOU FEEL CONFIDENT</h6>
    </div>
    <div class="account-card">
  <!-- Ảnh đại diện + thông tin chính -->
  <div class="account-header">
   <div class="avatar-wrapper">
  <img :src="user.Avatar" alt="avatar" class="avatar-img" />
  <button class="avatar-upload-btn" @click="$refs.avatarInput.click()" title="Đổi ảnh đại diện">
    📷
  </button>
  <input type="file" ref="avatarInput" @change="handleAvatarChange" accept="image/*" hidden />
</div>

    <h4 class="username">{{ user.Name }}</h4>
    <p class="user-email">{{ user.Email }}</p>
  </div>

  <!-- Thông tin xếp hạng / điểm / chi tiêu -->
<div class="account-info-stats">
  <div class="stat-box">
    <span class="stat-icon">🏅</span>
    <p class="label">Hạng thành viên</p>
    <strong>{{ user.Rank || 'Chưa xếp hạng' }}</strong>
  </div>
  <div class="stat-box">
    <span class="stat-icon">💰</span>
    <p class="label">Tổng chi tiêu</p>
    <strong>{{ user.TotalSpent.toLocaleString() }}₫</strong>
  </div>
  <div class="stat-box">
    <span class="stat-icon">🌟</span>
    <p class="label">Điểm tích lũy</p>
    <strong>{{ user.LoyaltyPoint }}</strong>
  </div>
</div>


  <!-- Các nút chức năng -->
  <div class="button-holder">
    <button class="open-popup-btn" @click="openPopupTaiKhoan">Cài đặt thông tin tài khoản</button>
    <button class="open-popup-btn" @click="openPopupDoiMatKhau">Đổi mật khẩu</button>
    <button class="open-popup-btn" @click="openPopupTopUp">Nạp tiền</button>
    <button class="open-popup-btn" @click="redirectOrder">Đơn Hàng</button>
    <button class="open-popup-btn" @click="openPopupDiaChi">Địa chỉ nhận hàng</button>
  </div>
</div>


        <div v-if="showPopupTaiKhoan" class="popup-overlay">
          <form class="form-container">
            <section class="form-section">
              <h3>Thông tin khách hàng</h3>
              <div class="form-grid">
                <div class="form-group">
                  <label>Họ và tên</label>
                  <input type="text" v-model="user.Name" />
                </div>
                <div class="form-group">
                  <label>Số điện thoại</label>
                  <input type="text" v-model="user.Phone" />
                </div>
                <div class="form-group full-width">
                  <label>Địa chỉ Email</label>
                  <input type="email" v-model="user.Email" />
                </div>
              </div>
            </section>

            <div class="form-buttons">
              <button class="submit-btn" @click.prevent="updateProfile">Lưu thay đổi</button>
              <button class="cancel-btn" @click.prevent="showPopupTaiKhoan = false">Hủy</button>
            </div>
          </form>
        </div>

        <div v-if="showPopupDoiMatKhau" class="popup-overlay">
  <form class="form-container">
    <section class="form-section">
      <h3>Đổi mật khẩu</h3>
      <div class="form-grid">
        <div class="form-group full-width">
  <label>Mật khẩu mới</label>
  <div class="password-wrapper">
   <input :type="showPasswordNew ? 'text' : 'password'" v-model="user.NewPassword" />
<button type="button" class="toggle-password" @click="togglePasswordNew">
  <span v-if="showPasswordNew">🙈</span>
  <span v-else>👁️</span>
</button>
  </div>
</div>

<div class="form-group full-width">
  <label>Xác nhận mật khẩu</label>
  <div class="password-wrapper">
   <input :type="showPasswordConfirm ? 'text' : 'password'" v-model="user.ConfirmPassword" />
<button type="button" class="toggle-password" @click="togglePasswordConfirm">
  <span v-if="showPasswordConfirm">🙈</span>
  <span v-else>👁️</span>
</button>
  </div>
</div>

      </div>
    </section>

            <div class="form-buttons">
              <button class="submit-btn" @click.prevent="changePassword">Đổi mật khẩu</button>
              <button class="cancel-btn" @click.prevent="showPopupDoiMatKhau = false">Hủy</button>
            </div>
          </form>
        </div>

  </template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { authService } from '../../Configs/api'
const router = useRouter()

// 👤 Dữ liệu người dùng
const user = ref({
  Name: '',
  Email: '',
  Phone: '',
  Avatar: '', 
  Rank: '',
  TotalSpent: 0,
  LoyaltyPoint: 0,
  NewPassword: '',
  ConfirmPassword: ''
})


// 🔄 Trạng thái popup
const showPopupTaiKhoan = ref(false)
const showPopupDoiMatKhau = ref(false)

// 🧭 Các nút điều hướng
const openPopupTaiKhoan = () => { showPopupTaiKhoan.value = true }
const openPopupDoiMatKhau = () => { showPopupDoiMatKhau.value = true }
const openPopupTopUp = () => { router.push({ name: "wallet" }) }
const openPopupDiaChi = () => { router.push({ name: "shippingaddress" }) }
const redirectOrder = () => { router.push({ name: "user-orders" }) }
const showPasswordNew = ref(false)
const showPasswordConfirm = ref(false)

const togglePasswordNew = () => {
  showPasswordNew.value = !showPasswordNew.value
}

const togglePasswordConfirm = () => {
  showPasswordConfirm.value = !showPasswordConfirm.value
}

// ✅ Lấy thông tin người dùng từ API qua authService
onMounted(async () => {
  try {
    const data = await authService.getProfile()
    user.value.Name = data.fullname
    user.value.Email = data.email
    user.value.Phone = data.phone
    user.value.Avatar = data.avatarUrl
    user.value.Rank = data.userRank
    user.value.TotalSpent = data.totalSpent
    user.value.LoyaltyPoint = data.loyaltyPoint
  } catch (err) {
    console.error("Không thể lấy thông tin tài khoản", err)
  }
})


// ✅ Cập nhật thông tin người dùng
const updateProfile = async () => {
  try {
    const dto = {
      fullname: user.value.Name,
      email: user.value.Email,
      phone: user.value.Phone
    }
    await authService.updateProfile(dto)
    alert("Cập nhật thông tin thành công")
    showPopupTaiKhoan.value = false
  } catch (err) {
    alert(err.response?.data?.message || "Lỗi cập nhật thông tin")
  }
}

// ✅ Đổi mật khẩu người dùng
const changePassword = async () => {
  if (user.value.NewPassword !== user.value.ConfirmPassword) {
    alert("Mật khẩu xác nhận không khớp")
    return
  }

  try {
    const dto = {
      newPassword: user.value.NewPassword,
      confirmPassword: user.value.ConfirmPassword
    }

    await authService.changePassword(dto)
    alert("Đổi mật khẩu thành công. Vui lòng đăng nhập lại.")

    authService.logout()

  } catch (err) {
    const message = err.response?.data || "Lỗi khi đổi mật khẩu"
    alert(typeof message === 'string' ? message : message.message)
  }
}
const avatarInput = ref(null)

const handleAvatarChange = async (event) => {
  const file = event.target.files[0]
  if (!file) return

  const formData = new FormData()
  formData.append("file", file)

  try {
    const res = await authService.uploadAvatar(formData)
    user.value.Avatar = res.avatarUrl
    alert("Cập nhật ảnh đại diện thành công!")
  } catch (err) {
    alert(err.response?.data?.message || "Lỗi khi cập nhật ảnh đại diện")
  }
}

</script>



<style scoped>
/* Form popup layout giống mẫu */
.form-container {
  width: 600px;
  background: #fff;
  padding: 30px;
  border-radius: 15px;
  box-shadow: 0 6px 18px rgba(0, 0, 0, 0.15);
  font-family: sans-serif;
}

.form-section {
  margin-bottom: 20px;
}

.form-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 16px;
}

.full-width {
  grid-column: span 2;
}

.form-group {
  display: flex;
  flex-direction: column;
}

label {
  font-weight: bold;
  margin-bottom: 6px;
}

input {
  padding: 10px 15px;
  border: 1px solid #ccc;
  border-radius: 25px;
  font-size: 14px;
}

/* Nút lưu và hủy */
.form-buttons {
  display: flex;
  justify-content: center;
  gap: 15px;
  margin-top: 20px;
}

.submit-btn {
  background-color: #fdddcf;
  color: black;
  padding: 12px 24px;
  border: none;
  border-radius: 25px;
  font-size: 16px;
  cursor: pointer;
  transition: background 0.3s;
}

.submit-btn:hover {
  background-color: #ffcbb5;
}

.cancel-btn {
  background-color: #ddd;
  color: #333;
  padding: 12px 24px;
  border: none;
  border-radius: 25px;
  font-size: 16px;
  cursor: pointer;
  transition: background 0.3s;
}

.cancel-btn:hover {
  background-color: #bbb;
}

.popup-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.4);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 999;
}

.form-container {
  width: 600px;
  background: #fff;
  padding: 30px;
  border-radius: 15px;
  box-shadow: 0 6px 18px rgba(0, 0, 0, 0.15);
  font-family: sans-serif;
}

.form-section {
  margin-bottom: 20px;
}

.form-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 16px;
}

.full-width {
  grid-column: span 2;
}

.form-group {
  display: flex;
  flex-direction: column;
}

label {
  font-weight: bold;
  margin-bottom: 6px;
}

input,
textarea {
  padding: 10px 15px;
  border: 1px solid #ccc;
  border-radius: 25px;
  font-size: 14px;
}

textarea {
  border-radius: 15px;
  resize: vertical;
}

.form-buttons {
  display: flex;
  justify-content: center;
  gap: 15px;
  margin-top: 20px;
}

.submit-btn {
  background-color: #fdddcf;
  color: black;
  padding: 12px 24px;
  border: none;
  border-radius: 25px;
  font-size: 16px;
  cursor: pointer;
  transition: background 0.3s;
}

.submit-btn:hover {
  background-color: #ffcbb5;
}

.cancel-btn {
  background-color: #ddd;
  color: #333;
  padding: 12px 24px;
  border: none;
  border-radius: 25px;
  font-size: 16px;
  cursor: pointer;
  transition: background 0.3s;
}

.cancel-btn:hover {
  background-color: #bbb;
}

.button-holder {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  align-items: center;
  margin: 30px auto;
}

/* Nút điều hướng chính */
.open-popup-btn {
  background-color: #fdddcf;
  color: #333;
  font-size: 16px;
  font-weight: 600;
  padding: 12px 30px;
  border: none;
  border-radius: 25px;
  cursor: pointer;
  transition: background-color 0.3s ease, transform 0.2s ease;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.08);
  min-width: 260px;
}

.open-popup-btn:hover {
  background-color: #ffcbb5;
  transform: translateY(-2px);
}

.account-card {
  max-width: 600px;
  margin: 40px auto;
  padding: 30px;
  background-color: #fff;
  border-radius: 20px;
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.08);
  text-align: center;
}

.account-header {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-bottom: 30px;
}

.avatar-img {
  width: 140px;
  height: 140px;
  border-radius: 50%;
  object-fit: cover;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.username {
  font-size: 22px;
  font-weight: bold;
  margin-top: 15px;
  color: #333;
}

.user-email {
  color: #777;
  font-size: 14px;
  margin-top: 5px;
}
  .password-wrapper {
  position: relative;
  display: flex;
  align-items: center;
}

.password-wrapper input {
  flex: 1;
  padding-right: 70px; /* chừa khoảng trống cho nút */
}

.toggle-password {
  position: absolute;
  right: 16px;
  background: none;
  border: none;
  font-size: 20px; /* 👉 icon to hơn */
  cursor: pointer;
  color: #666;
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  padding: 0;
  transition: color 0.3s ease, transform 0.2s ease;
}

.toggle-password:hover {
  color: #007bff; /* xanh dương nổi bật khi hover */
  transform: scale(1.2); /* hơi phóng to khi hover */
}
.user-rank,
.user-spending,
.user-points {
  font-size: 14px;
  color: #555;
  margin-top: 6px;
}
.account-info-stats {
  display: flex;
  justify-content: space-around;
  margin-bottom: 20px;
  padding: 15px;
  background-color: #f9f9f9;
  border-radius: 15px;
  box-shadow: inset 0 1px 6px rgba(0, 0, 0, 0.05);
}

.stat-box {
  display: flex;
  flex-direction: column;
  align-items: center;
  min-width: 100px;
}

.stat-box .label {
  font-size: 13px;
  color: #888;
  margin-bottom: 4px;
}
.stat-icon {
  font-size: 26px;
  margin-bottom: 6px;
}
.stat-icon {
  font-size: 26px;
  margin-bottom: 6px;
}
.avatar-wrapper {
  position: relative;
  display: inline-block;
}

.avatar-upload-btn {
  position: absolute;
  bottom: 8px;
  right: 8px;
  background-color: #fdddcf;
  border: none;
  border-radius: 50%;
  padding: 8px;
  font-size: 18px;
  cursor: pointer;
  box-shadow: 0 2px 6px rgba(0,0,0,0.2);
  transition: background 0.3s, transform 0.2s;
}

.avatar-upload-btn:hover {
  background-color: #ffcbb5;
  transform: scale(1.1);
}

  </style>
