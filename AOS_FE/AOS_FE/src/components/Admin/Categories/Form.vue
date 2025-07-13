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
        <div class="dropdown mb-3">
          Select Catalog
          <button class="btn btn-outline-secondary dropdown-toggle w-100" type="button" data-bs-toggle="dropdown">
            <span v-if="selectedCatalog">
              {{ selectedCatalog.name }}
            </span>
            <span v-else>Select a catalog</span>
          </button>
          <ul class="dropdown-menu">
            <li v-for="catalog in dropDownListCatalog" :key="catalog.id" @click="selectCatalog(catalog)"
              class="dropdown-item d-flex align-items-center" style="cursor: pointer;">
              <div>
                <strong>{{ catalog.name }}</strong><br />
              </div>
            </li>
          </ul>
        </div>
        <div class="mb-3">
          <label for="name" class="form-label text-capitalize">name</label>
          <input id="name" v-model="formData.name" type="text" class="form-control" :placeholder="`Enter name`" />
        </div>


        <button type="submit" :disabled="props.action == 'view'" class="btn btn-primary">
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
    console.log('Update successful:', response.data)
    router.push(`/Admin/${props.TableName}`)
  } catch (error) {
    console.error('Update failed:', error)
  }
}

async function submitForm() {
  console.log(formData)
  try {
    const response = await formTableService.create(formData)
    console.log('Insert successful:', response.data)
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
  console.log('Catalogs:', dropDownListCatalog.value)
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