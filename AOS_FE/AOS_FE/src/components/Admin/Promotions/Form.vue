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
          <label :for="type" class="form-label text-capitalize">type</label>
          <input
            :id="type"
            v-model="formData.type"
            type="text"
            class="form-control"
            :placeholder="`Enter type`"
          />
        </div>

        <div class="mb-3">
          <label :for="discountType" class="form-label text-capitalize">discountType</label>
          <input
            :id="discountType"
            v-model="formData.discountType"
            type="text"
            class="form-control"
            :placeholder="`Enter discountType`"
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
          <label :for="comboPrice" class="form-label text-capitalize">comboPrice</label>
          <input
            :id="comboPrice"
            v-model="formData.comboPrice"
            type="number"
            class="form-control"
            :placeholder="`Enter comboPrice`"
          />
        </div>

        <div class="mb-3">
          <label :for="usageLimit" class="form-label text-capitalize">usageLimit</label>
          <input
            :id="usageLimit"
            v-model="formData.usageLimit"
            type="number"
            class="form-control"
            :placeholder="`Enter usageLimit`"
          />
        </div>

        <div class="mb-3">
          <label :for="startAt" class="form-label text-capitalize">startAt</label>
          <input
            :id="startAt"
            v-model="formData.startAt"
            type="date"
            class="form-control"
            :placeholder="`Enter startAt`"
          />
        </div>

        <div class="mb-3">
          <label :for="endAt" class="form-label text-capitalize">endAt</label>
          <input
            :id="endAt"
            v-model="formData.endAt"
            type="date"
            class="form-control"
            :placeholder="`Enter endAt`"
          />
        </div>

        <div class="mb-3">
          <label :for="isActive" class="form-label text-capitalize">isActive</label>
          <input
            :id="isActive"
            v-model="formData.isActive"
            type="text"
            class="form-control"
            :placeholder="`Enter isActive`"
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



  async function submitUpdateForm() {
  	console.log(formData)
  	try {
  		const response = await formTableService.put(`http://localhost:8080/api/admin/${props.TableName}`, formData)
  		console.log('Insert successful:', response.data)
    router.push(`/Admin/${props.TableName}`)
  	} catch (error) {
  		console.error('Insert failed:', error)
  	}
  }


  async function submitForm() {
  	console.log(formData)
  	try {
  		const response = await formTableService.post(`http://localhost:8080/api/admin/${props.TableName}`, formData)
  		console.log('Insert successful:', response.data)
    router.push(`/Admin/${props.TableName}`)
  	} catch (error) {
  		console.error('Insert failed:', error)
  	}
  }
  const fetchData = async () => {
  	if (!props.TableName) return
  	try {
  		const response = await formTableService.get(`http://localhost:8080/api/admin/${props.TableName}/${props.id}`)
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
