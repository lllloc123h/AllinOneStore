<script setup>
import ImageUpload from './components/Module/ImageUpload.vue';
import Login from './components/Module/Login.vue';
import Register from './components/Module/Register.vue';
import ShippingAddress from './components/Module/ShippingAddress.vue';
</script>

<template>
  <router-view />
  <shipping-address />
</template>
