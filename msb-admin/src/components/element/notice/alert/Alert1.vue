<template>
  <div class="al-page">
    <!-- Alert 组件提供四种主题，由type属性指定，默认值为info。 -->
    <el-alert class="spacingAdjustment"
              title="成功提示的文案"
              type="success">
    </el-alert>
    <el-alert class="spacingAdjustment"
              title="消息提示的文案"
              type="info">
    </el-alert>
    <el-alert class="spacingAdjustment"
              title="警告提示的文案"
              type="warning">
    </el-alert>
    <el-alert class="spacingAdjustment"
              title="错误提示的文案"
              type="error">
    </el-alert>
  </div>
</template>

<script>
export default {
  name: 'Alert1'
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
