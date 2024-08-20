<template>
  <div class="me-ct-body" v-title :data-title="title">
    <el-container>
      <el-aside>
        <base-header :activeIndex="activeIndex"></base-header>
      </el-aside>
      <el-main>
        <div class="me-ct-title">
          <!-- 使用 $route 替代 this.$route -->
          <template v-if="route.params.type === 'tag'">
            <img class="me-ct-picture" :src="ct.avatar ? ct.avatar : defaultAvatar"/>
            <h3 class="me-ct-name">{{ ct.tagName }}</h3>
          </template>

          <template v-else-if="route.params.type === 'category'">
            <img class="me-ct-picture" :src="ct.avatar ? ct.avatar : defaultAvatar"/>
            <h3 class="me-ct-name">{{ ct.categoryName }}</h3>
            <p>{{ ct.description }}</p>
          </template>

          <template v-else>
            <img class="me-ct-picture" :src="ct.avatar ? ct.avatar : defaultAvatar"/>
            <h3 class="me-ct-name">{{ ct.favoriteName }}</h3>
          </template>

          <span class="me-ct-meta">{{ ct.articles }}</span>
        </div>

        <div class="me-ct-articles">
          <scroll-page :loading="loading" :offset="offset" :no-data="noData" @load="load">
            <article-item style="margin-top: 20px" v-for="a in articles" :key="a.id" v-bind="a"></article-item>
          </scroll-page>
        </div>
      </el-main>
    </el-container>
  </div>
</template>

<script setup>
import {ref, reactive, onMounted, watch, computed} from 'vue';
import {useRoute, useRouter} from 'vue-router';
import ArticleScrollPage from '@/views/common/ArticleScrollPage.vue';
import defaultAvatar from '@/assets/img/logo.png';
import {ElMessage} from 'element-plus';
import {getTagDetailApi} from "@/api/tag.js";
import {getArticlesApi, getArticlesByCategoryApi, getArticlesByTagApi} from "@/api/article.js";
import {getFavoriteDetailApi} from "@/api/favorite.js";
import noData from "lodash/seq.js";
import ScrollPage from "@/components/scrollpage/index.vue";
import ArticleItem from "@/components/article/ArticleItem.vue";
import BaseHeader from "@/views/BaseHeader.vue";
import {getCategoryDetailApi} from "@/api/category.js";
// API函数导入...
const loading = ref(false);
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
const load = () => {
  getCategoryOrTagAndArticles(route.params);
};
const router = useRouter()
const route = useRoute()
// 创建响应式状态
const ct = ref({
  avatar: '',
  favoriteName: '',
  categoryName: '',
  tagName: '',
  articles: []
});
const articles = ref([]);
const article = reactive({
  query: {
    tagId: '',
    categoryId: '',
    favoriteId: ''
  }
});
const innerPage = ref({
  pageSize: 5,
  pageNumber: 1,
  name: 'a.createDate',
  sort: 'desc'
});

// 计算属性


// 路由变化监听
watch(() => route.params, (newParams) => {
  getCategoryOrTagAndArticles(newParams);
});

// 生命周期钩子
onMounted(() => {
  getCategoryOrTagAndArticles(route.params);
});
const newArticles = ref([])
// 方法定义
const getCategoryOrTagAndArticles = (params) => {
  const {id, type} = params;
  if (!id || !type) return;
  console.log("接下来是路由")
  console.log(params)
  switch (type) {
    case 'tag':
      getTagDetail(id);
      article.query.tagId = id;
      break;
    case 'category':
      getCategoryDetail(id);
      article.query.categoryId = id;
      break;
    case 'favorite':
      getFavoriteDetail(id);
      article.query.favoriteId = id;
      break;
    default:
      break;
  }
  loading.value = true;

  getArticlesApi(article.query, innerPage.value).then(data => {
    newArticles.value = data.data;

    if (newArticles.value && newArticles.value.length > 0) {
      innerPage.value.pageNumber += 1;

      articles.value = articles.value.concat(newArticles.value);
      console.log("these are articles")
      console.log(articles.value)
    } else {
      noData.value = true;
    }
  }).catch(error => {
    if (error !== 'error') {
      // 假设这是你的全局消息组件
      ElMessage({type: 'error', message: '文章加载失败!', showClose: true});
    }
  }).finally(() => {
    loading.value = false;
  });
};
// API调用方法...
// API调用方法：获取标签详情
const getTagDetail = async (id) => {
  try {
    const response = await getTagDetailApi(id);
    ct.value = response.data;
  } catch (error) {
    if (error !== 'error') {
      ElMessage({type: 'error', message: '标签加载失败', showClose: true});
    }
  }
};

// API调用方法：获取分类详情
const getCategoryDetail = async (id) => {
  // 类似于getTagDetail，使用相应的API函数
  try {
    const response = await getCategoryDetailApi(id);
    ct.value = response.data;
  } catch (error) {
    if (error !== 'error') {
      ElMessage({type: 'error', message: '文章分类加载失败', showClose: true});
    }
  }
};

// API调用方法：根据分类获取文章列表
const getArticlesByCategory = async (id) => {
  try {
    const response = await getArticlesByCategoryApi(id);
    article.value = response.data;
  } catch (error) {
    if (error !== 'error') {
      ElMessage({type: 'error', message: '文章加载失败', showClose: true});
    }
  }
};

// API调用方法：根据标签获取文章列表
const getArticlesByTag = async (id) => {
  // 类似于getArticlesByCategory，使用相应的API函数
  try {
    const response = await getArticlesByTagApi(id);
    article.value = response.data;
  } catch (error) {
    if (error !== 'error') {
      ElMessage({type: 'error', message: '文章加载失败', showClose: true});
    }
  }
};

// API调用方法：获取收藏夹详情
const getFavoriteDetail = async (id) => {
  try {
    const response = await getFavoriteDetailApi(id);
    ct.value = response.data;
  } catch (error) {
    if (error !== 'error') {
      ElMessage({type: 'error', message: '收藏加载失败', showClose: true});
    }
  }
};

// 导出组件

</script>

<style scoped>
.el-aside {
  margin-top: 12vh;
}

.el-container {
  margin-left: 15vw;
  width: 70vw;
}

.el-main {
  padding: 0px;
  line-height: 16px;
  margin-left: -15vw;
  height: 80vh;
}

.me-ct-title {
  text-align: center;
  height: 150px;
  padding-top: 20px;
  margin-left: 2vw;
}

.me-ct-picture {
  margin-left: 2vw;
  width: 60px;
  height: 60px;
}

.me-ct-name {
  font-size: 28px;
  margin-left: 2vw;
}

.me-ct-meta {
  font-size: 12px;
  color: #969696;
}

.me-ct-articles {
  margin-left: 14vw;
  width: 45vw;
}

</style>
