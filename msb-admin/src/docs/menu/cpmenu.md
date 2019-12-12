## 页面组件

FROS-UI采用的标签页实现方式是基于vue的动态组件功能实现的，一个最简的页面代码如下所示：

```html
<template>
</template>

<script>
import Base from '@/assets/js/base'

export default {
  name: 'ComponentName',
  extends: Base,
  data () {
    return {}
  },
  methods: {
  }
}
</script>

<style scoped lang="scss">
</style>

```

其中，继承的Base是所有页面组件都必须继承的内容，里面包含了统一的loading以及一些键盘事件的监听。除此之外需要注意的是，组件的文件名要与组件的name属性一致，否则框架的缓存策略不会对其进行缓存。

除了通过菜单新增一个标签页，还可以通过框架提前在vue实例中封装好的newpage方法进行创建，使用代码如下：

```html
<script>
import Base from '@/assets/js/base'

export default {
  name: 'CpMenu',
  extends: Base,
  data () {
    return {}
  },
  methods: {
    newCpMenuDoc: function () {
      let self = this
      // 这里通过实例的newpage方法创建了一个标签页
      self.$newpage({
        path: 'cpmenudoc/CpMenuDoc',
        title: '页面组件',
        params: {}
      })
    }
  }
}
</script>
```

其中，path参数对应的是组件的文件路径，title对应的标签名，params可以传递参数

#### 注意事项

<font color=#F5606A>1、框架为页面组件提供了缓存功能，这样的缓存功能是基于一定的策略的，就是页面组件的文件名还有组件实例的name必须一致，否则将无法触发页面组件的缓存。
</font>
