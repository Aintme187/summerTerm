<script setup>
import { defineComponent, onMounted, ref } from 'vue';
import { useRoute } from 'vue-router'
import PageContainer from '@/components/PageContainer.vue'
import { getAssignmentsService, submitAssignmentService, downloadAssignmentService } from '@/api/student';
import { useStore } from 'vuex';

const store = useStore();
const userId = store.state.id;
const route = useRoute();
const courseId = route.query.courseId;
const assignments = ref([]);

const getAssignments = async () => {
  const resp = await getAssignmentsService(courseId, userId);
  assignments.value = resp.data;
  console.log(assignments.value);
}

const submitAssignment = async (assignmentId, file) => {
    await submitAssignmentService(userId, assignmentId, file.raw);
    await getAssignments();
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

onMounted(() => {
    getAssignments();
});
</script>

<template>
    <PageContainer title="课程作业">
      <div>
        <!-- 作业列表 -->
        <el-row :gutter="20">
          <el-col :span="24">
            <el-card shadow="hover" class="assignment-card" v-for="(assignment, index) in assignments" :key="index">
              <div class="assignment-item">
                <div class="assignment-header">
                  <el-tag v-if="assignment.submission && assignment.submission.feedbackUrl" type="success" class="submission-status">
                    作业已批改
                  </el-tag>
                  <el-tag v-else-if="assignment.submission" type="success" class="submission-status">
                    作业已提交
                  </el-tag>
                  <el-tag v-else type="danger" class="submission-status">
                    作业未提交
                  </el-tag>
                  <p>作业名称： {{ assignment.name }}</p>
                  <p>作业内容： {{ assignment.description }}</p>
                  <p>
                      作业附件：
                      <span v-if="assignment.fileName" @click="downloadAssignment(assignment.id)" style="color: blue; cursor: pointer;">
                        {{ assignment.fileName }}
                      </span>
                      <span v-else>无</span>
                  </p>
                  <p v-if="assignment.submission && assignment.submission.score">作业得分： {{ assignment.submission.score }}</p>
                  <p v-if="assignment.submission && assignment.submission.comment">作业评价： {{ assignment.submission.comment }}</p>
                </div>
                <div class="upload-section">
                  <el-upload
                    class="upload-demo"
                    :auto-upload="false"
                    :on-change="(file) => submitAssignment(assignment.id, file)"
                  >
                    <el-button slot="trigger" type="primary">上传作业</el-button>
                  </el-upload>
                  <div slot="tip" class="el-upload__tip">文件大小不超过20MB</div>
                </div>
              </div>
            </el-card>
          </el-col>
        </el-row>
      </div>
    </PageContainer>
  </template>

<style scoped lang="scss">
.assignment-item {
  overflow: auto;
  font-family: Arial, sans-serif;
  font-size: 16px;
  margin-bottom: 20px;
}
.el-upload__tip {
    color: #909399;
  }
.assignment-card {
    margin-bottom: 5px;
    transition: transform 0.3s;
  }
.upload-section {
    float: right;
    text-align: right;
  }
.assignment-card:hover {
    transform: scale(1.02);
  }
.assignment-header {
    align-items: flex-start;
    float: left;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    align-items: left;
    margin-bottom: 10px;
  }
  .assignment-header p {
    margin: 7px; 
  }
</style>