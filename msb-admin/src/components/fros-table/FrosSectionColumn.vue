<template>
    <div class="accessory">
        <el-table-column
                v-if='selectionShow'
                type="selection"
                width="50"
                fixed
                highlight-current-row >
        </el-table-column>
        <!--<el-table-column v-if='indexShow'
                         type="index"
                         fixed
                         :label="indexLabel"
                         :indexWidth="indexWidth">
        </el-table-column>-->
       <!-- <el-table-column
                v-if='expandShow'
                type="expand"
                width="50"
                fixed
                highlight-current-row >
        </el-table-column>-->
    </div>
</template>
<script>
    export default {
        props: {
            indexShow: Boolean, // 是否显示排序列
            selectionShow: Boolean, // 是否显示多选列
            expandShow: Boolean, // 是否显示展开列
            indexLabel: String,
            indexWidth: Number
        }
    }
</script>
