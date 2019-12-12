export const getFrosTable = req => {
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
                    name: '王小虎A',
                    province: 50,
                    city: '普陀区',
                    address: '上海市普陀区金沙江路 1512 弄上海市普陀区金沙江路 1512 弄',
                    zip: 200,
                    uploadId: '',
                },
                {
                    id: 2,
                    IsAudit: 1,
                    date: '2016-05-01',
                    name: '王小虎A',
                    province: 50,
                    city: '普陀区',
                    address: '上海市普陀区金沙江路 1512 弄上海市普陀区金沙江路 1512 弄',
                    zip: 200,
                    uploadId: '',
                },
                {
                    id: 3,
                    IsAudit: 1,
                    date: '2016-05-01',
                    name: '王小虎A',
                    province: 50,
                    city: '普陀区',
                    address: '上海市普陀区金沙江路 1512 弄上海市普陀区金沙江路 1512 弄',
                    zip: 200,
                    uploadId: '',
                },
                {
                    id: 4,
                    IsAudit: 1,
                    date: '2016-05-01',
                    name: '王小虎A',
                    province: 50,
                    city: '普陀区',
                    address: '上海市普陀区金沙江路 1512 弄上海市普陀区金沙江路 1512 弄',
                    zip: 200,
                    uploadId: '',
                }
            ],
            'tableAsset': [
                {
                    id: 1,
                    IsAudit: 1,
                    date: '2016-05-01',
                    type: '普通仓库',
                    number:'001',
                    customer:'美团',
                    province: '上海',
                    city: '普陀区',
                    name: '上海市普陀区金沙江路',
                    container:1,
                    refrigerator:2,
                    gateway:3,
                    state: '已启用',
                    uploadId: '',
                }
            ]
        }
    }
}

export const getWarehouseFilter = req => {
    console.log('@@@Mock请求参数：', req)
    return {
        'code': 0,
        'message': '操作成功!',
        'data': [
            {
                label: '名字',
                key: 'name',
                type: 'input'
            },
            {
                label: '性别',
                key: 'sex',
                type: 'select',
                url: ''
            },
            {
                label: '创建日期',
                key: 'createtime',
                type: 'date'
            },
            {
                label: '城市',
                key: 'city',
                type: 'select',
                url: ''
            },

        ]
    }
}

export const getAssetFilter = req => {
    console.log('@@@Mock请求参数：', req)
    return {
        'code': 0,
        'message': '操作成功!',
        'data': [
            {
                label: '省',
                key: 'province',
                type: 'select'
            },
            {
                label: '市',
                key: 'city',
                type: 'select',
                url: ''
            },
            {
                label: '创建日期',
                key: 'createtime',
                type: 'date'
            },
            {
                label: '客户名称',
                key: 'number',
                type: 'input',
            },
            {
                label: '仓库编号',
                key: 'number',
                type: 'input',
            },

        ]
    }
}
