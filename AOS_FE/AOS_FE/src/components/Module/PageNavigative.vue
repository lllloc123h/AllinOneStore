<template>
  <div class="pageselect">
    <div class="page-size-selector">
      <label for="pageSize" class="form-label">Hiển thị</label>
      <select
        id="pageSize"
        class="form-select custom-select"
        v-model="currentSize"
        @change="updatePageSize(currentSize)"
      >
        <option :value="5">5</option>
        <option :value="10">10</option>
        <option :value="15">15</option>
      </select>
      <span class="form-text">mục/trang</span>
    </div>

    <nav aria-label="Page navigation">
      <ul class="pagination">
        <!-- Previous -->
        <li class="page-item" :class="{ disabled: currentPage <= 0 }">
          <button class="page-link" @click.prevent="updateCurrentPage(currentPage - 1)">
            &laquo;
          </button>
        </li>
        <!-- Page -1 -->
        <!-- <li class="page-item" v-if="currentPage > 0" :style="{ color: 'white' }">
          <button class="page-link" @click.prevent="updateCurrentPage(currentPage - 1)">
            {{ currentPage - 1 }}
          </button>
        </li> -->
        <!-- Current Page -->
        <li class="page-item active">
          <button class="page-link active" disabled>
            {{ currentPage + 1 }}
          </button>
        </li>
        <!-- Page +1 -->
        <li class="page-item" v-if="currentPage + 1 < props.totalPage">
          <button class="page-link" @click.prevent="updateCurrentPage(currentPage + 1)">
            {{ currentPage + 2 }}
          </button>
        </li>
        <!-- Next -->
        <li class="page-item" :class="{ disabled: currentPage + 1 >= props.totalPage }">
          <button class="page-link" @click.prevent="updateCurrentPage(currentPage + 1)">
            &raquo;
          </button>
        </li>
      </ul>
    </nav>
  </div>
</template>
<script setup>
import { ref, watch } from "vue";

const emits = defineEmits(["update:currentPage", "update:currentSize"]);

const props = defineProps({
  totalPage: Number, // total number of pages
});

const currentPage = ref(0);
const currentSize = ref(5);

function updatePageSize(size) {
  currentSize.value = size;
  emits("update:currentSize", size);
  currentPage.value = 0; // reset to first page
}

function updateCurrentPage(page) {
  if (page >= 0 && page <= props.totalPage) {
    currentPage.value = page;
    emits("update:currentPage", page);
  }
}

watch(currentPage, (newPage) => {
  emits("update:currentPage", newPage);
});

watch(currentSize, (newSize) => {
  emits("update:currentSize", newSize);
});
</script>
<style scoped>
.pageselect {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 20px;
  margin-bottom: 20px;
}

.page-size-selector {
  display: flex;
  align-items: center;
  gap: 8px;
  background: white;
  padding: 10px 15px;
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  border: 1px solid #e9ecef;
}

.form-label {
  font-size: 14px;
  font-weight: 500;
  color: #495057;
  margin: 0;
  white-space: nowrap;
}

.custom-select {
  border: 1px solid #dee2e6;
  border-radius: 8px;
  padding: 15px 12px;
  font-size: 14px;
  font-weight: 500;
  color: #495057;
  background: white;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
  cursor: pointer;
  min-width: 60px;
  height: 54px;
}

.custom-select:focus {
  border-color: #007bff;
  box-shadow: 0 0 0 0.2rem rgba(0, 123, 255, 0.25);
  outline: none;
}

.custom-select:hover {
  border-color: #007bff;
  box-shadow: 0 2px 6px rgba(0, 123, 255, 0.2);
}

.form-text {
  font-size: 14px;
  color: #6c757d;
  margin: 0;
  white-space: nowrap;
}

button.page-link {
  border: none;
  padding: 15px 22px;
  margin: 0 5px;
  border-radius: 10px;
  background: white;
  color: #495057;
  font-weight: 500;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
}

.page-link.active {
  background: #007bff;
  color: white;
  transform: translateY(-3px);
  /* box-shadow: 0 5px 15px rgba(0, 123, 255, 0.4); */
}

button.page-link:hover {
  background: #007bff;
  color: white;
  box-shadow: rgba(0, 0, 0, 0.5) 0 10px 20px;
  transform: translateY(-3px);
  transition-duration: 0.6s;
  transition: all 300ms cubic-bezier(0.23, 1, 0.32, 1);
}
</style>
