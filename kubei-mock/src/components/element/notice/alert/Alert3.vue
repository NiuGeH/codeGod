<template>
  <div class="al-page">
    <!--通过设置show-icon属性来显示 Alert 的 icon，这能更有效地向用户展示你的显示意图。 -->
    <el-alert class="spacingAdjustment"
              title="成功提示的文案"
              type="success"
              show-icon>
    </el-alert>
    <el-alert class="spacingAdjustment"
              title="消息提示的文案"
              type="info"
              show-icon>
    </el-alert>
    <el-alert class="spacingAdjustment"
              title="警告提示的文案"
              type="warning"
              show-icon>
    </el-alert>
    <el-alert class="spacingAdjustment"
              title="错误提示的文案"
              type="error"
              show-icon>
    </el-alert>
  </div>
</template>

<script>
export default {
  name: 'Alert3',
  data () {
    return {}
  }
}
</script>

<style scoped>
  .spacingAdjustment {
    margin-bottom: 10px;
  }

  .al-page {
    max-width: 500px
  }
</style>
