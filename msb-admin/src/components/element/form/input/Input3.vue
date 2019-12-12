<template>
  <el-col :span="8">
    <el-input placeholder="请输入内容" v-model="input10" clearable>
    </el-input>
  </el-col>
</template>

<script>
export default {
  name: 'Input3',
  data () {
    return {
      input10: ''
    }
  }
}
</script>

<style scoped>
</style>
