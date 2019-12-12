<template>
	<div>
		<!-- 默认情况下，当总页数超过 7 页时，Pagination 会折叠多余的页码按钮。通过pager-count属性可以设置最大页码按钮数。 -->

		<el-pagination
		:page-size="20"
		:pager-count="11"
		layout="prev, pager, next"
		:total="1000">
	</el-pagination>
</div>
</template>

<script>
export default {
  name: 'Pagination2'
}
</script>

<style scoped>

</style>
