import SiteMapComponent from './sitemap.component';
import NavComponent from './nav.component';
import TableComponent from './table.component';
import TableFilterComponent from './tablefilter.component';
import TabsComponent from './tabs.component';
import CityLinkageComponent from './citylinkage.component';

const
    Register = [
        // global true 注册成为全局组件, 在 vue 页面不需要 import
        // global false 不注册成为全局组件, 在 vue 页面使用需要单独 import 组件
        { name: 'SiteMapComponent', component: SiteMapComponent, global: true },
        { name: 'NavComponent', component: NavComponent, global: true },
        { name: 'TableComponent', component: TableComponent, global: true },
        { name: 'TableFilterComponent', component: TableFilterComponent, global: true },
        { name: 'TabsComponent', component: TabsComponent, global: true },
        { name: 'CityLinkageComponent', component: CityLinkageComponent, global: true },
    ],
    Components = {
        install( Vue ) {
            // 注册组件
            Register.forEach( val => {
                if( val.global ){
                    Vue.component( val.name, val.component );
                }
            });
        }
    };

export default Components;