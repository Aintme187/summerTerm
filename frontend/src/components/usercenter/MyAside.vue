<template>
    <el-card class="box-card" :body-style="{ padding: '0px' }">
        <template v-slot:header>
            <div class="clearfix">
              <span class="person_body_list" style="border-bottom: none">
                个人中心
              </span>
            </div>
        </template>

        <el-menu
        default-active="info"
        active-text-color="#00c3ff"
        class="el-menu-vertical-demo"
        >

            <el-menu-item
            @click="addRoutesInfo"
            index="info"
            >
                <el-icon><UserFilled /></el-icon>
                <template v-slot:title>个人简介</template>
            </el-menu-item>

            <el-menu-item
            @click="addRoutesBlog"
            index="blog"
            >
                <el-icon><Reading /></el-icon>
                <template v-slot:title>我的博客</template>
            </el-menu-item>

            <el-menu-item
            v-if="this.$store.state.id == this.$route.params.id"
            @click="addRoutesFav"
            index="fav"
            >
                <el-icon><Collection /></el-icon>
                <template v-slot:title>我的收藏</template>
            </el-menu-item>

        </el-menu>
    </el-card>
</template>

<script>
    export default{
        props:{
            userId:Number
        },
        methods:{
            addRoutesInfo () {
                // this.$router.push('/myInfo')
                // this.$router.push(`/usercenter/myInfo/${this.$store.state.id}`)
                this.$router.push(`/usercenter/myInfo/${this.$props.userId}`)
            },
            addRoutesBlog () {
                // this.$router.push('/usercenter/myBlog')
                this.$router.push(`/blog/myBlogs/${this.$props.userId}`)
            },
            addRoutesFav () {
                // this.$router.push('/usercenter/myFav')
                this.$router.push(`/usercenter/myFav/${this.$props.userId}`)
            },
        }
    }
</script>

<style scoped>

    .box-card {
        height: 100%;
    }

    .el-menu-item>span {
        font-size: 16px;
        color: #999;
    }

    .person_body_list {
        width: 100%;
        height: 50px;
        margin-top: 25px;
        font-size: 22px;
        border-bottom: 1px solid #f0f0f0;
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

    .el-menu-item {
        margin-top: 22px;
    }
</style>