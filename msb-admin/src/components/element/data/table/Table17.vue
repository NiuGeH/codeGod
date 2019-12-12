<template>
	<div>
		<!-- 将show-summary设置为true就会在表格尾部展示合计行。默认情况下，对于合计行，第一列不进行数据求合操作，而是显示「合计」二字（可通过sum-text配置），其余列会将本列所有数值进行求合操作，并显示出来。当然，你也可以定义自己的合计逻辑。使用summary-method并传入一个方法，返回一个数组，这个数组中的各项就会显示在合计行的各列中，具体可以参考本例中的第二个表格。 -->

		<el-table
		    :data="tableData6"
		    border
		    show-summary
		    style="width: 100%">
		    <el-table-column
		      prop="id"
		      label="ID"
		      width="180">
		    </el-table-column>
		    <el-table-column
		      prop="name"
		      label="姓名">
		    </el-table-column>
		    <el-table-column
		      prop="amount1"
		      sortable
		      label="数值 1">
		    </el-table-column>
		    <el-table-column
		      prop="amount2"
		      sortable
		      label="数值 2">
		    </el-table-column>
		    <el-table-column
		      prop="amount3"
		      sortable
		      label="数值 3">
		    </el-table-column>
		</el-table>

		<el-table
		    :data="tableData6"
		    border
		    height="200"
		    :summary-method="getSummaries"
		    show-summary
		    style="width: 100%; margin-top: 20px">
		    <el-table-column
		      prop="id"
		      label="ID"
		      width="180">
		    </el-table-column>
		    <el-table-column
		      prop="name"
		      label="姓名">
		    </el-table-column>
		    <el-table-column
		      prop="amount1"
		      label="数值 1（元）">
		    </el-table-column>
		    <el-table-column
		      prop="amount2"
		      label="数值 2（元）">
		    </el-table-column>
		    <el-table-column
		      prop="amount3"
		      label="数值 3（元）">
		    </el-table-column>
		</el-table>
	</div>
</template>

<script>
export default {
  name: 'Table17',
  data () {
		    return {
		        tableData6: [{
		          id: '12987122',
		          name: '王小虎',
		          amount1: '234',
		          amount2: '3.2',
		          amount3: 10
		        }, {
		          id: '12987123',
		          name: '王小虎',
		          amount1: '165',
		          amount2: '4.43',
		          amount3: 12
		        }, {
		          id: '12987124',
		          name: '王小虎',
		          amount1: '324',
		          amount2: '1.9',
		          amount3: 9
		        }, {
		          id: '12987125',
		          name: '王小虎',
		          amount1: '621',
		          amount2: '2.2',
		          amount3: 17
		        }, {
		          id: '12987126',
		          name: '王小虎',
		          amount1: '539',
		          amount2: '4.1',
		          amount3: 15
		        }]
		    }
    	},
    	methods: {
		    getSummaries (param) {
		        const { columns, data } = param
		        const sums = []
		        columns.forEach((column, index) => {
		          if (index === 0) {
		            sums[index] = '总价'
		            return
		          }
		          const values = data.map(item => Number(item[column.property]))
		          if (!values.every(value => isNaN(value))) {
		            sums[index] = values.reduce((prev, curr) => {
		              const value = Number(curr)
		              if (!isNaN(value)) {
		                return prev + curr
		              } else {
		                return prev
		              }
		            }, 0)
		            sums[index] += ' 元'
		          } else {
		            sums[index] = 'N/A'
		          }
		        })

		        return sums
		    }
  }
}
</script>

<style scoped>
	h3 {
        margin: 10px 0px;
    }
</style>
