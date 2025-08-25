<template>
  <div class="wallet-container">
    <!-- Header Section -->
    <div class="wallet-header">
      <div class="container">
        <div class="header-content">
          <div class="header-text">
            <h1 class="wallet-title">
              <i class="bi bi-wallet2 me-3"></i>
              Ví Điện Tử
            </h1>
            <p class="wallet-subtitle">Quản lý số dư và giao dịch một cách thông minh</p>
          </div>
          <div class="header-actions" v-if="!walletNotFound">
            <button class="btn btn-topup" @click="MomoTopUp">
              <i class="bi bi-plus-circle me-2"></i>
              Nạp tiền
            </button>
            <button class="btn btn-topup" @click="MomoWithdraw">
              <i class="bi bi-plus-circle me-2"></i>
              Rút tiền
            </button>
          </div>
        </div>
      </div>
    </div>

    <div class="container">
      <!-- Wallet Not Found State -->
      <div v-if="walletNotFound" class="wallet-empty-state">
        <div class="empty-state-card">
          <div class="empty-state-icon">
            <i class="bi bi-wallet-fill"></i>
          </div>
          <h3>Chưa có ví điện tử</h3>
          <p class="text-muted mb-4">Tạo ví để bắt đầu sử dụng các dịch vụ thanh toán</p>
          <button class="btn btn-create-wallet" @click="showModal = true">
            <i class="bi bi-plus-circle me-2"></i>
            Tạo ví mới
          </button>
        </div>
      </div>

      <!-- Wallet Overview Cards -->
      <div v-else class="wallet-overview">
        <div class="row g-4 mb-5">
          <!-- Balance Card -->
          <div class="col-lg-4 col-md-6">
            <div class="stat-card balance-card">
              <div class="stat-icon balance-icon">
                <i class="bi bi-cash-stack"></i>
              </div>
              <div class="stat-content">
                <h6 class="stat-label">Số dư hiện tại</h6>
                <div class="stat-value balance-value">
                  {{ formatCurrency(wallet.balance || 0) }}
                </div>
                <div class="stat-status">
                  <span class="badge" :class="wallet.active === true ? 'status-active' : 'status-pending'">
                    <i class="bi" :class="wallet.active === true ? 'bi-check-circle' : 'bi-clock'"></i>
                    {{ wallet.active === true ? "Đã xác minh" : "Chờ xác minh" }}
                  </span>
                </div>
              </div>
            </div>
          </div>

          <!-- Total Top-up Card -->
          <div class="col-lg-4 col-md-6">
            <div class="stat-card topup-card">
              <div class="stat-icon topup-icon">
                <i class="bi bi-arrow-down-circle"></i>
              </div>
              <div class="stat-content">
                <h6 class="stat-label">Tổng nạp</h6>
                <div class="stat-value topup-value">
                  {{ formatCurrency(totalTopup) }}
                </div>
                <small class="stat-update">Cập nhật: {{ topupUpdated }}</small>
              </div>
            </div>
          </div>

          <!-- Total Spent Card -->
          <div class="col-lg-4 col-md-6">
            <div class="stat-card spent-card">
              <div class="stat-icon spent-icon">
                <i class="bi bi-arrow-up-circle"></i>
              </div>
              <div class="stat-content">
                <h6 class="stat-label">Tổng chi tiêu</h6>
                <div class="stat-value spent-value">
                  {{ formatCurrency(totalSpent) }}
                </div>
                <small class="stat-update">Cập nhật: {{ spentUpdated }}</small>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!-- Wallet Details Section -->
      <div v-if="!walletNotFound" class="wallet-details">
        <div class="row g-4">
          <!-- Wallet Information -->
          <div class="col-lg-8">
            <div class="detail-card">
              <div class="detail-header">
                <h5 class="detail-title">
                  <i class="bi bi-info-circle me-2"></i>
                  Thông tin ví
                </h5>
                <div v-if="wallet.active === false">
                  <button class="btn btn-verify" @click="showVerifyModal = true">
                    <i class="bi bi-shield-check me-2"></i>
                    Xác minh ví
                  </button>
                </div>
              </div>
              <div class="detail-body">
                <div class="row g-3">
                  <div class="col-md-6">
                    <div class="info-item">
                      <label class="info-label">ID Ví</label>
                      <div class="info-value">{{ wallet.id }}</div>
                    </div>
                  </div>
                  <div class="col-md-6">
                    <div class="info-item">
                      <label class="info-label">Trạng thái</label>
                      <div class="info-value">
                        <span class="badge" :class="wallet.active === true ? 'status-active' : 'status-pending'
                          ">
                          <i class="bi" :class="wallet.active === true ? 'bi-check-circle' : 'bi-clock'
                            "></i>
                          {{
                            wallet.active === true ? "Đang hoạt động" : "Chưa xác minh"
                          }}
                        </span>
                      </div>
                    </div>
                  </div>
                  <div class="col-md-6">
                    <div class="info-item">
                      <label class="info-label">Ngày tạo</label>
                      <div class="info-value">{{ formatDate(wallet.createdAt) }}</div>
                    </div>
                  </div>
                  <div class="col-md-6">
                    <div class="info-item">
                      <label class="info-label">Cập nhật lần cuối</label>
                      <div class="info-value">{{ formatDate(wallet.updatedAt) }}</div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- Recent Transactions -->
          <div class="col-lg-4">
            <div class="detail-card transactions-card">
              <div class="detail-header">
                <h5 class="detail-title">
                  <i class="bi bi-clock-history me-2"></i>
                  Giao dịch gần đây
                </h5>
              </div>
              <div class="detail-body">
                <div class="transaction-list">
                  <div class="transaction-item" v-for="(txn, index) in recentTransactions" :key="index">
                    <div class="transaction-info">
                      <div class="transaction-description">{{ txn.description }}</div>
                      <div class="transaction-date">{{ formatDate(txn.date) }}</div>
                      <div class="transaction-status">
                        <span class="badge" :class="txn.status === 'PENDING' ? 'status-pending' : 'status-success'
                          ">
                          {{
                            txn.status === "PENDING"
                              ? "Giao dịch quá hạn"
                              : "Nạp tiền thành công"
                          }}
                        </span>
                      </div>
                    </div>
                    <div class="transaction-amount">
                      <span :class="txn.amount > 0 ? 'amount-positive' : 'amount-negative'">
                        {{ formatCurrency(txn.amount) }}
                      </span>
                    </div>
                  </div>
                </div>
                <div class="transaction-footer">
                  <a href="#" class="btn btn-link-custom">
                    <i class="bi bi-list-ul me-1"></i>
                    Xem tất cả giao dịch
                  </a>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Help Section -->
      <div v-if="!walletNotFound" class="help-section">
        <div class="help-card">
          <div class="help-icon">
            <i class="bi bi-lightbulb"></i>
          </div>
          <div class="help-content">
            <h6>Hướng dẫn sử dụng</h6>
            <ul class="help-list">
              <li>Số dư được sử dụng để thanh toán dịch vụ trên nền tảng</li>
              <li>Giao dịch nạp tiền thường xử lý trong 24 giờ làm việc</li>
              <li>Liên hệ nếu bạn cần hỗ trợ về các vấn đề liên quan đến ví</li>
            </ul>
          </div>
        </div>
      </div>
    </div>

    <!-- Create Wallet Modal -->
    <div v-if="showModal" class="custom-modal">
      <div class="modal-backdrop" @click="showModal = false"></div>
      <div class="modal-container">
        <div class="modal-header">
          <h5 class="modal-title">
            <i class="bi bi-wallet-fill me-2"></i>
            Tạo ví mới
          </h5>
          <button type="button" class="btn-close" @click="showModal = false">
            <i class="bi bi-x-lg"></i>
          </button>
        </div>
        <div class="modal-body">
          <div class="form-group">
            <label class="form-label">ID ví (tùy chọn)</label>
            <input type="text" v-model="formDataWallet.id" class="form-control" placeholder="Nhập ID ví..." />
            <small class="form-text">Chỉ chứa chữ cái, số, dấu gạch ngang hoặc gạch dưới</small>
          </div>
          <div class="confirmation-text">
            <p>Bạn có chắc chắn muốn tạo ví mới?</p>
          </div>
        </div>
        <div class="modal-footer">
          <button class="btn btn-secondary" @click="showModal = false">
            <i class="bi bi-x-circle me-2"></i>
            Hủy
          </button>
          <button class="btn btn-primary" @click="createWallet">
            <i class="bi bi-check-circle me-2"></i>
            Tạo ví
          </button>
        </div>
      </div>
    </div>
    <div v-if="showSuccess" class="success-modal">
      <div class="success-modal-backdrop" @click="showSuccess = false"></div>
      <div class="success-modal-content">
        <div class="success-icon">
          <i class="bi bi-check-circle-fill"></i>
        </div>
        <h3 class="success-title">Thanh toán thành công!</h3>
        <p class="success-message">
          Cảm ơn bạn đã đặt hàng. Chúng tôi sẽ xử lý đơn hàng và giao đến bạn sớm nhất có
          thể.
        </p>
        <div class="success-actions">
          <button class="continue-shopping-btn" @click="router.push('/')">
            <i class="bi bi-house me-2"></i>
            Về trang chủ
          </button>
          <button class="view-orders-btn" @click="router.push('/orders')">
            <i class="bi bi-list-check me-2"></i>
            Xem đơn hàng
          </button>
        </div>
      </div>
    </div>
    <div v-if="showFailure" class="success-modal">
      <div class="success-modal-backdrop" @click="showFailure = false"></div>
      <div class="success-modal-content">
        <div class="failure-icon text-danger">
          <i class="bi bi-x-circle-fill"></i>
        </div>
        <h3 class="success-title">Thanh toán thất bại!</h3>
        <p class="success-message">
          Rất tiếc, giao dịch của bạn không thành công. Vui lòng thử lại hoặc chọn phương thức thanh toán khác.
        </p>
        <div class="success-actions">
          <button class="continue-shopping-btn" @click="router.push('/')">
            <i class="bi bi-house me-2"></i>
            Về trang chủ
          </button>
          <button class="view-orders-btn" @click="router.push('/orders')">
            <i class="bi bi-list-check me-2"></i>
            Xem đơn hàng
          </button>
        </div>
      </div>
    </div>
    <div v-if="showTimeout" class="success-modal">
      <div class="success-modal-backdrop" @click="showFailure = false"></div>
      <div class="success-modal-content">
        <div class="timeout-icon">
          <i class="bi bi-exclamation-triangle-fill"></i>
        </div>
        <h3 class="success-title">Thanh toán hết hạn!</h3>
        <p class="success-message">
          Rất tiếc, giao dịch của bạn không thành công. Vui lòng thử lại hoặc chọn phương thức thanh toán khác.
        </p>
        <div class="success-actions">
          <button class="continue-shopping-btn" @click="router.push('/')">
            <i class="bi bi-house me-2"></i>
            Về trang chủ
          </button>
          <button class="view-orders-btn" @click="router.push('/orders')">
            <i class="bi bi-list-check me-2"></i>
            Xem đơn hàng
          </button>
        </div>
      </div>
    </div>

    <!-- Verify Modal -->
    <div v-if="showVerifyModal" class="custom-modal">
      <div class="modal-backdrop" @click="showVerifyModal = false"></div>
      <div class="modal-container">
        <div class="modal-header">
          <h5 class="modal-title">
            <i class="bi bi-shield-check me-2"></i>
            Xác minh ví
          </h5>
          <button type="button" class="btn-close" @click="showVerifyModal = false">
            <i class="bi bi-x-lg"></i>
          </button>
        </div>
        <div class="modal-body">
          <div class="verification-info">
            <p>Nhập mã xác minh đã gửi đến email/SMS của bạn:</p>
          </div>
          <div class="form-group">
            <label class="form-label">Mã xác minh</label>
            <input type="text" v-model="verifyCode" class="form-control otp-input" placeholder="Nhập mã 6 số..."
              maxlength="6" />
          </div>
        </div>
        <div class="modal-footer">
          <button class="btn btn-secondary" @click="showVerifyModal = false">
            <i class="bi bi-x-circle me-2"></i>
            Hủy
          </button>
          <button class="btn btn-warning" :disabled="timeLineOTP > 0" @click="ReSendOTP">
            <i class="bi bi-arrow-clockwise me-2"></i>
            Gửi lại OTP
            <span v-if="timeLineOTP > 0" class="ms-1">({{ timeLineOTP }}s)</span>
          </button>
          <button class="btn btn-primary" @click="verifyWallet">
            <i class="bi bi-check-circle me-2"></i>
            Xác minh
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from "vue";
import { useRouter, useRoute } from "vue-router";
import api from "../../Configs/api";
import { notification } from "ant-design-vue";
const wallet = ref({});
const totalTopup = ref(0);
const totalSpent = ref(0);
const recentTransactions = ref([]);
const topupUpdated = ref("");
const spentUpdated = ref("");
const walletNotFound = ref(false);
const router = useRouter();
const route = useRoute();
const showModal = ref(false);
const showVerifyModal = ref(false);
const verifyCode = ref("");
const timeLineOTP = ref(0);
let otpTimer = null;
const showSuccess = ref(false);
const showFailure = ref(false);
const showTimeout = ref(false);

const formDataWallet = ref({
  id: "",
  createdAt: "",
  accounts: "",
  walletType: "REAL",
  isActive: false,
  codeActivce: "",
});
async function verifyWallet() {
  if (!verifyCode.value) {
    notification.warning({
      message: "Thiếu mã",
      description: "Vui lòng nhập mã xác minh.",
    });
    return;
  }

  try {
    const response = await api.post(`/user/VerifyEWallets`, {
      codeActivce: verifyCode.value,
      accounts: "",
    });
    wallet.value = response.data;
    showVerifyModal.value = false;
    verifyCode.value = "";

    notification.success({
      message: "Thành công",
      description: "Ví đã được xác minh thành công.",
    });
  } catch (error) {
    console.error("Lỗi xác minh:", error);
    notification.error({
      message: "Lỗi",
      description: "Mã xác minh không hợp lệ hoặc đã hết hạn.",
    });
  }
}
async function createWallet() {
  try {
    if (!formDataWallet.value.id || !/^[a-zA-Z0-9_-]+$/.test(formDataWallet.value.id)) {
      notification.error({
        message: "Lỗi",
        description: `ID KHÔNG HỢP LỆ. Vui lòng nhập ID chỉ chứa chữ cái, số, dấu gạch ngang hoặc gạch dưới.`,
      });
      return;
    }
    const response = await api.post(`/user/EWallets`, { ...formDataWallet.value }); // adjust body if needed
    wallet.value = response.data;
    walletNotFound.value = false;
    showModal.value = false;
  } catch (error) {
    console.error("Lỗi khi tạo ví:", error);
    alert("Không thể tạo ví. Vui lòng thử lại.");
  }
}
function ReSendOTP() {
  if (timeLineOTP.value > 0) return;
  try {
    api.get(`/user/resendOTP`);
    timeLineOTP.value = 60;
    otpTimer = setInterval(() => {
      if (timeLineOTP.value > 0) {
        timeLineOTP.value--;
      } else {
        clearInterval(otpTimer);
      }
    }, 1000);
  } catch (error) {
    console.error("Lỗi khi gửi lại OTP:", error);
    notification.error({
      message: "Lỗi",
      description: "Không thể gửi lại mã xác minh. Vui lòng thử lại sau.",
    });
  }
}
function formatDate(dateStr) {
  if (!dateStr) return "";
  const date = new Date(dateStr);
  return date.toLocaleString("vi-VN");
}

function formatCurrency(amount) {
  return new Intl.NumberFormat("vi-VN", {
    style: "currency",
    currency: "VND",
  }).format(amount);
}

async function fetchInfor() {
  try {
    const response = await api.get(`EWallets`);
    const transactionResponse = await api.get(`EWalletTransactions`);
    walletNotFound.value = false;
    showVerifyModal.value = !response.data.active;
    const transactions = transactionResponse.data;
    totalTopup.value = transactions
      .filter((txn) => txn.transactionType === "TOP_UP" && txn.status === "SUCCESS")
      .reduce((sum, txn) => sum + txn.amount, 0);
    topupUpdated.value = formatDate(new Date());
    totalSpent.value = transactions
      .filter((txn) => txn.transactionType === "MOMOPAY" && txn.status === "SUCCESS")
      .reduce((sum, txn) => sum + txn.amount, 0);
    spentUpdated.value = formatDate(new Date());

    recentTransactions.value = transactions.slice(0, 5).map((txn) => ({
      description: txn.description || txn.transactionType,
      amount: txn.amount,
      date: txn.createdAt,
    }));
    // console.log(response.data)
    // console.log(transactionResponse.data)
    wallet.value = response.data;
  } catch (error) {
    if (error.response && error.response.data.message === "404") {
      walletNotFound.value = true;
    } else {
      console.error("Lỗi khi lấy dữ liệu ví:", error);
    }
  }
}

function MomoTopUp() {
  router.push({ name: "momotopup" });
}
function MomoWithdraw() {
  router.push({ name: "momowithdraw" });
}
onMounted(() => {
  fetchInfor();
  console.log("WalletInfor component mounted", route.query);
  const resultCode = route.query?.resultCode;
  if (resultCode !== undefined) {
    switch (resultCode) {
      case "0": // Success
        showSuccess.value = true;
        break;
      case "9000": // Timeout
        showTimeout.value = true;
        break;
      case "1006": // Failure (Canceled)
        showFailure.value = true;
        break;
      default:
        console.log("Unknown payment result code:", resultCode);
    }
  } else {
    console.log("No payment result parameter found in the route.");
  }

});
</script>

<style scoped>
/* Global Styles */
.wallet-container {
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding-bottom: 2rem;
}

/* Header Styles */
.wallet-header {
  background: transparent;
  padding: 3rem 0 2rem;
  color: white;
}

.header-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-wrap: wrap;
  gap: 1rem;
}

.wallet-title {
  font-size: 2.5rem;
  font-weight: 700;
  margin: 0;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.wallet-subtitle {
  font-size: 1.1rem;
  opacity: 0.9;
  margin: 0.5rem 0 0;
}

.btn-topup {
  background: rgba(255, 255, 255, 0.15);
  border: 2px solid rgba(255, 255, 255, 0.3);
  color: white;
  padding: 0.75rem 1.5rem;
  border-radius: 12px;
  font-weight: 600;
  backdrop-filter: blur(10px);
  transition: all 0.3s ease;
}

.btn-topup:hover {
  background: rgba(255, 255, 255, 0.25);
  border-color: rgba(255, 255, 255, 0.5);
  color: white;
  transform: translateY(-2px);
}

/* Empty State */
.wallet-empty-state {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 60vh;
}

.empty-state-card {
  background: white;
  padding: 3rem;
  border-radius: 20px;
  text-align: center;
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
  max-width: 400px;
  width: 100%;
}

.empty-state-icon {
  font-size: 4rem;
  color: #6c63ff;
  margin-bottom: 1.5rem;
}

.empty-state-card h3 {
  color: #2c3e50;
  margin-bottom: 1rem;
  font-weight: 600;
}

.btn-create-wallet {
  background: linear-gradient(135deg, #6c63ff, #8b79f5);
  border: none;
  color: white;
  padding: 0.75rem 2rem;
  border-radius: 12px;
  font-weight: 600;
  transition: all 0.3s ease;
}

.btn-create-wallet:hover {
  background: linear-gradient(135deg, #8b79f5, #6c63ff);
  transform: translateY(-2px);
  box-shadow: 0 10px 20px rgba(108, 99, 255, 0.3);
}

/* Stat Cards */
.wallet-overview {
  margin-top: -2rem;
  position: relative;
  z-index: 2;
}

.stat-card {
  background: white;
  border-radius: 20px;
  padding: 2rem;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
  height: 100%;
  border: none;
}

.stat-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.15);
}

.stat-icon {
  width: 60px;
  height: 60px;
  border-radius: 16px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.5rem;
  margin-bottom: 1rem;
}

.balance-icon {
  background: linear-gradient(135deg, #4facfe, #00f2fe);
  color: white;
}

.topup-icon {
  background: linear-gradient(135deg, #43e97b, #38f9d7);
  color: white;
}

.spent-icon {
  background: linear-gradient(135deg, #fa709a, #fee140);
  color: white;
}

.stat-label {
  color: #6c757d;
  font-size: 0.9rem;
  font-weight: 500;
  margin-bottom: 0.5rem;
}

.stat-value {
  font-size: 1.8rem;
  font-weight: 700;
  margin-bottom: 0.5rem;
}

.balance-value {
  color: #2c3e50;
}

.topup-value {
  color: #28a745;
}

.spent-value {
  color: #dc3545;
}

.stat-update {
  color: #8e9ba8;
  font-size: 0.8rem;
}

.stat-status {
  margin-top: 0.5rem;
}

/* Status Badges */
.status-active {
  background: linear-gradient(135deg, #28a745, #20c997);
  color: white;
  padding: 0.4rem 0.8rem;
  border-radius: 20px;
  font-size: 0.8rem;
  font-weight: 500;
}

.status-pending {
  background: linear-gradient(135deg, #ffc107, #ffcd39);
  color: #212529;
  padding: 0.4rem 0.8rem;
  border-radius: 20px;
  font-size: 0.8rem;
  font-weight: 500;
}

.status-success {
  background: linear-gradient(135deg, #28a745, #20c997);
  color: white;
  padding: 0.3rem 0.6rem;
  border-radius: 12px;
  font-size: 0.75rem;
}

/* Detail Cards */
.wallet-details {
  margin-bottom: 2rem;
}

.detail-card {
  background: white;
  border-radius: 20px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  height: 100%;
}

.detail-header {
  background: linear-gradient(135deg, #f8f9fa, #e9ecef);
  padding: 1.5rem 2rem;
  border-bottom: 1px solid #e9ecef;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.detail-title {
  margin: 0;
  color: #2c3e50;
  font-weight: 600;
}

.detail-body {
  padding: 2rem;
}

.btn-verify {
  background: linear-gradient(135deg, #17a2b8, #20c997);
  border: none;
  color: white;
  padding: 0.5rem 1rem;
  border-radius: 8px;
  font-size: 0.9rem;
  font-weight: 500;
  transition: all 0.3s ease;
}

.btn-verify:hover {
  background: linear-gradient(135deg, #20c997, #17a2b8);
  transform: translateY(-1px);
}

/* Info Items */
.info-item {
  margin-bottom: 1rem;
}

.info-label {
  font-size: 0.85rem;
  color: #6c757d;
  font-weight: 500;
  margin-bottom: 0.25rem;
  display: block;
}

.info-value {
  color: #2c3e50;
  font-weight: 600;
}

/* Transaction List */
.transactions-card {
  background: linear-gradient(135deg, #667eea, #764ba2);
  color: white;
}

.transactions-card .detail-header {
  background: rgba(255, 255, 255, 0.1);
  border-bottom: 1px solid rgba(255, 255, 255, 0.2);
}

.transactions-card .detail-title {
  color: white;
}

.transaction-list {
  max-height: 300px;
  overflow-y: auto;
}

.transaction-item {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  padding: 1rem 0;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
}

.transaction-item:last-child {
  border-bottom: none;
}

.transaction-info {
  flex: 1;
}

.transaction-description {
  font-weight: 600;
  margin-bottom: 0.25rem;
}

.transaction-date {
  font-size: 0.8rem;
  opacity: 0.8;
  margin-bottom: 0.5rem;
}

.transaction-amount {
  text-align: right;
  margin-left: 1rem;
}

.amount-positive {
  color: #28a745;
  font-weight: 700;
}

.amount-negative {
  color: #dc3545;
  font-weight: 700;
}

.transaction-footer {
  padding-top: 1rem;
  border-top: 1px solid rgba(255, 255, 255, 0.1);
}

.btn-link-custom {
  color: white;
  text-decoration: none;
  font-weight: 500;
  transition: all 0.3s ease;
}

.btn-link-custom:hover {
  color: rgba(255, 255, 255, 0.8);
  text-decoration: underline;
}

/* Help Section */
.help-section {
  margin-bottom: 2rem;
}

.help-card {
  background: white;
  border-radius: 20px;
  padding: 2rem;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
  display: flex;
  align-items: flex-start;
  gap: 1.5rem;
}

.help-icon {
  width: 60px;
  height: 60px;
  background: linear-gradient(135deg, #ffc107, #ffcd39);
  border-radius: 16px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.5rem;
  color: white;
  flex-shrink: 0;
}

.help-content h6 {
  color: #2c3e50;
  font-weight: 600;
  margin-bottom: 1rem;
}

.help-list {
  list-style: none;
  padding: 0;
  margin: 0;
}

.help-list li {
  color: #6c757d;
  margin-bottom: 0.5rem;
  padding-left: 1.5rem;
  position: relative;
}

.help-list li::before {
  content: "✓";
  position: absolute;
  left: 0;
  color: #28a745;
  font-weight: bold;
}

/* Custom Modals */
.custom-modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 1050;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 1rem;
}

.modal-backdrop {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  backdrop-filter: blur(5px);
}

.modal-container {
  background: white;
  border-radius: 20px;
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.2);
  max-width: 500px;
  width: 100%;
  position: relative;
  z-index: 1051;
  overflow: hidden;
}

.modal-header {
  background: linear-gradient(135deg, #667eea, #764ba2);
  color: white;
  padding: 1.5rem 2rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.modal-title {
  margin: 0;
  font-weight: 600;
}

.btn-close {
  background: none;
  border: none;
  color: white;
  font-size: 1.2rem;
  cursor: pointer;
  padding: 0.5rem;
  border-radius: 8px;
  transition: background 0.3s ease;
}

.btn-close:hover {
  background: rgba(255, 255, 255, 0.1);
}

.modal-body {
  padding: 2rem;
}

.form-group {
  margin-bottom: 1.5rem;
}

.form-label {
  color: #2c3e50;
  font-weight: 500;
  margin-bottom: 0.5rem;
  display: block;
}

.form-control {
  width: 100%;
  padding: 0.75rem;
  border: 2px solid #e9ecef;
  border-radius: 12px;
  font-size: 1rem;
  transition: all 0.3s ease;
}

.form-control:focus {
  outline: none;
  border-color: #6c63ff;
  box-shadow: 0 0 0 3px rgba(108, 99, 255, 0.1);
}

.otp-input {
  text-align: center;
  font-size: 1.2rem;
  font-weight: 600;
  letter-spacing: 0.5rem;
}

.form-text {
  color: #6c757d;
  font-size: 0.8rem;
  margin-top: 0.25rem;
  display: block;
}

.verification-info {
  background: #f8f9fa;
  padding: 1rem;
  border-radius: 12px;
  margin-bottom: 1.5rem;
}

.verification-info p {
  margin: 0;
  color: #2c3e50;
}

.confirmation-text {
  background: #fff3cd;
  border: 1px solid #ffeaa7;
  padding: 1rem;
  border-radius: 12px;
  margin-top: 1rem;
}

.confirmation-text p {
  margin: 0;
  color: #856404;
  font-weight: 500;
}

.modal-footer {
  padding: 1.5rem 2rem;
  background: #f8f9fa;
  display: flex;
  gap: 1rem;
  justify-content: flex-end;
  flex-wrap: wrap;
}

.btn {
  padding: 0.75rem 1.5rem;
  border-radius: 8px;
  font-weight: 500;
  transition: all 0.3s ease;
  border: none;
  cursor: pointer;
  display: inline-flex;
  align-items: center;
  gap: 0.5rem;
}

.btn-primary {
  background: linear-gradient(135deg, #6c63ff, #8b79f5);
  color: white;
}

.btn-primary:hover {
  background: linear-gradient(135deg, #8b79f5, #6c63ff);
  transform: translateY(-1px);
}

.btn-secondary {
  background: #6c757d;
  color: white;
}

.btn-secondary:hover {
  background: #5a6268;
  transform: translateY(-1px);
}

.btn-warning {
  background: linear-gradient(135deg, #ffc107, #ffcd39);
  color: #212529;
}

.btn-warning:hover {
  background: linear-gradient(135deg, #ffcd39, #ffc107);
  transform: translateY(-1px);
}

.btn-warning:disabled {
  opacity: 0.6;
  cursor: not-allowed;
  transform: none;
}

/* Responsive Design */
@media (max-width: 768px) {
  .wallet-title {
    font-size: 2rem;
  }

  .header-content {
    flex-direction: column;
    text-align: center;
  }

  .stat-card {
    padding: 1.5rem;
  }

  .detail-card {
    margin-bottom: 1rem;
  }

  .help-card {
    flex-direction: column;
    text-align: center;
  }

  .modal-container {
    margin: 1rem;
    max-width: none;
  }

  .modal-footer {
    flex-direction: column;
  }

  .modal-footer .btn {
    width: 100%;
    justify-content: center;
  }
}

@media (max-width: 576px) {
  .wallet-header {
    padding: 2rem 0 1rem;
  }

  .empty-state-card {
    padding: 2rem 1.5rem;
  }

  .transaction-item {
    flex-direction: column;
    gap: 0.5rem;
  }

  .transaction-amount {
    margin-left: 0;
    text-align: left;
  }
}

.success-modal {
  position: fixed;
  inset: 0;
  z-index: 9999;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 2rem;
}

.success-modal-backdrop {
  position: absolute;
  inset: 0;
  background: rgba(0, 0, 0, 0.7);
  backdrop-filter: blur(5px);
}

.success-modal-content {
  background: white;
  border-radius: 20px;
  padding: 3rem;
  max-width: 500px;
  width: 100%;
  text-align: center;
  position: relative;
  z-index: 1;
  box-shadow: 0 20px 50px rgba(0, 0, 0, 0.2);
}

.success-icon {
  font-size: 4rem;
  color: #10b981;
  margin-bottom: 1.5rem;
}

.success-title {
  font-size: 2rem;
  font-weight: 700;
  color: #2d3748;
  margin-bottom: 1rem;
}

.success-message {
  color: #6c757d;
  line-height: 1.6;
  margin-bottom: 2rem;
  font-size: 1.1rem;
}

.success-actions {
  display: flex;
  gap: 1rem;
  justify-content: center;
}

.continue-shopping-btn,
.view-orders-btn {
  padding: 12px 24px;
  border-radius: 12px;
  font-weight: 600;
  border: none;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
}

.continue-shopping-btn {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
}

.view-orders-btn {
  background: transparent;
  color: #667eea;
  border: 2px solid #667eea;
}

.continue-shopping-btn:hover,
.view-orders-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 25px rgba(102, 126, 234, 0.3);
}

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-content {
  background: white;
  padding: 24px;
  border-radius: 16px;
  width: 90%;
  max-width: 500px;
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.25);
}
</style>
