<template>
  <div style="margin-top: 20px">
    <el-form ref="form" :model="form" label-width="80px">
      <el-form-item label="账号">
        <el-input v-model="form.account" placeholder="请输入账号" disabled="true"/>
      </el-form-item>
      <el-form-item label="密码">
        <el-input v-model="form.password" type="password" placeholder="请输入密码"/>
      </el-form-item>
      <el-form-item label="姓名">
        <el-input v-model="form.name" placeholder="请输入姓名"/>
      </el-form-item>
      <el-form-item label="电话">
        <el-input v-model="form.phone" placeholder="请输入电话"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">修改</el-button>
      </el-form-item>
    </el-form>
  </div>


</template>

<script>
import { mapGetters } from 'vuex'
import { getToken} from '@/utils/auth'
import {getUserInfo, update} from "@/api/user";

export default {
  name: 'Dashboard',
  computed: {
    ...mapGetters([
      'name'
    ])
  },
  data(){
   return{
     form:{}
   }
  },
  created() {
    this.getInfo();
  },
  methods:{
    getInfo(){
      getUserInfo(getToken()).then(res=>{
        this.form = res.data;
      })
    },

    onSubmit(){
      update(this.form)
        .then(response => {// 请求成功
          this.$message({
            type: 'success',
            message: '修改成功！'
          });
          this.getInfo();
        })
    }
  }
}
</script>

