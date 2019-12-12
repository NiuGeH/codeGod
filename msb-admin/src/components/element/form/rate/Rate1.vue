<template>
  <div >
    <!-- 评分被分为三个等级，可以利用颜色对分数及情感倾向进行分级（默认情况下不区分颜色）。三个等级所对应的颜色用过colors属性设置，而它们对应的两个阈值则通过 low-threshold 和 high-threshold 设定。 -->
    <div class="block">
      <span class="demonstration">默认不区分颜色</span>
      <el-rate v-model="value1"></el-rate>
    </div>
    <div class="block">
      <span class="demonstration">区分颜色</span>
      <el-rate
        v-model="value2"
        :colors="['#99A9BF', '#F7BA2A', '#FF9900']">
      </el-rate>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Rate1',
  data () {
    return {
      value1: null,
      value2: null
    }
  }
}
</script>

<style scoped>

  .block{
    margin-right: 30px;
  }
</style>
