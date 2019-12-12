<template>
  <el-col :span="8">
    <el-time-select
      v-model="value1"
      :picker-options="{
    start: '08:30',
    step: '00:15',
    end: '18:30'
  }"
      placeholder="选择时间">
    </el-time-select>
  </el-col>
</template>

<script>
export default {
  name: 'TimePicker1',
  data () {
    return {
      value1: ''
    }
  }
}
</script>

<style scoped>

</style>
