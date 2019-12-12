#持续构建

## 升级node版本

```
npm install -g n
n v10.15.3
node -v
```
## 查看系统node的安装路径
```
which node
```

## 通过N_PREFIX变量来修改 n 的默认node安装路径
```
vim ~/.bash_profile   
export N_PREFIX=/usr/local/bin/node #node实际安装位置
export PATH=$N_PREFIX/bin:$PATH
source ~/.bash_profile
```
