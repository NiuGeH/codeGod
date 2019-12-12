<template>
  <el-col :span="8">
    <el-input
      type="textarea"
      :rows="2"
      placeholder="请输入内容"
      v-model="textarea">
    </el-input>
  </el-col>
</template>

<script>
export default {
  name: 'InputBasic',
  data () {
    return {
      textarea: ''
    }
  }
}
</script>

<style scoped>
</style>
