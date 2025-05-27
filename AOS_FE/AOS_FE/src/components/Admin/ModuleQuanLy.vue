<template>
    <div class="container row">
        <div class="col-3 ">
            <button class="btn btn-primary" type="button" data-bs-toggle="offcanvas"
                data-bs-target="#offcanvasScrolling" aria-controls="offcanvasScrolling">Admin menu</button>

            <div class="holdedDashBoard offcanvas offcanvas-start" data-bs-scroll="true" data-bs-backdrop="false"
                tabindex="-1" id="offcanvasScrolling" aria-labelledby="offcanvasScrollingLabel">
                <div class="offcanvas-header">
                    <h5 class="offcanvas-title" id="offcanvasScrollingLabel">Admin menu</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
                </div>
                <Dashboard :listDashBoard="props.listDashBoard"></Dashboard>
            </div>
        </div>
        <div class="col-9">
            <Form :TableName="props.TableName" :id="props.id" v-if="props.isEditing"></Form>
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
    isEditing: {
        type: Boolean,
        required: true
    },
    listDashBoard: {
        type: Array,
        required: true
    }
})

</script>
