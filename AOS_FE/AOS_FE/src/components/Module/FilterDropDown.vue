<template>
    <div class="accordion filter-item" id="accordionFlushExample">
        <div class="accordion-item rounded-3 border-0">
            <h2 class="accordion-header" id="flush-headingTwo">
                <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                    data-bs-target="#flush-collapseTwo" aria-expanded="false" aria-controls="flush-collapseTwo">
                    Filters
                </button>
            </h2>

            <div id="flush-collapseTwo" class="accordion-collapse collapse" aria-labelledby="flush-headingTwo"
                data-bs-parent="#accordionFlushExample">
                <div class="accordion-body">
                    <div class="row p-3">
                        <div class="col-md-4" v-for="field in props.FilterList" :key="field.name">
                            <BaseInput v-model="filters[field.name]"
                                :label="field.name.charAt(0).toUpperCase() + field.name.slice(1)"
                                :placeholder="`Enter ${field.name}`" :type="field.type" :id="field.name" />
                        </div>
                    </div>
                    <div class="text-end mt-3">
                        <button class="btn btn-outline-secondary" @click="clearFilters">Clear Filters</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>



<script setup>
import { ref, watch } from 'vue'
import BaseInput from './BaseInput.vue'

const props = defineProps({
    modelValue: Object,
    FilterList: Array
})
const filters = ref({ ...props.modelValue })
const emit = defineEmits(['update:modelValue'])
const clearFilters = () => {
    Object.keys(filters.value).forEach(key => {
        filters.value[key] = ''
    })
}
watch(filters, (newVal) => {
    emit('update:modelValue', { ...newVal })
}, { deep: true })
watch(() => props.FilterList, (newList) => {
    if (newList) {
        newList.forEach(field => {
            if (!(field.name in filters.value)) {
                filters.value[field.name] = ''
            }
        })
    }
}, { immediate: true })
</script>


<style scoped>
.filter-item {
    border-radius: 15px;
    border: 1px solid #dee2e6;
    overflow: hidden;
}

.accordion-button {
    border-radius: 0 !important;
}

.accordion-item {
    border: none;
}

.accordion-body {
    background-color: #f8f9fa;
}
</style>
