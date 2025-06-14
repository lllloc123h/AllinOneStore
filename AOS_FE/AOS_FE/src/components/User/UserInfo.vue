<template>
  <div class="mt-2 mx-5">
    <h2><strong> Cài đặt tài khoản</strong></h2>
    <h6 class="mb-5">WEAR WHAT MAKES YOU FEEL CONFIDENT</h6>
  </div>

  <div>
    <form>
      <!-- Avatar -->
      <div class="card" style="width: 240px; height: 320px; margin: auto;">
        <img :src="user.Avatar" alt="" id="avatar" class="card-img" width="100%" height="100%">
      </div>

      <!-- Thông tin khách hàng -->
      <h4><strong>Thông tin khách hàng</strong></h4>
      <label class="form-label">Họ và tên</label>
      <input type="text" class="form-control" v-model="user.Name">

      <div class="row">
        <div class="col-sm-6">
          <label class="form-label">Địa chỉ Email</label>
          <input type="email" class="form-control" v-model="user.Email">
        </div>
        <div class="col-sm-6">
          <label class="form-label">Số điện thoại</label>
          <input type="text" class="form-control" v-model="user.Phone">
        </div>
      </div>

      <!-- Đổi mật khẩu -->
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

      <!-- Địa chỉ -->
      <h4 class="mt-4"><strong>Địa chỉ</strong></h4>

      <div class="row">
        <div class="col-sm-4">
          <label class="form-label">Tỉnh / Thành phố</label>
          <select class="form-select" v-model="selectedProvinceId">
            <option disabled value="">-- Chọn Tỉnh --</option>
            <option v-for="p in provinces" :key="p.ProvinceID" :value="p.ProvinceID">
              {{ p.ProvinceName }}
            </option>
          </select>
        </div>

        <div class="col-sm-4">
          <label class="form-label">Quận / Huyện</label>
          <select class="form-select" v-model="selectedDistrictId">
            <option disabled value="">-- Chọn Huyện --</option>
            <option v-for="d in districts" :key="d.DistrictID" :value="d.DistrictID">
              {{ d.DistrictName }}
            </option>
          </select>
        </div>

        <div class="col-sm-4">
          <label class="form-label">Phường / Xã</label>
          <select class="form-select" v-model="selectedWard">
            <option disabled value="">-- Chọn Xã --</option>
            <option v-for="w in wards" :key="w.WardCode" :value="w.WardName">
              {{ w.WardName }}
            </option>
          </select>
        </div>
      </div>

      <label class="form-label mt-3">Địa chỉ đầy đủ</label>
      <textarea class="form-control input-address" v-model="user.Address" readonly></textarea>

      <div class="div-input">
        <button class="btn my-4" type="button" @click="updateProfile">Cập Nhật</button>
      </div>
    </form>
  </div>
</template>

<script setup>
import { reactive, ref, onMounted, watch } from 'vue'
import axios from 'axios'
import avatar from '../../assets/imgs/avarta_Kha.png'

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
  border: 1px solid black;
  border-radius: 15px;
  margin-bottom: 50px;
}

.btn {
  background-color: burlywood;
  right: 45%;
  position: absolute;
  font-size: larger;
  height: 60px;
  width: 180px;
  border-radius: 25px;
}

.btn:hover {
  background-color: rgb(255, 168, 53);
}

.div-input {
  position: relative;
  min-height: 90px;
}
</style>
