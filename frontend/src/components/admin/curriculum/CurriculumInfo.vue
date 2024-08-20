<script setup>
import {onMounted, ref} from "vue";
import {rules} from "@/utils/validator.js";
import {ElMessage} from "element-plus";
import {useRouter} from "vue-router";
import {getCurriculumInfoApi, updateCurriculumApi} from "@/api/adminCurriculum.js";

const router = useRouter()
const formRef = ref()
const curriculum = ref({
  "id": null,
  "name": "",
  "credit": 0,
  "introduction": "",
})

function submit() {
  formRef.value.validate((valid) => {
    if (valid) {
      updateCurriculumApi(curriculum.value).then(() => {
        ElMessage({type: "success", message: "成功编辑课程组"})
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

onMounted(() => {
  getCurriculumInfoApi(router.currentRoute.value.params["id"]).then((value) => {
    curriculum.value = value.data
  }).catch((reason) => {
    ElMessage({type: "error", message: reason})
  })
})
</script>

<template>
  <div style="margin: 0 10px;">
    <h1 style="margin-top: 0">编辑id为{{ router.currentRoute.value.params["id"] }}的课程组信息</h1>
    <el-form
        ref="formRef"
        :model="curriculum"
        status-icon
        :rules="rules"
        label-position="top"
        label-width="auto"
    >
      <el-form-item label="课程组名称" prop="name">
        <el-input v-model="curriculum.name"/>
      </el-form-item>
      <el-form-item label="学分" prop="credit">
        <el-input-number :controls="false" v-model="curriculum.credit"/>
      </el-form-item>
      <el-form-item label="简介" prop="introduction">
        <el-input v-model="curriculum.introduction"/>
      </el-form-item>
    </el-form>
    <el-button @click="submit">提交</el-button>
  </div>
</template>

<style scoped>
</style>