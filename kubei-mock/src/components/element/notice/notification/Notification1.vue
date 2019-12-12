<template>
  <div>
    <!-- Notification 组件提供通知功能，Element 注册了$notify方法，接收一个options字面量参数，在最简单的情况下，你可以设置title字段和message字段，用于设置通知的标题和正文。默认情况下，经过一段时间后 Notification 组件会自动关闭，但是通过设置duration，可以控制关闭的时间间隔，特别的是，如果设置为0，则不会自动关闭。注意：duration接收一个Number，单位为毫秒，默认为4500。    -->
    <el-button
        plain
        @click="open">
        可自动关闭
    </el-button>
    <el-button
      plain
      @click="open2">
      不会自动关闭
    </el-button>
  </div>
</template>

<script>
export default {
  name: 'Notification1',
  methods: {
    open () {
      const h = this.$createElement

      this.$notify({
        title: '标题名称',
        message: h('i', { style: 'color: teal' }, '这是提示文案这是提示文案这是提示文案这是提示文案这是提示文案这是提示文案这是提示文案这是提示文案')
      })
    },

    open2 () {
      this.$notify({
        title: '提示',
        message: '这是一条不会自动关闭的消息',
        duration: 0
      })
    }
  }
}
</script>

<style scoped>

</style>
