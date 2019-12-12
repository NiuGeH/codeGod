<template>
  <div>
    <!-- 将dangerouslyUseHTMLString属性设置为 true，message 就会被当作 HTML 片段处理。  -->
    <el-button type="text" @click="open5">点击打开 Message Box</el-button>
  </div>
</template>

<script>
export default {
  name: 'MessageBox5',
  methods: {
    open5 () {
      this.$alert('<strong>这是 <i>HTML</i> 片段</strong>', 'HTML 片段', {
        dangerouslyUseHTMLString: true
      })
    }
  }
}
</script>

<style scoped>

</style>
