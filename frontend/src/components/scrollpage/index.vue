<template>
  <div ref="scroll" id="scroll-page" class="me-scroll-page">
    <slot></slot>
<!--    <div-->
<!--        style="height: 40px;margin-top: 10px;z-index: 1"-->
<!--        v-loading="loading"-->
<!--        element-loading-text="拼命加载中"-->
<!--        element-loading-spinner="el-icon-loading"-->
<!--        element-loading-background="rgba(245,245,245)">-->
<!--    </div>-->
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted, defineProps, defineEmits } from 'vue';

// 使用defineProps定义props
const props = defineProps({
  loading: Boolean,
  noData: Boolean,
  offset: Number
});

// 创建响应式的滚动行为状态
const scrollAction = ref({
  x: 'undefined',
  y: 'undefined'
});

// 创建响应式的DOM引用

// 在组件挂载时获取DOM元素引用
onMounted(() => {
  window.addEventListener('scroll', handleScroll, false);
});

onUnmounted(() => {
  window.removeEventListener('scroll', handleScroll);
});

// 定义组件的方法
const emit = defineEmits(['load']);
const handleScroll = (e) => {
  if (!props.noData) {
    let curHeight = document.documentElement.scrollTop || document.body.scrollTop;
    let scrollPage = document.getElementById('scroll-page');

    if ((curHeight + window.innerHeight >= scrollPage.offsetHeight + props.offset) && isDownDirection()) {
      if (!props.loading) {
        console.log("I am downDirection!!!")
        emit('load');
      }
    }
  }
};

const isDownDirection = () => {
  if (scrollAction.value.x === 'undefined') {
    scrollAction.value.x = window.pageXOffset;
    scrollAction.value.y = window.pageYOffset;
  }
  let diffX = scrollAction.value.x - window.pageXOffset;
  let diffY = scrollAction.value.y - window.pageYOffset;

  scrollAction.value.x = window.pageXOffset;
  scrollAction.value.y = window.pageYOffset;

  if (diffX < 0) {
    // Scroll right
  } else if (diffX > 0) {
    // Scroll left
  } else if (diffY < 0) {
    // Scroll down
    return true
  } else if (diffY > 0) {
    // Scroll up
  } else {
    // First scroll event
  }
  return false
};
</script>

<style scoped>
</style>