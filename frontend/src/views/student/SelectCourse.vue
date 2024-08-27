<script setup>

import PageContainer from '@/components/PageContainer.vue'
import {applyForCourseEnrollmentService, getCoursesApi, applyToBeAssistantService} from '@/api/student'

import {onMounted, ref, watch} from "vue";
import {ElMessage, ElMessageBox} from "element-plus";
import FilterDialog from "@/components/admin/layout/FilterDialog.vue";
import { useStore } from 'vuex';

const isLoading = ref(false)

const CoursesRef = ref()
// const router = useRouter()
const filterDialogRef = ref()
const columns = {
  "curriculumName": {"type": "string", "label": "课程名称"},
  "credit": {"type": "double", "label": "学分"},
  "introduction": {"type": "string", "label": "课程简介"},
  "enrollment": {"type": "integer", "label": "选课人数"},
  "capacity": {"type": "integer", "label": "课程容量"},
  "teacherName": {"type": "string", "label": "教师名称"},
  "deptName": {"type": "string", "label": "学院"},
  "weekBegin": {"type": "integer", "label": "开始周"},
  "weekEnd": {"type": "integer", "label": "结束周"},
  "day": {"type": "integer", "label": "周几"},
  "sectionBegin": {"type": "integer", "label": "开始节次"},
  "sectionEnd": {"type": "integer", "label": "结束节次"},
  "room": {"type": "string", "label": "上课地点"},
}
const enum2label = {}
/**
 * 筛选条件
 * @type {Ref<UnwrapRef<*[{filter: string, name: string, content: string}]>>}
 */
const filterData = ref([])
const total = ref()
const page = ref(1)
watch(page, async (value) => {
  getCourses()
})
const pageSize = ref(10)
watch(pageSize, async (value) => {
  getCourses()
})
const courseData = ref()
const excludeFull = ref(false)
const excludeConflict = ref(false)
const store = useStore()
const userId = store.state.id

function getCourses() {
  getCoursesApi(page.value, pageSize.value, filterData.value, excludeFull.value, excludeConflict.value).then((value) => {
    total.value = value.data["studentLearningCourseInfoVoCount"]
    courseData.value = value.data["studentLearningCourseInfoVoList"]
  }).catch((reason) => {
    ElMessage({
      type: "error",
      message: "服务器压力太大了！！！！！！",
    })
  })
}

// 处理选课
const handleSelectCourse = async (row) => {
  // 弹出确认对话框
  ElMessageBox.confirm('确认选这门课吗？', '提示', {
    confirmButtonText: '确认',
    cancelButtonText: '取消'
  })
      .then(async () => {
        // 1.发送选课请求参数
        await applyForCourseEnrollmentService(row.id, userId)
        // 2.如果能拿到数据，返回选课成功
        ElMessage({
          type: 'success',
          message: '选课成功'
        })
        // 3.再次发送请求获取数据（应该要给一个默认界面？）
        getCourses()
      })
      .catch((error) => {
        console.log(error)
        ElMessage({
          type: 'info',
          message: '选课取消'
        })
      })
}

// 处理成为助教
const handleTA = async (row) => {
  // 弹出确认对话框
  ElMessageBox.confirm('确认申请成为助教吗？', '提示', {
    confirmButtonText: '确认',
    cancelButtonText: '取消'
  })
      .then(async () => {
        // 1.发送选课请求参数
        await applyToBeAssistantService(row.id, userId)
        // 2.如果能拿到数据，返回选课成功
        ElMessage({
          type: 'success',
          message: '申请成功'
        })
        // 3.再次发送请求获取数据（应该要给一个默认界面？）
        getCourses()
      })
      .catch((error) => {
        console.log(error)
        ElMessage({
          type: 'info',
          message: '申请取消'
        })
      })
}

onMounted(() => {
  console.log(userId)
  getCourses()
})
</script>

<template>
  <PageContainer title="学生选课页面">
    <template #extra>
      <div class="header">
        <el-button type="primary" style="padding: 10px" @click="filterDialogRef.showFilter()">
          筛选
          <svg t="1715827385633" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
               p-id="2206" width="20" height="20" style="padding-left: 5px">
            <path
                d="M697.6 764.16A379.52 379.52 0 0 1 448 855.04a391.68 391.68 0 0 1-384-395.52A391.68 391.68 0 0 1 448 64a391.68 391.68 0 0 1 387.2 395.52 401.28 401.28 0 0 1-87.04 250.24L947.84 896a39.04 39.04 0 0 1 0 53.76 36.48 36.48 0 0 1-53.12 0l-199.68-185.6zM138.88 459.52a315.52 315.52 0 0 0 311.68 320 315.52 315.52 0 0 0 312.32-320A315.52 315.52 0 0 0 448 140.8a315.52 315.52 0 0 0-311.68 320z"
                fill="#ffffff" p-id="2207"></path>
          </svg>
          <span v-if="filterData.length">&nbsp;{{ filterData.length }}</span>
        </el-button>
      </div>
    </template>

    <!-- 查询课程表格 -->
    <el-table v-loading="isLoading" ref="CoursesRef" :data="courseData" border key="Courses"
              style="margin: 10px 0; flex: 1">
      <el-table-column v-for="(value,key) in columns" :key="key" :prop="key" :label="value.label">
        <template #default="scope">
          {{ value["type"] === "enum" ? enum2label[scope.row[key]] : scope.row[key] }}
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" width="180px">
        <template #default="scope">
          <div style="display:flex">
            <el-button type="primary" size="small" @click="handleSelectCourse(scope.row)">选课</el-button>
            <el-button type="warning" size="small" @click="handleTA(scope.row)">申请成为助教</el-button>
          </div>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination layout="total, sizes, prev, pager, next" :total="total" v-model:current-page="page"
                   v-model:page-size="pageSize"/>
  </PageContainer>
  <filter-dialog ref="filterDialogRef" :columns="columns" :enum2label="enum2label" :filter-data="filterData"
                 @get-data="getCourses()"/>
</template>

<style scoped lang="scss">
.header {
  display: flex;
}
</style>
