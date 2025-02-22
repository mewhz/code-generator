import { createRouter, createWebHistory } from 'vue-router'
import DatabaseConfig from '@/views/DatabaseConfig.vue'
import GeneratorConfig from '@/views/GeneratorConfig.vue'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/',
      name: 'home',
      component: DatabaseConfig
    },
    {
      path: '/settings',
      name: 'settings',
      component: GeneratorConfig
    }
  ]
})

export default router
