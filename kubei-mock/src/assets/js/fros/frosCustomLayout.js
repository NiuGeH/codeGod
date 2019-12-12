import utils from "../utils";

const frosCustomLayout = {
  // 保存布局
  saveLayout: function(self,url,method,data){
    utils.sendReq({
      queryUrl: url,
      req: {
        method: method,
        data: data,
        success: function (res) {
          self.$message({
            type: 'success',
            message: JSON.stringify(res.data.message)
          })
        }
      }
    })
  },
  // 获取页面中可自定义的元素以及该页面已有设置项
  getFormCustom: function(self, componentCode){
    /*
    * 1、获取当前页面所有可编辑元素并进行数据拼接
    * 2、获取当前页面已有的数据进行展示
    * */
    let result = {}
    let allList = []
    let selectList = JSON.parse(frosCustomLayout.getFormConfig(self,componentCode)).code
    let customList = document.querySelectorAll('[frosComponentCode="' + componentCode + '"] .frosEleCustom')
    if(customList.length > 0){
      for(let i=0; i<customList.length; i++) {
        let single = {}
        let key = customList[i].getAttribute('frosEleCode')
        let label = customList[i].getAttribute('frosEleName')
        if(key == null){
          let childNodes = customList[i].getElementsByTagName('*')
          for(let j = 0;j<childNodes.length;j++){
            if(childNodes[j].getAttribute('frosEleCode')!=null){
              key = childNodes[j].getAttribute('frosEleCode')
              label = childNodes[j].getAttribute('frosEleName')
              break
            }
          }
        }
        single.key = key
        single.label = label
        let temp = true //  用作判空
        for (let z = 0; z < allList.length; z++) {
          if(allList[z].key == key){
            temp = false
          }
        }
        if(temp){
          allList.push(single)
        }
      }
    }
    result.allList = allList
    result.selectList = selectList
    return result
  },
  // 获取全局自定义布局数据
  getPageName: function(self){
    return utils.getName(self.$store.state.global.defaultIndex) // 获取自定义页面数据
  },
  // 获取全局自定义布局数据
  getCustomLayoutData: function(self){
    return self.$store.state.global.frosCustomLayoutList // 获取自定义页面数据
  },
  // 获取当前页Form布局配置数据
  getFormConfig: function(self, componentCode){
    let layoutList = frosCustomLayout.getCustomLayoutData(self) // 获取自定义页面数据
    let pageName = frosCustomLayout.getPageName(self) // 获取当前页面的router
    let configJson = ''
    for (let i=0; i < layoutList.length; i++) {
      let singleConfig = layoutList[i]
      if(singleConfig.pageConfig.pageCode.toUpperCase() === pageName.toUpperCase()){
        // 调用formConfig方法处理页面自定义布局
          let comConfigs = singleConfig.componentConfigs
          for(let j=0; j < comConfigs.length; j++){
            if(comConfigs[j].componentCode == componentCode){
                configJson = comConfigs[j].componentJson // 调用执行方法，将code中的对象设置为hidden
                break // 结束循环查找当前页自定义数据
            }
          }
          break // 结束循环查找当前页自定义数据
      }
    }
    return configJson
  },
  // TODO 获取当前页table布局配置数据,待后续完善
  getTableConfig: function(self, componentCode){
      let layoutList = frosCustomLayout.getCustomLayoutData(self) // 获取自定义页面数据
      let pageName = frosCustomLayout.getPageName(self) // 获取当前页面的router
      let configJson = ''
      for (let i=0; i < layoutList.length; i++) {
          let singleConfig = layoutList[i]
          if(singleConfig.pageConfig.pageCode.toUpperCase() === pageName.toUpperCase()){
              // 调用formConfig方法处理页面自定义布局
              let comConfigs = singleConfig.componentConfigs
              for(let j=0; j < comConfigs.length; j++){
                  if(comConfigs[j].componentCode == componentCode){
                      configJson = comConfigs[j].componentJson // 调用执行方法，将code中的对象设置为hidden
                      break // 结束循环查找当前页自定义数据
                  }
              }
              break // 结束循环查找当前页自定义数据
          }
      }
      return configJson
  },

  // 执行布局生效操作
    executeLayout: function (self, componentCode, componentType) {
    let layoutList = self.$store.state.global.frosCustomLayoutList // 获取自定义页面数据
    let pageName = utils.getName(self.$store.state.global.defaultIndex) // 获取当前页面的router
    for (let i=0; i < layoutList.length; i++) {
      let singleConfig = layoutList[i]
      if(singleConfig.pageConfig.pageCode.toUpperCase() === pageName.toUpperCase()){
          let comConfigs = singleConfig.componentConfigs
        // 调用formConfig方法处理页面自定义布局
          for(let j=0; j < comConfigs.length; j++ ){
            if(comConfigs[j].componentCode == componentCode){
              if(componentType == "table"){
                  // 调用tableConfig方法处理table布局
                  frosCustomLayout.tableConfig(comConfigs[j].componentJson) // 调用执行方法，将code中的对象设置为hidden
              }else {
                  frosCustomLayout.formConfig(comConfigs[j].componentJson) // 调用执行方法，将code中的对象设置为hidden
              }
              break // 结束循环查找当前页自定义数据
            }
          }
        break // 结束循环查找当前页自定义数据
      }
    }
  },
  // 页面表单布局配置
  formConfig: function(formConfig){
    let codes = JSON.parse(formConfig).code
    if(codes.length > 0) {
      for (let i=0; i < codes.length; i++){
        let selectList = document.querySelectorAll('[frosEleShow=' + codes[i] + ']')
        if(selectList.length > 0){
          frosCustomLayout.eleHidden(selectList)
        }
      }
    }
  },
  // TODO table布局配置，待后续完善。
  tableConfig: function(tableConfigs){
    return tableConfigs
  },

  // 执行具体的操作
  execute: function (config) {
    let code = config.code
    if(code != undefined) {
      for (let i=0; i < code.length; i++){
        let selectList = document.querySelectorAll('[frosEleShow=' + code[i] + ']')
        if(selectList.length > 0){
          frosCustomLayout.eleHidden(selectList)
        }
      }
    }
  },
  // 元素隐藏
  eleHidden: function (selectList) {
    for (let i = 0; i < selectList.length; i++) {
      selectList[i].setAttribute("hidden", true) // 隐藏指定元素
    }
  },
  // 元素移除
  eleRemove: function (selectList) {
    for (let i = 0; i < selectList.length; i++) {
      selectList[i].parentNode.removeChild(selectList[i]) // 删除指定元素
    }
  },
  // 元素只读
  eleReadonly: function (selectList) {
    for (let i = 0; i < selectList.length; i++) {
      selectList[i].readOnly = true
    }
  }
}
export default frosCustomLayout
