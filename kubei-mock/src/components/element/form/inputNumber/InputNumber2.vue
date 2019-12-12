<template>
  <div>
    <el-input-number v-model="num2" :disabled="true"></el-input-number>
  </div>

</template>

<script>
export default {
  name: 'InputNumber2',
  data () {
    return {
      num2: 1
    }
  }
}
</script>

<style scoped>

</style>
