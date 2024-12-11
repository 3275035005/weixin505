import request from '@/utils/request'

export function  pageQuery(current, limit, data) {
  return request({
    url: `http://localhost:9001/topic/pageQuery/${current}/${limit}`,
    method: 'post',
    data: data
  })
}


export function deleteById(id) {
  return request({
    url: `http://localhost:9001/topic/deleteById/${id}`,
    method: 'delete'
  })
}

export function insert(data){
  return request({
    url: `http://localhost:9001/topic/insert`,
    method: 'post',
    data: data
  })
}

export function update(data){
  return request({
    url: `http://localhost:9001/topic/update`,
    method: 'put',
    data: data
  })
}

export function getTopicAll(){
  return request({
    url: `http://localhost:9001/topic/getTopicAll`,
    method: 'get'
  })
}




