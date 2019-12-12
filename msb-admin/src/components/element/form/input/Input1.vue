<template>
  <el-col :span="8">
    <el-input v-model="input" placeholder="请输入内容"></el-input>
  </el-col>
</template>

<script>
export default {
  name: 'Input1',
  data () {
    return {
      input: ''
    }
  }
}
</script>

<style scoped>
</style>
