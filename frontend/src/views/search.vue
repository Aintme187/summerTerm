<template>
  <div v-title data-title="博客系统">
    <el-container>

      <el-main class="me-articles">

        <article-scroll-page></article-scroll-page>

      </el-main>

      <el-aside>

<!--        <card-me class="me-area"></card-me>-->
        <card-tag :tags="hotTags"></card-tag>

        <card-article cardHeader="最热文章" :articles="hotArticles"></card-article>

        <card-archive cardHeader="文章归档" :archives="archives"></card-archive>

        <card-article cardHeader="最新文章" :articles="newArticles"></card-article>

      </el-aside>

    </el-container>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import CardMe from '@/components/card/CardMe.vue';
import CardArticle from '@/components/card/CardArticle.vue';
import CardArchive from '@/components/card/CardArchive.vue';
import CardTag from '@/components/card/CardTag.vue';
import ArticleScrollPage from '@/views/common/ArticleScrollPage.vue';
import { getHotArticesApi, getNewArticesApi } from '@/api/article';
import { getHotTagsApi } from '@/api/tag';

const hotTags = ref([]);
const hotArticles = ref([]);
const newArticles = ref([]);
const archives = ref([]);

onMounted(() => {
  getHotArtices();
  getNewArtices();
  getHotTags();
  listArchives();
});

async function getHotArtices() {
  try {
    const response = await getHotArticesApi();
    hotArticles.value = response.data;
  } catch (error) {
    handleError(error, '最热文章加载失败!');
  }
}

async function getNewArtices() {
  try {
    const response = await getNewArticesApi();
    newArticles.value = response.data;
  } catch (error) {
    handleError(error, '最新文章加载失败!');
  }
}

async function getHotTags() {
  try {
    const response = await getHotTagsApi();
    hotTags.value = response.data;
  } catch (error) {
    handleError(error, '最热标签加载失败!');
  }
}



function handleError(error, message) {
  if (error !== 'error') {
    $message({ type: 'error', message, showClose: true });
  }
}
</script>

<style scoped>
.el-container {
  width: 960px;
}

.el-aside {
  margin-left: 20px;
  width: 260px;
}

.el-main {
  padding: 0px;
  line-height: 16px;
}

.el-card {
  border-radius: 0;
}

.el-card:not(:first-child) {
  margin-top: 20px;
}
</style>
