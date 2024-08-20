<script setup>
import {
  getStudentCourseTableService
} from '@/api/student.js'
import PageContainer from '@/components/PageContainer.vue'
import { onMounted, ref } from 'vue'
import TimeTable from '@/components/TimeTable.vue'
import { useStore } from 'vuex'

const store = useStore()
const userId = store.state.id

//课程表所需要的数据
const events = ref([])
const getTimeTableList = async () => {
  console.log('Fetching data for user ID:', userId)
  const resp = await getStudentCourseTableService(userId)
  console.log('API response:', resp)
  events.value = resp.data
}

onMounted(() => {
  getTimeTableList()
})
</script>

<template>
  <PageContainer title="学习任务">
    <!-- 课程表 -->
    <div class="timetable">
      <div style="margin-top: 20px; font-weight: bold">学生已选课程表</div>
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
