<template>
  <el-col :span="12">
    <!-- 使用datetimerange进行范围选择时，在日期选择面板中选定起始与结束的日期，默认会使用该日期的00:00:00作为起始与结束的时刻；通过选项default-time可以控制选中起始与结束日期时所使用的具体时刻。default-time接受一个数组，数组每项值为字符串，形如12:00:00，其中第一项控制起始日期的具体时刻，第二项控制结束日期的具体时刻。 -->
    <el-row :span="24">
      <el-col>
        <span class="demonstration">起始日期时刻为 12:00:00</span><br/>
        <el-date-picker
          v-model="value6"
          type="datetimerange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :default-time="['12:00:00']">
        </el-date-picker>
      </el-col>
      <el-col>
        <span class="demonstration">起始日期时刻为 12:00:00，结束日期时刻为 08:00:00</span><br/>
        <el-date-picker
          v-model="value7"
          type="datetimerange"
          align="right"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :default-time="['12:00:00', '08:00:00']">
        </el-date-picker>
      </el-col>
    </el-row>
  </el-col>
</template>

<script>
import Breadcrumb from '@components/element/navigation/breadcrumb/Breadcrumb1'
export default {
  name: 'DateTimePicker1',
  components: { Breadcrumb },
  data () {
    return {
      value6: '',
      value7: ''
    }
  }
}
</script>

<style scoped>
  .el-col{
    margin-bottom: 10px;
  }
</style>
