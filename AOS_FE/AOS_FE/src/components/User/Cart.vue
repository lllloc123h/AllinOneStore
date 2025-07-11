<template>
  <div class="container py-5">
    <div class="row">
      <!-- Giỏ hàng -->
      <div class="col-lg-8">
        <table class="table table-borderless align-middle">
          <thead class="cart-header">
            <tr class="border-1">
              <!-- Checkbox cột -->
              <th>
                <input
                  type="checkbox"
                  :checked="isAllSelected"
                  @change="toggleSelectAll"
                />
              </th>
              <th class="text-start">Sản phẩm</th>
              <th>Tên</th>
              <th>Loại</th>
              <th>Giá</th>
              <th class="text-center">Số lượng</th>
              <th style="width: 20%" class="text-end">Tổng</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            <template v-for="(items, groupKey) in comboGroups" :key="'combo-' + groupKey">
              <tr>
                <td colspan="8" class="p-0 border-0">
                  <div class="card mb-3 border-warning shadow-sm">
                    <div
                      class="card-header bg-warning bg-opacity-25 fw-bold text-danger d-flex align-items-center justify-content-between"
                    >
                      <input
                        type="checkbox"
                        class="me-2"
                        :checked="isComboSelected(items)"
                        @change="toggleSelectCombo(items)"
                      />
                      <div class="d-flex align-items-center">
                        <i class="bi bi-tag-fill me-2"></i>
                        <span>
                          Combo: {{ items[0].promotions.name }} ({{ groupKey }})
                          <span class="text-muted ms-2">{{
                            items[0].promotions.description
                          }}</span>
                        </span>
                      </div>
                      <button
                        class="btn btn-sm btn-outline-danger"
                        @click="removeComboGroup(groupKey)"
                        title="Xóa toàn bộ combo"
                      >
                        <i class="bi bi-x-lg"></i>
                      </button>
                    </div>
                    <div class="card-body p-0">
                      <!-- Số lượng combo -->
                      <div class="d-flex align-items-center p-3">
                        <span class="me-2">Số lượng combo:</span>
                        <button
                          class="btn btn-sm btn-outline-secondary"
                          @click="decreaseComboGroupQty(items)"
                        >
                          -
                        </button>
                        <span class="mx-2 fw-bold">{{ items[0].comboQty }}</span>
                        <button
                          class="btn btn-sm btn-outline-secondary"
                          @click="increaseComboGroupQty(items)"
                        >
                          +
                        </button>
                      </div>
                      <!-- Danh sách sản phẩm trong combo -->
                      <table class="table mb-0">
                        <tbody>
                          <tr v-for="item in items" :key="item.id" class="border-bottom">
                            <td>
                              <!-- <input
                                type="checkbox"
                                v-model="selectedItems"
                                :value="item.productItemId"
                              /> -->
                            </td>
                            <td>
                              <div class="d-flex align-items-center">
                                <img
                                  :src="item.image"
                                  class="img-thumbnail me-2"
                                  style="height: 125px; width: 100px"
                                />
                              </div>
                            </td>
                            <td>{{ item.name }}</td>
                            <td>{{ item.sku }}</td>
                            <td>{{ item.price.toLocaleString() }}₫</td>
                            <td class="text-center">
                              <span class="mx-2">{{ item.quantity }}</span>
                            </td>
                            <td class="text-end">
                              {{ (item.price * item.quantity).toLocaleString() }}₫
                            </td>
                            <td></td>
                          </tr>
                        </tbody>
                      </table>
                    </div>
                  </div>
                </td>
              </tr>
            </template>

            <tr v-for="item in singleProducts" :key="item.id" class="border-bottom">
              <td>
                <input
                  type="checkbox"
                  v-model="selectedItems"
                  :value="item.productItemId"
                />
              </td>
              <td>
                <div class="d-flex position-relative align-items-center">
                  <span class="position-relative mt-2 mb-2">
                    <img
                      :src="item.image"
                      class="img-thumbnail me-2"
                      style="height: 125px; width: 156px"
                    />

                    <!-- Thay nút Ưu đãi -->
                    <button
                      v-if="item.promotions.type === 'COMBO' && item.comboGroup == ''"
                      data-bs-toggle="modal"
                      data-bs-target="#exampleModalToggle"
                      class="btn border-0 position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger"
                      @click="openPromotionModal(item.productItemId)"
                      type="button"
                    >
                      <i class="bi bi-tag-fill"></i> Ưu đãi
                    </button>
                    <span
                      v-else-if="item.comboType === 'DISCOUNT'"
                      class="border-0 position-absolute top-0 start-100 translate-middle badge rounded-pill bg-warning"
                    >
                      <i class="bi bi-gift-fill"></i> Đang giảm giá
                    </span>

                    <!-- Modal của Ưu đãiiiiiiiiiii -->
                    <div
                      class="modal fade"
                      id="exampleModalToggle"
                      aria-hidden="true"
                      aria-labelledby="exampleModalToggleLabel"
                      tabindex="-1"
                    >
                      <div class="modal-dialog modal-lg modal-dialog-centered">
                        <div class="modal-content">
                          <div class="modal-header">
                            <h1 class="modal-title fs-5" id="exampleModalToggleLabel">
                              Ưu đãi hiện có
                            </h1>
                            <button
                              type="button"
                              class="btn-close"
                              data-bs-dismiss="modal"
                              aria-label="Close"
                            ></button>
                          </div>
                          <div class="modal-body">
                            <div
                              v-for="item in promotions"
                              :key="item.id"
                              class="mb-3 row shadow-sm rounded-3"
                            >
                              <div class="col-10 card border-0">
                                <div class="card-body py-3 px-4">
                                  <!-- Header: badge + tên + giá -->
                                  <div
                                    class="d-flex justify-content-between align-items-center mb-2"
                                  >
                                    <div class="d-flex align-items-center">
                                      <span class="badge bg-warning text-dark me-2">
                                        <i class="bi bi-gift-fill me-1"></i> Ưu đãi
                                      </span>
                                      <span class="fw-bold">{{ item.name }}</span>
                                    </div>
                                    <div
                                      class="fw-bold text-danger"
                                      style="min-width: 100px; text-align: right"
                                    >
                                      {{ item.comboPrice.toLocaleString() }}đ
                                    </div>
                                  </div>

                                  <!-- Mô tả -->
                                  <div
                                    class="text-muted text-start mb-3 mt-3"
                                    style="font-size: 0.95em; white-space: pre-line"
                                  >
                                    {{ item.description }}
                                  </div>

                                  <!-- Info phụ -->
                                  <div
                                    class="d-flex justify-content-between"
                                    style="font-size: 0.9em"
                                  >
                                    <span class="text-success"
                                      >Còn lại: {{ item.qty }}</span
                                    >
                                    <span class="text-secondary"
                                      >HSD:
                                      {{
                                        new Date(item.endAt).toLocaleDateString()
                                      }}</span
                                    >
                                  </div>
                                </div>
                              </div>
                              <!-- Nút chuyển tab -->
                              <div class="col-2 text-end" style="padding: 0">
                                <button
                                  style="width: 100%; height: 100%"
                                  class="btn btn-sm btn-outline-primary border-0"
                                  @click="openSpecificPromotionModal(item.id)"
                                  data-bs-target="#exampleModalToggle2"
                                  data-bs-toggle="modal"
                                >
                                  Xem thêm
                                </button>
                              </div>
                            </div>
                          </div>
                          <!-- <div class="modal-footer">
                            <button class="btn btn-primary">Open second modal</button>
                          </div> -->
                        </div>
                      </div>
                    </div>
                    <div
                      class="modal fade"
                      id="exampleModalToggle2"
                      aria-hidden="true"
                      aria-labelledby="exampleModalToggleLabel2"
                      tabindex="-1"
                    >
                      <div class="modal-dialog modal-lg modal-dialog-centered">
                        <div class="modal-content">
                          <div class="modal-header">
                            <h1 class="modal-title fs-5" id="exampleModalToggleLabel2">
                              Modal 2
                            </h1>
                            <button
                              type="button"
                              class="btn-close"
                              data-bs-dismiss="modal"
                              aria-label="Close"
                            ></button>
                          </div>
                          <div class="modal-body">
                            <!-- Hiển thị groupProducts -->
                            <div v-if="groupProducts.length" class="mt-4">
                              <h3 class="mb-2">Sản phẩm trong combo</h3>
                              <div
                                v-for="(group, idx) in groupProducts"
                                :key="'group-' + idx"
                                class="card mb-4 shadow-sm border-0"
                              >
                                <div class="card-body">
                                  <!-- Thông tin combo -->
                                  <div class="d-flex align-items-center mb-3">
                                    <img
                                      :src="group.baseProduct.mainImageUrl"
                                      alt="Ảnh sản phẩm"
                                      style="
                                        width: 125px;
                                        height: 156px;
                                        object-fit: cover;
                                        border-radius: 10px;
                                        background: #f8f8f8;
                                      "
                                      class="me-3"
                                    />
                                    <div>
                                      <div class="fw-bold" style="font-size: 1.1em">
                                        {{ group.baseProduct.name }}
                                      </div>
                                      <div class="text-muted small mb-1">
                                        {{ group.baseProduct.material }}
                                      </div>
                                      <div>
                                        <span class="badge bg-success me-2">
                                          <i class="bi bi-star-fill"></i>
                                          {{ group.baseProduct.rating }}/5
                                        </span>
                                        <span class="badge bg-info text-dark">{{
                                          group.baseProduct.categories
                                        }}</span>
                                      </div>
                                    </div>
                                  </div>
                                  <!-- Danh sách item trong combo -->
                                  <div class="table-responsive">
                                    <table class="table table-sm align-middle mb-0">
                                      <thead>
                                        <tr>
                                          <th></th>
                                          <th>Tên SP</th>
                                          <th>Loại</th>
                                          <th>Giá</th>
                                          <th>Số lượng</th>
                                        </tr>
                                      </thead>
                                      <tbody>
                                        <tr v-for="item in group.items" :key="item.id">
                                          <td>
                                            <input
                                              type="checkbox"
                                              :checked="selectedComboItems[item.id] > 0"
                                              disabled
                                              name="combo-item-checkbox"
                                              :value="item.id"
                                            />
                                          </td>
                                          <td>{{ group.baseProduct.name }}</td>
                                          <td>{{ item.sku }}</td>
                                          <td class="text-danger fw-bold">
                                            {{ item.price.toLocaleString() }}₫
                                          </td>
                                          <td>
                                            <div class="d-flex align-items-center">
                                              <button
                                                class="btn btn-sm btn-outline-secondary"
                                                @click="decreaseComboQty(item)"
                                                :disabled="
                                                  !selectedComboItems[item.id] ||
                                                  selectedComboItems[item.id] <= 0 ||
                                                  productItemIdRef == item.id
                                                "
                                              >
                                                -
                                              </button>
                                              <span class="mx-2">{{
                                                selectedComboItems[item.id] || 0
                                              }}</span>
                                              <button
                                                class="btn btn-sm btn-outline-secondary"
                                                @click="increaseComboQty(item, group)"
                                                :disabled="
                                                  selectedComboItems[item.id] >=
                                                    item.qty ||
                                                  getBaseProductTotalQty(group) >=
                                                    item.qty
                                                "
                                              >
                                                +
                                              </button>
                                            </div>
                                            <div class="small text-muted">
                                              Tối đa: {{ item.qty }}
                                            </div>
                                          </td>
                                        </tr>
                                      </tbody>
                                    </table>
                                  </div>
                                </div>
                              </div>
                            </div>
                          </div>
                          <div class="modal-footer">
                            <button
                              type="button"
                              class="btn btn-secondary"
                              data-bs-dismiss="modal"
                              :disabled="!iscalculateTotalQuantity"
                              @click="handleProcessCombo()"
                            >
                              Xong
                            </button>
                          </div>
                        </div>
                      </div>
                    </div>
                  </span>
                </div>
              </td>
              <td>{{ item.name }}</td>
              <td>{{ item.sku }}</td>
              <td>{{ item.price.toLocaleString() }}₫</td>
              <td class="text-center">
                <div class="d-inline-flex align-items-center border rounded px-2">
                  <button class="btn btn-sm" @click="decreaseQty(item)">−</button>
                  <span class="mx-2">{{ item.quantity }}</span>
                  <button class="btn btn-sm" @click="increaseQty(item)">＋</button>
                </div>
              </td>
              <td class="text-end">
                {{ (item.price * item.quantity).toLocaleString() }}₫
              </td>
              <td>
                <button class="btn btn-sm text-danger" @click="removeItem(item)">
                  ✕
                </button>
              </td>
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
          <button
            class="btn btn-dark w-100"
            :disabled="selectedItems.length === 0"
            @click="checkout"
          >
            Thanh toán
          </button>
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
import { ref, computed, onMounted, watch } from "vue";
import { useRouter } from "vue-router";
import { authService, cartService } from "../../Configs/api";
import {
  finalHandleCartProgress,
  handleUpdateQuantityCartWhileLogin,
} from "../../Configs/cart";
const router = useRouter();
const cart = ref([]);
const selectedItems = ref([]);
const promotions = ref([]);
const groupProducts = ref([]);
const productItemIdRef = ref({});
const promotonIdRef = ref(null);
const comboGroups = computed(() => {
  // Nhóm các sản phẩm có comboGroup và promotions.type === 'COMBO'
  const groups = {};
  cart.value.forEach((item) => {
    if (item.comboGroup && item.promotions && item.promotions.type === "COMBO") {
      if (!groups[item.comboGroup]) groups[item.comboGroup] = [];
      groups[item.comboGroup].push(item);
    }
  });
  return groups;
});
const singleProducts = computed(() =>
  cart.value.filter(
    (item) => !item.comboGroup || !item.promotions || item.promotions.type !== "COMBO"
  )
);
function isComboSelected(items) {
  // Trả về true nếu tất cả sản phẩm trong combo đều đã được chọn
  return items.every((item) => selectedItems.value.includes(item.productItemId));
}
function toggleSelectCombo(items) {
  const allSelected = isComboSelected(items);
  if (allSelected) {
    // Bỏ chọn tất cả sản phẩm trong combo
    selectedItems.value = selectedItems.value.filter(
      (id) => !items.some((item) => item.productItemId === id)
    );
  } else {
    // Thêm tất cả sản phẩm trong combo vào selectedItems
    const idsToAdd = items
      .map((item) => item.productItemId)
      .filter((id) => !selectedItems.value.includes(id));
    selectedItems.value = [...selectedItems.value, ...idsToAdd];
  }
}
function increaseComboGroupQty(items) {
  items.forEach((item) => {
    item.comboQty = (item.comboQty || 1) + 1;
    item.quantity = item.comboQty;
  });
}
function decreaseComboGroupQty(items) {
  if (items[0].comboQty > 1) {
    items.forEach((item) => {
      item.comboQty = item.comboQty - 1;
      item.quantity = item.comboQty;
    });
  }
}
function removeComboGroup(groupKey) {
  cart.value = cart.value.filter((item) => item.comboGroup !== groupKey);
  selectedItems.value = selectedItems.value.filter(
    (id) =>
      !cart.value.find(
        (item) => item.productItemId === id && item.comboGroup === groupKey
      )
  );
}
function openPromotionModal(productItemId) {
  promotions.value = [];
  productItemIdRef.value = productItemId;
  console.log("Open promotion modal for item:", productItemId);

  promotions.value.push(
    {
      id: 1,
      name: "Giảm giá 10%",
      description: "Mua combo 3 sản phẩm bất kỳ được giảm giá 10%",
      type: "COMBO",
      discountType: null,
      discountValue: null,
      comboPrice: 4500000,
      qty: 45,
      startAt: "2023-01-01T00:00:00Z",
      endAt: "2023-12-31T23:59:59Z",
      isActive: true,
      createdAt: new Date().toISOString(),
      updatedAt: new Date().toISOString(),
    },
    {
      id: 2,
      name: "Giảm giá 50%",
      description: "Mua combo 3 sản phẩm bất kỳ được giảm giá 10%",
      type: "COMBO",
      discountType: null,
      discountValue: null,
      comboPrice: 4500000,
      qty: 45,
      startAt: "2023-01-01T00:00:00Z",
      endAt: "2023-12-31T23:59:59Z",
      isActive: true,
      createdAt: new Date().toISOString(),
      updatedAt: new Date().toISOString(),
    }
  );
}
function openSpecificPromotionModal(promotionId) {
  groupProducts.value = [];
  console.log("Open specific promotion modal for promotion:", productItemIdRef.value);

  groupProducts.value.push(
    {
      baseProduct: {
        id: 2,
        name: "Áo thun trơn form rộng",
        material: "Cotton lạnh",
        mainImageUrl:
          "https://res.cloudinary.com/da2v8uqir/image/upload/v1751943855/smzc8utvxiqvvvpzbdfp.png",
        turnBuy: 95,
        rating: 5,
        createdAt: "2025-07-07T15:00:32.69",
        updatedAt: "2025-07-07T15:00:32.69",
        categories: "Áo thun",
        active: true,
        custom: false,
      },
      items: [
        {
          id: 3,
          cost: 65000.0,
          price: 160000.0,
          turnBuy: 70,
          description: "Áo thun XL, xám",
          sku: "XL-Màu trắng",
          safetyStock: 10,
          qty: 5,
          sellStart: "2025-07-07T15:00:32.69",
          sellEnd: null,
          createdAt: "2025-07-07T15:00:32.69",
          updatedAt: "2025-07-07T15:00:32.69",
          baseProducts: 2,
        },
        {
          id: 6,
          cost: 65000.0,
          price: 160000.0,
          turnBuy: 70,
          description: "Áo thun XL, xám",
          sku: "XL-Màu trắng",
          safetyStock: 10,
          qty: 5,
          sellStart: "2025-07-07T15:00:32.69",
          sellEnd: null,
          createdAt: "2025-07-07T15:00:32.69",
          updatedAt: "2025-07-07T15:00:32.69",
          baseProducts: 2,
        },
      ],
    },
    {
      baseProduct: {
        id: 1,
        name: "Áo thun unisex mùa hè",
        material: "Cotton 100%",
        mainImageUrl:
          "https://res.cloudinary.com/da2v8uqir/image/upload/v1751943855/smzc8utvxiqvvvpzbdfp.png",
        turnBuy: 120,
        rating: 4,
        createdAt: "2025-07-07T15:00:32.69",
        updatedAt: "2025-07-07T15:00:32.69",
        categories: "Áo thun",
        active: true,
        custom: false,
      },
      items: [
        {
          id: 1,
          cost: 60000.0,
          price: 150000.0,
          turnBuy: 80,
          description: "Áo thun M, trắng",
          sku: "M-Màu trắng",
          safetyStock: 10,
          qty: 2,
          sellStart: "2025-07-07T15:00:32.69",
          sellEnd: null,
          createdAt: "2025-07-07T15:00:32.69",
          updatedAt: "2025-07-07T15:00:32.69",
          baseProducts: 1,
        },
        {
          id: 2,
          cost: 60000.0,
          price: 150000.0,
          turnBuy: 60,
          description: "Áo thun L, đen",
          sku: "L-Màu đen",
          safetyStock: 10,
          qty: 2,
          sellStart: "2025-07-07T15:00:32.69",
          sellEnd: null,
          createdAt: "2025-07-07T15:00:32.69",
          updatedAt: "2025-07-07T15:00:32.69",
          baseProducts: 1,
        },
      ],
    }
  );
  // Tự động set số lượng tối đa cho item trùng id
  if (productItemIdRef.value) {
    groupProducts.value.forEach((group) => {
      group.items.forEach((item) => {
        if (item.id === productItemIdRef.value) {
          selectedComboItems.value[item.id] = item.qty;
        }
      });
    });
  }
}
// Hàm kiểm tra tổng số lượng đã chọn có đủ cho tất cả nhóm sản phẩm
function calculateTotalQuantity() {
  // Trả về true nếu tất cả group đều đủ số lượng yêu cầu
  return groupProducts.value.every((group) => {
    console.log("Checking group:", getBaseProductTotalQty(group));

    // Giả sử tất cả item trong group đều có cùng qty yêu cầu
    const requiredQty = group.items[0]?.qty || 0;
    return getBaseProductTotalQty(group) == requiredQty;
  });
}
const selectedComboItems = ref({}); // { [itemId]: số lượng đã chọn }

function getBaseProductTotalQty(group) {
  // Tính tổng số lượng đã chọn của tất cả item trong baseProduct này
  return group.items.reduce(
    (sum, item) => sum + (selectedComboItems.value[item.id] || 0),
    0
  );
}
const iscalculateTotalQuantity = ref(false); // Biến kiểm tra xem có đủ số lượng đã chọn hay không
function increaseComboQty(item, group) {
  const totalQty = getBaseProductTotalQty(group);
  if (!selectedComboItems.value[item.id]) selectedComboItems.value[item.id] = 0;
  // Chỉ cho tăng nếu tổng chưa vượt quá item.qty
  if (selectedComboItems.value[item.id] < item.qty && totalQty < item.qty) {
    selectedComboItems.value[item.id]++;
  }
  iscalculateTotalQuantity.value = calculateTotalQuantity();
}
function handleProcessCombo() {
  // Lấy danh sách item đã chọn và số lượng, theo đúng thứ tự hiển thị
  const selectedList = [];
  groupProducts.value.forEach((group) => {
    group.items.forEach((item) => {
      const qty = selectedComboItems.value[item.id] || 0;
      if (qty > 0) {
        selectedList.push({
          itemId: item.id,
          quantity: qty,
        });
      }
    });
  });
  const finalData = {
    cartId: cart.value.find((c) => c.productItemId === productItemIdRef.value)?.id, // Lấy id từ cart nếu có
    items: selectedList,
  };
  console.log("Dữ liệu gửi về BE:", finalData);
  // Gửi selectedList về backend ở đây
}

function decreaseComboQty(item) {
  if (selectedComboItems.value[item.id] > 1) {
    selectedComboItems.value[item.id]--;
  } else {
    selectedComboItems.value[item.id] = 0;
  }
  iscalculateTotalQuantity.value = calculateTotalQuantity();
}
// ...existing code...

const isAllSelected = computed(
  () =>
    cart.value.length > 0 &&
    cart.value.every((item) => selectedItems.value.includes(item.productItemId))
);

function toggleSelectAll(e) {
  if (e.target.checked) {
    selectedItems.value = cart.value.map((item) => item.productItemId);
  } else {
    selectedItems.value = [];
  }
}
async function loadCart() {
  try {
    const response = await cartService.getCart();

    console.log("Cart loaded:", response);
    if (authService.isLogged()) {
      cart.value = response.map((item) => ({
        id: item.id,
        productItemId: item.productItems,
        name: item.name,
        price: item.price,
        quantity: item.qty,
        image: item.mainImageUrl,
        promotions: item.promotionType[0] || "",
        sku: item.sku || "", // Thêm sku nếu có
        comboGroup: item.comboGroup, // Thêm comboGroup nếu có
        comboQty: item.comboQty, // Thêm comboQty nếu có
      }));
      selectedItems.value = cart.value.map((item) => item.productItemId);
    } else {
      cart.value = response.map((item) => ({
        id: item.id,
        productItemId: item.productItems,
        name: item.name,
        price: item.price,
        quantity: item.qty,
        image: item.mainImageUrl,
        promotions: item.promotions || "",
        sku: item.sku || "", // Thêm sku nếu có
        comboGroup: item.comboGroup, // Thêm comboGroup nếu có
        comboQty: item.comboQty, // Thêm comboQty nếu có
      }));
      selectedItems.value = cart.value.map((item) => item.productItems);
    }
  } catch (error) {
    console.error("Failed to load cart:", error);
  }
}

// Xóa sản phẩm
function removeItem(item) {
  cart.value = cart.value.filter((i) => i.productItemId !== item.productItemId);
  selectedItems.value = selectedItems.value.filter(
    (productItemId) => productItemId !== item.productItemId
  );
  cart.value = cart.value.filter((i) => i.productItemId !== item.productItemId);
  selectedItems.value = selectedItems.value.filter((id) => id !== item.productItemId);

  if (authService.isLogged()) {
    // ✅ Call API to remove from backend
    // await axios.delete(`http://localhost:8080/cart/delete/${item.id}`);
  } else {
    let tempCart = JSON.parse(localStorage.getItem("cart")) ?? [];
    tempCart = tempCart.filter((i) => i.productItems !== item.productItemId);
    localStorage.setItem("cart", JSON.stringify(tempCart));
  }
}
// Tăng số lượng
function increaseQty(item) {
  if (authService.isLogged()) {
    handleUpdateQuantityCartWhileLogin(item, "increase");
    item.quantity++;
  } else {
    let tempLocalList = JSON.parse(localStorage.getItem("cart")) ?? [];
    tempLocalList = tempLocalList.map((cartItem) => {
      if (cartItem.productItems === item.productItemId) {
        cartItem.qty++;
      }
      return cartItem;
    });
    localStorage.setItem("cart", JSON.stringify(tempLocalList));
    const cartItem = cart.value.find((c) => c.productItemId === item.productItemId);
    if (cartItem) {
      cartItem.quantity++;
    }
  }
}

// Giảm số lượng hoặc xóa
function decreaseQty(item) {
  if (item.quantity > 1) {
    item.quantity--;
  } else {
    handleUpdateQuantityCartWhileLogin(item, "decrease");
    removeItem(item);
  }
}

// Tính tổng tiền sản phẩm được chọn
const selectedTotal = computed(() => {
  if (authService.isLogged()) {
    return cart.value
      .filter((item) => selectedItems.value.includes(item.productItemId))
      .reduce((sum, item) => sum + item.price * item.quantity, 0);
  } else {
    return cart.value
      .filter((item) => selectedItems.value.includes(item.productItemId))
      .reduce((sum, item) => sum + item.price * item.quantity, 0);
  }
});

// Gửi dữ liệu thanh toán
function checkout() {
  const selectedProducts = cart.value.filter((item) =>
    selectedItems.value.includes(item.productItemId)
  );
  router.push({
    name: "CheckoutPage",
    query: {
      products: JSON.stringify(selectedProducts),
    },
  });
}

// Tải giỏ hàng khi trang được mount
onMounted(() => {
  loadCart();
  // Thêm đơn hàng mẫu nếu cart rỗng (chỉ để test)
  // setTimeout(() => {
  //   if (cart.value.length === 0) {
  //     cart.value = [
  //       {
  //         id: 1,
  //         productItemId: 1,
  //         name: "Áo thun basic",
  //         sku: "Xanh-M",
  //         price: 120000,
  //         quantity: 2,
  //         image:
  //           "https://res.cloudinary.com/da2v8uqir/image/upload/v1751960219/nvij8xogfmve5pgzkf2f.png",
  //         comboType: "DISCOUNT",
  //       },

  //       {
  //         id: 3,
  //         productItemId: 3,
  //         name: "Váy yếm jean",
  //         sku: "Xanh-M",

  //         price: 300000,
  //         quantity: 1,
  //         image:
  //           "https://res.cloudinary.com/da2v8uqir/image/upload/v1751943855/smzc8utvxiqvvvpzbdfp.png",
  //         comboType: "COMBO",
  //       },
  //       {
  //         id: 4,
  //         productItemId: 103,
  //         name: "Váy yếm jean",
  //         sku: "Xanh-M",

  //         price: 300000,
  //         quantity: 1,
  //         image:
  //           "https://res.cloudinary.com/da2v8uqir/image/upload/v1751943855/smzc8utvxiqvvvpzbdfp.png",
  //         comboType: "",
  //       },
  //       {
  //         id: 6,
  //         productItemId: 102,
  //         name: "Quần jeans xanh",
  //         sku: "Xanh-M",

  //         price: 250000,
  //         quantity: 1,
  //         image:
  //           "https://res.cloudinary.com/da2v8uqir/image/upload/v1751964597/qclbaowfuxqfbudyc5fh.jpg",
  //         comboType: "COMBO",
  //       },
  //     ];
  //     selectedItems.value = cart.value.map((item) => item.productItemId);
  //   }
  // }, 200);
});

// watch(() => selectedItems.value, (newvalue) => {
//   console.log(selectedProducts[newvalue])
// })
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
