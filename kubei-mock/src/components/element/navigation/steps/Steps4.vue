<template>
  <el-col :span="12">
    <!--  -->
    <el-steps :active="2" align-center>
      <el-step title="步骤1" description="这是一段很长很长很长的描述性文字"></el-step>
      <el-step title="步骤2" description="这是一段很长很长很长的描述性文字"></el-step>
      <el-step title="步骤3" description="这是一段很长很长很长的描述性文字"></el-step>
      <el-step title="步骤4" description="这是一段很长很长很长的描述性文字"></el-step>
    </el-steps>

  </el-col>
</template>

<script>
export default {
  name: 'Steps4'
}
</script>

<style scoped>
h3 {
  padding-bottom: 10px;
}
</style>
