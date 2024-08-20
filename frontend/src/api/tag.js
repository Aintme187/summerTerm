import request from '@/request'
import {getArticlesApi} from "@/api/article.js";

export function getAllTagsApi() {
  return request({
    url: '/tags',
    method: 'get',
  })
}

export function getAllTagsDetailApi() {
  return request({
    url: '/tags/detail',
    method: 'get',
  })
}

export function getHotTagsApi() {
  return request({
    url: '/tags/hot',
    method: 'get',
  })
}

export function getTagApi(id) {
  return request({
    url: `/tags/${id}`,
    method: 'get',
  })
}

export function getTagDetailApi(id) {
  return request({
    url: `/tags/detail/${id}`,
    method: 'get',
  })
}

export function createTagApi(tag, token) {
  return request({
    headers: {'Authorization': token},
    url: `/tags/create`,
    method: 'post',
    data:tag
  })
}

