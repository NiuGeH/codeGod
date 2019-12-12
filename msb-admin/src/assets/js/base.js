import Bus from '@/assets/js/bus'
import QuickConfig from '@/assets/js/quickconfig'
import '../../assets/scss/manage/manage.scss'

export default {
  name: 'Base',
  props: {
    isLong: {
      type: Boolean
    },
    params: {
      type: Object
    },
    pagePath: {
      type: String
    }
  },
  data () {
    return {
      dict: dict
    }
  },
  mounted: function () {
    let self = this
    setTimeout(function () {
      self.$store.commit('setLoading', false)
    }, 100)
    // 监听按键
    Bus.$off('windowKeyup').$on('windowKeyup', function (e) {
      // 确定是否有组合键
      let ctrlKey = {
        isKey: e.ctrlKey,
        string: 'ctrl'
      }
      let altKey = {
        isKey: e.altKey,
        string: 'alt'
      }
      let shiftKey = {
        isKey: e.shiftKey,
        string: 'shift'
      }
      let metaKey = {
        isKey: e.metaKey,
        string: 'command'
      }
      let exKeyList = [ctrlKey, altKey, shiftKey, metaKey]
      let keyStringList = []
      exKeyList.forEach(function (item, index) {
        if (item.isKey) {
          keyStringList.push(item.string)
        }
      })
      keyStringList.push(e.key)
      let quickKey = keyStringList.join('+')
      if (QuickConfig[quickKey]) {
        if (QuickConfig[quickKey][self.pagePath] && QuickConfig[quickKey][self.pagePath].length !== 0) {
          if (QuickConfig[quickKey][self.pagePath].length === 1) {
            self.togger(QuickConfig[quickKey][self.pagePath][0].method, QuickConfig[quickKey][self.pagePath][0].params)
          } else {
            QuickConfig[quickKey][self.pagePath].forEach(function (item, index) {
              self.togger(QuickConfig[quickKey][self.pagePath][index].method, QuickConfig[quickKey][self.pagePath][index].params)
            })
          }
        }
      }
    })
  },
  activated: function () {
    let self = this
    setTimeout(function () {
      self.$store.commit('setLoading', false)
    }, 100)
    // 监听按键
    Bus.$off('windowKeyup').$on('windowKeyup', function (e) {
      // 确定是否有组合键
      let ctrlKey = {
        isKey: e.ctrlKey,
        string: 'ctrl'
      }
      let altKey = {
        isKey: e.altKey,
        string: 'alt'
      }
      let shiftKey = {
        isKey: e.shiftKey,
        string: 'shift'
      }
      let metaKey = {
        isKey: e.metaKey,
        string: 'command'
      }
      let exKeyList = [ctrlKey, altKey, shiftKey, metaKey]
      let keyStringList = []
      exKeyList.forEach(function (item, index) {
        if (item.isKey) {
          keyStringList.push(item.string)
        }
      })
      keyStringList.push(e.key.toLowerCase())
      let quickKey = keyStringList.join('+')
      if (QuickConfig[quickKey]) {
        if (QuickConfig[quickKey][self.pagePath] && QuickConfig[quickKey][self.pagePath].length !== 0) {
          if (QuickConfig[quickKey][self.pagePath].length === 1) {
            self.togger(QuickConfig[quickKey][self.pagePath][0].method, QuickConfig[quickKey][self.pagePath][0].params, quickKey)
          } else {
            QuickConfig[quickKey][self.pagePath].forEach(function (item, index) {
              self.togger(QuickConfig[quickKey][self.pagePath][index].method, QuickConfig[quickKey][self.pagePath][index].params, quickKey)
            })
          }
        }
      }
    })
  },
  methods: {
    togger: function (methodname, params, quickKey) {
      // 触发对应的事件
      let self = this
      self[methodname](params, quickKey)
    }
  }
}
