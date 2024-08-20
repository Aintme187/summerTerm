<script setup>

import {computed, onMounted, ref, watch, onBeforeUnmount} from "vue";
import BaseHeader from "@/views/BaseHeader.vue";
import BaseFooter from "@/components/BaseFooter.vue";
import HeaderforAll from "./components/HeaderforAll.vue";
import {useRoute} from "vue-router";
import * as THREE from "three";
import FOG from "vanta/src/vanta.fog";
  

const activeIndex = ref('/')
const footerShow = ref(true)
const route = useRoute()

let vantaEffect=null
const vantaRef=ref(null)

// 监听路由变化
watch(() => route.path, (newPath) => {
  activeIndex.value = newPath;
  footerShow.value = newPath === '/';
});

onBeforeUnmount(()=>{
    if(vantaEffect){
        vantaEffect.destroy()
    }
})

// 组件挂载时
onMounted(() => {
  activeIndex.value = route.path;
  footerShow.value = route.path === '/';

  vantaEffect = FOG({
    el: vantaRef.value,
    THREE: THREE,
    // position:fixed
    mouseControls: true,
    touchControls: true,
    gyroControls: false,
    minHeight: 200.00,
    minWidth: 200.00,
    highlightColor: 0xb8b81c,
    midtoneColor: 0xc5ff,
    lowlightColor: 0x20b61e,
    baseColor: 0xefe4e4,
    blurFactor: 0.55,
    speed: 1.10,
    zoom: 1.70
    });
  // VANTA.BIRDS({
  //   el: "#home",
  //   mouseControls: true,
  //   touchControls: true,
  //   gyroControls: false,
  //   minHeight: 200.00,
  //   minWidth: 200.00,
  //   scale: 1.00,
  //   scaleMobile: 1.00
  // })
});



</script>




<template>
  <div ref="vantaRef" class="bg" style="width:100%; height: 100vh; ">
    <el-container>
    <!-- ; -->
    <!-- id="home" -->
    <!-- <el-container style="overflow: visible;"> -->
    <!-- <div style=" overflow:scroll"> -->
      <!--<base-header :activeIndex="activeIndex"></base-header>-->
      <!-- <el-header> -->
      <!-- <div style="position:fixed;"> -->
      <!-- <el-affix> -->
      <el-header style="position: sticky;top: 0;z-index: 1024;">
        <HeaderforAll/>
      <!-- </el-affix> -->
      </el-header>
      <!-- </div> -->
      <!-- </el-header> -->
      <div>
        <br/>
      </div>
      <el-container>
        <router-view />
        <!-- <router-view class="route" style="height: 100vh;overflow: auto; " /> -->
      </el-container>
    </el-container> 
  </div>
    <!-- </el-container> -->

  <!-- </div> -->

</template>


<style scoped>
.el-header{
  background-color: white;
}

.route::-webkit-scrollbar {
  width: 0 !important;
}

</style>

<style >
.bg canvas{
  position: fixed !important;
}
</style>