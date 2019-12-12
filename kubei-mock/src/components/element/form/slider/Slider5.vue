<template>
  <el-col :span="8">
    <div class="block">
      <el-slider
        v-model="value10"
        vertical
        height="200px">
      </el-slider>
    </div>
  </el-col>
</template>

<script>
export default {
  name: 'Slider5',
  data () {
    return {
      value10: 0
    }
  }
}
</script>

<style scoped>

</style>
