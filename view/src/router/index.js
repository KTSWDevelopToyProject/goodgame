import {createRouter, createWebHistory} from 'vue-router'
import GameView from '../views/GameView.vue'
import GameWaitingView from "@/views/GameWaitingView.vue";

const routes = [
  {
    path: `/view`,
    name: `root`,
    redirect: GameWaitingView,
  },
  {
    path: `/view/game-waiting`,
    name: `gameWaiting`,
    component: GameWaitingView,
  },
  {
    path: '/gameview',
    name: 'gameview',
    component: GameView
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
