/**
 * 此文件将通过docker volumes自动替换实际接口环境地址
 * add by dream.chen
 */

var apiConfig = {
  apiHost: '',
  apiLoclHost: '',
  // 公共服务
  publicServerHostList: {
    imgUpload: 'http://211.159.156.75:8080'
  }
}

if (typeof window !== 'undefined') {
  window['apiConfig'] = apiConfig
}
