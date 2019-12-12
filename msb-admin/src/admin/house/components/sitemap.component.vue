<template>
    <div class="component-sitemap">
        <div class="left">
            <template v-for="(item, index) of sitemap">
                <router-link :to="item.url">{{ item.title }}</router-link>
                <template v-if="index < sitemap.length - 1">
                    <span class="iconfont icon-jiantou"></span>
                </template>
            </template>
        </div>
        <div class="right">
            <span v-if="rText" class="rtext">{{rText}}</span>
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
                sitemap: [
                    {
                        url: '/view/Home',
                        title: '冷链应用'
                    }
                ],
                lmap: null, // lastmap,
                rText: ''
            }
        },
        props: ["source", "options", "lastmap", 'rtext'],
        watch: {
            '$route'(to, from) {
            },
            options(n){
                this.option = Object.assign({}, this.option, n);
            },
            source(n){
                let hash = {},
                    map = [...this.sitemap, ...n].reduceRight((item, next) => {
                        hash[next.title] ? '' : hash[next.title] = true && item.push(next);
                        return item;
                    }, []);
                this.sitemap.push( map[0] );
            },
            lastmap(n){
                let arr = this.sitemap.concat();
                this.lmap ?
                    (
                        arr[ arr.length - 1 ] = n,
                        this.sitemap = arr
                    ):
                    (
                        this.sitemap.push(n),
                        this.lmap = n
                    );
            },
            rtext(n){
                this.rText = n;
            }
        },
        computed: {},
        created(){},
        mounted(){
            this.sitemap = [...this.sitemap, ...this.source];
            this.option = Object.assign({}, this.option, this.options);
        },
        methods: {

        }
    }
</script>
<style scoped lang="scss">
    .component-sitemap {
        display: flex;
        height: .5rem;
        line-height: .5rem;
        margin: .2rem 0;

        .left {
            display: flex;
            flex: 1;
            a {
                font-size: .14rem;
                text-decoration: none;
            }

            span {
                margin: 0 .05rem;
            }
        }

        .right {
            .rtext {
                color: #666;
                font-size: .14rem;
            }
        }
    }
</style>
