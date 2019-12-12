export const getMenu = req => {
    console.log('@@@Mock请求参数：', req)
    return {
        'code': 0,
        'message': '操作成功!',
        'data': [
            {
                id: 1,
                name: '运营管理',
                url: '/view/Home',
                icon: 'icon-daohangshouye'
            },
            {
                id: 2,
                name: '人资管理',
                url: '',
                icon: 'icon-cangkuguanli',
                children: [
                    {
                        id: 21,
                        pid: 2,
                        name: '推荐签约审核',
                        url: '/view/',
                        icon: '',
                    }
                ]
            },
            {
                id: 3,
                name: '项目管理',
                url: '',
                icon: 'icon-shujubidui',
            },
            {
                id: 4,
                name: '财务管理',
                url: '',
                icon: 'icon-shebeizichanguanli',
                children: [
                    {
                        id: 23,
                        pid: 4,
                        name: '人力外包收款',
                        url: '/view/',
                        icon: '',
                    },
                    {
                        id: 24,
                        pid: 4,
                        name: '人力外包付款',
                        url: '/view',
                        icon: '',
                    }
                ]
            }
        ]

    }
}
