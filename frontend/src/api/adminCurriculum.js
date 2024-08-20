import Request from "@/request/index.js";

function getCurriculumsApi(page, pageSize, filterData) {
    // ES6 属性简写
    const data = {page, pageSize, "filterDataList": filterData}
    return Request({
        url: "/admin/curriculum/list",
        method: "POST",
        data,
    })
}

function addCurriculumApi(curriculum) {
    const data = curriculum
    return Request({
        url: "/admin/curriculum/add",
        method: "POST",
        data,
    })
}

function deleteCurriculumApi(id) {
    return Request({
        url: "/admin/curriculum/delete/" + id,
        method: "POST",
    })
}

function batchDeleteCurriculumsApi(ids) {
    const data = ids
    return Request({
        url: "/admin/curriculum/batch/delete",
        method: "POST",
        data
    })
}

function getCurriculumInfoApi(id) {
    return Request({
        url: "/admin/curriculum/info/" + id,
        method: "GET",
    })
}

function updateCurriculumApi(curriculum) {
    const data = curriculum
    return Request({
        url: "/admin/curriculum/update",
        method: "POST",
        data
    })
}

function batchUpdateCurriculumsApi(ids, curriculum) {
    const data = {ids, curriculum}
    return Request({
        url: "/admin/curriculum/batch/update",
        method: "POST",
        data
    })
}

export {
    getCurriculumsApi,
    addCurriculumApi,
    deleteCurriculumApi,
    batchDeleteCurriculumsApi,
    getCurriculumInfoApi,
    updateCurriculumApi,
    batchUpdateCurriculumsApi
}