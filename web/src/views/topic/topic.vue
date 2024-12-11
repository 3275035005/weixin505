<template>
  <div class="app-container">
    <!--查询表单  :inline="true" 表示表单单行展示 -->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="问题集名称">
        <el-input v-model="dataVo.title" placeholder="请输入问题集名称"/>
      </el-form-item>

      <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
      <el-button type="default" @click="resetData()">清空</el-button>
      <el-button type="primary" @click="updateAndSave(null)">新增</el-button>
    </el-form>
    <el-row :gutter="24">
      <el-col :span="6" v-for="item in list" style="margin: 50px">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>{{item.title}}</span>

            <el-button style="float: right; color: red;" type="text"  @click="removeDataById(item.id)">删除</el-button>
            <el-button style="float: right;margin-left: 10px" type="text" @click="updateAndSave(item)">修改</el-button>
          </div>
          <div style="height: 100px; font-size: 15px; text-align: center;line-height: 100px">
            {{item.sum}}<i class="el-icon-star-on" style="color:red"></i>
          </div>
          <div  style="margin-left: 280px">{{item.created}}</div>
        </el-card>

      </el-col>
    </el-row>



    <!-- 新增修改弹框 -->
    <el-dialog
      :title="title"
      :visible.sync="dialogVisible"
      width="30%">

      <el-form :model="formData" label-position="left" label-width="100px">
        <el-form-item label="问题集名称">
          <el-input v-model="formData.title" placeholder="请输入问题集名称"/>
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
  import {pageQuery, deleteById, update, insert} from "@/api/topic";

  export default {
    data() { // 定义变量和初始值
      return {
        list: null,// 查询之后接口返回集合
        page: 1,// 当前页
        limit: 6, // 每页记录数
        total: 100, // 总页数
        dataVo: {}, // 条件封装对象
        dialogVisible:false,
        formData:{},
        title:'',

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
          this.title = '新增问题集';
          this.formData = {
            avatar:''
          };
        }else{
          this.title = '修改问题集'
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
  .text {
    font-size: 14px;
  }

  .item {
    margin-bottom: 18px;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }
  .clearfix:after {
    clear: both
  }

  .box-card {
    width: 480px;
  }
</style>
