# android xposed module develop project

### 进度表

当前问题    |   解决方法    | 进度
---|---|---
android xposed 模块如何开发？ |[官网的案例实现，监听系统时钟方法，使时钟颜色改变](https://github.com/rovo89/XposedBridge/wiki/Development-tutorial)|ok
如何在xposed模块开发中安装存放在assets文件夹下的XposedInstall.apk?   |  [android一个应用安装另一个apk](http://blog.csdn.net/zxc514257857/article/details/77485561)    |ok
如何不显示安装界面情况下安装assets文件夹下的XposedInstall.apk   |  [android一个应用安装另一个apk静默安装(需root)](http://blog.csdn.net/zxc514257857/article/details/77488832)   | ok
静默安装后, 在模块程序中如何打开XposedInstall程序?  | 通过获取包名和类名, [反编译Apk，获取包名和类名](http://blog.csdn.net/vipzjyno1/article/details/21039349)    | ok
如何通过代码以及包名卸载应用?            |[android应用程序中启动或卸载其他App程序](http://blog.csdn.net/wangjintao1988/article/details/12572307)  |ok
如何通过代码以及包名静默的卸载APP?    |  [pm 删除隐藏apk](https://my.oschina.net/u/2502529/blog/822114g) | ok
如何隐藏安装后的XposedInstall应用的图标？  |    [android隐藏应用图标](https://www.jianshu.com/p/0d64bce9fbd2/)    |   ok
如何使用Android Studio 打包改了代码的XposedInstall应用的Apk? |  [［Android Studio 权威教程］打包、生成jks密钥、签名Apk、多渠道打包](http://blog.csdn.net/yy1300326388/article/details/48344411) | ok
如何让xposedInstall与android版本相对应?   |  [Xposed框架对应各系统版本的下载与卸载](http://xposed.appkg.com/1152.html)    --  -- [XposedInstall Github](https://github.com/rovo89/XposedInstaller/releases)    |  ok (目前2.6.1)
由于2.6.1以及以前的xposed版本源码是Eclipse, 需要转成AS项目  |[android studio 导入eclipse项目, 注意Jar包引用](https://www.jianshu.com/p/e96034f69dec)    | ok
添加开启和停止XposedInstaller程序的按钮及相应事件       |[adb shell 启动,停止android应用](http://blog.csdn.net/pingqingbo/article/details/20450951)        | ok
启动XposedInstaller 时隐藏Activity    | [android启动时隐藏Activity](https://www.jianshu.com/p/3afcaa959de2)  | ok
一键激活XposdInstaller | 修改XposedInstaller源码(CustomActivity.java)   | ok
给激活后的XposedInstaller添加宿主程序到Module中并激活宿主程序  | 修改XposedInstaller源码(CustomActivity.java)，现在宿主程序包名(com.example.eric.myapplication)    | ok
修改MainActivity.java，实现宿主程序后仅申请一次权限，xposedInstaller也是一次权限 |    初始化后只执行一次Runtime.getRuntime().exec("su");    | ok  
给宿主程序打包并安装到真机中 |     打包测试,需要购买真机    |  waiting
Android版本与XposedInstaller版本的适配   | 需要给不同的XposedInstaller重新编译。 | waiting
现在版本的XposedInstaller 报出 ： the latest version of xposed is currently not active.   | waiting 可能是最新版本的XposedInstaller可以安装在低版本上， 后续来看    | waiting
深入学习Xposed 开发     | 官网源码以及一些社区      | `ing`
添加一键卸载xposed程序后自动重启方法彻底清除XposedInstaller    |   coding |
编写一个申请权限的Demo, 向系统访问各种权限  | coding  |
调研android 权限，查看监控的权限方法    | 查看源码，或者相关书籍文献  |



