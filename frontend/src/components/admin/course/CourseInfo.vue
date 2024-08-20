<script setup>
import {onMounted, ref} from "vue";
import {deptNames, rules} from "@/utils/validator.js";
import {ElMessage} from "element-plus";
import {useRouter} from "vue-router";
import {addCourseApi, getCourseInfoApi, updateCourseApi} from "@/api/adminCourse.js";

const router = useRouter()
const formRef = ref()
const course = ref({
  "id": null,
  "curriculumId": 0,
  "enrollment": null,
  "capacity": 0,
  "teacherId": 0,
  "deptName": "",
  "weekBegin": 0,
  "weekEnd": 0,
  "day": 0,
  "sectionBegin": 0,
  "sectionEnd": 0,
  "room": "",
})

function submit() {
  formRef.value.validate((valid) => {
    if (!valid) {
      ElMessage({type: "error", message: "请检查输入信息"})
    } else if (course.value.weekBegin > course.value.weekEnd) {
      ElMessage({type: "error", message: "开始周不能大于结束周"})
    } else if (course.value.sectionBegin > course.value.sectionEnd) {
      ElMessage({type: "error", message: "开始节次不能大于结束节次"})
    } else {
      updateCourseApi(course.value).then(() => {
        ElMessage({type: "success", message: "成功编辑课程"})
      }).catch((reason) => {
        ElMessage({type: "error", message: reason})
      })
    }
  })
}

onMounted(() => {
  getCourseInfoApi(router.currentRoute.value.params["id"]).then((value) => {
    course.value = value.data
  }).catch((reason) => {
    ElMessage({type: "error", message: reason})
  })
})
</script>

<template>
  <div style="margin: 0 10px;">
    <h1 style="margin-top: 0">编辑id为{{ router.currentRoute.value.params["id"] }}的课程信息</h1>
    <el-form
        ref="formRef"
        :model="course"
        status-icon
        :rules="rules"
        label-position="top"
        label-width="auto"
    >
      <!--prop 和 v-model 需要对应-->
      <el-form-item label="课程组id" prop="curriculumId">
        <el-input-number :controls="false" v-model="course.curriculumId"/>
      </el-form-item>
      <el-form-item label="课程组容量" prop="capacity">
        <el-input-number :controls="false" v-model="course.capacity"/>
      </el-form-item>
      <el-form-item label="教师id" prop="teacherId">
        <el-input-number :controls="false" v-model="course.teacherId"/>
      </el-form-item>
      <el-form-item label="学院" prop="deptName">
        <el-select v-model="course.deptName" placeholder="请选择学院">
          <el-option v-for="(value,key) in deptNames" :key="key" :label="value" :value="value"/>
        </el-select>
      </el-form-item>
      <el-form-item label="开始周" prop="weekBegin">
        <el-input-number :controls="false" v-model="course.weekBegin"/>
      </el-form-item>
      <el-form-item label="结束周" prop="weekEnd">
        <el-input-number :controls="false" v-model="course.weekEnd"/>
      </el-form-item>
      <el-form-item label="周几" prop="day">
        <el-input-number :controls="false" v-model="course.day"/>
      </el-form-item>
      <el-form-item label="开始节次" prop="sectionBegin">
        <el-input-number :controls="false" v-model="course.sectionBegin"/>
      </el-form-item>
      <el-form-item label="结束节次" prop="sectionEnd">
        <el-input-number :controls="false" v-model="course.sectionEnd"/>
      </el-form-item>
      <el-form-item label="上课地点" prop="room">
        <el-input :controls="false" v-model="course.room"/>
      </el-form-item>
    </el-form>
    <el-button @click="submit">提交</el-button>
  </div>
</template>

<style scoped>
</style>