# FrosDatePicker使用方法

FrosUI内部提供了日期选择器组件，可自定义默认日期和日期返回格式。

```html
<fros-date-picker
    class="fros-form-item-center"
	ref="datePicker"
	v-model="date1"
	:frosDefValue="value3"
	type="date"
	placeholder="请选择日期"
	size="large"
	:editable=true
	:disabled=false
	:readonly=false
	@focus="fetFocus"
	@blur="getBlur">
</fros-date-picker>

import FrosDatePicker from '@/components/fros-date-picker/FrosDatePicker'

export default {
    components: {
        FrosDatePicker
    }
}
```
## 注意事项
1、在改变文本框显示内容时（居左fros-form-item-left、居中fros-form-item-center、居右fros-form-item-right）
```html
   需要给组件添加class='fros-form-item-center'
```

## 属性

|参数|说明|类型|可选值|默认值|
|-|-|-|-|-|
|type|显示类型| string |date/datetime| date |
|frosDefValue|设置默认时间| Date |-|-|
|placeholder|提示信息|string|-|-|
|size|输入框尺寸|string|large, small, mini|-|
|editable|文本框可输入|boolean|-|-|
|disabled|禁用|boolean|-|false|
|readonly|完全只读|boolean|-|false|
|format|显示在输入框中的格式|string|-|-|
|value-format|返回数据格式|string|-|-|

## 方法

|方法名|说明|参数|
|-|-|-|
|focus|input 获取焦点| - |
|blur|input 失去焦点| - |
