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
          <label :for="email" class="form-label text-capitalize">email</label>
          <input
            :id="email"
            v-model="formData.email"
            type="text"
            class="form-control"
            :placeholder="`Enter email`"
          />
        </div>

        <div class="mb-3">
          <label :for="password" class="form-label text-capitalize">password</label>
          <input
            :id="password"
            v-model="formData.password"
            type="text"
            class="form-control"
            :placeholder="`Enter password`"
          />
        </div>

        <div class="mb-3">
          <label :for="fullname" class="form-label text-capitalize">fullname</label>
          <input
            :id="fullname"
            v-model="formData.fullname"
            type="text"
            class="form-control"
            :placeholder="`Enter fullname`"
          />
        </div>

        <div class="mb-3">
          <label :for="avatar" class="form-label text-capitalize">avatar</label>
          <input
            :id="avatar"
            v-model="formData.avatar"
            type="text"
            class="form-control"
            :placeholder="`Enter avatar`"
          />
        </div>

        <div class="mb-3">
          <label :for="phone" class="form-label text-capitalize">phone</label>
          <input
            :id="phone"
            v-model="formData.phone"
            type="text"
            class="form-control"
            :placeholder="`Enter phone`"
          />
        </div>

        <div class="mb-3">
          <label :for="averageOrderValue" class="form-label text-capitalize">averageOrderValue</label>
          <input
            :id="averageOrderValue"
            v-model="formData.averageOrderValue"
            type="number"
            class="form-control"
            :placeholder="`Enter averageOrderValue`"
          />
        </div>

        <div class="mb-3">
          <label :for="userRank" class="form-label text-capitalize">userRank</label>
          <input
            :id="userRank"
            v-model="formData.userRank"
            type="text"
            class="form-control"
            :placeholder="`Enter userRank`"
          />
        </div>

        <div class="mb-3">
          <label :for="totalSpent" class="form-label text-capitalize">totalSpent</label>
          <input
            :id="totalSpent"
            v-model="formData.totalSpent"
            type="number"
            class="form-control"
            :placeholder="`Enter totalSpent`"
          />
        </div>

        <div class="mb-3">
          <label :for="totalOrder" class="form-label text-capitalize">totalOrder</label>
          <input
            :id="totalOrder"
            v-model="formData.totalOrder"
            type="number"
            class="form-control"
            :placeholder="`Enter totalOrder`"
          />
        </div>

        <div class="mb-3">
          <label :for="loyaltyPoint" class="form-label text-capitalize">loyaltyPoint</label>
          <input
            :id="loyaltyPoint"
            v-model="formData.loyaltyPoint"
            type="number"
            class="form-control"
            :placeholder="`Enter loyaltyPoint`"
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
  			email: '',
  			password: '',
  			fullname: '',
  			avatar: '',
  			phone: '',
  			averageOrderValue: '',
  			userRank: '',
  			totalSpent: '',
  			totalOrder: '',
  			loyaltyPoint: '',
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
