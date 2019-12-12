## 如何使用本地mock

Fros-UI提供了本地mock的功能，使用这可以通过配置对应的本地mock数据和请求url的正则即可让匹配到请求返回提前设置好的mock数据。

对应的配置地址在项目根目录下的mock文件夹，data文件夹为返回的mock数据，index则为编写匹配正则的入口文件。推荐在做mock数据分类的时候，按照不同功能模块的mock数据区分不同的js，方便开发快速的找到对应的mock数据，如通用的接口就在data文件夹下面创建一个common.js，获取表单记录就创建一个form.js，以此类推。

```javascript
// data/common.js
// ...
export const getUserInfo = req => {
  console.log('@@@Mock请求参数：', req)
  // 获取用户信息
  return {
    // 返回成功标识
    code: 0,
    // 查询返回列表
    data: {
      avatar: '/static/image/manage/default_handsome.jpg',
      username: 'wadaxiwanear',
      root: 0,
      rootName: '管理员',
      sex: 1
    }
  }
}
```

然后在index中引入对应的数据，并编写正则匹配逻辑。记得将逻辑写在isMock成立的代码块中。

```javascript
// index.js
import Mock from 'mockjs'
import {getMenuInfo, getUserInfo} from './data/common'
import config from '../assets/js/config'

if (config.isMock) {
  // 是否使用Mock数据
  Mock.mock(/\/getMenuInfo/, getMenuInfo)
  Mock.mock(/\/getUserInfo/, getUserInfo)
}

export default Mock

```
