import Vue from 'vue';

const verify = {

    test: ( regx, value ) => {

        const regxs = {
            // 只能输入中文
            'u': /^[\u4e00-\u9fa5]+$/,

            // 只能输入数字
            'n': /^\d+$/,

            // 只能输入英文
            'e': /^[a-zA-Z]+$/,

            // 只能输入中文, 英文大小写a-z, 数字0-9
            'une': /^[\u4e00-\u9fa5a-zA-Z0-9]+$/,

            // 手机号码验证
            'cp': /^(0|86|17951)?(13[0-9]|15[012356789]|166|17[3678]|18[0-9]|14[57])[0-9]{8}$/,

            // 固定电话验证
            'fp': /^([0-9]{3,4}-)?[0-9]{7,8}$/
        };

        return regxs[ regx ].test( value ) && /\S/.test( value );
    }
}

Vue.prototype.verify = verify.test;