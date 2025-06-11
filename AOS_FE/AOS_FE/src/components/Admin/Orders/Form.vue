<template>
  <div class="container-fluid row">
    <div class="aside col-3">
      <Dashboard :listDashBoard="listDashBoard"></Dashboard>
    </div>
    <div class="article col-9">
      <form
        @submit.prevent="props.action === 'create' ? submitForm() : submitUpdateForm()"
      >
        <div
          class="mb-3"
          :style="(props.action === 'view' || props.action === 'create') ? ' display:none;' : ''"
        >
          <label :for="id" class="form-label text-capitalize"></label>
          <input
            :id="id"
            v-model="formData.id"
            v-if="props.action !== 'create'"
            :hidden="props.action === 'view'"
            type="number"
            class="form-control"
            :placeholder="`Enter id`"
          />
        </div>
        <div class="mb-3">
          <label :for="shippingStatus" class="form-label text-capitalize">shippingStatus</label>
          <input
            :id="shippingStatus"
            v-model="formData.shippingStatus"
            type="text"
            class="form-control"
            :placeholder="`Enter shippingStatus`"
          />
        </div>

        <div class="mb-3">
          <label :for="estimatedShippingFee" class="form-label text-capitalize">estimatedShippingFee</label>
          <input
            :id="estimatedShippingFee"
            v-model="formData.estimatedShippingFee"
            type="number"
            class="form-control"
            :placeholder="`Enter estimatedShippingFee`"
          />
        </div>

        <div class="mb-3">
          <label :for="freeshipCouponCode" class="form-label text-capitalize">freeshipCouponCode</label>
          <input
            :id="freeshipCouponCode"
            v-model="formData.freeshipCouponCode"
            type="text"
            class="form-control"
            :placeholder="`Enter freeshipCouponCode`"
          />
        </div>

        <div class="mb-3">
          <label :for="actualShippingFee" class="form-label text-capitalize">actualShippingFee</label>
          <input
            :id="actualShippingFee"
            v-model="formData.actualShippingFee"
            type="number"
            class="form-control"
            :placeholder="`Enter actualShippingFee`"
          />
        </div>

        <div class="mb-3">
          <label :for="discountCouponCode" class="form-label text-capitalize">discountCouponCode</label>
          <input
            :id="discountCouponCode"
            v-model="formData.discountCouponCode"
            type="text"
            class="form-control"
            :placeholder="`Enter discountCouponCode`"
          />
        </div>

        <div class="mb-3">
          <label :for="discountValue" class="form-label text-capitalize">discountValue</label>
          <input
            :id="discountValue"
            v-model="formData.discountValue"
            type="number"
            class="form-control"
            :placeholder="`Enter discountValue`"
          />
        </div>

        <div class="mb-3">
          <label :for="shippedDate" class="form-label text-capitalize">shippedDate</label>
          <input
            :id="shippedDate"
            v-model="formData.shippedDate"
            type="date"
            class="form-control"
            :placeholder="`Enter shippedDate`"
          />
        </div>

        <div class="mb-3">
          <label :for="paymentStatus" class="form-label text-capitalize">paymentStatus</label>
          <input
            :id="paymentStatus"
            v-model="formData.paymentStatus"
            type="text"
            class="form-control"
            :placeholder="`Enter paymentStatus`"
          />
        </div>

        <div class="mb-3">
          <label :for="note" class="form-label text-capitalize">note</label>
          <input
            :id="note"
            v-model="formData.note"
            type="text"
            class="form-control"
            :placeholder="`Enter note`"
          />
        </div>

        <div class="mb-3">
          <label :for="point" class="form-label text-capitalize">point</label>
          <input
            :id="point"
            v-model="formData.point"
            type="number"
            class="form-control"
            :placeholder="`Enter point`"
          />
        </div>

        <div class="mb-3">
          <label :for="finalTotal" class="form-label text-capitalize">finalTotal</label>
          <input
            :id="finalTotal"
            v-model="formData.finalTotal"
            type="number"
            class="form-control"
            :placeholder="`Enter finalTotal`"
          />
        </div>

        <div class="mb-3">
          <label :for="orderInfor" class="form-label text-capitalize">orderInfor</label>
          <input
            :id="orderInfor"
            v-model="formData.orderInfor"
            type="text"
            class="form-control"
            :placeholder="`Enter orderInfor`"
          />
        </div>


        <button
          type="submit"
          :disable="props.action == 'view'"
          class="btn btn-primary"
        >
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
  import { formatDate } from '../../Module/CommonsFunctions.js'
  import Dashboard from '../../Module/Dashboard.vue'
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
  			shippingStatus: '',
  			estimatedShippingFee: '',
  			freeshipCouponCode: '',
  			actualShippingFee: '',
  			discountCouponCode: '',
  			discountValue: '',
  			shippedDate: '',
  			paymentStatus: '',
  			note: '',
  			point: '',
  			finalTotal: '',
  			orderInfor: '',
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
