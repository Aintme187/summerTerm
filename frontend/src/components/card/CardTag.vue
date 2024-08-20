<template>
  <el-card :body-style="{ padding: '8px 18px' }">
    <div slot="header" class="me-tag-header">
      <span>最热标签</span>
    </div>

    <ul class="me-tag-list">
      <li class="me-tag-item" v-for="t in tags" :key="t.id">
        <!--type="primary"-->
        <el-button @click="() => tag(t.id)" size="small" type="primary" round plain>{{t.tagName}}</el-button>
      </li>
    </ul>
  </el-card>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';

// 使用defineProps定义props
const props = defineProps({
  tags: Array
});

// 从vue-router中解构出所需的函数
const route = useRoute();
const router = useRouter();

// 定义一个方法来处理查看全部标签的路由跳转
const moreTags = () => {
  router.push('/blog/tag/all');
};

// 定义一个方法来处理点击特定标签的路由跳转
const tag = (id) => {
  router.push({ path: `/blog/tag/${id}` });
};
</script>

<style scoped>
.me-tag-header {
  font-weight: 600;
}

.me-tag-more {
  font-size: 14px;
  color: #78b6f7;
}

.me-tag-list {
  list-style-type: none;
  height: 100px;
}

.me-tag-item {
  display: inline-block;
  padding: 4px;
  font-size: 14px;
  color: #5FB878;
}

.me-tag-item a:hover {
  text-decoration: underline;
}
</style>