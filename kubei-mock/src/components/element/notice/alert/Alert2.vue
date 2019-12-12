<template>
  <div class="al-page">
    <!-- 在 Alert 组件中，你可以设置是否可关闭，关闭按钮的文本以及关闭时的回调函数。closable属性决定是否可关闭，接受boolean，默认为true。你可以设置close-text属性来代替右侧的关闭图标，注意：close-text必须为文本。设置close事件来设置关闭时的回调。 -->
    <el-alert class="spacingAdjustment"
              title="不可关闭的 alert"
              type="success"
              :closable="false">
    </el-alert>
    <el-alert class="spacingAdjustment"
              title="自定义 close-text"
              type="info"
              close-text="知道了">
    </el-alert>
    <el-alert class="spacingAdjustment"
              title="设置了回调的 alert"
              type="warning"
              @close="hello">
    </el-alert>
  </div>
</template>

<script>
export default {
  name: 'Alert2',
  data () {
    return {}
  },
  methods: {
    hello () {
      alert('Hello World!')
    }
  }
}
</script>

<style scoped>
  .spacingAdjustment {
    margin-bottom: 10px;
  }

  .al-page {
    max-width: 500px
  }
</style>
