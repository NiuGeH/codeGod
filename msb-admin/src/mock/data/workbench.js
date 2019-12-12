export const getWarehouseAmount = req => {
    console.log('@@@Mock请求参数：', req)
    return {
        'code': 0,
        'message': '操作成功!',
        'data': [
            {
                name: '四川',
                value: '3',
            },
            {
                name: '重庆',
                value: '13',
            },
            {
                name: '新疆',
                value: '6',
            },
            {
                name: '内蒙古',
                value: '28',
            },
            {
                name: '甘肃',
                value: '10',
            },
            {
                name: '宁夏',
                value: '6',
            },
            {
                name: '陕西',
                value: '9',
            },
            {
                name: '湖北',
                value: '4',
            },
            {
                name: '湖南',
                value: '8',
            },
            {
                name: '河南',
                value: '12',
            },
            {
                name: '黑龙江',
                value: '10',
            },
            {
                name: '辽宁',
                value: '30',
            },
            {
                name: '云南',
                value: '9',
            }
        ]
    }
}
