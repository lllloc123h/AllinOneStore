<template>
  <div class="container-fluid row">
    <div class="aside col-3">
      <Dashboard></Dashboard>
    </div>
    <div class="article col-9 form-article">
      <form
        class="product-form shadow p-4 rounded bg-white"
        @submit.prevent="props.action === 'create' ? submitForm() : submitUpdateForm()"
      >
        <div
          class="mb-3"
          :style="
            props.action === 'view' || props.action === 'create' ? 'display:none;' : ''
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
            readonly
          />
        </div>
        <div class="mb-3">
          <label for="name" class="form-label text-capitalize fw-semibold">Name</label>
          <input
            id="name"
            v-model="formData.name"
            type="text"
            class="form-control"
            placeholder="Enter name"
          />
        </div>

        <div class="mb-3">
          <label for="material" class="form-label text-capitalize fw-semibold"
            >Material</label
          >
          <input
            id="material"
            v-model="formData.material"
            type="text"
            class="form-control"
            placeholder="Enter material"
          />
        </div>
        <div class="mb-3" v-show="props.action != 'view'">
          <label for="categories" class="form-label text-capitalize fw-semibold"
            >Categories</label
          >
          <select id="categories" v-model="formData.categories" class="form-select">
            <option disabled value="">Select type</option>
            <option
              v-for="item in categoriesDropDownList"
              :key="item.id"
              :value="item.name"
            >
              {{ item.name }}
            </option>
          </select>
        </div>
        <div class="mb-3">
          <label for="mainImageUrl" class="form-label text-capitalize fw-semibold"
            >Main Image</label
          >
          <uploadProducts
            ref="uploadRef"
            :maxFiles="1"
            :aspectRatio="'4:5'"
            @update:images="handleImagesUploadBaseProduct"
            @delete-image="handleImagesDeleteBaseProduct"
          />
        </div>

        <!-- <div v-if="formData.mainImageUrl" class="mb-3 text-center">
          <label class="form-label fw-semibold">Preview:</label>
          <div class="preview-img-wrapper d-flex justify-content-center align-items-center">
            <img :src="previewImg" alt="mainImageUrl Preview" class="img-thumbnail shadow"
              style="max-height: 150px; border-radius: 8px" />
          </div>
        </div> -->
        <div class="mb-3 d-flex align-items-center gap-3">
          <div class="form-check form-check-inline">
            <input
              class="form-check-input"
              type="radio"
              id="isActiveTrue"
              :value="true"
              v-model="formData.active"
            />
            <label class="form-check-label" for="isActiveTrue">Active</label>
          </div>
          <div class="form-check form-check-inline">
            <input
              class="form-check-input"
              type="radio"
              id="isActiveFalse"
              :value="false"
              v-model="formData.active"
            />
            <label class="form-check-label" for="isActiveFalse">Inactive</label>
          </div>
        </div>
        <!-- custom -->
        <div class="mb-3 d-flex align-items-center gap-3">
          <div class="form-check form-check-inline">
            <input
              class="form-check-input"
              type="radio"
              id="isCustomTrue"
              :value="true"
              v-model="formData.custom"
              :disabled="props.action === 'update'"
            />
            <label class="form-check-label" for="isCustomTrue">Có thể custom</label>
          </div>
          <div class="form-check form-check-inline">
            <input
              class="form-check-input"
              type="radio"
              id="isCustomFalse"
              :value="false"
              v-model="formData.custom"
              :disabled="props.action === 'update'"
            />
            <label class="form-check-label" for="isCustomFalse">Không thể custom</label>
          </div>
        </div>

        <button
          type="submit"
          :disabled="props.action == 'view'"
          class="btn btn-primary w-100 py-2 fw-bold"
        >
          <span v-if="props.action === 'create'">Create</span>
          <span v-else>Update</span>
        </button>
      </form>
      <div v-if="selectedProduct" class="card mt-3 shadow-sm p-3 rounded-4">
        <div
          class="modal fade show d-block"
          v-if="showModalToUpdateProductItems"
          tabindex="-1"
          style="background-color: rgba(0, 0, 0, 0.5)"
        >
          <div class="modal-dialog modal-xl" style="max-width: 900px">
            <div class="modal-content">
              <div class="modal-header">
                <h5 class="modal-title">Product Form</h5>
                <button
                  type="button"
                  class="btn-close"
                  @click="
                    () => {
                      showModalToUpdateProductItems = false;
                    }
                  "
                ></button>
              </div>

              <div class="modal-body">
                <!-- Form -->
                <div class="mb-4">
                  <label class="form-label text-capitalize fw-semibold"
                    >List Product Items Images</label
                  >
                  <table class="table table-hover custom-table">
                    <thead class="table-header-custom">
                      <tr>
                        <th class="table-th action-column">ID</th>
                        <th class="table-th action-column">Images</th>
                        <th class="table-th action-column">Thao tác</th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr
                        v-for="(item, index) in formDataUpdateProductItems.images"
                        :key="item.id"
                        class="table-row"
                      >
                        <td class="table-cell action-cell">
                          {{ item.id }}
                        </td>

                        <td class="table-cell">
                          <span class="cell-content">
                            <img
                              :src="item.imageUrl"
                              alt="image"
                              style="
                                max-width: 900px;
                                max-height: 100px;
                                object-fit: contain;
                              "
                              @error="handleImageError($event)"
                            />
                            <span
                              v-if="item.imageError"
                              style="
                                padding: 4px 8px;
                                background: #f8f9fa;
                                border-radius: 4px;
                                font-size: 0.8rem;
                              "
                            >
                              Không thể tải ảnh
                            </span>
                          </span>
                        </td>

                        <td class="table-cell action-cell">
                          <input
                            type="radio"
                            name="defaultImage"
                            :checked="item.default"
                            @change="setImageDefault(item.id)"
                          />
                        </td>

                        <!-- Delete Button -->
                        <td class="table-cell action-cell">
                          <button
                            type="button"
                            @click="deleteImageById(item.id)"
                            class="btn btn-danger btn-sm action-btn"
                            title="Xóa"
                          >
                            <i class="bi bi-trash"></i>
                          </button>
                        </td>
                      </tr>
                    </tbody>
                  </table>
                </div>
                <div class="mb-4">
                  <label class="form-label text-capitalize fw-semibold"
                    >Ảnh sản phẩm</label
                  >
                  <uploadProducts
                    ref="uploadRefProductItems"
                    :maxFiles="5"
                    :aspectRatio="'4:5'"
                    @update:images="handleImagesUpdate"
                    @delete-image="handleImagesDelete"
                  />
                </div>
                <div class="mb-3">
                  <label for="cost" class="form-label text-capitalize">Cost</label>
                  <input
                    id="cost"
                    v-model="formDataUpdateProductItems.cost"
                    type="number"
                    class="form-control"
                    placeholder="Enter cost"
                    required
                  />
                </div>

                <div class="mb-3">
                  <label for="price" class="form-label text-capitalize">Price</label>
                  <input
                    id="price"
                    v-model="formDataUpdateProductItems.price"
                    type="number"
                    class="form-control"
                    placeholder="Enter price"
                    required
                  />
                </div>

                <div class="mb-3">
                  <label for="turnBuy" class="form-label text-capitalize">Turn Buy</label>
                  <input
                    id="turnBuy"
                    v-model="formDataUpdateProductItems.turnBuy"
                    type="number"
                    class="form-control"
                    placeholder="Enter turnBuy"
                    disabled
                  />
                </div>

                <div class="mb-3">
                  <label for="description" class="form-label text-capitalize"
                    >Description (Hãy cập nhật dữ liệu cho Chatbot khi mô tả thay
                    đổi)</label
                  >
                  <input
                    id="description"
                    v-model="formDataUpdateProductItems.description"
                    type="text"
                    class="form-control"
                    placeholder="Enter description"
                  />
                </div>

                <div class="mb-3">
                  <label for="sku" class="form-label text-capitalize">SKU</label>
                  <input
                    id="sku"
                    v-model="formDataUpdateProductItems.sku"
                    type="text"
                    class="form-control"
                    placeholder="Enter sku"
                    disabled
                  />
                </div>

                <div class="mb-3">
                  <label for="safetyStock" class="form-label text-capitalize"
                    >Id vector store</label
                  >
                  <input
                    id="safetyStock"
                    v-model="formDataUpdateProductItems.searchPoint"
                    type="text"
                    class="form-control"
                    placeholder="Enter safety stock"
                    disabled
                  />
                </div>

                <div class="mb-3">
                  <label for="qty" class="form-label text-capitalize">Quantity</label>
                  <input
                    id="qty"
                    v-model="formDataUpdateProductItems.qty"
                    type="number"
                    class="form-control"
                    placeholder="Enter quantity"
                    required
                  />
                </div>

                <div class="mb-3">
                  <label for="sellStart" class="form-label text-capitalize"
                    >Sell Start</label
                  >
                  <input
                    id="sellStart"
                    v-model="formDataUpdateProductItems.sellStart"
                    type="datetime-local"
                    class="form-control"
                    placeholder="Enter sell start"
                  />
                </div>

                <div class="mb-3">
                  <label for="sellEnd" class="form-label text-capitalize">Sell End</label>
                  <input
                    id="sellEnd"
                    v-model="formDataUpdateProductItems.sellEnd"
                    type="datetime-local"
                    class="form-control"
                    placeholder="Enter sell end"
                  />
                </div>
                <div class="mb-3 d-flex align-items-center gap-3">
                  <div class="form-check form-check-inline">
                    <input
                      class="form-check-input"
                      type="radio"
                      id="isActiveTrue"
                      :value="true"
                      v-model="formDataUpdateProductItems.active"
                    />
                    <label class="form-check-label" for="isActiveTrue">Active</label>
                  </div>
                  <div class="form-check form-check-inline">
                    <input
                      class="form-check-input"
                      type="radio"
                      id="isActiveFalse"
                      :value="false"
                      v-model="formDataUpdateProductItems.active"
                    />
                    <label class="form-check-label" for="isActiveFalse">Inactive</label>
                  </div>
                </div>
              </div>

              <div class="modal-footer">
                <button
                  class="btn btn-secondary"
                  @click="showModalToUpdateProductItems = false"
                >
                  Close
                </button>
                <button class="btn btn-success" @click="submitFormUpdateProductItems">
                  Save
                </button>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div
            class="dropdown mb-3 col-6"
            v-for="(items, groupName) in mapVarriants"
            :key="groupName"
          >
            <label class="form-label">Select {{ groupName }}</label>
            <button
              class="btn btn-outline-secondary dropdown-toggle w-100"
              type="button"
              data-bs-toggle="dropdown"
            >
              <span
                v-if="selected[groupName]"
                :style="{ color: refError === true ? 'red' : '' }"
              >
                {{ selected[groupName].description }} ({{
                  selected[groupName].signalSku
                }})
              </span>
              <span v-else>Select a {{ groupName }}</span>
            </button>
            <ul class="dropdown-menu w-100">
              <li
                v-for="variant in items"
                :key="variant.id"
                @click="selectVariant(groupName, variant)"
                class="dropdown-item d-flex align-items-center"
                style="cursor: pointer"
              >
                <div>
                  <strong>{{ variant.description }}</strong
                  ><br />
                  <small class="text-muted">{{ variant.signalSku }}</small>
                </div>
              </li>
            </ul>
          </div>
        </div>

        <div class="d-flex flex-column gap-2">
          <button
            class="btn btn-sm btn-outline-primary"
            @click="addToListVariantPreview()"
          >
            thêm
          </button>
        </div>
        <div class="row">
          <div
            class="mb-3 col-6"
            v-for="(items, groupName) in variantPrevieBeforeSaveBaseProduct"
            :key="groupName"
          >
            <label class="form-label">{{ groupName }}</label>
            <div class="list-group w-100">
              <div
                v-for="variant in items"
                :key="variant.id || variant.signalSku"
                class="list-group-item d-flex align-items-center"
                style="cursor: pointer"
              >
                <div>
                  <strong>{{ variant.description }}</strong
                  ><br />
                  <small class="text-muted">{{ variant.signalSku }}</small>
                </div>
              </div>
            </div>
          </div>
        </div>

        <label class="form-label">Preview combinations variant</label>
        <div v-for="(combo, index) in combinations" :key="index" class="mb-2">
          <div class="border p-2 rounded">
            <span v-for="(variant, index) in combo" :key="variant.signalSku"
              >{{ variant.description }} {{ index == 0 ? "-" : "" }}
            </span>
            <span v-for="(variant, index) in combo" :key="variant.signalSku"
              >{{ variant.signalSku }} {{ index == 0 ? "-" : "" }}</span
            >
          </div>
        </div>
        <div class="d-flex flex-column gap-2">
          <button class="btn btn-sm btn-outline-primary" @click="saveProductItems()">
            Lưu biến thể
          </button>
        </div>
        <div class="variant-preview mt-4">
          <h5 class="mb-3">Danh sách biến thể đã thêm:</h5>
          <div v-if="list.length === 0" class="text-muted">
            Chưa có biến thể nào được thêm.
          </div>
          <ul class="list-group">
            <li
              class="list-group-item d-flex justify-content-between align-items-center"
              v-for="(item, index) in list"
            >
              <div class="d-flex align-items-center">
                <img
                  :src="item.imgPreview || previewMainImg"
                  alt="Preview"
                  class="me-3 rounded"
                  style="width: 50px; height: 50px; object-fit: cover"
                />
                <div>
                  NAME: <strong>{{ item.name }}</strong
                  ><br />
                  <small>
                    SKU: {{ item.sku }} | Giá: {{ item.price.toLocaleString() }} đ | SL:
                    {{ item.qty }} </small
                  ><br />
                </div>
              </div>
              <div class="d-flex flex-column gap-2">
                <button
                  class="btn btn-sm btn-outline-primary"
                  @click="openPopupToUpdateProductItems(item.productItemsId)"
                >
                  Cập nhật
                </button>
                <button
                  class="btn btn-sm btn-outline-danger"
                  @click="removeVariant(item.productItemsId)"
                >
                  Xóa
                </button>
              </div>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>
<style></style>

<script setup>
import { reactive, ref, onMounted, watch } from "vue";
import { formatDate, formatDateTimeLocal } from "../../Module/CommonsFunctions.js";
import Dashboard from "../../Module/DashBoard.vue";
import createCrudService from "../../../Configs/reusableCRUDService.js";
import { useRouter } from "vue-router";
import api from "../../../Configs/api.js";
import "bootstrap/dist/js/bootstrap.bundle.min.js";
import "bootstrap/dist/css/bootstrap.min.css";
import uploadProducts from "../../Module/upload-images.vue";
import { ref as storageRef, uploadBytes, getDownloadURL } from "firebase/storage";

const router = useRouter();
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
const productItemsService = createCrudService("ProductItems");
const productImagesService = createCrudService("ProductImages");
const categoriesService = createCrudService("Categories");
const categoriesDropDownList = ref([]);
const previewImg = ref();
const list = ref([]);
const resultUpload = ref([]);
const selectedCategory = ref([]);
const previewMainImg = ref("");
const mapVarriants = ref({});
const selected = ref([]);
const variantPrevieBeforeSaveBaseProduct = ref({});
const combinations = ref();
const uploadRef = ref(null);
const uploadRefProductItems = ref(null);

const listProductItemsToSave = ref({});
const expandedSections = ref(["category-0", "productItems-0", "variant-0", "price"]);
const showModalToUpdateProductItems = ref(false);
import { notification } from "ant-design-vue";
import { Modal } from "ant-design-vue";

const showConfirm = (id) => {
  Modal.confirm({
    title: "Bạn có muốn cập nhật lại dữ liệu cho Chat bot về sản phẩm",
    content: "Hành động này không thể hoàn tác.",
    okText: "OK",
    cancelText: "Hủy",
    onOk() {
      api
        .put("/admin/qdrant?productItemId=" + id)
        .then((response) => {
          notification.success({
            message: "Cập nhật thành công",
            description: "Dữ liệu đã được cập nhật thành công cho Chat bot.",
          });
        })
        .catch((error) => {
          notification.error({
            message: "Cập nhật thất bại",
            description: `Thất bại`,
          });
          console.log(error);
        });
    },
    onCancel() {
      console.log("Người dùng bấm Cancel");
    },
  });
};
const formData = reactive({
  id: "",
  name: "áo thun 1",
  material: "Cotton Nam",
  categories: "",
  mainImageUrl: null,
  custom: "true",
  turnBuy: "0",
  rating: "0",
  isActive: "true",
  active: "true",
  createdAt: "",
  updatedAt: "",
  isCustom: "true",
});
const selectedProduct = ref({
  id: "",
  baseId: "",
  cost: "",
  price: "",
  turnBuy: 0,
  description: "",
  sku: "",
  safetyStock: "",
  qty: "",
  sellStart: "",
  sellEnd: "",
  createdAt: "",
  updatedAt: "",
  imgPreview: "",
  fileNameImgOfVariant: "",
});
const formDataUpdateProductItems = ref({
  id: "",
  baseId: "",
  cost: "",
  price: "",
  turnBuy: 0,
  description: "",
  sku: "",
  searchPoint: "",
  qty: "",
  sellStart: "",
  sellEnd: "",
  createdAt: "",
  updatedAt: "",
  imgPreview: "",
  fileNameImgOfVariant: "",
  images: "",
  promotions: "",
  isGift: "",
  baseProducts: "",
  imageUrl: "",
  material: "",
  name: "",
  productItemsId: "",
  active: "",
  isActive: "",
});
const formDataUpdateProductIamges = ref({
  id: "",
  imageUrl: "",
  createdAt: "",
  updatedAt: "",
  productItems: "",
  isDefault: "",
});

async function openPopupToUpdateProductItems(id) {
  showModalToUpdateProductItems.value = true;
  formDataUpdateProductItems.value = list.value.find(
    (condition) => condition.productItemsId === id
  );
  console.log("formDataUpdateProductItems:", formDataUpdateProductItems.value);
  // if (
  //   uploadRefProductItems.value === null &&
  //   formDataUpdateProductItems.value.images.length > 0
  // ) {
  //   console.log(
  //     "formDataUpdateProductItems:",
  //     formDataUpdateProductItems.value.images.map((item) => {
  //       return item.imageUrl;
  //     })
  //   );
  //   uploadRefProductItems.value.loadFromUrls(
  //     formDataUpdateProductItems.value.images.map((item) => {
  //       return item.imageUrl;
  //     })
  //   );
  // }
}
async function removeVariant(index) {
  try {
    const response = await api.delete("/admin/ProductItems/" + index);
    notification.success({
      message: "Success",
      description: `Biến thể đã được xóa thành công.`,
    });
  } catch (error) {
    console.error("Remove variant failed:", error);
    notification.error({
      message: "Error",
      description: `${error.response.data.message}`,
    });
  }
  await getProductItems(props.id);
}
async function selectVariant(GroupName, Variant) {
  selected.value[GroupName] = Variant;
  for (const groupName in selected.value) {
    const selectedVariant = selected.value[groupName];
    if (!selectedVariant) continue;
    if (!Array.isArray(variantPrevieBeforeSaveBaseProduct.value[groupName])) {
      variantPrevieBeforeSaveBaseProduct.value[groupName] = [];
    }
    const isContain = variantPrevieBeforeSaveBaseProduct.value[groupName].filter(
      (item) => item.signalSku === selectedVariant.signalSku
    );
    if (isContain.length > 0) {
      refError.value = true;
      continue;
    }
    refError.value = false;
  }
}
const refError = ref();
async function addToListVariantPreview() {
  for (const groupName in selected.value) {
    const selectedVariant = selected.value[groupName];
    if (!selectedVariant) continue;
    if (!Array.isArray(variantPrevieBeforeSaveBaseProduct.value[groupName])) {
      variantPrevieBeforeSaveBaseProduct.value[groupName] = [];
    }
    const isContain = variantPrevieBeforeSaveBaseProduct.value[groupName].filter(
      (item) => item.signalSku === selectedVariant.signalSku
    );
    if (isContain.length > 0) continue;
    variantPrevieBeforeSaveBaseProduct.value[groupName].push(selectedVariant);
    selected.value[groupName] = null;
  }
  combinations.value = generateVariantCombinations(
    variantPrevieBeforeSaveBaseProduct.value
  );
}
async function saveProductItems() {
  combinations.value = generateVariantCombinations(
    variantPrevieBeforeSaveBaseProduct.value
  );
  listProductItemsToSave.value = combinations.value.map((item) => {
    return {
      id: null,
      baseId: props.id,
      cost: 0,
      price: 0,
      turnBuy: 0,
      description: "",
      sku: props.id + "-" + item[1].signalSku + "-" + item[0].signalSku,
      searchPoint: "",
      qty: 0,
      sellStart: "",
      sellEnd: "",
      createdAt: "",
      updatedAt: "",
      imgPreview: "",
      fileNameImgOfVariant: "",
      images: [],
      promotions: [],
      isGift: false,
      baseProducts: {},
      imageUrl: formData.mainImageUrl,
      material: "",
      name: "",
      productItemsId: "",
      active: true,
      isActive: true,
    };
  });
  try {
    for (const productItems of listProductItemsToSave.value) {
      const isContainSku = list.value.filter((condition) => {
        return condition.sku.includes(productItems.sku);
      });
      if (isContainSku.length > 0) {
        notification.error({
          message: "Error",
          description: `Biến thể với SKU ${productItems.sku} đã tồn tại. Vui lòng kiểm tra lại.`,
        });
        console.log("Debug Avoid duplicate:", productItems);
        return;
      }
      const response = await api.post(`/admin/ProductItems`, productItems);
      notification.success({
        message: "Success",
        description: `Biến thể với SKU ${productItems.sku} đã được thêm thành công.`,
      });
      console.log("Insert successful:", response.data);
      await getProductItems(props.id);
    }
  } catch (error) {
    notification.error({
      message: "Error",
      description: `Lưu biến thể thất bại. Vui lòng thử lại.`,
    });
    console.error("Insert failed:", error);
  }

  console.log("listProductItemsTosave ", listProductItemsToSave.value);
}

function generateVariantCombinations(groupedVariants) {
  const groupNames = Object.keys(groupedVariants);
  const result = [];

  function backtrack(index, currentCombo) {
    if (index === groupNames.length) {
      result.push([...currentCombo]);
      return;
    }

    const groupName = groupNames[index];
    const variants = groupedVariants[groupName];

    for (const variant of variants) {
      // at this point, use condition if the variant == null or undefined skip push method
      if (!variant || !variant.signalSku) continue;
      currentCombo.push({ group: groupName, ...variant });
      backtrack(index + 1, currentCombo);
      currentCombo.pop();
    }
  }
  console.log(result);
  backtrack(0, []);
  return result;
}

async function submitUpdateForm() {
  if (formData.mainImageUrl === null) {
    notification.error({
      message: "Error",
      description: `Vui lòng thêm ảnh đại diện cho sản phẩm`,
    });
    return;
  }
  try {
    formData.createdAt = null;
    formData.updatedAt = null;
    console.log(formData);
    const response = await formTableService.update(props.id, formData);
    console.log("Update successful:", response.data);
    router.push(`/Admin/${props.TableName}`);

    await getProductItems(props.id);
  } catch (error) {
    console.error("Insert failed:", error);
  }
}

async function submitFormUpdateProductItems() {
  try {
    formDataUpdateProductItems.value.createdAt = formatDateTimeLocal(
      formDataUpdateProductItems.value.createdAt
    );
    formDataUpdateProductItems.value.updatedAt = formatDateTimeLocal(
      formDataUpdateProductItems.value.updatedAt
    );
    formDataUpdateProductItems.value.sellStart = formatDateTimeLocal(
      formDataUpdateProductItems.value.sellStart
    );
    formDataUpdateProductItems.value.sellEnd = formatDateTimeLocal(
      formDataUpdateProductItems.value.sellEnd
    );
    formDataUpdateProductItems.value.id = formDataUpdateProductItems.value.productItemsId;
    console.log(formDataUpdateProductItems.value);
    const response = await productItemsService.update(
      formDataUpdateProductItems.value.productItemsId,
      formDataUpdateProductItems.value
    );
    showConfirm(formDataUpdateProductItems.value.productItemsId);
    console.log("Update successful:", response.data);
    showModalToUpdateProductItems.value = false;
    await getProductItems(props.id);
  } catch (error) {
    console.error("Insert failed:", error);
  }
}

async function submitForm() {
  console.log(formData);
  if (formData.mainImageUrl === null) {
    notification.error({
      message: "Error",
      description: `Vui lòng thêm ảnh đại diện cho sản phẩm`,
    });
    return;
  }
  formData.createdAt = null;
  formData.updatedAt = null;
  try {
    const response = await formTableService.create(formData);
    console.log("Insert successful:", response.data);
    router.push(`/Admin/${props.TableName}`);
    await getProductItems(props.id);
  } catch (error) {
    console.error("Insert failed:", error);
  }
}

async function getProductItems(id) {
  if (!props.TableName) return;
  try {
    const response = await api.get("/admin/ProductItems/ByBaseProductId/" + id);
    console.log(response.data.content);
    if (response.data.content && response.data.content.length > 0) {
      list.value = response.data.content.map((item) => {
        return {
          ...item,
          name: item.baseProducts.name,
          imgPreview:
            item.images.filter((image) => image.default === true)[0]?.imageUrl || "",
          fileNameImgOfVariant: item.fileNameImgOfVariant || "",
          productItemsId: item.id,
        };
      });
    } else {
      list.value = [];
    }
  } catch (error) {
    console.error("Get failed:", error);
  }
}
const handleImagesUploadBaseProduct = async (images) => {
  console.log("Images updated:", images);
  if (images.length > 0) {
    formData.mainImageUrl = images[0].cloudinaryUrl || images[0].url;
    formData.id = props.id;
    if (images[0].cloudinaryUrl && props.action === "update") {
      try {
        formData.createdAt = formatDateTimeLocal(formData.createdAt);
        formData.updatedAt = formatDateTimeLocal(formData.updatedAt);
        const response = await api.put(`/admin/BaseProducts/${props.id}`, formData);
        console.log("Update successful:", response.data);

        showModalToUpdateProductItems.value = false;
        notification.success({
          message: "Cập nhật thành công",
          description: `Avatar đã được cập nhật thành công.`,
          duration: 3,
        });
      } catch (error) {
        showModalToUpdateProductItems.value = false;
        notification.error({
          message: "Cập nhật thất bại",
          description: `Không thể cập nhật avatar.`,
          duration: 3,
        });
        console.error("Failed to update avatar:", error);
      }
    }
  } else {
    formDataUpdateProductIamges.value.imageUrl = null;
  }
  await getProductItems(props.id);
};
const handleImagesDeleteBaseProduct = async (index) => {
  console.log("Deleting image at index:", index);
  formData.mainImageUrl = null;
  notification.success({
    message: "Xóa thành công",
    description: `Hãy cập nhật lại ảnh mới`,
    duration: 3,
  });
  // try {
  //   const response = await api.put(`/admin/BaseProducts/${props.id}`, formData);
  //   showModalToUpdateProductItems.value = false;
  //   notification.success({
  //     message: "Xóa thành công",
  //     description: `Ảnh đã được xóa thành công.`,
  //     duration: 3,
  //   });
  //   console.log("Image deleted successfully");
  //   await getProductItems(props.id);
  // } catch (error) {
  //   showModalToUpdateProductItems.value = false;
  //   notification.error({
  //     message: "Xóa thất bại",
  //     description: `Không thể xóa ảnh.`,
  //     duration: 3,
  //   });
  //   console.error("Failed to delete image:", error);
  // }
};

const handleImagesUpdate = async (images) => {
  console.log("Images updated:", images);
  if (images.length > 0) {
    formDataUpdateProductIamges.value.productItems =
      formDataUpdateProductItems.value.productItemsId;
    formDataUpdateProductIamges.value.imageUrl = images[0].cloudinaryUrl || images[0].url;
    if (images[0].cloudinaryUrl) {
      try {
        for (const ImageElement of images) {
          console.log(ImageElement);
          formDataUpdateProductIamges.value.imageUrl = ImageElement.cloudinaryUrl;
          formDataUpdateProductIamges.value.createdAt = formatDateTimeLocal(
            formDataUpdateProductItems.value.createdAt
          );
          formDataUpdateProductIamges.value.updatedAt = formatDateTimeLocal(
            formDataUpdateProductItems.value.updatedAt
          );
          const response = await productImagesService.create(
            formDataUpdateProductIamges.value
          );
          showModalToUpdateProductItems.value = false;
          console.log("Update successful:", response.data);
          notification.success({
            message: "Cập nhật thành công",
            description: `Avatar đã được cập nhật thành công.`,
            duration: 3,
          });
        }
      } catch (error) {
        showModalToUpdateProductItems.value = false;
        notification.error({
          message: "Cập nhật thất bại",
          description: `Không thể cập nhật avatar.`,
          duration: 3,
        });
        console.error("Failed to update avatar:", error);
      }
    }
  } else {
    formDataUpdateProductIamges.value.imageUrl = null;
  }

  await getProductItems(props.id);
  if (images.length > 0) {
    console.log("Avatar updated:", formData.avatarUrl);
  }
};
const handleImagesDelete = async (index) => {
  console.log("Deleting image at index:", index);
  formDataUpdateProductIamges.value.imageUrl = null;
  try {
    const response = await productImagesService.update(
      formDataUpdateProductIamges.value.id,
      formDataUpdateProductIamges.value
    );
    notification.success({
      message: "Xóa thành công",
      description: `Ảnh đã được xóa thành công.`,
      duration: 3,
    });

    showModalToUpdateProductItems.value = false;
    console.log("Image deleted successfully");
    await getProductItems(props.id);
  } catch (error) {
    notification.error({
      message: "Xóa thất bại",
      description: `Không thể xóa ảnh.`,
      duration: 3,
    });
    console.error("Failed to delete image:", error);
  }
};
const setImageDefault = async (id) => {
  try {
    const response = await api.put(`/admin/ProductImages/` + id);
    if (response) {
      notification.success({
        message: "Cập nhật thành công",
        description: `Ảnh đã được đặt làm mặc định.`,
        duration: 3,
      });
    }
    showModalToUpdateProductItems.value = false;
    await getProductItems(props.id);
    formDataUpdateProductItems.value = list.value.find(
      (condition) => condition.productItemsId === props.id
    );
    // showConfirm(response.data.itemId);
  } catch (error) {
    console.error("Update failed:", error);
  }
};
const deleteImageById = async (id) => {
  try {
    // console.log("Delete successful:", formDataUpdateProductItems.value);
    let image = formDataUpdateProductItems.value.images.find((image) => image.id === id);
    console.log("Delete successful:", image);
    if (image.default === true) {
      showModalToUpdateProductItems.value = false;
      return notification.error({
        message: "Xóa thất bại",
        description: `Không thể xóa ảnh mặc định.`,
        duration: 3,
      });
    }
    const response = await productImagesService.delete(id);
    await getProductItems(props.id);
    showModalToUpdateProductItems.value = false;
    notification.success({
      message: "Xóa thành công",
      description: `Ảnh đã được xóa thành công.`,
      duration: 3,
    });
  } catch (error) {
    console.error("Delete failed:", error);
  }
};

const fetchData = async () => {
  if (!props.TableName) return;
  try {
    if (!props.action || props.action === "view" || props.action === "update") {
      const response = await formTableService.getById(props.id);
      console.log("Response data:", response.data);
      const responseCategories = await categoriesService.getAll(0, 1000);
      categoriesDropDownList.value = responseCategories.data.content.map((category) => {
        return {
          id: category.id,
          name: category.name,
        };
      });
      if (uploadRef.value && response.data.mainImageUrl) {
        uploadRef.value.loadFromUrls([response.data.mainImageUrl]);
      }
      await getProductItems(props.id);
      Object.assign(formData, response.data);
    }
    const responseCategories = await categoriesService.getAll(0, 1000);
    categoriesDropDownList.value = responseCategories.data.content.map((category) => {
      return {
        id: category.id,
        name: category.name,
      };
    });
  } catch (err) {
    console.error("Get failed:", err);
  }
};

onMounted(async () => {
  fetchData(),
    api
      .get("/VariantValues")
      .then((resp) => {
        mapVarriants.value = resp.data;
        console.log(mapVarriants.value);
        for (const groupName in resp.data) {
          selected.value[groupName] = [];
          variantPrevieBeforeSaveBaseProduct.value[groupName] = [];
        }
      })
      .catch((error) => console.log(error));
});
watch(() => props.id, fetchData);
</script>
<style scoped>
.filter-card-header {
  padding: 1.2rem 1.5rem;
  background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: space-between;
  transition: all 0.3s ease;
  border-bottom: 1px solid #e9ecef;
}

.filter-card-header:hover {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
}

.filter-title {
  font-weight: 600;
  font-size: 1rem;
  display: flex;
  align-items: center;
  color: #2c3e50;
}

.filter-card-header:hover .filter-title {
  color: white;
}

.toggle-icon {
  transition: transform 0.3s ease;
  font-size: 1rem;
  color: #7f8c8d;
}

.filter-card-header:hover .toggle-icon {
  color: white;
}

/* Container spacing */
.mb-3 {
  margin-bottom: 1.5rem;
}

/* Group label styling */
.form-label {
  font-weight: 600;
  font-size: 1.1rem;
  margin-bottom: 0.5rem;
  color: #333;
}

/* List group container */
.list-group {
  border: 1px solid #dee2e6;
  border-radius: 6px;
  padding: 0;
  background-color: #f8f9fa;
}

/* List item styling */
.list-group-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0.75rem 1rem;
  border: none;
  border-bottom: 1px solid #dee2e6;
  background-color: #fff;
  transition: background-color 0.2s ease;
}

/* Last item: remove bottom border */
.list-group-item:last-child {
  border-bottom: none;
}

/* Hover effect */
.list-group-item:hover {
  background-color: #e9ecef;
  cursor: pointer;
}

/* Variant description */
.list-group-item strong {
  font-size: 1rem;
  color: #212529;
}

/* Variant SKU */
.list-group-item small {
  font-size: 0.85rem;
  color: #6c757d;
}

.toggle-icon.rotated {
  transform: rotate(180deg);
}

.filter-card-content {
  max-height: 0;
  overflow: hidden;
  transition: max-height 0.4s ease;
}

.dropdown-toggle {
  width: 100%;
  text-align: left;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.dropdown-toggle span {
  display: flex;
  align-items: center;
}

.dropdown-menu {
  width: 100%;
  max-height: 300px;
  overflow-y: auto;
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.dropdown-item {
  padding: 0.5rem 1rem;
  transition: background-color 0.2s ease-in-out;
}

.dropdown-item:hover {
  background-color: #f0f0f0;
}

.dropdown-item img {
  border-radius: 6px;
  object-fit: cover;
}

.filter-card-content.expanded {
  max-height: 500px;
}

.filter-options {
  padding: 1.5rem;
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

/* Tiêu đề danh sách */
.variant-preview h5 {
  font-weight: 600;
  color: #343a40;
}

/* Thông báo chưa có biến thể */
.variant-preview .text-muted {
  font-style: italic;
  font-size: 0.95rem;
}

/* Item trong danh sách */
.variant-preview .list-group-item {
  border: 1px solid #dee2e6;
  border-radius: 8px;
  margin-bottom: 10px;
  padding: 12px 16px;
  transition: box-shadow 0.2s ease;
}

.dropdown {
  margin-bottom: 1rem;
}

.variant-preview .list-group-item:hover {
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

/* Ảnh preview */
.variant-preview img {
  width: 50px;
  height: 50px;
  object-fit: cover;
  border-radius: 6px;
  border: 1px solid #ccc;
}

/* Thông tin sản phẩm */
.variant-preview strong {
  font-size: 1rem;
  color: #212529;
}

.variant-preview small {
  font-size: 0.85rem;
  color: #6c757d;
}

/* Nhóm nút thao tác */
.variant-preview .btn {
  min-width: 80px;
  font-size: 0.8rem;
  padding: 4px 8px;
}

.variant-preview .btn-outline-primary:hover {
  background-color: #0d6efd;
  color: #fff;
}

.variant-preview .btn-outline-danger:hover {
  background-color: #dc3545;
  color: #fff;
}
</style>
