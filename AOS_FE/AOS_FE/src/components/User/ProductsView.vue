<template>
  <div class="products-container">
    <!-- Header Section -->
    <div class="page-header">
      <div class="container">
        <div class="header-content">
          <h1 class="page-title">
            <i class="bi bi-grid me-3"></i>
            Sản phẩm của chúng tôi
          </h1>
          <p class="page-subtitle">KHÁM PHÁ BỘ SƯU TẬP ĐA DẠNG VÀ CHẤT LƯỢNG</p>
        </div>
      </div>
    </div>

    <div class="container-fluid px-5">
      <div class="row mt-4 g-4">
        <!-- start categories -->
        <div class="col-lg-3">
          <div class="filter-sidebar">
            <div class="filter-header">
              <h3>
                <i class="bi bi-funnel me-2"></i>
                <span class="line"></span>Bộ lọc sản phẩm
              </h3>
            </div>

            <!-- Search Box -->
            <div class="search-section">
              <div class="modern-searchBox">
                <i class="bi bi-search search-icon"></i>
                <input
                  class="modern-searchInput"
                  type="text"
                  v-model="keyWord"
                  placeholder="Tìm kiếm sản phẩm..."
                />
                <button class="search-clear" v-if="keyWord" @click="keyWord = ''">
                  <i class="bi bi-x"></i>
                </button>
              </div>
            </div>

            <div class="filter-section">
              <div
                v-for="(items, groupName, index) in dropDowncatalogCategory"
                :key="groupName"
                class="filter-group"
              >
                <div class="modern-filter-card">
                  <div
                    class="filter-card-header"
                    @click="toggleAccordion('category-' + index)"
                  >
                    <h4 class="filter-title">
                      <i class="bi bi-tags me-2"></i>
                      <span class="line"></span> {{ groupName }}
                    </h4>
                    <i
                      class="bi bi-chevron-down toggle-icon"
                      :class="{ rotated: isExpanded('category-' + index) }"
                    ></i>
                  </div>
                  <div
                    class="filter-card-content"
                    :class="{ expanded: isExpanded('category-' + index) }"
                  >
                    <div class="filter-options">
                      <label
                        v-for="item in items"
                        :key="item.id"
                        class="modern-filter-option"
                      >
                        <input
                          type="checkbox"
                          :name="groupName"
                          :value="item.name"
                          v-model="selectedCatalogCategory[groupName]"
                        />
                        <span class="checkmark"></span>
                        <span class="option-text">{{ item.name }}</span>
                      </label>
                    </div>
                  </div>
                </div>
              </div>
              <div
                v-for="(items, groupName, index) in mapVarriants"
                :key="groupName"
                class="filter-group"
              >
                <div class="modern-filter-card">
                  <div
                    class="filter-card-header"
                    @click="toggleAccordion('variant-' + index)"
                  >
                    <h4 class="filter-title">
                      <i class="bi bi-palette me-2" v-if="groupName.includes('Màu')"></i>
                      <i class="bi bi-rulers me-2" v-else></i>
                      <span class="line"></span> {{ groupName }}
                    </h4>
                    <i
                      class="bi bi-chevron-down toggle-icon"
                      :class="{ rotated: isExpanded('variant-' + index) }"
                    ></i>
                  </div>
                  <div
                    class="filter-card-content"
                    :class="{ expanded: isExpanded('variant-' + index) }"
                  >
                    <div class="filter-options">
                      <label
                        v-for="item in items"
                        :key="item.id"
                        class="modern-filter-option"
                      >
                        <input
                          type="checkbox"
                          :name="groupName"
                          :value="item.signalSku"
                          v-model="selected[groupName]"
                        />
                        <span class="checkmark"></span>
                        <span class="option-text">{{ item.description }}</span>
                      </label>
                    </div>
                  </div>
                </div>
              </div>

              <div class="filter-group">
                <div class="modern-filter-card">
                  <div class="filter-card-header" @click="toggleAccordion('price')">
                    <h4 class="filter-title">
                      <i class="bi bi-currency-dollar me-2"></i>
                      <span class="line"></span> Khoảng giá
                    </h4>
                    <i
                      class="bi bi-chevron-down toggle-icon"
                      :class="{ rotated: isExpanded('price') }"
                    ></i>
                  </div>
                  <div
                    class="filter-card-content"
                    :class="{ expanded: isExpanded('price') }"
                  >
                    <div class="price-options">
                      <label class="modern-price-option">
                        <input
                          type="radio"
                          name="price"
                          v-model="selectedPrice"
                          value="100-200"
                        />
                        <span class="radio-mark"></span>
                        <span class="price-range">100k - 200k</span>
                      </label>
                      <label class="modern-price-option">
                        <input
                          type="radio"
                          name="price"
                          v-model="selectedPrice"
                          value="200-500"
                        />
                        <span class="radio-mark"></span>
                        <span class="price-range">200k - 500k</span>
                      </label>
                      <label class="modern-price-option">
                        <input
                          type="radio"
                          name="price"
                          v-model="selectedPrice"
                          value="500-700"
                        />
                        <span class="radio-mark"></span>
                        <span class="price-range">500k - 700k</span>
                      </label>
                      <label class="modern-price-option">
                        <input
                          type="radio"
                          name="price"
                          v-model="selectedPrice"
                          value=">700"
                        />
                        <span class="radio-mark"></span>
                        <span class="price-range">&gt; 700k</span>
                      </label>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <!-- end categories -->
        <div class="col-lg-9">
          <div class="products-section">
            <!-- Products Header -->
            <div class="products-header">
              <div class="header-info">
                <h2 class="products-title">
                  <i class="bi bi-grid-3x3-gap me-2"></i>
                  Sản phẩm của chúng tôi
                </h2>
                <div class="modern-searchBox-mobile d-lg-none">
                  <i class="bi bi-search search-icon"></i>
                  <input
                    class="modern-searchInput"
                    type="text"
                    v-model="keyWord"
                    placeholder="Tìm kiếm sản phẩm..."
                  />
                  <button class="search-clear" v-if="keyWord" @click="keyWord = ''">
                    <i class="bi bi-x"></i>
                  </button>
                </div>
              </div>
            </div>

            <!-- Products Grid -->
            <div class="product-flatform row mt-4 g-4">
              <div
                v-for="product in products"
                :key="product.id"
                class="col-lg-4 col-md-6 col-sm-12"
              >
                <div class="product-card-wrapper">
                  <ProductCard :product="product" @view-detail="openModal" />
                </div>
              </div>

              <!-- Empty State -->
              <div v-if="products.length === 0" class="empty-state col-12">
                <div class="empty-content">
                  <div class="empty-icon">
                    <i class="bi bi-inbox"></i>
                  </div>
                  <h3>Không tìm thấy sản phẩm</h3>
                  <p>Thử điều chỉnh bộ lọc hoặc từ khóa tìm kiếm</p>
                </div>
              </div>

              <!-- Pagination -->
              <div class="col-12 pagination-wrapper">
                <PageNavigative
                  :totalPage="data"
                  v-model:currentPage="pageIndex"
                  v-model:currentSize="pageSize"
                />
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Product Modal -->
    <div v-if="showModal" class="modern-modal-overlay" @click.self="closeModal">
      <div class="modern-modal">
        <div class="modal-header">
          <h3 class="modal-title">
            <i class="bi bi-eye me-2"></i>
            {{ selectedProduct?.name }}
          </h3>
          <button class="modal-close" @click="closeModal">
            <i class="bi bi-x"></i>
          </button>
        </div>

        <div class="modal-body">
          <div class="product-preview">
            <div class="preview-image">
              <img :src="selectedProduct?.imageUrl" :alt="selectedProduct?.name" />
            </div>
            <div class="preview-details">
              <div class="product-description">
                <p>{{ selectedProduct?.description || "Chưa có mô tả sản phẩm" }}</p>
              </div>

              <div class="product-specs">
                <div class="spec-item">
                  <span class="spec-label">
                    <i class="bi bi-currency-dollar me-1"></i>
                    Giá bán:
                  </span>
                  <span class="spec-value price"
                    >{{ selectedProduct?.price || "---" }} VND</span
                  >
                </div>
                <div class="spec-item">
                  <span class="spec-label">
                    <i class="bi bi-layers me-1"></i>
                    Vật liệu:
                  </span>
                  <span class="spec-value">{{ selectedProduct?.material || "---" }}</span>
                </div>
                <div class="spec-item">
                  <span class="spec-label">
                    <i class="bi bi-tag me-1"></i>
                    Biến thể:
                  </span>
                  <span class="spec-value">{{ selectedProduct?.sku || "---" }}</span>
                </div>
              </div>

              <div class="quantity-section">
                <label for="qtyInput" class="quantity-label">
                  <i class="bi bi-plus-minus me-2"></i>
                  Số lượng:
                </label>
                <div class="quantity-controls">
                  <button class="qty-btn" @click="quantity = Math.max(1, quantity - 1)">
                    <i class="bi bi-dash"></i>
                  </button>
                  <input
                    v-model="quantity"
                    type="number"
                    id="qtyInput"
                    min="1"
                    class="quantity-input"
                  />
                  <button class="qty-btn" @click="quantity++">
                    <i class="bi bi-plus"></i>
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div class="modal-footer">
          <button class="btn btn-secondary" @click="closeModal">
            <i class="bi bi-x-circle me-2"></i>
            Đóng
          </button>
          <button class="btn btn-primary" @click="addToCart">
            <i class="bi bi-cart-plus me-2"></i>
            Thêm vào giỏ
          </button>
        </div>
      </div>
    </div>
  </div>
</template>
<script setup>
import { ref, onMounted, watch } from "vue";
import { storage, ref as storageRef, getDownloadURL } from "../../Configs/firebase";
import axios from "axios";
import api, { authService } from "../../Configs/api";
import PageNavigative from "../Module/PageNavigative.vue";
import { finalHandleCartProgress } from "../../Configs/cart";
import { notification } from "ant-design-vue";
import ProductCard from "../Module/ProductCard.vue";
import { catchUserEvent } from "../../Configs/handleCatchUserProductEvent";
const mapVarriants = ref({});
const data = ref([]);
const selected = ref([]);
const selectedCatalogCategory = ref({});
const skuColorLike = ref("");
const categoriesFilter = ref("");
const catalogsFilter = ref([]);
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
const dropDowncatalogCategory = ref([]);
const timeSpent = ref(0);
const keyWord = ref("");
const expandedSections = ref(["category-0", "variant-0", "price"]); // Default expanded sections

// Function to toggle accordion sections
const toggleAccordion = (sectionId) => {
  const index = expandedSections.value.indexOf(sectionId);
  if (index > -1) {
    expandedSections.value.splice(index, 1);
  } else {
    expandedSections.value.push(sectionId);
  }
};

// Function to check if section is expanded
const isExpanded = (sectionId) => {
  return expandedSections.value.includes(sectionId);
};

let timer = null;
const openModal = (product) => {
  selectedProduct.value = product;
  showModal.value = true;
  itemCart.value.productItems = product.productItemId;
  itemCart.value.qty = quantity.value;
};
const closeModal = () => {
  showModal.value = false;
};
onMounted(() => {
  timer = setInterval(() => {
    timeSpent.value++;
  }, 1000);
  api
    .get("/VariantValues")
    .then((resp) => {
      mapVarriants.value = resp.data;
      for (const groupName in resp.data) {
        selected.value[groupName] = [];
      }
    })
    .catch((error) => console.log(error));
  // api
  //   .get("/BaseProducts")
  //   .then(async (resp) => {
  //     data.value = resp.data.totalPages;
  //     products.value = resp.data;
  //     console.log("Products loaded:", products.value);
  //   })
  //   .catch((error) => console.log("Error loading base products:", error));
  api
    .get("/CatalogCategoriesFilter")
    .then(async (resp) => {
      dropDowncatalogCategory.value = resp.data;
      for (const groupName in dropDowncatalogCategory.value) {
        selectedCatalogCategory.value[groupName] = [];
      }
    })
    .catch((error) => console.log(error));
});

const itemCart = ref({
  id: "",
  accounts: "",
  productItems: "",
  promotions: null,
  comboGroup: "",
  comboQty: "",
  comboGroupId: "",
  name: "",
  mainImageUrl: "",
  price: "",
  sku: "",
  qty: "",
  createdAt: "",
  updatedAt: "",
});
const addToCart = () => {
  if (!selectedProduct.value || quantity.value <= 0) return;
  if (quantity.value < selectedProduct.value.safetyStock) {
    authService.updateCart(quantity.value);
    clearInterval(timer);

    let payLoad = {
      id: "",
      eventType: "ADDTOCART",
      positionInList: "",
      timeSpentSeconds: timeSpent.value,
      productItemId: itemCart.value.productItems,
    };
    catchUserEvent(payLoad);

    finalHandleCartProgress(itemCart.value);
    notification.success({
      message: "Success",
      description: `Đã thêm ${quantity.value} x ${selectedProduct.value.name} vào giỏ hàng`,
    });
    closeModal();
  } else {
    // alert(`Đã thêm ${quantity.value} x ${selectedProduct.value.name} vào giỏ hàng`);
    notification.error({
      message: "Danger",
      description: `Số lượng tồn không đủ`,
    });
    closeModal();
  }
};

const fetchData = async () => {
  try {
    skuColorLike.value = selected.value["Màu sắc"].join("-");
    skuSizeLike.value = selected.value["Kích thước"].join("-");
    categoriesFilter.value = "";
    for (const groupName in selectedCatalogCategory.value) {
      if (selectedCatalogCategory.value[groupName].length > 0) {
        categoriesFilter.value =
          categoriesFilter.value +
          (categoriesFilter.value.length <= 0 ? "" : "-") +
          selectedCatalogCategory.value[groupName].join("-");
      }
    }
    if (selectedPrice.value.includes("-")) {
      const [min, max] = selectedPrice.value.split("-").map((p) => p.trim());
      minPriceReq.value = min + "000";
      maxPriceReq.value = max + "000";
    } else if (selectedPrice.value.startsWith(">")) {
      maxPriceReq.value = 10000000000;
      minPriceReq.value = selectedPrice.value.replace(">", "").trim() + "000";
    }
    // console.log(minPriceReq.value, maxPriceReq.value)
    const response = await axios.get(
      `http://localhost:8080/api/Product/MultiplrFilter?page=${pageIndex.value}&size=${pageSize.value}&skuColorLikeReq=${skuColorLike.value}&skuSizeLikeReq=${skuSizeLike.value}&minPriceReq=${minPriceReq.value}&maxPriceReq=${maxPriceReq.value}&categories=${categoriesFilter.value}&keyWord=${keyWord.value}`
    );
    console.log(response.data);
    data.value = response.data.totalPages;
    products.value = response.data.content;
    const rawProducts = response.data.content;
    //handle img with firebase storage
    const updatedProducts = await Promise.all(
      rawProducts.map(async (product) => {
        const imageUrl = product.mainImage;
        return { ...product, imageUrl };
      })
    );
    products.value = updatedProducts;
  } catch (error) {
    console.error("Error fetching variants:", error);
  }
};
watch(() => selected.value["Kích thước"], fetchData);
watch(() => selected.value["Màu sắc"], fetchData);
watch(() => selectedCatalogCategory.value, fetchData, { deep: true });
watch(() => keyWord.value, fetchData);
watch(() => selectedPrice.value, fetchData);
watch(() => pageIndex.value, fetchData);
watch(() => pageSize.value, fetchData);
watch(
  () => quantity.value,
  () => (itemCart.value.qty = quantity.value)
);
</script>
<style scoped>
/* Page Header */
.page-header {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 4rem 0;
  text-align: center;
  margin-bottom: 2rem;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
}

.header-content {
  max-width: 800px;
  margin: 0 auto;
}

.page-title {
  color: white;
  font-size: 2.8rem;
  font-weight: 700;
  margin-bottom: 1rem;
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
  letter-spacing: 2px;
}

/* Main Layout */
.products-container {
  min-height: 100vh;
  background: linear-gradient(to bottom, #f8f9fa 0%, #e9ecef 100%);
}

/* Filter Sidebar */
.filter-sidebar {
  background: white;
  border-radius: 20px;
  padding: 2rem;
  box-shadow: 0 15px 35px rgba(0, 0, 0, 0.1);
  position: sticky;
  top: 2rem;
  max-height: calc(100vh - 4rem);
  overflow-y: auto;
  border: 1px solid rgba(102, 126, 234, 0.1);
}

.filter-header h3 {
  color: #2c3e50;
  font-size: 1.4rem;
  font-weight: 600;
  margin-bottom: 2rem;
  display: flex;
  align-items: center;
  padding-bottom: 1rem;
  border-bottom: 3px solid #667eea;
}

/* Search Section */
.search-section {
  margin-bottom: 2rem;
}

.modern-searchBox {
  position: relative;
  display: flex;
  align-items: center;
  background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
  border-radius: 25px;
  padding: 0.5rem;
  border: 2px solid transparent;
  transition: all 0.3s ease;
}

.modern-searchBox:focus-within {
  border-color: #667eea;
  box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
  background: white;
}

.search-icon {
  position: absolute;
  left: 1.2rem;
  color: #7f8c8d;
  font-size: 1.1rem;
  z-index: 2;
}

.modern-searchInput {
  width: 100%;
  padding: 1rem 3rem 1rem 3rem;
  border: none;
  background: transparent;
  font-size: 1rem;
  color: #2c3e50;
  outline: none;
}

.modern-searchInput::placeholder {
  color: #95a5a6;
}

.search-clear {
  position: absolute;
  right: 1rem;
  background: #e74c3c;
  color: white;
  border: none;
  border-radius: 50%;
  width: 28px;
  height: 28px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  font-size: 0.9rem;
  transition: all 0.3s ease;
}

.search-clear:hover {
  background: #c0392b;
  transform: scale(1.1);
}

/* Modern Filter Cards */
.filter-section {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.modern-filter-card {
  border: 2px solid #f1f3f4;
  border-radius: 15px;
  overflow: hidden;
  transition: all 0.3s ease;
  background: white;
}

.filter-card-header {
  padding: 1.2rem 1.5rem;
  background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: space-between;
  transition: all 0.3s ease;
  border-bottom: 1px solid #e9ecef;
}

.filter-card-header:hover {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
}

.filter-title {
  font-weight: 600;
  font-size: 1rem;
  display: flex;
  align-items: center;
  color: #2c3e50;
}

.filter-card-header:hover .filter-title {
  color: white;
}

.toggle-icon {
  transition: transform 0.3s ease;
  font-size: 1rem;
  color: #7f8c8d;
}

.filter-card-header:hover .toggle-icon {
  color: white;
}

.toggle-icon.rotated {
  transform: rotate(180deg);
}

.filter-card-content {
  max-height: 0;
  overflow: hidden;
  transition: max-height 0.4s ease;
}

.filter-card-content.expanded {
  max-height: 500px;
}

.filter-options {
  padding: 1.5rem;
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.modern-filter-option {
  display: flex;
  align-items: center;
  gap: 1rem;
  cursor: pointer;
  padding: 0.8rem;
  border-radius: 10px;
  transition: background-color 0.2s ease;
  border: 1px solid transparent;
}

.modern-filter-option:hover {
  background: #f8f9fa;
}

.modern-filter-option input[type="checkbox"] {
  display: none;
}

.checkmark {
  width: 22px;
  height: 22px;
  border: 2px solid #dee2e6;
  border-radius: 6px;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.3s ease;
  position: relative;
  background: white;
}

.modern-filter-option input[type="checkbox"]:checked + .checkmark {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-color: #667eea;
  transform: scale(1.1);
}

.modern-filter-option input[type="checkbox"]:checked + .checkmark::after {
  content: "✓";
  color: white;
  font-size: 0.9rem;
  font-weight: bold;
}

.option-text {
  color: #2c3e50;
  font-weight: 500;
  font-size: 0.95rem;
}

/* Price Options */
.price-options {
  padding: 1.5rem;
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.modern-price-option {
  display: flex;
  align-items: center;
  gap: 1rem;
  cursor: pointer;
  padding: 1rem;
  border-radius: 12px;
  transition: background-color 0.2s ease;
  border: 2px solid transparent;
  background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
}

.modern-price-option:hover {
  background: white;
}

.modern-price-option input[type="radio"] {
  display: none;
}

.radio-mark {
  width: 22px;
  height: 22px;
  border: 2px solid #dee2e6;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.3s ease;
  position: relative;
  background: white;
}

.modern-price-option input[type="radio"]:checked + .radio-mark {
  border-color: #667eea;
  transform: scale(1.1);
}

.modern-price-option input[type="radio"]:checked + .radio-mark::after {
  content: "";
  width: 12px;
  height: 12px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 50%;
}

.price-range {
  color: #2c3e50;
  font-weight: 600;
  font-size: 1rem;
}

/* Products Section */
.products-section {
  background: white;
  border-radius: 20px;
  padding: 2.5rem;
  box-shadow: 0 15px 35px rgba(0, 0, 0, 0.1);
  border: 1px solid rgba(102, 126, 234, 0.1);
}

.products-header {
  margin-bottom: 2rem;
  padding-bottom: 1.5rem;
  border-bottom: 3px solid #f8f9fa;
}

.products-title {
  color: #2c3e50;
  font-size: 2rem;
  font-weight: 700;
  margin-bottom: 1rem;
  display: flex;
  align-items: center;
}

.products-count {
  color: #7f8c8d;
  font-size: 1rem;
  font-weight: 500;
  display: flex;
  align-items: center;
  background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
  padding: 0.8rem 1.2rem;
  border-radius: 25px;
  border: 1px solid #e9ecef;
  width: fit-content;
}

/* Mobile Search Box */
.modern-searchBox-mobile {
  margin-top: 1rem;
  position: relative;
  display: flex;
  align-items: center;
  background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
  border-radius: 25px;
  padding: 0.5rem;
  border: 2px solid transparent;
  transition: all 0.3s ease;
}

.modern-searchBox-mobile:focus-within {
  border-color: #667eea;
  box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
  background: white;
}

/* Empty State */
.empty-state {
  padding: 4rem 2rem;
}

.empty-content {
  text-align: center;
  max-width: 400px;
  margin: 0 auto;
}

.empty-icon {
  font-size: 5rem;
  margin-bottom: 1.5rem;
  color: #bdc3c7;
}

.empty-content h3 {
  color: #2c3e50;
  font-size: 1.5rem;
  margin-bottom: 0.8rem;
  font-weight: 600;
}

.empty-content p {
  color: #7f8c8d;
  font-size: 1rem;
}

/* Pagination */
.pagination-wrapper {
  margin-top: 3rem;
  display: flex;
  justify-content: center;
}

/* Modern Modal */
.modern-modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.7);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
  padding: 1rem;
  backdrop-filter: blur(5px);
}

.modern-modal {
  background: white;
  border-radius: 25px;
  max-width: 900px;
  width: 100%;
  max-height: 90vh;
  overflow-y: auto;
  box-shadow: 0 25px 80px rgba(0, 0, 0, 0.3);
  animation: modalSlideIn 0.4s ease;
  border: 2px solid rgba(102, 126, 234, 0.1);
}

.modal-header {
  padding: 2rem 2.5rem 1rem 2.5rem;
  border-bottom: 3px solid #f8f9fa;
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
}

.modal-title {
  color: #2c3e50;
  font-size: 1.4rem;
  font-weight: 600;
  margin: 0;
  display: flex;
  align-items: center;
}

.modal-close {
  background: linear-gradient(135deg, #e74c3c 0%, #c0392b 100%);
  border: none;
  border-radius: 50%;
  width: 45px;
  height: 45px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: transform 0.2s ease;
  color: white;
  font-size: 1.3rem;
  box-shadow: 0 4px 15px rgba(231, 76, 60, 0.3);
}

.modal-close:hover {
  transform: scale(1.05);
}

.modal-body {
  padding: 2.5rem;
}

.product-preview {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 2.5rem;
  align-items: start;
}

.preview-image {
  border-radius: 20px;
  overflow: hidden;
  background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
  border: 2px solid rgba(102, 126, 234, 0.1);
}

.preview-image img {
  width: 100%;
  height: 350px;
  object-fit: cover;
}

.preview-details {
  display: flex;
  flex-direction: column;
  gap: 2rem;
}

.product-description {
  color: #7f8c8d;
  line-height: 1.8;
  font-size: 1rem;
  background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
  padding: 1.5rem;
  border-radius: 15px;
  border: 1px solid #e9ecef;
}

.product-specs {
  display: flex;
  flex-direction: column;
  gap: 1.2rem;
}

.spec-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1.2rem;
  background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
  border-radius: 12px;
  border: 1px solid #e9ecef;
}

.spec-label {
  display: flex;
  align-items: center;
  color: #7f8c8d;
  font-weight: 500;
  font-size: 0.95rem;
}

.spec-value {
  color: #2c3e50;
  font-weight: 600;
  font-size: 1rem;
}

.spec-value.price {
  color: #e74c3c;
  font-size: 1.3rem;
  font-weight: 700;
}

.quantity-section {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.quantity-label {
  color: #2c3e50;
  font-weight: 600;
  display: flex;
  align-items: center;
  font-size: 1rem;
}

.quantity-controls {
  display: flex;
  align-items: center;
  gap: 0.8rem;
  background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
  border-radius: 15px;
  padding: 0.8rem;
  width: fit-content;
  border: 2px solid #e9ecef;
}

.qty-btn {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border: none;
  border-radius: 10px;
  width: 40px;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: transform 0.2s ease;
  box-shadow: 0 4px 15px rgba(102, 126, 234, 0.3);
}

.qty-btn:hover {
  transform: scale(1.05);
}

.quantity-input {
  width: 80px;
  text-align: center;
  border: none;
  background: transparent;
  font-weight: 600;
  color: #2c3e50;
  font-size: 1.1rem;
}

.quantity-input:focus {
  outline: none;
}

.modal-footer {
  padding: 1.5rem 2.5rem 2.5rem 2.5rem;
  display: flex;
  gap: 1.5rem;
  justify-content: flex-end;
  background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
}

.btn {
  padding: 1rem 2.5rem;
  border-radius: 25px;
  font-weight: 600;
  font-size: 1rem;
  cursor: pointer;
  transition: transform 0.2s ease;
  border: none;
  display: flex;
  align-items: center;
  text-decoration: none;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
}

.btn-secondary {
  background: linear-gradient(135deg, #95a5a6 0%, #7f8c8d 100%);
  color: white;
}

.btn-secondary:hover {
  transform: translateY(-1px);
}

.btn-primary {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
}

.btn-primary:hover {
  transform: translateY(-1px);
}

/* Line accent */
.line {
  width: 4px;
  height: 20px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  margin-right: 12px;
  border-radius: 2px;
}

/* Responsive Design */
@media (max-width: 992px) {
  .page-title {
    font-size: 2.2rem;
    flex-direction: column;
    gap: 0.8rem;
  }

  .product-preview {
    grid-template-columns: 1fr;
    gap: 2rem;
  }

  .filter-sidebar {
    position: relative;
    top: 0;
    max-height: none;
    margin-bottom: 2rem;
  }
}

@media (max-width: 768px) {
  .page-header {
    padding: 2.5rem 0;
  }

  .page-title {
    font-size: 1.8rem;
  }

  .products-section,
  .filter-sidebar {
    padding: 1.5rem;
    border-radius: 15px;
  }

  .modal-header,
  .modal-body,
  .modal-footer {
    padding: 1.5rem;
  }

  .modern-modal {
    border-radius: 20px;
    margin: 1rem;
  }

  .modal-footer {
    flex-direction: column;
  }

  .btn {
    width: 100%;
    justify-content: center;
  }
}

@media (max-width: 480px) {
  .page-title {
    font-size: 1.5rem;
  }

  .container-fluid {
    padding: 0 1rem !important;
  }

  .filter-options,
  .price-options {
    padding: 1rem;
  }

  .modern-filter-card,
  .modern-modal {
    border-radius: 12px;
  }
}

/* Custom scrollbar for sidebar */
.filter-sidebar::-webkit-scrollbar {
  width: 6px;
}

.filter-sidebar::-webkit-scrollbar-track {
  background: #f1f1f1;
  border-radius: 3px;
}

.filter-sidebar::-webkit-scrollbar-thumb {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 3px;
}

.filter-sidebar::-webkit-scrollbar-thumb:hover {
  background: linear-gradient(135deg, #5a67d8 0%, #6b46c1 100%);
}
</style>
