// src/services/authService.js
import { computed, ref } from 'vue';
import router from '../router' // ✅ đúng, vì bạn đã export router ở router/index.js
import axios, { Axios } from 'axios';
import { toast } from 'vue3-toastify';
import { syncLocalCartToServer } from './cart';
// ${import.meta.env.VITE_PAKE_DOMAIN}
const api = axios.create({
  baseURL: `http://localhost:8080/api`,
  headers: {
    'Content-Type': 'application/json'
  },
  withCredentials: true // Nếu bạn dùng session hoặc OAuth2
});

const excludedPaths = [
  '/Accounts/login',
  '/Accounts/register',
  '/Accounts/verify-otp',
  '/BaseProducts',
  '/VariantValues',
  '/openai/chat',
  '/Promotions/',
  '/Promotions',
  '/shipping/fee',
  '/CatalogCategoriesFilter'
]

// Automatically attach token to each request
api.interceptors.request.use(config => {
  const token = localStorage.getItem('jwtToken');
  // Kiểm tra nếu URL KHÔNG nằm trong danh sách ngoại lệ thì mới gắn token
  // Trong interceptor:
  const isExcluded = excludedPaths.some(path =>
    path.endsWith('/') ? config.url.startsWith(path) : config.url.includes(path) && !config.url.includes("/admin")
  );
  console.log('Request URL:', config.url, '| Excluded:', isExcluded);
  // neu url ngoai le 
  if (!isExcluded) {
    const isExpirate = new Date(authService.parseJwt(token).exp * 1000).toLocaleString();
    config.headers.Authorization = `Bearer ${token}`;
    if (token && !isExpirate >= new Date().toLocaleString()) {
      alert('Đăng nhập hết hạn 1')
      localStorage.removeItem('jwtToken')
      router.push('/login')
    }
  }

  return config;
});

api.interceptors.response.use(
  res => res,
  err => {
    if (err.response) {
      const status = err.response.status
      console.error('Response error:', err.response.data);
      console.log('Response error status:', status);
      // if (status === 401) {
      //   localStorage.removeItem('jwtToken')
      //   toast.error('Hết phiên đăng nhập, vui lòng đăng nhập lại !')
      //   localStorage.removeItem('jwtToken')
      //   router.push('/login')
      // } else if (status === 403) {
      //   router.push('/403')
      // } else 
      if (status === 403) {
        router.push('/403')
      } else if (status === 401 && err.response.data.includes('Token đã hết hạn')) {
        localStorage.removeItem('jwtToken')
        cartSize.value = 0;
        tokenRef.value = null;
        router.push('/login')
        setTimeout(() => {
          alert('Hết phiên đăng nhập, vui lòng đăng nhập lại !')
        }, 500)
      }
    }
    return Promise.reject(err)
  }
)
const tokenRef = ref(localStorage.getItem('jwtToken'))
const cartSize = ref(localStorage.getItem('cartSize') ? parseInt(localStorage.getItem('cartSize')) : 0);
const authService = {
  async login(email, password) {
    // console.log({ email, password })
    return api.post('/Accounts/login', { email, password })
      .then(async (response) => {
        const token = response.data.token;
        const cartSizeValue = response.data.cartSize || 0;
        // Set token và cart size trước
        localStorage.setItem('jwtToken', token);
        localStorage.setItem('cartSize', cartSizeValue);
        console.log('cart sizeValue: ', cartSizeValue);
        this.setTokenRef(token);
        cartSize.value = cartSizeValue;
        console.log('authService redirect: ', localStorage.getItem('redirectTo'));
        // Sync cart
        await new Promise(resolve => setTimeout(resolve, 100));
        await syncLocalCartToServer();

        // Check admin role
        authService.isAdmin();

        // Navigate
        const redirectTo = localStorage.getItem('redirectTo') || '/';
        localStorage.removeItem('redirectTo'); // Clear redirect after use

        setTimeout(() => {
          toast.success('Đăng nhập thành công !');
        }, 500);

        await router.push(redirectTo);
      })
      .catch(error => {
        toast.warning(error.response?.data?.message || 'Đăng nhập thất bại');
        console.log('Đăng nhập thất bại ', error.response)
      })
  },
  setTokenRef(token) {
    tokenRef.value = token;
  }
  ,
  isLogged() {
    return tokenRef.value != null;
  }
  ,
  getCartSize() {
    return cartSize.value;
  },
  updateCart(qty) {
    cartSize.value += qty;
    console.log('Cart size updated:', cartSize.value)
    localStorage.setItem('cartSize', cartSize.value);
  }
  ,
  setCart(qty) {
    cartSize.value = qty;
    console.log('Cart size set:', cartSize.value)
    localStorage.setItem('cartSize', cartSize.value);
  }
  ,
  isAdmin() {
    if (tokenRef.value) {
      try {
        const roles = authService.parseJwt(tokenRef.value).roles;
        return Array.isArray(roles) && roles.includes('ADMIN');
      } catch (e) {
        console.error('Invalid token:', e);
        return false;
      }
    }
    return false;
  },
  getUserName() {
    if (localStorage.getItem("jwtToken")) {
      try {
        const username = authService.parseJwt(tokenRef.value)
        // // .username
        // console.log(username)
        return username.sub;
      } catch (error) {
        console.error('Invalid payload:', error);
        return false;
      }
    }
  },
  getProfile() {
    return api.get('/Accounts/me')
      .then(res => res.data)
      .catch(err => {
        console.error('Không thể lấy thông tin tài khoản:', err);
        throw err;
      });
  },
  updateProfile(dto) {
    return api.put('/Accounts/me', dto)
      .then(res => res.data)
      .catch(err => {
        console.error('Lỗi cập nhật thông tin:', err);
        throw err;
      });
  },
  uploadAvatar(formData) {
    return api.put("/Accounts/me/avatar", formData, {
      headers: { "Content-Type": "multipart/form-data" }
    }).then(res => res.data)
  },
  changePassword(dto) {
    return api.put('/Accounts/change-password', dto)
      .then(res => res.data)
      .catch(err => {
        console.error('Lỗi đổi mật khẩu:', err);
        throw err;
      });
  },

  logout() {
    localStorage.removeItem('jwtToken');
    router.push('/');
    setTimeout(() => {
      toast.success('Đăng xuất thành công !');
    }, 600);
    cartSize.value = 0;
    localStorage.removeItem('cartSize');
    tokenRef.value = null;
    console.log('User logged out');
  },

  getToken() {
    return localStorage.getItem('jwtToken');
  },

  parseJwt(token) {
    try {
      var base64Url = token.split('.')[1];
      var payload = atob(base64Url)
      return JSON.parse(payload)
    } catch (error) {
      console.log(error);
      return null;
    }
  }
};
const homeService = {
  getBestSellers(limit = 8) {
    return api.get('/homepage/bestsellers', {
      params: { limit }
    }).then(res => res.data)
      .catch(err => {
        console.error('Lỗi lấy sản phẩm bán chạy:', err);
        throw err;
      });
  }
};

const cartService = {
  async getCart() {
    const cartList = ref([]);

    try {
      const response = await api.get('/cart');
      cartList.value = response.data;
      return cartList.value;
    } catch (error) {
      const localCartList = JSON.parse(localStorage.getItem('cart')) ?? [];

      if (localCartList.length === 0) {
        return cartList.value;
      }

      const response = await axios.get(
        `http://localhost:8080/api/Product/MultiplrFilter?page=0&size=10000000&skuColorLikeReq=&skuSizeLikeReq=&minPriceReq=&maxPriceReq=`
      );

      // Get product data from response
      const productList = response.data.content;

      // Map local cart with product details
      cartList.value = localCartList.map(item => {
        const product = productList.find(p => p.productItemId === item.productItems);

        return {
          id: item.id || '',
          productItemId: product?.productItemId,
          name: product?.name || 'Unknown Product',
          price: product?.price || 0,
          quantity: item.qty,
          image: product?.image || 'no-image.png',
          comboType: product?.comboType || 'normal',
        };
      });

      // ✅ Return after mapping
      return cartList.value;
    }
  }
};


export default api;
export { authService, cartService, homeService };
