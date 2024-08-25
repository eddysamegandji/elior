import { createApp } from 'vue'
import piniaPluginPersistedState from 'pinia-plugin-persistedstate'
import App from './App.vue'
import router from './router'
import vuetify from './plugins/vuetify'
import VueDatePicker from '@vuepic/vue-datepicker'
import '@vuepic/vue-datepicker/dist/main.css'
import '@/assets/main.scss'
import { createPinia } from 'pinia'
import '@mdi/font/css/materialdesignicons.css'
import 'bootstrap-icons/font/bootstrap-icons.css'

const pinia = createPinia().use(piniaPluginPersistedState)

createApp(App)
  .use(router)
  .use(vuetify)
  .use(pinia)
  .component('VueDatePicker', VueDatePicker)
  .mount('#app')
