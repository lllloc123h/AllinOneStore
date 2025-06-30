<template>
  <div class="container-fluid row">
    <div class="aside col-2">
      <Dashboard :listDashBoard="listDashBoard"></Dashboard>
    </div>
    
    
    <div class="article col-10">
      <h1>
        Returns
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
          	{ name: 'qty', type: 'number' },
          	{ name: 'reason', type: 'text' },
          	{ name: 'imageUrl1', type: 'text' },
          	{ name: 'imageUrl2', type: 'text' },
          	{ name: 'imageUrl3', type: 'text' },
          	{ name: 'videoUrl', type: 'text' },
          	{ name: 'isReturnedMoney', type: 'text' },
          	{ name: 'refundAmount', type: 'number' },
          	{ name: 'returnType', type: 'text' },
          	{ name: 'refundType', type: 'text' },
          	{ name: 'status', type: 'text' },
          	{ name: 'processedAt', type: 'datetime' },
 
]

const filters = ref({
  			id: '',
  			qty: '',
  			reason: '',
  			imageUrl1: '',
  			imageUrl2: '',
  			imageUrl3: '',
  			videoUrl: '',
  			isReturnedMoney: '',
  			refundAmount: '',
  			returnType: '',
  			refundType: '',
  			status: '',
  			processedAt: '',
  			createdAt: '',
  			updatedAt: '',
})
  
  
</script>
