<template>
    <el-dropdown
            :type="type"
            :size="size"
            :split-button="splitButton"
            :placement="placement"
            :trigger="trigger"
            :hide-on-click="hideOnclick"
            :show-timeout="showTimeout"
            :hide-timeout="hideTimeout"
            :tabindex="tabIndex"
            @command="handleClick"
            @click="handleClickMain(dpconfig.mainMethod)">
              <span class="el-dropdown-link">
                <i :class="dpconfig.icon"></i>
                  {{dpconfig.mainTitle}}
              </span>
        <el-dropdown-menu slot="dropdown">
            <el-dropdown-item
                    v-for="(item, index) in dpconfig.lists"
                    :icon="item.icon"
                    :key="index"
                    :command="item.itemMethod"
                    :disabled="item.disabled"
                    :divided="item.divided"
            >{{item.itemTitle}}
            </el-dropdown-item>
        </el-dropdown-menu>
    </el-dropdown>
</template>
<script>
    export default {
        data() {
            return {
                maintext: '',
                lists: []
            }
        },
        props: {
            dpconfig:null,  // 渲染菜单
            trigger: String,    // 下来菜单触发方式hover/click
            type: String,   // 菜单按钮类型，同 Button 组件(只在split-button为 true 的情况下有效)
            size: String,   //菜单尺寸(medium / small / mini)，在split-button为 true 的情况下也对触发按钮生效
            splitButton: Boolean,   // 下拉触发元素是否呈现为按钮组
            placement: String,  //top/top-start/top-end/bottom/bottom-start/bottom-end
            hideOnClick: Boolean,   // 是否在点击菜单项后隐藏菜单
            showTimeout: Number,    // 展开下拉菜单的延时（毫秒值，仅在 trigger 为 hover 时有效）
            hideTimeout: Number,    // 收起下拉菜单的延时（毫秒值，仅在 trigger 为 hover 时有效）
            tabIndex: Number
        },
        mounted() {
        },
        methods: {
            // 主按钮触发事件
            handleClickMain(command) {
                this.$emit('mainClick', command)
            },
            // 子按钮触发事件
            handleClick(command) {
                this.$emit('itemClick', command)
            }
        }
    }
</script>

<style>

</style>
