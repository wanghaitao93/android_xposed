
# android xposed module develop project


### 进度表

当前问题    |   解决方法    | 进度
---|---|---
android xposed 模块如何开发？ |[官网的案例实现，监听系统时钟方法，使时钟颜色改变](https://github.com/rovo89/XposedBridge/wiki/Development-tutorial)|ok
如何在xposed模块开发中安装存放在assets文件夹下的XposedInstall.apk?   |  [android一个应用安装另一个apk](http://blog.csdn.net/zxc514257857/article/details/77485561)    |ok
如何不显示安装界面情况下安装assets文件夹下的XposedInstall.apk   |  [android一个应用安装另一个apk静默安装(需root)](http://blog.csdn.net/zxc514257857/article/details/77488832)   | ok
静默安装后, 在模块程序中如何打开XposedInstall程序?  | 通过获取包名和类名, [反编译Apk，获取包名和类名](http://blog.csdn.net/vipzjyno1/article/details/21039349)    | ok
如何通过代码以及包名卸载应用?            |[android应用程序中启动或卸载其他App程序](http://blog.csdn.net/wangjintao1988/article/details/12572307)  |ok
如何通过代码以及包名静默的卸载APP?    |  [pm 删除隐藏apk](https://my.oschina.net/u/2502529/blog/822114g) |
如何隐藏安装后的XposedInstall应用的图标？  |    [android隐藏应用图标](https://www.jianshu.com/p/0d64bce9fbd2/)    |   ok
如何使用Android Studio 打包改了代码的XposedInstall应用的Apk? |  [［Android Studio 权威教程］打包、生成jks密钥、签名Apk、多渠道打包](http://blog.csdn.net/yy1300326388/article/details/48344411) | ok
如何让xposedInstall与android版本相对应?   |  [Xposed框架对应各系统版本的下载与卸载](http://xposed.appkg.com/1152.html)    --  -- [XposedInstall Github](https://github.com/rovo89/XposedInstaller/releases)    |  ok (目前2.6.1)
由于2.6.1以及以前的xposed版本源码是Eclipse  |[android studio 导入eclipse项目, 注意Jar包引用](https://www.jianshu.com/p/e96034f69dec)    | ok
如何调用隐形调用xposed install方法使它激活(framework/module)    |  无 |
如何调用android 系统重启机器激活xposed  |  无   | 

