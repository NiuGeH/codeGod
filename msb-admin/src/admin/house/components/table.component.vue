<template>
    <div class="component-table">
        <div class="toolbar" v-if="table.topControl.length > 0">
            <template v-for="(item, index) of table.topControl">
                <el-button @click="$emit('control', { action: item })" size="mini">
                    <template v-if="item.icon">
                        <i :class="['iconfont', item.icon]"></i>
                    </template>
                    <template v-else>
                        <label>
                            <i class="round" :style="{ background: colors[index] }"></i>
                            {{ item.name }}
                        </label>
                    </template>
                </el-button>
            </template>
        </div>
        <el-table :data="table.data" style="width: 100%" :lazy="true" empty-text=" " @selection-change="selected" stripe>
            <el-table-column v-if="option.serialnumber" type="index" width="50" label="序号"> </el-table-column>
            <el-table-column v-if="option.checkbox" type="selection" width="35" fixed="left"></el-table-column>
            <el-table-column fixed="right" label="操作" :width="table.columnControl.length * ( 92 + table.columnControl.length * 8 )" v-if="table.columnControl.length > 0">
                <template slot-scope="scope">
                    <template v-for="(item, index) of table.columnControl">

                        <template v-if="item.showtype == 'ti'">
                            <el-button @click="$emit('control', { action: item, row: scope.row })" size="mini">
                                <i :class="['iconfont', item.icon]"></i>
                                {{ item.name }}
                            </el-button>
                        </template>

                        <template v-else-if="item.showtype == 't'">
                            <el-button @click="$emit('control', { action: item, row: scope.row })" size="mini">
                                {{ item.name }}
                            </el-button>
                        </template>

                        <template v-else-if="item.showtype == 'i'">
                            <i
                                :title="item.name"
                                :class="['iconfont', item.icon]"
                                @click="$emit('control', { action: item, row: scope.row })"
                            ></i>
                        </template>

                    </template>
                </template>
            </el-table-column>
            <template v-for="(item, index) of table.column">
                <!-- 时间 -->
                <el-table-column v-if="item.value == 'createTime'" :label="item.key" :width="table.column.length < 10 ? 245 : 200">
                    <template slot-scope="scope">
                        {{ formatTime( scope.row[ item.value ] ) }}
                    </template>
                </el-table-column>

                <el-table-column v-else :prop="item.value" :label="item.key" :width="table.column.length < 10 ? 'auto' : 200">
                    <template slot-scope="scope" :show-overflow-tooltip="true">
                        {{ scope.row[ item.value ] != '' ? scope.row[ item.value ] : '-' }}
                    </template>
                </el-table-column>
            </template>
        </el-table>
        <el-pagination
            v-if="option.showpage"
            background
            layout="sizes, prev, pager, next, jumper"
            :page-sizes="[10, 20, 30, 50]"
            :total="table.pagination.total"
            :current-page="table.pagination.current"
            :style="{ textAlign: 'center', padding: '.2rem 0' }"
            @current-change="togglePage"
        ></el-pagination>
    </div>
</template>
<script>
export default {
    data() {
        // table 组件默认配置
        const defaults = {
            showpage: true,
            serialnumber: false, // 是否显示序号
            checkbox: true // 是否显示复选框
        };

        return {
            // 组件默认配置
            colors: ['#4290BE','#FF9200','#BAC5F3', '#4290BE', '#BAC5F3', '#E60012'],
            option: defaults,
            table: { data: [], columnControl: [], topControl: [], column: [], pagination: {} }
        }
    },
    props: ["source", "options"],
    watch: {
        options(n){
            this.option = Object.assign({}, this.option, n);
        },
        source(n) {
            this.table = n;
        }
    },
    computed: {
        formatTime(){
            return val => {
                const date = new Date( val );
                return `${ date.getFullYear() }-${ date.getMonth()+1 }-${ date.getDay() } ${ date.getHours() }:${ date.getMinutes() }:${ date.getSeconds()}`
            }
        },
    },
    created(){},
    mounted(){
        this.option = Object.assign({}, this.option, this.options);
    },
    methods: {
        selected(selection) {
            this.$emit('selected', selection);
        },

        togglePage( val ){
            this.$emit('pages', val);
        }
    }
}

</script>
<style scoped lang="scss">
    .component-table {
        width: 100%;

        .icon-shanchu {
            font-size: .2rem;
        }

        .toolbar {
            // height: 28px;
            // margin-bottom: .1rem;
            padding: .3rem .4rem;
            button {
                height: .4rem;
                padding: 0 .15rem;
                //width: 1.04rem;
            }

            label {
                align-items: center;
                display: flex;
                font-size: .14rem;

                i {
                    margin-right: .12rem;
                }
            }
        }

        .cell {

            button {
                display: inline-block;
                height: .32rem;
                overflow: hidden;
                vertical-align: middle;
                // width: 1.04rem;
            }
        }

        .product-img {
            width: 1rem;
        }

        i.round {
            border-radius: .07rem;
            height: .07rem;
            width: .07rem;
        }

        i.iconfont {
            cursor: pointer;
        }
    }
</style>
