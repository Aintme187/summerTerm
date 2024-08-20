<template>
    <!-- <li v-for="c in categorys" @click="view(c.id)" :key="c.id" class="me-allct-item"> -->

    <el-row :gutter="0">
        <el-col :span="8">   
            <el-card shadow="hover" class="course-card">
                <div class="course-item">
                    <div class="course-index">
                        <img src="@/assets/飞机航班.png" width="30" alt="课程标识"/>
                    </div>
                    <div class="course-details">
                        <p class="course-name">全部博客</p>
                    </div>
                </div>
            </el-card> 
        </el-col>

        <el-col :span="8">   
            <el-card shadow="hover" class="course-card">
                <div class="course-item">
                    <div class="course-index">
                        <img src="@/assets/飞机航班.png" width="30" alt="课程标识"/>
                    </div>
                    <div class="course-details">
                        <p class="course-name">热度文章</p>
                    </div>
                </div>
            </el-card> 
        </el-col>

        <el-col :span="8">   
            <el-card shadow="hover" class="course-card">
                <div class="course-item">
                    <div class="course-index">
                        <img src="@/assets/飞机航班.png" width="30" alt="课程标识"/>
                    </div>
                    <div class="course-details">
                        <p class="course-name">分类一</p>
                    </div>
                </div>
            </el-card> 
        </el-col>

    </el-row>

     
        
        <!-- <el-card shadow="hover" class="course-card">
            <div class="course-item">
                <div class="course-index">
                    <img src="@/assets/飞机航班.png" width="30" alt="课程标识"/>
                </div>
                <div class="course-details">
                    <p class="course-name">收藏夹名称</p>
                </div>
            </div>
        </el-card>  -->
    <!-- </li> -->
</template>


<script>
    // import { getAllCategorysDetailApi} from '@/api/category.js'
    export default {
        name:'MyFav',
        data() {
            // return {
            //     data:[{
            //         id:1,
            //         img:'@/assets/飞机航班.png',
            //         name:'分类1'
            //     },{
            //         id:1,
            //         img:'@/assets/飞机航班.png',
            //         name:'分类2'
            //     },{
            //         id:1,
            //         img:'@/assets/飞机航班.png',
            //         name:'分类3'
            //     },{
            //         id:1,
            //         img:'@/assets/飞机航班.png',
            //         name:'分类4'
            //     },{
            //         id:1,
            //         img:'@/assets/飞机航班.png',
            //         name:'分类4'
            //     }]
            // };
        },
        methods:{
            // regetInfo(){
            //     getMyInfoService(1).then((res) =>{
            //         console.log("返回参数")
            //         console.log(res)
            //         this.userdata = res
            //         console.log("gettheinfo"+this.userdata)
            //     })
            // }
            // getCategories(){
            //     // 替换this.$message为ElMessage
            //     getAllCategorysDetailApi().then(data => {
            //         categorys.value = data.data;
            //     }).catch(error => {
            //         ElMessage({
            //             type: 'error',
            //             message: '文章分类加载失败',
            //             showClose: true
            //         });
            //     });
            // }
        },
        mounted(){
            // if (this.$route.params.id != null) {
            //     console.log(this.$route.params.id+"getidddddddddddddddddddddddddddddd")
            //     //getArticleById(route.params.id);
            //     getMyInfoService(this.$route.params.id).then((res) =>{
            //         console.log("返回参数")
            //         console.log(res)
            //         this.userdata = res.data
            //     }).catch((reason) =>{
                    
            //     })
            // }
        }
    }

</script>

<style scoped>
.course-card {
    width:200px;
    height:200px;
    margin: 10px;
    transition: transform 0.3s;
}

.course-card:hover {
  transform: scale(1.05);
}

.course-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 20px;
  border-bottom: 1px solid #ebeef5;
}

.course-index {
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #ff6f61;
  color: white;
  width: 50px;
  height: 50px;
  border-radius: 50%;
  font-size: 18px;
  font-weight: bold;
}
.course-details {
  flex: 1;
  padding-left: 20px;
}
.course-name {
  font-size: 20px;
  font-weight: bold;
  margin: 0;
  writing-mode: vertical-lr; /* 从左向右的垂直书写模式 */
  /* text-align: center; /* 居中对齐 */
  /* height: 200px; */ 
}
</style>