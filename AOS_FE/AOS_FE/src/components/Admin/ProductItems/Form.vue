<template>
  <div class="container-fluid row">
    <div class="aside col-3">
      <Dashboard :listDashBoard="listDashBoard"></Dashboard>
    </div>
    <div class="article col-9">
      <form @submit.prevent="props.action === 'create' ? submitForm() : submitUpdateForm()">
        <div class="mb-3" :style="(props.action === 'view' || props.action === 'create') ? ' display:none;' : ''">
          <label :for="id" class="form-label text-capitalize"></label>
          <input :id="id" v-model="formData.id" v-if="props.action !== 'create'" :hidden="props.action === 'view'"
            type="number" class="form-control" :placeholder="`Enter id`" />
        </div>
        <div class="dropdown">
          <button class="btn btn-outline-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown">
            <span v-if="selectedProduct">
              <img :src="selectedProduct.mainImage" alt="" width="30" height="30" class="me-2" />
              {{ selectedProduct.name }} ({{ selectedProduct.material }})
            </span>
            <span v-else>Select a product</span>
          </button>
          <ul class="dropdown-menu" style="width: 100%; max-height: 300px; overflow-y: auto;">
            <li v-for="product in dropDownListBaseProduct" :key="product.id" @click="selectBaseProduct(product)"
              class="dropdown-item d-flex align-items-center" style="cursor: pointer;">
              <img :src="product.mainImage" alt="" width="40" height="40" class="me-2" />
              <div>
                <strong>{{ product.name }}</strong><br />
                <small class="text-muted">{{ product.material }}</small>
              </div>
            </li>
          </ul>
        </div>

        <!-- <div class="mb-3">
          <label :for="cost" class="form-label text-capitalize">cost</label>
          <input :id="cost" v-model="formData.cost" type="number" class="form-control" :placeholder="`Enter cost`" />
        </div> -->
        <div class="dropdown">
          <button class="btn btn-outline-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown">
            <span v-if="selectedPrice">
              {{ selectedPrice }}
            </span>
            <span v-else>Select a Price</span>
          </button>
          <ul class="dropdown-menu" style="width: 100%; max-height: 300px; overflow-y: auto;">
            <li v-for="(price, index) in autoCompleteListPrice" :key="index" @click="selectPrice(price)"
              class="dropdown-item d-flex align-items-center" style="cursor: pointer;">
              <div>
                <strong>{{ price }}</strong><br />
              </div>
            </li>
          </ul>
        </div>

        <div class="mb-3">
          <label :for="turnBuy" class="form-label text-capitalize">turnBuy</label>
          <input :id="turnBuy" v-model="formData.turnBuy" type="number" class="form-control"
            :placeholder="`Enter turnBuy`" />
        </div>

        <div class="mb-3">
          <label :for="description" class="form-label text-capitalize">description</label>
          <input :id="description" v-model="formData.description" type="text" class="form-control"
            :placeholder="`Enter description`" />
        </div>
        <div class="dropdown-grid">
          <!-- Product dropdown -->


          <!-- Color dropdown -->
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

          <!-- Size dropdown -->
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
        </div>


        <div class="mb-3">
          <label :for="sku" class="form-label text-capitalize">sku</label>
          <input :id="sku" v-model="formData.sku" type="text" class="form-control" :placeholder="`Enter sku`" />
        </div>

        <div class="mb-3">
          <label :for="safetyStock" class="form-label text-capitalize">safetyStock</label>
          <input :id="safetyStock" v-model="formData.safetyStock" type="number" class="form-control"
            :placeholder="`Enter safetyStock`" />
        </div>

        <div class="mb-3">
          <label :for="qty" class="form-label text-capitalize">qty</label>
          <input :id="qty" v-model="formData.qty" type="number" class="form-control" :placeholder="`Enter qty`" />
        </div>

        <div class="mb-3">
          <label :for="sellStart" class="form-label text-capitalize">sellStart</label>
          <input :id="sellStart" v-model="formData.sellStart" type="datetime" class="form-control"
            :placeholder="`Enter sellStart`" />
        </div>

        <div class="mb-3">
          <label :for="sellEnd" class="form-label text-capitalize">sellEnd</label>
          <input :id="sellEnd" v-model="formData.sellEnd" type="datetime" class="form-control"
            :placeholder="`Enter sellEnd`" />
        </div>


        <button type="submit" :disable="props.action == 'view'" class="btn btn-primary">
          <span v-if="props.action === 'create'">Create</span>
          <span v-else-if="props.action === 'create'">Create</span>
          <span v-else>Update</span>
        </button>
      </form>
    </div>
  </div>
</template>
<style scoped>
.dropdown {
  min-width: 300px;
}

.dropdown-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 1rem;
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
import { dropDown, dropDownVariant } from '../../../Configs/DropDownList.js'
// import { fabric } from 'fabric/fabric-impl'
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
const mapSku = ref()
const formData = reactive({
  id: '',
  cost: '',
  price: '',
  turnBuy: '',
  description: '',
  sku: '',
  safetyStock: '',
  qty: '',
  sellStart: '',
  sellEnd: '',
  createdAt: '',
  updatedAt: '',
})

const listDashBoard = [
  "Accounts",
  "Authorities",
  "BaseProducts",
  "Cancels",
  "CartItems",
  "Catalogs",
  "Categories",
  "CostHistories",
  "Coupons",
  "News",
  "OrderItems",
  "Orders",
  "PaymentMethods",
  "PriceHistories",
  "ProductImages",
  "ProductItems",
  "PromotionProduct",
  "Promotions",
  "PurchaseOrderItems",
  "PurchaseOrders",
  "Returns",
  "Reviews",
  "Roles",
  "ShippingMethods",
  "UserAddresses",
  "VariantValues",
  "Variants",
]


async function submitUpdateForm() {
  console.log(formData)
  try {
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
    const response = await formTableService.post(formData)
    console.log('Insert successful:', response.data)
    router.push(`/Admin/${props.TableName}`)
  } catch (error) {
    console.error('Insert failed:', error)
  }
}
const dropDownListVariants = ref([])
const dropDownListBaseProduct = ref(new Map())
const fetchData = async () => {
  if (!props.TableName) return
  try {
    const response = await formTableService.getById(props.id)
    response.data.createdAt = formatDate(response.data.createdAt)
    response.data.updatedAt = formatDate(response.data.updatedAt)
    response.data.sellStart = formatDate(response.data.sellStart)
    response.data.sellSEnd = formatDate(response.data.sellSEnd)
    Object.assign(formData, response.data)
  } catch (err) {
    console.error('Get failed:', err)
  }

}
const selectedProduct = ref(null)
const selectedPrice = ref(null)
const selectedVarriantColor = ref("")
const selectedVarriantSize = ref("")
const autoCompleteListPrice = ref([])


function selectBaseProduct(product) {
  console.log(product)
  selectedProduct.value = product
  formData.baseProductId = product.id
  formData.cost = product.cost
  formData.price = product.listPriceRaw
  autoCompleteListPrice.value = product.listPrice
  formData.turnBuy = product.turnBuy
  formData.safetyStock = product.safetyStock
  formData.description = product.description
  // formData.sku = product.id
  formData.safetyStock = product.safetyStock
  formData.qty = product.qty //Save to form if needed
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
  fetchData;
  dropDownListVariants.value = await dropDownVariant()
  dropDownListBaseProduct.value = await dropDown("BaseProducts")
  console.log(dropDownListVariants.value)
})
watch(() => props.id, fetchData)
watch([() => selectedVarriantColor.value, () => selectedVarriantSize.value], () => {
  mapSku.value = (selectedVarriantColor.value?.signalSku) + "-" + selectedVarriantSize.value?.signalSku
})

watch(() => mapSku.value, () => {
  formData.sku = mapSku.value
})

</script>
