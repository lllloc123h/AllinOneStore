<template>
  <div class="mt-2 mx-5">
    <h2><strong> Cài đặt tài khoản</strong></h2>
    <h6 class="mb-5">WEAR WHAT MAKES YOU FEEL CONFIDENT</h6>
  </div>



  <div>
    <div v-if="showPopupTaiKhoan" class="popup-overlay">
      <form>
        <div class="popup-content">
          <h4><strong>Thông tin khách hàng</strong></h4>
          <label class="form-label">Họ và tên</label>
          <input type="text" class="form-control" v-model="user.Name">

          <div class="row">
            <div class="col-sm-12">
              <label class="form-label">Địa chỉ Email</label>
              <input type="email" class="form-control" v-model="user.Email">
            </div>
            <div class="col-sm-12">
              <label class="form-label">Số điện thoại</label>
              <input type="text" class="form-control" v-model="user.Phone">
            </div>
          </div>
          <button class="btn-popup" @click="closePopup">OK</button>
        </div>
      </form>
    </div>
    <div v-if="showPopupDoiMatKhau" class="popup-overlay">
      <form>
        <div class="popup-content">
          <h4 class="mt-4"><strong>Đổi mật khẩu</strong></h4>
          <div class="row">
            <div class="col-sm-6">
              <label class="form-label">Mật khẩu cũ</label>
              <input type="password" class="form-control" v-model="user.Password">
            </div>
            <div class="col-sm-6 mt-3"></div>
            <div class="col-sm-6 mt-3">
              <label class="form-label">Mật khẩu mới</label>
              <input type="password" class="form-control">
            </div>
            <div class="col-sm-6 mt-3">
              <label class="form-label">Xác nhận mật khẩu</label>
              <input type="password" class="form-control">
            </div>
          </div>
          <button class="btn-popup" @click="closePopup">OK</button>
        </div>
      </form>
    </div>

    <div class="card" style="width: 240px; height: 320px; margin: auto;">
      <img :src="user.Avatar" alt="" id="avatar" class="card-img" width="100%" height="100%">

    </div>
    <div class="button-holder">
      <div>
        <button class="open-popup-btn" @click="openPopupTaiKhoan">Cài đặt thông tin tài khoản</button>
      </div>
      <div>
        <button class="open-popup-btn" @click="openPopupDoiMatKhau">Đổi mật khẩu</button>
      </div>
      <div>
        <button class="open-popup-btn" @click="openPopupDiaChi">Địa chỉ nhận hàng</button>
      </div>
    </div>
    <!-- <div class="div-input">
        <button class="btn" type="button" @click="updateProfile">Cập Nhật</button>
      </div> -->

  </div>
</template>

<script setup>
import { reactive, ref, onMounted, watch } from 'vue'
import axios from 'axios'
import avatar from '../../assets/imgs/avarta_Kha.png'
import { useRouter } from 'vue-router'

const GHN_TOKEN = '86a66676-4794-11f0-8342-3e24ae01a77c'

const user = reactive({
  Name: 'Nguyễn Nhật Khả',
  Phone: '0494953535',
  Avatar: avatar,
  Email: 'KhaDepTraiNhatVuTru@gmail.com',
  Password: '123123',
  Address: 'Cà Mau'
})

// Danh sách địa phương
const provinces = ref([])
const districts = ref([])
const wards = ref([])

const selectedProvinceId = ref('')
const selectedDistrictId = ref('')
const selectedWard = ref('')

const selectedProvinceName = ref('')
const selectedDistrictName = ref('')
const showPopupTaiKhoan = ref(false)
const showPopupDoiMatKhau = ref(false)
const showPopupDiaChi = ref(false)
const popupMessage = ref("")
const router = useRouter()
const openPopupTaiKhoan = () => {
  showPopupTaiKhoan.value = true
}
const openPopupDoiMatKhau = () => {
  showPopupDoiMatKhau.value = true
}
const openPopupDiaChi = () => {
  router.push({
    name: "shippingaddress"
  }

  )
}
const openPopup = () => {
  showPopup.value = true
}

// load tỉnh khi khởi động
onMounted(async () => {
  try {
    const res = await axios.get('https://online-gateway.ghn.vn/shiip/public-api/master-data/province', {
      headers: { Token: GHN_TOKEN }
    })
    provinces.value = res.data.data
  } catch (err) {
    console.error('Lỗi load tỉnh:', err)
  }
})

// chọn tỉnh, load huyện
watch(selectedProvinceId, async (newVal) => {
  if (!newVal) return
  try {
    const res = await axios.get('https://online-gateway.ghn.vn/shiip/public-api/master-data/district', {
      headers: { Token: GHN_TOKEN },
      params: { province_id: newVal }
    })
    districts.value = res.data.data
    wards.value = []
    selectedDistrictId.value = ''
    selectedWard.value = ''

    const selected = provinces.value.find(p => p.ProvinceID === newVal)
    selectedProvinceName.value = selected?.ProvinceName || ''
  } catch (err) {
    console.error('Lỗi load huyện:', err)
  }
})

// chọn huyện, load xã
watch(selectedDistrictId, async (newVal) => {
  if (!newVal) return
  try {
    const res = await axios.get('https://online-gateway.ghn.vn/shiip/public-api/master-data/ward', {
      headers: { Token: GHN_TOKEN },
      params: { district_id: newVal }
    })
    wards.value = res.data.data
    selectedWard.value = ''

    const selected = districts.value.find(d => d.DistrictID === newVal)
    selectedDistrictName.value = selected?.DistrictName || ''
  } catch (err) {
    console.error('Lỗi load xã:', err)
  }
})

// cập nhật user.Address
watch([selectedProvinceName, selectedDistrictName, selectedWard], () => {
  const parts = [selectedWard.value, selectedDistrictName.value, selectedProvinceName.value]
  user.Address = parts.filter(Boolean).join(', ')
})

const updateProfile = async () => {
  try {
    const dto = {
      name: user.Name,
      email: user.Email,
      phone: user.Phone,
      avatar: user.Avatar,
      address: user.Address,
      provinceId: selectedProvinceId.value,
      districtId: selectedDistrictId.value,
      wardName: selectedWard.value
    }

    const token = localStorage.getItem("token")

    const res = await axios.put("http://localhost:8080/api/Accounts/profile", dto, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    })

    alert(res.data.message || "Cập nhật thành công")
  } catch (err) {
    alert(err.response?.data?.message || "Đã xảy ra lỗi khi cập nhật")
  }
}
</script>

<style scoped>
form {
  height: auto;
  width: 50%;
  margin: auto;
  padding: 10px;
  /* border: 1px solid black; */
  border-radius: 15px;
  margin-bottom: 50px;
}

.btn {
  background-color: burlywood;
  right: 45%;
  font-size: larger;
  height: 60px;
  width: 180px;
  border-radius: 25px;
}

template {
  position: relative;
}

.popup-overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
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
  width: 300px;
  margin: 0 auto;
}

.div-input {
  display: flex;
  justify-content: center;
  margin-top: 30px;
}

.btn-popup {
  background-color: #deb887;
  /* burlywood */
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

.btn-popup:active {
  transform: scale(0.95);
}

.btn:hover {
  background-color: rgb(255, 168, 53);
}

.btn:hover {
  background-color: rgb(255, 168, 53);
}

.div-input {
  position: relative;
  min-height: 90px;
}

.open-popup-btn {
  background-color: #deb887;
  /* burlywood */
  color: white;
  font-size: 16px;
  font-weight: 600;
  padding: 12px 24px;
  border: none;
  border-radius: 25px;
  cursor: pointer;
  margin: 10px 5px;
  transition: background-color 0.3s ease, transform 0.2s ease;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.15);
}

.open-popup-btn:hover {
  background-color: #ffa835;
  transform: scale(1.05);
}

.open-popup-btn:active {
  transform: scale(0.95);
}

.button-holder {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  align-items: stretch;
  max-width: 300px;
  width: 100%;
  margin: 20px auto;
}

.button-holder>div>button {
  min-width: 100%;
}

.open-popup-btn {
  background-color: #deb887;
  /* burlywood */
  color: white;
  font-size: 16px;
  font-weight: 600;
  padding: 12px 24px;
  border: none;
  border-radius: 25px;
  cursor: pointer;
  transition: background-color 0.3s ease, transform 0.2s ease;
}
</style>
