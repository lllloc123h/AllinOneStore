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
        <div class="mb-3" v-show="props.action != 'view'">
          <label for="categories" class="form-label text-capitalize fw-semibold">Variant</label>
          <select id="categories" v-model="formData.name" class="form-select">
            <option disabled value="">Select type</option>
            <option v-for="item in variantDropDownList" :key="item.id" :value="item.name">
              {{ item.name }}
            </option>
          </select>
        </div>
        <div class="mb-3">
          <label for="signalSku" class="form-label text-capitalize">signalSku</label>
          <input id="signalSku" v-model="formData.signalSku" type="text" class="form-control"
            placeholder="`Enter signalSku`" />
        </div>

        <div class="mb-3">
          <label for="description" class="form-label text-capitalize">description</label>
          <input id="description" v-model="formData.description" type="text" class="form-control"
            placeholder="`Enter description`" />
        </div>


        <button type="submit" :disable="props.action == 'view'" class="btn btn-primary">
          <span v-if="props.action === 'create'">Create</span>
          <span v-else>Update</span>
        </button>
      </form>
    </div>
  </div>
</template>
<style></style>

<script setup>
import { reactive, ref, onMounted, watch } from 'vue'
import { formatDate, formatDateTimeLocal } from '../../Module/CommonsFunctions.js'
import Dashboard from '../../Module/DashBoard.vue'
import createCrudService from '../../../Configs/reusableCRUDService.js'
import { useRouter } from 'vue-router'
const router = useRouter()
import axios from 'axios'
import 'bootstrap/dist/js/bootstrap.bundle.min.js'
import 'bootstrap/dist/css/bootstrap.min.css'
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

const variantDropDownList = ref([]);
const formData = reactive({
  id: '',
  name: '',
  signalSku: '',
  description: '',
  createdAt: '',
  updatedAt: '',
})

async function submitUpdateForm() {
  console.log(formData)
  try {
    const response = await api.put(`/admin/VariantValues/${props.id}`, formData)
    console.log('Insert successful:', response.data)
    router.push(`/Admin/${props.TableName}`)
  } catch (error) {
    console.error('Insert failed:', error)
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
const fetchData = async () => {
  if (!props.TableName) return
  try {
    if (!props.action || props.action === "view" || props.action === "update") {
      const response = await formTableService.getById(props.id)
      response.data.createdAt = formatDateTimeLocal(response.data.createdAt)
      response.data.updatedAt = formatDateTimeLocal(response.data.updatedAt)
      Object.assign(formData, response.data)
    }
    const responseVariant = await api.get('/admin/Variants')
    console.log('Variants:', responseVariant.data.content)
    variantDropDownList.value = responseVariant.data.content.map((Variant) => {
      return {
        id: Variant.id,
        name: Variant.name,
      };
    });

  } catch (err) {
    console.error('Get failed:', err)
  }
}

onMounted(fetchData)
watch(() => props.id, fetchData)
watch(() => formData,
  console.log(formData))
</script>
