<template>
  <div class="container-fluid row">
    <div class="aside col-2">
      <Dashboard :listDashBoard="listDashBoard"></Dashboard>
    </div>
    
    
    <div class="article col-10">
      <h1>
        OrderItems
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
          	{ name: 'qtyFrom', type: 'number' },
          	{ name: 'qtyTo', type: 'number' },
          	{ name: 'costFrom', type: 'number' },
          	{ name: 'costTo', type: 'number' },
          	{ name: 'isGift', type: 'text' },
          	{ name: 'sellingPriceFrom', type: 'number' },
          	{ name: 'sellingPriceTo', type: 'number' },
          	{ name: 'totalFrom', type: 'number' },
          	{ name: 'totalTo', type: 'number' },
          	{ name: 'couponCode', type: 'text' },
          	{ name: 'createdAtFrom', type: 'datetime' },
          	{ name: 'createdAtTo', type: 'datetime' },
          	{ name: 'updatedAtFrom', type: 'datetime' },
          	{ name: 'updatedAtTo', type: 'datetime' },
 
]

const filters = ref({
  			id: '',
  			qty: '',
  			cost: '',
  			isGift: '',
  			sellingPrice: '',
  			total: '',
  			couponCode: '',
  			createdAt: '',
  			updatedAt: '',
})
  
  
</script>
