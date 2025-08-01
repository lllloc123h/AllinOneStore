<template>
  <div>
    <div class="address-container">
      <div class="address row">
        <div class="container mt-4 mb-3" v-if="fromCheckout">
          <button class="btn btn-outline-secondary" @click="goBackToCheckout">
            ← Quay lại trang thanh toán
          </button>
        </div>

        <div class="form col-sm-12">
          <h2 class="form-title">ĐỊA CHỈ GIAO HÀNG</h2>
          <p class="form-subtitle">
            All in One Store luôn lắng nghe tận tâm từ khách hàng
          </p>

          <div
            class="address-item mb-4 p-3 rounded-xl position-relative"
            v-for="(address, index) in shippingAddress"
          >
            <label class="form-label mb-1">Địa chỉ giao hàng</label>
            <input
              type="text"
              :value="
                address.street +
                ', ' +
                address.ward +
                ', ' +
                address.district +
                ', ' +
                address.province
              "
              readonly
              class="form-control"
            />
            <span class="default-label" v-if="address.default">Mặc định</span>
            <button class="default-label" v-else @click="setAddressDefault(address.id)">
              Chọn là mặc định
            </button>

            <button
              title="Xoá địa chỉ"
              class="btn-remove"
              @click="removeAddress(address.id)"
            >
              ×
            </button>
          </div>

          <div class="text-center mt-4 mx-3">
            <button class="btn btn-add" @click="showModal = true">
              Thêm địa chỉ mới
            </button>
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

        <select class="input-group" v-model="selectedProvince" @change="loadDistricts">
          <option value="" disabled>Chọn Tỉnh/Thành phố</option>
          <option v-for="prov in provinces" :key="prov.code" :value="prov.code">
            {{ prov.name }}
          </option>
        </select>

        <select
          class="input-group"
          v-model="selectedDistrict"
          @change="loadWards"
          :disabled="!districts.length"
        >
          <option value="" disabled>Chọn Quận/Huyện</option>
          <option v-for="dist in districts" :key="dist.code" :value="dist.code">
            {{ dist.name }}
          </option>
        </select>

        <select class="input-group" v-model="selectedWard" :disabled="!wards.length">
          <option value="" disabled>Chọn Phường/Xã</option>
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
          <select v-model="label">
            <option value="Nhà Riêng">Nhà Riêng</option>
            <option value="Nơi làm việc">Nơi làm việc</option>
          </select>
        </div>
        <div class="input-group">
          <input type="text" placeholder="Ghi chú" v-model="note" />
        </div>
        <div class="actions">
          <button class="cancel-btn" @click="showModal = false">Quay lại</button>
          <button class="submit-btn" @click="addAddress(1)">Xác nhận</button>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import axios from "axios";
import api from "../../Configs/api";
import { notification } from "ant-design-vue";
export default {
  data() {
    return {
      showModal: false,
      name: "Tran Huu Loc",
      phone: "0969214372",
      detailAddress: "95 ap 1",
      provinces: [],
      districts: [],
      wards: [],
      label: "",
      note: "",
      selectedProvince: "",
      selectedDistrict: "",
      selectedWard: "",
      shippingAddress: [],
      fromCheckout: false,
    };
  },
  mounted() {
    this.loadProvinces();
    this.fetchData();
    if (this.$route.query.fromCheckout === "1") {
      this.fromCheckout = true;
    }
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
        const res = await fetch(
          `https://provinces.open-api.vn/api/p/${this.selectedProvince}?depth=2`
        );
        const data = await res.json();
        this.districts = data.districts;
      }
    },
    async loadWards() {
      this.selectedWard = "";
      this.wards = [];
      if (this.selectedDistrict) {
        const res = await fetch(
          `https://provinces.open-api.vn/api/d/${this.selectedDistrict}?depth=2`
        );
        const data = await res.json();
        this.wards = data.wards;
      }
    },
    async fetchData() {
      try {
        const res = await api.get(`/UserAddresses`);
        // console.log(res.data)
        this.shippingAddress = res.data;
      } catch (err) {
        console.log(err.message);
      }
    },
    async removeAddress(id) {
      try {
        const res = await api.delete(`/UserAddresses/${id}`);
        this.fetchData();
      } catch (err) {
        console.log(err.message);
      }
    },
    async setAddressDefault(id) {
      try {
        await api.put(`/UserAddresses/${id}`);
        this.fetchData(); // gọi lại danh sách để reload
        notification.success({
          message: "Thành công",
          description: "Đã chọn địa chỉ mặc định",
        });
      } catch (err) {
        console.log(err.message);
        notification.error({
          message: "Thất bại",
          description: "Không thể chọn địa chỉ mặc định",
        });
      }
    },
    async addAddress() {
      try {
        const provinceObj = this.provinces.find((p) => p.code === this.selectedProvince);
        const districtObj = this.districts.find((d) => d.code === this.selectedDistrict);
        const wardObj = this.wards.find((w) => w.code === this.selectedWard);

        const formData = {
          recipientName: this.name,
          phone: this.phone,
          province: provinceObj?.name || "",
          district: districtObj?.name || "",
          ward: wardObj?.name || "",
          street: this.detailAddress,
          label: this.label,
          isDefault: false,
          note: this.note,
          districtId: districtObj?.code || null,
          wardCode: wardObj?.code || null,
          accounts: "",
        };

        const isContainAddress = this.shippingAddress.some(
          (item) =>
            item.province === formData.province &&
            item.district === formData.district &&
            item.ward === formData.ward &&
            item.street === formData.street
        );
        if (isContainAddress) {
          notification.error({
            message: "Địa chỉ đã tồn tại",
            description: "Vui lòng kiểm tra lại địa chỉ bạn vừa nhập.",
          });
          return;
        }

        await api.post(`/UserAddresses`, formData);

        // Reset form
        this.name = "";
        this.phone = "";
        this.detailAddress = "";
        this.selectedProvince = "";
        this.selectedDistrict = "";
        this.selectedWard = "";
        this.districts = [];
        this.wards = [];
        this.label = "";
        this.note = "";

        this.fetchData();
        this.showModal = false;
        notification.success({
          message: "Thành công",
          description: "Đã thêm địa chỉ mới.",
        });
      } catch (err) {
        console.error(err);
        notification.error({
          message: "Lỗi khi thêm địa chỉ",
          description: err.message,
        });
      }
    },
    goBackToCheckout() {
      this.$router.push({ name: "CheckoutPage" }); // chuyển ngược lại checkout
    },
  },
};
</script>

<style scoped>
/* ==== Layout tổng thể ==== */
.address-container {
  display: flex;
  justify-content: center;
  margin-top: 40px;
  padding: 0 16px;
}

.address {
  width: 700px;
  background-color: #ffffff;
  border-radius: 10px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}

.form {
  padding: 30px;
}

/* ==== Tiêu đề ==== */
.form-title {
  font-size: 1.75rem;
  font-weight: 700;
  color: #1f2937;
}

.form-subtitle {
  color: #6b7280;
  font-size: 0.95rem;
  margin-bottom: 24px;
}

/* ==== Card địa chỉ ==== */
.address-item {
  background-color: #fefefe;
  border: 1px solid #e0e0e0;
  border-radius: 12px;
  padding: 1.25rem;
  margin-bottom: 1rem;
  position: relative;
  transition: box-shadow 0.2s ease;
}

.address-item:hover {
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.08);
}

.form-label {
  font-weight: 600;
  color: #4b5563;
  font-size: 0.9rem;
  margin-bottom: 0.5rem;
}

.form-control {
  width: 100%;
  background-color: white;
  border: 1px solid #d1d5db;
  border-radius: 12px;
  padding: 10px 14px;
  font-size: 0.95rem;
  color: #374151;
  outline: none;
}

/* ==== Nút Mặc định & Chọn mặc định ==== */
.default-label {
  position: absolute;
  top: 16px;
  right: 60px;
  font-size: 0.75rem;
  background-color: #fde68a;
  color: #92400e;
  padding: 4px 10px;
  border-radius: 6px;
  font-weight: bold;
}

button.default-label {
  background-color: #e0f2fe;
  color: #0284c7;
  border: none;
  padding: 4px 10px;
  border-radius: 6px;
  font-weight: 600;
  cursor: pointer;
  transition: background-color 0.2s;
}

button.default-label:hover {
  background-color: #bae6fd;
}

/* ==== Nút Xoá địa chỉ ==== */
.btn-remove {
  position: absolute;
  right: 15px;
  top: 15px;
  font-size: 1.2rem;
  background-color: transparent;
  color: #9ca3af;
  border: none;
  transition: color 0.2s;
}

.btn-remove:hover {
  color: #dc2626;
}

/* ==== Nút Thêm địa chỉ ==== */
.btn-add {
  background-color: #1f2937;
  color: white;
  padding: 12px 36px;
  font-size: 1rem;
  border-radius: 24px;
  border: none;
  transition: background-color 0.3s;
}

.btn-add:hover {
  background-color: #111827;
}

/* ==== Modal thêm địa chỉ ==== */
.overlay {
  position: fixed;
  inset: 0;
  background: rgba(0, 0, 0, 0.4);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 9999;
}

.form-container {
  background-color: #ffffff;
  width: 450px;
  padding: 30px;
  border-radius: 16px;
  box-shadow: 0 10px 24px rgba(0, 0, 0, 0.15);
}

/* ==== Form trong modal ==== */
.input-group {
  display: flex;
  gap: 10px;
  margin-bottom: 10px;
}

input,
select {
  width: 100%;
  padding: 12px;
  font-size: 0.95rem;
  border: 1px solid #cbd5e1;
  border-radius: 10px;
  background-color: #f9fafb;
}

/* ==== Map placeholder box ==== */
.map-box {
  background: #f3f4f6;
  height: 100px;
  margin-bottom: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.add-location {
  border: 1px solid #ccc;
  background: white;
  padding: 8px 15px;
  cursor: pointer;
  border-radius: 4px;
}

/* ==== Loại địa chỉ ==== */
.address-type {
  margin: 15px 0;
}

.address-type label {
  display: block;
  margin-bottom: 5px;
}

/* ==== Nút xác nhận & quay lại trong modal ==== */
.actions {
  display: flex;
  justify-content: space-between;
  gap: 10px;
}

.cancel-btn {
  background-color: #f3f4f6;
  color: #374151;
  padding: 10px 15px;
  border-radius: 8px;
  border: none;
  font-weight: 600;
  cursor: pointer;
}

.cancel-btn:hover {
  background-color: #e5e7eb;
}

.submit-btn {
  background-color: #ef4444;
  color: white;
  padding: 10px 15px;
  border-radius: 8px;
  border: none;
  font-weight: 600;
  cursor: pointer;
}

.submit-btn:hover {
  background-color: #dc2626;
}
</style>
