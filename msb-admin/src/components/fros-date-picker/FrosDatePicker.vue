<template>
    <el-date-picker
            id="frosDatePickerFocus"
            v-model="value"
            :type="type"
            :size="size"
            :frosDefValue="frosDefValue"
            :editable="editable"
            :placeholder="placeholder"
            :format="format"
            :value-format="valueFormat"
            :default-value="initValue"
            ref="datePoint"
            :readonly='readonly'
            :disabled="disabled"
            popper-class="frosDatePickerPopper"
            @input.native="inputChange"
            @keydown.space.native="keydownSpaceEvent"
            @keyup.space.native="keyupSpaceEvent"
            @keyup.enter.native="enterEvent"
            @focus="getFocus"
            @blur="getBlur"
            @change="getDateChange">
    </el-date-picker>
</template>
<script>
    console.log('---')
    export default {
        components: {
            // 自定义组件
        },
        model: {
            prop: "value",
            event: 'sync'
        },
        props: {
            type: String,
            size: String, // 输入框尺寸大小 medium / small / mini
            frosDefValue: null,   //  默认日期值
            editable: String, // 是否可编辑，默认为可编辑
            placeholder: String,  // 默认非选择时的占位内容
            format: String,   // 显示在输入框中的格式
            valueFormat: String,  // 返回数据格式
            defaultValue: String,
            disabled: Boolean,  // 是否禁用
            readonly: Boolean   // 是否只读
        },
        data() {
            return {
                value: '',  // 已选择日期数据
                // frosDatePickerPopper: '',
                scrollTarget: null,
                scrollCallback: null,
                monitorValue: '',
                initValue: ''
            }
        },
        computed: {},
        watch: {
            value(val, oldVal) {
                if (val !== oldVal) {
                    this.value = val
                    this.$emit('sync', this.value)
                }
                if (val == null) {
                    this.initValue = ''
                    this.monitorValue = ''
                }
            },
            initValue(val, oldVal) {
                if (val !== oldVal) {
                    this.initValue = val
                }
            },
            // 动态监听默认值
            frosDefValue(val, oldVal) {
                if (val !== oldVal) {
                    this.value = val
                }
            }
        },
        mounted() {
            // 是否设置默认值
            if (this.frosDefValue != null) {
                this.value = this.frosDefValue
            }

            /*  if(this.defaultValue != null){
                this.initValue = this.defaultValue
              } */

            function setPosition(popper, reference) {
                if (popper.attributes['x-placement'].value === 'bottom-start') {
                    popper.style.top = `${reference.getBoundingClientRect().top + reference.getBoundingClientRect().height}px`
                } else {
                    popper.style.top = `${reference.getBoundingClientRect().top - popper.getBoundingClientRect().height - 12}px`
                }
            }

            // 监听时间组件的下拉框显隐（因为首次进入未曾渲染，所以得在其渲染出来才可以操作）
            this.$watch(
                () => {
                    return this.$refs.datePoint.pickerVisible
                },
                (val) => {
                    if(this.readonly) {
                        // 如果是只读状态则不执行以下操作
                        return false
                    }
                    // 下拉框的dom
                    const popper = this.$refs.datePoint.picker.$el
                    // 点击区域的dom，用于参考设置位置
                    const reference = this.$refs.datePoint.$el

                    function scrollCallback() {
                        setPosition(popper, reference)
                    }

                    !this.scrollCallback && (this.scrollCallback = scrollCallback.bind(this))
                    if (val) {
                        // 滚动目标区域
                        this.scrollTarget = this.$refs.datePoint.popperJS.state.scrollTarget
                        // 调用其自身方法去除事件监听（该方法在IIFE之内，在外部无法通过removeEventListener去除监听）
                        this.$refs.datePoint.popperJS._removeEventListeners()
                        //  下拉框渲染完毕重设位置，加setTimeout是为简单处理dom渲染，不然一些诸如getBoundingClientRect方法取得值不是渲染之后的
                        this.$refs.datePoint.picker.$nextTick(function () {
                            setTimeout(function () {
                                setPosition(popper, reference)
                            }, 200)
                        })
                        //  通过之前保存的滚动目标区域添加scroll的监听，使得滚动时可以重设位置
                        this.scrollTarget.addEventListener('scroll', this.scrollCallback);
                    } else {
                        //  当然，下拉框去除也得去除监听
                        this.scrollTarget.removeEventListener('scroll', this.scrollCallback)
                    }
                }
            )
        },
        methods: {
            //日期自动补全
            inputChange() {
                var picker = this.$refs.datePoint;
                var temp = picker.userInput;
                if(temp == undefined || temp.indexOf(' ') > -1){
                    return false
                }
                if(!(this.IsMoney(temp))){
                    picker.handleChange()
                    picker.userInput = ''
                }else{
                    var v = temp.split("-");
                    //
                    if (Number(v[1] || 0) == 0) v[1] = "01";
                    if (Number(v[2] || 0) == 0) v[2] = "01";
                    picker.userInput = v.join("-");
                    picker.handleChange();
                    picker.userInput = temp;
                }
            },
            IsMoney(Money){
                if(/^[\d|/|-]+$/.test(Money)) {
                    let counetFirst = Money.charAt(0)
                    let symbols = ['-','/']
                    if(isNaN(counetFirst)){  //不是数字，是运算
                        this.value = ""
                    }
                    for(let i=0;i<symbols.length;i++){
                        if(Money.indexOf(symbols[i]) > -1){
                            let newMoney = Money.split('')
                            // console.log(newMoney)
                            let a = newMoney.indexOf(symbols[i])
                            if(symbols.indexOf(newMoney[a+1]) > -1){
                                newMoney.splice(a,newMoney.length-a-1)
                                this.value= newMoney.join('')
                                this.content = this.value.toString()
                            }
                        }
                    }
                    return true
                }
                else{
                    return false;
                }
            },
            // 清空日期数据
            clearDatePicker: function () {
                this.value = null
            },
            // 清空日期控件数据
            clearFormValue: function () {
                this.value = null
            },
            // 监听date控件值的变化
            getDateChange: function () {
                this.$emit('getDateChange', this.value, event)
                this.$emit('change', this.value)
            },
            // 获取焦点触发事件
            getFocus: function (event) {
                /*var picker = document.querySelectorAll('.frosDatePickerPopper')
                for (let i = 0; i < picker.length; i++) {
                  picker[i].style.display = "block"
                }*/
                this.$emit('getFocus', this.value, event)
                this.$emit('focus', this.value)
            },
            getBlur() {
                this.$emit('getBlur', this.value, event)
                this.$emit('blur', this.value)
            },
            // 鼠标空格键按下回填当前时间
            keydownSpaceEvent: function () {
                let myDate = new Date()
                if (this.type == "date") {
                    this.value = new Date(myDate.getFullYear(), myDate.getMonth(), myDate.getDate())
                } else if (this.type == "datetime") {
                    this.value = new Date(myDate.getFullYear(), myDate.getMonth(), myDate.getDate(), myDate.getHours(), myDate.getMinutes(), myDate.getSeconds())
                }
                return false
            },
            // 空格键升起时触发关闭日期框选择器
            keyupSpaceEvent: function () {
                this.$el.firstElementChild.blur() // 使控件失去焦点
                var picker = document.querySelectorAll('.frosDatePickerPopper')
                //document.querySelector('.el-date-picker').style.display="none";
                for (let i = 0; i < picker.length; i++) {
                    picker[i].style.display = "none"
                }
            },
            // 监听回车事件
            enterEvent: function () {
                let input = document.getElementById("frosDatePickerFocus")
                input.blur()    // 失去焦点
                this.value = this.value

            },
            // 格式化日期
            datePattern: function () {
                // 设置data的prototype
                Date.prototype.pattern = function (fmt) {
                    var o = {
                        "M+": this.getMonth() + 1, //月份
                        "d+": this.getDate(), //日
                        "h+": this.getHours() % 12 == 0 ? 12 : this.getHours() % 12, //小时
                        "H+": this.getHours(), //小时
                        "m+": this.getMinutes(), //分
                        "s+": this.getSeconds(), //秒
                        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
                        "S": this.getMilliseconds() //毫秒
                    };
                    var week = {
                        "0": "/u65e5",
                        "1": "/u4e00",
                        "2": "/u4e8c",
                        "3": "/u4e09",
                        "4": "/u56db",
                        "5": "/u4e94",
                        "6": "/u516d"
                    };
                    if (/(y+)/.test(fmt)) {
                        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
                    }
                    if (/(E+)/.test(fmt)) {
                        fmt = fmt.replace(RegExp.$1, ((RegExp.$1.length > 1) ? (RegExp.$1.length > 2 ? "/u661f/u671f" : "/u5468") : "") + week[this.getDay() + ""]);
                    }
                    for (var k in o) {
                        if (new RegExp("(" + k + ")").test(fmt)) {
                            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
                        }
                    }
                    return fmt;
                }
            }
        }
    }
</script>
<style scoped>
    .frosDatePickerPopper {
        /*display: none;*/
    }
    .el-date-editor{
        width: 100%;
    }
</style>
