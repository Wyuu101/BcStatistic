## **BcStatic**
一款用于自定义占位符变量来统计部分服务器集群人数的插件

通过在配置文件稍作修改，就能直接在为其他插件提供便捷的自定义占位符

### 指令
`/bcstatic help` 查看帮助

`/bcstatic list` 列出所有注册成功的占位符

`/bcstatic reload` 热重载插件
>请注意，热重载并不能解决所有问题，如果热重载失败，建议重启

### 提示
1. 插件初次加载会自动释放默认配置文件，文件内附有注释
2. 每次加载插件时，会自动注册占位符，并统计注册成功与失败的数量
3. 检验占位符可以使用/papi来进行联调


### 配置文件
#### 1. 示例
```yml
StaticServers:
  Servers1:
    name: ""
    offset: 0
    itemAmountMode: false
    NoPlayerDefault: 1
    elements:
      - ""
```

#### 2. 介绍
```yml
#Servers1为自定义键，可以创建多个

#name为自定义占位符名称（比如设置为BedWar,那么你创建的占位符就是`%bcstatic_BedWar%`

#offset为数量偏置，如果为1，那么最终结果都会被加1后返回

#itemAmountMode:是物品模式，为了避免物品数量为0引发异常，特设此模式。true启用此模式，则NoPlayerDefault生效，并且最大返回值为64;设置为false时NoPlayerDefault不生效，且返回值无上限；

#NoPlayerDefault：在itemAmountMode启用下，可以在这里单独定义0值的映射结果而不受到偏置offset影响。如设置为1，那么即使总人数为0，它仍返回1

#elements为需要占位符去统计的服务器，最终的人数值就是这些服务器的总人数，需注意这里要填每个服务器在BC里设置的名称
```


### 英文版
已发布在[spigot官网](https://www.spigotmc.org/threads/bcstatistic.640706/)

![image](https://github.com/YF-Eternal/DXZzz-Server/assets/145853103/5383dc97-825d-41f8-8cc4-7cf50bb62367)
