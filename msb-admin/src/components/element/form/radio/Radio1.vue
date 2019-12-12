<template>
  <div>
    <!--要使用 Radio 组件，只需要设置v-model绑定变量，选中意味着变量的值为相应 Radio label属性的值，label可以是String、Number或Boolean-->
    <!-- `checked` 为 true 或 false -->
    <el-radio v-model="radio" label="1">备选项</el-radio>
    <el-radio v-model="radio" label="2">备选项</el-radio>
  </div>
</template>

<script>
export default {
  name: 'Checkbox1',
  data () {
    return {
      radio: '1'
    }
  }
}
</script>

<style scoped>
  h3 {
    padding-bottom: 10px;
  }
</style>
