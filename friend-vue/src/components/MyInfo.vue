<template>
  <div>
    <el-form :model="state.user" label-width="80px">
      <el-form-item label="">
        <el-upload
            class="avatar-uploader"
            :action="url"
            :headers="state.headers"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
        >
          <img v-if="state.user.avatar" :src="state.user.avatar" class="avatar" />
          <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
        </el-upload>
      </el-form-item>
      <el-form-item label="用户名">
        <el-input v-model="state.user.username" disabled />
      </el-form-item>
      <el-form-item label="姓名">
        <el-input v-model="state.user.name"/>
      </el-form-item>
      <el-form-item label="邮箱">
        <el-input v-model="state.user.email"/>
      </el-form-item>
      <el-form-item label="">
        <el-button type="primary" @click="save">保 存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup>
import { Plus } from '@element-plus/icons-vue'
import {reactive, ref} from "vue";
import config from "../config";
import {useUserStore} from "../stores/user";
import request from "../utils/request";
import {ElMessage} from "element-plus";

const url = ref('http://' + config.serverUrl + "/file/upload")
const store = useUserStore()
let state = reactive({
  user: {},
  headers: {
    Authorization: store.getBearerToken
  }
})

const userId = store.getUserId
const loadUser = () => {
  request.get('/user/' + userId).then(res => {
    state.user = res.data
  })
}
loadUser()

const handleAvatarSuccess = (res) => {
  if (res.code === '200') {
    state.user.avatar = res.data + "?loginId=" + store.getUser.uid  + "&token=" + store.getToken
  } else {
    ElMessage.error('上传失败')
  }
}

const save = () => {
  request.put('/user', state.user).then(res => {
    if (res.code === '200') {
      ElMessage.success('保存成功')
      store.setUser(state.user)
    } else {
      ElMessage.error(res.msg)
    }
  })
}
</script>

<style scoped>
.avatar {
  width: 100px;
  height: 100px;
}
</style>
