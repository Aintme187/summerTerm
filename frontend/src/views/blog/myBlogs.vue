<template>
  <div v-title data-title="博客系统">
    <el-container>
      <el-aside>
        <base-header :activeIndex="activeIndex" v-if="vistRight === true"></base-header>
          <el-select v-model="selectCategoryId" placeholder="请选择分区" style="margin: 30px;width: 10vw">
            <el-option
                v-for="item in categorys"
                :key="item.id"
                :label="item.categoryName"
                :value="item.id">
            </el-option>
          </el-select>
      </el-aside>
      <el-main style="height: 80vh; margin-left: -10vw">
        <el-row :gutter="40">
          <el-col :span="16">
            <myBlogs-scroll-page ref="myBlogPageRef" :selectCategoryId="[selectCategoryId]" :user-id="route.params.id"></myBlogs-scroll-page>
          </el-col>
          <el-col :span="8">
            <card-article cardHeader="最热文章" :articles="hotArticles"></card-article>
          </el-col>
        </el-row>
      </el-main>
    </el-container>
  </div>
</template>

<script setup>
import {ref, onMounted, watch} from 'vue';
import {useRoute} from 'vue-router';
import {ElMessage} from 'element-plus';
import CardMe from '@/components/card/CardMe.vue';
import CardArticle from '@/components/card/CardArticle.vue';
import CardArchive from '@/components/card/CardArchive.vue';
import CardTag from '@/components/card/CardTag.vue';
import MyBlogsScrollPage from "@/views/common/MyBlogsScrollPage.vue";
import {
  getHotArticlesByUserApi,
  getArticlesByCategoryApi,
  getMyBlogsApi,
} from '@/api/article';
import {getAllCategorysDetailApi} from "@/api/category";
import BaseHeader from "@/views/BaseHeader.vue";
import {useStore} from "vuex";
const route = useRoute();
const noEmpty = ref(true)
// 响应式状态
const vistRight = ref(true);
const hotTags = ref([]);
const hotArticles = ref([]);
const newArticles = ref([]);
const archives = ref([]);
const articles = ref([]);
const categorys = ref([]);
const selectCategoryId = ref('');
const myBlogPageRef = ref();
const store = useStore();
// 获取最热文章
const checkRight = () => {

  if(route.params.id != store.state.id) vistRight.value = false;
  else vistRight.value = true;
}

const getHotArticles = () => {
  getHotArticlesByUserApi(route.params.id)
      .then(data => {
        hotArticles.value = data.data;
      })
      .catch(error => {
        if (error !== 'error') {
          ElMessage({type: 'error', message: '最热文章加载失败!', showClose: true});
        }
      });
};

// 获取分类
const getCategorys = () => {
  getAllCategorysDetailApi()
      .then(data => {
        categorys.value = data.data;
        categorys.value.splice(0,0,{id:0, categoryName: "全部分类"});
      })
      .catch(error => {
        if (error !== 'error') {
          ElMessage({type: 'error', message: '分类加载失败!', showClose: true});
        }
      });
};

// 获取我的博客
const getMyBlogs = () => {
  getMyBlogsApi()
      .then(data => {
        articles.value = data.data;
      })
      .catch(error => {
        if (error !== 'error') {
          ElMessage({type: 'error', message: '我的博客加载失败!', showClose: true});
        }
      });
};

// 分类选择变化
const selectChanged = () => {
  getArticlesByCategoryApi()
      .then(data => {
        articles.value = data.data;
      });
};

onMounted(() => {
  getCategorys();
  getHotArticles();
  checkRight();
});


watch(selectCategoryId, (value) => {
  myBlogPageRef.value.getArticles(value)
})
</script>

<style scoped>


.el-container {

  margin-left: 10vw;
  width: 80vw;

}

.me-aside-right {
  margin-left: 20px;
  width: 260px;
}

.el-card {
  border-radius: 0;
}

.el-card:not(:first-child) {
  margin-top: 20px;
}
</style>
