<template>
  <!-- Loading State -->
  <div v-if="loading" class="loading-section">
    <div class="text-center py-5">
      <div class="spinner-border text-primary mb-3" role="status">
        <span class="visually-hidden">Đang tải...</span>
      </div>
      <p class="text-muted">Đang tải dữ liệu...</p>
    </div>
  </div>
</template>
<style>
.loading-section {
  background: white;
  border-radius: 20px;
  padding: 3rem;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
}
</style>
<script setup>
defineProps({
  loading: {
    type: Boolean,
    default: false,
  },
});
const setLoading = (value) => {
  loading.value = value;
};
</script>
