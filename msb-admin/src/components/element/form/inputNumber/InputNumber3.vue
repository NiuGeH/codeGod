<template>
  <div>
    <!-- 设置step属性可以控制步长，接受一个Number -->
    <el-input-number v-model="num3" :step="2"></el-input-number>
  </div>
</template>

<script>
export default {
  name: 'InputNumber3',
  data () {
    return {
      num3: 5
    }
  }
}
</script>

<style scoped>
</style>
