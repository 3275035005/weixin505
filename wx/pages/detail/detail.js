var call = require("../../utils/request")
var help = require("../../utils/help")
Page({
  data: {
    data:{} // 存储数据
  },
  onLoad: function (options) {

    const {id} = options;
    this.getApplyDataItem(id)
  },

  /* 详情 */
  getApplyDataItem(id) {
    call.getData(`wx/getFeedbackById/${id}` , this.onSuccessInit, this.onFailInit);
  },
 

  onSuccessInit(res) {
    if(res.status_code == 1){
      this.setData({
        data: res.data
      })
    }
  },
  onFailInit() {
    help.show("网络请求失败");
  },
})