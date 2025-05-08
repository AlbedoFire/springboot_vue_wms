<template>
    <el-form
      :model="registerForm"
      :rules="rules"
      ref="registerForm"
      label-width="100px"
      class="register-form"
    >
      <!-- 用户名输入框 -->
      <el-form-item label="用户名" prop="username">
        <el-input
          v-model="registerForm.username"
          placeholder="请输入用户名"
          clearable
        ></el-input>
      </el-form-item>
  
      <!-- 密码输入框 -->
      <el-form-item label="密码" prop="password">
        <el-input
          v-model="registerForm.password"
          type="password"
          placeholder="请输入密码"
          show-password
          clearable
        ></el-input>
      </el-form-item>
  
      <!-- 确认密码输入框 -->
      <el-form-item label="确认密码" prop="confirmPassword">
        <el-input
          v-model="registerForm.confirmPassword"
          type="password"
          placeholder="请再次输入密码"
          show-password
          clearable
        ></el-input>
      </el-form-item>
  
      <!-- 邮箱输入框 -->
      <el-form-item label="电话" prop="phone">
        <el-input
          v-model="registerForm.email"
          placeholder="请输入邮箱地址"
          clearable
        ></el-input>
      </el-form-item>
  
      <!-- 注册按钮 -->
      <el-form-item>
        <el-button type="primary" @click="submitForm">注册</el-button>
        <el-button @click="resetForm">重置</el-button>
      </el-form-item>
    </el-form>
  </template>
  
  <script>
  import axios from '@/axiosWrapper'; // 引入axios封装
  export default {
    data() {
      // 确认密码的自定义验证规则
      const validateConfirmPassword = (rule, value, callback) => {
        if (value !== this.registerForm.password) {
          callback(new Error('两次输入的密码不一致'));
        } else {
          callback();
        }
      };
  
      return {
        // 注册表单数据
        registerForm: {
          no: '',
          password: '',
          confirmPassword: '',
          phone: ''
        },
        // 表单验证规则
        rules: {
          name: [
            { required: true, message: '请输入用户名', trigger: 'blur' },
            { min: 3, max: 15, message: '用户名长度在 3 到 15 个字符之间', trigger: 'blur' }
          ],
          password: [
            { required: true, message: '请输入密码', trigger: 'blur' },
            { min: 6, max: 20, message: '密码长度在 6 到 20 个字符之间', trigger: 'blur' }
          ],
          confirmPassword: [
            { required: true, message: '请再次输入密码', trigger: 'blur' },
            { validator: validateConfirmPassword, trigger: 'blur' }
          ]
        }
      };
    },
    methods: {
      // 提交表单
      submitForm() {
        
        this.$refs.registerForm.validate((valid) => {
          if (valid) {
            this.registerForm['name'] = this.registerForm['no']
            // 表单验证通过，可以进行注册逻辑
            console.log('注册表单数据：', this.registerForm);
            axios.post('/user/save', this.registerForm)
              .then(response => {
                // 处理注册成功的逻辑
                this.$message.success('注册成功！');
                this.$router.replace('/login')
                console.log(response.data);
                // 可以在这里跳转到登录页面或其他页面
              })
              .catch(error => {
                // 处理注册失败的逻辑
                this.$message.error('注册失败，请稍后再试'+error.message);
              });
            // 在这里可以发送请求到后端进行注册操作
          } else {
            // 表单验证失败，提示用户
            this.$message.error('表单填写有误，请检查后再试');
          }
        });
      },
      // 重置表单
      resetForm() {
        this.$refs.registerForm.resetFields();
      }
    }
  };
  </script>
  
  <style scoped>
  .register-form {
    width: 400px;
    margin: 50px auto;
    padding: 20px;
    border: 1px solid #ebeef5;
    border-radius: 5px;
    background-color: #fff;
  }
  </style>