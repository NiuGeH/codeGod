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
