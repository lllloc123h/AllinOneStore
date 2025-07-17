<template>
  <div class="container-fluid row">
    <div class="aside col-3">
      <Dashboard></Dashboard>
    </div>
    <div class="article col-9">
      <form @submit.prevent="props.action === 'create' ? submitForm() : submitUpdateForm()">
        <div class="mb-3" :style="(props.action === 'view' || props.action === 'create') ? ' display:none;' : ''">
          <label for="id" class="form-label text-capitalize"></label>
          <input id="id" v-model="formData.id" v-if="props.action !== 'create'" :hidden="props.action === 'view'"
            type="number" class="form-control" placeholder="`Enter id`" />
        </div>
        <div class="mb-3">
          <label for="email" class="form-label text-capitalize">email</label>
          <input id="email" v-model="formData.email" type="email" class="form-control" placeholder="`Enter email`" />
        </div>

        <div class="mb-3">
          <label for="password" class="form-label text-capitalize">password</label>
          <input id="password" v-model="formData.password" type="password" class="form-control"
            placeholder="`Enter password`" />
        </div>

        <div class="mb-3">
          <label for="fullname" class="form-label text-capitalize">fullname</label>
          <input id="fullname" v-model="formData.fullname" type="text" class="form-control"
            placeholder="`Enter fullname`" />
        </div>

        <!-- <div class="mb-3">
          <label for="avatar" class="form-label text-capitalize">avatar</label>
          <input id="avatar" v-model="formData.avatar" type="text" class="form-control"
            placeholder="`Enter avatar`" />
        </div> -->
        <ImageUpload @update-avatar="formData.avatarUrl = $event" />
        <div v-if="formData.avatar" class="mb-3">
          <label class="form-label">Preview Avatar:</label>
          <img :src="formData.avatar" alt="Avatar Preview" class="img-thumbnail" style="max-height: 150px;" />
        </div>

        <div class="row mb-3">
          <div class="mb-3 col-4">
            <label for="phone" class="form-label text-capitalize">phone</label>
            <input id="phone" v-model="formData.phone" type="text" class="form-control" placeholder="`Enter phone`" />
          </div>

          <div class="mb-3 col-4">
            <label for="averageOrderValue" class="form-label text-capitalize">averageOrderValue</label>
            <input id="averageOrderValue" v-model="formData.averageOrderValue" type="number" class="form-control"
              placeholder="`Enter averageOrderValue`" />
          </div>
          <div class="mb-3 col-4">
            <label for="userRank" class="form-label text-capitalize">userRank</label>
            <input id="userRank" v-model="formData.userRank" type="text" class="form-control"
              placeholder="`Enter userRank`" />
          </div>
        </div>
        <div class="row mb-3">
          <div class="mb-3 col-4">
            <label for="totalSpent" class="form-label text-capitalize">totalSpent</label>
            <input id="totalSpent" v-model="formData.totalSpent" type="number" class="form-control"
              placeholder="`Enter totalSpent`" />
          </div>

          <div class="mb-3 col-4">
            <label for="totalOrder" class="form-label text-capitalize">totalOrder</label>
            <input id="totalOrder" v-model="formData.totalOrder" type="number" class="form-control"
              placeholder="`Enter totalOrder`" />
          </div>

          <div class="mb-3 col-4">
            <label for="loyaltyPoint" class="form-label text-capitalize">loyaltyPoint</label>
            <input id="loyaltyPoint" v-model="formData.loyaltyPoint" type="number" class="form-control"
              placeholder="`Enter loyaltyPoint`" />
          </div>
        </div>
        <div class="row role-list">
          <label v-for="role in dropDownListRoles" :key="role.id" class="role-item">
            <input type="checkbox" :value="role.id" v-model="selectedRoles" />
            <div class="role-info">
              <span class="role-name">{{ role.name }}</span>
              <span class="role-date">Created: {{ formatDate(role.createdAt) }}</span>
            </div>
          </label>
        </div>

        <button type="submit" :disabled="props.action == 'view'" class="btn btn-primary">
          <span v-if="props.action === 'create'">Create</span>
          <span v-else-if="props.action === 'create'">Create</span>
          <span v-else>Update</span>
        </button>
      </form>
    </div>
  </div>
</template>
<style scoped>
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
