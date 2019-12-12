<template>
  <div >
    <!-- ColorPicker 支持普通颜色，也支持带 Alpha 通道的颜色，通过show-alpha属性即可控制是否支持透明度的选择。 -->
    <el-color-picker v-model="color3" show-alpha></el-color-picker>
  </div>
</template>

<script>
export default {
  name: 'ColorPicker2',
  data () {
    return {
      color3: 'rgba(19, 206, 102, 0.8)'
    }
  }
}
</script>

<style scoped>
  h3 {
    margin: 10px 0px;
  }
  .block{
    margin-right: 30px;
  }
</style>
