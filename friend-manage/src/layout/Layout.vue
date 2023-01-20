<script setup>
import { RouterView } from 'vue-router'
import router from "@/router";
import {
  Document,
  Menu as IconMenu,
  Location,
  Setting
} from '@element-plus/icons-vue'
import {useUserStore} from "@/stores/user";
import request from "@/utils/request";
import {ElMessage} from "element-plus";
const userStore = useUserStore()
const user = userStore.getUser

const logout = () => {
  request.get('/logout/' + user.uid).then(res => {
    if (res.code === '200') {
      // this.$router.push(".")
      userStore.logout()
    } else {
      ElMessage.error(res.msg)
    }
  })
}
</script>

<template>
  <div>
    <div style="height: 60px; line-height: 60px; border-bottom: 1px solid #ccc; background-color: aliceblue">
      <div style="display: flex">
        <div style="width: 200px; color: dodgerblue; font-weight: bold;  text-align: center; font-size: 20px">
          后台管理
        </div>

        <div style="flex: 1; display: flex">
          <div style="flex: 1">
          </div>
          <div style="width: 200px; text-align: right; padding-right: 20px">
            <el-dropdown>
              <el-avatar :size="40" :src="user.avatar" style="margin-top: 10px" />
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item><div @click="router.push('/person')">个人信息</div></el-dropdown-item>
                  <el-dropdown-item><div @click="logout">退出登录</div></el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>

          </div>
        </div>
      </div>
    </div>

    <div style="display: flex">
      <div style="width: 200px; min-height: calc(100vh - 60px); border-right: 1px solid #ccc">
        <el-menu
            router
            default-active="/"
            class="el-menu-vertical-demo"
        >
          <el-sub-menu index="1">
            <template #title>
              <el-icon><location /></el-icon>
              <span>Navigator One</span>
            </template>
            <el-menu-item index="/user">用户</el-menu-item>
            <el-menu-item index="/role">角色</el-menu-item>
            <el-menu-item index="/permission">权限</el-menu-item>
          </el-sub-menu>
          <el-menu-item index="2">
            <el-icon><icon-menu /></el-icon>
            <span>Navigator Two</span>
          </el-menu-item>
          <el-menu-item index="3" disabled>
            <el-icon><document /></el-icon>
            <span>Navigator Three</span>
          </el-menu-item>
          <el-menu-item index="4">
            <el-icon><setting /></el-icon>
            <span>Navigator Four</span>
          </el-menu-item>
        </el-menu>
      </div>

      <div style="flex: 1; padding: 10px">
        <RouterView />
      </div>
    </div>

  </div>
</template>
