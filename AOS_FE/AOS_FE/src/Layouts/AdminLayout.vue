<template>
  <div>
    <AdminHeader></AdminHeader>
    <router-view class="mt-5"></router-view>
    <footer>Footer</footer>
  </div>
</template>
<script setup>
import AdminHeader from "../components/Module/AdminHeader.vue";
</script>
