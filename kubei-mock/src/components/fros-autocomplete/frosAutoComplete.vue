<template>
    <div class="friendSearchContainer">
       <!-- {{filtered}}-->
        <!--双击事件-->
        <el-input
                v-if='dblclick'
                v-popover:popover
                v-model="input"
                placeholder=""
                autocomplete="off"
                :id='id'
                :ref="id"
                :size="size"
                :disabled="disabled"
                @dblclick.native="init"
                @change="emitChange"
                @blur="emitBlur"
                @focus="emitFocus"
                @keydown.native="search"
                class="form-control smartInput"
                suffix-icon="iconfont fros-icon-youxiajiaogouxuan">
        </el-input>

        <!--单击input-->
        <el-input
                v-else
                v-popover:frosAutoCompletePopover
                v-model="input"
                placeholder=""
                autocomplete="off"
                :id='id'
                :ref="id"
                :size="size"
                :disabled="disabled"
                @click.native="init"
                @blur="emitBlur"
                @focus="emitFocus"
                @change="emitChange"
                @keydown.native="search"
                class="form-control smartInput"
                suffix-icon="iconfont fros-icon-youxiajiaogouxuan">
        </el-input>
        <div class="fros_smart">
            <el-popover placement="bottom-start" class="frosAutoCompletePopover" ref="frosAutoCompletePopover" trigger="manual" v-model="facPopoverShow">
                <div v-show="searching" class="friendSearchList">
                    <div class="dataLength" style="margin-bottom: 10px">
                        <el-pagination
                                small
                                :current-page.sync="props.pgconfig.currentPage"
                                :page-size="props.pgconfig.pageSize"
                                :layout="props.pgconfig.layout"
                                :total="totalRows"
                                @size-change="handleSizeChange"
                                @current-change="handleCurrentChange">
                            <span style="color: #606266;" v-if='pageDataLength==0'>当前显示0条</span>
                            <span style="color: #606266;" v-else>当前显示{{datafrom}}-{{datato}}条</span>
                        </el-pagination>
                    </div>
                    <el-table
                            max-height='250'
                            ref="multipleTable"
                            v-show="frosAutoCompleteTableShow"
                            :data="filtered"
                            :cell-class-name="tableRowClassName"
                            :row-style="selectedHighlight"
                            @cell-click="clickOne"
                            @select="handleSelectionChange">
                        <!--自定义添加多选列-->
                        <fros-section-column :selectionShow="selectionShow">
                        </fros-section-column>
                        <el-table-column
                                v-for="(item, index) in dropCol"
                                :key="index"
                                :width="(props.columnAuto?colWidth[item.prop]:item.width)"
                                :prop="item.prop"
                                :sortable="item.sortable"
                                :label="item.label">
                        </el-table-column>
                    </el-table>
                </div>
                <div v-show="searching" class="friendSearchModal" @click="disappear"></div>
            </el-popover>
        </div>
        <span ref="dddd" id="dddd"
              style='visibility: hidden; position: absolute; top: 0; left: 0; white-space: nowrap;'>{{tempText}}</span>
    </div>
</template>

<script>
    import utils from '@js/utils'
    import FrosSectionColumn from '@/components/fros-table/FrosSectionColumn.vue'
    export default {
        props: {
            props: null,    // 配置参数
            frosDefValue: null, // 默认值
            size: null,  // 输入框规格，medium / small / mini，默认medium
            disabled: Boolean   // 禁用
        },
        model: {
            prop: "input",
            event: 'sync'
        },
        components: {
            FrosSectionColumn
        },
        data() {
            return {
                //参数选项配置
                tempText: "",
                colWidth: null,
                queryFields: [],
                facPopoverShow: false,  // popover提示框
                dropCol: [],
                id: "",
                dblclick: false,//是否支持双击
                searching: false,//显示检索框
                timer: null,
                filtered: [],//获取后台的数据
                totalRows: 0,
                currentPage: 0,
                input: '',
                focusIndex: 0,
                invalidData: '',
                getIndex: 0,
                frosCode: true,//全半角转换
                frosCase: true,//大小写转换
                multiple: false,//是否多选
                selectionShow: false,	//多选列是否显示
                selectOption: [],//记录多选行数据
                datafrom: 0,//
                pageDataLength:null, // 初始化表格的长度
                datato: 0,
                isLoading: true,
                frosAutoCompleteTableShow: false,    // 用于控制在为加载出数据时隐藏table框
                isInputOther: false // 用于判断是切入联想是否做输入搜索操作
            };
        },
        watch: {
            input(val) {
                // 判断全角半角转换
                if (this.frosCode == true) {
                    if (this.input != null) {
                        this.valChangeCode(this.input)
                    }
                }
                // 判断大小写
                if (this.frosCase == true) {
                    if (this.input != null) {
                        this.valUpper()
                    }
                }
                //多选状态监听删除操作
                if (this.multiple == true && this.selectOption.length > 0 && val != '') {
                    //input的值和勾选的值做对比，相同的保存，不同的删除选项
                    let strsItem = val.split(this.props.separator);
                    let arrList = []
                    for (let i = 0; i < this.selectOption.length; i++) {
                        for (let j = 0; j < strsItem.length; j++) {
                            if (this.selectOption[i][this.props.inputKey] == strsItem[j]) {
                                arrList.push(this.selectOption[i])
                            }
                        }
                    }
                    this.selectOption = arrList
                    this.$emit('rowData', this.selectOption);
                }
                // 触发标签内声明的sync函数，用于传递数据给父组件
                this.$emit('sync', this.input);
                // 点选支持change事件
                this.$emit('change',this.input)
            },
            frosDefValue: {
                deep: true,
                handler: function (val) {
                    this.input = val
                }
            },
            props: {
                deep: true,
                handler: function (val) {
                    this.input = this.frosDefValue
                    this.queryFields = this.props.queryFields
                }
            },
            filtered: {
                deep: true,
                handler: function (val) {
                    this.filtered = val
                    this.pageDataLength= this.filtered.length
                    // 计算分页 当前页数
                    if(this.pageDataLength<this.props.pgconfig.pageSize){
                        this.datato = this.props.pgconfig.currentPage * (this.props.pgconfig.pageSize)+this.pageDataLength - this.props.pgconfig.pageSize
                    }else{
                        this.datato = this.props.pgconfig.currentPage * (this.props.pgconfig.pageSize)+this.pageDataLength - this.props.pgconfig.pageSize
                    }
                }
            },

        },
        computed: {
            listLength() {
                return this.filtered.length;
            },
        },
        mounted() {
            // 支持初始化参数值
            this.dblclick = this.props.dblclick
            this.input = this.frosDefValue || '';
            this.id = this.props.id || '';
            if(this.props.queryFields != null){
                this.queryFields = JSON.parse(JSON.stringify(this.props.queryFields))
            }
            // this.queryFields = this.props.queryFields
            this.frosCode = this.props.frosCode
            this.frosCase = this.props.frosCase
            this.multiple = this.props.multiple
            this.frostrim = this.props.frostrim
            //this.filtered = this.props.list
            this.dropCol = this.props.showColumn;
            this.currentPage = this.props.pgconfig.currentPage
            this.datafrom = (this.props.pgconfig.currentPage - 1) * this.props.pgconfig.pageSize + 1
            // 分页显示当前页码
            if(this.pageDataLength<this.props.pgconfig.pageSize){
                this.datato = this.props.pgconfig.currentPage * (this.props.pgconfig.pageSize)+this.pageDataLength - this.props.pgconfig.pageSize
            }else{
                this.datato = this.props.pgconfig.currentPage * (this.props.pgconfig.pageSize)+this.pageDataLength - this.props.pgconfig.pageSize
            }
           // this.datato = this.props.pgconfig.currentPage * (this.props.pgconfig.pageSize)
            this.updateColWidth();
            if (this.multiple) {
                this.selectionShow = true
            }
        },
        methods: {
            // 捕捉点击事件，初始化联想控件下拉框。
            init() {
                if(!this.disabled){
                    this.frosAutoCompleteTableShow = false // 隐藏联想table展示区域，避免请求闪刷数据
                    this.facPopoverShow = true  // 打开popover提示框
                    this.searching = true
                    this.isInputOther = false   // 初始化联想控件时，将是否输入置为false
                    // 重置当前页以及当前显示页数据
                    this.props.pgconfig.currentPage = this.currentPage
                    this.datafrom = (this.props.pgconfig.currentPage - 1) * this.props.pgconfig.pageSize + 1

                   // this.datato = this.props.pgconfig.currentPage * (this.props.pgconfig.pageSize)

                    // 默认点击查询后台数据
                    if (this.props.DefaultequestData) {
                        this.requestData(this.input);
                    }else if(this.input != null && this.input != ''){
                        this.requestData(this.input);
                    }else {
                        this.filtered = []
                    }
                    // 初始化加载计算列宽自适应
                    this.updateColWidth();
                }
            },
            // 在上下键索引后调整视口
            scrollViewport() {
                this.getIndex = this.focusIndex;
            },

            // 联想搜索的主体功能函数，这里使用keydown是为了保证持续性的上下键能够保证执行
            search(e) {
                // ctrl, shift, alt, CAPSLK(大小写), esc, f1-f12 ...
                if(e.ctrlKey || e.shiftKey || e.altKey || e.keyCode ==20 || e.keyCode ==27 || (e.keyCode >113 && e.keyCode< 123) ) {
                    return false;
                }
                if(e.keyCode === 9){
                    //  tab切换时如果是强校验则进入执行处理逻辑
                    if(this.props.StrongCheck){
                        // 执行了搜索操作后进入其他判断
                        if(this.isInputOther){
                            if(this.filtered.length != 1){
                                // 如果联想出来的数据为空或者大于1，则清空value
                                this.input = ''
                            }else {
                                // 如果联想出的数据等于1，则赋值
                                if(this.input.length != 0) {
                                    this.input = this.filtered[this.focusIndex][this.props.inputKey]
                                }
                            }
                        }
                    }
                    this.timer = setTimeout(() => {
                        this.searching = false;
                        this.facPopoverShow = false;
                        this.frosAutoCompleteTableShow = false  // 失去焦点时将默认隐藏联想数据table置为false
                        this.isInputOther = false
                    }, 200);
                    // 如果是tab键切换则不作任何操作
                    return false
                }else {
                    this.isInputOther = true // 切入联想控件并执行搜索，用来判断是否是临时切入不做搜索
                }
                // 非搜索状态进行点击，则呼出面板
                if (!this.searching) {
                    this.searching = true;
                    this.facPopoverShow = true;
                }
                e = e || window.event;
                // 通过上下键和回车选择
                if (e.keyCode === 38) {
                    this.focusIndex = (this.focusIndex - 1 + this.listLength) % this.listLength;
                    this.scrollViewport();
                } else if (e.keyCode === 40) {
                    this.focusIndex = (this.focusIndex + 1 + this.listLength) % this.listLength;
                    this.scrollViewport();
                } else if (e.keyCode === 37) {
                    //this.prevClick();
                } else if (e.keyCode === 39) {
                    //this.nextClick();
                } else if (e.keyCode === 13) {
                    this.isInputOther = false   // 失去焦点将用于判断是切入联想是否做输入搜索操作置为false
                    //this.requestData(this.input,'13')
                    if (this.multiple) {
                        //多选
                        this.$refs.multipleTable.toggleRowSelection(this.filtered[this.focusIndex], true)
                        this.$emit('rowData', this.filtered[this.focusIndex])
                    } else {
                        //单选 强弱校验判断是否清空input的值
                        if(this.filtered.length > 0){
                            let value = this.filtered[this.focusIndex][this.props.inputKey]
                            this.input = value;
                            this.$emit('rowData', this.filtered[this.focusIndex]);
                        }else if(this.props.StrongCheck){
                            this.input = '';
                        }
                        this.searching = false;
                        this.facPopoverShow = false;
                    }
                } else {
                    // 延时搜索，降低卡顿
                    clearTimeout(this.timer);
                    this.timer = setTimeout(() => {
                        //请求后台接口进行可选项过滤
                        this.requestData(this.input);
                        if (this.multiple || this.dblclick) {
                            this.searching = true;
                            this.facPopoverShow = true;
                        }
                    }, 300);
                }
            },
            //根据列值列宽自适应
            updateColWidth() {
                let that = this
                let _W = {}
                this.dropCol.forEach(function (nn) {
                    that.tempText = nn.label
                    that.$refs.dddd.textContent = nn.label
                    // that.$set(_W, nn.prop, that.$refs.dddd.offsetWidth + 70)
                    // TODO lishujuan 减去多余的留白
                    if(nn.sortable){
                        that.$set(_W, nn.prop, that.$refs.dddd.offsetWidth + 52)
                    }else{
                        that.$set(_W, nn.prop, that.$refs.dddd.offsetWidth + 28)
                    }
                });
                // 根据是否可编辑表格状态的变更列宽
                if (this.filtered.length > 0) {
                    this.filtered.forEach(function (n) {
                        that.dropCol.forEach(function (nn) {
                            let val = _W[nn.prop] || "auto"
                            that.tempText = n[nn.prop]
                            //that.$nextTick(function() {
                            that.$refs.dddd.textContent = n[nn.prop]
                            // that.$set(_W, nn.prop, Math.max(that.$refs.dddd.offsetWidth + 40, val == "auto" ? 0 : val))
                            // TODO lishujuan 减去多余的留白
                            that.$set(_W, nn.prop, Math.max(that.$refs.dddd.offsetWidth + 28, val == "auto" ? 0 : val))
                            //})
                        });
                    })
                }
                this.colWidth = _W;
            },
            tableRowClassName({row, rowIndex}) {
                //把每一行的索引放进row
                row.index = rowIndex;
            },
            selectedHighlight({row, rowIndex}) {
                if ((this.getIndex) === rowIndex) {
                    return {
                        "background-color": "#CAE1FF"
                    };
                }
            },
            clickOne(row) {
                this.getIndex = row.index
                clearTimeout(this.timer);
                if (this.multiple) {
                    //多选状态
                } else {
                    this.focusIndex = row.index;
                    //单选状态
                    let value = row[this.props.inputKey];
                    this.input = value;
                    //返回选中的行数据
                    this.$emit('rowData', row);
                    this.searching = false;
                    this.facPopoverShow = false;
                }
            },
            // 选择一个参数
            selectOne() {
                clearTimeout(this.timer);
                let value = this.filtered[this.focusIndex][this.props.inputKey];
                this.input = value;
                this.$emit('rowData', this.filtered[this.focusIndex]);
                this.searching = false;
                this.facPopoverShow = false;
            },
            // 点击分页请求数据
            handleCurrentChange(currentPage) {
                clearTimeout(this.timer);
                this.props.pgconfig.currentPage = currentPage;    //动态改变
                this.datafrom = (currentPage - 1) * this.props.pgconfig.pageSize + 1
                this.datato = currentPage * (this.props.pgconfig.pageSize)
                // 实现翻页光标不消失
                this.$nextTick(()=>{
                    this.$refs[this.id].focus()
                })
                this.requestData(this.input);
                this.scrollViewport();
                // this.searching = true;
            },
            // 切换展示条数
            handleSizeChange(pageSize) {
                this.props.pgconfig.pageSize = pageSize
                this.datafrom = (this.props.pgconfig.currentPage - 1) * pageSize + 1
                this.datato = this.props.pgconfig.currentPage * (pageSize)
                // 实现翻页光标不消失
                this.$nextTick(()=>{
                    this.$refs[this.id].focus()
                })
                this.requestData(this.input)
            },
            handleSelectionChange(val, row) {
                let uniqueKey = this.props.inputKey;
                if (val.length == 0) {
                    let keywordList = this.selectOption.filter(item => item[uniqueKey] !== row[uniqueKey]);

                    if (keywordList.length > 0) {
                        let inputText = ''
                        for (let i = 0; i < keywordList.length; i++) {
                            inputText = inputText + keywordList[i][this.props.inputKey] + this.props.separator
                        }
                        this.input = inputText;
                        this.$emit('rowData', keywordList);
                    }
                    this.selectOption = keywordList
                }
                let keywordList = val
                for (let i = 0; i < val.length; i++) {
                    //选中
                    if (val[i][uniqueKey] == row[uniqueKey]) {
                        //合并去重
                        let json = this.selectOption.concat(keywordList);
                        keywordList = this.clear_repeat(json)
                        /*keywordList = Array.from(new Set(json)) */
                        if (keywordList.length > 0) {
                            let inputText = ''
                            for (let i = 0; i < keywordList.length; i++) {
                                //循环选中行获得input显示的值
                                inputText = inputText + keywordList[i][uniqueKey] + this.props.separator
                            }
                            this.input = inputText;
                            this.$emit('rowData', keywordList);
                        }
                        this.selectOption = keywordList
                    } else {
                        //反选中 删除操作
                        //console.log(this.selectOption)
                        let keywordList = this.selectOption.filter(item => item[uniqueKey] !== row[uniqueKey]);
                        if (keywordList.length > 0) {
                            let inputText = ''
                            for (let i = 0; i < keywordList.length; i++) {
                                inputText = inputText + keywordList[i][uniqueKey] + this.props.separator
                            }
                            this.input = inputText;
                            this.$emit('rowData', keywordList);
                        }
                        this.selectOption = keywordList
                    }
                }
                //删除最后一个元素，删除原理同上边的反选中
            },
            //数组去重
            clear_repeat(oldArr) {
                let uniqueKey = this.props.inputKey;
                // 根据数组中对象的id去重
                return oldArr.reduce(function(accumulator, currentValue){
                    // 根据 传入的 inputkey去重  原来使用的id 在业务场景中可能是不存在的
                    let findex = accumulator.find((ele) => ele[uniqueKey] === currentValue[uniqueKey])
                    if(findex ===undefined){
                        accumulator.push(currentValue)
                    }
                    return accumulator;
                }, [])
            },
            // 大小写转换
            valUpper() {
                this.input = this.input.toUpperCase()
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
                    }
                    else {
                        tmp += String.fromCharCode(val.charCodeAt(i));
                    }
                }
                this.input = tmp
            },
            // 动态添加queryFields
            appendQueryFields(val){
                if(val != null){
                    this.queryFields.push.apply(this.queryFields,val)
                }
            },
            // 请求后台参数
            requestData(val, keydown) {
                let uniqueKey = this.props.inputKey;
                // this.isInputOther = true   // 请求后台则将联想改为触发。
                clearTimeout(this.timer);
                let keyword = val
                if (this.multiple) {
                    let strs = val.split(this.props.separator);
                    keyword = strs[strs.length - 1]
                }
                // 去除空格操作
                if (keyword != null) {
                    if (this.frostrim === 'ltrim') {
                        keyword = keyword.replace(/(^\s*)/g, "")
                    } else if (this.frostrim === 'rtrim') {
                        keyword = keyword.replace(/(\s*$)/g, "")
                    } else if (this.frostrim === 'lrtrim') {
                        keyword = keyword.replace(/(^\s*)|(\s*$)/g, "")
                    } else if (this.frostrim === 'trim') {
                        keyword = keyword.replace(/\s/g, "")
                    }
                }

                let temp = JSON.parse(JSON.stringify(this.props.queryFields))
                this.queryFields = temp   // 先重置搜索条件，后执行append操作
                // 检测是否有动态修改queryFields参数
                this.$emit('appendQueryFields')

                let data = {
                    "orderBy": this.props.orderBy,
                    "queryType": this.props.queryType,
                    "keyword": keyword,
                    "pagingInfo": {
                        "pageSize": this.props.pgconfig.pageSize,
                        "currentPage": this.props.pgconfig.currentPage,
                    },
                    "queryFields": this.queryFields,
                }
                utils.sendReq({
                    queryUrl: this.props.url,
                    req: {
                        method: 'post',
                        data: data,
                        success: res => {
                            this.frosAutoCompleteTableShow = true   // 当请求成功返回后显示联想table
                            let preSearching = this.searching;
                            this.filtered = res.data.data.dataList
                            this.totalRows = res.data.data.totalRows
                            this.focusIndex = 0;
                            //重新列宽自适应
                            this.updateColWidth();
                            //数据	更新立即执行
                            this.$nextTick(function () {
                                //对比最新数据是否有已经选中的值，有的进行勾选操作
                                if (this.selectOption.length > 0) {
                                    let inputText = ''
                                    for (let i = 0; i < this.selectOption.length; i++) {
                                        //循环选中行获得input显示的值
                                        inputText = inputText + this.selectOption[i][this.props.inputKey] + this.props.separator
                                    }
                                    this.input = inputText;
                                }
                                //var list = [];
                                for (let i = 0; i < this.filtered.length; i++) {
                                    for (let j = 0; j < this.selectOption.length; j++) {
                                        // 根据 传入的 inputkey去重  原来使用的id 在业务场景中可能是不存在的
                                        if (this.filtered[i][uniqueKey] == this.selectOption[j][uniqueKey]) {
                                            //有选中状态的数据进行下一步勾选动作
                                            this.$refs.multipleTable.toggleRowSelection(this.filtered[i], true)
                                        }
                                    }
                                }
                                //this.toggleSelection(list)
                                //
                            })
                        }
                    }
                })
            },
            // 自定义组件清空表单
            clearFormValue() {
                this.input = null
            },
            disappear() {
                this.searching = false;
                this.facPopoverShow = false;
                if (this.props.StrongCheck && this.filtered.length != 1) {
                    // 用于判断是否是通过输入搜索的内容
                    if(this.isInputOther){
                        // 强校验模式,如果联想出来的数据为空或者大于1，则清空value
                        this.input = ''
                    }
                } else if (this.props.StrongCheck && this.multiple == false && this.filtered.length == 1) {
                    // 强校验模式,如果联想出的数据等于1，则赋值
                    if(this.input.length != 0) {
                        this.input = this.filtered[this.focusIndex][this.props.inputKey]
                    }
                } else if (this.props.StrongCheck && this.multiple == true && this.filtered.length == 0) {
                    this.input = this.input
                }
                this.isInputOther = false   // 失去焦点将用于判断是切入联想是否做输入搜索操作置为false
            },
            // 失去焦点触发事件
            emitBlur(event) {
                /*this.timer = setTimeout(() => {
                    this.searching = false;
                    this.facPopoverShow = false;
                    this.frosAutoCompleteTableShow = false  // 失去焦点时将默认隐藏联想数据table置为false
                }, 200);*/
                this.$emit('blur',event)
            },
            // 获取焦点触发事件
            emitFocus(event) {
                this.$emit('focus',event)
            },
            // input框值发生改变触发事件
            emitChange(val) {
                this.$emit('change',val)
            }
        }
    }
</script>

<style>
    .fros-icon-youxiajiaogouxuan {
        font-size: 20px;
        color: #2b579a;
    }

    .smartInput .el-input__suffix {
        right: 0;
        top: 7px;
    }

    .frosAutoCompletePopover {
        max-height: 320px;
        /* (高度自行选择) */
        /* //overflow: auto; */
        padding: 0;
    }

    .fl {
        float: left;
    }

    .fr {
        float: right;
    }

    .fbtn li {
        float: left;
    }

    .smartInput {
        width: 100%;
    }

    .friendSearchContainer {
        position: relative;
    }



    .friendSearchList {
        width: auto;
        max-height: 315px;
        overflow: auto;
        background: #fff;
        z-index: 10;
        position: relative;
        /*  box-shadow: 0 10px 10px rgba(0, 0, 0, .2);
         border: 1px solid #ccc;
         position: absolute;
         top: -10px;
         left: 0px;*/
    }

    .dataLength {
        color: #000;
    }

    .friendSearchList tr {
        padding: 3px 12px;
    }

    .friendSearchList table.gridtable tbody tr:hover {
        background-color: #36bc7f;
        color: #fff;
    }

    .friendSearchList table.gridtable tbody tr.active {
        background: #337ab7;
        color: #fff;
    }

    .friendSearchList table.gridtable tbody tr.hover {
        background-color: #36bc7f;
        color: #fff;
    }

    .friendSearchList table.gridtable tbody tr.active:hover {
        background-color: #36bc7f;
    }
    .friendSearchModal {
        position: fixed;
        top: 0;
        left: 0;
        height: 100%;
        width: 100%;
        z-index: 1;
    }

    .el-table th,
    .el-table td {
        padding: 2px 0;
    }
</style>
