import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import PrimeVue from 'primevue/config';
createApp(App).use(store)
    .use(PrimeVue)
    .use(router).mount('#app')

//in main.js
import 'primevue/resources/themes/aura-light-green/theme.css'
