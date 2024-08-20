<template>
  <el-card class="me-area" :body-style="{ padding: '32px' }">
    <div class="me-article-header">

      <a @click="view(id)" class="me-article-title">{{ title }}</a>
      <el-button v-if="weight > 0" class="me-article-icon" type="text">置顶</el-button>
      <span class="me-pull-right me-article-count">
	    	<el-icon size="12px"><Comment/></el-icon>&nbsp;{{ commentCounts }}
	    </span>
      <span class="me-pull-right me-article-count">
	    	<el-icon size="12px"><User/></el-icon>
        {{ viewCounts }}
	    </span>
    </div>

    <div class="me-artile-description">
      {{ summary }}
    </div>
    <div class="me-article-footer">

      <div class="me-category-tags">
        <span>
          <el-text class="me-text">分区：</el-text>
          <el-tag round type="primary" size="small" class="me-category">{{ category.categoryName }}</el-tag>
        </span>
        <span>
          <el-text class="me-text_tag">标签：</el-text>
          <span class="me-tag">
            <el-tag v-for="t in tags" :key="t.tagName" size="small" type="primary">{{ t.tagName }}</el-tag>
          </span>
        </span>
      </div>

      <div class="me-article-author">
        <el-icon>
          <UserFilled/>
        </el-icon>&nbsp;{{ author }}
        <span style="margin-left: 10px">
	    	  <el-icon><Timer/></el-icon>{{ createDate }}
	      </span>
        <span class="me-article-like">
        <el-icon v-if="noLike === true" @click="like(id)"><MagicStick/></el-icon>
        <el-icon v-if="noLike === false" @click="like(id)"><Smoking/></el-icon>
        {{ likeCounts }}
      </span>

        <span class="me-article-favorite">
        <el-icon @click="favoriteVisible = true"><Star/></el-icon>
        收藏
      </span>
      </div>


      <scroll-page :loading="loading" :offset="offset" :no-data="noData" v-on:load="load">
        <el-dialog title="添加到收藏夹"
                   v-model="favoriteVisible"
                   :close-on-click-modal=false
                   custom-class="me-dialog"
                   class="me-dialog"
        >
          <el-form :model="favoriteForm" ref="favoriteForm">
            <el-form-item label="收藏夹" prop="favorite">
              <el-select v-model="favoriteFormRef.favorite" value-key="id" placeholder="请选择收藏夹">
                <el-option v-for="f in favoriteList" :key="f.id" :label="f.favoriteName" :value="f.id"></el-option>
              </el-select>
            </el-form-item>
          </el-form>

          <div slot="footer" class="dialog-footer">
            <el-button @click="favoriteVisible = false">取 消</el-button>
            <el-button type="primary" @click="uploadFavorite">添加</el-button>
          </div>
        </el-dialog>
      </scroll-page>
    </div>

  </el-card>


</template>

<script setup>
import {ref, onMounted, watch, defineProps} from 'vue';
import {formatTime} from '@/utils/time.js';
import {likeApi} from '@/api/article';
import {getAllCategorysApi} from '@/api/category.js';
import {getFavoriteListApi} from '@/api/favorite.js';
import {uploadFavoriteApi} from '@/api/favorite.js';
import ScrollPage from '@/components/scrollpage/index.vue';
import {ElMessage} from "element-plus";
import {useRoute, useRouter} from "vue-router";
import {useStore} from "vuex";
import {Comment, MagicStick, Smoking, Star, Timer, User, UserFilled} from "@element-plus/icons-vue";

const store = useStore()
const router = useRouter()
const route = useRoute()
const noLike = ref(true)
// 定义props
const props = defineProps({
  offset: {
    type: Number,
    default: 100
  },
  id: String,
  weight: Number,
  title: String,
  commentCounts: Number,
  viewCounts: Number,
  summary: String,
  author: String,
  tags: Array,
  category: {
    id: Number,
    categoryName: String,
    avatar: String
  },
  createDate: String,
  likeCounts: Number
});

// 创建响应式状态
const favoriteFormRef = ref({
      articleId: '',
      favorite: ''
    }
);
const loading = ref(false);
const noData = ref(false);
const favoriteVisible = ref(false);
const favoriteList = ref({
  id: '',
  favoriteName: ''
});
const favoriteForm = ref({
      articleId: '',
      favorite: ''
    }
);

// 挂载时获取收藏夹列表
onMounted(() => {
  getFavoriteList();
});

// 监听props的变化
watch(() => props.id, (newVal) => {
  // 处理id变化时的逻辑
});

// 定义方法
const load = () => {
  getFavoriteList();
};

const view = (id) => {
  router.push({path: `/blog/view/${id}`});
};
const likeCounts = ref(props.likeCounts);
const like = (id) => {
  console.log(store.state.token)
  likeApi(id, store.state.token).then((data) => {
    if (data.success) {
      console.log(store.state.token)
      likeCounts.value = data.data.likeCounts;
      ElMessage({type: 'success', message: '点赞成功!', showClose: true});
    } else {
      noLike.value = false;
      ElMessage({type: 'error', message: '您已经点赞过此文章!', showClose: true});
    }
  }).catch((error) => {
    noLike.value = false;
    ElMessage({type: 'error', message: '您已经点赞过此文章!', showClose: true});
  });
};

const getFavoriteList = () => {
  const token = store.state.token; // 确保你已经定义了$store并且有state.token
  getFavoriteListApi(token).then((data) => {
    if (data.success) {
      favoriteList.value = data.data;
    } else {
      ElMessage({type: 'error', message: '收藏夹加载失败', showClose: true});
    }
  }).catch((error) => {
    ElMessage({type: 'error', message: '收藏夹加载失败', showClose: true});
  });
};

const uploadFavorite = () => {
  favoriteForm.value.articleId = props.id;
  favoriteForm.value.favorite = favoriteFormRef.value.favorite;
  uploadFavoriteApi(favoriteForm.value).then((data) => {
    if (data.success && favoriteForm.value.favorite !== '') {
      favoriteVisible.value = false;
      ElMessage({message: '收藏成功啦', type: 'success', showClose: true});
    } else {
      ElMessage({message: '未创建收藏夹', type: 'error', showClose: true});
    }
  }).catch((error) => {
    ElMessage({message: error, type: 'error', showClose: true});
  });
};

// 导出组件

</script>

<style scoped>
.me-text {
  //position: absolute;
  top: 1vh;
  left: 1vh;
  font-weight: bold;
}

.me-text_tag {
  //position: absolute;
  top: 1vh;
  right: 16vh;
  font-weight: bold;
}

.me-tag {
  //position: absolute;
  top: 1vh;
  right: 2vh;
}

.me-category {
  //position: absolute;
  top: 1vh;
  left: 6vh;
}

.me-article-footer {
  position: relative;
  padding: 10px;
}

.me-article-header {
  /*padding: 10px 18px;*/
  padding-bottom: 10px;
}

.me-article-title {
  font-weight: 600;
  font-size: 26px;
  margin-left: 10px;
  cursor: pointer;
  transition: color 0.3s;
}

.me-article-title:hover {
  color: #66b1ff;
}

.me-article-icon {
  padding: 3px 8px;
  font-size: 12px;
  color: #f56c6c;
  cursor: pointer;
}

.me-article-icon:hover {
  color: #ff7f7f;
}

.me-article-count {
  color: #a6a6a6;
  padding-left: 14px;
  font-size: 13px;
  display: flex;
  align-items: center;
}

.me-article-time {
  color: #a6a6a6;
  padding-left: 14px;
  font-size: 13px;
  //position: absolute;
  right: 20px;
  top: 100px;
}

.me-pull-right {
  float: right;
}

.me-artile-description {
  font-size: 18px;
  line-height: 24px;
  margin-bottom: 10px;
  margin-left: 10px;
}

.me-article-author {
  //position: absolute;
  top: 5vh;
  color: #a6a6a6;
  padding-right: 18px;
  margin-top: 20px;
  font-size: 13px;
}

.el-tag {
  margin-left: 6px;
}

.me-article-favorite {
  float: right;
  //position: absolute;
  font-size: 12px;
  margin-bottom: 10px;
  right: 15px;
  top: 5vh;
  cursor: pointer;
  margin-right: 10px;
}

.me-article-like {
  float: right;
  //position: absolute;
  font-size: 12px;
  margin-bottom: 10px;
  right: 95px;
  top: 5vh;
  cursor: pointer;
}

.el-icon-caret-top {
  font-size: 20px;
  margin-bottom: 5px;
  margin-left: 500px;
}

.me-dialog {
  width: 800px;
  position: fixed;
  margin-left: 520px;
  margin-top: 140px;
}

.me-category-tags {
  display: flex;
  justify-content: space-between;
  //margin-bottom: 10px;
}
</style>
