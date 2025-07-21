<template>
  <div class="container-fluid row">
    <div class="aside col-3">
      <Dashboard></Dashboard>
    </div>
    <div class="article col-9 form-article">
      <form class="product-form shadow p-4 rounded bg-white"
        @submit.prevent="props.action === 'create' ? submitForm() : submitUpdateForm()">
        <div class="mb-3" :style="(props.action === 'view' || props.action === 'create') ? 'display:none;' : ''">
          <label for="id" class="form-label text-capitalize"></label>
          <input id="id" v-model="formData.id" v-if="props.action !== 'create'" :hidden="props.action === 'view'"
            type="number" class="form-control" placeholder="`Enter id`" readonly />
        </div>
        <div class="mb-3">
          <label for="name" class="form-label text-capitalize fw-semibold">Name</label>
          <input id="name" v-model="formData.name" type="text" class="form-control" placeholder="Enter name" />
        </div>

        <div class="mb-3">
          <label for="material" class="form-label text-capitalize fw-semibold">Material</label>
          <input id="material" v-model="formData.material" type="text" class="form-control"
            placeholder="Enter material" />
        </div>
        <div class="mb-3" v-show="props.action != 'view'">
          <label for="categories" class="form-label text-capitalize fw-semibold">Categories</label>
          <select id="categories" v-model="formData.categories" class="form-select">
            <option disabled value="">Select type</option>
            <option v-for="item in categoriesDropDownList" :key="item.id" :value="item.name">
              {{ item.name }}
            </option>
          </select>
        </div>
        <div class="mb-3">
          <label for="mainImageUrl" class="form-label text-capitalize fw-semibold">Main Image</label>
          <ImageUpload :max-images="1" :max-videos="1" folder="products" :heightImg="0" :widthImg="0"
            :videoDuration="60" ref="imageUploadRef" @result-uploaded="handleGetUploadUrl" />
        </div>

        <div v-if="formData.mainImageUrl" class="mb-3 text-center">
          <label class="form-label fw-semibold">Preview:</label>
          <div class="preview-img-wrapper d-flex justify-content-center align-items-center">
            <img :src="previewImg" alt="mainImageUrl Preview" class="img-thumbnail shadow"
              style="max-height: 150px; border-radius: 8px;" />
          </div>
        </div>
        <div class="mb-3 d-flex align-items-center gap-3">
          <div class="form-check form-check-inline">
            <input class="form-check-input" type="radio" id="isActiveTrue" :value="true" v-model="formData.active" />
            <label class="form-check-label" for="isActiveTrue">Active</label>
          </div>
          <div class="form-check form-check-inline">
            <input class="form-check-input" type="radio" id="isActiveFalse" :value="false" v-model="formData.active" />
            <label class="form-check-label" for="isActiveFalse">Inactive</label>
          </div>
        </div>

        <button type="submit" :disabled="props.action == 'view'" class="btn btn-primary w-100 py-2 fw-bold">
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
import ImageUpload from '../../Module/ImageUpload.vue'
const router = useRouter()
import axios from 'axios'
import 'bootstrap/dist/js/bootstrap.bundle.min.js'
import 'bootstrap/dist/css/bootstrap.min.css'
import { ref as storageRef, uploadBytes, getDownloadURL } from "firebase/storage";
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
const categoriesService = createCrudService("Categories");
const categoriesDropDownList = ref([]);
const previewImg = ref();
const formData = reactive({
  id: '',
  name: '',
  material: '',
  categories: '',
  mainImageUrl: '',
  isCustom: '',
  turnBuy: '',
  rating: '',
  isActive: '',
  active: '',
  createdAt: '',
  updatedAt: '',
})

const resultUpload = ref([]);
const selectedCategory = ref([]);
function handleGetUploadUrl(results) {
  resultUpload.value = results;
  formData.avatarUrl = resultUpload.value[0]?.url
  previewImg.value = resultUpload.value[0]?.url;
}

// function onAvatarUpdate(url) {
//   // console.log("Received image URL:", url);
//   formData.mainImageUrl = url.filePath;
//   const filePath = `products/${Date.now()}_${file.name}`;
//   const fileRef = storageRef(storage, filePath);
//   previewImg.value = url.downloadUrl
//   // console.log("formData.mainImageUrl = ", formData.mainImageUrl);
// }
async function submitUpdateForm() {
  try {
    formData.createdAt = formatDateTimeLocal(formData.createdAt)
    formData.updatedAt = formatDateTimeLocal(formData.updatedAt)
    console.log(formData)
    const response = await formTableService.update(props.id, formData)
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
    if (!props.action || props.action === 'view' || props.action === 'update') {
      const response = await formTableService.getById(props.id)
      console.log(response.data)
      const responseCategories = await categoriesService.getAll(0, 1000)
      // const filePath = `products/${response.data.mainImageUrl}`;
      // const fileRef = storageRef(storage, filePath);
      // previewImg.value = await getDownloadURL(fileRef);
      categoriesDropDownList.value = responseCategories.data.content.map(category => {
        return {
          id: category.id,
          name: category.name
        }
      })
      Object.assign(formData, response.data)
    }
    const responseCategories = await categoriesService.getAll(0, 1000)
    categoriesDropDownList.value = responseCategories.data.content.map(category => {
      return {
        id: category.id,
        name: category.name
      }
    })
  } catch (err) {
    console.error('Get failed:', err)
  }
}

onMounted(fetchData)
watch(() => props.id, fetchData)

</script>
