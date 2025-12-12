<template>
  <div class="container-fluid row">
    <div class="aside col-3">
      <Dashboard :listDashBoard="listDashBoard"></Dashboard>
    </div>
    <div class="article col-9">
      <form @submit.prevent="props.action === 'create' ? submitForm() : submitUpdateForm()">
        <div class="mb-3" :style="(props.action === 'view' || props.action === 'create') ? ' display:none;' : ''">
          <label :for="id" class="form-label text-capitalize"></label>
          <input :id="id" v-model="formData.id" v-if="props.action !== 'create'" :hidden="props.action === 'view'"
            type="number" class="form-control" :placeholder="`Enter id`" />
        </div>
        <div class="mb-3">
          <label :for="nameTEGV" class="form-label text-capitalize">nameTEGV</label>
          <input :id="nameTEGV" v-model="formData.nameTEGV" type="text" class="form-control"
            :placeholder="`Enter nameTEGV`" />
        </div>

        <div class="mb-3">
          <label :for="emailTEGV" class="form-label text-capitalize">emailTEGV</label>
          <input :id="emailTEGV" v-model="formData.emailTEGV" type="email" class="form-control"
            :placeholder="`Enter emailTEGV`" />
        </div>


        <button type="submit" :disable="props.action == 'view'" class="btn btn-primary">
          <span v-if="props.action === 'create'">Create</span>
          <span v-else-if="props.action === 'create'">Create</span>
          <span v-else>Update</span>
        </button>
      </form>
      <form @submit.prevent="uploadExcel" enctype="multipart/form-data">
        <div class="mb-3">
          <label class="form-label">Choose Excel File</label>
          <input type="file" accept=".xlsx, .xls" @change="handleFileUpload" class="form-control" />
        </div>

        <button type="submit" class="btn btn-success">
          Import Excel
        </button>
      </form>
    </div>
  </div>
  <div class="container-fluid row">
    <div class="aside col-3">
      <Dashboard :listDashBoard="listDashBoard"></Dashboard>
    </div>

    <div class="article col-9">

      <!-- SEND EMAIL FORM -->
      <form @submit.prevent="sendEmail" enctype="multipart/form-data">

        <h4 class="mb-3">Send Email</h4>



        <!-- Subject -->
        <div class="mb-3">
          <label class="form-label">Subject</label>
          <input v-model="emailForm.subject" type="text" class="form-control" placeholder="Enter subject" required />
        </div>
        <!-- <quill-editor v-model="content" theme="snow" style="height: 300px;" /> -->
        <!-- Message -->
        <div class="mb-3">
          <label class="form-label">Message</label>
          <textarea v-model="emailForm.message" class="form-control" placeholder="Enter message" rows="7"
            required></textarea>
        </div>

        <!-- Multiple Images -->
        <div class="mb-3">
          <label class="form-label">Choose Images</label>
          <input type="file" accept="image/*" multiple @change="handleImages" class="form-control" />
        </div>

        <!-- Send Button -->
        <button type="submit" class="btn btn-success w-100">
          Send Email
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
import api from '../../../Configs/api.js'
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
  nameTEGV: '',
  emailTEGV: '',
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
const excelFile = ref(null)

function handleFileUpload(event) {
  excelFile.value = event.target.files[0]
}

async function uploadExcel() {
  if (!excelFile.value) {
    alert("Please choose a file.")
    return
  }

  const formDataUpload = new FormData()
  formDataUpload.append("file", excelFile.value)

  try {
    const response = await api.post(
      `http://localhost:8080/api/admin/EmailTEGV/import`,
      formDataUpload,
      { headers: { "Content-Type": "multipart/form-data" } }
    )
    console.log("Import successful:", response.data)
    alert("Excel imported successfully.")
    router.push(`/Admin/${props.TableName}`)
  } catch (error) {
    console.error("Import failed:", error)
    alert("Import failed. Check console for details.")
  }
}


const emailForm = reactive({
  subject: "",
  message: "",
  images: []
});

// Read selected images
function handleImages(event) {
  emailForm.images = event.target.files;
}

// Send email API
async function sendEmail() {
  try {
    const formData = new FormData();
    formData.append("toEmail", emailForm.toEmail);
    formData.append("subject", emailForm.subject);
    formData.append("message", emailForm.message);

    // Attach all selected images
    for (let i = 0; i < emailForm.images.length; i++) {
      formData.append("images", emailForm.images[i]);
    }

    const response = await api.post(
      "http://localhost:8080/api/admin/send-with-images",
      formData,
      { headers: { "Content-Type": "multipart/form-data" } }
    );

    alert("Email sent successfully!");
    console.log(response.data);
  } catch (error) {
    console.error("Email failed:", error);
    alert("Email failed. Check console for details.");
  }
}

onMounted(fetchData)
watch(() => props.id, fetchData)
</script>
