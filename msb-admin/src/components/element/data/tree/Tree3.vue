<template>
  <el-col :span="12">
    <!-- 由于在点击节点时才进行该层数据的获取，默认情况下 Tree 无法预知某个节点是否为叶子节点，所以会为每个节点添加一个下拉按钮，如果节点没有下层数据，则点击后下拉按钮会消失。同时，你也可以提前告知 Tree 某个节点是否为叶子节点，从而避免在叶子节点前渲染下拉按钮。 -->

    <el-tree
      :props="props1"
      :load="loadNode1"
      lazy
      show-checkbox>
    </el-tree>
  </el-col>
</template>

<script>
export default {
  name: 'Tree3',
  data () {
    return {
      props1: {
        label: 'name',
        children: 'zones',
        isLeaf: 'leaf'
      }
    }
  },
  methods: {
    loadNode1 (node, resolve) {
      if (node.level === 0) {
        return resolve([{ name: 'region' }])
      }
      if (node.level > 1) return resolve([])

      setTimeout(() => {
        const data = [{
          name: 'leaf',
          leaf: true
        }, {
          name: 'zone'
        }]

        resolve(data)
      }, 500)
    }
  }
}
</script>

<style scoped>

</style>
