<template>
  <div class="login row mt-5 mb-5">
    <div class="form col-sm-6">
      <h2 class="mx-3 mt-3">Đăng Ký</h2>
      <div class="mx-3">
        <p style="font-size: small">
          Bạn đã có tài khoản.
          <RouterLink to="/login" style="color: black">Đăng nhập ngay </RouterLink>
        </p>
      </div>
      <form @submit.prevent="handleLogin()">
        <label for="emailInput" class="form-label mt-3"> Email</label>
        <input
          type="email"
          id="emailInput"
          class="form-control"
          placeholder="Nhập Email tại đây"
          required
          v-model="userRegister.email"
        />
        <label for="emailInput" class="form-label mt-3"> Họ và tên </label>
        <input
          type="text"
          id="emailInput"
          class="form-control"
          placeholder="Nhập Email tại đây"
          required
          v-model="userRegister.fullname"
        />
        <label for="emailInput" class="form-label mt-3"> Số điện thoại</label>
        <input
          type="text"
          id="emailInput"
          class="form-control"
          placeholder="Nhập Email tại đây"
          required
          v-model="userRegister.phone"
        />
        <label for="passwordInput" class="form-label mt-3"> Mật Khẩu</label>
        <div class="password-input-container">
          <input
            type="password"
            id="passwordInput"
            class="form-control"
            placeholder="......"
            required
            v-model="userRegister.password"
          />
        </div>
        <label for="passwordInput" class="form-label mt-1">Nhập lại mật khẩu </label>
        <div class="password-input-container">
          <input
            type="password"
            id="passwordInput"
            class="form-control"
            placeholder="......"
            required
            v-model="userRegister.confirmPassword"
          />
        </div>
        <button type="submit" class="btn mt-3">Đăng Ký</button>
      </form>
    </div>
    <div class="col-sm-6 benphai" style="padding: 0px">
      <img
        style="width: 100%; padding: 0px"
        src="/src/assets/imgs/tải xuống.jpg"
        alt=""
      />
    </div>
  </div>
  <OTPView
    :show="showOption"
    @verified="verified"
    @resend="sendOTP"
    @close="closeModal"
  ></OTPView>
</template>
<script setup>
import { ref } from "vue";
import { toast } from "vue3-toastify";
import api, { authService } from "../../Configs/api";
import OTPView from "./OTPView.vue";
const userRegister = ref({
  email: "tranhuuloc123@gmail.com",
  fullname: "Tran Huu Loc",
  phone: "0901234567",
  password: "123",
  confirmPassword: "123",
});
const showOption = ref(false);
const handleLogin = () => {
  if (userRegister.value.password !== userRegister.value.confirmPassword) {
    toast.warning("Password xác nhận không khớp !");
    return;
  }
  // gui OTP o day
  sendOTP();
  // neu dung thi push di, ch thi o lai
};

const closeModal = () => {
  showOption.value = false;
};
const sendOTP = () => {
  api
    .post("/Accounts/register", {
      email: userRegister.value.email,
      password: userRegister.value.password,
      fullname: userRegister.value.fullname,
      phone: userRegister.value.phone,
    })
    .then((resp) => {
      toast.success(resp.data.OTP);
      showOption.value = true;
    })
    .catch((erorr) => toast.error(erorr.response?.data?.message));
};
const verified = () => {
  toast.success("Đăng ký thành công!");
  authService.login(userRegister.value.email, userRegister.value.password);
  userRegister.value.email = "";
  userRegister.value.password = "";
  userRegister.value.fullname = "";
  userRegister.value.confirmPassword = "";
};
</script>
<style scoped>
body {
  font-family: Arial, sans-serif;
  background-color: #f0f2f5;
}

.login {
  padding: 0;
  margin: auto;
  width: 700px;
  height: auto;

  border-radius: 10px;
  overflow: hidden;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}

.login .form {
  padding: 30px;
}

.btn {
  background-color: #fff2eb;
  width: 100%;
  border-radius: 15px;
  border: 1px solid black;
  padding: 10px;
  font-size: 1.1rem;
  transition: background-color 0.3s ease;
}

.btn:hover {
  background-color: #edcdbb;
  border-color: #edcdbb;
}

form > .quenMK {
  text-align: center;
}

form > .quenMK > a:hover {
  color: #edcdbb;
  text-decoration: none;
}

form > .quenMK > a {
  color: #000;
  text-decoration: none;
}

.benphai {
  border-radius: 0 10px 10px 0;
  background-color: #edcdbb;

  display: flex;
  justify-content: center;
  align-items: center;

  color: white;
  font-size: 1.5rem;
  background-size: cover;
  background-position: center;
}

.password-input-container {
  position: relative;
  width: 100%;
  margin-bottom: 15px;
}

.password-input-container input {
  padding-right: 45px;
}

.toggle-password {
  position: absolute;
  right: 15px;
  top: 50%;
  transform: translateY(-50%);
  cursor: pointer;
  color: #888;
  font-size: 1.1rem; /* Kích thước biểu tượng */
}

.toggle-password:hover {
  color: #555;
}

/* Kiểu cho biểu tượng khi mật khẩu được ẩn (mắt có dấu gạch chéo) */
.fa-eye-slash {
  color: #555;
}

/* Điều chỉnh responsive cho màn hình nhỏ hơn */
@media (max-width: 768px) {
  .login {
    width: 95%;
    margin-top: 20px;
  }
  .benphai {
    border-radius: 0 0 10px 10px; /* Đổi border-radius cho di động */
    height: 150px; /* Chiều cao cố định cho cột bên phải trên di động */
  }
  .form {
    order: 2; /* Đặt form ở dưới trên di động */
  }
  .benphai {
    order: 1; /* Đặt cột phải ở trên trên di động */
  }
}
</style>
