<script setup>
import {
  Search,
  RefreshLeft,
  Plus
} from '@element-plus/icons-vue'
import {reactive, ref} from "vue";

const name = ref('')

const multipleSelection = ref([])
const handleSelectionChange = (val) => {
  multipleSelection.value = val
}

const state = reactive({
  tableData: [
    {name: "admin", address: "苏州市", phone: "132342352"},
    {name: "admin", address: "苏州市", phone: "132342352"},
    {name: "admin", address: "苏州市", phone: "132342352"}
  ]
})
</script>

<template>
  <div>
    <el-input v-model="name" placeholder="请输入姓名" class="w300 ml5"></el-input>
    <el-input v-model="name" placeholder="请输入姓名" class="w300 ml5"></el-input>
    <el-button type="warning" class="ml5">
      <el-icon style="vertical-align: middle">
        <RefreshLeft/>
      </el-icon>
      <span style="vertical-align: middle"> 重置 </span>
    </el-button>
    <el-button class="ml5" type="primary">
      <el-icon style="vertical-align: middle">
        <Search/>
      </el-icon>
      <span style="vertical-align: middle"> 搜索 </span>
    </el-button>
    <el-button class="ml5" type="success">
      <el-icon style="vertical-align: middle">
        <Plus/>
      </el-icon>
      <span style="vertical-align: middle"> 新增 </span>
    </el-button>
  </div>
  <div style="margin: 10px 0">
    <el-table :data="state.tableData" stripe border @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"/>
      <el-table-column prop="name" label="姓名"></el-table-column>
      <el-table-column prop="address" label="地址"></el-table-column>
      <el-table-column prop="phone" label="联系方式"></el-table-column>
      <el-table-column label="操作">
        <template #default>
          <el-button type="primary">编辑</el-button>
          <el-button type="danger">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
  <div style="margin: 10px 0">
    <el-pagination
      small
      background
      layout="total,prev, pager, next,sizes,jumper"
      :total="50"
    />
  </div>
</template>
