<template>
  <div style="" class="container-fluid px-5">
    <div class="row mt-4 g-4">
      <!-- start categories -->
      <div class="col-3">
        <h3><span class="line"></span>Phân loại</h3>
        <div class="filter-section">
          <div v-for="(items, groupName, index) in mapVarriants" :key="groupName" class="filter-group">
            <div class="accordion-item">
              <h2 class="accordion-header">
                <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                  :data-bs-target="'#flush-collapseOne-' + index" aria-expanded="false"
                  aria-controls="flush-collapseOne">
                  <h3><span class="line"></span> {{ groupName }}</h3>
                </button>
              </h2>
              <div :id="'flush-collapseOne-' + index" class="accordion-collapse collapse"
                data-bs-parent="#accordionFlushExample">
                <ul class="filter-list">
                  <li v-for="item in items" :key="item.id">
                    <label>
                      <input type="checkbox" :name="groupName" :value="item.signalSku" v-model="selected[groupName]" />
                      {{ item.description }}
                    </label>
                  </li>
                </ul>
              </div>
            </div>
          </div>
          <hr class="divider" />

          <div class="filter-group">
            <!-- Khoảng giá -->
            <h3><span class="line"></span> Khoảng giá</h3>
            <ul class="filter-list">
              <li>
                <label><input type="radio" name="price" v-model="selectedPrice" value="100-200" /> 100k -
                  200k</label>
              </li>
              <li>
                <label><input type="radio" name="price" v-model="selectedPrice" value="200-500" /> 200k -
                  500k</label>
              </li>
              <li>
                <label><input type="radio" name="price" v-model="selectedPrice" value="500-700" /> 500k -
                  700k</label>
              </li>
              <li>
                <label><input type="radio" name="price" v-model="selectedPrice" value=">700" /> &gt; 700k</label>
              </li>
            </ul>
            <hr class="divider" />

            <!-- Đánh giá -->
            <h3><span class="line"></span> Đánh giá</h3>
            <ul class="filter-list">
              <li>
                <label><input type="checkbox" name="rating" value="5" />
                  <span class="stars">★★★★★</span> (5)
                </label>
              </li>
              <li>
                <label><input type="checkbox" name="rating" value="4" />
                  <span class="stars">★★★★☆</span> (4+)
                </label>
              </li>
              <li>
                <label><input type="checkbox" name="rating" value="3" />
                  <span class="stars">★★★☆☆</span> (3+)
                </label>
              </li>
            </ul>
          </div>
        </div>
      </div>
      <!-- end categories -->
      <div class="col-9">
        <h1>Sản phẩm của chúng tôi</h1>
        <div class="searchBox mt-4">
          <input class="searchInput" type="text" name="" placeholder="Search something" />
          <button class="searchButton" href="#">
            <svg xmlns="http://www.w3.org/2000/svg" width="29" height="29" viewBox="0 0 29 29" fill="none">
              <g clip-path="url(#clip0_2_17)">
                <g filter="url(#filter0_d_2_17)">
                  <path
                    d="M23.7953 23.9182L19.0585 19.1814M19.0585 19.1814C19.8188 18.4211 20.4219 17.5185 20.8333 16.5251C21.2448 15.5318 21.4566 14.4671 21.4566 13.3919C21.4566 12.3167 21.2448 11.252 20.8333 10.2587C20.4219 9.2653 19.8188 8.36271 19.0585 7.60242C18.2982 6.84214 17.3956 6.23905 16.4022 5.82759C15.4089 5.41612 14.3442 5.20435 13.269 5.20435C12.1938 5.20435 11.1291 5.41612 10.1358 5.82759C9.1424 6.23905 8.23981 6.84214 7.47953 7.60242C5.94407 9.13789 5.08145 11.2204 5.08145 13.3919C5.08145 15.5634 5.94407 17.6459 7.47953 19.1814C9.01499 20.7168 11.0975 21.5794 13.269 21.5794C15.4405 21.5794 17.523 20.7168 19.0585 19.1814Z"
                    stroke="white" stroke-width="3" stroke-linecap="round" stroke-linejoin="round"
                    shape-rendering="crispEdges"></path>
                </g>
              </g>
              <defs>
                <filter id="filter0_d_2_17" x="-0.418549" y="3.70435" width="29.7139" height="29.7139"
                  filterUnits="userSpaceOnUse" color-interpolation-filters="sRGB">
                  <feFlood flood-opacity="0" result="BackgroundImageFix"></feFlood>
                  <feColorMatrix in="SourceAlpha" type="matrix" values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 127 0"
                    result="hardAlpha"></feColorMatrix>
                  <feOffset dy="4"></feOffset>
                  <feGaussianBlur stdDeviation="2"></feGaussianBlur>
                  <feComposite in2="hardAlpha" operator="out"></feComposite>
                  <feColorMatrix type="matrix" values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0.25 0"></feColorMatrix>
                  <feBlend mode="normal" in2="BackgroundImageFix" result="effect1_dropShadow_2_17"></feBlend>
                  <feBlend mode="normal" in="SourceGraphic" in2="effect1_dropShadow_2_17" result="shape"></feBlend>
                </filter>
                <clipPath id="clip0_2_17">
                  <rect width="28.0702" height="28.0702" fill="white" transform="translate(0.403503 0.526367)"></rect>
                </clipPath>
              </defs>
            </svg>
          </button>
        </div>
        <h3 class="mt-4">hien thi 12 trong so 34 san pham</h3>

        <div class="product-flatform row mt-4 g-4">
          <div v-for="product in products" :key="product.id" class="col-4">
            <div style="border: 0px" class="card position-relative overflow-hidden rounded-4">
              <!-- Label Giảm giá -->
              <div class="position-absolute top-0 start-0 bg-danger text-white px-3 py-1 m-3 shadow-sm"
                style="border-radius: 12px; font-size: 0.85rem; z-index: 10">
                Giảm giá
              </div>

              <!-- Bo góc ảnh luôn -->
              <img class="card-img rounded-4 custom-shadow" style="height: 450px; object-fit: cover"
                src="https://firebasestorage.googleapis.com/v0/b/datn-cube.firebasestorage.app/o/products%2Fao_bomber_nu.webp?alt=media&token=1e7dafd1-5898-4893-92cb-72342f849d07"
                alt="Card
              image" />

              <!-- Nội dung -->
              <div class="card-body">
                <div class="card-title">
                  <i class="bi bi-star-fill text-warning"></i>
                  <i class="bi bi-star-fill text-warning"></i>
                  <i class="bi bi-star-fill text-warning"></i>
                  <i class="bi bi-star-half text-warning"></i>
                  <i class="bi bi-star text-warning"></i>
                  <span> (5,4k) revivews</span>
                </div>
                <h5 class="card-text"><del>450 000 VND</del> 300 000 VND</h5>

                <p class="card-text">{{ product.name }}</p>

                <div class="d-flex justify-content-end">
                  <!-- From Uiverse.io by AKAspidey01 -->

                  <div class="main-section rounded-4">
                    <button class="first-button">Còn hàng</button>
                    <button class="second-button" @click="openModal(product)">
                      <svg viewBox="0 0 24 24" width="20" height="20" stroke="#ffd300" stroke-width="2" fill="none"
                        stroke-linecap="round" stroke-linejoin="round" class="css-i6dzq1">
                        <circle cx="9" cy="21" r="1"></circle>
                        <circle cx="20" cy="21" r="1"></circle>
                        <path d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6"></path>
                      </svg>
                      3,4k đã bán
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div v-if="showModal" class="modal fade show d-block" tabindex="-1" style="background-color: rgba(0,0,0,0.5)">
            <div class="modal-dialog modal-lg">
              <div class="modal-content">
                <div class="modal-header">
                  <h5 class="modal-title">{{ selectedProduct?.name }}</h5>
                  <button type="button" class="btn-close" @click="closeModal"></button>
                </div>
                <div class="modal-body">
                  <img
                    :src="selectedProduct?.imageUrl || 'https://firebasestorage.googleapis.com/v0/b/datn-cube.firebasestorage.app/o/products%2Fao_bomber_nu.webp?alt=media&token=1e7dafd1-5898-4893-92cb-72342f849d07'"
                    class="img-fluid mb-3" />
                  <p>{{ selectedProduct?.description }}</p>
                  <p><strong>Giá:</strong> {{ selectedProduct?.price || '---' }} VND</p>
                  <p><strong>Vật liệu:</strong> {{ selectedProduct?.material || '---' }} </p>
                  <p><strong>Biến thể:</strong> {{ selectedProduct?.sku || '---' }} </p>
                  <label for="qtyInput">Số lượng:</label>
                  <input v-model="quantity" type="number" id="qtyInput" min="1" class="form-control w-25" />
                </div>
                <div class="modal-footer">
                  <button class="btn btn-secondary" @click="closeModal">Đóng</button>
                  <button class="btn btn-primary" @click="addToCart">Thêm vào giỏ</button>
                </div>
              </div>
            </div>
          </div>
          <PageNavigative :totalPage="data" v-model:currentPage="pageIndex" v-model:currentSize="pageSize" />
        </div>
      </div>
    </div>
  </div>
</template>
<script setup>
import { ref, onMounted, watch } from "vue";

import axios from "axios";
import api, { authService } from "../../Configs/api";
import PageNavigative from "../Module/PageNavigative.vue";
import { finalHandleCartProgress } from "../../Configs/cart";
const mapVarriants = ref({});
const data = ref([])
const selected = ref([]);
const skuColorLike = ref("");
const skuSizeLike = ref("");
const minPriceReq = ref(1);
const maxPriceReq = ref(200000000);
const selectedPrice = ref("");
const products = ref([]);
const pageIndex = ref(0);
const pageSize = ref(5);
const totalPages = ref(0);
const showModal = ref(false);
const selectedProduct = ref(null);
const quantity = ref(1);

const openModal = (product) => {
  selectedProduct.value = product;
  showModal.value = true;
  itemCart.value.productItems = product.id;
  itemCart.value.qty = quantity.value;
};
const closeModal = () => {
  showModal.value = false;
};
onMounted(() => {
  api
    .get("/VariantValues")
    .then((resp) => {
      mapVarriants.value = resp.data;
      for (const groupName in resp.data) {
        selected.value[groupName] = [];
      }
    })
    .catch((error) => console.log(error));

  api
    .get("/BaseProducts")
    .then((resp) => {
      // console.log(resp.data);
      data.value = resp.data.totalPages
      products.value = resp.data;
    })
    .catch((erorr) => console.log(erorr));
});
const itemCart = ref({
  id: '',
  accounts: authService.getUserName(),
  productItems: '',
  qty: '',
  createdAt: '',
  updatedAt: ''
})
const addToCart = () => {
  if (!selectedProduct.value || quantity.value <= 0) return;
  finalHandleCartProgress(itemCart.value)
  alert(`Đã thêm ${quantity.value} x ${selectedProduct.value.name} vào giỏ hàng`);
  closeModal();
};

const fetchData = async () => {
  try {
    skuColorLike.value = selected.value["Màu sắc"].join("-");
    skuSizeLike.value = selected.value["Kích thước"].join("-");
    if (selectedPrice.value.includes("-")) {
      const [min, max] = selectedPrice.value.split("-").map(p => p.trim());
      minPriceReq.value = min + "000";
      maxPriceReq.value = max + "000";
    } else if (selectedPrice.value.startsWith(">")) {
      maxPriceReq.value = selectedPrice.value.replace(">", "").trim() + "000";
      minPriceReq.value = 1; // or "" if needed
    }
    // console.log(minPriceReq.value, maxPriceReq.value)

    const response = await axios.get(
      `http://localhost:8080/api/Product/MultiplrFilter?page=${pageIndex.value}&size=${pageSize.value}&skuColorLikeReq=${skuColorLike.value}&skuSizeLikeReq=${skuSizeLike.value}&minPriceReq=${minPriceReq.value}&maxPriceReq=${maxPriceReq.value}`
    );

    data.value = response.data.totalPages
    products.value = response.data.content;
    // console.log(response.data);
  } catch (error) {
    console.error("Error fetching variants:", error);
  }
};
watch(() => selected.value["Kích thước"], fetchData);
watch(() => selected.value["Màu sắc"], fetchData);
watch(() => selectedPrice.value, fetchData);
watch(() => pageIndex.value, fetchData);
watch(() => pageSize.value, fetchData);
watch(() => quantity.value, () => itemCart.value.qty = quantity.value);
</script>
<style scoped>
/* .product-flatform {
  position: relative;

}

.modal {
  position: absolute;
} */

.filter-group h3 {
  font-size: 18px;
  font-weight: bold;
  display: flex;
  align-items: center;
  margin-top: 30px;
  margin-bottom: 16px;
  color: #2e2e2e;
}

.line {
  width: 3px;
  height: 20px;
  background-color: #2e2e2e;
  margin-right: 10px;
}

.filter-list {
  list-style: none;
  padding-left: 0;
  margin-bottom: 20px;
}

.filter-list li {
  margin-bottom: 12px;
}

.filter-list label {
  display: flex;
  align-items: center;
  font-size: 16px;
  gap: 8px;
  cursor: pointer;
  color: #333;
}

.filter-list input[type="checkbox"] {
  width: 18px;
  height: 18px;
  cursor: pointer;
  accent-color: #fcd9b0;
  /* Cam nhạt */
}

.stars {
  font-family: "Arial", sans-serif;
  color: #f8b400;
  letter-spacing: 1px;
}

.accordion-button:focus {
  box-shadow: none;
  outline: none;
}

.pagination-floating {
  margin: 2rem 0;
}

.pagination-floating .page-link {
  border: none;
  padding: 15px 22px;
  margin: 0 5px;
  border-radius: 10px;
  background: white;
  color: #495057;
  font-weight: 500;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
}

.pagination-floating .page-item.active .page-link {
  background: #007bff;
  color: white;
  transform: translateY(-3px);
  box-shadow: 0 5px 15px rgba(0, 123, 255, 0.4);
}

.pagination-floating .page-link:hover {
  background: #007bff;
  color: white;
  transform: translateY(-3px);
  box-shadow: 0 5px 15px rgba(0, 123, 255, 0.4);
}

/* From Uiverse.io by AKAspidey01 */
.first-button {
  background: #ffe8cd;
  color: #fff;
  border: none;
  font-weight: 700;
  font-size: 1em;
  min-height: 45px;
  width: 200px;
  gap: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
  transition-duration: 0.6s;
}

.main-section {
  display: flex;
  flex-direction: column;
  height: 45px;
  overflow: hidden;
  align-items: flex-start;
}

.main-section:hover .second-button,
.main-section:hover .first-button {
  transform: translateY(-45px);
}

.second-button {
  background: rgb(209, 15, 57);
  color: #fff;
  border: none;
  font-weight: 700;
  font-size: 1em;
  min-height: 45px;
  width: 200px;
  gap: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
  transition-duration: 0.6s;
}

/* From Uiverse.io by OnlyCodeChannel */
.searchBox {
  display: flex;
  width: 100%;
  height: 50px;
  align-items: center;
  justify-content: space-between;
  gap: 8px;
  background: #cecccc;
  border-radius: 50px;
  position: relative;
}

.searchButton {
  color: white;
  position: absolute;
  right: 8px;
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background: var(--gradient-2, linear-gradient(90deg, #2af598 0%, #009efd 100%));
  border: 0;
  display: inline-block;
  transition: all 300ms cubic-bezier(0.23, 1, 0.32, 1);
}

/*hover effect*/
.searchButton:hover {
  color: #fff;
  background-color: #1a1a1a;
  box-shadow: rgba(0, 0, 0, 0.5) 0 10px 20px;
  transform: translateY(-3px);
}

/*button pressing effect*/
.searchButton:active {
  box-shadow: none;
  transform: translateY(0);
}

.searchInput {
  border: none;
  background: none;
  outline: none;
  color: white;
  font-size: 15px;
  padding: 24px 46px 24px 26px;
}
</style>
