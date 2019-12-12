<template>
  <div>
    <!-- 将dangerouslyUseHTMLString属性设置为 true，message 就会被当作 HTML 片段处理。  -->
    <el-button :plain="true" @click="openHTML">使用 HTML 片段</el-button>
  </div>
</template>

<script>
export default {
  name: 'Message5',
  methods: {
    openHTML () {
      this.$message({
        dangerouslyUseHTMLString: true,
        message: '<strong>这是 <i>HTML</i> 片段</strong>'
      })
    }
  }
}
</script>

<style scoped>
h3 {
  padding-bottom: 10px;
}

</style>
