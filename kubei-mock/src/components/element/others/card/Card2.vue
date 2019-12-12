<template>
  <div>
    <el-card class="box-card">
      <div v-for="o in 4" :key="o" class="text item">
        {{'列表内容 ' + o }}
      </div>
    </el-card>

  </div>
</template>

<script>
export default {
  name: 'Card2'
}
</script>

<style scoped>
  .text {
    font-size: 14px;
  }

  .item {
    padding: 18px 0;
  }

  .box-card {
    max-width: 480px;
  }
</style>
