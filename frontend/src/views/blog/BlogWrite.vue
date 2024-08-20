<template>
  <div id="write" v-title :data-title="title">
    <el-container class="me-container-write">

      <el-header>
        <div style="display: flex; justify-content: space-between">
          <div class="me-write-info">写文章</div>
          <div style="margin-top: 15px;display: flex">
            <el-button round @click="publishShow">发布</el-button>
            <el-button round @click="cancel">取消</el-button>
            <el-upload
                :on-change="(file) => submit(file)"
                :auto-upload="false"
                style="margin-left: 10px"
            >
              <template #trigger >
                <el-button round>上传文件
                </el-button>
              </template>
<!--              <template #tip>-->
<!--              </template>-->
            </el-upload>
          </div>
        </div>

        <!--        <el-col :span="2" :offset="4">-->

        <!--          -->
        <!--        </el-col>-->
      </el-header>
      <!--markdown-->

      <el-aside class="me-material-upload">

        <el-dialog

            v-model="dialogTableVisible"
            @update:visible="val => dialogTableVisible = val"
            width="800"
            :append-to-body="true"
            center>

       <el-upload
           class="upload-demo"
           :on-change="(file) => submit(file)"
           :auto-upload="false"
       >
         <template #trigger >
           <el-button class="upload-file-button" type="success">


           </el-button>
         </template>
         <template #tip>
           <div class="upload-file-text">
             文件大小不超过20MB
           </div>
         </template>
       </el-upload>
        </el-dialog>
      </el-aside>
      <el-container class="me-write-box">
        <el-main class="me-write-main" style="width: 90vw;height: 85vh">
          <div class="me-write-title">
            <el-input resize="none"
                      type="textarea"
                      autosize
                      v-model="articleForm.title"
                      placeholder="请输入标题"
                      class="me-write-input">
            </el-input>
          </div>
          <div id="placeholder" style="visibility: hidden;height: 89px;display: none;"></div>

          <mavon-editor ref="mavonEditorRef" v-model="articleForm.content" :ishljs="true" @imgAdd="imgAdd"/>
        </el-main>
        <!--        <el-footer>-->
        <!--        </el-footer>-->
      </el-container>

      <el-dialog title="摘要 分类 标签"
                 v-model="publishVisible"
                 :close-on-click-modal=false
                 custom-class="me-dialog">

        <el-form :model="articleForm" ref="articlePublishForm" :rules="rules">
          <el-form-item prop="summary">
            <el-input type="textarea"
                      v-model="articleForm.summary"
                      :rows="6"
                      placeholder="请输入摘要">
            </el-input>
          </el-form-item>
          <el-form-item label="文章分类" prop="category">
            <el-select v-model="articleForm.category" value-key="id" placeholder="请选择文章分类">
              <el-option v-for="c in categorys" :key="c.id" :label="c.categoryName" :value="c"></el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="文章标签" prop="tags">
            <el-checkbox-group v-model="articleForm.tags">
              <el-checkbox v-for="t in tags" :key="t.id" :label="t.id" name="tags">{{ t.tagName }}</el-checkbox>
            </el-checkbox-group>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="publishVisible = false">取消</el-button>
          <el-button type="primary" @click="publish()">发布</el-button>
        </div>
        <!-- 课程资料展示 -->

      </el-dialog>
    </el-container>
  </div>
</template>

<script setup>
import {ref, onMounted, onUnmounted, computed} from 'vue';
import {onBeforeRouteUpdate, useRoute, useRouter} from 'vue-router';
import {ElMessage, ElLoading, ElMessageBox, ElDialog} from 'element-plus';
import BaseHeader from '@/views/BaseHeader.vue';
import MarkdownEditor from '@/components/markdown/MarkdownEditor.vue';
import {publishArticleApi, getArticleByIdApi} from '@/api/article';
import {getAllCategorysApi} from '@/api/category';
import {getAllTagsApi} from '@/api/tag';
import {useStore} from 'vuex';
import $_ from "lodash/function.js";
import {Edit} from "@element-plus/icons-vue";
import {upload} from "@/api/upload.js";
import log from "@/views/Log.vue";
import {downloadMaterialService, getMaterialService, submitMaterialService} from "@/api/student.js";


const store = useStore()
const router = useRouter()
const route = useRoute()
const formatTime = (time) => {
  return time.replace('T', ' ');
}
// 组件引用

// 响应式状态
const dialogTableVisible = ref(false);
const articlePublishForm = ref();
const publishVisible = ref(false);
const categorys = ref([]);
const tags = ref([]);
const editorToolBarToFixedWrapper = ref();
const articleForm = ref({
  id: '',
  title: '',
  summary: '',
  category: null,
  tags: [],
  content: '',
  contentHtml: '',
  file: '',
  editor: {
    value: '',
    ref: '',
    default_open: 'edit',
    toolbars: {
      bold: true,
      italic: true,
      header: true,
      underline: true,
      strikethrough: true,
      mark: true,
      superscript: true,
      subscript: true,
      quote: true,
      ol: true,
      ul: true,
      imagelink: true,
      code: true,
      fullscreen: true,
      readmodel: true,
      help: true,
      undo: true,
      redo: true,
      trash: true,
      navigation: true,
      preview: true,
    },
  },
});
const rules = ref({
  summary: [
    {required: true, message: '请输入摘要', trigger: 'blur'},
    {max: 80, message: '不能大于80个字符', trigger: 'blur'},
  ],
  category: [{required: true, message: '请选择文章分类', trigger: 'change'}],
  tags: [{type: 'array', required: true, message: '请选择标签', trigger: 'change'}],
});
const materials = ref([]);
const currentArticleId = ref();

const openMaterials = () => {
  dialogTableVisible.value = true;
}

const downloadMaterial = async (materialId) => {
  try {
    // 调用服务生成下载 URL
    const response = await downloadMaterialService(materialId);
    const url = response.data; // 假设返回的 JSON 中有一个 data 字段包含 URL
    // 创建一个临时的 <a> 元素
    const link = document.createElement('a');
    link.href = url;
    document.body.appendChild(link); // 将 <a> 元素添加到 DOM 中
    link.click(); // 触发点击事件
    document.body.removeChild(link); // 移除 <a> 元素
  } catch (error) {
    console.error('下载失败：', error);
  }
}
const submit = async (file) => {
  articleForm.value.file = file.raw;
  dialogTableVisible.value = false;
  ElMessage({type: 'success', message: '上传资料成功', showClose: true});
}
// 计算属性
const title = computed(() => '写文章 - 博客系统');


// 路由守卫
onBeforeRouteUpdate(() => {
  window.document.body.style.backgroundColor = '#f5f5f5';
});

// 组件挂载后执行
onMounted(() => {
  if (route.params.id != null) {
    console.log(route.params.id)
    //getArticleById(route.params.id);
  }
  getCategorysAndTags();
  editorToolBarToFixedWrapper.value = $_.throttle(editorToolBarToFixed, 200);
  window.addEventListener('scroll', editorToolBarToFixedWrapper.value, false);
});

// 组件销毁前移除事件监听
onUnmounted(() => {
  window.removeEventListener('scroll', editorToolBarToFixedWrapper.value, false);
});

// 定义方法
const mavonEditorRef = ref();

const imgAdd = (pos, $file) => {
  const formdata = new FormData();
  formdata.append('image', $file);

  upload(formdata).then(data => {
    if (data.success) {
      mavonEditorRef.value.$img2Url(pos, data.data);
    } else {
      // Assuming you have a way to show messages, replace this with your message function
      alert(data.msg);
    }
  }).catch(err => {
    // Assuming you have a way to show messages, replace this with your message function
    alert(err);
  });
};
const getArticleById = (id) => {
  getArticleByIdApi(id).then(data => {
    Object.assign(articleForm.value, data.data);
    articleForm.value.editor.value = data.data.body.content;
    let tags = articleForm.value.tags.map((item) => item.id);
    articleForm.value.tags = tags;
  }).catch(error => {
    if (error !== 'error') {
      ElMessage({type: 'error', message: '文章加载失败', showClose: true});
    }
  })
};

const publishShow = () => {
  if (!articleForm.value.title) {
    ElMessage({message: '标题不能为空哦', type: 'warning', showClose: true});
    return;
  }

  if (articleForm.value.title.length > 30) {
    ElMessage({message: '标题不能大于30个字符', type: 'warning', showClose: true});
    return;
  }
  console.log(mavonEditorRef.value.d_render)
  console.log(mavonEditorRef.value.d_value)
  //if (!articleForm.value.editor.ref.d_render) {
  if (mavonEditorRef.value.d_render == null || !mavonEditorRef.value.d_render) {
    ElMessage({message: '内容不能为空哦', type: 'warning', showClose: true});
    return;
  }

  publishVisible.value = true;
  console.log(publishVisible.value)
};

const publish = () => {
  articlePublishForm.value.validate((valid) => {
    if (valid) {
      let tags = articleForm.value.tags.map((item) => ({id: item}));
      let article = {
        id: articleForm.value.id,
        title: articleForm.value.title,
        summary: articleForm.value.summary,
        category: articleForm.value.category,
        tags: tags,
        body: {
          //content: articleForm.value.editor.value,
          //contentHtml: articleForm.value.editor.ref.d_render,
          content: mavonEditorRef.value.d_value,
          contentHtml: mavonEditorRef.value.d_render
        },

      };
      console.log("below is article")
      console.log(article)
      console.log(articleForm.value.file)
      publishVisible.value = false;

      let loading = ElLoading.service({
        lock: true,
        text: '发布中，请稍后...',
      });

      publishArticleApi(article, articleForm.value.file, store.state.token)
          .then((data) => {
            loading.close();
            if (data.success) {
              ElMessage({message: '发布成功啦', type: 'success', showClose: true});
              router.push({path: `/blog/view/${data.data.id}`});
            } else {
              ElMessage({message: '发布文章失败: ' + data.msg, type: 'error', showClose: true});
            }
          })
          .catch((error) => {
            loading.close();
            if (error !== 'error') {
              ElMessage({message: error, type: 'error', showClose: true});
            }
          });
    } else {
      return false;
    }
  });
};

const cancel = () => {
  ElMessageBox.confirm('文章将不会保存, 是否继续?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
  }).then(() => {
    router.push('/blog/home');
  }).catch(() => {
  });
};

const getCategorysAndTags = () => {
  getAllCategorysApi()
      .then((data) => {
        if (data.success) {
          categorys.value = data.data;
        } else {
          ElMessage({type: 'error', message: '文章分类加载失败', showClose: true});
        }
      })
      .catch((error) => {
        if (error !== 'error') {
          ElMessage({type: 'error', message: '文章分类加载失败', showClose: true});
        }
      });

  getAllTagsApi()
      .then((data) => {
        if (data.success) {
          tags.value = data.data;
        } else {
          ElMessage({type: 'error', message: '标签加载失败', showClose: true});
        }
      })
      .catch((error) => {
        if (error !== 'error') {
          ElMessage({type: 'error', message: '标签加载失败', showClose: true});
        }
      });
};

const editorToolBarToFixed = () => {
  let toolbar = document.querySelector('.v-note-op');
  let curHeight = document.documentElement.scrollTop || document.body.scrollTop;
  if (curHeight >= 160) {
    document.getElementById('placeholder').style.display = 'block';
    toolbar.classList.add('me-write-toolbar-fixed');
  } else {
    document.getElementById('placeholder').style.display = 'none';
    toolbar.classList.remove('me-write-toolbar-fixed');
  }
};
</script>

<style>
.upload-file-button{
  margin-top:3vh;
  margin-left: 17vw;
}
.upload-file-text{
  margin-top:3vh;
  margin-left: 16vw;
}
.me-material-upload {
  position: fixed;
  left: 20vw;
  top: 50vh;
}

.me-container-write {
  border-color: black;
}

.el-header {
  position: fixed;
  z-index: 1024;
  min-width: 100%;
  box-shadow: 0 2px 3px hsla(0, 0%, 7%, 0.1), 0 0 0 1px hsla(0, 0%, 7%, 0.1);
}

.me-write-info {
  line-height: 60px;
  font-size: 18px;
  font-weight: 600;
}

.me-write-btn {
  width: 20vh;
  margin-top: -5vh;
  margin-left: 60vh;
}

.me-write-box {
  margin: 80px auto 0;
}

.me-write-main {
  padding: 0;

}

.me-write-title {
}

.me-write-input textarea {
  font-size: 32px;
  font-weight: 600;
  height: 20px;
  border: none;
  border-radius: 95px;

}

.me-write-editor {
  min-height: 650px !important;
}

.me-header-left {
  margin-top: 10px;
}

.me-title img {
  max-height: 2.4rem;
  max-width: 100%;
}

.me-write-toolbar-fixed {
  position: fixed;
  width: 700px !important;
  top: 60px;
}

.v-note-op {
  box-shadow: none !important;
}

.auto-textarea-input,
.auto-textarea-block {
  font-size: 18px !important;
}
</style>
