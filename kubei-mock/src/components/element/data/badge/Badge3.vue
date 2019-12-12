<template>
	<div>
		<!-- 定义value为String类型是时可以用于显示自定义文本。 -->

		<el-badge value="new" class="item">
			<el-button size="small">评论</el-button>
		</el-badge>
		<el-badge value="hot" class="item">
			<el-button size="small">回复</el-button>
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
