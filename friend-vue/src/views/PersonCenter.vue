<template>
  <div style="display: flex;" class="container-height">
    <div style="width: 240px; padding: 20px" class="box">
      <ul>
        <li @click="changePagePath('myInfo')" :class="pagePath === 'myInfo' ? 'menu-active' : ''"><el-icon class="menu-icon"><User /></el-icon>个人资料</li>
        <li><el-icon class="menu-icon"><Lock /></el-icon>修改密码</li>
        <li><el-icon class="menu-icon"><Message /></el-icon>消息提醒</li>
        <li @click="changePagePath('myDynamic')" :class="pagePath === 'myDynamic' ? 'menu-active' : ''"><el-icon class="menu-icon"><Histogram /></el-icon>我的动态</li>
      </ul>
    </div>

    <div style="flex: 1; margin-left: 20px; padding: 30px 100px" class="box">
      <MyInfo v-if="pagePath === 'myInfo'" />
      <MyDynamic v-if="pagePath === 'myDynamic'" />
    </div>
  </div>
</template>

<script setup>
import { User,Message,Histogram, Lock } from '@element-plus/icons-vue'
import MyInfo from "@/components/MyInfo.vue";
import MyDynamic from "@/components/MyDynamic.vue";
import { useRoute } from "vue-router";
import router from "@/router";
import {inject} from "vue";

const reload = inject('reload')

const route = useRoute()
const pagePath = route.query.page

const changePagePath = (pagePath) => {
  router.push({ query: {page: pagePath} })  // 触发页面参数的更改
  route.query.page = pagePath     // 触发菜单的高亮和页面你内容的更改
  reload()    // 重新渲染页面
}


</script>

<style scoped>
.box {
  background-color: white;
  border-radius: 10px;
}
li {
  text-align: center;
  margin: 15px 0;
  cursor: pointer;
  font-size: 16px;
}

.menu-icon {
  margin-right: 5px;
  top: 2px
}
.menu-active {
  color: dodgerblue;
}
</style>
