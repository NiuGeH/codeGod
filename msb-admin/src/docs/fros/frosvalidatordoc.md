# FrosValidator使用说明

FrosVue校验控件是为了提供前端校验数据的合法性。目前前端校验支持非空、字段最大值、字段最小值、正则、单字段唯一校验、组合合并字段唯一性校验、组内字段相等、组内至少N个值不能为空、动态校验等校验场景。

```html
<el-form :rules="rules" ref="form" :model="form" :validate-on-rule-change="false" label-width="80px">
    <el-form-item label="姓名" prop="name" class='fros-form-item-center'>
        <el-input v-model.trim="form.name"></el-input>
    </el-form-item>
    <el-form-item label="密码" prop="password1" class='fros-form-item-right'>
        <el-input v-model="form.password1"></el-input>
    </el-form-item>
    <el-form-item label="确认密码" prop="password2">
        <el-input v-model="form.password2"></el-input>
    </el-form-item>
    <el-form-item>
        <el-button type="primary" @click="submitForm('form')">立即创建</el-button>
        <el-button>取消</el-button>
    </el-form-item>
</el-form>

/****** 用法一 ******/

/**
 * 参数1：当前引用检验者的this对象
 * 参数2：请求后台接口获取检验规则的地址
 * 参数3：请求后台所携带的参数
 * 参数4：对应表单对象的校验规则变量名
 * 参数5：对应校验表单的实体类对象
 * 参数6：唯一性校验请求后台时所携带主键名称。
 */
validator.getRulerData(this,'/rules',{name:'1'},"rules",this.form, "id")

/****** 用法二 ******/
/**
 * 参数1：当前引用检验者的this对象
 * 参数2：获取检验规则数据
 * 参数3：对应表单对象的校验规则变量名
 * 参数4：对应校验表单的实体类对象
 * 参数5：唯一性校验请求后台时所携带主键名称。
 */
 let rules = [
             	{
             		"validateField":{
             			"code":"email",
             			"name":"邮箱",
             			...
             		},
             		"validateRules":[
             			{
             				"id":"1",
             				"fieldId":"1",
             				"sceId":"1",
             				"valType":1,
             				"valField":"string",
             				"mainFeildId":"1",
             				"valRule":"",
             				"valErrorMessage":"不能为空",
             				....
             			}
             		]
             	}
             ]
this.rules = validator.outputRulers(this, rules, "rules",this.form, "id")

/****** 保存表单时获取校验是否通过 ******/
/**
 * 手动触发校验方法
 */
submitForm(formName) {
  this.$refs[formName].validate((valid) => {
    if (valid) {
      alert('校验通过！');
    } else {
      alert('校验未通过！');
      return false;
    }
  });
},
```
## 注意事项

```html
1、当**日期控件**使用校验时，如果设置了日期控件**返回格式**value-format="yyyy-MM-dd HH:mm:ss"，则返回数据则是**string类型**（**默认为date类型**），在配置对应**校验规则类型**时需要将类型设置为**string类型**。
```

```html
2、在改变文本框显示内容时（居左fros-form-item-left、居中fros-form-item-center、居右fros-form-item-right）
   需要设置给<el-form-item> 标签添加 class='fros-form-item-center'
```




## Form 属性说明

|参数|说明|类型|可选值|默认值|
|-|-|-|-|-|
|model|表单数据对象|Object|-|-|
|rules|表单验证规则|Object|-|-|
|hide-required-asterisk|是否显示必填字段的标签旁边的红色星号|boolean|-|false|
|show-message|是否显示校验错误信息|boolean|-|true|
|inline-message|是否以行内形式展示校验信息|boolean|-|false|
|currentPage|当前页数|number|-|-|
|status-icon|是否在输入框中显示校验结果反馈图标|是否在输入框中显示校验结果反馈图标|—|false|
|validate-on-rule-change|是否在 rules 属性改变后立即触发一次验证|boolean|-|true|

## Form 方法说明

|方法名|说明|参数|
|-|-|-|
|validate|对整个表单进行校验的方法，参数为一个回调函数。该回调函数会在校验结束后被调用，并传入两个参数：是否校验成功和未通过校验的字段。若不传入回调函数，则会返回一个 promise|Function(callback: Function(boolean, object))|
|validateField|对部分表单字段进行校验的方法|Function(props: array | string, callback: Function(errorMessage: string))|
|resetFields|对整个表单进行重置，将所有字段值重置为初始值并移除校验结果|-|
|clearValidate|移除表单项的校验结果。传入待移除的表单项的 prop 属性或者 prop 组成的数组，如不传则移除整个表单的校验结果|Function(props: array | string)|

## Form 事件说明

|事件名称|说明|回调参数|
|-|-|-|
|Form 事件说明|任一表单项被校验后触发|任一表单项被校验后触发|

## Form-Item属性说明

|参数|说明|类型|可选值|默认值|
|-|-|-|-|-|
|prop|表单域 model 字段，在使用 validate、resetFields 方法的情况下，该属性是必填的|string|string|-|


## 场景解析
1.	非空检验（valType=1）

    场景：当前字段不可为空，例如：用户名不能为空。

    生成校验规则：
```json
{
  "name": [
    {
      "required": true,
      "type": "string",
      "message": "不能为空",
      "trigger": "change"
    }
  ]
}
```
2.	字段长度最小值校验（valType=2）

    场景：当前字段输入值最小长度为大于N，例如：用户名长度不能小于2。

    生成校验规则：
```json
{
  "name": [
    {
      "min": 2,
      "message": "字符串长度不能低于最小值2",
      "trigger": "change"
    }
  ]
}
```
 3.	字段长度最大值校验（valType=3）

    场景：当前字段输入值最大长度为小于N，例如：用户名长度不能超多20。

    生成校验规则：
```json
{
  "name": [
    {
      "max": 20,
      "message": "字符串长度不能超过最大值20",
      "trigger": "change"
    }
  ]
}
```

4.	正则表达式校验（valType=4）

    场景：当前值内容需要匹配某规则，例如：邮箱。

    生成校验规则：
```json
{
  "email": [
    {
      "pattern": "\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}",
      "message": "邮箱格式不正确",
      "trigger": "change"
    }
  ]
}
```
5.	单字段唯一性检验（valType=5）

    场景：某字段需要保证在数据库里保证唯一，例如：运单号。

    生成校验规则：
```json
{
  "orgNo": [
    {
      "validator": (rule, value, callback) => {
        ... // 回调方法，单字段值请求后台校验逻辑
      },
      "trigger": "change"
    }
  ]
}
```
6.	组合字段唯一性校验（valType=6）

    场景：某几个字段需要保证唯一，例如：船名和航次。

    生成校验规则：
```json
{
  "orgNo": [
    {
      "validator": (rule, value, callback) => {
        ... // 回调方法，多字段值请求后台校验逻辑
      },
      "trigger": "change"
    }
  ]
}
```
7.	组合内值相等校验（valType=7）

    场景：当前组内值需要保持一致时，例如：密码和确认密码。

    生成校验规则：
```json
{
  "password": [
    {
      "validator": (rule, value, callback) => {
        // 组内值相等
        for (let i = 0; i < codeList.length; i++) {
          if (value !== model[codeList[i]]) {
            callback(valrule.valErrorMessage)
            return
          } else {
            callback()
          }
        }
      },
      "trigger": "change"
    }
  ]
}
```
 8.	组内值至少N个不为空校验（valType=8）

    场景：组内字段必须保证N个值不能为空，例如：单号、手机号、客户名称必须保证其中任意两个不能为空。

    生成校验规则：
```json
{
  "password": [
    {
      "validator": (rule, value, callback) => {
        // 组内值相等
        for (let i = 0; i < codeList.length; i++) {
          if (value !== model[codeList[i]]) {
            callback(valrule.valErrorMessage)
            return
          } else {
            callback()
          }
        }
      },
      "trigger": "change"
    }
  ]
}
```
9.	动态绑定校验规则（valType=9）

    场景：组内字段必须保证N个值不能为空，例如：单号、手机号、客户名称必须保证其中任意两个不能为空。

    生成校验规则：
```json
{
  "whNo": [
    {
      "validator": (rule, value, callback) => {
        // 动态给指定字段设置校验规则
        if (......) {
          callback(valrule.valErrorMessage)
          return
        } else {
          callback()
        }
      },
      "trigger": "change"
    }
  ]
}
```
