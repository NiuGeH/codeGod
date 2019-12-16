import Vue from 'vue';

const   h = Vue.prototype.http,
        api = {
          //加密测试
          getpassword: (params, callback) => h.post('/findDataTest', params).then(res => callback(res.data)),
           //登录
          login: (params, callback) => h.post('/login', params).then(res => callback(res.data)),
           //修改密码modifyPwd
           modifyPwd: (params, callback) => h.post('/modifyPwd', params).then(res => callback(res.data)),
        };

Vue.prototype.api = api;