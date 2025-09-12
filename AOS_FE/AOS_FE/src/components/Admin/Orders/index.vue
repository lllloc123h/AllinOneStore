<template>
  <div class="container-fluid row">
    <div class="aside col-2">
      <Dashboard :listDashBoard="listDashBoard"></Dashboard>
    </div>
    
    
    <div class="article col-10">
      <h1>
        Orders
      </h1>
      <FilterDropDown :FilterList="FilterList" v-model:modelValue="filters" />
      <div class="export-section d-flex align-items-center gap-2 mb-3">
  <input
    type="datetime-local"
    v-model="start"
    class="form-control export-start"
    placeholder="Start date"
  />
  <input
    type="datetime-local"
    v-model="end"
    class="form-control export-end"
    placeholder="End date"
  />
  <button
    class="btn btn-success export-btn"
    @click="exportOrders(start, end)"
  >
    <i class="bi bi-file-earmark-excel"></i> Export Orders
  </button>
</div>
      <Table class="table" :TableName="props.TableName" :FilterList="filters"></Table>
    </div>
  </div>
</template>
<style scoped>
.export-section {
  flex-wrap: wrap;
}

.export-start,
.export-end {
  max-width: 220px;
}

.export-btn {
  white-space: nowrap;
  font-weight: 600;
}
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
import axios from 'axios';
import api from "../../../Configs/api";

const exportOrders = async (startDate, endDate) => {
  try {
    const response = await api.get('http://localhost:8080/api/orders/export', {
      params: {
        start: startDate,
        end: endDate
      },
      responseType: 'blob'
    });

   
    const url = window.URL.createObjectURL(new Blob([response.data]));
    const link = document.createElement('a');
    link.href = url;
    link.setAttribute('download', 'orders.xlsx'); 
    document.body.appendChild(link);
    link.click();
    link.remove();
  } catch (error) {
    console.error('Lỗi export:', error);
  }
};

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
          	{ name: 'shippingStatus', type: 'text' },
          	{ name: 'estimatedShippingFeeFrom', type: 'number' },
          	{ name: 'estimatedShippingFeeTo', type: 'number' },
          	{ name: 'freeshipCouponCode', type: 'text' },
          	{ name: 'actualShippingFeeFrom', type: 'number' },
          	{ name: 'actualShippingFeeTo', type: 'number' },
          	{ name: 'discountCouponCode', type: 'text' },
          	{ name: 'discountValueFrom', type: 'number' },
          	{ name: 'discountValueTo', type: 'number' },
          	{ name: 'shippedDateFrom', type: 'datetime-local' },
          	{ name: 'shippedDateTo', type: 'datetime-local' },
          	{ name: 'paymentStatus', type: 'text' },
          	{ name: 'note', type: 'text' },
          	{ name: 'pointFrom', type: 'number' },
          	{ name: 'pointTo', type: 'number' },
          	{ name: 'finalTotalFrom', type: 'number' },
          	{ name: 'finalTotalTo', type: 'number' },
          	{ name: 'orderInfor', type: 'text' },
          	{ name: 'createdAtFrom', type: 'datetime-local' },
          	{ name: 'createdAtTo', type: 'datetime-local' },
          	{ name: 'updatedAtFrom', type: 'datetime-local' },
          	{ name: 'updatedAtTo', type: 'datetime-local' },
 
]

const filters = ref({
  id: '',
  shippingStatus: '',
  estimatedShippingFee: '',
  freeshipCouponCode: '',
  actualShippingFee: '',
  discountCouponCode: '',
  discountValue: '',
  shippedDate: '',
  paymentStatus: '',
  note: '',
  point: '',
  finalTotal: '',
  orderInfor: '',
  createdAtFrom: '2000-01-01T00:00',
  createdAtTo: '2100-12-31T23:59',
  updatedAtFrom: '2000-01-01T00:00',
  updatedAtTo: '2100-12-31T23:59',
});
const start = ref('');
const end = ref('');
  
</script>
