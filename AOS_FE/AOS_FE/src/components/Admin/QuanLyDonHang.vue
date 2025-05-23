<template>
    <ModuleQuanLy :TableName="props.TableName" :id="props.id"></ModuleQuanLy>
</template>
<script setup>
import { ref, watch, onMounted } from 'vue'
import 'bootstrap/dist/css/bootstrap.min.css'

import ModuleQuanLy from '../Admin/ModuleQuanLy.vue'

const props = defineProps({
    TableName: {
        type: String,
        required: true
    },
    id: {
        type: String,
        required: true
    }
})
</script>

<style scoped>
table {
    border-collapse: collapse;
}
</style>
