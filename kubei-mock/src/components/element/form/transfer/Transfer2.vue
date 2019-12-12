<template>
  <div >
    <!-- 设置 filterable 为 true 即可开启搜索模式。默认情况下，若数据项的 label 属性包含搜索关键字，则会在搜索结果中显示。你也可以使用 filter-method 定义自己的搜索逻辑。filter-method 接收一个方法，当搜索关键字变化时，会将当前的关键字和每个数据项传给该方法。若方法返回 true，则会在搜索结果中显示对应的数据项。 -->
    <el-transfer
      filterable
      :filter-method="filterMethod"
      filter-placeholder="请输入城市拼音"
      v-model="value2"
      :data="data2">
    </el-transfer>
  </div>
</template>

<script>
export default {
  name: 'Transfer2',
  data () {
    const generateData2 = _ => {
      const data = []
      const cities = ['上海', '北京', '广州', '深圳', '南京', '西安', '成都']
      const pinyin = ['shanghai', 'beijing', 'guangzhou', 'shenzhen', 'nanjing', 'xian', 'chengdu']
      cities.forEach((city, index) => {
        data.push({
          label: city,
          key: index,
          pinyin: pinyin[index]
        })
      })
      return data
    }
    return {
      data2: generateData2(),
      value2: [],
      filterMethod (query, item) {
        return item.pinyin.indexOf(query) > -1
      }
    }
  }
}
</script>

<style scoped>
  .block{
    margin-right: 30px;
  }
</style>
