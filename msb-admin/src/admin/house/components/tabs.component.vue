<template>
    <div class="component-tabs">
        <template v-for="(item, index) of tabsdata">
            <span :class="{ active: item._active }" @click="toggle( item, index )">{{ item.name }}</span>
        </template>
    </div>
</template>
<script>
    export default {
        data() {
            // tabs 组件默认配置
            const defaults = {
            };

            return {
                // 组件默认配置
                option: defaults,
                tabsdata: []
            }
        },
        props: ["source", "options"],
        watch: {
            options(n){
                this.option = Object.assign({}, this.option, n);
            },
            source(n) {
                this.tabsdata = n;
            }
        },
        computed: {
        },
        created(){},
        mounted(){
            this.option = Object.assign({}, this.option, this.options);
            this.tabsdata = this.source;
        },
        methods: {
            toggle( item, index ){
                this.tabsdata.forEach( res => res._active = res.key == item.key );
                this.$emit('getvalue',{ tab: item, index: index } );
            }
        }
    }
</script>
<style scoped lang="scss">
    .component-tabs {
        display: flex;
        padding: .16rem 0;

        span {
            border-right: .03rem solid #EEF5FD;
            cursor: pointer;
            font-size: .18rem;
            font-weight: bold;
            height: .38rem;
            line-height: .40rem;
            text-align: center;
            width: 1.42rem;
        }
    }
</style>
