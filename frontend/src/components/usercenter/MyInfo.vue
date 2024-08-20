<template>
    <el-card>
        <el-descriptions class="margin-top" title="个人信息" :column="2" :size="large" border>

            <el-descriptions-item
            align="left"
            width="100px"
            >
                <template v-slot:label>
                    <el-icon><User /></el-icon>
                    姓名
                </template>
                {{ userdata.nickname }}
            </el-descriptions-item>

            <el-descriptions-item
            align="left"
            width="100px"
            >
                <template v-slot:label>
                    <el-icon><Postcard /></el-icon>
                    账号
                </template>
                {{ userdata.account }}
            </el-descriptions-item>
                  
            <el-descriptions-item
            align="left"
            width="100px"
            >
                <template v-slot:label>
                    <el-icon><Iphone /></el-icon>
                    电话号码
                </template>
                <template v-if="userdata.mobilePhoneNumber">
                    {{ userdata.mobilePhoneNumber }}
                </template>
                <template v-else>
                    <span style="color: #CDD0D6;">待完善</span>
                </template>
                <!-- {{ userdata.mobilePhoneNumber }} -->
            </el-descriptions-item>

            <el-descriptions-item
            align="left"
            width="100px"
            >
                <template v-slot:label>
                    <el-icon><Message /></el-icon>
                    邮箱地址
                </template>
                <template v-if="userdata.email">
                    {{ userdata.email }}
                </template>
                <template v-else>
                    <span style="color: #CDD0D6;">待完善</span>
                </template>
            </el-descriptions-item>

            <el-descriptions-item
            align="left"
            width="100px"
            >
                <template v-slot:label>
                    <el-icon><UserFilled /></el-icon>
                    用户身份
                </template>
                <span v-if="userdata.permission === 'teacher'">教师</span>
                <span v-else>学生</span>
            </el-descriptions-item>

            <el-descriptions-item
            align="left"
            width="100px"
            >
                <template v-slot:label>
                    <el-icon><HomeFilled /></el-icon>
                    所属院系
                </template>
                    <template v-if="userdata.deptName">
                        {{ userdata.deptName }}
                    </template>
                    <template v-else>
                        <span style="color: #CDD0D6;">待完善</span>
                    </template>
            </el-descriptions-item>

            <el-descriptions-item
            align="left"
            width="100px"
            >
                <template #label>
                    <div class="cell-item">
                        <el-icon><ChatDotSquare /></el-icon>
                        个性签名
                    </div>
                </template>  
                <template v-if="userdata.signature">
                    {{ userdata.signature }}
                </template>
                <template v-else>
                    <span style="color: #CDD0D6;">待完善</span>
                </template>
            </el-descriptions-item>

        </el-descriptions> 
    </el-card>
</template>

<script>
    import { getMyInfoService } from '@/api/userInfo.js';
    import {onBeforeRouteUpdate, useRoute, useRouter} from 'vue-router';
    export default {
        name:'MyInfo',
        data() {
            return {
                userdata:[
                    
                ],
                router:useRouter(),
                route:useRoute()
            };
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
        },
        mounted(){
            if (this.$route.params.id != null) {
                console.log(this.$route.params.id+"getidddddddddddddddddddddddddddddd")
                //getArticleById(route.params.id);
                getMyInfoService(this.$route.params.id).then((res) =>{
                    console.log("返回参数")
                    console.log(res)
                    this.userdata = res.data
                    console.log(this.userdata.permission==='student')
                }).catch((reason) =>{
                    
                })
            }
        }
    }
</script>

<style scoped>
    :deep(.cell-item) {
        height: 180px;
    }

    :deep(.el-descriptions__cell){
        height: 48px;
        vertical-align: top;
    } 

    :deep(.el-descriptions__title){
        font-size: 30px !important;
        margin-top: 10px;
        margin-bottom: 10px;
        background-image: -webkit-linear-gradient(
        left,
        rgb(42, 134, 141),
        #e9e625dc 20%,
        #3498db 40%,
        #e74c3c 60%,
        #09ff009a 80%,
        rgba(82, 196, 204, 0.281) 100%
        );
        -webkit-text-fill-color: transparent;
        -webkit-background-clip: text;
        -webkit-background-size: 200% 100%;
        -webkit-animation: masked-animation 4s linear infinite;
    }  
</style>