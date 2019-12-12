## 如何使用数据字典

FrosUI默认在assets/js中提供了一个dict.js的js模块，由于各项目组是实际开发项目中经常会使用到类似数据字典的功能，但是在维护查询字段上经常是哪里用就哪里写，导致了字典字段的修改成本很高，因此FrosUI在设计的时候会于开发者有些约定，以保证降低修改成本，所以在使用数据字典之前，需要在dict中维护，使用的时候再通过引入dict.js调用对应的字段。

```javascript
// 数据字典类型代码维护

let dict = {
  baseCode: {
    name: '码头代码',
    value: 'BASE_CODE'
  }
}

export default dict
```


```javascript
// 调用
console.log(dict.baseCode.value)
```
