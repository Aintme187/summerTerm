<template>
  <scroll-page :loading="loading" :offset="offset" :no-data="noData" @load="load">
    <article-item v-for="a in articles" :key="a.id" v-bind="a"></article-item>
  </scroll-page>
</template>

<script setup>
import { ref, watch, onMounted } from 'vue';
import ArticleItem from '@/components/article/ArticleItem.vue';
import ScrollPage from '@/components/scrollpage/index.vue';
import { getArticlesApi } from '@/api/article.js';
import {ElMessage} from "element-plus";

// 使用 defineProps 定义 props
const props = defineProps({
  offset: {
    type: Number,
    default: 100
  },
  page: {
    type: Object,
    default: () => ({})
  },
  query: {
    type: Object,
    default: () => ({})
  }
});

// 创建响应式数据
const loading = ref(false);
const noData = ref(false);
const innerPage = ref({
  pageSize: 100000,
  pageNumber: 1,
  name: 'a.createDate',
  sort: 'desc'
});
const articles = ref([]);

// 观察 props 的变化
watch(() => props.query, (newVal) => {
  noData.value = false;
  articles.value = [];
  innerPage.value.pageNumber = 1;
  getArticles();
}, {
  deep: true
});

watch(() => props.page, (newVal) => {
  noData.value = false;
  articles.value = [];
  innerPage.value = props.page;
  getArticles();
}, {
  deep: true
});

// 使用 onMounted 代替 created 钩子
onMounted(() => {
  getArticles();
});

// 定义组件的方法
const load = () => {
  getArticles();
};
const newArticles = ref([])
const getArticles = () => {
  loading.value = true;

  getArticlesApi(props.query, innerPage.value).then(data => {
    newArticles.value = data.data;

    if (newArticles.value && newArticles.value.length > 0) {
      innerPage.value.pageNumber += 1;

      articles.value = articles.value.concat(newArticles.value);
      console.log("these are articles" + articles.value)
    } else {
      noData.value = true;
    }
  }).catch(error => {
    if (error !== 'error') {
      // 假设这是你的全局消息组件
      ElMessage({ type: 'error', message: '文章加载失败!', showClose: true });
    }
  }).finally(() => {
    loading.value = false;
  });
};
</script>

<style scoped>
.el-card {
  border-radius: 20px;
}

.el-card:not(:first-child) {
  margin-top: 10px;
}
</style>
