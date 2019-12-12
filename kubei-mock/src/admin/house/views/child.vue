<template>
    <div class="modules-child" v-loading.fullscreen.lock="!update" element-loading-background="rgba(255,255,255,.3)" element-loading-text="拼命加载中...">
        <ViewComponent v-if="update"></ViewComponent>
    </div>
</template>
<script>
    import Vue from 'vue';

    let that = null;
    /**
     * [动态加载页面]
     * @param  {[type]} param [description]
     * @return {[type]}       [description]
     */
    const loader = param => {
        Vue.component('ViewComponent', resolve => {
            require([`./${ param.view }/${ param.page || 'index' }`], resolve)
            .catch( res => {
                if( that ){
                    that.$router.push({ path: '/404' });
                }
            });
        });
    }

    export default {
        name: 'view-child',
        data(){
            return {
                query: {},
                update: false
            }
        },
        created(){
            that = this;
            this.query = this.$route.params;
            loader( this.query  );
            setTimeout( () => { this.update = true ; }, 500);
        },
        mounted(){
        },
        computed: {
        },
        methods: {
        }
    }
</script>
<style scoped lang="scss">
    .modules-child {
        height: 100%;
    }
</style>
