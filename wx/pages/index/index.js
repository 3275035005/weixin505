//pages/index/index.js
Page({
  data: {
  },

  
  /**
   * 最先执行
   */
  onLoad: function () {
    this.getInit();
  },

    /**
   * 获取登录用户信息
   */
  getInit(){
    // let user = wx.getStorageSync("user")
    //  // 用户信息不存在跳转登录页面
    //  if(user == null || user == undefined || user == ''){
    //   // 跳转到登录页面
    //   wx.reLaunch({
    //     url: '/pages/login/index'
    //  })
  // }
  },

  //提交新反馈
  home_bingtap_image1:function() {
    wx.switchTab({
      url: '/pages/feedback/index',
    })
  },
  //反馈列表
  home_bingtap_image2:function() {
    wx.navigateTo({
      url: '/pages/list/index',
    })
  },
  //个人中心
  home_bingtap_image3:function() {
    wx.navigateTo({
      url: '/pages/personage/index',
    })
  },
  home_bingtap_image4:function() {
    wx.navigateTo({
      url: '/pages/questionnaire/index',
    })
  },
  home_bingtap_image5:function() {
    wx.navigateTo({
      url: '/pages/suggestion/suggestion',
    })
  }
})
