<template>
    <el-row :gutter="0">
        <el-col :span="6" v-for = "(value) in favoriteList" @click="view(value.id)" :key="value.id">   
            <el-card shadow="hover" class="course-card">
                <div class="course-item" style="text-align: center">
                    <div class="course-index" style="text-align: center;">
                        <img :src="value.avatar" width="60px" height="60px" alt="课程标识"/>
                        <!-- <img src="value.avatar" width="30" alt="课程标识"/> -->
                    </div>
                    <div class="course-details" style="text-align: center; padding-left: 0px;">
                        <p class="course-name" style="text-align: center;">{{ value.favoriteName }}</p>
                    </div>
                </div>
            </el-card> 
        </el-col>
        <el-card v-if="this.$store.state.id == this.$route.params.id" shadow="hover" class="course-card" @click="showdiv">
            <div class="course-item">
                <div class="course-index">
                    <img src="@/assets/飞机航班.png" width="50px" height="50px" alt="课程标识"/>
                    <!-- <img src="value.avatar" width="30" alt="课程标识"/> -->
                </div>
                <div class="course-details">
                    <p class="course-name">新建收藏夹</p>
                </div>
            </div>
        </el-card>
    </el-row>

    <el-dialog 
    style="text-align:center;display: flex;justify-content: center;flex-direction: column;"
    title="创建收藏夹"
    v-model="dialogVisible"
    @update:visible="val => dialogVisible = val"
    width="40%"
    height="auto"
    >
        <el-form
        :rules="rules"
        ref="favoriteRuleForm"
        :model="favoriteForm"
        label-width="120px"
        class="demo-ruleForm"
        >
            <el-form-item label="收藏夹名称 :" prop="name" >
                <el-input v-model="favoriteForm.name" placeholder="请输入新建收藏夹的名称" ></el-input>
            </el-form-item>
            <el-form-item label="收藏夹图片 :" prop="avatar"> 
                <el-input v-model="favoriteForm.avatar" v-if="false"></el-input>
                <el-upload
                    ref="uploadRef"
                    action=""
                    list-type="picture-card"
                    :on-change="UploadImage"
                    :limit="1"
                    :file-list="fileList"
                    :auto-upload="false"
                >
                 <!-- :on-preview="handlePictureCardPreview"
                    :on-remove="handleRemove" -->
                    <i class="el-icon-plus"></i>
                    <template #tip>
                    <div style="font-size: 12px;color: #919191;">
                        单次限制上传一张图片
                    </div>
                    </template>
                </el-upload>
              <!-- <el-dialog v-model="dialogVisible" style="line-height: 0;">
                <img style="width: 100%;height: 100%" alt="" />
              </el-dialog> -->
            </el-form-item>
            <!-- <el-form-item>
              <el-button type="primary" @click="submitForm('favoriteRuleForm')">确定</el-button>
            </el-form-item> -->
        </el-form>
        <div style="text-align: center;">
            <el-button @click="handleClose">取消</el-button>
            <el-button type="primary" @click="submitForm('favoriteRuleForm')">提交</el-button>
        </div>
    </el-dialog>

    <!-- <el-dialog
    style="text-align:center;display: flex;justify-content: center;flex-direction: column;"
    title="新建收藏夹"
    v-model="dialogVisible"
    @update:visible="val => dialog2Visible = val"
    width="40%"
    height="auto"
    :before-close="handle2Close">
        <el-form>
            <el-form-item label="收藏夹名称 :" prop="favname">
                <el-input v-model="favname"></el-input>
            </el-form-item>
        </el-form>
        <div style="text-align: center;">
            <el-button @click="handleClose">取消</el-button>
            <el-button type="primary" @click="submitMyUpdate">提交</el-button>
        </div>
    </el-dialog> -->

</template>


<script>
    // import { getAllCategorysDetailApi} from '@/api/category.js'
    import { getFavoriteListApi } from '@/api/favorite'
    import { upload } from "@/api/upload"
    import {getOthersFavoriteListApi} from '@/api/favorite'
    import {createApi} from "@/api/favorite"

    export default {
        name:'MyFav',
        data() {
            return {
                fileList:[],
                favoriteList:[

                ],
                dialogVisible:false,
                favoriteForm:{
                    name:'',
                    avatar:'',
                },
                rules:{
                    name: [
                        { required: true, message: "收藏夹名称不能为空", trigger: "blur" },
                        // { pattern: /^(?=.*[a-zA-Z])(?=.*\d).{8,}$/, message: '密码必须包含数字与字母,且不少于8位', trigger: 'blur' }
                        // { pattern: /^[\w@#%]{5,20}$/, message: '密码由5-20位数字字母下划线或@#$%组成', trigger: 'blur' }
                        // /^[\\w@#$%]{5,20}$/
                        // /^[\w@#%]{5,20}$/
                    ]
                }
            };
        },
        mounted(){
            // const token = store.state.token; // 确保你已经定义了$store并且有state.token
            getOthersFavoriteListApi(this.$route.params.id).then((data) => {
                console.log("zhishisuccess"+data.success)
                if (data.success) {
                    console.log("获取某个人的收藏列表"+this.$route.params.id)
                    this.favoriteList= data.data;
                    console.log("获取我的收藏列表111111111111111111111111111111111111111111111111")
                    // console.log(favoriteList)
                } 
            })
            // .catch((error) => {
            //     ElMessage({ type: 'error', message: '收藏夹加载失败1234556666', showClose: true });
            // })
        },
        methods:{
            updateFormAvatar(url){
                // if (currentTagName.value === '收藏夹') {
                    this.favoriteForm.avatar = url;
                // }
            },
            showdiv(){
                console.log('新建收藏夹')
                this.dialogVisible = true
            },
            handleClose(key, keyPath) {
                this.dialogVisible=false
                console.log(key, keyPath);
            },
            view(id){
                console.log(`/blog/favorite/${id}`)
                this.$router.push(`/blog/favorite/${id}`);
            },
            UploadImage(file){
                console.log("我的我的照片");
                const formdata = new FormData();
                formdata.append('image', file.raw); // 传给后台接收的名字 file
                upload(formdata).then((data) => {
                    if (data.success) {
                        console.log("上传图片到:" + data.data);
                        // 将图片地址给到表单
                        this.favoriteForm.avatar = data.data;
                    } else {
                        ElMessage({ message: data.msg, type: 'error', showClose: true });
                    }
                }).catch((err) => {
                    ElMessage({ message: err, type: 'error', showClose: true });
                });
            },
            submitForm(){
                this.$refs["favoriteRuleForm"].validate(valid => {
                    if(valid){
                        // console.log("token is " + token.value);
                        console.log(this.favoriteForm)
                        console.log("below")
                        console.log(this.favoriteForm.avatar+this.favoriteForm.name)
                        if(!this.favoriteForm.avatar){
                            this.favoriteForm.avatar="http://seicj6zi6.hb-bkt.clouddn.com/%E8%BD%AF%E4%BB%B6%E5%AD%A6%E9%99%A2.jpg"
                        }
                        createApi(this.favoriteForm, this.$store.state.token).then(data => {
                            console.log("这是是否成功"+data.success)
                            if (data.success) {
                                ElMessage({ message: "创建收藏夹成功", type: 'success', showClose: true });
                                // createFavoriteShow.value = false;
                                this.favoriteForm = {
                                    name: '',
                                    avatar: ''
                                };
                                // getCategories(); // 假设这是获取分类的异步方法
                                // getTags(); // 假设这是获取标签的异步方法
                                // getFavorites(); // 假设这是获取收藏夹列表的异步方法
                            } 
                        }).catch (error => {
                            ElMessage({ message: error, type: 'error123', showClose: true });
                        })
                        this.dialogVisible=false
                        window.location.reload();
                    }
                    else{
                        ElMessage({
                            showClose: true,
                            message: '收藏夹名称不能为空。',
                            type: 'error',
                        })
                    }
                })
            } 
        },
    }

</script>

<style scoped>



.course-card {
    width:150px;
    height:140px;
    margin: 10px;
    transition: transform 0.3s;
}

.course-card:hover {
  transform: scale(1.05);
}

:deep(.el-card__body){
    display: flex;
    align-items: center;
    justify-content: center;
    height: 100%;
    width:100%;
    padding:0%;
}

.course-item {
    display: flex;
    align-items: center;
    justify-content: space-between;
    flex-direction: column;
    /* padding: 20px; 
    border-bottom: 1px solid #ebeef5;  */
}

.course-index {
  /* display: flex;
  align-items: center;
  justify-content: center;
  background-color: #ff6f61; */
  color: white;
  width: 100%;
  height: 80%;
  /* border-radius: 50%; */
  /* font-size: 18px;
  font-weight: bold; */
}
.course-details {
  flex: 1;
  padding-left: 0px;
}
.course-name {
  font-size: 15%;
  font-weight: bold;
  margin: 0;
  /* writing-mode: vertical-lr; */
  text-align: center; /* 居中对齐
  /* height: 200px; */ 
}
</style>
