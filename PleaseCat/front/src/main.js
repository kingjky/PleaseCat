import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import vuetify from './plugins/vuetify'
import moment from 'moment'
 window.Vue = Vue;

Vue.config.productionTip = false

Vue.prototype.moment = moment

new Vue({
  router,
  store,
  beforeCreate(){
    this.$store.dispatch('checkToken');
  },
  vuetify,
  render: h => h(App)
}).$mount('#app')
