## 菜单项数据结构

FROS-UI支持菜单分组和菜单签单（最多五层），同时支持模块化动态菜单，即不同功能块对应不同的菜单，其主要的数据结构如下所示

```json
{
    "code": 200,
    "data": {
        "menuList": [
            {
                "id": "00001",	// 菜单ID
                "resourceName": "DEMO",	// 菜单名称
                "resourceNo": null,	// 资源代码
                "resourceType": null,	// 资源类型
                "functionType": 1,	// 菜单类型(1:MODULE 2:MENU 3:wnode)
                "icon": "",	// 标题icon
                "resourceDescr": null,	// 资源描述
                "url": "",	// 跳转地址
                "level": null,	// 菜单等级
                "parentResourceNo": "0",	// 父级资源代码
                "recVer": null,	// 版本号
                "children": [	// 子菜单
                    {
                        "id": "00011",
                        "resourceName": "框架基础能力",
                        "resourceNo": null,
                        "resourceType": null,
                        "functionType": 2,
                        "resourceDescr": null,
                        "icon": "fros-icon-kaifa-xianxing",
                        "url": "",
                        "level": null,
                        "parentResourceNo": "00001",
                        "recVer": null,
                        "children": [
                            {
                                "id": "00111",
                                "resourceName": "框架功能",
                                "resourceNo": null,
                                "resourceType": null,
                                "functionType": 3,
                                "resourceDescr": null,
                                "icon": "",
                                "url": "",
                                "level": null,
                                "parentResourceNo": "00003",
                                "recVer": null,
                                "children": [
                                    {
                                        "id": "01118",
                                        "resourceName": "权限控制",
                                        "resourceNo": null,
                                        "resourceType": null,
                                        "functionType": 2,
                                        "resourceDescr": null,
                                        "icon": "",
                                        "url": "",
                                        "level": null,
                                        "parentResourceNo": "00111",
                                        "recVer": null,
                                        "children": [
                                            {
                                                "id": "11181",
                                                "resourceName": "无权限页（仅样式）",
                                                "resourceNo": null,
                                                "resourceType": null,
                                                "functionType": 2,
                                                "resourceDescr": null,
                                                "icon": "",
                                                "url": "demo/noright/NoRight",
                                                "level": null,
                                                "parentResourceNo": "00003",
                                                "recVer": null,
                                                "children": []
                                            }
                                        ]
                                    }
                                ]
                            }
                        ]
                    }
                ]
            },
            {
                "id": "00002",
                "resourceName": "文档",
                "resourceNo": null,
                "resourceType": null,
                "functionType": 1,
                "resourceDescr": null,
                "icon": "",
                "url": "",
                "level": null,
                "parentResourceNo": "0",
                "recVer": null,
                "children": [
                    {
                        "id": "00021",
                        "resourceName": "框架基础能力",
                        "resourceNo": null,
                        "resourceType": null,
                        "functionType": 2,
                        "resourceDescr": null,
                        "icon": "fros-icon-kaifa-xianxing",
                        "url": "",
                        "level": null,
                        "parentResourceNo": "00002",
                        "recVer": null,
                        "children": [
                            {
                                "id": "00212",
                                "businessSysCode": null,
                                "resourceName": "菜单",
                                "resourceNo": null,
                                "resourceType": null,
                                "functionType": 3,
                                "resourceDescr": null,
                                "icon": "",
                                "url": "",
                                "level": null,
                                "parentResourceNo": "00021",
                                "recVer": null,
                                "children": [
                                    {
                                        "id": "02121",
                                        "resourceName": "菜单数据结构",
                                        "resourceNo": null,
                                        "resourceType": null,
                                        "functionType": 2,
                                        "resourceDescr": null,
                                        "icon": "",
                                        "url": "doc/menudatadoc/MenuDataDoc",
                                        "level": null,
                                        "parentResourceNo": "00021",
                                        "recVer": null,
                                        "children": []
                                    }
                                ]
                            }
                        ]
                    }
                ]
            }
        ]
    }
}
```

其中，menuList是对应的列表是模块化对应的菜单，如上面所示，对应的是DEMO和文档两个模块，那么在管理系统的头部上就会区分出两个菜单模块，点击不同的模块，左侧的菜单栏将会加载不同的菜单项。menuList每个元素中的children对应的列表为菜单项的等级级列表，function用于标记菜单分组，如果菜单分组类型为3(wnode)的的话，将会自动为菜单增加分组标题。接下去children元素即为子级菜单，同理children中的每个元素都有一个children，代表的就是子级菜单。需要注意的是，每一级菜单都有对应的url，对应的是需要动态读取的组件路径在manage/views下的相对路径，如果是多级嵌套菜单，就不需要上最后一级的父级菜单上添加url了。

#### 注意事项

<font color=#F5606A>1、使用者可以通过配置对应菜单项的params中的checkSave来配置关闭标签栏的时候是否提示确认关闭</font>

