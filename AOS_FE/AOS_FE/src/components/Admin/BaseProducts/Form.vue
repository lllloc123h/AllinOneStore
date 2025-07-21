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
          :style="
            props.action === 'view' || props.action === 'create' ? ' display:none;' : ''
          "
        >
          <label for="id" class="form-label text-capitalize"></label>
          <input
            id="id"
            v-model="formData.id"
            v-if="props.action !== 'create'"
            :hidden="props.action === 'view'"
            type="number"
            class="form-control"
            placeholder="`Enter id`"
          />
        </div>
        <div class="mb-3">
          <label for="name" class="form-label text-capitalize">name</label>
          <input
            id="name"
            v-model="formData.name"
            type="text"
            class="form-control"
            placeholder="`Enter name`"
          />
        </div>

        <div class="mb-3">
          <label for="material" class="form-label text-capitalize">material</label>
          <input
            id="material"
            v-model="formData.material"
            type="text"
            class="form-control"
            placeholder="`Enter material`"
          />
        </div>
        <div class="mb-3" v-if="props.action === 'view' || props.action === 'update'">
          <label class="form-label text-capitalize">Categories</label>
          <input
            type="text"
            class="form-control"
            :value="formData.categories?.name || 'Unknown'"
            readonly
          />
        </div>
        <div class="mb-3" v-show="props.action != 'view'">
          <label for="categories" class="form-label text-capitalize">Categories</label>
          <select id="categories" v-model="formData.categories" class="form-select">
            <option disabled value="">Select type</option>
            <option
              v-for="item in categoriesDropDownList"
              :key="item.id"
              :value="item.id"
            >
              {{ item.name }}
            </option>
          </select>
        </div>
        <!-- <div class="mb-3">
          <label for="mainImageUrl" class="form-label text-capitalize">mainImageUrl</label>
          <input id="mainImageUrl" v-model="formData.mainImageUrl" type="text" class="form-control"
            placeholder="`Enter mainImageUrl`" />
        </div> -->
        <div class="mb-3">
          <label for="mainImageUrl" class="form-label text-capitalize"
            >mainImageUrl</label
          >
          <ImageUpload @update-main-image-url="onAvatarUpdate" />
        </div>

        <div v-if="formData.mainImageUrl" class="mb-3">
          <label class="form-label">Preview Avatar:</label>
          <img
            :src="previewImg"
            alt="mainImageUrl Preview"
            class="img-thumbnail"
            style="max-height: 150px"
          />
        </div>

        <!-- <div class="mb-3">
          <label for="isCustom" class="form-label text-capitalize">isCustom</label>
          <input id="isCustom" v-model="formData.isCustom" type="text" class="form-control"
            placeholder="`Enter isCustom`" />
        </div> -->
        <!-- <div class="mb-3">
          <label for="isActive" class="form-label text-capitalize">isActive</label>
          <input id="isActive" v-model="formData.isActive" type="text" class="form-control"
            placeholder="`Enter isActive`" />
        </div> -->
        <div class="mb-3">
          <!-- <label class="form-label text-capitalize">isActive</label> -->
          <input
            class="form-check-input"
            type="radio"
            id="isActiveTrue"
            :value="true"
            v-model="formData.isActive"
          />
          <label class="form-check-label" for="isActiveTrue">Active</label>

          <input
            class="form-check-input"
            type="radio"
            id="isActiveFalse"
            :value="false"
            v-model="formData.isActive"
          />
          <label class="form-check-label" for="isActiveFalse">Inactive</label>
        </div>

        <button type="submit" :disabled="props.action == 'view'" class="btn btn-primary">
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
import { reactive, ref, onMounted, watch } from "vue";
import { formatDate, formatDateTimeLocal } from "../../Module/CommonsFunctions.js";
import Dashboard from "../../Module/DashBoard.vue";
import { storage } from "../../../Configs/firebase.js";
import createCrudService from "../../../Configs/reusableCRUDService.js";
import { useRouter } from "vue-router";
import ImageUpload from "../../Module/ImageUpload.vue";
const router = useRouter();
import axios from "axios";
import "bootstrap/dist/js/bootstrap.bundle.min.js";
import "bootstrap/dist/css/bootstrap.min.css";
import { ref as storageRef, uploadBytes, getDownloadURL } from "firebase/storage";
const props = defineProps({
  TableName: {
    type: String,
    required: true,
  },
  action: {
    type: String,
    required: true,
  },
  id: {
    type: String,
    required: true,
  },
});
const formTableService = createCrudService(props.TableName);
const categoriesService = createCrudService("Categories");
const categoriesDropDownList = ref([]);
const previewImg = ref();
const formData = reactive({
  id: "",
  name: "",
  material: "",
  categories: "",
  mainImageUrl: "",
  isCustom: "",
  turnBuy: "",
  rating: "",
  isActive: "",
  createdAt: "",
  updatedAt: "",
});
function onAvatarUpdate(url) {
  // console.log("Received image URL:", url);
  formData.mainImageUrl = url.filePath;
  const filePath = `products/${Date.now()}_${file.name}`;
  const fileRef = storageRef(storage, filePath);
  previewImg.value = url.downloadUrl;
  // console.log("formData.mainImageUrl = ", formData.mainImageUrl);
}
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
];
async function submitUpdateForm() {
  try {
    formData.createdAt = formatDateTimeLocal(formData.createdAt);
    formData.updatedAt = formatDateTimeLocal(formData.updatedAt);
    console.log(formData);
    const response = await formTableService.update(props.id, formData);
    console.log("Insert successful:", response.data);
    router.push(`/Admin/${props.TableName}`);
  } catch (error) {
    console.error("Insert failed:", error);
  }
}
async function submitForm() {
  console.log(formData);
  try {
    const response = await formTableService.create(formData);
    console.log("Insert successful:", response.data);
    router.push(`/Admin/${props.TableName}`);
  } catch (error) {
    console.error("Insert failed:", error);
  }
}
const fetchData = async () => {
  if (!props.TableName) return;
  try {
    if (!props.action || props.action === "view" || props.action === "update") {
      const response = await formTableService.getById(props.id);
      const responseCategories = await categoriesService.getAll(0, 1000);
      // const fileRef = storageRef(storage, filePath);
      // previewImg.value = await getDownloadURL(fileRef);
      previewImg.value = response.data.mainImageUrl;
      categoriesDropDownList.value = responseCategories.data?.content.map((category) => {
        return {
          id: category.id,
          name: category.name,
        };
      });
      response.data.createdAt = formatDate(response.data.createdAt);
      response.data.updatedAt = formatDate(response.data.updatedAt);
      Object.assign(formData, response.data);
    }
    // const responseCategories = await categoriesService.getAll(0, 1000);
    // console.log("load ", responseCategories.data);
    // categoriesDropDownList.value = responseCategories.data?.content.map((category) => {
    //   return {
    //     id: category.id,
    //     name: category.name,
    //   };
    // });
  } catch (err) {
    console.error("Get failed:", err);
  }
};

onMounted(fetchData);
watch(() => props.id, fetchData);

watch(formData, (newValue) => {
  console.log(newValue);
});
</script>
