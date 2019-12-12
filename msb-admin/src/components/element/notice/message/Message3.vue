<template>
  <div>
    <!-- 默认的 Message 是不可以被人工关闭的，如果需要可手动关闭的 Message，可以使用showClose字段。此外，和 Notification 一样，Message 拥有可控的duration，设置0为不会被自动关闭，默认为 3000 毫秒。  -->
    <el-button :plain="true" @click="open5">消息</el-button>
    <el-button :plain="true" @click="open6">成功</el-button>
    <el-button :plain="true" @click="open7">警告</el-button>
    <el-button :plain="true" @click="open8">错误</el-button>
  </div>
</template>

<script>
export default {
  name: 'Message3',
  methods: {
    open5 () {
      this.$message({
        showClose: true,
        message: '这是一条消息提示'
      })
    },

    open6 () {
      this.$message({
        showClose: true,
        message: '恭喜你，这是一条成功消息',
        type: 'success'
      })
    },

    open7 () {
      this.$message({
        showClose: true,
        message: '警告哦，这是一条警告消息',
        type: 'warning'
      })
    },

    open8 () {
      this.$message({
        showClose: true,
        message: '错了哦，这是一条错误消息',
        type: 'error'
      })
    }
  }
}
</script>

<style scoped>
h3 {
  padding-bottom: 10px;
}

</style>
