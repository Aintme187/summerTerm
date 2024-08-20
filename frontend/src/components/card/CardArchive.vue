<template>
  <el-card :body-style="{ padding: '8px 18px' }">
    <div slot="header" class="me-category-header">
      <span>{{cardHeader}}</span>
    </div>

    <ul class="me-category-list">
      <li v-for="a in archives" :key="a.year + a.month" class="me-category-item"
          @click="() => view(a.year, a.month)">
        <a>{{`${a.year}年${a.month}月(${a.count})`}}</a>
      </li>
    </ul>
  </el-card>
</template>

<script setup>
import { defineProps, ref } from 'vue';
import {useRoute, useRouter} from "vue-router";
import {useStore} from "vuex";

const store = useStore()
const router = useRouter()
const route = useRoute()
// 定义props
const props = defineProps({
  cardHeader: {
    type: String,
    required: true
  },
  archives: {
    type: Array,
    required: true
  }
});

// 定义一个方法来响应点击事件
const view = (year, month) => {
  // 假设有一个router对象可用，并且已经定义了路由
  router.push({ path: `/archives/${year}/${month}` });
};
</script>

<style scoped>
.me-category-header {
  font-weight: 600;
}

.me-category-list {
  list-style-type: none;
}

.me-category-item {
  display: inline-block;
  width: 40%;
  padding: 4px;
  font-size: 14px;
  color: #5FB878;
}

.me-category-item a:hover {
  text-decoration: underline;
}
</style>