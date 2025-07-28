<template>
  <div class="container-fluid row">
    <div class="aside col-3">
      <Dashboard></Dashboard>
    </div>
    <div class="article col-9">
      <form class="account-form shadow-lg p-4 rounded bg-white"
        @submit.prevent="props.action === 'create' ? submitForm() : submitUpdateForm()">
        <div class="mb-4" :style="(props.action === 'view' || props.action === 'create') ? 'display:none;' : ''">
          <label for="id" class="form-label text-capitalize"></label>
          <input id="id" v-model="formData.id" v-if="props.action !== 'create'" :hidden="props.action === 'view'"
            type="number" class="form-control custom-input" placeholder="Enter id" />
        </div>
        <div class="mb-4">
          <label for="email" class="form-label text-capitalize fw-semibold">Email</label>
          <input id="email" v-model="formData.email" type="email" class="form-control custom-input"
            placeholder="Enter email" />
        </div>

        <div class="mb-4">
          <label for="password" class="form-label text-capitalize fw-semibold">Password</label>
          <input id="password" v-model="formData.password" type="password" class="form-control custom-input"
            placeholder="Enter password" />
        </div>

        <div class="mb-4">
          <label for="fullname" class="form-label text-capitalize fw-semibold">Full Name</label>
          <input id="fullname" v-model="formData.fullname" type="text" class="form-control custom-input"
            placeholder="Enter fullname" />
        </div>

        <div class="mb-4">
          <label class="form-label text-capitalize fw-semibold">Avatar</label>
          <ImageUpload :max-images="1" :max-videos="0" folder="profiles" :heightImg="0" :widthImg="0"
            :videoDuration="60" ref="imageUploadRef" @result-uploaded="handleGetUploadUrl" class="upload-container" />
          <div v-if="formData.avatar" class="mt-3 text-center">
            <label class="form-label">Preview Avatar:</label>
            <img :src="formData.avatar" alt="Avatar Preview" class="img-thumbnail avatar-preview rounded-circle"
              style="width: 100px; height: 100px; object-fit: cover;" />
          </div>
        </div>

        <div class="row mb-4">
          <div class="col-md-4">
            <label for="phone" class="form-label text-capitalize fw-semibold">Phone</label>
            <input id="phone" v-model="formData.phone" type="text" class="form-control custom-input"
              placeholder="Enter phone" />
          </div>

          <div class="col-md-4">
            <label for="averageOrderValue" class="form-label text-capitalize fw-semibold">Avg. Order Value</label>
            <input id="averageOrderValue" v-model="formData.averageOrderValue" type="number"
              class="form-control custom-input" placeholder="Enter average order value" />
          </div>
          <div class="col-md-4">
            <label for="userRank" class="form-label text-capitalize fw-semibold">User Rank</label>
            <input id="userRank" v-model="formData.userRank" type="text" class="form-control custom-input"
              placeholder="Enter user rank" />
          </div>
        </div>

        <div class="row mb-4">
          <div class="col-md-4">
            <label for="totalSpent" class="form-label text-capitalize fw-semibold">Total Spent</label>
            <input id="totalSpent" v-model="formData.totalSpent" type="number" class="form-control custom-input"
              placeholder="Enter total spent" />
          </div>

          <div class="col-md-4">
            <label for="totalOrder" class="form-label text-capitalize fw-semibold">Total Orders</label>
            <input id="totalOrder" v-model="formData.totalOrder" type="number" class="form-control custom-input"
              placeholder="Enter total orders" />
          </div>

          <div class="col-md-4">
            <label for="loyaltyPoint" class="form-label text-capitalize fw-semibold">Loyalty Points</label>
            <input id="loyaltyPoint" v-model="formData.loyaltyPoint" type="number" class="form-control custom-input"
              placeholder="Enter loyalty points" />
          </div>
        </div>

        <div class="role-list mb-4 p-4 rounded bg-light border">
          <label class="form-label mb-3 fw-semibold">Roles</label>
          <div class="row">
            <div v-for="role in dropDownListRoles" :key="role.id" class="col-md-6 mb-2">
              <label class="role-item w-100">
                <input type="checkbox" :value="role.id" v-model="selectedRoles" class="form-check-input me-2" />
                <div class="role-info">
                  <span class="role-name">{{ role.name }}</span>
                  <span class="role-date">Created: {{ formatDate(role.createdAt) }}</span>
                </div>
              </label>
            </div>
          </div>
        </div>

        <div class="text-end">
          <button type="submit" :disabled="props.action == 'view'" class="btn btn-primary px-5 py-2 rounded-pill">
            <span v-if="props.action === 'create'">Create</span>
            <span v-else>Update</span>
          </button>
        </div>
      </form>


    </div>
  </div>
</template>
<style scoped>
.custom-input {
  border-radius: 8px;
  border: 1px solid #dee2e6;
  padding: 0.75rem 1rem;
  transition: all 0.3s ease;
}

.custom-input:focus {
  border-color: #0d6efd;
  box-shadow: 0 0 0 0.2rem rgba(13, 110, 253, 0.25);
}

.account-form {
  max-width: 1200px;
  margin: 2rem auto;
  border-radius: 15px;
}

.form-label {
  color: #495057;
  margin-bottom: 0.5rem;
}

.upload-container {
  border: 2px dashed #dee2e6;
  border-radius: 8px;
  padding: 1rem;
}

.btn-primary {
  font-weight: 600;
  text-transform: uppercase;
  letter-spacing: 0.5px;
  transition: all 0.3s ease;
}

.btn-primary:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(13, 110, 253, 0.15);
}

.role-list {
  background: #f8f9fa !important;
}

.form-check-input {
  cursor: pointer;
}

.role-list {
  display: flex;
  flex-direction: column;
  gap: 10px;
  padding: 10px;
}

.role-item {
  display: flex;
  align-items: center;
  gap: 10px;
  border: 1px solid #ccc;
  padding: 8px 12px;
  border-radius: 8px;
  transition: background 0.2s ease;
  cursor: pointer;
}

.role-item:hover {
  background-color: #f9f9f9;
}

.role-info {
  display: flex;
  flex-direction: column;
}

.role-name {
  font-weight: bold;
  color: #333;
}

.role-date {
  font-size: 0.85rem;
  color: #777;
}
</style>


<script setup>
import { reactive, ref, onMounted, watch } from 'vue'
import { formatDate } from '../../Module/CommonsFunctions.js'
import Dashboard from '../../Module/DashBoard.vue'
import createCrudService from '../../../Configs/reusableCRUDService.js'
import { useRouter } from 'vue-router'
import ImageUpload from '../../Module/ImageUpload.vue'
const router = useRouter()
import axios from 'axios'
import 'bootstrap/dist/js/bootstrap.bundle.min.js'
import 'bootstrap/dist/css/bootstrap.min.css'
import { dropDown } from '../../../Configs/DropDownList.js'
import api from '../../../Configs/api.js'
const props = defineProps({
  TableName: {
    type: String,
    required: true
  },
  action: {
    type: String,
    required: true
  },
  id: {
    type: String,
    required: true
  }
})
const formTableService = createCrudService(props.TableName);
const dropDownListRoles = ref([])
const resultUpload = ref([]);
function handleGetUploadUrl(results) {
  resultUpload.value = results;
  formData.avatarUrl = resultUpload.value[0]?.url
}
const formData = reactive({
  id: '',
  email: '',
  password: '',
  fullname: '',
  avatarUrl: '',
  phone: '',
  averageOrderValue: '',
  userRank: '',
  totalSpent: '',
  totalOrder: '',
  loyaltyPoint: '',
  createdAt: '',
  updatedAt: '',
})
const selectedRoles = ref([])
async function submitUpdateForm() {
  console.log('Debug update: ', formData)
  try {
    const payload = {
      ...formData// Add this if your backend needs role IDs
    };
    const response = await api.put(`/admin/${props.TableName}/` + props.id, payload)
    console.log('Form updated successfully:', response.data)
    await updateRoles(formData.id)
    router.push(`/Admin/${props.TableName}`)
  } catch (error) {
    console.error('Insert failed:', error)
  }
}
async function submitForm() {
  console.log('Debug submit: ', formData)
  console.log(formData)
  try {
    const response = await formTableService.create({ ...formData })
    console.log('Form submitted successfully:', response.data)
    await updateRoles(response.data.id) // Pass the created ID if needed
    router.push(`/Admin/${props.TableName}`)
  } catch (error) {
    console.error('Insert failed:', error)
  }
}
const fetchData = async () => {
  if (!props.TableName) return
  try {
    dropDownListRoles.value = (await dropDown('admin/Roles')).content
    const responseAuthorities = await api.get(`/admin/Authorities/` + props.id)
    selectedRoles.value = responseAuthorities.data.map(role => role.roles.id)
    const response = await formTableService.getById(props.id)
    console.log('Response data:', response.data)
    Object.assign(formData, response.data)
  } catch (err) {
    console.error('Get failed:', err)
  }
}
async function updateRoles(id) {
  try {
    console.log('Roles need successfully:', {
      accounts: formData.email,
      listRoles: selectedRoles.value,
      roles: '',
      updatedAt: '',
      createdAt: '',
      id: '',
    })

    const response = await api.put(`/admin/Authorities/${id}`, {
      accounts: formData.email,
      listRoles: selectedRoles.value,
      roles: '',
      updatedAt: '',
      createdAt: '',
      id: '',
    })
    console.log('Roles updated successfully:', response.data)
  } catch (error) {
    console.error('Failed to update roles:', error)
  }
}
onMounted(fetchData)
watch(() => props.id, fetchData)
watch(() => selectedRoles.value, (newValue) => {
  console.log('Selected roles updated:', newValue)
  // Here you can handle the selected roles if needed
})
</script>
