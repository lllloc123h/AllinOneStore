<template>
  <div>
    <AdminHeader></AdminHeader>
    <router-view></router-view>

    <footer>Footer</footer>
  </div>
</template>
<script setup>
import AdminHeader from '../components/Module/AdminHeader.vue';
</script>
