<template>
  <div class="container-fluid row">
    <div class="aside col-3">
      <Dashboard></Dashboard>
    </div>
    <div class="article col-9 p-4">
      <form @submit.prevent="props.action === 'create' ? submitForm() : submitUpdateForm()" class="form-container">
        <div class="mb-4" :style="(props.action === 'view' || props.action === 'create') ? ' display:none;' : ''">
          <label for="id" class="form-label text-capitalize fw-bold"></label>
          <input id="id" v-model="formData.id" v-if="props.action !== 'create'" :hidden="props.action === 'view'"
            type="number" class="form-control shadow-sm" placeholder="`Enter id`" />
        </div>
        <div class="dropdown mb-4">
          <label class="form-label fw-bold mb-2">Select Catalog</label>
          <button class="btn btn-outline-secondary dropdown-toggle w-100 shadow-sm" type="button"
            data-bs-toggle="dropdown">
            <span v-if="selectedCatalog" class="text-dark">
              {{ selectedCatalog.name }}
            </span>
            <span v-else class="text-muted">Select a catalog</span>
          </button>
          <ul class="dropdown-menu shadow">
            <li v-for="catalog in dropDownListCatalog" :key="catalog.id" @click="selectCatalog(catalog)"
              class="dropdown-item d-flex align-items-center hover-highlight" style="cursor: pointer;">
              <div>
                <strong>{{ catalog.name }}</strong>
              </div>
            </li>
          </ul>
        </div>
        <div class="mb-4">
          <label for="name" class="form-label text-capitalize fw-bold">Name</label>
          <input id="name" v-model="formData.name" type="text" class="form-control shadow-sm"
            placeholder="Enter name" />
        </div>

        <button type="submit" :disabled="props.action == 'view'"
          class="btn btn-primary px-4 py-2 shadow-sm hover-scale">
          <span v-if="props.action === 'create'">Create</span>
          <span v-else>Update</span>
        </button>
      </form>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref, onMounted, watch } from 'vue'
import { formatDate } from '../../Module/CommonsFunctions.js'
import Dashboard from '../../Module/DashBoard.vue'
import createCrudService from '../../../Configs/reusableCRUDService.js'
import { dropDown } from '../../../Configs/DropDownList.js'
import { useRouter } from 'vue-router'
import axios from 'axios'
import 'bootstrap/dist/js/bootstrap.bundle.min.js'
import 'bootstrap/dist/css/bootstrap.min.css'

const router = useRouter()

const props = defineProps({
  TableName: String,
  action: String,
  id: String
})

const formTableService = createCrudService(props.TableName)

const dropDownListCatalog = ref([])
const formData = reactive({
  id: '',
  catalogs: '',
  name: '',
  createdAt: '',
  updatedAt: '',
})

const selectedCatalog = ref({})

async function submitUpdateForm() {
  console.log(formData)
  try {
    const response = await formTableService.update(props.id, formData)
    router.push(`/Admin/${props.TableName}`)
  } catch (error) {
    console.error('Update failed:', error)
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

async function selectCatalog(catalog) {
  selectedCatalog.value = catalog
  formData.catalogs = catalog.name
}

async function fetchData() {
  if (!props.TableName || props.action === 'create') return
  try {
    const response = await formTableService.getById(props.id)
    response.data.createdAt = formatDate(response.data.createdAt)
    response.data.updatedAt = formatDate(response.data.updatedAt)
    Object.assign(formData, response.data)
  } catch (err) {
    console.error('Get failed:', err)
  }
}

onMounted(async () => {
  await fetchData()
  dropDownListCatalog.value = (await dropDown('admin/Catalogs')).content
})

watch(() => props.id, fetchData)
</script>

<style scoped>
.dropdown {
  margin-bottom: 1rem;
}

.dropdown-toggle {
  width: 100%;
  text-align: left;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.dropdown-toggle span {
  display: flex;
  align-items: center;
}

.dropdown-menu {
  width: 100%;
  max-height: 300px;
  overflow-y: auto;
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.dropdown-item {
  padding: 0.5rem 1rem;
  transition: background-color 0.2s ease-in-out;
}

.dropdown-item:hover {
  background-color: #f0f0f0;
}

.dropdown-item img {
  border-radius: 6px;
  object-fit: cover;
}
</style>