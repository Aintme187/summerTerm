<script setup>
import { defineComponent, onMounted, ref } from 'vue';
import { format } from 'date-fns';
import { useUserStore } from '@/stores/user';
import { useRoute } from 'vue-router'
import PageContainer from '@/components/PageContainer.vue'
import { downloadAssignmentService } from '@/api/student';
import { getAssignments, getCourseAssignments, releaseAssignment, showSubmission, downloadSubmission, correctSubmission } from '@/api/teacher.js'
import {ElMessage} from "element-plus";
import { useStore } from 'vuex'

const store = useStore();
const userId = store.state.id;
const route = useRoute();
const courseId = route.query.courseId;
const assignments = ref([]);
const addDialogVisible = ref(false);
const submitDialogVisible = ref(false);
const correctDialogVisible = ref(false);
const nameIn = ref('');
const descriptionIn = ref('');
const beginEndIn = ref([new Date(), null]);
const assignmentFile = ref();
const submissions = ref([]);
const currentSub = ref();
const scoreIn = ref();
const commentIn = ref('');
const feedbackFile = ref();

const handleUploadSuccess = (response, file, fileList) => {
  console.log('上传成功：', response, file, fileList);
  // 处理上传成功后的逻辑，这里可以更新作业状态
};

const getSubmissions = async (assignmentId) => {
  submitDialogVisible.value = true;
  const resp = await showSubmission(assignmentId);
  submissions.value = resp.data;
}

const correct = (submissionId) => {
  currentSub.value = submissionId;
  correctDialogVisible.value = true;
}

const feedback = async () => {
  await correctSubmission(currentSub.value, scoreIn.value, feedbackFile.value, commentIn.value);
  correctDialogVisible.value = false;
  ElMessage('成功提交批改！');
}

const getTeacherAssignments = async () => {
  const resp = await getAssignments(userId);
  assignments.value = resp.data;
  //console.log(assignments.value);
}

const getCourseAssign = async () => {
  const resp = await getCourseAssignments(courseId);
  assignments.value = resp.data;
}

const newAssignment = async () => {
  //console.log(nameIn, descriptionIn, beginEndIn.value[0], beginEndIn.value[1], courseId, assignmentFile)
  const formattedBegin = format(beginEndIn.value[0], "yyyy-MM-dd'T'HH:mm:ss");
  const formattedEnd = format(beginEndIn.value[1], "yyyy-MM-dd'T'HH:mm:ss");
  await releaseAssignment(nameIn.value, descriptionIn.value, formattedBegin, formattedEnd, courseId, assignmentFile.value);
  await getCourseAssign();
  addDialogVisible.value = false;
  ElMessage('作业发布成功！');
}

const downloadAssignment = async (assignmentId) => {
  try {
    // 调用服务生成下载 URL
    const response = await downloadAssignmentService(assignmentId);
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

const downloadSub = async (submissionId) => {
  try {
    // 调用服务生成下载 URL
    const response = await downloadSubmission(submissionId);
    const url = response.data.data; // 假设返回的 JSON 中有一个 data 字段包含 URL

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

const shortcuts = [
  {
    text: '现在起一周',
    value: () => {
      const start = new Date()
      const end = new Date()
      end.setDate(end.getDate() + 7)
      return [start, end]
    },
  },
  {
    text: '现在起两周',
    value: () => {
      const start = new Date()
      const end = new Date()
      end.setDate(end.getDate() + 14)
      return [start, end]
    },
  },
  {
    text: '现在起一个月',
    value: () => {
      const start = new Date()
      const end = new Date()
      end.setMonth(end.getMonth() + 1)
      return [start, end]
    },
  },
]

onMounted(() => {
  //console.log(courseId);
  if (courseId) {
    getCourseAssign();
  }else {
    getTeacherAssignments();
  }
});
</script>

<template>
  <PageContainer title="作业管理">
    <div>
      <!-- 作业列表 -->
      <el-row :gutter="20">
        <el-col :span="24">
          <el-card class="assignment-item" v-for="(assignment, index) in assignments" :key="index">
            <p>作业名称： {{ assignment.name }}</p>
            <p>作业内容： {{ assignment.description }}</p>
            <p>
              作业附件：
              <span v-if="assignment.fileName" @click="downloadAssignment(assignment.id)" style="color: blue; cursor: pointer;">
                    {{ assignment.fileName }}
                  </span>
              <span v-else>无</span>
            </p>
            <el-button type="primary" @click="getSubmissions(assignment.id)">查看作业提交</el-button>
          </el-card>
        </el-col>
      </el-row>
    </div>
    <el-button type="success" @click="addDialogVisible = true">发布作业</el-button>
  </PageContainer>

  <!-- 查看提交的页面 -->
  <el-dialog
      title="学生作业"
      v-model="submitDialogVisible"
      @update:visible="val => submitDialogVisible = val"
      width="1000"
      :append-to-body="true"
      center>
    <el-table :data="submissions">
      <el-table-column property="studentName" label="提交者" width="auto" />
      <el-table-column property="fileName" label="文件名称">
        <template v-slot:default="scope">
          <el-button @click="downloadSub(scope.row.id)" style="color: blue; cursor: pointer;">
            {{ scope.row.fileName }}
          </el-button>
        </template>
      </el-table-column>
      <el-table-column property="time" label="上传时间" width="150"/>
      <el-table-column label="批阅" width="150">
        <template v-slot:default="scope">
          <el-button type="primary" @click="correct(scope.row.id)">批阅结果</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-dialog>

  <!-- 作业反馈的界面 -->
  <el-dialog
      title="作业反馈"
      v-model="correctDialogVisible"
      @update:visible="val => correctDialogVisible = val"
      width="800"
      :append-to-body="true"
      center>
    <span class="dialog-body">
      分数：
      <el-input
          v-model="scoreIn"
          style="width: 240px"
          placeholder="请输入分数"
          clearable
      />
    </span>
    <br>
    <br>
    <span class="dialog-body">
      评语：
      <el-input
          v-model="commentIn"
          style="width: 360px"
          :autosize="{ minRows: 2, maxRows: 8 }"
          type="textarea"
          placeholder="请输入评语"
          clearable
      />
    </span>
    <br>
    <br>
    <span class="dialog-body">
      反馈文件：
      <el-upload
          class="upload-demo"
          :on-change="(file) => {feedbackFile = file.raw}"
          :auto-upload="false"
      >
        <template #trigger>
          <el-button type="success">上传反馈文件</el-button>
        </template>
        <template #tip>
          <div class="el-upload__tip">
            文件大小不超过20MB
          </div>
        </template>
      </el-upload>
    </span>
    <br>
    <br>
    <template #footer>
      <el-button type="primary" @click="feedback">提交</el-button>
    </template>
  </el-dialog>

  <!-- 发布作业的界面 -->
  <el-dialog
      title="发布作业"
      v-model="addDialogVisible"
      @update:visible="val => addDialogVisible = val"
      width="600"
      :append-to-body="true"
      center>
    <span class="dialog-body">
      名称：
      <el-input
          v-model="nameIn"
          style="width: 240px"
          placeholder="请输入作业名称"
          clearable
      />
    </span>
    <br>
    <br>
    <span class="dialog-body">
      描述：
      <el-input
          v-model="descriptionIn"
          style="width: 480px"
          :autosize="{ minRows: 4, maxRows: 12 }"
          type="textarea"
          placeholder="请输入作业描述"
          clearable
      />
    </span>
    <br>
    <br>
    <span class="dialog-body">
      起始时间：
      <el-date-picker
          v-model="beginEndIn"
          type="datetimerange"
          :shortcuts="shortcuts"
          range-separator="至"
          start-placeholder="开始时间"
          end-placeholder="截止时间"
          style="width: 180px"
      />
    </span>
    <br>
    <br>
    <span class="dialog-body">
      添加作业文件：
      <el-upload
          class="upload-demo"
          :on-change="(file) => {assignmentFile = file.raw}"
          :auto-upload="false"
      >
        <template #trigger>
          <el-button type="success">上传作业文件</el-button>
        </template>
        <template #tip>
          <div class="el-upload__tip">
            文件大小不超过20MB
          </div>
        </template>
      </el-upload>
    </span>
    <br>
    <br>
    <template #footer>
      <el-button v-if="courseId" type="primary" @click="newAssignment">发布</el-button>
    </template>
  </el-dialog>
</template>

<style scoped lang="scss">
.assignment-item {
  font-family: Arial, sans-serif;
  font-size: 16px;
  margin-bottom: 20px;
}
.el-upload__tip {
  color: #909399;
}
.dialog-body {
  font-weight: bolder;
}
</style>