// 接口地址
let API = {

    // 开发环境
    //'118.24.219.198:18089': 'http://118.24.219.198:18083/iotcoldchainware',

    // 本地环境
    //'117.78.37.58:8000': 'http://117.78.37.58:8000/codeGod/',
    'localhost:8081': 'http://192.168.3.10:8000/codeGod/',
    
    // 测试环境
    //'152.136.37.175:18087':'http://152.136.37.175:8087/iotcoldchainware',

    // 我的ip, 服务器
    '192.168.3.31': 'http://192.168.3.10:8000/codeGod/'
};

// 登录页面地址
let LOGIN = {
    // 开发环境
    '118.24.219.198:18089': 'http://62.234.164.69:8585/iotplatform/login',

    // 测试环境
    '152.136.37.175:18087':'http://152.136.37.175:8585/iotplatform/login',

    // 本地环境
    'localhost:8081':'http://117.78.37.58:8000/codeGod/login',

    // 本地联调的服务器
    '192.168.3.10': 'http://192.168.3.10:8000/codeGod/login'
};

let ROOT, URL = API[ location.host ];

//由于封装的axios请求中，会将ROOT打包进去，为了方便之后不再更改，判断了当前环境，而生成的不同的ROOT
if (process.env.NODE_ENV === "development") {
    //开发环境下的代理地址，解决本地跨域跨域
    ROOT = "/api";
} else {
    //生产环境下的地址
    ROOT = URL;
}
exports.URL = URL; //代理指向地址
exports.ROOT = ROOT;
//exports.EXPORT = URL; // 导出地址

// 项目登录地址
exports.LOGIN = LOGIN[ location.host ];