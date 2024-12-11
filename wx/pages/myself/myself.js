
// pages/tongji/index.js
var call = require("../../utils/request")
var help = require("../../utils/help")
Page({
  data: {
    list: [{
      icon: "images/aboutUs.png",
      title: "关于我们",
      click: "about"
    },{
      icon: "images/login.png",
      title: "退出登录",
      click: "login"
    }],
    info:{}
  },
  onLoad() {
    this.getInit();
  },
    /**
   * 获取登录用户信息
   */
  getInit(){
    let user = wx.getStorageSync("user")
     // 用户信息不存在跳转登录页面
     if(user == null || user == undefined || user == ''){
      // 跳转到登录页面
      wx.reLaunch({
        url: '/pages/login/index'
     })
  }
  this.getUserInfo(user);
},

  getUserInfo(userId){
    call.getData('wx/getUserInfo/'+userId , this.onSuccessTopicAll, this.onFailTopicAll);
  },
  onSuccessTopicAll(res) {
    if(res.status_code == 1){
      this.setData({
        info :res.data
      })
    }
  },
  onFailTopicAll() {
    help.show("网络请求失败");
  },

  class(e) {
    console.log(e, "班级");
  },
  academy(e) {
    console.log(e, "专业");
  },
  about(e) {
    wx.navigateTo({
      url: '/pages/about/index',
    })
  },
  journal(e) {
    console.log(e)
    console.log("更新日志")
  },
  login(e) {
    wx.removeStorageSync('user')
    help.okShow("退出成功");
    setTimeout(function () {
      wx.navigateTo({
        url: '/pages/login/index',
      })
    }, 1000)
   
  }
})