<template>
  <div class="me-allct-body" v-title :data-title="categoryTagTitle">
    <el-container class="me-allct-container">
      <el-aside>
        <base-header :activeIndex="activeIndex"></base-header>
        <el-button
            class="me-create-button"
            @click="checkCurrentTag"
            style="margin: 40px"
        >
          <el-text>创建{{ currentTagName }}</el-text>
        </el-button>
      </el-aside>
      <el-main style="height: 80vh">
        <el-tabs v-model="currentActiveName" class="me-object-option" @tab-click="handleTabClick">
          <el-tab-pane label="文章分区" name="category" ref="categoryRef">
            <ul class="me-allct-items">
              <li v-for="c in categorys" @click="view(c.id)" :key="c.id" class="me-allct-item">
                <div class="me-allct-content">
                  <a class="me-allct-info">
                    <img class="me-allct-img" :src="c.avatar?c.avatar:defaultAvatar"/>
                    <h4 class="me-allct-name">{{ c.categoryName }}</h4>
                    <p class="me-allct-description">{{ c.description }}</p>
                  </a>
                </div>
              </li>
            </ul>
          </el-tab-pane>
          <el-tab-pane label="标签" name="tag">
            <ul class="me-allct-items">
              <li v-for="t in tags" @click="view(t.id)" :key="t.id" class="me-allct-item">
                <div class="me-allct-content">
                  <a class="me-allct-info">
                    <img class="me-allct-img" :src="t.avatar?t.avatar:defaultAvatar"/>
                    <h4 class="me-allct-name">{{ t.tagName }}</h4>
                  </a>
                </div>
              </li>
            </ul>
          </el-tab-pane>
          <el-tab-pane label="收藏" name="favorite">
            <ul class="me-allct-items">
              <li v-for="f in favorites" @click="view(f.id)" :key="f.id" class="me-allct-item">
                <div class="me-allct-content">
                  <a class="me-allct-info">
                    <img class="me-allct-img" :src="f.avatar?f.avatar:defaultAvatar"/>
                    <h4 class="me-allct-name">{{ f.favoriteName }}</h4>
                  </a>
                </div>
              </li>
            </ul>
          </el-tab-pane>
        </el-tabs>
        <!--图片上传/创建分区、标签、收藏夹-->
        <el-dialog v-model="createFavoriteShow">
          <el-form
              :rules="rules"
              ref="favoriteRuleForm"
              :model="favoriteForm"
              label-width="120px"
              class="demo-ruleForm"
          >
            <el-form-item label="收藏夹名称" prop="name">
              <el-input v-model="favoriteForm.name" placeholder="请输入收藏夹的名称" style="width: 500px;"></el-input>
            </el-form-item>
            <el-form-item label="收藏夹图片" prop="avatar">
              <el-input v-model="favoriteForm.avatar" v-if="false"></el-input>
              <el-upload
                  ref="uploadRef"
                  action=""
                  list-type="picture-card"
                  :on-preview="handlePictureCardPreview"
                  :on-remove="handleRemove"
                  :on-change="UploadImage"
                  :limit="1"
                  :file-list="fileList"
                  :auto-upload="false"
              >
                <i class="el-icon-plus"></i>
                <template #tip>
                  <div style="font-size: 12px;color: #919191;">
                    单次限制上传一张图片
                  </div>
                </template>
              </el-upload>
              <el-dialog v-model="dialogVisible" style="line-height: 0;">
                <img style="width: 100%;height: 100%" alt=""/>
              </el-dialog>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="submitForm(favoriteRuleForm)">确定</el-button>
            </el-form-item>
          </el-form>
        </el-dialog>

        <el-dialog v-model="createCategoryShow">
          <el-form
              :rules="rules"
              ref="categoryRuleForm"
              :model="categoryForm"
              label-width="120px"
              class="demo-ruleForm"
          >
            <el-form-item label="分区名称" prop="categoryName">
              <el-input v-model="categoryForm.categoryName" placeholder="请输入分区的名称"
                        style="width: 500px;"></el-input>
            </el-form-item>
            <el-form-item label="分区描述" prop="description">
              <el-input v-model="categoryForm.description" placeholder="请输入分区的描述"
                        style="width: 500px;"></el-input>
            </el-form-item>
            <el-form-item label="分区图片" prop="avatar">
              <el-input v-model="categoryForm.avatar" v-if="false"></el-input>
              <el-upload
                  ref="uploadRef"
                  action=""
                  list-type="picture-card"
                  :on-preview="handlePictureCardPreview"
                  :on-remove="handleRemove"
                  :on-change="UploadImage"
                  :limit="1"
                  :file-list="fileList"
                  :auto-upload="false"

              >
                <i class="el-icon-plus"></i>
                <template #tip>
                  <div style="font-size: 12px;color: #919191;">
                    单次限制上传一张图片
                  </div>
                </template>
              </el-upload>
              <el-dialog v-model="dialogVisible" style="line-height: 0;">
                <img style="width: 100%;height: 100%" alt=""/>
              </el-dialog>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="submitForm(categoryRuleForm)">确定</el-button>
            </el-form-item>
          </el-form>
        </el-dialog>

        <el-dialog v-model="createTagShow">
          <el-form
              :rules="rules"
              ref="tagRuleForm"
              :model="tagForm"
              label-width="120px"
              class="demo-ruleForm"
          >
            <el-form-item label="标签名称" prop="tagName">
              <el-input v-model="tagForm.tagName" placeholder="请输入标签的名称" style="width: 500px;"></el-input>
            </el-form-item>
            <el-form-item label="标签图片" prop="avatar">
              <el-input v-model="tagForm.avatar" v-if="false"></el-input>
              <el-upload
                  ref="uploadRef"
                  action=""
                  list-type="picture-card"
                  :on-preview="handlePictureCardPreview"
                  :on-remove="handleRemove"
                  :on-change="UploadImage"
                  :limit="1"
                  :file-list="fileList"
                  :auto-upload="false"

              >
                <i class="el-icon-plus"></i>
                <template #tip>
                  <div style="font-size: 12px;color: #919191;">
                    单次限制上传一张图片
                  </div>
                </template>
              </el-upload>
              <el-dialog v-model="dialogVisible" style="line-height: 0;">
                <img style="width: 100%;height: 100%" alt=""/>
              </el-dialog>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="submitForm(tagRuleForm)">确定</el-button>
            </el-form-item>
          </el-form>
        </el-dialog>
      </el-main>
    </el-container>
  </div>
</template>

<script setup>
import {ref, reactive, onMounted, watch, computed} from 'vue';
import defaultAvatar from '@/assets/img/logo.png';
import {getAllCategorysDetailApi} from '@/api/category.js'
import {useRoute, useRouter} from 'vue-router';
import {ElMessage} from 'element-plus';
import {getAllTagsDetailApi} from "@/api/tag.js";
import {getFavoriteListApi} from "@/api/favorite.js";
import {upload} from "@/api/upload";
import {createApi} from "@/api/favorite"
import {createCategoryApi} from "@/api/category";
import {createTagApi} from "@/api/tag";
import {useStore} from "vuex";
import BaseHeader from "@/views/BaseHeader.vue";

const store = useStore()
const router = useRouter()
const route = useRoute()
// 使用ref创建响应式数据
const categorys = ref([]);
const tags = ref([]);
const favorites = ref([]);
const fileList = ref([]);
const currentActiveName = ref('category');
const currentTagName = ref('分区');
const favoriteForm = ref({
  name: '',
  avatar: '',
});
const categoryForm = ref({
  categoryName: '',
  avatar: '',
  description: '',
});
const tagForm = ref({
  tagName: '',
  avatar: '',
});
const createFavoriteShow = ref(false);
const createCategoryShow = ref(false);
const createTagShow = ref(false);
const dialogImageUrl = ref('');
const dialogVisible = ref(false);
const favoriteRuleForm = ref();
const categoryRuleForm = ref();
const tagRuleForm = ref();
const uploadRef = ref();
const categoryRef = ref()
const ruleForm = reactive({
  name: '',
  image: '',
  summary: '',
  issue: '',
});
const rules = reactive({
  name: [
    {
      min: 1,
      max:6,
      required: true,
      message: '请输入标题',
      trigger: 'blur',
    },
  ],
  categoryName: [
    {
      min: 1,
      max:6,
      required: true,
      message: '请输入标题',
      trigger: 'blur',
    },
  ],
  tagName: [
    {
      min: 1,
      max:6,
      required: true,
      message: '请输入标题',
      trigger: 'blur',
    },
  ],
  description: [
    {
      min: 1,
      max:30,
      required: true,
      message: '请输入简介',
      trigger: 'blur',
    },
  ],
  summary: [
    {
      min: 1,
      max:6,
      required: true,
      message: '请输入简介',
      trigger: 'blur',
    },
  ],
});


// // 使用computed定义计算属性
// const activeName = computed({
//   get: () => {
//     currentActiveName.value = route.params.type;
//     console.log(currentTagName.value)
//   },
//   set: newValue => {
//     currentActiveName.value = newValue;
//     console.log(currentTagName.value)
//   },
// });

const categoryTagTitle = computed(() => {
  if (currentActiveName.value == 'category') {
    return '文章分区 - 博客系统';
  } else if (currentActiveName.value == 'favorite') {
    return '收藏 - 博客系统';
  }
  return '标签 - 博客系统';
});

// 使用onMounted替代mounted钩子
onMounted(() => {
  getCategories();
  getTags();
  getFavorites();
});

// 定义方法
const checkCurrentTag = () => {
  if (currentTagName.value === '收藏夹') {
    createFavoriteShow.value = true
  } else if (currentTagName.value === '分区') {
    createCategoryShow.value = true
  } else createTagShow.value = true
};
const handleTabClick = (tab, event) => {
  console.log(tab.props.name)
  if (tab.props.name === 'favorite') {
    currentTagName.value = '收藏夹'
    console.log(currentTagName.value)
  } else if (tab.props.name === 'category') {
    currentTagName.value = '分区'
    console.log(currentTagName.value)
  } else {
    currentTagName.value = '标签'
    console.log(currentTagName.value)
  }
}
const view = (id) => {
  router.push({path: `/blog/${currentActiveName.value}/${id}`});
};

const getCategories = () => {
  // 替换this.$message为ElMessage
  getAllCategorysDetailApi().then(data => {
    categorys.value = data.data;
  }).catch(error => {
    ElMessage({
      type: 'error',
      message: '文章分区加载失败',
      showClose: true
    });
  });
};

const getTags = () => {
  getAllTagsDetailApi().then(data => {
    tags.value = data.data;
  }).catch(error => {
    ElMessage({
      type: 'error',
      message: '标签加载失败',
      showClose: true
    });
  });
};

const getFavorites = () => {
  getFavoriteListApi().then(data => {
    favorites.value = data.data;
  }).catch(error => {
    ElMessage({
      type: 'error',
      message: '收藏加载失败',
      showClose: true
    });
  });
};

// ... 其他方法不变，确保使用ref或reactive声明响应式数据 ...
// 上传图片的方法
const UploadImage = (file) => {
  console.log(file);
  const formdata = new FormData();
  formdata.append('image', file.raw); // 传给后台接收的名字 file
  upload(formdata).then((data) => {
    if (data.success) {
      console.log("上传图片到:" + data.data);
      // 将图片地址给到表单
      updateFormAvatar(data.data);
    } else {
      ElMessage({message: data.msg, type: 'error', showClose: true});
    }
  }).catch((err) => {
    ElMessage({message: err, type: 'error', showClose: true});
  });
};

// 更新表单头像的函数
const updateFormAvatar = (url) => {
  if (currentTagName.value === '收藏夹') {
    favoriteForm.value.avatar = url;
  } else if (currentTagName.value === '分区') {
    categoryForm.value.avatar = url;
  } else {
    tagForm.value.avatar = url;
  }
};

// 预览图片功能
const handlePictureCardPreview = (file) => {
  console.log(file.url);
  dialogVisible.value = true;
  dialogImageUrl.value = file.url;
};

// 上传添加功能

const token = ref(store.state.token); // 使用Composition API获取token

// 提交表单的方法
const submitForm = (formName) => {
  console.log("formname is " + formName);
  console.log(formName)

    if (currentTagName.value === '收藏夹') {

      createApi(favoriteForm.value, token.value).then(data => {
        if (data.success) {
          ElMessage({message: "创建收藏夹成功", type: 'success', showClose: true});
          createFavoriteShow.value = false;
          favoriteForm.value = {
            name: '',
            avatar: ''
          };
          getCategories(); // 假设这是获取分区的异步方法
          getTags(); // 假设这是获取标签的异步方法
          getFavorites(); // 假设这是获取收藏夹列表的异步方法
        } else {
          ElMessage({message: "创建失败", type: 'error', showClose: true});
        }
      }).catch(error => {
        ElMessage({message: "创建失败", type: 'error', showClose: true});
      })
    } else if (currentTagName.value === '分区') {
      // 类似地处理分区和标签的创建逻辑...
      createCategoryApi(categoryForm.value, token.value).then(data => {
        if (data.success) {
          createCategoryShow.value = false
          ElMessage({message: "创建分区成功", type: 'success', showClose: true})
          categoryForm.value = {
            categoryName: '',
            avatar: '',
            description: '',
          }
          getCategories(); // 假设这是获取分区的异步方法
          getTags(); // 假设这是获取标签的异步方法
          getFavorites(); // 假设这是获取收藏夹列表的异步方法
        } else {
          ElMessage({message: "创建失败", type: 'error', showClose: true})
        }
      })
    } else {
      // 类似地处理标签的创建逻辑...
      createTagApi(tagForm.value, token.value).then(data => {
        if (data.success) {
          createTagShow.value = false
          ElMessage({message: "创建标签成功", type: 'success', showClose: true})
          tagForm.value = {
            tagName: '',
            avatar: '',
          }
          getCategories(); // 假设这是获取分区的异步方法
          getTags(); // 假设这是获取标签的异步方法
          getFavorites(); // 假设这是获取收藏夹列表的异步方法
        } else {
          ElMessage({message: "创建失败", type: 'error', showClose: true})
        }
      })
    }


};

// 提交后重置上传界面表单

// 组件内的守卫 调整body的背景色
const beforeRouteEnter = (to, from, next) => {
  window.document.body.style.backgroundColor = '#fff';
  next();
};

const beforeRouteLeave = (to, from, next) => {
  window.document.body.style.backgroundColor = '#f5f5f5';
  next();
};
</script>

<style scoped>
.el-aside {
  width: 20vw;
}

.me-allct-body {
  margin: 60px auto;
}

.me-allct-container {
  width: 80vw;
  margin-top: -6vh;
}

.me-allct-items {
  //padding: 3vw;
  padding-bottom: 2rem;
}

.me-allct-item {
  width: 48%;
  margin-left: 1%;
  margin-right: 1%;
  display: inline-block;
  margin-bottom: 2.4rem;

  box-sizing: border-box;
}

.me-allct-content {
  display: inline-block;
  width: 100%;
  background-color: white;
  border-radius: 5px;
  transition: border-color .3s;
  text-align: center;
  padding: 1.5rem 0;
  margin-left: -2vw;
  margin-right: 2vw;
}

.me-allct-info {
  cursor: pointer;
}

.me-allct-img {
  margin: 0 10px;
  width: 60px;
  height: 60px;
  vertical-align: middle;

}

.me-allct-name {
  font-size: 21px;
  font-weight: 150;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  margin-top: 4px;
  font-weight: bold;
}

.me-allct-description {
  min-height: 50px;
  font-size: 13px;
  line-height: 25px;
}

.me-allct-meta {
  font-size: 12px;
  color: #969696;
}

.hide >>> .el-upload--picture-card {
  display: none;
}

.me-object-option {
}

.me-create-card {
  margin-bottom: 100px;
  margin-left: 150px;
}

:deep(.el-tabs__nav-scroll) {
  background-color: white;
  padding-left: 30px;
}

</style>
