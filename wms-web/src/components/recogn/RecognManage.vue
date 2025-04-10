<template>
    <div>
      <el-button type="primary" @click="openAddForm">新增发票</el-button>
      <el-button type="success" @click="openUploadDialog">PDF识别上传</el-button>
      <el-table :data="invoices" style="width: 100%">
        <el-table-column prop="invoiceNumber" label="发票编号"></el-table-column>
        <el-table-column prop="issueDate" label="开具日期"></el-table-column>
        <el-table-column prop="dueDate" label="到期日期"></el-table-column>
        <el-table-column prop="totalAmount" label="总金额"></el-table-column>
        <el-table-column prop="taxAmount" label="税额"></el-table-column>
        <el-table-column prop="currency" label="货币类型"></el-table-column>
        <el-table-column prop="status" label="状态"></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button size="mini" @click="openEditForm(scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" @click="deleteInvoice(scope.row.invoiceId)">删除</el-button>
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
          <el-form-item label="发票编号" label-width="120px">
            <el-input v-model="form.invoiceNumber" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="开具日期" label-width="120px">
            <el-date-picker v-model="form.issueDate" type="date" placeholder="选择日期"></el-date-picker>
          </el-form-item>
          <el-form-item label="到期日期" label-width="120px">
            <el-date-picker v-model="form.dueDate" type="date" placeholder="选择日期"></el-date-picker>
          </el-form-item>
          <el-form-item label="总金额" label-width="120px">
            <el-input v-model.number="form.totalAmount" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="税额" label-width="120px">
            <el-input v-model.number="form.taxAmount" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="货币类型" label-width="120px">
            <el-select v-model="form.currency" placeholder="请选择货币类型">
              <el-option label="人民币" value="CNY"></el-option>
              <el-option label="美元" value="USD"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="状态" label-width="120px">
            <el-select v-model="form.status" placeholder="请选择状态">
              <el-option label="已开具" value="issued"></el-option>
              <el-option label="已支付" value="paid"></el-option>
              <el-option label="逾期" value="overdue"></el-option>
              <el-option label="已取消" value="cancelled"></el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
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
 
  
  export default {
    data() {
      return {
        invoices: [],
        currentPage: 1,
        pageSize: 10,
        total: 0,
        dialogFormVisible: false,
        dialogTitle: '',
        form: {
          invoiceId: null,
          invoiceNumber: '',
          issueDate: '',
          dueDate: '',
          totalAmount: 0,
          taxAmount: 0,
          currency: 'CNY',
          status: 'issued'
        },
        uploadDialogVisible: false,
        fileList: [],
        isProcessing: false
      };

    },
    methods: {
      fetchInvoices() {
        axios.get('/invoices/page', {
          params: {
            page: this.currentPage,
            size: this.pageSize
          }
        }).then(response => {
          this.invoices = response.data.data;
          this.total = response.data.total;
        }).catch(error => {
          console.error(error);
        });
      },
      handleSizeChange(newSize) {
        this.pageSize = newSize;
        this.fetchInvoices();
      },
      handleCurrentChange(newPage) {
        this.currentPage = newPage;
        this.fetchInvoices();
      },
      openAddForm() {
        this.dialogTitle = '新增发票';
        this.form = {
          invoiceId: null,
          invoiceNumber: '',
          issueDate: '',
          dueDate: '',
          totalAmount: 0,
          taxAmount: 0,
          currency: 'CNY',
          status: 'issued'
        };
        this.dialogFormVisible = true;
      },
      openEditForm(invoice) {
        this.dialogTitle = '编辑发票';
        this.form = { ...invoice };
        this.dialogFormVisible = true;
      },
      saveInvoice() {
        if (this.form.invoiceId) {
          // 更新发票
          axios.put(`/invoices/${this.form.invoiceId}`, this.form).then(response => {
            this.$message.success('更新成功');
            this.dialogFormVisible = false;
            this.fetchInvoices();
            console.console.log(response.data);
            
          }).catch(error => {
            console.error(error);
            this.$message.error('更新失败');
          });
        } else {
          // 创建发票
          axios.post('/invoices', this.form).then(response => {
            this.$message.success('添加成功');
            this.dialogFormVisible = false;
            this.fetchInvoices();
            console.log(response.data);
          }).catch(error => {
            console.error(error);
            this.$message.error('添加失败');
            console.log(error);
          });
        }
      },
      deleteInvoice(id) {
        this.$confirm('此操作将永久删除该发票, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          axios.delete(`/invoices/${id}`).then(response => {
            this.$message.success('删除成功');
            console.log(response.data);
            this.fetchInvoices();
          }).catch(error => {
            console.error(error);
            this.$message.error('删除失败');
          });
        }).catch(() => {
          this.$message.info('已取消删除');
        });
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
    return {
      invoiceNumber: data.number || '',  // 发票代码
      issueDate: data.date || '',      // 开票日期
      totalAmount: data.totalAmount || 0,     // 总金额
      taxAmount: data.taxAmount || 0,        // 税额
      currency: 'CNY',                       // 默认人民币
      status: 'issued'                       // 默认状态
    };
  }

    },
    mounted() {
      this.fetchInvoices();
    }
  };
  </script>