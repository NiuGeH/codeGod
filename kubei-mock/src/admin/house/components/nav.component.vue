<template>
    <div class="component-nav">
        <ul class="left">
            <template v-for="(item, index) of navdata">
                <li :key="index" :class="{ active: item._active }">

                    <span @click="go( item )" v-if="option.showtext" :class="['text']">
                        <i v-if="option.showicon" :class="['iconfont', item.icon]"></i>
                        <span>{{item[ option.keys ]}}</span>
                    </span>

                    <ul class="children" v-if="item.children && item.children.length > 0" :class="{ open: item._active }">
                        <template v-for="(child, index) of item.children">
                            <li @click="go( child )" :class="{ active: child._active }">
                                <el-tooltip effect="dark" :content="child[ option.keys ]" placement="right">
                                    <span class="child-text">{{ child[ option.keys ] }}</span>
                                </el-tooltip>
                            </li>
                        </template>
                    </ul>
                </li>
            </template>
        </ul>
        <span class="pull"><i class="iconfont icon-jiantou"></i></span>

        <!-- <ul class="right" v-if="child && child.length > 0">
            <li v-for="(item, index) of navChild" @click="go( item )" :class="{ active: childCurr._id == item._id }">
                {{ item.title }}
            </li>
        </ul> -->
    </div>
</template>
<script>
    import { mapMutations, mapState } from "vuex";
    export default {
        data() {
            // Nav 组件默认配置
            const defaults = {
                keys: 'value',
                showtext: true,
                showtitle: true,
                showicon: true,
            };

            return {
                // 组件默认配置
                option: defaults,
                navdata: [],
                current: {},
            }
        },
        props: ["source", "options"],
        watch: {
            '$route'(to, from) {
                console.log(to)
                const loops = n => {
                    n.some( e => {
                        if( to.path == e.url || (to.path.search(e.url) > -1 && e.pid ) ){
                            this.toggle( e );
                            this.setRouter( e );
                            return true;
                        }
                        e.children ? loops( e.children ) : null;
                    });
                };
                loops( this.navdata );
            },
            options(n){
                this.option = Object.assign({}, this.option, n);
            },
            source(n) {
                this.navdata = n;
            }
        },
        computed: {
            ...mapState(["router"])
        },
        created(){},
        mounted(){
            this.option = Object.assign({}, this.option, this.options);
            this.router ? setTimeout( () => { this.toggle( JSON.parse(this.router) ); }, 100) : null;
        },
        methods: {
            ...mapMutations(['setRouter']),

            toggle( nav ){
                const loops = n => {

                    n.forEach( e => {

                        this.$set( e, '_active', e.id == nav.id || e.id == nav.pid ? true : false );

                        e.children ? loops( e.children ) : null;

                    });
                }

                loops( this.navdata );
            },

            go( nav ){
                this.toggle( nav );
                this.setRouter( nav );
                if( nav.url ){
                    this.current = nav;
                    this.$router.push({ path: nav.url });
                }
            }
        }
    }
</script>
<style scoped lang="scss">
    .component-nav {
        display: flex;
        height: 100%;
        position: relative;

        .iconfont {
            font-size: .2rem;
        }

        li, i {
            cursor: pointer;
        }

        .left {
            height: 100%;
            min-width: 3.38rem;
            overflow-y: auto;
            padding-top: .55rem;

            > li {
                .text {
                    align-items: center;
                    display: flex;
                    min-height: .77rem;
                    min-width: .5rem;
                    padding: 0 .4rem;

                    i {
                        font-size: .34rem;
                        height: .34rem;
                        line-height: 1;
                        width: .34rem;
                    }

                    span {
                        font-size: .18rem;
                        height: .19rem;
                        margin-left: .18rem;
                    }
                }
            }

            .children {
                height: 0;
                overflow: hidden;

                &.open {
                    height: 100%;
                }

                > li {
                    font-size: .16rem;
                    line-height: .77rem;
                    padding-left: .92rem;
                }
            }
        }

        .right {
            width: 2rem;

            li {
                height: .5rem;
                line-height: .5rem;
                padding: 0 .2rem;
                text-align: left;
            }
        }

        .pull {
            box-shadow: .07rem 0 .29rem 0 rgba(209, 209, 209, .35);
            border-radius: .7rem;
            height: .7rem;
            line-height: .7rem;
            margin: -.35rem 0 0 0;
            padding: 0 .05rem;
            position: absolute;
            right: -.27rem;
            top: 50%;
            text-align: right;
            width: .7rem;
            z-index: -1;
        }
    }
</style>
