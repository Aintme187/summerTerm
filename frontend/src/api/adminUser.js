import Request from "@/request/index.js";

function getAdminNameApi() {
    return Request({
        url: "/users/currentUser",
        method: "GET",
    })
}

function getUsersApi(page, pageSize, filterData) {
    // ES6 属性简写
    const data = {page, pageSize, "filterDataList": filterData}
    return Request({
        url: "/admin/user/list",
        method: "POST",
        data,
    })
}

function addUserApi(user) {
    const data = user
    return Request({
        url: "/admin/user/add",
        method: "POST",
        data,
    })
}

function deleteUserApi(id) {
    return Request({
        url: "/admin/user/delete/" + id,
        method: "POST",
    })
}

function batchDeleteUsersApi(ids) {
    const data = ids
    return Request({
        url: "/admin/user/batch/delete",
        method: "POST",
        data
    })
}

function getUserInfoApi(id) {
    return Request({
        url: "/admin/user/info/" + id,
        method: "GET",
    })
}

function updateUserApi(user) {
    const data = user
    return Request({
        url: "/admin/user/update",
        method: "POST",
        data
    })
}

function batchUpdateUsersApi(ids, user) {
    const data = {ids, "sysUserInfoVo": user}
    return Request({
        url: "/admin/user/batch/update",
        method: "POST",
        data
    })
}

export {
    getAdminNameApi,
    getUsersApi,
    addUserApi,
    deleteUserApi,
    batchDeleteUsersApi,
    getUserInfoApi,
    updateUserApi,
    batchUpdateUsersApi
}