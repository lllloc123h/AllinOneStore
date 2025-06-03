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
          <label :for="userId" class="form-label text-capitalize">userId</label>
          <input
            :id="userId"
            v-model="formData.userId"
            type="number"
            class="form-control"
            :placeholder="`Enter userId`"
          />
        </div>

        <div class="mb-3">
          <label :for="action" class="form-label text-capitalize">action</label>
          <input
            :id="action"
            v-model="formData.action"
            type="text"
            class="form-control"
            :placeholder="`Enter action`"
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
          <label :for="ipAddress" class="form-label text-capitalize">ipAddress</label>
          <input
            :id="ipAddress"
            v-model="formData.ipAddress"
            type="text"
            class="form-control"
            :placeholder="`Enter ipAddress`"
          />
        </div>

        <div class="mb-3">
          <label :for="userAgent" class="form-label text-capitalize">userAgent</label>
          <input
            :id="userAgent"
            v-model="formData.userAgent"
            type="text"
            class="form-control"
            :placeholder="`Enter userAgent`"
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
          <label :for="module" class="form-label text-capitalize">module</label>
          <input
            :id="module"
            v-model="formData.module"
            type="text"
            class="form-control"
            :placeholder="`Enter module`"
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
  			userId: '',
  			action: '',
  			description: '',
  			ipAddress: '',
  			userAgent: '',
  			createdAt: '',
  			module: '',
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
  		const response = await axios.put(`http://localhost:8080/Api/Admin/${props.TableName}`, formData)
  		console.log('Insert successful:', response.data)
    router.push(`/Admin/${props.TableName}`)
  	} catch (error) {
  		console.error('Insert failed:', error)
  	}
  }


  async function submitForm() {
  	console.log(formData)
  	try {
  		const response = await axios.post(`http://localhost:8080/Api/Admin/${props.TableName}`, formData)
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
  		const response = await axios.get(`http://localhost:8080/Api/Admin/${props.TableName}/${props.id}`)
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
