<template>
  <div class="me-view-comment-item">
    <div style="margin: 20px">
      <div style="display: flex;justify-content: space-between">
        <div style="display: flex;margin-top: 10px">
          <img class="me-view-picture" :src="comment.author.avatar"/>
          <p style="margin-left: 10px">{{ comment.author.nickname }}</p>
        </div>
        <p style="font-size: small;color: grey">
          <span>{{ rootCommentCounts - index }}楼</span>
          <span>{{ comment.createDate }}</span>
        </p>
      </div>
      <div>
        <p class="me-view-comment-content">{{ comment.content }}</p>
        <div class="me-view-comment-tools">
          <!--<a class="me-view-comment-tool">-->
          <!--<i class="el-icon-caret-top"></i> 20-->
          <!--</a>-->
          <a class="me-view-comment-tool" @click="showComment(comment.author)">
            <el-button class="me-icon-comment" type="success">评论</el-button>
          </a>
        </div>

        <div class="me-reply-list">
          <div class="me-reply-item" v-for="c in comment.childrens" :key="c.id">
            <div style="display: flex;justify-content: space-between">
              <div>
                <span class="me-reply-user">{{ c.author.nickname }}:&nbsp;&nbsp;</span>

                <span v-if="c.level == 2" class="me-reply-user">@{{ c.toUser.nickname }} </span>

                <span>{{ c.content }}</span>
              </div>
              <span style="font-size: small;color: grey">{{ c.createDate }}</span>
              <!--  <a class="me-view-comment-tool" @click="showComment(c.id, c.author)">
                  <i class="me-icon-comment"></i>&nbsp;回复
                </a> -->
            </div>

          </div>

          <div class="me-view-comment-write" v-show="commentShow">
            <el-input
                v-model="reply.content"
                type="input"
                style="width: 90%"
                :placeholder="placeholder"
                class="me-view-comment-text"
                resize="none">
            </el-input>
            <el-button style="margin-left: 8px" @click="publishComment" type="text">评论</el-button>
          </div>

        </div>

      </div>
    </div>
  </div>
</template>

<script setup>
import {ref, reactive, onMounted} from 'vue';
import {ElMessage} from 'element-plus';
import {publishCommentApi} from '@/api/comment';
import store from "@/store/index.js";

// 使用defineProps定义props
const props = defineProps({
  articleId: String,
  comment: {
    id: Number,
    author: {
      id: Number,
      nickName: String,
      avatar: String,
    },
    content: String,
    createDate: String,
    toUser: Number,
    childrens: [],
    level: Number,
  },

  index: Number,
  rootCommentCounts: Number
});

// 使用ref创建响应式状态
const commentShow = ref(false);
const commentShowIndex = ref('');
const reply = ref(getEmptyReply());
const placeholder = ref('你的评论...');

// 初始化reply为空对象
function getEmptyReply() {
  return {
    articleId: props.articleId,
    parent: props.comment.id,
    toUserId: '',
    content: ''
  };
}

// 定义组件的方法
const showComment = (toUser) => {
  reply.value = getEmptyReply();
  if (toUser) {
    placeholder.value = `@${toUser.nickname} `;
    reply.value.toUserId = toUser.id;
  } else {
    placeholder.value = '你的评论...';
  }
  commentShow.value = true;
};

const publishComment = () => {
  if (!reply.value.content) {
    return;
  }
  publishCommentApi(reply.value, store.state.token).then(data => {
    ElMessage({type: 'success', message: '评论成功', showClose: true});
    if (!props.comment.childrens) {
      props.comment.childrens = [];
    }
    props.comment.childrens.unshift(data.data);

    commentShow.value = false;
    location.reload()
  }).catch(error => {
    ElMessage({type: 'error', message: '评论失败', showClose: true});
  });
};
</script>

<style scoped>
.me-view-meta {
  position: absolute;
  margin-left: 70vh;
  margin-top: 5vh;
  font-size: 12px;
  width: 80vh;
  color: #969696
}

.me-view-picture {
  width: 40px;
  height: 40px;
  border: 1px solid #ddd;
  border-radius: 50%;
  vertical-align: middle;
  background-color: #5fb878;
  margin-top: 1vh;
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

.me-view-comment-write {
  margin-top: 20px;
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

.me-view-comment-item {
  margin-top: 20px;
  padding-bottom: 16px;
  border-bottom: 1px solid #f0f0f0;
  background-color: white;
  border-radius: 10px;
}

.me-view-comment-author {
  margin: 10px 0;
  vertical-align: middle;
}

.me-view-nickname {
  margin-left: 60px;
  font-size: 14px;
}

.me-view-comment-content {
  line-height: 1.5;
}

.me-view-comment-tools {
  margin-top: 4px;
  margin-bottom: 10px;
}

.me-view-comment-tool {
  font-size: 13px;
  color: #a6a6a6;
  padding-right: 14px;
}

.v-note-wrapper .v-note-panel .v-note-show .v-show-content, .v-note-wrapper .v-note-panel .v-note-show .v-show-content-html {
  background: #fff !important;
}

.me-reply-list {
  padding-left: 16px;
  border-left: 4px solid #c5cac3;
}

.me-reply-item {
  margin-bottom: 8px;
  border-bottom: 1px solid #f0f0f0;
}

.me-reply-user {
  color: #78b6f7;
}
</style>
