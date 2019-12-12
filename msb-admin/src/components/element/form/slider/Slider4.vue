<template>
  <el-col :span="8">
    <div class="block">
      <el-slider
        v-model="value9"
        range
        show-stops
        :max="10">
      </el-slider>
    </div>
  </el-col>
</template>

<script>
export default {
  name: 'Slider4',
  data () {
    return {
      value9: [4, 8]
    }
  }
}
</script>

<style scoped>

</style>
