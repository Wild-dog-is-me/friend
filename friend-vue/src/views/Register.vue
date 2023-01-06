<template>
  <div
    style="height: 100vh;overflow: hidden; position: relative; background-image: linear-gradient(to top, #d9afd9 0%, #97d9e1 100%);">
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
        <el-form-item prop="confirm" >
          <el-input v-model="form.confirm" show-password  placeholder="请确认密码" :prefix-icon="Lock"
                    autocomplete="new-password"></el-input>
        </el-form-item>
        <el-form-item prop="name">
          <el-input v-model="form.name" show-password  placeholder="请设置昵称" :prefix-icon="UserFilled"
                    ></el-input>
        </el-form-item>
        <div style="margin-bottom: 0.83em">
          <el-button style="width: 100%" type="primary" @click="register">注册</el-button>
        </div>
        <div style="text-align: right">
          <el-button type="primary" link @click="router.push('/login')" style="float: right">已有账号？请登陆</el-button>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import {reactive, ref} from "vue";
import { User, Lock,UserFilled } from '@element-plus/icons-vue'
import router from "../router";
import request from "../utils/request";
import {ElMessage} from "element-plus";
import { useUserStore} from "../stores/user";
const form = reactive({})
const ruleFormRef = ref()

const confirmPassword = (rule,value,callback) => {
  if (value == '') {
    callback(new Error("请确认密码"))
  }
  if (form.password !== value) {
    callback(new Error("两次输入的密码不一致"))
  }
  callback()
}
const rules = reactive({
  username: [
    {required: true, message: '请输入账号', trigger: 'blur'}
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur'},
  ],
  confirm: [
    {validator: confirmPassword, trigger: 'blur'},
  ],
  name: [
    {required: true, message: '请设置昵称', trigger: 'blur'}
  ]
})



const store = useUserStore()
const register = ()=> {
  console.log(ruleFormRef)
  ruleFormRef.value.validate(valid => {
    if (valid) {
      request.post("/register",form).then(res => {
        console.log(form)
        console.log(res)
        if (res.code === '200') {
          store.setUser(res.data)
          ElMessage.success("注册成功");
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
  background-image: linear-gradient(to top, #0c3483 0%, #a2b6df 100%, #6b8cce 100%, #a2b6df 100%);
  padding: 50px;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translateX(-50%) translateY(-50%);
}
</style>
