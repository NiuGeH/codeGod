import Vue from "vue";
import App from "./App";
import router from "./router";
// import echarts from 'echarts';
import ElementUI from "element-ui";
import { store } from './private/store';
import Components from './components/index';
//mock数据
//import config from '../../assets/js/config';

// 全局设置
import 'element-ui/lib/theme-chalk/index.css';
import './assets/css/style.css';
import './assets/theme/theme.css';
import './private/service';
import './private/api';
import './private/verify';
//加密
import './private/crypto';

// if(config.isMock) {
//     let mockPlugin = require('../../mock/index');
//     Vue.use(mockPlugin)
// }

Vue.use( ElementUI );

for( let key in Components ){
    Vue.use( Components[ key ] );
}

// Vue.prototype.$echarts = echarts;

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount("#app");