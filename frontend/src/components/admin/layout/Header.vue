<script setup>
import {computed, onMounted, ref} from "vue";
import {useRouter} from "vue-router";
import Menu from "@/components/admin/layout/Menu.vue";
import {getAdminNameApi} from "@/api/adminUser.js";
import {useStore} from "vuex";
import {ElMessage} from "element-plus";

const store = useStore()
const router = useRouter()

const adminName = store.state.name
const isHome = computed(() => {
  return router.currentRoute.value.fullPath.match("home")
})

function logout() {
  store.dispatch('logout').then(() => {
    ElMessage({message: '退出登录成功', type: 'success', showClose: true});
    router.push({path: '/'});
  }).catch((error) => {
    if (error !== 'error') {
      ElMessage({message: error, type: 'error', showClose: true});
    }
  });
}
</script>

<template>
  <div>
    <el-container style="height: 100vh; display: flex; flex-direction: column;">
      <!--height="100" 自适应高度-->
      <el-header height="100" style="background-color: #41758f; padding: 0">
        <div style="color: #fdfdcb; padding-inline: 20px;display: flex;  justify-content: space-between;">
          <h1>MySoftwareCareer 管理系统</h1>
          <p style="padding-top: 10px">
            欢迎，{{ adminName }}
            <span @click="logout" v-if="!isHome" style="color: #d81e06">&nbsp;退出登录</span>
          </p>
        </div>
        <div style="background-color: #264b5d; padding: 10px 0 10px 20px; color: white" v-if="!isHome">
          <span @click="router.back()"><-&nbsp;返回</span>
          |
          <router-link to="/admin/home" class="no-decoration" style="color: white;text-decoration-color: white">
            <svg t="1715524910729" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
                 p-id="7645" width="20" height="20">
              <path
                  d="M1010.795 510.814L544.922 31.665c-8.688-8.942-20.361-13.867-32.872-13.867-0.317 0-0.633 0.003-0.949 0.01-12.79-0.225-24.81 4.75-33.694 13.912L13.167 510.871C0.586 523.848-3.731 548.752 3.534 566.364c6.893 16.778 22.739 33.707 42.602 33.707h69.708v349.4c0 24.247 26.528 56.732 53.658 56.732h204.496c26.299 0 42.577-28.003 42.577-53.947V787.665h164.386v161.806c0 17.357 12.836 32.298 18.354 37.917 5.557 5.657 20.352 18.815 37.681 18.815h201.361c13.429 0 25.566-7.76 34.178-21.849 6.341-10.374 10.126-23.415 10.126-34.884v-349.4h95.212c19.863 0 35.714-16.938 42.612-33.727 7.247-17.644 2.906-42.55-9.69-55.529z m-221.933 40.379v361.213H674.759V731.631c0-9.106-1.123-20.585-9.883-28.585-7.9-7.217-18.069-8.211-27.881-8.211h-267.33c-12.388 0-21.536 1.883-28.787 5.925-11.049 6.159-17.134 17.123-17.134 30.871v180.775h-115.07V543.498c0-37.225-29.483-37.225-39.172-37.225h-13.613l355.362-366.776 356.604 366.776h-24.286c-25.876 0-54.707 18.449-54.707 44.92z"
                  fill="#ffffff" p-id="7646"></path>
            </svg>
            主页
          </router-link>
        </div>
      </el-header>
      <el-main style="flex: 1; min-height: 0; margin: 0 20px 20px 10px" :class='isHome?"none":"flex"'>
        <Menu v-if="!isHome" style="max-width: 150px;padding-right: 10px;"></Menu>
        <router-view/>
      </el-main>
    </el-container>
  </div>
</template>

<style scoped>

:deep(.center) {
  margin: 0 auto;
  text-align: center;
}

:deep(.no-decoration) {
  text-decoration: none;
}

.flex {
  display: flex;
}
</style>
