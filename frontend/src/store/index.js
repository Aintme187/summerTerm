import {getToken, setToken, removeToken} from '@/request/token'
import {login, getUserInfo, logout, register} from '@/api/login'
import Vuex from 'vuex'

export default new Vuex.Store({
    state: {
        id: '',
        account: '',
        name: '',
        avatar: '',
        token: getToken(),
    },
    mutations: {
        SET_TOKEN: (state, token) => {
            state.token = token;
        },
        SET_ACCOUNT: (state, account) => {
            state.account = account
        },
        SET_NAME: (state, name) => {
            state.name = name
        },
        SET_AVATAR: (state, avatar) => {
            state.avatar = avatar
        },
        SET_ID: (state, id) => {
            state.id = id
        },
        SET_PERMISSION: (state, permission) => {
            state.permission = permission
        }
    },
    actions: {
        login({commit}, user) {
            return new Promise((resolve, reject) => {
                login(user.account, user.password).then(data => {
                    if (data.success) {
                        commit('SET_TOKEN', data.data)
                        setToken(data.data)

                        resolve()
                    } else {
                        reject(data.msg)
                    }
                }).catch(error => {
                    reject(error)
                })
            })
        },
        // 获取用户信息
        getUserInfo({commit, state}) {

            return new Promise((resolve, reject) => {
                getUserInfo(state.token).then(data => {
                    if (data.success) {
                        commit('SET_ACCOUNT', data.data.account)
                        commit('SET_NAME', data.data.nickname)
                        commit('SET_AVATAR', data.data.avatar)
                        commit('SET_ID', data.data.id)
                        commit('SET_PERMISSION',data.data.permission)
                        resolve(data)
                    } else {
                        commit('SET_ACCOUNT', '')
                        commit('SET_NAME', '')
                        commit('SET_AVATAR', '')
                        commit('SET_ID', '')
                        commit('SET_PERMISSION', '')
                        removeToken()
                        resolve(data)
                    }

                }).catch(error => {
                    commit('SET_ACCOUNT', '')
                    commit('SET_NAME', '')
                    commit('SET_AVATAR', '')
                    commit('SET_ID', '')
                    commit('SET_PERMISSION', '')
                    removeToken()
                    reject(error)
                })
            })
        },
        // 退出
        logout({commit, state}) {
            return new Promise((resolve, reject) => {
                logout(state.token).then(data => {
                    if (data.success) {

                        commit('SET_TOKEN', '')
                        commit('SET_ACCOUNT', '')
                        commit('SET_NAME', '')
                        commit('SET_AVATAR', '')
                        commit('SET_ID', '')
                        removeToken()
                        resolve()
                    }

                }).catch(error => {
                    reject(error)
                })
            })
        },
        // 前端 登出
        fedLogOut({commit}) {
            return new Promise(resolve => {
                commit('SET_TOKEN', '')
                commit('SET_ACCOUNT', '')
                commit('SET_NAME', '')
                commit('SET_AVATAR', '')
                commit('SET_ID', '')
                removeToken()
                resolve()
            }).catch(error => {
                reject(error)
            })
        },
        register({commit}, user) {
            return new Promise((resolve, reject) => {
                register(user.account, user.nickname, user.password).then((data) => {
                    if (data.success) {
                        commit('SET_TOKEN', data.data)
                        setToken(data.data)
                        resolve()
                    } else {
                        reject(data.msg)
                    }
                }).catch((error) => {
                    reject(error)
                })
            })
        }
    }
})
