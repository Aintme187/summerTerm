<script setup>
import PageContainer from '@/components/PageContainer.vue'
import { ref, onMounted } from 'vue'
import TimeTable from '@/components/TimeTable.vue'
import {getTeacherCourseTable} from "@/api/teacher.js";
import {useUserStore} from "@/stores/user.js";
import { useStore } from 'vuex'
const store = useStore()
const userId = store.state.id

// 测试使用
//userStore.setUserId(12)
//课程表所需要的数据
const events = ref([])
const getTimeTableList = async () => {
  const resp = await getTeacherCourseTable(userId)
  events.value = resp.data
}

onMounted(() => {
  getTimeTableList()
  console.log(events.value)
})
</script>

<template>
  <PageContainer title="课程表">
    <!-- 课程表 -->
    <div class="timetable">
      <div style="margin-top: 20px; font-weight: bold">教师所教授课程</div>
      <TimeTable
        style="width: 80%; margin-top: 30px"
        :afternoon-length="5"
        :length="14"
        :events="events"
      ></TimeTable>
    </div>
  </PageContainer>
</template>

<style scoped lang="scss">
.timetable {
  display: flex;
  flex-direction: column;
  // align-items: center;
}
</style>
