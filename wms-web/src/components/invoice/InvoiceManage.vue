<template>
    <div>
      <el-button type="primary" @click="openAddForm">新增发票</el-button>
      <el-button type="success" @click="openUploadDialog">PDF识别上传</el-button>
      <el-button type="primary" @click="exportTable">导出表格</el-button>
      <el-button type="primary" @click="batchReimburse">一起报销</el-button>

      <!-- 查询表单 -->
    <el-form :inline="true" :model="searchForm" class="demo-form-inline">
      <el-form-item label="发票标题">
        <el-input v-model="searchForm.title" placeholder="请输入发票标题"></el-input>
      </el-form-item>
      <el-form-item label="发票号码">
        <el-input v-model="searchForm.number" placeholder="请输入发票号码"></el-input>
      </el-form-item>
      <!-- <el-form-item label="开票日期">
        <el-date-picker
          v-model="searchForm.date"
          type="date"
          placeholder="选择开票日期"

      
        ></el-date-picker>
      </el-form-item> -->
      <el-form-item label="发票类型">
        <el-select v-model="searchForm.type" placeholder="请选择发票类型">
          <el-option label="材料" value="材料"></el-option>
          <el-option label="加工" value="加工"></el-option>
          <el-option label="车票" value="车票"></el-option>
          <el-option label="住宿" value="住宿"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="search">查询</el-button>
        <el-button @click="resetSearch">重置</el-button>
      </el-form-item>
    </el-form>
      
      <!-- 发票列表 -->
      <el-table :data="filteredInvoices" style="width: 100%" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55"></el-table-column>
        <!-- <el-table-column prop="id" label="ID"></el-table-column> -->
        <el-table-column prop="title" label="发票标题"></el-table-column>
        <el-table-column prop="number" label="发票号码"></el-table-column>
        <el-table-column prop="date" label="开票日期"></el-table-column>
        <el-table-column prop="totalAmount" label="总金额"></el-table-column>
        <el-table-column prop="type" label="类型"></el-table-column>
        <el-table-column prop="status" label="状态"></el-table-column>
        <el-table-column prop="pdfName" label="PDF文件名"></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button size="mini" @click="openEditForm(scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" @click="deleteInvoice(scope.row.id)">删除</el-button>
            <div v-if="scope.row.status !='已报销' ">
            <el-button size="mini" type="success" @click="checkInvoice(scope.row.id)">报销</el-button>
            </div>
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
      <el-form-item label="状态">
        <el-input v-model="form.status" placeholder="请输入状态"></el-input>
      </el-form-item>
    </el-form>
        <div slot="footer">
          <el-button @click="dialogFormVisible = false">取消</el-button>
          <el-button @click="resetForm">重置</el-button>
          <el-button type="primary" @click="saveInvoice">保存</el-button>
        </div>
      </el-dialog>
      <el-dialog :title="报销发票检查" :visible.sync="dialogCheckVisible">
        <InvoiceDetailManageall :invoiceId="openid"/>
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
  import InvoiceDetailManageall from '../../comm/InvoiceDetailManageall.vue';

  import * as XLSX from 'xlsx';

  
  export default {
    name: 'InvoiceManage',
    components: {
      InvoiceDetailManageall
    },
    data() {
      return {
        openid:0,
        dialogCheckVisible: false,
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
        status: 0,
      },
        searchForm: {
          title: '',
          number: '',
          date: ''
        },
        filteredInvoices: [], // 过滤后的发票列表
      };
    },
    methods: {
      handleSelectionChange(val) {
      this.selectedRows = val // 更新选中的行
    },
    // 批量报销操作
    batchReimburse() {
      if (this.selectedRows.length > 0) {
        // 示例：弹出选中的发票编号
        const invoiceNumbers = this.selectedRows.map(row => row.invoiceNumber).join(', ')
        this.$message.success(`选中的发票编号: ${invoiceNumbers}`)
        // 在实际业务中，可以在这里调用后端 API 进行批量报销
        this.selectedRows.forEach(row => {
          this.updateStatus(row.id).then(()=>{
            this.$message.success(`发票 ${row.invoiceNumber} 状态更新成功`)
          }).catch(()=>{
            this.$message.error(`发票 ${row.invoiceNumber} 状态更新失败`)
          }); // 更新每一条发票的状态
        });
      } else {
        this.$message.warning('请先选择至少一条发票记录')
      }
    },
    async checkInvoice(id) {
      this.dialogCheckVisible = true;
        this.openid = id;
    },
      async updateStatus(id){
        
        try {
          await axios.get(`/invoice/updateStatus/${id}`);
          this.$message.success('发票状态更新成功');
          this.fetchInvoices(); // 刷新发票列表
        } catch (error) {
          console.error('更新发票状态失败:', error);
          this.$message.error('更新发票状态失败');
        }
      },      // 查询方法
    search() {
      let result = [...this.invoices];
      
      // 根据发票标题筛选
      if (this.searchForm.title) {
        result = result.filter(invoice => 
          invoice.title && invoice.title.includes(this.searchForm.title)
        );
      }
      
      // 根据发票号码筛选
      if (this.searchForm.number) {
        result = result.filter(invoice => 
          invoice.number && invoice.number.includes(this.searchForm.number)
        );
      }
      
      // 根据开票日期筛选
      console.log(`this.searchForm.date`, this.searchForm.date);
      if (this.searchForm.date) {
        result = result.filter(invoice => {
        console.log(`invoice.date`, invoice.date)
          
          this.searchForm.date = this.searchForm.date.toISOString().substring(0, 10); // 格式化日期
          this.searchForm.date = this.searchForm.date.replace('年', '-').replace('月', '-').replace('日', '');
          return invoice.date && invoice.date.includes(this.searchForm.date);
        });
      }

      // 根据发票类型筛选
      if (this.searchForm.type) {
        result = result.filter(invoice => 
          invoice.type && invoice.type.includes(this.searchForm.type)
        );
      }
      
      // 更新显示的数据
      this.filteredInvoices = result;
    },
    // 重置查询
    resetSearch() {
      this.searchForm = {
        title: '',
        number: '',
        date: ''
      };
      this.filteredInvoices = [...this.invoices]; // 重置为原始数据
    },
      exportTable() {
      const data = this.invoices;
      let columns_to_keep = ['title','number','date','totalAmount']; // 需要保留的列
      let filteredData = data.map(item => {
        let newItem = {};
        columns_to_keep.forEach(col => {
          newItem[col] = item[col];
        });
        return newItem;
      });
      const headers = ['标题','发票号码','开票日期','总金额'];// 表头
      const worksheet = XLSX.utils.json_to_sheet(filteredData, { header: headers });
      const workbook = XLSX.utils.book_new();
      XLSX.utils.book_append_sheet(workbook, worksheet, "Sheet1");
      XLSX.writeFile(workbook, "invoices.xlsx");
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
            invoice.date = invoice.date.replace('年', '-').replace('月', '-').replace('日', '');
            invoice.date = new Date(invoice.date).toISOString(); // 格式化日期
            invoice.date = invoice.date.substring(0, 10); // 只保留日期部分
          });
          this.filteredInvoices = this.invoices; // 初始化过滤后的发票列表
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
            this.form.date = this.form.date.replace('年', '-').replace('月', '-').replace('日', '');
            this.form.date = new Date(this.form.date).toISOString(); // 格式化日期
            // 更新发票
            await axios.post('/invoice/update', this.form);
            let invoiceId = await axios.get('/invoice/lastInsertId');
            if(invoiceId.data.data == null) {
              invoiceId = 0;
            }else {
              invoiceId = invoiceId.data.data;
            }
            // for(let i = 0; i < this.form.detailList.length; i++) {
            //   this.form.detailList[i].invoiceId = invoiceId;
            //     await axios.post('/invoice-details/update', this.form.detailList[i]);
            // }
            
            this.$message.success('发票更新成功');
          } else {
            // 新增发票
            this.form.date = this.form.date.replace('年', '-').replace('月', '-').replace('日', '');
            this.form.date = new Date(this.form.date).toISOString(); // 格式化日期
            await axios.post('/invoice/save', this.form);
            await axios.post('/invoice/update', this.form);
            let invoiceId = await axios.get('/invoice/lastInsertId');
            if(invoiceId.data.data == null) {
              invoiceId = 0;
            }else {
              invoiceId = invoiceId.data.data;
            }
            // for(let i = 0; i < this.form.detailList.length; i++) {
            //   this.form.detailList[i].invoiceId = invoiceId;
            //     await axios.post('/invoice-details/save', this.form.detailList[i]);
            // }
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
      resetForm() {
      this.$refs.invoiceForm.resetFields();
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
      this.form.date = this.form.date.replace('年', '-').replace('月', '-').replace('日', '');
      this.form.date = new Date(this.form.date).toISOString(); // 格式化日期
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