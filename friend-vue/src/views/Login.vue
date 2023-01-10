<template>
  <div
    style="height: 100vh;overflow: hidden; position: relative; background-image: linear-gradient(to top, #30cfd0 0%, #330867 100%);">
    <div class="form-box">
      <el-form ref="ruleFormRef" :rules="rules" status-icon :model="form">
        <h2 style="text-align: center; color: dodgerblue">登 录</h2>
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
          <el-button link style="float: left" @click="handleResetPassword">忘记密码</el-button>
          <el-button type="primary" link @click="router.push('/register')" style="float: right">没有账号？请注册</el-button>
        </div>
      </el-form>
    </div>
    <el-dialog v-model="passwordVis" title="忘记密码" :close-on-click-modal="false" style="width: 500px; padding: 0 20px">
      <el-form :model="passwordForm" ref="rulePasswordFormRef" :rules="passwordRules" status-icon label-width="70px">
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="passwordForm.email" autocomplete="off" />
        </el-form-item>
        <el-form-item label="验证码" prop="emailCode">
          <div style="display: flex; width: 100%">
            <el-input style="flex: 1" v-model="passwordForm.emailCode" clearable></el-input>
            <el-button style="width: 120px; margin-left: 5px" @click="sendEmail" :disabled="time > 0">点击发送<span v-if="time">（{{ time }}）</span></el-button>
          </div>
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="passwordVis = false">取消</el-button>
        <el-button type="primary" @click="resetPassword">确认</el-button>
      </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import {nextTick, reactive, ref} from "vue"
import { User, Lock } from '@element-plus/icons-vue'
import router from "../router";
import request from "../utils/request";
import {ElMessage} from "element-plus";
import { useUserStore} from "../stores/user";

const rules = reactive({
  username: [
    {required: true, message: '请输入账号', trigger: 'blur'}
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur'},
  ]
})

const passwordRules = reactive({
  email: [
    { required: true, message: '请输入账号', trigger: 'blur'},
  ],
  emailCode: [
    { required: true, message: '请输入验证码', trigger: 'blur'},
  ],
})


const form = reactive({})
const passwordForm = reactive({})
const ruleFormRef = ref()
const passwordVis = ref(false)
const rulePasswordFormRef = ref()
const store = useUserStore()
const interval = ref(-1)
const time = ref(0)

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

const login = ()=> {
  console.log(ruleFormRef)
  ruleFormRef.value.validate(valid => {
    if (valid) {
      request.post("/login",form).then(res => {
        console.log(res)
        if (res.code === '200') {
          store.setLoginInfo(res.data)
          ElMessage.success("登陆成功");
          router.push("/");
        } else {
          ElMessage.error(res.msg);
        }
      })
    }
  })
}

// 发送邮件
const sendEmail = () => {
  const reg = /^\w+((.\w+)|(-\w+))@[A-Za-z0-9]+((.|-)[A-Za-z0-9]+).[A-Za-z0-9]+$/
  if(!reg.test(passwordForm.email)) {  // test可以校验你的输入值
    ElMessage.warning("请输入合法的邮箱")
    return
  }

  request.get("/email", {
    params: {
      email: passwordForm.email,
      type: "RESETPASSWORD"
    }
  }).then(res => {
    if (res.code === '200') {
      times()  // 倒计时
      ElMessage.success('发送成功，有效期5分钟')
    } else {
      ElMessage.error(res.msg)
    }
  })
}


// 点击忘记密码触发
const handleResetPassword = () => {
  passwordVis.value = true
  // 触发表单重置
  nextTick(() => {   // 下个时钟触发
    rulePasswordFormRef.value.resetFields()
  })
}

// 调用新接口重置密码
const resetPassword = () => {
  rulePasswordFormRef.value.validate(valid => {
    if (valid) {
      request.post("/password/reset", passwordForm).then(res => {
        if (res.code === '200') {
          ElMessage.success('重置成功，您的密码为：' + res.data)
          passwordVis.value = false
        } else {
          ElMessage.error(res.msg)
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
