// src/plugins/authPlugin.js
import api from '../ConfigAPI/api';

export default {
  install(app) {

    console.log(api),
      app.config.globalProperties.$auth = api;
  }
};
