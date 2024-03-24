import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import SessionView from '../views/JoinSessionView.vue'
import CreateSessionView from '../views/CreateSessionView.vue'


const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/join/:id?',
    name: 'session',
    component: SessionView
  },
  {
    path: '/create',
    name: 'create',
    component: CreateSessionView
  }
  
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
