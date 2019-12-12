# FrosSelect使用方法

FrosUI内部提供了下拉框组件，支持数据字典翻译。

```html
<fros-select
    class='fros-form-item-center'
	multiple="multiple"
	clearable="clearable"
	filterable ="filterable"
	placeholder="请选择"
	:options="options2"
	:disabled=true
	:frosDefValue="defval"
	:frosBlurShow=true
	:frosEnterShow=true
	:sortable="sortable"
	dictType="province"
	:isDict=false
	:dictData="dictData"
	:isLocalStorage=true
	:frosCode=true
	:frosCase=true
	:tooltipShow=true
	@focus="getFocus"
	@blur="getBlur"
	@getSelectValue="getSelectValue"
	@frosSelectClear="frosSelectClear">
</fros-select>

import FrosSelect from '@/components/fros-select/FrosSelect'

export default {
    components: {
        FrosSelect
    },
    data() {
        return {
            // 数据字典获取数据
            dictData: [
                {
                    'value': '1',
                    'key': '苹果'
                }, {
                    'value': '2',
                    'key': '香蕉'
                }, {
                    'value': '3',
                    'key': '山竹'
                }
            ],
            options: [
                {
                    value: 'A',
                    key: '黄金糕'
                }, {
                    value: 'B',
                    key: '双皮奶'
                }, {
                    value: 'C',
                    key: '蚵仔煎'
                }
            ]
            sortable: {
                orderby: 'value',
                type: 'ASC'  // 升序ASC或者降序DESC
            }
        }
    },
    methods: {
        getSelectValue: function (val) {
            console.log("我是选择值" + val)
        },
        frosSelectClear: function () {
            console.log("clear触发事件。")
        }
    }
}
```


## 注意事项
1、数据字典翻译执行优先级，如果同时配置需要翻译、指定本地缓存翻译、以及单页面数据字典（dictData:[]），则优先级为dictData > isLocalStorage > 默认本地数据翻译。

2、在使用tooltipShow属性时候，只针对下拉多选情况使用，单选没有该属性。

3、在改变文本框显示内容时（居左fros-form-item-left、居中fros-form-item-center、居右fros-form-item-right）
```html
   需要给组件添加class='fros-form-item-center'
```

## 属性

|参数|说明|类型|可选值|默认值|
|-|-|-|-|-|
|options|select下拉列表选项|array|-|-|
|frosDefValue|设置默认值|string/array|-|-|
|sortable|排序参数对象，type（升序/降序）、value根据什么字段排序|object|-|-|
|placeholder|提示信息|string|-|-|
|multiple|支持多选|string(后期将优化为boolean)|multiple|-|
|clearable|是否支持清空，只支持单选状态下清空|string(后期将优化为boolean)|clearable|-|
|filterable|是否支持可搜索|string(后期将优化为boolean)|filterable|-|
|isDict|是否翻译|boolean|-|false|
|isLocalStorage|是否启用本地缓存字典|boolean|-|false|
|dictType|数据字典根据哪一列翻译|string|-|-|
|dictData|数据字典|array|-|-|
|disabled|禁用|boolean|-|false|
|frosBlurShow|失去焦点回显第一条数据|boolean|-|false|
|frosEnterShow|回车回显第一条数据|boolean|-|false|
|collapseTags|多选时是否将选中值按文字的形式展示|boolean|-|false|
|frosCode|全半角转换开关|boolean|-|-|
|frosCase|大小写转换|boolean|-|-|
|tooltipShow|是否显示提示框|boolean|-|-|

## 方法

|方法名|说明|参数|
|-|-|-|
|focus|input 获取焦点| - |
|blur|input 失去焦点| - |
|getSelectValue|获取选中数据| Function(val){}|


