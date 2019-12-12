<template>
  <div>
    <!--使用 min 和 max 属性能够限制可以被勾选的项目的数量。-->
    <el-checkbox-group
      v-model="checkedCities1"
      :min="1"
      :max="2">
      <el-checkbox v-for="city in cities" :label="city" :key="city">{{city}}</el-checkbox>
    </el-checkbox-group>
  </div>
</template>

<script>
const cityOptions = ['上海', '北京', '广州', '深圳']
export default {
  name: 'Checkbox5',
  data () {
    return {
      checkedCities1: ['上海', '北京'],
      cities: cityOptions
    }
  }
}
</script>

<style scoped>
  h3 {
    padding-bottom: 10px;
  }
</style>
