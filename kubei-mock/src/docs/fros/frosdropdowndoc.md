# FrosDropdown使用方法

FrosDropdown将动作或菜单折叠到下拉菜单中。

```html
<fros-dropdown
        :type="type"
        :size="size"
        :splitButton="splitButton"
        :placement="placement"
        :trigger="trigger"
        :hideOnClick="hideOnClick"
        :showTimeout="showTimeout"
        :hideTimeout="hideTimeout"
        :tabindex="tabIndex"
        :dpconfig="dpconfig"
        @itemClick="handleClick"
        @mainClick="handleClickMain">
</fros-dropdown>

// 配置属性说明
data() {
    return {
        dpconfig: {
            icon:'el-icon-aim', // 主按钮icon
            mainTitle: '下拉按钮', // 主按钮标题
            mainMethod: 'mainClick', // 主按钮触发事件名称
            lists: [
                {
                    itemTitle: '跳转链接',  // 子按钮标题
                    itemMethod: 'itemLink1',   // 子按钮触发方法名
                    disabled: false,    // 子按钮是否禁用
                    divided: false, // 子按钮是否显示分割线
                    icon: 'el-icon-aim' // 子按钮图标类名
                },
                {
                    itemTitle: '禁用项',
                    itemMethod: 'itemLink2',
                    disabled: true,
                    icon: 'el-icon-close'
                }
            ]
        },
        type: 'primary',    // 菜单按钮类型，同 Button 组件(只在split-button为 true 的情况下有效)
        size: 'medium', //菜单尺寸(medium / small / mini)，在split-button为 true 的情况下也对触发按钮生效
        splitButton: true,  // 下拉触发元素是否呈现为按钮组
        placement: 'bottom-end',    // 弹出位置 top/top-start/top-end/bottom/bottom-start/bottom-end
        trigger: 'click',   // 触发下拉的行为hover, click
        hideOnClick: true,  // 是否在点击菜单项后隐藏菜单
        showTimeout: 250,   // 展开下拉菜单的延时（毫秒值，仅在 trigger 为 hover 时有效）
        hideTimeout: 150,   // 收起下拉菜单的延时（毫秒值，仅在 trigger 为 hover 时有效）
        tabIndex: 0,
    }
}
```

## FrosDropdown属性

|参数|说明|类型|可选值|默认值|
|-|-|-|-|-|
|type|菜单按钮类型，同 Button 组件(只在split-button为 true 的情况下有效)|string|-|-|
|size|菜单尺寸，在split-button为 true 的情况下也对触发按钮生效|string|medium / small / mini|-|
|split-button|下拉触发元素呈现为按钮组|boolean|-|false|
|placement|菜单弹出位置|string|top/top-start/top-end/bottom/bottom-start/bottom-end|bottom-end|
|trigger|触发下拉的行为|string|hover, click	|hover|
|hideOnClick|是否在点击菜单项后隐藏菜单|boolean|-|true|
|showTimeout|展开下拉菜单的延时（仅在 trigger 为 hover 时有效）|number|-|250|
|hideTimeout|收起下拉菜单的延时（仅在 trigger 为 hover 时有效）|number|-|150|
|tabindex|Dropdown 组件的 tabindex|number|-|0|

## FrosDropdown事件

|事件名称|说明|回调参数|
|-|-|-|
|click|split-button 为 true 时，点击左侧按钮的回调|-|
|command|点击菜单项触发的事件回调|dropdown-item 的指令|
|visible-change|下拉框出现/隐藏时触发|出现则为 true，隐藏则为 false|


## FrosDropdown Item属性

|参数|说明|类型|可选值|默认值|
|-|-|-|-|-|
|mainTitle|主按钮标题|string|-|-|
|mainMethod|主按钮触发方法名|string|-|-|
|itemTitle|Item标题|string|-|-|
|itemMethod|Item触发方法名|string|-|-|
|disabled|禁用|boolean|-|false|
|divided|显示分割线	|boolean|-|false|
|icon|图标类名|string|-|-|
