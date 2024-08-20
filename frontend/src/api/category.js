import request from '@/request'
import {getArticlesApi} from "@/api/article.js";

export function getAllCategorysApi() {
  return request({
    url: '/categorys',
    method: 'get',
  })
}

export function getAllCategorysDetailApi() {
  return request({
    url: '/categorys/detail',
    method: 'get',
  })
}

export function getCategoryApi(id) {
  return request({
    url: `/categorys/${id}`,
    method: 'get',
  })
}

export function getCategoryDetailApi(id) {
  return request({
    url: `/categorys/detail/${id}`,
    method: 'get',
  })
}

export function createCategoryApi(category, token) {
  return request({
    headers: {'Authorization': token},
    url: `/categorys/create`,
    method: 'post',
    data:category
  })
}
