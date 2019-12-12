<template>
    <span>
        <div v-if="frosInputTooltip">
            <el-tooltip :disabled="tooltipShow" placement="right-end" effect="light">
                <div slot="content" style="white-space: pre-wrap;">{{content}}</div>
                <el-input style="width:100%"
                          v-model="value"
                          :type="type"
                          :rows="rows"
                          :placeholder="placeholder"
                          :disabled="disabled"
                          :readonly="readonly"
                          :maxlength="newMaxlength"
                          :show-word-limit="showWordLimit"
                          @keyup.native="getLength"
                          @change="emitChange"
                          @blur="emitBlur"
                          @focus="emitFocus">
                    <!--onKeypress="return (/[\d,]/.test(String.fromCharCode(event.keyCode)))"-->
                </el-input>
            </el-tooltip>
        </div>
        <div v-else>
            <el-input style="width:100%"
                      v-model="value"
                      :type="type"
                      :rows="rows"
                      :placeholder="placeholder"
                      :disabled="disabled"
                      :readonly="readonly"
                      :maxlength="newMaxlength"
                      :show-word-limit="showWordLimit"
                      @keyup.native="getLength"
                      @change="emitChange"
                      @blur="emitBlur"
                      @focus="emitFocus">
                <!--onKeypress="return (/[\d,]/.test(String.fromCharCode(event.keyCode)))"-->
            </el-input>
        </div>
    </span>
</template>
<script>
    export default {
        props: {
            frosInputTooltip:Boolean, // 是否显示提示框
			rows:null,
            frosDefValue: null,
            type: String,
            placeholder: String,
            disabled: Boolean,
            readonly: Boolean,
            length: null,
            onlyNum: Boolean, // 只能输入数字
            letter: Boolean, // 只能输入字母
            letterAndNum: Boolean, // 只能输入字母和数字
            frosCode: Boolean, // 全角半角
            frosCase: Boolean, // 大小写
            frostrim: null,
            showWordLimit: Boolean  // 是否显示输入字数统计，只在 type = "text" 或 type = "textarea" 时有效
        },
        model: {
            prop: 'value',
            event: 'getValue'
        },
        watch: {
            value(val) {
                this.$emit('getValue', val)
                // 判断全角半角
                if (this.frosCode === true) {
                    this.valChangeCode(val)
                }
                // 判断大小写
                if (this.frosCase === true) {
                    this.valUpper()
                }
            },
            frosDefValue(val) {
                this.value = val
                this.content = val
            }
        },
        data() {
            return {
                value: '',
                content: '',
                tooltipShow:false,
                newMaxlength:null,
            }
        },
        mounted() {
            this.value = this.frosDefValue
            this.content = this.frosDefValue
            let val = this.value
            this.newMaxlength = this.length
            if(val==''|| val==' ' || val=='null'){
                this.value=''
                this.tooltipShow=true
            }else{
                this.tooltipShow=false
            }
        },
        methods: {
            // 清空
            clearDataInput() {
                this.value = ""
                this.content =""
                this.tooltipShow=true
            },
            // 清空普通文本框数据
			clearFormValue () {
                this.value = ""
                this.content =""
                this.tooltipShow=true
			},
            getLength() {
                // 大于最大长度
                if (this.value.length >= this.maxlength) {
                    this.content = "长度必须小于等与" + this.maxlength
                } else {
                    this.content = this.value
                }
                // 只能输入数字
                if(this.onlyNum){
                    let val = this.value
                    this.value=val.replace(/[^\d]/g,'')
                    this.content = this.value
                }
                // 只能输入字母
                if(this.letter){
                    let val = this.value
                    this.value=val.replace(/[^a-zA-Z]/g,'')
                    this.content = this.value
                }
                // 只能输入数字和英文
                if(this.letterAndNum){
                    let val = this.value
                    this.value=val.replace(/[\W]/g,'')
                    this.content = this.value
                }

                let val=this.value
                if(val=='' || val==' '){
                    this.tooltipShow=true
                }else{
                    this.tooltipShow=false
                }
            },
            changeVal() {
                let val = this.value
                // 判断全角半角
                if (this.frosCode === true) {
                    this.valChangeCode(val)
                }
                // 判断大小写
                if (this.frosCase === true) {
                    this.valUpper()
                }
            },
            emitChange(val) {
                this.changeVal()
                this.$emit('change', val)
            },
            emitFocus() {
                this.$emit('focus')
            },
            emitBlur() {
                let tmp = this.value
                // 空格
                // let newVal = this.value
                if (this.frostrim === 'ltrim') {
                    this.value = tmp.replace(/(^\s*)/g, "")
                    this.content = this.value
                } else if (this.frostrim === 'rtrim') {
                    this.value = tmp.replace(/(\s*$)/g, "")
                    this.content = this.value
                } else if (this.frostrim === 'lrtrim') {
                    this.value = tmp.replace(/(^\s*)|(\s*$)/g, "")
                    this.content = this.value
                } else if (this.frostrim === 'trim') {
                    this.value = tmp.replace(/\s/g, "")
                    this.content = this.value
                    //this.value = tmp.replace(/(^　*)|(　*$)/g, "")
                }
                this.$emit('blur')
            },
            // 大小写转换
            valUpper() {
                this.value = this.value.toUpperCase()
                this.content = this.value
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
                this.content = this.value
            },
        }
    }
</script>
<style scoped>
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
</style>
