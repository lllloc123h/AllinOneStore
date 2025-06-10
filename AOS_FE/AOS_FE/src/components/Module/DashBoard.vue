<template>
    <div class="container-fluid vh-100">
        <div class="row h-100">
            <nav class="col-12 bg-dark text-white p-3 d-flex flex-column justify-content-start">
                <div class="mb-12">
                    <div v-for="(items, index) in props.listDashBoard" class="mb-3">
                        <button @click="goToView(items)" class="nav-link text-white">
                            {{ items }}
                        </button>


                    </div>
                </div>
            </nav>


        </div>
    </div>
</template>


<script setup>
import { ref, watch, onMounted } from 'vue'
import { useRouter } from 'vue-router'
const router = useRouter()
import 'bootstrap/dist/css/bootstrap.min.css'
const props = defineProps({
    listDashBoard: {
        type: Array,
        required: true
    }
})

function goToView(tableName) {
    router.push(`/Admin/${tableName}`)
}
</script>
