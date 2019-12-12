<template>
  <div>
    <!--只要在el-radio元素中设置disabled属性即可，它接受一个Boolean，true为禁用。-->
    <el-radio disabled v-model="radio1" label="禁用">备选项</el-radio>
    <el-radio disabled v-model="radio1" label="选中且禁用">备选项</el-radio>
  </div>
</template>

<script>
export default {
  name: 'Checkbox2',
  data () {
    return {
      radio1: '选中且禁用'
    }
  }
}
</script>

<style scoped>
  h3 {
    padding-bottom: 10px;
  }
</style>
