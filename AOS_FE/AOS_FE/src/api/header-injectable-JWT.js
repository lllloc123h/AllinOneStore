// src/services/authService.js
import router from '../router' // ✅ đúng, vì bạn đã export router ở router/index.js
import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080/api',
  headers:{
    'Content-Type': 'application/json'
    },
  withCredentials: true // Nếu bạn dùng session hoặc OAuth2
});

// Automatically attach token to each request
api.interceptors.request.use(config => {
  const token = localStorage.getItem('jwtToken');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

api.interceptors.response.use(
    res => res,
    err => {
        if (err.response) {
            const status = err.response.status
            if (status === 401) {
                localStorage.removeItem('token')
                alert('Hết phiên đăng nhập, vui lòng đăng nhập lại !')
                localStorage.removeItem('jwtToken')
                router.push('/login')
            }else if (status === 403) {
                router.push('/403')
            }else if (status === 400) {
                alert('Sai thông tin đăng nhập !')
            }
        }
        return Promise.reject(err)
    }
)

const authService = {
  login(email, password) {
    console.log({ email, password })
    return api.post('/Accounts/login', { email, password })
    .then(response => {
      localStorage.setItem('jwtToken', response.data.token);
      console.log('Đăng nhập thành công ',response.data)
      console.log('authService redirect: ',localStorage.getItem('redirectTo'));
      router.push(localStorage.getItem('redirectTo') || '/')

    })
    .catch(error => console.log('Đăng nhập thất bại ',error.response.data))
  }
  ,

  logout() {
    localStorage.removeItem('jwtToken');
    console.log('User logged out');
  },

  getToken() {
    return localStorage.getItem('jwtToken');
  },

  isLoggedIn() {
    return !!localStorage.getItem('jwtToken');
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

export default authService;
