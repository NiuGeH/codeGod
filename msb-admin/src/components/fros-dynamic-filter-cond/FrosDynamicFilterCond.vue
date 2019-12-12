<template>
    <el-card class="box-card" :style="cardStyle">
        <div slot="header" class="clearfix">
            <span>高级表单查询</span>
        </div>
        <div class="" id="contextMenu" @contextmenu="frosDynamicFormShowMenu">
            <!-- 右键菜单列表 -->
            <vue-context-menu :contextMenuData="contextMenuData"
                              @saveTheConditionAs="saveTheConditionAs"
                              @editCondition="editCondition"
                              @deleteCondition="deleteCondition"
                              @frosDynamicFormModelType="frosDynamicFormModelType">
            </vue-context-menu>

            <el-form label-width="auto" ref="frosDynamicForm" :size="size">
                <el-form-item
                        v-for="(domain, index) in modelDomains"
                        :label="domain.label"
                        :key="index"
                        :prop="domain.prop">
                    <span style="position:absolute;left:-15px">：</span>

                    <el-input v-if="domain.type == 'input'" :type="domain.inputType" v-model="domain.value"
                              :placeholder="domain.placeholder"></el-input>

                    <el-radio-group v-if="domain.type == 'radio'" v-model="domain.value">
                        <el-radio
                                v-for="(option, index) in domain.options"
                                :label="option.value"
                                :key="index">{{option.label}}
                        </el-radio>
                    </el-radio-group>

                    <el-checkbox-group v-if="domain.type == 'checkbox'" v-model="domain.value">
                        <el-checkbox
                                v-for="(option, index) in domain.options"
                                :label="option.value"
                                :key="index">{{option.label}}
                        </el-checkbox>
                    </el-checkbox-group>

                    <fros-date-picker v-if="domain.type == 'fros-date'" ref="frosDomainRef"
                                      v-model="domain.value"
                                      type="date"
                                      :frosDefValue="domain.value"
                                      :placeholder="domain.placeholder"
                                      @focus="frosDatePickerFocus"
                                      @blur="frosDatePickerBlur"
                                      @getDateChange="frosDatePickerGetDateChange"
                                      @change="frosDatePickerChange">
                    </fros-date-picker>

                    <fros-date-picker v-if="domain.type == 'fros-date-time'" ref="frosDomainRef" v-model="domain.value"
                                      type="datetime" :frosDefValue="domain.value"
                                      :placeholder="domain.placeholder"
                                      @focus="frosDatePickerFocus"
                                      @blur="frosDatePickerBlur"
                                      @getDateChange="frosDatePickerGetDateChange"
                                      @change="frosDatePickerChange">
                    </fros-date-picker>

                    <fros-select v-if="domain.type == 'fros-select'" ref="frosDomainRef" :options="domain.options"
                                 v-model="domain.value"
                                 :frosDefValue="domain.value" :dictType="domain.dictType" :isDict="domain.isDict"
                                 :dictData="domain.dictData" :isLocalStorage="domain.isLocalStorage"
                                 :placeholder="domain.placeholder"
                                 @focus="fSelectBlur"
                                 @blur="fSelectFocus"
                                 @getSelectValue="fSelectGetSelectValue"></fros-select>

                    <fros-select v-if="domain.type == 'fros-multiple-select'" ref="frosDomainRef"
                                 :options="domain.options"
                                 v-model="domain.value" :frosDefValue="domain.value" :dictType="domain.dictType"
                                 :isDict="domain.isDict" :dictData="domain.dictData"
                                 :isLocalStorage="domain.isLocalStorage" multiple="multiple"
                                 :placeholder="domain.placeholder"
                                 @focus="fSelectBlur"
                                 @blur="fSelectFocus"
                                 @getSelectValue="fSelectGetSelectValue"></fros-select>

                    <fros-auto-complete v-if="domain.type == 'fros-auto-complete'" ref="frosDynamicFilterAutoComplete"
                                        :frosDefValue='domain.value'
                                        v-model="domain.value"
                                        :props="domain.serviceList"
                                        @blur="frosAutoCompleteEmitBlur"
                                        @focus="frosAutoCompleteEemitFocus"
                                        @rowData='frosAutoCompleteGetRowData'
                                        @appendQueryFields="frosAutoCompleteAppendQueryFields"></fros-auto-complete>

                    <fros-base-input v-if="domain.type == 'fros-base-input'" ref="frosDomainRef"
                                     :placeholder="domain.placeholder"
                                     :frosDefValue="domain.value"
                                     :type="domain.inputType" v-model="domain.value" :frostrim="domain.frostrim"
                                     :frosCode=true :frosCase=true
                                     @change="fBaseInputChange"
                                     @focus="fBaseInputFocus"
                                     @blur="fBaseInputBlur">
                    </fros-base-input>

                    <fros-num-input v-if="domain.type == 'fros-num-input'" ref="frosDomainRef"
                                    :placeholder="domain.placeholder" :length="10"
                                    v-model="domain.value" :frosDefValue="domain.value" :frosCode=true
                                    :frosCase=true
                                    :frostrim="domain.frostrim" :onlyNum=true :frosNegative=false :frosRounding=true
                                    :frosPrecision="2" :frosOperation=false
                                    @change="fNumInputChange"
                                    @focus="fNumInputFocus"
                                    @blur="fNumInputBlur">
                        <!-- maxlength="10"-->
                    </fros-num-input>
                </el-form-item>
            </el-form>

            <el-row style="text-align: center">
                <el-button type="primary" :size="size" @click="frosDynamicFilterCondConfig">条件配置</el-button>
                <el-button type="primary" :size="size" @click="frosDynamicResetForm()">重置表单</el-button>
                <el-button type="primary" :size="size" @click="frosDynamicFilterCondQuery">查询</el-button>
            </el-row>
        </div>

        <el-dialog title="条件配置" :visible.sync="frosDynamicFilterCondConfigVisible" width="535px">
            <div id="fros_transfer">
                <el-transfer v-model="frosDynamicFilterCondValue"
                             :data="frosDynamicFilterCondData"
                             :props="{ key: 'prop'}"
                             :titles="['未显示列', '显示列']"
                             filterable
                             target-order="push"
                             @right-check-change="rightChange">
                </el-transfer>
                <div>
                    <ul id="frosTransfer" class="transfer-footer"
                        slot="right-footer" style="height:20px;float:right;">
                        <li @click="stickTop" title="置顶"><i class="el-icon-upload2"></i></li>
                        <li @click="stickBottom" title="置底"><i class="el-icon-download"></i></li>
                        <li @click="upLevel" title="向上"><i class="el-icon-caret-top"></i></li>
                        <li @click="downLevel" title="向下"><i class="el-icon-caret-bottom"></i></li>
                    </ul>
                </div>
            </div>
            <span slot="footer" class="dialog-footer">
                <el-button size="mini" @click="frosDynamicFilterCondConfigVisible = false">取 消</el-button>
                <el-button size="mini" type="primary" @click="buildCodeProducer">确定</el-button>
            </span>
        </el-dialog>

        <el-dialog title="条件另存为" :visible.sync="frosDynamicFilterCondSaveVisible" width="535px">
            <div class="com-dialog-s-title">模板信息：</div>
            <el-form ref="frosDynamicFilterCondSaveAs" :model='form' label-width="120px" size="mini"
                     style="width: 300px">
                <el-form-item label="分组名称" prop="modelName" :rules="[{ required: true, message: '组名不能为空。'} ]">
                    <span style="position:absolute;left:-15px">：</span>
                    <el-input v-model="form.modelName"></el-input>
                </el-form-item>
                <el-form-item label="分组CODE" prop="modelCode" :rules="[{ required: true, message: 'CODE不能为空。'} ]">
                    <span style="position:absolute;left:-15px">：</span>
                    <el-input v-model="form.modelCode"></el-input>
                </el-form-item>
            </el-form>
            <div class="com-dialog-s-title">待保存数据：</div>
            <el-form :model='form' label-width="120px" size="mini">
                <el-form-item
                        v-for="(item, index) in dataToBeSaved"
                        :label="item.key"
                        :key="index"
                        :prop="item.prop">
                    <span style="position:absolute;left:-15px">：</span>

                    <el-input v-if="item.type == 'input'" :type="item.inputType" v-model="item.value">
                    </el-input>

                    <el-radio-group v-if="item.type == 'radio'" v-model="item.value">
                        <el-radio
                                v-for="(option, index) in item.options"
                                :label="option.value"
                                :key="index">{{option.label}}
                        </el-radio>
                    </el-radio-group>

                    <el-checkbox-group v-if="item.type == 'checkbox'" v-model="item.value">
                        <el-checkbox
                                v-for="(option, index) in item.options"
                                :label="option.value"
                                :key="index">{{option.label}}
                        </el-checkbox>
                    </el-checkbox-group>

                    <span v-if="item.type == 'fros-date'">
                        <el-date-picker
                                v-model="item.value"
                                type="date"
                                placeholder="选择日期时间"
                                style="width: 175px"
                        >
                        </el-date-picker>
                        <el-select
                                v-model="item.dateSelectClass"
                                :default="item.dateSelectClass"
                                style="width: 100px">
                            <el-option
                                    v-for="(item,index) in dateClassSelect"
                                    :key="index"
                                    :label="item.key"
                                    :value="item.value">
                            </el-option>
                        </el-select>
                        <el-input v-model.number="item.dateFloatValue" placeholder="增加天数" style="width: 100px;"></el-input>
                    </span>
                    <span v-if="item.type == 'fros-date-time'">
                        <el-date-picker
                            v-model="item.value"
                            type="datetime"
                            placeholder="选择日期时间"
                            style="width: 175px"
                        >
                        </el-date-picker>
                        <el-select
                                v-model="item.dateSelectClass"
                                :default="item.dateSelectClass"
                                style="width: 100px">
                            <el-option
                                    v-for="(item,index) in dateClassSelect"
                                    :key="index"
                                    :label="item.key"
                                    :value="item.value">
                            </el-option>
                        </el-select>
                        <el-input v-model.number="item.dateFloatValue" placeholder="增加天数" style="width: 100px;"></el-input>
                    </span>

                    <fros-select v-if="item.type == 'fros-select'" :options="item.options"
                                 v-model="item.value"
                                 :frosDefValue="item.value" :dictType="item.dictType" :isDict="item.isDict"
                                 :dictData="item.dictData" :isLocalStorage="item.isLocalStorage"></fros-select>

                    <fros-select v-if="item.type == 'fros-multiple-select'"
                                 :options="item.options"
                                 v-model="item.value" :frosDefValue="item.value" :dictType="item.dictType"
                                 :isDict="item.isDict" :dictData="item.dictData"
                                 :isLocalStorage="item.isLocalStorage"
                                 multiple="multiple"></fros-select>

                    <fros-auto-complete v-if="item.type == 'fros-auto-complete'"
                                        :frosDefValue='item.value'
                                        v-model="item.value"
                                        :props="item.serviceList"></fros-auto-complete>

                    <fros-base-input v-if="item.type == 'fros-base-input'"
                                     :frosDefValue="item.value"
                                     :type="item.inputType" v-model="item.value" :frostrim="item.frostrim"
                                     :frosCode=true :frosCase=true>
                    </fros-base-input>

                    <fros-num-input v-if="item.type == 'fros-num-input'"
                                    :length="10"
                                    v-model="item.value" :frosDefValue="item.value" :frosCode=true
                                    :frosCase=true
                                    :frostrim="item.frostrim" :onlyNum=true :frosNegative=false :frosRounding=true
                                    :frosPrecision="2" :frosOperation=false>
                        <!-- maxlength="10"-->
                    </fros-num-input>
                </el-form-item>
            </el-form>
            <el-row style="text-align: right">
                <el-button @click="frosDynamicFilterCondSaveVisible = false">取 消</el-button>
                <el-button type="primary" @click="frosDynamicFilterCondSaveAs">确定</el-button>
            </el-row>
        </el-dialog>
    </el-card>
</template>
<script>
    import FrosDatePicker from '@/components/fros-date-picker/FrosDatePicker'
    import FrosAutoComplete from '@/components/fros-autocomplete/frosAutoComplete'
    import FrosSelect from '../fros-select/FrosSelect'
    import FrosBaseInput from '../fros-input/FrosBaseInput.vue'
    import FrosNumInput from '../fros-input/FrosNumInput.vue'
    import FrosTable from '../fros-table/FrosTable'
    export default {
        components: {
            FrosAutoComplete,
            FrosSelect,
            FrosDatePicker,
            FrosBaseInput, // 基础文本
            FrosNumInput, // 数字文本
            FrosTable
        },
        props: {
            height: null,   // 用于设置高度。
            frosDfcd: null,  // 存储动态表单所有数据
            domains: null,  // 配置表单所有展示项
            defShowForm: null,   // 默认渲染表单项
            conditionLists: null    // 默认所有可配置菜单项
        },
        data() {
            return {
                cardStyle: '',  // 当height为auto时则根据父级div自适应。
                form: {
                    groupName: '',  // 分组名称
                    modelName: '',
                    modelCode: ''
                },
                menuVisible: false,
                size: "mini",   //  表单按钮以及输入框展示尺寸medium / small / mini
                // 条件配置相关配置项
                transferRightData: [],  // 穿梭框显示列勾选数据
                frosDynamicFilterCondConfigVisible: false,    // 条件配置穿梭框
                frosDynamicFilterCondData: [],
                frosDynamicFilterCondValue: [],  // 已选条件
                frosDynamicFilterCondSaveVisible: false,    // 条件另存为弹出框
                defDomains: [], // 默认渲染项
                modelDomains: [],   // 渲染表单数据
                nowMobelCode: '',    // 现在使用模板名称
                // 动态添加右键菜单
                rightList: [],
                // 右键菜单
                contextMenuData: {
                    // the contextmenu name(@1.4.1 updated)
                    menuName: 'demo',
                    // The coordinates of the display(菜单显示的位置)
                    axis: {x: null, y: null},
                    // Menu options (菜单选项)
                    menulists: [
                        {
                            fnHandler: 'saveTheConditionAs', // Binding events(绑定事件)
                            btnName: '条件另存为' // The name of the menu option (菜单名称)
                        },
                        {
                            fnHandler: 'editCondition',
                            btnName: '修改模板条件'
                        },
                        {
                            fnHandler: 'deleteCondition',
                            btnName: '删除条件'
                        }
                    ]
                },
                /* Table组件相关配置 */
                multipleSelection: [],  // 用于记录多选数据
                headerAlign: 'center',//列头对齐方式
                // 表格基础数据
                dataToBeSaved: [],
                buttonListShow: false,  // 是否显示隐藏列
                menuCopy: [
                    {
                        fnHandler: 'saveTheConditionAs', // Binding events(绑定事件)
                        btnName: '条件另存为' // The name of the menu option (菜单名称)
                    },
                    {
                        fnHandler: 'editCondition',
                        btnName: '修改模板条件'
                    },
                    {
                        fnHandler: 'deleteCondition',
                        btnName: '删除条件'
                    }
                ],    // 右键菜单默认模板
                addQueryFieldList: null, // 用于联想控件动态添加条件变量
                dateClassSelect: [
                    {
                        key: '当前时间',
                        value: 0
                    },{
                        key: '当月月初',
                        value: 1
                    },{
                        key: '当月月末',
                        value: 2
                    }
                ] // 条件另存为时时间组件选择类型（当前时间、当月月初、当月月末）
            }
        },
        computed: {},
        watch: {
            domains: {
                handler(val, oldVal) {
                    this.domains = val
                },
                immediate: true,
                deep: true
            },
            frosDfcd: {
                handler(val, oldVal) {
                    this.frosDfcd = val
                    if (val != null && val.showItem != undefined) {
                        this.defDomains = val.showItem
                        this.defCodeProducer() // 默认代码生成器
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
                },
                immediate: true,
                deep: true
            }
        },
        mounted() {
            if(this.height == 'auto'){
                this.changeHeight() // 自适应高度
            }/*else{
                this.cardStyle = 'overflow: auto; height:'+ this.height + 'px'
            }*/
            // 设置默认表单
            if (this.frosDfcd != null && this.frosDfcd.showItem != undefined) {
                this.defDomains = this.frosDfcd.showItem
                this.menuProducer() // 菜单生成器
                this.addMenu()  // 动态添加右键菜单
            }
            this.frosDynamicFilterCondData = this.conditionLists
            this.defDomains = this.defShowForm  // 传递默认展示值
            this.defCodeProducer() // 默认代码生成器
        },
        methods: {
            // 根据auto改变表单的高度
            changeHeight(){
                this.$nextTick(()=> {
                    let el = this
                    let currentDiv =el.$el
                    let parentDiv = currentDiv.parentNode
                    let parentDivHeight = parentDiv.offsetHeight
                    this.cardStyle = 'overflow: auto; height:'+ parentDivHeight + 'px'
                })
            },
            // 条件配置穿梭框右侧选择框change事件
            rightChange(key) {
                this.transferRightData = key
            },
            // 排序置顶
            stickTop() {
                // 改变剪切列顺序
                if (this.transferRightData.length == 1) {
                    for (let i = 0; i < this.frosDynamicFilterCondValue.length; i++) {
                        for (let j = 0; j < this.transferRightData.length; j++) {
                            if (this.frosDynamicFilterCondValue[i] == this.transferRightData[j]) {
                                let item = this.frosDynamicFilterCondValue[i]
                                this.frosDynamicFilterCondValue.splice(i, 1)
                                this.frosDynamicFilterCondValue.unshift(item)
                            }
                        }
                    }
                } else if (this.transferRightData.length == 0) {
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
                if (this.transferRightData.length == 1) {
                    for (let i = 0; i < this.frosDynamicFilterCondValue.length; i++) {
                        for (let j = 0; j < this.transferRightData.length; j++) {
                            if (this.frosDynamicFilterCondValue[i] == this.transferRightData[j]) {
                                let item = this.frosDynamicFilterCondValue[i]
                                this.frosDynamicFilterCondValue.splice(i, 1)
                                this.frosDynamicFilterCondValue.push(item)
                            }
                        }
                    }
                } else if (this.transferRightData.length == 0) {
                    this.$message.error('请选择一条数据')
                } else {
                    this.$message.error('只能选择一条数据去排序')
                }
            },
            // 上一级
            upLevel() {
                // 改变剪切列顺序
                if (this.transferRightData.length == 1) {
                    for (let i = 0; i < this.frosDynamicFilterCondValue.length; i++) {
                        for (let j = 0; j < this.transferRightData.length; j++) {
                            if (this.frosDynamicFilterCondValue[i] == this.transferRightData[j]) {
                                let item = this.frosDynamicFilterCondValue[i]
                                this.frosDynamicFilterCondValue.splice(i, 1)
                                if (i - 1 <= 0) {
                                    this.frosDynamicFilterCondValue.splice(0, 0, item)
                                } else {
                                    this.frosDynamicFilterCondValue.splice(i - 1, 0, item)
                                }
                            }
                        }
                    }
                } else if (this.transferRightData.length == 0) {
                    this.$message.error('请选择一条数据')
                } else {
                    this.$message.error('只能选择一条数据去排序')
                }
            },
            // 下一级
            downLevel() {
                // 改变剪切列顺序
                if (this.transferRightData.length == 1) {
                    let tempList = JSON.parse(JSON.stringify(this.frosDynamicFilterCondValue))
                    for (let i = 0; i < this.frosDynamicFilterCondValue.length; i++) {
                        let val = this.frosDynamicFilterCondValue[i]
                        for (let j = 0; j < this.transferRightData.length; j++) {
                            let sor = this.transferRightData[j]
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
                    this.frosDynamicFilterCondValue = tempList // 数据置换
                } else if (this.transferRightData.length == 0) {
                    this.$message.error('请选择一条数据')
                } else {
                    this.$message.error('只能选择一条数据去排序')
                }
            },
            // 默认生成表单方法
            defCodeProducer: function () {
                let self = this
                let defDomains = self.defDomains
                let domains = self.domains
                if (defDomains.length > 0) {
                    self.modelDomains = []  // 如果有默认展示项则先清空展示项再赋值
                    for (let i = 0; i < defDomains.length; i++) {
                        for (let j = 0; j < domains.length; j++) {
                            if (defDomains[i] == domains[j].prop) {
                                self.modelDomains.push(domains[j])
                            }
                        }
                    }
                    self.frosDynamicFilterCondValue = self.defDomains
                } else {
                    /*self.modelDomains = self.domains // 如果默认模板为空则显示全部
                    let tempList = []
                    for (let i = 0; i < self.domains.length; i++) {
                        tempList.push(self.domains[i].prop)
                    }
                    self.frosDynamicFilterCondValue = tempList*/

                    self.modelDomains = null // TODO 如果默认模板为空则不显示
                    self.frosDynamicFilterCondValue = []

                }
            },
            // 构建生成表单方法
            buildCodeProducer: function () {
                let self = this
                let selDomains = self.frosDynamicFilterCondValue
                let domains = self.domains
                if (selDomains.length > 0) {
                    self.modelDomains = []  // 置空已显示表单
                    for (let i = 0; i < selDomains.length; i++) {
                        for (let j = 0; j < domains.length; j++) {
                            if (selDomains[i] == domains[j].prop) {
                                self.modelDomains.push(domains[j])
                            }
                        }
                    }
                } else {
                    // self.defCodeProducer()  // 调用默认显示项
                    self.frosDynamicFilterCondValue = []  // 已选条件置空
                }
                self.frosDynamicFilterCondConfigVisible = false    // 关闭条件配置默认框
                this.$emit('frosDynamicFilterCondDefShowForm', selDomains)
            },
            // 获取条件配置数据
            frosDynamicFilterCondConfig: function () {
                this.frosDynamicFilterCondConfigVisible = true    // 打开条件配置默认框
            },
            // 重置表单
            frosDynamicResetForm: function () {
                let refsList = []
                let autoCompleteRefsList = []
                for (let i = 0; i < this.modelDomains.length; i++) {
                    if (this.modelDomains[i].type == 'fros-num-input' || this.modelDomains[i].type == 'fros-base-input' || this.modelDomains[i].type == 'fros-multiple-select' || this.modelDomains[i].type == 'fros-select' || this.modelDomains[i].type == 'fros-date-time' || this.modelDomains[i].type == 'fros-date') {
                        refsList.push(this.modelDomains[i].prop)
                    }
                    if (this.modelDomains[i].type == 'fros-auto-complete') {
                        autoCompleteRefsList.push(this.modelDomains[i].prop)
                    }
                    if (this.modelDomains[i].type == 'checkbox' || this.modelDomains[i].type == 'multipleselect') {
                        this.modelDomains[i].value = []
                    } else {
                        this.modelDomains[i].value = ''
                    }
                }
                // 如果动态表单存在自定义组件则调用自定义组件清除方法清除
                if (refsList.length > 0) {
                    for (let j = 0; j < refsList.length; j++) {
                        this.$refs['frosDomainRef'][j].clearFormValue()
                    }
                }
                // 如果动态表单存在自定义联想控件则调用自定义组件清除方法清除
                if (autoCompleteRefsList.length > 0) {
                    for (let z = 0; z < autoCompleteRefsList.length; z++) {
                        this.$refs['frosDynamicFilterAutoComplete'][z].clearFormValue()
                    }
                }
            },
            // 查询
            frosDynamicFilterCondQuery: function () {
                let formData = {}
                for (let i = 0; i < this.modelDomains.length; i++) {
                    formData[this.modelDomains[i].prop] = this.modelDomains[i].value
                }
                this.$emit('frosDynamicFilterCondQuery', formData)
            },
            // 条件另存为
            frosDynamicFilterCondSaveAs: function () {
                this.$refs['frosDynamicFilterCondSaveAs'].validate((valid) => {
                    if (valid) {
                        // 处理返回值
                        let tempObj = {}
                        for (let i = 0; i < this.dataToBeSaved.length; i++) {
                            if(this.dataToBeSaved[i]['type'] =="fros-date" || this.dataToBeSaved[i]['type'] =="fros-date-time"){
                                let dateSelectClass = this.dataToBeSaved[i]['dateSelectClass'] // 0:当前时间，1:当月月初，2:当月月末
                                let floatValue = this.dataToBeSaved[i]['dateFloatValue']
                                if(dateSelectClass == 0){
                                    let data = new Date()
                                    tempObj[this.dataToBeSaved[i]['prop']] = new Date(data.setDate(data.getDate() + floatValue))
                                }else if(dateSelectClass == 1){
                                    let data = new Date(this.getCurrentMonthFirst())
                                    tempObj[this.dataToBeSaved[i]['prop']] = new Date(data.setDate(data.getDate() + floatValue))
                                }else if(dateSelectClass == 2){
                                    let data = new Date(this.getCurrentMonthLast())
                                    tempObj[this.dataToBeSaved[i]['prop']] = new Date(data.setDate(data.getDate() + floatValue))
                                }else{
                                    tempObj[this.dataToBeSaved[i]['prop']] = this.dataToBeSaved[i]['value']
                                }
                            }else
                                tempObj[this.dataToBeSaved[i]['prop']] = this.dataToBeSaved[i]['value']
                        }
                        // 校验通过向父级传参
                        this.$emit('frosDynamicFilterCondSaveAs', this.form.modelName, this.form.modelCode, tempObj)
                        this.frosDynamicFilterCondSaveVisible = false   // 关闭dialog
                    } else {
                        return false;
                    }
                });
            },
            // 获取当前月的第一天
            getCurrentMonthFirst(){
                var date=new Date();
                date.setDate(1);
                return date;
            },
            // 获取当前月的最后一天
            getCurrentMonthLast(){
                var date=new Date();
                var currentMonth=date.getMonth();
                var nextMonth=++currentMonth;
                var nextMonthFirstDay=new Date(date.getFullYear(),nextMonth,1);
                var oneDay=1000*60*60*24;
                return new Date(nextMonthFirstDay-oneDay);
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
            // 添加菜单项
            addMenu() {
                if (this.rightList.length > 0) {
                    for (let i = 0; i < this.rightList.length; i++) {
                        this.contextMenuData.menulists.push(this.rightList[i])
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
            // 鼠标右键菜单
            frosDynamicFormShowMenu() {
                event.preventDefault()
                var x = event.clientX
                var y = event.clientY
                // Get the current location
                this.contextMenuData.axis = {
                    x, y
                }
            },
            // 条件另存为模板
            saveTheConditionAs() {
                let temp = []
                for (let i = 0; i < this.domains.length; i++) {
                    let dValue = this.domains[i].value
                    if (dValue != '' && dValue != undefined && dValue != []) {
                        let tempObj = {}
                        tempObj.key = this.domains[i].label
                        tempObj.prop = this.domains[i].prop
                        tempObj.value = this.domains[i].value
                        tempObj.type = this.domains[i].type
                        tempObj.inputType = this.domains[i].inputType
                        tempObj.options = this.domains[i].options
                        tempObj.defValue = this.domains[i].defValue
                        tempObj.dictData = this.domains[i].dictData
                        tempObj.dictType = this.domains[i].dictType
                        tempObj.isDict = this.domains[i].isDict
                        tempObj.isLocalStorage = this.domains[i].isLocalStorage
                        tempObj.serviceList = this.domains[i].serviceList
                        tempObj.frostrim = this.domains[i].frostrim
                        tempObj.dateSelectClass = 0 // 0:当前时间，1:当月月初，2:当月月末
                        tempObj.dateFloatValue = 0
                        temp.push(tempObj)
                    }
                }
                this.dataToBeSaved = temp
                this.frosDynamicFilterCondSaveVisible = true    // 打开另存为dialog
            },
            // 编辑保存模板
            editCondition() {
                if (this.nowMobelCode == null || this.nowMobelCode == '') {
                    this.$message.error('暂未选中任何模板。');
                    return false
                } else {
                    let tempObj = {}
                    for (let i = 0; i < this.domains.length; i++) {
                        let dValue = this.domains[i].value
                        if (dValue != '' && dValue != undefined && dValue != []) {
                            tempObj[this.domains[i].prop] = this.domains[i].value
                        }
                    }
                    this.$emit('frosDynamicFilterCondEditSave', this.nowMobelCode, tempObj)
                }
            },
            // 删除模板
            deleteCondition() {
                if (this.nowMobelCode == null || this.nowMobelCode == '') {
                    this.$message.error('暂未选中任何模板。');
                    return false
                } else {
                    this.$emit('frosDynamicFilterCondDelete', this.nowMobelCode)
                }
            },
            // 右键模板切换
            frosDynamicFormModelType() {
                let el = event.currentTarget.innerText
                let list = this.contextMenuData.menulists
                let modelCode = list.find(item => item.btnName == el.trim()).modelCode
                this.nowMobelCode = modelCode
                // 右侧菜单id 传到父级
                let self = this
                if (this.frosDfcd != null) {
                    let defModel = this.frosDfcd.defModel
                    let defVal = null
                    if (defModel != undefined || defModel != '') {
                        for (let i = 0; i < defModel.length; i++) {
                            if (defModel[i].modelCode == modelCode) {
                                defVal = defModel[i].defValue
                                break
                            }
                        }
                    }
                    if (defVal != null) {
                        for (let key in defVal) {
                            for (let j = 0; j < self.modelDomains.length; j++) {
                                if (key == self.modelDomains[j].prop) {
                                    self.modelDomains[j].value = defVal[key]
                                }
                            }
                        }
                    }
                }
            },
            /**
             * 联想控件相关触发方法
             * */
            // 失去焦点
            frosAutoCompleteEmitBlur(val) {
                this.$emit('fAutoCompleteBlur',val)
            },
            // 获取焦点
            frosAutoCompleteEemitFocus(val) {
                this.$emit('fAutoCompleteFocus',val)
            },
            frosAutoCompleteGetRowData(row) {
                this.$emit('fAutoCompleteGetRowData',row)
            },

            // 动态添加queryFields
            appendQueryFields(val){
                if(val != null){
                    this.addQueryFieldList = val
                }
            },
            // 联想控件请求前触发事件
            frosAutoCompleteAppendQueryFields(){
                console.log('联想控件请求前触发方法。')

                this.$emit('fAppendQueryFields')    // 用于触发父级方法
                let autoCompleteRefsList = []
                for (let i = 0; i < this.modelDomains.length; i++) {
                    if (this.modelDomains[i].type == 'fros-auto-complete') {
                        autoCompleteRefsList.push(this.modelDomains[i].prop)
                    }
                }

                // 如果动态表单存在自定义联想控件则调用自定义组件清除方法清除
                if (autoCompleteRefsList.length > 0) {
                    for (let j = 0; j < autoCompleteRefsList.length; j++) {
                        this.$refs["frosDynamicFilterAutoComplete"][j].appendQueryFields(this.addQueryFieldList)
                    }
                }

            },
            /**
             * 下拉框触发方法
             * */
            // 失去焦点
            fSelectBlur(val) {
                this.$emit('fSelectBlur',val)
            },
            // 获取焦点
            fSelectFocus(val) {
                this.$emit('fSelectFocus',val)
            },
            fSelectGetSelectValue(val) {
                this.$emit('fSelectGetSelectValue', val)
            },
            /**
             * 基础文本框触发方法
             * */
            // 失去焦点
            fBaseInputBlur() {
                this.$emit('fBaseInputBlur')
            },
            // 获取焦点
            fBaseInputFocus() {
                this.$emit('fBaseInputFocus')
            },
            // 获取change事件
            fBaseInputChange(val) {
                // this.$emit('fBaseInputChange',val)
            },
            /**
             * 数字文本框触发方法
             * */
            // 失去焦点
            fNumInputBlur() {
                this.$emit('fNumInputBlur')
            },
            // 获取焦点
            fNumInputFocus() {
                this.$emit('fNumInputFocus')
            },
            // 获取change事件
            fNumInputChange(val) {
                this.$emit('fNumInputChange',val)
            },
            /**
             * 日期控件相关触发方法
             * */
            // 失去焦点
            frosDatePickerBlur(val) {
                this.$emit('fDatePickerBlur',val)
            },
            // 获取焦点
            frosDatePickerFocus(val) {
                this.$emit('fDatePickerFocus',val)
            },
            frosDatePickerGetDateChange(val, event) {
                this.$emit('fDatePickerGetDateChange',val, event)
            },
            frosDatePickerChange(val) {
                this.$emit('fDatePickerChange',val)
            },

        }
    }
</script>
<style>
    #contextMenu ul {
        background: #fff;
        border: 1px solid #ebeef5;
        border-radius: 4px;
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, .1);
    }

    #contextMenu .vue-contextmenu-listWrapper {
        font-family: Helvetica Neue, Helvetica, PingFang SC, Hiragino Sans GB, Microsoft YaHei, SimSun, sans-serif;
        font-weight: 400;
        padding: 0
    }

    #contextMenu .vue-contextmenu-listWrapper .context-menu-list {
        padding: 5px 10px 10px 10px;
        font-size: 14px;
        color: #606266;
        cursor: pointer;
        outline: none;
        border-bottom: 1px solid #ccc;
        font-weight: 400;
    }

    #contextMenu .vue-contextmenu-listWrapper .context-menu-list:hover {
        color: #409eff;
        background: #ECF5FF
    }

    #contextMenu .vue-contextmenu-listWrapper .context-menu-list:last-child {
        border-bottom: none;
    }
</style>
<style scoped>
    #frosTransfer li {
        display: inline-block;
        padding: 8px 5px;
        margin: 0 3px
    }

    .box-card {
        margin-right: 10px;
        /*min-height: 510px;*/
    }

    .el-form-item {
        /*margin-bottom: 0px;*/
    }

    .el-transfer {
        width: 500px;
    }

    .menu__item {
        display: block;
        line-height: 20px;
        text-align: center;
        margin-top: 10px;
    }

    .menu {
        height: 100px;
        width: 100px;
        position: absolute;
        border-radius: 10px;
        border: 1px solid #999999;
        background-color: #f4f4f4;
    }

    li:hover {
        background-color: #1790ff;
        color: white;
    }
</style>
