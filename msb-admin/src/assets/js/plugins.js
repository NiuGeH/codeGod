import store from '../../store/index'
import Bus from '../js/bus'

let Plugin = {}

Plugin.install = function (Vue) {
  // 新增一个页面
  const plugin = new Vue({
    store,
    methods: {
      handleSelect (key) {
        let self = this
        self.$store.commit('setDefaultIndex', key.toString())
      },
      deleteTab: function (option) {
        Bus.$emit('deleteTab', option)
      }
    }
  })
  /**
  * @param option|新建页面所需参数|Object|eg: {
  *   path: 'HomePage', // 组件路径
  *   title: '主页', // 标签页对应的title
  *   params: {
  *     orderId: '13800138000'
  *   } // 传递参数 需为对象Object
  * }
  * **/
  Vue.prototype.$newpage = (option) => {
    let params
    if (option.params) {
      option.params.apiNew = true
      params = JSON.stringify(option.params)
    } else {
      params = JSON.stringify({ apiNew: true })
    }
    plugin.handleSelect(option.path + '@gap@' + option.title + '@longgap@' + params)
  }
  /**
     * @param option|关闭tab所需参数|Object|eg: {
     *     type: 'close', // 关闭类型，必填
     *     idx: 0,    // 标签索引 Number，必填
     *     target: 0 | 'HomePage' // 关闭标签后跳转标签或组件 Number | String（仅type==='close'生效，默认跳往上一页）
     * }
     * **/
  Vue.prototype.$closepage = (option) => {
    plugin.deleteTab(option)
  }
}

export default Plugin
