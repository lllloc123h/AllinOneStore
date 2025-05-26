<template>
    <div class="container-fluid vh-100">
        <div class="row h-100">

            <!-- Sidebar with 3 RouterLinks, stacked vertically -->
            <nav class="col-12 bg-dark text-white p-3 d-flex flex-column justify-content-start">
                <h3 class="mb-4">Admin Panel</h3>

                <!-- Each link in its own row -->
                <div class="mb-12">

                    <div v-for="(items, index) in props.listDashBoard"  class="mb-3">
                        <button @click="goToView(items)" class="nav-link text-white">
                            {{items}}
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
