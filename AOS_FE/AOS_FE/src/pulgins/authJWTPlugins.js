// src/plugins/authPlugin.js
import authService from '../services/header-injectable-JWT';

export default {
  install(app) {
    app.config.globalProperties.$auth = authService;
  }
};
