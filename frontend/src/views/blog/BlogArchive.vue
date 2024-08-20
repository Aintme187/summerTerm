<template>
  <div v-title :data-title="title">
    <el-container>

      <el-aside class="me-area">
        <ul class="me-month-list">
          <li v-for="a in archives" :key="a.year + a.month" class="me-month-item">
            <el-badge :value="a.count">
              <el-button @click="changeArchive(a.year, a.month)" size="small">{{a.year +'年' + a.month + '月'}}
              </el-button>
            </el-badge>
          </li>
        </ul>

      </el-aside>


      <el-main class="me-articles">
        <div class="me-month-title">{{currentArchive}}</div>

        <article-scroll-page v-bind="article"></article-scroll-page>

      </el-main>
    </el-container>
  </div>

</template>

<script setup>
import { ref, reactive, onMounted, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import ArticleScrollPage from '@/views/common/ArticleScrollPage';
import { ElMessage } from 'element-plus';

// 创建响应式状态
const article = reactive({
  query: {
    month: useRoute().params.month,
    year: useRoute().params.year
  }
});
const archives = ref([]);

// 计算属性
const title = computed(() => currentArchive.value + ' - 文章归档 - 博客系统');
const currentArchive = computed(() => {
  if (article.query.year && article.query.month) {
    return `${article.query.year}年${article.query.month}月`;
  }
  return '全部';
});

// 路由变化监听
watch(() => useRoute(), (newRoute) => {
  if (newRoute.params.year && newRoute.params.month) {
    article.query.year = newRoute.params.year;
    article.query.month = newRoute.params.month;
  }
});

// 生命周期钩子
onMounted(() => {
  listArchives();
});

// 方法定义
const changeArchive = (year, month) => {
  useRouter().push({ path: `/archives/${year}/${month}` });
};


</script>

<style scoped>

  .el-container {
    width: 640px;
  }

  .el-aside {
    position: fixed;
    left: 200px;
    margin-right: 50px;
    width: 150px !important;
  }

  .el-main {
    padding: 0px;
    line-height: 16px;
  }

  .me-month-list {
    margin-top: 10px;
    margin-bottom: 10px;
    text-align: center;
    list-style-type: none;
  }

  .me-month-item {
    margin-top: 18px;
    padding: 4px;
    font-size: 18px;
    color: #5FB878;
  }

  .me-order-list {
    float: right;
  }

  .me-month-title {
    margin-left: 4px;
    margin-bottom: 12px;
  }
</style>
