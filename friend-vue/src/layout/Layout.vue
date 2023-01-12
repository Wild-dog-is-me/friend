<template>
  <div>
    <!-- 头部 -->
    <div style="display:flex; height: 60px; background-color: white; line-height: 60px; border-bottom: 1px solid #ddd">
      <div style="width: 200px; display: flex">
        <div style="width: 100px; padding-left: 30px">
          <img src="../assets/imgs/friend.png" alt="" style="width: 60px; display: inline">
        </div>
        <div style="flex: 1; color: SlateBlue; font-size: 16px"><b>Odin交友</b></div>
      </div> <!-- logo -->

      <div style="flex: 1; padding-left: 50px">
        <el-menu
          :default-active="$route.path"
          class="el-menu-demo"
          mode="horizontal"
          router
        >
          <el-menu-item index="/home">首页</el-menu-item>
          <el-sub-menu index="2">
            <template #title>Workspace</template>
            <el-menu-item index="2-1">item one</el-menu-item>
          </el-sub-menu>
        </el-menu>
      </div> <!-- 菜单区域 -->

      <div style="width: 200px; text-align: right; padding-right: 10px">

        <el-dropdown>
          <div class="el-dropdown-link" style="line-height: 60px">
            <el-avatar :size="40" :src="store.loginInfo.user.avatar" style="position: relative; top: 10px; right: 5px" />
            <!--            <span style="font-size: 14px; margin-left: 45px">{{ store.loginInfo.user.name }}</span>-->
            <!--            <el-icon class="el-icon&#45;&#45;right">-->
            <!--              <arrow-down />-->
            <!--            </el-icon>-->
          </div>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item>
                <div @click="router.push('/personCenter')">个人中心</div>
              </el-dropdown-item>
              <el-dropdown-item>
                <div @click="logout">退出登录</div>
              </el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div> <!-- 头像和下拉菜单 -->

    </div>

    <!--  主体 -->
    <div style="margin: 10px auto 0 auto; width: 60%;">
      <router-view />  <!--  加载子路由的视图 -->
    </div>

  </div>
</template>

<script setup>
import {useUserStore} from "@/stores/user";
import router from "@/router";
import {ArrowDown} from '@element-plus/icons-vue'

const store = useUserStore()

const logout = () => {
  localStorage.removeItem("user")
  router.push('/login')
}
</script>

<style scoped>
.el-dropdown-link {
  cursor: pointer;
}
</style>
