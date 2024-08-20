import request from "@/request";
import {getArticlesApi} from "@/api/article.js";

export function getFavoriteListApi(token) {
  return request({
    headers: {'Authorization': token},
    url: '/favorites/showFavorite',
    method: 'post',
  })
}

export function getOthersFavoriteListApi(id) {
  return request({
    url: `/favorites/showFavorite/${id}`,
    method: 'post',
  })
}

export function uploadFavoriteApi(favoriteForm) {
  return request({
    url: '/favorites/upload',
    method: 'post',
    data: favoriteForm
  })
}

export function getFavoriteDetailApi(id) {
  return request({
    url: `/favorites/detail/${id}`,
    method: 'post',
  })
}

export function createApi(favorite, token) {
  return request({
    headers: {'Authorization': token},
    url: `/favorites/create`,
    method: 'post',
    data:favorite
  })
}

