<template>
  <div >
    <!-- 为组件设置 show-text 属性会在右侧显示辅助文字。通过设置 texts 可以为每一个分值指定对应的辅助文字。texts 为一个数组，长度应等于最大值 max。 -->
    <el-rate
      v-model="value3"
      show-text>
    </el-rate>
  </div>
</template>

<script>
export default {
  name: 'Rate2',
  data () {
    return {
      value3: null
    }
  }
}
</script>

<style scoped>

  .block{
    margin-right: 30px;
  }
</style>
