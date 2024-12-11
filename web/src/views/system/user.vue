<template>
  <div class="app-container">
    <!--查询表单  :inline="true" 表示表单单行展示 -->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="学生账号">
        <el-input v-model="dataVo.account" placeholder="请输入学生账号"/>
      </el-form-item>
      <el-form-item label="学生姓名">
        <el-input v-model="dataVo.name" placeholder="请输入学生姓名"/>
      </el-form-item>
      <el-form-item label="用户状态">
        <el-select v-model="dataVo.status" clearable placeholder="请输入用户状态">
          <el-option value="0" label="正常"/>
          <el-option value="1" label="禁用"/>
        </el-select>
      </el-form-item>

      <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
      <el-button type="default" @click="resetData()">清空</el-button>
      <el-button type="primary" @click="updateAndSave(null)">新增</el-button>
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
      <el-table-column prop="account" label="账号"/>
      <el-table-column prop="name" label="姓名"/>
      <el-table-column prop="phone" label="手机号"/>
      <el-table-column prop="banji" label="班级"/>
      <el-table-column prop="zhuanye" label="专业"/>
      <el-table-column prop="avatar" label="头像">
        <template slot-scope="scope">
          <el-image
            style="width: 50px; height: 50px"
            :src="scope.row.avatar"></el-image>
        </template>

      </el-table-column>
      <el-table-column prop="userStatus" label="用户状态">
        <template slot-scope="scope">
          <el-tag type="success" v-if="scope.row.status == '0'">正常</el-tag>
          <el-tag type="warning" v-if="scope.row.status == '1'">禁用</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="created" label="注册时间"/>
      <el-table-column label="操作" align="center" width="200">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" icon="el-icon-edit" @click="updateAndSave(scope.row)">修改</el-button>
          <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>


    <!-- 新增修改弹框 -->
    <el-dialog
      :title="title"
      :visible.sync="dialogVisible"
      width="30%">

      <el-form :model="formData" label-position="left" label-width="100px">
        <el-form-item label="账号">
          <el-input v-model="formData.account" placeholder="请输入账号"/>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="formData.password" type="password" placeholder="请输入密码"/>
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="formData.name" placeholder="请输入姓名"/>
        </el-form-item>

        <el-form-item label="电话">
          <el-input v-model="formData.phone" placeholder="请输入电话"/>
        </el-form-item>
        <el-form-item label="班级">
          <el-input v-model="formData.banji" placeholder="请输入班级"/>
        </el-form-item>
        <el-form-item label="专业">
          <el-input v-model="formData.zhuanye" placeholder="请输入专业"/>
        </el-form-item>
        <el-form-item label="头像">
          <el-upload
            ref="upload"
            :file-list="fileList"
            action="http://localhost:9001/file/"
            :on-success="fileHandleSuccess"
            accept="image/*"
            class="avatar-uploader"
          >
            <img  v-if="formData.avatar" :src="formData.avatar" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="账号状态">
          <el-select v-model="formData.status" placeholder="请选择账号状态" @change="refreshData">
            <el-option label="正常" value="0"/>
            <el-option label="禁用" value="1"/>
          </el-select>
        </el-form-item>


      </el-form>
      <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="handleBtn">确 定</el-button>
    </span>
    </el-dialog>

    <!-- 分页 -->
    <el-pagination
      @current-change="getList"
      :current-page="page"
      style="padding: 30px 0; text-align: center;"
      :page-size="limit"
      layout="total, prev, pager, next, jumper"
      :total="total">
    </el-pagination>
  </div>
</template>
<script>
  import {pageQuery, deleteById, update, insert} from "@/api/user";

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
        title:'',
        fileList:[]

      }
    },
    created() { // 页面渲染之前执行，一般调用method定义方法
      this.getList()
    },
    methods: { // 创建具体的方法
      refreshData(){
       this.$forceUpdate()
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
        if(row == null){
          this.title = '新增';
          this.formData = {
            avatar:''
          };
        }else{
          this.title = '修改'
          this.formData = row;
        }
        this.dialogVisible = true;
      },

      // 确认按钮
      handleBtn(){
        console.log(this.formData)
        // 关闭弹窗
        this.dialogVisible = false;
        if(this.formData.id){
          update(this.formData)
            .then(response => {// 请求成功
              this.$message({
                type: 'success',
                message: '操作成功！'
              });
              this.getList();
            })
        }else{
          insert(this.formData)
            .then(response => {// 请求成功
              this.$message({
                type: 'success',
                message: '操作成功！'
              });
              this.getList();
            })
        }
        this.$refs.upload.clearFiles()

      },

      // 删除
      removeDataById(id) {

        this.$confirm('此操作将永久删除该用户信息, 是否继续?', '提示', {
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

      // 上传头像成功
      fileHandleSuccess(response) {
        console.log(response.data)
        this.formData.avatar = response.data;


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
