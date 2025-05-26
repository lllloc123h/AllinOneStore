<template>

    <div class="container row">
        <div class="col-3">
            <Dashboard :listDashBoard="props.listDashBoard"></Dashboard>
        </div>
        <div class="col-9">
            <Form :TableName="props.TableName" :id="props.id"></Form>
            <Table :TableName="props.TableName"></Table>
        </div>
    </div>
</template>
<script setup>
import { ref, watch, onMounted } from 'vue'
import 'bootstrap/dist/css/bootstrap.min.css'
import Table from '../Module/Table.vue'
import Form from '../Module/Form.vue'
import Dashboard from '../Module/Dashboard.vue'

const props = defineProps({
    TableName: {
        type: String,
        required: true
    },
    id: {
        type: String,
        required: true
    },
    listDashBoard: {
        type: Array,
        required: true
    }
})

</script>

<style scoped>
table {
    border-collapse: collapse;
}
</style>
