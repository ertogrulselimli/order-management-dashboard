import Vue from 'vue'

import Cookies from 'js-cookie'


import 'normalize.css/normalize.css' // a modern alternative to CSS resets


import Element from 'element-ui'
import VueGoodTablePlugin from 'vue-good-table';
import AlovDatatable from '@/components/DataTable'

// import the styles
import 'vue-good-table/dist/vue-good-table.css'
import './styles/element-variables.scss'
import enLang from 'element-ui/lib/locale/lang/en'

import '@/styles/index.scss' // global css

import App from './App'
import store from './store'
import router from './router'

import './icons' // icon
import './permission' // permission control
import './utils/error-log' // error log


//import directives
import  permission from '@/directive/permission/permission';
Vue.directive('permission',permission);

import * as filters from './filters' // global filters

Vue.use(VueGoodTablePlugin); //as plugin
Vue.component("alov-datatable",AlovDatatable);
Vue.use(Element, {
  size: Cookies.get('size') || 'medium', // set element-ui default size
  locale: enLang
})

// register global utility filters
Object.keys(filters).forEach(key => {
  Vue.filter(key, filters[key])
})





Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
