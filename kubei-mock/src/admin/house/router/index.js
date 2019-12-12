import Vue from 'vue'
import Router from 'vue-router'
import { store } from '../private/store'

Vue.use( Router )

const route = [
    {
        path: '*',
        name: '404',
        component: () => import('../views/404')
    },
    {
        path: '/',
        name: 'Main',
        component: () => import('../views/Login/index')
    },
    {
        path: '/login',
        name: 'Login',
        component: () => import('../views/Login/index')
    },
    {
        path: '/view',
        name: 'Views',
        component: () => import('../views/index'),
        meta: {
            require: true
        },
        children: [
            {
                path: ':view',
                component: () => import('../views/child'),
                meta: {
                    require: true
                }
            },
            {
                path: ':view/:page',
                component: () => import('../views/child'),
                meta: {
                    require: true
                }
            },
            {
                path: ':view/:page/:id',
                component: () => import('../views/child'),
                meta: {
                    require: true
                }
            }
        ]
    }
];

const routes = new Router({
    routes: route
});


// 路由跳转前处理
routes.beforeEach((to, from, next) => {

    if( to.meta.require ) { // 判断该路由是否需要登录权限
        if( store.state.token ) { // 通过vuex state获取当前的token是否存在
            next();
        } else {
            let url ='/';

            to.fullPath.includes('view') ? url = '/login' : null;

            next({
                path: url,
                query: { redirect: to.fullPath } // 将跳转的路由path作为参数，登录成功后跳转到该路由
            });
        }
    } else {
        next();
    }
});
//路由存储sessionStorage
// router.beforeEach((to, from, next) => {
//   let queryJson = JSON.stringify(from.query)
//   sessionStorage.setItem("fromQuery",queryJson);
//   next()
// })
export default routes;
