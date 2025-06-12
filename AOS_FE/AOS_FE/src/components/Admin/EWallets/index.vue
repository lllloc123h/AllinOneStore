<template>
  <div class="container-fluid row">
    <div class="aside col-2">
      <Dashboard :listDashBoard="listDashBoard"></Dashboard>
    </div>
    
    
    <div class="article col-10">
      <h1>
        EWallets
      </h1>
      <FilterDropDown :FilterList="FilterList" v-model:modelValue="filters" />
      <Table class="table" :TableName="props.TableName"></Table>
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
import Dashboard from "../../Module/Dashboard.vue";
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
          	{ name: 'balance', type: 'number' },
          	{ name: 'walletType', type: 'text' },
          	{ name: 'isActive', type: 'text' },
 
]

const filters = ref({
  			id: '',
  			balance: '',
  			walletType: '',
  			isActive: '',
  			createdAt: '',
})
  
  
</script>
