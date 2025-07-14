<template>
    <div class="container-fluid">
        <div v-if="loading" class="text-primary">
            <div class="spinner-border text-primary" role="status">
                <span class="visually-hidden">Loading...</span>
            </div>
        </div>
        <div v-if="error" class="text-danger">{{ error }}</div>

        <div><a :href="`/Admin/${props.TableName}/create`" class="btn btn-primary">Thêm mới</a></div>
        <table v-if="data.length" class="table table-bordered table-hover container-fluid">
            <thead class="table-light">
                <tr>
                    <th v-for="key in columns" :key="key">
                        {{ key }}
                    </th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="(item, index) in data" :key="index">
                    <td v-for="key in columns" :key="key">
                        {{ item[key] }}
                    </td>
                    <td class="row btn-holder">

                        <button type="button" @click="goToView(item.id)"
                            class="btn btn-primary me-2 row-6">View</button>
                        <button type="button" @click="goToEdit(item.id)"
                            class="btn btn-primary me-2 row-6">Edit</button>
                        <button type="button" @click="deleteById(item.id)" class="btn btn-danger row-6">Delete</button>

                    </td>
                </tr>
            </tbody>
        </table>
        <div v-if="!data.length && !loading && !error" class="text-muted">
        </div>
        <PageNavigative :totalPage="totalPage" v-model:currentPage="currentPage" v-model:currentSize="currentSize">
        </PageNavigative>
    </div>
</template>
<style>
.pageselect>select#pageSize {
    width: 50px;
}

/* Add some margin to the table */
.table {
    margin-top: 20px;
}

/* Make header text bold and centered */
.table thead th {
    font-weight: bold;
    text-align: center;
    vertical-align: middle;
}

/* Center all cell content */
.table tbody td {
    text-align: center;
    vertical-align: middle;
}

.table td,
.table th {
    max-width: 150px;
    /* Set your desired max width */
    white-space: normal;
    /* Allow text to wrap */
    word-wrap: break-word;
    /* Break long words */
    overflow-wrap: break-word;
}

/* Space between buttons in action column */
/* .table .btn {
    min-width: 60px;
    font-size: 0.9rem;
} */
.btn-holder {
    margin: 0 auto;
    border: 0px;
}

.btn {
    min-width: 80px;
    font-size: 0.9rem;
    font-weight: 500;
    padding: 6px 12px;
    border-radius: 6px;
    transition: all 0.3s ease;
}

.btn:hover {
    transform: scale(1.05);
    opacity: 0.9;
}

/* Responsive improvement for small screens */
@media (max-width: 768px) {
    .table {
        font-size: 14px;
    }

    .btn {
        margin-bottom: 5px;
        width: 100%;
    }
}
</style>
<script setup>
import { ref, watch, onMounted, computed, reactive } from 'vue'
import { useRouter } from 'vue-router'
import createCrudService from '../../Configs/reusableCRUDService'
import PageNavigative from './PageNavigative.vue'
import FilterDropDown from './FilterDropDown.vue'
import 'bootstrap/dist/css/bootstrap.min.css'
const router = useRouter()

const currentPage = ref(0);
const currentSize = ref(5);
const totalPage = ref(0);
function updatePageSize(size) {
    currentSize.value = size
}
function updateCurrentPage(pageIndex) {
    currentPage.value = pageIndex
}

const props = defineProps({
    TableName: {
        type: String,
        required: true
    },
    FilterList: Object
})

const indexTableService = createCrudService(props.TableName);

function goToView(id) {
    router.push(`/Admin/${props.TableName}/view/${id}`)
}
function goToEdit(id) {
    router.push(`/Admin/${props.TableName}/update/${id}`)
}

function deleteById(id) {
    indexTableService.delete(id)
        .then(() => {
            console.log('Delete successful');
            fetchData(); // Refresh data after deletion
        })
        .catch(error => {
            console.error('Delete failed:', error);
        });
}
const data = ref([])
const columns = ref([])
const loading = ref(false)
const error = ref(null)
const fetchData = async () => {
    if (!props.TableName) return
    loading.value = true
    error.value = null
    try {
        const responseIndexTable = await indexTableService.getAll(currentPage.value, currentSize.value, props.FilterList)
        console.log(responseIndexTable)
        const json = responseIndexTable.data.content
        totalPage.value = responseIndexTable.data.totalPages
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
const totalPages = computed(() => {
    return Math.ceil(data.value.length / currentSize.value)
})

onMounted(fetchData)
watch(() => props.TableName, fetchData)
watch(() => currentSize.value, fetchData)
watch(() => currentPage.value, fetchData)
watch(
    () => props.FilterList,
    () => {
        fetchData()
    },
    { deep: true }
);
</script>

<style scoped>
table {
    border-collapse: collapse;
}
</style>
