<template>
	<div>
		<!-- 设置background属性可以为分页按钮添加背景色。 -->

		<el-pagination
		background
		layout="prev, pager, next"
		:total="1000">
	</el-pagination>
</div>
</template>

<script>
export default {
  name: 'Pagination3'
}
</script>

<style scoped>

</style>
