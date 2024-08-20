<template>
  <div v-title data-title="消息中心">

    <el-container class="me-message-container">

      <el-main class="me-message">
        <message-scroll-page></message-scroll-page>
      </el-main>
      <el-aside>
      </el-aside>
    </el-container>
  </div>
</template>

<script setup>
import {ref} from 'vue';
import MessageScrollPage from '@/views/common/MessageScrollPage';

// 如果需要响应式状态，可以使用ref或reactive创建
// 示例：const myRef = ref(0);

// 如果需要使用路由或其他Composition API，可以在这里引入
// 示例：import { useRoute, useRouter } from 'vue-router';

// 定义组件
const components = {
  MessageScrollPage,
};

// 如果有需要响应式数据或方法，可以在这里定义
// 示例：function incrementCount() { myRef.value++; }
</script>

<style scoped>
/* 样式保持不变 */
.me-message-container{
  position: fixed;
  left: 100px;
}

.me-message{
  position: absolute;
  left: 100px;
}
.el-container {
  width: 960px;
}

.el-aside {
  margin-left: -20px;
  width: 260px;
}

.el-main {
  padding: 0px;
  line-height: 16px;
}

.el-card {
  border-radius: 10px;
}

.el-card:not(:first-child) {
  margin-top: 20px;
}
</style>