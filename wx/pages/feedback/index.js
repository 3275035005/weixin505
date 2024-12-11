// pages/tongji/index.js
var call = require("../../utils/request")
var help = require("../../utils/help")
Page({

	/**
	 * 页面的初始数据
	 */
	data: {

		// banner
		current:0, 
		pic:"/images/22.png",
		// message
		title:"问题反馈",


		// 反馈类型
		title_Index:0,
		title_Screen:['请选择反馈类别','男','女'],

		// 姓名
		name:'',
		// 联系方式
		contact:'',
		// 反馈内容
		content:'',
	},
  /**
   * 最先执行
   */
  onLoad: function () {
		this.getTopicList();
  },
		// 获取反馈列表
	getTopicList(){
		  call.getData('wx/getTopicAll' , this.onSuccessTopicAll, this.onFailTopicAll);
  },
  onSuccessTopicAll(res) {
    if(res.status_code == 1){
      this.setData({
        title_Screen :res.data
      })
    }
  },
  onFailTopicAll() {
    help.show("网络请求失败");
  },
		// 提交
		sub(){
			if (this.validate()) {
				wx.showLoading({
					title: '正在提交...',
					mask: true
				})
				let userId = 	wx.getStorageSync("user");
				call.request('wx/sendFeedback' , {
					content: this.data.content,
					name: this.data.name,
					contact: this.data.contact,
					title: this.data.title_Screen[this.data.title_Index],
					userId: userId
				}, this.onSuccessSubmit, this.onFailSubmit);
			}
		},

		onSuccessSubmit(res) {
			if(res.status_code == 1){
				// 关闭加载框
				wx.hideLoading();
				help.okShow("提交成功");
				setTimeout(function () {
					// 跳转到首页
					wx.reLaunch({
						url: '../index/index',
					})
				}, 2000)
			}else{
				help.show("提交失败!");
			}
		},
	
		onFailSubmit() {
			help.show("网络请求失败");
		},
		
		 /* 申报表单验证 */
		 validate() {
			if (this.data.content === '' || !this.data.content) {
				wx.showToast({
					title: '请填写反馈内容',
					icon: 'error',
					duration: 500
				})
				return false;
			}
			if (this.data.name === '' || !this.data.name) {
				wx.showToast({
					title: '请填写姓名',
					icon: 'error',
					duration: 500
				})
				return false;
			}
			
			if (this.data.title_Screen == 0) {
				wx.showToast({
					title: '请填选择专业技能',
					icon: 'error',
					duration: 500
				})
				return false;
			}
			if (this.data.contact === '' || !this.data.contact) {
				wx.showToast({
					title: '请填写联系方式',
					icon: 'error',
					duration: 500
				})
				return false;
			}
			return true;
		},
		 /* 姓名 */
		 setName(e) {
			this.setData({
				name: e.detail.value
			})
		},


		 /* 内容 */
		 setContent(e) {
			this.setData({
				content: e.detail.value
			})
		},

	 /* 联系方式 */
	 setContact(e) {
    this.setData({
      contact: e.detail.value
    })
	},
	

	// 反馈类型
	title_Change(e){
		console.log(e)
		this.setData({
			title_Index : e.detail.value
		})
	},

 
})