<template>
  <div>
    <el-table :data="tableData20" class="tb-edit" style="width: 100%" highlight-current-row
              @row-click="handleCurrentChange">

      <el-table-column label="#" width="50" header-align="center">
        <template slot-scope="{row,$index}">
          <span>{{$index + 1}}</span>
        </template>
      </el-table-column>
      <el-table-column label="日期" width="180">
        <template slot-scope="scope">
          <el-input size="small" v-model="scope.row.date" placeholder="请输入内容"
                    @change="handleEdit(scope.$index, scope.row)"></el-input>
          <span>{{scope.row.date}}</span>
        </template>
      </el-table-column>
      <el-table-column label="姓名" width="180">
        <template slot-scope="scope">
          <el-input size="small" v-model="scope.row.name" placeholder="请输入内容"
                    @change="handleEdit(scope.$index, scope.row)"></el-input>
          <span>{{scope.row.name}}</span>
        </template>
      </el-table-column>
      <el-table-column prop="address" label="地址">
        <template slot-scope="scope">
          <el-input size="small" v-model="scope.row.address" placeholder="请输入内容"
                    @change="handleEdit(scope.$index, scope.row)"
                    @keyup.tab.native="tabFunction(scope.$index, scope.row)"></el-input>
          <span>{{scope.row.address}}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <br>
    数据:{{tableData20}}
  </div>
</template>

<script>
export default {
  name: 'Table20',
  data () {
    return {
      tableData20: [{
        date: '2016-05-02',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      }, {
        date: '2016-05-04',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1517 弄'
      }, {
        date: '2016-05-01',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1519 弄'
      }, {
        date: '2016-05-03',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1516 弄'
      }]
    }
  },
  methods: {
    handleCurrentChange (row, event, column) {
      console.log(row, event, column, event.currentTarget)
    },
    handleEdit (index, row) {
      console.log(index, row)
    },
    handleDelete (index, row) {
      console.log(index, row)
    },
    tabFunction (index, row) {
      alert('huiche')
      this.handleEdit(index + 1, this.tableData20[index + 1])
      // tabak="tabclass"
      /* $("[tabak='tabclass']").bind('keyup', function(event) {
                  if (event.keyCode == "9") {
                      alert("提示框"+index);
                  }
              }); */
    }
  }
}

</script>

<style scoped>

  h3 {
    margin: 10px 0px;
  }

  .tb-edit .el-input {
    display: none
  }

  .tb-edit .current-row .el-input {
    display: block
  }

  .tb-edit .current-row .el-input + span {
    display: none
  }
</style>
