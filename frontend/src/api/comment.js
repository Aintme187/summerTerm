import request from '@/request'
import {getArticlesApi} from "@/api/article.js";


export function getCommentsByArticleApi(id) {
  return request({
    url: `/comments/article/${id}`,
    method: 'get'
  })
}

export function publishCommentApi(comment,token) {
  return request({
    headers: {'Authorization': token},
    url: '/comments/create/change',
    method: 'post',
    data: comment
  })
}
