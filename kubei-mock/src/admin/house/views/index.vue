<template>
    <div id="views">
        <header class="hd">
            <h1 class="title">
                <i class="iconfont icon-liebiao"></i>
                <router-link to="/view/Home">Sinotrans IOT</router-link>
            </h1>
            <div class="hd-bar">
                <ul class="hd-nav">
                    <li>
                        <i class="iconfont icon-xiazai"></i>
                    </li>
                    <li class="message">
                        <i class="iconfont icon-xiaoxi"></i>
                        <span></span>
                    </li>
                    <li class="user">
                        <img src="#" alt="">
                        <span>超级管理员 {{ username }}</span>
                    </li>
                    <li>
                        <i class="iconfont icon-shezhi"></i>
                    </li>
                </ul>
                <!-- <el-select v-model="themeVal" size="mini" placeholder="请选择主题" @change="toggleTheme" :style="{width:'100px'}">
                    <el-option v-for="item in themes" :key="item.value" :label="item.key" :value="item.value">
                    </el-option>
                </el-select> -->
                <!-- <el-button size="mini" @click="getOut">退出</el-button> -->
            </div>
        </header>
        <div class="bd">
            <nav class="nav">
                <NavComponent :source="navdata" :options="navoptions"></NavComponent>
            </nav>
            <div class="container">
                <div class="views-main">
                    <!-- 模块入口 -->
                    <router-view :key="online" />
                </div>
            </div>
        </div>
        <!-- <footer class="ft">&copy; 2019-2020</footer> -->
    </div>
</template>
<script>
    import Vue from 'vue';
    import { mapMutations, mapState } from "vuex";

    export default {
        name: 'views',
        data() {
            return {
                online: '',
                navdata: [],
                navoptions: {
                    keys: 'name',
                },
                username: '',
                themes: [
                    { key:'默认主题', value:'default-theme' },
                    { key:'红色主题', value:'red-theme' }
                ],
                themeVal: ''
            }
        },
        computed: {
            ...mapState(['token', 'theme', 'userinfo'])
        },
        watch: {
            '$route'(to, from){
                // 路由变化的时候才刷新 router-view
                this.online = new Date().getTime();
            }
        },
        created() {

        },
        mounted() {
            this.themeVal = this.theme;
            this.username = this.userinfo.uid;

            this.api.getMenu( res => {
                this.navdata = res.data;
            });
        },
        methods: {
            ...mapMutations(['empty', 'toggleThemeStore']),

            /**
             * [getOut 退出登录]
             * @return {[type]} [description]
             */
            getOut() {
                this.empty();
                this.$message.success('退出登录');
                this.$router.push({ path: '/login' });
            },

            /**
             * [toggleTheme 切换主题]
             * @return {[type]} [description]
             */
            toggleTheme(){
                this.toggleThemeStore(this.themeVal);

                const userinfo =  typeof this.userinfo == 'object' ? this.userinfo : JSON.parse(this.userinfo) ||  null;

                if( userinfo ){
                    // 修改 cookie 记录的主题
                    const cookie = document.cookie.match(`(^| )user=([^;]*)(;|$)`);

                    if( cookie ){
                        document.cookie = `user=${ cookie[2].replace(/\w+-theme/, this.themeVal)}`;
                    }
                }else{
                    this.$message.warning('主题保存失败, 请刷新页面再试');
                }
            }
        }
    }

</script>
<style scoped lang="scss">
    .hd {
        align-items: center;
        box-shadow:.07rem 0 .27rem .02rem rgba(223,223,223,0.94);
        display: flex;
        height: 1.02rem;

        .title {
            font-size: .42rem;
            i {
                cursor: pointer;
                font-size: inherit;
                margin-right: .26rem;
            }
        }

        &-bar {
            display: flex;
            justify-content: flex-end;
            > * {
                margin-right: .1rem;
            }
        }

        &-nav {
            align-items: center;
            display: flex;

            li {
                margin-left: .5rem;

                i {
                    cursor: pointer;
                    color: #a4a4ae;
                    font-size: .3rem;
                }
            }

            .user {
                align-items: center;
                display: flex;
                img {
                    border-radius: .5rem;
                    background: #eee;
                    height: .5rem;
                    margin-right: .13rem;
                    width: .5rem;
                }
            }

            .message {
                position: relative;

                span {
                    border-radius: .15rem;
                    background: #f00;
                    height: .1rem;
                    position: absolute;
                    right: 0;
                    top: .05rem;
                    width: .1rem;
                }
            }
        }
    }

    .bd {
        display: flex;
        height: calc(100% - 1.02rem);

        .nav {
            min-width: .5rem;
            height: 100%;
        }

        .container {
            display: flex;
            flex: 1;
            flex-direction: column;
            height: 100%;
            overflow: hidden;
            overflow-y: auto;
            position: relative;

            .views-main {
                height: 100%;
                padding: 0 .73rem 0 .45rem;

            }
        }
    }

    .ft {
        height: .3rem;
        line-height: .3rem;
        text-align: center;
    }
</style>
