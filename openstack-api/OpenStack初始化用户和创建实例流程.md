# OpenStack初始化用户和创建实例流程

## 一、初始化用户

包含用户的权限模块和网络模块，权限多区域共用（keystone服务），网络各区域私有（neutron）。权限用于用户获取token认证，以访问OpenStack各个服务；网络用于用户创建实例时使用。

### 1.1权限模块

```java
1.创建project；
2.创建user；
3.将user分配role并绑定到project。
```



### 1.2网络模块

```java
1.创建network；
2.创建subnet;
3.创建router;
4.将router绑定到subnet;
5.创建security group;
6.创建security group rule。
```

## 二、创建实例

创建实例包含核心流程和非核心流程（附属条件）。核心流程是实例成功创建并且能访问使用；非核心流程则是实例的一些附加条件，并不影响实例的使用，例如Qos、volume等。

### 2.1核心流程

```java
1.创建instance；
2.创建floating ip；
3.将floating ip绑定到instance；
4.将浮动ip通过端口号映射到routerOS服务。
```

### 2.2非核心流程

```java
1.创建qos-policy；
2.创建qos policy bandwidth rule；
3.将qos policy绑定到floatingIp；
4.创建volume；
5.将volume绑定到instance。
```