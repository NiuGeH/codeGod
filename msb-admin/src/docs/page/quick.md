## 页面快捷键配置

Fros-UI提供了页面组件的快捷键配置功能，用户只需通过在assets/js/quickconfig.js中配置相应的触发事件即可，配置方式如下图所示：

```javascript
// 快捷键默认选项
/**
 * 层级关系 按键值: { 页面组件路径: [{name: 执行事件名, method: 执行事件方法, params: 参数}] }
 * **/
let quickBtn = {
  'enter': {
    'demo/quick/Quick': [
      {
        name: '展示快捷按钮',
        method: 'showQuickKey',
        params: {
          name: 'fros-ui'
        }
      }
    ]
  },
  'ctrl+s': {
    'demo/quick/Quick': [
      {
        name: '展示快捷按钮',
        method: 'showQuickKey'
      }
    ]
  },
  'ctrl+d': {
    'demo/quick/Quick': [
      {
        name: '展示快捷按钮',
        method: 'showQuickKey'
      }
    ]
  }
}

export default quickBtn

```

对应的配置方式是，按键值: { 页面组件路径: [{name: 执行事件名, method: 执行事件方法, params: 参数}] }

其中，按键值约定为小写，执行事件为对应页面组件中method中的方法，这个方法可以接收两个参数，第一个参数是params，即快捷键附带的参数，另外一个是key，对应的按键值。
