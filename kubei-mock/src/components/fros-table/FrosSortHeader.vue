<template>
    <div class="pad20">
        <div class="title">过滤列：{{title}}</div>
        <el-form>
            <el-input v-model="searchInfo" placeholder="搜索"/>
            <div class="elastic_padding click">
                <span @click="handleCheckAllChange()">全选</span>&nbsp;&nbsp;
                <span @click="clearCheckAll()">清除</span>
            </div>
            <div class="elastic_padding elastic_border">
                <el-checkbox-group v-model="submitData" @change="handleCheckedChange($event)">
                    <!--列表三个字段 status状态，id单独id，name名称-->
                    <p v-for="item in showDataList" :key="'type'+item.value" class="textP">
                        <el-checkbox v-if="item.name.length==0" :label="item.value" >空</el-checkbox>
                        <el-checkbox v-else :label="item.value" >{{item.name}}</el-checkbox>
                    </p>
                </el-checkbox-group>
            </div>
        </el-form>
        <div class="elastic_footer">
            <el-button type="primary" @click="submitMiddleData(true)">确定</el-button>
            <el-button @click="submitMiddleData(false)">取消</el-button>
        </div>
    </div>
</template>
<script>
    export default {
        name: 'FrosSortHeader',
        props: {
            title: String,
            searchData: {
                type: Array
            },
            propData: {
                type: String,
                default: ''
            },
            dataSubmit: {
                type: String,
                default: ''
            }
        },
        data () {
            return {
                bool: false, // 是否全选
                searchInfo: '', // 搜索内容
                dataList: [], // 循环原始数据
                showDataList: [], // 循环显示数据
                submitAllData: [], // 原始/全选提交数据
                submitData: [] // 提交数据
            }
        },
        watch: {
            searchData: function (val) {
                this.$nextTick(() => {
                    this.getData(val)
                })
            },
            searchInfo: function (newinfo, oldinfo) {
                if (newinfo !== oldinfo) {
                    this.clearCheckAll () // 清空选中状态
                    this.dataAll(newinfo)
                }
            }
        },
        mounted(){
            this.getData()
            this.handleCheckAllChange()   // 执行全选
        },
        methods: {
            // 初始化数据
            getData () {
                if (this.searchData !== '') {
                    this.dataList = JSON.parse(JSON.stringify(this.searchData))
                    this.showDataList = JSON.parse(JSON.stringify(this.searchData))
                    let showDataList = this.showDataList
                    for (let i=0; i < showDataList.length; i++) {
                        if(showDataList[i].name == null || showDataList[i].name == '' || showDataList[i].name == undefined) {
                            showDataList[i].name = '空'
                        }
                    }
                    this.showDataList = showDataList
                    this.dataAll()
                }
            },
            // 获取全选submit数据
            dataAll (info) {
                let self = this
                if (info) {
                    self.showDataList = []
                }
                self.dataList.map(function (item) {
                    if (!info) {
                        self.submitAllData.push(item.value)
                        self.showDataList = self.dataList
                    } else {
                        let itName = item.name.toLowerCase()  // 忽略大小写
                        info = info.toLowerCase()  // 忽略大小写
                        if (itName.indexOf(info) !== -1) {
                            self.submitAllData.push(item.value)
                            self.showDataList.push(item)
                        }
                    }
                })
            },
            submitMiddleData (bool) {
                let self = this
                if (bool) {
                    let data = {}
                    let newData = []
                    for (let i=0; i<self.searchData.length; i++) {
                        for (let j=0; j<self.submitData.length; j++) {
                            if(self.searchData[i].value === self.submitData[j]) {
                                newData.push(self.searchData[i].name)
                            }
                        }
                    }
                    data[self.propData] = newData
                    self.$emit('childByValue', data)
                    self.$emit('pClose', self.propData)
                } else {
                    self.$emit('pClose', self.propData)
                    this.clearCheckAll ()   // 执行清除
                    this.bool = false
                    this.handleCheckAllChange()   // 执行全选
                }
            },
            handleCheckAllChange () {
                this.filterData() // 数据去重
                let submitData = this.submitData
                let submitAllData = this.submitAllData
                if(submitData < 1) {
                    this.submitData = this.submitAllData
                }else if(submitData.length != submitAllData.length) {
                    for (let i=0;i<submitAllData.length; i++) {
                        if(submitData.indexOf(submitAllData[i]) == -1){
                            this.submitData.push(submitAllData[i])
                        }
                    }
                }else {
                    this.submitData = []
                }
            },
            // 非全选
            handleCheckedChange (val) {
                this.submitData = val
            },
            // 清除选中
            clearCheckAll () {
                this.bool = false
                this.submitData = []
            },
            // list数据去重
            filterData() {
                let submitAllData = this.submitAllData
                let arr = []
                arr.push(submitAllData[0])
                for(var i=1;i<submitAllData.length;i++){   //从数组第二项开始循环遍历此数组
                    //对元素进行判断：
                    //如果数组当前元素在此数组中第一次出现的位置不是i
                    //那么我们可以判断第i项元素是重复的，否则直接存入结果数组
                    if(arr.indexOf(submitAllData[i]) == -1){
                        arr.push(submitAllData[i]);
                    }
                }
                this.submitAllData = arr
            }
        }
    }
</script>

<style scoped>
    .pad20{
        padding: 12px;
    }
    .pad20 .title{
        padding-bottom: 15px;
        font-size: 15px;
    }
    .elastic_footer{
        padding-top: 10px;
    }
    .elastic_padding {
        padding: 6px 10px;
    }
    .elastic_border{
        border: 1px solid #eee;
    }
    .click>span {
        cursor: pointer;
    }
</style>
