<template>
  <el-col :sapn="12">
    <!-- 只需要在el-steps元素中设置direction属性为vertical即可。 -->
    <div style="height: 300px;">
      <el-steps direction="vertical" :active="1">
        <el-step title="步骤 1"></el-step>
        <el-step title="步骤 2"></el-step>
        <el-step title="步骤 3" description="这是一段很长很长很长的描述性文字"></el-step>
      </el-steps>
    </div>

  </el-col>
</template>

<script>
export default {
  name: 'Steps6',
  data () {
    return {
      active: 0
    }
  },

  methods: {
    next () {
      if (this.active++ > 2) this.active = 0
    }
  }
}
</script>

<style scoped>

</style>
