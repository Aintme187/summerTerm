import request from '@/request'
import {getArticlesApi} from "@/api/article.js";

export function getMessagesApi(innerPage, token) {
  return request({
    headers: {'Authorization': token},
    url: '/messages/list',
    method: 'post',
    data: {
      page: innerPage.pageNumber,
      pageSize: innerPage.pageSize,
    }
  })
}