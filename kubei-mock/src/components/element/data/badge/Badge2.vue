<template>
	<div>
		<!-- 由max属性定义，它接受一个Number，需要注意的是，只有当value为Number时，它才会生效。 -->

		<el-badge :value="200" :max="99" class="item">
			<el-button size="small">评论</el-button>
		</el-badge>
		<el-badge :value="100" :max="10" class="item">
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
