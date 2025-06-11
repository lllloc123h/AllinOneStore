<template>
  <div class="container-fluid row">
    <div class="aside col-2">
      <Dashboard :listDashBoard="listDashBoard"></Dashboard>
    </div>
    
    
    <div class="article col-10">
      <h1>
        UserAddresses
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
          	{ name: 'recipientName', type: 'text' },
          	{ name: 'phone', type: 'text' },
          	{ name: 'province', type: 'text' },
          	{ name: 'district', type: 'text' },
          	{ name: 'ward', type: 'text' },
          	{ name: 'street', type: 'text' },
          	{ name: 'label', type: 'text' },
          	{ name: 'isDefault', type: 'text' },
          	{ name: 'note', type: 'text' },
 
]

const filters = ref({
  			id: '',
  			recipientName: '',
  			phone: '',
  			province: '',
  			district: '',
  			ward: '',
  			street: '',
  			label: '',
  			isDefault: '',
  			note: '',
  			createdAt: '',
  			updatedAt: '',
})
  
  
</script>
