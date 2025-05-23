<template>
    <div></div>
    <div class="container row">

        <aside class="col-3">aside</aside>
        <article class="col-9">
            <Table TableName="Accounts"></Table>
        </article>
    </div>
</template>
<script setup>
import { ref, watch, onMounted } from 'vue'
import 'bootstrap/dist/css/bootstrap.min.css'

import Table from '../Module/Table.vue'
</script>

<style scoped>
table {
    border-collapse: collapse;
}
</style>
