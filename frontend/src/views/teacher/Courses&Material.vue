<script setup>
import PageContainer from '@/components/PageContainer.vue'
import {ref, reactive, onMounted} from 'vue'
import {useUserStore} from '@/stores/user'
import {getTeachingCourses, getMaterials, submitMaterial} from '@/api/teacher'
import CourseMark from "@/views/teacher/assignmentManager.vue";
import {downloadMaterialService} from "@/api/student.js";
import { useRouter } from "vue-router";
import { useStore } from 'vuex'

const store = useStore()
const teacherId = store.state.id
//userStore.setUserId(12);
const router = useRouter();
const courses = ref([]);
const materials = ref([]);
const currentId = ref('');
const dialogTableVisible = ref(false);
const getCourses = async () => {
  const resp = await getTeachingCourses(teacherId);
  courses.value = resp.data;
}
onMounted(()=>{
  getCourses();
});

const navigateToAssignment = (courseId) => {
  router.push({path: '/teacher/assignmentManage', query: {courseId}}); // 将'/target-page'替换为你想跳转的页面路径
}

const navigateToCategory = (categoryId) => {
  router.push({path: '/blog/category/' + categoryId}); // 将'/target-page'替换为你想跳转的页面路径
}

const openMaterials = async (courseId) => {
  const resp = await getMaterials(courseId);
  materials.value = resp.data;
  dialogTableVisible.value = true;
  currentId.value = courseId;
}
const submit = async (file) => {
  const response = await submitMaterial(currentId.value, file.raw);
  //console.log(response);
  await openMaterials(currentId.value);
}

const download = async (materialId) => {
  try {
    console.log("in download");
    // 调用服务生成下载 URL
    const response = await downloadMaterialService(materialId);
    console.log(response);
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
</script>

<template>
  <PageContainer title="课程管理">
    <div>
      <!-- 课程列表 -->
      <el-row :gutter="20">
        <el-col :span="24" v-for="(course, index) in courses" :key="index">
          <el-card shadow="hover" class="course-card">
            <div class="course-item">
              <div class="course-index">
                <img src="../../assets/飞机航班.png" width="30" alt="课程标识"/>
                <span>{{ index + 1 }}</span>
              </div>
              <div class="course-details">
                <p class="course-name">{{ course.courseName }}</p>
                <div class="course-info">
                  <span class="teacher-info">教师：{{ course.teacherName }}</span>
                  <span class="dept-info">学院：{{ course.deptName }}</span>
                </div>
              </div>
              <div class="course-actions">
                <el-button type="primary" @click="openMaterials(course.courseId)">课程资料</el-button>
                <el-button type="primary" @click="navigateToAssignment(course.courseId)">课程作业</el-button>
                <el-button type="primary" @click="navigateToCategory(course.categoryId)">讨论区</el-button>
              </div>
              <div class="course-students">
                <span>学生人数：{{ course.enrollment }}/{{ course.capacity }}人</span>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>>
  </PageContainer>

  <!-- 课程资料展示 -->
  <el-dialog
      title="课程资料"
      v-model="dialogTableVisible"
      @update:visible="val => dialogTableVisible = val"
      width="800"
      :append-to-body="true"
      center>
    <el-table :data="materials">
      <el-table-column property="name" label="名称" width="auto">
        <template v-slot:default="scope">
          <el-button @click="download(scope.row.id)" style="color: blue; cursor: pointer;">
            {{ scope.row.name }}
          </el-button>
        </template>
      </el-table-column>
      <el-table-column property="size" label="文件大小"/>
      <el-table-column property="time" label="上传时间" width="150"/>
    </el-table>
    <br>
    <span slot="footer" class="dialog-footer" >
      <el-upload
          class="upload-demo"
          :on-change="(file) => submit(file)"
          :auto-upload="false"
      >
        <template #trigger>
          <el-button type="success">上传资料</el-button>
        </template>
        <template #tip>
          <div class="el-upload__tip">
            文件大小不超过20MB
          </div>
        </template>
      </el-upload>
    </span>
  </el-dialog>
</template>

<style scoped lang="scss">
.course-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 20px;
  border-bottom: 1px solid #ebeef5;
}
.course-card {
  margin-bottom: 15px;
  transition: transform 0.3s;
}
.course-card:hover {
  transform: scale(1.05);
}
.course-index {
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #ff6f61;
  color: white;
  width: 50px;
  height: 50px;
  border-radius: 50%;
  font-size: 18px;
  font-weight: bold;
}
.course-details {
  flex: 1;
  padding-left: 20px;
}
.course-name {
  font-size: 20px;
  font-weight: bold;
  margin: 0;
}
//.course-info {
//  display: flex;
//  justify-content: space-between;
//  margin-top: 5px;
//}
.course-actions {
  display: flex;
  gap: 10px;
  align-items: center;
}
.course-students {
  font-size: 14px;
  color: #666;
  margin-left: 20px;
}
.course-details {
  flex: 1;
  padding-left: 20px;
  display: flex;
  flex-direction: column;
  justify-content: center;
}
.course-info {
  display: flex;
  //justify-content: space-between;
  margin-top: 10px;
}
.teacher-info {
  font-size: 14px;
  color: #666;
  margin-right: 10px;
}
.dept-info {
  font-size: 14px;
  color: #666;
  //margin-right: 600px;
}
</style>