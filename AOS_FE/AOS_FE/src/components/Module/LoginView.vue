<template>
  <div class="container-fluid login row mt-5 mb-5">
    <div class="form col-sm-6">
      <h2 class="mx-3 mt-3">Đăng Nhập</h2>
      <div class="mx-3">
        <p style="font-size: small">
          Bạn đã có tài khoản.
          <RouterLink to="/register" style="color: black">Tạo 1 tài khoản mới</RouterLink>
        </p>
      </div>

      <form @submit.prevent="handleLogin">
        <label for="emailInput" class="form-label mt-3">Nhập Email</label>
        <input
          type="text"
          id="emailInput"
          class="form-control"
          v-model="formData.email"
          placeholder="Nhập Email tại đây"
        />

        <label for="passwordInput" class="form-label mt-3">Nhập Mật Khẩu</label>
        <div class="password-input-container">
          <input
            type="password"
            id="passwordInput"
            v-model="formData.password"
            class="form-control"
            placeholder="......"
          />
        </div>

        <button type="submit" class="btn mt-3">Đăng Nhập</button>
      </form>

      <a
        class="btn btn-facebook mt-3"
        href="http://localhost:8080/oauth2/authorization/facebook"
      >
        <img
          src="https://cdn-icons-png.flaticon.com/512/733/733547.png"
          style="height: 30px"
          alt="Facebook"
        />
        Đăng nhập bằng Facebook
      </a>
      <a
        class="btn btn-google mt-3"
        href="http://localhost:8080/oauth2/authorization/google"
      >
        <img
          src="https://cdn-icons-png.flaticon.com/512/281/281764.png"
          style="height: 30px"
          alt="Google"
        />
        Đăng nhập bằng Google
      </a>

      <div class="quenMK mt-4">
        <a class="btn btn-outline-secondary btn-sm" @click.prevent="showForgotPassword = true">Quên mật khẩu</a>
      </div>

      <div v-if="showForgotPassword" class="mt-4">
        <h5 class="text-center">Khôi phục mật khẩu</h5>
        <input v-model="forgotEmail" class="form-control mt-2" placeholder="Nhập Email" />
        <button type="button" @click="sendOtp" class="btn mt-2">📨 Gửi mã OTP</button>

        <div v-if="otpSent" class="mt-3">
          <input
            v-model="enteredOtp"
            class="form-control mt-2"
            placeholder="Nhập mã OTP"
          />
          <button type="button" @click="verifyOtp" class="btn mt-2">
            ✅ Xác minh OTP
          </button>
        </div>

        <div v-if="otpVerified" class="mt-3">
          <input
            v-model="newPassword"
            type="password"
            class="form-control mt-2"
            placeholder="Nhập mật khẩu mới"
          />
          <button type="button" @click="resetPassword" class="btn mt-2">
            🔒 Đặt lại mật khẩu
          </button>
        </div>
      </div>
    </div>

    <div class="col-sm-6 benphai" style="padding: 0px">
      <img
        style="width: 100%; padding: 0px"
        src="/src/assets/imgs/tải xuống.jpg"
        alt=""
      />
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from "vue";
import { RouterLink, useRouter } from "vue-router";
import { authService } from "../../Configs/api";
import { syncLocalCartToServer } from "../../Configs/cart";
import { toast } from "vue3-toastify";
import "bootstrap/dist/js/bootstrap.bundle.min.js";
import "bootstrap/dist/css/bootstrap.min.css";

const router = useRouter();

const formData = reactive({
  email: "adminCUDE@gmail.com",
  password: "123",
});

const showForgotPassword = ref(false);
const forgotEmail = ref("");
const enteredOtp = ref("");
const otpSent = ref(false);
const otpVerified = ref(false);
const newPassword = ref("");

async function handleLogin() {
  try {
    await authService.login(formData.email, formData.password);
    await syncLocalCartToServer();
  } catch (error) {
    toast.error("Đăng nhập thất bại");
  }
}

function sendOtp() {
  fetch("http://localhost:8080/api/forgot-password/request", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      // Nếu API yêu cầu xác thực, thêm dòng sau:
      // "Authorization": `Bearer ${token}`
    },
    body: JSON.stringify({ email: forgotEmail.value }),
  })
    .then((res) => {
      if (!res.ok) throw new Error("Gửi OTP thất bại");
      toast.success("Đã gửi mã OTP đến email.");
      otpSent.value = true;
    })
    .catch(() => toast.error("Không gửi được mã OTP."));
}

function verifyOtp() {
  try {
    console.log("✅ Hàm verifyOtp đã được gọi");

    if (!enteredOtp.value) {
      console.error("🚫 Không có giá trị OTP");
      toast.error("Vui lòng nhập mã OTP.");
      return;
    }

    const trimmedOtp = enteredOtp.value.trim();
    const payload = {
      email: forgotEmail.value,
      otpCode: trimmedOtp.toString(),
    };

    console.log("📦 Payload gửi lên:", payload);

    fetch("http://localhost:8080/api/forgot-password/verify", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload),
    })
      .then(async (res) => {
        const msg = await res.text();
        console.log("📨 Server trả về:", res.status, msg);

        if (!res.ok) {
          toast.error("❌ " + msg);
          return;
        }

        if (msg.includes("thành công")) {
          toast.success(msg);
          otpVerified.value = true;
        } else {
          toast.error(msg);
        }
      })
      .catch((err) => {
        console.error("❌ Lỗi kết nối:", err);
        toast.error("Lỗi xác minh OTP.");
      });
  } catch (error) {
    console.error("🔥 Lỗi trong verifyOtp:", error);
  }
}
function resetPassword() {
  fetch("http://localhost:8080/api/forgot-password/change", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      email: forgotEmail.value,
      otp: enteredOtp.value.toString(),
      newPassword: newPassword.value,
    }),
  })
    .then((res) => res.text())
    .then((msg) => toast.success(msg))
    .catch(() => toast.error("Không thể đặt lại mật khẩu."));
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
