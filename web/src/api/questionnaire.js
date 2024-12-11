import request from "@/utils/request";

export function pageQuery(current, limit, data) {
  return request({
    url: `http://localhost:9001/questionnaire/pageQuery/${current}/${limit}`,
    method: 'post',
    data: data
  })
}

export function deleteById(id) {
  return request({
    url: `http://localhost:9001/questionnaire/deleteById/${id}`,
    method: 'delete'
  })
}

