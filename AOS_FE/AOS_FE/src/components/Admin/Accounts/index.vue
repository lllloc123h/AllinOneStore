<template>
    <div class="container-fluid row">
        <div class="aside col-3">
             <Dashboard :listDashBoard="listDashBoard"></Dashboard></div>
        <div class="article col-9">
            <Table class="table" :TableName="tableName"></Table>
        </div>
    </div>
</template>
<style scoped>

.table{
    width: 100%;
}
</style>
<script setup>
// import { ref, watch, onMounted } from 'vue'
import Table from '../../Module/Table.vue'
import Dashboard from '../../Module/Dashboard.vue'

// const formData=ref();
const tableName = 'Accounts'
const listDashBoard = [
    "Accounts",
    "Authorities",
    "BaseProducts",
    "Cancels",
    "CartItems",
    "Catalogs",
    "Categories",
    "CostHistories",
    "Coupons",
    "News",
    "OrderItems",
    "Orders",
    "PaymentMethods",
    "PriceHistories",
    "ProductImages",
    "ProductItems",
    "PromotionProduct",
    "Promotions",
    "PurchaseOrderItems",
    "PurchaseOrders",
    "Returns",
    "Reviews",
    "Roles",
    "ShippingMethods",
    "UserAddresses",
    "VariantValues",
    "Variants",
    "TraceXeActionMap",
    "TraceXeEventMap",
]

</script>