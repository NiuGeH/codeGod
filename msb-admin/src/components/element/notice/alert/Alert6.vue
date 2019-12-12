<template>
  <div class="al-page">
    <!-- 最后，这是一个同时具有 icon 和辅助性文字的样例。 -->
    <el-alert class="spacingAdjustment"
              title="成功提示的文案"
              type="success"
              description="文字说明文字说明文字说明文字说明文字说明文字说明"
              show-icon>
    </el-alert>
    <el-alert class="spacingAdjustment"
              title="消息提示的文案"
              type="info"
              description="文字说明文字说明文字说明文字说明文字说明文字说明"
              show-icon>
    </el-alert>
    <el-alert class="spacingAdjustment"
              title="警告提示的文案"
              type="warning"
              description="文字说明文字说明文字说明文字说明文字说明文字说明"
              show-icon>
    </el-alert>
    <el-alert class="spacingAdjustment"
              title="错误提示的文案"
              type="error"
              description="文字说明文字说明文字说明文字说明文字说明文字说明"
              show-icon>
    </el-alert>
  </div>
</template>

<script>
export default {
  name: 'Alert6',
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
