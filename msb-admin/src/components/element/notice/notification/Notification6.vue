<template>
  <div>
    <!-- 将showClose属性设置为false即可隐藏关闭按钮。  -->
    <el-button
      plain
      @click="open13">
      隐藏关闭按钮
    </el-button>
  </div>
</template>

<script>
export default {
  name: 'Notification6',
  methods: {
    open13 () {
      this.$notify.success({
        title: 'Info',
        message: '这是一条没有关闭按钮的消息',
        showClose: false
      })
    }
  }
}
</script>

<style scoped>

</style>
