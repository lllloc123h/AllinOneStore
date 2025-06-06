<template>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" />
  <header class="header">
    <div class="header-left">
      <span class="logo">OOO</span>
    </div>
    <nav class="header-nav">
      <ul>
        <li><a href="#">Trang chủ</a></li>
        <li><a href="#">Loại Hàng</a></li>
        <li><a href="#">Giới thiệu</a></li>
        <li><a href="#">Tin tức</a></li>
      </ul>
    </nav>
    <div class="header-right">
      <i class="header-icon fas fa-user"></i>
      <i class="header-icon fas fa-shopping-bag"></i>
      <i class="header-icon fas fa-chevron-down"></i>
    </div>
  </header>

  <div class="address-container">
    <div class="address row">
      <div class="form col-sm-12">
        <h2 class="form-title">ĐỊA CHỈ GIAO HÀNG</h2>
        <p class="form-subtitle">
          All in One Store luôn lắng nghe tận tâm từ khách hàng
        </p>

        <div class="address-item mb-4 p-3 rounded-xl position-relative">
          <label class="form-label mb-1">Địa chỉ giao hàng</label>
          <input type="text" value="Trường Chinh, quận 12, Thành phố Hồ Chí Minh" readonly class="form-control" />
          <span class="default-label">Mặc định</span>
          <button title="Xoá địa chỉ" class="btn-remove">×</button>
        </div>

        <div class="address-item mb-4 p-3 rounded-xl position-relative">
          <label class="form-label mb-1">Địa chỉ giao hàng</label>
          <input type="text" value="Xóm chùa, xã Thường Phước, Đồng Tháp" readonly class="form-control" />
          <button title="Xoá địa chỉ" class="btn-remove">×</button>
        </div>

        <div class="address-item mb-4 p-3 rounded-xl position-relative">
          <label class="form-label mb-1">Địa chỉ giao hàng</label>
          <input type="text" value="xã Long Khánh, Đồng Tháp" readonly class="form-control" />
          <button title="Xoá địa chỉ" class="btn-remove">×</button>
        </div>

        <div class="text-center mt-4 mx-3">
          <button class="btn btn-add">
            Thêm địa chỉ mới
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
</script>

<style scoped>
body {
  font-family: 'Arial', sans-serif;
  margin: 0;
  background-color: #f0f2f5;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px 50px;
  background-color: white;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
}

.header-left .logo {
  font-weight: bold;
  font-size: 1.5rem;
  color: black;
}

.header-nav ul {
  list-style: none;
  display: flex;
  margin: 0;
  padding: 0;
}

.header-nav li {
  margin-left: 30px;
}

.header-nav a {
  text-decoration: none;
  color: #333;
  font-weight: 500;
  font-size: 0.95rem;
  transition: color 0.2s;
}

.header-nav a:hover {
  color: #000;
}

.header-right {
  display: flex;
  align-items: center;
}

.header-icon {
  margin-left: 20px;
  font-size: 1.2rem;
  cursor: pointer;
  color: #555;
  transition: color 0.2s;
}

.header-icon:hover {
  color: #000;
}

.address-container {
  display: flex;
  justify-content: center;
  margin-top: 50px;
}

.address {
  padding: 0;
  width: 700px;
  border-radius: 10px;
  overflow: hidden;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  background-color: white;
  display: block;
}

.form {
  padding: 30px;
}

.form-title {
  font-size: 1.75rem;
  font-weight: 700;
  margin-bottom: 0.5rem;
  color: #333;
}

.form-subtitle {
  color: #6b7280;
  font-size: 0.9rem;
  margin-top: 0;
  margin-bottom: 1.5rem;
}

.address-item {
  background-color: #f3f4f6;
  position: relative;
  border-radius: 0.75rem;
  padding: 1rem;
  margin-left: 12px;
  margin-right: 12px;
}

.form-label {
  font-weight: 500;
  color: #4b5563;
  font-size: 0.9rem;
  display: block;
  margin-bottom: 0.25rem;
}

.form-control {
  width: calc(100% - 20px);
  background-color: white;
  border: 1px solid #d1d5db;
  border-radius: 12px;
  padding: 10px 14px;
  font-size: 0.9rem;
  color: #374151;
  outline: none;
  box-sizing: border-box;
}

.default-label {
  position: absolute;
  right: 50px;
  top: 18px;
  background-color: #e5e7eb;
  color: #374151;
  font-size: 0.7rem;
  padding: 3px 8px;
  border-radius: 8px;
  font-weight: 600;
}

.btn-remove {
  position: absolute;
  right: 15px;
  top: 15px;
  background: none;
  border: none;
  font-size: 20px;
  color: #9ca3af;
  cursor: pointer;
  transition: color 0.2s;
  padding: 0;
  line-height: 1;
}

.btn-remove:hover {
  color: #ef4444;
}

.btn-add {
  background-color: #000;
  color: white;
  padding: 10px 40px;
  border-radius: 9999px;
  font-size: 0.9rem;
  font-weight: 600;
  border: none;
  cursor: pointer;
  transition: background-color 0.3s ease;
  width: auto;
  display: inline-block;
  margin-top: 1.5rem;
}

.btn-add:hover {
  background-color: #374151;
}

@media (max-width: 768px) {
  .header {
    padding: 15px 20px;
  }
  .header-nav {
    display: none;
  }
  .header-right .header-icon:first-child {
    display: none;
  }
  .address-container {
    margin-top: 20px;
  }
  .address {
    width: 95%;
    display: block;
    margin: auto;
  }
  .form {
    padding: 20px;
  }
  .address-item {
    margin-left: 0;
    margin-right: 0;
  }
}
</style>


