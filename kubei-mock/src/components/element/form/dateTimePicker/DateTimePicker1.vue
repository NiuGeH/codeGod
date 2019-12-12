<template>
  <el-col :span="12">
    <!-- 通过设置type属性为datetime，即可在同一个选择器里同时进行日期和时间的选择。快捷选项的使用方法与 Date Picker 相同。 -->
    <el-row :span="24">
      <el-col :span="12">
        <span class="demonstration">默认</span><br/>
        <el-date-picker
          v-model="value1"
          type="datetime"
          placeholder="选择日期时间">
        </el-date-picker>
      </el-col>
      <el-col :span="12">
        <span class="demonstration">带快捷选项</span><br/>
        <el-date-picker
          v-model="value2"
          type="datetime"
          placeholder="选择日期时间"
          align="right"
          :picker-options="pickerOptions1">
        </el-date-picker>
      </el-col>
    </el-row>
    <el-row :span="24">
      <el-col>
        <span class="demonstration">设置默认时间</span><br/>
        <el-date-picker
          v-model="value3"
          type="datetime"
          placeholder="选择日期时间"
          default-time="12:00:00">
        </el-date-picker>
      </el-col>
    </el-row>
  </el-col>
</template>

<script>
export default {
  name: 'DateTimePicker1',
  data () {
    return {
      pickerOptions1: {
        shortcuts: [{
          text: '今天',
          onClick (picker) {
            picker.$emit('pick', new Date())
          }
        }, {
          text: '昨天',
          onClick (picker) {
            const date = new Date()
            date.setTime(date.getTime() - 3600 * 1000 * 24)
            picker.$emit('pick', date)
          }
        }, {
          text: '一周前',
          onClick (picker) {
            const date = new Date()
            date.setTime(date.getTime() - 3600 * 1000 * 24 * 7)
            picker.$emit('pick', date)
          }
        }]
      },
      value1: '',
      value2: '',
      value3: ''
    }
  }
}
</script>

<style scoped>
  .el-col{
    margin-bottom: 10px;
  }
</style>
