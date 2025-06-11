<template>
  <main>
    <!-- start carouselcarousel -->
    <div id="carouselExampleFade" class="carousel slide carousel-fade">
      <div class="carousel-inner">
        <div class="carousel-item active">
          <img src="../../assets/imgs/banner1.png" class="d-block w-100" alt="..." />
        </div>
        <div class="carousel-item">
          <img src="../../assets/imgs/banner2.png" class="d-block w-100" alt="..." />
        </div>
        <div class="carousel-item">
          <img src="../../assets/imgs/banner3.png" class="d-block w-100" alt="..." />
        </div>
        <div class="carousel-item">
          <img src="../../assets/imgs/banner4.png" class="d-block w-100" alt="..." />
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

    <div style="" class="container-fluid px-5">
      <!-- px-5 la container -->
      <!--start giảm giá -->
      <div class="hstack mt-5 align-items-center">
        <div>
          <h3>San pham giam gia</h3>
        </div>
        <div class="ms-auto">
          <p class="mb-0">
            <!-- From Uiverse.io by alexmaracinaru -->
            <button id="next">
              <span>Xem thêm</span>
              <svg
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 74 74"
                height="34"
                width="34"
              >
                <circle stroke-width="3" stroke="black" r="35.5" cy="37" cx="37"></circle>
                <path
                  fill="black"
                  d="M25 35.5C24.1716 35.5 23.5 36.1716 23.5 37C23.5 37.8284 24.1716 38.5 25 38.5V35.5ZM49.0607 38.0607C49.6464 37.4749 49.6464 36.5251 49.0607 35.9393L39.5147 26.3934C38.9289 25.8076 37.9792 25.8076 37.3934 26.3934C36.8076 26.9792 36.8076 27.9289 37.3934 28.5147L45.8787 37L37.3934 45.4853C36.8076 46.0711 36.8076 47.0208 37.3934 47.6066C37.9792 48.1924 38.9289 48.1924 39.5147 47.6066L49.0607 38.0607ZM25 38.5L48 38.5V35.5L25 35.5V38.5Z"
                ></path>
              </svg>
            </button>
          </p>
        </div>
      </div>
      <div class="row mt-4 g-4">
        <div class="col-3">
          <div
            style="border: 0px"
            class="card position-relative overflow-hidden rounded-4"
          >
            <!-- Label Giảm giá -->
            <div
              class="position-absolute top-0 start-0 bg-danger text-white px-3 py-1 m-3 shadow-sm"
              style="border-radius: 12px; font-size: 0.85rem; z-index: 10"
            >
              Giảm giá
            </div>

            <!-- Bo góc ảnh luôn -->
            <img
              class="card-img rounded-4 custom-shadow"
              style="height: 450px; object-fit: cover"
              src="../../assets/imgs/Vàng nhạt và Xanh ngọc lam Nghệ thuật sắp chữ In đậm Tiệm cà phê Biểu trưng Cà phê.png"
              alt="Card image"
            />

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

              <p class="card-text">Áo thun nam</p>

              <div class="d-flex justify-content-end">
                <!-- From Uiverse.io by AKAspidey01 -->

                <div class="main-section rounded-4">
                  <button class="first-button">Còn hàng</button>
                  <button class="second-button">
                    <svg
                      viewBox="0 0 24 24"
                      width="20"
                      height="20"
                      stroke="#ffd300"
                      stroke-width="2"
                      fill="none"
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      class="css-i6dzq1"
                    >
                      <circle cx="9" cy="21" r="1"></circle>
                      <circle cx="20" cy="21" r="1"></circle>
                      <path
                        d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6"
                      ></path>
                    </svg>
                    3,4k đã bán
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="col-3">
          <div
            style="border: 0px"
            class="card position-relative overflow-hidden rounded-4"
          >
            <!-- Label Giảm giá -->
            <div
              class="position-absolute top-0 start-0 bg-danger text-white px-3 py-1 m-3 shadow-sm"
              style="border-radius: 12px; font-size: 0.85rem; z-index: 10"
            >
              Giảm giá
            </div>

            <!-- Bo góc ảnh luôn -->
            <img
              class="card-img rounded-4 custom-shadow"
              style="height: 450px; object-fit: cover"
              src="../../assets/imgs/Vàng nhạt và Xanh ngọc lam Nghệ thuật sắp chữ In đậm Tiệm cà phê Biểu trưng Cà phê.png"
              alt="Card image"
            />

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

              <p class="card-text">Áo thun nam</p>

              <div class="d-flex justify-content-end">
                <!-- From Uiverse.io by AKAspidey01 -->

                <div class="main-section rounded-4">
                  <button class="first-button">Còn hàng</button>
                  <button class="second-button">
                    <svg
                      viewBox="0 0 24 24"
                      width="20"
                      height="20"
                      stroke="#ffd300"
                      stroke-width="2"
                      fill="none"
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      class="css-i6dzq1"
                    >
                      <circle cx="9" cy="21" r="1"></circle>
                      <circle cx="20" cy="21" r="1"></circle>
                      <path
                        d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6"
                      ></path>
                    </svg>
                    3,4k đã bán
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="col-3">
          <div
            style="border: 0px"
            class="card position-relative overflow-hidden rounded-4"
          >
            <!-- Label Giảm giá -->
            <div
              class="position-absolute top-0 start-0 bg-danger text-white px-3 py-1 m-3 shadow-sm"
              style="border-radius: 12px; font-size: 0.85rem; z-index: 10"
            >
              Giảm giá
            </div>

            <!-- Bo góc ảnh luôn -->
            <img
              class="card-img rounded-4 custom-shadow"
              style="height: 450px; object-fit: cover"
              src="../../assets/imgs/Vàng nhạt và Xanh ngọc lam Nghệ thuật sắp chữ In đậm Tiệm cà phê Biểu trưng Cà phê.png"
              alt="Card image"
            />

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

              <p class="card-text">Áo thun nam</p>

              <div class="d-flex justify-content-end">
                <!-- From Uiverse.io by AKAspidey01 -->

                <div class="main-section rounded-4">
                  <button class="first-button">Còn hàng</button>
                  <button class="second-button">
                    <svg
                      viewBox="0 0 24 24"
                      width="20"
                      height="20"
                      stroke="#ffd300"
                      stroke-width="2"
                      fill="none"
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      class="css-i6dzq1"
                    >
                      <circle cx="9" cy="21" r="1"></circle>
                      <circle cx="20" cy="21" r="1"></circle>
                      <path
                        d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6"
                      ></path>
                    </svg>
                    3,4k đã bán
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="col-3">
          <div
            style="border: 0px"
            class="card position-relative overflow-hidden rounded-4"
          >
            <!-- Label Giảm giá -->
            <div
              class="position-absolute top-0 start-0 bg-danger text-white px-3 py-1 m-3 shadow-sm"
              style="border-radius: 12px; font-size: 0.85rem; z-index: 10"
            >
              Giảm giá
            </div>

            <!-- Bo góc ảnh luôn -->
            <img
              class="card-img rounded-4 custom-shadow"
              style="height: 450px; object-fit: cover"
              src="../../assets/imgs/Vàng nhạt và Xanh ngọc lam Nghệ thuật sắp chữ In đậm Tiệm cà phê Biểu trưng Cà phê.png"
              alt="Card image"
            />

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

              <p class="card-text">Áo thun nam</p>

              <div class="d-flex justify-content-end">
                <!-- From Uiverse.io by AKAspidey01 -->

                <div class="main-section rounded-4">
                  <button class="first-button">Còn hàng</button>
                  <button class="second-button">
                    <svg
                      viewBox="0 0 24 24"
                      width="20"
                      height="20"
                      stroke="#ffd300"
                      stroke-width="2"
                      fill="none"
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      class="css-i6dzq1"
                    >
                      <circle cx="9" cy="21" r="1"></circle>
                      <circle cx="20" cy="21" r="1"></circle>
                      <path
                        d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6"
                      ></path>
                    </svg>
                    3,4k đã bán
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!--end giảm giá -->
      <!--start danh mục nổi bật -->
      <div class="row mt-5 g-4">
        <div class="col text-center">
          <h1>Các danh mục nổi bật</h1>
          <p>Top san pham cua nam ajfdlsfjlsdjlfjlsjfdljf</p>
        </div>
      </div>
      <!--end danh mục nổi bật -->
      <!--start phân loại -->
      <div class="row mt-5 g-4">
        <div class="col-4">
          <div
            style="border: 0px"
            class="card position-relative overflow-hidden rounded-4"
          >
            <!-- Bo góc ảnh luôn -->
            <img
              class="card-img rounded-4 custom-shadow"
              style="height: 700px; object-fit: cover"
              src="../../assets/imgs/Vàng nhạt và Xanh ngọc lam Nghệ thuật sắp chữ In đậm Tiệm cà phê Biểu trưng Cà phê.png"
              alt="Card image"
            />
          </div>
        </div>
        <div class="col-4">
          <div class="row">
            <div class="col">
              <div
                style="border: 0px"
                class="card position-relative overflow-hidden rounded-4"
              >
                <!-- Bo góc ảnh luôn -->
                <img
                  class="card-img rounded-4 custom-shadow"
                  style="height: 338px; object-fit: cover"
                  src="../../assets/imgs/Vàng nhạt và Xanh ngọc lam Nghệ thuật sắp chữ In đậm Tiệm cà phê Biểu trưng Cà phê.png"
                  alt="Card image"
                />
              </div>
            </div>
          </div>
          <div class="row mt-4">
            <div class="col">
              <div
                style="border: 0px"
                class="card position-relative overflow-hidden rounded-4"
              >
                <!-- Bo góc ảnh luôn -->
                <img
                  class="card-img rounded-4 custom-shadow"
                  style="height: 338px; object-fit: cover"
                  src="../../assets/imgs/Vàng nhạt và Xanh ngọc lam Nghệ thuật sắp chữ In đậm Tiệm cà phê Biểu trưng Cà phê.png"
                  alt="Card image"
                />
              </div>
            </div>
          </div>
        </div>
        <div class="col-4">
          <div
            style="border: 0px"
            class="card position-relative overflow-hidden rounded-4"
          >
            <!-- Bo góc ảnh luôn -->
            <img
              class="card-img rounded-4 custom-shadow"
              style="height: 700px; object-fit: cover"
              src="../../assets/imgs/Vàng nhạt và Xanh ngọc lam Nghệ thuật sắp chữ In đậm Tiệm cà phê Biểu trưng Cà phê.png"
              alt="Card image"
            />
          </div>
        </div>
      </div>
      <!--end phân loại -->

      <!--start all sản phẩm -->
      <div class="hstack mt-5 align-items-center">
        <div>
          <h3>San pham ban chay</h3>
        </div>
        <div class="ms-auto">
          <p class="mb-0">
            <!-- From Uiverse.io by alexmaracinaru -->
            <button id="next">
              <span>Xem thêm</span>
              <svg
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 74 74"
                height="34"
                width="34"
              >
                <circle stroke-width="3" stroke="black" r="35.5" cy="37" cx="37"></circle>
                <path
                  fill="black"
                  d="M25 35.5C24.1716 35.5 23.5 36.1716 23.5 37C23.5 37.8284 24.1716 38.5 25 38.5V35.5ZM49.0607 38.0607C49.6464 37.4749 49.6464 36.5251 49.0607 35.9393L39.5147 26.3934C38.9289 25.8076 37.9792 25.8076 37.3934 26.3934C36.8076 26.9792 36.8076 27.9289 37.3934 28.5147L45.8787 37L37.3934 45.4853C36.8076 46.0711 36.8076 47.0208 37.3934 47.6066C37.9792 48.1924 38.9289 48.1924 39.5147 47.6066L49.0607 38.0607ZM25 38.5L48 38.5V35.5L25 35.5V38.5Z"
                ></path>
              </svg>
            </button>
          </p>
        </div>
      </div>
      <div class="row mt-4 g-4">
        <div class="col-3">
          <div
            style="border: 0px"
            class="card position-relative overflow-hidden rounded-4"
          >
            <!-- Label Giảm giá -->
            <div
              class="position-absolute top-0 start-0 bg-danger text-white px-3 py-1 m-3 shadow-sm"
              style="border-radius: 12px; font-size: 0.85rem; z-index: 10"
            >
              Giảm giá
            </div>

            <!-- Bo góc ảnh luôn -->
            <img
              class="card-img rounded-4 custom-shadow"
              style="height: 450px; object-fit: cover"
              src="../../assets/imgs/Vàng nhạt và Xanh ngọc lam Nghệ thuật sắp chữ In đậm Tiệm cà phê Biểu trưng Cà phê.png"
              alt="Card image"
            />

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

              <p class="card-text">Áo thun nam</p>

              <div class="d-flex justify-content-end">
                <!-- From Uiverse.io by AKAspidey01 -->

                <div class="main-section rounded-4">
                  <button class="first-button">Còn hàng</button>
                  <button class="second-button">
                    <svg
                      viewBox="0 0 24 24"
                      width="20"
                      height="20"
                      stroke="#ffd300"
                      stroke-width="2"
                      fill="none"
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      class="css-i6dzq1"
                    >
                      <circle cx="9" cy="21" r="1"></circle>
                      <circle cx="20" cy="21" r="1"></circle>
                      <path
                        d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6"
                      ></path>
                    </svg>
                    3,4k đã bán
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="col-3">
          <div
            style="border: 0px"
            class="card position-relative overflow-hidden rounded-4"
          >
            <!-- Label Giảm giá -->
            <div
              class="position-absolute top-0 start-0 bg-danger text-white px-3 py-1 m-3 shadow-sm"
              style="border-radius: 12px; font-size: 0.85rem; z-index: 10"
            >
              Giảm giá
            </div>

            <!-- Bo góc ảnh luôn -->
            <img
              class="card-img rounded-4 custom-shadow"
              style="height: 450px; object-fit: cover"
              src="../../assets/imgs/Vàng nhạt và Xanh ngọc lam Nghệ thuật sắp chữ In đậm Tiệm cà phê Biểu trưng Cà phê.png"
              alt="Card image"
            />

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

              <p class="card-text">Áo thun nam</p>

              <div class="d-flex justify-content-end">
                <!-- From Uiverse.io by AKAspidey01 -->

                <div class="main-section rounded-4">
                  <button class="first-button">Còn hàng</button>
                  <button class="second-button">
                    <svg
                      viewBox="0 0 24 24"
                      width="20"
                      height="20"
                      stroke="#ffd300"
                      stroke-width="2"
                      fill="none"
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      class="css-i6dzq1"
                    >
                      <circle cx="9" cy="21" r="1"></circle>
                      <circle cx="20" cy="21" r="1"></circle>
                      <path
                        d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6"
                      ></path>
                    </svg>
                    3,4k đã bán
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="col-3">
          <div
            style="border: 0px"
            class="card position-relative overflow-hidden rounded-4"
          >
            <!-- Label Giảm giá -->
            <div
              class="position-absolute top-0 start-0 bg-danger text-white px-3 py-1 m-3 shadow-sm"
              style="border-radius: 12px; font-size: 0.85rem; z-index: 10"
            >
              Giảm giá
            </div>

            <!-- Bo góc ảnh luôn -->
            <img
              class="card-img rounded-4 custom-shadow"
              style="height: 450px; object-fit: cover"
              src="../../assets/imgs/Vàng nhạt và Xanh ngọc lam Nghệ thuật sắp chữ In đậm Tiệm cà phê Biểu trưng Cà phê.png"
              alt="Card image"
            />

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

              <p class="card-text">Áo thun nam</p>

              <div class="d-flex justify-content-end">
                <!-- From Uiverse.io by AKAspidey01 -->

                <div class="main-section rounded-4">
                  <button class="first-button">Còn hàng</button>
                  <button class="second-button">
                    <svg
                      viewBox="0 0 24 24"
                      width="20"
                      height="20"
                      stroke="#ffd300"
                      stroke-width="2"
                      fill="none"
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      class="css-i6dzq1"
                    >
                      <circle cx="9" cy="21" r="1"></circle>
                      <circle cx="20" cy="21" r="1"></circle>
                      <path
                        d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6"
                      ></path>
                    </svg>
                    3,4k đã bán
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="col-3">
          <div
            style="border: 0px"
            class="card position-relative overflow-hidden rounded-4"
          >
            <!-- Label Giảm giá -->
            <div
              class="position-absolute top-0 start-0 bg-danger text-white px-3 py-1 m-3 shadow-sm"
              style="border-radius: 12px; font-size: 0.85rem; z-index: 10"
            >
              Giảm giá
            </div>

            <!-- Bo góc ảnh luôn -->
            <img
              class="card-img rounded-4 custom-shadow"
              style="height: 450px; object-fit: cover"
              src="../../assets/imgs/Vàng nhạt và Xanh ngọc lam Nghệ thuật sắp chữ In đậm Tiệm cà phê Biểu trưng Cà phê.png"
              alt="Card image"
            />

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

              <p class="card-text">Áo thun nam</p>

              <div class="d-flex justify-content-end">
                <!-- From Uiverse.io by AKAspidey01 -->

                <div class="main-section rounded-4">
                  <button class="first-button">Còn hàng</button>
                  <button class="second-button">
                    <svg
                      viewBox="0 0 24 24"
                      width="20"
                      height="20"
                      stroke="#ffd300"
                      stroke-width="2"
                      fill="none"
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      class="css-i6dzq1"
                    >
                      <circle cx="9" cy="21" r="1"></circle>
                      <circle cx="20" cy="21" r="1"></circle>
                      <path
                        d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6"
                      ></path>
                    </svg>
                    3,4k đã bán
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="row mt-4 g-4">
        <div class="col-3">
          <div
            style="border: 0px"
            class="card position-relative overflow-hidden rounded-4"
          >
            <!-- Label Giảm giá -->
            <div
              class="position-absolute top-0 start-0 bg-danger text-white px-3 py-1 m-3 shadow-sm"
              style="border-radius: 12px; font-size: 0.85rem; z-index: 10"
            >
              Giảm giá
            </div>

            <!-- Bo góc ảnh luôn -->
            <img
              class="card-img rounded-4 custom-shadow"
              style="height: 450px; object-fit: cover"
              src="../../assets/imgs/Vàng nhạt và Xanh ngọc lam Nghệ thuật sắp chữ In đậm Tiệm cà phê Biểu trưng Cà phê.png"
              alt="Card image"
            />

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

              <p class="card-text">Áo thun nam</p>

              <div class="d-flex justify-content-end">
                <!-- From Uiverse.io by AKAspidey01 -->

                <div class="main-section rounded-4">
                  <button class="first-button">Còn hàng</button>
                  <button class="second-button">
                    <svg
                      viewBox="0 0 24 24"
                      width="20"
                      height="20"
                      stroke="#ffd300"
                      stroke-width="2"
                      fill="none"
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      class="css-i6dzq1"
                    >
                      <circle cx="9" cy="21" r="1"></circle>
                      <circle cx="20" cy="21" r="1"></circle>
                      <path
                        d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6"
                      ></path>
                    </svg>
                    3,4k đã bán
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="col-3">
          <div
            style="border: 0px"
            class="card position-relative overflow-hidden rounded-4"
          >
            <!-- Label Giảm giá -->
            <div
              class="position-absolute top-0 start-0 bg-danger text-white px-3 py-1 m-3 shadow-sm"
              style="border-radius: 12px; font-size: 0.85rem; z-index: 10"
            >
              Giảm giá
            </div>

            <!-- Bo góc ảnh luôn -->
            <img
              class="card-img rounded-4 custom-shadow"
              style="height: 450px; object-fit: cover"
              src="../../assets/imgs/Vàng nhạt và Xanh ngọc lam Nghệ thuật sắp chữ In đậm Tiệm cà phê Biểu trưng Cà phê.png"
              alt="Card image"
            />

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

              <p class="card-text">Áo thun nam</p>

              <div class="d-flex justify-content-end">
                <!-- From Uiverse.io by AKAspidey01 -->

                <div class="main-section rounded-4">
                  <button class="first-button">Còn hàng</button>
                  <button class="second-button">
                    <svg
                      viewBox="0 0 24 24"
                      width="20"
                      height="20"
                      stroke="#ffd300"
                      stroke-width="2"
                      fill="none"
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      class="css-i6dzq1"
                    >
                      <circle cx="9" cy="21" r="1"></circle>
                      <circle cx="20" cy="21" r="1"></circle>
                      <path
                        d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6"
                      ></path>
                    </svg>
                    3,4k đã bán
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="col-3">
          <div
            style="border: 0px"
            class="card position-relative overflow-hidden rounded-4"
          >
            <!-- Label Giảm giá -->
            <div
              class="position-absolute top-0 start-0 bg-danger text-white px-3 py-1 m-3 shadow-sm"
              style="border-radius: 12px; font-size: 0.85rem; z-index: 10"
            >
              Giảm giá
            </div>

            <!-- Bo góc ảnh luôn -->
            <img
              class="card-img rounded-4 custom-shadow"
              style="height: 450px; object-fit: cover"
              src="../../assets/imgs/Vàng nhạt và Xanh ngọc lam Nghệ thuật sắp chữ In đậm Tiệm cà phê Biểu trưng Cà phê.png"
              alt="Card image"
            />

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

              <p class="card-text">Áo thun nam</p>

              <div class="d-flex justify-content-end">
                <!-- From Uiverse.io by AKAspidey01 -->

                <div class="main-section rounded-4">
                  <button class="first-button">Còn hàng</button>
                  <button class="second-button">
                    <svg
                      viewBox="0 0 24 24"
                      width="20"
                      height="20"
                      stroke="#ffd300"
                      stroke-width="2"
                      fill="none"
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      class="css-i6dzq1"
                    >
                      <circle cx="9" cy="21" r="1"></circle>
                      <circle cx="20" cy="21" r="1"></circle>
                      <path
                        d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6"
                      ></path>
                    </svg>
                    3,4k đã bán
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="col-3">
          <div
            style="border: 0px"
            class="card position-relative overflow-hidden rounded-4"
          >
            <!-- Label Giảm giá -->
            <div
              class="position-absolute top-0 start-0 bg-danger text-white px-3 py-1 m-3 shadow-sm"
              style="border-radius: 12px; font-size: 0.85rem; z-index: 10"
            >
              Giảm giá
            </div>

            <!-- Bo góc ảnh luôn -->
            <img
              class="card-img rounded-4 custom-shadow"
              style="height: 450px; object-fit: cover"
              src="../../assets/imgs/Vàng nhạt và Xanh ngọc lam Nghệ thuật sắp chữ In đậm Tiệm cà phê Biểu trưng Cà phê.png"
              alt="Card image"
            />

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

              <p class="card-text">Áo thun nam</p>

              <div class="d-flex justify-content-end">
                <!-- From Uiverse.io by AKAspidey01 -->

                <div class="main-section rounded-4">
                  <button class="first-button">Còn hàng</button>
                  <button class="second-button">
                    <svg
                      viewBox="0 0 24 24"
                      width="20"
                      height="20"
                      stroke="#ffd300"
                      stroke-width="2"
                      fill="none"
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      class="css-i6dzq1"
                    >
                      <circle cx="9" cy="21" r="1"></circle>
                      <circle cx="20" cy="21" r="1"></circle>
                      <path
                        d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6"
                      ></path>
                    </svg>
                    3,4k đã bán
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!--end all sp -->
    </div>
    <!--start gioi thieu -->
    <section
      style="
        background-color: #f1cdbd;
        padding: 8rem 2rem;
        margin-top: 100px;
        margin-bottom: 100px;
      "
    >
      <div class="container">
        <div class="row align-items-center">
          <!-- Bên trái -->
          <div class="col-md-6 mb-4 mb-md-0">
            <h2 class="fw-bold text-white mb-4">
              Hãy xem qua những điểm bán hàng độc đáo của chúng tôi
            </h2>
            <p class="mb-0">
              <!-- From Uiverse.io by alexmaracinaru -->
              <button id="next">
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
                    stroke="black"
                    r="35.5"
                    cy="37"
                    cx="37"
                  ></circle>
                  <path
                    fill="black"
                    d="M25 35.5C24.1716 35.5 23.5 36.1716 23.5 37C23.5 37.8284 24.1716 38.5 25 38.5V35.5ZM49.0607 38.0607C49.6464 37.4749 49.6464 36.5251 49.0607 35.9393L39.5147 26.3934C38.9289 25.8076 37.9792 25.8076 37.3934 26.3934C36.8076 26.9792 36.8076 27.9289 37.3934 28.5147L45.8787 37L37.3934 45.4853C36.8076 46.0711 36.8076 47.0208 37.3934 47.6066C37.9792 48.1924 38.9289 48.1924 39.5147 47.6066L49.0607 38.0607ZM25 38.5L48 38.5V35.5L25 35.5V38.5Z"
                  ></path>
                </svg>
              </button>
            </p>
          </div>

          <!-- Bên phải -->
          <div class="col-md-6 text-white">
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
    </section>
    <!--end gioi thieu -->
    <div class="container-fluid px-5">
      <!--start tin tuc -->
      <div class="hstack mt-5 align-items-center">
        <div>
          <h3>San pham ban chay</h3>
        </div>
        <div class="ms-auto">
          <p class="mb-0">
            <!-- From Uiverse.io by alexmaracinaru -->
            <button id="next">
              <span>Xem thêm</span>
              <svg
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 74 74"
                height="34"
                width="34"
              >
                <circle stroke-width="3" stroke="black" r="35.5" cy="37" cx="37"></circle>
                <path
                  fill="black"
                  d="M25 35.5C24.1716 35.5 23.5 36.1716 23.5 37C23.5 37.8284 24.1716 38.5 25 38.5V35.5ZM49.0607 38.0607C49.6464 37.4749 49.6464 36.5251 49.0607 35.9393L39.5147 26.3934C38.9289 25.8076 37.9792 25.8076 37.3934 26.3934C36.8076 26.9792 36.8076 27.9289 37.3934 28.5147L45.8787 37L37.3934 45.4853C36.8076 46.0711 36.8076 47.0208 37.3934 47.6066C37.9792 48.1924 38.9289 48.1924 39.5147 47.6066L49.0607 38.0607ZM25 38.5L48 38.5V35.5L25 35.5V38.5Z"
                ></path>
              </svg>
            </button>
          </p>
        </div>
      </div>
      <div class="row mt-4 g-4">
        <div class="col-4">
          <div
            style="border: 0px"
            class="card position-relative overflow-hidden rounded-4"
          >
            <!-- Bo góc ảnh luôn -->
            <img
              class="card-img rounded-4 custom-shadow"
              style="height: 450px; object-fit: cover"
              src="../../assets/imgs/Vàng nhạt và Xanh ngọc lam Nghệ thuật sắp chữ In đậm Tiệm cà phê Biểu trưng Cà phê.png"
              alt="Card image"
            />

            <!-- Nội dung -->
            <div class="card-body">
              <p class="card-text">04/05/2024</p>
              <div class="card-title">Tin tuc</div>

              <div class="d-flex justify-content-end">
                <!-- From Uiverse.io by AKAspidey01 -->

                <div class="main-section rounded-4">
                  <button class="first-button">Còn hàng</button>
                  <button class="second-button">
                    <svg
                      viewBox="0 0 24 24"
                      width="20"
                      height="20"
                      stroke="#ffd300"
                      stroke-width="2"
                      fill="none"
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      class="css-i6dzq1"
                    >
                      <circle cx="9" cy="21" r="1"></circle>
                      <circle cx="20" cy="21" r="1"></circle>
                      <path
                        d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6"
                      ></path>
                    </svg>
                    3,4k đã bán
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="col-4">
          <div
            style="border: 0px"
            class="card position-relative overflow-hidden rounded-4"
          >
            <!-- Bo góc ảnh luôn -->
            <img
              class="card-img rounded-4 custom-shadow"
              style="height: 450px; object-fit: cover"
              src="../../assets/imgs/Vàng nhạt và Xanh ngọc lam Nghệ thuật sắp chữ In đậm Tiệm cà phê Biểu trưng Cà phê.png"
              alt="Card image"
            />

            <!-- Nội dung -->
            <div class="card-body">
              <p class="card-text">04/05/2024</p>
              <div class="card-title">Tin tuc</div>

              <div class="d-flex justify-content-end">
                <!-- From Uiverse.io by AKAspidey01 -->

                <div class="main-section rounded-4">
                  <button class="first-button">Còn hàng</button>
                  <button class="second-button">
                    <svg
                      viewBox="0 0 24 24"
                      width="20"
                      height="20"
                      stroke="#ffd300"
                      stroke-width="2"
                      fill="none"
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      class="css-i6dzq1"
                    >
                      <circle cx="9" cy="21" r="1"></circle>
                      <circle cx="20" cy="21" r="1"></circle>
                      <path
                        d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6"
                      ></path>
                    </svg>
                    3,4k đã bán
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="col-4">
          <div
            style="border: 0px"
            class="card position-relative overflow-hidden rounded-4"
          >
            <!-- Bo góc ảnh luôn -->
            <img
              class="card-img rounded-4 custom-shadow"
              style="height: 450px; object-fit: cover"
              src="../../assets/imgs/Vàng nhạt và Xanh ngọc lam Nghệ thuật sắp chữ In đậm Tiệm cà phê Biểu trưng Cà phê.png"
              alt="Card image"
            />

            <!-- Nội dung -->
            <div class="card-body">
              <p class="card-text">04/05/2024</p>
              <div class="card-title">Tin tuc</div>

              <div class="d-flex justify-content-end">
                <!-- From Uiverse.io by AKAspidey01 -->

                <div class="main-section rounded-4">
                  <button class="first-button">Còn hàng</button>
                  <button class="second-button">
                    <svg
                      viewBox="0 0 24 24"
                      width="20"
                      height="20"
                      stroke="#ffd300"
                      stroke-width="2"
                      fill="none"
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      class="css-i6dzq1"
                    >
                      <circle cx="9" cy="21" r="1"></circle>
                      <circle cx="20" cy="21" r="1"></circle>
                      <path
                        d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6"
                      ></path>
                    </svg>
                    3,4k đã bán
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!--end tin tuc  -->
    </div>
    <!-- thong tin -->
    <section style="padding: 8rem 2rem; margin-top: 100px; margin-bottom: 100px">
      <div class="container">
        <div class="row align-items-center">
          <!-- Bên trái -->
          <div class="col-md-6 mb-4 mb-md-0">
            <h2 class="fw-bold text-black mb-4">
              Hãy xem qua những điểm bán hàng độc đáo của chúng tôi
            </h2>
            <p class="mb-0">
              <!-- From Uiverse.io by alexmaracinaru -->
              <button id="next">
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
                    stroke="black"
                    r="35.5"
                    cy="37"
                    cx="37"
                  ></circle>
                  <path
                    fill="black"
                    d="M25 35.5C24.1716 35.5 23.5 36.1716 23.5 37C23.5 37.8284 24.1716 38.5 25 38.5V35.5ZM49.0607 38.0607C49.6464 37.4749 49.6464 36.5251 49.0607 35.9393L39.5147 26.3934C38.9289 25.8076 37.9792 25.8076 37.3934 26.3934C36.8076 26.9792 36.8076 27.9289 37.3934 28.5147L45.8787 37L37.3934 45.4853C36.8076 46.0711 36.8076 47.0208 37.3934 47.6066C37.9792 48.1924 38.9289 48.1924 39.5147 47.6066L49.0607 38.0607ZM25 38.5L48 38.5V35.5L25 35.5V38.5Z"
                  ></path>
                </svg>
              </button>
            </p>
          </div>

          <!-- Bên phải -->
          <div class="col-md-6 text-white">
            <div class="accordion" id="accordionPanelsStayOpenExample">
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
                    Accordion Item #1
                  </button>
                </h2>
                <div
                  id="panelsStayOpen-collapseOne"
                  class="accordion-collapse collapse show"
                >
                  <div class="accordion-body">
                    <strong>This is the first item’s accordion body.</strong> It is shown
                    by default, until the collapse plugin adds the appropriate classes
                    that we use to style each element. These classes control the overall
                    appearance, as well as the showing and hiding via CSS transitions. You
                    can modify any of this with custom CSS or overriding our default
                    variables. It’s also worth noting that just about any HTML can go
                    within the <code>.accordion-body</code>, though the transition does
                    limit overflow.
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
                    Accordion Item #2
                  </button>
                </h2>
                <div id="panelsStayOpen-collapseTwo" class="accordion-collapse collapse">
                  <div class="accordion-body">
                    <strong>This is the second item’s accordion body.</strong> It is
                    hidden by default, until the collapse plugin adds the appropriate
                    classes that we use to style each element. These classes control the
                    overall appearance, as well as the showing and hiding via CSS
                    transitions. You can modify any of this with custom CSS or overriding
                    our default variables. It’s also worth noting that just about any HTML
                    can go within the <code>.accordion-body</code>, though the transition
                    does limit overflow.
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
                    Accordion Item #3
                  </button>
                </h2>
                <div
                  id="panelsStayOpen-collapseThree"
                  class="accordion-collapse collapse"
                >
                  <div class="accordion-body">
                    <strong>This is the third item’s accordion body.</strong> It is hidden
                    by default, until the collapse plugin adds the appropriate classes
                    that we use to style each element. These classes control the overall
                    appearance, as well as the showing and hiding via CSS transitions. You
                    can modify any of this with custom CSS or overriding our default
                    variables. It’s also worth noting that just about any HTML can go
                    within the <code>.accordion-body</code>, though the transition does
                    limit overflow.
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
    <!-- thong tin -->
  </main>
</template>
<style scoped>
/* Tùy chỉnh hiệu ứng dropdown */

.custom-shadow {
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.12);
}

/* From Uiverse.io by alexmaracinaru */
#next {
  cursor: pointer;
  font-weight: 700;
  transition: all 0.2s;
  padding: 10px 20px;
  border-radius: 100px;
  background: #cfef00;
  border: 1px solid transparent;
  display: flex;
  align-items: center;
  font-size: 15px;
}

#next:hover {
  background: #c4e201;
}

#next > svg {
  width: 34px;
  margin-left: 10px;
  transition: transform 0.3s ease-in-out;
}

#next:hover svg {
  transform: translateX(5px);
}

#next:active {
  transform: scale(0.95);
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
</style>
