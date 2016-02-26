Bigapple
========

这是一个相对全面，的安卓快速开发工具

这个框架主要分为以下几部分，这里大致做一个介绍，具体使用可以查看我的wiki

##如果你是用Android Studio工具开发，可以这样依赖：
dependencies {
    compile 'com.xuan:bigapplelib:2.0.0'
}

##IOC注解注入
ioc部分主要使用注解，@InjectView。主要用来注入View对象。以免重复调用findViewByView这个方法。使代码更加整洁。

##Asynctask异步加载封装
用模板方法，简易的封装了安卓Asynctask类的使用，内置了ProgressDialog的实现。使用他你只需要关注你的逻辑部分。

##Bitmap加载网络图片
有一种需求是这样的，需要从网络上加载图片。例如，加载头像，加载广告图片等。如果从头自己写，需要考虑很多问题啊，例如：
（1）缓存问题，你不可能每次都去网络加载，用户那点可怜的流量被你这么白白浪费了不砍死你。
（2）说到缓存，你用什么缓存呢。据说一个应用安卓只分配给你16M内存，有些是24M根据不同手机而定。而现在的一张图片就十几M，两个图片下来你就爆了，这就是传说中OOM。
（3）在加载多张图片快速切换时，一般的会出现卡顿现象，这就需要特殊处理。

##本地存储db部分
db部分是对sqlite数据操作的半自动化封装。方便sql语法的升级。在操作数据库方面，有点类似Spring的轻量级封装。

##IO模块
对文件的创建、删除、复制、查询等操作的封装。

##Cache模块
对内存缓存，文件缓存做了一个较好分装使用方便。

##Utils模块
里面有杂七杂八的工具类，只要你用心，总能找到1。


##changelog
=============
* [ V2.0.0 ]（1）支持jcenter引用的首次版本
* [ V2.0.1 ]（1）重构HTTP模块
