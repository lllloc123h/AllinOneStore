<!-- Modal chọn bản phác thảo cho sản phẩm custom -->
<template>
  <Loading v-if="loading" />
  <div v-if="showDraftModal && !loading" class="custom-modal-overlay">
    <div class="custom-modal-content">
      <h4 class="modal-title mb-4">
        <i class="bi bi-palette me-2"></i>
        Chọn bản phác thảo cho sản phẩm tùy chỉnh
      </h4>
      <div
        v-for="product in customProducts"
        :key="product.id"
        class="custom-product-section mb-4"
      >
        <div class="d-flex align-items-center mb-2">
          <img :src="product.image" class="custom-product-img me-3" />
          <div class="flex-grow-1">
            <div class="fw-bold">{{ product.name }}</div>
            <div class="text-muted small">{{ product.sku }}</div>
            <div class="d-flex align-items-center gap-3 mt-2">
              <div>
                Số lượng tối đa: <strong>{{ product.maxQuantity }}</strong>
              </div>
              <div class="quantity-status" :class="getQuantityStatusClass(product.id)">
                Đã chọn: {{ getTotalQuantity(product.id) }}/{{ product.maxQuantity }}
              </div>
              <span class="small"
                ><i class="bi bi-info-circle"></i> Số lượng tối đa là số lượng sản phẩm
                này có trong giỏ hàng của bạn</span
              >
            </div>
          </div>
        </div>
        <div class="draft-list">
          <template v-if="product.drafts && product.drafts.length">
            <div
              v-for="draft in product.drafts"
              :key="draft.id"
              class="draft-item"
              :class="{ selected: selectedDrafts[product.id]?.[draft.id] > 0 }"
            >
              <div class="draft-checkbox">
                <input
                  type="checkbox"
                  :id="`draft-${product.id}-${draft.id}`"
                  :checked="selectedDrafts[product.id]?.[draft.id] > 0"
                  @change="toggleDraft(product.id, draft.id)"
                  class="form-check-input"
                />
              </div>
              <img
                :src="draft.imageUrl"
                :alt="draft.designName"
                style="width: 160px; aspect-ratio: 4/5"
              />
              <div class="draft-info flex-grow-1">
                <div class="draft-name">{{ draft.designName }}</div>
                <div class="draft-date">Ngày tạo: {{ formatDate(draft.createdAt) }}</div>
                <!-- <div class="draft-product-desc text-muted small">
                  {{ draft.productItems?.description }}
                </div> -->
              </div>
              <div
                class="quantity-controls"
                v-if="selectedDrafts[product.id]?.[draft.id] > 0"
              >
                <button
                  type="button"
                  class="btn btn-sm btn-outline-secondary"
                  :disabled="selectedDrafts[product.id]?.[draft.id] <= 1"
                  @click="decreaseQuantity(product.id, draft.id)"
                >
                  <i class="bi bi-dash"></i>
                </button>
                <span class="quantity-display">
                  {{ selectedDrafts[product.id]?.[draft.id] || 0 }}
                </span>
                <button
                  type="button"
                  class="btn btn-sm btn-outline-secondary"
                  :disabled="getTotalQuantity(product.id) >= product.maxQuantity"
                  @click="increaseQuantity(product.id, draft.id)"
                >
                  <i class="bi bi-plus"></i>
                </button>
              </div>
            </div>
          </template>
          <template v-else>
            <div class="no-draft">
              <i class="bi bi-exclamation-circle me-2"></i>
              Chưa có bản phác thảo nào cho sản phẩm này
              <a
                class="btn btn-sm btn-success ms-3"
                :href="`/Customizer/create/${product.id}`"
                target="_blank"
              >
                <i class="bi bi-plus-lg me-1"></i> Tạo mới
              </a>
            </div>
          </template>
        </div>
      </div>
      <div class="d-flex justify-content-end gap-2 mt-4">
        <button
          class="btn btn-primary"
          :disabled="!isAllQuantityValid"
          @click="confirmDrafts"
        >
          <i class="bi bi-check-circle me-2"></i>
          Xác nhận
        </button>
        <button class="btn btn-outline-secondary" @click="closeDraftModal">
          <i class="bi bi-x-lg me-1"></i>
          Đóng
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, watch } from "vue";
import api from "../../Configs/api";
import Loading from "./Loading.vue";
const loading = ref(false);
// Props và emits giữ nguyên
const props = defineProps({
  showDraftModal: { type: Boolean, default: false },
  customProductIds: Array,
});

const emit = defineEmits(["close", "select"]);
const customProducts = ref([]);
const selectedDrafts = ref({});

watch(
  () => props.showDraftModal,
  (newVal) => {
    if (newVal) {
      fetchDrafts();
    } else {
      customProducts.value = [];
      selectedDrafts.value = {};
    }
  },
  { immediate: true }
);

async function fetchDrafts() {
  customProducts.value = [];
  selectedDrafts.value = {};
  if (!props.customProductIds || props.customProductIds.length === 0) return;
  loading.value = true;
  try {
    const itemIds = props.customProductIds.map((item) => item.productItemId);
    const response = await api.get(
      `/customs/productItems?productItemIds=${itemIds.join(",")}`
    );
    console.log("Dữ liệu response trong modal:", response.data);

    const drafts = response.data || [];
    const grouped = [];

    drafts.forEach((draft) => {
      const pid = draft.productItems?.id;
      if (!pid) return;
      // if (!grouped[pid]) {
      //   grouped[pid] = {
      //     id: pid,
      //     name: draft.productItems.name,
      //     image: draft.productItems.imageUrl,
      //     sku: draft.productItems.sku,
      //     maxQuantity:
      //       props.customProductIds.find((item) => item.productItemId === pid)
      //         ?.maxQuantity || 0,
      //     drafts: [],
      //   };
      // }
      // if (draft.id) grouped[pid].drafts.push(draft);
      if (!grouped.find((item) => item.id === pid)) {
        grouped.push({
          id: pid,
          name: draft.productItems.name,
          image: draft.productItems.imageUrl,
          sku: draft.productItems.sku,
          maxQuantity:
            props.customProductIds.find((item) => item.productItemId === pid)
              ?.maxQuantity || 0,
          drafts: [],
        });
      }
      if (draft.id) grouped.find((item) => item.id === pid).drafts.push(draft);
    });

    customProducts.value = Object.values(grouped);
    console.log("dữ liệu customProducts tải lên :", customProducts.value);

    customProducts.value.forEach((product) => {
      selectedDrafts.value[product.id] = {};
    });
  } catch (error) {
    console.error("Lỗi khi lấy danh sách phác thảo:", error);
  } finally {
    loading.value = false;
  }
}

function toggleDraft(productId, draftId) {
  if (!selectedDrafts.value[productId]) {
    selectedDrafts.value[productId] = {};
  }

  if (selectedDrafts.value[productId][draftId] > 0) {
    selectedDrafts.value[productId][draftId] = 0;
  } else {
    const currentTotal = getTotalQuantity(productId);
    const maxQuantity =
      customProducts.value.find((p) => p.id === productId)?.maxQuantity || 0;

    if (currentTotal < maxQuantity) {
      selectedDrafts.value[productId][draftId] = 1;
    }
  }
}

function increaseQuantity(productId, draftId) {
  if (!selectedDrafts.value[productId]) {
    selectedDrafts.value[productId] = {};
  }

  const currentTotal = getTotalQuantity(productId);
  const maxQuantity =
    customProducts.value.find((p) => p.id === productId)?.maxQuantity || 0;

  if (currentTotal < maxQuantity) {
    selectedDrafts.value[productId][draftId] =
      (selectedDrafts.value[productId][draftId] || 0) + 1;
  }
}

function decreaseQuantity(productId, draftId) {
  if (!selectedDrafts.value[productId] || !selectedDrafts.value[productId][draftId])
    return;

  if (selectedDrafts.value[productId][draftId] > 1) {
    selectedDrafts.value[productId][draftId]--;
  } else {
    selectedDrafts.value[productId][draftId] = 0;
  }
}

function getTotalQuantity(productId) {
  if (!selectedDrafts.value[productId]) return 0;
  return Object.values(selectedDrafts.value[productId]).reduce(
    (sum, qty) => sum + (qty || 0),
    0
  );
}

function getQuantityStatusClass(productId) {
  const total = getTotalQuantity(productId);
  const maxQuantity =
    customProducts.value.find((p) => p.id === productId)?.maxQuantity || 0;

  if (total === maxQuantity && maxQuantity > 0) {
    return "quantity-complete";
  } else if (total > 0) {
    return "quantity-partial";
  }
  return "quantity-empty";
}

const isAllQuantityValid = computed(() => {
  return (
    customProducts.value.length > 0 &&
    customProducts.value.every((product) => {
      const total = getTotalQuantity(product.id);
      return total === product.maxQuantity;
    })
  );
});

function confirmDrafts() {
  const result = [];
  Object.keys(selectedDrafts.value).forEach((productId) => {
    const drafts = selectedDrafts.value[productId];
    const validDrafts = [];
    Object.keys(drafts).forEach((draftId) => {
      if (drafts[draftId] > 0) {
        const tempDraft = customProducts.value
          .find((p) => p.id == productId)
          .drafts.find((d) => d.id == draftId);
        validDrafts.push({
          customId: draftId,
          quantity: drafts[draftId],
          imageUrl: tempDraft.imageUrl,
          name: tempDraft.designName,
        });
      }
    });
    if (validDrafts.length > 0) {
      console.log("Xác nhận phác thảo cho sản phẩm:", productId);
      const productInfor = customProducts.value.find((p) => p.id == productId);
      console.log("Danh sách phác thảo hợp lệ:", validDrafts);
      result.push({
        id: productInfor.id,
        name: productInfor.name,
        image: productInfor.image,
        sku: productInfor.sku,
        drafts: validDrafts,
      });
    }
  });
  console.log("Kết quả xác nhận phác thảo:", result);
  emit("select", result);
  emit("close");
}

function closeDraftModal() {
  emit("close");
}

function formatDate(date) {
  const d = new Date(date);
  return d.toLocaleString("vi-VN", {
    hour12: false,
    year: "numeric",
    month: "2-digit",
    day: "2-digit",
    hour: "2-digit",
    minute: "2-digit",
    second: "2-digit",
  });
}
</script>

<style scoped>
.custom-modal-overlay {
  position: fixed;
  inset: 0;
  background: rgba(44, 62, 80, 0.55);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 2000;
}

.custom-modal-content {
  background: #fff;
  border-radius: 18px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.15);
  padding: 32px 28px;
  min-width: 700px;
  max-width: 50vw;
  width: 100%;
  position: relative;
  max-height: 90vh;
  overflow-y: auto;
}

.modal-title {
  font-size: 1.4rem;
  font-weight: 700;
  color: #0d6efd; /* Màu primary của Cart */
  text-align: center;
  margin-bottom: 18px;
}

.custom-product-section {
  border: 1px solid #dee2e6;
  border-radius: 12px;
  padding: 18px;
  background: #f8f9fa; /* Màu background nhẹ như Cart */
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
}

.custom-product-img {
  width: 100px;
  min-height: 125px;
  aspect-ratio: 4/5;
  height: auto;
  object-fit: cover;
  border-radius: 10px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  border: 2px solid #dee2e6;
  margin-right: 12px;
}

.quantity-status {
  font-size: 0.9rem;
  font-weight: 600;
  padding: 4px 12px;
  border-radius: 20px;
  transition: all 0.2s;
}

.quantity-empty {
  background: #f8d7da; /* Màu đỏ nhẹ */
  color: #721c24;
  border: 1px solid #f5c2c7;
}

.quantity-partial {
  background: #fff3cd; /* Màu vàng nhẹ */
  color: #664d03;
  border: 1px solid #ffecb5;
}

.quantity-complete {
  background: #d1e7dd; /* Màu xanh lá nhẹ */
  color: #0f5132;
  border: 1px solid #badbcc;
}

.draft-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
  max-height: 400px;
  overflow-y: auto;
}

.draft-item {
  border: 2px solid #dee2e6;
  border-radius: 12px;
  padding: 12px;
  background: #fff;
  transition: border-color 0.2s, box-shadow 0.2s, background 0.2s;
  display: flex;
  align-items: center;
  gap: 12px;
}

.draft-item.selected {
  border-color: #0d6efd; /* Màu primary như Cart */
  background: #e7f3ff; /* Màu xanh dương nhẹ */
  box-shadow: 0 4px 18px rgba(13, 110, 253, 0.15);
}

.draft-checkbox {
  flex-shrink: 0;
}

.draft-checkbox .form-check-input {
  width: 18px;
  height: 18px;
  margin-top: 0;
  border-color: #dee2e6;
}

.draft-checkbox .form-check-input:checked {
  background-color: #0d6efd;
  border-color: #0d6efd;
}

.draft-item img {
  width: 145px;
  aspect-ratio: 4/5;
  object-fit: cover;
  border-radius: 10px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  border: 1.5px solid #dee2e6;
  height: auto;
  max-height: 300px;
  flex-shrink: 0;
}

.draft-info {
  flex: 1;
  font-size: 0.9rem;
}

.draft-name {
  font-weight: 600;
  color: #212529; /* Màu text chính */
  margin-bottom: 4px;
  font-size: 1rem;
}

.draft-date {
  font-size: 0.85rem;
  color: #6c757d; /* Màu text muted */
  margin-bottom: 2px;
}

.draft-product-desc {
  font-size: 0.85rem;
  color: #6c757d;
}

.quantity-controls {
  display: flex;
  align-items: center;
  gap: 8px;
  flex-shrink: 0;
}

.quantity-controls .btn {
  width: 32px;
  height: 32px;
  padding: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 6px;
  transition: all 0.2s;
  border-color: #6c757d;
  color: #6c757d;
}

.quantity-controls .btn:hover:not(:disabled) {
  background: #0d6efd;
  border-color: #0d6efd;
  color: white;
}

.quantity-display {
  min-width: 30px;
  text-align: center;
  font-weight: 600;
  color: #0d6efd; /* Màu primary */
  font-size: 1.1rem;
}

.no-draft {
  color: #dc3545; /* Màu danger */
  background: #f8d7da;
  border: 1px solid #f5c2c7;
  border-radius: 8px;
  padding: 16px;
  font-size: 0.95rem;
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
}

.btn-primary {
  background: #0d6efd; /* Màu primary của Cart */
  border: none;
  color: #fff;
  font-weight: 600;
  box-shadow: 0 2px 8px rgba(13, 110, 253, 0.25);
  transition: background 0.2s;
}

.btn-primary:hover:not(:disabled) {
  background: #0b5ed7;
}

.btn-primary:disabled {
  background: #6c757d;
  color: #fff;
  box-shadow: none;
  opacity: 0.65;
}

.btn-outline-secondary {
  border: 1.5px solid #6c757d;
  color: #6c757d;
  background: #fff;
  font-weight: 500;
  transition: all 0.2s;
}

.btn-outline-secondary:hover {
  background: #6c757d;
  border-color: #6c757d;
  color: #fff;
}

/* Responsive */
@media (max-width: 768px) {
  .custom-modal-content {
    min-width: 90vw;
    padding: 20px 16px;
  }

  .draft-item {
    flex-direction: column;
    align-items: flex-start;
    gap: 8px;
  }

  .quantity-controls {
    align-self: flex-end;
  }
}
</style>
