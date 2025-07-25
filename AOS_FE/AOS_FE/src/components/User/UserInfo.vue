  <template>
    <div class="mt-2 mx-5">
      <h2><strong> Cài đặt tài khoản</strong></h2>
      <h6 class="mb-5">WEAR WHAT MAKES YOU FEEL CONFIDENT</h6>
    </div>
<div class="account-card">
  <!-- Ảnh đại diện và tên -->
  <div class="account-header">
    <img :src="user.Avatar || avatar" alt="avatar" class="avatar-img" />
    <h4 class="username">{{ user.Name }}</h4>
    <p class="user-email">{{ user.Email }}</p>
  </div>
    <div>
     
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

      <!-- Các nút điều hướng -->
      <div class="button-holder">
        <div>
          <button class="open-popup-btn" @click="openPopupTaiKhoan">Cài đặt thông tin tài khoản</button>
        </div>
        <div>
          <button class="open-popup-btn" @click="openPopupDoiMatKhau">Đổi mật khẩu</button>
        </div>
        <div>
          <button class="open-popup-btn" @click="openPopupTopUp">Nạp tiền</button>
        </div>
        <div>
          <button class="open-popup-btn" @click="openPopupDiaChi">Địa chỉ nhận hàng</button>
        </div>
      </div>
    </div>
  </div>
  </template>

  <script setup>
  import { ref, onMounted } from 'vue'
  import avatar from '../../assets/imgs/avarta_Kha.png'
  import { useRouter } from 'vue-router'
  import { authService } from '../../Configs/api'
  const router = useRouter()

  // 👤 Dữ liệu người dùng
  const user = ref({
    Name: '',
    Email: '',
    Phone: '',
    NewPassword: '',
    ConfirmPassword: ''
  })
const showPasswordNew = ref(false)
const showPasswordConfirm = ref(false)

const togglePasswordNew = () => {
  showPasswordNew.value = !showPasswordNew.value
}

const togglePasswordConfirm = () => {
  showPasswordConfirm.value = !showPasswordConfirm.value
}


  // 🔄 Trạng thái popup
  const showPopupTaiKhoan = ref(false)
  const showPopupDoiMatKhau = ref(false)

  // 🧭 Các nút điều hướng
  const openPopupTaiKhoan = () => { showPopupTaiKhoan.value = true }
  const openPopupDoiMatKhau = () => { showPopupDoiMatKhau.value = true }
  const openPopupTopUp = () => { router.push({ name: "wallet" }) }
  const openPopupDiaChi = () => { router.push({ name: "shippingaddress" }) }

  // ✅ Lấy thông tin người dùng từ API qua authService
  onMounted(async () => {
    try {
      const data = await authService.getProfile()
      user.value.Name = data.fullname
      user.value.Email = data.email
      user.value.Phone = data.phone
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
  top: 0; left: 0; right: 0; bottom: 0;
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

  </style>
