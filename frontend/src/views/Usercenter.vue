<template>
    <!-- <div class="changeablebackground"> -->
      <div ref="vantaRef" class="bg" style="width: 100%; height: 100vh">
        <!-- header组件 -->
          <MyHeader style="position: sticky;top: 0;z-index: 1024;" :user-id="this.$route.params.id"/> 
        <!-- top组件 -->
        <MyTop @submitUpload="triggermethod" @submitMyUpdate="triggermethod"></MyTop>
        <!-- 兄弟组件间通信 -->
        <div class="person_body">
          <div class="person_body_left">
            <MyAside :user-id="this.$route.params.id"></MyAside>
          </div>
          <div class="person_body_right">
            <router-view></router-view>
            <!-- <MyInfo></MyInfo> -->
          </div>
        </div>
      </div>
</template>
  
  
  
  <script>
    // import MyInfo from '@/components/MyInfo.vue';
    import MyHeader from '@/components/HeaderforAll.vue';
    import MyTop from '@/components/usercenter/MyTop.vue'
    import MyAside from '@/components/usercenter/MyAside.vue'
    import * as THREE from "three";
    import FOG from "vanta/src/vanta.fog";
  
    export default {
      components:{
        // MyInfo,
        MyHeader,
        MyTop,
        MyAside,
      },
      mounted() {
        this.vantaEffect = FOG({
          el: this.$refs.vantaRef,
          THREE: THREE,
        });
        VANTA.FOG({
          el: this.$refs.vantaRef,
          /*以下为样式配置*/
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
      },
      methods:{
        triggermethod(){
          this.$refs.borther.regetInfo()
        },
        beforeDestroy() {
          if (this.vantaEffect) {
            this.vantaEffect.destroy();
          }
        },
      }
    };
  </script>
  
  <style lang="less" scoped>
    
  
    .changeablebackground{
      position: fixed;
      height: 100%;
      width: 100%;
      top: 0;
      left: 0;
      background-image: linear-gradient(
        90deg,
        cyan,
        #67C23A
      );
      background-size: 400%;
      animation: myanimation 10s infinite;
    }
    @keyframes myanimation {  
      0%{
        background-position: 0% 50%;
      }
      50%{
        background-position: 100% 50%;
      }
      100%{
        background-position: 0% 50%;
      }
    }
  
    .me-video-player {
      background-color: transparent;
      width: 100%;
      height: 100%;
      object-fit: fill;
      display: block;
      position: fixed;
      left: 0;
      z-index: 0;
      top: 0;
    }
  
    .person_body {
      width: 70%;
      margin-top: 240px;
      display: flex;
      position: absolute;
      left: 50%;
      transform: translateX(-50%);
      border-radius: 5px;
    }
  
    .person_body_left {
      width: 27%;
      height: 460px;
      border-radius: 5px;
      margin-right: 3%;
      text-align: center;
    }
  
    .person_body_right {
      width: 70%;
      border-radius: 5px;
      background-color: white;  
    }

    .el-header{
      background-color: white;
    }
   
</style>