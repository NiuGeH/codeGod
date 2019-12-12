<template>
	<div>
		<!-- 除了数字外，设置is-dot属性，它接受一个Boolean。 -->

		<el-badge is-dot class="item">数据查询</el-badge>
		<el-badge is-dot class="item">
			<el-button class="share-button" icon="el-icon-share" type="primary"></el-button>
		</el-badge>

	</div>
</template>

<script>
export default {
  name: 'Badge1'
}
</script>

<style scoped>
.item {
	margin-top: 10px;
	margin-right: 40px;
}
</style>
