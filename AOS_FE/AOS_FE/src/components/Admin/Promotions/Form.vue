<template>
  <div class="container-fluid row">
    <div class="aside col-3">
      <Dashboard :listDashBoard="listDashBoard"></Dashboard>
    </div>
    <div class="article col-9">
      <form @submit.prevent="props.action === 'create' ? submitForm() : submitUpdateForm()">
        <div class="mb-3" :style="(props.action === 'view' || props.action === 'create') ? ' display:none;' : ''">
          <label for="id" class="form-label text-capitalize"></label>
          <input id="id" v-model="formData.id" v-if="props.action !== 'create'" :hidden="props.action === 'view'"
            type="number" class="form-control" placeholder="`Enter id`" />
        </div>
        <div class="mb-3">
          <label for="name" class="form-label text-capitalize">name</label>
          <input id="name" v-model="formData.name" type="text" class="form-control" placeholder="`Enter name`" />
        </div>

        <div class="mb-3">
          <label for="description" class="form-label text-capitalize">description</label>
          <input id="description" v-model="formData.description" type="text" class="form-control"
            placeholder="`Enter description`" />
        </div>

        <!-- <div class="mb-3">
          <label :for="type" class="form-label text-capitalize">type</label>
          <input :id="type" v-model="formData.type" type="text" class="form-control" :placeholder="`Enter type`" />
        </div>

        <div class="mb-3">
          <label :for="discountType" class="form-label text-capitalize">discountType</label>
          <input :id="discountType" v-model="formData.discountType" type="text" class="form-control"
            :placeholder="`Enter discountType`" />
        </div> -->
        <div class="mb-3">
          <label for="type" class="form-label text-capitalize">Type</label>
          <select id="type" v-model="formData.type" class="form-select">
            <option disabled value="">Select type</option>
            <option v-for="item in dropdownTypePromotions" :key="item.id" :value="item.type">
              {{ item.type }}
            </option>
          </select>
        </div>

        <div class="mb-3">
          <label for="discountType" class="form-label text-capitalize">Discount Type</label>
          <select id="discountType" v-model="formData.discountType" class="form-select"
            :disabled="formData.type === 'COMBO'">
            <option disabled value="">Select discount type</option>
            <option v-for="item in dropdownTypeDiscount" :key="item.id" :value="item.type">
              {{ item.type }}
            </option>
          </select>
        </div>

        <div class="mb-3">
          <label for="discountValue" class="form-label text-capitalize">discountValue</label>
          <input id="discountValue" v-model="formData.discountValue" type="number" :disabled="formData.type === 'COMBO'"
            class="form-control" placeholder="`Enter discountValue`" />
        </div>

        <div class="mb-3">
          <label for="comboPrice" class="form-label text-capitalize">comboPrice</label>
          <input id="comboPrice" v-model="formData.comboPrice" type="number" :disabled="formData.type === 'DISCOUNT'"
            class="form-control" placeholder="`Enter comboPrice`" />
        </div>

        <div class="mb-3">
          <label for="usageLimit" class="form-label text-capitalize">usageLimit</label>
          <input id="usageLimit" v-model="formData.usageLimit" type="number" class="form-control"
            placeholder="`Enter usageLimit`" />
        </div>

        <div class="mb-3">
          <label for="startAt" class="form-label text-capitalize">startAt</label>
          <input id="startAt" v-model="formData.startAt" type="datetime-local" class="form-control"
            placeholder="`Enter startAt`" />
        </div>

        <div class="mb-3">
          <label for="endAt" class="form-label text-capitalize">endAt</label>
          <input id="endAt" v-model="formData.endAt" type="datetime-local" class="form-control"
            placeholder="`Enter endAt`" />
        </div>

        <div class="mb-3">
          <!-- <label class="form-label text-capitalize">isActive</label> -->
          <input class="form-check-input" type="radio" id="isActiveTrue" :value="true" v-model="formData.isActive" />
          <label class="form-check-label" for="isActiveTrue">Active</label>

          <input class="form-check-input" type="radio" id="isActiveFalse" :value="false" v-model="formData.isActive" />
          <label class="form-check-label" for="isActiveFalse">Inactive</label>

        </div>


        <button type="submit" :disable="props.action === 'view'" class="btn btn-primary">
          <span v-if="props.action === 'create'">Create</span>
          <span v-else-if="props.action === 'create'">Create</span>
          <span v-else>Update</span>
        </button>
      </form>
    </div>
  </div>
</template>
<style></style>

<script setup>
import { reactive, ref, onMounted, watch } from 'vue'
import { formatDate, formatDateTimeLocal, toISOStringWithTimezone } from '../../Module/CommonsFunctions.js'
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
  description: '',
  type: '',
  discountType: '',
  discountValue: '',
  comboPrice: '',
  usageLimit: '',
  startAt: '',
  endAt: '',
  isActive: '',
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
const dropdownTypePromotions = [
  { id: "0", type: "DISCOUNT" },
  { id: "1", type: "COMBO" },
]
const dropdownTypeDiscount = [
  { id: "0", type: "PERCENT" },
  { id: "1", type: "AMOUNT" },
]


async function submitUpdateForm() {
  formData.startAt = toISOStringWithTimezone(formData.startAt)
  formData.endAt = toISOStringWithTimezone(formData.endAt)
  try {
    console.log(formData)
    const response = await formTableService.update(props.id, formData)
    console.log('Insert successful:', response.data)
    router.push(`/Admin/${props.TableName}`)
  } catch (error) {
    console.error('Insert failed:', error)
  }
}


async function submitForm() {
  formData.startAt = toISOStringWithTimezone(formData.startAt)
  formData.endAt = toISOStringWithTimezone(formData.endAt)
  try {
    console.log(formData)
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
    const response = await formTableService.getById(props.id)
    response.data.createdAt = formatDate(response.data.createdAt)
    response.data.updatedAt = formatDate(response.data.updatedAt)

    response.data.startAt = formatDateTimeLocal(response.data.startAt)
    response.data.endAt = formatDateTimeLocal(response.data.endAt)

    Object.assign(formData, response.data)
  } catch (err) {
    console.error('Get failed:', err)
  }
}

onMounted(fetchData)
watch(() => props.id, fetchData)
watch(
  () => formData.type,
  (newVal) => {
    if (newVal === 'COMBO') {
      formData.discountType = '';
      formData.discountValue = '';
    } else if (newVal === 'DISCOUNT') {
      formData.comboPrice = '';
    }
  }
);
</script>
