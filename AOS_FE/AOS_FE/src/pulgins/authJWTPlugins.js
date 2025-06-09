// src/plugins/authPlugin.js
import api from '../ConfigAPI/api';

export default {
  install(app) {
    app.config.globalProperties.$auth = api;
  }
};
