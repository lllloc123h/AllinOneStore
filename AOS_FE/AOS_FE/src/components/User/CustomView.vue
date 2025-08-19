<template>
  <div class="custom-drafts-page">
    <!-- Hero Section -->
    <div class="hero-section">
      <div class="container">
        <div class="hero-content">
          <h1 class="hero-title">
            <i class="bi bi-palette me-3"></i>
            Bản phác thảo của tôi
          </h1>
          <p class="hero-subtitle">Quản lý và chỉnh sửa các thiết kế tùy chỉnh của bạn</p>
        </div>
      </div>
    </div>

    <!-- Main Content -->
    <div class="main-content">
      <div class="container">
        <!-- Filter & Search -->
        <div class="filter-section">
          <div class="row g-3 mb-4">
            <div class="col-md-6">
              <div class="search-box">
                <i class="bi bi-search search-icon"></i>
                <input
                  type="text"
                  class="form-control search-input"
                  placeholder="Tìm kiếm theo tên thiết kế..."
                  v-model="searchQuery"
                  @input="filterDrafts"
                />
              </div>
            </div>
            <div class="col-md-3">
              <select class="form-select" v-model="sortBy" @change="sortDrafts">
                <option value="newest">Mới nhất</option>
                <option value="oldest">Cũ nhất</option>
                <option value="name">Tên A-Z</option>
                <option value="name-desc">Tên Z-A</option>
              </select>
            </div>
          </div>
        </div>

        <!-- Loading State -->
        <div v-if="loading" class="loading-section">
          <div class="text-center py-5">
            <div class="spinner-border text-primary mb-3" role="status">
              <span class="visually-hidden">Đang tải...</span>
            </div>
            <p class="text-muted">Đang tải danh sách bản phác thảo...</p>
          </div>
        </div>

        <!-- Empty State -->
        <div v-else-if="filteredDrafts.length === 0 && !loading" class="empty-state">
          <div class="empty-content">
            <i class="bi bi-palette empty-icon"></i>
            <h3 class="empty-title">Chưa có bản phác thảo nào</h3>
            <p class="empty-description">
              Bắt đầu tạo thiết kế đầu tiên của bạn để thấy chúng hiển thị ở đây
            </p>
            <button class="btn btn-primary btn-lg" @click="createNewDraft">
              <i class="bi bi-plus-circle me-2"></i>
              Tạo thiết kế đầu tiên
            </button>
          </div>
        </div>

        <!-- Drafts Grid -->
        <div v-else class="drafts-grid">
          <div class="row g-4">
            <div
              v-for="draft in filteredDrafts"
              :key="draft.id"
              class="col-lg-3 col-md-4 col-sm-6"
            >
              <div class="draft-card">
                <!-- Draft Image -->
                <div class="draft-image-wrapper">
                  <img
                    :src="draft.imageUrl || '/default-design.png'"
                    :alt="draft.designName"
                    class="draft-image"
                    @error="handleImageError"
                  />
                  <div class="draft-overlay">
                    <div class="draft-actions">
                      <button
                        class="action-btn preview-btn"
                        @click="previewDraft(draft)"
                        title="Xem trước"
                      >
                        <i class="bi bi-eye"></i>
                      </button>
                      <button
                        class="action-btn edit-btn"
                        @click="editDraft(draft)"
                        title="Chỉnh sửa"
                      >
                        <i class="bi bi-pencil"></i>
                      </button>
                      <button
                        class="action-btn delete-btn"
                        @click="confirmDelete(draft)"
                        title="Xóa"
                      >
                        <i class="bi bi-trash3"></i>
                      </button>
                    </div>
                  </div>
                </div>

                <!-- Draft Info -->
                <div class="draft-info">
                  <h5 class="draft-name">
                    {{ draft.designName || `Thiết kế #${draft.id}` }}
                  </h5>
                  <div class="draft-meta">
                    <div class="meta-item">
                      <i class="bi bi-calendar3 me-1"></i>
                      <span>{{ formatDate(draft.createdAt) }}</span>
                    </div>
                    <div class="meta-item" v-if="draft.updatedAt !== draft.createdAt">
                      <i class="bi bi-pencil-square me-1"></i>
                      <span>{{ formatDate(draft.updatedAt) }}</span>
                    </div>
                  </div>

                  <!-- Product Info -->
                  <div v-if="draft.productItems" class="product-info">
                    <div class="product-details">
                      <div class="product-name">
                        <i class="bi bi-box me-1"></i>
                        {{
                          draft.productItems.baseProducts?.name ||
                          draft.productItems.name ||
                          "Sản phẩm"
                        }}
                      </div>

                      <div class="product-meta">
                        <div
                          v-if="draft.productItems.baseProducts?.categories?.name"
                          class="meta-detail"
                        >
                          <span class="meta-label">Danh mục:</span>
                          <span class="meta-value category">{{
                            draft.productItems.baseProducts.categories.name
                          }}</span>
                        </div>

                        <div class="meta-detail">
                          <span class="meta-label">Trạng thái:</span>
                          <span
                            class="meta-value status"
                            :class="{
                              'status-active': draft.productItems.active === true,
                              'status-inactive': draft.productItems.active === false,
                            }"
                          >
                            <i
                              :class="{
                                'bi bi-check-circle-fill':
                                  draft.productItems.active === true,
                                'bi bi-x-circle-fill':
                                  draft.productItems.active === false,
                              }"
                            ></i>
                            {{
                              draft.productItems.active
                                ? "Đang hoạt động"
                                : "Ngừng hoạt động"
                            }}
                          </span>
                        </div>

                        <div v-if="draft.productItems.material" class="meta-detail">
                          <span class="meta-label">Chất liệu:</span>
                          <span class="meta-value">{{
                            draft.productItems.material
                          }}</span>
                        </div>

                        <div v-if="draft.productItems.price" class="meta-detail">
                          <span class="meta-label">Giá:</span>
                          <span class="meta-value price">{{
                            formatPrice(draft.productItems.price)
                          }}</span>
                        </div>

                        <div v-if="draft.productItems.sku" class="meta-detail">
                          <span class="meta-label">SKU:</span>
                          <span class="meta-value sku">{{ draft.productItems.sku }}</span>
                        </div>

                        <div
                          v-if="draft.productItems.baseProducts?.rating"
                          class="meta-detail"
                        >
                          <span class="meta-label">Đánh giá:</span>
                          <span class="meta-value rating">
                            <span
                              v-for="star in 5"
                              :key="star"
                              class="star"
                              :class="{
                                filled: star <= draft.productItems.baseProducts.rating,
                              }"
                            >
                              ★
                            </span>
                            ({{ draft.productItems.baseProducts.rating }}/5)
                          </span>
                        </div>

                        <div v-if="draft.productItems.description" class="meta-detail">
                          <span class="meta-label">Mô tả:</span>
                          <span
                            class="meta-value description"
                            :title="draft.productItems.description"
                            >{{ draft.productItems.description }}</span
                          >
                        </div>
                      </div>
                    </div>

                    <!-- Order Status -->
                    <div v-if="draft.orderItem" class="product-badge ordered">
                      <i class="bi bi-check-circle me-1"></i>
                      Đã đặt hàng
                    </div>
                  </div>

                  <!-- Fallback Product Info -->
                  <div v-else-if="draft.orderItem" class="product-info">
                    <div class="product-badge ordered">
                      <i class="bi bi-check-circle me-1"></i>
                      Đã đặt hàng
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- Pagination -->
        <div v-if="totalPages > 1" class="pagination-section">
          <nav aria-label="Phân trang bản phác thảo">
            <ul class="pagination justify-content-center">
              <li class="page-item" :class="{ disabled: currentPage === 1 }">
                <button class="page-link" @click="changePage(currentPage - 1)">
                  <i class="bi bi-chevron-left"></i>
                </button>
              </li>
              <li
                v-for="page in visiblePages"
                :key="page"
                class="page-item"
                :class="{ active: page === currentPage }"
              >
                <button class="page-link" @click="changePage(page)">{{ page }}</button>
              </li>
              <li class="page-item" :class="{ disabled: currentPage === totalPages }">
                <button class="page-link" @click="changePage(currentPage + 1)">
                  <i class="bi bi-chevron-right"></i>
                </button>
              </li>
            </ul>
          </nav>
        </div>
      </div>
    </div>

    <!-- Delete Confirmation Modal -->
    <div
      v-if="showDeleteModal"
      class="modal fade show d-block"
      tabindex="-1"
      style="background: rgba(0, 0, 0, 0.5)"
    >
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">
              <i class="bi bi-exclamation-triangle text-warning me-2"></i>
              Xác nhận xóa
            </h5>
            <button
              type="button"
              class="btn-close"
              @click="showDeleteModal = false"
            ></button>
          </div>
          <div class="modal-body">
            <p>
              Bạn có chắc muốn xóa bản phác thảo
              <strong>"{{ selectedDraft?.designName || "Thiết kế này" }}"</strong>?
            </p>
            <p class="text-muted small">Hành động này không thể hoàn tác.</p>
          </div>
          <div class="modal-footer">
            <button
              type="button"
              class="btn btn-secondary"
              @click="showDeleteModal = false"
            >
              Hủy
            </button>
            <button type="button" class="btn btn-danger" @click="deleteDraft">
              <i class="bi bi-trash3 me-1"></i>
              Xóa
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- Preview Modal -->
    <div
      v-if="showPreviewModal"
      class="modal fade show d-block"
      tabindex="-1"
      style="background: rgba(0, 0, 0, 0.8)"
    >
      <div class="modal-dialog modal-lg modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">
              <i class="bi bi-eye me-2"></i>
              {{ selectedDraft?.designName || "Xem trước thiết kế" }}
            </h5>
            <button
              type="button"
              class="btn-close"
              @click="showPreviewModal = false"
            ></button>
          </div>
          <div class="modal-body text-center">
            <img
              :src="selectedDraft?.imageUrl || '/default-design.png'"
              :alt="selectedDraft?.designName"
              class="img-fluid rounded"
              style="max-height: 500px"
            />
          </div>
          <div class="modal-footer">
            <button
              type="button"
              class="btn btn-secondary"
              @click="showPreviewModal = false"
            >
              Đóng
            </button>
            <button
              type="button"
              class="btn btn-primary"
              @click="editDraft(selectedDraft)"
            >
              <i class="bi bi-pencil me-1"></i>
              Chỉnh sửa
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import { useRouter } from "vue-router";
import api from "../../Configs/api";

const router = useRouter();

// Data
const drafts = ref([]);
const filteredDrafts = ref([]);
const loading = ref(false);
const searchQuery = ref("");
const sortBy = ref("newest");
const currentPage = ref(1);
const itemsPerPage = ref(12);
const showDeleteModal = ref(false);
const showPreviewModal = ref(false);
const selectedDraft = ref(null);

// Computed
const totalPages = computed(() =>
  Math.ceil(filteredDrafts.value.length / itemsPerPage.value)
);

const visiblePages = computed(() => {
  const pages = [];
  const start = Math.max(1, currentPage.value - 2);
  const end = Math.min(totalPages.value, currentPage.value + 2);

  for (let i = start; i <= end; i++) {
    pages.push(i);
  }
  return pages;
});

const paginatedDrafts = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage.value;
  const end = start + itemsPerPage.value;
  return filteredDrafts.value.slice(start, end);
});

// Methods
const fetchDrafts = async () => {
  loading.value = true;
  try {
    const response = await api.get("/customs/email");
    drafts.value = response.data || [];
    filteredDrafts.value = [...drafts.value];
    sortDrafts();
    console.log("✅ Đã tải danh sách bản phác thảo:", drafts.value);
  } catch (error) {
    console.error("❌ Lỗi khi tải bản phác thảo:", error);
    drafts.value = [];
    filteredDrafts.value = [];
  } finally {
    loading.value = false;
  }
};

const filterDrafts = () => {
  if (!searchQuery.value.trim()) {
    filteredDrafts.value = [...drafts.value];
  } else {
    const query = searchQuery.value.toLowerCase();
    filteredDrafts.value = drafts.value.filter((draft) =>
      (draft.designName || "").toLowerCase().includes(query)
    );
  }
  currentPage.value = 1;
  sortDrafts();
};

const sortDrafts = () => {
  filteredDrafts.value.sort((a, b) => {
    switch (sortBy.value) {
      case "newest":
        return new Date(b.createdAt) - new Date(a.createdAt);
      case "oldest":
        return new Date(a.createdAt) - new Date(b.createdAt);
      case "name":
        return (a.designName || "").localeCompare(b.designName || "");
      case "name-desc":
        return (b.designName || "").localeCompare(a.designName || "");
      default:
        return 0;
    }
  });
};

const formatDate = (dateString) => {
  if (!dateString) return "";
  const date = new Date(dateString);
  return date.toLocaleDateString("vi-VN", {
    year: "numeric",
    month: "short",
    day: "numeric",
    hour: "2-digit",
    minute: "2-digit",
  });
};

const formatPrice = (price) => {
  if (!price) return "";
  return new Intl.NumberFormat("vi-VN", {
    style: "currency",
    currency: "VND",
  }).format(price);
};

const handleImageError = (event) => {
  event.target.src = "/default-design.png";
};

const createNewDraft = () => {
  // Navigate to customizer create page
  router.push({ name: "CustomizerCreate" });
};

const previewDraft = (draft) => {
  selectedDraft.value = draft;
  showPreviewModal.value = true;
};

const editDraft = (draft) => {
  // Navigate to customizer edit page
  router.push({
    name: "CustomizerUpdate",
    params: { id: draft.id },
  });
};

const duplicateDraft = async (draft) => {
  try {
    // Create a copy of the draft
    const payload = {
      canvasJson: draft.canvasJson,
      designName: `${draft.designName || "Thiết kế"} - Copy`,
      productItems: draft.productItems,
    };

    const response = await api.post("/customs", payload);
    console.log("✅ Đã sao chép bản phác thảo:", response.data);

    // Refresh the list
    await fetchDrafts();

    // Show success message
    alert("Đã sao chép bản phác thảo thành công!");
  } catch (error) {
    console.error("❌ Lỗi khi sao chép:", error);
    alert("Không thể sao chép bản phác thảo. Vui lòng thử lại sau.");
  }
};

const confirmDelete = (draft) => {
  selectedDraft.value = draft;
  showDeleteModal.value = true;
};

const deleteDraft = async () => {
  if (!selectedDraft.value) return;

  try {
    await api.delete(`/customs/${selectedDraft.value.id}`);
    console.log("✅ Đã xóa bản phác thảo:", selectedDraft.value.id);

    // Remove from local list
    drafts.value = drafts.value.filter((d) => d.id !== selectedDraft.value.id);
    filterDrafts();

    // Close modal
    showDeleteModal.value = false;
    selectedDraft.value = null;

    // Show success message
    alert("Đã xóa bản phác thảo thành công!");
  } catch (error) {
    console.error("❌ Lỗi khi xóa:", error);
    alert("Không thể xóa bản phác thảo. Vui lòng thử lại sau.");
  }
};

const changePage = (page) => {
  if (page >= 1 && page <= totalPages.value) {
    currentPage.value = page;
  }
};

// Lifecycle
onMounted(() => {
  fetchDrafts();
});
</script>

<style scoped>
/* ==================== GLOBAL STYLES ==================== */
.custom-drafts-page {
  font-family: "Inter", "Segoe UI", sans-serif;
  background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
  min-height: 100vh;
}

/* ==================== HERO SECTION ==================== */
.hero-section {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 4rem 0 2rem;
  color: white;
  position: relative;
  overflow: hidden;
}

.hero-section::before {
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: url('data:image/svg+xml,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1000 100" fill="rgba(255,255,255,0.1)"><path d="M0,20 Q250,80 500,20 T1000,20 L1000,0 L0,0 Z"/></svg>')
    repeat-x;
  background-size: 1000px 100px;
  animation: wave 10s infinite linear;
}

@keyframes wave {
  0% {
    background-position-x: 0;
  }
  100% {
    background-position-x: 1000px;
  }
}

.hero-content {
  text-align: center;
  position: relative;
  z-index: 1;
}

.hero-title {
  font-size: 2.5rem;
  font-weight: 700;
  margin-bottom: 1rem;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.hero-subtitle {
  font-size: 1.2rem;
  opacity: 0.9;
  margin: 0;
}

/* ==================== MAIN CONTENT ==================== */
.main-content {
  padding: 2rem 0;
  margin-top: -1rem;
  position: relative;
  z-index: 2;
}

/* ==================== FILTER SECTION ==================== */
.filter-section {
  background: white;
  border-radius: 20px;
  padding: 1.5rem;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
  margin-bottom: 2rem;
}

.search-box {
  position: relative;
}

.search-icon {
  position: absolute;
  left: 15px;
  top: 50%;
  transform: translateY(-50%);
  color: #6c757d;
  z-index: 2;
}

.search-input {
  padding-left: 45px;
  border: 2px solid #e9ecef;
  border-radius: 12px;
  font-size: 1rem;
  transition: all 0.3s ease;
}

.search-input:focus {
  border-color: #667eea;
  box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
}

/* ==================== LOADING & EMPTY STATES ==================== */
.loading-section {
  background: white;
  border-radius: 20px;
  padding: 3rem;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
}

.empty-state {
  background: white;
  border-radius: 20px;
  padding: 4rem 2rem;
  text-align: center;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
}

.empty-content {
  max-width: 400px;
  margin: 0 auto;
}

.empty-icon {
  font-size: 4rem;
  color: #667eea;
  margin-bottom: 1.5rem;
  opacity: 0.7;
}

.empty-title {
  font-size: 1.5rem;
  font-weight: 700;
  color: #2d3748;
  margin-bottom: 1rem;
}

.empty-description {
  color: #6c757d;
  line-height: 1.6;
  margin-bottom: 2rem;
}

/* ==================== DRAFTS GRID ==================== */
.drafts-grid {
  margin-bottom: 3rem;
}

.draft-card {
  background: white;
  border-radius: 20px;
  overflow: hidden;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
  height: 100%;
  display: flex;
  flex-direction: column;
}

.draft-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.15);
}

/* ==================== DRAFT IMAGE ==================== */
.draft-image-wrapper {
  position: relative;
  aspect-ratio: 4/5;
  overflow: hidden;
}

.draft-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.3s ease;
}

.draft-card:hover .draft-image {
  transform: scale(1.05);
}

.draft-overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.7);
  display: flex;
  align-items: center;
  justify-content: center;
  opacity: 0;
  transition: opacity 0.3s ease;
}

.draft-card:hover .draft-overlay {
  opacity: 1;
}

.draft-actions {
  display: flex;
  gap: 0.5rem;
}

.action-btn {
  width: 45px;
  height: 45px;
  border-radius: 50%;
  border: none;
  color: white;
  font-size: 1.2rem;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.3s ease;
  backdrop-filter: blur(10px);
}

.preview-btn {
  background: rgba(102, 126, 234, 0.9);
}

.edit-btn {
  background: rgba(16, 185, 129, 0.9);
}

.delete-btn {
  background: rgba(239, 68, 68, 0.9);
}

.action-btn:hover {
  transform: scale(1.1);
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.3);
}

/* ==================== DRAFT INFO ==================== */
.draft-info {
  padding: 1.5rem;
  flex-grow: 1;
  display: flex;
  flex-direction: column;
}

.draft-name {
  font-size: 1.1rem;
  font-weight: 700;
  color: #2d3748;
  margin-bottom: 1rem;
  line-height: 1.3;
}

.draft-meta {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  margin-bottom: 1rem;
}

.meta-item {
  display: flex;
  align-items: center;
  color: #6c757d;
  font-size: 0.85rem;
}

.product-info {
  margin-top: auto;
}

.product-details {
  margin-bottom: 0.75rem;
}

.product-name {
  display: flex;
  align-items: center;
  font-weight: 600;
  color: #374151;
  margin-bottom: 0.5rem;
  font-size: 0.9rem;
}

.product-meta {
  display: flex;
  flex-direction: column;
  gap: 0.25rem;
}

.meta-detail {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 0.8rem;
}

.meta-label {
  color: #6b7280;
  font-weight: 500;
}

.meta-value {
  color: #374151;
  font-weight: 600;
}

.meta-value.price {
  color: #dc2626;
}

.meta-value.sku {
  color: #6366f1;
  font-family: monospace;
  font-size: 0.75rem;
}

.meta-value.category {
  color: #059669;
  font-weight: 600;
  font-size: 0.75rem;
}

.meta-value.status {
  display: flex;
  align-items: center;
  gap: 0.25rem;
  font-weight: 600;
  font-size: 0.75rem;
}

.meta-value.status-active {
  color: #059669;
}

.meta-value.status-inactive {
  color: #dc2626;
}

.meta-value.rating {
  display: flex;
  align-items: center;
  gap: 0.25rem;
  font-size: 0.75rem;
}

.star {
  color: #d1d5db;
  font-size: 0.875rem;
}

.star.filled {
  color: #fbbf24;
}

.meta-value.description {
  color: #4b5563;
  font-size: 0.75rem;
  max-width: 200px;
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
}

.product-badge {
  display: inline-flex;
  align-items: center;
  background: linear-gradient(135deg, #10b981 0%, #059669 100%);
  color: white;
  padding: 0.25rem 0.75rem;
  border-radius: 15px;
  font-size: 0.75rem;
  font-weight: 600;
}

.product-badge.ordered {
  background: linear-gradient(135deg, #3b82f6 0%, #1d4ed8 100%);
}

/* ==================== DRAFT FOOTER ==================== */
.draft-footer {
  padding: 1rem 1.5rem;
  border-top: 1px solid #f1f3f4;
  display: flex;
  gap: 0.5rem;
}

.draft-footer .btn {
  flex: 1;
  font-size: 0.85rem;
  font-weight: 600;
  border-radius: 8px;
  transition: all 0.3s ease;
}

.draft-footer .btn:hover {
  transform: translateY(-1px);
}

/* ==================== PAGINATION ==================== */
.pagination-section {
  margin-top: 3rem;
}

.pagination .page-link {
  border: none;
  color: #6c757d;
  font-weight: 600;
  padding: 0.75rem 1rem;
  margin: 0 0.25rem;
  border-radius: 8px;
  transition: all 0.3s ease;
}

.pagination .page-item.active .page-link {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  box-shadow: 0 4px 15px rgba(102, 126, 234, 0.3);
}

.pagination .page-link:hover {
  background: #f8f9fa;
  color: #667eea;
}

/* ==================== MODALS ==================== */
.modal-content {
  border-radius: 20px;
  border: none;
  box-shadow: 0 20px 50px rgba(0, 0, 0, 0.2);
}

.modal-header {
  border-bottom: 1px solid #f1f3f4;
  padding: 1.5rem;
}

.modal-body {
  padding: 1.5rem;
}

.modal-footer {
  border-top: 1px solid #f1f3f4;
  padding: 1.5rem;
}

/* ==================== RESPONSIVE ==================== */
@media (max-width: 768px) {
  .hero-title {
    font-size: 2rem;
  }

  .filter-section {
    padding: 1rem;
  }

  .draft-actions {
    flex-wrap: wrap;
  }

  .action-btn {
    width: 40px;
    height: 40px;
    font-size: 1rem;
  }

  .draft-footer {
    flex-direction: column;
  }
}

@media (max-width: 576px) {
  .main-content {
    padding: 1rem 0;
  }

  .hero-title {
    font-size: 1.75rem;
  }

  .hero-subtitle {
    font-size: 1rem;
  }

  .empty-state {
    padding: 2rem 1rem;
  }

  .empty-icon {
    font-size: 3rem;
  }
}
</style>
