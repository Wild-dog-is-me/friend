import { createRouter, createWebHistory } from 'vue-router'
import {useUserStore} from "@/stores/user"

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/login',
      name: 'Login',
      component: () => import('../views/Login.vue')
    },
    {
      path: '/404',
      name: '404',
      component: () => import('../views/404.vue')
    }
  ]
})

// 注意：刷新页面会导致页面路由重置
export const setRoutes = () => {
  const manager = localStorage.getItem('manager')
  if (!manager) {
    return
  }
  const menus = JSON.parse(manager).managerInfo.menus
  if (menus.length) {
    // 拼装动态路由
    const layoutRoute = { path: '/', name: 'Layout', component: () => import('../layout/Layout.vue'), redirect: "/home",
      children: [
        { path: 'home', name: 'Home', component: () => import('../views/Home.vue') }
      ]
    }
    // 开始渲染 未来的不确定的  用户添加的路由
    menus.forEach(item => {   // 所有的页面都需要设置路由，而目录不需要设置路由
      if (item.path) {  // 当且仅当path不为空的时候才去设置路由
        let itemMenu = { path: item.path, name: item.page, component: () => import('../views/' + item.page + '.vue')}
        layoutRoute.children.push(itemMenu)
      } else {
        if (item.children && item.children.length) {
          item.children.forEach(sub => {
            if (sub.path) {
              let subMenu = { path: sub.path, name: sub.page, component: () => import('../views/' + sub.page + '.vue')}
              layoutRoute.children.push(subMenu)
            }
          })
        }
      }
    })

    // 获取当前的路由对象名称数组
    const currentRouteNames = router.getRoutes().map(v => v.name)
    if (!currentRouteNames.includes('Layout')) {
      // 动态添加到现在的路由对象中去
      router.addRoute(layoutRoute)
    }
  }
}

setRoutes()


// 路由守卫
router.beforeEach((to, from, next) => {
  const store = useUserStore()  // 拿到用户对象信息
  const user = store.managerInfo.user
  const hasUser = user && user.id
  const noPermissionPaths = ['/login', '/404']   // 定义无需登录的路由
  if (!hasUser && !noPermissionPaths.includes(to.path)) {  // 用户没登录,  假如你当前跳转login页面，然后login页面没有用户信息，这个时候你再去往 login页面跳转，就会发生无限循环跳转
    // 获取缓存的用户数据
    //  如果to.path === '/login' 的时候   !noPermissionPaths.includes(to.path) 是返回 false的，也就不会进 next("/login")
    next("/login")
  } else {
    if (!to.matched.length) {
      next('/404')
    } else {
      next()
    }
  }
})

export default router
