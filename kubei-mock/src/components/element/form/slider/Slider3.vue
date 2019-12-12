<template>
  <el-col :span="8">
    <!--设置show-input属性会在右侧显示一个输入框-->
    <div class="block">
      <el-slider
        v-model="value8"
        show-input>
      </el-slider>
    </div>
  </el-col>
</template>

<script>
export default {
  name: 'Slider3',
  data () {
    return {
      value8: 0
    }
  }
}
</script>

<style scoped>

</style>
