<template>
    <div>
      <el-button type="primary" @click="openAddForm">新增发票</el-button>
      <el-button type="success" @click="openUploadDialog">PDF识别上传</el-button>
      
      <!-- 发票列表 -->
      <el-table :data="invoices" style="width: 100%">
        <!-- <el-table-column prop="id" label="ID"></el-table-column> -->
        <el-table-column prop="pdfName" label="文件名"></el-table-column>
        <el-table-column prop="title" label="发票标题"></el-table-column>
        <el-table-column prop="number" label="发票号码"></el-table-column>
        <el-table-column prop="date" label="开票日期"></el-table-column>
        <el-table-column prop="totalAmount" label="总金额"></el-table-column>
        <el-table-column label="项目名称"><InvoiceDetailManage :ref="invoicedetail"></InvoiceDetailManage></el-table-column>
        
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button size="mini" @click="openEditForm(scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" @click="deleteInvoice(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
  
      <!-- 分页 -->
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[10, 20, 50]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
  
      <!-- 新增/编辑发票表单 -->
      <el-dialog :title="dialogTitle" :visible.sync="dialogFormVisible">
        <el-form :model="form" label-width="120px">
          <el-form-item label="发票标题">
            <el-input v-model="form.title"></el-input>
          </el-form-item>
          <el-form-item label="发票号码">
            <el-input v-model="form.number"></el-input>
          </el-form-item>
          <el-form-item label="开票日期">
            <el-date-picker v-model="form.date" type="date" placeholder="选择日期"></el-date-picker>
          </el-form-item>
          <el-form-item label="总金额">
            <el-input v-model.number="form.total_amount"></el-input>
          </el-form-item>
          <el-form-item label="税额">
            <el-input v-model.number="form.tax_amount"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer">
          <el-button @click="dialogFormVisible = false">取消</el-button>
          <el-button type="primary" @click="saveInvoice">保存</el-button>
        </div>
      </el-dialog>
      <el-dialog title="PDF识别上传" :visible.sync="uploadDialogVisible">
        <el-upload
          action="#"
          :show-file-list="false"
          :before-upload="handleFileUpload"
          accept="application/pdf">
          <el-button type="primary" :loading="isProcessing">点击上传</el-button>
          <div slot="tip" class="el-upload__tip">只能上传PDF文件，且不超过10MB</div>
        </el-upload>
      </el-dialog>
    </div>
  </template>
  
  <script>
  import axios from '@/axiosWrapper';
  import InvoiceDetailManage from '../../comm/InvoiceDetailManage.vue';
  
  export default {
    data() {
      return {
        invoices: [], // 发票列表
        currentPage: 1, // 当前页码
        pageSize: 10, // 每页显示条数
        total: 0, // 总记录数
        dialogFormVisible: false, // 控制表单对话框的显示
        uploadDialogVisible: false, // 控制上传对话框的显示
        isProcessing: false, // 控制上传按钮的加载状态
        dialogTitle: '', // 对话框标题
        form: {
            pdfname:'',
          id: null,
          title: '',
          number: '',
          date: '',
          total_amount: 0,
          tax_amount: 0
        }
      };
    },
    components: {
      InvoiceDetailManage
    },
    methods: {
      // 获取发票列表
      async fetchInvoices() {
        try {
          const response = await axios.get('/invoice/page', {
            params: {
              page: this.currentPage,
              size: this.pageSize
            }
          });
          console.log(response.data.data);
          this.invoices = response.data.data;
          this.invoices.forEach(invoice => {
                invoice.date = new Date(invoice.date).toLocaleDateString(); // 格式化日期
          });

          this.total = response.data.total;
        } catch (error) {
          console.error('获取发票列表失败:', error);
          this.$message.error('获取发票列表失败');
        }
      },
      // 新增或更新发票
      async saveInvoice() {
        try {
          if (this.form.id) {
            // 更新发票
            await axios.post('/invoice/update', this.form);
            this.$message.success('发票更新成功');
          } else {
            // 新增发票
            await axios.post('/invoice/save', this.form);
            this.$message.success('发票新增成功');
          }
          this.dialogFormVisible = false;
          this.fetchInvoices(); // 刷新发票列表
        } catch (error) {
          console.error('保存发票失败:', error);
          this.$message.error('保存发票失败');
        }
      },
      // 删除发票
      async deleteInvoice(id) {
        try {
          await axios.get('/invoice/del', { params: { id } });
          this.$refs.invoicedetail.deleteDetailByInvoiceId(id);
          this.$message.success('发票删除成功');
          this.fetchInvoices(); // 刷新发票列表
        } catch (error) {
          console.error('删除发票失败:', error);
          this.$message.error('删除发票失败');
        }
      },
      // 打开新增发票表单
      openAddForm() {
        this.dialogTitle = '新增发票';
        this.form = {
          id: null,
          title: '',
          number: '',
          date: '',
          total_amount: 0,
          tax_amount: 0
        };
        this.dialogFormVisible = true;
      },
      // 打开编辑发票表单
      openEditForm(invoice) {
        this.dialogTitle = '编辑发票';
        this.form = { ...invoice };
        this.dialogFormVisible = true;
      },
      // 分页事件处理
      handleSizeChange(newSize) {
        this.pageSize = newSize;
        this.fetchInvoices();
      },
      handleCurrentChange(newPage) {
        this.currentPage = newPage;
        this.fetchInvoices();
      },
      openUploadDialog() {
      this.uploadDialogVisible = true;
    },
    async handleFileUpload(file) {
    if (!file) return;

    this.isProcessing = true;
    try {
      const formData = new FormData();
      formData.append('file', file);

      const response = await axios.post('http://invoice.heycore.com/invoice/extrat', formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      });
      console.log(response.data);
      const invoiceData = this.parseInvoiceData(response.data);
      
      // 填充表单
      this.form = { ...invoiceData };
      this.dialogTitle = 'PDF识别结果';
      this.dialogFormVisible = true;
      
      this.$message.success('PDF解析成功');
    } catch (error) {
      console.error(error);
      this.$message.error('PDF解析失败');
    } finally {
      this.isProcessing = false;
      this.uploadDialogVisible = false;
    }
  },

  parseInvoiceData(data) {
    // 根据接口返回的JSON结构解析发票信息
    return data;
  }
    },
    created() {
      this.fetchInvoices(); // 页面加载时获取发票列表
    }
  };
  </script>