// src/plugins/authPlugin.js
import api from '../Configs/api';

export default {
  install(app) {
    app.config.globalProperties.$auth = api;
  }
};
