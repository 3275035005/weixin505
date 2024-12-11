import request from "@/utils/request";

export function pageQuery(current, limit, data) {
  return request({
    url: `http://localhost:9001/feedback/pageQuery/${current}/${limit}`,
    method: 'post',
    data: data
  })
}

export function deleteById(id) {
  return request({
    url: `http://localhost:9001/feedback/deleteById/${id}`,
    method: 'delete'
  })
}
export function update(data){
  return request({
    url: `http://localhost:9001/feedback/update`,
    method: 'put',
    data: data
  })
}

