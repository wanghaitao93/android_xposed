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
如何让xposedInstall与android版本相对应?   |  [Xposed框架对应各系统版本的下载与卸载](http://xposed.appkg.com/1152.html)    --  -- [XposedInstall Github](https://github.com/rovo89/XposedInstaller/releases)    |  ok (目前两个版本，2.6.1作为4.0到4.4使用，3.1.5方作为5.0以上使用)
由于2.6.1以及以前的xposed版本源码是Eclipse, 需要转成AS项目  |[android studio 导入eclipse项目, 注意Jar包引用](https://www.jianshu.com/p/e96034f69dec)    | ok
添加开启和停止XposedInstaller程序的按钮及相应事件       |[adb shell 启动,停止android应用](http://blog.csdn.net/pingqingbo/article/details/20450951)        | ok
启动XposedInstaller 时隐藏Activity    | [android启动时隐藏Activity](https://www.jianshu.com/p/3afcaa959de2)  | ok
一键激活XposdInstaller | 修改XposedInstaller源码(CustomActivity.java)   | ok
给激活后的XposedInstaller添加宿主程序到Module中并激活宿主程序  | 修改XposedInstaller源码(CustomActivity.java)，现在宿主程序包名(com.example.eric.myapplication)    | ok
给宿主程序打包并安装到真机中 |     打包测试    | `waiting`
Android版本与XposedInstaller版本的适配   | 需要给不同的XposedInstaller重新编译。 |
实现一键卸载Xposed与宿主程序    |   添加exeRootShellCmd方法参数 | ok
深入学习Xposed 开发     | 官网源码以及一些社区      |ok 
修改MainActivity.java，实现宿主程序后仅申请一次权限，xposedInstaller也是一次权限 |    初始化后只执行一次Runtime.getRuntime().exec("su");    | ok  
添加一键卸载xposed程序后自动重启    ||
编写一个申请权限的Demo  |  [详细请看](https://github.com/wanghaitao93/android_attack) | ok
统计android 设备 与 xposed 兼容 |  查看xposed官网 | OK
非Activity 类调用Activity类的方法 | [在非Activity类里面启动Intent、Toast等、非Activity类引用getResources()方法问题的解决方法](https://blog.csdn.net/qq_21856521/article/details/51810596)    | 由于无法使MainActivity初始化,所以context没有成功
实现成功安装完Xposed框架后删除SD卡中的临时APK文件 | File.delete()   | ok
区别系统应用与用户应用的方法，监控除了系统和自身APP的其他第三方app     |   | ok
添加java.lang.Runtime获取root权限检测方法|  修改Tutorial，添加hook方法  | ok
修改superUser获取数据库源码	| 查看SuperUser源码	| `ing`
监控android 数据 的类	| 调研数据权限类 	| ok
集成了两个版本的Xposed安装包到APP中，经过测试，实现了Xposed框架在Android4.0到Android7.0均可使用 | 通过Build.VERSION.SDK_INT < 21，去区分两种5.0以上还是以下用不同的XposedInstaller安装包	| ok
对于Xposed的hook到的 Runtime.getRuntime().exec()方法进行功能细化，区别申请root权限，文件修改权限、运行shell脚本权限、查看系统进程权限等功能 | [参考](https://github.com/devadvance/rootcloak/blob/master/app/src/main/java/com/devadvance/rootcloak2/RootCloak.java) | ok

