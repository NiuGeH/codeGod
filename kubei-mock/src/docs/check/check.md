# 参数校验配置

##   一、点击“新增验证对象”按钮，配置校验对象信息如下
 
![](http://mybucket1511-1252602947.cn-north.myqcloud.com/12345/26bbfdd2-039d-41a9-8bf1-f0e0bbefce09.png)

##  二、点击校验对象列表中“编辑”按钮，点击“新增验证属性”配置校验对象属性

![](http://mybucket1511-1252602947.cn-north.myqcloud.com/12345/8d4e408c-43fd-4e90-a4cf-91b0ab3c37f1.png)
 
1.	进入配置验证对象页面，点击“验证场景”，然后点击“新增验证场景”按钮
 
![](http://mybucket1511-1252602947.cn-north.myqcloud.com/12345/a72bde3f-599a-40aa-aa74-cc5f30910482.png)

## 三、点击场景列表中“验证规则”按钮，然后点击“动态添加数据”
1.	非空
①　字符串类型非空校验配置
 
![](http://mybucket1511-1252602947.cn-north.myqcloud.com/12345/ec0e8462-02ac-4fb3-ba52-e4ca6c57c689.png)

②　数字类型非空校验配置
 
![](http://mybucket1511-1252602947.cn-north.myqcloud.com/12345/0699f618-d9c6-4a6f-bc7f-8c627b7c50d6.png)

③　下拉框非空校验配置


![](http://mybucket1511-1252602947.cn-north.myqcloud.com/12345/48575de5-a81a-4ea2-b848-16912d89082a.png)
 

2.	最小值
①　数字最小长度校验配置（注:如果该字段是数字类型的校验，需要对该字段配置一个非空校验规则）
 
![](http://mybucket1511-1252602947.cn-north.myqcloud.com/12345/21feefcf-0316-4646-accc-ede25c7d3094.png?)

②　字符串最小长度校验配置

![](http://mybucket1511-1252602947.cn-north.myqcloud.com/12345/395bd470-1eed-479a-969b-286ede07ec51.png?)

 
③　下拉框复选中最小选中个数校验配置

 ![](http://mybucket1511-1252602947.cn-north.myqcloud.com/12345/b2be64a8-f452-4119-8573-7e2adf1a7926.png?)

3.	最大值
①　数字最大值校验配置（注:如果该字段是数字类型的校验，需要对该字段配置一个非空校验规则）

![](http://mybucket1511-1252602947.cn-north.myqcloud.com/12345/d9d0403b-02fa-4919-92cb-da87734e2f51.png?)

②　字符串最大长度校验配置

![](http://mybucket1511-1252602947.cn-north.myqcloud.com/12345/a0eb6531-11d0-4d76-9121-c8ac32a09521.png?)

 
③　下拉复选框最多选中个数校验配置

![](http://mybucket1511-1252602947.cn-north.myqcloud.com/12345/007795a4-8c88-49af-890b-1484dea88947.png?)

 
4.	正则配置

![](http://mybucket1511-1252602947.cn-north.myqcloud.com/12345/3c190deb-c1a6-4b27-b470-8733baf84464.png?)
 
5.	单唯一配置

![](http://mybucket1511-1252602947.cn-north.myqcloud.com/12345/54239bec-981b-4323-9359-9e83858d770d.png?)
 
如：testName唯一性校验
请求地址：http://172.30.254.67:8081/bootapp/dept/uniqueQuery
请求参数：post
参数格式：
{
	"testName":"测试"
}

6.	组合唯一

![](http://mybucket1511-1252602947.cn-north.myqcloud.com/12345/00b617e6-960c-4a32-a3a6-7f4a1c10d756.png?)

如：testName，testCode组合唯一性校验
请求地址：http://172.30.254.67:8081/bootapp/dept/uniqueQuery
请求参数：post
参数格式：
{
	"testName":"测试",
"testCode":"000008"
}

7.	组内值相等
 
![](http://mybucket1511-1252602947.cn-north.myqcloud.com/12345/dcaafa79-e8f3-45d3-a840-21f73c444906.png?)

8.	组合属性：至少有n个不能为空（n根据情况，设置不同数字）
①　至少n（例如n=2）个字符串类型数据不能为空

![](http://mybucket1511-1252602947.cn-north.myqcloud.com/12345/2040d14f-63d0-4d8b-b8f1-f3dfc2a93e3e.png?)
 
②　至少n（例如n=2）个数字类型数据不能为空

![](http://mybucket1511-1252602947.cn-north.myqcloud.com/12345/e46588ac-24e0-4b5c-9d36-3b90d4094fac.png?)
 
③　多个属性下拉框中至少有n（例如n=2）个不能为空

![](http://mybucket1511-1252602947.cn-north.myqcloud.com/12345/057c08bf-bffe-4996-b735-cc42adad2a49.png?)
 
9.	动态必输校验（目前没有使用场景）

![](http://mybucket1511-1252602947.cn-north.myqcloud.com/12345/a02e0fab-d6fb-4688-814d-ca1b1fa2a835.png?)
 
动态必输校验校验规则具体说明。

校验规则格式如：
```{
 "condition": "value == \"777\"",
 "listenProperties": [
  {
   "mobile": [
    {
     "required": true,
     "type": "string",
     "message": "不能为空",
     "trigger": "change"
    }
   ]
  },
  {
   "telephone": [
    {
     "required": true,
     "type": "string",
     "message": "不能为空",
     "trigger": "change"
    }
   ]
  }
 ]
}

```

校验规则解释：只有当校验字段（如：testName）输入值是“777”时候，会动态校验字段“mobile”，“telephone”是否不为空。

10.	自定义校验（此校验类型，暂时未在系统中显示配置）
根据字段输入值是否为空校验，有就根据选择的字段类型校验，没有值就不校验。

字段类型支持：

【string、number、boolean、regexp、integer、float、array、date、url、email】



```
