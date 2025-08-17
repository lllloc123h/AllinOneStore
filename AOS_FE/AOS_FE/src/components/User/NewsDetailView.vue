<template>
  <div class="news-detail-container">
    <button class="back-btn" @click="goBack">
      <i class="bi bi-arrow-left"></i> Quay lại
    </button>
    <div v-if="loading" class="loading-state">
      <i class="bi bi-hourglass-split"></i> Đang tải nội dung...
    </div>
    <div v-else-if="!news" class="empty-state">
      <i class="bi bi-exclamation-circle"></i> Không tìm thấy tin tức!
    </div>
    <div v-else class="news-detail-card">
      <div class="news-detail-header">
        <h1 class="news-detail-title">{{ news.title }}</h1>
        <div class="news-detail-meta">
          <span class="news-detail-time">
            <i class="bi bi-calendar-event me-2"></i>
            {{ formatDateTime(news.createdAt) }}
          </span>
        </div>
      </div>
      <div class="news-detail-image" v-if="news.imageUrl">
        <img :src="news.imageUrl" :alt="news.title" />
      </div>
      <div class="news-detail-content" v-html="news.description"></div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import api from "../../Configs/api";

const route = useRoute();
const router = useRouter();
const news = ref(null);
const loading = ref(true);

const formatDateTime = (d) => {
  const date = new Date(d);
  return date.toLocaleString("vi-VN", {
    year: "numeric",
    month: "2-digit",
    day: "2-digit",
    hour: "2-digit",
    minute: "2-digit",
    second: "2-digit",
  });
};

const goBack = () => {
  router.go(-1);
};

onMounted(async () => {
  try {
    const id = route.params.id;
    const res = await api.get(`/News/${id}`);
    news.value = res.data;
  } catch (err) {
    news.value = null;
  } finally {
    loading.value = false;
  }
});
</script>

<style scoped>
.back-btn {
  position: absolute;
  top: 24px;
  left: 32px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border: none;
  border-radius: 25px;
  padding: 10px 22px;
  font-size: 1rem;
  font-weight: 600;
  box-shadow: 0 4px 16px rgba(102, 126, 234, 0.18);
  cursor: pointer;
  display: flex;
  align-items: center;
  gap: 8px;
  z-index: 10;
  transition: background 0.2s, box-shadow 0.2s;
}
.back-btn:hover {
  background: linear-gradient(135deg, #5a67d8 0%, #6b46c1 100%);
  box-shadow: 0 8px 32px rgba(102, 126, 234, 0.25);
}
.news-detail-container {
  position: relative;
  max-width: 100vw;
  min-height: 100vh;
  margin: 0;
  background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
  padding: 0;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;
}
.news-detail-card {
  background: white;
  border-radius: 24px;
  box-shadow: 0 10px 40px rgba(102, 126, 234, 0.13);
  padding: 3rem 2rem;
  margin-top: 60px;
  margin-bottom: 40px;
  max-width: 1000px;
  width: 100%;
  position: relative;
  animation: fadeInUp 0.5s;
}
/* @keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(40px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
} */
.news-detail-header {
  text-align: center;
  margin-bottom: 2rem;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 18px;
  padding: 1.5rem 1rem 1rem 1rem;
  box-shadow: 0 4px 16px rgba(102, 126, 234, 0.1);
}
.news-detail-title {
  font-size: 2.2rem;
  font-weight: 700;
  color: white;
  margin-bottom: 0.5rem;
  text-shadow: 0 2px 8px rgba(44, 62, 80, 0.18);
}
.news-detail-meta {
  color: #e3e7fa;
  font-size: 1rem;
}
.news-detail-time {
  background: linear-gradient(135deg, #e3e7fa 0%, #c3cfe2 100%);
  color: #2c3e50;
  padding: 6px 18px;
  border-radius: 18px;
  font-weight: 600;
  font-size: 1rem;
  display: inline-flex;
  align-items: center;
  gap: 6px;
  box-shadow: 0 2px 8px rgba(102, 126, 234, 0.08);
  margin-top: 8px;
}
.news-detail-image {
  text-align: center;
  margin-bottom: 2rem;
}
.news-detail-image img {
  max-width: 400px;
  aspect-ratio: 4/5;
  object-fit: cover;
  border-radius: 18px;
  box-shadow: 0 8px 32px rgba(102, 126, 234, 0.18);
  background: #f8f9fa;
}

.news-detail-content {
  color: #495057;
  font-size: 1.15rem;
  line-height: 1.8;
  margin-top: 1.5rem;
  word-break: break-word;
  padding: 1.2rem;
  background: #f8f9fa;
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(102, 126, 234, 0.07);
}
.loading-state,
.empty-state {
  text-align: center;
  color: #888;
  font-size: 1.2rem;
  padding: 3rem 0;
}
@media (max-width: 768px) {
  .news-detail-card {
    padding: 2rem 0.5rem;
    margin-top: 40px;
  }
  .news-detail-title {
    font-size: 1.4rem;
  }
  .news-detail-image img {
    max-width: 90vw;
  }
}
</style>
