<template>
<div>
        <!--基于原始版本
            添加表格右键菜单
            添加表格提示框(样式没法更改)
        -->
        <el-form autocomplete="off" :model="formData"
                 :rules="rules" ref="ruleForm2" class="demo-ruleForm FrosTableFrom"
                 :validate-on-rule-change="false" onsubmit="return false;" size="mini" @validate="aaa">
            <div class="frosBox">
                <!--右键菜单事件-->
                <div v-if="rightListShow" id="contextMenu" @contextmenu="showMenu" v-show="rowContextmenuVisible"
                     style="width: 100%;position:relative;top:40px;left:0;z-index:99">
                    <vue-context-menu :contextMenuData="contextMenuData"
                                      @savedata="savedata"
                                      @newdata="newdata"
                                      @deldata="deldata"
                                      @frosDynamicFormModelType="frosDynamicFormModelType">
                    </vue-context-menu>
                </div>
                <!--右键菜单-->
                <el-table
                        :row-key='getRowKeys'
                        :expand-row-keys="expands"
                        @cell-mouse-enter="cellMouseEnter"
                        highlight-current-row
                        :row-class-name="tableRowClassName"
                        border
                        :stripe="stripe"
                        :data="(editable?showData:clonetableData)"
                        :height="Frosheight"
                        align="left"
                        :subtotalType="subtotalType"
                        @cell-click="cellClick"
                        @cell-dblclick="cellDbclick"
                        @row-click="rowClick"
                        @row-dblclick="rowDbclick"
                        @selection-change="handleSelectionChange"
                        @sort-change="sortChange"
                        @header-click = "headerClick"
                        tooltip-effect="light"
                        filter-placement="right"
                        id="multipleTable"
                        class="FrosTablemultiple"
                        :title="cellTitle"
                        ref="multipleTable"
                        @header-contextmenu="headerContextmenu"
                        @current-change="singleCurrentChange"
                        @expand-change="expandChange"
                        style="width:100%">
                    <!--@row-click="rowClick"-->
                    <!-- @expand-change="expandChange"-->
                    <!--树表格-->
                    <el-table-column  fixed type="expand"  v-if="expandShow">
                        <template slot-scope="props">
                            <slot name="children" ></slot>
                        </template>
                    </el-table-column>
                    <!-- 自定义添加排序列
                     <el-table-column
                             v-if='indexShow'
                             type="index"
                             align="center"
                             :label="indexLabel"
                             :width="indexWidth">
                     </el-table-column>-->
                    <!--自定义添加多选列-->
                    <el-table-column
                            v-if='selectionShow'
                            type="selection"
                            width="50"
                            align="center"
                            highlight-current-row>
                    </el-table-column>
                    <!--自定义添加排序列-->
                    <el-table-column
                            v-if='indexShow'
                            align="center"
                            :label="indexLabel"
                            :width="indexWidth">
                        <template slot-scope="scope">
                            <span v-if="pageShow">{{scope.$index+(pgconfig.currentPage - 1) * pgconfig.pageSize + 1}} </span>
                            <span v-else>{{scope.$index + 1}} </span>
                        </template>
                    </el-table-column>
                    <!--上传文件-->
                    <el-table-column
                            v-if="editable && uploadShow"
                            align="center"
                            label="上传"
                            width="100"
                            highlight-current-row>
                        <template slot-scope="scope">
                            <!-- <el-upload
                                     class="upload-demo"
                                     :showFileList="false"
                                     action="upLoad/objectstorecloud/files/v2/"
                                     :before-upload="beforeUpload">&lt;!&ndash;:on-change="addFile"&ndash;&gt;
                                 <el-button size="small" type="primary" @click="uploadaa(scope)">点击上传</el-button>
                             </el-upload>-->
                            <el-form enctype="multipart/form-data" id="uploadForm">
                                <input v-if="scope.row.uploadId?false:true" style="z-index:1;position:absolute;top:0;left:0;opacity:0;width:80px;height:30px;padding:9px 15px;overflow: hidden" class="file-btn"
                                       ref="upload" @change="fileChange($event,scope)"
                                       type="file" accept=""  value="" />
                                <el-form-item style="position:absolute;left:0;top:0;z-index:0">
                                    <el-button size="small" type="primary" :disabled="scope.row.uploadId?true:false">点击上传</el-button>
                                </el-form-item>
                            </el-form>
                        </template>
                    </el-table-column>
                    <el-table-column
                            v-for="(item, index) in tableHead"
                            :header-align="item.headerAlign"
                            :align="item.align"
                            :key="index"
                            :fixed="item.fixed"
                            :width="ColumnWidth(item)"
                            :prop="item.prop"
                            :label="changeLabel(item,index)"
                            :sortable="item.sortable" >
                        <!-- :render-header="companyRenderHeader"-->
                        <!-- 表头筛选模块 -->
                        <template slot="header" slot-scope="{ column }">
                            <span>{{ column.label }}</span>
                            <el-popover
                                    placement="bottom-start"
                                    width="350"
                                    trigger="manual"
                                    :ref="item.prop">
                                <!--search-data是筛选出来的过滤条件，search-data是过滤条件对应的key-->
                                <fros-sort-header :title="column.label" :search-data="item.filterData"
                                                  :prop-data="item.prop"
                                                  v-on:childByValue="submitData"
                                                  v-on:pClose="pClose">
                                </fros-sort-header>
                                <i slot="reference" @click="popover($event,item.prop)" v-if="item.filterShow"
                                   class="iconfont fros-icon-shaixuanguolv"></i>
                            </el-popover>
                        </template>
                        <!-- table数据展示区域 -->
                        <template slot-scope="scope">
                            <div v-if="!editable">
                                <div v-if="showAll" class="showTable" style="min-height: 20px;">
                                    <span v-html="normalFormatter(item.prop,scope.row[item.prop])">{{scope.row[item.prop]}}</span>
                                </div>
                                <div v-if="showPart">
                                    <i v-show="scope.row[item.prop]" :class="changeClass(scope.row.frosClass)"
                                       @click="cell(scope,scope.$index,item.prop)"></i>
                                    <span v-html="normalFormatter(item.prop,scope.row[item.prop])"></span>
                                </div>
                            </div>
                            <div v-else class="frosVailt">
                                <div class="showTable" style="min-height: 20px;" @click="btnClick(scope,item,$event)">
                                    <span v-html="frosFormat(item.editType.selectOption,scope.row[item.prop],item.editType.multiple,item.editType.isDict,item.editType.dictType,item.editType.dictData,item.editType.isLocalStorage)"
                                          class='selSpan' v-if="selSpanShow(scope.$index,item.prop,item.editType.label)"></span>
                                    <span class='hideSpan' v-html="normalFormatter(item.prop,scope.row[item.prop])"
                                          v-if="!getVisible(scope.$index,item.prop,item.editType.label)">{{scope.row[item.prop]}}</span>
                                    <el-checkbox
                                            @change="checkboxChange"
                                            v-if="item.editType.label == 'checkbox'"
                                            class="mr0"
                                            :disabled="item.editType.disabled"
                                            v-model="scope['row'][item.prop]"
                                            :true-label="(item.editType.trueLabel!=undefined && item.editType.trueLabel!='' ? item.editType.trueLabel:1)"
                                            :false-label="(item.editType.falseLabel!=undefined && item.editType.falseLabel!='' ? item.editType.falseLabel:0)"></el-checkbox>
                                    <div v-if="getVisible(scope.$index,item.prop)">
                                        <div v-if="item.editType.label == 'input'">
                                            <el-form-item :prop="item.prop">
                                                <el-input ref='inputVal' v-show="getVisible(scope.$index,item.prop)"
                                                          v-model="scope['row'][item.prop]"
                                                          :placeholder="item.editType.placeholder"
                                                          @keydown.tab.native="toggleCell(scope,item.prop,item)"
                                                          @keydown.enter.native="closeCell(scope.$index,item.prop)"
                                                          @change="transferChange"
                                                          @blur="transferBlur"
                                                          @focus="transferFocus"
                                                          :type='item.editType["type"]'></el-input>
                                                <!--  @focus="validateFocus" @blur="validateBlur" -->
                                            </el-form-item>
                                        </div>
                                        <!--文本框开始-->
                                        <div v-if="item.editType.label == 'frosBaseInput'">
                                            <div class="cutTable">
                                                <el-form-item :prop="item.prop">
                                                    <fros-base-input
                                                            @change="baseInputChange"
                                                            @blur="baseInputBlur"
                                                            @focus="baseInputFocus"
                                                            ref='inputVal'
                                                            rows="1"
                                                            v-show="getVisible(scope.$index,item.prop)"
                                                            v-model="scope['row'][item.prop]"
                                                            :placeholder="item.editType.placeholder"
                                                            :type="item.editType.type"
                                                            :maxlength="item.editType.length"
                                                            :frosDefValue="scope['row'][item.prop]"
                                                            :frosInputTooltip="item.editType.frosInputTooltip"
                                                            :disabled="item.editType.disabled"
                                                            :readonly="item.editType.readonly"
                                                            :frostrim="frostrim"
                                                            :onlyNum="item.editType.onlyNum"
                                                            :letter="item.editType.letter"
                                                            :letterAndNum="item.editType.letterAndNum"
                                                            :frosCode=true
                                                            :frosCase=true>
                                                    </fros-base-input>
                                                </el-form-item>
                                            </div>
                                        </div>

                                        <div v-if="item.editType.label == 'frosNumInput'">
                                            <div class="cutTable">
                                                <el-form-item :prop="item.prop">
                                                    <fros-num-input
                                                            @change="numInputChange"
                                                            @blur="numInputBlur"
                                                            @focus="numInputFocus"
                                                            ref='inputVal'
                                                            v-show="getVisible(scope.$index,item.prop)"
                                                            v-model="scope['row'][item.prop]"
                                                            :placeholder="item.editType.placeholder"
                                                            :length="item.editType.length"
                                                            :frosDefValue="scope['row'][item.prop]"
                                                            :disabled="item.editType.disabled"
                                                            :readonly="item.editType.readonly"
                                                            :frostrim="frostrim"
                                                            :frosInputTooltip="item.editType.frosInputTooltip"
                                                            :frosCode="item.editType.frosCode"
                                                            :frosCase=true
                                                            :onlyNum="item.editType.onlyNum"
                                                            :frosInterger="item.editType.frosInterger"
                                                            :frosNegative="item.editType.frosNegative"
                                                            :frosRounding="item.editType.frosRounding"
                                                            :frosPrecision="item.editType.frosPrecision"
                                                            :frosOperation="item.editType.frosOperation">
                                                    </fros-num-input>
                                                </el-form-item>
                                            </div>
                                        </div>
                                        <!--文本框结束-->
                                        <div v-if="item.editType.label == 'date'">
                                            <el-form-item :prop="item.prop">
                                                <fros-date-picker
                                                        @change="dateChange"
                                                        @blur="dateBlur"
                                                        @focus="dateFocus"
                                                        type="date"
                                                        :placeholder="item.editType.placeholder"
                                                        format="yyyy-MM-dd"
                                                        value-format="yyyy-MM-dd"
                                                        v-show="getVisible(scope.$index,item.prop)"
                                                        v-model="scope['row'][item.prop]"
                                                        :frosDefValue="scope['row'][item.prop]"
                                                        :disabled="item.editType.disabled"
                                                        @sync="syncService"
                                                        @keydown.tab.native="dateTabCell(scope,item.prop,item)"
                                                        @keydown.enter.native="dateEnterCell(scope.$index,item.prop)">
                                                </fros-date-picker>
                                            </el-form-item>
                                        </div>
                                        <div v-if="item.editType.label == 'datetime'">
                                            <el-form-item :prop="item.prop">
                                                <fros-date-picker
                                                        @change="dateChange"
                                                        @blur="dateBlur"
                                                        @focus="dateFocus"
                                                        type="datetime"
                                                        :placeholder="item.editType.placeholder"
                                                        value-format="yyyy-MM-dd HH:mm:ss"
                                                        v-show="getVisible(scope.$index,item.prop)"
                                                        v-model="scope['row'][item.prop]"
                                                        :frosDefValue="scope['row'][item.prop]"
                                                        :disabled="item.editType.disabled"
                                                        @sync="syncService"
                                                        @keydown.tab.native="dateTabCell(scope,item.prop,item)"
                                                        @keydown.enter.native="dateEnterCell(scope.$index,item.prop)">
                                                </fros-date-picker>
                                            </el-form-item>
                                        </div>
                                        <div v-if="item.editType.label == 'SmartInput'">
                                            <el-form-item :prop="item.prop">
                                                <fros-auto-complete
                                                                    v-show="getVisible(scope.$index,item.prop,item)"
                                                                    id="service"
                                                                    :placeholder="item.editType.placeholder"
                                                                    v-model="scope['row'][item.prop]"
                                                                    :frosDefValue="scope['row'][item.prop]"
                                                                    :disabled='item.editType.disabled'
                                                                    @sync="syncService"
                                                                    :props="serviceList[item.editType.config]"
                                                                    ref="inputValue"
                                                                    @rowData='rowData'
                                                                    @change="autocompleteChange"
                                                                    @blur="autocompleteBlur"
                                                                    @focus="autocompleteFocus"
                                                                    @keydown.enter.native="autocompleteEnter(scope.$index,item.prop)"
                                                                    @keydown.tab.native="autocompleteTab(scope,item.prop,item)">
                                                </fros-auto-complete>
                                            </el-form-item>
                                        </div>
                                        <div v-if="item.editType.label == 'select'">
                                            <div v-if='item.editType.multiple'>
                                                <!--select多选-->
                                                <el-form-item :prop="item.prop">
                                                    <fros-select
                                                            v-show="getVisible(scope.$index,item.prop)"
                                                            class="item-choose"
                                                            multiple="multiple"
                                                            :dictType="item.dictType"
                                                            :dictData="item.dictData"
                                                            :filterable="item.editType.filterable"
                                                            :frosEnterShow="item.editType.frosEnterShow"
                                                            :frosBlurShow="item.editType.frosBlurShow"
                                                            :frosCode="item.editType.frosCode"
                                                            :frosCase="item.editType.frosCase"
                                                            :disabled="item.editType.disabled"
                                                            :tooltipShow="item.editType.tooltipShow"
                                                            :placeholder="item.editType.placeholder"
                                                            :frosDefValue="scope['row'][item.prop]"
                                                            :options="item.editType['selectOption']"
                                                            v-model="scope['row'][item.prop]"
                                                            size="small"
                                                            @change="selectChange"
                                                            @blur="selectBlur"
                                                            @focus="selectFocus"
                                                            @keydown.tab.native="selectTab(scope,item.prop,item)">
                                                    </fros-select>
                                                </el-form-item>
                                            </div>
                                            <div v-else>
                                                <!--select单选-->
                                                <el-form-item :prop="item.prop">
                                                    <fros-select
                                                            v-show="getVisible(scope.$index,item.prop)"
                                                            class="item-choose"
                                                            :dictType="item.editType.dictType"
                                                            :dictData="item.editType.dictData"
                                                            :disabled="item.editType.disabled"
                                                            :filterable="item.editType.filterable"
                                                            :frosEnterShow="item.editType.frosEnterShow"
                                                            :frosBlurShow="item.editType.frosBlurShow"
                                                            :frosCode="item.editType.frosCode"
                                                            :frosCase="item.editType.frosCase"
                                                            :placeholder="item.editType.placeholder"
                                                            :frosDefValue="scope['row'][item.prop]"
                                                            :options="item.editType['selectOption']"
                                                            v-model="scope['row'][item.prop]"
                                                            size="small"
                                                            @change="selectChange"
                                                            @blur="selectBlur"
                                                            @focus="selectFocus"
                                                            @keydown.tab.native="selectTab(scope,item.prop,item)">
                                                    </fros-select>
                                                </el-form-item>
                                            </div>

                                        </div>
                                        <div v-if="item.editType.label == 'none'">
                                            <span class='abc' v-show="getVisible(scope.$index,item.prop)">{{scope.row[item.prop]}}</span>
                                        </div>

                                    </div>
                                </div>
                                <div v-if="!getVisible(scope.$index,item.prop) && scope.row._error[item.prop]"
                                     class='table_form_error'>
                                    <!-- {{scope.row._error[item.prop]}} -->
                                </div>
                            </div>
                        </template>

                    </el-table-column>
                    <!--自定义添加操作列 按钮事件-->
                    <el-table-column
                            align="center"
                            v-if="buttonListShow"
                            :fixed="addBtnList.fixed"
                            :label=addBtnList.label
                            :width=addBtnList.width>
                        <template slot-scope="scope">
                            <el-button
                                    v-for="(item,index) in addBtnList.list"
                                    :key="index"
                                    v-show="exchangeBtn(item.showBtn,scope)"
                                    @click="clkCall(item.methods,scope)"
                                    type="text"
                                    size="small" :title="item.title">
                                <i class="add-btn-i" :class="item.icon"></i>
                                {{item.title}}
                            </el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
        </el-form>
        <!--分页-->
        <div class="pagebox">
            <span class="btn_left">
                <i v-if="cutShow" title="剪切列" class="el-icon-menu"
                   style="color:#2B579A;font-size: 16px"
                   @click="show()"></i>
                <i v-if="itemShow" title="分组" class="el-icon-star-on" style="color:#2B579A" @click="showItem()"></i>
                <i v-if="freshShow" title="刷新" class="el-icon-refresh" style="color:#2B579A" @click="refresh"></i>
            </span>
            <div class="pagelist" v-if="pageShow">
                <el-pagination
                        @size-change="handleSizeChange"
                        @current-change="handleCurrentChange"
                        :layout="pgconfig.layout"
                        :small="pgconfig.smallSize"
                        :background="pgconfig.background"
                        :disabled="pgconfig.disabled"
                        :hide-on-single-page="pgconfig.hideSingle"
                        :popper-class="pgconfig.popperClass"
                        :prev-text="pgconfig.prevText"
                        :next-text="pgconfig.nextText"
                        :current-page="pgconfig.currentPage"
                        :page-sizes="pgconfig.pageSizes"
                        :page-size="pgconfig.pageSize"
                        :total="pgconfig.total">
                    <span>当前显示{{datafrom}}-{{datato}}条</span>
                </el-pagination>
            </div>
        </div>
        <!--弹框 剪切列-->
        <el-dialog title="剪切列" :visible.sync="ColCutDlgVisible" width="550px" :class="frosDialogClassname">
            <div v-if="fixedColumn" style="display: inline-block;margin-right: 20px;margin-bottom: 20px">
                固定列数：
                <div style="width:60px" class="el-input">
                    <el-input v-model="num" clearable></el-input>
                </div>
                <!-- <el-button size="small" type="primary"@click="combinNum">确定</el-button>-->
            </div>
            <div id="fros_transfer">
                <el-transfer
                        :titles="['未显示列', '已显示列']"
                        v-model="value2"
                        :data="data2"
                        filterable
                        target-order="push"
                        @right-check-change="rightChange">
                </el-transfer>
                <div>
                    <ul id="frosTransfer" class="transfer-footer"
                        slot="right-footer" style="height:20px;float:right;">
                        <li @click="stickTop"><i class="el-icon-upload2"></i></li>
                        <li @click="stickBottom"><i class="el-icon-download"></i></li>
                        <li @click="upLevel"><i class="el-icon-caret-top"></i></li>
                        <li @click="downLevel"><i class="el-icon-caret-bottom"></i></li>
                    </ul>
                </div>
                <!-- <div><el-button @click="transferSumit"type="primary" size="mini">确定</el-button></div>-->
            </div>
            <span slot="footer" class="dialog-footer">
                <el-button size="mini" @click="closeShow">取 消</el-button>
                <el-button size="mini" type="primary" @click="transferSumit">确 定</el-button>
            </span>
        </el-dialog>
        <span ref="dddd" id="dddd"
              style='visibility: hidden; position: absolute; top: 0; left: 0; white-space: nowrap;'>{{tempText}}</span>
        <!--弹框 分组-->
        <el-dialog
                title="分组"
                :visible.sync="itemVisible"
                width="550px" style="">
            <el-transfer
                    :titles="['未分組', '已分組']"
                    v-model="value3"
                    :data="data3"
                    filterable
                    target-order="push">
            </el-transfer>
            <span slot="footer" class="dialog-footer">
                <el-button size="mini" @click="itemVisible = false">取 消</el-button>
                <el-button size="mini" type="primary" @click="handleChangeitem" >确 定</el-button>
            </span>
        </el-dialog>


        <!--右键新建弹框-->
        <el-dialog title="新增" :visible.sync = 'newVisible'  width="550px" style="">
            <div style="padding-top: 30px">
                <el-form :model="formMenu" label-width="100px" class="demo-ruleForm">
                    <el-form-item label="模板名称">
                        <el-input v-model="formMenu.name"></el-input>
                    </el-form-item>
                    <el-form-item label="模板CODE">
                        <el-input v-model="formMenu.code"></el-input>
                    </el-form-item>
                </el-form>
            </div>
            <span slot="footer" class="dialog-footer">
                <el-button size="mini" @click="newVisible = false">取 消</el-button>
                <el-button size="mini" type="primary" @click="newConfirm" >确 定</el-button>
            </span>
        </el-dialog>
       <!-- &lt;!&ndash;右键保存弹框&ndash;&gt;
        <el-dialog title="保存" :visible.sync = 'saveVisible'  width="300px" style="">
            <p style="text-align: center">确认保存{{meunName}}吗？</p>
            <span slot="footer" class="dialog-footer">
                <el-button size="mini" @click="saveVisible = false">取 消</el-button>
                <el-button size="mini" type="primary" @click="saveConfirm" >确 定</el-button>
            </span>
        </el-dialog>
        &lt;!&ndash;右键删除弹框&ndash;&gt;
        <el-dialog title="保存" :visible.sync = 'delVisible'  width="300px" style="">
            <p style="text-align: center">确认删除{{meunName}}吗？</p>
            <span slot="footer" class="dialog-footer">
                <el-button size="mini" @click="delVisible = false">取 消</el-button>
                <el-button size="mini" type="primary" @click="delConfirm" >确 定</el-button>
            </span>
        </el-dialog>-->
    </div>
</template>
<script>
    import FrosSortHeader from './FrosSortHeader'
    import FrosAutoComplete from '../fros-autocomplete/frosAutoComplete'
    import FrosSelect from '../fros-select/FrosSelect'
    import FrosDatePicker from '../fros-date-picker/FrosDatePicker'

    import FrosBaseInput from '../fros-input/FrosBaseInput.vue'
    import FrosNumInput from '../fros-input/FrosNumInput.vue'
    let time = null
    export default {
        components: {
            FrosSortHeader,
            FrosAutoComplete,
            FrosSelect,
            FrosDatePicker,
            FrosBaseInput, // 基础文本
            FrosNumInput, // 数字文本
        },
        props: {
            // 表格
            expands:null,
            getRowKeys:null,
            isCollapse:Boolean, // 子表是否折叠
            uploadShow: Boolean, // 上传列是否显示
            rightListShow:null, // 是否显示右键功能

            frosDfcd: null,  // 存储动态表单所有数据
            stripe: Boolean,    // 是否开启斑马线
            indexShow: Boolean, // 是否显示排序列
            selectionShow: Boolean, // 是否显示多选列
            expandShow: Boolean, // 是否显示展开列
            indexLabel: null, // 排序列名称
            indexWidth: null, // 排序列宽度
            height: null,
            cutShow: Boolean,
            itemShow: Boolean, // 是否显示分组
            addSubtotal: Boolean, // 是否添加汇总功能
            subtotalType: null, // 配置汇总属性
            fixedColumn: Boolean,
            freshShow: Boolean,
            pageShow: Boolean,
            dropCol: Array,
            tableData: Array,
            // 分页
            pgconfig: null,
            columnAuto: Boolean,
            // 表格自定义布局
            tableLayoutConf: null,
            serviceList: null,
            rules: null,
            editable: Boolean, // 是否可编辑
            // 自定义列添加按钮事件
            addBtnList: null,
            buttonListShow: Boolean,   // 是否显示隐藏列
            frosDialogClassname:null, // 剪切列弹框class名称
            RowColorisShow:Boolean, // 是否配置表格状态颜色
            tableRowColorType:null, // 表格行根据状态变色
        },
        data() {
            return {
                Frosheight:null,
                // 右键菜单
                rightList: [], //菜单右键
                meunId:'', // 模板id
                meunName:'', // 模板名称
                // 右键新增
                formMenu:{
                    name:'',
                    code:'',
                },
                newVisible:false,  //新增
              /*  saveVisible:false, // 保存
                delVisible:false, // 删除*/
                rowContextmenuVisible: false,//右键菜单开关变量
                contextMenuData: {
                    // the contextmenu name(@1.4.1 updated)
                    menuName: 'demo',
                    // The coordinates of the display(菜单显示的位置)
                    axis: {
                        x: null,
                        y: null
                    },
                    // Menu options (菜单选项)
                    menulists: [{
                        fnHandler: 'savedata', // Binding events(绑定事件)
                        icoName: 'fa fa-home fa-fw', // icon (icon图标 )
                        btnName: '保存', // The name of the menu option (菜单名称)
                        id: 3
                    }, {
                        fnHandler: 'newdata',
                        icoName: 'fa fa-home fa-fw',
                        btnName: '新增',
                        id: 4
                    },{
                        fnHandler: 'deldata',
                        icoName: 'fa fa-home fa-fw',
                        btnName: '删除',
                        id: 5
                    }]
                },
                menuCopy: [
                    {
                        fnHandler: 'savedata', // Binding events(绑定事件)
                        icoName: 'fa fa-home fa-fw', // icon (icon图标 )
                        btnName: '保存', // The name of the menu option (菜单名称)
                        id: 3
                    }, {
                        fnHandler: 'newdata',
                        icoName: 'fa fa-home fa-fw',
                        btnName: '新增',
                        id: 4
                    },{
                        fnHandler: 'deldata',
                        icoName: 'fa fa-home fa-fw',
                        btnName: '删除',
                        id: 5
                    }
                ],    // 右键菜单默认模板
                cellTitle:null,
                //el-form绑定的校验数据
                formData: null,
                /* 公共属性 */
                // 文本框
                frostrim: 'trim', // 清除空格位置 | ltrim(左) | rtrim(右) | lrtrim(左右) | trim(全部)
                // 数字框配置
                onlyNum: false, // 默认只能是数字（不可输入小数和负数）
                frosNegative: false, // 不能输入负数
                frosRounding: true, // 四舍五入 | true:四舍五入（默认保留两位小数） | false：不允许
                frosPrecision: 2, // 小数位数设置 | 默认是两位（不足自动补齐0）
                frosFloat: true,  // 数字是否加逗号分隔
                frosOperation: true, // 是否进行运算
                // 剪切列弹框
                num: '',
                data2: [],
                value2: [],
                tableHead: [],
                ColCutDlgVisible: false,
                // 分组弹框
                showAll:true,  //
                showPart:false,
                isNum: '',
                // 分组筛选
                clonedropCol:[], // 本地存储数据
                // clonetableData:[], // 本地存储数据
                tableOne:[], // 第一遍筛选出来表头数据
                tableTwo:[], // 第一遍筛选出来的表格数据
                changeTable: [], // 每次存取的数据
                // 点击单元格事件
                cellIndex: null, // 获取当前行index
                cellProp: '', // 获取当前text
                cellText: '', // 当前文本
                filDate: [], // 每次过滤后的数据
                condition: {}, // 过滤条件集合
                // 添加汇总功能
                subList: {}, // 存汇总类型
                allCountList: {}, // 设置汇总初始化
                typeList: [], // 判断汇总类型

                itemVisible: false,
                data3: [],
                value3: [],
                pageCount: 0,
                datafrom: 1,//
                datato: 0,
                pageDataLength:null, // 初始化表格的长度
                currentColumnAuto: this.columnAuto,
                currentCellText: '', //鼠标悬浮数据

                /* 基础表格特有属性 */
                time: null, // 单双击事件
                clonetableData: [], // 基础表格原始数据
                sortData: [], // 剪切排序
                searchInfo: {}, // 表头搜索提交数据

                /* 可编辑表格特有属性 */
                showData: [],
                selectData: null,
                selvalList: [], // 下拉多选val
                propRowdata: {},
                tempText: "",
                colWidth: null,
                autoWidth:null,//列宽自适应为false时取值
                edited: false,
                row: null,
                col: null,
                cellrowIndex: null, // 下拉框行号
                /* dragging: false, */
                // 校验规则
                ruleForm2: {
                    pass: 'ddd'
                },
                pageTotal: [1, 2, 3, 4],
                visible: '',
                selectVisible: '',
                //checkbox选中的行数据
                SelectCurrentData: [],
                currentRow:null,   // 单选选中的行数据

            }
        },
        computed: {},
        watch: {
            tableData: {
                handler(val, oldVal) {
                    if(this.currentColumnAuto){
                        this.updateColWidth();
                    }else{
                        this.updateAutoWidth()
                    }
                    if (this.editable) {
                        this.showData = val
                        this.clonetableData = val

                        // 分页显示当前页码数量
                        this.pageDataLength =  this.clonetableData.length
                        if(this.pageShow){
                            if(this.pageDataLength<this.pgconfig.pageSize){
                                this.datato = this.pgconfig.currentPage * (this.pgconfig.pageSize)+this.pageDataLength - this.pgconfig.pageSize
                            }else{
                                this.datato = this.pgconfig.currentPage * (this.pgconfig.pageSize)+this.clonetableData.length - this.pgconfig.pageSize
                            }
                        }

                        // console.log(this.showData)
                        /**添加校验属性开始**/
                        this.showData = this.tableData.map(function (n) {
                            if (!n._error) {
                                n._error = {}
                            }
                            if (!n._changedata) {
                                n._changedata = false
                            }
                            return n
                        });
                        /**添加校验属性结束**/
                        this.elasticList()
                    } else {
                        localStorage.setItem("orData", JSON.stringify(this.tableData))
                        this.clonetableData =val

                        // 分页显示当前页码数量
                        this.pageDataLength =  this.clonetableData.length
                        if(this.pageShow){
                            if(this.pageDataLength<this.pgconfig.pageSize){
                                this.datato = this.pgconfig.currentPage * (this.pgconfig.pageSize)+this.pageDataLength - this.pgconfig.pageSize
                            }else{
                                this.datato = this.pgconfig.currentPage * (this.pgconfig.pageSize)+this.clonetableData.length - this.pgconfig.pageSize
                            }
                        }
                        this.elasticList()
                    }
                    this.elasticList()
                },
                deep: true
            },
            dropCol: {
                handler(val, oldVal) {
                    this.dropCol = val
                    this.tableHead=[]
                    if(this.tableHead.length > 0){
                        this.elasticList()
                    }else{
                        this.headTable()
                        this.elasticList()
                    }
                },
                immediate: true,
                deep: true
            },
            pgconfig: {
                handler(val, oldVal) {
                    this.pgconfig = val
                },
                immediate: true,
                deep: true
            },
            frosDfcd: {
                handler(val, oldVal) {
                    this.frosDfcd = val
                    if (val != null && val.showItem != undefined && this.rightListShow) {
                        this.menuProducer() // 菜单生成器
                        this.addMenu()  // 动态添加右键菜单
                    }
                },
                immediate: true,
                deep: true
            },
            rightList: {
                handler(val, oldVal) {
                    this.rightList = val
                    /*if(this.rightListShow){
                        this.addMenu()
                    }*/
                },
                immediate: true,
                deep: true
            },
            // 改变行样式
            tableRowColorType(newName, oldName) {
                this.tableRowColorType = newName;
                this.tableRowClassName()
            },
            height(val, oldVal) {
                if (val !== oldVal) {
                    this.Frosheight = val
                }
            }
        },
        mounted() {
            // 监听滚动条事件
          //  window.addEventListener('scroll', this.handleScroll,true)
            // 设置高度自适应
            this.Frosheight=this.height
            if(this.Frosheight=='auto'){
                this.$nextTick(()=> {
                    this.changeHeight()
                })
            }else {
                this.Frosheight=this.height
            }

            if(this.currentColumnAuto){
                this.updateColWidth();
            }else{
                this.updateAutoWidth()
            }
            // 基础表格
            this.initData()   // 初始化数据
            this.elasticList()    // 表头筛选功能
            if(this.addSubtotal){
                this.judgeTypeList() // 分组判断汇总类型
            }

           /* // 判断分页是否显示
            if(this.pageShow){
                this.datato = this.pgconfig.pageSizes[0]
            }*/
            // 拖动更改复选框状态
            var ckbSelf = this
            document.getElementById("multipleTable").onmousedown = function (ev) {
                var ev = ev || event
                const oTable = document.getElementById("multipleTable")
                const asideWidth = document.getElementsByTagName("aside")[0].offsetWidth
                const tHeadHeight = oTable.querySelector('.el-table__header-wrapper thead').rows[0].offsetHeight
                const headerHeight = document.getElementsByTagName("header")[0].offsetHeight
                const checkboxWidth = oTable.querySelector('.el-table__body-wrapper tbody').rows[0].cells[0].offsetWidth
                let disX = ev.clientX - oTable.offsetLeft - asideWidth;
                let disY = ev.clientY - oTable.offsetTop - headerHeight - tHeadHeight;
                if (disX <= checkboxWidth) {
                    localStorage.setItem("startY", disY);
                }
                document.getElementById("multipleTable").onmouseup = function (ev) {
                    disX = ev.clientX - oTable.offsetLeft - asideWidth;
                    disY = ev.clientY - oTable.offsetTop - headerHeight - tHeadHeight;
                    if (disX <= checkboxWidth) {
                        const scrollT = oTable.querySelector('.el-table__body-wrapper').scrollTop
                        const rowLength = oTable.querySelector('.el-table__body-wrapper tbody').rows
                        let rowsHeight = 0
                        let start = 0
                        let end = 0
                        let boolStart = true
                        let boolEnd = true
                        for (let i = 0; i < rowLength.length; i++) {
                            rowsHeight += rowLength[i].offsetHeight
                            if ((rowsHeight - scrollT) > localStorage.getItem("startY") && boolStart === true) {
                                start = i
                                boolStart = false
                            }
                            if ((rowsHeight - scrollT) > disY && boolEnd === true) {
                                end = i
                                let rows = []
                                if (start !== end) {
                                    for (let i = start; i < end + 1; i++) {
                                        rows.push(ckbSelf.clonetableData[i])
                                    }
                                    ckbSelf.toggleSelection(rows)
                                }
                                boolEnd = false
                            }
                        }
                    }
                }
            }

            // 点击屏蔽右键事件
            if(this.rightListShow){
                document.getElementById("contextMenu").onclick = function(){
                    this.rowContextmenuVisible = false
                    const menu = document.getElementById("contextMenu")
                    menu.style.display = 'none'
                }
            }

        },
        methods: {
            // 滚动条滚动时候，下拉框消失
            handleScroll(){
                let drapsList = document.getElementsByClassName('el-select-dropdown')
                let inputsList = document.getElementsByClassName(' el-input__inner')
                for(let i=0;i<drapsList.length;i++){
                    drapsList[i].style.display='none'
                }
                for(let i=0;i<inputsList.length;i++){
                    inputsList[i].blur()
                }

            },
            // 给标题加*号
            changeLabel(item,index){
                this.$nextTick(function(){
                    let childNum = null
                    let oThead = document.getElementsByTagName('thead')[0]
                    let otr = oThead.getElementsByTagName('tr')[0]
                    if(item.addSymbol=='*'){
                        if(this.indexShow && this.selectionShow && this.editable && this.uploadShow){
                            childNum = index+3
                            let oTh = otr.getElementsByTagName('th')[childNum]
                            let odiv = oTh.getElementsByTagName('div')[0]
                            odiv.className='change'
                        }else if(this.indexShow && this.selectionShow){
                            childNum = index+2
                            let oTh = otr.getElementsByTagName('th')[childNum]
                            let odiv = oTh.getElementsByTagName('div')[0]
                            odiv.className='change'
                        }else if(this.indexShow || this.selectionShow){
                            childNum = index+1
                            let oTh = otr.getElementsByTagName('th')[childNum]
                            let odiv = oTh.getElementsByTagName('div')[0]
                            odiv.className='change'
                        }else{
                            childNum = index
                            let oTh = otr.getElementsByTagName('th')[childNum]
                            let odiv = oTh.getElementsByTagName('div')[0]
                            odiv.className='change'
                        }
                    }
                })
                return item.label
            },
            toggleChange(row,boolean){
                const $table = this.$refs.multipleTable
                $table.toggleRowExpansion(row,boolean)
            },

            // 点击收起、展开子表格事件
            expandChange(row,expandedRows){
                this.$emit('expandChange',row,expandedRows)
            },
            // 判断操作类按钮显示、隐藏
            exchangeBtn(itemshowBtn,scope){
                if(eval(itemshowBtn)){
                    return true
                }
            },
            // 根据auto改变表格高度
            changeHeight(){
                this.$nextTick(()=> {
                    let el = this
                    let currentDiv =el.$el
                    let parentDiv = currentDiv.parentNode
                    let parentDivHeight = parentDiv.offsetHeight
                    this.Frosheight = parentDivHeight
                })
            },
            // 自定义标题
            companyRenderHeader(h, { column, $index }) {
                if(column.label.indexOf(',') != -1){
                    let labels = column.label.split(',')
                    return h("span",[
                        h("span",{
                            "class":{
                                "red-star":true
                            }
                        },labels[0]),
                        h("span",labels[1]),
                    ])
                }else
                    return h("span",[
                        h("span",column.label),
                    ])
            },
            // 表格行加状态样式
            tableRowClassName({row, rowIndex}) {
                this.$emit('tableRowClassName',row,rowIndex)
                if(this.RowColorisShow){
                    let List = this.tableRowColorType
                    let colorList = List.list
                    let type = this.tableRowColorType.type
                    for(let i=0;i<colorList.length;i++){
                        if(row[type] == colorList[i].value){
                            return colorList[i].className
                        }
                    }
                }
            },
            // 清空多选
            clearSelection() {
                this.$refs.multipleTable.clearSelection();
            },
            // 多选 选中行状态
            selectionData(rows){
                rows.forEach(row => {
                    this.$refs.multipleTable.toggleRowSelection(row);
                })
            },
            // 单选 选中行状态
            setCurrent(row) {
                this.$refs.multipleTable.setCurrentRow(row);
            },
            singleCurrentChange(val) {
                this.currentRow = val;
                this.$emit('singleCurrentChange', val)
            },
            // 数据初始化
            initData() {
                localStorage.setItem("orData", JSON.stringify(this.tableData))
                this.clonedropCol = this.dropCol
                this.clonetableData = this.tableData  // 解决编辑表格拖拽全选问题
                if (this.editable) {
                    this.showData = this.tableData.map(function (n) {
                        n._error = {}
                        n._changedata = false
                        return n
                    });
                } else {
                    this.clonetableData = this.tableData
                }
            },

            // 动态菜单生成器
            menuProducer() {
                let self = this
                if (this.frosDfcd != null) {
                    let defModel = this.frosDfcd.defModel
                    if (defModel != undefined || defModel != '') {
                        // 置空右键中的菜单栏
                        self.rightList = []
                        // 将右键菜单动态部分重置为默认菜单项。
                        let menuTemp = JSON.parse(JSON.stringify(this.menuCopy))
                        this.contextMenuData.menulists = menuTemp
                        for (let i = 0; i < defModel.length; i++) {
                            let tempModel = {
                                fnHandler: 'frosDynamicFormModelType',
                                icoName: 'fa fa-home fa-fw',
                                btnName: '##',
                                modelCode: '1'
                            }
                            tempModel.btnName = defModel[i].label
                            tempModel.modelCode = defModel[i].modelCode
                            if (!self.menuIsExist(defModel[i].modelCode)) {
                                self.rightList.push(tempModel)  // 如果右键菜单中不存在则添加。
                            }
                        }
                    }
                }
            },
            // 判断菜单是否已经存在
            menuIsExist(mCode) {
                let menulists = this.contextMenuData.menulists
                for (let i = 0; i < menulists.length; i++) {
                    if (menulists[i].modelCode != undefined) {
                        if (mCode == menulists[i].modelCode) {
                            return true
                        }
                    }
                }
            },
            // 表头右键
            headerContextmenu(column, event){
                if(this.rightListShow){
                    const menu = document.getElementById("contextMenu")
                    menu.style.display = 'block'
                    this.rowContextmenuVisible = true
                    this.showMenu ()
                }
            },
            // 添加菜单项
            addMenu(){
                if(this.rightList.length >0){
                    for(let i=0;i<this.rightList.length;i++){
                        this.contextMenuData.menulists.push(this.rightList[i])
                    }
                }

            },
            showMenu () {
                event.preventDefault()
                //  console.log(event)
                var x = event.clientX
                var y = event.clientY
                // Get the current location
                this.contextMenuData.axis = {
                    x, y
                }
            },
            // 右键新增
            newdata () {
                this.newVisible=true
            },
            // 确认新增按钮
            newConfirm(){
                this.newVisible=false
                this.$emit('addModel',this.formMenu.name,this.formMenu.code,this.tableHead)
            },
            // 右键保存
            savedata () {
                if(this.meunId=='' || this.meunId==' '){
                    this.$message.error('请选择模板')
                }else{
                   // this.saveVisible=true
                    this.$emit('saveModel',this.meunId,this.meunName,this.tableHead)
                }
            },
            // 确认保存按钮
            saveConfirm(){
                this.saveVisible=false
                this.$emit('saveModel',this.meunId,this.meunName)
            },
            // 右键删除
            deldata () {
                if(this.meunId=='' || this.meunId==' '){
                    this.$message.error('请选择模板')
                }else{
                    // this.delVisible=true
                    this.$emit('delModel',this.meunId,this.meunName,this.tableHead)
                }
            },
            // 删除确认按钮
            delConfirm(){
                this.delVisible=false
                this.$emit('delModel',this.meunId,this.meunName)
            },
            modelType () {
                let el = event.currentTarget.innerText
                let list = this.contextMenuData.menulists
                this.meunId = list.find(item=>item.btnName ==el.trim()).modelCode
                this.meunName=el
                /* // 右侧菜单id 传到父级
                 this.$emit('menuId',this.meunId)*/
            },

            // 右键模板切换
            frosDynamicFormModelType() {
                let el = event.currentTarget.innerText
                let list = this.contextMenuData.menulists
                this.meunId = list.find(item => item.btnName == el.trim()).modelCode
                this.meunName=el
                // 右侧菜单id 传到父级
            },

            // 改变分组按钮样式
            changeClass(val){
                if(val==true){
                    return 'el-icon el-icon-circle-plus'
                }else if(val==false){
                    return 'el-icon el-icon-remove'
                }else{
                    return ''
                }
            },
            // 分组弹框
            showItem(){
                this.itemVisible = true
                let items = this.tableHead
                this.data3 = []
                console.log(items)
                items.forEach((item,index) => {
                    this.data3.push({
                        label: item.label,
                        // prop: item.prop,
                        // filter:item.filter,
                        key: item.prop,
                        disabled: false
                    })
                })
                console.log(this.data3)
                // 判断是否开启汇总开关
                if(this.addSubtotal == true){
                    // 数组对比值
                    let typeList = this.typeList
                    console.log(typeList)
                    for(let i=0;i<typeList.length;i++){
                        for(let j=0;j<this.data3.length;j++){
                            if(typeList[i] == this.data3[j].key){
                                this.data3[j].disabled = true
                            }
                        }
                    }
                }

            },
            // 判断汇总类型
            judgeTypeList(){
                this.typeList = []
                for(let key in this.subtotalType){
                    this.typeList.push(key)
                }
            },
            // 确定分组筛选
            handleChangeitem() {
                if(this.value3.length !=0){
                    // 根据选项表头数据重新排序
                    let newHead = []
                    for (let i=0;i<this.value3.length;i++){
                        for (let n =0;n<this.tableHead.length;n++){
                            if(this.value3[i] === this.tableHead[n].prop){
                                newHead.push(this.tableHead[n])
                                this.tableHead.splice(n,1)
                            }
                        }
                    }
                    console.log(newHead)
                    for (let j=newHead.length-1;j>=0; j--) {
                        this.tableHead.unshift(newHead[j])
                    }
                    console.log(this.tableHead)
                    this.groupFilter(0)
                }else{
                    this.tableHead = this.clonedropCol
                    this.clonetableData = JSON.parse(localStorage.getItem('orData'))
                    this.tableOne = []
                    this.showAll=true
                    this.showPart=false
                    // console.log(JSON.parse(localStorage.getItem('orData')))
                    // console.log(this.tableData)
                    console.log(this.clonetableData)
                    console.log(this.tableData)
                }
                this.itemVisible = false
            },
            // 隐藏汇总按钮
            btnDel(){
                let typeList = this.typeList
                // 获取长度
                let listNum = []
                for(let i=0;i<this.tableHead.length;i++){
                    for(let j=0;j<typeList.length;j++){
                        if(typeList[j] == this.tableHead[i].prop){
                            listNum.push(i+1)
                        }
                    }
                }
                // 获取表头 单元格class集合
                let tbHead = document.getElementsByTagName('thead')[0]
                this.$nextTick(function () {
                    let trCell = tbHead.children[0]
                    let thList = trCell.children
                    let nameList = []
                    for(let i=0;i<listNum.length;i++){
                        nameList.push(thList[listNum[i]].classList[0])
                    }
                    // 循环class集合 去掉按钮
                    for(let i=0; i<nameList.length;i++){
                        let tbody = document.getElementsByTagName('tbody')[0]
                        let btnParent = tbody.getElementsByClassName(nameList[i])
                        for(let m=0;m<btnParent.length;m++){
                            let btn = btnParent[m].lastChild
                            let ibtn = btn.getElementsByTagName('i')[0]
                            ibtn.style.display = 'none'
                        }
                    }
                })
            },
            // 点击切换样式
            cell (scope,index,prop){
                let el = window.event.currentTarget
                this.cellText = el.offsetParent.innerText
                this.cellIndex = null
                this.cellIndex = index
                this.cellProp = prop
                if(scope.row.frosClass == true){
                    // 点击加号方法
                    scope.row.frosClass = false
                    for(let i=0;i<this.value3.length;i++){
                        if(prop==this.value3[i]){
                            let num = i+1
                            // 查找层级数据
                            if (this.value3[num] != undefined) {
                                let group = JSON.parse(JSON.stringify(this.clonetableData[this.cellIndex].frosGroup))
                                this.ChangeFormatter(prop)
                                group.push(this.cellText)
                                this.condition = {}
                                for(let n=0;n<group.length;n++){
                                    this.condition[this.value3[n]] = group[n]
                                }
                                this.dataFilter (num)
                                this.groupFilter (num)
                            }else{
                                // 查找详细数据
                                let group = JSON.parse(JSON.stringify(this.clonetableData[this.cellIndex].frosGroup))
                                this.ChangeFormatter(prop)
                                group.push(this.cellText)
                                this.condition = {}
                                for(let n=0;n<group.length;n++){
                                    this.condition[this.value3[n]] = group[n]
                                }
                                this. dataFinal()
                            }
                        }
                    }
                }else{
                    // 点击减号方法
                    scope.row.frosClass = true
                    el.className = 'el-icon el-icon-circle-plus'
                    let itemNum = []
                    this.ChangeFormatter(prop)
                    for (let i=0;i<this.clonetableData.length;i++){
                        if(this.clonetableData[i].frosGroup.indexOf(this.cellText) > -1){
                            itemNum.push(i)
                        }
                    }
                    this.clonetableData.splice(itemNum[0], itemNum.length);
                }
            },
            // 点击加号转换状态值
            ChangeFormatter(prop){
                let tableHead = this.tableHead
                for(let i=0; i<tableHead.length; i++){
                    let item = tableHead[i]
                    if(prop == item.prop){
                        if(item.formatterType == undefined){
                            return this.cellText
                        }else{
                            let list = item.formatterType
                            for(let j=0; j<list.length; j++){
                                if(this.cellText == list[j].key){
                                    this.cellText = list[j].value
                                    //return list[j].val
                                }
                            }
                        }
                    }
                }
            },
            // 分组筛选
            groupFilter(index){
                let label = this.value3[index]
                console.log(label)
                this.tableOne = []
                if(index==0){
                    this.clonetableData = JSON.parse(localStorage.getItem('orData'))
                    this.tableOne = this.clonetableData.map(function (item) {
                        return item[label]
                    })
                    this.tableOne = Array.from(new Set(this.tableOne))
                    console.log(this.tableOne)
                    // 判断是否开启汇总方法
                    if(this.addSubtotal==true){
                        this.dataFilter(index)
                    }
                }else{
                    this.clonetableData = this.filDate
                    let propList = this.filDate.map(function (item) {
                        return item[label]
                    })
                    propList = Array.from(new Set(propList)) // 去重
                    this.tableOne = propList
                }
                this.tableTwo = []
                for(let i=0;i<this.tableOne.length;i++) {
                    let item = {
                        [label]: this.tableOne[i],
                        frosGroup : [],
                        frosClass :true
                    }
                    this.tableTwo.push(item)
                }
                this.showAll=false
                this.showPart=true
                if(index==0){
                    // 判断是否开启汇总方法
                    if(this.addSubtotal==true){
                        let typeList = this.typeList
                        for(let i=0;i<typeList.length;i++){
                            let item = typeList[i]
                            let vList = this.allCountList[item]
                            for (let v=0;v<this.tableTwo.length;v++){
                                this.tableTwo[v][item] = vList[v]
                            }
                        }
                        this.btnDel()
                    }
                    this.clonetableData = this.tableTwo
                    this.changeTable = this.clonetableData
                }
                else{
                    // 表格改变成上一次数据
                    this.clonetableData = this.changeTable
                    let group = Array.from(new Set(this.clonetableData[this.cellIndex].frosGroup))
                    let grouplist = JSON.parse(JSON.stringify(group))
                    grouplist.push(this.cellText)
                    // 判断是否汇总
                    if(this.addSubtotal==true){
                        // 获取计算类型的key
                        let typeList = this.typeList
                        // 新增属性
                        for(let i=0;i<typeList.length;i++){
                            let item = typeList[i]
                            let vList = this.allCountList[item]
                            for (let v=0;v<this.tableTwo.length;v++){
                                this.tableTwo[v].frosGroup = Array.from(new Set(grouplist))
                                this.tableTwo[v][item] = vList[v]
                            }
                        }
                        for(let m=0; m<this.tableTwo.length;m++){
                            this.clonetableData.splice(this.cellIndex+m+1,0,this.tableTwo[m])
                        }
                        this.changeTable = this.clonetableData
                        this.btnDel()
                    }else{
                        for (let v=0;v<this.tableTwo.length;v++){
                            this.tableTwo[v].frosGroup = Array.from(new Set(grouplist))
                        }
                        // 把新数据添加进表格里
                        for(let m=0; m<this.tableTwo.length;m++){
                            this.clonetableData.splice(this.cellIndex+m+1,0,this.tableTwo[m])
                        }
                        this.changeTable = this.clonetableData
                    }
                }
            },
            // 层级数据过滤
            dataFilter(num){
                let data = JSON.parse(localStorage.getItem('orData'))
                let filter=(condition,data)=>{
                    return data.filter( item => {
                        return Object.keys( condition ).every( key => {
                            return String( item[ key ] ).toLowerCase().includes(
                                String( condition[ key ] ).trim().toLowerCase() )
                        } )
                    } )
                }
                this.filDate = []
                this.filDate = filter(this.condition,data)

                // 计算数据值
                // 判断是否开启汇总方法
                if(this.addSubtotal==true){
                    let prop = this.value3[num] // 计算的属性只

                    let allList = {} // 总的变量
                    // 获取到下一级别的具体的属性值
                    let propList = this.filDate.map(function (item) {
                        return item[prop]
                    })
                    propList = Array.from(new Set(propList)) // 去重
                    // 获取计算类型的key
                    let typeList = this.typeList
                    let filterData = JSON.parse(JSON.stringify(this.filDate))
                    // 添加所有的数据变量添加
                    for(let i=0;i<propList.length;i++){
                        let itemList = propList[i]
                        let list = allList[itemList]={}
                        for(let n=0;n<typeList.length; n++){
                            let item = typeList[n]
                            list[item] = []
                            for(let j=0; j<filterData.length;j++){
                                if(itemList == filterData[j][prop]){
                                    list[item].push(filterData[j][item])
                                }
                            }
                        }
                    }
                    // set计算初始化
                    for(let key in this.subtotalType){
                        this.allCountList[key] = []
                    }
                    // 判断计算类型
                    for(let i=0; i<propList.length;i++){
                        let item =  allList[propList[i]]
                        for(let key in this.subtotalType){
                            let val = this.subtotalType[key]
                            if(val == 'count'){
                                this.totalCount(key,item)
                            }else if(val == 'sum'){
                                this.totalSum(key,item)
                            }else if(val == 'min'){
                                this.totalMin(key,item)
                            }else if(val == 'max'){
                                this.totalMax(key,item)
                            }else if(val == 'ave'){
                                this.totalAverage(key,item)
                            }
                        }
                    }
                }
            },
            // 计算条数
            totalCount(key,item){
                let listItem = item[key]
                let list = this.allCountList[key]
                list.push('count:'+listItem.length)
                // this.delBtn()
            },
            // 计算总和
            totalSum(key,item){
                let listItem = item[key]
                let list = this.allCountList[key]
                let sum = null
                for(let i=0;i<listItem.length;i++){
                    sum = sum +listItem[i]
                }
                list.push('sum:'+sum)
            },
            // 计算最小值
            totalMin(key,item){
                let listItem = item[key]
                let list = this.allCountList[key]
                let minVal = null
                minVal = Math.min.apply(null, listItem)
                list.push('min:'+minVal)
            },
            // 计算最大值
            totalMax(key,item){
                let listItem = item[key]
                let list = this.allCountList[key]
                let maxVal = null
                maxVal = Math.max.apply(null, listItem)
                list.push('max:'+maxVal)
            },
            // 计算平均值
            totalAverage(key,item){
                let listItem = item[key]
                let list = this.allCountList[key]
                let sum = null
                let ave = null
                for(let i=0;i<listItem.length;i++){
                    sum = sum +listItem[i]
                }
                ave = sum / listItem.length
                let final = 'average:'+(ave.toFixed(2))
                list.push(final)
            },
            // 最终的过滤信息
            dataFinal(){
                let data = JSON.parse(localStorage.getItem('orData'))
                let filter=(condition,data)=>{
                    return data.filter( item => {
                        return Object.keys( condition ).every( key => {
                            return String( item[ key ] ).toLowerCase().includes(
                                String( condition[ key ] ).trim().toLowerCase() )
                        } )
                    } )
                }
                // 过滤最终的数据
                let listData = []
                listData = filter(this.condition,data)  // 过滤结果
                let tempLastGrop = JSON.parse(JSON.stringify(this.clonetableData[this.cellIndex].frosGroup))
                tempLastGrop.push(this.cellText)
                for(let i=0; i <listData.length; i++){
                    listData[i].frosGroup = tempLastGrop
                }
                for(let j=0; j <listData.length; j++){
                    this.clonetableData.splice(this.cellIndex+1,0,listData[j])
                }
            },

            //表格宽度取值
            ColumnWidth(item){
                if(this.currentColumnAuto){
                    if(this.colWidth){
                        return this.colWidth[item.prop]
                    }else{
                        return "auto"
                    }
                }else{
                    if(this.autoWidth){
                        return this.autoWidth[item.prop]
                    }else{
                        return "auto"
                    }
                }
            },
            //点击表头事件是列宽自适应
            headerClick(a,b){
                console.log(a.property)
                this.updateAutoWidth(a.property)
                //this.$set(this.rfidInfo,row.$index,this.rfidInfo[row.$index]);
            },
            // 上传文件
            fileChange(e,scope){
                let uploadForm=document.getElementById("uploadForm")
                let fd = new FormData(uploadForm)
                this.$emit("beforeUpload", e, scope, fd)
            },

            //checkbox更改值传给父组件
            checkboxChange(val) {
                this.$emit('checkboxChange', {"row": this.row,'col':this.col, "val": val})
            },
            // 自定义列
            clkCall(methodsWords,scope) {
                let el = event.currentTarget;
                this.$emit('clkCallBk', methodsWords,scope,el)

                // console.log(el)
            },
            /** 可编辑table相关方法 */
            // 数据字典-对回显值进行翻译
            frosFormat(sel, value, multiple, isDict, dictType, dictData, isLocalStorage) {
                // 判断是否需要字典翻译
                if (isDict) {
                    if (dictData != null) {
                        // 单页字典数据翻译
                        this.getDictionary(sel, dictData) // 调用翻译
                    } else {
                        // 全局字典数据翻译
                        let dataDic = this.$store.state.global.frosDictionary
                        if(isLocalStorage){
                            // 如果配置本地缓存则走本地缓存数据字典数据
                            let lsDictData = localStorage.getItem('FrosDictionary')
                            if(lsDictData != null){
                                dataDic = JSON.parse(lsDictData)
                            }
                        }
                        this.getDictionary(sel, dataDic[dictType]) // 调用翻译
                    }
                }
                let val = value
                if (multiple) {
                    let lab = ''
                    for (let i = 0; i < sel.length; i++) {
                        for (let j = 0; j < val.length; j++) {
                            if (sel[i].value == val[j]) {
                                lab = lab + ' ' + sel[i].key
                            }
                        }
                    }
                    return lab
                } else {
                    for (let i = 0; i < sel.length; i++) {
                        if (sel[i].value == val) {
                            return sel[i].key
                        }
                    }
                }
            },
            // 数据字典翻译
            getDictionary(sel, dicList) {
                if (sel.length === 0) {
                    sel = dicList
                } else {
                    let optionList = sel
                    for (let i = 0; i < optionList.length; i++) {
                        for (let j = 0; j < dicList.length; j++) {
                            if (optionList[i].value === dicList[j].value) {
                                optionList[i].key = dicList[j].key
                            }
                        }
                    }
                }
            },
            //判断select的span显示隐藏
            selSpanShow(index, key, label) {
                //console.log(index,key,label)
                if (this.selectVisible) {
                    let _key = this.selectVisible.split('_')[1]
                    let _name = this.selectVisible.split('_')[0]
                    let _label = this.selectVisible.split('_')[2]

                    if (index == _name) {

                        return false
                    }

                }
                if (label == "select") {
                    return true
                }
            },
            // 基本文本框 change
            baseInputChange(val) {
                // console.log(val);
                if (this.row) {
                    this.row._changedata = true
                }
                this.$emit('baseInputChange', this.row,this.col, val)
            },
            //基本文本框 失去焦点
            baseInputBlur(val) {
                this.$emit('baseInputBlur', this.row,this.col, val)
            },
            //基本文本框 获取焦点
            baseInputFocus(val) {
                this.$emit('baseInputFocus',this.row,this.col,val)
            },

            // 基本文本框 change
            numInputChange(val) {
                // console.log(val);
                if (this.row) {
                    this.row._changedata = true
                }
                this.$emit('numInputChange',this.row,this.col,val)
            },
            //基本文本框 失去焦点
            numInputBlur() {
                this.$emit('numInputBlur',this.row,this.col)
            },
            //基本文本框 获取焦点
            numInputFocus() {
                this.$emit('numInputFocus',this.row,this.col)
            },

            // 日期 change
            dateChange(val) {
                // console.log(val);
                if (this.row) {
                    this.row._changedata = true
                }
                this.$emit('dateChange', this.row,this.col, val)
            },
            //日期 失去焦点
            dateBlur() {
                this.$emit('dateBlur',this.row,this.col)
            },
            //日期 获取焦点
            dateFocus() {
                this.$emit('dateFocus',this.row,this.col)
            },
            // 联想控件 change
            autocompleteChange(val){
                if (this.row) {
                    this.row._changedata = true
                }
                this.$emit('autocompleteChange', this.row,this.col, val)
            },
            // 联想控件 失去焦点
            autocompleteBlur(){
                this.$emit('autocompleteBlur',this.row,this.col)
            },
            // 联想控件 获取焦点
            autocompleteFocus() {
                this.$emit('autocompleteFocus',this.row,this.col)
            },
            // 下拉change事件
            selectChange(val){
                // console.log(val);
                if (this.row) {
                    this.row._changedata = true
                }
                this.$emit('selectChange',  this.row,this.col,val)
            },
            // 下拉控件 失去焦点
            selectBlur(){
                this.$emit('selectBlur',this.row,this.col)
            },
            // 下拉控件 获取焦点
            selectFocus() {
                this.$emit('selectFocus',this.row,this.col)
            },

            //向父组件暴露change方法
            transferChange(val) {
                // console.log(val);
                if (this.row) {
                    this.row._changedata = true
                }
                this.$emit('change', {"row": this.row,'col':this.col, "val": val})
            },
            //向父组件暴露Blur方法
            transferBlur() {
                this.$emit('blur')
            },
            //向父组件暴露Focus方法
            transferFocus() {
                this.$emit('focus')
            },
            aaa(a, b, c) {
                if (this.row) {
                    if (!b) this.row._error[a] = c
                    else delete this.row._error[a]
                }
            },
            /* validateFocus() {
               let form = this.$refs['ruleForm2'][0]
               if (form) form.validate(new Function());
             },
             validateBlur() {
               //this.visible = ''
               this.updateColWidth()
             }, */
            updateColWidth() {
                let that = this
                let _W = {}
                // 根据是否可编辑表格状态的变更列宽
                this.tableData.forEach(function (n) {
                    that.dropCol.forEach(function (nn) {
                        let val = _W[nn.prop] || "auto"
                        that.tempText = n[nn.prop]
                        //that.$nextTick(function() {
                        that.$refs.dddd.textContent = n[nn.prop]
                        that.$set(_W, nn.prop, Math.max(that.$refs.dddd.offsetWidth + 40, val == "auto" ? 0 : val))
                        //})
                    });
                })
                this.colWidth = _W;
            },
            updateAutoWidth(property){
                let that = this
                let _W = {}
                that.dropCol.forEach(function (nn) {
                    that.$set(_W, nn.prop, nn.width)
                });
                // 根据是否可编辑表格状态的变更列宽
                if(property){
                    this.showData.forEach(function (n) {

                        let val = _W[property] || "auto"
                        that.tempText = n[property]
                        //that.$nextTick(function() {
                        that.$refs.dddd.textContent = n[property]
                        that.$set(_W, property, Math.max(that.$refs.dddd.offsetWidth + 40, val == "auto" ? 0 : val))
                        //})

                    })
                }

                this.autoWidth = _W;
                console.log(this.autoWidth)
            },
            // 获取校验结果状态（是否存在有为通过的校验）
            getValidatorStatus() {
                /* this.$refs.ruleForm2.validate((valid) => {
                    console.log(valid);
         if (valid) {
           alert('submit!');
         } else {
           console.log('error submit!!')
         }
       }); */
                let wrong = this.showData.some(function (n) {
                    return Object.keys(n._error).length > 0;
                })
                if (wrong) {
                    window.console.log('--------> 未通过');
                    this.$emit('getValidatorStatus', false);
                    return false
                } else {
                    window.console.log('--------> 通过')
                    this.$emit('getValidatorStatus', true);
                    return true
                }
            },
            //联想控件接受子组件传递的选中的一行的数据
            rowData(val) {
                console.log(this.col)
                this.$set(this.propRowdata, this.cellrowIndex + this.col, val)
                let columnLinkData = {
                    prop: this.col,
                    autoCompleteRowData: val,
                    tableRowData: this.row
                }
                this.$emit("autocompleteRowData", columnLinkData);
            },
            //联想控件接受子组件传递的值
            syncService(data) {
                //	this.service = data;
            },
            //判断编辑框显示隐藏的函数
            getVisible(index, key, label) {
                // 判断显示隐藏
                // console.log(key + ':' + label)
                if (label == 'select') {
                    return true
                }
                if (label == 'checkbox') {
                    return true
                }
                if (this.visible) {
                    let _key = this.visible.split('_')[1]
                    let _name = this.visible.split('_')[0]
                    if (index == _name || label == 'select') {
                        return true
                    }
                }
                return false
            },
            addEditData() {
                console.log(this.showData.length)
                this.row = this.showData[this.showData.length]
                this.formData = this.row
                this.visible = this.showData.length + '_' + 'cc'
                this.selectVisible = this.showData.length + '_' + 'cc' + '_' + 'select'

            },
            //点击单元格可编辑事件

            btnClick(scope, key, event) {
                let index = scope.$index
                let el = event.currentTarget;
                this.edited = true
                this.row = scope.row
                this.formData = this.row
                this.col = key.prop
                this.cellrowIndex = scope.$index;
                if (el.querySelector(".selSpan")) {
                    el.querySelector(".selSpan").style.display = 'none';
                }
                //
                if (key.editType.linkage) {
                    let getLinkData = {
                        prop: key.editType.config,
                        linkLabel: this.propRowdata[this.cellrowIndex + key.editType.linkage]
                    }
                    this.$emit("getLinkData", getLinkData);
                }
                //   设置visible
                this.visible = index + '_' + key.prop
                this.selectVisible = index + '_' + key.prop + '_' + key.editType.label

                this.$nextTick(function () {
                    //点击当前元素查找input获取焦点
                    if (el.querySelector(".el-input__inner")) {
                        //el.querySelector(".el-input__inner").readOnly= false
                        el.querySelector(".el-input__inner").focus();
                    }
                    if (el.querySelector(".smartInput")) {
                        let serviceListId = this.serviceList[this.col].id
                        el.querySelector("#" + serviceListId).value = scope.row[key.prop];

                      /*  // 解决联想控件飞出页面问题
                        let bodyW = document.body.scrollWidth
                        let leftW = event.clientX
                        let divW =  document.getElementsByClassName('friendSearchList')[0]
                        if(bodyW-leftW<=100){
                            console.log(leftW)

                            divW.classList.add("friendSearchList_smart")


                            //divW.setAttribute('class','friendSearchList_smart')
                        }*/



                    }
                    //this.$refs['ruleForm2'][0].validate()
                    /* 	this.$refs.inputValue.getInputValue(scope.row[key.prop]) */
                    /* 	this.$refs.inputVal[index].$refs.input */
                })
                /* 	this.$refs.inputValue.getInputValue(scope.row[key.prop]) */
            },
            //监听 日期按键tab事件
            dateTabCell(scope, key, item) {
                var arr = [];
                let colIndex = 0;
                let rowIndex = scope.$index;
                for (let i in scope.row) {
                    console.log(i)
                    if (i.indexOf("_") == -1) {
                        arr.push(i)
                    }

                }
                for (let i = 0; i < arr.length; i++) {
                    if (key === arr[i]) {
                        colIndex = i + 1;
                        if (i + 1 === arr.length) {
                            colIndex = 0;
                            rowIndex = rowIndex + 1;
                        }
                    }
                }
                this.visible = rowIndex + '_' + arr[colIndex];

                let selLabel = ''
                for (let i = 0; i < this.tableHead.length; i++) {
                    if (arr[colIndex] == this.tableHead[i].prop) {
                        selLabel = this.tableHead[i].editType.label
                    }
                }
                this.selectVisible = rowIndex + '_' + arr[colIndex] + '_' + selLabel
                this.$emit("dateTabCell", scope, key)
            },
            //监听日期按键enter事件
            dateEnterCell(index, key) {
                this.visible = '';
                this.$emit("dateEnterCell", index, key)
            },

            //监听联想控件按键tab事件
            autocompleteEnter(scope, key, item) {
                var arr = [];
                let colIndex = 0;
                let rowIndex = scope.$index;
                for (let i in scope.row) {
                    console.log(i)
                    if (i.indexOf("_") == -1) {
                        arr.push(i)
                    }

                }
                for (let i = 0; i < arr.length; i++) {
                    if (key === arr[i]) {
                        colIndex = i + 1;
                        if (i + 1 === arr.length) {
                            colIndex = 0;
                            rowIndex = rowIndex + 1;
                        }
                    }
                }
                this.visible = rowIndex + '_' + arr[colIndex];

                let selLabel = ''
                for (let i = 0; i < this.tableHead.length; i++) {
                    if (arr[colIndex] == this.tableHead[i].prop) {
                        selLabel = this.tableHead[i].editType.label
                    }
                }
                this.selectVisible = rowIndex + '_' + arr[colIndex] + '_' + selLabel
                this.$emit("autocompleteEnter", scope, key)
            },
            //监听联想按键enter事件
            autocompleteTab(index, key) {
                this.visible = '';
                this.$emit("autocompleteTab", index, key)
            },

            //监听下拉按键tab事件
            selectTab(scope, key, item) {
                var arr = [];
                let colIndex = 0;
                let rowIndex = scope.$index;
                for (let i in scope.row) {
                    console.log(i)
                    if (i.indexOf("_") == -1) {
                        arr.push(i)
                    }

                }
                for (let i = 0; i < arr.length; i++) {
                    if (key === arr[i]) {
                        colIndex = i + 1;
                        if (i + 1 === arr.length) {
                            colIndex = 0;
                            rowIndex = rowIndex + 1;
                        }
                    }
                }
                this.visible = rowIndex + '_' + arr[colIndex];

                let selLabel = ''
                for (let i = 0; i < this.tableHead.length; i++) {
                    if (arr[colIndex] == this.tableHead[i].prop) {
                        selLabel = this.tableHead[i].editType.label
                    }
                }
                this.selectVisible = rowIndex + '_' + arr[colIndex] + '_' + selLabel
                this.$emit("selectTab", scope, key)
            },




            //监听按键tab事件
            toggleCell(scope, key, item) {
                var arr = [];
                let colIndex = 0;
                let rowIndex = scope.$index;
                for (let i in scope.row) {
                    console.log(i)
                    if (i.indexOf("_") == -1) {
                        arr.push(i)
                    }

                }
                for (let i = 0; i < arr.length; i++) {
                    if (key === arr[i]) {
                        colIndex = i + 1;
                        if (i + 1 === arr.length) {
                            colIndex = 0;
                            rowIndex = rowIndex + 1;
                        }
                    }
                }
                this.visible = rowIndex + '_' + arr[colIndex];

                let selLabel = ''
                for (let i = 0; i < this.tableHead.length; i++) {
                    if (arr[colIndex] == this.tableHead[i].prop) {
                        selLabel = this.tableHead[i].editType.label
                    }
                }
                this.selectVisible = rowIndex + '_' + arr[colIndex] + '_' + selLabel
            },
            //监听按键enter事件
            closeCell(index, key) {
                this.visible = '';
            },



            /** 基础table相关方法 */
            // 手动触发popover弹框
            popover(ev, prop) {
                for (let i = 0; i < this.tableHead.length; i++) {
                    if (this.tableHead[i].filterShow === true) {
                        if (this.tableHead[i].prop != prop) {
                            this.$refs[this.tableHead[i].prop][0].doClose()
                        }
                    }
                }
                this.$refs[prop][0].doToggle()
                ev.stopPropagation()
            },
            // 关闭表头筛选框
            pClose(prop) {
                this.$refs[prop][0].doClose()
            },
            // 拖拽时复选框选中与取消选中
            toggleSelection(rows) {
                if (rows) {
                    rows.forEach(row => {
                        this.$refs.multipleTable.toggleRowSelection(row);
                    });
                }
            },
            // 表头数据筛选
            doFilter(array, filters) {
                const filterKeys = Object.keys(filters)
                return array.filter((item) => {
                    return filterKeys.every(key => {
                        if (!filters[key].length) return true
                        return !!~filters[key].indexOf(item[key])
                    })
                })
            },
            // 过滤筛选列表数据
            unique(_this, arr, strArr) {
                strArr.map(function (str) { // 筛选对象名称
                    let getArr = []
                    let dataArr = []
                    _this[arr[0]][str] = []
                    _this[arr[1]].map(function (item) { // 从arr对象中筛选对象
                        dataArr.push(item[str])
                    })
                    dataArr = Array.from(new Set(dataArr)) // 去重
                    dataArr.map(function (item, index) {
                        getArr.push({'name': item, 'value': index})
                    })
                    _this[str] = getArr
                })
            },
            // 获取表头搜索数据和列表数据
            elasticList: function () {
                let self = this
                let arr = []
                for (let i = 0; i < self.tableHead.length; i++) {
                    arr.push(self.tableHead[i].prop)
                }
                // 用于筛选列表，1、对象，2、[搜索条件，搜索原始列表]，3、[每个搜索条件](与数据库对应属性名称一致)
                self.unique(self, ['searchInfo', 'tableData'],
                    arr)
                for (let i = 0; i < arr.length; i++) {
                    self.tableHead[i]['filterData'] = this[arr[i]]
                }
            },
            // 提交表头筛选数据
            submitData(data) {
                let self = this
                if (data !== null) {
                    let name = Object.keys(data)
                    self.searchInfo[name] = data[name]
                    if (self.editable) {
                        if (data[name].length > 0) {
                            self.showData = self.doFilter(self.tableData, self.searchInfo)
                        } else {
                            // 如果为筛选条件空则清空展示数据
                            self.showData = []
                        }
                    } else {
                        if (data[name].length > 0) {
                            self.clonetableData = self.doFilter(self.tableData, self.searchInfo)
                        } else {
                            // 如果为筛选条件空则清空展示数据
                            self.clonetableData = []
                        }
                    }
                }
            },
            // 普通表格改变状态
            normalFormatter(prop, value) {
                let tableHead = this.tableHead
                for (let i = 0; i < tableHead.length; i++) {
                    let item = tableHead[i]
                    if (prop == item.prop) {
                        if (item.formatterType == undefined) {
                            return value
                        } else {
                            let list = item.formatterType
                            if(typeof list =='string'){
                                let dataDic=null
                                // 判断从哪里获取数据
                                if(item.isLocalStorage){
                                    dataDic = JSON.parse(localStorage.getItem('FrosDictionary'))
                                }else{
                                    dataDic = this.$store.state.global.frosDictionary
                                }
                                // 当数据字典为空或找不到时候
                                if(dataDic===undefined || dataDic==null){
                                    return value
                                }else{

                                    // 如果数据字典里取不到对应的数据
                                    let dictList = dataDic[item.formatterType]
                                    if(dictList===undefined || dictList==null){
                                        return value
                                    }else{
                                        let dicValue = null
                                        for (let j = 0; j < dictList.length; j++) {
                                            if (value == dictList[j].value) {
                                                dicValue = dictList[j].key
                                            }
                                        }
                                        // 如果数据字典中没有翻译数据，返回key值
                                        if(dicValue===null){
                                            return value
                                        }else{
                                            return dicValue
                                        }
                                    }
                                }

                            }else{
                                for (let j = 0; j < list.length; j++) {
                                    if (value == list[j].value) {
                                        return list[j].key
                                    }
                                }

                            }

                        }
                    }
                }
            },
            /* // 编辑表格改变状态
             statusFormatter(row, column) {
                 let tableHead = this.tableHead
                 for (let i = 0; i < tableHead.length; i++) {
                     let item = tableHead[i]
                     if (column.property == item.prop) {
                         if (item.formatterType == undefined) {
                             return row[column.property]
                         } else {
                             let list = item.formatterType
                             for (let j = 0; j < list.length; j++) {
                                 if (row[column.property] == list[j].value) {
                                     return list[j].key
                                 }
                             }
                         }
                     }
                 }
             },*/

            // 单击事件
            singleClick(row, column, event, cell) {
                this.$emit('singleClick', row)
                clearTimeout(time)
                time = setTimeout(() => {
                    //console.log('单击' + column.id)
                }, 300)
            },
            // 双击事件
            doubleClick(row, column, event, cell) {
                clearTimeout(time)
                console.log('双击' + column.id)
            },
            // 筛选
            filterTag(value, row, column) {
                const property = column['property']
                return row[property] === value || row[property].value === value
            },
            // 全选
            handleSelectionChange(val) {
                console.log(val);
                this.SelectCurrentData = val
                this.$emit('handleSelectionChange', val)
            },
            // 调用父页面方法（表格刷新）
            refresh: function () {
                this.$emit('reFresh')
            },
            // 调用父页面方法（cell-单击）
            cellClick: function (row, column, cell, event) {
                this.$emit('cellClick', row, column, cell, event)
                clearTimeout(time)
                time = setTimeout(() => {   // 用于解决同一个单元格中既有单击事件又有双击事件
                }, 300)
            },
            // 调用父页面方法（cell-双击）
            cellDbclick: function (row, column, cell, event) {
                this.$emit('cellDbclick', row, column, cell, event)
                clearTimeout(time)
                time = setTimeout(() => {   // 用于解决同一个单元格中既有单击事件又有双击事件
                }, 300)
            },
            // 调用父页面方法（row-单击）
            rowClick: function (row, event, column) {
                this.$emit('rowClick', row, event, column)
            },
            // 调用父页面方法（row-双击）
            rowDbclick: function (row, event, column) {
                this.$emit('rowDbclick', row, event, column)
            },
            sortChange: function (column) {
                this.$emit('sortChange', column)
            },
            cellMouseEnter: function (row, column, cell, event) {
                this.cellTitle=cell.innerText
            },
            //列宽自适应
            columnAutoFormat: function () {
                this.currentColumnAuto = true
            },
            //取消列宽自适应
            columnDefaultFormat: function () {
                this.currentColumnAuto = false
            },
            //保存
            saveTableLayout: function () {
                //TODO 王浩补充frosCustomLayout中tableconfig部分
                console.info("调用接口保存布局")
            },
            //切换布局
            setCurrentTableLayout: function () {
                //TODO
                //layoutConfigList.tableconfig=xxx
            },
            //设置默认布局
            setDefaultTableLayout: function () {
                //TODO
            },
            //清除筛选条件
            cleanColumnFilter: function () {
                //TODO
            },
            // 弹框初始化
            headTable() {
                this.tableHead=[]
                this.data2=[]
                this.value2=[]
                for (let i = 0; i < this.dropCol.length; i++) {
                    this.data2.push(this.dropCol[i])
                    this.value2.push(this.dropCol[i].prop)
                }
                for (let i = 0; i < this.value2.length; i++) {
                    for (let j = 0; j < this.dropCol.length; j++) {
                        if (this.value2[i] === this.dropCol[j].prop) {
                            this.tableHead.push(this.dropCol[j])
                        }
                    }
                }
                // 初始化num值
                let numItem = []
                for (let i = 0; i < this.dropCol.length; i++) {
                    if (this.dropCol[i].fixed === true) {
                        numItem.push(this.dropCol[i].fixed)
                    }
                }
                this.num = numItem.length
            },
            // 显示剪切列弹框
            show() {
                this.ColCutDlgVisible = true
                let cities = this.dropCol
                this.data2 = []
                cities.forEach((city) => {
                    this.data2.push({
                        label: city.label,
                        prop: city.prop,
                        filter: city.filter,
                        key: city.prop
                    })
                })
            },
            closeShow() {
                let oData2=this.data2
                let num1 = oData2.length
                let oValue2 = this.value2
                let num2 =oValue2.length
                let num3 = this.tableHead.length
                if(num1!=num2 && num2!=num3){
                    this.ColCutDlgVisible = false
                    let list = []
                    for (let i = 0; i < this.tableHead.length; i++) {
                        list.push(this.tableHead[i].prop)
                    }
                    this.value2 = list

                }else{
                    this.ColCutDlgVisible = false
                }

            },
            // 获取排序的选项
            rightChange(key) {
                this.sortData = key
            },
            // 排序置顶
            stickTop() {
                // 改变剪切列顺序
                if (this.sortData.length == 1) {
                    for (let i = 0; i < this.value2.length; i++) {
                        for (let j = 0; j < this.sortData.length; j++) {
                            if (this.value2[i] == this.sortData[j]) {
                                let item = this.value2[i]
                                this.value2.splice(i, 1)
                                this.value2.unshift(item)
                            }
                        }
                    }
                } else if (this.sortData.length == 0) {
                    this.$message.error('请选择一条数据')
                } else {
                    this.$message.error('只能选择一条数据去排序')
                }

                // 改变表头数据
                // this.sortThead()
            },
            // 排序置底
            stickBottom() {
                // 改变剪切列顺序
                if (this.sortData.length == 1) {
                    for (let i = 0; i < this.value2.length; i++) {
                        for (let j = 0; j < this.sortData.length; j++) {
                            if (this.value2[i] == this.sortData[j]) {
                                let item = this.value2[i]
                                this.value2.splice(i, 1)
                                this.value2.push(item)
                            }
                        }
                    }
                } else if (this.sortData.length == 0) {
                    this.$message.error('请选择一条数据')
                } else {
                    this.$message.error('只能选择一条数据去排序')
                }
            },
            // 上一级
            upLevel() {
                // 改变剪切列顺序
                if (this.sortData.length == 1) {
                    for (let i = 0; i < this.value2.length; i++) {
                        for (let j = 0; j < this.sortData.length; j++) {
                            if (this.value2[i] == this.sortData[j]) {
                                let item = this.value2[i]
                                this.value2.splice(i, 1)
                                if (i - 1 <= 0) {
                                    this.value2.splice(0, 0, item)
                                } else {
                                    this.value2.splice(i - 1, 0, item)
                                }
                            }
                        }
                    }
                } else if (this.sortData.length == 0) {
                    this.$message.error('请选择一条数据')
                } else {
                    this.$message.error('只能选择一条数据去排序')
                }
            },
            // 下一级
            downLevel() {
                // 改变剪切列顺序
                if (this.sortData.length == 1) {
                    let tempList = JSON.parse(JSON.stringify(this.value2))
                    for (let i = 0; i < this.value2.length; i++) {
                        let val = this.value2[i]
                        for (let j = 0; j < this.sortData.length; j++) {
                            let sor = this.sortData[j]
                            if (val == sor) {
                                let item = tempList[i]
                                tempList.splice(i, 1)
                                if (Number(i) + 1 >= Number(tempList.length)) {
                                    tempList.splice(Number(tempList.length), 0, item)
                                } else {
                                    tempList.splice(Number(i) + 1, 0, item)
                                }
                            }
                        }
                    }
                    this.value2 = tempList // 数据置换
                } else if (this.sortData.length == 0) {
                    this.$message.error('请选择一条数据')
                } else {
                    this.$message.error('只能选择一条数据去排序')
                }
            },
            // 表头排序
            sortThead() {
                this.tableHead = []
                for (let i = 0; i < this.value2.length; i++) {
                    for (let j = 0; j < this.dropCol.length; j++) {
                        if (this.value2[i] === this.dropCol[j].prop) {
                            this.tableHead.push(this.dropCol[j])
                        }
                    }
                }
            },
            // 剪切列按钮点击事件
            handleChange(value, direction, movedKeys) {
                this.tableHead = []
                for (let i = 0; i < this.value2.length; i++) {
                    for (let j = 0; j < this.dropCol.length; j++) {
                        if (this.value2[i] == this.dropCol[j].prop) {
                            this.tableHead.push(this.dropCol[j])
                        }
                    }
                }
            },
            // 固定列
            combinNum() {
                for (let i = 0; i < this.tableHead.length; i++) {
                    if (this.tableHead[i].fixed != 'right') {
                        this.tableHead[i].fixed = false
                        if (i < this.num) {
                            this.tableHead[i].fixed = true
                        }
                    }
                }
            },
            // 剪切确定按钮
            transferSumit() {
                // 修改列排序
                this.sortThead()
                // 固定列
                this.combinNum()
                this.ColCutDlgVisible = false
            },
            // 分页
            handleSizeChange(pageSize) {
                this.$emit("getPagesize", pageSize)
                this.$emit('fatherMethod') // 调用父页面方法请求后台数据
                this.pgconfig.pageSize = pageSize;    //动态改变
                this.datafrom = (this.pgconfig.currentPage - 1) * pageSize + 1
                this.datato = this.pgconfig.currentPage * (pageSize)+this.clonetableData.length - this.pgconfig.pageSize
                // console.log(`每页 ${pageSize} 条`)
            },
            handleCurrentChange(currentPage) {
                this.$emit("getCurrentpage", currentPage)
                this.$emit('fatherMethod') // 调用父页面方法请求后台数据
                this.pgconfig.currentPage = currentPage;    //动态改变
                this.datafrom = (currentPage - 1) * this.pgconfig.pageSize + 1
                this.datato = currentPage * (this.pgconfig.pageSize)+this.clonetableData.length - this.pgconfig.pageSize
                // console.log(`当前页: ${currentpage}`)
            },
        }
    }
</script>
<!--菜单样式-->

<style>
    .friendSearchList_smart{
        width: auto;
        max-height: 315px;
        overflow: auto;
        background: #fff;
        z-index: 10;
        box-shadow: 0 10px 10px rgba(0, 0, 0, .2);
        border: 1px solid #ccc;
        position: absolute;
        top: -10px;
        right: 0px;
    }


    .el-transfer-panel__list .el-checkbox{display:block}
    .change:before{content:'*';color:red;margin-right: 3px}
    .frosbtn_hide{display: none}
    #contextMenu ul{
        background:#fff;
        border: 1px solid #ebeef5;
        border-radius: 4px;
        box-shadow: 0 2px 12px 0 rgba(0,0,0,.1);
    }
    #contextMenu .vue-contextmenu-listWrapper{
        font-family: Helvetica Neue,Helvetica,PingFang SC,Hiragino Sans GB,Microsoft YaHei,SimSun,sans-serif;
        font-weight: 400;
        padding:0
    }
    #contextMenu .vue-contextmenu-listWrapper .context-menu-list{
        padding: 5px 10px 10px 10px;
        font-size: 14px;
        color: #606266;
        cursor: pointer;
        outline: none;
        border-bottom:1px solid #ccc ;
        font-weight: 400;
    }
    #contextMenu .vue-contextmenu-listWrapper .context-menu-list:hover{
        color: #409eff;
        background:#ECF5FF
    }
    #contextMenu .vue-contextmenu-listWrapper .context-menu-list:last-child{
        border-bottom:none ;
    }

</style>
<!--表单校验样式-->
<style>
    .FrosTablemultiple .el-form-item__error {
        background: #f56c6cd6;
        border: 1px solid red;
        border-radius: 5px;
        padding: 8px 3px;
        z-index: 99;
        position: absolute;
        top: 105%;
        /* left: 103%; */
        /* width: 90px; */
        /* height: 30px; */
        display: none;
        color: #fff;
        z-index: 9999;
    }
    .FrosTablemultiple td:hover .el-form-item__error{
        display:block
    }
    /* 自定义表头标签设置颜色*/
    .red-star{
        color: #ff0000;
    }
</style>
<style scoped>
    .el-table .cell {
        padding-left: 4px;
        padding-right: 4px;
    }

    .FrosTablemultiple .el-form-item {
        position: absolute;
        top: 0;
        width: 90%;
    }

    /* #fros_transfer .el-transfer-panel {
       padding-bottom: 40px;
     }*/

    #frosTransfer li {
        display: inline-block;
        padding: 8px 5px;
        margin: 0 3px
    }

    .pagebox {
        margin-top: 20px;
        clear: both;
    }

    .pagelist {
        display: inline-block;
        float: right
    }

    .btn_left i {
        font-size: 18px;
        margin-right: 10px
    }

    .frosVailt {
        width: 100%;
        height: 100%;
    }

    /** 可编辑table样式*/
    .table_form_error {
        border: 1px solid red;
        position: absolute;
        right: 0;
        top: 0;
        width: 100%;
        height: 100%;
        z-index: 0
    }

    .el-input__inner {
        height: 30px;
        line-height: 30px;
    }

    .hideSpan {
        position: relative;
        z-index: 3;
        width: 100%;
        display: inline-block;
        height:100%;
    }

    .selSpan {
        position: relative;
        z-index: 3;
        width: 100%;
        display: inline-block;
        height:100%;
    }

    .hiddenInput >>> .el-input__inner {
        height: 0;
        line-height: 0;
        padding: 0;
        border: indianred;
        display: none;
    }
    .mr0{
        margin-right: 0;
    }
    .hiddenItem {
        height: 0;
        line-height: 0;
    }

    .hiddenInput >>> .el-input {
        height: 0;
        line-height: 0;
    }

    .hiddenItem >>> .el-form-item__content {
        height: 0;
        line-height: 0;
    }

    .pagebox {
        margin-top: 20px;
        clear: both;
    }

    .pagelist {
        display: inline-block;
        float: right
    }

    .el-checkbox + .el-checkbox {
        margin-left: 0px;
    }

    .btn_left i {
        font-size: 18px;
        margin-right: 10px
    }

    .friendSearchContainer {
        position: static;
    }

    .cellpopover {
        position: absolute;
        border-radius: 10px;
        border: 1px solid rgba(255, 55, 53, 0.59);
        background-color: #f4e4ca;
    }

    /*.menu-box-card {*/
    /*width: 480px;*/
    /*position: absolute;*/
    /*}*/
    .menu__item {
        display: block;
        line-height: 20px;
        text-align: center;
        margin-top: 10px;
    }

    /*li:hover {*/
    /*background-color: #1790ff;*/
    /*color: white;*/
    /*}*/
</style>
