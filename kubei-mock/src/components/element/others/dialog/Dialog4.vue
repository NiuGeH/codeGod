<template>
  <div>
    <!-- 将center设置为true即可使标题和底部居中。center仅影响标题和底部区域。Dialog 的内容是任意的，在一些情况下，内容并不适合居中布局。如果需要内容也水平居中，请自行为其添加 CSS。 -->
    <el-button type="text" @click="centerDialogVisible = true">点击打开 Dialog</el-button>

    <el-dialog
      title="提示"
      :visible.sync="centerDialogVisible"
      width="30%"
      center>
      <span>需要注意的是内容是默认不居中的</span>
      <span slot="footer" class="dialog-footer">
      <el-button @click="centerDialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="centerDialogVisible = false">确 定</el-button>
    </span>
    </el-dialog>

  </div>
</template>

<script>
export default {
  name: 'Dialog4',
  data () {
    return {
      centerDialogVisible: false
    }
  }
}
</script>

<style scoped>

</style>
