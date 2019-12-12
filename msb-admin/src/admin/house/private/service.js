import Vue from 'vue';
import axios from 'axios';
import { store } from './store.js.js';

import Qs from 'qs';
import { URL, ROOT, EXPORT, LOGIN } from './config.js.js';

// api设置
axios.defaults.baseURL = ROOT;

// 请求拦截器
axios.interceptors.request.use(
    config => {
        const token = store.state.token;

        if ( token ) { // 判断是否存在token，如果存在的话，则每个http header都加上token
            config.headers.Token = `${ token }`;
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
        return response;
    }, err => {
        alert('服务器错误, 请联系管理员');
        return Promise.reject( err );
    }
);

const service = {
    defaults: axios.defaults,

    sync(promise) {
        return Promise.all(promise);
    },

    post(url, params) {
        return axios.post(url, params);
    },

    get(url, params) {
        return axios.get(url + (params ? `?${ params }` : ''));
    },

    delete(url, params) {
        return axios.delete(url, params);
    },

    put(url, params) {
        return axios.put(url, params);
    }
};

Vue.prototype.http = service;
