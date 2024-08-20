<template>
  <el-aside>
    <el-card>
      <h1 class="me-author-name">博客导航栏</h1>

      <el-row>
        <el-col v-if="!simple" :span="4">
          <el-menu :router="true"
                   menu-trigger="click"
                   :default-active="activeIndex"
                   class="me-el-row"
          >
            <el-menu-item index="/blog/home">博客大厅</el-menu-item>
            <el-menu-item index="/blog/category/all">分类检索</el-menu-item>
            <el-menu-item index="/blog/messages">消息中心</el-menu-item>
            <el-menu-item @click="viewMyBlogs">我的博客</el-menu-item>
            <el-menu-item index="/write">写文章</el-menu-item>

          </el-menu>
        </el-col>

        <template v-else>
          <slot></slot>
        </template>


        <!--      <el-col :span="4">-->
        <!--        <el-menu :router="true" menu-trigger="click" mode="horizontal" active-text-color="#5FB878">-->
        <!--          <template v-if="!user.login">-->
        <!--            <el-menu-item index="/login">-->
        <!--              <el-button type="text">登录</el-button>-->
        <!--            </el-menu-item>-->
        <!--            <el-menu-item index="/register">-->
        <!--              <el-button type="text">注册</el-button>-->
        <!--            </el-menu-item>-->
        <!--          </template>-->

        <!--          <template v-else>-->
        <!--              <template slot="title">-->
        <!--                <img class="me-header-picture" :src="user.avatar"/>-->
        <!--              </template>-->
        <!--              <el-menu-item index @click="logout"><i class="el-icon-back"></i>退出</el-menu-item>-->
        <!--          </template>-->
        <!--        </el-menu>-->
        <!--      </el-col>-->

      </el-row>
    </el-card>

  </el-aside>
</template>

<script setup>
import {computed, ref} from 'vue';
import {useRoute, useRouter} from 'vue-router';
import { searchArticleApi } from '@/api/article';
import {useStore} from "vuex";
import {ElMessage} from "element-plus";

const store = useStore()
const router = useRouter()
const route = useRoute()

const activeIndex = ref('');
const search = ref('');

const props = defineProps({
  activeIndex: String,
  simple: {
    type: Boolean,
    default: false
  }
});

// 使用 ref 定义响应式变量

// 使用 computed 定义计算属性
const user = computed(() => {
  const login = store.state.account.length !== 0;
  const avatar = store.state.avatar;
  return { login, avatar };
});
const viewMyBlogs = () => {
  router.push({ path: `/blog/myBlogs/${store.state.id}`});
};

const logout = () => {
  // Dispatch logout action
  store.dispatch('logout').then(() => {
    ElMessage({message: '退出登录成功', type:"success", showClose: true});
    router.push({path: '/'})
  }).catch((error) => {
    if (error !== 'error') {
      ElMessage({message: error, type: 'error', showClose: true});
    }
  })

};

const querySearchAsync = (queryString, cb) => {
  searchArticleApi(search.value).then((res) => {
    if (res.success) {
      const results = [];
      for (const item of res.data) {
        results.push({
          id: item.id,
          value: item.title
        });
      }
      cb(results);
    }
  });
};

const handleSelect = (item) => {
  router.push({ path: '/blog/view/' + item.id });
};
</script>

<style scoped>
.me-author-name{
  margin-left: 10px;
}
.me-el-row{
  width: 20vh;

}

.el-header {

}

.me-title {
  margin-top: 10px;
  font-size: 24px;
}

.me-header-left {
  margin-top: 10px;
}

.me-title img {
  max-height: 2.4rem;
  max-width: 100%;
}

.me-header-picture {
  width: 36px;
  height: 36px;
  border: 1px solid #ddd;
  border-radius: 50%;
  vertical-align: middle;
  background-color: #5fb878;
}
.el-card{
  margin-left: 1vw;
  height: 45vh;
  width: 12vw;
}
</style>
