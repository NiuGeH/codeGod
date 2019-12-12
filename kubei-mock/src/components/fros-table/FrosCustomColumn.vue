<template>
    <div class="accessory">
        <el-table-column
            :fixed=addBtnList.fixed
            :label=addBtnList.label
            :width=addBtnList.width>
            <template slot-scope="scope">
                <el-button
                    v-for ="(item,index) in addBtnList.list"
                    :key="index"
                    v-show="item.showBtn"
                    @click="clkCall(item.methods)"
                    type="text"
                    size="small">
                    <i class="add-btn-i" :class="item.icon"></i>
                    {{item.title}}
                </el-button>
            </template>
        </el-table-column>
    </div>
</template>
<script>
export default {
  props: ['addBtnList'],
  methods: {
    clkCall (methodsWords) {
      this.$emit('clkCallBk', methodsWords)
    }
  }
}
</script>
