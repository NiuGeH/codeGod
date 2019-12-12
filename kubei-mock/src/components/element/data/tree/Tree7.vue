<template>
  <el-col :span="12">
    <!-- 可以通过两种方法进行树节点内容的自定义：render-content和 scoped slot。使用render-content指定渲染函数，该函数返回需要的节点区内容即可。渲染函数的用法请参考 Vue 文档。使用 scoped slot 会传入两个参数node和data，分别表示当前节点的 Node 对象和当前节点的数据。注意：由于 jsfiddle 不支持 JSX 语法，所以render-content示例在 jsfiddle 中无法运行。但是在实际的项目中，只要正确地配置了相关依赖，就可以正常运行。 -->

    <div class="custom-tree-container">
      <div class="block">
        <p>使用 render-content</p>
        <el-tree
          :data="data4"
          show-checkbox
          node-key="id"
          default-expand-all
          :expand-on-click-node="false"
          :render-content="renderContent">
        </el-tree>
      </div>
      <div class="block">
        <p>使用 scoped slot</p>
        <el-tree
          :data="data5"
          show-checkbox
          node-key="id"
          default-expand-all
          :expand-on-click-node="false">
          <span class="custom-tree-node" slot-scope="{ node, data }">
            <span>{{ node.label }}</span>
            <span>
              <el-button
                type="text"
                size="mini"
                @click="() => append(data)">
              Append
            </el-button>
              <el-button
                type="text"
                size="mini"
                @click="() => remove(node, data)">
              Delete
            </el-button>
            </span>
          </span>
        </el-tree>
      </div>
    </div>
  </el-col>
</template>

<script>
let id = 1000

export default {
  name: 'Tree7',
  data () {
    const data = [{
      id: 1,
      label: '一级 1',
      children: [{
        id: 4,
        label: '二级 1-1',
        children: [{
          id: 9,
          label: '三级 1-1-1'
        }, {
          id: 10,
          label: '三级 1-1-2'
        }]
      }]
    }, {
      id: 2,
      label: '一级 2',
      children: [{
        id: 5,
        label: '二级 2-1'
      }, {
        id: 6,
        label: '二级 2-2'
      }]
    }, {
      id: 3,
      label: '一级 3',
      children: [{
        id: 7,
        label: '二级 3-1'
      }, {
        id: 8,
        label: '二级 3-2'
      }]
    }]
    return {
      data4: JSON.parse(JSON.stringify(data)),
      data5: JSON.parse(JSON.stringify(data))
    }
  },

  methods: {
    append (data) {
      const newChild = { id: id++, label: 'testtest', children: [] }
      if (!data.children) {
        this.$set(data, 'children', [])
      }
      data.children.push(newChild)
    },

    remove (node, data) {
      const parent = node.parent
      const children = parent.data.children || parent.data
      const index = children.findIndex(d => d.id === data.id)
      children.splice(index, 1)
    },

    renderContent (h, { node, data, store }) {
      return (
        <span class="custom-tree-node">
          <span>{node.label}</span>
          <span>
            <el-button size="mini" type="text" on-click={ () => this.append(data) }>Append</el-button>
            <el-button size="mini" type="text" on-click={ () => this.remove(node, data) }>Delete</el-button>
          </span>
        </span>)
    }
  }
}
</script>

<style scoped>
  .custom-tree-node {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: space-between;
    font-size: 14px;
    padding-right: 8px;
  }
</style>
