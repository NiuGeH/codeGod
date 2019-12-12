<template>
	<div>
		<!-- 设置layout，表示需要显示的内容，用逗号分隔，布局元素会依次显示。prev表示上一页，next为下一页，pager表示页码列表，除此以外还提供了jumper和total，size和特殊的布局符号->，->后的元素会靠右显示，jumper表示跳页元素，total表示总条目数，size用于设置每页显示的页码数量。 -->

		<div class="block">
			<span class="demonstration">页数较少时的效果</span>
			<el-pagination
			layout="prev, pager, next"
			:total="50">
		</el-pagination>
	</div>
	<div class="block">
		<span class="demonstration">大于 7 页时的效果</span>
		<el-pagination
		layout="prev, pager, next"
		:total="1000">
	</el-pagination>
</div>
</div>
</template>

<script>
export default {
  name: 'Pagination1'
}
</script>

<style scoped>

</style>
