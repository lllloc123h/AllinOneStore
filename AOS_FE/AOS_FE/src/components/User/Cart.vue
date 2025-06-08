
<template> 
  <div class="container py-5">
    <div class="row">
      <!-- Giỏ hàng -->
      <div class="col-lg-8">
        <table class="table table-borderless align-middle">
          <thead class="cart-header">
            <tr>
              <th></th>
              <th></th> <!-- Checkbox cột -->
              <th>Sản phẩm</th>
              <th>Giá</th>
              <th class="text-center">Số lượng</th>
              <th class="text-end">Tổng</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="item in cart" :key="item.id" class="border-bottom">
              <td>
                <button class="btn btn-sm text-danger" @click="removeItem(item)">
                  ✕
                </button>
              </td>
              <td>
                <input type="checkbox" v-model="selectedItems" :value="item.id" />
              </td>
              <td>
                <div class="d-flex align-items-center">
                  <img :src="item.image" class="img-thumbnail me-2" style="width: 60px; height: 60px;" />
                  {{ item.name }}
                </div>
              </td>
              <td>{{ item.price.toLocaleString() }}₫</td>
              <td class="text-center">
                <div class="d-inline-flex align-items-center border rounded px-2">
                  <button class="btn btn-sm" @click="decreaseQty(item)">−</button>
                  <span class="mx-2">{{ item.quantity }}</span>
                  <button class="btn btn-sm" @click="increaseQty(item)">＋</button>
                </div>
              </td>
              <td class="text-end">{{ (item.price * item.quantity).toLocaleString() }}₫</td>
            </tr>
          </tbody>
        </table>
      </div>

      <!-- Thanh toán -->
      <div class="col-lg-4">
        <div class="p-3 rounded cart-summary">
          <h5>Tổng thanh toán</h5>
          <ul class="list-unstyled">
            <li class="d-flex justify-content-between py-1">
              <span>Tạm Tính</span>
              <span>{{ selectedTotal.toLocaleString() }}₫</span>
            </li>
            <li class="d-flex justify-content-between py-1">
              <span>Giảm Giá</span>
              <span>—</span>
            </li>
            <hr />
            <li class="d-flex justify-content-between fw-bold py-1">
              <span>Tổng Cộng</span>
              <span>{{ selectedTotal.toLocaleString() }}₫</span>
            </li>
          </ul>
          <button class="btn btn-dark w-100" :disabled="selectedItems.length === 0" @click="checkout">Thanh toán</button>
        </div>
      </div>
    </div>

    <!-- Sản phẩm gợi ý -->
    <div class="mt-5">
      <h5 class="mb-4">Bạn cũng có thể thích</h5>
      <div class="row">
        <div
          class="col-6 col-md-3"
          v-for="(product, index) in suggestions"
          :key="'suggestion-' + index"
        >
          <div class="text-center">
            <img :src="product.image" class="suggestion-img mb-2" />
            <div>{{ product.name }}</div>
            <small class="text-muted text-decoration-line-through">80.000</small>
            <strong class="ms-1">{{ product.price.toLocaleString() }}₫</strong>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'

// Import ảnh từ assets
import aoTrang from '@/assets/imgs/ao-thun-trang.jpg'
import aoXanh from '@/assets/imgs/ao-thun-xanh.jpg'
import aoHong from '@/assets/imgs/ao-thun-hong.jpg'
import aoDen from '@/assets/imgs/ao-thun-den.webp'
import aoDo from '@/assets/imgs/ao-thun-do.jpeg'

import { useRouter } from 'vue-router'
const router = useRouter()

function checkout() {
  const selectedProducts = cart.value.filter(item => selectedItems.value.includes(item.id))

  // Chuyển đến trang Checkout và truyền state
  router.push({
    name: 'CheckoutPage',
    state: {
      selectedProducts: selectedProducts
    }
  })
}

// Giỏ hàng
const cart = ref([
  { id: 1, name: 'Áo Thun Phông Trắng', price: 50000, quantity: 1, image: aoTrang },
  { id: 2, name: 'Áo Thun Phông Xanh', price: 50000, quantity: 1, image: aoXanh },
  { id: 3, name: 'Áo Thun Phông Hồng', price: 50000, quantity: 1, image: aoHong },
  { id: 4, name: 'Áo Thun Phông Đen', price: 50000, quantity: 1, image: aoDen }
])

// Mảng chứa id sản phẩm được chọn để thanh toán
const selectedItems = ref(cart.value.map(item => item.id)) // Mặc định chọn hết

// Gợi ý
const suggestions = ref([
  { name: 'Áo thun phông đen', price: 50000, image: aoDen },
  { name: 'Áo thun phông xanh', price: 50000, image: aoXanh },
  { name: 'Áo thun phông đỏ', price: 50000, image: aoDo },
  { name: 'Áo thun phông hồng', price: 50000, image: aoHong }
])

// Xử lý
function removeItem(item) {
  cart.value = cart.value.filter(i => i.id !== item.id)
  // Nếu xóa, cũng bỏ chọn luôn
  selectedItems.value = selectedItems.value.filter(id => id !== item.id)
}

function increaseQty(item) {
  item.quantity++
}

function decreaseQty(item) {
  if (item.quantity > 1) item.quantity--
  else removeItem(item)
}

// Tính tổng chỉ với sản phẩm được chọn
const selectedTotal = computed(() => {
  return cart.value
    .filter(item => selectedItems.value.includes(item.id))
    .reduce((sum, item) => sum + item.price * item.quantity, 0)
})

</script>

<style scoped>
img.img-thumbnail {
  object-fit: cover;
}
.suggestion-img {
  width: 100%;
  height: 300px;
  object-fit: cover;
  border-radius: 8px;
}
</style>
