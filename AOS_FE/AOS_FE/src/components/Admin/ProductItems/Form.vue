<template>
  <div class="container-fluid row">
    <div class="aside col-3">
      <Dashboard :listDashBoard="listDashBoard"></Dashboard>
    </div>
    <div class="article col-9">
      <h1>ProductItems</h1>
      <div class="dropdown mb-3">
        Select product
        <button class="btn btn-outline-secondary dropdown-toggle w-100" type="button" data-bs-toggle="dropdown">
          <span v-if="selectedProduct">
            <img :src="previewMainImg" alt="" width="30" height="30" class="me-2" />
            {{ selectedProduct.name }} ({{ selectedProduct.material }})
          </span>
          <span v-else>Select a product</span>
        </button>
        <ul class="dropdown-menu">
          <li v-for="product in dropDownListBaseProduct" :key="product.id" @click="selectBaseProduct(product)"
            class="dropdown-item d-flex align-items-center" style="cursor: pointer;">
            <img :src="product.mainImagePreviewImg" alt="" width="40" height="40" class="me-2" />
            <div>
              <strong>{{ product.name }}</strong><br />
              <small class="text-muted">{{ product.material }}</small>
            </div>
          </li>
        </ul>
      </div>
      <h2>Base infomation </h2>
      <div v-if="selectedProduct" class="card mt-3 shadow-sm p-3 rounded-4">
        <h5 class="mb-3">Base Product Information</h5>
        <div class="row">
          <div class="col-md-6 mb-2">
            <strong>Name:</strong> {{ selectedProduct.name }}
          </div>
          <div class="col-md-6 mb-2">
            <strong>Material:</strong> {{ selectedProduct.material }}
          </div>
          <div class="col-md-6 mb-2">
            <strong>Quantity:</strong> {{ selectedProduct.qty }}
          </div>
          <div class="col-md-6 mb-2">
            <strong>Category ID:</strong> {{categoriesDropDownList.find(c => c.id === selectedProduct.categoryId)?.name
              || 'Unknown'}}
          </div>

        </div>
      </div>

      <!-- <form @submit.prevent="handleSubmit"> -->
      <div class="mb-3" :style="(props.action === 'view' || props.action === 'create') ? ' display:none;' : ''">
        <label for="id" class="form-label text-capitalize"></label>
        <input id="id" v-model="formData.id" v-if="props.action !== 'create'" :hidden="props.action === 'view'"
          type="number" class="form-control" placeholder="`Enter id`" />
      </div>
      <div class="dropdown-grid">

        <div class="main-section rounded-4">
          <button class="first-button" @click="openModal(selectedProduct)">Thêm Biến thể</button>
        </div>

      </div>
      <div v-if="showModal" class="modal fade show d-block" tabindex="-1" style="background-color: rgba(0,0,0,0.5)">
        <div class="modal-dialog modal-lg">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title">{{ selectedProduct?.name }}</h5>
              <button type="button" class="btn-close" @click="closeModal"></button>
            </div>
            <div class="modal-body">
              <div class="mb-3">
                <label for="mainImageUrl" class="form-label text-capitalize">mainImageUrl</label>
                <ImageUpload :max-images="1" :max-videos="1" folder="products" :heightImg="0" :widthImg="0"
                  :videoDuration="60" ref="imageUploadRef" @result-uploaded="handleGetUploadUrl" />

                <!-- <ImageUpload @update-main-image-url="onAvatarUpdate" /> -->
              </div>
              <div v-if="formData.mainImageUrl" class="mb-3">
                <label class="form-label">Preview Avatar:</label>
                <img :src="previewImg" alt="mainImageUrl Preview" class="img-thumbnail" style="max-height: 150px;" />
              </div>
              <div class="dropdown">
                <button class="btn btn-outline-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown">
                  <span v-if="selectedVarriantColor">
                    {{ selectedVarriantColor.name }} ({{ selectedVarriantColor.description }})
                  </span>
                  <span v-else>Select a color</span>
                </button>
                <ul class="dropdown-menu" style="width: 100%; max-height: 300px; overflow-y: auto;">
                  <li v-for="color in dropDownListVariants['Màu sắc']" :key="color.id" @click="selectColor(color)"
                    class="dropdown-item d-flex align-items-center" style="cursor: pointer;">
                    <div>
                      <strong>{{ color.description }}</strong><br />
                      <small class="text-muted">{{ color.name }}</small>
                    </div>
                  </li>
                </ul>
              </div>
              <div class="dropdown">
                <button class="btn btn-outline-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown">
                  <span v-if="selectedVarriantSize">
                    {{ selectedVarriantSize.name }} ({{ selectedVarriantSize.description }})
                  </span>
                  <span v-else>Select a size</span>
                </button>
                <ul class="dropdown-menu" style="width: 100%; max-height: 300px; overflow-y: auto;">
                  <li v-for="size in dropDownListVariants['Kích thước']" :key="size.id" @click="selectSize(size)"
                    class="dropdown-item d-flex align-items-center" style="cursor: pointer;">
                    <div>
                      <strong>{{ size.description }}</strong><br />
                      <small class="text-muted">{{ size.name }}</small>
                    </div>
                  </li>
                </ul>
              </div>
              <p><strong>Biến thể:</strong> {{ selectedProduct?.sku || '---' }} </p>
              <div class="mb-3">
                <label for="description" class="form-label text-capitalize">description</label>
                <input id="description" v-model="selectedProduct.description" type="text" class="form-control"
                  placeholder="`Enter description`" />
              </div>
              <div class="mb-3">
                <label for="safetyStock" class="form-label text-capitalize">safetyStock</label>
                <input id="safetyStock" v-model="selectedProduct.safetyStock" type="number" class="form-control"
                  placeholder="`Enter safetyStock`" />
              </div>
              <div class="mb-3">
                <label for="cost" class="form-label text-capitalize">cost</label>
                <input id="cost" v-model="selectedProduct.cost" type="number" class="form-control"
                  placeholder="`Enter cost`" />
              </div>
              <div class="mb-3">
                <label for="price" class="form-label text-capitalize">price</label>
                <input id="price" v-model="formData.price" type="number" class="form-control"
                  placeholder="`Enter price`" />
              </div>
              <div class="mb-3">
                <label for="qty" class="form-label text-capitalize">qty</label>
                <input id="qty" v-model="selectedProduct.qty" type="number" class="form-control"
                  placeholder="`Enter qty`" />
              </div>
              <div class="mb-3">
                <label for="sellStart" class="form-label text-capitalize">sellStart</label>
                <input id="sellStart" v-model="selectedProduct.sellStart" type="datetime-local" class="form-control"
                  placeholder="`Enter sellStart`" />
              </div>
              <div class="mb-3">
                <label for="sellEnd" class="form-label text-capitalize">sellEnd</label>
                <input id="sellEnd" v-model="selectedProduct.sellEnd" type="datetime-local" class="form-control"
                  placeholder="`Enter sellEnd`" />
              </div>
            </div>
            <div class="modal-footer">
              <button class="btn btn-secondary" @click="closeModal">Đóng</button>
              <button class="btn btn-primary" @click="addToCart">Thêm biến thể</button>
            </div>
          </div>
        </div>
      </div>
      <!-- Preview of Added Variants -->
      <div class="variant-preview mt-4">
        <h5 class="mb-3">Danh sách biến thể đã thêm:</h5>
        <div v-if="list.length === 0" class="text-muted">Chưa có biến thể nào được thêm.</div>
        <ul class="list-group">
          <li class="list-group-item d-flex justify-content-between align-items-center" v-for="(item, index) in list"
            :key="index">
            <div class="d-flex align-items-center">
              <img :src="item.imgPreview || previewMainImg" alt="Preview" class="me-3 rounded"
                style="width: 50px; height: 50px; object-fit: cover;" />
              <div>NAME:
                <strong>{{ item.name }}</strong><br />
                <small>
                  SKU: {{ item.sku }} |
                  Giá: {{ item.price }} |
                  SL: {{ item.qty }}
                </small><br />
                <small class="text-muted">Ảnh: {{ item.fileNameImgOfVariant || 'Không có' }}</small>
              </div>
            </div>
            <button class="btn btn-sm btn-outline-danger" @click="removeVariant(item.id)">Xóa</button>
          </li>
        </ul>
      </div>

      <!-- <button @click="handleSubmit" :disabled="props.action === 'view'" class="btn btn-primary">
        <span v-if="props.action === 'create'">Create</span>
        <span v-else-if="props.action === 'create'">Create</span>
        <span v-else>Update</span>
      </button> -->
      <!-- </form> -->
    </div>
  </div>
</template>
<script setup>
import { reactive, ref, onMounted, watch } from 'vue'
import { formatDate } from '../../Module/CommonsFunctions.js'
import Dashboard from '../../Module/DashBoard.vue'
import createCrudService from '../../../Configs/reusableCRUDService.js'
import { useRouter } from 'vue-router'
import axios from 'axios'
import 'bootstrap/dist/js/bootstrap.bundle.min.js'
import 'bootstrap/dist/css/bootstrap.min.css'
import { dropDown, dropDownVariant } from '../../../Configs/DropDownList.js'
import ImageUpload from '../../Module/ImageUpload.vue'
import { storage } from "../../../Configs/firebase.js";
import { ref as storageRef, uploadBytes, getDownloadURL } from "firebase/storage";
import api from '../../../Configs/api.js'
import { notification } from "ant-design-vue";
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
const router = useRouter()
const formTableService = createCrudService(props.TableName);
const mapSku = ref()
const dropDownListVariants = ref([])
const dropDownListBaseProduct = ref(new Map())
const imageUploadRef = ref([])

const selectedProduct = ref({
  id: '',
  baseId: '',
  cost: '',
  price: '',
  turnBuy: 0,
  description: '',
  sku: '',
  safetyStock: '',
  qty: '',
  sellStart: '',
  sellEnd: '',
  createdAt: '',
  updatedAt: '',
  imgPreview: '',
  fileNameImgOfVariant: '',
})
const selectedPrice = ref(null)
const selectedVarriantColor = ref("")
const selectedVarriantSize = ref("")
const list = ref([])
const previewImg = ref();
const showModal = ref(false);
const previewMainImg = ref("");
const categoriesDropDownList = ref([]);
const categoriesService = createCrudService("Categories");
const formData = reactive({
  id: '',
  baseId: '',
  cost: '',
  price: '',
  turnBuy: 0,
  description: '',
  sku: '',
  safetyStock: '',
  qty: '',
  sellStart: '',
  sellEnd: '',
  createdAt: '',
  updatedAt: '',
})
const itemToAddList = reactive({
  id: '',
  baseId: '',
  cost: '',
  price: '',
  turnBuy: 0,
  description: '',
  imageUrl: '',
  sku: '',
  safetyStock: '',
  qty: '',
  sellStart: '',
  sellEnd: '',
  createdAt: '',
  updatedAt: '',
})

const fetchData = async () => {
  if (!props.TableName) return
  try {
    if (!props.action || props.action === 'view' || props.action === 'update') {
      const response = await formTableService.getById(props.id)
      response.data.createdAt = formatDate(response.data.createdAt)
      response.data.updatedAt = formatDate(response.data.updatedAt)
      Object.assign(formData, response.data)
    }
  } catch (err) {
    console.error('Get failed:', err)
  }
}

async function getProductItems(id) {
  if (!props.TableName) return
  try {
    const response = await api.get('/admin/ProductItems/ByBaseProductId/' + id)
    if (response.data.content && response.data.content.length > 0) {
      list.value = response.data.content.map(item => {
        return {
          ...item,
          name: item.baseProducts.name,
          imgPreview: item.imageUrl || previewMainImg.value || '',
          fileNameImgOfVariant: item.fileNameImgOfVariant || ''
        }
      })
    } else {
      list.value = []
    }

  } catch (error) {
    console.error('Get failed:', error)
  }
}
function handleSubmit() {
  if (props.action === 'create') {
    submitForm()
  } else {
    submitUpdateForm()
  }
}
const resultUpload = ref([])
function handleGetUploadUrl(results) {
  resultUpload.value = results;
}
async function submitUpdateForm() {
  console.log(formData)
  try {
    const response = await formTableService.update(props.id, formData)
    router.push(`/Admin/${props.TableName}`)
  } catch (error) {
    console.error('Insert failed:', error)
  }
}
async function removeVariant(index) {
  try {
    const response = await api.delete('/admin/ProductItems/' + index)
  } catch (error) {
    console.error('Remove variant failed:', error)
    notification.error({
      message: 'Error',
      description: `Lỗi khi xóa biến thể: ${error.message}`,
    });
  }
  getProductItems(selectedProduct.value.baseId);
  notification.success({
    message: 'Success',
    description: `Biến thể đã được xóa thành công.`,
  });
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
const openModal = (product) => {
  if (selectedProduct.value == null) return
  itemToAddList.value = { ...product };
  selectedProduct.value = { ...product };
  showModal.value = true;
  itemToAddList.value.baseId = product.id;
  console.log('Selected Product:', product)
  selectedProduct.value.baseId = product.id;
  selectedProduct.value.id = '';
  selectedProduct.value.price = 0;
  selectedProduct.value.cost = 0;
};
const closeModal = () => {
  showModal.value = false;
};
function splitSku(itemCheck) {
  return itemCheck.split('-')[itemCheck.split('-').length - 2] + "-" + itemCheck.split('-')[itemCheck.split('-').length - 1]
}

async function addToCart() {
  const isContain = list.value.filter(item => splitSku(item.sku) === selectedProduct.value.sku)
  if (isContain.length > 0) {
    closeModal()
    notification.error({
      message: 'Error',
      description: `Biến thể đã tồn tại trong danh sách. Vui lòng chọn biến thể khác.`,
    });
    return;
  }
  const resultImgUpload = resultUpload.value;


  try {
    const responseSaveProductItem = await api.post('/admin/ProductItems', selectedProduct.value);
    const responseSaveImg = await api.post('/admin/ProductImages', { id: '', productItems: responseSaveProductItem.data.id, imageUrl: resultImgUpload[0].url });
  } catch (error) {
    console.error('Insert failed:', error)
    notification.error({
      message: 'Error',
      description: `Lỗi khi thêm biến thể: ${error.message}`,
    });
    return;
  }
  getProductItems(selectedProduct.value.baseId);

  closeModal();
};
async function selectBaseProduct(product) {
  selectedProduct.value = product
  selectedProduct.value.baseId = product.id
  itemToAddList.value = { ...product };
  itemToAddList.baseId = product.id;
  // previewMainImg.value = await handleImg(product.mainImage)
}
function selectColor(color) {
  selectedVarriantColor.value = color
}
function selectSize(size) {
  selectedVarriantSize.value = size
}
function selectPrice(price) {
  selectedPrice.value = price
  formData.price = price
}
onMounted(async () => {
  await fetchData();
  dropDownListVariants.value = await dropDownVariant()
  dropDownListBaseProduct.value = await dropDown("BaseProducts")
  const responseCategories = await categoriesService.getAll(0, 1000)
  categoriesDropDownList.value = responseCategories.data.map(category => {
    return {
      id: category.id,
      name: category.name
    }
  })
})
watch(() => props.id, fetchData)
watch([() => selectedVarriantColor.value, () => selectedVarriantSize.value], () => {
  mapSku.value = (selectedVarriantColor.value?.signalSku) + "-" + selectedVarriantSize.value?.signalSku
})
watch(() => mapSku.value, () => {
  selectedProduct.value.sku = mapSku.value
})

watch(() => selectedProduct.value, (newValue) => {
  getProductItems(newValue.baseId);
});

watch(() => dropDownListBaseProduct.value, async () => {
  for (const product of dropDownListBaseProduct.value) {
    // product.mainImagePreviewImg = await handleImg(product.mainImage)
  }
})
</script>
<style scoped>
.form-control {
  border-radius: 0.75rem;
  border: 1px solid #dee2e6;
  transition: border-color 0.2s, box-shadow 0.2s;
}

.form-control:focus {
  border-color: #86b7fe;
  box-shadow: 0 0 0 0.25rem rgba(13, 110, 253, 0.25);
}

.dropdown {
  margin-bottom: 1rem;
}

.first-button {
  background-color: #0d6efd;
  color: white;
  border: none;
  padding: 0.75rem 1.5rem;
  font-size: 1rem;
  border-radius: 12px;
  transition: all 0.3s ease;
  cursor: pointer;
}

.first-button:hover {
  background-color: #0b5ed7;
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
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

.variant-preview ul {
  overflow-y: auto;
}

.variant-preview img {
  border: 1px solid #ccc;
}
</style>