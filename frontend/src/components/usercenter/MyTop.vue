<template>
    <div class="PersonTop">
        <div class="PersonTop_img">
            <img :src=this.userdata.avatar style="object-fit:cover">
            <!-- :src="userdata.avatar ? userdata.avatar : '指定的路径'" -->
        </div>
        <div class="PersonTop_text">
            <div class="user_text">
                <div class="user_name">
                    <span>{{ userdata.nickname }} </span>
                </div>
                <div class="user-v">
                    <span class="user-v-font" v-if="userdata.permission === 'teacher'">教师</span>
                    <span class="user-v-font" v-else>学生</span>
                </div>
                <div class="user_qianming">
                    <span v-if="!userdata.signature">快来编辑你的个性签名吧！</span>
                    <span v-else> {{ userdata.signature }} </span>
                </div>
                <div v-if="isConditionMet" class="user_anniu">
                    <el-button
                    class="el-icon-edit"
                    type="primary"
                    size="medium"
                    plain
                    @click="showdiv2()"
                    >
                        上传头像
                    </el-button>

                    <el-button
                    class="el-icon-edit"
                    type="primary"
                    size="medium"
                    plain
                    @click="showdiv1()"
                    >
                        编辑信息
                    </el-button>
                </div>
            </div>

            <!-- 选择性保留 -->
            <!-- <div class="user_num"> -->
                <!-- 是否需要添加fanCount,followCount,goodCount属性 -->
                <!-- 保留myfan,myfollow两种方法，是否有必要 -->
                <!-- @click="myfan"  @click="myfollow" -->
                <!-- <div style="cursor: pointer"> -->
                    <!-- <div class="num_number">{{ fansCounts }}</div> -->
                    <!-- <span class="num_text">粉丝</span>
                </div>
                <div style="cursor: pointer"> -->
                    <!-- <div class="num_number">{{ followCounts }}</div> -->
                    <!-- <span class="num_text">关注</span>
                </div>
                <div> -->
                    <!-- <div class="num_number">{{ goodCounts }}</div> -->
                    <!-- <span class="num_text">获赞</span>
                </div> -->
            <!-- </div>  -->

        </div>
    </div>

    <el-dialog
    style="text-align: center;"
    title="修改个人信息"
    v-model="dialog1Visible"
    @update:visible="val => dialog1Visible = val"
    width="50%"
    height="auto"
    :before-close="handle1Close">
        <el-form
        class="formstyle"
        style="width:70%; margin-left:5%;"
        :model="userdata"
        :rules="rules"
        ref="userInfo1"
        label-width="200px">
            <el-form-item label="账号 :" prop="account">
                <el-input v-model="userdata.account" disabled></el-input>
            </el-form-item>
            <el-form-item label="学院 :">
                <el-select v-model="userdata.deptName" placeholder="请选择学院">
                    <el-option label="安全科学学院" value="安全科学学院" />
                    <el-option label="材料学院" value="材料学院" />
                    <el-option label="电机工程与应用电子技术系" value="电机工程与应用电子技术系" />
                    <el-option label="法学院" value="法学院" />
                    <el-option label="工程物理系" value="工程物理系" />
                    <el-option label="公共管理学院" value="公共管理学院" />
                    <el-option label="国家卓越工程师学院" value="国家卓越工程师学院" />
                    <el-option label="航天航空学院" value="航天航空学院" />
                    <el-option label="核能与新能源技术研究院" value="核能与新能源技术研究院" />
                    <el-option label="化学工程系" value="化学工程系" />
                    <el-option label="环境学院" value="环境学院" />
                    <el-option label="计算机学院" value="计算机学院" />
                    <el-option label="机械工程学院" value="机械工程学院" />
                    <el-option label="能源与动力工程系" value="能源与动力工程系" />
                    <el-option label="车辆与运载学院" value="车辆与运载学院" />
                    <el-option label="建筑学院" value="建筑学院" />
                    <el-option label="教育研究院" value="教育研究院" />
                    <el-option label="经济管理学院" value="经济管理学院" />
                    <el-option label="理学院" value="理学院" />
                    <el-option label="马克思主义学院" value="马克思主义学院" />
                    <el-option label="美术学院" value="美术学院" />
                    <el-option label="人文学院" value="人文学院" />
                    <el-option label="软件工程学院" value="软件工程学院" />
                    <el-option label="社会科学学院" value="社会科学学院" />
                    <el-option label="生命科学学院" value="生命科学学院" />
                    <el-option label="生物医学交叉研究院" value="生物医学交叉研究院" />
                    <el-option label="土木水利学院" value="土木水利学院" />
                    <el-option label="新闻与传播学院" value="新闻与传播学院" />
                    <el-option label="信息科学技术学院" value="信息科学技术学院" />
                    <el-option label="药学院" value="药学院" />
                    <el-option label="医学院" value="医学院" />
                </el-select>
            </el-form-item>
            <el-form-item label="电话号码 :" prop="mobilePhoneNumber">
                <el-input v-model="userdata.mobilePhoneNumber"></el-input>
            </el-form-item>
            <el-form-item label="邮箱地址 :" prop="email">
                <el-input v-model="userdata.email"></el-input>
            </el-form-item>
            <!-- <el-form-item label="密码 :" prop="password">
                <el-input v-model="userdata.password"></el-input>
            </el-form-item> -->
            <el-form-item label="密码 :" :prop="changePassword.length>0?'password':'free'">
                <el-input v-model="userdata.password" :disabled="changePassword.length===0">
                <template #suffix>
                    <el-checkbox-group ref="" v-model="changePassword" :max="1" style="margin-right: 0px">
                        <!-- style="margin-bottom: 10px" -->
                        <el-checkbox-button value="true">
                            修改密码
                        </el-checkbox-button>
                    </el-checkbox-group>
                </template>
                </el-input>
            </el-form-item>
            
            
            


            <el-form-item label="个性签名 :" prop="account">
                <el-input v-model="userdata.signature" type="textarea" :placeholder="!userdata.signature ? '快来完善你的个人签名吧!' : ''"/>
            </el-form-item>
        </el-form>

        <div style="text-align: center;">
            <el-button @click="handle1Close">取消</el-button>
            <el-button type="primary" @click="submitMyUpdate">提交</el-button>
        </div>
    </el-dialog>


    <el-dialog
    style="text-align:center;display: flex;justify-content: center;flex-direction: column;"
    title="上传头像"
    v-model="dialog2Visible"
    @update:visible="val => dialog2Visible = val"
    width="40%"
    height="auto"
    :before-close="handle2Close">
        <div style="width: 150px;height: 120px; text-align: center; margin-left: 210px;" class="avatar">
            <!-- <img v-if="userdata.avatar" :src="userdata.avatar" style="width: 100%;height: 100%;"  />
            <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon> -->
            <img :src="userdata.avatar" style="width: 100%;height: 100%;"  />
        </div>
        <!-- <img v-if="sysuser.avatar" :src="sysuser.avatar" class="avatar" /> -->
        <div style="margin: 15px;text-align: center;">
            <el-upload
            ref="uploadRef"
            :action="uploadurl"
            :auto-upload="false"
            :limit="1"
            name="image"
            :on-success="handleAvatarSuccess"
            >
                <template #trigger>
                <!-- <div style="display: flex;width: 40%;height: auto;justify-content: left;margin-left: -150%;margin-top: 15px;"> -->
                <!-- <div style="text-align: center"> -->
                    <el-button type="primary">选择图片</el-button>
                </template>
                    <el-button style="margin-left: 10px;" type="success" @click="submitUpload">上传图片</el-button>
                <!-- </div> -->

                <template #tip>
                    <div class="el-upload__tip">
                        小于500kb的jpg/png文件
                    </div>
                </template>
            </el-upload>
        </div>
    </el-dialog>

</template>

<script>
    import { ElMessage } from 'element-plus'
    import { updateMyInfoService } from '@/api/userInfo.js'
    import { getMyInfoService } from '@/api/userInfo.js'
    import {updateMyAvatarService} from '@/api/userInfo.js'
    

    export default{
        data() {
            return {
                dialog1Visible:false,
                dialog2Visible:false,
                isConditionMet:(this.$store.state.id==this.$route.params.id),
                userdata:[

                ],
                changePassword:[],
                rules:{
                    password: [
                        { required: true, message: "账号密码不能为空", trigger: "blur" },
                        // { pattern: /^(?=.*[a-zA-Z])(?=.*\d).{8,}$/, message: '密码必须包含数字与字母,且不少于8位', trigger: 'blur' }
                        { pattern: /^[\w@#%]{5,20}$/, message: '密码由5-20位数字字母下划线或@#$%组成', trigger: 'blur' }
                        // /^[\\w@#$%]{5,20}$/
                        // /^[\w@#%]{5,20}$/
                    ],
                    mobilePhoneNumber:[
                        { required: true, message: '电话号码不能为空', trigger: 'blur' },
                        { pattern: /^1[3456789]\d{9}$/, message: '请输入有效的电话号码', trigger: 'blur' },
                    ],
                    email:[
                        { required: true, message: '邮箱地址不能为空', trigger: 'blur' },
                        { pattern: /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/, message: '请输入有效的邮箱地址', trigger: 'blur' },
                    ]
                },
                uploadurl:import.meta.env.VITE_API_URL+"/upload"
            };
        },
        created(){
            getMyInfoService(this.$route.params.id).then((res) =>{
                console.log("fid"+this.$store.state.id)
                console.log("sid"+this.$route.params.id)
                console.log("trf"+(this.$store.state.id==this.$route.params.id))
                console.log("返回参数")
                console.log(res)
                this.userdata = res.data
                this.userdata.password=''
                console.log("111111111111111111111111111111111111"+this.userdata)
            }).catch((reason) =>{

            })
        },
        methods: {
            submitMyUpdate(){
                console.log(this.userdata.deptName)
                this.$refs["userInfo1"].validate(valid => {
                    // console.log('----------',this.userInfo1);
                    if(valid){
                        if(this.changePassword.length===0){
                            this.userdata.password=null;
                        }
                        console.log("这是更新后的密码"+this.userdata.password)
                        updateMyInfoService(this.userdata).then((res)=>{
                            console.log("修改信息返回参数"+res)
                            if(res){
                                this.userdata=res.data
                                ElMessage({
                                    showClose: true,
                                    message: '个人信息修改成功。',
                                    type: 'success',
                                })
                                this.dialog1Visible=false
                                // this.location.reload
                                // this.$router.replace({ path: '/myInfo' });
                                window.location.reload();
                                console.log('rererereload')
                            }
                            else{
                                ElMessage({
                                    showClose: true,
                                    message: '修改信息失败，请重试。',
                                    type: 'error',
                                })
                            }
                        }).catch((reason) =>{

                        })
                    }
                    else{
                        ElMessage({
                            showClose: true,
                            message: '修改信息失败，请检查输入信息的格式。',
                            type: 'error',
                        })
                    }
                })
            },
            showdiv1(){
                console.log('编辑个人信息')
                this.dialog1Visible = true
            },
            showdiv2(){
                console.log('上传头像弹窗',this.dialog2Visible)
                this.dialog2Visible = true
            },
            handle1Close(key, keyPath) {
                this.dialog1Visible=false
                console.log(key, keyPath);
            },
            handle2Close(key, keyPath) {
                this.dialog2Visible=false
                console.log(key, keyPath);
            },
            submitUpload(){
                console.log('上传头像hhhhhhhhhhhh'+this.uploadRef)
                if(this.$refs.uploadRef){
                    this.$refs.uploadRef.submit()
                    getMyInfoService(this.$store.state.id)
                    console.log("state.id")
                    console.log(this.$store.state.id)
                }
                else{
                    ElMessage({
                        showClose: true,
                        message: '请先选择要上传的图片。',
                        type: 'error',
                    })
                }
            },
            handleAvatarSuccess(response){
                console.log(response);
                console.log("responseresponseresponseresponse========>"+response)
                updateMyAvatarService(this.userdata.id,response.data).then((res)=>{
                    this.userdata=res;
                })
                
                this.dialog2Visible=false
                window.location.reload();
            }
        },
    };
</script>

<style lang="less" scoped>
    .avatar-uploader .avatar {
        width: 178px;
        height: 178px;
        display: block;
    }

    .avatar-uploader .el-upload {
        border: 1px dashed var(--el-border-color);
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
        transition: var(--el-transition-duration-fast);
    }

    .avatar-uploader .el-upload:hover {
        border-color: var(--el-color-primary);
    }

    .el-icon.avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 178px;
        height: 178px;
        text-align: center;
    }

    :deep(.el-dialog .el-dialog__body){
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .formstyle{
        height:100%;
    }

    .num_text {
        color: #999;
    }

    .num_number {
        font-size: 20px;
        color: #333;
    }

    .user_num {
        width: 40%;
        height: 100%;
        display: flex;
        align-items: center;
    }

    .user_num > div {
        text-align: center;
        border-right: 1px dotted #999;
        box-sizing: border-box;
        width: 80px;
        height: 40px;
        line-height: 20px;
    }

    .user-v-font {
        font-size: 15px;
        color: #00c3ff;
    }

    .user_qianming {
        font-size: 14px;
        color: #999;
    }

    .PersonTop {
        width: 70%;
        height: 20vh;
        padding-top: 20px;
        background-color: white;
        margin-top: 30px;
        position: absolute;
        left: 50%;
        transform: translateX(-50%);
        display: flex;
        border-radius: 5px;
    }

    .PersonTop_img {
        width: 120px;
        height: 120px;
        background-color: #8c939d;
        margin-right: 24px;
        margin-left: 20px;
        overflow: hidden;
        border-radius: 20px;
    }

    .PersonTop_img img {
        width: 100%;
        height: 100%;
        border-radius: 20px;
    }

    .PersonTop_text {
        height: 120px;
        width: 880px;
        display: flex;
    }

    .user_text {
        width: 60%;
        height: 100%;
        line-height: 30px;
    }

    .user_name {
        font-weight: bold;
    }

    .user-v {
        margin-bottom: -5px;
    }

    .el-button {
        width: 84px;
    }

    .updateinfo {
        height: 100%;
        overflow: auto;
    }

    .left {
        float: left;
        width:250px;
    }

    .right {
        overflow: hidden;
    }

    .el-button {
        width: 84px;
    }

</style>