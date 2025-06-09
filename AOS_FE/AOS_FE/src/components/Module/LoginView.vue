<template>
  <div class="container-fluid login row mt-5 mb-5">
    <div class="form col-sm-6">
      <h2 class="mx-3 mt-3">Đăng Nhập</h2>
      <div class="mx-3">
        <p style="font-size: small">
          Bạn đã có tài khoản.
          <RouterLink to="/register" style="color: black">Tạo 1 tài khoản mới
          </RouterLink>
        </p>
      </div>
      <form @submit.prevent="handleLogin">
        <label for="emailInput" class="form-label mt-3">Nhập Email</label>
        <input type="text" id="emailInput" class="form-control" v-model="formData.email"
          placeholder="Nhập Email tại đây" />

        <label for="passwordInput" class="form-label mt-3">Nhập Mật Khẩu</label>
        <div class="password-input-container">
          <input type="password" id="passwordInput" v-model="formData.password" class="form-control"
            placeholder="......" />
        </div>
        <button type="submit" class="btn mt-3">Đăng Nhập</button>
        <a class="btn btn-facebook mt-3" href="http://localhost:8080/oauth2/authorization/facebook">
          <img src="https://cdn-icons-png.flaticon.com/512/733/733547.png" style="height: 30px" alt="Facebook" />
          Đăng nhập bằng Facebook
        </a>
        <a class="btn btn-google mt-3" href="http://localhost:8080/oauth2/authorization/google">
          <img src="https://cdn-icons-png.flaticon.com/512/281/281764.png" style="height: 30px" alt="Google" />
          Đăng nhập bằng Google
        </a>
        <div class="quenMK mt-4">
          <a href="#">Quên mật khẩu</a>
        </div>
      </form>
    </div>
    <div class="col-sm-6 benphai" style="padding: 0px">
      <img style="width: 100%; padding: 0px" src="/src/assets/imgs/tải xuống.jpg" alt="" />
    </div>
  </div>
</template>
<script setup>
import { reactive, ref, onMounted, watch } from "vue";
import { RouterLink, useRouter } from "vue-router";
const router = useRouter();
import { authService } from "../../Configs/api";
import "bootstrap/dist/js/bootstrap.bundle.min.js";
import "bootstrap/dist/css/bootstrap.min.css";
import { toast } from "vue3-toastify";
const formData = reactive({
  email: "admin",
  password: "123",
});
async function handleLogin() {
  authService.login(formData.email, formData.password);
  toast.success("Đăng nhập thành công !");
}
</script>
<style scoped>
body {
  font-family: Arial, sans-serif;
  background-color: #f0f2f5;
}

.login {
  padding: 0;
  width: 700px;
  height: auto;
  margin: auto;

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
  border: 1px solid #edcdbb;
  padding: 10px;
  font-size: 1.1rem;
  transition: background-color 0.3s ease;
}

.btn:hover {
  background-color: #edcdbb;
  border-color: gray;
}

form>.quenMK {
  text-align: center;
}

form>.quenMK>a:hover {
  color: #edcdbb;
  text-decoration: none;
}

form>.quenMK>a {
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
  font-size: 1.1rem;
  /* Kích thước biểu tượng */
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
    border-radius: 0 0 10px 10px;
    /* Đổi border-radius cho di động */
    height: 150px;
    /* Chiều cao cố định cho cột bên phải trên di động */
  }

  .form {
    order: 2;
    /* Đặt form ở dưới trên di động */
  }

  .benphai {
    order: 1;
    /* Đặt cột phải ở trên trên di động */
  }
}
</style>
