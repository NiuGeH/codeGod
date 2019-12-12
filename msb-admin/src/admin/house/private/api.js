import Vue from 'vue';

const   h = Vue.prototype.http,
        api = {
          //加密测试
          getpassword: (params, callback) => h.post('/AppUser/findDataTest', params).then(res => callback(res.data)),
        };

Vue.prototype.api = api;