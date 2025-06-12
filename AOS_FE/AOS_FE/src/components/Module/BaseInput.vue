<template>
    <div class="mb-3">
        <label :for="id" class="form-label text-capitalize">{{ label }}</label>
        <input :id="id" :type="type" class="form-control" :placeholder="placeholder" v-bind="$attrs"
            v-model="internalValue" />
    </div>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
    modelValue: [String, Number],
    label: String,
    placeholder: String,
    type: {
        type: String,
        default: 'text'  // ✅ default to "text"
    },
    id: String
})

const emit = defineEmits(['update:modelValue'])

const internalValue = computed({
    get: () => props.modelValue,
    set: (val) => emit('update:modelValue', val)
})
</script>
