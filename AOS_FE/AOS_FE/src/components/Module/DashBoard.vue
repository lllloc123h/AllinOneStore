<template>
  <div class="container-fluid vh-100">
    <div id="Sidebar" class="sidebar col-sm-3">
      <ul id="menu">
        <li class="ItemMenu" v-for="(item, index) in menuItems" :key="index">
          <button class="menu-btn" @click="toggleList(index)">
            <h5>{{ item.title }}</h5>
            <span class="arrow" :class="{ open: isVisible[index] }">▼</span>
          </button>
          <transition name="fade">
            <ul class="list" v-show="isVisible[index]">
              <li class="Item" v-for="(child, i) in item.children" :key="i">
                <router-link :to="child.path" class="link">{{ child.name }}</router-link>
              </li>
            </ul>
          </transition>
        </li>
      </ul>
    </div>
  </div>
</template>

<script setup>
import { ref, watch, onMounted } from "vue";
import { useRouter } from "vue-router";
const router = useRouter();
import "bootstrap/dist/css/bootstrap.min.css";
const props = defineProps({
  listDashBoard: {
    type: Array,
  },
});
const isVisible = ref(Array(12).fill(false));
function toggleList(index) {
  isVisible.value[index] = !isVisible.value[index];
}
function goToView(tableName) {
  router.push(`/Admin/${tableName}`);
}
const menuItems = [
  {
    title: "QUẢN LÝ TÀI KHOẢN",
    children: [
      {
        name: "Quản lý Accounts",
        path: "/Admin/Accounts",
      },
      {
        name: "Quản lý Authorities",
        path: "/Admin/Authorities",
      },
      {
        name: "Quản lý Roles",
        path: "/Admin/Roles",
      },
      {
        name: "Quản lý địa chỉ nhận hàng",
        path: "/Admin/UserAddresses",
      },
    ],
  },
  {
    title: "QUẢN LÝ SẢN PHẨM",
    children: [
      {
        name: "Quản lý catalogs",
        path: "/Admin/catalogs",
      },
      {
        name: "Quản lý Categories",
        path: "/Admin/Categories",
      },
      {
        name: "Quản lý BaseProducts",
        path: "/Admin/BaseProducts",
      },
      {
        name: "Quản lý các biến thể",
        path: "/Admin/ProductItems",
      },
      {
        name: "Quản lý Hình ảnh sản phẩm",
        path: "/Admin/ProductImages",
      },
    ],
  },
  {
    title: "QUẢN LÝ ĐƠN HÀNG",
    children: [
      {
        name: "Quản lý đơn hàng",
        path: "/Admin/Orders",
      },
      {
        name: "Quản lý Returns (Trả hàng)",
        path: "/Admin/Returns",
      },
      {
        name: "Quản lý Cancled Orders (Đơn hàng đã hủy)",
        path: "/Admin/Cancels",
      },
      {
        name: "Quản lý các đánh giá sản phẩm",
        path: "/Admin/Reviews",
      },
      {
        name: "Quản lý Shippingmethods (Vận chuyển)",
        path: "/Admin/ShippingMethods",
      },
      {
        name: "Quản lý PaymentMethods (phương thức thanh toán)",
        path: "/Admin/PaymentMethods",
      },
    ],
  },
  {
    title: "QUẢN LÝ VÍ TIỀN",
    children: [
      {
        name: "Quản lý ví",
        path: "/Admin/Ewallets",
      },
      {
        name: "Quản lý lịch sử giao dịch",
        path: "/Admin/EwalletTransactions",
      },
    ],
  },
  {
    title: "QUẢN LÝ KHUYẾN MÃI",
    children: [
      {
        name: "Quản lý Promotions",
        path: "/Admin/Promotions",
      },
      {
        name: "Quản lý Promotions Products",
        path: "/Admin/PromotionProducts",
      },
      {
        name: "Quản lý COUPONS",
        path: "/Admin/Coupons",
      }
    ],
  },
  {
    title: "QUẢN LÝ CUSTOMS",
    children: [
      {
        name: "Quản lý CUSTOMS",
        path: "/Admin/Customs",
      }
    ],
  },
    {
    title: "Thống kê",
    children: [
      {
        name: "Thống kê tổng quát",
        path: "/Admin/products/general/stats",
      },
      {
        name: "Thống kê theo sản phẩm",
        path: "/Admin/products/details/stats",
      },
    ],
  },
];
</script>

<style scoped>
.sidebar {
  background: linear-gradient(135deg, #1a1a2e 0%, #16213e 50%, #0f3460 100%);
  color: #ffffff;
  padding: 25px;
  width: 100%;
  max-width: 320px;
  border-radius: 15px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.3);
  position: relative;
  overflow: hidden;
}

.sidebar::before {
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 4px;
  background: linear-gradient(90deg, #667eea 0%, #764ba2 50%, #f093fb 100%);
}

.menu-btn {
  background: linear-gradient(135deg, #2d3748 0%, #1a202c 100%);
  color: white;
  width: 100%;
  text-align: left;
  padding: 15px 20px;
  border: none;
  border-radius: 12px;
  margin-bottom: 8px;
  position: relative;
  cursor: pointer;
  font-weight: 600;
  font-size: 0.95rem;
  transition: all 0.3s ease;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  border: 1px solid rgba(255, 255, 255, 0.1);
}

.menu-btn:hover {
  background: linear-gradient(135deg, #4a5568 0%, #2d3748 100%);
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.25);
}

.menu-btn h5 {
  margin: 0;
  font-size: 0.9rem;
  font-weight: 600;
  letter-spacing: 0.5px;
  text-transform: uppercase;
}

.arrow {
  position: absolute;
  right: 20px;
  top: 50%;
  transform: translateY(-50%);
  transition: transform 0.3s ease;
  font-size: 0.8rem;
  color: #a0aec0;
}

.arrow.open {
  transform: translateY(-50%) rotate(180deg);
  color: #667eea;
}

.ItemMenu {
  list-style-type: none;
  margin-bottom: 12px;
}

.list {
  background: rgba(255, 255, 255, 0.05);
  backdrop-filter: blur(10px);
  padding: 15px;
  margin-top: 8px;
  border-radius: 12px;
  border: 1px solid rgba(255, 255, 255, 0.1);
  box-shadow: inset 0 2px 8px rgba(0, 0, 0, 0.1);
}

.Item {
  margin: 10px 0;
  list-style-type: none;
}

.link {
  color: #e2e8f0;
  text-decoration: none;
  transition: all 0.3s ease;
  padding: 12px 16px;
  border-radius: 8px;
  display: block;
  font-weight: 500;
  font-size: 0.9rem;
  position: relative;
  overflow: hidden;
}

.link::before {
  content: "";
  position: absolute;
  left: 0;
  top: 0;
  bottom: 0;
  width: 0;
  background: linear-gradient(45deg, #667eea, #764ba2);
  transition: width 0.3s ease;
}

.link:hover {
  color: #ffffff;
  background: rgba(102, 126, 234, 0.1);
  transform: translateX(5px);
}

.link:hover::before {
  width: 4px;
}

.link.router-link-active {
  background: linear-gradient(45deg, rgba(102, 126, 234, 0.2), rgba(118, 75, 162, 0.2));
  color: #ffffff;
  font-weight: 600;
}

.link.router-link-active::before {
  width: 4px;
}

#menu {
  padding: 0;
  margin: 0;
}

.fade-enter-active,
.fade-leave-active {
  transition: all 0.4s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
  transform: translateY(-10px);
}

/* Scrollbar styling */
.sidebar::-webkit-scrollbar {
  width: 6px;
}

.sidebar::-webkit-scrollbar-track {
  background: rgba(255, 255, 255, 0.1);
  border-radius: 3px;
}

.sidebar::-webkit-scrollbar-thumb {
  background: linear-gradient(135deg, #667eea, #764ba2);
  border-radius: 3px;
}

.sidebar::-webkit-scrollbar-thumb:hover {
  background: linear-gradient(135deg, #5a67d8, #6b46c1);
}

/* Responsive design */
@media (max-width: 768px) {
  .sidebar {
    max-width: 100%;
    padding: 20px;
    border-radius: 0;
  }

  .menu-btn {
    padding: 12px 16px;
  }

  .menu-btn h5 {
    font-size: 0.85rem;
  }

  .link {
    padding: 10px 14px;
    font-size: 0.85rem;
  }
}
</style>
