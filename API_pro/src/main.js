import Vue from 'vue'
import VueRouter from 'vue-router';
import App from './App.vue'
import Connexion from './Components/Connexion.vue'
import Inscription from './Components/Inscription.vue'

Vue.use(VueRouter);

const routes = [
 
  {path:'/', component: Connexion},

  {path:'/inscription', component: Inscription},

]

const router = new VueRouter({
 
  routes: routes,

  mode:'history'

})

// declaration d'un composant global
Vue.component("app-connexion", Connexion);

new Vue({
  el: '#app',
  router:router,
  render: h => h(App)
})
