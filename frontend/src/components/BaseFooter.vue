<template>
  <el-footer class="me-el-basefooter">
    <div class="me-footer">
      <p>BUAA
        <strong>
          <router-link to="/" class="me-login-design-color">博客系统</router-link>
        </strong>
      </p>
    </div>
  </el-footer>
</template>

<script setup>
// 如果组件逻辑简单，可以不写任何逻辑
// 如果需要引入Composition API的功能，可以在这里引入
</script>

<style scoped>

  .me-el-basefooter {
    min-width: 100%;

    background-color: white;
    position: fixed;
    bottom: 0;
    left: 0;
    z-index: 1024;
    height:80px;
    align-items: center;
    justify-content: center;
    text-align: center;
  }

  .me-footer {
    text-align: center;
    line-height: 60px;
    font-family: 'Open Sans', sans-serif;
    font-size: 18px;

  }

  .me-login-design-color {
    color: #5FB878 !important;
  }

</style>
