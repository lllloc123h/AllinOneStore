<template>
  <div>
    <UserHeader></UserHeader>
    <main>
      <router-view></router-view>
    </main>
    <footer>Footer</footer>
  </div>
</template>
<script setup>
import 'bootstrap/dist/js/bootstrap.bundle.min.js'
import 'bootstrap/dist/css/bootstrap.min.css'
import UserHeader from '../components/Module/UserHeader.vue';
</script>
