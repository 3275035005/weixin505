
var call = require("../../utils/request")
var help = require("../../utils/help")
var user = ''
Page({

  data: {
    tabList: [{
      name: '未回复',
      status: '0'
    }, {
      name: '已回复',
      status: '1'
    }],
    title:'',
    titleList: [], // 存储反馈类别
    status:'0',// 回复状态
    // 数据列表
		feedbackList:[]
  },


  /**
   * 最先执行
   */
  onLoad: function () {
   
    this.getInit();
    this.getTopicList();
    this.getFeedbackData();
  },

  /**
   * 获取登录用户信息
   */
  getInit(){
    user = wx.getStorageSync("user")
     // 用户信息不存在跳转登录页面
     if(user == null || user == undefined || user == ''){
      // 跳转到登录页面
      wx.reLaunch({
        url: '/pages/login/index'
     })
  }
  },
	// 获取反馈列表
	getTopicList(){
    call.getData('wx/getTopicAll' , this.onSuccessTopicAll, this.onFailTopicAll);
},

onSuccessTopicAll(res) {
  if(res.status_code == 1){
    this.setData({
      titleList :res.data
    })
  }
},
onFailTopicAll() {
  help.show("网络请求失败");
},

 /* 请选择反馈类别 */
 selectFloor(e) {
  // 查询所有
  if (e.detail.value === '请选择反馈类别') {
    this.setData({
      titleList: []
    })
    this.setData({
      title :null
    })
   
  } else {
    this.setData({
      title :e.detail.value
    })
  }
  this.getFeedbackData();
},

  /* 选择处理状态 */
  selectStatus(e) {
    this.setData({
      status : e.detail.status
    })
    this.getFeedbackData();
  },

  /* 获取反馈列表*/
  async getFeedbackData() {
    call.request(`wx/feedbackList`,{
      userId: user,
      status: this.data.status,
      title: this.data.title
    } , this.onSuccessInit, this.onFailEquipmentAll);
  },

  onSuccessInit(res) {
    console.log(res)
    if(res.status_code == 1){
      this.setData({
        feedbackList: res.data
      })
    }
  },

  /* 跳转申报页 */
  toPublish() {
    wx.navigateTo({
      url: '../publish/publish'
    })
  },
  /* 退出登录 */
  toReset(){
    wx.removeStorageSync('token')
    help.show("退出登录成功");
    setTimeout(function () {
      // 跳转到登录页面
      wx.reLaunch({
        url: '/pages/login/index'
    })
    }, 2000)
   
  },

  /* 宿舍管理员 */
  toAdmin() {
      wx.navigateTo({
        url: '../admin/admin'
      })
  },

  /* 查看申报表详情 */
  navDetail(e) {
    wx.navigateTo({
      url: '../detail/detail?id=' + e.currentTarget.dataset.id
    })

  }

})