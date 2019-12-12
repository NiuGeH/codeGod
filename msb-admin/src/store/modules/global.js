let state = {
  userInfo: {},
  menuInfo: [],
  frosEleList: [], // 用于存放当前工程所有页面的元素权限
  frosCustomLayoutList: {}, // 用于存放当前用户所有页面自定义布局数据
  defaultIndex: '',
  cachePage: [],
  lang: 'zh',
  loadingShow: false,
  // 子模块索引
  childSysIndex: null,
  // 首页快捷模块
  homeQuickList: {homeQuickList: [], homeQuickIndex: []},
  objConfig: {},
  isMobile: false,
  rightPathList: [],
  frosDictionary :[] // 数据字典
}

let mutations = {
  setUserInfo (state, userInfo) {
    state.userInfo = userInfo
  },
  setMenuInfo (state, menuInfo) {
    state.menuInfo = menuInfo
  },
  setFrosEleList (state, frosEleList) {
    state.frosEleList = frosEleList
  },
  setFrosCustomLayoutList (state, frosCustomLayoutList) {
    state.frosCustomLayoutList = frosCustomLayoutList
  },
  // 数据字典
  setFrosDictionary (state, frosDictionary) {
  state.frosDictionary = frosDictionary
},
  setDefaultIndex (state, defaultIndex) {
    state.defaultIndex = defaultIndex
  },
  setCachePage (state, cpPath) {
    let cpPathIndex = state.cachePage.indexOf(cpPath)
    if (cpPathIndex === -1) {
      state.cachePage.push(cpPath)
    }
  },
  cleanCachePage (state) {
    state.cachePage = []
  },
  cleanDefaultIndex (state) {
    state.defaultIndex = ''
  },
  moveCachePage (state, cpPath) {
    let cpPathIndex = state.cachePage.indexOf(cpPath)
    if (cpPathIndex !== -1) {
      delete state.cachePage[cpPathIndex]
      let newCachePage = []
      state.cachePage.forEach(function (item) {
        if (item) {
          newCachePage.push(item)
        }
      })
      state.cachePage = newCachePage
    }
  },
  setAppLang (state, lang) {
    state.lang = lang
  },
  setLoading (state, status) {
    state.loadingShow = status
  },
  changeChildSysIndex (state, index) {
    state.childSysIndex = index
  },
  updateHomeQuickList (state, homeQuickList) {
    state.homeQuickList = homeQuickList
  },
  setObjConfig (state, objConfig) {
    state.objConfig = objConfig
  },
  setIsMobile (state, isMobile) {
    state.isMobile = isMobile
  },
  setRightPathInfo (state, rightPathList) {
    state.rightPathList = rightPathList
  }
}

let actions = {}

let getters = {
  defaultIndex: state => {
    return state.defaultIndex
  },
  cachePage: state => {
    return state.cachePage
  },
  curLang: state => {
    return state.lang
  },
  loadingShow: state => {
    return state.loadingShow
  },
  userInfo: state => {
    return state.userInfo
  },
  childSysIndex: state => {
    return state.childSysIndex
  },
  homeQuickList: state => {
    return state.homeQuickList
  },
  menuInfo: state => {
    return state.menuInfo
  },
  objConfig: state => {
    return state.objConfig
  },
  isMobile: state => {
    return state.isMobile
  },
  rightPathList: state => {
    return state.rightPathList
  }
}

export default {
  state,
  mutations,
  actions,
  getters
}
