import {createRouter, createWebHistory} from 'vue-router'
import {useUserStore} from "../stores/user";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: () => import('../views/HomeView.vue')
    },
    {
      path: '/login',
      name: 'Login',
      component: () => import('../views/Login.vue')
    },
    {
      path: '/register',
      name: 'Register',
      component: () => import('../views/Register.vue')
    },{
      path: '/404',
      name: '404',
      component: () => import('../views/404.vue')
    }
  ]
})

// 路由守卫
router.beforeEach((to, from, next) => {
  const store = useUserStore();
  const user = store.user
  const hasUser = user && user.id;
  const noPermissionPaths = ['/login', '/register']
  if (!hasUser && !noPermissionPaths.includes(to.path)) {
      next("/login")
  } else {
    next()
  }
})

export default router
