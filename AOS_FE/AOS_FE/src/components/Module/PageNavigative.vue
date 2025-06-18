<template>
    <div class="pageselect">
        <select id="pageSize" class="form-select" v-model="currentSize" @change="updatePageSize(currentSize)">
            <option :value="5">5</option>
            <option :value="10">10</option>
            <option :value="15">15</option>
        </select>

        <nav aria-label="Page navigation">
            <ul class="pagination">

                <!-- Previous -->
                <li class="page-item" :class="{ disabled: currentPage <= 0 }">
                    <button class="page-link" @click.prevent="updateCurrentPage(currentPage - 1)">
                        &laquo;
                    </button>
                </li>

                <!-- Page -1 -->
                <li class="page-item" v-if="currentPage > 0" :style="{ color: 'white' }">
                    <button class="page-link" @click.prevent="updateCurrentPage(currentPage - 1)">
                        {{ currentPage - 1 }}
                    </button>
                </li>

                <!-- Current Page -->
                <li class="page-item active">
                    <button class="page-link active" disabled>
                        {{ currentPage }}
                    </button>
                </li>

                <!-- Page +1 -->
                <li class="page-item" v-if="currentPage + 1 < props.totalPage">
                    <button class="page-link" @click.prevent="updateCurrentPage(currentPage + 1)">
                        {{ currentPage + 1 }}
                    </button>
                </li>

                <!-- Next -->
                <li class="page-item" :class="{ disabled: currentPage + 1 >= props.totalPage }">
                    <button class="page-link" @click.prevent="updateCurrentPage(currentPage + 1)">
                        &raquo;
                    </button>
                </li>

            </ul>
        </nav>
    </div>
</template>
<script setup>
import { ref, watch, defineEmits, defineProps } from 'vue'

const emits = defineEmits(['update:currentPage', 'update:currentSize'])

const props = defineProps({
    totalPage: Number,   // total number of pages
})

const currentPage = ref(0)
const currentSize = ref(5)

function updatePageSize(size) {
    currentSize.value = size
    emits('update:currentSize', size)
    currentPage.value = 0 // reset to first page
}

function updateCurrentPage(page) {
    if (page >= 0 && page <= props.totalPage) {
        currentPage.value = page
        emits('update:currentPage', page)
    }
}

watch(currentPage, (newPage) => {
    emits('update:currentPage', newPage)
})

watch(currentSize, (newSize) => {
    emits('update:currentSize', newSize)
})
</script>
<style scoped>
select {
    width: 300px;
}

button.page-link {
    border: none;
    padding: 15px 22px;
    margin: 0 5px;
    border-radius: 10px;
    background: white;
    color: #495057;
    font-weight: 500;
    box-shadow: 0 1px 4px rgba(0, 0, 0, 0.1);
    transition: all 0.3s ease;
}

.page-link.active .page-link {
    background: #007bff;
    color: white;
    transform: translateY(-3px);
    /* box-shadow: 0 5px 15px rgba(0, 123, 255, 0.4); */
}

button.page-link:hover {
    background: #007bff;
    color: white;
    box-shadow: rgba(0, 0, 0, 0.5) 0 10px 20px;
    transform: translateY(-3px);
    transition-duration: 0.6s;
    transition: all 300ms cubic-bezier(0.23, 1, 0.32, 1);
}

.pageselect {
    display: flex;
    align-content: center;
    justify-content: center;
}
</style>
