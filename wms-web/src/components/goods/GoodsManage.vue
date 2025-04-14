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
        <el-form ref="invoiceForm" :model="form" label-width="150px">
      <el-form-item label="发票标题">
        <el-input v-model="form.title" placeholder="请输入发票标题"></el-input>
      </el-form-item>
      <el-form-item label="机器编号">
        <el-input v-model="form.machineNumber" placeholder="请输入机器编号"></el-input>
      </el-form-item>
      <el-form-item label="代码">
        <el-input v-model="form.code" placeholder="请输入代码"></el-input>
      </el-form-item>
      <el-form-item label="发票号码">
        <el-input v-model="form.number" placeholder="请输入发票号码"></el-input>
      </el-form-item>
      <el-form-item label="开票日期">
        <el-date-picker
          v-model="form.date"
          type="date"
          placeholder="选择开票日期"
          format="yyyy-MM-dd"
          value-format="yyyy-MM-dd">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="校验码">
        <el-input v-model="form.checksum" placeholder="请输入校验码"></el-input>
      </el-form-item>
      <el-form-item label="购买方名称">
        <el-input v-model="form.buyerName" placeholder="请输入购买方名称"></el-input>
      </el-form-item>
      <el-form-item label="购买方税号">
        <el-input v-model="form.buyerCode" placeholder="请输入购买方税号"></el-input>
      </el-form-item>
      <el-form-item label="购买方地址">
        <el-input v-model="form.buyerAddress" placeholder="请输入购买方地址"></el-input>
      </el-form-item>
      <el-form-item label="购买方账号">
        <el-input v-model="form.buyerAccount" placeholder="请输入购买方账号"></el-input>
      </el-form-item>
      <el-form-item label="密码">
        <el-input v-model="form.password" placeholder="请输入密码"></el-input>
      </el-form-item>
      <el-form-item label="金额">
        <el-input-number v-model="form.amount" placeholder="请输入金额" :min="0" :step="0.01"></el-input-number>
      </el-form-item>
      <el-form-item label="税额">
        <el-input-number v-model="form.taxAmount" placeholder="请输入税额" :min="0" :step="0.01"></el-input-number>
      </el-form-item>
      <el-form-item label="总金额（大写）">
        <el-input v-model="form.totalAmountString" placeholder="请输入总金额（大写）"></el-input>
      </el-form-item>
      <el-form-item label="总金额">
        <el-input-number v-model="form.totalAmount" placeholder="请输入总金额" :min="0" :step="0.01"></el-input-number>
      </el-form-item>
      <el-form-item label="销售方名称">
        <el-input v-model="form.sellerName" placeholder="请输入销售方名称"></el-input>
      </el-form-item>
      <el-form-item label="销售方税号">
        <el-input v-model="form.sellerCode" placeholder="请输入销售方税号"></el-input>
      </el-form-item>
      <el-form-item label="销售方地址">
        <el-input v-model="form.sellerAddress" placeholder="请输入销售方地址"></el-input>
      </el-form-item>
      <el-form-item label="销售方账号">
        <el-input v-model="form.sellerAccount" placeholder="请输入销售方账号"></el-input>
      </el-form-item>
      <el-form-item label="收款人">
        <el-input v-model="form.payee" placeholder="请输入收款人"></el-input>
      </el-form-item>
      <el-form-item label="复核人">
        <el-input v-model="form.reviewer" placeholder="请输入复核人"></el-input>
      </el-form-item>
      <el-form-item label="开票人">
        <el-input v-model="form.drawer" placeholder="请输入开票人"></el-input>
      </el-form-item>
      <el-form-item label="发票类型">
        <el-input v-model="form.type" placeholder="请输入发票类型"></el-input>
      </el-form-item>
      <el-form-item label="PDF 文件名">
        <el-input v-model="form.pdfName" placeholder="请输入 PDF 文件名"></el-input>
      </el-form-item>
      <el-form :model="form.detailList">
        <div v-for="(item,index) in form.detailList" :key="index" class="invoice-detail">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="项目名称">
              <el-input v-model="item.name" placeholder="请输入项目名称"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="规格型号">
              <el-input v-model="item.model" placeholder="请输入规格型号"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="单位">
              <el-input v-model="item.unit" placeholder="请输入单位"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="数量">
              <el-input-number v-model="item.count" placeholder="请输入数量" :min="1"></el-input-number>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="单价">
              <el-input-number v-model="item.price" placeholder="请输入单价" :min="0" :step="0.01"></el-input-number>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="金额">
              <el-input-number v-model="item.amount" placeholder="请输入金额" :min="0" :step="0.01"></el-input-number>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="税率">
              <el-input-number v-model="item.taxRate" placeholder="请输入税率" :min="0" :step="0.01"></el-input-number>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="税额">
              <el-input-number v-model="item.taxAmount" placeholder="请输入税额" :min="0" :step="0.01"></el-input-number>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="PDF 文件名">
              <el-input v-model="item.pdfName" placeholder="请输入 PDF 文件名"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-button type="danger" @click="removeItem(index)">删除</el-button>
          </el-col>
        </el-row>
      </div>
      </el-form>
     
      <el-form-item>
        <el-button type="primary" @click="addItem">新增明细</el-button>
      </el-form-item>
    </el-form>
        <div slot="footer">
          <el-button @click="dialogFormVisible = false">取消</el-button>
          <el-button @click="resetForm">重置</el-button>
          <el-button type="primary" @click="saveInvoice">保存</el-button>
        </div>
      </el-dialog>
      <<el-dialog title="PDF识别上传" :visible.sync="uploadDialogVisible">
  <el-upload
    action="#"
    :show-file-list="false"
    :before-upload="handleFileUpload"
    accept="application/pdf"
    drag
    class="upload-dragger"
  >
    <i class="el-icon-upload"></i>
    <div class="el-upload__text">
      将文件拖到此处，或
      <em>点击上传</em>
    </div>
    <el-button type="primary" :loading="isProcessing" slot="trigger">点击上传</el-button>
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
        title: '',
        machineNumber: '',
        code: '',
        number: '',
        date: '',
        checksum: '',
        buyerName: '',
        buyerCode: '',
        buyerAddress: '',
        buyerAccount: '',
        password: '',
        amount: 0,
        taxAmount: 0,
        totalAmountString: '',
        totalAmount: 0,
        sellerName: '',
        sellerCode: '',
        sellerAddress: '',
        sellerAccount: '',
        payee: '',
        reviewer: '',
        drawer: '',
        type: '',
        pdfName: '',
        detailList: []
        }
      };
    },
    components: {
      InvoiceDetailManage
    },
    methods: {
        addItem() {
            if(this.form.detailList ==null){
                this.form.detailList = [];
            }
          this.form.detailList.push({
            id: null,
            name: '',
            model: '',
            unit: '',
            count: 0,
            price: 0,
            amount: 0,
            taxRate: 0,
            taxAmount: 0,
            pdfName: ''
          });
          this.$forceUpdate();
        },
        removeItem(index) {
          this.form.detailList.splice(index, 1);
        },
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
        let invoiceId = await axios.get('/invoice/lastInsertId');
        if(invoiceId.data.data == null) {
          invoiceId.data.data = 0;
        }
        try {
          if (this.form.id) {
            this.form.date = this.form.date.replace('年', '-').replace('月', '-').replace('日', '');
            this.form.date = new Date(this.form.date).toISOString(); // 格式化日期
            // 更新发票
            await axios.post('/invoice/update', this.form);
            for(let i = 0; i < this.form.detailList.length; i++) {
              this.form.detailList[i].invoiceId = invoiceId.data.data;
                await axios.post('/invoice-details/save', this.form.detailList[i]);
            }
            
            this.$message.success('发票更新成功');
          } else {
            // 新增发票
            this.form.date = this.form.date.replace('年', '-').replace('月', '-').replace('日', '');
            this.form.date = new Date(this.form.date).toISOString(); // 格式化日期
            await axios.post('/invoice/save', this.form);
            for(let i = 0; i < this.form.detailList.length; i++) {
              this.form.detailList[i].invoiceId = invoiceId.data.data;
                await axios.post('/invoice-details/save', this.form.detailList[i]);
            }
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
        //    await this.$refs.invoicedetail.deleteDetailByInvoiceId(id);
          await axios.get(`/invoice-details/invoice/del/${id}`);
          await axios.get('/invoice/del', { params: { id } });
          
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
        this.form.detailList  = axios.get(`/invoice-details/${invoice.id}`);
        this.dialogFormVisible = true;
      },
      resetForm() {
      this.$refs.invoiceForm.resetFields();
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
      this.form.pdfName = file.name;
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
    return data;
  }
    },
    created() {
      this.fetchInvoices(); // 页面加载时获取发票列表
    }
  };
  </script>