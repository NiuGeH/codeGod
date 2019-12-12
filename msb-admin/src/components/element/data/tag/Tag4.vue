<template>
	<div>
		<!-- 额外的尺寸：medium、small、mini，通过设置size属性来配置它们。 -->
		<el-tag closable>默认标签</el-tag>
		<el-tag size="medium" closable>中等标签</el-tag>
		<el-tag size="small" closable>小型标签</el-tag>
		<el-tag size="mini" closable>超小标签</el-tag>
	</div>
</template>

<script>
export default {
  name: 'Tag4'
}
</script>

<style scoped>

</style>
