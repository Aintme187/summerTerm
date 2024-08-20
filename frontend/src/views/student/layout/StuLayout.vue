<script setup>
import { ref } from 'vue'
import { useUserStore } from '@/stores/user'
import { logoutService } from '@/api/login.js'
import { useRouter } from 'vue-router'

//两个需要导入的element组件
import { Management, Promotion } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'

import HeaderforAll from '@/components/HeaderforAll.vue'

//设置用户名字
const userName = ref('')
const userStore = useUserStore()
userName.value = userStore.userName

//登出功能
const router = useRouter()
const logout = async () => {
  //1.后端发送请求 删除Redis数据库缓存token
  await logoutService(userStore.token)
  //2.前端清空token信息
  userStore.setToken('')

  ElMessage.success('登出成功')
  router.push('/login')
}
</script>

<template>
  <el-container style="height: 95vh">

    <el-header>
      <HeaderforAll/>
    </el-header>

    <!-- <el-header class="mainHeader">
      <div>
        <div style="display: flex; align-items: center">
          <img src="@/assets/logo_school.png" />
          <div style="color: #ffffff; font-size: 30px">教务管理系统</div>
        </div>
      </div>
      <div style="display: flex; align-items: center">
        <div>欢迎您：{{ userName }} 同学</div>
        <div style="margin-left: 10px">
          <el-button type="primary" @click="logout">登出</el-button>
        </div>
      </div>
    </el-header> -->

    <el-container>
      <el-aside width="200px" style="background-color: #F5F7FA;">
        <div class="el-aside__logo" style="background-color: #F5F7FA; display: flex;  align-items: center;">
          <span margin-left class="title" style="margin-left: 30px;">
            我的学习
          </span>
        </div>
        <el-menu
          active-text-color="#ffd04b"
          background-color="#F5F7FA"
          :default-active="$route.path"
          text-color="#303133"
          router
        >
          <el-menu-item index="/student/info">
            <el-icon><Management /></el-icon>
            <span>学生信息</span>
          </el-menu-item>
          <el-menu-item index="/student/selectCourse">
            <el-icon><Promotion /></el-icon>
            <span>选课管理</span>
          </el-menu-item>
          <el-menu-item index="/student/querySelectedCourse">
            <el-icon><Promotion /></el-icon>
            <span>已选课程</span>
          </el-menu-item>
          <el-menu-item index="/student/myCourse">
            <el-icon><Promotion /></el-icon>
            <span>课程资料</span>
          </el-menu-item>
        </el-menu>
      </el-aside>
      <el-container style="height: 90vh;">
        <el-main>
          <router-view></router-view>
        </el-main>
        <!-- <el-footer>教务管理系统 ©2023 Created by 软件工程第十组</el-footer> -->
      </el-container>
    </el-container> 
  </el-container>
</template>

<style scoped lang="scss">
  // .layout-container {
  //   height: 100vh;
  // }
  .title{
    font-size: 20px;
    font-weight: bold;
    color: #333;
  }
  .el-aside {
    height: 90vh;
      background-color: #232323;
      &__logo {
        height: 120px;
        // background: url('@/assets/logo.png') no-repeat center / 120px auto;
      }
      .el-menu {
        border-right: none;
      }
    }
  // .el-header {
  //   background-color: rgb(4, 131, 213);
  //   display: flex;
  //   align-items: center;
  //   justify-content: space-between;
  //   .el-dropdown__box {
  //     display: flex;
  //     align-items: center;
  //     .el-icon {
  //       color: #999;
  //       margin-left: 10px;
  //     }

  //     &:active,
  //     &:focus {
  //       outline: none;
  //     }
  //   }
  // }
  .el-footer {
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 14px;
    color: #666;
  }

  // .mainHeader {
  //   display: flex;
  //   justify-content: space-between;
  //   background-color: rgb(4, 131, 213);
  // }
</style>
