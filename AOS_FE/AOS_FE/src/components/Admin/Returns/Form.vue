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
          <label :for="reason" class="form-label text-capitalize">reason</label>
          <input
            :id="reason"
            v-model="formData.reason"
            type="text"
            class="form-control"
            :placeholder="`Enter reason`"
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

        <div class="mb-3">
          <label :for="isReturnedMoney" class="form-label text-capitalize">isReturnedMoney</label>
          <input
            :id="isReturnedMoney"
            v-model="formData.isReturnedMoney"
            type="text"
            class="form-control"
            :placeholder="`Enter isReturnedMoney`"
          />
        </div>

        <div class="mb-3">
          <label :for="refundAmount" class="form-label text-capitalize">refundAmount</label>
          <input
            :id="refundAmount"
            v-model="formData.refundAmount"
            type="number"
            class="form-control"
            :placeholder="`Enter refundAmount`"
          />
        </div>

        <div class="mb-3">
          <label :for="returnType" class="form-label text-capitalize">returnType</label>
          <input
            :id="returnType"
            v-model="formData.returnType"
            type="text"
            class="form-control"
            :placeholder="`Enter returnType`"
          />
        </div>

        <div class="mb-3">
          <label :for="refundType" class="form-label text-capitalize">refundType</label>
          <input
            :id="refundType"
            v-model="formData.refundType"
            type="text"
            class="form-control"
            :placeholder="`Enter refundType`"
          />
        </div>

        <div class="mb-3">
          <label :for="status" class="form-label text-capitalize">status</label>
          <input
            :id="status"
            v-model="formData.status"
            type="text"
            class="form-control"
            :placeholder="`Enter status`"
          />
        </div>

        <div class="mb-3">
          <label :for="processedAt" class="form-label text-capitalize">processedAt</label>
          <input
            :id="processedAt"
            v-model="formData.processedAt"
            type="datetime"
            class="form-control"
            :placeholder="`Enter processedAt`"
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
    			qty: '',
    			reason: '',
    			imageUrl1: '',
    			imageUrl2: '',
    			imageUrl3: '',
    			videoUrl: '',
    			isReturnedMoney: '',
    			refundAmount: '',
    			returnType: '',
    			refundType: '',
    			status: '',
    			processedAt: '',
    			createdAt: '',
    			updatedAt: '',
    })

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
      Object.assign(formData, response.data)
    } catch (err) {
      console.error('Get failed:', err)
    }
  }

    onMounted(fetchData)
    watch(() => props.id, fetchData)
</script>
