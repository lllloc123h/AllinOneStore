// src/plugins/authPlugin.js
import authService from '../api/header-injectable-JWT';

export default {
  install(app) {
    app.config.globalProperties.$auth = authService;
  }
};
