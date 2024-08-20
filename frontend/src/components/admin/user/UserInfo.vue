<script setup>
import {onMounted, ref} from "vue";
import {deptNames, rules} from "@/utils/validator.js";
import {ElMessage} from "element-plus";
import {useRouter} from "vue-router";
import {getUserInfoApi, updateUserApi} from "@/api/adminUser.js";
import {upload} from "@/api/upload.js";

const router = useRouter()
const formRef = ref()
const fileList = ref()
const user = ref({
  "account": "",
  "password": "",
  "nickname": "",
  "avatar": "",
  "email": "",
  "mobilePhoneNumber": "",
  "deptName": "",
  "permission": "",
  "signature": "",
})
const uploadRef = ref()
const changePassword = ref([])
const imageChanged = ref(false)

function uploadImage() {
  return new Promise((resolve, reject) => {
    if (imageChanged.value && fileList.value && fileList.value.length > 0) {
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

function submit() {
  formRef.value.validate((valid) => {
    if (valid) {
      if (changePassword.value.length === 0) user.value.password = null
      uploadImage().then(() => {
        updateUserApi(user.value).then(() => {
          ElMessage({type: "success", message: "成功编辑用户"})
        }).catch((reason) => {
          ElMessage({type: "error", message: reason})
        })
      })
    } else {
      ElMessage({
        type: "error",
        message: "请检查输入信息"
      })
    }
  })
}

// 限制上传一个文件，重新选择文件替换原来的文件
const handleExceed = (files) => {
  uploadRef.value.clearFiles()
  uploadRef.value.handleStart(files[0])
}

function handleChange() {
  imageChanged.value = true
}

onMounted(() => {
  getUserInfoApi(router.currentRoute.value.params["id"]).then((value) => {
    user.value = value.data
    user.value.password = ""
    if (user.value.avatar) {
      fileList.value = [{"url": user.value.avatar}]
    }
  }).catch((reason) => {
    ElMessage({type: "error", message: reason})
  })
})
</script>

<template>
  <div style="margin: 0 10px;">
    <h1 style="margin-top: 0">编辑id为{{ router.currentRoute.value.params["id"] }}的用户信息</h1>
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
                   :on-exceed="handleExceed" :on-change="handleChange">
          <span style="color: grey">将图片拖到此处或者点击上传</span>
        </el-upload>
      </el-form-item>
      <el-form-item label="账号" prop="account">
        <el-input v-model="user.account"/>
      </el-form-item>
      <el-form-item label="密码" :prop="changePassword.length>0?'password':'free'">
        <el-input v-model="user.password" :disabled="changePassword.length===0"/>
      </el-form-item>
      <el-checkbox-group v-model="changePassword" :max="1" style="margin-bottom: 10px">
        <el-checkbox-button value="true">
          修改密码
        </el-checkbox-button>
      </el-checkbox-group>
      <el-form-item label="昵称" prop="nickname">
        <el-input v-model="user.nickname"/>
      </el-form-item>
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
      <!--      <el-form-item label="身份" prop="permission">-->
      <!--        <el-radio-group v-model="user.permission">-->
      <!--          <el-radio-button label="管理员" value="administer"></el-radio-button>-->
      <!--          <el-radio-button label="教师" value="teacher"></el-radio-button>-->
      <!--          <el-radio-button label="学生" value="student"></el-radio-button>-->
      <!--        </el-radio-group>-->
      <!--      </el-form-item>-->
    </el-form>
    <el-button @click="submit">提交</el-button>
  </div>
</template>

<style scoped>
</style>