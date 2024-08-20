<template>
  <div v-title data-title="博客系统">
    <el-container>
      <el-aside>
        <base-header :activeIndex="activeIndex"></base-header>
      </el-aside>
      <el-main style="height: 80vh;padding-right: 15vw">
        <message-scroll-page></message-scroll-page>
      </el-main>
    </el-container>
  </div>
</template>

<script setup>
import {defineComponent} from 'vue';
import MessageScrollPage from '@/views/common/MessageScrollPage.vue';
import BaseHeader from "@/views/BaseHeader.vue";

// 导出组件

</script>

<style scoped>


.el-aside {
  margin-left: 0px;
  width: 260px;
}


.el-container {
  margin-left: 15vw;
  width: 85vw;
}

.el-main {
  padding: 0px;
  line-height: 16px;
  margin-left: 20vh;
  margin-right: 30px;
}


.el-card {
  border-radius: 20px;
}

.el-card:not(:first-child) {
  margin-top: 20px;
}
</style>
