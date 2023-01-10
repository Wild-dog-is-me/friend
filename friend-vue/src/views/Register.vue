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
        <el-form-item prop="email" >
          <el-input v-model="form.email" placeholder="请输入邮箱" :prefix-icon="Message"
                    ></el-input>
        </el-form-item>
        <el-form-item prop="emailCode">
          <div style="display: flex">
            <el-input style="flex: 1" v-model="form.emailCode" :prefix-icon="ChatLineRound"></el-input>
            <el-button style="width: 120px; margin-left: 15px" @click="sendEmail" :disabled="time > 0">发送验证码<span v-if="time" type="primary" plain>（{{ time }}）</span></el-button>
          </div>
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
import { User, Lock,UserFilled,Message,ChatLineRound } from '@element-plus/icons-vue'
import router from "../router";
import request from "../utils/request";
import {ElMessage} from "element-plus";
import { useUserStore} from "../stores/user";

const form = reactive({})
const ruleFormRef = ref()
const time = ref(0)
const interval = ref(-1)
const reg = /^\w+((.\w+)|(-\w+))@[A-Za-z0-9]+((.|-)[A-Za-z0-9]+).[A-Za-z0-9]+$/

const times = () => {
  // 清空定时器
  if (interval.value >= 0) {
    clearInterval(interval.value)
  }
  time.value = 60
  interval.value = setInterval(() => {
    if (time.value > 0) {
      time.value --
    }
  }, 1000)
}

const sendEmail = () => {
  if (!reg.test(form.email)) {
    ElMessage.warning("请输入合法邮箱")
    return
  }
  request.get("/email",{
    params: {
      email: form.email,
      type: "REGISTER"
    }
  }).then(res => {
    times()
    if (res.code === '200') {
      ElMessage.success("发送成功,有效期五分钟");
    } else {
      ElMessage.error(res.msg);
    }
  })
}
const checkEmail = (rule, value, callback) => {
  if(!reg.test(value)) {  // test可以校验你的输入值
    return callback(new Error('邮箱格式不合法'));
  }
  callback()
}

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
  ],
  email: [
    { validator: checkEmail, trigger: 'blur'},
  ],
  emailCode: [
    {required: true, message: '请输入验证码', trigger: 'blur'}
  ],
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
          store.setLoginInfo(res.data)
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
