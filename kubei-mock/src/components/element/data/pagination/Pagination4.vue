<template>
	<div>
		<!-- 只需要一个small属性，它接受一个Boolean，默认为false，设为true即可启用。 -->

		<el-pagination
		small
		layout="prev, pager, next"
		:total="50">
	</el-pagination>
</div>
</template>

<script>
export default {
  name: 'Pagination4'
}
</script>

<style scoped>

</style>
