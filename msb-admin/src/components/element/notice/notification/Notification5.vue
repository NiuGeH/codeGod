<template>
  <div>
    <!-- 将dangerouslyUseHTMLString属性设置为 true，message 就会被当作 HTML 片段处理。  -->
    <el-button
      plain
      @click="open12">
      使用 HTML 片段
    </el-button>
  </div>
</template>

<script>
export default {
  name: 'Notification5',
  methods: {
    open12 () {
      this.$notify({
        title: 'HTML 片段',
        dangerouslyUseHTMLString: true,
        message: '<strong>这是 <i>HTML</i> 片段</strong>'
      })
    }
  }
}
</script>

<style scoped>

</style>
