<template>
  <div class="mt-2 mx-5">
    <h2><strong> Cài đặt tài khoản</strong></h2>
    <h6 class="mb-5">WEAR WHAT MAKES YOU FEEL CONFIDENT</h6>
  </div>

  <div>
    <!-- Popup cập nhật thông tin -->
    <div v-if="showPopupTaiKhoan" class="popup-overlay">
      <form>
        <div class="popup-content">
          <h4><strong>Thông tin khách hàng</strong></h4>
          <label class="form-label">Họ và tên</label>
          <input type="text" class="form-control" v-model="user.Name">

          <label class="form-label">Địa chỉ Email</label>
          <input type="email" class="form-control" v-model="user.Email">

          <label class="form-label">Số điện thoại</label>
          <input type="text" class="form-control" v-model="user.Phone">

          <label class="form-label">Link ảnh đại diện</label>
          <input type="text" class="form-control" v-model="user.Avatar">

          <button class="btn-popup" @click.prevent="updateProfile">Cập nhật</button>
        </div>
      </form>
    </div>

    <!-- Popup đổi mật khẩu -->
    <div v-if="showPopupDoiMatKhau" class="popup-overlay">
      <form>
        <div class="popup-content">
          <h4 class="mt-4"><strong>Đổi mật khẩu</strong></h4>
          <div class="row">
            <div class="col-sm-12">
              <label class="form-label">Mật khẩu mới</label>
              <input type="password" class="form-control" v-model="user.NewPassword">
            </div>
            <div class="col-sm-12 mt-3">
              <label class="form-label">Xác nhận mật khẩu</label>
              <input type="password" class="form-control" v-model="user.ConfirmPassword">
            </div>
          </div>
          <button class="btn-popup" @click.prevent="changePassword">Đổi mật khẩu</button>
        </div>
      </form>
    </div>

    <!-- Ảnh đại diện -->
    <div class="card" style="width: 240px; height: 320px; margin: auto;">
      <img :src="user.Avatar || avatar" alt="avatar" class="card-img" width="100%" height="100%">
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
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import avatar from '../../assets/imgs/avarta_Kha.png'
import { useRouter } from 'vue-router'

const router = useRouter()

const user = ref({
  Name: '',
  Phone: '',
  Avatar: '',
  Email: '',
  Password: '',
  Address: '',
  NewPassword: '',
  ConfirmPassword: ''
})

const showPopupTaiKhoan = ref(false)
const showPopupDoiMatKhau = ref(false)

const openPopupTaiKhoan = () => {
  showPopupTaiKhoan.value = true
}

const openPopupDoiMatKhau = () => {
  showPopupDoiMatKhau.value = true
}

const openPopupTopUp = () => {
  router.push({ name: "wallet" })
}

const openPopupDiaChi = () => {
  router.push({ name: "shippingaddress" })
}

// Lấy thông tin người dùng khi tải trang
onMounted(async () => {
  try {
    const token = localStorage.getItem("token")
    const res = await axios.get("http://localhost:8080/api/Accounts/me", {
      headers: { Authorization: `Bearer ${token}` }
    })

    const data = res.data
    user.value.Name = data.name
    user.value.Email = data.email
    user.value.Phone = data.phone
    user.value.Avatar = data.avatar
  } catch (err) {
    console.error("Không thể lấy thông tin tài khoản", err)
  }
})

// Gửi request cập nhật thông tin
const updateProfile = async () => {
  try {
    const token = localStorage.getItem("token")
    const dto = {
      fullname: user.value.Name,
      email: user.value.Email,
      phone: user.value.Phone,
      avatar: user.value.Avatar
    }

    const res = await axios.put("http://localhost:8080/api/Accounts/me", dto, {
      headers: { Authorization: `Bearer ${token}` }
    })

    alert(res.data.message || "Cập nhật thông tin thành công")
    showPopupTaiKhoan.value = false
  } catch (err) {
    alert(err.response?.data?.message || "Lỗi cập nhật thông tin")
  }
}

// Gửi request đổi mật khẩu
const changePassword = async () => {
  try {
    const token = localStorage.getItem("token")
    const dto = {
      newPassword: user.value.NewPassword,
      confirmPassword: user.value.ConfirmPassword
    }

    const res = await axios.put("http://localhost:8080/api/Accounts/change-password", dto, {
      headers: { Authorization: `Bearer ${token}` }
    })

    alert(res.data || "Đổi mật khẩu thành công")
    showPopupDoiMatKhau.value = false
  } catch (err) {
    alert(err.response?.data || "Lỗi khi đổi mật khẩu")
  }
}
</script>

<style scoped>
form {
  width: 50%;
  margin: auto;
  padding: 10px;
  border-radius: 15px;
  margin-bottom: 50px;
}

.btn-popup {
  background-color: #deb887;
  color: #fff;
  font-size: 16px;
  font-weight: 600;
  height: 48px;
  width: 160px;
  border: none;
  border-radius: 25px;
  cursor: pointer;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  transition: background-color 0.3s ease, transform 0.2s ease;
}

.btn-popup:hover {
  background-color: #ffa835;
  transform: scale(1.05);
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

.popup-content {
  background-color: white;
  padding: 30px;
  border-radius: 15px;
  text-align: center;
  width: 350px;
  max-width: 100%;
}

.button-holder {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  align-items: center;
  margin: 20px auto;
}

.open-popup-btn {
  background-color: #deb887;
  color: white;
  font-size: 16px;
  font-weight: 600;
  padding: 12px 24px;
  border: none;
  border-radius: 25px;
  cursor: pointer;
  transition: background-color 0.3s ease, transform 0.2s ease;
}

.open-popup-btn:hover {
  background-color: #ffa835;
  transform: scale(1.05);
}

.card-img {
  object-fit: cover;
  border-radius: 12px;
}
</style>
