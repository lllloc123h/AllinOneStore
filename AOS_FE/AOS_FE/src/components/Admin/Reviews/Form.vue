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
          <label :for="rating" class="form-label text-capitalize">rating</label>
          <input
            :id="rating"
            v-model="formData.rating"
            type="number"
            class="form-control"
            :placeholder="`Enter rating`"
          />
        </div>

        <div class="mb-3">
          <label :for="comment" class="form-label text-capitalize">comment</label>
          <input
            :id="comment"
            v-model="formData.comment"
            type="text"
            class="form-control"
            :placeholder="`Enter comment`"
          />
        </div>

        <div class="mb-3">
          <label :for="imageUrl1" class="form-label text-capitalize">imageUrl1</label>
          <input
            :id="imageUrl1"
            v-model="formData.imageUrl1"
            type="text"
            class="form-control"
            :placeholder="`Enter imageUrl1`"
          />
        </div>

        <div class="mb-3">
          <label :for="imageUrl2" class="form-label text-capitalize">imageUrl2</label>
          <input
            :id="imageUrl2"
            v-model="formData.imageUrl2"
            type="text"
            class="form-control"
            :placeholder="`Enter imageUrl2`"
          />
        </div>

        <div class="mb-3">
          <label :for="imageUrl3" class="form-label text-capitalize">imageUrl3</label>
          <input
            :id="imageUrl3"
            v-model="formData.imageUrl3"
            type="text"
            class="form-control"
            :placeholder="`Enter imageUrl3`"
          />
        </div>

        <div class="mb-3">
          <label :for="videoUrl" class="form-label text-capitalize">videoUrl</label>
          <input
            :id="videoUrl"
            v-model="formData.videoUrl"
            type="text"
            class="form-control"
            :placeholder="`Enter videoUrl`"
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
    			rating: '',
    			comment: '',
    			imageUrl1: '',
    			imageUrl2: '',
    			imageUrl3: '',
    			videoUrl: '',
    			createdAt: '',
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
