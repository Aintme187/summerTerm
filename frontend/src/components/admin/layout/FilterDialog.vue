<script setup>
import {ref} from "vue";
import {ElMessage} from "element-plus";
import {remove} from "lodash";
import {contain} from "@/utils/typeUtils.js";

/**
 * 传递的 props 会被解包
 * 形式参考 User.vue
 * @type {Prettify<Readonly<{[key in string]?: any}>>}
 */
const props = defineProps(["filterData", "columns", "enum2label",])
/*
传递事件
 */
const emit = defineEmits(["getData"])
const filterRef = ref()
const filterVisible = ref(false)
const content = ref("")
const filter = ref("")
const name = ref("")
const selections = ref([])
const filter2label = {
  "equal": "等于",
  "lessThan": "小于",
  "greaterThan": "大于",
  "equalDouble": "等于",
  "lessThanDouble": "小于",
  "greaterThanDouble": "大于",
  "contain": "包含",
  "startWith": "以……开始",
  "endWith": "以……结尾",
  "at": "在……时间",
  "before": "早于",
  "after": "晚于",
  "on": "在……日期",
  "in": "属于",
  "notIn": "不属于",
}
const type2filter = {
  "integer": ["equal", "lessThan", "greaterThan"],
  "double": ["equalDouble", "lessThanDouble", "greaterThanDouble"],
  "string": ["contain", "startWith", "endWith"],
  "datetime": ["at", "before", "after", "on"],
  "enum": ["in", "notIn"],
}

function getContent(type, content) {
  if (type !== "enum") return content
  // enum 但是单选
  if (content.indexOf(",") === -1) return props.enum2label[content]
  // enum 多选
  let contentList = content.split(",")
  let labels = []
  for (let index in contentList) {
    labels.push(props.enum2label[contentList[index]])
  }
  return labels.toString()
}

function deleteFilter(key) {
  remove(props.filterData, (value, index, array) => {
    return key === index
  })
  ElMessage({
    message: "成功删除筛选条件",
    type: "success"
  })
  emit("getData")
}

function deleteAllFilters() {
  // props 只读，不能通过 = [] 清空
  props.filterData.length = 0
  ElMessage({
    message: "成功删除筛选条件",
    type: "success"
  })
  emit("getData")
}

function getFilters() {
  if (name.value === "") {
    return null
  } else {
    return type2filter[props.columns[name.value]["type"]]
  }
}

/**
 * 根据 name, filter 选择 content 的输入形式
 * return {0:不显示,1:输入框,2:日期时间选择器,3:日期选择器,4:多选框}
 */
function getInputType() {
  if (name.value === "" || filter.value === "") {
    return 0
  }
  let type = props.columns[name.value]["type"]
  if (filter.value === "on") {
    return 3
  }
  if (type === "integer" || type === "double" || type === "string") {
    return 1
  }

  if (type === "datetime") {
    return 2
  }
  if (type === "enum") {
    return 4
  }
}

function getMax() {
  let enums = getEnums()
  return enums ? enums.length : 0
}

function addFilter() {
  let newFilter = {"name": name.value, "filter": filter.value, "content": content.value}
  if (name.value && props.columns[name.value] && props.columns[name.value]["type"] === "enum") {
    newFilter["content"] = selections.value.toString()
  }
  if (newFilter["name"] === "" || newFilter["filter"] === "" || newFilter["content"] === "") {
    ElMessage({
      message: "请补全筛选条件",
      type: "error"
    })
    return
  }
  if (contain(props.filterData, newFilter)) {
    ElMessage({
      message: "请勿添加重复筛选条件",
      type: "warning"
    })
  } else {
    ElMessage({
      message: "成功添加筛选条件",
      type: "success"
    })
    props.filterData.push(newFilter)
    emit("getData")
  }
}

function getEnums() {
  if (name.value === "" || filter.value === "") {
    return null
  }
  return props.columns[name.value]["enum"]
}

function showFilter() {
  name.value = ""
  filter.value = ""
  content.value = ""
  selections.value = []
  filterVisible.value = true
}

/*
暴露方法
 */
defineExpose({showFilter})
</script>

<template>
  <!--el-dialog 弹出对话框-->
  <el-dialog v-model="filterVisible" title="筛选" draggable>
    <div class="center">
      <el-table ref="filterRef" :data="props.filterData" border key="users" style="margin: 10px 0; flex: 1">
        <el-table-column prop="name" label="列名">
          <template #default="scope">
            {{ props.columns[scope.row.name]["label"] }}
          </template>
        </el-table-column>
        <el-table-column prop="filter" label="条件">
          <template #default="scope">
            {{ filter2label[scope.row.filter] }}
          </template>
        </el-table-column>
        <el-table-column prop="content" label="内容">
          <template #default="scope">
            {{ getContent(props.columns[scope.row.name]["type"], scope.row.content) }}
          </template>
        </el-table-column>
        >
        <el-table-column fixed="right" label="操作">
          <template #default="scope">
            <el-link type="danger" @click="deleteFilter(scope.$index)">
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
      <el-button type="danger" @click="deleteAllFilters()" :disabled="props.filterData.length===0">全部删除</el-button>
      <el-select v-model="name" filterable placeholder="选择列" style="margin-top: 10px" @change="filter='';content=''">
        <el-option v-for="(value,key) in props.columns" :key="key" :value="key" :label="value.label"/>
      </el-select>
      <el-select v-model="filter" filterable placeholder="选择条件" style="margin-top: 10px" @change="content=''">
        <el-option v-for="(value,key) in getFilters()" :key="key" :value="value" :label="filter2label[value]"/>
      </el-select>
      <div style="margin: 10px 0" class="center">
        <el-input v-model="content" placeholder="请输入" v-if="getInputType()===1"/>
        <div v-else-if="getInputType()===2">
          <el-date-picker v-model="content" type="datetime" placeholder="选择日期时间" style="width: 100%"
                          format="YYYY-MM-DD HH:mm:ss" value-format="YYYY-MM-DD HH:mm:ss"/>
          <small style="color: grey">日期时间选择超出屏幕？请拖动到底部</small>
        </div>
        <div v-else-if="getInputType()===3">
          <el-date-picker v-model="content" type="date" placeholder="选择日期" style="width: 100%"
                          format="YYYY-MM-DD HH:mm:ss" value-format="YYYY-MM-DD HH:mm:ss"/>
          <small style="color: grey">日期选择超出屏幕？请拖动到底部</small>
        </div>
        <el-checkbox-group v-else v-model="selections" :max="getMax()">
          <el-checkbox-button v-for="(value,key) in getEnums()" :key="key" :value="value">
            {{ props.enum2label[value] }}
          </el-checkbox-button>
        </el-checkbox-group>
      </div>
      <el-button @click="addFilter()">添加</el-button>
    </div>
  </el-dialog>
</template>

<style scoped>
.center {
  margin: 0 auto;
  text-align: center;
}
</style>