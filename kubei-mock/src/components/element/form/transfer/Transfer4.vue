<template>
  <div >
    <!-- 本例中的数据源没有 key 和 label 字段，在功能上与它们相同的字段名为 value 和 desc。因此可以使用props 属性为 key 和 label 设置别名。 -->
    <el-transfer
      v-model="value5"
      :props="{
      key: 'value',
      label: 'desc'
    }"
      :data="data3">
    </el-transfer>
  </div>
</template>

<script>
export default {
  name: 'Transfer4',
  data () {
    const generateData3 = _ => {
      const data = []
      for (let i = 1; i <= 15; i++) {
        data.push({
          value: i,
          desc: `备选项 ${i}`,
          disabled: i % 4 === 0
        })
      }
      return data
    }
    return {
      data3: generateData3(),
      value5: []
    }
  }
}
</script>

<style scoped>
  h3 {
    margin: 10px 0px;
  }
  .block{
    margin-right: 30px;
  }
</style>
