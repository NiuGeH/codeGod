<template>
  <div>
    <!--   -->
    <el-button :plain="true" @click="openCenter">文字居中</el-button>
  </div>
</template>

<script>
export default {
  name: 'Message4',
  methods: {
    openCenter () {
      this.$message({
        message: '居中的文字',
        center: true
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
