import utils from '../utils'
import Vue from 'vue'

const frosEleStorage = {
  getElement: function (self) {
    let untreated = [] // 未包含情况
    let frosEleList = self.$store.state.global.frosEleList
    let pageName = utils.getName(self.$store.state.global.defaultIndex)
    let pageEle = frosEleList[pageName]
    if(pageEle.length > 0){
      // alert("获取元素列表成功！")
      for(let i = 0; i < pageEle.length; i++){
        if(!frosEleStorage.execute(pageEle[i])){
          untreated.push(pageEle[i])
        }
      }
    }else{
      Vue.prototype.$message.error('该页面没有可控制元素信息，请核对后重试。')
    }
    return untreated
  },
  execute: function(ele){
    if(ele.resourceType == 2) {
      let selectList = document.querySelectorAll(ele.selector)
      if(selectList.length > 0){
          if(ele.params.toLowerCase() == 'hidden') {
            frosEleStorage.eleHidden(selectList)
            return true
          }else if(ele.params.toLowerCase() == 'remove') {
            frosEleStorage.eleRemove(selectList)
            return true
          }else if(ele.params.toLowerCase() == 'readonly') {
            frosEleStorage.eleReadonly(selectList)
            return true
          }else{
            return false
          }
      }
    }
  },
  eleHidden: function (selectList) {
    for(let i = 0; i < selectList.length; i++) {
      selectList[i].setAttribute("hidden",true) // 隐藏指定元素
    }
  },
  eleRemove: function (selectList) {
    for(let i = 0; i < selectList.length; i++) {
      selectList[i].parentNode.removeChild(selectList[i]) // 删除指定元素
    }
  },
  eleReadonly: function (selectList) {
    for(let i = 0; i < selectList.length; i++) {
      selectList[i].readOnly=true
    }
  }
}
export default frosEleStorage
