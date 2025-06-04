// src/services/authService.js
import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080/api',
});

// Automatically attach token to each request
api.interceptors.request.use(config => {
  const token = localStorage.getItem('jwtToken');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

const authService = {
  login(email, password) {
    console.log( { email, password })
    return api.post('/Accounts/login', { email, password }).then(response => {
      if (response.data && response.data.token) {
        localStorage.setItem('jwtToken', response.data.token);
      }
      console.log(response.data)
      return response.data;
    });
  },

  logout() {
    localStorage.removeItem('jwtToken');
    console.log('User logged out');
  },

  getToken() {
    return localStorage.getItem('jwtToken');
  },

  isLoggedIn() {
    return !!localStorage.getItem('jwtToken');
  }
};

export default authService;
