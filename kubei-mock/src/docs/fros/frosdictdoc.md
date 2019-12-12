# Fros数据字典使用说明

数据字典在FrosVue框架中是以访问工程时便加载字典数据数据并存储，以便后续页面直接使用字典数据。

```html
<!-- FrosSelect组件用法，详情参考FrosSelect文档 -->
<fros-select
    :options="options1"
    :dictType="dictType"
    :isDict="isDict"
    :dictData="dictData"
    :isLocalStorage=true
    :frosDefValue="defval2"
    placeholder="普通单选"
    @getSelectValue="getSelectValue">
</fros-select>

<!-- FrosTable组件可编辑表格表头配置用法，详情参考FrosTable文档 -->
{
    "width": 200,
    "label": "标签",
    "prop": "province",
    "align": "left",
    "headerAlign": "center",
    "fixed": false,
    "sortable": false,
    "editType": {
        "label": "select",
        "multiple": false,
        "isDict": true,
        "isLocalStorage": true,     // 是否根据本地缓存字典数据翻译
        "dictType": "province",
        "dictData": [
            ......
        ],
        "selectOption": [
            ......
        ]
    }
}
```


## 配置以及使用说明

### 配置说明

相关配置文件：config.js、apiUrl.js、root.js

1、config.js

    配置文件描述：
        工程全局配置文件，在该配置文件中可以配置工程最基础的配置项，例如：是否使用mock、是否开启面包屑、以及apiHost等等。
    apiHost是用于配置工程封装组件统一请求接口的路径域名/ip:port，数据字典请求后台接口可以使用改地址前缀或者不用。

```html
apiHost: apiConfig.apiHost || '',
```
2、apiUrl.js

    配置文件描述：
        用于配置框架封装组件（数据字典就是其中一种）请求后台接口的全路径地址，在该配置文件中可以配合config.js中的apiHost进行使用。

```html
// 数据字典
  getFrosDictionary: apiHost + '/getFrosDictionary',
```

3、root.js

    配置文件描述：

        用于处理对应组件返回数据的一些逻辑，例如数据字典接口返回数据存储位置以及其他的一些逻辑等

```html
    // 数据字典
    getFrosDictionary: function () {
      let self = this
      api.getFrosDictionary({
        data: {},
        success: function (res) {
          self.$store.commit('setFrosDictionary', res.data.data)
            // 写入本地缓存
            localStorage.setItem('FrosDictionary', JSON.stringify(res.data.data))
        }
      })
    }
```

### 使用说明

1、FrosSelect组件，详情参考FrosSelect文档

说明：
通过isLocalStorage属性来控制是否通过本地缓存数据进行翻译。

```html
    <!-- FrosSelect组件用法 -->
    <fros-select
        :options="options1"
        :dictType="dictType"
        :isDict="isDict"
        :dictData="dictData"
        :isLocalStorage=true
        :frosDefValue="defval2"
        placeholder="普通单选"
        @getSelectValue="getSelectValue">
    </fros-select>
```

2、FrosTable组件

说明：
在可编辑表格中使用FrosSelect组件进行数据字典翻译时，需要在表头配置项editType中新增isLocalStorage属性来控制是否通过本地缓存数据进行翻译。

```html
    <!-- FrosTable组件可编辑表格用法 -->
    {
        "width": 200,
        "label": "标签",
        "prop": "province",
        "align": "left",
        "headerAlign": "center",
        "fixed": false,
        "sortable": false,
        "editType": {
            "label": "select",
            "multiple": false,
            "isDict": true,
            "isLocalStorage": true,     // 是否根据本地缓存字典数据翻译
            "dictType": "province",
            "dictData": [
                ......
            ],
            "selectOption": [
                ......
            ]
        }
    }
```

**注意：数据字典翻译执行优先级，如果同时配置需要翻译、指定本地缓存翻译、以及单页面数据字典（dictData:[]），则优先级为dictData > isLocalStorage > 默认本地数据翻译。**
