<template>
  <div class="container-fluid row">
    <div class="aside col-2">
      <Dashboard :listDashBoard="listDashBoard"></Dashboard>
    </div>
    
    
    <div class="article col-10">
      <h1>
        Accounts
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
          	{ name: 'email', type: 'email' },
          	{ name: 'password', type: 'password' },
          	{ name: 'fullname', type: 'text' },
          	{ name: 'avatarUrl', type: 'text' },
          	{ name: 'phone', type: 'text' },
          	{ name: 'averageOrderValueFrom', type: 'number' },
          	{ name: 'averageOrderValueTo', type: 'number' },
          	{ name: 'userRank', type: 'text' },
          	{ name: 'totalSpentFrom', type: 'number' },
          	{ name: 'totalSpentTo', type: 'number' },
          	{ name: 'totalOrderFrom', type: 'number' },
          	{ name: 'totalOrderTo', type: 'number' },
          	{ name: 'loyaltyPointFrom', type: 'number' },
          	{ name: 'loyaltyPointTo', type: 'number' },
          	{ name: 'createdAtFrom', type: 'datetime-local' },
          	{ name: 'createdAtTo', type: 'datetime-local' },
          	{ name: 'updatedAtFrom', type: 'datetime-local' },
          	{ name: 'updatedAtTo', type: 'datetime-local' },
 
]

const filters = ref({
  			id: '',
  			email: '',
  			password: '',
  			fullname: '',
  			avatarUrl: '',
  			phone: '',
  			averageOrderValue: '',
  			userRank: '',
  			totalSpent: '',
  			totalOrder: '',
  			loyaltyPoint: '',
  			createdAt: '',
  			updatedAt: '',
})
  
  
</script>
