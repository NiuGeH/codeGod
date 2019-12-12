import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

export const store = new Vuex.Store({
    state: {
        router: localStorage.getItem('router') || '',

        // socket地址
        socket: '',

        // token
        token: sessionStorage.getItem('token') || '',

        // userinfo
        userinfo: sessionStorage.getItem('userinfo') || '',

        // theme
        theme: localStorage.getItem('theme') ||  'default-theme'

    },
    mutations: {
        setRouter( state, value ){
            state.router = value;
            localStorage.setItem('router', JSON.stringify(value));
        },
        toggleThemeStore( state, value ){
            state.theme = value;
            localStorage.setItem('theme', value);
        },
        saveUserInfo( state, value ){
            state.userinfo = value;
            sessionStorage.setItem('userinfo', JSON.stringify(value));
        },
        saveToken( state, value ){
            state.token = value;
            sessionStorage.setItem('token', value);
        },
        empty( state ){
            state.token = '';
            state.userinfo = '';
            sessionStorage.removeItem('token');
            sessionStorage.removeItem('userinfo');
        }
    },
    getters: {
    },
    actions: {
    }
});