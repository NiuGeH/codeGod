<template>
  <div>
    <!--  默认情况下，当用户触发取消（点击取消按钮）和触发关闭（点击关闭按钮或遮罩层、按下 ESC 键）时，Promise 的 reject 回调和callback回调的参数均为 'cancel'。如果将distinguishCancelAndClose属性设置为 true，则上述两种行为的参数分别为 'cancel' 和 'close'。 -->
    <el-button type="text" @click="open6">点击打开 Message Box</el-button>
  </div>
</template>

<script>
export default {
  name: 'MessageBox6',
  methods: {
    open6 () {
      this.$confirm('检测到未保存的内容，是否在离开页面前保存修改？', '确认信息', {
        distinguishCancelAndClose: true,
        confirmButtonText: '保存',
        cancelButtonText: '放弃修改'
      })
        .then(() => {
          this.$message({
            type: 'info',
            message: '保存修改'
          })
        })
        .catch(action => {
          this.$message({
            type: 'info',
            message: action === 'cancel'
              ? '放弃保存并离开页面'
              : '停留在当前页面'
          })
        })
    }
  }
}
</script>

<style scoped>

</style>
