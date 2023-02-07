# jxys-vue

jxys-vue脚手架

## 简介

&emsp;&emsp; 建行一世脚手架系统是一套快速开发平台，是根据开源框架[若依](https://github.com/yangzongzhuan/RuoYi-Vue)
二次开发而来，支持加载动态权限菜单，多方式轻松权限控制，高效率开发，使用代码生成器可以一键生成前后端代码。<br>
&emsp;&emsp;
经过不同系统的使用总结以及其他技术大牛的改编参考，加入了许多适用于现在开发习惯的功能框架例如mybatis-plus、mybatis-plus-join、hutool等，并且同步接入了框架本身具有的数据权限控制以及代码生成器，包括mybatis-plus自带的伪删除与自动注入配置也可以使用。<br>
&emsp;&emsp; 还有一些包括开发习惯上的变动，例如在原本只有开发与正式环境的配置文件中加入了测试的配置文件、代码生成器中的特色代码、父级实体类中的属性等等。<br>
&emsp;&emsp; 目前，根据需求，建行一世脚手架系统另有一些格外加入的功能，包括：基于webSocket开发的三级通知功能、前端美化UI组件、logback日志、基于docker的部署等等。<br>
&emsp;&emsp; 除此之外，建行一世脚手架系统也一直跟随若依官方进行bug与漏洞的修补，现在已经同步迭代到了3.8.5版本。

## 快速开始

1. 拉取Git：https://github.com/a241978181/jxys-vue.git
2. 使用maven工具下载jar包
3. 将目录【jxys-vue/sql/】中的【jxys_vue.sql】文件放入MySQL执行
4. 修改【jxys-vue/jxys-admin/src/main/resources】目录下【application-druid.yml】文件中的MySQL配置与Redis配置
5. 在命令行中进入到【jxys-vue/jxys-ui】目录下，执行如下指令：npm install或者yarn install。

## 更新简报

> ### v1.00
>

> 2023-2-7

1. 第一次公开使用

## 文档

    文档位置（/doc/建行一世脚手架系统介绍及使用方式.docx)

文档内提供了详细的功能说明，强烈建议阅读文档后再开始编码。
![文档内容截图](https://s1.ax1x.com/2023/02/07/pSgc07n.png "文档内容截图")

当前版本：v1

## 基本功能

- 用户管理：用户是系统操作者，该功能主要完成系统用户配置。
- 部门管理：配置系统组织机构（公司、部门、小组），树结构展现支持数据权限。
- 岗位管理：配置系统用户所属担任职务。
- 菜单管理：配置系统菜单，操作权限，按钮权限标识等。
- 角色管理：角色菜单权限分配、设置角色按机构进行数据范围权限划分。
- 字典管理：对系统中经常使用的一些较为固定的数据进行维护。
- 参数管理：对系统动态配置常用参数。
- 通知公告：系统通知公告信息发布维护。
- 操作日志：系统正常操作日志记录和查询；系统异常信息日志记录和查询。
- 登录日志：系统登录日志记录查询包含登录异常。
- 在线用户：当前系统中活跃用户状态监控。
- 定时任务：在线（添加、修改、删除)任务调度包含执行结果日志。
- 代码生成：前后端代码的生成（java、html、xml、sql）支持CRUD下载 。
- 系统接口：根据业务代码自动生成相关的api接口文档。
- 服务监控：监视当前系统CPU、内存、磁盘、堆栈等相关信息。
- 缓存监控：对系统的缓存信息查询，命令统计等。
- 在线构建器：拖动表单元素生成相应的HTML代码。
- 连接池监视：监视当前系统数据库连接池状态，可进行分析SQL找出系统性能瓶颈。
- 通知管理：后端封装有工具类，创建通知后将根据通知等级以不同程度实时反馈到用户前端

## 相关依赖

1. 系统环境

- Java EE 8
- Servlet 3.0
- Apache Maven 3

2. 主框架

- Spring Boot 2.2.14
- Spring Framework 5.2.x
- Spring Security 5.2.x

3. 持久层

- Apache MyBatis 3.5.x
- Hibernate Validation 6.0.x
- Alibaba Druid 1.2.x
- mybatis-plus 3.4.2
- Hutool 5.8.5

4. 视图层

- Vue 2.6.x
- Axios 0.21.x
- Element 2.15.x

## 定制化系统截图

#### 1. 通知弹出框（初级）

<figure>
    <!--通知红点-->
    <img src="https://s1.ax1x.com/2023/02/07/pSgcXBd.png" width="40%" height="200">
    <!--通知弹出框-->
    <img src="https://s1.ax1x.com/2023/02/07/pSgcjHA.png" width="40%" height="200">
</figure>

#### 2. 地图组件及数据

<figure>
    <!--地图组件-->
    <img src="https://s1.ax1x.com/2023/02/07/pSggS4P.png" width="40%" height="200">
    <!--地区数据-->
    <img src="https://s1.ax1x.com/2023/02/07/pSgcxAI.png" width="40%" height="200">
</figure>

#### 3. 多样化组件

![多样化组件](https://s1.ax1x.com/2023/02/07/pSgczNt.png "多样化组件")


