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
          <label :for="cost" class="form-label text-capitalize">cost</label>
          <input
            :id="cost"
            v-model="formData.cost"
            type="number"
            class="form-control"
            :placeholder="`Enter cost`"
          />
        </div>

        <div class="mb-3">
          <label :for="price" class="form-label text-capitalize">price</label>
          <input
            :id="price"
            v-model="formData.price"
            type="number"
            class="form-control"
            :placeholder="`Enter price`"
          />
        </div>

        <div class="mb-3">
          <label :for="turnBuy" class="form-label text-capitalize">turnBuy</label>
          <input
            :id="turnBuy"
            v-model="formData.turnBuy"
            type="number"
            class="form-control"
            :placeholder="`Enter turnBuy`"
          />
        </div>

        <div class="mb-3">
          <label :for="description" class="form-label text-capitalize">description</label>
          <input
            :id="description"
            v-model="formData.description"
            type="text"
            class="form-control"
            :placeholder="`Enter description`"
          />
        </div>

        <div class="mb-3">
          <label :for="sku" class="form-label text-capitalize">sku</label>
          <input
            :id="sku"
            v-model="formData.sku"
            type="text"
            class="form-control"
            :placeholder="`Enter sku`"
          />
        </div>

        <div class="mb-3">
          <label :for="safetyStock" class="form-label text-capitalize">safetyStock</label>
          <input
            :id="safetyStock"
            v-model="formData.safetyStock"
            type="number"
            class="form-control"
            :placeholder="`Enter safetyStock`"
          />
        </div>

        <div class="mb-3">
          <label :for="qty" class="form-label text-capitalize">qty</label>
          <input
            :id="qty"
            v-model="formData.qty"
            type="number"
            class="form-control"
            :placeholder="`Enter qty`"
          />
        </div>

        <div class="mb-3">
          <label :for="sellStart" class="form-label text-capitalize">sellStart</label>
          <input
            :id="sellStart"
            v-model="formData.sellStart"
            type="datetime"
            class="form-control"
            :placeholder="`Enter sellStart`"
          />
        </div>

        <div class="mb-3">
          <label :for="sellEnd" class="form-label text-capitalize">sellEnd</label>
          <input
            :id="sellEnd"
            v-model="formData.sellEnd"
            type="datetime"
            class="form-control"
            :placeholder="`Enter sellEnd`"
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
