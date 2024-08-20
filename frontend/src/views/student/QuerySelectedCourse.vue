<script setup>
import PageContainer from '@/components/PageContainer.vue'
import { ref, onMounted } from 'vue'
import { getSelectedCoursesService, dropCourseService } from '@/api/student'
import { formatTime } from '@/utils/format'
import {ElMessageBox} from "element-plus";
import { useStore } from 'vuex';

const courseList = ref([])
const isLoading = ref(false)
const store = useStore()
const userId = store.state.id

//查询课程，赋值到表格里
const getHasSelectCourseList = async (studentId) => {
  isLoading.value = true
  const resp = await getSelectedCoursesService(studentId)
  courseList.value = resp.data
  console.log(courseList.value)
  isLoading.value = false
}

onMounted(()=>{
  getHasSelectCourseList(userId)
})

// 处理退课
const dropCourse = async (row) => {
  ElMessageBox.confirm('确认退选这门课吗？', '提示', {
    confirmButtonText: '确认',
    cancelButtonText: '取消'
  })
    .then(async () => {
      // 1.发送退课请求参数带上学生学号 + 课程号
      await dropCourseService(userId, row.courseId)
      // 2.如果能拿到数据，返回选课成功
      ElMessage({
        type: 'success',
        message: '退课成功'
      })
      // 3.再次发送请求获取数据
      getHasSelectCourseList(userId)
    })
    .catch(() => {
      ElMessage({
        type: 'info',
        message: '退课取消'
      })
    })
}
</script>

<template>
  <PageContainer title="已选课程页面">
    <template #extra>
      <div class="header">
      </div>
    </template>

    <!-- 查询课程表格 -->
    <el-table v-loading="isLoading" :data="courseList" style="width: 100%">
      <el-table-column prop="courseId" label="课程号" width="100"></el-table-column>
      <el-table-column prop="courseName" label="课程名"></el-table-column>
      <el-table-column prop="teacherName" label="主讲教师"></el-table-column>
      <el-table-column prop="startTime" label="开课时间" width="100">
        <template #default="{ row }">
          {{ formatTime(row.startTime) }}
        </template>
      </el-table-column>
      <el-table-column prop="endTime" label="结课时间" width="100">
        <template #default="{ row }">
          {{ formatTime(row.endTime) }}
        </template>
      </el-table-column>
      <el-table-column prop="credit" label="学分"></el-table-column>
      <el-table-column prop="enrollment" label="已选人数"></el-table-column>
      <el-table-column prop="capacity" label="最大人数"></el-table-column>
      <el-table-column prop="room" label="地点"></el-table-column>
      <el-table-column prop="weekBegin" label="开课周次"></el-table-column>
      <el-table-column prop="weekEnd" label="结课周次"></el-table-column>
      <el-table-column prop="sectionBegin" label="上课节次">
        <template #default="{ row }"> 第{{ row.sectionBegin }}节 </template>
      </el-table-column>
      <el-table-column prop="sectionEnd" label="下课节次">
        <template #default="{ row }"> 第{{ row.sectionEnd }}节 </template>
      </el-table-column>
      <el-table-column label="操作" width="150">
        <template #default="scope">
          <el-button
            type="primary"
            @click="dropCourse(scope.row)"
            >退课</el-button
          >
          <!--<el-text type="primary" v-else>得分:{{ scope.row.mark }}</el-text>-->
        </template>
      </el-table-column>

      <template #empty>
        <el-empty description="没有数据"></el-empty>
      </template>
    </el-table>
  </PageContainer>
</template>

<style scoped lang="scss">
.header {
  display: flex;
}
</style>
