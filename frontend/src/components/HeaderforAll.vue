<template>
        <!-- :default-active="1" -->
        <!-- :active-color="#409eff" -->
        <!-- background-color="#545c64"
        text-color="#fff" -->
        <!-- ref="activeIndex" -->
        <el-menu
        class="el-menu-demo"
        mode="horizontal"
        active-text-color="#ffd04b"
        :ellipsis="false"
        @select="handleSelect"
        :default-active="this.$router.path"
        >
            <div class="flex-grow"> 
                <img class="img" src="http://seicj6zi6.hb-bkt.clouddn.com/%E8%BD%AF%E4%BB%B6%E5%AD%A6%E9%99%A2.jpg"> 
                <span class="title">教务系统</span>
            </div>

            <div class="flex-grow2"> 
                <!-- <el-menu-item 
                index="1"
                >
                    首页
                </el-menu-item> -->
                <el-menu-item 
                @click="addRoutestoTeachingModule"
                index="/1"
                >
                <!-- <span v-if >
                    我的学习
                </span> -->
                <span v-if="userdata.permission === 'teacher'">我的教学</span>
                <span v-else>我的学习</span>
                </el-menu-item>
                <el-menu-item 
                index="/2"
                @click="addRoutestoBlogModule"
                >
                    博客中心
                </el-menu-item>
                <el-menu-item 
                index="/3"
                @click="addRoutestoUserCenter"
                >
                    个人中心
                </el-menu-item>
            </div>
            <!-- <div class="flex-grow"> 
                <img src="../assets/portrait.png">
            </div> -->
            <div class="navbar">
                <img class="avatar" :src=userdata.avatar>
                <span class="user">{{ userdata.nickname }}</span>
                <el-button style="margin-right: 10px;" type="primary" plain size="default" @click="logout">登出</el-button>
            </div>
        </el-menu>
</template>

<script>
    import { getMyInfoService } from '@/api/header.js';
    export default {
        name:'Header',
        data() {
            return {
                userdata:[
                    
                ],
            };
        },
        methods:{
            addRoutestoUserCenter () {
                this.$router.push(`/usercenter/myInfo/${this.$store.state.id}`)
                this.activeIndex="3";
                console.log(this.$store.state.id+"bbbbbbbbbbbbbbbbbbbbaaaaaaaaaaaaaaaaaaaaaa")
            },
            addRoutestoTeachingModule () {
                if(this.userdata.permission === 'teacher'){
                    this.$router.push('/teacher')
                    this.activeIndex="1";
                }
                else if(this.userdata.permission === 'student'){
                    this.$router.push('/student')
                    this.activeIndex="2";
                }
            },
            addRoutestoBlogModule () {
                this.$router.push('/blog/home')
            },
            logout(){
                // Dispatch logout action
                // store.dispatch('logout').then(() => {
                //     ElMessage({message: '退出登录成功', type:"success", showClose: true});
                //     router.push({path: '/'})
                // }).catch((error) => {
                //     if (error !== 'error') {
                //     ElMessage({message: error, type: 'error', showClose: true});
                //     }
                // })
                this.$store.dispatch('logout').then(() => {
                    this.$message({ message: '退出登录成功', type: 'success', showClose: true });
                    this.$router.push({ path: '/' });
                }).catch((error) => {
                    if (error !== 'error') {
                        this.$message({ message: error, type: 'error', showClose: true });
                    }
                });
            },
        },
        created(){
            getMyInfoService(this.$store.state.id).then((res) =>{
                console.log(res)
                this.userdata = res.data
            }).catch((reason) =>{
                
            })
        }
    }
</script>

<style scoped>
    .user{
        font-size: 15px;
        margin-right:10px;
        color: #909399;
        font-weight: bold;
    }
    .center{
        display: flex; 
        justify-content: flex-start;
    }
    .img{
        height: 50px;
        width: 50px;
        margin-top: 3px;
        margin-right: 10px;
        margin-left: 8px;
    }
    .flex-grow {
        flex-grow: 1;
        display: flex;
        align-items: center;
        /* height: 100vh; */
    }
    .flex-grow2 {
        flex-grow: 15;
        display: flex;
        align-items: center;
        /* height: 100vh; */
    }
    .title{
        font-size: 24px;
        font-weight: bold;
        color: #333;
    }
    .navbar {
        display: flex; /* 使用 Flexbox 布局 */
        justify-content: space-between; /* 将子元素在父容器中分散对齐，使其紧贴右侧 */
        display: flex;
        align-items: center;
    }
    .avatar{
        border-radius: 50%; 
        height: 40px;
        width: 40px;
        /* margin-top: 10px; */
        margin-right: 10px;
        margin-left: 8px;
    }
</style>