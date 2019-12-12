## Codemirror编辑器

> 编辑器配置

FrosUI的代码编辑器是基于vue-codemirror，所以具体的配置和使用方法均可以参照文档。

<a href="https://github.com/surmon-china/vue-codemirror" target="_blank">vue-codemirror github文档</a>

<a href="https://codemirror.net/" target="_blank">codemirror编辑器官方文档</a>

>代码示例

```vue
<template>
  <div class="cp-page">
    <div class="com-dialog-s-title">javascript</div>
    <codemirror v-model="javascriptEditor.data" :options="javascriptEditor.options"></codemirror>
    <div class="com-dialog-s-title">python</div>
    <codemirror v-model="pythonEditor.data" :options="pythonEditor.options"></codemirror>
    <div class="com-dialog-s-title">golang</div>
    <codemirror v-model="golangEditor.data" :options="golangEditor.options"></codemirror>
    <div class="com-dialog-s-title">json</div>
    <codemirror v-model="jsonEditor.data" :options="jsonEditor.options"></codemirror>
  </div>
</template>

<script>
import Base from '@/assets/js/base'

// 导入必要的样式和依赖
import 'codemirror/addon/hint/javascript-hint.js'
import 'codemirror/lib/codemirror.css'
import 'codemirror/addon/hint/show-hint.css'
import 'codemirror/addon/hint/show-hint.js'

// 导入模式文件
import 'codemirror/mode/javascript/javascript.js'
import 'codemirror/mode/go/go'
import 'codemirror/mode/python/python'

export default {
  name: "CodeMirror",
  extends: Base,
  data () {
    return {
      javascriptEditor: {
        data: '',
        options: {
          tabSize: 4,
          mode: 'text/javascript',
          lineNumbers: true,
          line: true
        }
      },
      pythonEditor: {
        data: '',
        options: {
          tabSize: 4,
          mode: 'text/x-python',
          lineNumbers: true,
          line: true
        }
      },
      golangEditor: {
        data: '',
        options: {
          tabSize: 4,
          mode: 'text/x-go',
          lineNumbers: true,
          line: true
        }
      },
      jsonEditor: {
        data: '',
        options: {
          tabSize: 4,
          mode: 'application/json',
          lineNumbers: true,
          line: true
        }
      }
    }
  },
  mounted: function () {
    let self = this
    self.init()
  },
  methods: {
    init: function () {
      // 初始化各种语言内容
      let self = this
      // json
      self.jsonEditor.data = JSON.stringify({name: 'FROS-UI', value: 'Hello, FrosUI'}, null, 4)
      // golang
      self.golangEditor.data = "package main\n" +
        "\n" +
        "import \"fmt\"\n" +
        "\n" +
        "func sayHello (str string) string {\n" +
        "\treturn \"Hello, \" + str\n" +
        "}\n" +
        "\n" +
        "func main () {\n" +
        "\t// 打印Hello, FrosUI\n" +
        "\tfmt.Println(sayHello(\"FrosUI\"))\n" +
        "}\n"
      // python
      self.pythonEditor.data = "def sayHello (str):\n  \t# print something\n\tprint(str)\n\nsayHello('Hello, FrosUI')"
      // js
      self.javascriptEditor.data = "function sayHello () {\n" +
        "\tconsole.log('Hello, FrosUI');\n" +
        "}\n" +
        "\n" +
        "sayHello();\n"
    }
  }
}
</script>

<style scoped>
  .cp-page{
    background-color: #fff;
    padding: 0 24px 24px 24px;
    display: flex;
    flex-direction: column;
    flex: 1;
    overflow: auto;
  }
</style>
```
