<template>
  <div class="me-message" v-title :data-title="title">
    <el-container class="">
      <el-main class="me-main">
        <el-alert
            title="不能留言哦。。。。。。。。。。。"
            type="warning"
            center
            show-icon>
        </el-alert>
      </el-main>
    </el-container>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue';

const title = computed(() => '留言板 - 博客系统');
</script>

<style scoped>
.me-message {
}

.el-container {
  width: 700px;
}

.me-main {
  overflow: hidden;
}

.me-log-box {
  margin-left: 30%;
  margin-top: 20px;
}
</style>
