<template>Doi ti dang load ...</template>
<script setup>
import router from "/src/router";
import { onMounted } from "vue";
import { useRoute } from "vue-router";
import { authService } from "../../Configs/api";

onMounted(() => {
  const route = useRoute();
  const token = route.query.token;
  const cartSize = route.query.cartSize || 0;
  if (token) {
    localStorage.setItem("jwtToken", token);
    authService.setTokenRef(token);
    authService.setCart(cartSize);
    const redirect = localStorage.getItem("redirectTo") || "/";
    console.log("Đăng nhập thành công với với oauth2");
    router.push(redirect);
  }
});
</script>
// const route = useRoute() // const token = route.query.token // if (token) { //
localStorage.setItem('token',token) // const redirect = localStorage.getItem('redirectTo')
|| '/' // router.push(redirect) // }
