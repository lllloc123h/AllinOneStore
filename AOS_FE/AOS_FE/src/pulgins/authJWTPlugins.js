// src/plugins/authPlugin.js
import api from '../Configs/api';

export default {
  install(app) {

    console.log(api),
      app.config.globalProperties.$auth = api;
  }
};
