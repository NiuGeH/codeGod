<template>
  <div>
    <!-- 设置 precision 属性可以控制数值精度，接收一个 Number。 -->
    <el-input-number v-model="num9" :precision="2" :step="0.1" :max="10"></el-input-number>
  </div>
</template>

<script>
export default {
  name: 'InputNumber4',
  data () {
    return {
      num9: 1
    }
  }
}
</script>

<style scoped>

</style>
