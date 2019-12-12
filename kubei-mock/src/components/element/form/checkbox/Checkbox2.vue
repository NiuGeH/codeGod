<template>
  <div>
    <!--设置disabled属性即可。-->
    <el-checkbox v-model="checked1" disabled>备选项1</el-checkbox>
    <el-checkbox v-model="checked2" disabled>备选项</el-checkbox>
  </div>
</template>

<script>
export default {
  name: 'Checkbox2',
  data () {
    return {
      checked1: false,
      checked2: true
    }
  }
}
</script>

<style scoped>
  h3 {
    padding-bottom: 10px;
  }
</style>
