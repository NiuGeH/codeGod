<template>
  <el-table-column :prop="col.prop"
                   :label="col.label"
                   align="left">
    <template v-if="col.children">
      <head-column v-for="(item, index) in col.children"
                   :key="index"
                   :col="item">
      </head-column>
    </template>
  </el-table-column>
</template>
<script>
export default {
  name: 'HeadColumn',
  props: {
    col: {
      type: Object
    }
  }
}
</script>
