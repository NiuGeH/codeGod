<template>
    <div class="friendSearchContainer">
        <!--双击事件-->
        <el-input v-if='dblclick' v-popover:popover v-model="input" autocomplete="off" class="form-control smartInput"
                  placeholder="" @dblclick.native="init" @change="emitChange"
                  @blur="emitBlur"
                  @focus="emitFocus"
                  @keydown.native="search" :id='id' :size="size"
                  suffix-icon="iconfont fros-icon-youxiajiaogouxuan"></el-input>
        <!--单击input-->
        <el-input v-else v-popover:popover v-model="input" autocomplete="off" class="form-control smartInput"
                  placeholder="" @click.native="init" @change="emitChange"
                  @blur="emitBlur"
                  @focus="emitFocus"
                  @keydown.native="search" :id='id' :size="size"
                  suffix-icon="iconfont fros-icon-youxiajiaogouxuan"></el-input>
        <div class="fros_smart">

            <el-popover placement="bottom-start" ref="popover" trigger="manual" v-model="popoverShow">
                <div v-show="searching" class="friendSearchList">
                    <div class="dataLength" style="margin-bottom: 10px">
                        <el-pagination
                                small
                                @size-change="handleSizeChange"
                                @current-change="handleCurrentChange"
                                :current-page.sync="props.pgconfig.currentPage"
                                :page-size="props.pgconfig.pageSize"
                                :layout="props.pgconfig.layout"
                                :total="totalRows">
                            <span style="color: #606266;">当前显示{{datafrom}}-{{datato}}条</span>
                        </el-pagination>
                    </div>
                    <el-table max-height='250' ref="multipleTable" :data="filtered" @cell-click="clickOne"
                              :cell-class-name="tableRowClassName" :row-style="selectedHighlight"
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
    import Base from '@/assets/js/base'
    import Bus from '@js/bus'
    import utils from '@js/utils'
    import FrosSectionColumn from '../fros-table/FrosSectionColumn.vue'

    export default {
        name: "FrosAutoComplete",
        extends: Base,
        props: ['props', 'frosDefValue', 'size'],
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
                popoverShow: false,
                dropCol: [],
                id: "",
                dblclick: Boolean,//是否支持双击
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
                datato: 0,
                isLoading: true
            };
        },
        computed: {
            listLength() {
                return this.filtered.length;
            },
            /* key() {
               return /(?:.*,)*(.*)$/.exec(this.input)[1];
             } */
        },
        mounted() {

            // 支持初始化参数值
            this.dblclick = this.props.dblclick

            this.input = this.frosDefValue || '';

            this.id = this.props.id || '';
            this.queryFields = this.props.queryFields
            this.frosCode = this.props.frosCode
            this.frosCase = this.props.frosCase
            this.multiple = this.props.multiple
            this.frostrim = this.props.frostrim
            //this.filtered = this.props.list
            this.dropCol = this.props.showColumn;
            this.currentPage = this.props.pgconfig.currentPage
            this.datafrom = (this.props.pgconfig.currentPage - 1) * this.props.pgconfig.pageSize + 1
            this.datato = this.props.pgconfig.currentPage * (this.props.pgconfig.pageSize)
            this.updateColWidth();
            if (this.multiple) {
                this.selectionShow = true
            }
        },
        methods: {
            clearFormValue: function () {
                this.input = null
            },
            init() {
                this.popoverShow = !this.popoverShow
                this.searching = true
                // 默认点击查询后台数据
                if (this.props.DefaultequestData) {
                    console.log(this.input)
                    this.requestData(this.input);
                }
                //初始化加载计算列宽自适应
                this.updateColWidth();

            },
            //向父组件暴露input方法
            emitChange(val) {
                this.$emit('getSelectValue', this.input, event)
                this.$emit('change', val)
            },
            emitBlur() {
                this.timer = setTimeout(() => {

                    this.searching = false;

                }, 200);
                this.$emit('blur',this.input)

            },
            emitFocus() {
                this.$emit('focus',this.input)

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
            //数组去重
            clear_repeat(oldArr) {
                var allArr = [];//新数组
                $.each(oldArr, function (i, v) {
                    var flag = true;
                    if (allArr.length > 0) {
                        $.each(allArr, function (n, m) {
                            if (allArr[n].id == oldArr[i].id) {
                                flag = false;
                            }

                        });
                    }

                    if (flag) {
                        allArr.push(oldArr[i]);
                    }

                });
                /* 	for(let i=0;i<oldArr.length;i++){
                        var flag = true;
                        if(allArr.length > 0){
                            for(let n=0;n<oldArr.length;n++){
                                if(allArr[n].id == oldArr[i].id){flag = false;};
                            }
                        };
                        if(flag){
                            allArr.push(oldArr[i]);
                        };
                    } */
                return allArr;
                console.log(allArr);
            },
            //多选事件
            handleSelectionChange(val, row) {
                console.log(val)
                if (val.length == 0) {
                    let keywordList = this.selectOption.filter(item => item.id !== row.id);

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
                    if (val[i].id == row.id) {
                        //合并去重
                        let json = keywordList.concat(this.selectOption);
                        keywordList = this.clear_repeat(json)
                        /*keywordList = Array.from(new Set(json)) */
                        if (keywordList.length > 0) {
                            let inputText = ''
                            for (let i = 0; i < keywordList.length; i++) {
                                //循环选中行获得input显示的值
                                inputText = inputText + keywordList[i][this.props.inputKey] + this.props.separator
                            }
                            this.input = inputText;
                            this.$emit('rowData', keywordList);
                        }
                        this.selectOption = keywordList
                    } else {
                        //反选中 删除操作
                        //console.log(this.selectOption)
                        let keywordList = this.selectOption.filter(item => item.id !== row.id);

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
                }
                //删除最后一个元素，删除原理同上边的反选中


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
            // 点击分页请求数据
            handleCurrentChange(currentPage) {
                clearTimeout(this.timer);
                this.props.pgconfig.currentPage = currentPage;    //动态改变
                this.datafrom = (currentPage - 1) * this.props.pgconfig.pageSize + 1
                this.datato = currentPage * (this.props.pgconfig.pageSize)
                this.requestData(this.input);
                this.scrollViewport();
                // this.searching = true;
            },
            // 切换展示条数
            handleSizeChange(pageSize) {
                this.props.pgconfig.pageSize = pageSize
                this.datafrom = (this.props.pgconfig.currentPage - 1) * pageSize + 1
                this.datato = this.props.pgconfig.currentPage * (pageSize)
                this.requestData(this.input)
            },
            /* 分页请求后台数据的方法 */
            requestData(val, keydown) {
                clearTimeout(this.timer);
                let keyword = val
                console.log(keyword);
                if (this.multiple) {
                    let strs = val.split(this.props.separator);
                    keyword = strs[strs.length - 1]
                }
                //去除空格操作
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
                let data = {
                    "orderBy": this.props.orderBy,
                    "queryType": this.props.queryType,
                    "keyword": keyword,
                    "pagingInfo": {
                        "pageSize": this.props.pgconfig.pageSize,
                        "currentPage": this.props.pgconfig.currentPage,
                    },
                    "queryFields": this.queryFields
                }

                utils.sendReq({
                    queryUrl: this.props.url,
                    req: {
                        method: 'post',
                        data: data,
                        success: res => {
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
                                        if (this.filtered[i].id == this.selectOption[j].id) {
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
            toggleSelection(rows) {
                if (rows) {
                    rows.forEach(row => {
                        this.$refs.multipleTable.toggleRowSelection(row);
                    });
                } else {
                    this.$refs.multipleTable.clearSelection();
                }
            },
            disappear() {
                this.searching = false;
                this.popoverShow = false;
                if (this.props.StrongCheck && this.multiple == false) {
                    //强校验模式清空
                    this.input = '';
                } else if (this.props.StrongCheck && this.multiple == true && this.filtered.length == 0) {
                    this.input = this.input
                    console.log(this.input)
                }
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

            // 失去焦点时关闭面板，主要是按下tab键切换时的作用，随之带来的是所有相关的事件都要清除该定时器

            // 在上下键索引后调整视口
            scrollViewport() {
                this.getIndex = this.focusIndex;
                /* let value = this.filtered[this.focusIndex][this.props.inputKey];
                this.input = value; */
            },
            // 联想搜索的主体功能函数，这里使用keydown是为了保证持续性的上下键能够保证执行
            search(e) {
                // 非搜索状态进行点击，则呼出面板
                if (!this.searching) {
                    this.searching = true;
					this.popoverShow = true;
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
                        this.popoverShow = false;
                    }
                } else {
                    // 延时搜索，降低卡顿
                    clearTimeout(this.timer);
                    this.timer = setTimeout(() => {
                        //请求后台接口进行可选项过滤
                        this.requestData(this.input);
                        if (this.multiple || this.dblclick) {
                            this.searching = true;
                            this.popoverShow = true;
                        }
                        // 前台进行可选项过滤
                        /* this.filtered = this.props.lists.filter(item => {
                let key = this.props.queryFields
                let aaa = '';
                for (var i = 0; i < key.length; i++) {

                    if (aaa == '') {
                        aaa = item[key[i].fieldName].toLowerCase().includes(this.key.toLowerCase());
                    } else {
                        aaa = aaa || item[key[i].fieldName].toLowerCase().includes(this.key.toLowerCase());
                    }
                }
                return aaa;
                this.fenye();
                this.focusIndex = 0;
            // return (item[key[0]].toLowerCase().includes(this.key.toLowerCase())||item[key[1]].toLowerCase().includes(this.key.toLowerCase()));
        }); */
                    }, 300);
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
                    this.search();
                    this.searching = false;
                    this.popoverShow = false;
                }
            },
            // 选择一个参数
            selectOne() {
                clearTimeout(this.timer);
                let value = this.filtered[this.focusIndex][this.props.inputKey];
                this.input = value;
                this.$emit('rowData', this.filtered[this.focusIndex]);
                this.searching = false;
                this.popoverShow = false;
            }
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

    .el-popover {
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
        box-shadow: 0 10px 10px rgba(0, 0, 0, .2);
        border: 1px solid #ccc;
        position: absolute;
        top: -10px;
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
