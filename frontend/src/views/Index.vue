<template>
  <div v-title data-title="博客系统">

    <el-container>
      <el-aside>
        <base-header :activeIndex="activeIndex"></base-header>
      </el-aside>

      <el-main>

        <div style="margin-right: 5vw;margin-left: 10vw">
          <el-row :gutter="50">
            <el-col :span="16">
              <article-scroll-page/>
            </el-col>

            <el-col :span="8">
<!--              <card-me class="me-area"></card-me>-->
              <card-tag :tags="hotTags"></card-tag>

              <card-article cardHeader="最热文章" :articles="hotArticles"></card-article>

              <card-article cardHeader="最新文章" :articles="newArticles"></card-article>
            </el-col>
          </el-row>
        </div>

      </el-main>

      <!--      <el-aside>-->
      <!--      </el-aside>-->
      <!-- <base-footer v-show="footerShow"></base-footer> -->
    </el-container>
  </div>
</template>

<script setup>
import CardMe from '@/components/card/CardMe.vue'
import CardArticle from '@/components/card/CardArticle.vue'
import CardArchive from '@/components/card/CardArchive.vue'
import CardTag from '@/components/card/CardTag.vue'
import ArticleScrollPage from '@/views/common/ArticleScrollPage.vue'

import {
  getHotArticesApi,
  getNewArticesApi,
} from '@/api/article.js'
import {getHotTagsApi} from '@/api/tag.js'
import {onMounted, ref, watch} from "vue";
import {ElMessage} from "element-plus";
import HeaderforAll from '@/components/HeaderforAll.vue'
import BaseHeader from "@/views/BaseHeader.vue";
import {useRoute} from "vue-router";
import BaseFooter from "@/components/BaseFooter.vue";

const activeIndex = ref('/')
const footerShow = ref(true)
const route = useRoute()

// 监听路由变化
watch(() => route.path, (newPath) => {
  activeIndex.value = newPath;
  footerShow.value = newPath === '/';
});

// 组件挂载时

onMounted(() => {
  activeIndex.value = route.path;
  footerShow.value = route.path === '/blog/home';
  getHotArtices()
  getNewArticles()
  getHotTags()
})

const hotTags = ref([])
const hotArticles = ref([])
const newArticles = ref([])
const archives = ref([])

function getHotArtices() {
  getHotArticesApi().then(data => {
    hotArticles.value = data.data
  }).catch(error => {
    if (error !== 'error') {
      ElMessage({type: 'error', message: '最热文章加载失败!', showClose: true})
    }

  })
}

function getNewArticles() {
  getNewArticesApi().then(data => {
    newArticles.value = data.data
  }).catch(error => {
    if (error !== 'error') {
      ElMessage({type: 'error', message: '最新文章加载失败!', showClose: true})
    }

  })
}

function getHotTags() {
  getHotTagsApi().then(data => {
    hotTags.value = data.data
  }).catch(error => {
    if (error !== 'error') {
      ElMessage({type: 'error', message: '最热标签加载失败!', showClose: true})
    }

  })
}


</script>

<style scoped>

.el-container {
  margin-left: 5vw;
  width: 80vw;
}

.el-aside {
  width: 20vw;
}

.el-main {
  padding: 0px;
  line-height: 16px;
  margin-left: -10vw;
  //margin-right: 5vw;
  height: 85vh;
}

.el-card {
  border-radius: 10px;
}

.el-card:not(:first-child) {
  margin-top: 20px;
}
</style>
