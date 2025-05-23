<template>
    <form @submit.prevent="submitForm" v-if="columns.length" class="p-3 border rounded bg-light">
        <div v-for="key in columns" :key="key" class="mb-3">
            <label :for="key" class="form-label text-capitalize">{{ key }}</label>
            <input :id="key" v-model="formData[key]" type="text" class="form-control" :placeholder="`Enter ${key}`" />
        </div>
        <button type="submit" class="btn btn-primary">Submit</button>
    </form>

    <div v-else-if="loading" class="text-info">Loading...</div>
    <div v-else-if="error" class="text-danger">{{ error }}</div>

</template>


<script setup>
import { reactive, ref, onMounted, watch } from 'vue'
import 'bootstrap/dist/js/bootstrap.bundle.min.js'
import 'bootstrap/dist/css/bootstrap.min.css'

const props = defineProps({
    TableName: {
        type: String,
        required: true
    },
    id: {
        type: String,
        required: true
    }
})

const data = ref([])
const columns = ref([])
const loading = ref(false)
const error = ref(null)

// formData will be reactive and hold form inputs
const formData = reactive({})

const fetchData = async () => {

    console.log(props.TableName + props.id)
    if (!props.TableName) return
    loading.value = true
    error.value = null

    try {
        const response = await fetch(`http://localhost:8080/Api/Admin/${props.TableName}/${props.id}`)
        if (!response.ok) throw new Error('Failed to fetch data')

        const json = await response.json()
        data.value = Array.isArray(json) ? json : [json]

        if (data.value.length) {
            columns.value = Object.keys(data.value[0])

            // Initialize formData with empty strings or first item's values
            columns.value.forEach(key => {
                formData[key] = data.value[0][key] ?? ''
            })
        } else {
            columns.value = []
        }
    } catch (err) {
        error.value = err.message
        data.value = []
        columns.value = []
    } finally {
        loading.value = false
    }
}

function submitForm() {
    alert('Submitted form data:\n' + JSON.stringify(formData, null, 2))
    // Send formData to API or handle as needed
}

onMounted(fetchData)
watch(() => props.TableName, fetchData)
watch(() => props.id, fetchData)
</script>
