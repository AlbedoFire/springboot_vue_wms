<template>
    <div>
        <el-button type="primary" @click="openAddForm">新增发票</el-button>
      <el-button type="success" @click="openUploadDialog">PDF识别上传</el-button>
      
      <!-- 发票明细列表 -->
      <el-table :data="invoiceDetails" style="width: 100%">
        <el-table-column prop="id" label="明细ID"></el-table-column>
        <el-table-column prop="name" label="项目名称"></el-table-column>
        <el-table-column prop="model" label="规格型号"></el-table-column>
        <el-table-column prop="unit" label="单位"></el-table-column>
        <el-table-column prop="count" label="数量"></el-table-column>
        <el-table-column prop="price" label="单价"></el-table-column>
        <el-table-column prop="amount" label="金额"></el-table-column>
        <el-table-column prop="tax_rate" label="税率"></el-table-column>
        <el-table-column prop="tax_amount" label="税额"></el-table-column>
        <el-table-column prop="pdf_name" label="pdf名字"></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button size="mini" @click="openEditForm(scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" @click="deleteDetail(scope.row.id)">删除</el-button>
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
  
      <!-- 新增/编辑发票明细表单 -->
      <el-dialog :title="dialogTitle" :visible.sync="dialogFormVisible">
        <el-form :model="form" label-width="120px">
          <el-form-item label="项目名称">
            <el-input v-model="form.name"></el-input>
          </el-form-item>
          <el-form-item label="规格型号">
            <el-input v-model="form.model"></el-input>
          </el-form-item>
          <el-form-item label="单位">
            <el-input v-model="form.unit"></el-input>
          </el-form-item>
          <el-form-item label="数量">
            <el-input v-model.number="form.count"></el-input>
          </el-form-item>
          <el-form-item label="单价">
            <el-input v-model.number="form.price"></el-input>
          </el-form-item>
          <el-form-item label="金额">
            <el-input v-model.number="form.amount"></el-input>
          </el-form-item>
          <el-form-item label="税率">
            <el-input v-model.number="form.tax_rate"></el-input>
          </el-form-item>
          <el-form-item label="税额">
            <el-input v-model.number="form.tax_amount"></el-input>
          </el-form-item>
          <el-form-item label="pdf名字">
            <el-input v-model="form.pdf_name"></el-input>
          </el-form-item>
            
        </el-form>
        <div slot="footer">
          <el-button @click="dialogFormVisible = false">取消</el-button>
          <el-button type="primary" @click="saveDetail">保存</el-button>
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
    props: {
      invoiceId: {
        type: Number,
        required: true
      }
    },
    data() {
      return {
        invoiceDetails: [], // 发票明细列表
        currentPage: 1, // 当前页码
        pageSize: 10, // 每页显示条数
        total: 0, // 总记录数
        dialogFormVisible: false, // 控制表单对话框的显示
        uploadDialogVisible: false, // 控制上传对话框的显示
        isProcessing: false, // 上传中状态
        dialogTitle: '', // 对话框标题
        form: {
          id: null,
          name: '',
          model: '',
          unit: '',
          count: 0,
          price: 0,
          amount: 0,
          tax_rate: 0,
          tax_amount: 0,
          pdf_name: ''
        
        }
      };
    },
    methods: {
      // 获取发票明细列表
      async fetchInvoiceDetails() {
        try {
          const response = await axios.get(`/invoice-details/page`, {
            params: {
              page: this.currentPage,
              size: this.pageSize
            }
          });
          this.invoiceDetails = response.data.data;
          this.total = response.data.total;
        } catch (error) {
          console.error('获取发票明细列表失败:', error);
          this.$message.error('获取发票明细列表失败');
        }
      },
      // 新增或更新发票明细
      async saveDetail() {
        try {
          if (this.form.id) {
            // 更新明细
            await axios.put(`/invoice-details/${this.form.id}`, this.form);
            this.$message.success('明细更新成功');
          } else {
            // 新增明细
            await axios.post(`/invoice-details`, this.form);
            this.$message.success('明细新增成功');
          }
          this.dialogFormVisible = false;
          this.fetchInvoiceDetails(); // 刷新明细列表
        } catch (error) {
          console.error('保存发票明细失败:', error);
          this.$message.error('保存发票明细失败');
        }
      },
      // 删除发票明细
      async deleteDetail(id) {
        try {
          await axios.delete(`/invoice-details/${id}`);
          this.$message.success('明细删除成功');
          this.fetchInvoiceDetails(); // 刷新明细列表
        } catch (error) {
          console.error('删除发票明细失败:', error);
          this.$message.error('删除发票明细失败');
        }
      },
      // 打开新增明细表单
      openAddForm() {
        this.dialogTitle = '新增明细';
        this.form = {
          id: null,
          name: '',
          model: '',
          unit: '',
          count: 0,
          price: 0,
          amount: 0,
          tax_rate: 0,
          tax_amount: 0
        };
        this.dialogFormVisible = true;
      },
      // 打开编辑明细表单
      openEditForm(detail) {
        this.dialogTitle = '编辑明细';
        this.form = { ...detail };
        this.dialogFormVisible = true;
      },
      // 分页事件处理
      handleSizeChange(newSize) {
        this.pageSize = newSize;
        this.fetchInvoiceDetails();
      },
      handleCurrentChange(newPage) {
        this.currentPage = newPage;
        this.fetchInvoiceDetails();
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
      issueDate: data.date,      // 开票日期
      totalAmount: data.totalAmount || 0,     // 总金额
      taxAmount: data.taxAmount || 0,        // 税额
      currency: 'CNY',                       // 默认人民币
      status: 'issued'                       // 默认状态
    };
  }
    },
    watch: {
      invoiceId() {
        this.fetchInvoiceDetails();
      }
    },
    created() {
      this.fetchInvoiceDetails(); // 页面加载时获取发票明细列表
    }
  };
  </script>