<template>
  <div class="container-fluid row">
    <div class="aside col-2">
      <Dashboard :listDashBoard="listDashBoard"></Dashboard>
    </div>
    
    
    <div class="article col-10">
      <h1>
        Coupons
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
          	{ name: 'code', type: 'text' },
          	{ name: 'description', type: 'text' },
          	{ name: 'discountType', type: 'text' },
          	{ name: 'discountValue', type: 'number' },
          	{ name: 'minOrderAmount', type: 'number' },
          	{ name: 'maxDiscountAmount', type: 'number' },
          	{ name: 'usageLimit', type: 'number' },
          	{ name: 'usagePerCustomer', type: 'number' },
          	{ name: 'isAllowVoucher', type: 'text' },
          	{ name: 'isActive', type: 'text' },
          	{ name: 'customerGroup', type: 'text' },
          	{ name: 'startAt', type: 'datetime' },
          	{ name: 'endAt', type: 'datetime' },
 
]

const filters = ref({
  			id: '',
  			code: '',
  			description: '',
  			discountType: '',
  			discountValue: '',
  			minOrderAmount: '',
  			maxDiscountAmount: '',
  			usageLimit: '',
  			usagePerCustomer: '',
  			isAllowVoucher: '',
  			isActive: '',
  			customerGroup: '',
  			startAt: '',
  			endAt: '',
  			createdAt: '',
  			updatedAt: '',
})
  
  
</script>
