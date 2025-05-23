<template>
  <div class="container mt-4">
    <h1 class="h3 fw-bold mb-4">Dynamic Data Table</h1>

    <div v-if="loading" class="text-primary">Loading...</div>
    <div v-if="error" class="text-danger">{{ error }}</div>

    <table v-if="data.length" class="table table-bordered table-hover">
      <thead class="table-light">
        <tr>
          <th v-for="key in columns" :key="key">
            {{ key }}
          </th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(item, index) in data" :key="index">
          <td v-for="key in columns" :key="key">
            {{ item[key] }}
          </td>
        </tr>
      </tbody>
    </table>

    <div v-if="!data.length && !loading && !error" class="text-muted">
      No data available.
    </div>
  </div>
</template>

<script setup>
import { ref, watch, onMounted } from 'vue'
import 'bootstrap/dist/css/bootstrap.min.css'

const props = defineProps({
  TableName: {
    type: String,
    required: true
  }
})

const data = ref([])
const columns = ref([])
const loading = ref(false)
const error = ref(null)

const fetchData = async () => {
  if (!props.TableName) return
    console.log(props.TableName)
  loading.value = true
  error.value = null
  try {
    const response = await fetch("http://localhost:8080/Api/Admin/"+props.TableName)
    if (!response.ok) throw new Error('Failed to fetch data')
    const json = await response.json()
    data.value = Array.isArray(json) ? json : [json]
    columns.value = data.value.length ? Object.keys(data.value[0]) : []
  } catch (err) {
    error.value = err.message
    data.value = []
    columns.value = []
  } finally {
    loading.value = false
  }
}

onMounted(fetchData)
watch(() => props.TableName, fetchData)
</script>

<style scoped>
table {
  border-collapse: collapse;
}
</style>
