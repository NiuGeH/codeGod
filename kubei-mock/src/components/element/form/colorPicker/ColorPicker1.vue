<template>
  <div >
    <!-- 使用 v-model 与 Vue 实例中的一个变量进行双向绑定，绑定的变量需要是字符串类型。 -->
    <div>
      <span class="demonstration">有默认值</span><br/>
      <el-color-picker v-model="color1"></el-color-picker>
    </div>
    <div style="margin-top: 10px;">
      <span class="demonstration">无默认值</span><br/>
      <el-color-picker v-model="color2"></el-color-picker>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ColorPicker1',
  data () {
    return {
      color1: '#409EFF',
      color2: null
    }
  }
}
</script>

<style scoped>
</style>
