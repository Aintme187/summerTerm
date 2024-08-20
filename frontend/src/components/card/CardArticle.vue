<template>
  <el-card :body-style="{ padding: '8px 18px' }" class="me-el-card">
    <div slot="header" class="me-category-header">
      <span>{{cardHeader}}</span>
    </div>

    <ul class="me-category-list">
      <li v-for="a in articles" :key="a.id" class="me-category-item" @click="view(a.id)" :style="itemStyle">
        <a>{{a.title}}</a>
      </li>
    </ul>
  </el-card>
</template>

<script setup>
import { defineProps, ref } from 'vue';
import {useRoute, useRouter} from "vue-router";
import {useStore} from "vuex";
const store = useStore()
const router = useRouter()
const route = useRoute()
// 定义props
const props = defineProps({
  cardHeader: {
    type: String,
    required: true
  },
  articles: {
    type: Array,
    required: true
  },
  itemStyle: Object
});

// 定义一个方法来响应点击事件
const view = (id) => {
  // 假设有一个router对象可用，并且已经定义了路由
  router.push({ path: `/blog/view/${id}` });
};
</script>

<style scoped>
.me-el-card {
  /* 你的样式 */
}
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