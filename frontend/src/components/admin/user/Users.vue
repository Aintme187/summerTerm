<script setup>
import {useRouter} from "vue-router";
import {onMounted, ref, watch} from "vue";
import {ElMessage} from "element-plus";
import {batchDeleteUsersApi, deleteUserApi, getUsersApi} from "@/api/adminUser.js";
import FilterDialog from "@/components/admin/layout/FilterDialog.vue";

const usersRef = ref()
const router = useRouter()
const filterDialogRef = ref()
const columns = {
  "id": {"type": "integer", "label": "id"},
  "account": {"type": "string", "label": "账号"},
  "nickname": {"type": "string", "label": "昵称"},
  "email": {"type": "string", "label": "邮箱"},
  "mobilePhoneNumber": {"type": "string", "label": "电话号码"},
  "deptName": {"type": "string", "label": "学院"},
  "signature": {"type": "string", "label": "个性签名"},
  "permission": {
    "type": "enum",
    "label": "身份",
    "enum": ["administer", "teacher", "student"]
  },
  "createDate": {"type": "datetime", "label": "注册时间"}
}
const enum2label = {
  "administer": "管理员", "teacher": "教师", "student": "学生"
}
/**
 * 筛选条件
 * @type {Ref<UnwrapRef<*[{filter: string, name: string, content: string}]>>}
 */
const filterData = ref([])
const total = ref()
const page = ref(1)
watch(page, (value) => {
  getUsers()
})
const pageSize = ref(10)
watch(pageSize, (value) => {
  getUsers()
})
/**
 * 后端数据
 * @type {Ref<UnwrapRef<*[{mobilePhoneNumber: string, lastLogin: string, nickname: string, permission: string, id: string, account: string, email: string}]>>}
 */
const userData = ref()
const loading = ref(false)

function getUsers() {
  loading.value = true
  getUsersApi(page.value, pageSize.value, filterData.value).then((value) => {
    total.value = value.data["adminSysUserCount"]
    userData.value = value.data["adminSysUserInfoList"]
  }).catch((reason) => {
    ElMessage({type: "error", message: reason})
  })
  loading.value = true
}

function deleteUser(id) {
  deleteUserApi(id).then(() => {
    ElMessage({type: "success", message: "成功删除用户"})
    getUsers()
  }).catch((reason) => {
    ElMessage({type: "error", message: reason})
  })
}

function editUser(id) {
  router.push("/admin/users/info/" + id)
}

function addUser() {
  router.push("/admin/users/add")
}

/**
 * 获取当前所选行的 id
 * @returns [id,...]
 */
function getSelectedIds() {
  if (!usersRef || !usersRef.value || !usersRef.value.getSelectionRows()) return []
  let ids = []
  // rows=[{prop:value,...},...]
  let rows = usersRef.value.getSelectionRows()
  for (let row in rows) {
    // for in list 得到的是索引
    ids.push(rows[row]["id"])
  }
  return ids
}

function editSelectedUsers() {
  router.push("/admin/users/edit/" + getSelectedIds())
}

function deleteSelectedUsers() {
  batchDeleteUsersApi(getSelectedIds()).then(() => {
    ElMessage({type: "success", message: "成功批量删除用户"})
    getUsers()
  }).catch((reason) => {
    ElMessage({type: "error", message: reason})
  })
}

onMounted(() => {
  getUsers()
})
</script>

<template>
  <!--flex: 1 子元素填充父元素剩余宽度; min-width: 0 告诉 flex 可以任意压缩子元素-->
  <div style="flex: 1; min-width: 0; display: flex; flex-direction: column">
    <div>
      <el-button type="success" style="padding: 10px" @click="addUser()">
        新增
        <svg t="1715762419761" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
             p-id="1414" width="20" height="20" style="padding-left: 5px">
          <path
              d="M512 902c215.391 0 390-174.609 390-390S727.391 122 512 122 122 296.609 122 512s174.609 390 390 390z m0 90C246.903 992 32 777.097 32 512S246.903 32 512 32s480 214.903 480 480-214.903 480-480 480z m45.328-524.951V287.744c0-60-90-60-90 0V467.05h-179.81c-60 0-60 90 0 90h179.81v179.81c0 60 90 60 90 0V557.05h179.304c60 0 60-90 0-90H557.328v-0.001z"
              p-id="1415" fill="#ffffff"></path>
        </svg>
      </el-button>
      <el-button type="warning" style="padding: 10px" @click="editSelectedUsers()"
                 :disabled="getSelectedIds().length<2">
        批量编辑
        <svg t="1715762892077" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
             p-id="1928" width="20" height="20" style="padding-left: 5px">
          <path
              d="M952.33152 216.32H488.97152l-24.96-79.36a72.32 72.32 0 0 0-68.48-51.84H71.69152A72.96 72.96 0 0 0 0.01152 159.36v704a72.96 72.96 0 0 0 71.68 74.24h468.48a37.12 37.12 0 0 0 0-74.24H71.69152v-576h880.64v154.88a35.84 35.84 0 1 0 71.68 0V290.56a72.96 72.96 0 0 0-71.68-74.24z m-880.64 0v-56.96h323.84l17.92 56.96z"
              fill="#ffffff" p-id="1929"></path>
          <path
              d="M855.05152 925.44a32.64 32.64 0 0 1-30.72-22.4 26.88 26.88 0 0 0-48.64 0 32 32 0 0 1-39.68 21.12 219.52 219.52 0 0 1-96-59.52 31.36 31.36 0 0 1 0-42.88 24.96 24.96 0 0 0 5.76-16 25.6 25.6 0 0 0-25.6-26.24 38.4 38.4 0 0 1-37.12-24.96 213.12 213.12 0 0 1-4.48-44.8A220.8 220.8 0 0 1 586.25152 640a33.28 33.28 0 0 1 30.72-22.4 25.6 25.6 0 0 0 26.24-25.6 21.12 21.12 0 0 0 0-10.88 32 32 0 0 1 7.04-37.76 227.84 227.84 0 0 1 92.16-51.84 31.36 31.36 0 0 1 37.12 16.64 26.88 26.88 0 0 0 46.08 0 32.64 32.64 0 0 1 37.12-16.64 227.84 227.84 0 0 1 92.16 51.84 32 32 0 0 1 7.04 37.76 21.12 21.12 0 0 0 0 10.88 24.96 24.96 0 0 0 25.6 25.6 33.92 33.92 0 0 1 31.36 22.4 220.8 220.8 0 0 1 5.12 69.76 213.12 213.12 0 0 1-4.48 44.8 32.64 32.64 0 0 1-33.92 25.6 25.6 25.6 0 0 0-23.04 41.6 31.36 31.36 0 0 1 0 42.88 219.52 219.52 0 0 1-97.28 59.52z m-55.04-103.68a89.6 89.6 0 0 1 69.12 32 166.4 166.4 0 0 0 28.8-17.92 96 96 0 0 1-5.12-30.08A89.6 89.6 0 0 1 960.01152 718.72v-8.96a148.48 148.48 0 0 0 0-29.44 90.24 90.24 0 0 1-64-86.4 104.96 104.96 0 0 1 0-14.08 192 192 0 0 0-31.36-17.92 92.8 92.8 0 0 1-128 0 192 192 0 0 0-31.36 17.92 104.96 104.96 0 0 1 0 14.08 90.24 90.24 0 0 1-64 86.4 148.48 148.48 0 0 0 0 29.44v8.96a90.88 90.88 0 0 1 67.2 87.04 96 96 0 0 1-5.12 30.08 166.4 166.4 0 0 0 28.8 17.92 89.6 89.6 0 0 1 67.84-32z"
              fill="#ffffff" p-id="1930"></path>
          <path
              d="M800.01152 808.32a99.2 99.2 0 1 1 0-198.4 99.2 99.2 0 1 1 0 198.4z m0-134.4a35.84 35.84 0 0 0-35.2 35.84 35.2 35.2 0 0 0 70.4 0 35.84 35.84 0 0 0-35.2-35.84z"
              fill="#ffffff" p-id="1931"></path>
        </svg>
      </el-button>
      <el-button type="danger" style="padding: 10px" @click="deleteSelectedUsers()"
                 :disabled="getSelectedIds().length<1">
        批量删除
        <svg t="1715763074329" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
             p-id="1762" width="20" height="20" style="padding-left: 5px">
          <path
              d="M821.708 225H915c24.853 0 45 20.147 45 45s-20.147 45-45 45h-17.892C879.215 436.337 846.4 615.666 798.63 853.28c-12.936 62.963-68.19 108.018-132.276 108.018H357.4c-64.086 0-119.34-45.055-132.274-108.01-47.878-237.956-80.694-417.286-98.48-538.288H109c-24.853 0-45-20.147-45-45s20.147-45 45-45h94.191l14.672-71.409C228.579 101.435 274.487 64 327.733 64h369.434c53.245 0 99.153 37.435 109.87 89.591L821.708 225z m-91.88 0l-10.95-53.296C716.76 161.398 707.688 154 697.167 154H327.733c-10.522 0-19.594 7.398-21.712 17.704L295.071 225h434.757z m76.282 90H217.637c17.92 118.88 49.83 292.446 95.7 520.43 4.327 20.886 22.727 35.868 44.063 35.868h308.955c21.336 0 39.734-14.981 44.063-35.865 45.77-227.67 77.677-401.236 95.692-520.433zM371 718V462c0-60 90-60 90 0v256c0 60-90 60-90 0z m192 0V462c0-60 90-60 90 0v256c0 60-90 60-90 0z"
              p-id="1763" fill="#ffffff"></path>
        </svg>
      </el-button>
      <el-button type="primary" style="padding: 10px" @click="filterDialogRef.showFilter()">
        筛选
        <svg t="1715827385633" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
             p-id="2206" width="20" height="20" style="padding-left: 5px">
          <path
              d="M697.6 764.16A379.52 379.52 0 0 1 448 855.04a391.68 391.68 0 0 1-384-395.52A391.68 391.68 0 0 1 448 64a391.68 391.68 0 0 1 387.2 395.52 401.28 401.28 0 0 1-87.04 250.24L947.84 896a39.04 39.04 0 0 1 0 53.76 36.48 36.48 0 0 1-53.12 0l-199.68-185.6zM138.88 459.52a315.52 315.52 0 0 0 311.68 320 315.52 315.52 0 0 0 312.32-320A315.52 315.52 0 0 0 448 140.8a315.52 315.52 0 0 0-311.68 320z"
              fill="#ffffff" p-id="2207"></path>
        </svg>
        <span v-if="filterData.length">&nbsp;{{ filterData.length }}</span>
      </el-button>
      <el-button type="info" style="padding: 10px" @click="getUsers()">
        刷新
        <svg t="1716031098776" class="icon" viewBox="0 0 1026 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
             p-id="2013" width="20" height="20" style="padding-left: 5px">
          <path
              d="M168.96 504.32a353.28 353.28 0 0 1 26.88-128A341.76 341.76 0 0 1 270.08 268.8a330.88 330.88 0 0 1 108.8-73.6A344.96 344.96 0 0 1 512 168.32a330.88 330.88 0 0 1 133.76 26.88 339.84 339.84 0 0 1 110.72 73.6 320 320 0 0 1 27.52 31.36l-60.16 46.72a8.32 8.32 0 0 0 0 14.72l175.36 42.88a8.32 8.32 0 0 0 10.24-7.68V215.68a8.32 8.32 0 0 0-12.8-6.4l-52.48 46.72A419.84 419.84 0 0 0 93.44 503.68a7.68 7.68 0 0 0 7.68 8.32h60.16a7.68 7.68 0 0 0 7.68-7.68zM925.44 512h-60.16a7.68 7.68 0 0 0-8.32 7.68 327.68 327.68 0 0 1-26.88 128 333.44 333.44 0 0 1-73.6 109.44A343.68 343.68 0 0 1 647.04 832a352 352 0 0 1-135.04 24.32A340.48 340.48 0 0 1 379.52 832a333.44 333.44 0 0 1-109.44-76.8 320 320 0 0 1-27.52-31.36l60.16-46.72a8.32 8.32 0 0 0-3.2-14.72l-176-42.88a7.68 7.68 0 0 0-9.6 7.68v181.12a7.68 7.68 0 0 0 12.8 6.4l55.68-46.72a419.84 419.84 0 0 0 750.72-247.68 7.68 7.68 0 0 0-7.68-8.32z"
              fill="#ffffff" p-id="2014"></path>
        </svg>
      </el-button>
    </div>
    <!--key="users" 显示复选框-->
    <el-table ref="usersRef" :data="userData" border key="users" :v-loading="true" style="margin: 10px 0; flex: 1">
      <el-table-column fixed="left" type="selection"/>
      <el-table-column v-for="(value,key) in columns" :key="key" :prop="key" :label="value.label">
        <template #default="scope">
          {{ value["type"] === "enum" ? enum2label[scope.row[key]] : scope.row[key] }}
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作">
        <template #default="scope">
          <el-link type="warning" @click="editUser(scope.row.id)" style="margin-right: 10px">
            <svg t="1715662608972" class="icon" viewBox="0 0 1024 1024" version="1.1"
                 xmlns="http://www.w3.org/2000/svg"
                 p-id="1010" width="20" height="20">
              <path
                  d="M893.867 443.733c0-26.51 21.49-48 48-48s48 21.49 48 48v450.134c0 53.02-42.98 96-96 96H130.133c-53.02 0-96-42.98-96-96V130.133c0-53.02 42.98-96 96-96h450.134c26.51 0 48 21.49 48 48s-21.49 48-48 48H130.133v763.734h763.734V443.733zM907.925 49.26c18.746-18.745 49.137-18.745 67.883 0 18.745 18.745 18.745 49.137 0 67.882L573.675 519.275c-18.746 18.744-49.137 18.744-67.883 0-18.745-18.746-18.745-49.137 0-67.883L907.925 49.259z"
                  p-id="1011" fill="#e6a23c"></path>
            </svg>
          </el-link>
          <el-link type="danger" @click="deleteUser(scope.row.id)">
            <svg t="1715666153398" class="icon" viewBox="0 0 1024 1024" version="1.1"
                 xmlns="http://www.w3.org/2000/svg"
                 p-id="1212" width="20" height="20">
              <path
                  d="M821.708 225H915c24.853 0 45 20.147 45 45s-20.147 45-45 45h-17.892C879.215 436.337 846.4 615.666 798.63 853.28c-12.936 62.963-68.19 108.018-132.276 108.018H357.4c-64.086 0-119.34-45.055-132.274-108.01-47.878-237.956-80.694-417.286-98.48-538.288H109c-24.853 0-45-20.147-45-45s20.147-45 45-45h94.191l14.672-71.409C228.579 101.435 274.487 64 327.733 64h369.434c53.245 0 99.153 37.435 109.87 89.591L821.708 225z m-91.88 0l-10.95-53.296C716.76 161.398 707.688 154 697.167 154H327.733c-10.522 0-19.594 7.398-21.712 17.704L295.071 225h434.757z m76.282 90H217.637c17.92 118.88 49.83 292.446 95.7 520.43 4.327 20.886 22.727 35.868 44.063 35.868h308.955c21.336 0 39.734-14.981 44.063-35.865 45.77-227.67 77.677-401.236 95.692-520.433zM371 718V462c0-60 90-60 90 0v256c0 60-90 60-90 0z m192 0V462c0-60 90-60 90 0v256c0 60-90 60-90 0z"
                  p-id="1213" fill="#d81e06"></path>
            </svg>
          </el-link>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination layout="total, sizes, prev, pager, next" :total="total" v-model:current-page="page"
                   v-model:page-size="pageSize"/>
  </div>
  <filter-dialog ref="filterDialogRef" :columns="columns" :enum2label="enum2label" :filter-data="filterData"
                 @get-data="getUsers()"/>
</template>

<style scoped>

</style>