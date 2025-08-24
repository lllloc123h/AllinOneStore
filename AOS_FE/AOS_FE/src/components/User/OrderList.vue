<template>
  <!-- Header Section -->
  <div class="page-header">
    <div class="header-content">
      <h1 class="page-title">Đơn hàng của tôi</h1>
      <p class="page-subtitle">THEO DÕI TRẠNG THÁI ĐƠN HÀNG CỦA BẠN</p>
    </div>
  </div>
  <Loading :loading="loading" />
  <!-- Main Container -->
  <div v-if="!loading" class="main-container my-5">
    <!-- Order Tabs -->
    <div class="order-tabs mb-4" v-if="orders.length > 0">
      <button
        v-for="tab in tabs"
        :key="tab"
        :class="['tab-button', { active: selectedTab === tab }]"
        @click="selectedTab = tab"
      >
        {{ tab }}
      </button>
    </div>

    <!-- Content Area with fixed height -->
    <div class="content-area">
      <!-- Empty State -->
      <div v-if="filteredOrders.length === 0" class="empty-state">
        <div class="empty-icon">
          <i class="bi bi-bag-x"></i>
        </div>
        <h3>Không có đơn hàng</h3>
        <p>Không có đơn hàng ở trạng thái "{{ selectedTab }}"</p>
        <button class="btn-shop-now" @click="$router.push('/products')">
          <i class="bi bi-bag-plus me-2"></i>Mua sắm ngay
        </button>
      </div>

      <!-- Orders List -->
      <div class="orders-list" v-if="orders.length > 0 && filteredOrders.length > 0">
        <div class="order-card" v-for="order in filteredOrders" :key="order.id">
          <!-- Order Header -->
          <div class="order-header">
            <div class="order-info">
              <h3 class="order-code">
                <i class="bi bi-receipt me-2"></i>{{ order.maVanDon }}
              </h3>
              <div class="order-meta">
                <span class="order-date">
                  <i class="bi bi-calendar3 me-1"></i>{{ formatDate(order.ngayDat) }}
                </span>
                <span class="order-status" :class="getStatusClass(order.trangThai)">
                  <i class="bi bi-circle-fill me-1"></i>{{ order.trangThai }}
                </span>
              </div>
            </div>
            <div class="order-total">
              <span class="total-label">Tổng tiền</span>
              <span class="total-amount">{{ formatMoney(order.tongTien) }}</span>
            </div>
            <div class="order-actions">
              <button
                v-if="order.paymentStatus === 'Chưa thanh toán'"
                class="btn-pay"
                @click="pay(order)"
              >
                <i class="bi bi-credit-card me-2"></i>Thanh toán
              </button>
            </div>
          </div>

          <!-- Products Section -->
          <div class="products-section">
            <h4 class="section-title">
              <i class="bi bi-box me-2"></i>Sản phẩm ({{ order.sanPham.length }})
            </h4>
            <div class="products-grid">
              <div v-for="(sp, i) in order.sanPham" :key="i" class="product-item">
                <div class="product-image">
                  <img :src="sp.anh" :alt="sp.ten" />
                </div>
                <div class="product-info">
                  <h5 class="product-name">{{ sp.ten }}</h5>
                  <div class="product-details">
                    <span class="sku">Mã sản phẩm: {{ sp.sku }}</span>
                    <span class="quantity">SL: {{ sp.soLuong }}</span>
                    <span class="price">{{ formatMoney(sp.gia) }}</span>
                  </div>
                  <div class="product-total">
                    Thành tiền: <strong>{{ formatMoney(sp.gia * sp.soLuong) }}</strong>
                  </div>
                  <!-- Nút đánh giá -->
                  <button
                    v-if="
                      !sp.daDanhGia &&
                      order.trangThai === 'Đã nhận hàng' &&
                      selectedTab === 'Đã nhận hàng'
                    "
                    class="btn btn-outline-primary mt-2"
                    @click="toggleReviewForm(order.id, sp.productItemId, sp)"
                  >
                    Đánh giá
                  </button>

                  <!-- Dòng đã đánh giá -->
                  <span
                    v-else-if="
                      sp.daDanhGia &&
                      order.trangThai === 'Đã nhận hàng' &&
                      selectedTab === 'Đã nhận hàng'
                    "
                    class="text-success mt-2 d-block"
                  >
                    Đã đánh giá
                  </span>
                  <button
                    v-if="sp.daDanhGia"
                    class="btn btn-link text-primary p-0 mt-1"
                    @click="toggleViewReview(order.id, sp.productItemId)"
                  >
                    Xem đánh giá
                  </button>
                  <!-- Form đánh giá -->
                  <transition name="tab-panel">
                    <div
                      v-if="activeReviewKey === `${order.id}_${sp.productItemId}`"
                      class="review-form-card"
                    >
                      <h5 class="form-title">Viết đánh giá của bạn</h5>
                      <form
                        @submit.prevent="submitReview(sp, order.id)"
                        class="review-form"
                      >
                        <div class="rating-input">
                          <label class="form-label">Đánh giá của bạn:</label>
                          <div class="star-rating">
                            <i
                              v-for="star in 5"
                              :key="star"
                              @click="newReview.rating = star"
                              :class="[
                                'bi',
                                star <= newReview.rating ? 'bi-star-fill' : 'bi-star',
                                'star-button',
                              ]"
                            ></i>
                          </div>
                        </div>

                        <div class="form-group">
                          <label class="form-label">Nội dung đánh giá:</label>
                          <textarea
                            class="form-textarea"
                            rows="4"
                            v-model="newReview.text"
                            placeholder="Chia sẻ trải nghiệm của bạn về sản phẩm này..."
                            required
                          ></textarea>
                        </div>
                        <div class="form-group">
                          <UploadImages
                            :maxFiles="5"
                            :aspectRatio="'4:5'"
                            :titleUpload="'đánh giá sản phẩm'"
                            @uploaded="handleImageUploaded"
                          />
                          <!-- Nút thêm video -->
                          <button
                            type="button"
                            class="btn btn-outline-secondary mt-2"
                            @click="showVideoUpload = !showVideoUpload"
                          >
                            <i class="bi bi-camera-video me-1"></i>
                            {{ showVideoUpload ? "Ẩn video" : "Thêm video" }}
                          </button>

                          <!-- Form upload video -->
                          <div v-if="showVideoUpload" class="form-group mt-2">
                            <UploadVideos
                              type="video"
                              folderName="reviews"
                              @uploaded="handleVideoUploaded"
                            />
                          </div>
                        </div>
                        <button type="submit" class="submit-review-btn">
                          <i class="bi bi-send me-2"></i>
                          Gửi đánh giá
                        </button>
                      </form>
                    </div>
                  </transition>
                  <transition name="fade">
                    <div
                      v-if="
                        activeViewReviewKey === `${order.id}_${sp.productItemId}` &&
                        sp.review
                      "
                      class="review-view-card mt-3"
                    >
                      <h6 class="mb-2">Đánh giá đã gửi</h6>

                      <!-- Số sao -->
                      <div class="rating-display mb-2">
                        <i
                          v-for="star in 5"
                          :key="star"
                          :class="[
                            'bi',
                            star <= sp.review.rating ? 'bi-star-fill' : 'bi-star',
                            'text-warning',
                          ]"
                        ></i>
                      </div>

                      <!-- Nội dung -->
                      <p class="review-text">{{ sp.review.text }}</p>

                      <!-- Ảnh -->
                      <div v-if="sp.review.images?.length" class="review-images mt-2">
                        <img
                          v-for="(img, index) in sp.review.images"
                          :key="index"
                          :src="img"
                          class="review-img"
                          alt="Ảnh đánh giá"
                          style="max-width: 120px; margin-right: 8px; border-radius: 6px"
                        />
                      </div>

                      <!-- Video -->
                      <div v-if="sp.review.video" class="review-video mt-2">
                        <video
                          controls
                          :src="sp.review.video"
                          style="width: 100%; max-width: 400px; border-radius: 8px"
                        ></video>
                      </div>
                    </div>
                  </transition>
                </div>
              </div>
            </div>
          </div>
          <div v-if="payURL && showPopUp" class="modal-overlay" @click.self="closePopup">
            <div class="payment-modal">
              <div class="modal-header">
                <h3><i class="bi bi-credit-card me-2"></i>Thanh toán MoMo</h3>
                <button class="close-btn" @click="closePopup">×</button>
              </div>
              <div class="modal-body">
                <p>Nhấn vào nút bên dưới để mở trang thanh toán MoMo.</p>
                <div class="payment-actions">
                  <a :href="payURL" target="_blank" class="btn-payment">
                    <i class="bi bi-credit-card me-2"></i>Mở trang thanh toán
                  </a>
                  <button class="btn-cancel" @click="closePopup">
                    <i class="bi bi-x-circle me-2"></i>Đóng
                  </button>
                </div>
              </div>
            </div>
          </div>
          <!-- HTML: Hiển thị danh sách thiết kế -->
          <div v-if="order.customs && order.customs.length" class="designs-section">
            <h4 class="section-title">
              <i class="bi bi-palette me-2"></i>Thiết kế đã chọn
            </h4>
            <div class="design-list">
              <div v-for="item in order.customs" :key="item.id" class="design-card">
                <div class="design-img">
                  <img :src="item?.imageUrl" :alt="item?.designName" />
                </div>
                <div class="design-info">
                  <div class="design-name">{{ item?.designName }}</div>
                  <div class="design-qty">
                    Số lượng: <strong>{{ item?.qty }}</strong>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <!-- Order Actions -->
          <div class="order-actions">
            <button class="btn-detail" @click="goToOrder(order.id)">
              <i class="bi bi-eye me-2"></i>Xem chi tiết
            </button>
            <button class="btn-reorder" @click="reorder(order)">
              <i class="bi bi-arrow-repeat me-2"></i>Đặt lại
            </button>
          </div>
        </div>
      </div>
    </div>
    <!-- Close content-area -->
  </div>
</template>

<script setup>
import { useRouter } from "vue-router";
import api from "../../Configs/api";
import { ref, onMounted, computed } from "vue";
import { notification } from "ant-design-vue";
import UploadImages from "../Module/upload-images.vue";
import UploadVideos from "../Module/upload-single-img-video.vue";
import Loading from "../Module/Loading.vue";
const loading = ref(false);
const orders = ref([]);
const router = useRouter();
const showPopUp = ref(false);
const payURL = ref("");
// Gọi API lấy danh sách đơn hàng người dùng
const loadOrders = async () => {
  loading.value = true;
  try {
    const res = await api.get("/user/Orders");

    const data = Array.isArray(res.data) ? res.data : res.data.content || [];
    console.log("Orders response:", data);
    const result = [];

    for (const order of data) {
      const sanPham = [];

      for (const i of order.items || []) {
        const sp = {
          productItemId: i.productItemId,
          orderId: order.id,
          anh: i.main_image_url || "no-image.png",
          ten: i.name,
          soLuong: i.quantity,
          gia: i.price,
          sku: i.product?.sku,
          daDanhGia: false, // mặc định
        };

        try {
          const reviewRes = await api.get("/user/reviews/check", {
            params: {
              productItemId: sp.productItemId,
              orderId: sp.orderId,
            },
          });
          sp.daDanhGia = reviewRes.data.hasReviewed;
          if (sp.daDanhGia) {
            const detailRes = await api.get("/user/reviews/detail", {
              params: {
                productItemId: sp.productItemId,
                orderId: sp.orderId,
              },
            });

            sp.review = {
              rating: detailRes.data.rating,
              text: detailRes.data.comment,
              images: [
                detailRes.data.imageUrl1,
                detailRes.data.imageUrl2,
                detailRes.data.imageUrl3,
              ].filter(Boolean),
              video: detailRes.data.videoUrl || null,
            };
          }
        } catch (err) {
          console.warn("Không thể kiểm tra đánh giá:", err);
        }

        sanPham.push(sp);
      }
      console.log("customs: ", order.customs);

      result.push({
        id: order.id,
        maDon: order.orderCode,
        ngayDat: order.createdAt,
        trangThai: translateStatus(order.shippingStatus),
        paymentMethodId: order.paymentMethodId,
        paymentStatus: order.paymentStatus,
        maVanDon: order.orderCode || "Đang cập nhật",
        tongTien: order.finalTotal,
        sanPham: sanPham,
        customs: order.customs || [],
      });
    }

    orders.value = result;
  } catch (err) {
    console.error("Lỗi khi tải đơn hàng:", err);
  } finally {
    loading.value = false;
  }
};

const goToOrder = (id) => {
  router.push({ name: "OrderStatus", params: { id } });
};

const reorder = (order) => {
  // Logic để đặt lại đơn hàng
  console.log("Reorder:", order);
  // Có thể redirect đến trang cart với sản phẩm từ đơn hàng này
};

const getStatusClass = (status) => {
  const statusMap = {
    "Đã xác nhận": "status-confirmed",
    "Đang giao": "status-shipping",
    "Đã giao": "status-delivered",
    "Đã hủy": "status-cancelled",
    "Hoàn trả": "status-returned",
  };
  return statusMap[status] || "status-default";
};

const formatDate = (d) => {
  const date = new Date(d);
  return date.toLocaleString("vi-VN", {
    hour: "2-digit",
    minute: "2-digit",
    second: "2-digit",
    day: "2-digit",
    month: "2-digit",
    year: "numeric",
  });
};
const formatMoney = (v) => Number(v).toLocaleString("vi-VN") + "đ";

onMounted(loadOrders);

const selectedTab = ref("Chờ xác nhận");

const tabs = [
  "Chờ thanh toán",
  "Chờ xác nhận",
  "Chờ lấy hàng",
  "Chờ giao hàng",
  "Đang giao hàng",
  "Đã nhận hàng",
  "Đã hủy",
];

const filteredOrders = computed(() => {
  const tabMap = {
    "Chờ thanh toán": "Chưa thanh toán",
    "Chờ xác nhận": "Chờ xác nhận",
    "Chờ lấy hàng": "Chờ lấy hàng",
    "Chờ giao hàng": "Chờ giao hàng",
    "Đang giao hàng": "Đang giao hàng",
    "Đã nhận hàng": "Đã nhận hàng",
    "Đã hủy": "Đã hủy",
  };
  const status = tabMap[selectedTab.value];
  console.log(orders.value);
  console.log("Filtering orders for status:", status, selectedTab.value);

  if (selectedTab.value === "Chờ thanh toán") {
    console.log("Filtering for unpaid orders");
    return orders.value.filter(
      (o) => o.paymentStatus === "Chưa thanh toán" && o.paymentMethodId === 2
    );
  } else {
    if (
      orders.value.filter(
        (o) => o.paymentStatus === "Chưa thanh toán" && o.paymentMethodId === 2
      ).length > 0
    ) {
      console.warn("Có đơn hàng chưa thanh toán, nhưng đang lọc theo trạng thái khác");
      return [];
    }
    console.log("Filtering for orders with status:", status);
    return orders.value.filter((o) => o.trangThai === status);
  }
});

console.log("Filtered orders:", filteredOrders);

async function pay(orderID) {
  try {
    console.log("Order ID:", orderID);
    console.log("Order ID:", orderID.id);
    const payLoad = { orderId: orderID.id, finalToTal: orderID.tongTien };

    console.log("Order ID:", payLoad);
    console.log("Order ID:", orderID.id);
    const response = await api.post(`/e-wallet/orderpay`, payLoad);
    console.log("Payment URL:", response.data);
    payURL.value = response.data.payUrl;
    showPopUp.value = true;
  } catch (err) {
    console.error();
  }
}
const translateStatus = (status) => {
  const map = {
    pending: "Chờ xác nhận",
    // GHN: Chờ lấy hàng
    ready_to_pick: "Chờ lấy hàng",
    picking: "Chờ lấy hàng",
    money_collect_picking: "Chờ lấy hàng",

    // GHN: Chờ giao hàng
    picked: "Chờ giao hàng",
    // đang giao hàng
    storing: "Đang giao hàng",
    sorting: "Đang giao hàng",
    transporting: "Đang giao hàng",
    delivering: "Đang giao hàng",
    money_collect_delivering: "Đang giao hàng",

    // GHN: Đã nhận
    delivered: "Đã nhận hàng",

    // GHN: Đã hủy / trả hàng
    cancel: "Đã hủy",
    return: "Đã hủy",
    returning: "Đã hủy",
    returned: "Đã hủy",
    return_sorting: "Đã hủy",
    return_transporting: "Đã hủy",
    lost: "Đã hủy",
    damage: "Đã hủy",
    delivery_fail: "Đã hủy",
    exception: "Đã hủy",
  };
  return map[status] || status;
};

const activeReviewKey = ref(null);
const reviewImageUrl = ref([]);

const newReview = ref({
  rating: 0,
  text: "",
  imageUrl: "",
  videoUrl: "",
});

const toggleReviewForm = (orderId, productItemId, sp) => {
  if (sp.daDanhGia) return;

  const key = `${orderId}_${productItemId}`;
  activeReviewKey.value = activeReviewKey.value === key ? null : key;

  newReview.value = {
    rating: 5,
    text: "",
    imageUrl: "",
  };
};

const activeReviewIndex = ref(null);
async function submitReview(sp, orderId) {
  if (!newReview.value.text) return;

  try {
    await api.post("/user/Reviews", {
      productItems: sp.productItemId,
      orderId: orderId,
      rating: newReview.value.rating,
      comment: newReview.value.text,
      imageUrl1: reviewImageUrl.value[0] || null,
      imageUrl2: reviewImageUrl.value[1] || null,
      imageUrl3: reviewImageUrl.value[2] || null,
      videoUrl: newReview.value.videoUrl || null,
    });

    notification.success({
      message: "Gửi đánh giá thành công",
      description: "Cảm ơn bạn đã đánh giá sản phẩm!",
      duration: 2.5,
    });
    showVideoUpload.value = false;
    sp.review = {
      rating: newReview.value.rating,
      text: newReview.value.text,
      images: reviewImageUrl.value,
      video: newReview.value.videoUrl,
    };
    sp.daDanhGia = true;
    activeReviewKey.value = null;

    newReview.value.text = "";
    newReview.value.rating = 5;
    reviewImageUrl.value = "";
    uploaderKey.value = Date.now();
    activeReviewIndex.value = null;

    await loadOrders();
  } catch (err) {
    notification.error({
      message: "Lỗi gửi đánh giá",
      description: "Vui lòng thử lại sau!",
      duration: 4.5,
    });
    console.error(err);
  }
}

const uploaderKey = ref(Date.now());

function handleImageUploaded(urls) {
  reviewImageUrl.value = urls;
  newReview.value.imageUrl = urls.join(",");
}

const reviewVideoUrl = ref([]);
function handleVideoUploaded(files) {
  const urls = Array.isArray(files)
    ? files.map((f) => (typeof f === "string" ? f : f.url))
    : [];

  reviewVideoUrl.value = urls;
  newReview.value.videoUrl = urls.join(",");
}

const activeViewReviewKey = ref(null);
function toggleViewReview(orderId, productItemId) {
  const key = `${orderId}_${productItemId}`;
  activeViewReviewKey.value = activeViewReviewKey.value === key ? null : key;
}

const showVideoUpload = ref(false);
</script>

<style scoped>
/* CSS: Đặt vào cuối file hoặc trong <style scoped> */
.design-list {
  display: flex;
  flex-wrap: wrap;
  gap: 24px;
  margin: 24px 0;
}

.design-card {
  background: #fff;
  border-radius: 16px;
  box-shadow: 0 4px 16px rgba(102, 126, 234, 0.08);
  display: flex;
  align-items: center;
  padding: 18px 24px;
  min-width: 260px;
  max-width: 320px;
  gap: 18px;
  transition: box-shadow 0.2s;
}

.design-img img {
  width: 80px;
  height: auto;
  object-fit: cover;
  border-radius: 12px;
  border: 2px solid #f1f1f1;
  background: #f8f9fa;
}

.design-info {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.design-name {
  font-size: 1.1rem;
  font-weight: 600;
  color: #2c3e50;
  margin-bottom: 2px;
}

.design-qty {
  font-size: 1rem;
  color: #667eea;
}
/* Page Header */
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
/* Thêm vào cuối file CSS của ProductsView.vue hoặc trong phần .page-header */
.page-header {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 3rem 0 2rem;
  color: white;
  position: relative;
  overflow: hidden;
}

/* Hiệu ứng sóng động */
.page-header::before {
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: url('data:image/svg+xml,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1000 100" fill="rgba(255,255,255,0.1)"><path d="M0,20 Q250,80 500,20 T1000,20 L1000,0 L0,0 Z"/></svg>')
    repeat-x;
  background-size: 1000px 100px;
  animation: wave 10s infinite linear;
  z-index: 0;
}

@keyframes wave {
  0% {
    background-position-x: 0;
  }
  100% {
    background-position-x: 1000px;
  }
}

.header-content {
  position: relative;
  z-index: 1;
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

/* Main Container */
.main-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 1rem;
  min-height: 800px;
}

/* Content Area - Fixed height to prevent layout shift */
.content-area {
  min-height: 600px;
  transition: all 0.3s ease;
}

/* Empty State */
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

.empty-state h3 {
  color: #2c3e50;
  font-size: 1.5rem;
  margin-bottom: 0.5rem;
}

.empty-state p {
  color: #7f8c8d;
  margin-bottom: 2rem;
}

.btn-shop-now {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border: none;
  padding: 1rem 2rem;
  border-radius: 25px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
  display: inline-flex;
  align-items: center;
}

.btn-shop-now:hover {
  box-shadow: 0 8px 25px rgba(102, 126, 234, 0.3);
}

/* Orders List */
.orders-list {
  display: flex;
  flex-direction: column;
  gap: 2rem;
}

.order-card {
  background: white;
  border-radius: 20px;
  padding: 2rem;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
  border: 2px solid transparent;
}

/* Order Header */
.order-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 2rem;
  padding-bottom: 1rem;
  border-bottom: 2px solid #f8f9fa;
}

.order-info {
  flex: 1;
}

.order-code {
  color: #2c3e50;
  font-size: 1.3rem;
  font-weight: 700;
  margin: 0 0 0.5rem 0;
  display: flex;
  align-items: center;
}

.order-meta {
  display: flex;
  gap: 1rem;
  flex-wrap: wrap;
}

.order-date {
  color: #7f8c8d;
  font-size: 0.95rem;
  display: flex;
  align-items: center;
}

.order-status {
  padding: 0.4rem 1rem;
  border-radius: 20px;
  font-size: 0.85rem;
  font-weight: 600;
  display: flex;
  align-items: center;
}

.status-processing {
  background: #fff3cd;
  color: #856404;
}

.btn-pay {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border: none;
  padding: 0.8rem 1.5rem;
  border-radius: 25px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
}

.btn-pay:hover {
  box-shadow: 0 8px 25px rgba(102, 126, 234, 0.3);
}

.status-confirmed {
  background: #d4edda;
  color: #155724;
}

.status-shipping {
  background: #cce7ff;
  color: #004085;
}

.status-delivered {
  background: #d1ecf1;
  color: #0c5460;
}

.status-cancelled {
  background: #f8d7da;
  color: #721c24;
}

.status-returned {
  background: #e2e3e5;
  color: #383d41;
}

.status-default {
  background: #f8f9fa;
  color: #6c757d;
}

.order-total {
  text-align: right;
}

.total-label {
  display: block;
  color: #7f8c8d;
  font-size: 0.9rem;
  margin-bottom: 0.3rem;
}

.total-amount {
  display: block;
  color: #e74c3c;
  font-size: 1.5rem;
  font-weight: 700;
}

/* Products Section */
.products-section {
  margin-bottom: 2rem;
}

.section-title {
  color: #2c3e50;
  font-size: 1.1rem;
  font-weight: 600;
  margin-bottom: 1rem;
  display: flex;
  align-items: center;
}

.products-grid {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.product-item {
  display: flex;
  gap: 1rem;
  padding: 1rem;
  background: #f8f9fa;
  border-radius: 15px;
}

.product-image {
  flex-shrink: 0;
}

.product-image img {
  width: 80px;
  height: 100px;
  object-fit: cover;
  border-radius: 10px;
  border: 2px solid white;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.product-info {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.product-name {
  color: #2c3e50;
  font-size: 1rem;
  font-weight: 600;
  margin: 0 0 0.5rem 0;
  line-height: 1.3;
}

.product-details {
  display: flex;
  gap: 1rem;
  margin-bottom: 0.5rem;
}

.quantity {
  background: #667eea;
  color: white;
  padding: 0.2rem 0.8rem;
  border-radius: 15px;
  font-size: 0.85rem;
  font-weight: 600;
}

.price {
  color: #7f8c8d;
  font-size: 0.9rem;
}

.product-total {
  color: #2c3e50;
  font-size: 0.95rem;
}

/* Payment Modal */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.6);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
  padding: 1rem;
}

.payment-modal {
  background: white;
  border-radius: 20px;
  max-width: 400px;
  width: 100%;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
  animation: modalSlideIn 0.3s ease;
}

@keyframes modalSlideIn {
  from {
    opacity: 0;
    transform: translateY(-30px) scale(0.95);
  }

  to {
    opacity: 1;
    transform: translateY(0) scale(1);
  }
}

.modal-header {
  padding: 1.5rem 2rem;
  border-bottom: 1px solid #e9ecef;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.modal-header h3 {
  margin: 0;
  font-size: 1.3rem;
  font-weight: 600;
  color: #2c3e50;
  display: flex;
  align-items: center;
}

.close-btn {
  background: none;
  border: none;
  font-size: 1.5rem;
  color: #7f8c8d;
  cursor: pointer;
  padding: 0.5rem;
  border-radius: 50%;
  transition: all 0.3s ease;
}

.close-btn:hover {
  background: #f8f9fa;
  color: #e74c3c;
}

.modal-body {
  padding: 2rem;
}

.modal-body p {
  color: #7f8c8d;
  margin-bottom: 2rem;
  text-align: center;
}

.payment-actions {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.btn-payment {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  text-decoration: none;
  padding: 1rem 2rem;
  border-radius: 25px;
  font-weight: 600;
  text-align: center;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
}

.btn-payment:hover {
  color: white;
  text-decoration: none;
  box-shadow: 0 8px 25px rgba(102, 126, 234, 0.3);
}

.btn-cancel {
  background: #f8f9fa;
  color: #6c757d;
  border: 2px solid #e9ecef;
  padding: 1rem 2rem;
  border-radius: 25px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
}

.btn-cancel:hover {
  background: #e9ecef;
  color: #495057;
  border-color: #667eea;
}

/* Order Actions */
.order-actions {
  display: flex;
  gap: 1rem;
  justify-content: flex-end;
  flex-wrap: wrap;
}

.btn-detail,
.btn-reorder {
  padding: 0.8rem 1.5rem;
  border: none;
  border-radius: 25px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  font-size: 0.9rem;
}

.btn-detail {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
}

.btn-detail:hover {
  box-shadow: 0 8px 25px rgba(102, 126, 234, 0.3);
}

.btn-reorder {
  background: #f8f9fa;
  color: #6c757d;
  border: 2px solid #e9ecef;
}

.btn-reorder:hover {
  background: #e9ecef;
  color: #495057;
  border-color: #667eea;
}

/* Responsive Design */
@media (max-width: 768px) {
  .page-header {
    padding: 2rem 1rem;
  }

  .page-title {
    font-size: 2rem;
  }

  .order-tabs {
    position: relative;
    top: auto;
    padding: 0.5rem;
    box-shadow: none;
  }

  .content-area {
    min-height: 400px;
  }

  .order-card {
    padding: 1.5rem;
  }

  .order-header {
    flex-direction: column;
    gap: 1rem;
    align-items: flex-start;
  }

  .order-total {
    text-align: left;
  }

  .order-meta {
    flex-direction: column;
    gap: 0.5rem;
  }

  .product-item {
    flex-direction: column;
    text-align: center;
  }

  .product-details {
    justify-content: center;
  }

  .order-actions {
    justify-content: center;
  }
}

@media (max-width: 480px) {
  .main-container {
    padding: 0 0.5rem;
  }

  .order-card {
    padding: 1rem;
    border-radius: 15px;
  }

  .product-image img {
    width: 60px;
    height: 75px;
  }

  .order-actions {
    flex-direction: column;
  }

  .btn-detail,
  .btn-reorder {
    justify-content: center;
  }
}

.order-tabs {
  display: flex;
  flex-wrap: wrap;
  gap: 0.75rem;
  justify-content: center;
  margin-bottom: 2rem;
  position: sticky;
  top: 20px;
  z-index: 100;
  background: white;
  padding: 1rem;
  border-radius: 15px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.tab-button {
  background: #f1f1f1;
  border: none;
  border-radius: 20px;
  padding: 0.6rem 1.2rem;
  font-weight: 600;
  cursor: pointer;
  transition: 0.3s;
}

.tab-button:hover {
  background: #e0e0e0;
}

.tab-button.active {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
}

/* Hiệu ứng dropdown */
.tab-panel {
  animation: fadeIn 0.3s ease;
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(-10px);
  }

  to {
    opacity: 1;
    transform: translateY(0);
  }
}

/* Card form đánh giá */
.review-form-card {
  background: #f8fafc;
  border-radius: 20px;
  padding: 30px;
  margin-bottom: 40px;
}

/* Tiêu đề form */
.form-title {
  color: #1a202c;
  font-weight: 600;
  margin-bottom: 25px;
}

/* Input đánh giá sao */
.rating-input {
  margin-bottom: 20px;
}

.star-rating {
  display: flex;
  gap: 5px;
  margin-top: 8px;
}

.star-button {
  font-size: 24px;
  color: #d1d5db;
  cursor: pointer;
  transition: all 0.3s ease;
}

.star-button.bi-star-fill {
  color: #fbbf24;
}

.star-button:hover {
  transform: scale(1.1);
}

/* Nhóm input */
.form-group {
  margin-bottom: 20px;
}

.form-label {
  display: block;
  font-weight: 600;
  color: #374151;
  margin-bottom: 8px;
}

.form-textarea {
  width: 100%;
  padding: 15px;
  border: 2px solid #e5e7eb;
  border-radius: 12px;
  font-size: 14px;
  resize: vertical;
  transition: border-color 0.3s ease;
}

.form-textarea:focus {
  outline: none;
  border-color: #667eea;
}

/* Nút gửi đánh giá */
.submit-review-btn {
  background: linear-gradient(135deg, #667eea, #764ba2);
  color: white;
  border: none;
  padding: 15px 30px;
  border-radius: 12px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
}

.submit-review-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 20px rgba(102, 126, 234, 0.4);
}

/* Vue Transition Classes */
.tab-panel-enter-active,
.tab-panel-leave-active {
  transition: all 0.3s ease;
}

.tab-panel-enter-from {
  opacity: 0;
  transform: translateY(-20px);
}

.tab-panel-leave-to {
  opacity: 0;
  transform: translateY(-10px);
}

.tab-panel-enter-to,
.tab-panel-leave-from {
  opacity: 1;
  transform: translateY(0);
}

/* Fix layout issues */
.orders-list {
  min-height: 200px;
  transition: all 0.3s ease;
}

.order-card {
  transition: all 0.3s ease;
}

.review-form-card {
  background: #f8fafc;
  border-radius: 20px;
  padding: 30px;
  margin-top: 15px;
  margin-bottom: 20px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
  border: 1px solid #e2e8f0;
}
</style>
