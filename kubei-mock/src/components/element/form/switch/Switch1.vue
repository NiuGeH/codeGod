<template>
  <div>
    <!-- 绑定v-model到一个Boolean类型的变量。可以使用active-color属性与inactive-color属性来设置开关的背景色。 -->
    <el-switch
      v-model="value2"
      active-color="#13ce66"
      inactive-color="#ff4949">
    </el-switch>
  </div>
</template>

<script>
export default {
  name: 'Switch1',
  data () {
    return {
      value1: true,
      value2: true
    }
  }
}
</script>

<style scoped>

</style>
