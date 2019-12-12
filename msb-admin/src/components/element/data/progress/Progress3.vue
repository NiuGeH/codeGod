<template>
	<el-col :span="12">
		<!-- Progress 组件可通过 type 属性来指定使用环形进度条，在环形进度条中，还可以通过 width 属性来设置其大小。 -->

		<el-progress type="circle" :percentage="0"></el-progress>
		<el-progress type="circle" :percentage="25"></el-progress>
		<el-progress type="circle" :percentage="80" color="#8e71c7"></el-progress>
		<el-progress type="circle" :percentage="100" status="success"></el-progress>
		<el-progress type="circle" :percentage="50" status="exception"></el-progress>
		<el-progress type="circle" :percentage="100" status="text">Done</el-progress>
	</el-col>
</template>

<script>
export default {
  name: 'Progress3'
}
</script>

<style scoped>
h3 {
	margin: 10px 0px;
}
</style>
