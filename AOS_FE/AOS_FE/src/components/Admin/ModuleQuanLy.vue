<template>
    
    <div class="container row">        
        <div class="col-3">
            <Dashboard></Dashboard>
        </div>
        <div class="col-9">
            <Table :TableName="props.TableName"></Table>
        </div>
    </div>
</template>
<script setup>
import { ref, watch, onMounted } from 'vue'
import 'bootstrap/dist/css/bootstrap.min.css'
import Table from '../Module/Table.vue'
import Dashboard from '../Module/Dashboard.vue'

const props = defineProps({
  TableName: {
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
