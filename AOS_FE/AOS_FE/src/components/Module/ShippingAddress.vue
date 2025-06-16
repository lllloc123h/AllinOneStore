<template>
  <div>
    <!-- Header -->
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
          <p class="form-subtitle">All in One Store luôn lắng nghe tận tâm từ khách hàng</p>

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
            <button class="btn btn-add" @click="showModal = true">Thêm địa chỉ mới</button>
          </div>
        </div>
      </div>
    </div>

    <!-- Modal Add Address -->
    <div v-if="showModal" class="overlay">
      <div class="form-container">
        <h3>Thêm địa chỉ</h3>
        <div class="input-group">
          <input type="text" placeholder="Họ và tên" v-model="name" />
          <input type="text" placeholder="Số điện thoại" v-model="phone" />
        </div>

        <select v-model="selectedProvince" @change="loadDistricts">
          <option value="" disabled selected>Chọn Tỉnh/Thành phố</option>
          <option v-for="prov in provinces" :key="prov.code" :value="prov.code">
            {{ prov.name }}
          </option>
        </select>

        <select v-model="selectedDistrict" @change="loadWards" :disabled="!districts.length">
          <option value="" disabled selected>Chọn Quận/Huyện</option>
          <option v-for="dist in districts" :key="dist.code" :value="dist.code">
            {{ dist.name }}
          </option>
        </select>

        <select v-model="selectedWard" :disabled="!wards.length">
          <option value="" disabled selected>Chọn Phường/Xã</option>
          <option v-for="ward in wards" :key="ward.code" :value="ward.code">
            {{ ward.name }}
          </option>
        </select>

        <input type="text" placeholder="Địa chỉ cụ thể" v-model="detailAddress" />

        <div class="map-box">
          <button class="add-location">+ Thêm vị trí</button>
        </div>

        <div class="address-type">
          <label>Loại địa chỉ:</label>
          <button class="type-btn">Nhà Riêng</button>
          <button class="type-btn">Nơi làm việc</button>
        </div>

        <div class="actions">
          <button class="cancel-btn" @click="showModal = false">Quay lại</button>
          <button class="submit-btn">Xác nhận</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      showModal: false,
      name: "",
      phone: "",
      detailAddress: "",
      provinces: [],
      districts: [],
      wards: [],
      selectedProvince: "",
      selectedDistrict: "",
      selectedWard: ""
    };
  },
  mounted() {
    this.loadProvinces();
  },
  methods: {
    async loadProvinces() {
      const res = await fetch("https://provinces.open-api.vn/api/p/");
      this.provinces = await res.json();
    },
    async loadDistricts() {
      this.selectedDistrict = "";
      this.selectedWard = "";
      this.districts = [];
      this.wards = [];
      if (this.selectedProvince) {
        const res = await fetch(`https://provinces.open-api.vn/api/p/${this.selectedProvince}?depth=2`);
        const data = await res.json();
        this.districts = data.districts;
      }
    },
    async loadWards() {
      this.selectedWard = "";
      this.wards = [];
      if (this.selectedDistrict) {
        const res = await fetch(`https://provinces.open-api.vn/api/d/${this.selectedDistrict}?depth=2`);
        const data = await res.json();
        this.wards = data.wards;
      }
    }
  }
};
</script>

<style scoped>
/* Header & Address styles như cũ */

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
}
.btn-add:hover {
  background-color: #374151;
}

.overlay {
  position: fixed;
  top: 0; left: 0;
  width: 100%; height: 100%;
  background: rgba(0,0,0,0.4);
  display: flex; align-items: center; justify-content: center;
  z-index: 9999;
}

.form-container {
  background: #fff;
  width: 400px;
  padding: 20px 25px;
  border-radius: 6px;
  box-shadow: 0 0 10px rgba(0,0,0,0.2);
}

h3 { margin-bottom: 20px; }

.input-group {
  display: flex;
  gap: 10px;
  margin-bottom: 10px;
}

input, select {
  width: 100%;
  padding: 10px;
  margin-bottom: 10px;
  box-sizing: border-box;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.map-box {
  background: #f7f7f7;
  height: 100px;
  margin-bottom: 10px;
  display: flex; align-items: center; justify-content: center;
}

.add-location {
  border: 1px solid #ccc;
  background: white;
  padding: 8px 15px;
  cursor: pointer;
  border-radius: 4px;
}

.address-type {
  margin: 15px 0;
}

.address-type label {
  display: block;
  margin-bottom: 5px;
}

.type-btn {
  padding: 8px 15px;
  margin-right: 10px;
  border: 1px solid #ccc;
  background: #fff;
  cursor: pointer;
  border-radius: 4px;
}

.actions {
  display: flex;
  justify-content: space-between;
}

.cancel-btn {
  background: #f2f2f2;
  border: none;
  padding: 10px 15px;
  border-radius: 4px;
  cursor: pointer;
}

.submit-btn {
  background: #ee4d2d;
  color: white;
  border: none;
  padding: 10px 15px;
  border-radius: 4px;
  cursor: pointer;
}
</style>
