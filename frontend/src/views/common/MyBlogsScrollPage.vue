<template>
  <el-card v-if="noEmpty === false" >
    <el-text class="el-card-text">您还没有创建博客，快去创建博客吧</el-text>
  </el-card>
  <scroll-page v-else :loading="loading" :offset="offset" :no-data="noData" @load="load">
    <article-item v-for="a in articles" :key="a.id" v-bind="a"></article-item>
  </scroll-page>
<!--  <scroll-page :loading="loading" :offset="offset" :no-data="noData" @load="load">-->
<!--    <article-item v-for="a in articles" :key="a.id" v-bind="a"></article-item>-->
<!--  </scroll-page>-->
</template>

<script setup>
import {ref, watch, onMounted, defineProps, toRef} from 'vue';
import ArticleItem from '@/components/article/ArticleItem.vue';
import ScrollPage from '@/components/scrollpage/index.vue';
import {getMyBlogsApi} from "@/api/article";
import {useStore} from "vuex";
import {useRoute, useRouter} from "vue-router";
import {ElMessage} from "element-plus";
const noEmpty = ref(false)
// 使用ref创建响应式数据
const loading = ref(false);
const noData = ref(false);
const innerPage = ref({
  pageSize: 5,
  pageNumber: 1,
  name: 'a.createDate',
  sort: 'desc'
});
const props = defineProps({
  userId: Number,
  selectCategoryId: Number,
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
const selectCategoryId=props.selectCategoryId[0]
const articles = ref([]);
const store = useStore()
const router = useRouter()
const route = useRoute()

// 监视selectCategoryId的变化
// watch(selectCategoryId, (newVal) => {
//   getArticles(newVal);
// });

// 使用onMounted代替created钩子
onMounted(() => {
  getArticles(0);
});

// 定义组件的方法
const load = () => {
  getArticles(0);
};

const view = (id) => {
  router.push({path: `/blog/view/${id}`});
};

function getArticles(categoryId) {
  loading.value = true;
  console.log("接下来是userId")
  console.log(props.userId)
  getMyBlogsApi(props.userId, categoryId).then(data => {
    console.log("接下来是userId")
    console.log(props.userId)
    innerPage.value.pageNumber += 1;
    articles.value = data.data;
    if(data.data.length === 0){
      noEmpty.value = false;
    }
    else noEmpty.value = true;

    console.log("below is articles.value")
    console.log(articles.value)
  }).catch(error => {
    if (error !== 'error') {
      // 假设这是你的全局消息组件
      ElMessage({type: 'error', message: '文章加载失败!', showClose: true});
    }
  }).finally(() => {
    loading.value = false;
  });
};

defineExpose({getArticles})
</script>

<style scoped>

.el-card-text{
  justify-content: center;
  align-items: center;
  margin-left: 9vw;

}
.el-card {
  margin-left: 6vw;
  border-radius: 20px;
}

.el-card:not(:first-child) {
  margin-top: 10px;
}
</style>
