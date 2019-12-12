## EventBus的使用

FrosUI内部默认提供了用于EventBus的实例，在assets/js中的bus.js，对于不需要使用的vuex的项目（绝大部分是不需要的），如果需要实现组件间的通信，便可以通过EventBus的方法进行传递。

```vue
// 发送者组件
<template>
  <el-button @click="pickMe">{{ btnText }}</el-button>
</template>

<script>
import Bus from '@js/bus'

export default {
  name: "ChildCp",
  data () {
    return {
      btnText: '发送者'
    }
  },
  methods: {
    pickMe: function () {
      let self = this
      Bus.$emit('changeCpSecond', '你好')
    }
  }
}
</script>

<style scoped>

</style>
```

```vue
// 接收者组件
<template>
  <el-button>{{ btnText }}</el-button>
</template>

<script>
import Bus from '@js/bus'

export default {
  name: "ChildCpSecond",
  data () {
    return {
      btnText: '接收者'
    }
  },
  created: function () {
    let self = this
    Bus.$off('changeCpSecond').$on('changeCpSecond', function (msg) {
      self.btnText = msg
    })
  },
  methods: {
  }
}
</script>

<style scoped>

</style>
```

发送者组件通过$emit发送消息key为"changeCpSecond"，参数为"你好"的消息，接收者组件通过$on对应的key来触发方法和接收参数。

#### 注意事项

<font color=#F5606A>1、为了避免eventbus的事件重复监听，在绑定前记得使用$off进行解绑</font>   
