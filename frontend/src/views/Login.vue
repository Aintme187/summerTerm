<template>
  <div id="login" v-title data-title="登录 - 博客系统">
    <!--<video preload="auto" class="me-video-player" autoplay="autoplay" loop="loop">
      <source src="../../static/vedio/sea.mp4" type="video/mp4">
    </video>-->

    <div class="me-login-box me-login-box-radius">
      <h1>博客系统 登录</h1>

      <el-form :model="userForm" :rules="rules" ref="formRef">
        <el-form-item prop="account">
          <el-input v-model="userForm.account" placeholder="用户名"></el-input>
        </el-form-item>

        <el-form-item prop="password">
          <el-input v-model="userForm.password" placeholder="密码" type="password"></el-input>
        </el-form-item>

        <el-form-item size="small" class="me-login-button">
          <el-button type="primary" @click.native.prevent="login()">登录</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';
import {rules} from "@/utils/validator.js";
const userForm = ref({
  account: '',
  password: ''
});



const store = useStore();
const router = useRouter();
const formRef = ref();
const login = () => {

  formRef.value.validate((valid) => {
    if (valid) {
      store.dispatch('login', userForm.value).then(() => {
        router.go({ path: '/'});
        ElMessage({message: '登录成功', type:"success", showClose: true});
      }).catch((error) => {
        if (error !== 'error') {
          ElMessage({ message: error, type: 'error', showClose: true });
        }
      });
    } else {
      return false;
    }
  });
};
</script>

<style scoped>
#login {
  min-width: 100%;
  min-height: 100%;
}

.me-video-player {
  background-color: transparent;
  width: 100%;
  height: 100%;
  object-fit: fill;
  display: block;
  position: absolute;
  left: 0;
  z-index: 0;
  top: 0;
}

.me-login-box {
  position: absolute;
  width: 300px;
  height: 260px;
  background-color: white;
  margin-top: 150px;
  margin-left: -180px;
  left: 50%;
  padding: 30px;
}

.me-login-box-radius {
  border-radius: 10px;
  box-shadow: 0px 0px 1px 1px rgba(161, 159, 159, 0.1);
}

.me-login-box h1 {
  text-align: center;
  font-size: 24px;
  margin-bottom: 20px;
  vertical-align: middle;
}

.me-login-design {
  text-align: center;
  font-family: 'Open Sans', sans-serif;
  font-size: 18px;
}

.me-login-design-color {
  color: #5FB878 !important;
}

.me-login-button {
  text-align: center;
}

.me-login-button button {
  width: 100%;
}
</style>
