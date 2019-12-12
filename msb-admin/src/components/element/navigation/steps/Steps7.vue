<template>
  <el-col :span="12" >
    <!--  -->
    <el-steps :active="1" simple>
      <el-step title="步骤 1" icon="el-icon-edit"></el-step>
      <el-step title="步骤 2" icon="el-icon-upload"></el-step>
      <el-step title="步骤 3" icon="el-icon-picture"></el-step>
    </el-steps>

    <el-steps :active="1" finish-status="success" simple style="margin-top: 20px">
      <el-step title="步骤 1" ></el-step>
      <el-step title="步骤 2" ></el-step>
      <el-step title="步骤 3" ></el-step>
    </el-steps>

  </el-col>
</template>

<script>
export default {
  name: 'Steps7'
}
</script>

<style scoped>

</style>
