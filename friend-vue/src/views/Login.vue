<template>
  <div
    style="height: 100vh;overflow: hidden; position: relative; background-image: linear-gradient(to top, #30cfd0 0%, #330867 100%);">
    <div class="form-box">
      <el-form ref="ruleFormRef" :rules="rules" status-icon :model="form">
        <h2 style="text-align: center;">后台登录</h2>
        <el-form-item prop="username">
          <el-input v-model="form.username" placeholder="请输入账号" :prefix-icon="User"></el-input>
        </el-form-item>
        <el-form-item prop="password" >
          <el-input v-model="form.password" show-password  placeholder="请输入密码" :prefix-icon="Lock"
                    autocomplete="new-password"></el-input>
        </el-form-item>
        <div style="margin-bottom: 0.83em">
          <el-button style="width: 100%" type="primary" @click="login">登录</el-button>
        </div>
        <div style="text-align: right">
          <el-button type="primary" link @click="router.push('/register')" style="float: right">没有账号？请注册</el-button>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import {reactive, ref} from "vue";
import { User, Lock } from '@element-plus/icons-vue'
import router from "../router";
import request from "../utils/request";
import {ElMessage} from "element-plus";
const form = reactive({})
const ruleFormRef = ref()
const rules = reactive({
  username: [
    {required: true, message: '请输入账号', trigger: 'blur'}
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur'},
  ]
})
const login = ()=> {
  ruleFormRef.value.validate(valid => {
    if (valid) {
      request.post("/login",form).then(res => {
        console.log(res)
        if (res.code == 200) {
          ElMessage.success("登陆成功");
          router.push("/");
        } else {
          ElMessage.error(res.msg);
        }
      })
    }
  })
}
</script>

<style scoped>
.form-box {
  width: 400px;
  border-radius: 10px;
  margin: 0 auto;
  box-shadow: 0 0 5px -2px rgba(0, 0, 0, .5);
  background-image: linear-gradient(120deg, #a1c4fd 0%, #c2e9fb 100%);
  padding: 50px;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translateX(-50%) translateY(-50%);
}
</style>
