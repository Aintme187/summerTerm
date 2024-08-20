<template>
  <div class="me-view-body" v-title :data-title="title">
    <el-container style="width:90vw;">
      <!-- <el-aside class="me-area">
         <ul class="me-operation-list">
           <li class="me-operation-item">
             <el-button type="primary" icon="el-icon-edit"></el-button>
           </li>
         </ul>
       </el-aside> -->
      <el-aside style="margin-left: 10vw">
        <card-article cardHeader="相关推荐" :articles="relatedArticles"></card-article>
      </el-aside>
      <el-main>

        <div class="me-view-card">
          <h1 class="me-view-title">{{ article.title }}</h1>
          <div style="display: flex;justify-content: space-between">
            <div>
              <img class="me-blog-view-picture-top" :src="article.authorInfo.avatar" @click="browseOthers"/>
              <span class="me-view-text-blog">{{ article.authorInfo.nickname }}</span>
              <div class="me-view-info">
                <div class="me-view-meta">
                  <span>{{ article.createDate }}</span>
                  <span>阅读   {{ article.viewCounts }}</span>
                  <span>评论   {{ article.commentCounts }}</span>
                  <el-icon @click="favoriteVisible = true" style="margin-left: 10px">
                    <Star/>
                  </el-icon>
                  <span style="font-weight: bold;font-size: large">添加到收藏夹</span>
                </div>
              </div>
            </div>
          </div>
          <div class="me-view-content">
            <markdown-editor :editor=article.editor></markdown-editor>
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
          </div>

          <div class="me-view-end">
            <el-alert
                title="文章End..."
                type="success"
                center
                :closable="false">
            </el-alert>
          </div>

          <div class="me-view-tag">
            标签：
            <!--<el-tag v-for="t in article.tags" :key="t.id" class="me-view-tag-item" size="mini" type="success">{{t.tagName}}</el-tag>-->
            <el-button @click="tagOrCategory('tag', t.id)" size="small" type="primary" v-for="t in article.tags"
                       :key="t.id" round plain>{{ t.tagName }}
            </el-button>
          </div>

          <div class="me-view-tag">
            文章分类：
            <!--<span style="font-weight: 600">{{article.category.categoryName}}</span>-->
            <el-button @click="tagOrCategory('category', article.category.id)" size="small" type="primary" round plain>
              {{ article.category.categoryName }}
            </el-button>
          </div>
          <el-button type="primary" style="margin-top: 20px" @click="openMaterials">查看资料</el-button>
          <el-dialog
              title="相关资料"
              v-model="dialogTableVisible"
              @update:visible="val => dialogTableVisible = val"
              width="800"
              :append-to-body="true"
              center>
            <el-table :data="materials">
              <el-table-column property="name" label="名称" width="auto">
                <template v-slot:default="scope">
           <span @click="downloadMaterial(scope.row.id)" style="color: blue; cursor: pointer;">
             {{ scope.row.name }}
           </span>
                </template>
              </el-table-column>
              <el-table-column property="size" label="文件大小"/>
              <el-table-column property="time" label="上传时间" width="150">
                <template v-slot:default="scope">
                  {{ formatTime(scope.row.time) }}
                </template>
              </el-table-column>
            </el-table>
            <br>
            <!--            <span slot="footer" class="dialog-footer">-->

            <!--     </span>-->
          </el-dialog>
          <div class="me-view-comment">
            <div style="display: flex">
              <img class="me-blog-view-picture" :src="avatar"/>
              <el-input
                  type="textarea"
                  :autosize="{ minRows: 2}"
                  placeholder="你的评论..."
                  class="me-view-comment-text"
                  style="margin-left: 10px"
                  v-model="comment.content"
                  resize="none">
              </el-input>
            </div>
            <el-button type="primary" style="margin:20px" @click="publishComment">评论</el-button>


            <commment-item
                v-for="(c,index) in comments"
                :comment="c"
                :articleId="article.id"
                :index="index"
                :rootCommentCounts="comments.length"
                @commentCountsIncrement="commentCountsIncrement"
                :key="c.id">
            </commment-item>

          </div>

        </div>
      </el-main>


    </el-container>
  </div>
</template>

<script setup>
import {ref, onMounted, watch, computed} from 'vue';
import MarkdownEditor from '@/components/markdown/MarkdownEditor.vue';
import CommmentItem from '@/components/comment/CommentItem.vue';
import {getHotArticesApi, getRelatedArticlesApi, viewArticleApi} from '@/api/article.js';
import {getCommentsByArticleApi, publishCommentApi} from '@/api/comment.js';
import default_avatar from '@/assets/img/default_avatar.png';
import CardArticle from '@/components/card/CardArticle.vue';
import {useRoute, useRouter} from 'vue-router';
import {useStore} from 'vuex';

// 使用Element Plus的消息提示组件
import {ElDialog, ElMessage} from 'element-plus';
import {
  downloadMaterialForArticle,
  downloadMaterialService,
  getMaterialForArticle,
  getMaterialService
} from "@/api/student.js";
import {getFavoriteListApi, uploadFavoriteApi} from "@/api/favorite.js";
import {Star} from "@element-plus/icons-vue";

const dialogTableVisible = ref(false);
const favoriteVisible = ref(false);
const formatTime = (time) => {
  return time.replace('T', ' ');
}
// 创建响应式状态
const favoriteForm = ref({
      articleId: '',
      favorite: ''
    }
);
const favoriteFormRef = ref({
      articleId: '',
      favorite: ''
    }
);
const favoriteList = ref({
  id: '',
  favoriteName: ''
});
// 挂载时获取收藏夹列表
const route = useRoute();
const router = useRouter();
const store = useStore();
const relatedArticles = ref([]);
const article = ref({
  // ...初始化article对象...
  id: '',
  title: '',
  commentCounts: 0,
  viewCounts: 0,
  summary: '',
  author: {},
  authorInfo: {},
  tags: [],
  category: {},
  createDate: '',
  editor: {
    value: '',
    toolbarsFlag: false,
    subfield: false,
    defaultOpen: 'preview'
  }
});
const comments = ref([]);
const comment = ref({
  content: '',
});

// 计算属性
const avatar = computed(() => {
  let avatar = store.state.avatar;
  return avatar.length > 0 ? avatar : default_avatar;
});

const title = computed(() => `${article.value.title} - 文章 - 博客系统`);

// 路由和状态管理钩子
const uploadFavorite = () => {
  favoriteForm.value.articleId = article.value.id;
  favoriteForm.value.favorite = favoriteFormRef.value.favorite;
  if (favoriteForm.value.favorite !== '') {
    uploadFavoriteApi(favoriteForm.value).then((data) => {
      if (data.success) {
        favoriteVisible.value = false;
        ElMessage({message: '收藏成功啦', type: 'success', showClose: true});
      } else {
        ElMessage({message: '未创建收藏夹', type: 'error', showClose: true});
      }
    }).catch((error) => {
      ElMessage({message: error, type: 'error', showClose: true});
    });
  } else {
    ElMessage({message: '未创建收藏夹', type: 'error', showClose: true});
  }

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
// 生命周期钩子
onMounted(() => {
  getArticle();
  getFavoriteList();
});

// 侦听器
watch(article, value => {
  getArticle();
})

// 方法定义
const browseOthers = () => {
  console.log("click image");
  console.log(article.value.authorInfo);
  // router.push(`/blog/myBlogs/${article.value.authorInfo.id}`);
  router.push(`/usercenter/myInfo/${article.value.authorInfo.id}`);
};

const message = (options) => ElMessage(options);
const materials = ref([]);
const currentArticleId = ref();

const openMaterials = () => {
  getMaterialForArticle(article.value.id).then(data => {
    materials.value = data.data;
    dialogTableVisible.value = true;
  }).catch(error => {
    ElMessage({type: 'error', message: '查看资料失败', showClose: true});
  })
}


const downloadMaterial = async (materialId) => {
  try {
    // 调用服务生成下载 URL
    const response = await downloadMaterialForArticle(materialId);
    const url = response.data; // 假设返回的 JSON 中有一个 data 字段包含 URL
    // 创建一个临时的 <a> 元素
    const link = document.createElement('a');
    link.href = url;
    document.body.appendChild(link); // 将 <a> 元素添加到 DOM 中
    link.click(); // 触发点击事件
    document.body.removeChild(link); // 移除 <a> 元素
  } catch (error) {
    console.error('下载失败：', error);
  }
}
// 获取相关文章列表的方法
const getRelatedArticles = async () => {
  try {
    const data = await getRelatedArticlesApi(article.value.tags);
    relatedArticles.value = data.data;
  } catch (error) {
    if (error !== 'error') {
      message({type: 'error', message: '相关推荐加载失败!', showClose: true});
    }
  }
};

// 导航到标签或分类的方法
const tagOrCategory = (type, id) => {
  router.push({path: `/${type}/${id}`});
};

// 编辑文章的方法
const editArticle = () => {
  router.push({path: `/write/${article.value.id}`});
};

// 获取文章详情的方法
const getArticle = () => {
  viewArticleApi(route.params.id).then(data => {
    Object.assign(article.value, data.data)
    console.log(article.value)
    article.value.editor.value = data.data.body.content;
    getRelatedArticles();
    getCommentsByArticle();
  }).catch(error => {
    console.log(error)
    if (error !== 'error') {
      message({type: 'error', message: '文章加载失败', showClose: true});
    }
  })
};

const publishComment = () => {
  if (!comment.value.content) {
    return;
  }
  publishCommentApi({
    articleId: article.value.id, // 使用路由参数中的文章ID
    content: comment.value.content,
    toUserId: article.value.authorInfo.id
  }, store.state.token).then(data => {

    ElMessage({type: 'success', message: '评论成功', showClose: true});
    comment.value.content = ''; // 清空评论内容
    comments.value.unshift(data.data); // 将新评论插入到评论列表的开头
    commentCountsIncrement(); // 更新评论计数
    article.value.commentCounts += 1;
    console.log("我被评论拉")
    console.log(article.value.commentCounts)
    getArticle();
    location.reload()
  }).catch(error => {
    ElMessage({type: 'error', message: '评论失败', showClose: true});
  })
};

// 获取文章评论的方法
const getCommentsByArticle = () => {
  getCommentsByArticleApi(article.value.id).then(data => {
    comments.value = data.data;
  }).catch(error => {
    if (error !== 'error') {
      message({type: 'error', message: '评论加载失败', showClose: true});
    }
  })
};
const commentCountsIncrement = () => {
  article.value.commentCounts += 1;
};

// 导出组件

</script>


<style scoped>
.me-article-view-favorite {
  margin-left: 45vw;
  font-size: 20px;
  margin-bottom: 20px;
}

.me-blog-view-picture-top {
  margin-top: 0px;
  margin-left: 20px;
  height: 60px;
  border: 1px solid #ddd;
  border-radius: 50%;
  vertical-align: middle;
  background-color: #5fb878;
}

.me-view-text-blog {
  font-weight: bold;
  margin-left: 1vw;
}

.me-view-container {
  width: 70vw;
}

.el-main {
  overflow: hidden;
}

.me-view-title {
  font-size: 34px;
  font-weight: 800;
  line-height: 1.3;
}

.me-view-author {
  /*margin: 30px 0;*/
  margin-top: 30px;
  vertical-align: middle;
}

.me-blog-view-picture {
  margin-top: 0px;
  margin-left: 20px;
  width: 50px;
  height: 50px;
  border: 1px solid #ddd;
  border-radius: 50%;
  vertical-align: middle;
  background-color: #5fb878;
}

.me-comment-info {
  display: inline-block;
  position: absolute;
  left: 61vh;
  margin-top: 20px;
}

.me-view-info {
  width: 100vh;
  left: 66vh;
  margin-left: 0px;
  margin-bottom: 40px;
}

.me-view-meta {
  //position: absolute;
  margin-left: 100px;
  font-size: 12px;
}

.me-view-end {
  margin-top: 20px;
}

.me-view-tag {
  margin-top: 20px;
  padding-left: 6px;
  border-left: 4px solid #c5cac3;
}

.me-view-tag-item {
  margin: 0 4px;
}

.me-view-comment {
  margin-top: 60px;
}

.me-view-comment-title {
  font-weight: 600;
  border-bottom: 1px solid #f0f0f0;
  padding-bottom: 20px;
}

.me-view-comment-text {
  font-size: 16px;
}

.v-show-content {
  padding: 8px 25px 15px 30px !important;
}

.v-note-wrapper .v-note-panel {
  box-shadow: none !important;
}

.v-note-wrapper .v-note-panel .v-note-show .v-show-content, .v-note-wrapper .v-note-panel .v-note-show .v-show-content-html {
  background: #fff !important;
}


</style>
