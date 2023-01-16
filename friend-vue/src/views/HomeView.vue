<script setup>
import {Compass, View, Pointer, ChatLineRound} from '@element-plus/icons-vue'
import request from "../utils/request";

const load = () => {
  request.get('/user').then(res => {
    console.log(res.data)
  })
}

load()
</script>

<template>
  <div style="background-color: white; border-radius: 10px; " class="container-height">
    <div style="display: flex; padding: 10px">
      <!--      话题动态-->
      <div class="dynamic-box" style="width: 60%; padding: 20px; border: 1px solid #ccc">
        <div style="display:flex;">
          <img style="width: 50px; height: 50px; margin-right: 20px; border-radius: 50%"
               src="https://pic.imgdb.cn/item/63bd7fd1be43e0d30e49cd4d.jpg"
               alt="">
          <div style="flex: 1; line-height: 25px">
            <div style="font-weight: bold">呵呵笋的小煮艺</div>
            <div style="font-size: 12px; color: #999">18分钟前 · 来自Redmi K30S 至尊纪念版</div>
          </div>
          <el-button>关注</el-button>
        </div>

        <div style="" class="content">
          每年到了冬至时节，上海人除了会做“冬至团”还有一项工作就是腌咸肉酱油肉，西北风一吹，香得不得了。
          老上海人几乎家家户户都有一口大缸和一块大石头，拿来腌腌咸肉做做酱油肉啥的。
          腌肉多见于入冬春节前，从冬至开始到立春结束，也是春节年夜饭上最早开始制作的菜。因为冬季天气较冷，做出来的腌肉不易变质且可用于迎接新年、招待客人用，所以成为冬季乃至春节餐桌上的一道经典菜肴。
          今日冬至，笋笋腌了一缸酱油肉，经过腌晾的过程，只需半个月，一缸上好的酱油肉就完成啦，能吃上一年呦～
        </div>

        <div style="margin: 10px 0">
          <el-row :gutter="10">
            <el-col :span="12" style="margin-bottom: 10px">
              <img style="width: 100%;"
                   src="https://cdn.cnbj1.fds.api.mi-img.com/middle.community.vip.bkt/15ee288e6a797869692e596182113345#vipAccount-img"
                   alt="">
            </el-col>
            <el-col :span="12">
              <img style="width: 100%;"
                   src="https://cdn.cnbj1.fds.api.mi-img.com/middle.community.vip.bkt/15ee288e6a797869692e596182113345#vipAccount-img"
                   alt="">
            </el-col>

            <el-col :span="12">
              <img style="width: 100%;"
                   src="https://cdn.cnbj1.fds.api.mi-img.com/middle.community.vip.bkt/15ee288e6a797869692e596182113345#vipAccount-img"
                   alt="">
            </el-col>
            <el-col :span="12">
              <img style="width: 100%;"
                   src="https://cdn.cnbj1.fds.api.mi-img.com/middle.community.vip.bkt/15ee288e6a797869692e596182113345#vipAccount-img"
                   alt="">
            </el-col>
          </el-row>
        </div>

        <div style="margin: 10px 0; display: flex; line-height: 25px">
          <div style="width: 50%">
            <el-tag type="primary"># 冬至到了</el-tag>
            <el-tag type="danger" style="margin-left: 10px">
              <el-icon style="top: 1px">
                <Compass/>
              </el-icon>
              米粉杂谈
            </el-tag>
          </div>
          <div style="width: 50%; text-align: right; color: #999; font-size: 14px;">
            <el-icon size="20" style="top: 5px">
              <View/>
            </el-icon>
            20
            <el-icon size="20" style="margin-left: 10px; top: 5px">
              <Pointer/>
            </el-icon>
            10
            <el-icon size="20" style="margin-left: 10px; top: 5px">
              <ChatLineRound/>
            </el-icon>
            30
          </div>
        </div>

      </div>

      <!-- 排行榜-->
      <div style="width: 40%; height: 100px; padding: 10px; border: 1px solid #ccc; margin-left: 10px">

      </div>

    </div>
  </div>
</template>

<style scoped>
/* 只显示2行文本，多余的用省略号代替 */
.content {
  margin: 10px 0;
  line-height: 25px;
  text-align: justify;
  word-break: break-all;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}
</style>
