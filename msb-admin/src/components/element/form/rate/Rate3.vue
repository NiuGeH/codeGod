<template>
  <div >
    <h3>3.其它 icon</h3>
    <!-- 设置icon-classes属性可以自定义对应 3 个不同分段的图标。本例还使用void-icon-class指定了未选中时的图标类名。 -->
    <el-rate
      v-model="value4"
      :icon-classes="['icon-rate-face-1', 'icon-rate-face-2', 'icon-rate-face-3']"
      void-icon-class="icon-rate-face-off"
      :colors="['#99A9BF', '#F7BA2A', '#FF9900']">
    </el-rate>
  </div>
</template>

<script>
export default {
  name: 'Rate3',
  data () {
    return {
      value4: null
    }
  }
}
</script>

<style scoped>

</style>
