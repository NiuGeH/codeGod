import api from './api/common/apiMethod'
import config from './config'
export default {
  name: 'Root',
  data () {
    return {}
  },
  created: function () {
    let self = this
    self.getMenuInfo()
    self.getUserInfo()
    self.getAppLang()
    self.getObjConfig()
    self.getFrosEleList() // 获取工程中元素权限所有数据
    self.getFrosCustomLayoutList() // 获取工程中页面布局所有数据
    self.getFrosDictionary() // 数据字典
  },
  methods: {
    getMenuInfo: function () {
      let self = this
      // 获取用户信息及菜单权限
      api.getMenuInfo({
        data: {
        },
        success: function (res) {
          // 获取当前的用户信息
          let ablePick = []
          let menuList = res.data.data.menuList
          for (let i = 0; i < menuList.length; i++) {
            let thisModule = menuList[i]
            for (let m = 0; m < thisModule.children.length; m++) {
              let thisItem = thisModule.children[m]
              if (thisItem.url) {
                ablePick.push(thisItem.url)
                continue
              } else {
                for (let j = 0; j < thisItem.children.length; j++) {
                  let thisSubItem = thisItem.children[j]
                   // functionType(1:MODULE 2:MENU 3:wnode)
                  if(thisSubItem.functionType != 3){
                      if (thisSubItem.url) {
                        ablePick.push(thisSubItem.url)
                        continue
                      } else {
                        for (let l = 0; l < thisSubItem.children.length; l++) {
                          let thisSubSubItem = thisSubItem.children[l]
                          ablePick.push(thisSubSubItem.url)
                        }
                    }
                  }else{
                    for (let k = 0; k < thisSubItem.children.length; k++) {
                      let thisSubSubItem = thisSubItem.children[k]
                      if (thisSubSubItem.url) {
                        ablePick.push(thisSubSubItem.url)
                        continue
                      } else {
                        for (let l = 0; l < thisSubSubItem.children.length; l++) {
                          let thisSubSubSubItem = thisSubSubItem.children[l]
                          ablePick.push(thisSubSubSubItem.url)
                        }
                      }
                    }
                  }

                }
              }
            }
          }
          self.$store.commit('setMenuInfo', res.data.data)
          self.$store.commit('setRightPathInfo', ablePick)
        }
      })
    },
    getUserInfo: function () {
      let self = this
      api.getUserInfo({
        data: {},
        success: function (res) {
          self.$store.commit('setUserInfo', res.data.data)
        }
      })
    },
    getAppLang: function () {
      let self = this
      let lang = localStorage.getItem('lang') ? localStorage.getItem('lang') : 'zh'
      // 获取当前选择系统语言
      self.$store.commit('setAppLang', lang)
    },
    getObjConfig: function () {
      let self = this
      self.$store.commit('setObjConfig', config)
    },
    getFrosEleList: function () {
      let self = this
      api.getFrosEleList({
        data: {},
        success: function (res) {
          self.$store.commit('setFrosEleList', res.data.data)
        }
      })
    },
    getFrosCustomLayoutList: function () {
      let self = this
      api.getFrosCustomLayoutList({
        data: {},
        success: function (res) {
          self.$store.commit('setFrosCustomLayoutList', res.data.data)
        }
      })
    },
    // 数据字典
    getFrosDictionary: function () {
      let self = this
      api.getFrosDictionary({
        data: {},
        success: function (res) {
          self.$store.commit('setFrosDictionary', res.data.data)
            // 写入本地缓存
            localStorage.setItem('FrosDictionary', JSON.stringify(res.data.data))
        }
      })
    }
  }
}
