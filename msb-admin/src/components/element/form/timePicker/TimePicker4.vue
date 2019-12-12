<template>
  <el-col :span="12">
    <!-- 添加is-range属性即可选择时间范围，同样支持arrow-control属性。  -->
    <el-row :span="24">
      <el-col :span="12">
        <el-time-picker
          is-range
          v-model="value4"
          range-separator="至"
          start-placeholder="开始时间"
          end-placeholder="结束时间"
          placeholder="选择时间范围" class="rangeEdit">
        </el-time-picker>
      </el-col>
      <el-col :span="12">
        <el-time-picker
          is-range
          arrow-control
          v-model="value5"
          range-separator="至"
          start-placeholder="开始时间"
          end-placeholder="结束时间"
          placeholder="选择时间范围" class="rangeEdit">
        </el-time-picker>
      </el-col>
    </el-row>
  </el-col>
</template>

<script>
export default {
  name: 'TimePicker4',
  data () {
    return {
      value4: [new Date(2016, 9, 10, 8, 40), new Date(2016, 9, 10, 9, 40)],
      value5: [new Date(2016, 9, 10, 8, 40), new Date(2016, 9, 10, 9, 40)]
    }
  }
}
</script>

<style scoped>
  .rangeEdit {
    width:220px;
  }
</style>
