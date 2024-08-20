import request from '@/request'


export function getArticlesApi(query, page) {
  return request({
    url: '/articles',
    method: 'post',
    data: {
      page: page.pageNumber,
      pageSize: page.pageSize,
      name: page.name,
      sort: page.sort,
      year: query.year,
      month: query.month,
      tagId: query.tagId,
      categoryId: query.categoryId,
      favoriteId: query.favoriteId
    }
  })
}

export function getHotArticesApi() {
  return request({
    url: '/articles/hot',
    method: 'post'
  })
}

export function getHotArticlesByUserApi(id){
  return request({
    url: `/articles/hot/${id}`,
    method: 'post'
  })
}

export function getRelatedArticlesApi(tags){
  return request({
    url: 'articles/related',
    method: 'post',
    data: tags
  })
}

export function getNewArticesApi() {
  return request({
    url: '/articles/new',
    method: 'post'
  })
}

export function viewArticleApi(id) {
  return request({
    url: `/articles/view/${id}`,
    method: 'post'
  })
}

export function getArticlesByCategoryApi(id) {
  return request({
    url: `/articles/category/${id}`,
    method: 'post'
  })
}

export function getArticlesByTagApi(id) {
  return request({
    url: `/articles/tag/${id}`,
    method: 'post'
  })
}

export function getArticlesByFavoriteApi(id, token) {
  return request({
    headers: {'Authorization': token},
    url: `/articles/favorite/${id}`,
    method: 'post'
  })
}



export function publishArticleApi(articleParam, multipartFile, token) {
  const formData = new FormData();

  // 将JSON对象转换为字符串并附加到FormData
  formData.append('articleParam', JSON.stringify(articleParam));

  // 将文件附加到FormData
  formData.append('file', multipartFile);
  return request({
    headers: {'Authorization': token},
    url: '/articles/publish',
    method: 'post',
    data: formData

  })
}

export function searchArticleApi(search) {
  return request({
    url: '/articles/search',
    method: 'post',
    data: {"search":search}
  })
}

export function listArchivesApi() {
  return request({
    url: '/articles/listArchives',
    method: 'post'
  })
}

export function getArticleByIdApi(id) {
  return request({
    url: `/articles/${id}`,
    method: 'post'
  })
}

export function likeApi(id, token){
  return request({
    headers: {'Authorization': token},
    url: `/articles/like/${id}`,
    method: 'post'
  })
}

export function getMyBlogsApi(id, categoryId){
  return request({
    url: `/articles/myBlogs/${id}/${categoryId}`,
    method: 'post',
  })
}


