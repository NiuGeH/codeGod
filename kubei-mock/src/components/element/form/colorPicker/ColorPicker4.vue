<template>
  <div >
    <!-- 评分被分为三个等级，可以利用颜色对分数及情感倾向进行分级（默认情况下不区分颜色）。三个等级所对应的颜色用过colors属性设置，而它们对应的两个阈值则通过 low-threshold 和 high-threshold 设定。 -->
    <el-color-picker v-model="color4"></el-color-picker>
    <el-color-picker v-model="color4" size="medium"></el-color-picker>
    <el-color-picker v-model="color4" size="small"></el-color-picker>
    <el-color-picker v-model="color4" size="mini"></el-color-picker>
  </div>
</template>

<script>
export default {
  name: 'ColorPicker4',
  data () {
    return {
      color4: '#409EFF'
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
