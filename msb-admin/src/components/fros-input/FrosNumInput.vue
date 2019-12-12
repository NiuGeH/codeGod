<template>
  <div>
    <div v-if="frosInputTooltip">
      <el-tooltip  :disabled="tooltipShow" placement="right-end" effect="light">
        <div slot="content" style="white-space: pre-wrap;">{{content}}</div>
        <el-input style="width:100%"
                  v-model="value"
                  type="text"
                  :placeholder="placeholder"
                  :disabled="disabled"
                  :readonly="readonly"
                  :maxlength="newMaxlength"
                  :frosCase="frosCase"
                  :frosCode="frosCode"
                  :frostrim="frostrim"
                  :frosFloat="frosFloat"
                  :frosNegative="frosNegative"
                  :frosRounding="frosRounding"
                  :frosPrecision="frosPrecision"
                  :frosOperation="frosOperation"
                  @keyup.native="numMethods"
                  @change="emitChange"
                  @blur="blurVal"
                  @focus="emitFocus">
          <!--onKeypress="return (/[\d,]/.test(String.fromCharCode(event.keyCode)))"-->
        </el-input>
      </el-tooltip>
    </div>
    <div v-else>
      <el-input style="width:100%"
                v-model="value"
                type="text"
                :placeholder="placeholder"
                :disabled="disabled"
                :readonly="readonly"
                :maxlength="newMaxlength"
                :frosCase="frosCase"
                :frosCode="frosCode"
                :frostrim="frostrim"
                :frosFloat="frosFloat"
                :frosNegative="frosNegative"
                :frosRounding="frosRounding"
                :frosPrecision="frosPrecision"
                :frosOperation="frosOperation"
                @keyup.native="numMethods"
                @change="emitChange"
                @blur="blurVal"
                @focus="emitFocus"
      >
        <!--onKeypress="return (/[\d,]/.test(String.fromCharCode(event.keyCode)))"-->
      </el-input>
    </div>
  </div>
</template>
<script>
    export default {
        props: {
            frosInputTooltip: Boolean, // 是否显示提示框
            frosDefValue: null,
            placeholder: String,
            disabled: Boolean,
            readonly: Boolean,
            // maxlength: null,
            length: null,
            frosCode: Boolean, // 全角半角
            frosCase: Boolean, // 大小写
            frostrim: null, // 空格

            onlyNum: Boolean, // 不可输入小数和负数
            frosNegative: Boolean, // 不能是负数
            frosInterger: Boolean, // 是否为正整数
            frosComma:Boolean, // 是否开启逗号
            frosRounding: Boolean, // 四舍五入
            frosPrecision:Number,  // 小数位数
            frosFloat: Boolean, // 是否分隔
            frosOperation: Boolean, // 运算符


        },
        model:{
            prop: 'value',
            event: 'getValue'
        },
        watch:{
            value(val){
                this.$emit('getValue',val)
            },
            frosDefValue(val) {
                //this.value = parseFloat(val)
                this.value = val
            }
        },
        data () {
            return {
                value:null,
                content:'',
                newMaxlength:null,
                tooltipShow:false
            }
        },
        mounted () {
            this.value = this.frosDefValue
            if(isNaN(parseFloat(this.value))){
                this.value=''
                this.content = ''
            }else{
                this.value = parseFloat(this.value)
            }
            this.content = this.value.toString()

            this.newMaxlength = this.length
            let val = this.value
            if(val==''|| val==' ' || val=='null'){
                this.tooltipShow=true
            }else{
                this.tooltipShow=false
            }
        },
        methods: {
            // 清空数字文本框数据
            clearFormValue(){
                this.value = ''
                this.content = ''
                this.tooltipShow=true
            },
            changeVal(val){
                // 空格
                if(this.frostrim === 'ltrim'){
                    this.value = val.replace(/(^\s*)/g, "")
                }else if(this.frostrim === 'rtrim'){
                    this.value = val.replace(/(\s*$)/g, "")
                }else if (this.frostrim === 'lrtrim'){
                    this.value = val.replace(/(^\s*)|(\s*$)/g, "")
                }else if(this.frostrim === 'trim'){
                    this.value = val.replace(/\s/g, "")
                }
            },
            // 失去焦点
            blurVal(){
                if(this.value == ''){
                    this.value = ''
                    this.content = ''
                    this.tooltipShow=true
                    return
                }
                if(this.frosOperation === true){
                    this.operation()
                    /*if(isNaN(this.value)){
                        this.value=0
                        this.cfrosRoundingontent = '0'
                    }else{
                        this.value = parseFloat(this.value)
                    }*/
                }
                if(this.frosRounding === true){
                    let val = this.value.toString()
                    // this.content = this.value.toString()
                    let num1 = val.indexOf('.')
                    let num2 = parseInt(this.length)-parseInt(this.frosPrecision)
                    let num3 = val.length
                    let num4 = parseInt(this.newMaxlength)-parseInt(this.frosPrecision)
                    if(num1>num2){
                        this.content = "长度必须小于等与"+this.length
                    }else if(num1==-1 && num3>num4){
                        this.content = "整数位数小于等于"+num4
                    }else{
                        if(isNaN(this.value)) {
                            this.value = null
                            this.content = '无效数字'
                        } else {
                            this.value = this.fmoney(this.value,this.frosPrecision)
                            this.content = this.value.toString()
                        }
                    }


                    /*this.value = this.fmoney(this.value,this.frosPrecision)
                    this.content = this.value.toString()*/

                    //  this.value = this.fmoney(this.value,this.frosPrecision)

                }
                /*// 逗号隔开
                if(this.frosFloat === true){
                    console.log(111)
                    let val = this.value
                 //   this.formatnumber(val)
                    /!*let t=''
                    for (let i = 0; i < val.length; i++) {
                        t += val[i] + ((i + 1) % 3 == 0 && (i + 1) != val.length ? "," : "");
                    }
                    this.value = t*!/
                   // this.value = (this.value || 0).toString().replace(/(\d)(?=(?:\d{3})+$)/g, '$1,')
                }*/
                this.$emit('blur')
            },
            emitChange(val){
                this.changeVal(val);
                this.$emit('change', val)
            },
            emitFocus(){
                this.$emit('focus')
            },
            // 全角半角转换
            valChangeCode(val){
                let tmp = ""
                for(let i=0;i<val.length;i++){
                    if (val.charCodeAt(i) == 12288){
                        tmp += String.fromCharCode(val.charCodeAt(i)-12256);
                        continue;
                    }
                    if(val.charCodeAt(i) > 65280 && val.charCodeAt(i) < 65375){
                        tmp += String.fromCharCode(val.charCodeAt(i)-65248);
                    }
                    else{
                        tmp += String.fromCharCode(val.charCodeAt(i));
                    }
                }
                this.value = tmp
            },
            //数字类型的方法
            numMethods(){
                // 判断字符长度
                this.getLength()
                let val = this.value.toString()
                this.content = this.value.toString()
                // 只能填写数字
                if(this.onlyNum === true){
                    this.content = this.value.toString()
                    // 是否开启整数
                    if(this.frosInterger){
                        this.value = val.replace(/[^\d]/g, "")
                        if(val.length==this.length){
                            this.content ="长度必须小于等与"+this.length
                        }
                    }
                    //this.value = val.replace(/[^\d]/g, "")

                    if(isNaN(this.value)){
                        this.value=''
                        this.content = ''
                    }
                }
                // 不能填写负数
                if(this.frosNegative === true){
                    this.notNegativeNum()
                }
                // 操作运算符号
                if(this.frosOperation === true){
                    if(!(this.IsMoney(val))){
                        this.content ="无效运算符"
                        this.value = ''
                        this.newMaxlength=this.length
                        return false;
                    }
                }
                if(this.value=='' || this.value==' '){
                    this.tooltipShow=true
                }else{
                    this.tooltipShow=false
                }
            },
            // 判断字符长度是否超出最大值
            getLength(){
                let val = this.value.toString()
                let num1 = val.length
                let symbols = ['+','-','*','/','.']
                for(let i=0;i<symbols.length;i++){
                    if(val.indexOf(symbols[i]) != -1){
                        val = val.split(symbols[i]).join("")
                    }
                }
                let num2 = val.length
                let num3 = parseInt(num1)-parseInt(num2)
                //this.content = this.value.toString()
                if(this.interger){
                    this.newMaxlength=this.length
                    if(val.length==this.length){
                        this.content ="长度必须小于等与"+this.length
                    }
                }else{

                    if(num3!=0){
                        this.newMaxlength = parseInt(this.length)+num3
                    }else if(val.length==0){
                        this.newMaxlength=this.length
                    }else if(num2==this.length){
                        this.content ="长度必须小于等与"+this.length
                    }
                }


                /* if(this.frosRounding == true){

                    /!* if(this.value.length >= this.newMaxlength){
                         let num = Number(this.newMaxlength) - 1
                         this.content = "长度必须小于等与"+num
                     }else{
                         this.content = this.value.toString()
                     }*!/

                    // if(this.value.length >= this.newMaxlength){}



                 }else{
                     if(this.value.length >= this.newMaxlength){
                         this.content = "长度必须小于等与"+this.newMaxlength
                     }else{
                         this.content = this.value.toString()
                     }
                 }*/
            },
            // 不能输入负数
            notNegativeNum() {
                let val = this.value
                this.value = val.replace(/[^\d.]/g, '').replace(/\.{2,}/g, '.')
                this.content = this.value.toString()
                let num = this.find(this.value,'.',1)
                if(num>0){
                    let newVal = val.split("")
                    newVal.splice(num,1,'')
                    this.value = newVal.join('')
                    this.content = this.value.toString()
                }
            },
            // 查找第二次出现的"."的位置
            find(str,cha,num){
                var x=str.indexOf(cha);
                for(var i=0;i<num;i++){
                    x=str.indexOf(cha,x+1);
                }
                return x;
            },
            // 四舍五入,小数点位数
            fmoney(s, n) {
                if(s==''){
                    let t = "";
                    this.newMaxlength=this.length
                    this.tooltipShow=true
                    return t
                }else{
                    // n = n > 0 && n <= 20 ? n : 2;
                    s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
                    let l = s.split(".")[0].split("").reverse(), r = s.split(".")[1];
                    let t = "";
                    // 判断是否开启逗号分隔
                    if(this.frosComma){
                        for (let i = 0; i < l.length; i++) {
                            t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
                        }
                    }else{
                        for (let i = 0; i < l.length; i++) {
                            t += l[i]
                        }
                    }

                    if(n==0){
                        return t.split("").reverse().join("") ;
                    }else{
                        return t.split("").reverse().join("") + "." + r;
                    }
                }



            },

            // 数字三位加逗号
            /* formatnumber(val){
                 console.log(val)
                 if(this.comma){
                    // this.value = (val || 0).toString().replace(/(\d)(?=(?:\d{3})+$)/g, '$1,')
                 }else{
                    // this.value=val
                 }

             },*/

            // 操作运算符
            operatorNum() {
                // 判断字符长度
                this.getLength()
                // 运算符操作判断
                let val = this.value.toString()
                if(!(this.IsMoney(val))){
                    this.content ="无效运算符"
                    this.value = 0
                    return false;
                }
            },
            // 判断是否符合运算标准
            IsMoney(Money){
                Money = Money.toString()
                if(/^[\d|+{2,''}|.|*|/|-]+$/.test(Money)) {
                    let counetFirst = Money.charAt(0)
                    let counetLast = Money.charAt(Money.length-1)
                    let counetLast2 = Money.charAt(Money.length-2)
                    let symbols = ['+','-','*','/','.']
                    if(isNaN(counetFirst)){  //不是数字，是运算
                        this.content = "无效运算符"
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
                    /* if(symbols.indexOf(counetLast) > -1 && symbols.indexOf(counetLast2) > -1){

                         this.content = "无效运算符33"
                     }*/
                    return true
                }
                else{
                    return false;
                }

            },
            // 操作运算符
            operation(){
                let val = this.value + ''
                this.content = val.toString()

                if(val.indexOf(",") != -1){
                    val = val.split(",").join("")
                }


                /*if(val === this.frosDefValue){
                   this.value = parseFloat(this.frosDefValue)
                   this.content = this.frosDefValue.toString()
               }*/
                if(!isNaN(val)){
                    // this.value = parseFloat(this.frosDefValue)
                    // this.content = this.frosDefValue.toString()
                    this.content = this.value.toString()
                }
                else{
                    let shu=/[+,\-,*,/]/
                    let fu=/[0-9,.,=]/
                    let arr = []
                    let fuhao = []
                    for(let h=0;h<val.length;h++){
                        arr=val.split(shu);
                    }
                    for(let h=0;h<val.length;h++){
                        fuhao=val.split(fu);
                    }
                    this.yidong(fuhao,arr)
                    this.jisuan_1(fuhao,arr);
                    this.jisuan_2(fuhao,arr);
                    this.value =parseFloat(arr[0]);
                    this.content = (parseFloat(arr[0])).toString()
                    if(isNaN(this.value)){
                        this.value=''
                        this.content=''
                    }
                }
            },
            // 先算乘除
            jisuan_1(fuhao,arr){
                for(var a=0;a<fuhao.length;a++){
                    for(var h=0;h<arr.length;h++){
                        if(fuhao[a]=="*"){
                            arr[a]=parseFloat(arr[a])*parseFloat(arr[a+1]);
                            arr[a+1]="";
                            fuhao[a]="";
                            this.yidong(fuhao,arr)
                        }else if(fuhao[a]=="/"){
                            arr[a]=parseFloat(arr[a])/parseFloat(arr[a+1]);
                            arr[a+1]="";
                            fuhao[a]="";
                            this.yidong(fuhao,arr)
                        }else if(fuhao[a]=="%"){
                            arr[a]=parseFloat(arr[a])%parseFloat(arr[a+1]);
                            arr[a+1]="";
                            fuhao[a]="";
                            this.yidong(fuhao,arr)
                        }
                    }//for
                }//for

            },
            // 再算加减
            jisuan_2(fuhao,arr){
                for(var a=0;a<fuhao.length;a++){
                    for(var h=0;h<arr.length;h++){
                        if(fuhao[a]=="+"){
                            arr[a]=parseFloat(arr[a])+parseFloat(arr[a+1]);
                            arr[a+1]="";
                            fuhao[a]="";
                            this.yidong(fuhao,arr)
                        }else if(fuhao[a]=="-"){
                            arr[a]=parseFloat(arr[a])-parseFloat(arr[a+1]);
                            arr[a+1]="";
                            fuhao[a]="";
                            this.yidong(fuhao,arr)
                        }
                    }//for

                }//for
            },
            //将数组往前移一
            yidong(fuhao,arr){
                //数字前移
                for(var a=0;a<arr.length;a++){
                    if((a+1)<arr.length){
                        if(arr[a]==""&&arr[a+1]!=""){
                            arr[a]=arr[a+1];
                            arr[a+1]="";
                        }
                    }
                }
                //清除符号数组空值
                for(var i = 0 ;i<fuhao.length;i++){
                    if(fuhao[i] == "" || typeof(fuhao[i]) == "undefined") {
                        fuhao.splice(i,1);
                        i= i-1;
                    }
                }
            }

        }
    }
</script>
<style scoped>
  .pagebox{
    margin-top: 20px;
    clear: both;
  }
  .pagelist{
    display: inline-block;
    float:right
  }
  .el-checkbox + .el-checkbox {
    margin-left:0px;
  }
  .btn_left i{
    font-size: 18px;
    margin-right: 10px
  }
</style>
