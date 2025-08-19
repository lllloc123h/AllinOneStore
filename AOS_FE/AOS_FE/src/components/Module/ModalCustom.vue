<!-- Modal chọn bản phác thảo cho sản phẩm custom -->
<template>
  <div v-if="showDraftModal" class="custom-modal-overlay">
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
          <div>
            <div class="fw-bold">{{ product.name }}</div>
            <div class="text-muted small">{{ product.sku }}</div>
          </div>
        </div>
        <div class="draft-list">
          <template v-if="product.drafts && product.drafts.length">
            <div
              v-for="draft in product.drafts"
              :key="draft.id"
              class="draft-item"
              :class="{ selected: selectedDrafts[product.id] === draft.id }"
              @click="selectDraft(product.id, draft.id)"
            >
              <img :src="draft.imageUrl" :alt="draft.designName" />
              <div class="draft-info">
                <div class="draft-name">{{ draft.designName }}</div>
                <div class="draft-date">{{ formatDate(draft.createdAt) }}</div>
                <div class="draft-product-desc text-muted small">
                  {{ draft.productItems?.description }}
                </div>
              </div>
            </div>
          </template>
          <template v-else>
            <div class="no-draft">
              <i class="bi bi-exclamation-circle me-2"></i>
              Chưa có bản phác thảo nào cho sản phẩm này
            </div>
          </template>
        </div>
      </div>
      <div class="d-flex justify-content-end gap-2 mt-4">
        <button class="btn btn-primary" :disabled="!isAllSelected" @click="confirmDrafts">
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
import { ref, computed, onMounted, watch } from "vue";
import api from "../../Configs/api";

// Truyền vào từ Cart.vue
const props = defineProps({
  showDraftModal: { type: Boolean, default: false },
  customProductIds: Array, // [productItemId, ...]
});

// Trạng thái modal
const emit = defineEmits(["close", "select"]);
const customProducts = ref([]); // [{id, name, image, sku, drafts: [...] }]
const selectedDrafts = ref({}); // { [productId]: draftId }

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
// Khi mở modal, fetch danh sách phác thảo cho từng sản phẩm custom
async function fetchDrafts() {
  customProducts.value = [];
  selectedDrafts.value = {};
  if (!props.customProductIds || props.customProductIds.length === 0) return;
  try {
    const response = await api.get(
      `/customs/productItems?productItemIds=${props.customProductIds.join(",")}`
    );
    console.log("dữ liệu modal custom", response.data);

    const drafts = response.data || [];
    const grouped = {};

    drafts.forEach((draft) => {
      const pid = draft.productItems?.id;
      if (!pid) return;
      if (!grouped[pid]) {
        grouped[pid] = {
          id: pid,
          name: draft.productItems.name,
          image: draft.productItems.imageUrl,
          sku: draft.productItems.sku,
          drafts: [],
        };
      }
      grouped[pid].drafts.push(draft);
    });

    customProducts.value = Object.values(grouped);
  } catch (error) {
    console.error("Lỗi khi lấy danh sách phác thảo:", error);
  }
}
// onMounted(async () => {
//   await fetchDrafts();
// });

// Chọn phác thảo cho từng sản phẩm
function selectDraft(productId, draftId) {
  selectedDrafts.value[productId] = draftId;
}

// Đã chọn đủ phác thảo cho tất cả sản phẩm custom?
const isAllSelected = computed(
  () =>
    customProducts.value.length > 0 &&
    customProducts.value.every((p) => selectedDrafts.value[p.id])
);

// Xác nhận chọn
function confirmDrafts() {
  // Trả về mapping { productItemId: draftId }
  emit("select", { ...selectedDrafts.value });
  emit("close");
}

// Đóng modal
function closeDraftModal() {
  emit("close");
}

function formatDate(date) {
  return new Date(date).toLocaleDateString("vi-VN");
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
  box-shadow: 0 8px 32px rgba(102, 126, 234, 0.18);
  padding: 32px 28px;
  min-width: 600px;
  max-width: 90vw;
  width: 100%;
  position: relative;
  max-height: 90vh;
  overflow-y: auto;
}

.modal-title {
  font-size: 1.4rem;
  font-weight: 700;
  color: #764ba2;
  text-align: center;
  margin-bottom: 18px;
}

/* Container cho tất cả custom products - hiển thị ngang */
.custom-products-container {
  display: flex;
  flex-direction: row;
  gap: 24px;
  overflow-x: auto;
  padding-bottom: 16px;
  margin-bottom: 24px;
}

.custom-product-section {
  flex: 0 0 auto;
  min-width: 300px;
  max-width: 350px;
  border: 1px solid #eee;
  border-radius: 12px;
  padding: 18px;
  background: linear-gradient(90deg, #f8f9fa 60%, #f4f0ff 100%);
  box-shadow: 0 2px 8px rgba(102, 126, 234, 0.06);
}

/* Product header */
.product-header {
  display: flex;
  align-items: center;
  margin-bottom: 16px;
  padding-bottom: 12px;
  border-bottom: 1px solid #e9ecef;
}

.custom-product-img {
  width: 60px;
  height: 75px;
  object-fit: cover;
  border-radius: 10px;
  box-shadow: 0 2px 8px rgba(102, 126, 234, 0.1);
  border: 2px solid #e0e0e0;
  margin-right: 12px;
}

.product-info {
  flex: 1;
}

.product-name {
  font-weight: 600;
  font-size: 1rem;
  color: #2c3e50;
  margin-bottom: 4px;
}

.product-sku {
  font-size: 0.85rem;
  color: #7f8c8d;
}

/* Draft list - giữ nguyên hiển thị ngang cho drafts */
.draft-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
  max-height: 300px;
  overflow-y: auto;
}

.draft-item {
  cursor: pointer;
  border: 2px solid #eee;
  border-radius: 12px;
  padding: 12px;
  background: #f8f9fa;
  transition: border-color 0.2s, box-shadow 0.2s, background 0.2s;
  display: flex;
  align-items: center;
  gap: 12px;
}

.draft-item.selected {
  border-color: #764ba2;
  background: linear-gradient(135deg, #f4f0ff 70%, #e0c3fc 100%);
  box-shadow: 0 4px 18px rgba(118, 75, 162, 0.15);
}

.draft-item img {
  width: 60px;
  height: 75px;
  object-fit: cover;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(102, 126, 234, 0.1);
  border: 1.5px solid #d1c4e9;
  flex-shrink: 0;
}

.draft-info {
  flex: 1;
  font-size: 0.9rem;
}

.draft-name {
  font-weight: 600;
  color: #764ba2;
  margin-bottom: 4px;
  font-size: 1rem;
}

.draft-date {
  font-size: 0.85rem;
  color: #7f8c8d;
  margin-bottom: 2px;
}

.draft-product-desc {
  font-size: 0.85rem;
  color: #2c3e50;
}

.no-draft {
  color: #b71c1c;
  background: #fff3f3;
  border-radius: 8px;
  padding: 16px;
  font-size: 0.95rem;
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
  box-shadow: 0 1px 4px rgba(183, 28, 28, 0.08);
}

.btn-primary {
  background: linear-gradient(90deg, #764ba2 60%, #667eea 100%);
  border: none;
  color: #fff;
  font-weight: 600;
  box-shadow: 0 2px 8px rgba(102, 126, 234, 0.1);
  transition: background 0.2s;
}

.btn-primary:disabled {
  background: #eee;
  color: #aaa;
  box-shadow: none;
}

.btn-outline-secondary {
  border: 1.5px solid #764ba2;
  color: #764ba2;
  background: #fff;
  font-weight: 500;
  transition: background 0.2s, color 0.2s;
}

.btn-outline-secondary:hover {
  background: #f4f0ff;
  color: #667eea;
}

/* Responsive cho màn hình nhỏ */
@media (max-width: 768px) {
  .custom-products-container {
    flex-direction: column;
  }

  .custom-product-section {
    min-width: auto;
    max-width: none;
  }
}
</style>
