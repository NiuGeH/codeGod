<template>
    <div class="view-home">
        
    </div>
</template>
<script>
    export default {
        name: 'view-home',
        data(){
            return {
                query: {}
            }
        },
        mounted(){
            this.query = this.$route.params;
        },
        computed: {
        },
        methods: {
           
        }
    }
</script>
<style scoped lang="scss">
    .view-home {
        margin: 0 -.73rem 0 -.45rem;;
        min-height: 100%;
        position: relative;
    }
</style>
