<template>
  <el-card>
    <h1 class="me-author-name">博客系统</h1>
    <div class="me-author-description">
      <el-icon><Platform /></el-icon>
      <span><i class="el-icon-location-outline"></i> &nbsp;BUAA</span>
    </div>
    <div class="me-author-tool">

      <i @click="showTool(socials.qq)" :title="socials.qq.title" class="iconfont icon-qq"></i>
      <i @click="showTool(socials.bilibili)" :title="socials.bilibili.title" class="iconfont icon-bilibili-fill"></i>
    </div>
  </el-card>
</template>

<script setup>
import { ref } from 'vue';
import { ElMessage } from 'element-plus';
import {useRoute, useRouter} from "vue-router";
import {useStore} from "vuex";
import {Edit, Platform} from "@element-plus/icons-vue";
const store = useStore()
const router = useRouter()
const route = useRoute()
// 使用ref创建响应式数据
const socials = ref({
  qq: { title: 'QQ', message: '723663826' },
  bilibili: { title: '哔哩哔哩', message: '<a target="_blank" href="https://space.bilibili.com/473844125">https://space.bilibili.com/473844125</a>' }
});

// 定义一个方法来显示工具提示
const showTool = (tool) => {
  ElMessage({
    duration: 0,
    showClose: true,
    dangerouslyUseHTMLString: true,
    message: `<strong>${tool.message}</strong>`
  });
};
</script>

<style scoped>
.me-author-name {
  text-align: center;
  font-size: 30px;
  border-bottom: 1px solid #5FB878;
}

.me-author-description {
  margin-left:70px;
  padding: 8px 0;
}

.me-icon-job {
  padding-left: 16px;
}

.me-author-tool {
  text-align: center;
  padding-top: 10px;
}

.me-author-tool i {
  cursor: pointer;
  padding: 4px 10px;
  font-size: 30px;
}
</style>