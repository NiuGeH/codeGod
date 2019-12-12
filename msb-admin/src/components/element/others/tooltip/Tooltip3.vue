<template>
  <div>
    <!-- 用具名 slot 分发content，替代tooltip中的content属性。 -->
    <el-tooltip placement="top">
      <div slot="content">多行信息<br/>第二行信息</div>
      <el-button>Top center</el-button>
    </el-tooltip>

  </div>
</template>

<script>
export default {
  name: 'Tooltip3'
}
</script>

<style scoped>

</style>
