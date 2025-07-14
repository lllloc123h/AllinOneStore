<template>
  <div class="container-fluid row">
    <div class="aside col-2">
      <Dashboard ></Dashboard>
    </div>
    <div class="article col-10">
      <h1>
        EWalletTransactions
      </h1>
      <FilterDropDown :FilterList="FilterList" v-model:modelValue="filters" />
      <Table class="table" :TableName="props.TableName" :FilterList="filters"></Table>
    </div>
  </div>
</template>
<style scoped>
  .table {
    width: 100%;
  }
</style>
<script setup>
import Table from "../../Module/Table.vue";
import Dashboard from "../../Module/DashBoard.vue";
import { useRouter } from 'vue-router'
import { ref, reactive, watch } from 'vue'

import FilterDropDown from "../../Module/FilterDropDown.vue";
const router = useRouter()
  const props = defineProps({
    TableName: {
      type: String,
      required: true,
    },
  });
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
  ];
 
  const FilterList = [
          	{ name: 'amountFrom', type: 'number' },
          	{ name: 'amountTo', type: 'number' },
          	{ name: 'transactionType', type: 'text' },
          	{ name: 'orderId', type: 'text' },
          	{ name: 'status', type: 'text' },
          	{ name: 'relatedWalletIdFrom', type: 'number' },
          	{ name: 'relatedWalletIdTo', type: 'number' },
          	{ name: 'description', type: 'text' },
          	{ name: 'createdAtFrom', type: 'datetime-local' },
          	{ name: 'createdAtTo', type: 'datetime-local' },
 
]

const filters = ref({
  			id: '',
  			amount: '',
  			transactionType: '',
  			orderId: '',
  			status: '',
  			relatedWalletId: '',
  			description: '',
  			createdAt: '',
})
  
  
</script>
