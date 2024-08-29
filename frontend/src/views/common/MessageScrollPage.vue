<template>
  <div class="me-message-box">
    <scroll-page :loading="loading" :offset="offset" :no-data="noData" @load="load">
      <el-card v-if="noEmpty === false">
        <el-text class="el-card-text">您还没有收到消息</el-text>
      </el-card>
      <message-item v-if="noEmpty === true" v-for="m in messages" :key="m.id" v-bind="m"></message-item>
    </scroll-page>
  </div>

</template>

<script setup>
import { ref, watch, onMounted } from 'vue';
import MessageItem from '@/components/message/MessageItem.vue';
import ScrollPage from '@/components/scrollpage/index.vue';
import {getMessagesApi} from '@/api/message.js';
import {useStore} from "vuex";
import {useRoute, useRouter} from "vue-router";
import {ElMessage} from "element-plus";

const noEmpty = ref(true)
const store = useStore()
const router = useRouter()
const route = useRoute()
// 定义props
const props = defineProps({
  offset: {
    type: Number,
    default: 100
  },
  page: {
    type: Number,
    default: () => ({})
  },
  query: {
    type: Object,
    default: () => ({})
  }
});

// 响应式变量
const loading = ref(false);
const noData = ref(false);
const innerPage = ref({
  pageSize: 100,
  pageNumber: 1
});
const messages = ref([]);

// 观察props的变化
watch(() => props.query, () => {
  noData.value = false;
  messages.value = [];
  innerPage.value.pageNumber = 1;
  getMessages();
}, { deep: true });

watch(() => props.page, () => {
  noData.value = false;
  messages.value = [];
  innerPage.value = props.page;
  getMessages();
}, { deep: true });

// 在组件挂载时获取数据
onMounted(() => {
  getMessages();
});

// 方法
const load = () => {
  getMessages();
};

const getMessages = () => {
  loading.value = true;

  getMessagesApi(innerPage.value, store.state.token).then(data => {
    const newMessages = data.data;
    console.log(data.data.length)
    if(data.data.length === 0) noEmpty.value = false;
    else noEmpty.value = true;
    if (newMessages && newMessages.length > 0) {
      innerPage.value.pageNumber += 1;
      messages.value = [...messages.value, ...newMessages];
    } else {
      noData.value = true;
    }
  }).catch(error => {
    if (error !== 'error') {
      // 假设这是你的全局消息组件
      ElMessage({ type: 'error', message: '服务器压力过大，请稍后再试！！！', showClose: true });
    }
  }).finally(() => {
    loading.value = false;
  });
};
</script>

<style scoped>

.el-card-text{
  justify-content: center;
  align-items: center;
  margin-left: 8vw;

}
.me-message-box{
  border-color: black;
}
.el-card {
  border-radius: 20px;
}

.el-card:not(:first-child) {
  margin-top: 10px;
}
</style>
