<template>
  <el-card :body-style="{ padding: '8px 18px' }">
    <div slot="header" class="me-category-header">
      <span>最新文章</span>
    </div>

    <ul class="me-category-list">
      <!-- 这里假设你想要渲染3个列表项，每个列表项包含一个链接 -->
      <li class="me-category-item" v-for="n in 6" :key="n">
        <a>列表项 {{ n }}</a> <!-- 假设你想要显示列表项编号 -->
      </li>
    </ul>
  </el-card>
</template>

<script setup>
// 如果组件中没有数据和方法，则不需要引入任何Composition API的函数
import {useRoute, useRouter} from "vue-router";
import {useStore} from "vuex";

const store = useStore()
const router = useRouter()
const route = useRoute()
</script>

<style scoped>
.me-category-header {
  font-weight: 600;
}

.me-category-list {
  list-style-type: none;
}

.me-category-item {
  padding: 4px;
  font-size: 14px;
  color: #5FB878;
}

.me-category-item a:hover {
  text-decoration: underline;
}
</style>