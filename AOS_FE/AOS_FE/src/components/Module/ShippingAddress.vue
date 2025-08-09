<template>
  <div class="shipping-address-page">
    <!-- Hero Section -->
    <div class="hero-section">
      <div class="container">
        <div class="hero-content">
          <h1 class="hero-title">
            <i class="bi bi-geo-alt-fill me-3"></i>
            Quản lý địa chỉ giao hàng
          </h1>
          <p class="hero-subtitle">
            Thêm và quản lý địa chỉ giao hàng của bạn một cách dễ dàng
          </p>
        </div>
      </div>
    </div>

    <div class="main-content">
      <div class="container">
        <!-- Back to Checkout Button -->
        <div v-if="fromCheckout" class="back-button-container">
          <button class="back-button" @click="goBackToCheckout">
            <i class="bi bi-arrow-left me-2"></i>
            Quay lại trang thanh toán
          </button>
        </div>

        <div class="content-wrapper">
          <!-- Address Management Section -->
          <div class="address-management-card">
            <div class="card-header">
              <h2 class="card-title">
                <i class="bi bi-house-door me-2"></i>
                Địa chỉ đã lưu
              </h2>
              <p class="card-subtitle">Quản lý các địa chỉ giao hàng của bạn</p>
            </div>

            <div class="address-list">
              <!-- No Address State -->
              <div v-if="!shippingAddress.length" class="no-address-state">
                <div class="no-address-icon">
                  <i class="bi bi-house-add"></i>
                </div>
                <h3 class="no-address-title">Chưa có địa chỉ giao hàng</h3>
                <p class="no-address-description">
                  Thêm địa chỉ đầu tiên để bắt đầu đặt hàng
                </p>
                <button class="add-first-address-btn" @click="showModal = true">
                  <i class="bi bi-plus-lg me-2"></i>
                  Thêm địa chỉ đầu tiên
                </button>
              </div>

              <!-- Address Cards -->
              <div v-else class="address-cards">
                <div
                  v-for="(address, index) in shippingAddress"
                  :key="address.id"
                  :class="['address-card', { 'default-address': address.default }]"
                >
                  <div class="address-card-header">
                    <div class="address-label">
                      <i :class="getAddressIcon(address.label)" class="label-icon"></i>
                      <span class="label-text">{{ address.label || "Địa chỉ" }}</span>
                    </div>
                    <div class="address-actions">
                      <span v-if="address.default" class="default-badge">
                        <i class="bi bi-star-fill me-1"></i>
                        Mặc định
                      </span>
                      <button
                        v-else
                        class="set-default-btn"
                        @click="setAddressDefault(address.id)"
                        title="Đặt làm mặc định"
                      >
                        <i class="bi bi-star me-1"></i>
                        Chọn mặc định
                      </button>
                      <button
                        class="delete-btn"
                        @click="removeAddress(address.id)"
                        title="Xóa địa chỉ"
                      >
                        <i class="bi bi-trash"></i>
                      </button>
                    </div>
                  </div>

                  <div class="address-details">
                    <div class="recipient-info">
                      <h4 class="recipient-name">{{ address.recipientName }}</h4>
                      <span class="recipient-phone">
                        <i class="bi bi-telephone me-1"></i>
                        {{ address.phone }}
                      </span>
                    </div>
                    <div class="full-address">
                      <i class="bi bi-geo-alt me-2"></i>
                      {{ getFullAddress(address) }}
                    </div>
                    <div v-if="address.note" class="address-note">
                      <i class="bi bi-sticky me-1"></i>
                      {{ address.note }}
                    </div>
                  </div>
                </div>
              </div>

              <!-- Add New Address Button -->
              <div v-if="shippingAddress.length" class="add-address-section">
                <button class="add-address-btn" @click="showModal = true">
                  <i class="bi bi-plus-circle me-2"></i>
                  Thêm địa chỉ mới
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Add/Edit Address Modal -->
    <div v-if="showModal" class="modal-overlay">
      <div class="modal-container">
        <div class="modal-header">
          <h3 class="modal-title">
            <i class="bi bi-house-add me-2"></i>
            Thêm địa chỉ mới
          </h3>
          <button class="modal-close-btn" @click="closeModal">
            <i class="bi bi-x-lg"></i>
          </button>
        </div>

        <div class="modal-content">
          <form @submit.prevent="addAddress" class="address-form">
            <!-- Personal Information -->
            <div class="form-section">
              <h4 class="section-title">
                <i class="bi bi-person me-2"></i>
                Thông tin người nhận
              </h4>
              <div class="form-row">
                <div class="form-group">
                  <label class="form-label">
                    <i class="bi bi-person-circle me-1"></i>
                    Họ và tên
                  </label>
                  <input
                    type="text"
                    class="form-input"
                    placeholder="Nhập họ và tên"
                    v-model="name"
                    required
                  />
                </div>
                <div class="form-group">
                  <label class="form-label">
                    <i class="bi bi-telephone me-1"></i>
                    Số điện thoại
                  </label>
                  <input
                    type="tel"
                    class="form-input"
                    placeholder="Nhập số điện thoại"
                    v-model="phone"
                    required
                  />
                </div>
              </div>
            </div>

            <!-- Address Information -->
            <div class="form-section">
              <h4 class="section-title">
                <i class="bi bi-geo-alt me-2"></i>
                Thông tin địa chỉ
              </h4>

              <div class="form-row">
                <div class="form-group">
                  <label class="form-label">Tỉnh/Thành phố</label>
                  <select
                    class="form-select"
                    v-model="selectedProvince"
                    @change="loadDistricts"
                    required
                  >
                    <option value="" disabled>Chọn Tỉnh/Thành phố</option>
                    <option
                      v-for="prov in provinces"
                      :key="prov.ProvinceID"
                      :value="prov.ProvinceID"
                    >
                      {{ prov.ProvinceName }}
                    </option>
                  </select>
                </div>
                <div class="form-group">
                  <label class="form-label">Quận/Huyện</label>
                  <select
                    class="form-select"
                    v-model="selectedDistrict"
                    @change="loadWards"
                    :disabled="!districts.length"
                    required
                  >
                    <option value="" disabled>Chọn Quận/Huyện</option>
                    <option
                      v-for="dist in districts"
                      :key="dist.DistrictID"
                      :value="dist.DistrictID"
                    >
                      {{ dist.DistrictName }}
                    </option>
                  </select>
                </div>
              </div>

              <div class="form-row">
                <div class="form-group">
                  <label class="form-label">Phường/Xã</label>
                  <select
                    class="form-select"
                    v-model="selectedWard"
                    :disabled="!wards.length"
                    required
                  >
                    <option value="" disabled>Chọn Phường/Xã</option>
                    <option
                      v-for="ward in wards"
                      :key="ward.WardCode"
                      :value="ward.WardCode"
                    >
                      {{ ward.WardName }}
                    </option>
                  </select>
                </div>
                <div class="form-group">
                  <label class="form-label">Loại địa chỉ</label>
                  <select class="form-select" v-model="label">
                    <option value="Nhà Riêng">🏠 Nhà Riêng</option>
                    <option value="Nơi làm việc">🏢 Nơi làm việc</option>
                    <option value="Khác">📍 Khác</option>
                  </select>
                </div>
              </div>

              <div class="form-group">
                <label class="form-label">
                  <i class="bi bi-house-door me-1"></i>
                  Địa chỉ cụ thể
                </label>
                <input
                  type="text"
                  class="form-input"
                  placeholder="Số nhà, tên đường..."
                  v-model="detailAddress"
                  required
                />
              </div>

              <div class="form-group">
                <label class="form-label">
                  <i class="bi bi-chat-text me-1"></i>
                  Ghi chú (tùy chọn)
                </label>
                <textarea
                  class="form-textarea"
                  placeholder="Ghi chú thêm về địa chỉ..."
                  v-model="note"
                  rows="3"
                ></textarea>
              </div>
            </div>

            <!-- Location Helper -->
            <div class="location-helper">
              <div class="location-icon">
                <i class="bi bi-geo-alt-fill"></i>
              </div>
              <div class="location-text">
                <h6>Định vị chính xác</h6>
                <p>
                  Chúng tôi sẽ sử dụng thông tin này để giao hàng chính xác nhất đến bạn
                </p>
              </div>
            </div>

            <!-- Form Actions -->
            <div class="form-actions">
              <button type="button" class="cancel-btn" @click="closeModal">
                <i class="bi bi-x-circle me-2"></i>
                Hủy bỏ
              </button>
              <button type="submit" class="submit-btn" :disabled="!isFormValid">
                <i class="bi bi-check-circle me-2"></i>
                Lưu địa chỉ
              </button>
            </div>
          </form>
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
      name: "Trần Hữu Lộc",
      phone: "0969214372",
      detailAddress: "123 Đường ABC",
      provinces: [],
      districts: [],
      wards: [],
      label: "Nhà Riêng",
      note: "",
      selectedProvince: "",
      selectedDistrict: "",
      selectedWard: "",
      shippingAddress: [],
      fromCheckout: false,
    };
  },
  computed: {
    isFormValid() {
      return (
        this.name &&
        this.phone &&
        this.selectedProvince &&
        this.selectedDistrict &&
        this.selectedWard &&
        this.detailAddress
      );
    },
  },
  mounted() {
    this.loadProvinces();
    this.fetchData();
    if (this.$route.query.fromCheckout === "1") {
      this.fromCheckout = true;
    }
  },
  methods: {
    getAddressIcon(label) {
      const iconMap = {
        "Nhà Riêng": "bi-house-door",
        "Nơi làm việc": "bi-building",
        Khác: "bi-geo-alt",
      };
      return iconMap[label] || "bi-geo-alt";
    },

    getFullAddress(address) {
      return `${address.street}, ${address.ward}, ${address.district}, ${address.province}`;
    },

    closeModal() {
      this.showModal = false;
      this.resetForm();
    },

    resetForm() {
      this.name = "";
      this.phone = "";
      this.detailAddress = "";
      this.selectedProvince = "";
      this.selectedDistrict = "";
      this.selectedWard = "";
      this.districts = [];
      this.wards = [];
      this.label = "Nhà Riêng";
      this.note = "";
    },
    async loadProvinces() {
      const res = await fetch(
        "https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/province",
        {
          headers: {
            Token: "cc2fea72-5000-11f0-9b81-222185cb68c8",
            "Content-Type": "application/json",
          },
        }
      );

      this.provinces = await res.json();
      this.provinces = this.provinces.data;
    },
    async loadDistricts() {
      this.selectedDistrict = "";
      this.selectedWard = "";
      this.districts = [];
      this.wards = [];
      console.log("province_id ", this.selectedProvince);

      if (this.selectedProvince) {
        const res = await fetch(
          "https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/district",
          {
            method: "POST",
            headers: {
              Token: "cc2fea72-5000-11f0-9b81-222185cb68c8",
              "Content-Type": "application/json",
            },
            body: JSON.stringify({
              province_id: this.selectedProvince,
            }),
          }
        );

        const data = await res.json();
        this.districts = data.data;
      }
    },
    async loadWards() {
      this.selectedWard = "";
      this.wards = [];
      if (this.selectedDistrict) {
        const res = await fetch(
          "https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/ward?district_id",
          {
            method: "POST",
            headers: {
              Token: "cc2fea72-5000-11f0-9b81-222185cb68c8",
              "Content-Type": "application/json",
            },
            body: JSON.stringify({
              district_id: this.selectedDistrict,
            }),
          }
        );
        console.log("select ward ", this.selectedDistrict);
        const data = await res.json();
        this.wards = data.data;
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
        const provinceObj = this.provinces.find(
          (p) => p.ProvinceID === this.selectedProvince
        );
        const districtObj = this.districts.find(
          (d) => d.DistrictID === this.selectedDistrict
        );
        const wardObj = this.wards.find((w) => w.WardCode === this.selectedWard);

        const formData = {
          recipientName: this.name,
          phone: this.phone,
          province: provinceObj?.ProvinceName || "",
          district: districtObj?.DistrictName || "",
          ward: wardObj?.WardName || "",
          street: this.detailAddress,
          label: this.label,
          isDefault: false,
          note: this.note,
          districtId: districtObj?.DistrictID || null,
          wardCode: wardObj?.WardCode || null,
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

        this.resetForm();
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
/* ==================== GLOBAL STYLES ==================== */
.shipping-address-page {
  font-family: "Inter", "Segoe UI", sans-serif;
  background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
  min-height: 100vh;
}

/* ==================== HERO SECTION ==================== */
.hero-section {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 3rem 0 2rem;
  color: white;
  position: relative;
  overflow: hidden;
}

.hero-section::before {
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: url('data:image/svg+xml,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1000 100" fill="rgba(255,255,255,0.1)"><path d="M0,20 Q250,80 500,20 T1000,20 L1000,0 L0,0 Z"/></svg>')
    repeat-x;
  background-size: 1000px 100px;
  animation: wave 10s infinite linear;
}

@keyframes wave {
  0% {
    background-position-x: 0;
  }
  100% {
    background-position-x: 1000px;
  }
}

.hero-content {
  text-align: center;
  position: relative;
  z-index: 1;
}

.hero-title {
  font-size: 2.5rem;
  font-weight: 700;
  margin-bottom: 1rem;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.hero-subtitle {
  font-size: 1.2rem;
  opacity: 0.9;
  margin: 0;
}

/* ==================== MAIN CONTENT ==================== */
.main-content {
  padding: 2rem 0;
  margin-top: -1rem;
  position: relative;
  z-index: 2;
}

.content-wrapper {
  max-width: 900px;
  margin: 0 auto;
}

/* ==================== BACK BUTTON ==================== */
.back-button-container {
  margin-bottom: 2rem;
}

.back-button {
  background: white;
  border: 2px solid #e9ecef;
  border-radius: 12px;
  padding: 12px 20px;
  font-weight: 600;
  color: #6c757d;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
}

.back-button:hover {
  border-color: #667eea;
  color: #667eea;
  transform: translateY(-2px);
  box-shadow: 0 8px 25px rgba(102, 126, 234, 0.15);
}

/* ==================== ADDRESS MANAGEMENT CARD ==================== */
.address-management-card {
  background: white;
  border-radius: 20px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  border: 1px solid rgba(102, 126, 234, 0.1);
}

.card-header {
  background: linear-gradient(135deg, #f8f9fa 0%, #ffffff 100%);
  padding: 2rem;
  border-bottom: 1px solid #e9ecef;
  text-align: center;
}

.card-title {
  font-size: 1.75rem;
  font-weight: 700;
  color: #2d3748;
  margin-bottom: 0.5rem;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  display: flex;
  align-items: center;
  justify-content: center;
}

.card-subtitle {
  color: #6c757d;
  font-size: 1rem;
  margin: 0;
}

/* ==================== NO ADDRESS STATE ==================== */
.no-address-state {
  padding: 4rem 2rem;
  text-align: center;
}

.no-address-icon {
  font-size: 4rem;
  color: #e9ecef;
  margin-bottom: 1.5rem;
}

.no-address-title {
  font-size: 1.5rem;
  font-weight: 700;
  color: #2d3748;
  margin-bottom: 0.5rem;
}

.no-address-description {
  color: #6c757d;
  margin-bottom: 2rem;
  font-size: 1.1rem;
}

.add-first-address-btn {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border: none;
  border-radius: 50px;
  padding: 15px 35px;
  font-weight: 700;
  font-size: 1.1rem;
  transition: all 0.3s ease;
  display: inline-flex;
  align-items: center;
}

.add-first-address-btn:hover {
  transform: translateY(-3px);
  box-shadow: 0 15px 35px rgba(102, 126, 234, 0.3);
}

/* ==================== ADDRESS CARDS ==================== */
.address-cards {
  padding: 1.5rem;
  display: grid;
  gap: 1.5rem;
}

.address-card {
  background: linear-gradient(135deg, #ffffff 0%, #f8f9fa 100%);
  border: 2px solid #e9ecef;
  border-radius: 15px;
  padding: 1.5rem;
  transition: all 0.3s ease;
  position: relative;
  overflow: hidden;
}

.address-card::before {
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 4px;
  background: linear-gradient(90deg, #e9ecef 0%, #e9ecef 100%);
  transition: all 0.3s ease;
}

.address-card:hover {
  border-color: rgba(102, 126, 234, 0.3);
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
  transform: translateY(-2px);
}

.address-card:hover::before {
  background: linear-gradient(90deg, #667eea 0%, #764ba2 100%);
}

.address-card.default-address {
  border-color: #f59e0b;
  background: linear-gradient(135deg, #fefce8 0%, #fef3c7 100%);
}

.address-card.default-address::before {
  background: linear-gradient(90deg, #f59e0b 0%, #d97706 100%);
}

.address-card-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 1rem;
}

.address-label {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.label-icon {
  color: #667eea;
  font-size: 1.2rem;
}

.label-text {
  font-weight: 600;
  color: #2d3748;
  font-size: 1rem;
}

.address-actions {
  display: flex;
  align-items: center;
  gap: 0.75rem;
}

.default-badge {
  background: linear-gradient(135deg, #fef3c7 0%, #fde68a 100%);
  color: #92400e;
  padding: 0.25rem 0.75rem;
  border-radius: 20px;
  font-size: 0.8rem;
  font-weight: 600;
  display: flex;
  align-items: center;
  border: 1px solid #f59e0b;
}

.set-default-btn {
  background: transparent;
  color: #6c757d;
  border: 1px solid #e9ecef;
  border-radius: 8px;
  padding: 0.25rem 0.75rem;
  font-size: 0.8rem;
  font-weight: 600;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
}

.set-default-btn:hover {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border-color: #667eea;
}

.delete-btn {
  background: transparent;
  color: #dc3545;
  border: 1px solid #dc3545;
  border-radius: 8px;
  padding: 0.25rem 0.5rem;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 32px;
  height: 32px;
}

.delete-btn:hover {
  background: #dc3545;
  color: white;
  transform: scale(1.1);
}

.address-details {
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
}

.recipient-info {
  display: flex;
  flex-direction: column;
  gap: 0.25rem;
}

.recipient-name {
  font-size: 1.1rem;
  font-weight: 700;
  color: #2d3748;
  margin: 0;
}

.recipient-phone {
  color: #667eea;
  font-weight: 600;
  font-size: 0.9rem;
  display: flex;
  align-items: center;
}

.full-address {
  color: #6c757d;
  line-height: 1.5;
  display: flex;
  align-items: flex-start;
  gap: 0.5rem;
}

.address-note {
  color: #8b5cf6;
  font-style: italic;
  font-size: 0.9rem;
  display: flex;
  align-items: center;
  gap: 0.5rem;
  background: rgba(139, 92, 246, 0.1);
  padding: 0.5rem;
  border-radius: 8px;
}

/* ==================== ADD ADDRESS SECTION ==================== */
.add-address-section {
  padding: 1.5rem;
  text-align: center;
  border-top: 1px solid #e9ecef;
}

.add-address-btn {
  background: linear-gradient(135deg, #10b981 0%, #059669 100%);
  color: white;
  border: none;
  border-radius: 12px;
  padding: 15px 30px;
  font-weight: 600;
  font-size: 1rem;
  transition: all 0.3s ease;
  display: inline-flex;
  align-items: center;
}

.add-address-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 25px rgba(16, 185, 129, 0.3);
}

/* ==================== MODAL STYLES ==================== */
.modal-overlay {
  position: fixed;
  inset: 0;
  background: rgba(0, 0, 0, 0.7);
  backdrop-filter: blur(5px);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 9999;
  padding: 2rem;
}

.modal-container {
  background: white;
  border-radius: 20px;
  width: 100%;
  max-width: 600px;
  max-height: 90vh;
  overflow: hidden;
  box-shadow: 0 20px 50px rgba(0, 0, 0, 0.2);
  animation: modalSlideIn 0.3s ease;
}

@keyframes modalSlideIn {
  from {
    opacity: 0;
    transform: translateY(-20px) scale(0.95);
  }
  to {
    opacity: 1;
    transform: translateY(0) scale(1);
  }
}

.modal-header {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  padding: 1.5rem 2rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.modal-title {
  font-size: 1.3rem;
  font-weight: 700;
  margin: 0;
  display: flex;
  align-items: center;
}

.modal-close-btn {
  background: transparent;
  border: none;
  color: white;
  font-size: 1.2rem;
  padding: 0.5rem;
  border-radius: 8px;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
}

.modal-close-btn:hover {
  background: rgba(255, 255, 255, 0.2);
}

.modal-content {
  padding: 2rem;
  max-height: calc(90vh - 200px);
  overflow-y: auto;
}

/* ==================== FORM STYLES ==================== */
.address-form {
  display: flex;
  flex-direction: column;
  gap: 2rem;
}

.form-section {
  background: linear-gradient(135deg, #f8f9fa 0%, #ffffff 100%);
  border: 1px solid #e9ecef;
  border-radius: 15px;
  padding: 1.5rem;
}

.section-title {
  font-size: 1.1rem;
  font-weight: 700;
  color: #2d3748;
  margin-bottom: 1rem;
  display: flex;
  align-items: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.form-row {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 1rem;
  margin-bottom: 1rem;
}

.form-row:last-child {
  margin-bottom: 0;
}

.form-group {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.form-label {
  font-weight: 600;
  color: #2d3748;
  font-size: 0.9rem;
  display: flex;
  align-items: center;
}

.form-input,
.form-select,
.form-textarea {
  padding: 12px 15px;
  border: 2px solid #e9ecef;
  border-radius: 10px;
  font-size: 1rem;
  transition: all 0.3s ease;
  background: white;
}

.form-input:focus,
.form-select:focus,
.form-textarea:focus {
  outline: none;
  border-color: #667eea;
  box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
}

.form-select:disabled {
  background: #f8f9fa;
  color: #6c757d;
  cursor: not-allowed;
}

.form-textarea {
  resize: vertical;
  min-height: 80px;
  font-family: inherit;
}

/* ==================== LOCATION HELPER ==================== */
.location-helper {
  background: linear-gradient(135deg, #e0f2fe 0%, #b3e5fc 100%);
  border: 1px solid #0284c7;
  border-radius: 12px;
  padding: 1rem;
  display: flex;
  align-items: center;
  gap: 1rem;
}

.location-icon {
  color: #0284c7;
  font-size: 1.5rem;
  flex-shrink: 0;
}

.location-text h6 {
  font-weight: 700;
  color: #0c4a6e;
  margin: 0 0 0.25rem 0;
}

.location-text p {
  color: #075985;
  margin: 0;
  font-size: 0.9rem;
  line-height: 1.4;
}

/* ==================== FORM ACTIONS ==================== */
.form-actions {
  display: flex;
  gap: 1rem;
  justify-content: flex-end;
}

.cancel-btn {
  background: transparent;
  color: #6c757d;
  border: 2px solid #e9ecef;
  border-radius: 12px;
  padding: 12px 24px;
  font-weight: 600;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
}

.cancel-btn:hover {
  border-color: #6c757d;
  color: #495057;
}

.submit-btn {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border: none;
  border-radius: 12px;
  padding: 12px 24px;
  font-weight: 600;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
}

.submit-btn:hover:not(:disabled) {
  transform: translateY(-2px);
  box-shadow: 0 8px 25px rgba(102, 126, 234, 0.3);
}

.submit-btn:disabled {
  opacity: 0.6;
  cursor: not-allowed;
  transform: none;
  box-shadow: none;
}

/* ==================== RESPONSIVE DESIGN ==================== */
@media (max-width: 768px) {
  .hero-title {
    font-size: 2rem;
  }

  .hero-subtitle {
    font-size: 1rem;
  }

  .card-header {
    padding: 1.5rem;
  }

  .card-title {
    font-size: 1.5rem;
    flex-direction: column;
    gap: 0.5rem;
  }

  .address-card-header {
    flex-direction: column;
    align-items: flex-start;
    gap: 1rem;
  }

  .address-actions {
    align-self: stretch;
    justify-content: space-between;
  }

  .modal-container {
    margin: 1rem;
    max-width: none;
  }

  .modal-header {
    padding: 1rem 1.5rem;
  }

  .modal-content {
    padding: 1.5rem;
  }

  .form-row {
    grid-template-columns: 1fr;
  }

  .form-actions {
    flex-direction: column;
  }

  .location-helper {
    flex-direction: column;
    text-align: center;
  }
}

@media (max-width: 480px) {
  .main-content {
    padding: 1rem 0;
  }

  .content-wrapper {
    padding: 0 1rem;
  }

  .no-address-state {
    padding: 2rem 1rem;
  }

  .address-cards {
    padding: 1rem;
  }

  .address-card {
    padding: 1rem;
  }
}
</style>
