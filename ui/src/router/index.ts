import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'database',
      component: () => import('../views/DatabaseConfig.vue'),
    }
  ],
})

export default router
