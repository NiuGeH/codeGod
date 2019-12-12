<template>
  <div class="my-table">
     <!-- 显示合并行计算方法
     :summary-method="getSummaries"
      show-summary
      -->
    <el-table :data="data" :height="height">
      <HeaderColumn v-for="(item,index) in col" :key="index" :col="item"></HeaderColumn>
    </el-table >
    <!--分页-->
    <div class="pagebox">
            <span >
                <i title="刷新" class="el-icon-refresh" style="color:#2B579A" @click="refresh"></i>
            </span>
      <div class="pagelist">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="pageTotal"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
        </el-pagination>
      </div>
    </div>
  </div>
</template>

<script>
import HeaderColumn from './HeadColumn.vue'
export default {
  components: {
    HeaderColumn
  },
  props: {
    total: Number,
    height: {
      type: Number
    },
    col: {
      type: Array
    },
    data: {
      type: Array
    }
  },
  data () {
    return {
      currentPage: 1,
      pageTotal:[1,2,3,4],
      pageSize:1,
    }
  },
  methods:{
    // 表格刷新
    refresh: function () {
      this.$emit('reFresh');
    },
    // 分页
    handleSizeChange (val) {
      this.$emit("getPagesize",val)
      //console.log(`每页 ${val} 条`)
    },
    handleCurrentChange (val) {
      this.$emit("getCurrentpage",val)
      // console.log(`当前页: ${val}`)
    }
  }

}
</script>

<style scoped>

  .pagebox{
    margin-top: 20px;
    clear: both;
  }
  .pagelist{
    display: inline-block;
    float:right
  }
  .el-checkbox + .el-checkbox {
    margin-left:0px;
  }
  .grid-content {
    display: flex;
    align-items: center;
    height: 100px;
  }

  .grid-cont-right {
    flex: 1;
    text-align: center;
    font-size: 12px;
    color: #999;
  }

  .grid-num {
    font-size: 30px;
    font-weight: bold;
  }

  .grid-con-icon {
    font-size: 50px;
    width: 100px;
    height: 100px;
    text-align: center;
    line-height: 100px;
    color: #fff;
  }

  .grid-con-1 .grid-con-icon {
    background: rgb(45, 140, 240);
  }

  .grid-con-1 .grid-num {
    color: rgb(45, 140, 240);
  }

  .grid-con-2 .grid-con-icon {
    background: rgb(100, 213, 114);
  }

  .grid-con-2 .grid-num {
    color: rgb(45, 140, 240);
  }

  .grid-con-3 .grid-con-icon {
    background: rgb(242, 94, 67);
  }

  .grid-con-3 .grid-num {
    color: rgb(242, 94, 67);
  }

  .user-info {
    display: flex;
    align-items: center;
    padding-bottom: 20px;
    border-bottom: 2px solid #ccc;
    margin-bottom: 20px;
  }

  .user-avator {
    width: 120px;
    height: 120px;
    border-radius: 50%;
  }

  .user-info-cont {
    padding-left: 50px;
    flex: 1;
    font-size: 14px;
    color: #999;
  }

  .user-info-cont div:first-child {
    font-size: 30px;
    color: #222;
  }

  .user-info-list {
    font-size: 14px;
    color: #999;
    line-height: 25px;
  }

  .user-info-list span {
    margin-left: 70px;
  }

  .mgb20 {
    margin-bottom: 20px;
  }

  .todo-item {
    font-size: 14px;
  }

  .todo-item-del {
    text-decoration: line-through;
    color: #999;
  }

</style>
