<template>
  <div class="al-page">
    <!-- 除了必填的title属性外，你可以设置description属性来帮助你更好地介绍，我们称之为辅助性文字。辅助性文字只能存放单行文本，会自动换行显示。 -->
    <el-alert
      title="带辅助性文字介绍"
      type="success"
      description="这是一句绕口令：黑灰化肥会挥发发灰黑化肥挥发；灰黑化肥会挥发发黑灰化肥发挥。 黑灰化肥会挥发发灰黑化肥黑灰挥发化为灰……">
    </el-alert>
  </div>

</template>

<script>
export default {
  name: 'Alert5',
  data () {
    return {}
  }
}
</script>

<style scoped>
  .al-page {
    max-width: 500px
  }
</style>
