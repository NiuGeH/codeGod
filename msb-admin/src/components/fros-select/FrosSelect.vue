<template>
    <span >
        <span v-if="tooltipShow">
             <el-tooltip :disabled="contentShow" placement="right-end" effect="light">
                 <div slot="content" style="white-space: pre-wrap;">{{content}}</div>
                <el-select
                    v-model="value"
                    :popper-append-to-body=true
                    :disabled="disabled"
                    :default="frosDefValue"
                    :multiple="multiple"
                    :clearable="clearable"
                    :filterable="filterable"
                    :filter-method="dataFilter"
                    :placeholder="placeholder"
                    :sortable="sortable"
                    :dictType="dictType"
                    :dictData="dictData"
                    :collapse-tags="collapseTags"
                    :default-first-option="frosEnterShow"
                    @change="emitChange"
                    @blur="emitBlur"
                    @focus="emitFocus"
                    @clear="frosSelectClear">
            <el-option
                    v-for="(item,index) in optionsCopy"
                    :key="index"
                    :label="item.key"
                    :value="item.value"
                    :disabled="item.disabled">
            </el-option>
        </el-select>
            </el-tooltip>
        </span>
        <span v-else>
            <el-select
                    v-model="value"
                    :popper-append-to-body=true
                    :disabled="disabled"
                    :default="frosDefValue"
                    :multiple="multiple"
                    :clearable="clearable"
                    :filterable="filterable"
                    :filter-method="dataFilter"
                    :placeholder="placeholder"
                    :sortable="sortable"
                    :dictType="dictType"
                    :dictData="dictData"
                    :collapse-tags="collapseTags"
                    :default-first-option="frosEnterShow"
                    @change="emitChange"
                    @blur="emitBlur"
                    @focus="emitFocus"
                    @clear="frosSelectClear">
            <el-option
                    v-for="(item,index) in optionsCopy"
                    :key="index"
                    :label="item.key"
                    :value="item.value"
                    :disabled="item.disabled">
            </el-option>
        </el-select>
        </span>
    </span>

</template>
<script>
    export default {
        components: {
            // 自定义组件
        },
        model: {
            prop: "value",
            event: 'sync'
        },
        props: {
            tooltipShow:Boolean, // 显示提示信息
            frosCode: Boolean, // 全角半角
            frosCase: Boolean, // 大小写
            disabled: Boolean,  // 是否禁用
            frosEnterShow:Boolean, // 是否显示回车事件
            frosBlurShow:Boolean, // 是否显示失去焦点事件
            frosDefValue: null,    // 设置默认值
            options: Array,   // select数据
            placeholder: String,  // 描述信息
            multiple: String, // TODO 支持多选，待优化为Boolean类型
            clearable: Boolean,    // 是否支持清空，只支持单选状态下清空
            filterable: String,    // 是否支持可搜索
            sortable: null,   // 排序参数对象 orderBy（升序/降序）、label根据什么字段排序
            isDict: Boolean,  // 是否翻译
            isLocalStorage: Boolean,  // 是否使用本地存储翻译
            dictType: String, // 数据字典根据那一列翻译
            dictData: null,    // 数据字典
            collapseTags: Boolean   // 多选时是否将选中值按文字的形式展示
        },
        data() {
            return {
                contentShow:false,
                content:null,// 提示内容
                value: [],  // 已选择数据
                optionsCopy: [], // 用于自定义过滤数据
                dicList: []  // 用于临时存储字典数据
            }
        },
        computed: {},
        watch: {
            options(val, oldVal) {
                if (val !== oldVal) {
                    this.options = val
                    this.optionsCopy = val
                    this.isDictionary() // 调用字典翻译逻辑
                }
            },
            value(val, oldVal) {
                if (val !== oldVal) {
                    this.value = val
                    this.contentText()
                    this.$emit('sync', this.value)
                }
                // 判断全角半角
                if (this.frosCode === true) {
                    this.valChangeCode(val)
                }
                // 判断大小写
                if (this.frosCase === true) {
                    this.valUpper()
                }

            },
            frosDefValue(val, oldVal) {
                if (val !== oldVal) {
                    this.value = val
                }
            }
        },
        mounted() {
            // this.contentText()
            // 初始化非空校验
            if (this.options != null) {
                this.optionsCopy = Object.assign(this.options)   //  用于记录当前搜索框值
            }
            this.isDictionary() // 调用字典翻译逻辑
            this.value = this.frosDefValue // 初始化默认值
            // 判断是否排序
            if (this.sortable != null) {
                this.sortableFunction()
            }

        },
        methods: {
            // 提示信息翻译
            contentText(){
                let list = this.optionsCopy
                let valList = this.value || ''
                let cList = []
                if(typeof (valList) != "string"){
                    for(let i=0;i<list.length;i++){
                        for(let n=0;n<valList.length;n++){
                            if(list[i].value == valList[n]){
                                cList.push(list[i].key)
                            }
                        }
                    }
                }
                this.content = cList.join(",")
            },

            // 大小写转换
            valUpper() {
                this.value = this.value.toUpperCase()
                // this.content = this.value
            },
            // 全角半角转换
            valChangeCode(val) {
                let tmp = ""
                for (let i = 0; i < val.length; i++) {
                    if (val.charCodeAt(i) == 12288) {
                        tmp += String.fromCharCode(val.charCodeAt(i) - 12256);
                        continue;
                    }
                    if (val.charCodeAt(i) > 65280 && val.charCodeAt(i) < 65375) {
                        tmp += String.fromCharCode(val.charCodeAt(i) - 65248);
                    } else {
                        tmp += String.fromCharCode(val.charCodeAt(i));
                    }
                }
                this.value = tmp
                console.log(val,tmp)
                // this.content = this.value

                // 空格
                // let newVal = this.value
                if (this.frostrim === 'ltrim') {
                    this.value = tmp.replace(/(^\s*)/g, "")
                    // this.content = this.value
                } else if (this.frostrim === 'rtrim') {
                    this.value = tmp.replace(/(\s*$)/g, "")
                    // this.content = this.value
                } else if (this.frostrim === 'lrtrim') {
                    this.value = tmp.replace(/(^\s*)|(\s*$)/g, "")
                    //  this.content = this.value
                } else if (this.frostrim === 'trim') {
                    this.value = tmp.replace(/\s/g, "")
                    // this.content = this.value
                    //this.value = tmp.replace(/(^　*)|(　*$)/g, "")
                }

            },
            // 清空下拉框数据
            clearFormValue(){
                if(this.multiple == undefined){
                    this.value = ''
                }else{
                    this.value = []
                }
            },
            // 回车
            /*frosEnter() {
                if(this.frosEnterShow){
                    let val = this.value
                    if (typeof (val) == "string") {
                        this.value = this.optionsCopy[0].value
                    }
                    //this.value = this.optionsCopy[0].value
                    this.$emit('frosEnter', this.value, event)
                }
            },*/
            emitChange() {
                this.$emit('getSelectValue', this.value, event)
                this.$emit('change', this.value)
                // 在筛选状态下选中数值后需要重置optionsCopy
                if(this.filterable != undefined && this.filterable != ''){
                    this.optionsCopy = this.options
                    this.isDictionary() // 调用字典翻译逻辑
                }
            },
            // 失去焦点事件
            emitBlur() {
                if(this.frosBlurShow){
                    let val = this.value
                    if (typeof (val) == "string") {
                        if(this.optionsCopy.length > 0){
                            this.value = this.optionsCopy[0].value
                        }
                    }
                    this.$emit('blur',this.value)
                }
                // 失去焦点如果输入内容没有搜索出值的话则清空value值
                if(this.optionsCopy.length == 0) {
                    if(this.multiple == undefined){
                        this.value = ''
                    }else{
                        this.value = []
                    }
                }
                // 在筛选状态下选中数值后需要重置optionsCopy
                if(this.filterable != undefined && this.filterable != ''){
                    this.optionsCopy = this.options
                    this.isDictionary() // 调用字典翻译逻辑
                }
            },
            emitFocus() {
                this.$emit('focus',this.value)
            },
            frosSelectClear() {
                this.$emit('frosSelectClear')
            },
            isDictionary: function () {
                if (this.isDict) {
                    if (this.dictData != null) {
                        // 单页字典数据翻译
                        this.dicList = this.dictData
                        this.getDictionary() // 调用翻译
                    } else {
                        // 全局字典数据翻译
                        if (this.isLocalStorage) {
                            let dictData = localStorage.getItem('FrosDictionary')
                            if (dictData != null) {
                                this.dicList = JSON.parse(dictData)[this.dictType]
                            }
                        } else {
                            this.dicList = this.$store.state.global.frosDictionary[this.dictType]
                        }
                        this.getDictionary() // 调用翻译
                    }
                }
            },
            // 数据字典翻译
            getDictionary() {
                // let dataDic = this.$store.state.global.frosDictionary
                // this.dicList= dataDic[this.dicType]
                if (this.optionsCopy.length === 0) {
                    this.optionsCopy = this.dicList
                } else {
                    let optionList = this.optionsCopy
                    let dicList = this.dicList
                    for (let i = 0; i < optionList.length; i++) {
                        for (let j = 0; j < dicList.length; j++) {
                            if (optionList[i].value === dicList[j].value) {
                                optionList[i].key = dicList[j].key
                            }
                        }
                    }
                }
            },


            /* searchDate(val){
                 this.valChangeCode(val)

             },*/

            // 数据筛选过滤
            dataFilter: function (val) {
                // TODO 将来在 将 全角转半角的逻辑抽取为公共方法  有输入和输出 不在 方法中改变 额外的值 就像这里的 this.value
                // 首先判断 val 有值的时候才去转换
                // this.valChangeCode(val)
                val =  this.fullToHalf(val);
                // let selVal = this.value
                // 当前下拉框 有值  切输入也有值。才会进行批评
                if (val) {    // 输入框有值时则执行以下过滤条件
                    this.value = val
                    this.optionsCopy = this.options.filter((item) => {
                        // if (!!~item.key.indexOf(selVal) || !!~item.key.toUpperCase().indexOf(selVal.toUpperCase()) || !!~item.value.indexOf(selVal) || !!~item.value.toUpperCase().indexOf(selVal.toUpperCase())) {
                        if (!!~item.key.indexOf(val) || !!~item.key.toUpperCase().indexOf(val.toUpperCase()) || !!~item.value.indexOf(val) || !!~item.value.toUpperCase().indexOf(val.toUpperCase())) {
                            return true
                        }
                    })
                } else if(val ==''){
                    this.value = ''
                    this.optionsCopy = this.options
                }else {
                    // this.value = selVal
                    // 如果输入框中数值为空时则还原数值
                    this.optionsCopy = this.options
                }
            },
            // 排序功能
            sortableFunction: function () {
                let orderby = this.sortable.orderby
                let type = this.sortable.type
                if (type === "desc" || type === "DESC") {
                    // 降序排列
                    this.optionsCopy = this._sort(this.optionsCopy, orderby, "desc")
                } else {
                    // 升序
                    this.optionsCopy = this._sort(this.optionsCopy, orderby, "asc")
                }
            },
            _sort: function (obj, orderBy, type) { //按照position排序方法
                var jsonList = [];
                var returnList = [];
                for (var i in obj) {
                    var json = {
                        'nodeId': i,
                        'position': obj[i][orderBy]
                    };
                    jsonList.push(json);
                }
                if (type == "asc") {
                    jsonList.sort(this._upSort);
                } else {
                    jsonList.sort(this._downSort);
                }
                for (var j = 0; j < jsonList.length; j++) {
                    returnList.push(obj[jsonList[j].nodeId]);
                }
                return returnList;
            },
            _upSort: function (a, b) { //升序排序
                return a["position"] > b["position"] ? 1 : a["position"] == b["position"] ? 0 : -1;
            },
            _downSort: function (a, b) { //降序排序
                return a["position"] < b["position"] ? 1 : a["position"] == b["position"] ? 0 : -1;
            },
            fullToHalf(val) {
                let ret = ''
                for (let i = 0; i < val.length; i++) {
                    if (val.charCodeAt(i) == 12288) {
                        ret += String.fromCharCode(val.charCodeAt(i) - 12256);
                        continue;
                    }
                    if (val.charCodeAt(i) > 65280 && val.charCodeAt(i) < 65375) {
                        ret += String.fromCharCode(val.charCodeAt(i) - 65248);
                    } else {
                        ret += String.fromCharCode(val.charCodeAt(i));
                    }
                }
                return ret
            }
        }
    }
</script>
<style scoped>
    .el-select {
        width: 100%;
    }
</style>
