## 如何使用工具集

> 导入

工具集为assets/js中的utils.js，使用者只需要在对应的页面组件中import进去即可使用。

```javascript
import utils from '@js/utils'

utils.timestampToDateString(new Date().getTime(), '-', false, false)
```

目前工具集提供了cookies操作、日志操作、获取硬件信息、http请求、时间操作、excel操作等，如果使用者有需要一些其他的工具集可以自行在utils中添加，或者部分需要增加到utils中的话可以联系框架维护人员进行添加。
