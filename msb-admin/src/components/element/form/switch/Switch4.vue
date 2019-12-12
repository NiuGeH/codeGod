<template>
  <div>
    <!--设置disabled属性，接受一个Boolean，设置true即可禁用。-->
    <el-switch
      v-model="value6"
      disabled>
    </el-switch>
    <el-switch
      v-model="value7"
      disabled>
    </el-switch>
  </div>
</template>

<script>
export default {
  name: 'Switch4',
  data () {
    return {
      value6: true,
      value7: false
    }
  }
}
</script>

<style scoped>

</style>
