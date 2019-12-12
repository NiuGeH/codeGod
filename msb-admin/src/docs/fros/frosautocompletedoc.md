# FrosAutoComplete使用方法

FrosUI内部提供了联想控件组件，支持联动效果和校验，可以在props中配置属性信息。

```html
<fros-auto-complete
    class="fros-form-item-center"
    ref="frosAutoCompleteRef"
	v-model="form.name"
	:frosDefValue="serviceList.value"
	:props="serviceList"
	@rowData="getRowData"
	@sync="syncService"
	@blur="emitBlur"
	@focus="emitFocus"
	@change="changeValue"
	@appendQueryFields="appendQueryFields">
</fros-auto-complete>
```


## 注意事项
1、在改变文本框显示内容时（居左fros-form-item-left、居中fros-form-item-center、居右fros-form-item-right）
```html
   需要给组件添加class='fros-form-item-center'
```

## 属性

|参数|说明|类型|可选值|默认值|
|-|-|-|-|-|
|id|给input框添加唯一标识|string|-|-|
|placeholder|提示信息|string|-|-|
|frostrim|清除空格位置|string| ltrim(左),rtrim(右),lrtrim(左右) ,trim(全部)|-|
|columnAuto|是否自适应列宽|boolean|-|-|
|pageSizes|控制每页显示条数|array|-|-|
|pageSize|当前每页显示条数|number|-|-|
|currentPage|当前页数|number|-|-|
|layout|组件布局，子组件名用逗号分隔|string|sizes, prev, pager, next, jumper, ->, total, slot|'prev, pager, next, jumper, ->, total'|
|frosCode|全半角转换开关|boolean|-|-|
|frosCase|大小写转换|boolean|-|-|
|multiple|是否多选|boolean|-|-|
|dblclick|支持双击（单击无效）|boolean|-|-|
|StrongCheck|是否支持强校验|boolean|-|-|
|showColumn|配置显示列|array|-|-|
|queryFields|配置可模糊查询列|array|-|-|
|inputKey|显示填充字段值设置|string|-|-|
|Linkage|设置联动关联控件|string|-|-|
|url|后台接口地址|string|-|-|
|disabled|禁用|boolean|-|false|
|orderBy|用于后台解析排序字段，后续版本会废弃|string|-|-|


## 事件

|事件名称|说明|回调参数|
|-|-|-|
|rowData|获取数据|-|
|sync|跟智能输入框同步选中的值 （监听input的值）|-|
|focus|获取焦点时触发| - |
|blur|失去焦点时触发| - |
|change|值改变时触发| - |
|appendQueryFields|请求后台接口前触发| - |


## 属性配置实例
```html
serviceList: {
    frostrim:'ltrim',
    frosCase:true, //小写转换大写开关
    frosCode:true, //全半角转换开关
    url: '/feeder/opServiceLine/querySettleOfficeTest', //后台请求接口
    //分页属性配置
    pgconfig: {
        pageSizes: [10, 20, 30], // 控制每页显示条数
        pageSize: 10, // 当前每页显示条数
        currentPage: 0, // 当前页数
        // layout:'total, sizes, prev, pager, next, jumper',
        layout: 'total, prev, pager, next, jumper',
    },
    dblclick: true, // 是否双击显示联想控件
    DefaultequestData: true, // 是否默认查询数据
    list: [],
    multiple: false, // 是否多选
    orderBy: 'c_001 desc',    // 后台排序配置项，后续版本废弃
    separator: ";",//自定义分隔符,多选时使用
    //显示列配置
    showColumn: [
        {
            label: '结算公司名称',
            prop: 'settleOfficeName'
        },
        {
            label: '结算公司代码',
            prop: 'settleOffice'
        }
    ],
    //前置条件配置
    queryFields: [
        {
            "fieldName": "type",
            "fieldType": "String",
            "operator": "=",
            "fieldValue": '2',
            "junction": "and"
        }
    ],
    queryType: "SettleOfficeModel", //查询的boName
    StrongCheck: false, //是否支持强校验
    id: "serviceInput", //给input添加标识
    inputKey: "settleOfficeName" //input显示填充字段值设置
},

```

## 触发方法实例
```html

// 失去焦点触发方法
emitBlur(val) {
    console.log("失去焦点:" + val)
},

// 获取焦点
emitFocus(val) {
    console.log("获取焦点:" + val)
},

// 获取行触发事件
getRowData(row) {
    console.log(row)
},

// 执行动态修改查询条件,参数为list
appendQueryFields() {
    //
    this.$refs["frosAutoCompleteRef"].appendQueryFields(this.addQueryField)
},

```
