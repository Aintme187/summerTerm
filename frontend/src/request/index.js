import store from '@/store'
import {getToken} from '@/request/token'
import axios from "axios";
import {ElMessage} from "element-plus";
import {useRouter} from "vue-router";

const router = useRouter()

const service = axios.create({
    // config here
    baseURL: import.meta.env.VITE_API_URL,
    timeout: 10000
})

//request拦截器
service.interceptors.request.use(config => {

    if (store.state.token) {
        config.headers['Authorization'] = getToken()
    }
    //bug here for test
    else config.headers['Authorization'] = "Token"
    return config
}, error => {

    Promise.reject(error)
})

// response拦截器
service.interceptors.response.use(
    response => {
        console.log('test');
        console.log(response);
        //全局统一处理 Session超时
        if (response.headers['session_time_out'] == 'timeout') {
            store.dispatch('fedLogOut')
        }

        const res = response.data;

        //0 为成功状态
        if (!res.code) {
            return response;
        }
        if (res.code !== 200) {

            //90001 Session超时
            if (res.code === 90001) {
                return Promise.reject('error');
            }

            //20001 用户未登录
            if (res.code === 90002) {

                ElMessage({
                    type: 'warning',
                    showClose: true,
                    message: '未登录或登录超时，请重新登录哦'
                })
                console.log("======================bug")
                console.log("======================bug")
                console.log("======================bug")
                router.push("/login")
                // return Promise.reject('error');
            }

            //70001 权限认证错误
            if (res.code === 70001) {
                ElMessage({
                    type: 'warning',
                    showClose: true,
                    message: '你没有权限访问哦'
                })
                return Promise.reject('error');
            }

            return Promise.reject(res.msg);
        } else {
            return response.data;
        }
    },
    error => {
        ElMessage({
            type: 'warning',
            showClose: true,
            message: '连接超时'
        })
        return Promise.reject('error')
    })

export default service
