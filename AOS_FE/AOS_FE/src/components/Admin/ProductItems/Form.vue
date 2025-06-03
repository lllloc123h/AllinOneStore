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
          <label :for="name" class="form-label text-capitalize">name</label>
          <input
            :id="name"
            v-model="formData.name"
            type="text"
            class="form-control"
            :placeholder="`Enter name`"
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
          <label :for="isPromote" class="form-label text-capitalize">isPromote</label>
          <input
            :id="isPromote"
            v-model="formData.isPromote"
            type="text"
            class="form-control"
            :placeholder="`Enter isPromote`"
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
            type="date"
            class="form-control"
            :placeholder="`Enter sellStart`"
          />
        </div>

        <div class="mb-3">
          <label :for="sellEnd" class="form-label text-capitalize">sellEnd</label>
          <input
            :id="sellEnd"
            v-model="formData.sellEnd"
            type="date"
            class="form-control"
            :placeholder="`Enter sellEnd`"
          />
        </div>

        <div class="mb-3">
          <label :for="createdAt" class="form-label text-capitalize">createdAt</label>
          <input
            :id="createdAt"
            v-model="formData.createdAt"
            type="date"
            class="form-control"
            :placeholder="`Enter createdAt`"
          />
        </div>

        <div class="mb-3">
          <label :for="updatedAt" class="form-label text-capitalize">updatedAt</label>
          <input
            :id="updatedAt"
            v-model="formData.updatedAt"
            type="date"
            class="form-control"
            :placeholder="`Enter updatedAt`"
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

  const formData = reactive({
  			id: '',
  			name: '',
  			cost: '',
  			price: '',
  			isPromote: '',
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
  		const response = await axios.put(`http://localhost:8080/api/admin/${props.TableName}`, formData)
  		console.log('Insert successful:', response.data)
    router.push(`/Admin/${props.TableName}`)
  	} catch (error) {
  		console.error('Insert failed:', error)
  	}
  }


  async function submitForm() {
  	console.log(formData)
  	try {
  		const response = await axios.post(`http://localhost:8080/api/admin/${props.TableName}`, formData)
  		console.log('Insert successful:', response.data)
    router.push(`/Admin/${props.TableName}`)
  	} catch (error) {
  		console.error('Insert failed:', error)
  	}
  }
  const fetchData = async () => {

  	console.log(props.TableName + props.action)
  	if (!props.TableName) return
  	try {
  		const response = await axios.get(`http://localhost:8080/api/admin/${props.TableName}/${props.id}`)
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
