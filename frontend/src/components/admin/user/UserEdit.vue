<script setup>
import {onMounted, ref} from "vue";
import {deptNames, rules} from "@/utils/validator.js";
import {contain, string2ints} from "@/utils/typeUtils.js";
import {ElMessage} from "element-plus";
import {useRouter} from "vue-router";
import {cloneDeep} from "lodash";
import {batchUpdateUsersApi} from "@/api/adminUser.js";
import {upload} from "@/api/upload.js";

const router = useRouter()
const formRef = ref()
// account 不能批量编辑
const user = ref({
  "account": null,
  "password": "",
  "nickname": "",
  "avatar": "",
  "email": "",
  "mobilePhoneNumber": "",
  "deptName": "",
  "permission": "",
  "signature": "",
})

const changedColumns = ref([])
const column2label = {
  "avatar": "头像",
  "password": "密码",
  "nickname": "昵称",
  "email": "邮箱",
  "mobilePhoneNumber": "电话号码",
  "deptName": "学院",
  "permission": "身份",
  "signature": "个性签名",
}
const uploadRef = ref()
const fileList = ref()

// 限制上传一个文件，重新选择文件替换原来的文件
const handleExceed = (files) => {
  uploadRef.value.clearFiles()
  uploadRef.value.handleStart(files[0])
}

function uploadImage() {
  return new Promise((resolve, reject) => {
    if (fileList && fileList.value && fileList.value.length > 0) {
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
      uploadImage().then(() => {
        let userClone = cloneDeep(user.value)
        for (let key in user.value) {
          if (!contain(changedColumns.value, key)) {
            userClone[key] = null
          }
        }
        batchUpdateUsersApi(string2ints(router.currentRoute.value.params["ids"], ","), userClone).then(() => {
          ElMessage({type: "success", message: "成功批量编辑用户"})
        }).catch((reason) => {
          ElMessage({type: "error", message: reason})
        })
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
    <h1 style="margin-top: 0">编辑id为{{ router.currentRoute.value.params["ids"] }}的用户信息</h1>
    <h3 style="color: #e6a23c">批量编辑会作用到所有选中的用户，请选择需要修改的信息</h3>
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
        :model="user"
        status-icon
        :rules="rules"
        label-position="top"
        label-width="auto"
        style="max-width: 40vw"
    >
      <el-form-item label="头像" prop="free">
        <el-upload ref="uploadRef" accept=".png,.jpe,.jpeg" multiple drag :limit="1" list-type="picture-card"
                   v-model:file-list="fileList" :auto-upload="false"
                   :on-exceed="handleExceed" :disabled="!contain(changedColumns,'avatar')">
          <span style="color: grey">{{
              contain(changedColumns, 'avatar') ? "将图片拖到此处或者点击上传" : "不可上传，未选择修改头像"
            }}</span>
        </el-upload>
      </el-form-item>
      <el-form-item label="密码" :prop="contain(changedColumns,'password')?'password':'free'">
        <el-input v-model="user.password" :disabled="!contain(changedColumns,'password')"/>
      </el-form-item>
      <el-form-item label="昵称" :prop="contain(changedColumns,'nickname')?'nickname':'free'">
        <el-input v-model="user.nickname" :disabled="!contain(changedColumns,'nickname')"/>
      </el-form-item>
      <el-form-item label="邮箱" :prop="contain(changedColumns,'email')?'email':'free'">
        <el-input v-model="user.email" :disabled="!contain(changedColumns,'email')"/>
      </el-form-item>
      <el-form-item label="电话号码" :prop="contain(changedColumns,'mobilePhoneNumber')?'mobilePhoneNumber':'free'">
        <el-input v-model="user.mobilePhoneNumber" :disabled="!contain(changedColumns,'mobilePhoneNumber')"/>
      </el-form-item>
      <el-form-item label="学院" :prop="contain(changedColumns,'deptName')?'deptName':'free'">
        <el-select v-model="user.deptName" placeholder="请选择学院" :disabled="!contain(changedColumns,'deptName')">
          <el-option v-for="(value,key) in deptNames" :key="key" :label="value" :value="value"/>
        </el-select>
      </el-form-item>
      <el-form-item label="个性签名" :prop="contain(changedColumns,'signature')?'signature':'free'">
        <el-input v-model="user.signature" :disabled="!contain(changedColumns,'signature')"/>
      </el-form-item>
      <!--      <el-form-item label="身份" :prop="contain(changedColumns,'permission')?'permission':'free'">-->
      <!--        <el-radio-group v-model="user.permission" :disabled="!contain(changedColumns,'permission')">-->
      <!--          <el-radio-button label="管理员" value="administer"></el-radio-button>-->
      <!--          <el-radio-button label="教师" value="teacher"></el-radio-button>-->
      <!--          <el-radio-button label="学生" value="student"></el-radio-button>-->
      <!--        </el-radio-group>-->
      <!--      </el-form-item>-->
    </el-form>
    <el-button @click="submit" style="margin-bottom: 10px" :disabled="changedColumns.length===0">提交</el-button>
  </div>
</template>

<style scoped>
.grey {
  background-color: grey;
}
</style>