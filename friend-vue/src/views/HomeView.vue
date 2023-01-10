<template>
  <div>
    <el-button type="primary" @click="user">确认</el-button>
  </div>
</template>
<script setup>
import request from "../utils/request";


const user = ()=> {
  request.get("/user/").then(res => {
    console.log(res.data)
  })
}
</script>
