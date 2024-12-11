var call = require("../../utils/request.js")
var help = require("../../utils/help.js")
var user= ''
Page({
    data: {
      name:'',// 姓名
      phone:'', // 手机号
      account:'', // 账号
      password:'', // 密码
      baiji:'', // 班级
      zhuanye:'' // 专业
    },
    onLoad() {
      this.getInit();
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
  this.getUserInfo(user);
},

  getUserInfo(userId){
    call.getData('wx/getUserInfo/'+userId , this.onSuccessTopicAll, this.onFailTopicAll);
  },
  onSuccessTopicAll(res) {
    console.log(res);
    if(res.status_code == 1){
      this.setData({
        name:res.data.name,
        phone:res.data.phone,
        account:res.data.account,
        password:res.data.password,
        banji:res.data.banji,
        zhuanye:res.data.zhuanye,
      })
    }
  },
  onFailTopicAll() {
    help.show("网络请求失败");
  },

    // 修改
    sub(){
      wx.showLoading({
        title: '修改中...'
      });
      call.request('wx/updateInfo',{
        name: this.data.name,
        phone: this.data.phone,
        account:this.data.account,
        password:this.data.password,
        banji: this.data.banji,
        zhuanye: this.data.zhuanye,
        id:user
      }, this.onSuccess, this.onFail);
    },

    onSuccess(res) {
    
      wx.hideLoading();
      if(res.status_code == 1){
      help.show("修改成功")
      setTimeout(function(){ // 注册成功跳转页面
        wx.reLaunch({
          url: '/pages/index/index'
      })
      },2000)
         
      }else{
          help.show(res.msg)
      }
    },

    onFail() {
      wx.hideLoading();
      help.show("网络请求超时,请稍后再试")
    },
    


    //获取input输入框的值
    getPasswordValue:function(e){
      this.setData({
        password:e.detail.value
      })
    },
    getNameValue:function(e){
      this.setData({
        name:e.detail.value
      })
    },
    getPhoneValue:function(e){
        this.setData({
            phone:e.detail.value 
        })
    },
    getUsernameValue:function(e){
      this.setData({
        account:e.detail.value 
      })
  },
  getZhuanyeValue:function(e){
    this.setData({
        zhuanye:e.detail.value 
    })
},
getBanjiValue:function(e){
  this.setData({
      banji:e.detail.value 
  })
},

    
})