# FrosBaseInput使用方法

FrosUI内部提供了基础输入框组件，支持大小写转换、全角转半角、清除空格等功能。

```html
<fros-base-input
        class="fros-form-item-center"
        ref="baseInput"
        placeholder="请输入"
        length="5"
        frosDefValue=""
        type="textarea"
        rows="2"
        v-model="value"
        :disabled="disabled"
        :readonly="readonly"
        :frostrim="frostrim"
        :frosInputTooltip=true
        :frosCode=true
        :frosCase=true
        :onlyNum=false
        :letter=false
        :letterAndNum=true
        :showWordLimit=true
        @change="change"
        @focus="focus"
        @blur="blur" >
</fros-base-input>
export default {
    methods: {
        // 清空文本框
        clearDate(){
            utils.clearFrosComponentValue(this,['baseInput'])
        },
        // 文本
        change(val){
          console.log(val)
        },
        // 获取焦点事件
        focus(){
            console.log('获取焦点')
        },
        // 失去焦点事件
        blur(){
            console.log('失去焦点')
        }
    }
}

```


## 注意事项
1、如果不限制输入的内容

```html
    需要把（onlyNum，letter，letterAndNum）这些属性都设置为false

```
2、在使用length属性时候

```html
   如果有长度限制需要设置值length='99',
   如果对长度没有限制，不写length属性就可以
   如果设置的length是变量时候 需要写成 :lenght='num'
```

3、在改变文本框显示内容时（居左fros-form-item-left、居中fros-form-item-center、居右fros-form-item-right）

```html
   需要给组件添加class='fros-form-item-center'
```
## 属性

|参数|说明|类型|可选值|默认值|
|-|-|-|-|-|
|placeholder|提示信息|string|-|-|
|frosDefValue|默认值|string|-|-|
|length|长度|number|-|-|
|type|文本框类型 text(普通文本框)、 textarea(文本域)|string|-|-|
|rows|文本域行数|string|-|-|
|disabled|是否禁用|boolean|-|-|
|readonly|是否只读|boolean|-|-|
|frostrim|清除空格位置|string| ltrim(左),rtrim(右),lrtrim(左右) ,trim(全部)|-|
|frosCode|全半角转换开关|boolean|-|-|
|frosCase|大小写转换|boolean|-|-|
|frosInputTooltip|是否有提示框|boolean|-|-|
|onlyNum|仅数字|boolean|-|-|
|letter|仅字母|boolean|-|-|
|letterAndNum|数字和字母|boolean|-|-|
|showWordLimit|是否显示输入字数统计，只在 type = "text" 或 type = "textarea" 时有效|boolean|-|false|


## 事件

|事件名称|说明|回调参数|
|-|-|-|
|clearDate|清空文本框值|--|
|change|文本框值改变时触发|val|
|focus|获取焦点事件|-|
|blur|失去焦点事件|-|
