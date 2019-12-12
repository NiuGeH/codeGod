import store from '@store/index'

export default {
  'permit': {
    inserted (el, binding) {
      let userRoot = store.getters.userInfo.root
      let flag = 0
      // 获取用户信息，判断权限
      if (binding.value instanceof Array) {
        for (let i = 0; i < binding.value.length; i++) {
          let item = binding.value[i]
          if (item[0] && item[0] === '!') {
            // 取反操作
            if (userRoot !== item.substr(1, item.length - 1)) {
              flag++
              continue
            } else {
              el.parentNode && el.parentNode.removeChild(el)
              return false
            }
          } else {
            // 等于操作
            if (userRoot === item) {
              // 符合条件
              return true
            } else {
              continue
            }
          }
        }
        if (flag === binding.value.length) {
          return true
        } else {
          el.parentNode && el.parentNode.removeChild(el)
          return false
        }
      } else {
        el.parentNode && el.parentNode.removeChild(el)
        return false
      }
    },
    update: function (el, binding) {
      let userRoot = store.getters.userInfo.root
      let flag = 0
      // 获取用户信息，判断权限
      if (binding.value instanceof Array) {
        for (let i = 0; i < binding.value.length; i++) {
          let item = binding.value[i]
          if (item[0] && item[0] === '!') {
            // 取反操作
            if (userRoot !== item.substr(1, item.length - 1)) {
              flag++
              continue
            } else {
              el.parentNode && el.parentNode.removeChild(el)
              return false
            }
          } else {
            // 等于操作
            if (userRoot === item) {
              // 符合条件
              return true
            } else {
              continue
            }
          }
        }
        if (flag === binding.value.length) {
          return true
        } else {
          el.parentNode && el.parentNode.removeChild(el)
          return false
        }
      } else {
        el.parentNode && el.parentNode.removeChild(el)
        return false
      }
    }
  }
}
