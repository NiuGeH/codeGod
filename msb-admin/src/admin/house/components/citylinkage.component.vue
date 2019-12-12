<template>
    <div class="component-citylinkage">
        <el-select v-model="outval[0]" placeholder="选择省份" @change="toggle($event, 'p')">
            <el-option
                v-for="item in province"
                :key="item.value"
                :label="item.label"
                :value="item.value">
            </el-option>
        </el-select>
        <el-select v-model="outval[1]" placeholder="选择城市" @change="toggle($event, 'c')">
            <el-option
                v-for="item in city"
                :key="item.value"
                :label="item.label"
                :value="item.value">
            </el-option>
        </el-select>
    </div>
</template>
<script>
    export default {
        data() {
            // citylinkage 组件默认配置
            const defaults = {};

            return {
                // 组件默认配置
                option: defaults,
                province: [{label:'重庆市', value:'0'}],
                city: [{label: '重庆', value:'1'}],
                outval: []
            }
        },
        props: ["options", "reset"],
        watch: {
            options(n){
                this.option = Object.assign({}, this.option, n);
            },

            reset(n){
                n ? this.outval = [] : null;
            }
        },
        computed: {},
        created(){},
        mounted(){
            this.option = Object.assign({}, this.option, this.options);
        },
        methods: {
            toggle( val, type ){
                type === 'p' ? this.outval[0] = val :
                type === 'c' ? this.outval[1] = val : null;
                this.$emit('getvalue', this.outval);
            }
        }
    }
</script>
<style scoped lang="scss">
    .component-citylinkage {
        display: flex;

        > div:not(:last-child) {
            margin-right: .1rem;
        }
    }
</style>
