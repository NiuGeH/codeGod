// 项目配置文件

let apiConfig = require('apiConfig') || {}

let sysInfo = {
    name: 'FROS-UI',
    appId: apiConfig.appId || '',
    appSecret: apiConfig.appSecret || '',
    defaultModule: 0,
    unCheckPath: ['HomePage', 'setting/Setting', 'logline/Logline'] // 无需校验权限的路径（默认为一些系统自带组件）
}

// 股份提供的公共服务
let defaultPublicServiceHostList = {
    imgUpload: 'http://211.159.156.75:8080'
}

let config = {
    sysInfo: sysInfo,
    hasBread: true, // 是否显示面包屑
    apiHost: apiConfig.apiHost || '',
    apiLoclHost: apiConfig.apiLoclHost || '',
    publicServerHostList: apiConfig.publicServerHostList || defaultPublicServiceHostList,
    isMock: true,
    axiosFormDataConfig: {
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        timeout: 20000
    },
    axiosMultiFormData: {
        headers: {},
        timeout: 20000
    },
    axiosJsonConfig: {
        headers: {
            'Content-Type': 'application/json'
        },
        timeout: 20000
    }
}

export default config