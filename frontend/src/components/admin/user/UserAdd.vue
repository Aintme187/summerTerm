<script setup>
import {ref} from "vue";
import {deptNames, rules} from "@/utils/validator.js";
import {ElMessage} from "element-plus";
import {addUserApi, updateUserApi} from "@/api/adminUser.js";
import {upload} from "@/api/upload.js";

const formRef = ref()
const fileList = ref()
const uploadRef = ref()
const user = ref({
  "account": "",
  "password": "",
  "nickname": "",
  "avatar": "",
  "email": "",
  "mobilePhoneNumber": "",
  "deptName": "",
  "permission": "",
  "signature": ""
})

function submit() {
  formRef.value.validate((valid) => {
    if (valid) {
      uploadImage().then(() => {
        addUserApi(user.value).then(() => {
          ElMessage({type: "success", message: "成功添加用户"})
        }).catch((reason) => {
          ElMessage({type: "error", message: reason})
        })
      })
    } else {
      ElMessage({type: "error", message: "请检查输入信息"})
    }
  })
}

function uploadImage() {
  return new Promise((resolve, reject) => {
    if (fileList.value && fileList.value.length > 0) {
      let formData = new FormData()
      formData.append("image", fileList.value[0].raw)
      upload(formData).then((value) => {
        user.value["avatar"] = value.data
        resolve()
      }).catch((reason) => {
        ElMessage({type: "error", message: reason})
        reject()
      })
    } else {
      user.value["avatar"] = ""
      resolve()
    }
  })
}

// 限制上传一个文件，重新选择文件替换原来的文件
const handleExceed = (files) => {
  uploadRef.value.clearFiles()
  uploadRef.value.handleStart(files[0])
}
</script>

<template>
  <div style="margin: 10px;">
    <h1>新增用户</h1>
    <el-form
        ref="formRef"
        :model="user"
        status-icon
        :rules="rules"
        label-position="top"
        label-width="auto"
    >
      <el-form-item label="头像" prop="free">
        <el-upload ref="uploadRef" accept=".png,.jpe,.jpeg" multiple drag :limit="1" list-type="picture-card"
                   v-model:file-list="fileList" :auto-upload="false"
                   :on-exceed="handleExceed">
          <span style="color: grey">将图片拖到此处或者点击上传</span>
        </el-upload>
      </el-form-item>
      <el-form-item label="账号" prop="account">
        <el-input v-model="user.account"/>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input v-model="user.password"/>
      </el-form-item>
      <el-form-item label="昵称" prop="nickname">
        <el-input v-model="user.nickname"/>
      </el-form-item>
      <!--bug here avatar-->
      <el-form-item label="邮箱" prop="email">
        <el-input v-model="user.email"/>
      </el-form-item>
      <el-form-item label="电话号码" prop="mobilePhoneNumber">
        <el-input v-model="user.mobilePhoneNumber"/>
      </el-form-item>
      <el-form-item label="学院" prop="deptName">
        <el-select v-model="user.deptName" placeholder="请选择学院">
          <el-option v-for="(value,key) in deptNames" :key="key" :label="value" :value="value"/>
        </el-select>
      </el-form-item>
      <el-form-item label="个性签名" prop="signature">
        <el-input v-model="user.signature"/>
      </el-form-item>
      <el-form-item label="身份" prop="permission">
        <el-radio-group v-model="user.permission">
          <el-radio-button label="管理员" value="administer"></el-radio-button>
          <el-radio-button label="教师" value="teacher"></el-radio-button>
          <el-radio-button label="学生" value="student"></el-radio-button>
        </el-radio-group>
      </el-form-item>
    </el-form>
    <el-button @click="submit">提交</el-button>
  </div>
</template>

<style scoped>

</style>