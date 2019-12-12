# 自定义表单组件使用说明

```html
<fros-dynamic-filter-cond
        :frosComponentCode="frosComponentCode"
        :frosDfcd="frosDfcd"
        :conditionLists="conditionLists"
        :domains="domains"
        :defShowForm="defShowForm"
        @frosDynamicFilterCondQuery="frosSearch"
        @frosDynamicFilterCondSaveAs="frosSaveAs"
        @frosDynamicFilterCondEditSave="frosEditSave"
        @frosDynamicFilterCondDelete="frosDelete"
        @frosDynamicFilterCondDefShowForm="frosDefShowForm">
</fros-dynamic-filter-cond>
```
## 属性说明

|参数|说明|类型|可选值|默认值|
|-|-|-|-|-|
|frosComponentCode|组件唯一code值，用作保存自定义布局|string|-|-|
|frosDfcd|用于存储模板、以及默认渲染表单|string|-|-|
|conditionLists|条件配置项数据|array|-|-|
|domains|动态展示项配置|array|-|-|
|defShowForm|默认显示项|array|-|-|
|placeholder|输入框占位文本|string|-|-|

## frosDfcd 属性说明

|参数|说明|类型|可选值|默认值|
|-|-|-|-|-|
|defModel|模板存储对象|array|-|-|
|showItem|默认Form表单渲染项|array|-|-|

## conditionLists 属性说明

|参数|说明|类型|可选值|默认值|
|-|-|-|-|-|
|prop|绑定字段名|string|-|-|
|label|表单展示值|string|-|-|

## domains 属性说明

|参数|说明|类型|可选值|默认值|
|-|-|-|-|-|
|prop|绑定字段名|string|-|-|
|label|表单展示值|string|-|-|
|type|组件类型|string| input/radio/checkbox/fros-date/fros-date-time/fros-select/fros-multiple-select/fros-auto-complete/fros-base-input/fros-num-input |-|
|inputType|input类型，当type类型为input时生效|string|-|-|
|value|初始化值|string/Date/array/number|-|-|
|options|可选择配置项，当type类型为radio/fros-select/fros-multiple-select时生效/checkbox|array|-|-|
|serviceList|联想控件配置项，可参考联想控件配置项|object|-|-|
|frostrim|清除空格位置，type类型为fros-base-input/fros-num-input时生效|string|ltrim(左)/rtrim(右)/lrtrim(左右)/trim(全部)|-|
|defValue|缺省字段（未启用）|string|-|-|
|isDict|是否翻译，type类型为fros-select/fros-multiple-select时生效|boolean|-|false|
|isLocalStorage|是否启用本地缓存字典，type类型为fros-select/fros-multiple-select时生效|boolean|-|false|
|dictType|数据字典根据哪一列翻译，type类型为fros-select/fros-multiple-select时生效|string|-|-|
|dictData|数据字典，type类型为fros-select/fros-multiple-select时生效|array|-|-|

## 事件

|事件名称|说明|回调参数|
|-|-|-|
|frosDynamicFilterCondQuery|点击查询按钮触发方法|-|
|frosDynamicFilterCondSaveAs|条件另存为触发方法|-|
|frosDynamicFilterCondEditSave|编辑模板触发方法|-|
|frosDynamicFilterCondDelete|删除模板触发方法|-|
|frosDynamicFilterCondDefShowForm|保存条件配置项触发方法|-|

**注：具体使用可参考自定义表单页面。**
