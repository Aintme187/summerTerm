<script setup>
import PageContainer from '@/components/PageContainer.vue'
import {onMounted, ref} from 'vue'
import {
  showScApplication,
  showAssistantApplication,
  modifyCourseParticipants,
  modifyCourseAssistants
} from '@/api/teacher.js'
import {useStore} from 'vuex'
import {Grid, User, Menu, Avatar} from "@element-plus/icons-vue"
import {ElMessage} from "element-plus";

const store = useStore()
//userStore.setUserId(12)
const teacherId = store.state.id
const activeName = "ScApplication"
const scTreeRef = ref()
const assistantTreeRef = ref()
const scApplications = ref([])
const assistantApplications = ref([])
const scByCourse = ref();
const assistantByCourse = ref();

const getScApplications = async () => {
  const resp = await showScApplication(teacherId);
  scApplications.value = resp.data;
  console.log(scApplications);
  scByCourse.value = scApplications.value.reduce((accumulator, currentItem) => {
    // 如果 accumulator 中还没有这个 courseId，就添加一个新的课程对象
    if (!accumulator.find(item => item.courseId === currentItem.courseId)) {
      accumulator.push({
        courseId: currentItem.courseId,
        label: currentItem.courseName,
        applications: []
      });
    }
    // 找到对应的课程对象，并添加应用信息
    const course = accumulator.find(item => item.courseId === currentItem.courseId);
    course.applications.push({
      scId: currentItem.scId,
      studentId: currentItem.studentId,
      label: currentItem.studentName
    });
    return accumulator;
  }, []);
}

const getAssistantApplications = async () => {
  const resp = await showAssistantApplication(teacherId);
  assistantApplications.value = resp.data;
  assistantByCourse.value = assistantApplications.value.reduce((accumulator, currentItem) => {
    // 如果 accumulator 中还没有这个 courseId，就添加一个新的课程对象
    if (!accumulator.find(item => item.courseId === currentItem.courseId)) {
      accumulator.push({
        courseId: currentItem.courseId,
        label: currentItem.courseName,
        applications: []
      });
    }
    // 找到对应的课程对象，并添加应用信息
    const course = accumulator.find(item => item.courseId === currentItem.courseId);
    course.applications.push({
      assistantId: currentItem.assistantId,
      studentId: currentItem.studentId,
      label: currentItem.studentName
    });
    return accumulator;
  }, []);
}

onMounted(() => {
  getScApplications();
  getAssistantApplications();
})

const operateSc = async (scIdList, op) => {
  // await scIdList.forEach(scId => {
  //   const d = scApplications.value.find(app => app.scId === scId);
  //   const res = modifyCourseParticipants(d.courseId, d.studentId, op);
  //   console.log('had added ' + d.studentId);
  // });
  // 使用 Promise.all 来等待所有的异步操作完成
  await Promise.all(scIdList.map(scId => {
    return new Promise(async (resolve) => {
      const d = scApplications.value.find(app => app.scId === scId);
      if (d) {
        const res = await modifyCourseParticipants(d.courseId, d.studentId, op);
        console.log('had added ' + d.studentId);
        resolve(res);
      }
    });
  }));
  await getScApplications();
  if (op) {
    ElMessage('学生添加成功！');
  } else {
    ElMessage('已拒绝');
  }
}

const operateAssistant = async (assistantIdList, op) => {
  // assistantIdList.forEach(assistantId => {
  //   const d = assistantApplications.value.find(app => app.assistantId === assistantId);
  //   modifyCourseAssistants(d.courseId, d.studentId, op);
  // });
  // 使用 Promise.all 来等待所有的异步操作完成
  await Promise.all(assistantIdList.map(assistantId => {
    return new Promise(async (resolve) => {
      const d = assistantApplications.value.find(app => app.assistantId === assistantId);
      if (d) {
        const res = await modifyCourseAssistants(d.courseId, d.studentId, op);
        console.log('had agreed ' + d.studentId + ' as an assistant');
        resolve(res);
      }
    });
  }));
  await getAssistantApplications();
  if (op) {
    ElMessage('助教审批通过！');
  } else {
    ElMessage('已拒绝');
  }
}

const defaultProps = {
  label: 'label',
  children: 'applications'
}
const handleCheckChange = (data, checked, indeterminate) => {
  //console.log(data)
  // const checkedNodes = scTreeRef.value.getCheckedNodes(true);
  // console.log(checkedNodes);
}
</script>

<template>
  <PageContainer title="成员管理">
    <el-tabs v-model="activeName" class="demo-tabs">
      <el-tab-pane label="学生选课申请" name="ScApplication">
        <el-tree
            style="width: 600px"
            :props="defaultProps"
            :data="scByCourse"
            show-checkbox
            @checkChange="handleCheckChange"
            ref="scTreeRef"
        >
          <template #default="{ node, data }" style="margin-bottom: 10px">
            <span v-if="!node.isLeaf">
              <el-icon v-if="node.expanded" style="margin: 0 6px 0 2px;" size="16"><Grid/></el-icon>
              <el-icon v-else style="margin: 0 6px 0 2px;" size="16"><Menu/></el-icon>
              <span style="font-size: 16px">{{ node.label }}</span>
            </span>
            <span v-else class="custom-tree-node">
              <span>
                <el-icon style="margin: 0 6px 0 2px;" size="16"><User/></el-icon>
                <span style="font-size: 14px">{{ node.label }}</span>
              </span>
              <span>
                <el-button type="primary" @click="operateSc([data.scId], true)"> 同意 </el-button>
                <el-button type="danger" style="margin-left: 8px;"
                           @click="operateSc([data.scId], false)"> 拒绝 </el-button>
              </span>
            </span>
          </template>
        </el-tree>
        <br>
        <span style="bottom: auto">
          <el-button type="primary"
                     @click="operateSc(scTreeRef.getCheckedNodes(true).map(node=>node.scId), true)">批量同意</el-button>
          <el-button type="danger" style="margin-left: 12px"
                     @click="operateSc(scTreeRef.getCheckedNodes(true).map(node=>node.scId), false)">批量拒绝</el-button>
        </span>
      </el-tab-pane>

      <el-tab-pane label="助教申请" name="AssistantApplication">
        <el-tree
            style="max-width: 600px"
            :props="defaultProps"
            :data="assistantByCourse"
            show-checkbox
            node-key="assistantId"
            @checkChange="handleCheckChange"
            ref="assistantTreeRef"
        >
          <template #default="{ node, data }">
            <span v-if="!node.isLeaf">
              <el-icon v-if="node.expanded" style="margin: 0 6px 0 2px;" size="16"><Grid/></el-icon>
              <el-icon v-else style="margin: 0 6px 0 2px;" size="16"><Menu/></el-icon>
              <span style="font-size: 16px">{{ node.label }}</span>
            </span>
            <span v-else class="custom-tree-node">
              <span>
                <el-icon style="margin: 0 6px 0 2px;" size="16"><Avatar/></el-icon>
                <span style="font-size: 14px">{{ node.label }}</span>
              </span>
              <span>
                <el-button type="primary" @click="operateAssistant([data.assistantId], true)"> 同意 </el-button>
                <el-button type="danger" style="margin-left: 8px;" @click="operateAssistant([data.assistantId], false)"> 拒绝 </el-button>
              </span>
            </span>
          </template>
        </el-tree>
        <br>
        <span style="bottom: auto">
          <el-button type="primary"
                     @click="operateAssistant(assistantTreeRef.getCheckedNodes(true).map(node=>node.assistantId), true)">批量同意</el-button>
          <el-button type="danger" style="margin-left: 12px"
                     @click="operateAssistant(assistantTreeRef.getCheckedNodes(true).map(node=>node.assistantId), false)">批量拒绝</el-button>
        </span>
      </el-tab-pane>
    </el-tabs>

  </PageContainer>
</template>

<style scoped lang="scss">
.custom-tree-node {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
  padding-right: 8px;
}
</style>
