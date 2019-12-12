## 项目配置

Fros-UI提供一部分项目配置的内容，需要修改属性配置可以到assets/js/config.js中进行修改，具体的配置内容如下所示：

```javascript
let apiConfig = require('apiConfig') || {}
let sysInfo = {
  name: 'FROS-UI',
  appId: apiConfig.appId || '',
  appSecret: apiConfig.appSecret || '',
  defaultModule: 0,
  unCheckPath: ['HomePage', 'setting/Setting', 'logline/Logline'] // 无需校验权限的路径（默认为一些系统自带组件）
}

let config = {
  sysInfo: sysInfo,
  hasBread: true, // 是否显示面包屑
  apiHost: apiConfig.apiHost || '',
  apiLoclHost: apiConfig.apiLoclHost || '',
  isMock: true,
  axiosFormDataConfig: {
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    },
    timeout: 20000
  },
  axiosJsonConfig: {
    headers: {
      'Content-Type': 'application/json'
    },
    timeout: 20000
  }
}
```

apiConfig.js对应的是项目打包完读取的接口相关的配置文件，这个文件脱离整个打包项目，在static/js下，可以方面像docker等部署针对不同的镜像提供不同的接口地址。

sysInfo包含了系统名称，appid，appsecret等配置项，除此之外用户也可以根据自己的项目需要配置相应的内容增加到里面。

hasBread是配置管理界面是否显示面包屑的配置。

apiHost对应的接口host，在dev模式下是默认通过webpack server进行代理，在prod模式打包出来的话则是读取apiConfig

isMock在dev模式下面是否开启mock数据，开启的话匹配到mock的内容将会返回提前设置好的mock数据。

axiosFormDataConfig，axiosJsonConfig是两个头部，对于一些需要固定放在请求头部中的参数也可以在这里提前写入。
