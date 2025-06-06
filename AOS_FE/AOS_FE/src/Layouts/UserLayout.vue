<template>
  <div>
    <UserHeader></UserHeader>
    <main>
      <router-view></router-view>
    </main>
    <UserFooter></UserFooter>
  </div>
</template>
<script setup>
import 'bootstrap/dist/js/bootstrap.bundle.min.js'
import 'bootstrap/dist/css/bootstrap.min.css'
import UserHeader from '../components/Module/UserHeader.vue';
import UserFooter from '../components/Module/UserFooter.vue'
</script>
