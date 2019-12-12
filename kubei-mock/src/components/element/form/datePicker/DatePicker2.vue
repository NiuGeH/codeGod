<template>
  <el-col :span="12">
    <el-row :span="24">
      <el-col :span="12">
        <span class="demonstration">周</span><br/>
        <el-date-picker
          v-model="value3"
          type="week"
          format="yyyy 第 WW 周"
          placeholder="选择周">
        </el-date-picker>
      </el-col>
      <el-col :span="12">
        <span class="demonstration">月</span><br/>
        <el-date-picker
          v-model="value4"
          type="month"
          placeholder="选择月">
        </el-date-picker>
      </el-col>
    </el-row>
    <el-row :span="24">
      <el-col :span="12">
        <span class="demonstration">年</span><br/>
        <el-date-picker
          v-model="value5"
          type="year"
          placeholder="选择年">
        </el-date-picker>
      </el-col>
      <el-col :span="12">
        <span class="demonstration">多个日期</span><br/>
        <el-date-picker
          type="dates"
          v-model="value14"
          placeholder="选择一个或多个日期">
        </el-date-picker>
      </el-col>
    </el-row>
  </el-col>
</template>

<script>
export default {
  name: 'DatePicker2',
  data () {
    return {
      value3: '',
      value4: '',
      value5: '',
      value14: ''
    }
  }
}
</script>

<style scoped>
  .el-col{
    margin-bottom: 10px;
  }
</style>
