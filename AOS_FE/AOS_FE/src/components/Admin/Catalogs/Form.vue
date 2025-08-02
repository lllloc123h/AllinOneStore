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
        <label :for="name" class="form-label text-capitalize fw-bold">Name</label>
        <input :id="name" v-model="formData.name" type="text" class="form-control" :placeholder="`Enter name`"
        :readonly="props.action === 'view'" />
      </div>
      <div class="d-flex justify-content-end">
        <button type="submit" :disabled="props.action == 'view'" class="btn btn-primary px-4 py-2 aos-btn">
        <span v-if="props.action === 'create'">Create</span>
        <span v-else>Update</span>
        </button>
      </div>
      </form>
    </div>
    </div>
  </template>

  <style scoped>
  .aos-form {
    max-width: 800px;
    margin: 2rem auto;
    border: 1px solid #e0e0e0;
    transition: all 0.3s ease;
  }

  .aos-form:hover {
    box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
  }

  .form-control {
    padding: 0.8rem;
    border: 1px solid #ddd;
    border-radius: 6px;
    transition: border-color 0.3s ease;
  }

  .form-control:focus {
    border-color: #0d6efd;
    box-shadow: 0 0 0 0.2rem rgba(13, 110, 253, 0.25);
  }

  .form-label {
    color: #333;
    margin-bottom: 0.5rem;
  }

  .aos-btn {
    transition: all 0.3s ease;
    border-radius: 5px;
    font-weight: 500;
  }

  .aos-btn:hover:not(:disabled) {
    transform: translateY(-2px);
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  }

  .aos-btn:disabled {
    opacity: 0.6;
    cursor: not-allowed;
  }
  </style>

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
  name: '',
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
