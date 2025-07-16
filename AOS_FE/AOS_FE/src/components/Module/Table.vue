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
              <span v-if="typeof item[key] === 'boolean'" class="boolean-indicator">
                <i v-if="item[key]" class="bi bi-check-circle-fill text-success fs-5"></i>
                <i v-else class="bi bi-x-circle-fill text-danger fs-5"></i>
              </span>
              <span v-else class="cell-content">
                {{ formatCell(key, item[key]) }}
              </span>
            </td>
            <td class="table-cell action-cell">
              <div class="action-buttons">
                <button
                  type="button"
                  @click="goToView(item.id)"
                  class="btn btn-info btn-sm action-btn"
                  title="Xem chi tiết"
                >
                  <i class="bi bi-eye"></i>
                </button>
                <button
                  type="button"
                  @click="goToEdit(item.id)"
                  class="btn btn-warning btn-sm action-btn"
                  title="Chỉnh sửa"
                >
                  <i class="bi bi-pencil-square"></i>
                </button>
                <button
                  type="button"
                  @click="deleteById(item.id)"
                  class="btn btn-danger btn-sm action-btn"
                  title="Xóa"
                >
                  <i class="bi bi-trash"></i>
                </button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <div v-if="!data.length && !loading && !error" class="text-muted"></div>
    <PageNavigative
      :totalPage="totalPage"
      v-model:currentPage="currentPage"
      v-model:currentSize="currentSize"
    >
    </PageNavigative>
  </div>
</template>
<style>
.pageselect > select#pageSize {
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
</style>
<script setup>
import { ref, watch, onMounted, computed, reactive } from "vue";
import { useRouter } from "vue-router";
import createCrudService from "../../Configs/reusableCRUDService";
import PageNavigative from "./PageNavigative.vue";
import FilterDropDown from "./FilterDropDown.vue";
import dayjs from "dayjs";

import "bootstrap/dist/css/bootstrap.min.css";
const prices = ["price", "cost", "discountValue", "comboPrice"];
function formatCell(key, value) {
  if (typeof value === "string" && !isNaN(Date.parse(value))) {
    return dayjs(value).format("DD/MM/YYYY HH:mm:ss");
  } else if (prices.includes(key)) {
    return `${value.toLocaleString("vi-VN", { style: "currency", currency: "VND" })}`;
  } else if (Array.isArray(value)) {
    return value.join(", ");
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
    console.log(responseIndexTable);
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
