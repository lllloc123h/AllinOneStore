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
                <input type="checkbox" v-model="selectedItems" :value="item.productItemId" />
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
          <button class="btn btn-dark w-100" :disabled="selectedItems.length === 0" @click="checkout">Thanh
            toán</button>
        </div>
      </div>
    </div>

    <!-- Sản phẩm gợi ý -->
    <div class="mt-5">
      <h5 class="mb-4">Bạn cũng có thể thích</h5>
      <div class="row">
        <div class="col-6 col-md-3" v-for="(product, index) in suggestions" :key="'suggestion-' + index">
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
import { ref, computed, onMounted } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'
import { authService, cartService } from '../../Configs/api'

const router = useRouter()

// Giỏ hàng từ server
const cart = ref([])

// ID sản phẩm đã chọn
const selectedItems = ref([])

// Lấy giỏ hàng từ API
async function loadCart() {
  try {
    const response = await cartService.getCart();
    // console.log(response);
    if (authService.isLogged()) {
      cart.value = response.map(item => ({
        id: item.id,
        productItemId: item.productItems.productItemId,
        name: item.productItems.name,
        price: item.productItems.price,
        quantity: item.qty,
        image: item.productItems.image
      }));
      selectedItems.value = cart.value.map(item => item.productItems.productItemId);

    } else {
      cart.value = response.map(item => ({
        id: item.id,
        productItemId: item.productItemId,
        name: item.name,
        price: item.price,
        quantity: item.quantity,
        image: item.image
      }));
      selectedItems.value = cart.value.map(item => item.productItems);

    }


    // Select all items by default
  } catch (error) {
    console.error('Failed to load cart:', error);
  }
}

// Xóa sản phẩm
function removeItem(item) {
  cart.value = cart.value.filter(i => i.productItemId !== item.productItemId)
  selectedItems.value = selectedItems.value.filter(productItemId => productItemId !== item.productItemId)
  cart.value = cart.value.filter(i => i.productItemId !== item.productItemId);
  selectedItems.value = selectedItems.value.filter(id => id !== item.productItemId);

  if (authService.isLogged()) {
    // ✅ Call API to remove from backend
    // await axios.delete(`http://localhost:8080/cart/delete/${item.id}`);
  } else {
    // ✅ Update localStorage for guest user
    let tempCart = JSON.parse(localStorage.getItem('cart')) ?? [];

    // Remove item from local cart
    tempCart = tempCart.filter(i => i.productItems !== item.productItemId);

    // Save updated list
    localStorage.setItem('cart', JSON.stringify(tempCart));
  }
  // TODO: Gọi API xóa nếu backend hỗ trợ
  // await axios.delete(`http://localhost:8080/cart/delete/${item.id}`)
}

// Tăng số lượng
function increaseQty(item) {
  if (authService.isLogged()) {

    item.quantity++
  } else {
    let tempLocalList = JSON.parse(localStorage.getItem('cart')) ?? [];
    tempLocalList = tempLocalList.map(cartItem => {
      if (cartItem.productItems === item.productItemId) {
        cartItem.qty++; // Increase quantity
      }
      return cartItem;
    });
    localStorage.setItem('cart', JSON.stringify(tempLocalList));
    const cartItem = cart.value.find(c => c.productItemId === item.productItemId);
    if (cartItem) {
      cartItem.quantity++;
    }

  }
  // TODO: Gọi API cập nhật nếu backend hỗ trợ
}

// Giảm số lượng hoặc xóa
function decreaseQty(item) {
  if (item.quantity > 1) {
    item.quantity--
    // TODO: Gọi API cập nhật nếu backend hỗ trợ
  } else {
    removeItem(item)
  }
}

// Tính tổng tiền sản phẩm được chọn
const selectedTotal = computed(() => {
  if (authService.isLogged()) {
    return cart.value
      .filter(item => selectedItems.value.includes(item.productItemId))
      .reduce((sum, item) => sum + item.price * item.quantity, 0)
  } else {
    return cart.value
      .filter(item => selectedItems.value.includes(item.productItemId))
      .reduce((sum, item) => sum + item.price * item.quantity, 0)
  }

})

// Gửi dữ liệu thanh toán
function checkout() {
  const selectedProducts = cart.value.filter(item =>
    selectedItems.value.includes(item.productItemId)
  )
  router.push({
    name: 'CheckoutPage',
    state: {
      selectedProducts
    }
  })
}

// Tải giỏ hàng khi trang được mount
onMounted(() => {
  loadCart()
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
