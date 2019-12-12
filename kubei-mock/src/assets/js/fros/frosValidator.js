import utils from '../utils'

const validator = {
  /**
   * @param self  当前引用检验者的this对象
   * @param url 请求后台接口获取检验规则的地址
   * @param parm  请求后台所携带的参数
   * @param rules 对应表单对象的校验规则变量名
   * @param model 对应表单的实体model
   * @param uniqueRuleParm 唯一性校验请求后台时所携带主键名称。
   */
  getRulerData: function (self, url, parm, vname, model, uniqueRuleParm) {
    utils.sendReq({
      queryUrl: url,
      req: {
        method: 'post',
        data: parm,
        success: function (res) {
          // console.log(JSON.stringify(res.data))
          if(res.data.code == 200 || res.data.code == 0){
              self[vname] = validator.outputRulers(self, res.data.data, vname, model, uniqueRuleParm)
          }else{
              self[vname] ={}
              self.$message({
                  message: '获取校验规则失败，请联系管理员。',
                  type: 'warning'
              });
          }
          // console.log(JSON.stringify(self[vname]))
        }
      }
    })
  },
  outputRulers: function (self, rulData, vname, model, uniqueRuleParm) {
      let rules = {};
      let data = rulData;
      for (let i = 0; i < data.length; i++) {
          let name = data[i].validateField.code;
          let validate = data[i].validateRules;
          if(validate == null || validate ==undefined){
              continue
          }
          let valList = [];
          for (let j = 0; j < validate.length; j++) {
              let temp = {};
              let valrule = validate[j];
              if (valrule.valType == 1) { // 必须有值 required
                  temp = this.requiredRule(valrule)
              } else if (valrule.valType == 2) { // 最小值 min
                  temp = this.minRule(valrule, name)
              } else if (valrule.valType == 3) { // 最大值 max
                  temp = this.maxRule(valrule, name)
              } else if (valrule.valType == 4) { // 正则 regexp
                  temp = this.regexpRule(valrule, name)
              } else if (valrule.valType == 5) { // 单唯一 unique
                  temp = this.uniqueRule(valrule, model, self, uniqueRuleParm)
              } else if (valrule.valType == 6) { // 组合唯一 unionUnique
                  temp = this.unionUniqueRule(valrule, model, self, uniqueRuleParm)
              } else if (valrule.valType == 7) { // 组内值相等
                  temp = this.eachEqual(valrule, model) //Each-Equal
              } else if (valrule.valType == 8) { // 组内至少N个值不为空
                  temp = this.leastXNotNull(valrule, model) // Least-X-NotNull
              } else if (valrule.valType == 9) { // 动态数据校验
                  temp = this.dynamicRule(self, valrule, vname)
              } else if (valrule.valType == 10) { // 基础校验
                  temp = this.baseRule(self, valrule)
              }
              valList.push(temp) // push规则
          }
          rules[name] = valList;
      }
      return rules
  },
  // 必须有值 required
  requiredRule: function (valrule) {
    let result = {}
    result["required"] = true;
    if(valrule.valField != null || valrule.valField != undefined){
        result["type"] = valrule.valField.toLocaleLowerCase();
    }
    result["message"] = valrule.valErrorMessage;
    result["trigger"] = "change"
    return result
  },
  // 最小值 min
  minRule: function (valrule) {
    let result = {}
    result["min"] = parseInt(valrule.valRule);
    if(valrule.valField != null || valrule.valField != undefined){
      result["type"] = valrule.valField.toLocaleLowerCase();
    }
    result["message"] = valrule.valErrorMessage;
    result["trigger"] = "change"
    return result
  },
  // 最大值 max
  maxRule: function (valrule) {
    let result = {}
    result["max"] = parseInt(valrule.valRule);
    if(valrule.valField != null || valrule.valField != undefined){
      result["type"] = valrule.valField.toLocaleLowerCase();
    }
    result["message"] = valrule.valErrorMessage;
    result["trigger"] = "change"
    return result
  },
  // 正则 regexp
  regexpRule: function (valrule) {
    let result = {}
    result["pattern"] = valrule.valRule;
    result["message"] = valrule.valErrorMessage;
    result["trigger"] = "change"
    return result
  },
  // 单唯一 unique
  uniqueRule: function (valrule, model, self, uniqueRuleParm) {
    let result = {}
    let codeList = valrule.valCodes
    result["validator"] = (rule, value, callback) => {
      let tempParm = {}
      if(uniqueRuleParm != undefined && uniqueRuleParm != null){
          tempParm[uniqueRuleParm] = model[uniqueRuleParm]
      }
      for (let i = 0; i < codeList.length; i++) {
        tempParm[codeList[i]] = model[codeList[i]]
      }
      if (Object.keys(tempParm).length != 0) {
        //后台方法
        utils.sendReq({
          queryUrl: valrule.valRule,
          req: {
            method: 'post',
            data: tempParm,
            success: function (res) {
              if(res.data.code == 200){
                  if (res.data.data) {
                      callback()
                  } else {
                      callback(valrule.valErrorMessage)
                  }
              }else{
                  self.$message({
                      message: '获取唯一性校验失败，请联系管理员。',
                      type: 'warning'
                  });
              }
            }
          }
        })
      }
    };
    result["trigger"] = "change"
    return result
  },
  // 组合唯一 unionUnique
  unionUniqueRule: function (valrule, model ,self, uniqueRuleParm) {
    let result = {}
    let codeList = valrule.valCodes
    result["validator"] = (rule, value, callback) => {
      let tempParm = {}
        if(uniqueRuleParm != undefined && uniqueRuleParm != null){
            tempParm[uniqueRuleParm] = model[uniqueRuleParm]
        }
      for (let i = 0; i < codeList.length; i++) {
        tempParm[codeList[i]] = model[codeList[i]]
      }
      if (Object.keys(tempParm).length != 0) {
        //后台方法
        utils.sendReq({
          queryUrl: valrule.valRule,
          req: {
            method: 'post',
            data: tempParm,
            success: function (res) {
                if(res.data.code == 200){
                    if (res.data.data) {
                        callback()
                    } else {
                        callback(valrule.valErrorMessage)
                    }
                }else{
                    self.$message({
                        message: '获取唯一性校验失败，请联系管理员。',
                        type: 'warning'
                    });
                }
            }
          }
        })
      }
    };
    result["trigger"] = "change"
    return result
  },
  // 组内值相等
  eachEqual: function (valrule, model) {
    let result = {}
    let codeList = valrule.valCodes
    result["validator"] = (rule, value, callback) => {
      // 组内值相等
      for (let i = 0; i < codeList.length; i++) {
        if (value !== model[codeList[i]]) {
          callback(valrule.valErrorMessage)
          return
        } else {
          callback()
        }
      }
    };
    result["trigger"] = "change"
    return result
  },
  // 至少有N个值不能为空
  leastXNotNull: function (valrule, model) {
    let result = {}
    let codeList = valrule.valCodes
    let condition = valrule.valRule // 组合校验条件
    result["validator"] = (rule, value, callback) => {
      // 至少X个值不能为空
      let counter = 0
      /*if (value != null && value != "" && value != undefined) {
        counter = counter + 1
      }*/
      for (let j = 0; j < codeList.length; j++) {
        if (model[codeList[j]] != null && model[codeList[j]] != "" && model[codeList[j]] != undefined) {
          counter = counter + 1
        }
      }
      if (counter < condition) {
        callback(valrule.valErrorMessage)
        return
      } else {
        callback()
      }
    };
    result["trigger"] = "change"
    return result
  },
  // 动态校验
  dynamicRule: function (self, valrule, vname) {
    /*
    * 1、根据valRule字段获取condition校验前置条件
    * 2、如果满足前置条件则执行给校验规则set值，不满足则移除对应校验规则
    * 3、修改校验规则的值
    * */
    let result = {}
    result["validator"] = (rule, value, callback) => {
      let valRule = JSON.parse(valrule.valRule)
      let lps = valRule.listenProperties // 获取对应关联值
      if (eval(valRule.condition)) {
        let tempRules = self[vname]
        // let tempRules = self.rules
        for (let i = 0; i < lps.length; i++) {
          let keys = []
          for (let key in lps[i]) {
            keys.push(key)
          }
          for (let j = 0; j < keys.length; j++) {
            let drule = lps[i][keys[j]]
            for (let z = 0; z < drule.length; z++) {
              if (tempRules[[keys[j]]] != undefined) {
                tempRules[[keys[j]]].push(drule[z])
              } else {
                let tempList = new Array()
                tempList.push(drule[z])
                self.$set(tempRules, [keys[j]], tempList)
              }
            }
          }
        }
        self[vname] = tempRules  // 将修改后的校验规则赋值给页面校验
      } else {
        // let etempRules = JSON.parse(JSON.stringify(self[vname]))
        let etempRules = self[vname]
        for (let b = 0; b < lps.length; b++) {
          let ekeys = []
          for (let key in lps[b]) {
            ekeys.push(key)
          }
          for (let g = 0; g < ekeys.length; g++) {
            let edrule = lps[b][ekeys[g]]
            for (let k = 0; k < edrule.length; k++) {
              if (etempRules[[ekeys[g]]] != undefined) { //  判断该字段是否有校验规则，有则匹配
                for (let c = 0; c < etempRules[[ekeys[g]]].length; c++) {
                  // 获取两个对象是否相等
                  if (this.isObjectValueEqual(edrule[k], etempRules[[ekeys[g]]][c])) {
                    etempRules[[ekeys[g]]].splice(c, 1)  // 如果不满足条件则移除动态添加的校验规则
                  }
                }
              }
            }
          }
        }
        self[vname] = etempRules  // 将修改后的校验规则赋值给页面校验
      }
      callback()  // 绑定校验通过信息
    };
    result["trigger"] = "change"
    return result
  },
  // 增加普通校验
  baseRule: function(self, valrule){
      let result = {}
      result["required"] = false;
      result["type"] = valrule.valField.toLocaleLowerCase()
      result["message"] = valrule.valErrorMessage
      result["trigger"] = "change"
      return result
  },
  // 判断两个对象是否相等
  isObjectValueEqual: function (a, b) {
    let result = true
    for (let k in a) {
      if (b[k] == null || b[k] == undefined) {
        return false
      } else if (a[k] != b[k]) {
        return false
      }
    }
    return result
  }
}
export default validator
