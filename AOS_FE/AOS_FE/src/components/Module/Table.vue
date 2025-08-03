<template>
  <div class="container-fluid">
    <div v-if="loading" class="text-primary">
      <div class="spinner-border text-primary" role="status">
        <span class="visually-hidden">Loading...</span>
      </div>
    </div>
    <div v-if="error" class="text-danger">{{ error }}</div>

    <div class="table-header mb-3">
      <h4 class="table-title">Quản lý {{ props.TableName }}</h4>
      <a :href="`/Admin/${props.TableName}/create`" class="btn btn-success btn-create">
        Thêm mới
      </a>
    </div>
    <div class="table-container">
      <table v-if="data.length" class="table table-hover custom-table">
        <thead class="table-header-custom">
          <tr>
            <th v-for="key in columns" :key="key" class="table-th">
              {{ key }}
            </th>
            <th class="table-th action-column">Thao tác</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(item, index) in data" :key="index" class="table-row">
            <td v-for="key in columns" :key="key" class="table-cell">
              <span class="cell-content">
                <!-- {{ formatCell(key, item[key]) }} -->
                <span v-html="formatCell(key, item[key])"></span>
              </span>
            </td>
            <td class="table-cell action-cell">
              <div class="action-buttons">
                <button type="button" @click="goToView(item.id)" class="btn btn-info btn-sm action-btn"
                  title="Xem chi tiết">
                  <i class="bi bi-eye"></i>
                </button>
                <button type="button" @click="goToEdit(item.id)" class="btn btn-warning btn-sm action-btn"
                  title="Chỉnh sửa">
                  <i class="bi bi-pencil-square"></i>
                </button>
                <button type="button" @click="deleteById(item.id)" class="btn btn-danger btn-sm action-btn" title="Xóa">
                  <i class="bi bi-trash"></i>
                </button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <div v-if="!data.length && !loading && !error" class="text-muted"></div>
    <PageNavigative :totalPage="totalPage" v-model:currentPage="currentPage" v-model:currentSize="currentSize">
    </PageNavigative>
  </div>
</template>
<style scoped>
.pageselect>select#pageSize {
  width: 50px;
}

/* Modern Table Styling */
.table-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem 0;
  border-bottom: 2px solid #e9ecef;
}

.table-title {
  color: #2c3e50;
  font-weight: 600;
  margin: 0;
  text-transform: capitalize;
}

.btn-create {
  background: linear-gradient(45deg, #28a745, #34ce57);
  border: none;
  padding: 10px 20px;
  border-radius: 8px;
  font-weight: 500;
  box-shadow: 0 4px 12px rgba(40, 167, 69, 0.3);
  transition: all 0.3s ease;
}

.btn-create:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(40, 167, 69, 0.4);
}

.table-container {
  background: white;
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
  overflow-x: auto;
  overflow-y: hidden;
  margin-bottom: 2rem;
  min-height: 400px;
}

.custom-table {
  margin-bottom: 0;
  border: none;
  width: 100%;
  min-width: 800px;
}

.table-header-custom {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
}

.table-th {
  padding: 1rem 0.8rem;
  font-weight: 600;
  font-size: 0.9rem;
  text-transform: uppercase;
  letter-spacing: 0.5px;
  border: none;
  text-align: center;
  white-space: nowrap;
  min-width: 120px;
  position: sticky;
  top: 0;
  z-index: 10;
}

.action-column {
  min-width: 140px;
  position: sticky;
  right: 0;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  z-index: 11;
}

.table-row {
  border-bottom: 1px solid #f8f9fa;
}

.table-row:hover {
  background: linear-gradient(90deg, #f8f9ff 0%, #fff5f5 100%);
}

.table-row:hover .action-cell {
  background: linear-gradient(90deg, #f8f9ff 0%, #fff5f5 100%);
}

.table-cell {
  padding: 1rem 0.8rem;
  text-align: center;
  vertical-align: middle;
  border: none;
  color: #495057;
  font-size: 0.9rem;
  min-width: 120px;
  word-wrap: break-word;
  overflow-wrap: break-word;
}

.cell-content {
  font-weight: 500;
  max-width: 250px;
  display: inline-block;
  word-wrap: break-word;
  overflow-wrap: break-word;
  white-space: normal;
  line-height: 1.4;
}

.boolean-indicator {
  display: inline-flex;
  align-items: center;
  justify-content: center;
}

.action-cell {
  padding: 0.5rem 0.8rem;
  position: sticky;
  right: 0;
  background: white;
  border-left: 1px solid #f8f9fa;
  z-index: 5;
}

.action-buttons {
  display: flex;
  gap: 0.5rem;
  justify-content: center;
  align-items: center;
}

.action-btn {
  width: 35px;
  height: 35px;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 0.9rem;
  font-weight: 500;
  border: none;
  transition: all 0.3s ease;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.action-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
}

.btn-info {
  background: linear-gradient(45deg, #17a2b8, #20c997);
}

.btn-warning {
  background: linear-gradient(45deg, #ffc107, #ff8c00);
}

.btn-danger {
  background: linear-gradient(45deg, #dc3545, #e74c3c);
}

/* Loading and Error States */
.text-primary {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 2rem;
}

.text-danger {
  text-align: center;
  padding: 1rem;
  background: #f8d7da;
  border: 1px solid #f5c6cb;
  border-radius: 8px;
  color: #721c24;
  margin-bottom: 1rem;
}

/* Responsive improvements */
@media (max-width: 768px) {
  .table-header {
    flex-direction: column;
    gap: 1rem;
    align-items: stretch;
  }

  .table-title {
    text-align: center;
  }

  .table-container {
    margin: 0 -15px;
    border-radius: 0;
  }

  .custom-table {
    min-width: 600px;
  }

  .action-buttons {
    flex-direction: row;
    gap: 0.25rem;
  }

  .action-btn {
    width: 32px;
    height: 32px;
    font-size: 0.8rem;
  }

  .table-th,
  .table-cell {
    padding: 0.75rem 0.5rem;
    font-size: 0.85rem;
    min-width: 100px;
  }

  .cell-content {
    max-width: 150px;
  }
}

@media (max-width: 576px) {
  .custom-table {
    font-size: 0.75rem;
    min-width: 500px;
  }

  .table-th,
  .table-cell {
    padding: 0.5rem 0.25rem;
    min-width: 80px;
  }

  .cell-content {
    max-width: 120px;
  }
}

/* Custom scrollbar for table container */
.table-container::-webkit-scrollbar {
  height: 8px;
}

.table-container::-webkit-scrollbar-track {
  background: #f1f1f1;
  border-radius: 4px;
}

.table-container::-webkit-scrollbar-thumb {
  background: linear-gradient(90deg, #667eea, #764ba2);
  border-radius: 4px;
}

.table-container::-webkit-scrollbar-thumb:hover {
  background: linear-gradient(90deg, #5a67d8, #6b46c1);
}

/* Time remaining badges - scoped to table only */
.table-container .badge {
  font-size: 0.75rem;
  padding: 0.25rem 0.5rem;
  border-radius: 0.375rem;
  font-weight: 600;
  text-transform: uppercase;
  letter-spacing: 0.025em;
  display: inline-block;
  min-width: fit-content;
}

.table-container .bg-success {
  background-color: #198754 !important;
  color: white !important;
}

.table-container .bg-warning {
  background-color: #ffc107 !important;
  color: #000 !important;
}

.table-container .bg-warning.text-dark {
  background-color: #ff8c00 !important;
  color: #000 !important;
}

.table-container .bg-danger {
  background-color: #dc3545 !important;
  color: white !important;
}

/* Quantity badge specific styling - scoped to table only */
.table-container .badge.bg-success.text-white {
  background-color: #28a745 !important;
}

.table-container .badge.bg-warning.text-dark {
  background-color: #ffc107 !important;
}

.table-container .badge.bg-danger.text-white {
  background-color: #dc3545 !important;
}
</style>
<script setup>
import { ref, watch, onMounted, computed, reactive } from "vue";
import { useRouter } from "vue-router";
import createCrudService from "../../Configs/reusableCRUDService";
import PageNavigative from "./PageNavigative.vue";
import FilterDropDown from "./FilterDropDown.vue";
import dayjs from "dayjs";

import "bootstrap/dist/css/bootstrap.min.css";
const prices = [
  "price",
  "totalSpent",
  "averageOrderValue",
  "cost",
  "discountValue",
  "comboPrice",
];
function formatCell(key, value) {
  if (key.toLowerCase() === "password") return;
  if (key.toLowerCase().includes("gender")) {
    console.log("debug", key, value);
    let genderRender =
      value === true
        ? `<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-gender-male" viewBox="0 0 16 16">
        <path fill-rule="evenodd" d="M9.5 2a.5.5 0 0 1 0-1h5a.5.5 0 0 1 .5.5v5a.5.5 0 0 1-1 0V2.707L9.871 6.836a5 5 0 1 1-.707-.707L13.293 2zM6 6a4 4 0 1 0 0 8 4 4 0 0 0 0-8" />
      </svg>`
        : `<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-gender-female" viewBox="0 0 16 16">
        <path fill-rule="evenodd" d="M8 1a4 4 0 1 0 0 8 4 4 0 0 0 0-8M3 5a5 5 0 1 1 5.5 4.975V12h2a.5.5 0 0 1 0 1h-2v2.5a.5.5 0 0 1-1 0V13h-2a.5.5 0 0 1 0-1h2V9.975A5 5 0 0 1 3 5" />
      </svg>`;

    return genderRender;
  } else if (key.toLowerCase() === "createdat" && value) {
    const now = new Date();
    const createdDate = new Date(value);
    const timeDiff = now.getTime() - createdDate.getTime();
    const daysAgo = Math.floor(timeDiff / (1000 * 60 * 60 * 24));
    const hoursAgo = Math.floor((timeDiff % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));

    let statusText = "";
    let badgeClass = "";

    if (daysAgo === 0) {
      if (hoursAgo === 0) {
        statusText = "Vừa tạo";
        badgeClass = "bg-success text-white";
      } else {
        statusText = `${hoursAgo}h trước`;
        badgeClass = "bg-success text-white";
      }
    } else if (daysAgo <= 3) {
      statusText = `${daysAgo} ngày trước`;
      badgeClass = "bg-info text-white";
    } else if (daysAgo <= 14) {
      statusText = `${daysAgo} ngày trước`;
      badgeClass = "bg-warning text-dark";
    } else {
      statusText = `${daysAgo} ngày trước`;
      badgeClass = "bg-secondary text-white";
    }

    return `<span class="badge ${badgeClass}"><i class="bi bi-calendar-plus me-1"></i>${statusText}</span><br><small>${dayjs(
      value
    ).format("DD/MM/YYYY HH:mm:ss")}</small>`;
  } else if (key.toLowerCase() === "updatedat" && value) {
    const now = new Date();
    const updatedDate = new Date(value);
    const timeDiff = now.getTime() - updatedDate.getTime();
    const daysAgo = Math.floor(timeDiff / (1000 * 60 * 60 * 24));
    const hoursAgo = Math.floor((timeDiff % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
    const minutesAgo = Math.floor((timeDiff % (1000 * 60 * 60)) / (1000 * 60));

    let statusText = "";
    let badgeClass = "";

    if (daysAgo === 0) {
      if (hoursAgo === 0) {
        if (minutesAgo === 0) {
          statusText = "Vừa cập nhật";
          badgeClass = "bg-success text-white";
        } else {
          statusText = `${minutesAgo}p trước`;
          badgeClass = "bg-success text-white";
        }
      } else {
        statusText = `${hoursAgo}h trước`;
        badgeClass = "bg-success text-white";
      }
    } else if (daysAgo <= 3) {
      statusText = `${daysAgo} ngày trước`;
      badgeClass = "bg-info text-white";
    } else if (daysAgo <= 14) {
      statusText = `${daysAgo} ngày trước`;
      badgeClass = "bg-warning text-dark";
    } else {
      statusText = `${daysAgo} ngày trước`;
      badgeClass = "bg-secondary text-white";
    }

    return `<span class="badge ${badgeClass}"><i class="bi bi-pencil-square me-1"></i>${statusText}</span><br><small>${dayjs(
      value
    ).format("DD/MM/YYYY HH:mm:ss")}</small>`;
  } else if (key.toLowerCase() === "endat" && value) {
    const now = new Date();
    const endDate = new Date(value);
    const timeDiff = endDate.getTime() - now.getTime();

    if (timeDiff <= 0) {
      // Đã hết hạn
      return `<span class="badge bg-secondary text-white"><i class="bi bi-x-circle me-1"></i>Đã hết hạn</span><br><small>${dayjs(
        value
      ).format("DD/MM/YYYY HH:mm:ss")}</small>`;
    } else {
      // Tính toán thời gian còn lại
      const daysLeft = Math.floor(timeDiff / (1000 * 60 * 60 * 24));
      const hoursLeft = Math.floor((timeDiff % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
      const minutesLeft = Math.floor((timeDiff % (1000 * 60 * 60)) / (1000 * 60));

      let timeLeftText = "";
      let badgeClass = "";
      let iconClass = "";

      if (daysLeft > 14) {
        // Còn nhiều thời gian (> 14 ngày) - xanh lá
        timeLeftText = `${daysLeft} ngày`;
        badgeClass = "bg-success text-white";
        iconClass = "bi-check-circle";
      } else if (daysLeft > 3) {
        // Cảnh báo (3-14 ngày) - xanh dương
        timeLeftText = `${daysLeft} ngày`;
        badgeClass = "bg-info text-white";
        iconClass = "bi-info-circle";
      } else if (daysLeft > 0) {
        // Gần hết hạn (1-3 ngày) - vàng
        timeLeftText = `${daysLeft} ngày ${hoursLeft}h`;
        badgeClass = "bg-warning text-dark";
        iconClass = "bi-exclamation-triangle";
      } else {
        // Rất gấp (< 1 ngày) - đỏ (nhưng giữ màu đỏ riêng cho trường hợp khẩn cấp)
        timeLeftText = `${hoursLeft}h ${minutesLeft}m`;
        badgeClass = "bg-danger text-white";
        iconClass = "bi-alarm";
      }

      return `<span class="badge ${badgeClass}"><i class="bi ${iconClass} me-1"></i>Còn ${timeLeftText}</span><br><small>${dayjs(
        value
      ).format("DD/MM/YYYY HH:mm:ss")}</small>`;
    }
  } else if (key.toLowerCase() === "qty" && typeof value === "number") {
    // Hiển thị màu sắc cho quantity dựa trên mức tồn kho
    let badgeClass = "";
    let textClass = "";

    if (value <= 0) {
      // Hết hàng - đỏ
      badgeClass = "bg-danger";
      textClass = "text-white";
    } else if (value <= 5) {
      // Sắp hết (≤ 5) - cam
      badgeClass = "bg-warning text-dark";
      textClass = "text-dark";
    } else if (value <= 20) {
      // Ít (≤ 20) - vàng
      badgeClass = "bg-warning";
      textClass = "text-dark";
    } else {
      // Đủ hàng (> 20) - xanh lá
      badgeClass = "bg-success";
      textClass = "text-white";
    }

    const statusText =
      value <= 0 ? "Hết hàng" : value <= 5 ? "Sắp hết" : value <= 20 ? "Ít" : "Đủ hàng";

    return `<span class="badge ${badgeClass} ${textClass}">${value} - ${statusText}</span>`;
  } else if (
    typeof value === "string" &&
    !isNaN(Date.parse(value)) &&
    (key.toLowerCase().includes("date") ||
      key.toLowerCase().includes("time") ||
      key.toLowerCase().includes("at") ||
      /^\d{4}-\d{2}-\d{2}/.test(value) ||
      /^\d{2}\/\d{2}\/\d{4}/.test(value))
  ) {
    // Chỉ format thành ngày tháng nếu field name chứa date/time/at hoặc value có format ngày tháng rõ ràng
    return dayjs(value).format("DD/MM/YYYY HH:mm:ss");
  } else if (prices.includes(key)) {
    if (value != null) {
      return `${value.toLocaleString("vi-VN", { style: "currency", currency: "VND" })}`;
    }
    return '0'
  } else if (Array.isArray(value)) {
    return value.join(", ");
  } else if (key.toLowerCase().includes("url") && typeof value === "string") {
    // Kiểm tra nếu là avatar URL từ Google hoặc URL khác
    if (value.includes("googleusercontent.com") || key.toLowerCase().includes("avatar")) {
      // Đối với Google avatar, sử dụng crossorigin="anonymous" và referrerpolicy
      return `<img src="${value}" alt="avatar" crossorigin="anonymous" referrerpolicy="no-referrer" style="max-width: 60px; max-height: 60px; object-fit: cover; border-radius: 50%; border: 2px solid #ddd;" onerror="this.style.display='none'; this.nextSibling.style.display='inline';" /><span style="display:none; padding: 4px 8px; background: #f8f9fa; border-radius: 4px; font-size: 0.8rem; color: #6c757d;">Avatar không khả dụng</span>`;
    } else {
      return `<img src="${value}" alt="image" style="max-width: 100px; max-height: 60px; object-fit: contain;" onerror="this.style.display='none'; this.nextSibling.style.display='inline';" /><span style="display:none; padding: 4px 8px; background: #f8f9fa; border-radius: 4px; font-size: 0.8rem;">Không thể tải ảnh</span>`;
    }
  } else if (
    key.toLowerCase().includes("avatar") &&
    (value === null || value === undefined || value === "")
  ) {
    // Avatar mặc định khi không có avatar
    const defaultAvatar =
      "https://res.cloudinary.com/da2v8uqir/image/upload/v1754018153/baib6i5rkev8n2gpmswv.jpg";
    return `<img src="${defaultAvatar}" alt="ảnh mặc định" style="max-width: 60px; max-height: 60px; object-fit: cover; border-radius: 50%; border: 2px solid #ddd;" /><span class="ms-1 text-muted" style="font-size: 0.7rem;"></span>`;
  } else if (key.toLowerCase().includes("rating")) {
    const maxStars = 5;
    let stars = "";
    for (let i = 1; i <= maxStars; i++) {
      stars += `<i class="bi ${i <= value ? "bi-star-fill" : "bi-star"}"></i>`;
    }
    return `<span class="text-warning">${stars}</span>`;
  } else if (key.toLowerCase().includes("userrank") && typeof value === "string") {
    // Xử lý màu sắc cho userrank
    let badgeClass = "";
    let iconClass = "";

    switch (value.toLowerCase()) {
      case "đồng":
      case "dong":
      case "bronze":
        badgeClass = "bg-warning text-dark";
        iconClass = "bi-award";
        break;
      case "bạc":
      case "bac":
      case "silver":
        badgeClass = "bg-secondary text-white";
        iconClass = "bi-award-fill";
        break;
      case "vàng":
      case "vang":
      case "gold":
        badgeClass = "bg-success text-white";
        iconClass = "bi-trophy";
        break;
      case "bạch kim":
      case "bach kim":
      case "platinum":
        badgeClass = "bg-primary text-white";
        iconClass = "bi-trophy-fill";
        break;
      default:
        badgeClass = "bg-light text-dark";
        iconClass = "bi-person";
    }

    return `<span class="badge ${badgeClass}"><i class="bi ${iconClass} me-1"></i>${value}</span>`;
  } else if (key.toLowerCase() === "type" && typeof value === "string") {
    // Xử lý màu sắc cho promotion type
    let badgeClass = "";
    let iconClass = "";

    switch (value.toUpperCase()) {
      case "COMBO":
        badgeClass = "bg-warning text-dark";
        iconClass = "bi-collection";
        break;
      case "DISCOUNT":
        badgeClass = "bg-info text-white";
        iconClass = "bi-percent";
        break;
      default:
        badgeClass = "bg-secondary text-white";
        iconClass = "bi-tag";
    }

    return `<span class="badge ${badgeClass}"><i class="bi ${iconClass} me-1"></i>${value}</span>`;
  } else if (typeof value === "boolean") {
    return ` <span  class="boolean-indicator">
        ${value
        ? `<i class="bi bi-check-circle-fill text-success fs-5"></i>`
        : `<i class="bi bi-x-circle-fill text-danger fs-5"></i>`
      }
               
              </span>`;
  } else if (value === null || value === undefined) {
    return "N/A";
  }

  return value;
}

const router = useRouter();

const currentPage = ref(0);
const currentSize = ref(5);
const totalPage = ref(0);
function updatePageSize(size) {
  currentSize.value = size;
}
function updateCurrentPage(pageIndex) {
  currentPage.value = pageIndex;
}

const props = defineProps({
  TableName: {
    type: String,
    required: true,
  },
  FilterList: Object,
});

const indexTableService = createCrudService(props.TableName);

function goToView(id) {
  router.push(`/Admin/${props.TableName}/view/${id}`);
}
function goToEdit(id) {
  router.push(`/Admin/${props.TableName}/update/${id}`);
}

function deleteById(id) {
  indexTableService
    .delete(id)
    .then(() => {
      console.log("Delete successful");
      fetchData(); // Refresh data after deletion
    })
    .catch((error) => {
      console.error("Delete failed:", error);
    });
}
const data = ref([]);
const columns = ref([]);
const loading = ref(false);
const error = ref(null);
const fetchData = async () => {
  if (!props.TableName) return;
  loading.value = true;
  error.value = null;
  try {
    const responseIndexTable = await indexTableService.getAll(
      currentPage.value,
      currentSize.value,
      props.FilterList
    );
    const json = responseIndexTable.data.content;
    totalPage.value = responseIndexTable.data.totalPages;
    data.value = Array.isArray(json) ? json : [json];
    columns.value = data.value.length ? Object.keys(data.value[0]) : [];
  } catch (err) {
    error.value = err.message;
    data.value = [];
    columns.value = [];
    console.error("Error fetching data:", err);
  } finally {
    loading.value = false;
  }
};
const totalPages = computed(() => {
  return Math.ceil(data.value.length / currentSize.value);
});

onMounted(fetchData);
watch(() => props.TableName, fetchData);
watch(() => currentSize.value, fetchData);
watch(() => currentPage.value, fetchData);
watch(
  () => props.FilterList,
  () => {
    fetchData();
  },
  { deep: true }
);
</script>

<style scoped>
table {
  border-collapse: collapse;
}
</style>
