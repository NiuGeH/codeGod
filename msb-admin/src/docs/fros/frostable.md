# FrosTable使用方法

FrosTable内部提供普通表格和可编辑表格。

```html
<fros-table :tableData="tableData"
        :dropCol="dropCol"
        height="auto"
        ref="unitMultipleTable"
        :stripe=false
        :rightListShow=true
        :rightList="rightList"
        :indexShow=true
        :selectionShow=true
        indexLabel="序号"
        indexWidth="50"
        :cutShow=true
        :itemShow=true
        :addSubtotal=true
        :subtotalType="subtotalType"
        :fixedColumn=true
        :freshShow=true
        :pageShow=true
        :pgconfig="pgconfig"
        :editable=false
        :columnAuto=false
        :addBtnList="addBtnList"
        :buttonListShow=true
        frosDialogClassname="frosDialog"
        stripe="stripe"
        :RowColorisShow=false
        :tableRowColorType="tableRowColorType"
        // 复选框change事件
        @checkboxChange="checkboxChange"

        @baseInputChange="baseInputChange"
        @baseInputBlur="baseInputBlur"
        @baseInputFocus="baseInputFocus"

        @numInputChange="numInputChange"
        @numInputBlur="numInputBlur"
        @numInputFocus="numInputFocus"

        @dateChange="dateChange"
        @dateBlur="dateBlur"
        @dateFocus="dateFocus"
        @dateTabCell="dateTabCell"
        @dateEnterCell="dateEnterCell"

        @autocompleteChange="autocompleteChange"
        @autocompleteBlur="autocompleteBlur"
        @autocompleteFocus="autocompleteFocus"
        @autocompleteEnter="autocompleteEnter"
        @autocompleteTab="autocompleteTab"

        @selectChange="selectChange"
        @selectBlur="selectBlur"
        @selectFocus="selectFocus"
        @selectTab="selectTab"
        @change="changeData"
        @blur="blurData"
        @focus="focusData"
        @menuId="menuId"
        @reFresh="reFresh"
        @cellClick="cellClick"
        @cellDbclick="cellDbclick"
        @rowClick="rowClick"
        @rowDbclick="rowDbclick"
        @handleSelectionChange="handleSelectionChange"
        @sortChange="sortChange"
        @rowContextmenu="rowContextmenu"
        @cellMouseEnter="cellMouseEnter"
        @cellMouseLeave="cellMouseLeave"
        v-on:getPagesize="getPagesize"
        v-on:getCurrentpage="getCurrentpage"
        @clkCallBk="listenCall"
        @fatherMethod="initTableData"
        @singleCurrentChange="singleCurrentChange"
        @expandChange="expandChange">
        <!---树结构的子表格，配置项和父级一样，具体配置参考FrosTreeTableDemo.vue-->
            <fros-table></fros-table>
</fros-table>
export default {
    data() {
          return {
            // 上传
            uploadShow:true, // 是否显示上传列
            org_id:'123',
            source_code:'12345',
            file_type:'txt',
            business_type:'10',
            indexShow: true,
            selectionShow: true,
            cutShow: true, // 是否显示剪切列
            itemShow: false, // 是否显示分组
            fixedColumn: true, // 固定列是否显示
            //校验规则
            rules:null,
            height: 300,
            //是否显示分页
            pageShow: true,
            //是否显示刷新按钮
            freshShow: true,
            //列宽是否自适应
            columnAuto: false,
            // 是否可编辑啊
            editable: true,
            // 右键菜单
            rightList:[],  // 右键接受菜单
            rightmenuId:null, // 右键菜单项id
            // 表头数据
            dropCol: [
              {
                  width: 150, // 列宽
                  label: '上传id', // 列标题
                  addSymbol:'*', // 添加*标识  不添加标识 可以不写该属性
                  prop: 'uploadId', // 根据该属性读取表格展示的数据
                  align: 'left', // 列数据对齐方式 center/left/right
                  headerAlign:'center', // 列标题对齐方式 center/left/right
                  fixed: false, // 该列是否固定
                  filterShow: true, // 该列是否有表头筛选
                  sortable: true, // 该列是否进行默认排序
                  editType: {   // 该列可编辑类型
                      label: "none"  // 不可编辑
                  }
              },
              {
                  width: 150,
                  label: '是否',
                  prop: 'yRn',
                  align: 'center',
                  headerAlign:'center',
                  fixed: false,
                  filterShow: true,
                  sortable: true,
                  editType: {
                      label: "checkbox",    // 输入框类型
                      trueLabel: 'y',   // 当勾选复选框时返回值
                      disabled:true, // 是否可用
                      falseLabel:'n',    // 取消勾选复选框时返回值
                  }
              },
              {
                  width: 150,
                  label: '日期',
                  prop: 'date',
                  align: 'right',
                  headerAlign:'center',
                  fixed: false,
                  filterShow: true,
                  sortable: true,
                  editType: {
                      label: "date",
                      disabled:true, // 是否可用
                      placeholder:'', // 占位提示文字
                  }
              },
              {
                  width: 100,
                  label: '姓名',
                  prop: 'name',
                  align: 'left',
                  headerAlign:'center',
                  fixed: false,
                  filterShow: true,
                  sortable: false,
                  editType: {
                      label: "frosBaseInput",
                      type:'textarea', // 可配置文本域和文本框
                      frosInputTooltip:false, // 是否要提示框
                      placeholder:'', // 占位提示文字
                      disabled: false, // 是否禁用
                      readonly: false, // 是否只读
                      length: 5,    // 输入最大长度
                      frosCode: true, // 是否全角转半角
                      onlyNum:false, // 只输入数字
                      letter: false, // 只能输入字母
                      letterAndNum: true, // 只能输入字母和数字
                  }
              },
              {
                  width: 150,
                  label: '数字文本框',
                  prop: 'numinput',
                  align: 'right',
                  headerAlign:'center',
                  fixed: false,
                  filterShow: true,
                  sortable: false,
                  editType: {
                      label: "frosNumInput",
                      frosInputTooltip:false, // 是否要提示框
                       placeholder:'', // 占位提示文字
                      length: 15,    // 输入最大长度
                      disabled: false, // 是否禁用
                      readonly: false, // 是否只读
                      frosCode: true, // 是否全角转半角
                      onlyNum: true,  // 只能是数字（可输入小数）
                      interger:true, // 是否开启正整数
                      frosNegative: false, // 不能输入负数
                      frosRounding: false, // 四舍五入 | true:四舍五入（默认保留两位小数） | false：不允许
                      frosPrecision: 2,    // 小数位数设置 | 默认是两位（不足自动补齐0）
                      frosOperation: true // 是否进行运算
                  }
              },
              {
                  width: 300,
                  label: '地址',
                  prop: 'address',
                  align: 'left',
                  headerAlign:'center',
                  fixed: false,
                  editType: {
                      label: "SmartInput",
                      config: 'address',
                      disabled:false,
                       placeholder:'', // 占位提示文字
                  }
              },
              {
                  width: 200,
                  label: '标签',
                  prop: 'province',
                  align: 'left',
                  headerAlign:'center',
                  fixed: false,
                  sortable: false,
                  editType: {
                      label: "select",
                      multiple: false,
                      disabled:true, // 是否可用
                      filterable:'filterable',// 是否支持可搜索
                      frosCode: true, // 全角半角
                      frosCase: true, // 小写转大写
                      frosEnterShow:true, // 回车默认选中第一个值
                      frosBlurShow:true, // 失去焦点回填值
                      tooltipShow:true, // 关掉提示框
                      placeholder:'', // 占位提示文字
                      isDict: true, // 是否需要字典翻译
                      isLocalStorage: true, // 是否走本地缓存数据字典翻译
                      dictType: 'province', // 用于获取主数据对应类型字典数据
                      // 数据字典数据
                      dictData: [
                          {
                              'value': 'apple',
                              'key': '苹果'
                          }, {
                              'value': 'banana',
                              'key': '香蕉'
                          }, {
                              'value': 'sz',
                              'key': '山竹'
                          }
                      ],
                      selectOption: [
                          {
                              value: '',
                              key: '空'
                          },
                          {
                              value: 'apple',
                              key: 'a'
                          }, {
                              value: 'banana',
                              key: 'b'
                          }, {
                              value: 'sz',
                              key: 's'
                          }]
                  },

              },
              {
                  width: '200',
                  label: '城市',
                  prop: 'city',
                  align: 'left',
                  headerAlign:'center',
                  fixed: false,
                  sortable: false,
                  editType: {
                      label: "SmartInput",
                      config: "city",
                      //配置和那一列联动
                      linkage:"address",
                      disabled:true,
                       placeholder:'', // 占位提示文字
                  }
              },
              {
                  width: 150,
                  label: '状态',
                  align: 'left',
                  headerAlign:'center',
                  prop: 'IsAudit',
                  fixed: false,
                  sortable: false,
                  editType: {
                      label: "input",
                      type: "text"
                  },
                  isLocalStorage:false, // 数据字典取值 isLocalStorage | true走本地缓存 | false走数据字典
                  // 判断状态
                  formatterType: [
                      {
                          'value': '1',
                          'key': '完成'
                      },
                      {
                          'value': '2',
                          'key': '未完成'
                      },
                      {
                          'value': '3',
                          'key': '不知道'
                      }
                  ],
              },
              {
                  width: '100',
                  label: '邮编',
                  prop: 'zip',
                  align: 'right',
                  headerAlign:'center',
                  fixed: false,
                  sortable: false,
                  editType: {
                      label: "input",
                      type: "number"
                  }
              }
          ],
        // 表格基础数据
        tableData: [],
        buttonListShow: true,  // 是否显示隐藏列
        // 自定义列数据
        addBtnList: {
              label: '操作',
              width: 200,
              list: [{
                  title: '查看',  // 按钮标题
                  icon: 'el-icon-circle-plus-outline',  // 按钮图标
                  methods: 'addItem', // 按钮方法名称
                  showBtn: true  // 是否显示按钮  也可以手动添加判断条件  见注意事项

              }]
         },
        // 分页
        pgconfig:{
              layout:'prev,pager,next,sizes,total',
              smallSize:true,
              background:true,
              disabled:false,
              hideSingle:false, // 当只有一页时是否隐藏分页
              popperClass:'10',
              prevText:'上一页',
              nextText:'下一页',
              total: 100,
              pageSize:5,
              pagerCount:10,
              currentPage: 1,
              pageSizes: [5,10],
              //page
          },
        // 联想框配置项
        serviceList: {
          city: {},  // 见联想控件文档  Fros-From->联想控件
        },
        service: '',
        frosDialogClassname:'frosDialog', // 剪切列弹框className
        currentRow: null, // 单选 选中某一行数据
        RowColorisShow:true, // 表格是否显示行样式 || 如果是true 不能配置stripe="stripe" 属性
        // 表格行根据状态变色
        tableRowColorType:{
              type:'IsAudit',
              list:[
                  {
                      value:2,
                      className:'success-row'
                  },
                  {
                      value:1,
                      className:'warning-row'
                  },
              ]
        },
    },
    components: {
       FrosTable
    },
    mounted() {
          this.initTableData()
          this.initRightmenuData()
          this.rules = validator.getRulerData(this, '/editTableRules', {
            name: '1'
          }, 'rules',this.form)

    },
    watch:{
        // 动态改变数据时，需要加监听事件
        tableData(val, oldVal) {
            if (val !== oldVal) {
                 this.tableData = val
            }
        }
    },
    methods: {
            // 树表格 点击收起、展开子表格事件
            expandChange(row,expandedRows,expanded){
                let self = this
                // 设置只展开当前点击的行
                if (expandedRows.length) {
                    self.expands = []
                    if (row) {
                        self.expands.push(row.id)
                    }
                } else {
                    self.expands = []
                }
                // 树表格 行是否展开
                self.toggleChange(row,true)

                // 树表格 请求后台加载子表格table数据
                let self = this
                utils.sendReq({
                    queryUrl: '/getFrosTable',
                    req: {
                        method: 'post',
                        data: {
                            'currentPage':this.childrenPgconfig.currentPage, // 当前页数
                            'pageSize':this.childrenPgconfig.pageSize // 一页显示多少条
                        },
                        success: function (res) {
                            console.log(res.data.data.tableData)
                            self.childrenTableData = res.data.data.childTableData
                            self.childrenPgconfig.total = res.data.data.total
                        }
                    }
                })
                console.log(this.childrenTableData,this.pgconfig.pageSize)
            },
            // 改变行颜色
            tableRowClassName(row,rowIndex){
              //  alert(1)
                if (row.IsAudit === 1) {
                    let warnClass = 'warning-row'
                    this.tableRowColorType=warnClass
                    this.$refs.unitMultipleTable.tableRowClassName(row,rowIndex)
                  //  return warnClass;
                } else if (rowIndex === 3) {
                    return 'success-row';
                }
                return '';
            },
           // 多选 清空选中状态
           clearSelection(){
               this.$nextTick(()=>{
                   this.$refs.unitMultipleTable.clearSelection()
               })
           },
           // 多选 选中多行状态
           selectionData(){
               this.$nextTick(()=>{
                   this.$refs.unitMultipleTable.selectionData([this.tableData[1],this.tableData[2]])
               })
           },
            // 单选
           singleCurrentChange(val){},
           // 单选 选中某一行
           setCurrent(){
               this.$nextTick(()=>{
                   this.$refs.unitMultipleTable.setCurrent(this.tableData[0])
               })
           },
           // 单选清空
           setClearCurrent(){
               this.$nextTick(()=>{
                   this.$refs.unitMultipleTable.setCurrent()
               })
           },
          // 表格获取后台数据
          initTableData(){
              // 初始化table数据
              let self = this
              utils.sendReq({
                  queryUrl: '/getFrosTable',
                  req: {
                      method: 'post',
                      data: {},
                      success: function (res) {
                          self.dropCol = res.data.data.dropCol
                          self.tableData = res.data.data.tableData

                      }
                  }
              })
          },
           // 获取右键数据
          initRightmenuData(){
              // 初始化右键数据
              let self = this
              utils.sendReq({
                  queryUrl: '/getFrosTable',
                  req: {
                      method: 'post',
                      data: {},
                      success: function (res) {
                          self.rightList = res.data.data.rightList
                      }
                  }
              })
          },
          // 动态给表格添加数据
          addData(){
              let list ={
                    id: 99,
                    IsAudit: 3,
                    date: '2088-01-09',
                    name: '鸭血粉丝汤',
                    province:100,
                    city: '夫子庙',
                    address: '江苏南京',
                    zip: 500
                }
              this.tableData.push(list)
            },
          // 表格全选
          handleSelectionChange(val) {
            console.log(this.multipleSelection = val)
            // alert('我是多选方法')
          },
          // 表格刷新
          reFresh() {
            console.log('我是父页面，刷新方法')
          },
          cellClick(row, column, cell, event) {
            console.log('我是父页面，cell单击方法' + row + column + cell.className + event)
          },
          cellDbclick(row, column, cell, event) {
            console.log('我是父页面，cell双击方法' + row + column + cell.className + event)
          },
          rowClick(row, column, event) {
            console.log('我是父页面，row单击方法' + row + column + event)
          },
          rowDbclick(row, column, event) {
            console.log('我是父页面，row双击方法' + row + column + event)
          },
          sortChange:function(column){
              console.log('我是父页面，sortChange方法，可以根据列: ' +column.prop+'来调整请求后台By   '+column.order)
          },
          rowContextmenu:function(row, event){
              console.log('rowContextmenu',row)
          },
          cellMouseEnter:function(row, column, cell, event){
              // console.log('cell鼠标划入',cell.innerText)
          },
          cellMouseLeave:function(row, column, cell, event){
              // console.log('cell鼠标划出',row)
          },
          // 操作列按钮方法
          listenCall(methodsWords,scope,el) {
              console.log('methodsWords', methodsWords,scope,el)
              this[methodsWords](scope,el)
          },
          addItem(scope,el) {
              console.log(scope,el)
              alert('查看')
          },
          addItem2(scope,el) {
              console.log(scope,el)
              alert('编辑')
          },
          addItem3(scope,el) {
              console.log(scope,el)
              // 根据判断条件 显示/隐藏 某行数据的某个操作按钮
              if(scope.row.IsAudit ==0){
                // 显示
                el.className = 'el-button el-button--text el-button--small'
              }else{
                 // 隐藏
                 el.className = 'frosbtn_hide'
              }
              alert('删除')
          },
          // 获取分页条数
          getPagesize(val) {
            this.pgconfig.pageSize = val
          },
          // 当前页数
          getCurrentpage(val) {
            this.pgconfig.currentPage = val
          }
        }
}
```

## 注意事项
1、如果动态改变数据时，需要加监听事件

```html
watch:{
    tableData(val, oldVal) {
        if (val !== oldVal) {
             this.tableData = val
        }
    }
},
```
2、在改变行样式时候，除了加配置项外（不能和stripe一起用），还要自己写好样式，和判断方法
```html
方法
tableRowClassName(row,rowIndex){
    if (row.IsAudit === 1) {
        let warnClass = 'warning-row'
        this.tableRowColorType=warnClass
        this.$refs.unitMultipleTable.tableRowClassName(row,rowIndex)
      //  return warnClass;
    } else if (rowIndex === 3) {
        return 'success-row';
    }
    return '';
},
```
3、如果操作列按钮需要根据条件判断显示、隐藏（需要配置三目运算符）
```html
{
    title: '编辑',
    icon: 'el-icon-edit',
    methods: 'addItem2',
    showBtn: 'scope.row.IsAudit==1?true:false'  // 根据添加判断显示隐藏
},
scope：表格某一行所有信息
scope.row :表格中某一行所有属性
// 样式
<style>
    /*表格状态样式*/
    .el-table .warning-row {
        background: oldlace;
    }
    .el-table .success-row {
        background: #f0f9eb;
    }
    .el-table .danger-row {
        background: #fef0f0;
    }
</style>
```
3、在使用行选中方法时候，要注意添加 ref='' 属性
```html
|selectionData|多选选中方法||
|clearSelection|清除多选方法||
|setCurrent|单选方法||
|setClearCurrent|清除单选方法||
```
4、在使用展开子表格，需要设置唯一值，默认用id
```html
在data里设置
 getRowKeys (row) {
    return row.id
 },
 row:行数据
 row.id:行id值

 在methods里需要设置唯一值
 expandChange(row,expandedRows){
     let self = this
     // 设置值展开当前点击的行
     if (expandedRows.length) {
         self.expands = []
         if (row) {
             self.expands.push(row.id)
         }
     } else {
         self.expands = []
     }
     // 判断展开关闭 true展开行数据 、false关闭行数据
     self.toggleChange(row,true)
 }
```

5、在配置操作列按钮时，针对某行数据，某个按钮进行显示、隐藏操作
```html
addBtnList: {
      label: '操作',
      width: 200,
      list: [{
          title: '查看',  // 按钮标题
          icon: 'el-icon-circle-plus-outline',  // 按钮图标
          methods: 'addItem', // 按钮方法名称
          showBtn: true  // 是否显示按钮 （默认配置）
      }]
},
如果是手动配置，有判断条件
showBtn: 'scope.row.IsAudit==1?true:false'
scope：当前某行数据的所有事件和数据
scope.row：当前某行数据的行数据
scope.row.IsAudit：当前某行数据的某个类型
```
6、当表格内某列数据需要进行翻译时候，需要在表头内配置formatterType属性，formatterType配置固定值，也可以取数据字典的值
```html
dropCol: [{
            width: 150,
            label: '状态',
            align: 'left',
            headerAlign:'center',
            prop: 'IsAudit',
            fixed: false,
            sortable: false,
            editType: {
                label: "input",
                type: "text"
            },
            // 判断状态
            // 配置固定值
            formatterType: [
                {
                    'value': '1',
                    'key': '完成'
                },
                {
                    'value': '2',
                    'key': '未完成'
                },
                {
                    'value': '3',
                    'key': '不知道'
                }
            ],
            // 这个形式加载数据字典内status  具体用法参考文档 Fros-From -> 数据字典
            formatterType: 'status'
        },
```


## 属性

|参数|说明|类型|可选值|默认值|
|-|-|-|-|-|
|height|表格高度| 可以配置具体高度值 '200' || 配置'auto'自适应高度  默认和外层div高度一致 |-|-|-|
|columnAuto|列宽是否自适应，false（不开启）、true(开启)|Boolean|-|-|
|editable|配置表格是否可编辑,false（不可编辑）、true(可编辑)|Boolean|-|-|
|cutShow|是否显示剪切列,false（不显示）、true(显示)|Boolean|-|-|
|itemShow|是否显示分组，false（不显示）、true(显示)|Boolean|-|-|
|addSubtotal|分组是否添加汇总功能，false（不显示）、true(显示)|Boolean|-|-|
|subtotalType|分组配置汇总属性，min（最小值）、max(最大值)、sum(总和)、count(总条数)|Boolean|-|-|
|fixedColumn|是否显示固定列，false（不显示）、true(显示)|Boolean|-|-|
|freshShow|是否显示刷新，false（不显示）、true(显示)|Boolean|-|-|
|pageShow|是否显示分页，false（不显示）、true(显示)|Boolean|-|-|
|indexShow|是否显示列排序，false（不显示）、true(显示)|Boolean|-|-|
|selectionShow|是否显示全选列，false（不显示）、true(显示)|Boolean|-|-|
|headerAlign|列头对齐方式，left(左对齐)、center（居中）、right(右对齐)|String|-|center|
|buttonListShow|是否显示自定义按钮列，false（不显示）、true(显示)|Boolean|-|-|
|dropCol|表头数据|Array|-|-|
|tableData|表格数据|Array|-|-|
|addBtnList|自定义按钮列数据|Array|-|-|
|pgconfig|分页数据|Array|-|-|
|multipleSelection|用于记录多选数据|Array|-|-|
|rightList|右键接收菜单|Array|-|-|
|rightmenuId|右键菜单项id|null|-|-|
|frosDialogClassname|剪切列弹框className|String|-|-|
|currentRow|单选 选中某一行数据|null|-|-|
|RowColorisShow|剪切列弹框className|String|-|-|
|stripe|表格行显示斑马条纹 不能和frosDialogClassname属性一起用 |Boolean|-|-|
|frosDialogClassname|表格是否显示行样式 || 如果是true 不能和stripe="stripe" 属性一起用|Boolean|-|-|
|tableRowColorType|表格行根据状态变色|Array|-|-|
|expandShow|是否显示展开列，false（不显示）、true(显示)|Boolean|-|-|
|expands|展开列集合|Array|-|-|
|getRowKeys|当前展开列key值|String|-|-|
|isLocalStorage|数据字典取值、true走本地缓存 || false走数据字典|Boolean|-|-|



## 事件

|事件名称|说明|回调参数|
|-|-|-|
|reFresh|表格数据刷新|-|
|cellClick|单元格单击|row(当前行数据), column(单元格数据), cell, event|
|cellDbclick|单元格双击|row(当前行数据), column(单元格数据), cell, event|
|rowClick|单击行|row(当前行数据), column(单元格数据), cell, event|
|rowDbclick|双击行|row(当前行数据), column(单元格数据), cell, event|
|sortChange|表头排序|column(单元格数据)|
|rowContextmenu|右键表格菜单|row(当前行数据), event|
|cellMouseEnter|鼠标滑过单元格|row(当前行数据), column(单元格数据), cell, event|
|cellMouseLeave|鼠标滑离单元格|row(当前行数据), column(单元格数据), cell, event|
|listenCall|自定义按钮|methodsWords(调用的方法名称)|
|getPagesize|获取分页条数|val|
|getCurrentpage|当前页数|val|
|fatherMethod|刷新方法||
|singleCurrentChange|单选方法||
|selectionData|多选选中方法||
|clearSelection|清除多选方法||
|setCurrent|单选方法||
|setClearCurrent|清除单选方法||
|checkboxChange|复选框change方法||
|baseInputChange|基本文本框change方法||
|baseInputBlur|基本文本框失去焦点方法||
|baseInputFocus|基本文本框获取焦点方法||
|numInputChange|数字文本框change方法||
|numInputBlur|数字文本框失去焦点方法||
|numInputFocus|数字文本框获取焦点方法||
|dateChange|日期change方法||
|dateBlur|日期失去焦点方法||
|dateFocus|日期获取焦点方法||
|dateTabCell|日期tab方法||
|dateEnterCell|日期回车方法||
|autocompleteChange|联想控件change方法||
|autocompleteBlur|联想控件失去焦点方法||
|autocompleteFocus|联想控件获取焦点方法||
|autocompleteEnter|联想控件回车方法||
|autocompleteTab|联想控件tab方法||

|selectChange|下拉框change方法||
|selectBlur|下拉失去焦点方法||
|selectFocus|下拉获取焦点方法||
|selectTab|下拉tab方法||
