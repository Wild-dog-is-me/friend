import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: () => import('../layout/Layout.vue'),
      redirect: "/home",
      children: [
        {
          path: 'home',
          name: 'Home',
          component : () => import('../views/HomeView.vue'),
        }
      ]
    },
    {
      path: '/login',
      name: "Login",
      component : () => import('../views/Login.vue'),
    }
  ]
})

export default router
