import Vue from 'vue';
import App from './App.vue';
import {router} from './router';
import http from 'vue-resource';
import Paginate from 'vuejs-paginate';
import VeeValidate from 'vee-validate';
import id from './locale-id'
import Snotify from 'vue-snotify';

import 'jquery/dist/jquery.min';
import 'bootstrap/dist/js/bootstrap.min';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'vue-snotify/styles/material.css';

Vue.config.productionTip = false;
Vue.use(http);
Vue.use(Snotify);

Vue.use(VeeValidate);
const rulesPlugin = ({ Validator }) => {
  Validator.localize('id', id);
};

VeeValidate.use(rulesPlugin);

Vue.component('paginate', Paginate);

Vue.http.options.root = 'http://localhost:8080';
Vue.http.interceptors.push(request => {
  request.credentials = 'include';

  return function (response) {
    if (response.status === 401) {
      router.push({path: "/login"});
    }
  };
});

Vue.filter('capitalize', function (value) {
  if (!value) {
    return '';
  }
  value = value.toString();
  return value.charAt(0).toUpperCase() + value.slice(1)
});

new Vue({
  router,
  render: h => h(App),
}).$mount('#app');
