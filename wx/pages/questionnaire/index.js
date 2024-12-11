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
		pic:"/images/11.png",
		// message
		title:"问卷调查",


		// 性别
		sex_Index:0,
		sex_Screen:['请选择性别','男','女'],

		// 专业技能
		skill_Index: 0,
		skill_Screen:['请选择专业技能','前端','Java','Python','C++'],
		
		// 姓名
		name:'',
		// 联系电话
		phone:'',
		// 意见
		opinion:''
	

	},

		// 提交
		sub(){
			if (this.validate()) {
				wx.showLoading({
					title: '正在提交...',
					mask: true
				})
				let userId = 	wx.getStorageSync("user");
				call.request('wx/sendQuestionnaire' , {
						name: this.data.name,
						phone: this.data.phone.trim(),
						skill: this.data.skill_Screen[this.data.skill_Index],
						sex: this.data.sex_Screen[this.data.sex_Index],
						opinion: this.data.opinion,
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
				help.show(res.msg);
			}
		},
	
		onFailSubmit() {
			help.show("网络请求失败");
		},
		
		 /* 申报表单验证 */
		 validate() {
			let cp = /[1][3,4,5,7,8][0-9]{9}$/;
			if (this.data.name === '' || !this.data.name) {
				wx.showToast({
					title: '请填写姓名',
					icon: 'error',
					duration: 500
				})
				return false;
			}
			if (this.data.sex_Index == 0) {
				wx.showToast({
					title: '请填选择性别',
					icon: 'error',
					duration: 500
				})
				return false;
			}
			if (this.data.phone === '' || !this.data.phone) {
				wx.showToast({
					title: '请填写联系电话',
					icon: 'error',
					duration: 500
				})
				return false;
			}
			if (!cp.test(this.data.phone)) {
				wx.showToast({
					title: '请填写正确电话',
					icon: 'error',
					duration: 500
				})
				return false;
			}
			if (this.data.skill_Index == 0) {
				wx.showToast({
					title: '请选择专业技能',
					icon: 'error',
					duration: 500
				})
				return false;
			}
		
			if (this.data.opinion === '' || !this.data.opinion) {
				wx.showToast({
					title: '请填写您的意见',
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


		 /* 意见 */
		 setOpinion(e) {
			this.setData({
				opinion: e.detail.value
			})
		},

	 /* 联系电话 */
	 setPhone(e) {
    this.setData({
      phone: e.detail.value
    })
	},
	

	// 性别
	sex_Change(e){
		this.setData({
			sex_Index : e.detail.value
		})
	},

		// 专业技能
		skill_Change(e){
			console.log(e)
			this.setData({
				skill_Index : e.detail.value
			})
		},
 
})