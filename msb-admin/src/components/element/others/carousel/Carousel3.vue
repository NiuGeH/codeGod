<template>
  <div>
    <!-- arrow属性定义了切换箭头的显示时机。默认情况下，切换箭头只有在鼠标 hover 到走马灯上时才会显示；若将arrow设置为always，则会一直显示；设置为never，则会一直隐藏。 -->
    <el-carousel :interval="5000" arrow="always">
      <el-carousel-item v-for="item in 4" :key="item" style="text-align: center;">
        <h3>{{ item }}</h3>
      </el-carousel-item>
    </el-carousel>

  </div>
</template>

<script>
export default {
  name: 'Carousel3'
}
</script>

<style scoped>
  .el-carousel__item h3 {
    color: #475669;
    font-size: 18px;
    opacity: 0.75;
    line-height: 300px;
    margin: 0;
  }

  .el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
  }

  .el-carousel__item:nth-child(2n+1) {
    background-color: #d3dce6;
  }
</style>
