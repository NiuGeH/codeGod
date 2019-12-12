export const getWarningTabledata = req => {
    console.log('@@@Mock请求参数：', req)
    return {
        'code': 0,
        'message': '操作成功!',
        'data': {
            // 普通表格数据
            'total': 500,
            'tableData': [
                {
                    id: 1,
                    IsAudit: 1,
                    date: '2016-05-01',
                    DEPOT_NO: 'hc11230231',
                    province: 50,
                    city: '普陀区',
                    type: '严重告警',
                    zip: 200,
                    uploadId: '',
                },
                {
                    id: 2,
                    IsAudit: 1,
                    date: '2016-05-01',
                    DEPOT_NO: 'hc11230231',
                    province: 50,
                    city: '普陀区',
                    type: '严重告警',
                    zip: 200,
                    uploadId: '',
                },
            ]
        }
    }
}

export const getWarningFilter = req => {
    console.log('@@@Mock请求参数：', req)
    return {
        'code': 0,
        'message': '操作成功!',
        'data': [
            {
                label: '时间范围',
                key: 'time',
                type: 'date'
            },
            {
                label: '策略名称',
                key: 'name',
                type: 'input',
                url: ''
            },
            {
                label: '策略类型',
                key: 'type',
                type: 'select'
            },

        ]
    }
}

