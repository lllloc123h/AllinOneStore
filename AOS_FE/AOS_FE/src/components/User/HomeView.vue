<template>
  <main>
    <!-- start carouselcarousel -->
    <div
      id="carouselExampleFade"
      class="carousel slide carousel-fade"
      data-bs-ride="carousel"
      data-bs-interval="3000"
    >
      <!-- Carousel Indicators -->
      <div class="carousel-indicators">
        <button
          type="button"
          data-bs-target="#carouselExampleFade"
          data-bs-slide-to="0"
          class="active"
          aria-current="true"
          aria-label="Slide 1"
        ></button>
        <button
          type="button"
          data-bs-target="#carouselExampleFade"
          data-bs-slide-to="1"
          aria-label="Slide 2"
        ></button>
        <button
          type="button"
          data-bs-target="#carouselExampleFade"
          data-bs-slide-to="2"
          aria-label="Slide 3"
        ></button>
        <button
          type="button"
          data-bs-target="#carouselExampleFade"
          data-bs-slide-to="3"
          aria-label="Slide 4"
        ></button>
      </div>

      <div class="carousel-inner">
        <div class="carousel-item active">
          <img
            src="../../assets/imgs/banner1.png"
            class="d-block w-100 carousel-image"
            alt="Banner 1"
          />
        </div>
        <div class="carousel-item">
          <img
            src="../../assets/imgs/banner2.png"
            class="d-block w-100 carousel-image"
            alt="Banner 2"
          />
        </div>
        <div class="carousel-item">
          <img
            src="../../assets/imgs/banner3.png"
            class="d-block w-100 carousel-image"
            alt="Banner 3"
          />
        </div>
        <div class="carousel-item">
          <img
            src="../../assets/imgs/banner4.png"
            class="d-block w-100 carousel-image"
            alt="Banner 4"
          />
        </div>
      </div>
      <button
        class="carousel-control-prev"
        type="button"
        data-bs-target="#carouselExampleFade"
        data-bs-slide="prev"
      >
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Previous</span>
      </button>
      <button
        class="carousel-control-next"
        type="button"
        data-bs-target="#carouselExampleFade"
        data-bs-slide="next"
      >
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Next</span>
      </button>
    </div>
    <!--end carouselcarousel -->

    <div class="container-fluid px-5">
      <!-- px-5 la container -->

      <!--start giảm giá -->
      <div class="row mt-5 g-4">
        <div class="col text-center">
          <h3 class="section-title">🔥 Tin tức nóng hổi</h3>
          <p class="section-subtitle">Những ưu đãi không thể bỏ lỡ</p>
        </div>
      </div>

      <!-- Grid sản phẩm giảm giá -->
      <div class="row mt-4 g-4" v-if="discountedProducts.length > 0">
        <div
          v-for="product in discountedProducts.slice(0, 8)"
          :key="product.productItemId"
          class="col-lg-3 col-md-6 col-sm-6"
        >
        <a :href="`/news/${product.id}`" style="text-decoration: none;">
          <div class="product-card" >
            <div class="product-image-container">
              <img
                :src="product.imageUrl"
                :alt="product.name"
                class="product-image"
                @error="handleImageError"
              />
              <div class="discount-badge" v-if="product.discountPercentage">
                -{{ product.discountPercentage }}%
              </div>
              <div class="product-overlay">
                <button class="quick-view-btn">
                  <i class="bi bi-eye"></i>
                </button>
              </div>
            </div>
            <div class="product-info">
              <a
              class="news-title"
              :href="`/news/${product.id}`"
              style="text-decoration: none; "
              ><h5 class="product-name" style="font-size: 26px">{{ product.title }}</h5></a
            >
              
              <!-- <div class="price-section">
                <span class="original-price" v-if="product.originalPrice">
                  {{ formatPrice(product.originalPrice) }}
                </span>
                <span class="current-price">{{ formatPrice(product.price) }}</span>
              </div> -->
              <div class="decsription">
                <p style="color: black;">{{ extractH2(product.description) }}</p>
              </div>
              <div class="time-info">
                <p>Ngày cập nhật: {{ calTime(product.createdAt) }} ngày trước</p>
              </div>
            </div>
          </div>
          </a>
        </div>
      </div>

      <!-- Placeholder khi chưa có dữ liệu -->
      <div class="row mt-4 g-4" v-else>
        <div class="col-12 text-center">
          <div class="placeholder-section">
            <i class="bi bi-percent display-1 text-muted"></i>
            <h5 class="text-muted mt-3">Chưa có tin tức giảm giá</h5>
            <p class="text-muted">Các thông tin ưu đãi hấp dẫn sẽ sớm được cập nhật</p>
          </div>
        </div>
      </div>
      <!--end giảm giá -->
      <!--start danh mục nổi bật -->
      <div class="row mt-5 g-4">
        <div class="col text-center">
          <h3 class="section-title">✨ Các danh mục nổi bật</h3>
          <p class="section-subtitle">Khám phá bộ sưu tập thời trang đa dạng</p>
        </div>
      </div>
      <!--end danh mục nổi bật -->

      <!--start phân loại -->
      <div class="row mt-4 g-4">
        <div class="col-lg-4 col-md-6">
          <div class="category-card large-card">
            <img
              class="category-image"
              src="../../assets/imgs/banner_2.jpg"
              alt="Thời trang nam"
            />
            <div class="category-overlay">
              <div class="category-content">
                <h4 class="category-title">Thời Trang Nam</h4>
                <p class="category-desc">Phong cách lịch lãm, sang trọng</p>
                <router-link to="/products" class="category-btn">
                  Khám phá ngay <i class="bi bi-arrow-right ms-2"></i>
                </router-link>
              </div>
            </div>
          </div>
        </div>

        <div class="col-lg-4 col-md-6">
          <div class="row g-4">
            <div class="col-12">
              <div class="category-card">
                <img
                  class="category-image"
                  src="../../assets/imgs/banner_1.jpg"
                  alt="Thời trang nữ"
                />
                <div class="category-overlay">
                  <div class="category-content">
                    <h5 class="category-title">Thời Trang Nữ</h5>
                    <p class="category-desc">Duyên dáng, quyến rũ</p>
                    <router-link to="/products" class="category-btn">
                      Xem ngay <i class="bi bi-arrow-right ms-1"></i>
                    </router-link>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-12">
              <div class="category-card">
                <img
                  class="category-image"
                  src="../../assets/imgs/HUSSIO FASHION.jpg"
                  alt="Phụ kiện"
                />
                <div class="category-overlay">
                  <div class="category-content">
                    <h5 class="category-title">Phụ Kiện</h5>
                    <p class="category-desc">Hoàn thiện phong cách</p>
                    <router-link to="/products" class="category-btn">
                      Xem ngay <i class="bi bi-arrow-right ms-1"></i>
                    </router-link>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div class="col-lg-4 col-md-12">
          <div class="category-card large-card">
            <img
              class="category-image"
              src="../../assets/imgs/Realistic Summer Sale Concept Design _ Premium Vector.jpg"
              alt="Sale Off"
            />
            <div class="category-overlay sale-overlay">
              <div class="category-content">
                <div class="sale-badge">SALE UP TO 70%</div>
                <h4 class="category-title text-white">Mega Sale</h4>
                <p class="category-desc text-white">Giảm giá cực sốc - Số lượng có hạn</p>
                <router-link to="/products" class="category-btn sale-btn">
                  Mua ngay <i class="bi bi-lightning-fill ms-2"></i>
                </router-link>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!--end phân loại -->

      <!-- Header sản phẩm bán chạy -->
      <div class="section-header mt-5 mb-4">
        <div class="row align-items-center">
          <div class="col">
            <h3 class="section-title">⭐ Sản phẩm bán chạy</h3>
            <p class="section-subtitle">Được yêu thích nhất bởi khách hàng</p>
          </div>
          <div class="col-auto">
            <button class="view-more-btn" @click="router.push('/products')">
              <span>Xem thêm</span>
              <svg
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 74 74"
                height="34"
                width="34"
              >
                <circle
                  stroke-width="3"
                  stroke="currentColor"
                  r="35.5"
                  cy="37"
                  cx="37"
                ></circle>
                <path
                  fill="currentColor"
                  d="M25 35.5C24.1716 35.5 23.5 36.1716 23.5 37C23.5 37.8284 24.1716 38.5 25 38.5V35.5ZM49.0607 38.0607C49.6464 37.4749 49.6464 36.5251 49.0607 35.9393L39.5147 26.3934C38.9289 25.8076 37.9792 25.8076 37.3934 26.3934C36.8076 26.9792 36.8076 27.9289 37.3934 28.5147L45.8787 37L37.3934 45.4853C36.8076 46.0711 36.8076 47.0208 37.3934 47.6066C37.9792 48.1924 38.9289 48.1924 39.5147 47.6066L49.0607 38.0607ZM25 38.5L48 38.5V35.5L25 35.5V38.5Z"
                />
              </svg>
            </button>
          </div>
        </div>
      </div>

      <!-- Sản phẩm bán chạy -->
      <div class="row g-4" v-if="bestSellers.length > 0">
        <div
          v-for="product in bestSellers.slice(0, 8)"
          :key="product.id"
          class="col-lg-3 col-md-6 col-sm-6"
        >
          <div class="product-card bestseller" @click="goToDetail(product.id)">
            <div class="product-image-container">
              <img
                :src="product.imageUrl"
                :alt="product.name"
                class="product-image"
                @error="handleImageError"
              />
              <div class="bestseller-badge">
                <i class="bi bi-fire"></i>
                Hot
              </div>
              <div class="product-overlay">
                <button class="quick-view-btn">
                  <i class="bi bi-eye"></i>
                </button>
              </div>
            </div>
            <div class="product-info">
              <h6 class="product-name">{{ product.name }}</h6>
              <div class="price-section">
                <span class="current-price">{{ formatPrice(product.price) }}</span>
              </div>
              <div class="rating-section">
                <div class="stars">
                  <i class="bi bi-star-fill" v-for="n in 5" :key="n"></i>
                </div>
                <span class="rating-text">(4.9)</span>
                <span class="sold-count"
                  >• Đã bán
                  {{ product.soldCount|| 0 }}</span
                >
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Placeholder khi chưa có dữ liệu -->
      <div class="row g-4" v-else>
        <div class="col-12 text-center">
          <div class="placeholder-section">
            <i class="bi bi-star display-1 text-muted"></i>
            <h5 class="text-muted mt-3">Chưa có sản phẩm bán chạy</h5>
            <p class="text-muted">Dữ liệu sẽ được cập nhật sau khi có đơn hàng</p>
          </div>
        </div>
      </div>
    </div>
    <!--start gioi thieu -->
    <!-- <section style="
        background-color: #f1cdbd;
        padding: 8rem 2rem;
        margin-top: 100px;
        margin-bottom: 100px;
      ">
      <div class="container">
        <div class="row align-items-center"> -->
    <!-- Bên trái -->
    <!-- <div class="col-md-6 mb-4 mb-md-0">
            <h2 class="fw-bold text-white mb-4">
              Hãy xem qua những điểm bán hàng độc đáo của chúng tôi
            </h2>
            <p class="mb-0"> -->
    <!-- From Uiverse.io by alexmaracinaru -->
    <!-- <button id="next">
                <span>Xem thêm</span>
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 74 74" height="34" width="34">
                  <circle stroke-width="3" stroke="black" r="35.5" cy="37" cx="37"></circle>
                  <path fill="black"
                    d="M25 35.5C24.1716 35.5 23.5 36.1716 23.5 37C23.5 37.8284 24.1716 38.5 25 38.5V35.5ZM49.0607 38.0607C49.6464 37.4749 49.6464 36.5251 49.0607 35.9393L39.5147 26.3934C38.9289 25.8076 37.9792 25.8076 37.3934 26.3934C36.8076 26.9792 36.8076 27.9289 37.3934 28.5147L45.8787 37L37.3934 45.4853C36.8076 46.0711 36.8076 47.0208 37.3934 47.6066C37.9792 48.1924 38.9289 48.1924 39.5147 47.6066L49.0607 38.0607ZM25 38.5L48 38.5V35.5L25 35.5V38.5Z">
                  </path>
                </svg>
              </button>
            </p>
          </div> -->

    <!-- Bên phải -->
    <!-- <div class="col-md-6 text-white">
            <p>
              Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse varius
              enim in eros elementum tristique. Duis cursus, mi quis viverra ornare, eros
              dolor interdum nulla, ut commodo diam libero vitae erat.
            </p>

            <div class="d-flex gap-5 mt-4">
              <div>
                <h3 class="fw-bold">99%</h3>
                <p>
                  Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse
                  varius enim in eros.
                </p>
              </div>
              <div>
                <h3 class="fw-bold">100%</h3>
                <p>
                  Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse
                  varius enim in eros.
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section> -->
    <!--end gioi thieu -->
    <!-- Features Section -->
    <section class="features-section">
      <div class="container">
        <div class="row align-items-center">
          <!-- Bên trái -->
          <div class="col-lg-6 mb-4 mb-lg-0">
            <div class="features-content">
              <h2 class="features-title">Tại sao chọn chúng tôi?</h2>
              <p class="features-description">
                Khám phá những ưu điểm vượt trội khiến hàng nghìn khách hàng tin tưởng và
                lựa chọn chúng tôi
              </p>

              <div class="features-stats">
                <div class="stat-item">
                  <div class="stat-number">99%</div>
                  <div class="stat-label">Khách hàng hài lòng</div>
                </div>
                <div class="stat-item">
                  <div class="stat-number">24/7</div>
                  <div class="stat-label">Hỗ trợ khách hàng</div>
                </div>
                <div class="stat-item">
                  <div class="stat-number">10K+</div>
                  <div class="stat-label">Sản phẩm</div>
                </div>
              </div>

              <router-link to="/products" class="cta-button">
                <span>Khám phá ngay</span>
                <i class="bi bi-arrow-right ms-2"></i>
              </router-link>
            </div>
          </div>

          <!-- Bên phải -->
          <div class="col-lg-6">
            <div class="features-accordion" id="accordionPanelsStayOpenExample">
              <div class="accordion-item">
                <h2 class="accordion-header">
                  <button
                    class="accordion-button"
                    type="button"
                    data-bs-toggle="collapse"
                    data-bs-target="#panelsStayOpen-collapseOne"
                    aria-expanded="true"
                    aria-controls="panelsStayOpen-collapseOne"
                  >
                    <i class="bi bi-shield-check me-3"></i>
                    Chất lượng đảm bảo
                  </button>
                </h2>
                <div
                  id="panelsStayOpen-collapseOne"
                  class="accordion-collapse collapse show"
                >
                  <div class="accordion-body">
                    <strong>Chất lượng là lời hứa, không phải lời nói.</strong> 
                    Từng sản phẩm tại shop đều được chọn lọc kỹ lưỡng
                     từ chất liệu đến đường may, đảm bảo mang đến cho bạn cảm giác thoải mái,
                      bền đẹp và đúng chuẩn phong cách. Chúng tôi không chỉ bán quần áo — chúng tôi trao gửi sự tự tin.
                      <br>
                      <strong>🧵 Nếu có bất kỳ lỗi nhỏ nào, bạn cứ yên tâm: 
                        chúng tôi luôn sẵn sàng hỗ trợ đổi trả nhanh chóng và không rườm rà.</strong>
                  </div>
                </div>
              </div>
              <div class="accordion-item">
                <h2 class="accordion-header">
                  <button
                    class="accordion-button collapsed"
                    type="button"
                    data-bs-toggle="collapse"
                    data-bs-target="#panelsStayOpen-collapseTwo"
                    aria-expanded="false"
                    aria-controls="panelsStayOpen-collapseTwo"
                  >
                    <i class="bi bi-truck me-3"></i>
                    Giao hàng nhanh chóng
                  </button>
                </h2>
                <div id="panelsStayOpen-collapseTwo" class="accordion-collapse collapse">
                  <div class="accordion-body">
                    <strong>Giao hàng nhanh chóng, không để bạn phải chờ lâu.</strong> 
                    Chúng tôi hiểu cảm giác háo hức khi chốt đơn một món đồ ưng ý — vì thế,
                     đội ngũ vận chuyển luôn hoạt động hết công suất để đơn hàng đến tay bạn nhanh nhất có thể.
                      Dù bạn ở Sài Gòn hay bất kỳ tỉnh thành nào, chúng tôi đều có giải pháp giao hàng linh hoạt,
                       tiện lợi và đúng hẹn. <br>
                       <strong>🚚 Đặt hôm nay, giao liền tay — để bạn luôn là người diện đẹp đầu tiên!</strong>
                  </div>
                </div>
              </div>
              <div class="accordion-item">
                <h2 class="accordion-header">
                  <button
                    class="accordion-button collapsed"
                    type="button"
                    data-bs-toggle="collapse"
                    data-bs-target="#panelsStayOpen-collapseThree"
                    aria-expanded="false"
                    aria-controls="panelsStayOpen-collapseThree"
                  >
                    <i class="bi bi-headset me-3"></i>
                    Hỗ trợ tận tâm
                  </button>
                </h2>
                <div
                  id="panelsStayOpen-collapseThree"
                  class="accordion-collapse collapse"
                >
                  <div class="accordion-body">
                    <strong>Chúng tôi luôn ở đây vì bạn.</strong> 
                    Dù bạn đang phân vân chọn size, cần tư vấn phối đồ, 
                    hay đơn giản chỉ muốn hỏi về tình trạng đơn hàng — 
                    đội ngũ của chúng tôi luôn sẵn sàng hỗ trợ bạn một 
                    cách nhanh chóng và chu đáo. Mỗi khách hàng đều là 
                    một người bạn đồng hành trên hành trình thời trang, 
                    và chúng tôi cam kết mang đến trải nghiệm mua sắm dễ chịu, 
                    thân thiện và đáng nhớ.<strong> 💬 Bạn cần gì, cứ nói — chúng tôi lắng nghe</strong>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
    <!-- filepath: d:\AllInStore_Customize_T-shirt\AllinOneStore\AOS_FE\AOS_FE\src\components\User\HomeView.vue -->
    <!-- filepath: d:\AllInStore_Customize_T-shirt\AllinOneStore\AOS_FE\AOS_FE\src\components\User\HomeView.vue -->
    <transition
      name="modal-fade"
      v-if="imageHome.length > 0"
      v-for="(image, index) in imageHome"
      :key="index"
    >
      <div
        v-if="modalIndex === index"
        class="modern-modal-overlay"
        @click.self="closeModal"
      >
        <transition name="modal-zoom">
          <div class="modal-body">
            <button class="modal-close-btn" @click="closeModal(index)" aria-label="Đóng">
              <i class="bi bi-x-lg"></i>
            </button>

            <div class="modal-image-wrapper">
              <img :src="image.imageUrl" alt="" />
            </div>
          </div>
        </transition>
      </div>
    </transition>
    <!-- thong tin -->
  </main>
</template>
<script setup>
import { ref, onMounted } from "vue";
import api, { homeService } from "../../Configs/api";
import { useRouter } from "vue-router";
const modalIndex = ref(null);
const closeModal = (index) => {
  modalIndex.value = index + 1;
};
const router = useRouter();

function goToDetail(id) {
  router.push(`/product/${id}`);

}
function extractH2(html) {
  const parser = new DOMParser();
  const doc = parser.parseFromString(html, 'text/html');
  return doc.querySelector('h2')?.textContent || '';
}
// Sản phẩm giảm giá
const discountedProducts = ref([]);

// Sản phẩm bán chạy
const bestSellers = ref([]);

// Utility functions
const formatPrice = (price) => {
  return new Intl.NumberFormat("vi-VN", {
    style: "currency",
    currency: "VND",
  }).format(price);
};

const handleImageError = (event) => {
  event.target.src = "../../assets/imgs/no-image.png";
};
const imageHome = ref({
  id: "",
  title: "",
  imageUrl: "",
  isHome: false,
  description: "",
  createdAt: "",
  updatedAt: "",
});
// Gọi API khi component mount
onMounted(async () => {
  try {
    api
      .get("/News/home")
      .then((response) => {
        imageHome.value = response.data; // Lưu ảnh từ API vào biến
        // Xử lý dữ liệu từ API
        console.log("Dữ liệu từ API:", response.data);
      })
      .catch((error) => {
        console.error("Lỗi khi tải ảnh:", error);
      });
    setTimeout(() => {
      modalIndex.value = 0; // Hiện modal sau 300ms
    }, 300);
    // Tạm thời bỏ load dữ liệu
    discountedProducts.value = await homeService.getDiscountedProducts(); // ✅ gọi từ homeService
    bestSellers.value = await homeService.getBestSellers(); // ✅ gọi từ homeService
  } catch (error) {
    console.error("Lỗi khi tải dữ liệu:", error);
  }
});
// funtion cal time
const newTime = new Date();
const calTime = (passTimetr) => {
  const newTime = new Date(); // thời gian hiện tại
  const passTime = new Date(passTimetr); // thời gian quá khứ

  const timestamp = newTime - passTime; // đơn vị là milliseconds
  const timeObj = new Date(timestamp); // tạo đối tượng Date từ khoảng cách thời gian

  const day = timeObj.getUTCDate(); // lấy số ngày (tính từ 1970-01-01)

  console.log("Thời gian tính được = " + day + " ngày");
  return day;
};

</script>

<style scoped>
/* Overlay fade */
.modal-fade-enter-active,
.modal-fade-leave-active {
  transition: opacity 0.3s;
}
.modal-fade-enter-from,
.modal-fade-leave-to {
  opacity: 0;
}
.modal-fade-enter-to,
.modal-fade-leave-from {
  opacity: 1;
}

/* Modal zoom */
.modal-zoom-enter-active,
.modal-zoom-leave-active {
  transition: transform 0.3s, opacity 0.3s;
}
.modal-zoom-enter-from,
.modal-zoom-leave-to {
  transform: scale(0.85);
  opacity: 0;
}
.modal-zoom-enter-to,
.modal-zoom-leave-from {
  transform: scale(1);
  opacity: 1;
}
.modal-body {
  position: relative;
  background: white;
  border-radius: 18px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.18);
  max-width: 380px;
  height: auto;
  display: flex;
  align-items: center;
  justify-content: center;
}

.modal-image-wrapper {
  width: 380px;
  aspect-ratio: 4 / 5;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #f8f9fa;
  border-radius: 12px;
  overflow: hidden;
}
.product-name {
  color: #000; /* màu mặc định: đen */
  transition: color 0.3s ease;
}

.product-card:hover .product-name {
  color: #007bff; /* xanh dương khi hover */
}
.modal-image-wrapper img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 12px;
  display: block;
}

.modal-close-btn {
  position: absolute;
  top: 18px;
  right: 18px;
  background: rgba(0, 0, 0, 0.08);
  border: none;
  border-radius: 50%;
  width: 38px;
  height: 38px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.2rem;
  color: #333;
  cursor: pointer;
  transition: background 0.2s;
  z-index: 2;
}
.modal-close-btn:hover {
  background: #e74c3c;
  color: #fff;
}
.modern-modal-overlay {
  position: fixed;
  inset: 0;
  background: rgba(0, 0, 0, 0.7);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1050;
  padding: 1rem;
}
/* Section styles */
.section-title {
  font-weight: 700;
  font-size: 2.2rem;
  color: #2c3e50;
  margin-bottom: 0.5rem;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}
a.news-title{
  min-height: 20px;
}
.section-subtitle {
  color: #6c757d;
  font-size: 1.1rem;
  margin-bottom: 0;
}

.section-header {
  position: relative;
  text-align: center;
}

.section-header::after {
  content: "";
  position: absolute;
  bottom: -10px;
  left: 50%;
  transform: translateX(-50%);
  width: 60px;
  height: 3px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 2px;
}

/* Product Card Styles */
.product-card {
  background: white;
  border-radius: 16px;
  overflow: hidden;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
  transition: all 0.3s ease;
  cursor: pointer;
  height: 100%;
  display: flex;
  flex-direction: column;
}

.product-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 12px 30px rgba(0, 0, 0, 0.12);
}

.product-card.bestseller {
  border: 2px solid #ffd700;
  box-shadow: 0 4px 20px rgba(255, 215, 0, 0.2);
}

.product-image-container {
  position: relative;
  width: 100%;
  height: 250px;
  overflow: hidden;
  background: #f8f9fa;
}

.product-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.3s ease;
}

.product-card:hover .product-image {
  transform: scale(1.02);
}

.discount-badge {
  position: absolute;
  top: 12px;
  left: 12px;
  background: linear-gradient(135deg, #e74c3c, #c0392b);
  color: white;
  padding: 6px 12px;
  border-radius: 20px;
  font-size: 0.85rem;
  font-weight: 600;
  z-index: 2;
  box-shadow: 0 2px 8px rgba(231, 76, 60, 0.3);
}

.bestseller-badge {
  position: absolute;
  top: 12px;
  left: 12px;
  background: linear-gradient(135deg, #ff6b6b, #ee5a52);
  color: white;
  padding: 6px 12px;
  border-radius: 20px;
  font-size: 0.85rem;
  font-weight: 600;
  z-index: 2;
  display: flex;
  align-items: center;
  gap: 4px;
  box-shadow: 0 2px 8px rgba(238, 90, 82, 0.3);
}

.product-overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  opacity: 0;
  transition: opacity 0.3s ease;
}

.product-card:hover .product-overlay {
  opacity: 1;
}

.quick-view-btn {
  background: white;
  border: none;
  width: 50px;
  height: 50px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.2rem;
  color: #333;
  transition: all 0.3s ease;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

.quick-view-btn:hover {
  transform: scale(1.1);
  background: #667eea;
  color: white;
}

.product-info {
  padding: 20px;
  flex: 1;
  display: flex;
  flex-direction: column;
}

.product-name {
  font-weight: 600;
  color: #2c3e50;
  margin-bottom: 12px;
  font-size: 1.1rem;
  line-height: 1.4;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
  height: 1.7em;
}

.price-section {
  margin-bottom: 12px;
}

.original-price {
  color: #999;
  text-decoration: line-through;
  font-size: 0.9rem;
  margin-right: 8px;
}

.current-price {
  color: #e74c3c;
  font-weight: 700;
  font-size: 1.2rem;
}

.rating-section {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-top: auto;
  font-size: 0.9rem;
}

.stars {
  color: #ffc107;
  display: flex;
  gap: 2px;
}

.rating-text {
  color: #6c757d;
  font-weight: 500;
}

.sold-count {
  color: #28a745;
  font-weight: 500;
}

/* View More Button */
.view-more-btn {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border: none;
  border-radius: 50px;
  padding: 12px 24px;
  font-weight: 600;
  display: flex;
  align-items: center;
  gap: 12px;
  transition: all 0.3s ease;
  box-shadow: 0 4px 15px rgba(102, 126, 234, 0.3);
}

.view-more-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 25px rgba(102, 126, 234, 0.4);
  background: linear-gradient(135deg, #5a67d8 0%, #6b46c1 100%);
}

.view-more-btn svg {
  transition: transform 0.3s ease;
}

.view-more-btn:hover svg {
  transform: translateX(4px);
}

/* Placeholder Section */
.placeholder-section {
  padding: 60px 20px;
  text-align: center;
  background: #f8f9fa;
  border-radius: 16px;
  border: 2px dashed #dee2e6;
}

/* Category Cards */
.category-card {
  position: relative;
  overflow: hidden;
  border-radius: 16px;
  height: 300px;
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
}

.category-card.large-card {
  height: 620px;
}

.category-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 12px 30px rgba(0, 0, 0, 0.12);
}

.category-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.3s ease;
}
.product-image {
  aspect-ratio: 4 / 5;
  width: 100%; /* hoặc chiều rộng mong muốn */
  object-fit: cover; /* hoặc contain tùy mục đích */
  border-radius: 8px; /* tuỳ chọn để làm đẹp */
}
.category-card:hover .category-image {
  transform: scale(1.02);
}

.category-overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: linear-gradient(135deg, rgba(0, 0, 0, 0.3) 0%, rgba(0, 0, 0, 0.7) 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  opacity: 0;
  transition: opacity 0.4s ease;
  backdrop-filter: blur(2px);
}

.category-card:hover .category-overlay {
  opacity: 1;
}

.sale-overlay {
  background: linear-gradient(
    135deg,
    rgba(231, 76, 60, 0.8) 0%,
    rgba(192, 57, 43, 0.9) 100%
  );
}

.category-content {
  text-align: center;
  color: white;
  padding: 2rem;
}

.category-title {
  font-size: 1.8rem;
  font-weight: 700;
  margin-bottom: 0.5rem;
}

.category-desc {
  font-size: 1rem;
  margin-bottom: 1.5rem;
  opacity: 0.9;
}

.category-btn {
  background: rgba(255, 255, 255, 0.95);
  color: #333;
  text-decoration: none;
  padding: 12px 24px;
  border-radius: 25px;
  font-weight: 600;
  transition: all 0.3s ease;
  display: inline-flex;
  align-items: center;
  backdrop-filter: blur(10px);
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
}

.category-btn:hover {
  background: white;
  transform: translateY(-2px);
  box-shadow: 0 8px 25px rgba(0, 0, 0, 0.15);
  color: #333;
}

.sale-btn {
  background: white;
  color: #e74c3c;
}

.sale-btn:hover {
  background: #fff;
  color: #c0392b;
}

.sale-badge {
  background: rgba(255, 255, 255, 0.2);
  color: white;
  padding: 8px 16px;
  border-radius: 20px;
  font-size: 0.9rem;
  font-weight: 700;
  margin-bottom: 1rem;
  display: inline-block;
  border: 1px solid rgba(255, 255, 255, 0.3);
}

/* Features Section */
.features-section {
  background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
  padding: 5rem 2rem;
  margin-top: 4rem;
}

.features-content {
  padding: 2rem 0;
}

.features-title {
  font-size: 2.5rem;
  font-weight: 700;
  color: #2c3e50;
  margin-bottom: 1rem;
}
.product-image-container {
  position: relative;
  width: 100%;
  height: 100%; /* hoặc đặt chiều cao cụ thể nếu cần */
  overflow: hidden;
}

.product-image {
  width: 100%;
  height: 100%;
  object-fit: cover; /* ảnh sẽ cắt bớt để vừa khung mà không méo */
  display: block;
}
.features-description {
  font-size: 1.2rem;
  color: #6c757d;
  margin-bottom: 2rem;
  line-height: 1.6;
}

.features-stats {
  display: flex;
  gap: 2rem;
  margin-bottom: 2rem;
}

.stat-item {
  text-align: center;
}

.stat-number {
  font-size: 2.5rem;
  font-weight: 700;
  color: #667eea;
  line-height: 1;
}

.stat-label {
  font-size: 0.9rem;
  color: #6c757d;
  margin-top: 0.5rem;
}

.cta-button {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  text-decoration: none;
  padding: 15px 30px;
  border-radius: 50px;
  font-weight: 600;
  display: inline-flex;
  align-items: center;
  transition: all 0.3s ease;
  box-shadow: 0 4px 15px rgba(102, 126, 234, 0.3);
}

.cta-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 25px rgba(102, 126, 234, 0.4);
  color: white;
}

/* Features Accordion */
.features-accordion .accordion-item {
  border: none;
  margin-bottom: 1.5rem;
  border-radius: 16px;
  overflow: hidden;
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.08);
  transition: all 0.3s ease;
}

.features-accordion .accordion-item:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 25px rgba(0, 0, 0, 0.12);
}

.features-accordion .accordion-button {
  background: white;
  border: none;
  font-weight: 600;
  padding: 1.5rem 2rem;
  color: #2c3e50;
  font-size: 1.1rem;
  transition: all 0.3s ease;
}

.features-accordion .accordion-button:not(.collapsed) {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  box-shadow: none;
}

.features-accordion .accordion-button:focus {
  box-shadow: none;
}

.features-accordion .accordion-button::after {
  background-image: none;
  content: "\F229";
  font-family: "bootstrap-icons";
  font-size: 1.2rem;
  transition: transform 0.3s ease;
}

.features-accordion .accordion-button:not(.collapsed)::after {
  transform: rotate(180deg);
  color: white;
}

.features-accordion .accordion-button .bi {
  color: #667eea;
  font-size: 1.3rem;
}

.features-accordion .accordion-button:not(.collapsed) .bi {
  color: white;
}

.features-accordion .accordion-body {
  padding: 2rem;
  background: white;
  color: #6c757d;
  line-height: 1.7;
  font-size: 1rem;
  border-top: 1px solid rgba(0, 0, 0, 0.05);
}

/* Carousel Enhancements */
.carousel-item img {
  filter: brightness(0.9);
  transition: filter 0.3s ease;
}

.carousel-item.active img {
  filter: brightness(1);
}

.carousel-image {
  min-height: 400px;
  object-fit: cover;
  object-position: center;
}

.carousel-indicators {
  bottom: 20px;
}

.carousel-indicators [data-bs-target] {
  width: 12px;
  height: 12px;
  border-radius: 50%;
  margin: 0 6px;
  background-color: rgba(255, 255, 255, 0.5);
  border: none;
  transition: all 0.3s ease;
}

.carousel-indicators .active {
  background-color: white;
  transform: scale(1.2);
}

.carousel-control-prev,
.carousel-control-next {
  width: 60px;
  height: 60px;
  top: 50%;
  transform: translateY(-50%);
  background: rgba(0, 0, 0, 0.3);
  border-radius: 50%;
  border: none;
  transition: all 0.3s ease;
}

.carousel-control-prev {
  left: 20px;
}

.carousel-control-next {
  right: 20px;
}

.carousel-control-prev:hover,
.carousel-control-next:hover {
  background: rgba(0, 0, 0, 0.6);
  transform: translateY(-50%) scale(1.1);
}

.carousel-control-prev-icon,
.carousel-control-next-icon {
  width: 20px;
  height: 20px;
}

/* Custom shadows */
.custom-shadow {
  box-shadow: 0 8px 30px rgba(0, 0, 0, 0.12);
}

/* Responsive Design */
@media (max-width: 768px) {
  .section-title {
    font-size: 1.8rem;
  }

  .product-card {
    margin-bottom: 1.5rem;
  }

  .product-image-container {
    height: 220px;
  }

  .view-more-btn {
    padding: 10px 20px;
    font-size: 0.9rem;
  }

  .category-card {
    height: 250px;
    margin-bottom: 1rem;
  }

  .category-card.large-card {
    height: 300px;
  }

  .features-stats {
    flex-direction: column;
    gap: 1rem;
  }

  .features-title {
    font-size: 2rem;
  }

  .carousel {
    margin-top: 60px;
  }

  .carousel-image {
    height: 50vh;
    min-height: 300px;
  }

  .carousel-control-prev,
  .carousel-control-next {
    width: 45px;
    height: 45px;
  }

  .carousel-control-prev {
    left: 10px;
  }

  .carousel-control-next {
    right: 10px;
  }
}

@media (max-width: 576px) {
  .section-title {
    font-size: 1.6rem;
  }

  .product-info {
    padding: 15px;
  }

  .product-image-container {
    height: 200px;
  }

  .features-section {
    padding: 3rem 1rem;
  }
}

/* Loading animations */
@keyframes shimmer {
  0% {
    background-position: -468px 0;
  }
  100% {
    background-position: 468px 0;
  }
}

.loading {
  animation: shimmer 1.5s ease-in-out infinite;
  background: linear-gradient(to right, #f6f7f8 8%, #edeef1 38%, #f6f7f8 54%);
  background-size: 1000px 640px;
}
</style>
