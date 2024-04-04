import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import SessionView from '../views/JoinSessionView.vue'
import CreateSessionView from '../views/CreateSessionView.vue'
import SessionResults from '@/views/SessionResults.vue'


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
  },
  {
    path: '/results/:id?',
    name: 'results',
    component: SessionResults
  }
  
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
