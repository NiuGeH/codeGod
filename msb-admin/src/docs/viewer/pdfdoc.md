## 如何使用pdf在线预览

目前pdf在线预览需要使用到的是基于pdfjs的开发的内容，在static文件加中有一个pdf的文件夹用于存储pdf的viewer页面，用户在使用的时候可以直接

```html
http://localhost:8081/static/pdf/viewer.html?file=你的pdf路径
```

这样就可以在浏览器中浏览这个pdf了，如果是需要在管理平台中新开一个标签也打开一个pdf页面的话，则可以调用newpage的方法

```javascript
let self = this
self.$newpage({
  path: 'http://localhost:8081/static/pdf/web/viewer.html?file=/static/testfile/tableau.pdf',
  title: '人人都是数据分析师'
})
```

#### 注意事项

<font color=#F5606A>1、如果不需要pdf的功能，可以在打包配置文件中，将config/index.js中的pdf的used改为false</font>

```javascript
module.exports = {
  externals: {
    pdf: {
      used: false,
      url: ['/' + moduleName + '/static/js/pdf.js', '/' + moduleName + '/static/js/pdf.worker.js']
    }
  }
}
```
