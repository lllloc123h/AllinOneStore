<template>
  <nav class="navbar navbar-expand-lg bg-body-tertiary sticky-top shadow-sm">
    <div class="container-fluid">
      <a class="navbar-brand" href="#">
        <img src="/src/assets/imgs/logo.png" style="height: 75px" alt="" /> ALL-IN-ONE</a
      >
      <button
        class="navbar-toggler"
        type="button"
        data-bs-toggle="collapse"
        data-bs-target="#navbarSupportedContent"
        aria-controls="navbarSupportedContent"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <svg
          xmlns="http://www.w3.org/2000/svg"
          width="16"
          height="16"
          fill="currentColor"
          class="bi bi-border-width"
          viewBox="0 0 16 16"
        >
          <path
            d="M0 3.5A.5.5 0 0 1 .5 3h15a.5.5 0 0 1 .5.5v2a.5.5 0 0 1-.5.5H.5a.5.5 0 0 1-.5-.5zm0 5A.5.5 0 0 1 .5 8h15a.5.5 0 0 1 .5.5v1a.5.5 0 0 1-.5.5H.5a.5.5 0 0 1-.5-.5zm0 4a.5.5 0 0 1 .5-.5h15a.5.5 0 0 1 0 1H.5a.5.5 0 0 1-.5-.5"
          />
        </svg>
      </button>
      <div class="collapse navbar-collapse row" id="navbarSupportedContent">
        <ul class="navbar-nav mb-2 mb-lg-0 col-3">
          <li class="nav-item">
            <RouterLink class="nav-link active" aria-current="page" to="/"
              >Home</RouterLink
            >
          </li>
          <li class="nav-item">
            <RouterLink class="nav-link active" aria-current="page" to="/products"
              >Product</RouterLink
            >
          </li>
          <li class="nav-item">
            <RouterLink class="nav-link active" aria-current="page" to="/UserInfo"
              >User Info</RouterLink
            >
          </li>
          <li class="nav-item">
            <RouterLink class="nav-link active" aria-current="page" to="/Customizer"
              >Customizer
            </RouterLink>
          </li>
        </ul>
        <ul class="col-6"></ul>
        <ul class="navbar-nav mb-2 mb-lg-0 col-3">
          <li class="nav-item">
            <a class="nav-link" aria-current="page" href="/cart">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="16"
                height="16"
                fill="currentColor"
                class="bi bi-basket-fill"
                viewBox="0 0 16 16"
              >
                <path
                  d="M5.071 1.243a.5.5 0 0 1 .858.514L3.383 6h9.234L10.07 1.757a.5.5 0 1 1 .858-.514L13.783 6H15.5a.5.5 0 0 1 .5.5v2a.5.5 0 0 1-.5.5H15v5a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2V9H.5a.5.5 0 0 1-.5-.5v-2A.5.5 0 0 1 .5 6h1.717zM3.5 10.5a.5.5 0 1 0-1 0v3a.5.5 0 0 0 1 0zm2.5 0a.5.5 0 1 0-1 0v3a.5.5 0 0 0 1 0zm2.5 0a.5.5 0 1 0-1 0v3a.5.5 0 0 0 1 0zm2.5 0a.5.5 0 1 0-1 0v3a.5.5 0 0 0 1 0zm2.5 0a.5.5 0 1 0-1 0v3a.5.5 0 0 0 1 0z"
                />
              </svg>
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" aria-current="page" href="">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="16"
                height="16"
                fill="currentColor"
                class="bi bi-bell-fill"
                viewBox="0 0 16 16"
              >
                <path
                  d="M8 16a2 2 0 0 0 2-2H6a2 2 0 0 0 2 2m.995-14.901a1 1 0 1 0-1.99 0A5 5 0 0 0 3 6c0 1.098-.5 6-2 7h14c-1.5-1-2-5.902-2-7 0-2.42-1.72-4.44-4.005-4.901"
                />
              </svg>
            </a>
          </li>

          <li class="nav-item dropdown">
            <a
              class="nav-link dropdown-toggle"
              href="#"
              role="button"
              data-bs-toggle="dropdown"
              aria-expanded="false"
            >
              <i class="fa-solid fa-user"></i>
            </a>
            <ul class="dropdown-menu">
              <li>
                <RouterLink class="dropdown-item" to="/admin/Accounts" v-if="isAdmin"
                  >Admin
                </RouterLink>
              </li>
              <li>
                <button class="dropdown-item" @click="logout" v-if="isLogged">
                  Logout
                </button>
                <RouterLink class="dropdown-item" to="/login" v-else>Login</RouterLink>
              </li>
              <li>
                <RouterLink class="dropdown-item" to="/register" v-if="!isLogged"
                  >Register</RouterLink
                >
              </li>
            </ul>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>
<script setup>
import { computed, watch } from "vue";
import { authService } from "../../Configs/api";
console.log("trang thai logged ", !!authService.isLogged());
console.log("trang thai logged ", !!authService.isAdmin());
const isLogged = computed(() => {
  return !!authService.isLogged();
});
const isAdmin = computed(() => {
  console.log(authService.isAdmin());
  return !!authService.isAdmin();
});
const logout = () => {
  authService.logout();
};
</script>
<style scope>
/* Footer */
#footer {
  padding: 60px 0;
  background-color: #edcdbb;
}

section .section-title {
  text-align: center;
  color: #007b5e;
  margin-bottom: 50px;
  text-transform: uppercase;
}

#footer h5 {
  padding-left: 10px;
  border-left: 3px solid #eeeeee;
  padding-bottom: 6px;
  margin-bottom: 20px;
  color: #ffffff;
}

#footer a {
  color: #ffffff;
  text-decoration: none !important;
  background-color: transparent;
  -webkit-text-decoration-skip: objects;
}

#footer ul.social li {
  padding: 3px 0;
}

#footer ul.social li a i {
  margin-right: 5px;
  font-size: 25px;
  -webkit-transition: 0.5s all ease;
  -moz-transition: 0.5s all ease;
  transition: 0.5s all ease;
}

#footer ul.social li:hover a i {
  font-size: 30px;
  margin-top: -10px;
}

#footer ul.social li a,
#footer ul.quick-links li a {
  color: #ffffff;
}

#footer ul.social li a:hover {
  color: #eeeeee;
}

#footer ul.quick-links li {
  padding: 3px 0;
  -webkit-transition: 0.5s all ease;
  -moz-transition: 0.5s all ease;
  transition: 0.5s all ease;
}

#footer ul.quick-links li:hover {
  padding: 3px 0;
  margin-left: 5px;
  font-weight: 700;
}

#footer ul.quick-links li a i {
  margin-right: 5px;
}

#footer ul.quick-links li:hover a i {
  font-weight: 700;
}

@media (max-width: 767px) {
  #footer h5 {
    padding-left: 0;
    border-left: transparent;
    padding-bottom: 0px;
    margin-bottom: 10px;
  }
}

/* From Uiverse.io by OnlyCodeChannel */
.searchBox {
  display: flex;
  width: 530px;
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
  width: 50px;
  height: 50px;
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
