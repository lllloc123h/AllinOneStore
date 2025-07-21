<template>
  <div class="container-fluid row">
    <div class="aside col-3">
      <Dashboard :listDashBoard="listDashBoard"></Dashboard>
    </div>
    <div class="article col-9">
      <form @submit.prevent="props.action === 'create' ? submitForm() : submitUpdateForm()"
        class="p-4 rounded shadow-sm bg-white aos-form">
        <div class="mb-3" :style="(props.action === 'view' || props.action === 'create') ? 'display:none;' : ''">
          <label :for="id" class="form-label text-capitalize fw-bold"></label>
          <input :id="id" v-model="formData.id" v-if="props.action !== 'create'" :hidden="props.action === 'view'"
            type="number" class="form-control" :placeholder="`Enter id`" />
        </div>
        <div class="mb-3">
          <label :for="reason" class="form-label text-capitalize fw-bold">Reason</label>
          <input :id="reason" v-model="formData.reason" type="text" class="form-control" :placeholder="`Enter reason`"
            :readonly="props.action === 'view'" />
        </div>
        <div class="mb-3">
          <label :for="isPaid" class="form-label text-capitalize fw-bold">Is Paid</label>
          <input :id="isPaid" v-model="formData.isPaid" type="text" class="form-control" :placeholder="`Enter isPaid`"
            :readonly="props.action === 'view'" />
        </div>
        <div class="mb-3">
          <label :for="status" class="form-label text-capitalize fw-bold">Status</label>
          <input :id="status" v-model="formData.status" type="text" class="form-control" :placeholder="`Enter status`"
            :readonly="props.action === 'view'" />
        </div>
        <div class="d-flex justify-content-end">
          <button type="submit" :disabled="props.action == 'view'" class="btn btn-primary px-4 py-2">
            <span v-if="props.action === 'create'">Create</span>
            <span v-else>Update</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<style></style>

<script setup>
import { reactive, ref, onMounted, watch } from 'vue'
import { formatDate } from '../../Module/CommonsFunctions.js'
import Dashboard from '../../Module/DashBoard.vue'
import createCrudService from '../../../Configs/reusableCRUDService.js'
import { useRouter } from 'vue-router'
const router = useRouter()
import axios from 'axios'
import 'bootstrap/dist/js/bootstrap.bundle.min.js'
import 'bootstrap/dist/css/bootstrap.min.css'
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

const formData = reactive({
  id: '',
  reason: '',
  isPaid: '',
  status: '',
  createdAt: '',
  updatedAt: '',
})

async function submitUpdateForm() {
  console.log(formData)
  try {
    const response = await formTableService.update(props.id, formData)
    router.push(`/Admin/${props.TableName}`)
  } catch (error) {
    console.error('Insert failed:', error)
  }
}

async function submitForm() {
  console.log(formData)
  try {
    const response = await formTableService.create(formData)
    router.push(`/Admin/${props.TableName}`)
  } catch (error) {
    console.error('Insert failed:', error)
  }
}
const fetchData = async () => {
  if (!props.TableName) return
  try {
    const response = await formTableService.getById(props.id)
    response.data.createdAt = formatDate(response.data.createdAt)
    response.data.updatedAt = formatDate(response.data.updatedAt)
    Object.assign(formData, response.data)
  } catch (err) {
    console.error('Get failed:', err)
  }
}

onMounted(fetchData)
watch(() => props.id, fetchData)
</script>
