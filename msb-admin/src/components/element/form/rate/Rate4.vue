<template>
  <div >
    <!-- 为组件设置 disabled 属性表示组件为只读，支持小数分值。此时若设置 show-score，则会在右侧显示目前的分值。可以提供 score-template 作为显示模板，模板为一个包含了 {value} 的字符串，{value} 会被解析为分值。 -->
    <el-rate
      v-model="value5"
      disabled
      show-score
      text-color="#ff9900"
      score-template="{value}">
    </el-rate>
  </div>
</template>

<script>
export default {
  name: 'Rate4',
  data () {
    return {
      value5: 3.7
    }
  }
}
</script>

<style scoped>
  .block{
    margin-right: 30px;
  }
</style>
