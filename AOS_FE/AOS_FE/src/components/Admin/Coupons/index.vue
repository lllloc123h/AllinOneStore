<template>
  <div class="container-fluid row">
    <div class="aside col-2">
      <Dashboard></Dashboard>
    </div>
    <div class="article col-10">
      <h1>
        Coupons
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
const FilterList = [
  { name: 'code', type: 'text' },
  { name: 'description', type: 'text' },
  { name: 'discountType', type: 'text' },
  { name: 'discountValueFrom', type: 'number' },
  { name: 'discountValueTo', type: 'number' },
  { name: 'minOrderAmountFrom', type: 'number' },
  { name: 'minOrderAmountTo', type: 'number' },
  // { name: 'maxDiscountAmountFrom', type: 'number'},
  // { name: 'maxDiscountAmountTo', type: 'number'},
  { name: 'qtyFrom', type: 'number' },
  { name: 'qtyTo', type: 'number' },
  { name: 'usagePerCustomerFrom', type: 'number' },
  { name: 'usagePerCustomerTo', type: 'number' },
  { name: 'isAllowVoucher', type: 'text' },
  { name: 'isActive', type: 'text' },
  { name: 'customerGroup', type: 'text' },
  { name: 'startAtFrom', type: 'datetime-local' },
  { name: 'startAtTo', type: 'datetime-local' },
  { name: 'endAtFrom', type: 'datetime-local' },
  { name: 'endAtTo', type: 'datetime-local' },
  { name: 'createdAtFrom', type: 'datetime-local' },
  { name: 'createdAtTo', type: 'datetime-local' },
  { name: 'updatedAtFrom', type: 'datetime-local' },
  { name: 'updatedAtTo', type: 'datetime-local' },
]
const filters = ref({
  id: '',
  code: '',
  description: '',
  discountType: '',
  discountValue: '',
  minOrderAmount: '',
  // maxDiscountAmount: '',
  qty: '',
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
