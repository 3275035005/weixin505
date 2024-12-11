import request from '@/utils/request'




export function  pageQuery(current, limit, data) {
  return request({
    url: `http://localhost:9001/user/pageQuery/${current}/${limit}`,
    method: 'post',
    data: data
  })
}


export function deleteById(id) {
  return request({
    url: `http://localhost:9001/user/deleteById/${id}`,
    method: 'delete'
  })
}

export function getUserInfo(account) {
  return request({
    url: `http://localhost:9001/user/getUserInfo/${account}`,
    method: 'get'
  })
}


export function insert(data){
  return request({
    url: `http://localhost:9001/user/insert`,
    method: 'post',
    data: data
  })
}

export function update(data){
  return request({
    url: `http://localhost:9001/user/update`,
    method: 'put',
    data: data
  })
}

export function getUserAll(){
  return request({
    url: `http://localhost:9001/user/getUserAll`,
    method: 'get'
  })
}

export function sign(data) {
  return request({
    url: 'http://localhost:9001/user/sign',
    method: 'post',
    data
  })
}

export function logout() {
  return request({
    url: 'http://localhost:9001/user/logout',
    method: 'get'
  })
}

export function login(data) {
  return request({
    url: 'http://localhost:9001/user/login',
    method: 'post',
    data
  })
}



export function getInfos(token) {
  return request({
    url: 'http://localhost:9001/user/getInfo/' + token,
    method: 'get'
  })
}

