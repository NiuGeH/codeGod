<template>
  <div>
    <!-- 通过设置effect属性来改变主题，默认为dark。 -->
    <el-tooltip content="Top center" placement="top">
      <el-button>Dark</el-button>
    </el-tooltip>
    <el-tooltip content="Bottom center" placement="bottom" effect="light">
      <el-button>Light</el-button>
    </el-tooltip>
  </div>
</template>

<script>
export default {
  name: 'Tooltip2'
}
</script>

<style scoped>

</style>
