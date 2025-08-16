<template>
  <div v-if="order" class="order-status-container">
    <!-- Header Section -->
    <div class="page-header">
      <div class="header-content">
        <h1 class="page-title">
          <i class="bi bi-receipt me-3"></i>
          Trạng thái đơn hàng
        </h1>
        <p class="page-subtitle">THEO DÕI CHI TIẾT ĐƠN HÀNG CỦA BẠN</p>
      </div>
    </div>

    <!-- Order Info Card -->
    <div class="info-card">
      <div class="card-header">
        <i class="bi bi-info-square me-2"></i>
        <h3>Thông tin đơn hàng</h3>
      </div>
      <div class="info-grid">
        <div class="info-item">
          <div class="info-icon order">
            <i class="bi bi-hash"></i>
          </div>
          <div class="info-content">
            <span class="info-label">Mã đơn hàng</span>
            <span class="info-value">#{{ order.maDon }}</span>
          </div>
        </div>
        <div class="info-item">
          <div class="info-icon date">
            <i class="bi bi-calendar3"></i>
          </div>
          <div class="info-content">
            <span class="info-label">Ngày đặt hàng</span>
            <span class="info-value">{{ formatDate(order.ngayDat) }}</span>
          </div>
        </div>
        <div class="info-item">
          <div class="info-icon customer">
            <i class="bi bi-person-fill"></i>
          </div>
          <div class="info-content">
            <span class="info-label">Khách hàng</span>
            <span class="info-value">{{ order.khachHang.ten }}</span>
          </div>
        </div>
        <div class="info-item">
          <div class="info-icon status">
            <i class="bi bi-check-circle"></i>
          </div>
          <div class="info-content">
            <span class="info-label">Trạng thái hiện tại</span>
            <span
              class="info-value status-badge"
              :class="getStatusClass(order.trangThai)"
            >
              {{ getStatusText(order.trangThai) }}
            </span>
          </div>
        </div>
      </div>
    </div>

    <!-- Progress Tracker -->
    <div class="progress-card">
      <div class="card-header">
        <i class="bi bi-graph-up me-2"></i>
        <h3>Tiến trình đơn hàng</h3>
      </div>
      <div class="progress-tracker">
        <div
          v-for="(step, idx) in steps"
          :key="idx"
          :class="[
            'progress-step',
            {
              active: idx === statusIndex,
              completed: idx < statusIndex,
            },
          ]"
        >
          <div class="step-circle">
            <i :class="step.icon"></i>
          </div>
          <div class="step-label">{{ step.label }}</div>
          <div v-if="idx < steps.length - 1" class="step-line"></div>
        </div>
      </div>
    </div>

    <!-- Products Card -->
    <div class="products-card">
      <div class="card-header">
        <i class="bi bi-box-seam me-2"></i>
        <h3>Danh sách sản phẩm</h3>
        <div class="product-count">
          <span class="count-badge">{{ order.sanPham.length }} sản phẩm</span>
        </div>
      </div>
      <div class="products-grid">
        <div v-for="(sp, i) in order.sanPham" :key="i" class="product-row">
          <div class="product-image-container">
            <img :src="sp.anh" :alt="sp.ten" class="product-image" />
            <div class="quantity-badge">{{ sp.soLuong }}x</div>
          </div>
          <div class="product-content">
            <div class="product-header">
              <h4 class="product-title">{{ sp.ten }}</h4>
              <div class="product-status">
                <i class="bi bi-check-circle-fill"></i>
              </div>
            </div>
            <div class="product-pricing">
              <div class="price-row">
                <span class="price-label">
                  <i class="bi bi-tag me-1"></i>
                  Đơn giá
                </span>
                <span class="price-value unit-price">{{ formatMoney(sp.gia) }}</span>
              </div>
              <div class="price-row">
                <span class="price-label">
                  <i class="bi bi-x-lg me-1"></i>
                  Số lượng
                </span>
                <span class="price-value quantity">{{ sp.soLuong }}</span>
              </div>
              <div class="price-row total-row">
                <span class="price-label">
                  <i class="bi bi-calculator me-1"></i>
                  Thành tiền
                </span>
                <span class="price-value total-price">{{
                  formatMoney(sp.gia * sp.soLuong)
                }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Order Summary -->
      <div class="order-summary">
        <div class="summary-content">
          <div class="summary-item">
            <span class="summary-label">
              <i class="bi bi-box me-1"></i>
              Tổng số lượng
            </span>
            <span class="summary-value"
              >{{ order.sanPham.reduce((total, sp) => total + sp.soLuong, 0) }} sản
              phẩm</span
            >
          </div>
          <div class="summary-item total">
            <span class="summary-label">
              <i class="bi bi-currency-dollar me-1"></i>
              Tổng giá trị đơn hàng
            </span>
            <span class="summary-value total-amount">{{
              formatMoney(
                order.sanPham.reduce((total, sp) => total + sp.gia * sp.soLuong, 0)
              )
            }}</span>
          </div>
        </div>
      </div>
    </div>

    <!-- Contact & Payment Info -->
    <div class="details-section">
      <!-- Shipping Info -->
      <div class="detail-card">
        <div class="card-header">
          <i class="bi bi-truck me-2"></i>
          <h3>Thông tin giao hàng</h3>
        </div>
        <div class="detail-items">
          <div class="detail-item">
            <div class="detail-icon recipient">
              <i class="bi bi-person-badge"></i>
            </div>
            <div class="detail-content">
              <span class="detail-label">Người nhận</span>
              <span class="detail-value">{{ order.khachHang.ten }}</span>
            </div>
          </div>
          <div class="detail-item">
            <div class="detail-icon phone">
              <i class="bi bi-telephone"></i>
            </div>
            <div class="detail-content">
              <span class="detail-label">Số điện thoại</span>
              <span class="detail-value">{{ order.khachHang.sdt }}</span>
            </div>
          </div>
          <div class="detail-item full-width">
            <div class="detail-icon address">
              <i class="bi bi-geo-alt"></i>
            </div>
            <div class="detail-content">
              <span class="detail-label">Địa chỉ giao hàng</span>
              <span class="detail-value address-text">{{ order.khachHang.diaChi }}</span>
            </div>
          </div>
          <div class="detail-item">
            <div class="detail-icon shipping">
              <i class="bi bi-boxes"></i>
            </div>
            <div class="detail-content">
              <span class="detail-label">Đơn vị vận chuyển</span>
              <span class="detail-value">Giao hàng nhanh</span>
            </div>
          </div>
          <div class="detail-item">
            <div class="detail-icon tracking">
              <i class="bi bi-qr-code"></i>
            </div>
            <div class="detail-content">
              <span class="detail-label">Mã vận đơn</span>
              <span class="detail-value">{{ order.vanChuyen.maVanDon }}</span>
            </div>
          </div>
        </div>
      </div>

      <!-- Payment Info -->
      <div class="detail-card">
        <div class="card-header">
          <i class="bi bi-credit-card me-2"></i>
          <h3>Thông tin thanh toán</h3>
        </div>
        <div class="detail-items">
          <div class="detail-item">
            <div class="detail-icon payment">
              <i class="bi bi-wallet2"></i>
            </div>
            <div class="detail-content">
              <span class="detail-label">Phương thức thanh toán</span>
              <span class="detail-value">{{ order.thanhToan.phuongThuc }}</span>
            </div>
          </div>
          <div class="detail-item">
            <div class="detail-icon status">
              <i class="bi bi-check-circle"></i>
            </div>
            <div class="detail-content">
              <span class="detail-label">Trạng thái thanh toán</span>
              <span
                class="detail-value"
                :class="getPaymentStatusClass(order.thanhToan.trangThai)"
              >
                {{ order.thanhToan.trangThai }}
              </span>
            </div>
          </div>
          <div class="detail-item highlight">
            <div class="detail-icon total">
              <i class="bi bi-currency-dollar"></i>
            </div>
            <div class="detail-content">
              <span class="detail-label">Tổng tiền thanh toán</span>
              <span class="detail-value total-amount">{{
                formatMoney(order.tongTien)
              }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Notes Section -->
    <div v-if="order.ghiChu" class="notes-card">
      <div class="card-header">
        <i class="bi bi-chat-text me-2"></i>
        <h3>Ghi chú đặc biệt</h3>
      </div>
      <div class="notes-content">
        <div class="note-icon">
          <i class="bi bi-sticky"></i>
        </div>
        <div class="note-text">{{ order.ghiChu }}</div>
      </div>
    </div>

    <!-- History Section -->
    <div class="history-card">
      <div class="card-header">
        <i class="bi bi-clock-history me-2"></i>
        <h3>Lịch sử xử lý</h3>
      </div>
      <div class="history-timeline">
        <div v-for="(log, i) in order.lichSu" :key="i" class="timeline-item">
          <div class="timeline-dot"></div>
          <div class="timeline-content">
            <div class="timeline-time">{{ formatDateTime(log.thoiGian) }}</div>
            <div class="timeline-description">{{ log.noiDung }}</div>
          </div>
        </div>
      </div>
    </div>

    <!-- Actions -->
    <div class="actions-section">
      <div class="action-buttons">
        <button
          class="btn btn-cancel"
          @click="cancelOrder"
          v-if="['Chờ xác nhận', 'Chờ lấy hàng', 'Đang xử lý'].includes(order.trangThai)"
        >
          <i class="bi bi-x-circle me-2"></i>
          Hủy đơn hàng
        </button>
        <button class="btn btn-review">
          <i class="bi bi-star me-2"></i>
          Đánh giá sản phẩm
        </button>
        <button class="btn btn-reorder">
          <i class="bi bi-arrow-repeat me-2"></i>
          Mua lại
        </button>
        <button class="btn btn-print">
          <i class="bi bi-printer me-2"></i>
          In hóa đơn
        </button>
        <button class="btn btn-sync" @click="syncStatus">
          <i class="bi bi-arrow-clockwise me-2"></i>
          Đồng bộ trạng thái GHN
        </button>
      </div>
    </div>
  </div>

  <div v-else class="loading-container">
    <div class="loading-content">
      <div class="spinner">
        <i class="bi bi-arrow-clockwise"></i>
      </div>
      <p>Đang tải thông tin đơn hàng...</p>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRoute } from "vue-router";
import api from "../../Configs/api";
import { computed } from "vue";

const order = ref(null);

const steps = [
  { label: "Chờ xác nhận", icon: "bi bi-hourglass-split" },
  { label: "Chờ lấy hàng", icon: "bi bi-box-seam" },
  { label: "Chờ giao hàng", icon: "bi bi-truck" },
  { label: "Đang giao hàng", icon: "bi bi-a" },
  { label: "Đã nhận hàng", icon: "bi bi-check-circle-fill" },
];

// GHN trả về nhiều trạng thái, cần map để khớp UI
const statusMap = {
  // GHN Status → UI (Tiếng Việt)

  // Chờ xác nhận (nội bộ, chưa gửi GHN)
  "Chờ xác nhận": "Chờ xác nhận",

  // Chờ lấy hàng
  ready_to_pick: "Chờ lấy hàng",
  picking: "Chờ lấy hàng",
  money_collect_picking: "Chờ lấy hàng",

  // Chờ giao hàng
  picked: "Chờ giao hàng",
  //Đang giao hàng
  storing: "Đang giao hàng",
  sorting: "Đang giao hàng",
  transporting: "Đang giao hàng",
  delivering: "Đang giao hàng",
  money_collect_delivering: "Đang giao hàng",

  // Đã nhận hàng
  delivered: "Đã nhận hàng",

  // Đã hủy (gộp các trường hợp trả, lỗi, hủy, thất bại)
  cancel: "Đã hủy",
  return: "Đổi/Trả hàng",
  returning: "Đổi/Trả hàng",
  returned: "Đổi/Trả hàng",
  return_sorting: "Đổi/Trả hàng",
  return_transporting: "Đổi/Trả hàng",
  lost: "Đổi/Trả hàng",
  damage: "Đổi/Trả hàng",
  delivery_fail: "Đổi/Trả hàng",
};

function getStatusText(status) {
  return statusMap[status] || "Không xác định";
}

const statusToIndex = {
  "Chờ xác nhận": 0,
  "Chờ lấy hàng": 1,
  "Chờ giao hàng": 2,
  "Đang giao hàng": 3,
  "Đã nhận hàng": 4,
  "Đã hủy": 5,
};

// Dùng trạng thái hiện tại để xác định bước hiện tại
const statusIndex = computed(() => {
  const rawStatus = order.value?.trangThai;
  const viStatus = statusMap[rawStatus] || "Không xác định";
  return statusToIndex[viStatus] ?? -1;
});

const route = useRoute();
const maDon = route.params.id;

const loadOrder = async () => {
  try {
    const res = await api.get(`/Orders/detail/${maDon}`);
    const orderData = res.data;
    console.log("dữ liệu chi tiết đơn hàng", res.data);
    const [tenKH, sdtKH, diaChiKH] = orderData.order.orderInfor?.split(" - ") || [];

    order.value = {
      maDon: orderData.order.id,
      ngayDat: orderData.order.createdAt,
      trangThai: orderData.order.shippingStatus,
      ghiChu: orderData.order.note,
      tongTien: orderData.order.finalTotal,

      khachHang: {
        ten: tenKH || "N/A",
        sdt: sdtKH || "N/A",
        diaChi: diaChiKH || "N/A",
      },

      vanChuyen: {
        ten: orderData.order.shippingMethods?.name || "N/A",
        maVanDon: orderData.order.orderCode || "Đang cập nhật",
      },
      thanhToan: {
        phuongThuc: orderData.order.paymentMethods?.name || "N/A",
        trangThai: orderData.order.paymentStatus,
      },
      sanPham: orderData.items.map((i) => ({
        anh: i.main_image_url || "no-image.png",
        ten: i.name,
        soLuong: i.quantity,
        gia: i.price,
      })),
      lichSu: [
        {
          thoiGian: orderData.order.createdAt,
          noiDung: `Đơn hàng được tạo`,
        },
        ...(orderData.order.shippedDate
          ? [
              {
                thoiGian: orderData.order.shippedDate,
                noiDung: `Đơn hàng đã giao`,
              },
            ]
          : []),
      ],
    };
    if (order.value.vanChuyen.maVanDon && order.value.vanChuyen.maVanDon !== "Đang cập nhật") {
  try {
    const logRes = await api.get(`/log/${order.value.vanChuyen.maVanDon}`);
    const ghnLogs = logRes.data.map((log) => {
      console.log("Log từ GHN:", log);

      return {
        thoiGian: log.updated_date,
        noiDung: statusDisplayMap[log.status] || `Trạng thái: ${log.status}`,
      };
    });

    order.value.lichSu.push(...ghnLogs);
    order.value.lichSu.sort((a, b) => new Date(a.thoiGian) - new Date(b.thoiGian));
  } catch (err) {
    console.warn("Không thể lấy log từ GHN:", err);
  }
}
    if (statusMap[order.value.trangThai] === "Đã nhận hàng") {
      order.value.thanhToan.trangThai = "Đã thanh toán";
    }
    statusIndex.value = statusMap[order.value.trangThai] ?? 0;
  } catch (error) {
    console.error("Lỗi khi lấy chi tiết đơn hàng", error);
  }
};
const cancelOrder = async () => {
  if (!confirm("Bạn có chắc chắn muốn hủy đơn hàng này không?")) return;

  try {
    const res = await api.put(`/Users/Orders/cancelRefundOrder/${maDon}`);
    const msg = res.data.MESSAGE;
    alert(msg);

    if (msg.includes("thành công")) {
      await loadOrder(); // Tải lại đơn để cập nhật trạng thái
    }
  } catch (error) {
    alert("Đã xảy ra lỗi khi huỷ đơn hàng: " + error.message);
  }
};
onMounted(loadOrder);

// Gọi API cập nhật trạng thái từ GHN
const syncStatus = async () => {
  try {
    const res = await api.put(`/admin/Orders/update-ghn-status/${maDon}`);
    order.value.trangThai = res.data.shippingStatus;
    statusIndex.value = statusMap[res.data.shippingStatus] ?? 0;
    alert("Cập nhật trạng thái GHN thành công!");
  } catch (error) {
    alert("Lỗi khi cập nhật trạng thái GHN");
  }
};

const formatDate = (dateString) => {
  if (!dateString) return "N/A";
  const date = new Date(dateString);

  const months = [
    "Tháng 1",
    "Tháng 2",
    "Tháng 3",
    "Tháng 4",
    "Tháng 5",
    "Tháng 6",
    "Tháng 7",
    "Tháng 8",
    "Tháng 9",
    "Tháng 10",
    "Tháng 11",
    "Tháng 12",
  ];

  const day = date.getDate();
  const month = months[date.getMonth()];
  const year = date.getFullYear();

  return `${day} ${month} ${year}`;
};

const formatDateTime = (dateString) => {
  if (!dateString) return "N/A";
  const date = new Date(dateString);

  const months = [
    "Tháng 1",
    "Tháng 2",
    "Tháng 3",
    "Tháng 4",
    "Tháng 5",
    "Tháng 6",
    "Tháng 7",
    "Tháng 8",
    "Tháng 9",
    "Tháng 10",
    "Tháng 11",
    "Tháng 12",
  ];

  const day = date.getDate();
  const month = months[date.getMonth()];
  const year = date.getFullYear();
  const hours = date.getHours().toString().padStart(2, "0");
  const minutes = date.getMinutes().toString().padStart(2, "0");

  return `${day} ${month} ${year}, ${hours}:${minutes}`;
};

const formatMoney = (v) => Number(v).toLocaleString("vi-VN") + " VND";

const getStatusClass = (status) => {
  switch (status) {
    case "Chờ xác nhận":
      return "status-pending";
    case "Chờ lấy hàng":
      return "status-processing";
    case "Chờ giao hàng":
      return "status-shipping";
    case "Đã nhận hàng":
      return "status-delivered";
    default:
      return "status-default";
  }
};

const getPaymentStatusClass = (status) => {
  switch (status) {
    case "Đã thanh toán":
      return "payment-paid";
    case "Chưa thanh toán":
      return "payment-pending";
    default:
      return "payment-default";
  }
};

const statusDisplayMap = {
  ready_to_pick: "Chờ lấy hàng",
  picking: "Đang lấy hàng",
  picked: "Đã lấy hàng",
  storing: "Đã bàn giao đơn vị vận chuyển",
  transporting: "Đang vận chuyển giữa các kho",
  delivering: "Đang giao hàng",
  delivered: "Đã giao hàng thành công",
  cancel: "Đã huỷ",
  return: "Đang hoàn hàng",
  exception: "Giao hàng thất bại",
  damage: "Hàng bị hư hỏng",
  lost: "Mất hàng",
  "Chờ xác nhận": "Chờ xác nhận",
  "Chờ lấy hàng": "Chờ lấy hàng",
  "Chờ giao hàng": "Đang giao hàng",
  "Đã nhận hàng": "Đã nhận hàng",
};
</script>

<style scoped>
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

.page-title {
  color: white;
  font-size: 2.5rem;
  font-weight: 700;
  margin-bottom: 0.5rem;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.3);
  display: flex;
  align-items: center;
  justify-content: center;
}

.page-subtitle {
  color: rgba(255, 255, 255, 0.9);
  font-size: 1.1rem;
  margin: 0;
  font-weight: 400;
}

/* Main Container */
.order-status-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 1rem;
  padding-bottom: 2rem;
}

/* Card Styles */
.info-card,
.progress-card,
.products-card,
.detail-card,
.notes-card,
.history-card {
  background: white;
  border-radius: 20px;
  padding: 2rem;
  margin-bottom: 2rem;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
  border: 2px solid transparent;
}

.card-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 1.5rem;
  padding-bottom: 1rem;
  border-bottom: 2px solid #f8f9fa;
}

.card-header h3 {
  color: #2c3e50;
  font-size: 1.3rem;
  font-weight: 600;
  margin: 0;
  display: flex;
  align-items: center;
}

.product-count {
  margin-left: auto;
}

.count-badge {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  padding: 0.5rem 1rem;
  border-radius: 20px;
  font-size: 0.85rem;
  font-weight: 600;
}

/* Info Grid */
.info-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 1.5rem;
}

.info-item {
  background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
  border-radius: 15px;
  padding: 1.5rem;
  display: flex;
  align-items: center;
  gap: 1rem;
}

.info-icon {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.5rem;
  color: white;
  flex-shrink: 0;
}

.info-icon.order {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.info-icon.date {
  background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
}

.info-icon.customer {
  background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%);
}

.info-icon.status {
  background: linear-gradient(135deg, #fa709a 0%, #fee140 100%);
}

.info-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 0.3rem;
}

.info-label {
  color: #7f8c8d;
  font-weight: 600;
  font-size: 0.9rem;
  text-transform: uppercase;
  letter-spacing: 0.5px;
}

.info-value {
  color: #2c3e50;
  font-weight: 700;
  font-size: 1rem;
}

/* Status Badges */
.status-badge {
  padding: 0.4rem 1rem;
  border-radius: 20px;
  font-size: 0.85rem;
  font-weight: 600;
  text-align: center;
}

.status-pending {
  background: #fff3cd;
  color: #856404;
}

.status-processing {
  background: #e7f3ff;
  color: #0066cc;
}

.status-shipping {
  background: #fff2e6;
  color: #cc7a00;
}

.status-delivered {
  background: #d4edda;
  color: #155724;
}

.payment-paid {
  background: #d4edda;
  color: #155724;
}

.payment-pending {
  background: #fff3cd;
  color: #856404;
}

/* Progress Tracker */
.progress-tracker {
  display: flex;
  justify-content: space-between;
  position: relative;
  margin: 2rem 0;
}

.progress-tracker::before {
  content: "";
  position: absolute;
  top: 25px;
  left: 50px;
  right: 50px;
  height: 4px;
  background: #e9ecef;
  z-index: 1;
}

.progress-step {
  position: relative;
  z-index: 2;
  text-align: center;
  flex: 1;
  color: #7f8c8d;
}

.step-circle {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  background: #e9ecef;
  margin: 0 auto 1rem;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.5rem;
  color: #7f8c8d;
  transition: all 0.3s ease;
}

.progress-step.completed .step-circle,
.progress-step.active .step-circle {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  box-shadow: 0 4px 15px rgba(102, 126, 234, 0.3);
}

.progress-step.active {
  color: #667eea;
  font-weight: 600;
}

.step-label {
  font-size: 0.9rem;
  font-weight: 500;
}

/* Products Grid */
.products-grid {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
  margin-bottom: 2rem;
}

.product-row {
  background: white;
  border-radius: 15px;
  padding: 1.5rem;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.08);
  border: 2px solid #f8f9fa;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  gap: 2rem;
}

.product-row:hover {
  box-shadow: 0 8px 25px rgba(102, 126, 234, 0.15);
  border-color: #667eea;
  transform: translateY(-2px);
}

.product-image-container {
  position: relative;
  flex-shrink: 0;
  width: 120px;
  height: 150px;
  border-radius: 12px;
  overflow: hidden;
  background: #f8f9fa;
}

.product-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 12px;
  transition: transform 0.3s ease;
}

.product-row:hover .product-image {
  transform: scale(1.05);
}

.quantity-badge {
  position: absolute;
  top: 8px;
  right: 8px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  padding: 0.3rem 0.6rem;
  border-radius: 15px;
  font-size: 0.8rem;
  font-weight: 600;
  box-shadow: 0 2px 8px rgba(102, 126, 234, 0.3);
  z-index: 2;
}

.product-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.product-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.product-title {
  color: #2c3e50;
  font-size: 1.2rem;
  font-weight: 600;
  margin: 0;
  line-height: 1.4;
  flex: 1;
}

.product-status {
  color: #27ae60;
  font-size: 1.3rem;
  margin-left: 1rem;
}

.product-pricing {
  display: flex;
  gap: 2rem;
  flex-wrap: wrap;
}

.price-row {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 0.8rem 1.2rem;
  background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
  border-radius: 12px;
  min-width: 140px;
  text-align: center;
}

.price-row.total-row {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  box-shadow: 0 4px 15px rgba(102, 126, 234, 0.2);
}

.price-label {
  display: flex;
  align-items: center;
  font-size: 0.85rem;
  font-weight: 500;
  color: #7f8c8d;
  margin-bottom: 0.3rem;
}

.price-row.total-row .price-label {
  color: rgba(255, 255, 255, 0.9);
}

.price-value {
  font-weight: 700;
  font-size: 1rem;
}

.unit-price {
  color: #3498db;
}

.quantity {
  color: #f39c12;
}

.total-price {
  color: white;
  font-size: 1.1rem;
}

/* Order Summary */
.order-summary {
  margin-top: 2rem;
  background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
  border-radius: 15px;
  padding: 2rem;
  border: 2px solid #dee2e6;
}

.summary-content {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.summary-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 1rem;
  background: white;
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

.summary-item.total {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  box-shadow: 0 4px 15px rgba(102, 126, 234, 0.3);
}

.summary-label {
  display: flex;
  align-items: center;
  font-weight: 600;
  font-size: 1rem;
}

.summary-item.total .summary-label {
  color: rgba(255, 255, 255, 0.9);
}

.summary-value {
  font-weight: 700;
  font-size: 1.1rem;
  color: #2c3e50;
}

.summary-item.total .summary-value {
  color: white;
  font-size: 1.3rem;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
}

/* Details Section */
.details-section {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(400px, 1fr));
  gap: 2rem;
}

.detail-items {
  display: grid;
  gap: 1rem;
}

.detail-item {
  background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
  border-radius: 15px;
  padding: 1.5rem;
  display: flex;
  align-items: center;
  gap: 1rem;
}

.detail-item.highlight {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  box-shadow: 0 8px 25px rgba(102, 126, 234, 0.3);
}

.detail-item.full-width {
  grid-column: 1 / -1;
}

.detail-icon {
  width: 45px;
  height: 45px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.3rem;
  color: white;
  flex-shrink: 0;
}

.detail-icon.recipient {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.detail-icon.phone {
  background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%);
}

.detail-icon.address {
  background: linear-gradient(135deg, #fa709a 0%, #fee140 100%);
}

.detail-icon.shipping {
  background: linear-gradient(135deg, #ffecd2 0%, #fcb69f 100%);
  color: #d63384;
}

.detail-icon.tracking {
  background: linear-gradient(135deg, #a8edea 0%, #fed6e3 100%);
  color: #6610f2;
}

.detail-icon.payment {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.detail-icon.total {
  background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
}

.detail-item.highlight .detail-icon {
  background: rgba(255, 255, 255, 0.2);
  color: white;
}

.detail-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 0.3rem;
}

.detail-label {
  color: #7f8c8d;
  font-weight: 600;
  font-size: 0.9rem;
  text-transform: uppercase;
  letter-spacing: 0.5px;
}

.detail-item.highlight .detail-label {
  color: rgba(255, 255, 255, 0.8);
}

.detail-value {
  color: #2c3e50;
  font-weight: 700;
  font-size: 1rem;
}

.detail-item.highlight .detail-value {
  color: white;
}

.total-amount {
  color: #e74c3c;
  font-size: 1.5rem;
  font-weight: 800;
}

.detail-item.highlight .total-amount {
  color: white;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
}

.address-text {
  line-height: 1.4;
  word-break: break-word;
}

/* Notes Card */
.notes-content {
  display: flex;
  align-items: flex-start;
  gap: 1.5rem;
  background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
  border-radius: 15px;
  padding: 2rem;
}

.note-icon {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  background: linear-gradient(135deg, #fa709a 0%, #fee140 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.5rem;
  color: white;
  flex-shrink: 0;
}

.note-text {
  flex: 1;
  color: #2c3e50;
  font-size: 1rem;
  line-height: 1.6;
  font-style: italic;
}

/* History Timeline */
.history-timeline {
  position: relative;
  padding-left: 2rem;
}

.history-timeline::before {
  content: "";
  position: absolute;
  left: 15px;
  top: 0;
  bottom: 0;
  width: 2px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.timeline-item {
  position: relative;
  margin-bottom: 2rem;
  padding-left: 2rem;
}

.timeline-dot {
  position: absolute;
  left: -2rem;
  top: 0.5rem;
  width: 12px;
  height: 12px;
  border-radius: 50%;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border: 3px solid white;
  box-shadow: 0 2px 8px rgba(102, 126, 234, 0.3);
}

.timeline-content {
  background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
  border-radius: 12px;
  padding: 1.5rem;
}

.timeline-time {
  color: #667eea;
  font-weight: 600;
  font-size: 0.9rem;
  margin-bottom: 0.5rem;
}

.timeline-description {
  color: #2c3e50;
  font-size: 1rem;
  line-height: 1.5;
}

/* Action Buttons */
.actions-section {
  margin-top: 3rem;
  text-align: center;
}

.action-buttons {
  display: flex;
  gap: 1rem;
  justify-content: center;
  flex-wrap: wrap;
}

.btn {
  padding: 1rem 2rem;
  border-radius: 25px;
  border: none;
  font-weight: 600;
  font-size: 1rem;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  text-decoration: none;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
}

.btn-cancel {
  background: linear-gradient(135deg, #e74c3c 0%, #c0392b 100%);
  color: white;
}

.btn-cancel:hover {
  box-shadow: 0 8px 25px rgba(231, 76, 60, 0.3);
}

.btn-review {
  background: linear-gradient(135deg, #f39c12 0%, #e67e22 100%);
  color: white;
}

.btn-review:hover {
  box-shadow: 0 8px 25px rgba(243, 156, 18, 0.3);
}

.btn-reorder {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
}

.btn-reorder:hover {
  box-shadow: 0 8px 25px rgba(102, 126, 234, 0.3);
}

.btn-print {
  background: linear-gradient(135deg, #95a5a6 0%, #7f8c8d 100%);
  color: white;
}

.btn-print:hover {
  box-shadow: 0 8px 25px rgba(149, 165, 166, 0.3);
}

.btn-sync {
  background: linear-gradient(135deg, #1abc9c 0%, #16a085 100%);
  color: white;
}

.btn-sync:hover {
  box-shadow: 0 8px 25px rgba(26, 188, 156, 0.3);
}

/* Loading State */
.loading-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 4rem 1rem;
  text-align: center;
}

.loading-content {
  background: white;
  border-radius: 20px;
  padding: 4rem 2rem;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
}

.spinner {
  font-size: 3rem;
  color: #667eea;
  margin-bottom: 1.5rem;
  animation: spin 1s linear infinite;
}

@keyframes spin {
  from {
    transform: rotate(0deg);
  }
  to {
    transform: rotate(360deg);
  }
}

.loading-content p {
  color: #7f8c8d;
  font-size: 1.2rem;
  margin: 0;
}

/* Responsive Design */
@media (max-width: 768px) {
  .page-header {
    padding: 2rem 1rem;
  }

  .page-title {
    font-size: 2rem;
    flex-direction: column;
    gap: 0.5rem;
  }

  .info-grid {
    grid-template-columns: 1fr;
  }

  .details-section {
    grid-template-columns: 1fr;
  }

  .progress-tracker {
    flex-direction: column;
    gap: 2rem;
  }

  .progress-tracker::before {
    display: none;
  }

  .action-buttons {
    flex-direction: column;
    align-items: center;
  }

  .btn {
    width: 100%;
    max-width: 300px;
    justify-content: center;
  }

  .product-item {
    flex-direction: column;
    text-align: center;
  }

  .product-details {
    justify-content: center;
  }

  .product-row {
    flex-direction: column;
    text-align: center;
    gap: 1rem;
  }

  .product-image-container {
    width: 200px;
    height: 250px;
    margin: 0 auto;
  }

  .product-header {
    flex-direction: column;
    align-items: center;
    text-align: center;
    gap: 0.5rem;
  }

  .product-title {
    margin-right: 0;
  }

  .product-pricing {
    justify-content: center;
    gap: 1rem;
  }

  .price-row {
    min-width: 120px;
  }

  .summary-content {
    gap: 0.8rem;
  }

  .summary-item {
    flex-direction: column;
    text-align: center;
    gap: 0.5rem;
  }
}

@media (max-width: 480px) {
  .order-status-container {
    padding: 0 0.5rem;
  }

  .info-card,
  .progress-card,
  .products-card,
  .detail-card,
  .notes-card,
  .history-card {
    padding: 1.5rem;
    border-radius: 15px;
  }

  .card-header {
    flex-direction: column;
    text-align: center;
    gap: 0.5rem;
  }

  .detail-item {
    flex-direction: column;
    text-align: center;
    gap: 1rem;
  }

  .info-item {
    flex-direction: column;
    text-align: center;
    gap: 1rem;
  }
}
</style>
