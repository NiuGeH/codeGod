<template>
  <div>
    <!-- 设置active-value和inactive-value属性，接受Boolean, String或Number类型的值。 -->
    <el-tooltip :content="'Switch value: ' + value5" placement="top">
      <el-switch
        v-model="value5"
        active-color="#13ce66"
        inactive-color="#ff4949"
        active-value="100"
        inactive-value="0">
      </el-switch>
    </el-tooltip>
  </div>
</template>

<script>
export default {
  name: 'Switch3',
  data () {
    return {
      value5: '100'
    }
  }
}
</script>

<style scoped>

</style>
