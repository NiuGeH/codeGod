<template>
  <el-col :span="8">
    <!-- 为el-select设置clearable属性，则可将选择器清空。需要注意的是，clearable属性仅适用于单选。 -->
    <el-select v-model="value4" clearable placeholder="请选择">
      <el-option
        v-for="item in options"
        :key="item.value"
        :label="item.label"
        :value="item.value">
      </el-option>
    </el-select>
  </el-col>
</template>

<script>
export default {
  name: 'Select4',
  data () {
    return {
      options: [{
        value: '选项1',
        label: '黄金糕'
      }, {
        value: '选项2',
        label: '双皮奶',
        disabled: true
      }, {
        value: '选项3',
        label: '蚵仔煎'
      }, {
        value: '选项4',
        label: '龙须面'
      }, {
        value: '选项5',
        label: '北京烤鸭'
      }],
      value4: ''
    }
  }
}
</script>

<style scoped>
  .el-select {
    width: 100%;
  }
</style>
