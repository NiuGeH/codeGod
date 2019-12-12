## 权限控制

> 页面权限

权限的核心控制侧重点理应放在后端，FrosUI提供的页面权限控制是依托于后端已经做了相应的菜单权限分组，用户将无法访问菜单列表中没有的页面（除了在项目配置文件中配置的unCheckPath）。对于用户想要非法访问菜单中没有的组件地址，FrosUI将会抛出一个权限不足的页面。使用者可以修改NotRoot组件自定义权限界面。

> 指令权限

FrosUI默认维护了用户信息userInfo在vuex状态中，对应的root字段指的是用户所属的用户组或者权限组，Fros-UI默认的权限指令是根据root字段进行配置的，目前提供两种权限，一种是在其中现实，另一种是不在其中的话现实，分别使用例子如下：

```html
// 用户组在admin（管理员）或者salesman（业务员）中的显示这个dom
<div v-permit="['admin', 'salesman']"></div>

// 用户组不在admin（管理员）和salesman（业务员）中的显示这个dom
<div v-permit="['!admin', '!salesman']"></div>

// 用户组不在admin（管理员）,在salesman（业务员）或tester（测试人员）中的显示这个dom
<div v-permit="['!admin', 'salesman', 'tester']"></div>
```

#### 注意事项

<font color=#F5606A>1、v-permit传参必须为数组
</font>


