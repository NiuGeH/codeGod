<template>
  <el-col :span="8">
    <!-- 将自定义的 HTML 模板插入el-option的 slot 中即可。 -->
    <el-select v-model="value6" placeholder="请选择">
      <el-option
        v-for="item in cities"
        :key="item.value"
        :label="item.label"
        :value="item.value">
        <span style="float: left">{{ item.label }}</span>
        <span style="float: right; color: #8492a6; font-size: 13px">{{ item.value }}</span>
      </el-option>
    </el-select>
  </el-col>
</template>

<script>
export default {
  name: 'Select6',
  data () {
    return {
      cities: [{
        value: 'Beijing',
        label: '北京'
      }, {
        value: 'Shanghai',
        label: '上海'
      }, {
        value: 'Nanjing',
        label: '南京'
      }, {
        value: 'Chengdu',
        label: '成都'
      }, {
        value: 'Shenzhen',
        label: '深圳'
      }, {
        value: 'Guangzhou',
        label: '广州'
      }],
      value6: ''
    }
  }
}
</script>

<style scoped>
  .el-select {
    width: 100%;
  }
</style>
