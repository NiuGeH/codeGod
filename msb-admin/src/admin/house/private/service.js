import Vue from 'vue';
import axios from 'axios';
import { store } from './store.js';
import Qs from 'qs';
import { URL, ROOT, EXPORT, LOGIN } from './config.js';

// api设置
axios.defaults.baseURL = ROOT;
// 让 axios 请求接口时带上cookie
axios.defaults.withCredentials = true;

// 请求拦截器
axios.interceptors.request.use(
  config => {
    const token = store.state.token;

    if (token) { // 判断是否存在token，如果存在的话，则每个http header都加上token
      config.headers.Token = `${token}`;
    }
    return config;
  },
  err => {
    return Promise.reject(err);
  }
);


// 响应拦截器
axios.interceptors.response.use(
  response => {
    // if (response.type == 'formData' || response.method != 'post') {
    //   return response;
    // }
    // response.data = qs.stringify(response.data)
    return response;
  }, err => {
    alert('服务器错误, 请联系管理员');
    return Promise.reject(err);
  }
);

const service = {
  defaults: axios.defaults,
  //login: LOGIN,

  sync (promise) {
    return Promise.all(promise);
  },

  // post (url, params) {
  //   return axios.post(url, Qs.stringify(params));
  // },

  get (url, params) {
    return axios.get(url + (params ? `?${params}` : ''));
  },

  delete (url, params) {
    return axios.delete(url, params);
  },

  put (url, params) {
    return axios.put(url, params);
  },

  //json传递
  postJSON (url, params) {
    return axios.post(url, params);
  },

  //表单传递
  // login (url, params) {
  //   return axios.post(url, Qs.stringify(params), {
  //     headers: {
  //       'Content-Type': 'application/x-www-form-urlencoded',
  //       //'Content-Type': 'application/json',
  //     },
  //     //'responseType': 'blob'
  //   })
  // },
  post(url, params) {
    return axios.post(url,params, {
      headers: {
        'Content-Type': 'application/json',
        'Access-Control-Allow-Credentials':true,
        'Access-Control-Allow-Origin':'http://localhost:8081',
      },
      'responseType': 'json'
    },
    //{withCredentials:true}
    )
  },
};

Vue.prototype.http = service;
