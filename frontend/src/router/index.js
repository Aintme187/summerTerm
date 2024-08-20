import {createRouter, createWebHashHistory, useRoute, useRouter} from "vue-router";
import store from "@/store/index.js";
import Home from "@/Home.vue";
import {getToken} from "@/request/token.js";
import {ElMessage} from "element-plus";
import Login from '@/views/Login2.vue'
import Usercenter from '@/views/Usercenter.vue'

const routes = [
    {path: '/write/:id?', component: (() => import("@/views/blog/BlogWrite.vue")), meta: {requireLogin: true},},
    {
        path: '/blog',
        redirect: '/blog/home',
        // name: 'Home',
        component: Home,
        children: [
            {path: 'home', component: (() => import("@/views/Index.vue"))},
            {path: 'myBlogs/:id', component: (() => import("@/views/blog/myBlogs.vue"))},
            {path: 'log', component: (() => import("@/views/Log.vue"))},
            {path: 'messages', component: (() => import("@/views/message/MessageView.vue"))},
            // {path: '/messageBoard', component: (() => import("@/views/MessageBoard.vue"))},
            {path: 'view/:id', component: (() => import("@/views/blog/BlogView.vue"))},
            {path: ':type/all', component: (() => import("@/views/blog/BlogAllCategoryTag.vue"))},
            {path: ':type/:id', component: (() => import("@/views/blog/BlogCategoryTag.vue"))}
        ]
    },
    {path: '/login', component: Login},
    // {path: '/register', component: (() => import('@/views/Register.vue'))},
    {
        path: "/admin",
        redirect: "/admin/home",
        component: (() => import("@/components/admin/layout/Header.vue")),
        children: [
            {path: "home", component: (() => import("@/components/admin/layout/Home.vue"))},
            {path: "users/manage", component: (() => import("@/components/admin/user/Users.vue"))},
            {path: "users/add", component: (() => import("@/components/admin/user/UserAdd.vue"))},
            {path: "users/edit/:ids", component: (() => import("@/components/admin/user/UserEdit.vue"))},
            {path: "users/info/:id", component: (() => import("@/components/admin/user/UserInfo.vue"))},
            {path: "curriculums/manage", component: (() => import("@/components/admin/curriculum/Curriculums.vue"))},
            {path: "curriculums/add", component: (() => import("@/components/admin/curriculum/CurriculumAdd.vue"))},
            {
                path: "curriculums/edit/:ids",
                component: (() => import("@/components/admin/curriculum/CurriculumEdit.vue"))
            },
            {
                path: "curriculums/info/:id",
                component: (() => import("@/components/admin/curriculum/CurriculumInfo.vue"))
            },
            {path: "courses/manage", component: (() => import("@/components/admin/course/Courses.vue"))},
            {path: "courses/add", component: (() => import("@/components/admin/course/CourseAdd.vue"))},
            {path: "courses/edit/:ids", component: (() => import("@/components/admin/course/CourseEdit.vue"))},
            {path: "courses/info/:id", component: (() => import("@/components/admin/course/CourseInfo.vue"))},
        ]
    },
    {
        path: "/usercenter",
        component: Usercenter,
        redirect: "/usercenter/myInfo/:id",
        children: [
            {path: "myInfo/:id", name: "MyInfo", component: () => import('@/components/usercenter/MyInfo.vue')},
            {path: "myBlog/:id", name: "MyBlog", component: () => import('@/components/usercenter/MyBlog.vue')},
            {path: "myFav/:id", name: "MyFav", component: () => import('@/components/usercenter/MyFav.vue')}
        ]
    },
    {
        path: '/student',
        component: () => import('@/views/student/layout/StuLayout.vue'),
        redirect: '/student/info',
        children: [
            {
                path: 'info',
                component: () => import('@/views/student/StuInfo.vue')
            },
            {
                path: 'selectCourse',
                component: () => import('@/views/student/SelectCourse.vue')
            },
            {
                path: 'querySelectedCourse',
                component: () => import('@/views/student/QuerySelectedCourse.vue')
            },
            {
                path: 'myCourse',
                component: () => import('@/views/student/myCourse.vue')
            },
            {
                path: 'Assignment',
                component: () => import('@/views/student/Assignment.vue'),
                props: route => ({courseId: route.query.courseId})
            },
            {
                path: 'AssignmentForTA',
                component: () => import('@/views/student/AssignmentManager.vue'),
                props: route => ({courseId: route.query.courseId})
            }
        ]
    },
    {
        path: '/teacher',
        component: () => import('@/views/teacher/layout/TeaLayout.vue'),
        redirect: '/teacher/courseTable',
        children: [
            {
                path: 'courseTable',
                component: () => import('@/views/teacher/TeacherCourseTable.vue')
            },
            {
                path: 'memberManage',
                component: () => import('@/views/teacher/MemberManager.vue')
            },
            {
                path: 'assignmentManage',
                component: () => import('@/views/teacher/assignmentManager.vue')
            },
            {
                path: 'courses',
                component: () => import('@/views/teacher/Courses&Material.vue')
            }
        ]
    },
    {
        path: "/",
        redirect: "/login"
    }
]

const router = createRouter({
    history: createWebHashHistory(),
    routes: routes,
    scrollBehavior(to, from, savedPosition) {
        return {x: 0, y: 0}
    }
})

// 路由守卫可以通过路由实例的beforeEach方法设置
router.beforeEach((to, from, next) => {
    const token = getToken();
    if (token) {
        // console.log("===============bug")
        // console.log("===============bug")
        console.log(token)
        // console.log("===============bug")
        // console.log("===============bug")
        if (to.path === '/login') {
            next({path: '/'});
        } else {
            const account = store.state.account;
            if (account.length === 0) {
                store.dispatch('getUserInfo').then(() => {
                    next();
                }).catch(() => {
                    // 这里需要引入Message插件或其它方式来显示消息
                    ElMessage({
                        type: 'warning',
                        showClose: true,
                        message: '请先登录哦'
                    })
                    next({path: '/'});
                });
            } else {
                next();
            }
        }
    } else {
        if (to.path !== '/login') {
            // if (to.matched.some(r => r.meta.requireLogin)) {
            // 同样需要引入Message插件或其它方式来显示消息
            ElMessage({
                type: 'warning',
                showClose: true,
                message: '请先登录哦'
            })
            // }
            // console.log("173717371371")
            // console.log("173717371371")
            // console.log("173717371371")
            next({path: '/login'});
        } else next()
    }
});

export {router}
