<template>
  <el-col :span="12">
    <!-- 也可以使用title具名分发，可以用slot的方式来取代属性的设置，在本文档最后的列表中有所有的 slot name 可供参考。 -->
    <el-steps :space="200" :active="1" finish-status="success">
      <el-step title="已完成"></el-step>
      <el-step title="进行中"></el-step>
      <el-step title="步骤 3"></el-step>
    </el-steps>

  </el-col>
</template>

<script>
export default {
  name: 'Steps2'
}
</script>

<style scoped>
h3 {
  padding-bottom: 10px;
}
</style>
