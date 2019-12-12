<template>
  <div style="columns: -24px;">
    <!--在el-checkbox元素中定义v-model绑定变量，单一的checkbox中，默认绑定变量的值会是Boolean，选中为true。-->
    <!-- `checked` 为 true 或 false -->
    <el-checkbox v-model="checked">备选项</el-checkbox>
  </div>
</template>

<script>
export default {
  name: 'Checkbox1',
  data () {
    return {
      checked: true
    }
  }
}
</script>

<style scoped>
  h3 {
    padding-bottom: 10px;
  }
</style>
