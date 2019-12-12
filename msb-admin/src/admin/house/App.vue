<template>
    <div id="app" :class="[ themeClass ]">
        <router-view />
    </div>
</template>
<script>
    import { mapMutations, mapState } from "vuex";
    export default {
        name: 'App',
        data(){
            return {
                themeClass: ''
            }
        },
        computed: {
            ...mapState(["theme"])
        },
        watch: {
            '$route'(to, from){
                // this.setTheme();
            },
            theme( n ){
                this.themeClass = n;
                document.body.className = this.theme;
            }
        },
        mounted(){
            this.setTheme();
            this.setRem();

            window.onresize = () => this.setRem();
        },
        methods: {
            ...mapMutations([]),

            setRem(){
                let whdef = 100/1920,
                    wH = window.innerHeight,
                    wW = window.innerWidth,
                    rem = wW * whdef;

                document.querySelector('html').style.fontSize = rem + 'px';
            },

            setTheme(){
                this.themeClass = this.theme;
                document.body.className = this.theme;
            }
        }
    }
</script>
<style scoped lang="scss">
</style>
