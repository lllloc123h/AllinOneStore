<template>
  <div class="form-container">

    <section class="form-section">
      <h3>Cài đặt tài khoản</h3>
      <div class="form-grid">
        <div class="form-group">
          <label>Họ và tên</label>
          <input type="text" v-model="user.fullName" />
        </div>
        <div class="form-group">
          <label>Số điện thoại</label>
          <input type="text" v-model="user.phone" />
        </div>
        <div class="form-group full-width">
          <label>Địa chỉ Email</label>
          <input type="email" v-model="user.email" />
        </div>
      </div>
    </section>

    <!-- Đổi mật khẩu -->
    <section class="form-section">
      <h3>Đổi Mật Khẩu</h3>
      <div class="form-grid">
        <div class="form-group">
          <label>Mật khẩu mới</label>
          <input type="password" v-model="user.password" />
        </div>
        <div class="form-group">
          <label>Xác nhận mật khẩu</label>
          <input type="password" v-model="user.confirmPassword" />
        </div>
      </div>
    </section>

    <!-- Địa chỉ -->
    <section class="form-section">
      <h3>Địa Chỉ</h3>
      <div class="form-grid">
        <div class="form-group full-width">
          <label>Địa chỉ nhận hàng</label>
          <textarea rows="2" v-model="user.address"></textarea>
        </div>
        <div class="form-group">
          <label>Tình trạng</label>
          <input type="text" v-model="user.status" />
        </div>
        <div class="form-group">
          <label>Mã Zip</label>
          <input type="text" v-model="user.zip" />
        </div>
      </div>
    </section>

    <button class="submit-btn" @click="submitForm">Lưu thay đổi</button>
  </div>
</template>

<script setup>
import { reactive } from 'vue'

const user = reactive({
  fullName: 'Peter',
  phone: '0123456789',
  email: 'peterducker312@gmail.com',
  password: '',
  confirmPassword: '',
  address: '',
  status: '',
  zip: ''
})

const submitForm = () => {
  console.log('Form data:', user)
  alert('Đã lưu thay đổi!')
}
</script>

<style scoped>
.form-container {
  max-width: 900px;
  margin: 30px auto;
  padding: 20px;
  background: #fff;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  font-family: sans-serif;
}

.form-section {
  margin-bottom: 30px;
}

.form-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 20px;
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
</style>
