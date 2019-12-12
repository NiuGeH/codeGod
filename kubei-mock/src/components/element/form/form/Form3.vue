<template>
  <el-col :span="12">
    <!-- 通过设置 label-position 属性可以改变表单域标签的位置，可选值为 top、left，当设为 top 时标签会置于表单域的顶部 -->
    <el-radio-group v-model="labelPosition" size="small">
      <el-radio-button label="left">左对齐</el-radio-button>
      <el-radio-button label="right">右对齐</el-radio-button>
      <el-radio-button label="top">顶部对齐</el-radio-button>
    </el-radio-group>
    <div style="margin: 20px;"></div>
    <el-form :label-position="labelPosition" label-width="80px" :model="formLabelAlign">
      <el-form-item label="名称">
        <el-input v-model="formLabelAlign.name"></el-input>
      </el-form-item>
      <el-form-item label="活动区域">
        <el-input v-model="formLabelAlign.region"></el-input>
      </el-form-item>
      <el-form-item label="活动形式">
        <el-input v-model="formLabelAlign.type"></el-input>
      </el-form-item>
    </el-form>
  </el-col>
</template>

<script>
export default {
  name: 'Form3',
  data () {
    return {
      labelPosition: 'right',
      formLabelAlign: {
        name: '',
        region: '',
        type: ''
      }
    }
  }
}
</script>

<style scoped>

</style>
