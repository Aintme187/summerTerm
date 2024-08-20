<template>
  <!-- 使用v-show被替换为v-show，因为Vue 3中移除了v-show指令 -->
  <transition>
    <div v-show="topShow" class="me-to-top" @click="toTop">
      <i class="el-icon-caret-top"></i>
    </div>
  </transition>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue';

// 创建响应式状态
const topShow = ref(false);

// 定义组件的方法
const toTop = () => {
  document.body.scrollTop = 0;
  document.documentElement.scrollTop = 0;
  topShow.value = false;
};

const needToTop = () => {
  let curHeight = document.documentElement.scrollTop || document.body.scrollTop;
  topShow.value = curHeight > 400;
};

// 使用onMounted代替mounted钩子
onMounted(() => {
  window.addEventListener('scroll', needToTop);
});

// 使用onUnmounted移除事件监听器
onUnmounted(() => {
  window.removeEventListener('scroll', needToTop);
});
</script>


<style scoped>
  .me-to-top {
    background-color: #fff;
    position: fixed;
    right: 100px;
    bottom: 150px;
    width: 40px;
    height: 40px;
    border-radius: 20px;
    cursor: pointer;
    transition: .3s;
    box-shadow: 0 0 6px rgba(0, 0, 0, .12);
    z-index: 5;
  }

  .me-to-top i {
    color: #00d1b2;
    display: block;
    line-height: 40px;
    text-align: center;
    font-size: 18px;
  }

</style>
