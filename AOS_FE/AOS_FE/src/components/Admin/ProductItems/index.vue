<template>
  <div class="container-fluid row">
    <div class="aside col-2">
      <Dashboard :listDashBoard="listDashBoard"></Dashboard>
    </div>
    
    
    <div class="article col-10">
      <h1>
        ProductItems
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
          	{ name: 'costFrom', type: 'number' },
          	{ name: 'costTo', type: 'number' },
          	{ name: 'priceFrom', type: 'number' },
          	{ name: 'priceTo', type: 'number' },
          	{ name: 'turnBuyFrom', type: 'number' },
          	{ name: 'turnBuyTo', type: 'number' },
          	{ name: 'description', type: 'text' },
          	{ name: 'sku', type: 'text' },
          	{ name: 'safetyStockFrom', type: 'number' },
          	{ name: 'safetyStockTo', type: 'number' },
          	{ name: 'qtyFrom', type: 'number' },
          	{ name: 'qtyTo', type: 'number' },
          	{ name: 'sellStartFrom', type: 'datetime-local' },
          	{ name: 'sellStartTo', type: 'datetime-local' },
          	{ name: 'sellEndFrom', type: 'datetime-local' },
          	{ name: 'sellEndTo', type: 'datetime-local' },
          	{ name: 'createdAtFrom', type: 'datetime-local' },
          	{ name: 'createdAtTo', type: 'datetime-local' },
          	{ name: 'updatedAtFrom', type: 'datetime-local' },
          	{ name: 'updatedAtTo', type: 'datetime-local' },
 
]

const filters = ref({
  			id: '',
  			cost: '',
  			price: '',
  			turnBuy: '',
  			description: '',
  			sku: '',
  			safetyStock: '',
  			qty: '',
  			sellStart: '',
  			sellEnd: '',
  			createdAt: '',
  			updatedAt: '',
})
  
  
</script>
