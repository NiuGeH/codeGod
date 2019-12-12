<template>
  <div>
    <el-row>
      <el-col :span="8"><div class="grid-content" style="background:#409EFF">Blue--#409EFF</div></el-col>
      <el-col :span="8"><div class="grid-content" style="background:#67C23A">Success--#67C23A</div></el-col>
      <el-col :span="8"><div class="grid-content" style="background:#E6A23C">Warning--#E6A23C</div></el-col>
      <el-col :span="12"><div class="grid-content" style="background:#F56C6C">Danger--#F56C6C</div></el-col>
      <el-col :span="12"><div class="grid-content" style="background:#909399">Info--#909399</div></el-col>
      <el-col :span="6"><div class="grid-content" style="background:#303133">主要文字--#303133</div></el-col>
      <el-col :span="6"><div class="grid-content" style="background:#606266">常规文字--#606266</div></el-col>
      <el-col :span="6"><div class="grid-content" style="background:#909399">次要文字--#909399</div></el-col>
      <el-col :span="6"><div class="grid-content" style="background:#C0C4CC">占位文字--#C0C4CC</div></el-col>
      <el-col :span="6"><div class="grid-content" style="background:#DCDFE6">一级边框--#DCDFE6</div></el-col>
      <el-col :span="6"><div class="grid-content" style="background:#E4E7ED">二级边框--#E4E7ED</div></el-col>
      <el-col :span="6"><div class="grid-content" style="background:#EBEEF5">三级边框--#EBEEF5</div></el-col>
      <el-col :span="6"><div class="grid-content" style="background:#F2F6FC">四级边框--#F2F6FC</div></el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: 'Color'
}
</script>

<style scoped>
  .grid-content {
    margin: 10px;
    border-radius: 4px;
    min-height: 100px;
    text-align: center;
    line-height: 50px;
    color: white;
  }
</style>
