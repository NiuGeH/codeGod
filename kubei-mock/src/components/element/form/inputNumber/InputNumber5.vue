<template>
  <div>
    <!-- 额外提供了 medium、small、mini 三种尺寸的数字输入框 -->
    <el-input-number v-model="num4"></el-input-number>
    <el-input-number size="medium" v-model="num5"></el-input-number>
    <el-input-number size="small" v-model="num6"></el-input-number>
    <el-input-number size="mini" v-model="num7"></el-input-number>
  </div>
</template>

<script>
export default {
  name: 'InputNumber5',
  data () {
    return {
      num4: 1,
      num5: 1,
      num6: 1,
      num7: 1
    }
  }
}
</script>

<style scoped>
  .el-input-number {
    margin-bottom: 10px;
  }
</style>
