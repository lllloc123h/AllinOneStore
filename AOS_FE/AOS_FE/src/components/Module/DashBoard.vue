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
import { ref, watch, onMounted } from 'vue'
import { useRouter } from 'vue-router'
const router = useRouter()
import 'bootstrap/dist/css/bootstrap.min.css'
const props = defineProps({
    listDashBoard: {
        type: Array
    }
})
const isVisible = ref(Array(12).fill(false))
function toggleList(index) {
    isVisible.value[index] = !isVisible.value[index]
}
function goToView(tableName) {
    router.push(`/Admin/${tableName}`)
}
const menuItems = [
    {
        title: 'QUẢN LÝ TÀI KHOẢN',
        children: [
            {
                name: 'Quản lý Accounts',
                path: '/Admin/Accounts'
            },
            {
                name: 'Quản lý Authorities',
                path: '/Admin/Authorities'
            },
            {
                name: 'Quản lý Roles',
                path: '/Admin/Roles'
            },
            {
                name: 'Quản lý địa chỉ nhận hàng',
                path: '/Admin/UserAddresses'
            }
        ]
    },
    {
        title: 'QUẢN LÝ SẢN PHẨM',
        children: [
            {
                name: 'Quản lý catalogs',
                path: '/Admin/catalogs'
            },
            {
                name: 'Quản lý Categories',
                path: '/Admin/Categories'
            },
            {
                name: 'Quản lý BaseProducts',
                path: '/Admin/BaseProducts'
            },
            {
                name: 'Quản lý các biến thể',
                path: '/Admin/ProductItems'
            },
            {
                name: 'Quản lý Hình ảnh sản phẩm',
                path: '/Admin/ProductImages'
            }
        ]
    },
    {
        title: 'QUẢN LÝ ĐƠN HÀNG',
        children: [
            {
                name: 'Quản lý đơn hàng',
                path: '/Admin/Orders'
            },
            {
                name: 'Quản lý Returns (Trả hàng)',
                path: '/Admin/Returns'
            },
            {
                name: 'Quản lý Cancled Orders (Đơn hàng đã hủy)',
                path: '/Admin/Cancels'
            },
            {
                name: 'Quản lý các đánh giá sản phẩm',
                path: '/Admin/Reviews'
            },
            {
                name: 'Quản lý Shippingmethods (Vận chuyển)',
                path: '/Admin/ShippingMethods'
            },
            {
                name: 'Quản lý PaymentMethods (phương thức thanh toán)',
                path: '/Admin/PaymentMethods'
            }
        ]
    },
    {
        title: 'QUẢN LÝ VÍ TIỀN',
        children: [
            {
                name: 'Quản lý ví',
                path: '/Admin/Ewallets'
            },
            {
                name: 'Quản lý lịch sử giao dịch',
                path: '/Admin/EwalletTransactions'
            }
        ]
    },
    {
        title: 'QUẢN LÝ KHUYẾN MÃI',
        children: [
            {
                name: 'Quản lý Promotions',
                path: '/Admin/Promotions'
            },
            {
                name: 'Quản lý Promotions Products',
                path: '/Admin/EwalletTransactions'
            }
        ]
    }
]
</script>

<style scoped>
.sidebar {
    background-color: #1c2b4a;
    color: #ffffff;
    padding: 20px;
    width: 100%;
    max-width: 300px;
    border-radius: 10px;
}

.menu-btn {
    background-color: #2e3e5c;
    color: white;
    width: 100%;
    text-align: left;
    padding: 10px 15px;
    border: none;
    border-radius: 8px;
    margin-bottom: 5px;
    position: relative;
    cursor: pointer;
}

.menu-btn:hover {
    background-color: #3f5377;
}

.arrow {
    float: right;
    transition: transform 0.3s ease;
}

.arrow.open {
    transform: rotate(180deg);
}

.ItemMenu {
    list-style-type: none;
    margin-right: 5%;
}

.list {
    background-color: #34465d;
    padding: 10px;
    margin-top: 5px;
    border-radius: 6px;

}

.Item {
    margin: 6px 0;
    list-style-type: none;
}

.link {
    color: #ffffff;
    text-decoration: none;
    transition: color 0.2s;
    padding-left: 10px;

}

.link:hover {
    color: #50b7f5;
}

#menu {
    padding: 0;
}

.fade-enter-active,
.fade-leave-active {
    transition: opacity 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
    opacity: 0;
}
</style>