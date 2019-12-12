<template>
  <el-col :span="8">
    <!-- 使用allow-create属性即可通过在输入框中输入文字来创建新的条目。注意此时filterable必须为真。本例还使用了default-first-option属性，在该属性打开的情况下，按下回车就可以选中当前选项列表中的第一个选项，无需使用鼠标或键盘方向键进行定位。 -->
    <el-select
      v-model="value10"
      multiple
      filterable
      allow-create
      default-first-option
      placeholder="请选择文章标签">
      <el-option
        v-for="item in options5"
        :key="item.value"
        :label="item.label"
        :value="item.value">
      </el-option>
    </el-select>
  </el-col>
</template>

<script>
export default {
  name: 'Select10',
  data () {
    return {
      options5: [{
        value: 'HTML',
        label: 'HTML'
      }, {
        value: 'CSS',
        label: 'CSS'
      }, {
        value: 'JavaScript',
        label: 'JavaScript'
      }],
      value10: []
    }
  }
}
</script>

<style scoped>
  .el-select {
    width: 100%;
  }
</style>
