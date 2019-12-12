<template>
  <div>
    <!-- 当需要自定义更多属性时，Message 也可以接收一个对象为参数。比如，设置type字段可以定义不同的状态，默认为info。此时正文内容以message的值传入。同时，我们也为 Message 的各种 type 注册了方法，可以在不传入type字段的情况下像open4那样直接调用。  -->
    <el-button :plain="true" @click="open2">成功</el-button>
    <el-button :plain="true" @click="open3">警告</el-button>
    <el-button :plain="true" @click="open">消息</el-button>
    <el-button :plain="true" @click="open4">错误</el-button>
  </div>
</template>

<script>
export default {
  name: 'Message2',
  methods: {
    open () {
      this.$message('这是一条消息提示')
    },
    open2 () {
      this.$message({
        message: '恭喜你，这是一条成功消息',
        type: 'success'
      })
    },

    open3 () {
      this.$message({
        message: '警告哦，这是一条警告消息',
        type: 'warning'
      })
    },

    open4 () {
      this.$message.error('错了哦，这是一条错误消息')
    }
  }
}
</script>

<style scoped>
h3 {
  padding-bottom: 10px;
}

</style>
