<template>
  <div class="app-container">
    <!--查询表单  :inline="true" 表示表单单行展示 -->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="反馈学生">
        <el-select v-model="dataVo.userId" clearable placeholder="请选择反馈学生">
          <el-option v-for="item in userList" :label="item.name" :value="item.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="问题集">
        <el-select v-model="dataVo.topicId" clearable placeholder="请选择问题集">
          <el-option v-for="item in topicList" :label="item.title" :value="item.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="回复状态">
        <el-select v-model="dataVo.status" clearable placeholder="请选择回复状态">
          <el-option label="未回复" value="0"></el-option>
          <el-option label="已回复" value="1"></el-option>
        </el-select>
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
      <el-button type="default" @click="resetData()">清空</el-button>
    </el-form>
    <!-- 表格 -->
    <el-table
      :data="list"
      border
      fit
      empty-text="暂无数据"
      highlight-current-row>

      <el-table-column
        label="序号"
        width="60"
        align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}  <!--序号,算法-->
        </template>
      </el-table-column>
      <el-table-column prop="content" label="反馈内容"/>
      <el-table-column prop="title" label="问题集"/>
      <el-table-column prop="name" label="反馈人姓名"/>
      <el-table-column prop="account" label="反馈账号"/>
      <el-table-column prop="contact" label="联系方式"/>
      <el-table-column prop="created" label="反馈时间"/>
      <el-table-column prop="type" label="回复状态">
        <template slot-scope="scope">
          <el-tag type="warning" v-if="scope.row.status == '0'">未回复</el-tag>
          <el-tag type="success" v-if="scope.row.status == '1'">已回复</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="aname" label="回复人姓名"/>
      <el-table-column prop="acontent" label="回复内容"/>
      <el-table-column prop="acreated" label="回复时间"/>
      <el-table-column label="操作" align="center" width="200">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" icon="el-icon-edit" @click="updateAndSave(scope.row)" v-if="scope.row.status == '0'">回复</el-button>
          <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination
      @current-change="getList"
      :current-page="page"
      style="padding: 30px 0; text-align: center;"
      :page-size="limit"
      layout="total, prev, pager, next, jumper"
      :total="total">
    </el-pagination>

    <!-- 新增修改弹框 -->
    <el-dialog
      title="反馈回复"
      :visible.sync="dialogVisible"
      width="30%">

      <el-form :model="formData" label-position="left" label-width="100px">
        <el-form-item label="反馈学生">
          <el-input v-model="formData.name" disabled="true"/>
        </el-form-item>
        <el-form-item label="回复内容">
          <el-input type="textarea" :rows="2" v-model="formData.acontent" placeholder="请输入回复内容"/>
        </el-form-item>

      </el-form>
      <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="handleBtn">确 定</el-button>
    </span>
    </el-dialog>

  </div>
</template>
<script>
import {pageQuery, deleteById, update} from "@/api/feedback";
import {getUserAll} from "@/api/user";
import {getTopicAll} from "@/api/topic";
import { getToken} from '@/utils/auth'

export default {
  data() { // 定义变量和初始值
    return {
      list: null,// 查询之后接口返回集合
      page: 1,// 当前页
      limit: 9, // 每页记录数
      total: 100, // 总页数
      dataVo: {}, // 条件封装对象
      dialogVisible:false,
      formData:{},
      userList:[],
      topicList:[]
    }
  },
  created() { // 页面渲染之前执行，一般调用method定义方法
    this.getList();
    this.getUserList();
    this.getTopicList();
  },

  methods: {
    getUserList(){
      getUserAll().then(res=>{
        this.userList = res.data
      })
    },
    getTopicList(){
      getTopicAll().then(res=>{
        this.topicList = res.data
      })
    },
    // 查询
    getList(page = 1) {
      this.page = page; // 获取用户点击的页码赋值
      pageQuery(this.page, this.limit, this.dataVo)
        .then(response => {// 请求成功
          this.list = response.data.records;
          this.total = response.data.total;
        })
    },
    // 修改和更新功能
    updateAndSave(row){
      this.formData = row;
      this.dialogVisible = true;
    },
    // 确认按钮
    handleBtn(){
      // 关闭弹窗
      this.dialogVisible = false;
      this.formData.auserId = getToken();
      update(this.formData)
        .then(response => {// 请求成功
          this.$message({
            type: 'success',
            message: '操作成功！'
          });
          this.getList();
        })

    },

    // 删除
    removeDataById(id) {

      this.$confirm('此操作将永久删除该信息, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'

      }).then(() => { // 确定执行的方法
        deleteById(id)
          .then(response => { // 删除成功执行的方法
            this.$message({
              type: 'success',
              message: '删除成功！'
            });
            // 刷新表格
            this.getList()
          })

      })
    },

    // 清空按按钮执行的方法
    resetData() {
      // 第一步清空条件数据
      this.dataVo = {};
      this.getList();
    }
  }
}
</script>

<style>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>
