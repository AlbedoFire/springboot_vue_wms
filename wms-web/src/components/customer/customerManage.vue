<template>
    <div>
      <el-button type="primary" @click="openAddForm">新增客户</el-button>
      <el-table :data="customers" style="width: 100%">
        <el-table-column prop="customerId" label="客户ID"></el-table-column>
        <el-table-column prop="customerName" label="客户名称"></el-table-column>
        <el-table-column prop="contactPerson" label="联系人"></el-table-column>
        <el-table-column prop="contactEmail" label="联系邮箱"></el-table-column>
        <el-table-column prop="contactPhone" label="联系电话"></el-table-column>
        <el-table-column prop="address" label="地址"></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button size="mini" @click="openEditForm(scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" @click="deleteCustomer(scope.row.customerId)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
  
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[10, 20, 50]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
  
      <el-dialog :title="dialogTitle" :visible.sync="dialogFormVisible">
        <el-form :model="form">
          <el-form-item label="客户名称" label-width="120px">
            <el-input v-model="form.customerName" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="联系人" label-width="120px">
            <el-input v-model="form.contactPerson" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="联系邮箱" label-width="120px">
            <el-input v-model="form.contactEmail" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="联系电话" label-width="120px">
            <el-input v-model="form.contactPhone" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="地址" label-width="120px">
            <el-input type="textarea" v-model="form.address"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取消</el-button>
          <el-button type="primary" @click="saveCustomer">保存</el-button>
        </div>
      </el-dialog>
    </div>
  </template>
  
  <script>
  import axios from '@/axiosWrapper';
  
  export default {
    data() {
      return {
        customers: [],
        currentPage: 1,
        pageSize: 10,
        total: 0,
        dialogFormVisible: false,
        dialogTitle: '',
        form: {
          customerId: null,
          customerName: '',
          contactPerson: '',
          contactEmail: '',
          contactPhone: '',
          address: ''
        }
      };
    },
    methods: {
      fetchCustomers() {
        axios.get('/customers/page', {
          params: {
            page: this.currentPage,
            size: this.pageSize
          }
        }).then(response => {
          this.customers = response.data.data;
          this.total = response.data.total;
        }).catch(error => {
          console.error(error);
        });
      },
      handleSizeChange(newSize) {
        this.pageSize = newSize;
        this.fetchCustomers();
      },
      handleCurrentChange(newPage) {
        this.currentPage = newPage;
        this.fetchCustomers();
      },
      openAddForm() {
        this.dialogTitle = '新增客户';
        this.form = {
          customerId: null,
          customerName: '',
          contactPerson: '',
          contactEmail: '',
          contactPhone: '',
          address: ''
        };
        this.dialogFormVisible = true;
      },
      openEditForm(customer) {
        this.dialogTitle = '编辑客户';
        this.form = { ...customer };
        this.dialogFormVisible = true;
      },
      saveCustomer() {
        if (this.form.customerId) {
          // 更新客户
          axios.put(`/customers/${this.form.customerId}`, this.form).then(response => {
            this.$message.success('更新成功');
            this.dialogFormVisible = false;
            this.fetchCustomers();
            console.log(response.data);
          }).catch(error => {
            console.error(error);
            this.$message.error('更新失败');
          });
        } else {
          // 创建客户
          axios.post('/customers', this.form).then(response => {
            this.$message.success('添加成功');
            this.dialogFormVisible = false;
            this.fetchCustomers();
            console.log(response.data);
          }).catch(error => {
            console.error(error);
            this.$message.error('添加失败');
            console.log(error.response.data);
          });
        }
      },
      deleteCustomer(id) {
        this.$confirm('此操作将永久删除该客户, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          axios.delete(`/customers/${id}`).then(response => {
            this.$message.success('删除成功');
            this.fetchCustomers();
            console.log(response.data);
          }).catch(error => {
            console.error(error);
            this.$message.error('删除失败');
          });
        }).catch(() => {
          this.$message.info('已取消删除');
        });
      }
    },
    mounted() {
      this.fetchCustomers();
    }
  };
  </script>