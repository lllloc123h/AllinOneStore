<template>
  <!-- Header Section -->
  <div class="page-header">
    <div class="header-content">
      <h1 class="page-title">Tin tức khuyến mãi</h1>
      <p class="page-subtitle">CẬP NHẬT ƯU ĐÃI MỚI NHẤT TỪ ALL IN ONE STORE</p>
    </div>
  </div>

  <!-- Main Container -->
  <div class="main-container my-5">
    <!-- Loading State -->
    <!-- <div v-if="loading" class="loading-section">
      <div class="text-center py-5">
        <div class="spinner-border text-primary mb-3" role="status">
          <span class="visually-hidden">Đang tải...</span>
        </div>
        <p class="text-muted">Đang tải danh sách tin tức...</p>
      </div>
    </div> -->
    <Loading :loading="loading" />
    <div v-if="newsList.length === 0" class="empty-state">
      <div class="empty-icon">
        <i class="bi bi-newspaper"></i>
      </div>
      <h3>Chưa có tin tức khuyến mãi</h3>
      <p>Hãy quay lại sau để nhận thông tin ưu đãi mới nhất!</p>
    </div>

    <div class="news-list" v-else>
      <div class="news-card" v-for="item in newsList" :key="item.id">
        <div
          class="news-row"
          style="display: flex; justify-content: center; align-items: center; width: 100%"
        >
          <div
            class="news-image"
            style="flex: 0 0 160px; display: flex; align-items: center; padding: 15px"
          >
            <img
              :src="item.imageUrl"
              :alt="item.title"
              style="
                aspect-ratio: 4/5;
                object-fit: cover;
                border-radius: 20px;
                width: 160px;
                height: auto;
              "
            />
          </div>
          <div class="news-content" style="flex: 1; padding-left: 20px">
            <a
              class="news-title"
              :href="`/news/${item.id}`"
              style="text-decoration: none"
              >{{ item.title }}</a
            >
            <div class="news-description">
              <span
                v-if="
                  getShortText(item.description).length <
                  getPlainText(item.description).length
                "
                v-html="getShortText(item.description) + '...'"
              />
              <span v-else v-html="item.description" />
            </div>
            <div class="news-time">
              {{ formatDateTime(item.createdAt) }}
            </div>
          </div>
        </div>
      </div>
      <PageNavigative
        :total-page="totalPages"
        v-model:currentPage="pageIndex"
        v-model:currentSize="pageSize"
      />
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from "vue";
import { useRouter } from "vue-router";
import api from "../../Configs/api";
import PageNavigative from "../Module/PageNavigative.vue";
import Loading from "../Module/Loading.vue";
const newsList = ref([]);
const router = useRouter();
const totalPages = ref(0);
const pageIndex = ref(0);
const pageSize = ref(5);
const loading = ref(false);
const loadNews = async () => {
  loading.value = true;
  try {
    const res = await api.get("/News", {
      params: {
        page: pageIndex.value,
        size: pageSize.value,
      },
    });
    totalPages.value = res.data.totalPages || 0;
    newsList.value = Array.isArray(res.data) ? res.data : res.data.content || [];
  } catch (err) {
    console.error("Lỗi khi tải tin tức:", err);
  } finally {
    loading.value = false;
  }
};

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

function truncateHtml(html, maxLength) {
  if (!html) return "";
  const div = document.createElement("div");
  div.innerHTML = html;
  const text = div.textContent || div.innerText || "";
  if (text.length <= maxLength) return html;
  return text.slice(0, maxLength) + "...";
}

const goToDetail = (id) => {
  router.push({ name: "NewsDetail", params: { id } });
};

function getPlainText(html) {
  if (!html) return "";
  const div = document.createElement("div");
  div.innerHTML = html;
  return div.textContent || div.innerText || "";
}
function getShortText(html) {
  const text = getPlainText(html);
  if (text.length > 100) return text.slice(0, 100);
  return text;
}

onMounted(loadNews);
watch(() => pageIndex.value, loadNews);
watch(() => pageSize.value, loadNews);
</script>

<style scoped>
.loading-section {
  background: white;
  border-radius: 20px;
  padding: 3rem;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
}
.page-header {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 3rem 1rem;
  text-align: center;
  margin-bottom: 2rem;
}
.header-content {
  max-width: 600px;
  margin: 0 auto;
}
.page-title {
  color: white;
  font-size: 2.5rem;
  font-weight: 700;
  margin-bottom: 0.5rem;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.3);
}
.page-subtitle {
  color: rgba(255, 255, 255, 0.9);
  font-size: 1.1rem;
  margin: 0;
  font-weight: 400;
}
.main-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 1rem;
  min-height: 800px;
}
.empty-state {
  text-align: center;
  padding: 4rem 2rem;
  background: white;
  border-radius: 20px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
}
.empty-icon {
  font-size: 4rem;
  color: #bdc3c7;
  margin-bottom: 1rem;
}
.news-list {
  display: flex;
  flex-direction: column;
  gap: 2rem;
}
.news-card {
  display: flex;
  background: white;
  border-radius: 20px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);

  transition: box-shadow 0.3s;
}
.news-card:hover {
  box-shadow: 0 16px 40px rgba(102, 126, 234, 0.15);
}
.news-image {
  flex-shrink: 0;
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}
.news-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 20px 0 0 20px;
}
.news-content {
  flex: 1;
  padding: 1.5rem;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}
.news-title {
  color: #2c3e50;
  font-size: 1.3rem;
  font-weight: 700;
  margin-bottom: 0.5rem;
  cursor: pointer;
}
.news-title:hover {
  color: #1976d2;
}
.news-meta {
  color: #7f8c8d;
  font-size: 0.95rem;
  margin-bottom: 1rem;
}
.news-description {
  color: #495057;
  font-size: 1rem;
  margin-bottom: 1.5rem;
  min-height: 40px;
}
.btn-detail {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border: none;
  padding: 0.8rem 1.5rem;
  border-radius: 25px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
  display: inline-flex;
  align-items: center;
  font-size: 0.9rem;
}
.btn-detail:hover {
  box-shadow: 0 8px 25px rgba(102, 126, 234, 0.3);
}
@media (max-width: 768px) {
  .page-header {
    padding: 2rem 1rem;
  }
  .page-title {
    font-size: 2rem;
  }
  .news-card {
    flex-direction: column;
  }
  .news-image {
    width: 100%;
    height: 180px;
    border-radius: 20px 20px 0 0;
  }
  .news-content {
    padding: 1.2rem;
  }
}
@media (max-width: 480px) {
  .main-container {
    padding: 0 0.5rem;
  }
  .news-card {
    border-radius: 15px;
  }
  .news-image {
    height: 120px;
  }
}
</style>
