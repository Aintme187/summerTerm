import Request from "@/request/index.js";

function getCoursesApi(page, pageSize, filterData) {
    // ES6 属性简写
    const data = {page, pageSize, "filterDataList": filterData}
    return Request({
        url: "/admin/course/list",
        method: "POST",
        data,
    })
}

function addCourseApi(course) {
    const data = course
    return Request({
        url: "/admin/course/add",
        method: "POST",
        data,
    })
}

function deleteCourseApi(id) {
    return Request({
        url: "/admin/course/delete/" + id,
        method: "POST",
    })
}

function batchDeleteCoursesApi(ids) {
    const data = ids
    return Request({
        url: "/admin/course/batch/delete",
        method: "POST",
        data
    })
}

function getCourseInfoApi(id) {
    return Request({
        url: "/admin/course/info/" + id,
        method: "GET",
    })
}

function updateCourseApi(course) {
    const data = course
    return Request({
        url: "/admin/course/update",
        method: "POST",
        data
    })
}

function batchUpdateCoursesApi(ids, course) {
    const data = {ids, course}
    return Request({
        url: "/admin/course/batch/update",
        method: "POST",
        data
    })
}

export {
    getCoursesApi,
    addCourseApi,
    deleteCourseApi,
    batchDeleteCoursesApi,
    getCourseInfoApi,
    updateCourseApi,
    batchUpdateCoursesApi
}