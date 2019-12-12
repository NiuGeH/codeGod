<template>
    <div class="component-tablefilter">
        <div class="items">
            <template v-for="(item, index) of filter">

                <div class="item">
                    <span class="label">{{ item.label }}</span>
                    <div class="value">

                        <!-- 文本框 -->
                        <el-input v-if="item.type === 'input'" placeholder="请输入内容" v-model="outval[ item.key ]" clearable></el-input>

                        <!-- 下拉框 -->
                        <el-select
                             v-if="item.type === 'select'"
                            v-model="outval[ item.key ]" placeholder="请选择" clearable>
                            <el-option
                              v-for="item in selSource[ item.key ]"
                              :key="item.value"
                              :label="item.label"
                              :value="item.value">
                            </el-option>
                        </el-select>

                        <!-- 日期 -->
                        <el-date-picker
                            v-if="item.type === 'date'"
                            v-model="outval[ item.key ]"
                            type="daterange"
                            range-separator="至"
                            start-placeholder="开始日期"
                            end-placeholder="结束日期"
                            value-format="timestamp">
                        </el-date-picker>

                        <!-- 城市联动 -->
                        <CityLinkageComponent
                            v-if="item.type === 'citylinkage'"
                            :reset="isreset"
                            @getvalue="getCityLinkage($event, item)"
                        ></CityLinkageComponent>
                    </div>
                </div>
            </template>
        </div>

        <!-- 按钮 -->
        <div class="btns">
            <el-button @click="$emit('getvalue', outval)" type="primary" size="mini">查询</el-button>
            <el-button @click="reset" size="mini">重置</el-button>
        </div>
    </div>
</template>
<script>
    export default {
        data() {
            // sitemap 组件默认配置
            const defaults = {};

            return {
                // 组件默认配置
                option: defaults,
                filter: [],
                isreset: false,
                selSource: {},
                outval: {}
            }
        },
        props: ["source", "options"],
        watch: {
            options(n){
                this.option = Object.assign({}, this.option, n);
            },
            source(n){
                this.filter = n;
                this.filter.forEach( res => {
                    if( res.type === 'select' ){
                        this.selSource[ res.key ] = this.getSelectList( res.url );
                    }
                });
            }
        },
        computed: {},
        created(){},
        mounted(){
            this.option = Object.assign({}, this.option, this.options);
            this.filter = this.source;
        },
        methods: {
            getSelectList( url ){
                return [{
                    label: '测试',
                    value: 'ceshi'
                }];
            },

            getCityLinkage( val, item ){
                this.outval[ item.key ] = val;
            },

            reset(){
                this.outval = {};
                this.isreset = true;
                setTimeout(()=>{ this.isreset = false; },60);
            }
        }
    }
</script>
<style scoped lang="scss">

    .component-tablefilter {
        display: flex;
        padding: .45rem 0 .25rem 0;

        .items {
            display: flex;
            flex: 1;
            flex-wrap: wrap;
        }

        .item {
            align-items: center;
            display: flex;
            margin: 0 0 .2rem 0;
            width: 32.4%;

            .label {
                font-size: .16rem;
                margin-right: .05rem;
                text-align: right;
                white-space: nowrap;
                width: 1rem;
            }
            .value {
                flex: 1;
            }
        }

        .btns {
            align-items: center;
            display: flex;
            justify-content: center;
            margin-top: -.2rem;
            padding: 0 .4rem 0 0;
        }
    }
</style>
