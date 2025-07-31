<template>
  <div class="container mt-4">
    <h2 class="mb-3">Order List</h2>

    <ul class="nav nav-tabs mb-3">
      <li class="nav-item" v-for="tab in tabs" :key="tab">
        <a
          class="nav-link"
          :class="{ active: currentTab === tab }"
          href="#"
          @click.prevent="currentTab = tab"
        >
          {{ tab }}
        </a>
      </li>
    </ul>
    <div v-if="ordersData[currentTab] && ordersData[currentTab].length > 0">
      <div
        v-for="order in ordersData[currentTab]"
        :key="order.id"
        class="card mb-3 order-card"
      >
        <div class="card-header d-flex justify-content-between align-items-center">
          <h5 class="mb-0">Order #{{ order.id }}</h5>

          <button class="btn btn-primary btn-sm mx-2" @click="pay(order)">
            Thanh toán
          </button>
          <span :class="getStatusBadgeClass(order.paymentStatus)">
            {{ order.paymentStatus }}
          </span>
        </div>
        <div class="card-body">
          <div class="row">
            <div class="col-md-6">
              <p class="card-text">
                <strong>Order Code:</strong> {{ order.orderCode || "N/A" }}
              </p>
              <p class="card-text">
                <strong>Final Total:</strong>{{ order.finalTotal.toLocaleString() }} VND
              </p>
              <p class="card-text">
                <strong>Payment Method:</strong> {{ order.paymentMethodName }}
              </p>
              <p class="card-text">
                <strong>Shipping Method:</strong> {{ order.shippingMethodName }}
              </p>
            </div>
            <div class="col-md-6">
              <p class="card-text">
                <strong>Shipping Status:</strong>
                <span :class="getShippingStatusClass(order.shippingStatus)">
                  {{ order.shippingStatus }}
                </span>
              </p>
              <p class="card-text"><strong>Order Info:</strong> {{ order.orderInfor }}</p>
              <p class="card-text">
                <strong>Note:</strong> {{ order.note || "No note" }}
              </p>
            </div>
          </div>
          <hr />
          <div class="d-flex justify-content-between align-items-center text-muted small">
            <span>Created At: {{ formatDate(order.createdAt) }}</span>
            <span>Updated At: {{ formatDate(order.updatedAt) }}</span>
          </div>
        </div>
      </div>
    </div>
    <div v-if="payURL && showPopUp" class="popup-overlay" @click.self="closePopup">
      <div class="popup-iframe bg-white p-4 rounded shadow">
        <h5 class="mb-3 text-primary">MoMo Payment</h5>
        <div class="d-flex flex-column gap-3">
          <a :href="payURL" target="_blank" class="btn btn-primary">
            <i class="bi bi-credit-card me-2"></i>Open Payment
          </a>
          <button class="btn btn-outline-danger" @click="closePopup">
            <i class="bi bi-x-circle me-2"></i>Close
          </button>
        </div>
      </div>
    </div>
    <div v-else class="alert alert-info" role="alert">No orders found.</div>
  </div>
</template>

<script setup>
import { ref, watch, onMounted } from "vue";
import api from "../../Configs/api";
const ordersList = ref();

// Helper function to format dates
const formatDate = (dateString) => {
  if (!dateString) return "N/A";
  const options = {
    year: "numeric",
    month: "long",
    day: "numeric",
    hour: "2-digit",
    minute: "2-digit",
  };
  return new Date(dateString).toLocaleDateString("en-US", options);
};

const showPopUp = ref(false);
const payURL = ref("");

function closePopup() {
  showPopUp.value = false;
  payURL.value = "";
}
async function pay(orderID) {
  try {
    console.log("Order ID:", orderID);
    const response = await api.post(`/e-wallet/orderpay`, {
      OrderId: orderID.id,
      finalToTal: orderID.finalTotal,
    });
    console.log("Payment URL:", response.data);
    payURL.value = response.data;
    showPopUp.value = true;
  } catch (err) {
    console.error();
  }
}

const getStatusBadgeClass = (status) => {
  switch (status) {
    case "Chưa thanh toán":
      return "badge bg-warning text-dark";
    case "Đã thanh toán":
      return "badge bg-success";
    default:
      return "badge bg-secondary";
  }
};

const getShippingStatusClass = (status) => {
  switch (status) {
    case "Chờ xác nhận":
      return "badge bg-info text-dark";
    case "Đang xử lý":
      return "badge bg-primary";
    case "Đang giao hàng":
      return "badge bg-info";
    case "Đã giao hàng":
      return "badge bg-success";
    case "Đã hủy":
      return "badge bg-danger";
    default:
      return "badge bg-secondary";
  }
};

const tabs = ["Payment Pending", "Shipping Pending", "Sent"];
const currentTab = ref(tabs[0]);
const ordersData = ref({}); // To store fetched orders for each tab

const tabKeys = {
  "Payment Pending": "Chưa thanh toán",
  "Shipping Pending": "pending_shipping",
  Sent: "sent",
};
const loading = ref(false);

async function fetchDataForTab(tabName) {
  const keyToSend = tabKeys[tabName];
  if (!keyToSend) {
    ordersData.value[tabName] = [];
    return;
  }

  loading.value = true;
  try {
    const response = await api.get(`/user/Orders/paypending?KEY=${keyToSend}`);
    ordersData.value[tabName] = response.data;
  } catch (err) {
    console.error(`Error fetching data for ${tabName}:`, err);
    ordersData.value[tabName] = [];
  } finally {
    loading.value = false;
  }
}

watch(
  currentTab,
  (newTab) => {
    if (!ordersData.value[newTab]) {
      fetchDataForTab(newTab);
    }
  },
  { immediate: true }
);
</script>

<style scoped>
/* Scoped styles for this component */
.order-card {
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
}

.card-header {
  background-color: #f8f9fa;
  border-bottom: 1px solid #e0e0e0;
  padding: 0.75rem 1.25rem;
  border-top-left-radius: 7px;
  border-top-right-radius: 7px;
}

.card-body p {
  margin-bottom: 0.5rem;
}

.card-text strong {
  min-width: 120px;
  /* Adjust as needed for alignment */
  display: inline-block;
}

.badge {
  padding: 0.4em 0.8em;
  border-radius: 0.25rem;
  font-size: 0.85em;
  font-weight: 600;
}

.popup-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
}

/* Custom styles for status badges if Bootstrap defaults aren't enough */
.badge.bg-warning {
  color: #664d03 !important;
  /* Ensure text is dark on warning background */
}
</style>
