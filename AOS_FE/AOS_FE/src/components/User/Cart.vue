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
                  <div class="card mb-4 border-warning shadow-sm">
                    <div
                      class="card-header bg-warning bg-opacity-25 fw-bold text-danger d-flex align-items-center justify-content-between"
                    >
                      <div class="d-flex align-items-center">
                        <input
                          type="checkbox"
                          class="me-2"
                          :checked="isComboSelected(items)"
                          @change="toggleSelectCombo(items)"
                          :disabled="!isPromotionValid(items[0].promotions)"
                        />
                        <i class="bi bi-gift-fill mx-1"></i>
                        <span>
                          <span class="badge bg-danger me-2">Combo</span>
                          {{ items[0].promotions.name }}
                          <span class="text-muted ms-2">{{
                            items[0].promotions.description
                          }}</span>
                          <span
                            v-if="!isPromotionValid(items[0].promotions)"
                            class="badge ms-2"
                            :class="{
                              'bg-danger':
                                getPromotionStatusMessage(items[0].promotions) ===
                                'Hết hạn',
                              'bg-warning':
                                getPromotionStatusMessage(items[0].promotions) ===
                                'Hết hàng',
                              'bg-secondary':
                                getPromotionStatusMessage(items[0].promotions) ===
                                'Ngừng hoạt động',
                              'bg-info':
                                getPromotionStatusMessage(items[0].promotions) ===
                                'Chưa bắt đầu',
                            }"
                          >
                            {{ getPromotionStatusMessage(items[0].promotions) }}
                          </span>
                        </span>
                      </div>
                      <button
                        class="btn btn-sm btn-outline-danger"
                        @click="removeComboGroupId(items)"
                        :disabled="!isPromotionValid(items[0].promotions)"
                      >
                        <i class="bi bi-x-lg"></i>
                      </button>
                    </div>
                    <div class="card-body p-0 bg-light rounded-bottom">
                      <div class="d-flex align-items-center p-2">
                        <span class="me-2">Số lượng combo:</span>
                        <button
                          class="btn btn-sm btn-outline-secondary"
                          @click="decreaseComboGroupQty(items)"
                          :disabled="
                            items[0].comboQty <= 1 ||
                            !isPromotionValid(items[0].promotions)
                          "
                        >
                          -
                        </button>
                        <span class="mx-2 fw-bold">{{ items[0].comboQty }}</span>
                        <button
                          class="btn btn-sm btn-outline-secondary"
                          @click="increaseComboGroupQty(items)"
                          :disabled="!isPromotionValid(items[0].promotions)"
                        >
                          +
                        </button>
                        <span class="fw-bold text-danger ms-auto">
                          Tổng:
                          <template v-if="items[0].promotions.comboPrice > 0">
                            {{
                              (
                                items[0].promotions.comboPrice * items[0].comboQty
                              ).toLocaleString()
                            }}₫
                          </template>
                          <template v-else>
                            <span class="text-muted">Tính tại quầy</span>
                          </template>
                        </span>
                      </div>
                      <table class="table table-borderless align-middle mb-0">
                        <tbody>
                          <tr
                            v-for="item in items"
                            :key="item.id"
                            class="align-middle"
                            :class="{
                              'table-success bg-opacity-10': item.isGift,
                              'table-secondary bg-opacity-10': !isPromotionValid(
                                items[0].promotions
                              ),
                            }"
                          >
                            <td>
                              <img
                                :src="item.image"
                                class="img-thumbnail"
                                style="
                                  width: 100px;
                                  height: 125px;
                                  object-fit: cover;
                                  border-radius: 8px;
                                "
                              />
                            </td>
                            <td>
                              <div class="fw-bold">
                                {{ item.name }}
                                <span
                                  v-if="item.isGift"
                                  class="badge bg-gradient bg-success ms-2 position-relative"
                                >
                                  <i class="bi bi-gift-fill me-1"></i>
                                  Quà tặng
                                  <span
                                    class="position-absolute top-0 start-100 translate-middle p-1 bg-warning border border-light rounded-circle"
                                  >
                                    <span class="visually-hidden">Miễn phí</span>
                                  </span>
                                </span>
                              </div>
                              <div class="small text-muted">{{ item.sku }}</div>
                            </td>
                            <td class="text-center">{{ item.quantity }}</td>
                            <td class="text-end">
                              <template v-if="items[0].promotions.comboPrice > 0">
                                <span
                                  v-if="
                                    item.price !== undefined &&
                                    item.quantity !== undefined
                                  "
                                >
                                  <template v-if="item.isGift">
                                    <div class="d-flex flex-column align-items-end">
                                      <del class="text-muted small">
                                        {{
                                          (item.price * item.quantity).toLocaleString()
                                        }}₫
                                      </del>
                                      <span class="text-success fw-bold">
                                        <i class="bi bi-gift-fill me-1"></i>
                                        Miễn phí
                                      </span>
                                    </div>
                                  </template>
                                  <template v-else>
                                    {{ (item.price * item.quantity).toLocaleString() }}₫
                                  </template>
                                </span>
                              </template>
                              <template v-else>
                                <span class="text-muted">Tính tại quầy</span>
                              </template>
                            </td>
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
                <input type="checkbox" v-model="selectedItems" :value="item.id" />
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
                      v-if="item.promotions.type === 'COMBO'"
                      data-bs-toggle="modal"
                      data-bs-target="#exampleModalToggle"
                      class="btn border-0 position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger"
                      @click="openPromotionModal(item)"
                      type="button"
                    >
                      <i class="bi bi-gift-fill"></i>Ưu đãi
                    </button>
                    <span
                      v-else-if="item.promotions.type === 'DISCOUNT'"
                      class="border-0 position-absolute top-0 start-100 translate-middle badge rounded-pill bg-warning"
                    >
                      <i class="bi bi-tag-fill"></i>-{{
                        Math.round((item.promotions.discountValue / item.price) * 100)
                      }}%
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
                                        <tr
                                          v-for="item in group.items"
                                          :key="item.id"
                                          :class="item.isGift ? 'table-success' : ''"
                                        >
                                          <td>
                                            <input
                                              type="checkbox"
                                              :checked="selectedComboItems[item.id] > 0"
                                              disabled
                                              name="combo-item-checkbox"
                                              :value="item.id"
                                            />
                                          </td>
                                          <td>
                                            <div class="d-flex align-items-center">
                                              {{ group.baseProduct.name }}
                                              <span
                                                v-if="item.isGift"
                                                style="background-color: red"
                                                class="badge ms-2"
                                                ><i class="bi bi-gift-fill"></i> Quà
                                                tặng</span
                                              >
                                            </div>
                                          </td>
                                          <td>{{ item.sku }}</td>
                                          <td class="text-danger fw-bold">
                                            {{ item.price.toLocaleString() }}₫
                                          </td>
                                          <td>
                                            <template v-if="item.isGift">
                                              <span class="fw-bold text-success">{{
                                                item.qty
                                              }}</span>
                                            </template>
                                            <template v-else>
                                              <div class="d-flex align-items-center">
                                                <button
                                                  class="btn btn-sm btn-outline-secondary"
                                                  @click="decreaseComboQty(item)"
                                                  :disabled="
                                                    !selectedComboItems[item.id] ||
                                                    selectedComboItems[item.id] <= 0
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
                                            </template>
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
              <td v-if="item.promotions.type == 'DISCOUNT'">
                <del>{{ item.price.toLocaleString() }}đ</del>
                {{ (item.price - item.promotions.discountValue).toLocaleString() }}₫
              </td>
              <td class="text-end" v-else>{{ item.price.toLocaleString() }}₫</td>
              <td class="text-center">
                <div class="d-inline-flex align-items-center border rounded px-2">
                  <button class="btn btn-sm" @click="decreaseQty(item)">−</button>
                  <span class="mx-2">{{ item.quantity }}</span>
                  <button class="btn btn-sm" @click="increaseQty(item)">＋</button>
                </div>
              </td>
              <td v-if="item.promotions.type == 'DISCOUNT'" class="text-end">
                {{
                  (
                    (item.price - item.promotions.discountValue) *
                    item.quantity
                  ).toLocaleString()
                }}₫
              </td>
              <td class="text-end" v-else>
                {{ (item.price * item.quantity).toLocaleString() }}
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
              <span>Đã Giảm Giá</span>
              <span>{{ totalDiscount.toLocaleString() }}₫</span>
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
import api, { authService, cartService } from "../../Configs/api";
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
  // Gom nhóm combo chỉ theo comboGroupId
  const groups = {};
  cart.value.forEach((item) => {
    if (item.comboGroupId && item.promotions && item.promotions.type === "COMBO") {
      const groupKey = item.comboGroupId;
      if (!groups[groupKey]) groups[groupKey] = [];
      groups[groupKey].push(item);
    }
  });
  return groups;
});

// Hàm kiểm tra promotion có còn hợp lệ không
function isPromotionValid(promotion) {
  if (!promotion) return false;

  // Kiểm tra active
  if (!promotion.active) return false;

  // Kiểm tra số lượng
  if (promotion.qty <= 0) return false;

  // Kiểm tra thời gian
  const now = new Date();
  const startAt = new Date(promotion.startAt);
  const endAt = new Date(promotion.endAt);

  if (now < startAt || now > endAt) return false;

  return true;
}

// Hàm lấy thông báo trạng thái promotion
function getPromotionStatusMessage(promotion) {
  if (!promotion) return "Không có thông tin";

  // Kiểm tra active
  if (!promotion.active) return "Ngừng hoạt động";

  // Kiểm tra số lượng
  if (promotion.qty <= 0) return "Hết hàng";

  // Kiểm tra thời gian
  const now = new Date();
  const startAt = new Date(promotion.startAt);
  const endAt = new Date(promotion.endAt);

  if (now < startAt) return "Chưa bắt đầu";
  if (now > endAt) return "Hết hạn";

  return "Đang hoạt động";
}
const singleProducts = computed(() =>
  cart.value.filter(
    (item) => !item.comboGroup || !item.promotions || item.promotions.type !== "COMBO"
  )
);
const totalDiscount = computed(() => {
  let sum = 0;
  const countedComboGroups = new Set();
  // Gom nhóm combo theo comboGroupId
  const allComboGroups = {};
  cart.value.forEach((item) => {
    if (item.comboGroupId && item.promotions && item.promotions.type === "COMBO") {
      const groupKey = item.comboGroupId;
      if (!allComboGroups[groupKey]) allComboGroups[groupKey] = [];
      allComboGroups[groupKey].push(item);
    }
  });

  cart.value.forEach((item) => {
    if (!selectedItems.value.includes(item.id)) return;

    // DISCOUNT - chỉ áp dụng nếu promotion hợp lệ
    if (
      item.promotions &&
      item.promotions.type === "DISCOUNT" &&
      isPromotionValid(item.promotions)
    ) {
      sum += item.promotions.discountValue * item.quantity;
      return;
    }

    // COMBO: chỉ tính giảm giá cho 1 lần duy nhất mỗi comboGroupId và promotion hợp lệ
    if (
      item.promotions &&
      item.promotions.type === "COMBO" &&
      item.promotions.comboPrice > 0 &&
      isPromotionValid(item.promotions)
    ) {
      const groupKey = item.comboGroupId;
      if (
        !countedComboGroups.has(groupKey) &&
        allComboGroups[groupKey] &&
        allComboGroups[groupKey].every((i) => selectedItems.value.includes(i.id))
      ) {
        const comboQty = item.comboQty || 1;
        const totalOrigin = allComboGroups[groupKey].reduce(
          (s, i) => s + i.price * (i.quantity || comboQty),
          0
        );
        const totalCombo = item.promotions.comboPrice * comboQty;
        sum += totalOrigin - totalCombo;
        countedComboGroups.add(groupKey);
      }
    }
  });
  return sum;
});
function isComboSelected(items) {
  console.log("lựa chọn item : ", selectedItems.value);
  // Nếu promotion không hợp lệ thì không thể chọn
  if (!isPromotionValid(items[0].promotions)) {
    return false;
  }
  // Trả về true nếu tất cả sản phẩm trong combo đều đã được chọn
  return items.every((item) => selectedItems.value.includes(item.id));
}
function toggleSelectCombo(items) {
  // Nếu promotion không hợp lệ thì không cho phép thay đổi
  if (!isPromotionValid(items[0].promotions)) {
    return;
  }

  // toggleSelectCombo
  const allSelected = isComboSelected(items);
  if (allSelected) {
    selectedItems.value = selectedItems.value.filter(
      (id) => !items.some((item) => item.id === id)
    );
  } else {
    const idsToAdd = items
      .map((item) => item.id)
      .filter((id) => !selectedItems.value.includes(id));
    selectedItems.value = [...selectedItems.value, ...idsToAdd];
  }
}
function increaseComboGroupQty(items) {
  console.log("items combo: ", items);

  // Lấy id cart của các item trong comboGroup này
  const cartIds = items.map((i) => i.id);
  cart.value.forEach((item) => {
    if (item.comboGroup === items[0].comboGroup && cartIds.includes(item.id)) {
      const tempQty = item.quantity / item.comboQty; // 3/1
      item.comboQty = (item.comboQty || 1) + 1;
      item.quantity = tempQty + item.quantity;
      authService.updateCart(tempQty);
    }
  });
  if (authService.isLogged()) {
    api
      .put("/cart/updateComboQty", {
        comboQty: items[0].comboQty,
        comboGroupId: items[0].comboGroupId, // Gửi comboGroupId nếu có
        type: "increase",
      })
      .then((response) => {
        console.log("Combo quantity updated successfully:", response.data);
        // Cập nhật lại giỏ hàng sau khi gửi
        // loadCart();
      })
      .catch((error) => {
        console.error("Error updating combo quantity:", error);
      });
  } else {
    let tempLocalList = JSON.parse(localStorage.getItem("cart")) ?? [];
    tempLocalList = tempLocalList.map((cartItem) => {
      if (cartIds.includes(cartItem.id)) {
        const tempQty = cartItem.qty / cartItem.comboQty; // 3/1
        cartItem.comboQty = cartItem.comboQty - 1;
        cartItem.qty = cartItem.qty - tempQty;
      }
      return cartItem;
    });
    localStorage.setItem("cart", JSON.stringify(tempLocalList));
  }
}

function decreaseComboGroupQty(items) {
  if (items[0].comboQty > 1) {
    const cartIds = items.map((i) => i.id);
    cart.value.forEach((item) => {
      if (item.comboGroup === items[0].comboGroup && cartIds.includes(item.id)) {
        const tempQty = item.quantity / item.comboQty; // 3/1
        item.comboQty = item.comboQty - 1;
        item.quantity = item.quantity - tempQty;
        authService.updateCart(-tempQty); // Giảm số lượng trong giỏ hàng
      }
    });
    if (authService.isLogged()) {
      api
        .put("/cart/updateComboQty", {
          comboQty: items[0].comboQty,
          comboGroupId: items[0].comboGroupId, // Gửi comboGroupId nếu có
          type: "decrease",
        })
        .then((response) => {
          console.log("Combo quantity updated successfully:", response.data);
          // Cập nhật lại giỏ hàng sau khi gửi
          // loadCart();
        })
        .catch((error) => {
          console.error("Error updating combo quantity:", error);
        });
    } else {
      let tempLocalList = JSON.parse(localStorage.getItem("cart")) ?? [];
      tempLocalList = tempLocalList.map((cartItem) => {
        if (cartIds.includes(cartItem.id)) {
          const tempQty = cartItem.qty / cartItem.comboQty; // 3/1
          cartItem.comboQty = cartItem.comboQty - 1;
          cartItem.qty = cartItem.qty - tempQty;
        }
        return cartItem;
      });
      localStorage.setItem("cart", JSON.stringify(tempLocalList));
    }
  }
}
function removeComboGroupId(items) {
  console.log("Removing combo group:", items);
  const cartIds = items.map((i) => i.id);
  cart.value = cart.value.filter((item) => !cartIds.includes(item.id));
  if (authService.isLogged()) {
    api
      .delete(`/cart/deleteCombo/${items[0].comboGroupId}`)
      .then((response) => {
        console.log("Combo group removed successfully:", response.data);
        // Cập nhật lại giỏ hàng sau khi gửi
        loadCart();
      })
      .catch((error) => {
        console.error("Error removing combo group:", error);
      });
  } else {
    let tempLocalList = JSON.parse(localStorage.getItem("cart")) ?? [];
    tempLocalList = tempLocalList.filter((item) => !cartIds.includes(item.id));
    localStorage.setItem("cart", JSON.stringify(tempLocalList));
  }
}
const cartIdOfOpenPromotionModal = ref(null);
function openPromotionModal(item) {
  promotions.value = [];
  cartIdOfOpenPromotionModal.value = item.id;
  productItemIdRef.value = item.productItemId;
  console.log("Open promotion modal for item:", item.productItemId);
  api
    .get(`/Promotions?productItemId=${item.productItemId}`)
    .then((response) => {
      promotions.value = response.data;
      console.log("Promotions for item:", item.productItemId, promotions.value);
    })
    .catch((error) => {
      console.error("Error fetching promotions:", error);
    });
}
function openSpecificPromotionModal(promotionId) {
  groupProducts.value = [];
  promotonIdRef.value = promotionId;
  console.log("Open specific promotion modal for promotion:", productItemIdRef.value);
  api
    .get(`/Promotions/${promotionId}`)
    .then((response) => {
      groupProducts.value = response.data;
      console.log("Group products for promotion:", promotionId, groupProducts.value);
      // Tự động set số lượng  cho item trùng id
      if (productItemIdRef.value) {
        groupProducts.value.forEach((group) => {
          group.items.forEach((item) => {
            if (item.id === productItemIdRef.value) {
              selectedComboItems.value[item.id] = 1; // Mặc định chọn 1 sản phẩm
            }
            if (item.isGift) {
              selectedComboItems.value[item.id] = item.qty; //  chọn sản phẩm tặng kèm
            }
          });
        });
        iscalculateTotalQuantity.value = calculateTotalQuantity();
      }
    })
    .catch((error) => {
      console.error("Error fetching specific promotion:", error);
    });
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
          promotionId: promotonIdRef.value, // Lấy promotionId từ modal
        });
      }
    });
  });
  const finalData = {
    cartId: cartIdOfOpenPromotionModal.value, // Lấy id từ cart nếu có
    items: selectedList,
  };
  console.log("Dữ liệu combo:", finalData);

  api
    .post("/cart/addCombo", finalData)
    .then((response) => {
      console.log("Combo processed successfully:", response.data);
      // Cập nhật lại giỏ hàng sau khi gửi
      loadCart();
    })
    .catch((error) => {
      console.error("Error processing combo:", error);
    });
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
    cart.value.every((item) => selectedItems.value.includes(item.id))
);

function toggleSelectAll(e) {
  if (e.target.checked) {
    selectedItems.value = cart.value.map((item) => item.id);
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
        promotions: item.promotions || "",
        sku: item.sku || "", // Thêm sku nếu có
        comboGroup: item.comboGroup, // Thêm comboGroup nếu có
        comboQty: item.comboQty, // Thêm comboQty nếu có
        comboGroupId: item.comboGroupId, // Thêm comboGroupId nếu có
        isGift: item.isGift || false, // Thêm isGift nếu có
      }));
      authService.setCart(0);
      authService.updateCart(cart.value.reduce((sum, item) => sum + item.quantity, 0));
      selectedItems.value = cart.value.map((item) => item.id);
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
        comboGroupId: item.comboGroupId, // Thêm comboGroupId nếu co
        isGift: item.isGift || false, // Thêm isGift nếu có
      }));
      selectedItems.value = cart.value.map((item) => item.productItems);
    }
  } catch (error) {
    console.error("Failed to load cart:", error);
  }
}

// Xóa sản phẩm
function removeItem(item) {
  cart.value = cart.value.filter((i) => i.id !== item.id);
  selectedItems.value = selectedItems.value.filter((id) => id !== item.id);
  cart.value = cart.value.filter((i) => i.productItemId !== item.productItemId);
  selectedItems.value = selectedItems.value.filter((id) => id !== item.productItemId);

  if (authService.isLogged()) {
    //✅ Call API to remove from backend
    api
      .delete(`/cart/${item.id}`)
      .then(() => {
        console.log("Item removed successfully");
        // Cập nhật lại giỏ hàng sau khi xóa
        authService.updateCart(-item.quantity);
        loadCart();
      })
      .catch((error) => {
        console.error("Error removing item:", error);
      });
  } else {
    let tempCart = JSON.parse(localStorage.getItem("cart")) ?? [];
    tempCart = tempCart.filter((i) => i.productItems !== item.productItemId);
    localStorage.setItem("cart", JSON.stringify(tempCart));
  }
}
// Tăng số lượng
// Tăng số lượng sản phẩm đơn lẻ
function increaseQty(item) {
  // Nếu là sản phẩm combo thì không xử lý ở đây
  if (item.comboGroup) return;

  if (authService.isLogged()) {
    handleUpdateQuantityCartWhileLogin(item, "increase");
    // Tìm đúng item theo id cart
    const cartItem = cart.value.find((c) => c.id === item.id);
    if (cartItem) {
      cartItem.quantity++;
      authService.updateCart(1);
    }
  } else {
    let tempLocalList = JSON.parse(localStorage.getItem("cart")) ?? [];
    tempLocalList = tempLocalList.map((cartItem) => {
      if (cartItem.id === item.id) {
        cartItem.qty++;
      }
      return cartItem;
    });
    localStorage.setItem("cart", JSON.stringify(tempLocalList));
    const cartItem = cart.value.find((c) => c.id === item.id);
    if (cartItem) cartItem.quantity++;
  }
}

// Giảm số lượng hoặc xóa sản phẩm đơn lẻ
function decreaseQty(item) {
  // Nếu là sản phẩm combo thì không xử lý ở đây
  if (item.comboGroup) return;

  if (item.quantity > 1) {
    if (authService.isLogged()) {
      handleUpdateQuantityCartWhileLogin(item, "decrease");
      const cartItem = cart.value.find((c) => c.id === item.id);
      if (cartItem) {
        cartItem.quantity--;
        authService.updateCart(-1);
      }
    } else {
      let tempLocalList = JSON.parse(localStorage.getItem("cart")) ?? [];
      tempLocalList = tempLocalList.map((cartItem) => {
        if (cartItem.id === item.id && cartItem.qty > 1) {
          cartItem.qty--;
        }
        return cartItem;
      });
      localStorage.setItem("cart", JSON.stringify(tempLocalList));
      const cartItem = cart.value.find((c) => c.id === item.id);
      if (cartItem) cartItem.quantity--;
    }
  } else {
    handleUpdateQuantityCartWhileLogin(item, "decrease");
    removeItem(item);
  }
}

const selectedTotal = computed(() => {
  let sum = 0;
  const countedComboGroups = new Set();
  // Gom nhóm combo theo comboGroupId
  const allComboGroups = {};
  cart.value.forEach((item) => {
    if (item.comboGroupId && item.promotions && item.promotions.type === "COMBO") {
      const groupKey = item.comboGroupId;
      if (!allComboGroups[groupKey]) allComboGroups[groupKey] = [];
      allComboGroups[groupKey].push(item);
    }
  });

  cart.value.forEach((item) => {
    if (!selectedItems.value.includes(item.id)) return;

    // SẢN PHẨM LẺ: không có comboGroupId
    if (!item.comboGroupId) {
      // Nếu là DISCOUNT và promotion hợp lệ
      if (
        item.promotions &&
        item.promotions.type === "DISCOUNT" &&
        isPromotionValid(item.promotions)
      ) {
        sum += (item.price - item.promotions.discountValue) * item.quantity;
      } else {
        sum += item.price * item.quantity;
      }
      return;
    }

    // SẢN PHẨM COMBO
    if (
      item.promotions &&
      item.promotions.type === "COMBO" &&
      item.promotions.comboPrice > 0 &&
      isPromotionValid(item.promotions)
    ) {
      const groupKey = item.comboGroupId;
      if (countedComboGroups.has(groupKey)) return;

      // Nếu đủ combo: cộng comboPrice * comboQty
      if (
        allComboGroups[groupKey] &&
        allComboGroups[groupKey].every((i) => selectedItems.value.includes(i.id))
      ) {
        sum += item.promotions.comboPrice * (item.comboQty || 1);
        countedComboGroups.add(groupKey);
        return;
      } else {
        // Nếu chưa đủ combo: cộng giá lẻ từng sản phẩm đã chọn trong nhóm này
        allComboGroups[groupKey].forEach((i) => {
          if (selectedItems.value.includes(i.id)) {
            sum += i.price * i.quantity;
          }
        });
        countedComboGroups.add(groupKey);
        return;
      }
    } else if (item.comboGroupId) {
      // Trường hợp combo không hợp lệ (hết hạn/hết hàng): tính giá lẻ
      const groupKey = item.comboGroupId;
      if (!countedComboGroups.has(groupKey)) {
        const allComboGroups = {};
        cart.value.forEach((cartItem) => {
          if (
            cartItem.comboGroupId &&
            cartItem.promotions &&
            cartItem.promotions.type === "COMBO"
          ) {
            const gKey = cartItem.comboGroupId;
            if (!allComboGroups[gKey]) allComboGroups[gKey] = [];
            allComboGroups[gKey].push(cartItem);
          }
        });

        if (allComboGroups[groupKey]) {
          allComboGroups[groupKey].forEach((i) => {
            if (selectedItems.value.includes(i.id)) {
              sum += i.price * i.quantity;
            }
          });
        }
        countedComboGroups.add(groupKey);
      }
    }
  });
  return sum;
});
// Gửi dữ liệu thanh toán
function checkout() {
  const selectedProducts = cart.value.filter((item) =>
    selectedItems.value.includes(item.id)
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
