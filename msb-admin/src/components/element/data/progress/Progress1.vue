<template>
	<el-col :span="12">
		<!-- Progress 组件设置percentage属性即可，表示进度条对应的百分比，必填，必须在 0-100。 -->

		<el-progress :percentage="0"></el-progress>
		<el-progress :percentage="70"></el-progress>
		<el-progress :percentage="80" color="#8e71c7"></el-progress>
		<el-progress :percentage="100" status="success"></el-progress>
		<el-progress :percentage="50" status="exception"></el-progress>
	</el-col>
</template>

<script>
export default {
  name: 'Progress1'
}
</script>

<style scoped>

</style>
