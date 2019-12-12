<template>
  <div class="al-page">
    <!--  -->
    <el-alert class="spacingAdjustment"
              title="成功提示的文案"
              type="success"
              center
              show-icon>
    </el-alert>
    <el-alert class="spacingAdjustment"
              title="消息提示的文案"
              type="info"
              center
              show-icon>
    </el-alert>
    <el-alert class="spacingAdjustment"
              title="警告提示的文案"
              type="warning"
              center
              show-icon>
    </el-alert>
    <el-alert class="spacingAdjustment"
              title="错误提示的文案"
              type="error"
              center
              show-icon>
    </el-alert>
  </div>
</template>

<script>
export default {
  name: 'Alert4',
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
