<template>
  <div class="accordion filter-item" id="accordionFlushExample">
    <div class="accordion-item rounded-3 border-0">
      <h2 class="accordion-header" id="flush-headingTwo">
        <button
          class="accordion-button collapsed filter-toggle"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#flush-collapseTwo"
          aria-expanded="false"
          aria-controls="flush-collapseTwo"
        >
          <i class="bi bi-funnel me-2"></i>
          <span class="filter-title">Advanced Filters</span>
        </button>
      </h2>

      <div
        id="flush-collapseTwo"
        class="accordion-collapse collapse"
        aria-labelledby="flush-headingTwo"
        data-bs-parent="#accordionFlushExample"
      >
        <div class="accordion-body filter-content">
          <div class="row p-3">
            <div
              class="col-md-4 mb-3"
              v-for="field in props.FilterList"
              :key="field.name"
            >
              <div class="filter-field">
                <BaseInput
                  v-model="filters[field.name]"
                  :label="field.name.charAt(0).toUpperCase() + field.name.slice(1)"
                  :placeholder="`Enter ${field.name}`"
                  :type="field.type"
                  :id="field.name"
                />
              </div>
            </div>
          </div>
          <div class="text-end mt-3">
            <button class="btn btn-clear" @click="clearFilters">
              <i class="bi bi-x-circle me-2"></i>Clear Filters
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, watch } from "vue";
import BaseInput from "./BaseInput.vue";

const props = defineProps({
  modelValue: Object,
  FilterList: Array,
});
const filters = ref({ ...props.modelValue });
const emit = defineEmits(["update:modelValue"]);
const clearFilters = () => {
  Object.keys(filters.value).forEach((key) => {
    filters.value[key] = "";
  });
};
watch(
  filters,
  (newVal) => {
    emit("update:modelValue", { ...newVal });
  },
  { deep: true }
);
watch(
  () => props.FilterList,
  (newList) => {
    if (newList) {
      newList.forEach((field) => {
        if (!(field.name in filters.value)) {
          filters.value[field.name] = "";
        }
      });
    }
  },
  { immediate: true }
);
</script>

<style scoped>
.filter-item {
  border-radius: 15px;
  border: none;
  overflow: hidden;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
  background: white;
  margin-bottom: 2rem;
}

.accordion-item {
  border: none;
  background: transparent;
}

.filter-toggle {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border: none;
  padding: 1rem 1.5rem;
  font-weight: 600;
  font-size: 1rem;
  border-radius: 15px 15px 0 0 !important;
  box-shadow: none;
  transition: all 0.3s ease;
}

.filter-toggle:hover {
  background: linear-gradient(135deg, #5a67d8 0%, #6b46c1 100%);
  transform: translateY(-1px);
}

.filter-toggle:focus {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  box-shadow: 0 0 0 0.2rem rgba(102, 126, 234, 0.25);
}

.filter-toggle:not(.collapsed) {
  background: linear-gradient(135deg, #5a67d8 0%, #6b46c1 100%);
  color: white;
}

.filter-toggle::after {
  background-image: url("data:image/svg+xml,%3csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 16 16' fill='%23ffffff'%3e%3cpath fill-rule='evenodd' d='M1.646 4.646a.5.5 0 0 1 .708 0L8 10.293l5.646-5.647a.5.5 0 0 1 .708.708l-6 6a.5.5 0 0 1-.708 0l-6-6a.5.5 0 0 1 0-.708z'/%3e%3c/svg%3e");
  transition: transform 0.3s ease;
}

.filter-title {
  font-size: 1rem;
  font-weight: 600;
  letter-spacing: 0.5px;
}

.filter-content {
  background: linear-gradient(135deg, #f8f9ff 0%, #fff5f5 100%);
  border: none;
  padding: 2rem 1.5rem;
  position: relative;
}

.filter-content::before {
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 2px;
  background: linear-gradient(90deg, #667eea 0%, #764ba2 50%, #f093fb 100%);
}

.filter-field {
  background: rgba(255, 255, 255, 0.7);
  backdrop-filter: blur(10px);
  border-radius: 12px;
  padding: 1rem;
  border: 1px solid rgba(102, 126, 234, 0.1);
  transition: all 0.3s ease;
}

.filter-field:hover {
  background: rgba(255, 255, 255, 0.9);
  border-color: rgba(102, 126, 234, 0.2);
  transform: translateY(-2px);
  box-shadow: 0 4px 15px rgba(102, 126, 234, 0.1);
}

.btn-clear {
  background: linear-gradient(45deg, #f56565 0%, #e53e3e 100%);
  border: none;
  color: white;
  padding: 0.75rem 1.5rem;
  border-radius: 10px;
  font-weight: 600;
  font-size: 0.9rem;
  transition: all 0.3s ease;
  box-shadow: 0 4px 12px rgba(245, 101, 101, 0.3);
}

.btn-clear:hover {
  background: linear-gradient(45deg, #e53e3e 0%, #c53030 100%);
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(245, 101, 101, 0.4);
  color: white;
}

.btn-clear:focus {
  background: linear-gradient(45deg, #f56565 0%, #e53e3e 100%);
  color: white;
  box-shadow: 0 0 0 0.2rem rgba(245, 101, 101, 0.25);
}

/* Animation for accordion collapse */
.accordion-collapse {
  transition: all 0.4s ease;
}

/* Responsive design */
@media (max-width: 768px) {
  .filter-item {
    border-radius: 10px;
  }

  .filter-toggle {
    padding: 0.75rem 1rem;
    font-size: 0.9rem;
  }

  .filter-content {
    padding: 1.5rem 1rem;
  }

  .filter-field {
    padding: 0.75rem;
  }

  .btn-clear {
    padding: 0.6rem 1.2rem;
    font-size: 0.85rem;
  }
}

@media (max-width: 576px) {
  .col-md-4 {
    flex: 0 0 100%;
    max-width: 100%;
  }

  .filter-field {
    margin-bottom: 1rem;
  }
}
</style>
