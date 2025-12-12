<template>
  <div class="container-fluid row">
    <div class="aside col-2">
      <Dashboard></Dashboard>
    </div>
    <div class="article col-10">
      <h1>
        EmailTEGV
      </h1>
      <FilterDropDown :FilterList="FilterList" v-model:modelValue="filters" />
      <Table
        class="table"
        :TableName="props.TableName"
        :FilterList="filters"
      ></Table>
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
      { name: 'nameTEGV', type: 'text'},
      { name: 'emailTEGV', type: 'email'},
  ]
  const filters = ref({
    id: '',
    nameTEGV: '',
    emailTEGV: '',
  })
</script>
