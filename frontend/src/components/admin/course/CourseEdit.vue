<script setup>
import {onMounted, ref} from "vue";
import {deptNames, rules} from "@/utils/validator.js";
import {contain, string2ints} from "@/utils/typeUtils.js";
import {useRouter} from "vue-router";
import {batchUpdateCoursesApi} from "@/api/adminCourse.js";
import {ElMessage} from "element-plus";
import {cloneDeep} from "lodash";

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
const changedColumns = ref([])
const column2label = {
  "curriculumId": "课程组id",
  "capacity": "课程组容量",
  "teacherId": "教师id",
  "deptName": "学院",
  "weekBegin": "开始周",
  "weekEnd": "结束周",
  "day": "周几",
  "sectionBegin": "开始节次",
  "sectionEnd": "结束节次",
  "room": "上课地点",
}

function submit() {
  formRef.value.validate((valid) => {
    if (valid) {
      let courseClone = cloneDeep(course.value)
      for (let key in course.value) {
        if (!contain(changedColumns.value, key)) {
          courseClone[key] = null
        }
      }
      batchUpdateCoursesApi(string2ints(router.currentRoute.value.params["ids"], ","), courseClone).then(() => {
        ElMessage({type: "success", message: "成功批量编辑课程"})
      }).catch((reason) => {
        ElMessage({type: "error", message: reason})
      })
    } else {
      ElMessage({
        type: "error",
        message: "请检查输入信息"
      })
    }
  })
}
</script>

<template>
  <div style="margin: 0 10px;">
    <h1 style="margin-top: 0">编辑id为{{ router.currentRoute.value.params["ids"] }}的课程信息</h1>
    <h3 style="color: #e6a23c">批量编辑会作用到所有选中的课程，请选择需要修改的信息</h3>
    <h3 style="color: #f56c6c">对于非必填的信息，选择修改但是空白也会生效</h3>
    <div style="display: flex">
      <el-checkbox-group v-model="changedColumns" :max="column2label.length" style="margin: 10px 0">
        <el-checkbox-button v-for="(value,key) in column2label" :key="key" :value="key">
          {{ value }}
        </el-checkbox-button>
      </el-checkbox-group>
    </div>
    <el-form
        ref="formRef"
        :model="course"
        status-icon
        :rules="rules"
        label-position="top"
        label-width="auto"
        style="max-width: 40vw"
    >
      <el-form-item label="课程组id" :prop="contain(changedColumns,'curriculumId')?'curriculumId':'free'">
        <el-input-number :controls="false" v-model="course.curriculumId"
                         :disabled="!contain(changedColumns,'curriculumId')"/>
      </el-form-item>
      <el-form-item label="课程组容量" :prop="contain(changedColumns,'capacity')?'capacity':'free'">
        <el-input-number :controls="false" v-model="course.capacity" :disabled="!contain(changedColumns,'capacity')"/>
      </el-form-item>
      <el-form-item label="教师id" :prop="contain(changedColumns,'teacherId')?'teacherId':'free'">
        <el-input-number :controls="false" v-model="course.teacherId" :disabled="!contain(changedColumns,'teacherId')"/>
      </el-form-item>
      <el-form-item label="学院" :prop="contain(changedColumns,'deptName')?'deptName':'free'">
        <el-select v-model="course.deptName" placeholder="请选择学院"
                   :disabled="!contain(changedColumns,'deptName')">
          <el-option v-for="(value,key) in deptNames" :key="key" :label="value" :value="value"/>
        </el-select>
      </el-form-item>
      <el-form-item label="开始周" :prop="contain(changedColumns,'weekBegin')?'weekBegin':'free'">
        <el-input-number :controls="false" v-model="course.weekBegin" :disabled="!contain(changedColumns,'weekBegin')"/>
      </el-form-item>
      <el-form-item label="结束周" :prop="contain(changedColumns,'weekEnd')?'weekEnd':'free'">
        <el-input-number :controls="false" v-model="course.weekEnd" :disabled="!contain(changedColumns,'weekEnd')"/>
      </el-form-item>
      <el-form-item label="周几" :prop="contain(changedColumns,'day')?'day':'free'">
        <el-input-number :controls="false" v-model="course.day" :disabled="!contain(changedColumns,'day')"/>
      </el-form-item>
      <el-form-item label="开始节次" :prop="contain(changedColumns,'sectionBegin')?'sectionBegin':'free'">
        <el-input-number :controls="false" v-model="course.sectionBegin"
                         :disabled="!contain(changedColumns,'sectionBegin')"/>
      </el-form-item>
      <el-form-item label="结束节次" :prop="contain(changedColumns,'sectionEnd')?'sectionEnd':'free'">
        <el-input-number :controls="false" v-model="course.sectionEnd"
                         :disabled="!contain(changedColumns,'sectionEnd')"/>
      </el-form-item>
      <el-form-item label="上课地点" :prop="contain(changedColumns,'room')?'room':'free'">
        <el-input-number :controls="false" v-model="course.room" :disabled="!contain(changedColumns,'room')"/>
      </el-form-item>
    </el-form>
    <el-button @click="submit" style="margin-bottom: 10px" :disabled="changedColumns.length===0">提交</el-button>
  </div>
</template>

<style scoped>
</style>