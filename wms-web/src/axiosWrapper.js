import axios from 'axios';

// 创建 axios 实例
const instance = axios.create({
  baseURL: 'http://localhost:8090', // 设置基础路径
  timeout: 5000, // 请求超时时间
});

// 请求拦截器
instance.interceptors.request.use(
  (config) => {
    // 检查网络状态
    if (!navigator.onLine) {
      // 如果离线，返回错误
      return Promise.reject(new Error('网络不可用，请检查您的网络连接'));
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// 响应拦截器
instance.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    // 捕获网络错误
    if (!navigator.onLine) {
      return Promise.reject(new Error('网络不可用，请检查您的网络连接'));
    }
    if (error.response) {
      // 服务器返回了状态码
      return Promise.reject(error.response.data);
    } else if (error.request) {
      // 请求已发出，但未收到响应
      return Promise.reject(new Error('请求超时或服务器未响应'));
    } else {
      // 其他错误
      return Promise.reject(error.message);
    }
  }
);

// request 拦截器
// 可以自请求发送前对请求做一些处理
// 比如统一加token，对请求参数统一加密
request.interceptors.request.use(config => {
    config.headers['Content-Type'] = 'application/json;charset=utf-8';        // 设置请求头格式
    let user = JSON.parse(localStorage.getItem("xm-user") || '{}')  // 获取缓存的用户信息
    config.headers['token'] = user.token  // 设置请求头

    return config
}, error => {
    console.error('request error: ' + error) // for debug
    return Promise.reject(error)
});

export default instance;