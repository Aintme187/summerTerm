<template>
  <el-card class="me-area" :body-style="{ padding: '20px' }">
    <a @click="view" class="me-message-title">
      <div style="display: flex">
        <img :src="sendAvatar" width="80px" height="80px" style="margin-right: 10px"/>
        <div>
          <p>
            <el-icon size="12px">
              <User/>
            </el-icon>
            <span style="font-size: large;font-weight: bold;margin-left: 5px">{{ sendNickname }}</span>
            {{ createDate }}
          </p>
          <p>
            {{ sendContent }}
          </p>
        </div>
      </div>
    </a>
  </el-card>
</template>

<script setup>
import {ref, defineProps, onMounted} from 'vue';
import {useRoute, useRouter} from 'vue-router';
import {User} from "@element-plus/icons-vue";

// 使用defineProps定义props
const props = defineProps({
  id: String,
  sendNickname: String,
  sendAvatar: String,
  sendContent: String,
  articleTitle: String,
  articleId: String,
  createDate: String
});

// 从vue-router中解构出所需的函数
const route = useRoute();
const router = useRouter();

// 定义一个方法来处理路由跳转
const view = () => {
  router.push({path: `/blog/view/${props.articleId}`});
};
</script>

<style scoped>
.me-userInfo {
  float: left;
  margin-top: 20px;
  margin-left: -40px;
}

.me-area {

  left: 200px;
  height: 120px;
}

.me-message-header {
  /*padding: 10px 18px;*/
  margin-left: 60px;
  padding-bottom: 10px;
}

.me-message-icon {
  padding: 3px 8px;
}

.me-message-count {
  color: #a6a6a6;
  padding-left: 14px;
  font-size: 13px;
}

.me-create-date {

  font-size: 10px;
  float: right;
}

.me-pull-right {
  float: right;
}

.me-message-description {
  margin-left: 80px;
  font-size: 13px;
  line-height: 24px;
  margin-bottom: 10px;
}

.me-message-author {
  color: #a6a6a6;
  padding-right: 18px;
  font-size: 13px;
}

.me-header-picture-message {
  width: 36px;
  height: 36px;
  border: 1px solid #ddd;
  border-radius: 50%;
  vertical-align: middle;
  background-color: #5fb878;
  margin-top: -30px;
}

.el-tag {
  margin-left: 6px;
}

.me-message-userInfo {
  margin-top: 0px;
  float: left;

}


</style>
