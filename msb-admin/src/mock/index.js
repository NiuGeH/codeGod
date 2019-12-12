import Mock from 'mockjs'
import config from '@js/config'

import { getWarehouseAmount } from './data/workbench' // 工作台数据
import { getMenu } from './data/getMenu' // 菜单数据
import { getFrosTable, getWarehouseFilter ,getAssetFilter} from './data/tableData' // table数据
import {getWarningTabledata,getWarningFilter} from './data/warningData'           //告警信息



if (config.isMock) {
    // 是否使用Mock数据

    // 工作台地图数据
    Mock.mock(/\/getWarehouseAmount/, getWarehouseAmount),

    // 菜单数据
    Mock.mock(/\/getMenu/, getMenu),

    // 表格数据
    Mock.mock(/\/getFrosTable/, getFrosTable)
    Mock.mock(/\/getWarehouseFilter/, getWarehouseFilter)
    Mock.mock(/\/getAssetFilter/, getAssetFilter)

    //告警数据
    Mock.mock(/\/getWarningTabledata/, getWarningTabledata)
    Mock.mock(/\/getWarningFilter/, getWarningFilter)
}

export default Mock
