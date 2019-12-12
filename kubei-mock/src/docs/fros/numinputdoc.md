# FrosNumInput使用方法

FrosUI内部提供了数字输入框组件，支持数字计算和校验。

```html
<fros-num-input
        class='fros-form-item-center'
        ref='numInput'
        v-model.number="value"
        placeholder="请输入"
        frosDefValue="0"
        length="10"
        :frosInputTooltip=true
        :disabled="false"
        :readonly="false"
        :frosCase="false"
        :frosCode="false"
        :frostrim="trim"
        :onlyNum=false
        :frosInterger=false
        :frosComma=false
        :frosNegative="false"
        :frosRounding="true"
        :frosPrecision="2"
        :frosOperation="true"
        @change="change"
        @focus="focus"
        @blur="blur">
</fros-num-input>
export default {
    methods: {
        // 清空文本框
        clearDate(){
            utils.clearFrosComponentValue(this,['numInput'])
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
```html
1、配置时候注意：frosOperation、frosNegative、onlyNum三个必须保证其中一个为true
```
```html
2、当frosOperation配置为true时候，frosNegative、onlyNum要配置为false
```
```html
3、当不需要保留小数位，但是需要逗号隔开时候，需要frosRounding配置为true，frosPrecision='0'
```
```html
4、在使用length属性时候
   如果有长度限制需要设置值length='99',
   如果对长度没有限制，不写length属性就可以
   如果设置的length是变量时候 需要写成 :lenght='num'
```
```html
5、当要输入正整数时候，需要设置onlyNum=true和frosInterger=true
```
```html
6、当要输入小数时候(没有逗号分隔)，需要设置frosComma=false和frosRounding=true和frosPrecision="3"
```
```html
7、数字文本框中初配置使用逗号(，)分隔的类型外，其他类型绑定model时都需要使用v-model.number的形式绑定数据。
```

```html
8、在改变文本框显示内容时（居左fros-form-item-left、居中fros-form-item-center、居右fros-form-item-right）
   需要给组件添加class='fros-form-item-center'
```

## 属性

|参数|说明|类型|可选值|默认值|
|-|-|-|-|-|
|placeholder|提示信息|string|-|-|
|frosDefValue|默认值|number|-|-|
|disabled|是否禁用|boolean|-|-|
|readonly|是否只读|boolean|-|-|
|length|最大长度|number|-|-|
|frosCase|小写转大写|boolean|-|-|
|frosCode|全角转半角|boolean|-|-|
|frostrim|清除空格位置|string| ltrim(左),rtrim(右),lrtrim(左右) ,trim(全部)|-|
|frosNegative|不能输入负数|boolean|-|-|
|frosRounding|四舍五入|boolean|-|-|
|frosPrecision|小数位数设置|number|-|-|
|frosOperation|是否进行运算|boolean|-|-|
|frosInputTooltip|是否加提示框|boolean|-|-|
|onlyNum|只能输入数字|boolean|-|-|
|frosInterger|是否输入正整数|boolean|-|-|
|frosComma|是否开启逗号分隔|boolean|-|-|

## 事件

|事件名称|说明|回调参数|
|-|-|-|
|clearDate|清空文本框值|--|
|change|文本框值改变时触发|val|
|focus|获取焦点事件|-|
|blur|失去焦点事件|-|
