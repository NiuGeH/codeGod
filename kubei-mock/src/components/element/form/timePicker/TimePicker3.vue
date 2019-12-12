<template>
  <el-col :span="12">
    <el-row :span="24">
      <el-col :span="12">
        <el-time-select
          placeholder="起始时间"
          v-model="startTime"
          :picker-options="{
            start: '08:30',
            step: '00:15',
            end: '18:30'
          }">
        </el-time-select>
      </el-col>
      <el-col :span="12">
        <el-time-select
          placeholder="结束时间"
          v-model="endTime"
          :picker-options="{
            start: '08:30',
            step: '00:15',
            end: '18:30',
            minTime: startTime
          }">
        </el-time-select>
      </el-col>
    </el-row>
  </el-col>
</template>

<script>
export default {
  name: 'TimePicker3',
  data () {
    return {
      startTime: '',
      endTime: ''
    }
  }
}
</script>

<style scoped>

</style>
