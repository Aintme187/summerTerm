<script setup>
import {onMounted, ref} from "vue";
import {rules} from "@/utils/validator.js";
import {contain, string2ints} from "@/utils/typeUtils.js";
import {useRouter} from "vue-router";
import {batchUpdateCurriculumsApi} from "@/api/adminCurriculum.js";
import {ElMessage} from "element-plus";
import {cloneDeep} from "lodash";

const router = useRouter()
const formRef = ref()
// account 不能批量编辑
const curriculum = ref({
  "id": null,
  "name": "",
  "credit": 0,
  "introduction": "",
})
const changedColumns = ref([])
const column2label = {
  "name": "课程组名称",
  "credit": "学分",
  "introduction": "简介",
}

function submit() {
  formRef.value.validate((valid) => {
    if (valid) {
      let curriculumClone = cloneDeep(curriculum.value)
      for (let key in curriculum.value) {
        if (!contain(changedColumns.value, key)) {
          curriculumClone[key] = null
        }
      }
      batchUpdateCurriculumsApi(string2ints(router.currentRoute.value.params["ids"], ","), curriculumClone).then(() => {
        ElMessage({type: "success", message: "成功批量编辑课程组"})
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
    <h1 style="margin-top: 0">编辑id为{{ router.currentRoute.value.params["ids"] }}的课程组信息</h1>
    <h3 style="color: #e6a23c">批量编辑会作用到所有选中的课程组，请选择需要修改的信息</h3>
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
        :model="curriculum"
        status-icon
        :rules="rules"
        label-position="top"
        label-width="auto"
        style="max-width: 40vw"
    >
      <el-form-item label="课程组名称" :prop="contain(changedColumns,'name')?'name':'free'">
        <el-input v-model="curriculum.name" :disabled="!contain(changedColumns,'name')"/>
      </el-form-item>
      <el-form-item label="学分" :prop="contain(changedColumns,'credit')?'credit':'free'">
        <el-input-number :controls="false" v-model="curriculum.credit" :disabled="!contain(changedColumns,'credit')"/>
      </el-form-item>
      <el-form-item label="简介" :prop="contain(changedColumns,'introduction')?'introduction':'free'">
        <el-input v-model="curriculum.introduction" :disabled="!contain(changedColumns,'introduction')"/>
      </el-form-item>
    </el-form>
    <el-button @click="submit" style="margin-bottom: 10px" :disabled="changedColumns.length===0">提交</el-button>
  </div>
</template>

<style scoped>
</style>