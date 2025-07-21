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
          <label for="description" class="form-label text-capitalize">description</label>
          <input
            id="description"
            v-model="formData.description"
            type="text"
            class="form-control"
            placeholder="`Enter description`"
          />
        </div>

        <!-- <div class="mb-3">
          <label :for="type" class="form-label text-capitalize">type</label>
          <input :id="type" v-model="formData.type" type="text" class="form-control" :placeholder="`Enter type`" />
        </div>

        <div class="mb-3">
          <label :for="discountType" class="form-label text-capitalize">discountType</label>
          <input :id="discountType" v-model="formData.discountType" type="text" class="form-control"
            :placeholder="`Enter discountType`" />
        </div> -->
        <div class="mb-3">
          <label for="type" class="form-label text-capitalize">Type</label>
          <select id="type" v-model="formData.type" class="form-select">
            <option disabled value="">Select type</option>
            <option
              v-for="item in dropdownTypePromotions"
              :key="item.id"
              :value="item.type"
            >
              {{ item.type }}
            </option>
          </select>
        </div>

        <div class="mb-3">
          <label for="discountValue" class="form-label text-capitalize"
            >discountValue</label
          >
          <input
            id="discountValue"
            v-model="formData.discountValue"
            type="number"
            :disabled="formData.type === 'COMBO'"
            class="form-control"
            placeholder="`Enter discountValue`"
          />
        </div>

        <div class="mb-3">
          <label for="comboPrice" class="form-label text-capitalize">comboPrice</label>
          <input
            id="comboPrice"
            v-model="formData.comboPrice"
            type="number"
            :disabled="formData.type === 'DISCOUNT'"
            class="form-control"
            placeholder="`Enter comboPrice`"
          />
        </div>

        <div class="mb-3">
          <label for="usageLimit" class="form-label text-capitalize">usageLimit</label>
          <input
            id="usageLimit"
            v-model="formData.qty"
            type="number"
            class="form-control"
            placeholder="`Enter usageLimit`"
          />
        </div>

        <div class="mb-3">
          <label for="startAt" class="form-label text-capitalize">startAt</label>
          <input
            id="startAt"
            v-model="formData.startAt"
            type="datetime-local"
            class="form-control"
            placeholder="`Enter startAt`"
          />
        </div>

        <div class="mb-3">
          <label for="endAt" class="form-label text-capitalize">endAt</label>
          <input
            id="endAt"
            v-model="formData.endAt"
            type="datetime-local"
            class="form-control"
            placeholder="`Enter endAt`"
          />
        </div>

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
        <!-- Base Product Selection -->
        <div class="mb-3">
          <label class="form-label text-capitalize">Select Base Product</label>
          <div class="dropdown">
            <button
              class="btn btn-outline-secondary dropdown-toggle w-100"
              type="button"
              data-bs-toggle="dropdown"
            >
              <span v-if="selectedProduct">
                <img
                  :src="selectedProduct.mainImage"
                  alt=""
                  width="30"
                  height="30"
                  class="me-2"
                />
                {{ selectedProduct.name }} ({{ selectedProduct.material }})
              </span>
              <span v-else>Select a product</span>
            </button>
            <ul
              class="dropdown-menu"
              style="width: 100%; max-height: 300px; overflow-y: auto"
            >
              <li
                v-for="product in dropDownListBaseProduct"
                :key="product.id"
                @click="selectBaseProduct(product)"
                class="dropdown-item d-flex align-items-center"
                style="cursor: pointer"
              >
                <img
                  :src="product.mainImage"
                  alt=""
                  width="40"
                  height="40"
                  class="me-2"
                />
                <div>
                  <strong>{{ product.name }}</strong
                  ><br />
                  <small class="text-muted">{{ product.material }}</small>
                </div>
              </li>
            </ul>
          </div>
        </div>

        <!-- Base Product Information Display -->
        <div v-if="selectedProduct" class="card mt-3 shadow-sm p-3 rounded-4">
          <h5 class="mb-3">Selected Base Product Information</h5>
          <div class="row">
            <div class="col-md-6 mb-2">
              <strong>Name:</strong> {{ selectedProduct.name }}
            </div>
            <div class="col-md-6 mb-2">
              <strong>Material:</strong> {{ selectedProduct.material }}
            </div>
            <div class="col-md-6 mb-2">
              <strong>Quantity:</strong> {{ selectedProduct.qty }}
            </div>
            <div class="col-md-6 mb-2">
              <strong>Category:</strong>
              {{
                categoriesDropDownList.find((c) => c.id === selectedProduct.categoryId)
                  ?.name || "Unknown"
              }}
            </div>
          </div>

          <!-- Required Quantity Input -->
          <div class="mt-3" v-if="formData.type !== 'DISCOUNT'">
            <label class="form-label">Required Quantity for this Base Product:</label>
            <input
              type="number"
              class="form-control"
              :value="getBaseProductRequiredQuantity(selectedProduct.id)"
              @input="
                updateBaseProductRequiredQuantity(selectedProduct.id, $event.target.value)
              "
              min="1"
              placeholder="Enter required quantity"
            />
            <small class="form-text text-muted">
              Minimum quantity required to apply this promotion for this base product
            </small>
          </div>
        </div>

        <!-- Product Items List -->
        <div v-if="selectedProduct && productItemsList.length > 0" class="mt-4">
          <h5 class="mb-3">Product Items for {{ selectedProduct.name }}:</h5>
          <div class="row">
            <div v-for="item in productItemsList" :key="item.id" class="col-md-6 mb-3">
              <div
                class="card shadow-sm product-item-card"
                :class="{ 'selected-item': isItemSelected(item.id) }"
                @click="toggleItemSelection(item)"
                style="cursor: pointer"
              >
                <div class="card-body">
                  <div class="d-flex align-items-center">
                    <img
                      :src="item.imageUrl || selectedProduct.mainImagePreviewImg"
                      alt="Product Item"
                      class="me-3 rounded"
                      style="width: 60px; height: 60px; object-fit: cover"
                    />
                    <div class="flex-grow-1">
                      <h6 class="card-title mb-1">
                        {{ item.baseProducts?.name || selectedProduct.name }}
                      </h6>
                      <p class="card-text mb-1">
                        <small>SKU: {{ item.sku }}</small
                        ><br />
                        <small>Cost: {{ item.cost?.toLocaleString() }} VND</small> <br />
                        <small>Price: {{ item.price?.toLocaleString() }} VND</small><br />
                        <small>Quantity: {{ item.qty }}</small>
                      </p>

                      <!-- Gift Toggle -->
                      <div
                        class="form-check form-switch mt-2"
                        v-if="isItemSelected(item.id) && formData.type !== 'DISCOUNT'"
                      >
                        <input
                          class="form-check-input"
                          type="checkbox"
                          :id="'gift-' + item.id"
                          :checked="isItemGift(item.id)"
                          @change="toggleItemGift(item.id, $event.target.checked)"
                          @click.stop
                        />
                        <label class="form-check-label" :for="'gift-' + item.id">
                          <small class="text-success">
                            <i class="bi bi-gift me-1"></i>
                            Mark as Gift
                          </small>
                        </label>
                      </div>
                    </div>
                    <div class="selection-indicator">
                      <i
                        v-if="isItemSelected(item.id)"
                        class="bi bi-check-circle-fill text-success"
                        style="font-size: 1.5rem"
                      ></i>
                      <i
                        v-else
                        class="bi bi-circle text-muted"
                        style="font-size: 1.5rem"
                      ></i>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- Selected Items Display Panel - Show all selected items from all bases -->
        <div v-if="selectedItemsFromAllBases.length > 0" class="mt-4">
          <div class="selected-items-panel">
            <h5 class="mb-3">
              <i class="bi bi-cart me-2"></i>
              Selected Items for Promotion ({{ selectedItemsFromAllBases.length }})
            </h5>
            <div class="selected-items-container">
              <div
                v-for="item in selectedItemsFromAllBases"
                :key="item.id"
                class="selected-item-chip"
              >
                <img
                  :src="item.imageUrl || item.baseProduct?.mainImagePreviewImg"
                  alt="Selected Item"
                  class="chip-image"
                />
                <div class="chip-content">
                  <span class="chip-name">{{ item.name }}</span>
                  <small class="chip-sku">{{ item.sku }}</small>
                  <small class="chip-base">Base: {{ item.baseProduct?.name }}</small>
                  <small
                    v-if="item.isGift && formData.type !== 'DISCOUNT'"
                    class="chip-gift"
                  >
                    <i class="bi bi-gift me-1"></i>
                    Gift Item
                  </small>
                </div>
                <button
                  type="button"
                  class="btn-remove"
                  @click="removeItemFromAllSelections(item.id, item.baseProduct.id)"
                >
                  <i class="bi bi-x"></i>
                </button>
              </div>
            </div>
            <div class="mt-3 d-flex justify-content-between">
              <button
                type="button"
                class="btn btn-outline-danger btn-sm"
                @click="clearAllSelections"
              >
                <i class="bi bi-trash me-1"></i>
                Clear All
              </button>
              <div class="text-white">
                <small>
                  <i class="bi bi-info-circle me-1"></i>
                  Items from {{ getUniqueBaseProductCount() }} base product(s)
                </small>
              </div>
            </div>
          </div>
        </div>

        <!-- Summary of selected base products -->
        <div v-if="selectedItemsFromAllBases.length > 0" class="mt-4">
          <div class="card border-primary">
            <div class="card-header bg-primary text-white">
              <h6 class="mb-0">
                <i class="bi bi-bar-chart me-2"></i>
                Selection Summary
              </h6>
            </div>
            <div class="card-body">
              <div class="row">
                <div
                  v-for="(group, baseProductId) in getGroupedSelections()"
                  :key="baseProductId"
                  class="col-md-4 mb-3"
                >
                  <div class="summary-card">
                    <img
                      :src="group.baseProduct.mainImagePreviewImg"
                      alt="Base Product"
                      class="summary-image"
                    />
                    <div class="summary-content">
                      <h6 class="summary-title">{{ group.baseProduct.name }}</h6>
                      <p class="summary-count">
                        {{ group.items.length }} item(s) selected
                      </p>
                      <p class="summary-required-qty" v-if="formData.type !== 'DISCOUNT'">
                        <small class="text-primary">
                          <i class="bi bi-123 me-1"></i>
                          Required Qty:
                          {{ getBaseProductRequiredQuantity(group.baseProduct.id) || 1 }}
                        </small>
                      </p>
                      <p
                        class="summary-gifts"
                        v-if="group.giftCount > 0 && formData.type !== 'DISCOUNT'"
                      >
                        <small class="text-success">
                          <i class="bi bi-gift me-1"></i>
                          {{ group.giftCount }} gift item(s)
                        </small>
                      </p>
                      <button
                        type="button"
                        class="btn btn-sm btn-outline-primary"
                        @click="selectBaseProduct(group.baseProduct)"
                      >
                        View Items
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- Promotion Requirements Overview -->
        <div v-if="selectedItemsFromAllBases.length > 0" class="mt-4">
          <div class="card border-info">
            <div class="card-header bg-info text-white">
              <h6 class="mb-0">
                <i class="bi bi-info-circle me-2"></i>
                Promotion Requirements Overview
              </h6>
            </div>
            <div class="card-body">
              <div class="row">
                <div class="col-md-6">
                  <h6 class="text-primary">
                    <i class="bi bi-layers me-2"></i>
                    Base Products Requirements
                  </h6>
                  <ul class="list-unstyled">
                    <li
                      v-for="(group, baseProductId) in getGroupedSelections()"
                      :key="baseProductId"
                      class="mb-2"
                    >
                      <strong>{{ group.baseProduct.name }}</strong>
                      <br />
                      <small class="text-muted">
                        <span v-if="formData.type !== 'DISCOUNT'">
                          Min Quantity:
                          {{ getBaseProductRequiredQuantity(group.baseProduct.id) }} |
                        </span>
                        Items: {{ group.items.length
                        }}<span v-if="formData.type !== 'DISCOUNT'">
                          | Gifts: {{ group.giftCount }}</span
                        >
                      </small>
                    </li>
                  </ul>
                </div>
                <div class="col-md-6" v-if="formData.type !== 'DISCOUNT'">
                  <h6 class="text-success">
                    <i class="bi bi-gift me-2"></i>
                    Gift Items Summary
                  </h6>
                  <div v-if="getGiftItems().length > 0">
                    <ul class="list-unstyled">
                      <li
                        v-for="giftItem in getGiftItems()"
                        :key="giftItem.id"
                        class="mb-1"
                      >
                        <small>
                          <i class="bi bi-gift me-1 text-success"></i>
                          {{ giftItem.name }} ({{ giftItem.sku }})
                        </small>
                      </li>
                    </ul>
                  </div>
                  <div v-else>
                    <small class="text-muted">No gift items selected</small>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <button type="submit" :disable="props.action === 'view'" class="btn btn-primary">
          <span v-if="props.action === 'create'">Create</span>
          <span v-else-if="props.action === 'create'">Create</span>
          <span v-else>Update</span>
        </button>
      </form>
    </div>
  </div>
</template>
<style scoped>
.dropdown {
  margin-bottom: 1rem;
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

.card {
  border-radius: 12px;
  border: 1px solid #dee2e6;
}

.form-control {
  border-radius: 0.75rem;
  border: 1px solid #dee2e6;
  transition: border-color 0.2s, box-shadow 0.2s;
}

.form-control:focus {
  border-color: #86b7fe;
  box-shadow: 0 0 0 0.25rem rgba(13, 110, 253, 0.25);
}

/* Product Item Selection Styles */
.product-item-card {
  transition: all 0.3s ease;
  border: 2px solid transparent;
}

.product-item-card:hover {
  border-color: #0d6efd;
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

.product-item-card.selected-item {
  border-color: #198754;
  background-color: #f8f9fa;
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(25, 135, 84, 0.2);
}

.selection-indicator {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 30px;
}

/* Selected Items Panel Styles */
.selected-items-panel {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 16px;
  padding: 1.5rem;
  color: white;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
}

.selected-items-container {
  display: flex;
  flex-wrap: wrap;
  gap: 0.75rem;
  margin-top: 1rem;
}

.selected-item-chip {
  display: flex;
  align-items: center;
  background: rgba(255, 255, 255, 0.2);
  backdrop-filter: blur(10px);
  border-radius: 25px;
  padding: 0.5rem;
  border: 1px solid rgba(255, 255, 255, 0.3);
  transition: all 0.3s ease;
}

.selected-item-chip:hover {
  background: rgba(255, 255, 255, 0.3);
  transform: translateY(-1px);
}

.chip-image {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  object-fit: cover;
  margin-right: 0.75rem;
  border: 2px solid rgba(255, 255, 255, 0.5);
}

.chip-content {
  display: flex;
  flex-direction: column;
  margin-right: 0.75rem;
}

.chip-name {
  font-weight: 600;
  font-size: 0.9rem;
  margin-bottom: 0.25rem;
}

.chip-sku {
  font-size: 0.75rem;
  opacity: 0.8;
}

.chip-base {
  font-size: 0.7rem;
  opacity: 0.7;
  font-style: italic;
}

.chip-gift {
  font-size: 0.7rem;
  color: #28a745;
  font-weight: 600;
}

.btn-remove {
  background: rgba(220, 53, 69, 0.8);
  border: none;
  color: white;
  border-radius: 50%;
  width: 24px;
  height: 24px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: all 0.3s ease;
}

.btn-remove:hover {
  background: rgba(220, 53, 69, 1);
  transform: scale(1.1);
}

.btn-remove i {
  font-size: 0.75rem;
}

/* Summary Cards Styles */
.summary-card {
  display: flex;
  align-items: center;
  padding: 1rem;
  border: 1px solid #dee2e6;
  border-radius: 8px;
  background: #f8f9fa;
  transition: all 0.3s ease;
}

.summary-card:hover {
  background: #e9ecef;
  transform: translateY(-2px);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.summary-image {
  width: 50px;
  height: 50px;
  border-radius: 8px;
  object-fit: cover;
  margin-right: 1rem;
}

.summary-content {
  flex-grow: 1;
}

.summary-title {
  margin-bottom: 0.5rem;
  font-size: 0.9rem;
  font-weight: 600;
}

.summary-count {
  margin-bottom: 0.5rem;
  font-size: 0.8rem;
  color: #6c757d;
}

.summary-required-qty {
  margin-bottom: 0.5rem;
}

.summary-gifts {
  margin-bottom: 0.5rem;
}
</style>

<script setup>
import { reactive, ref, onMounted, watch } from "vue";
import {
  formatDate,
  formatDateTimeLocal,
  toISOStringWithTimezone,
} from "../../Module/CommonsFunctions.js";
import Dashboard from "../../Module/DashBoard.vue";
import createCrudService from "../../../Configs/reusableCRUDService.js";
import { useRouter } from "vue-router";
import { dropDown } from "../../../Configs/DropDownList.js";
import api from "../../../Configs/api.js";

const router = useRouter();
import axios from "axios";
import "bootstrap/dist/js/bootstrap.bundle.min.js";
import "bootstrap/dist/css/bootstrap.min.css";
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

const formData = reactive({
  id: "",
  name: "KHUYẾN MÃI HÈ CỰC SỐC MUA 2 ÁO THUN UNISEX GIÁ 150.000 VND",
  description: "Mua 2 áo thun unisex bất kỳ, giá combo chỉ 150.000 VND",
  type: "COMBO",
  discountValue: "",
  comboPrice: "150000",
  qty: "200",
  startAt: "2025-07-01T04:23:00",
  endAt: "2025-07-20T19:22:00",
  isActive: "false",
  createdAt: "",
  updatedAt: "",
});

// Base Product Selection Variables
const selectedProduct = ref(null);
const dropDownListBaseProduct = ref([]);
const productItemsList = ref([]);
const selectedProductItems = ref([]);
const categoriesDropDownList = ref([]);
const categoriesService = createCrudService("Categories");

// Store selected items from all base products
const allSelectedItems = ref(new Map()); // Map<baseProductId, Set<itemId>>
const selectedItemsFromAllBases = ref([]); // Array of all selected items with base product info

// Store required quantities for base products
const baseProductRequiredQuantities = ref(new Map()); // Map<baseProductId, number>
// Store gift status for items
const itemGiftStatus = ref(new Map()); // Map<itemId, boolean>

const listDashBoard = [
  "Accounts",
  "Authorities",
  "BaseProducts",
  "ProductItems",
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
const dropdownTypePromotions = [
  { id: "0", type: "DISCOUNT" },
  { id: "1", type: "COMBO" },
];

async function submitUpdateForm() {
  // Validation: Check if all items are gifts (only for COMBO type)
  if (formData.type !== "DISCOUNT" && selectedItemsFromAllBases.value.length > 0) {
    const allItemsAreGifts = selectedItemsFromAllBases.value.every((item) =>
      isItemGift(item.id)
    );
    if (allItemsAreGifts) {
      alert(
        "Không thể cập nhật khuyến mãi khi tất cả sản phẩm đều là quà tặng. Vui lòng chọn ít nhất một sản phẩm không phải là quà tặng."
      );
      return;
    }
  }

  try {
    formData.createdAt = formatDateTimeLocal(formData.createdAt);
    formData.updatedAt = formatDateTimeLocal(formData.updatedAt);
    console.log(formData);
    const response = await formTableService.update(props.id, formData);

    // Update promotion products
    await updatePromotionProducts(props.id);

    console.log("Update successful:", response.data);
    router.push(`/Admin/${props.TableName}`);
  } catch (error) {
    console.error("Update failed:", error);
  }
}

async function submitForm() {
  // Validation: Check if all items are gifts (only for COMBO type)
  if (formData.type !== "DISCOUNT" && selectedItemsFromAllBases.value.length > 0) {
    const allItemsAreGifts = selectedItemsFromAllBases.value.every((item) =>
      isItemGift(item.id)
    );
    if (allItemsAreGifts) {
      alert(
        "Không thể tạo khuyến mãi khi tất cả sản phẩm đều là quà tặng. Vui lòng chọn ít nhất một sản phẩm không phải là quà tặng."
      );
      return;
    }
  }

  formData.startAt = toISOStringWithTimezone(formData.startAt);
  formData.endAt = toISOStringWithTimezone(formData.endAt);
  try {
    const response = await formTableService.create(formData);
    // Create promotion products
    if (selectedItemsFromAllBases.value.length > 0) {
      await createPromotionProducts(response.data.id);
    }
    console.log("Create successful:", response.data);
    router.push(`/Admin/${props.TableName}`);
  } catch (error) {
    console.error("Create failed:", error);
  }
}

// Create promotion products
async function createPromotionProducts(promotionId) {
  try {
    const promotionProductPromises = selectedItemsFromAllBases.value.map((item) => {
      // For DISCOUNT type, don't use required quantity, just use 1
      const requiredQuantity =
        formData.type === "DISCOUNT"
          ? null
          : getBaseProductRequiredQuantity(item.baseProduct.id);
      const isGift = formData.type === "DISCOUNT" ? false : isItemGift(item.id);
      console.log(
        "Creating promotion product for item:",
        item,
        "with required quantity:",
        requiredQuantity,
        "and isGift:",
        isGift
      );
      return api.post("/admin/PromotionProducts", {
        promotionId: promotionId,
        productItem: { id: item.id },
        requireQty: requiredQuantity,
        gift: isGift,
      });
    });

    await Promise.all(promotionProductPromises);
    console.log("Promotion products created successfully");
  } catch (error) {
    console.error("Create promotion products failed:", error);
  }
}
async function editPromotionProducts(promotionId) {
  try {
    const promotionProductPromises = selectedItemsFromAllBases.value.map((item) => {
      // For DISCOUNT type, don't use required quantity, just use 1
      const requiredQuantity =
        formData.type === "DISCOUNT"
          ? null
          : getBaseProductRequiredQuantity(item.baseProduct.id);
      const isGift = formData.type === "DISCOUNT" ? false : isItemGift(item.id);
      console.log(
        "Creating promotion product for item:",
        item,
        "with required quantity:",
        requiredQuantity,
        "and isGift:",
        isGift
      );
      console.log("update ", {
        id: getPromotionProductIdFromGrouped(item.baseProduct.id, item.id),
        promotionId: promotionId,
        productItem: { id: item.id },
        requireQty: requiredQuantity,
        gift: isGift,
      });
      return api.put("/admin/PromotionProducts", {
        id: getPromotionProductIdFromGrouped(item.baseProduct.id, item.id),
        promotionId: promotionId,
        productItem: { id: item.id },
        requireQty: requiredQuantity,
        gift: isGift,
      });
    });

    await Promise.all(promotionProductPromises);
    console.log("Promotion products updated successfully");
  } catch (error) {
    console.error("Updated promotion products failed:", error);
  }
}

// Update promotion products
async function updatePromotionProducts(promotionId) {
  try {
    // Then create new ones
    if (selectedItemsFromAllBases.value.length > 0) {
      await editPromotionProducts(promotionId);
    }
  } catch (error) {
    console.error("Update promotion products failed:", error);
  }
}
const fetchData = async () => {
  if (!props.TableName) return;
  try {
    // Load promotion products if editing
    if (
      !props.action ||
      props.action === "view" ||
      (props.action === "update" && props.id)
    ) {
      const response = await formTableService.getById(props.id);
      console.log("=== PROMOTION DATA RESPONSE ===");
      console.log("Promotion ID:", props.id);
      console.log("Response data:", response.data);
      console.log("===============================");

      response.data.createdAt = formatDate(response.data.createdAt);
      response.data.updatedAt = formatDate(response.data.updatedAt);
      Object.assign(formData, response.data);

      // Load promotion products after setting form data
      await loadPromotionProducts(props.id);
    }
  } catch (err) {
    console.error("Get failed:", err);
  }
};
var groupedByBase = new Map();
function getPromotionProductIdFromGrouped(baseId, productItemId) {
  return groupedByBase.get(baseId)?.find((item) => item.id === productItemId)
    ?.promotionProductId;
}

// Load promotion products for editing
async function loadPromotionProducts(promotionId) {
  try {
    const response = await api.get(`/admin/promotionproducts?promotionId=${promotionId}`);
    // Handle direct array response (not wrapped in content)
    const promotionProducts = Array.isArray(response.data)
      ? response.data
      : response.data.content || [];

    if (promotionProducts.length > 0) {
      console.log("First promotion product:", promotionProducts[0]);

      // Group by base product

      for (const promotionProduct of promotionProducts) {
        // Use productItem instead of productItems based on the actual response structure
        const productItem = promotionProduct.productItem;
        const baseProductId = productItem.baseId;

        if (!groupedByBase.has(baseProductId)) {
          groupedByBase.set(baseProductId, []);
        }
        groupedByBase.get(baseProductId).push({
          ...productItem,
          promotionProductId: promotionProduct.id,
          requireQty: promotionProduct.requireQty,
          gift: promotionProduct.gift,
        });
      }

      console.log("Grouped by base:", groupedByBase);

      // Load items for each base product
      for (const [baseProductId, items] of groupedByBase) {
        const baseProduct = dropDownListBaseProduct.value.find(
          (p) => p.id === baseProductId
        );

        console.log("Processing base product:", baseProduct);

        if (baseProduct) {
          // Create set for this base
          const selectedForThisBase = new Set(items.map((item) => item.id));
          allSelectedItems.value.set(baseProductId, selectedForThisBase);

          // Add to selectedItemsFromAllBases
          for (const item of items) {
            // Set gift status
            itemGiftStatus.value.set(item.id, item.gift || false);

            selectedItemsFromAllBases.value.push({
              ...item,
              baseProduct: baseProduct,
              isGift: item.gift || false,
            });
          }

          // Set required quantity for this base product (use the first item's requireQty)
          if (items.length > 0 && items[0].requireQty) {
            baseProductRequiredQuantities.value.set(baseProductId, items[0].requireQty);
          }
        }
      }

      // If there are items loaded, select the first base product by default
      if (selectedItemsFromAllBases.value.length > 0) {
        const firstBaseProduct = selectedItemsFromAllBases.value[0].baseProduct;
        console.log("Auto-selecting first base product:", firstBaseProduct);
        await selectBaseProduct(firstBaseProduct);
      }
    }
  } catch (error) {
    console.error("Load promotion products failed:", error);
  }
}

// Base Product Selection Functions
async function selectBaseProduct(product) {
  selectedProduct.value = product;
  // Don't clear selectedProductItems anymore, just load the items for this base
  await getProductItems(product.id);

  // Update selectedProductItems based on what's already selected for this base
  const selectedForThisBase = allSelectedItems.value.get(product.id) || new Set();
  selectedProductItems.value = Array.from(selectedForThisBase);
}

async function getProductItems(baseProductId) {
  try {
    const response = await api.get(
      "/admin/ProductItems/ByBaseProductId/" + baseProductId
    );
    console.log("=== PRODUCT ITEMS RESPONSE ===");
    console.log("BaseProductId:", baseProductId);
    console.log("Response data:", response.data);
    console.log("==============================");

    // Handle both content wrapper and direct array response
    const items = response.data.content || response.data || [];

    if (items.length > 0) {
      console.log("First product item:", items[0]);

      productItemsList.value = items.map((item) => {
        return {
          ...item,
          name: item.baseProducts?.name || selectedProduct.value?.name,
        };
      });
    } else {
      productItemsList.value = [];
    }
  } catch (error) {
    console.error("Get product items failed:", error);
    productItemsList.value = [];
  }
}

// Product Item Selection Functions
function toggleItemSelection(item) {
  const baseProductId = selectedProduct.value.id;
  const itemId = item.id;

  // Get or create set for this base product
  if (!allSelectedItems.value.has(baseProductId)) {
    allSelectedItems.value.set(baseProductId, new Set());
  }

  const selectedForThisBase = allSelectedItems.value.get(baseProductId);

  if (selectedForThisBase.has(itemId)) {
    // Remove from this base
    selectedForThisBase.delete(itemId);
    // Remove from current view
    const index = selectedProductItems.value.indexOf(itemId);
    if (index > -1) {
      selectedProductItems.value.splice(index, 1);
    }
    // Remove from all selections
    removeFromAllSelections(itemId);
  } else {
    // Add to this base
    selectedForThisBase.add(itemId);
    // Add to current view
    selectedProductItems.value.push(itemId);
    // Add to all selections
    addToAllSelections(item, selectedProduct.value);
  }
}

function addToAllSelections(item, baseProduct) {
  const existingIndex = selectedItemsFromAllBases.value.findIndex(
    (i) => i.id === item.id
  );
  if (existingIndex === -1) {
    selectedItemsFromAllBases.value.push({
      ...item,
      baseProduct: baseProduct,
      isGift: isItemGift(item.id),
    });
  }
}

function removeFromAllSelections(itemId) {
  const index = selectedItemsFromAllBases.value.findIndex((i) => i.id === itemId);
  if (index > -1) {
    selectedItemsFromAllBases.value.splice(index, 1);
  }
  // Remove gift status
  itemGiftStatus.value.delete(itemId);
}

function removeItemFromAllSelections(itemId, baseProductId) {
  // Remove from specific base
  const selectedForThisBase = allSelectedItems.value.get(baseProductId);
  if (selectedForThisBase) {
    selectedForThisBase.delete(itemId);
  }

  // Remove from current view if it's the current base
  if (selectedProduct.value && selectedProduct.value.id === baseProductId) {
    const index = selectedProductItems.value.indexOf(itemId);
    if (index > -1) {
      selectedProductItems.value.splice(index, 1);
    }
  }

  // Remove from all selections
  removeFromAllSelections(itemId);
}

function clearAllSelections() {
  allSelectedItems.value.clear();
  selectedProductItems.value = [];
  selectedItemsFromAllBases.value = [];
  baseProductRequiredQuantities.value.clear();
  itemGiftStatus.value.clear();
}

function isItemSelected(itemId) {
  return selectedProductItems.value.includes(itemId);
}

function getUniqueBaseProductCount() {
  const baseProductIds = new Set();
  selectedItemsFromAllBases.value.forEach((item) => {
    baseProductIds.add(item.baseProduct.id);
  });
  return baseProductIds.size;
}

function getGroupedSelections() {
  const grouped = {};
  selectedItemsFromAllBases.value.forEach((item) => {
    const baseProductId = item.baseProduct.id;
    if (!grouped[baseProductId]) {
      grouped[baseProductId] = {
        baseProduct: item.baseProduct,
        items: [],
        giftCount: 0,
      };
    }
    grouped[baseProductId].items.push(item);
    if (item.isGift) {
      grouped[baseProductId].giftCount++;
    }
  });
  return grouped;
}

// Required Quantity Functions
function getBaseProductRequiredQuantity(baseProductId) {
  return baseProductRequiredQuantities.value.get(baseProductId) || 1;
}

function updateBaseProductRequiredQuantity(baseProductId, quantity) {
  baseProductRequiredQuantities.value.set(baseProductId, parseInt(quantity) || 1);
}

// Gift Status Functions
function isItemGift(itemId) {
  return itemGiftStatus.value.get(itemId) || false;
}

function toggleItemGift(itemId, isGift) {
  // Validation: Prevent all items from being gifts
  if (isGift && formData.type !== "DISCOUNT") {
    const nonGiftItems = selectedItemsFromAllBases.value.filter(
      (item) => item.id !== itemId && !isItemGift(item.id)
    );

    if (nonGiftItems.length === 0) {
      alert(
        "Không thể đặt tất cả sản phẩm làm quà tặng. Phải có ít nhất một sản phẩm không phải là quà tặng."
      );
      return;
    }
  }

  itemGiftStatus.value.set(itemId, isGift);

  // Update the item in selectedItemsFromAllBases
  const item = selectedItemsFromAllBases.value.find((i) => i.id === itemId);
  if (item) {
    item.isGift = isGift;
  }
}

function getItemById(itemId) {
  return productItemsList.value.find((item) => item.id === itemId);
}

function getGiftItems() {
  return selectedItemsFromAllBases.value.filter((item) => item.isGift);
}

onMounted(async () => {
  try {
    // Load base products dropdown first
    dropDownListBaseProduct.value = await dropDown("BaseProducts");
    console.log("=== BASE PRODUCTS DROPDOWN ===");
    console.log("Base products loaded:", dropDownListBaseProduct.value?.length, "items");
    if (dropDownListBaseProduct.value?.length > 0) {
      console.log("First base product:", dropDownListBaseProduct.value[0]);
    }
    console.log("==============================");

    // Load categories dropdown
    const responseCategories = await categoriesService.getAll(0, 1000);
    console.log("=== CATEGORIES RESPONSE ===");
    console.log("Categories response:", responseCategories.data);
    console.log("===========================");

    // Handle different response structures
    const categoriesData =
      responseCategories.data?.content || responseCategories.data || [];
    categoriesDropDownList.value = categoriesData.map((category) => {
      return {
        id: category.id,
        name: category.name,
      };
    });

    console.log("Categories loaded:", categoriesDropDownList.value?.length, "items");

    // Then fetch promotion data (this will also load promotion products)
    await fetchData();
  } catch (error) {
    console.error("Error loading data:", error);
  }
});
watch(() => props.id, fetchData);
watch(
  () => formData.type,
  (newVal) => {
    if (newVal === "COMBO") {
      formData.discountType = "";
      formData.discountValue = "";
    } else if (newVal === "DISCOUNT") {
      formData.comboPrice = "";
    }
  }
);
</script>
