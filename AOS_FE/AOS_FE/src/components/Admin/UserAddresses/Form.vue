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
          <label :for="recipientName" class="form-label text-capitalize">recipientName</label>
          <input
            :id="recipientName"
            v-model="formData.recipientName"
            type="text"
            class="form-control"
            :placeholder="`Enter recipientName`"
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
          <label :for="province" class="form-label text-capitalize">province</label>
          <input
            :id="province"
            v-model="formData.province"
            type="text"
            class="form-control"
            :placeholder="`Enter province`"
          />
        </div>

        <div class="mb-3">
          <label :for="district" class="form-label text-capitalize">district</label>
          <input
            :id="district"
            v-model="formData.district"
            type="text"
            class="form-control"
            :placeholder="`Enter district`"
          />
        </div>

        <div class="mb-3">
          <label :for="ward" class="form-label text-capitalize">ward</label>
          <input
            :id="ward"
            v-model="formData.ward"
            type="text"
            class="form-control"
            :placeholder="`Enter ward`"
          />
        </div>

        <div class="mb-3">
          <label :for="street" class="form-label text-capitalize">street</label>
          <input
            :id="street"
            v-model="formData.street"
            type="text"
            class="form-control"
            :placeholder="`Enter street`"
          />
        </div>

        <div class="mb-3">
          <label :for="label" class="form-label text-capitalize">label</label>
          <input
            :id="label"
            v-model="formData.label"
            type="text"
            class="form-control"
            :placeholder="`Enter label`"
          />
        </div>

        <div class="mb-3">
          <label :for="isDefault" class="form-label text-capitalize">isDefault</label>
          <input
            :id="isDefault"
            v-model="formData.isDefault"
            type="text"
            class="form-control"
            :placeholder="`Enter isDefault`"
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
    			recipientName: '',
    			phone: '',
    			province: '',
    			district: '',
    			ward: '',
    			street: '',
    			label: '',
    			isDefault: '',
    			note: '',
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
