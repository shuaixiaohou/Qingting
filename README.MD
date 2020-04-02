* 适配AndroidX
* 使用BackgroundLibrary库代替所有shape资源,请无视xml文件中app:bl_xxxx_xxxx="xxxx"错误
* 引入databinding,减少findViewById使用
* 用户登陆
* 友盟分享
* 布局优化,避免过渡绘制
* 完善状态管理,提升用户体验
* 声音批量下载
* 语音搜索
* 专辑订阅
* 声音喜欢
* 优化启动速度
### 主要功能(包括不仅限于):
* 专辑展示
* 声音展示及播放
* 电台展示及播放
* 主播展示
* 声音下载
* 历史播放展示及播放
### 项目架构
本项目采用retrofit+rxjava2+rxandroid+arouter+mvvm+fragmentation实现单activity多fragme组件化架构
* app：负责管理各个业务组件，和打包apk，没有具体的业务功能；
* lib_third：负责第三方库的集成和初始化;
* lib_common：属于功能组件，支撑业务组件的基础，提供多数业务组件需要的功能;
* module_main：属于业务组件，指定APP启动页面、主界面；
* module_home：首页展示,包括热门,分类,精品,主播,电台,展示及播放等；
* module_listen：我听,包括订阅,喜欢,播放历史,下载等；
* module_discover：发现,包括后续扩展功能等；
* module_user：用户管理模块;
### 组件化实现：
倾听使用阿里ARouter作为路由，实现组件与组件的通信跳转
### 集成模式和组件模式转换
Module的属性是在每个组件的 build.gradle 文件中配置的，当我们在组件模式开发时，业务组件应处于application属性，这时的业务组件就是一个 Android App，可以独立开发和调试；而当我们转换到集成模式开发时，业务组件应该处于 library 属性，这样才能被我们的“app壳工程”所依赖，组成一个具有完整功能的APP
先打开工程的根目录下找到gradle.properties 文件，然后将 isModule 改为你需要的开发模式（true/false）， 然后点击 "Sync Project" 按钮同步项目
```
isModule=false
```
```
if (isModule.toBoolean()) {
    apply plugin: 'com.android.application'
} else {
    apply plugin: 'com.android.library'
}
```
### 组件之间AndroidManifest合并问题
我们可以为组件开发模式下的业务组件再创建一个 AndroidManifest.xml，然后根据isModule指定AndroidManifest.xml的文件路径，让业务组件在集成模式和组件模式下使用不同的AndroidManifest.xml，这样表单冲突的问题就可以规避了
已module_main组件为例配置如下：
```
sourceSets {
        main {
            if (isModule.toBoolean()) {
                manifest.srcFile 'src/main/module/AndroidManifest.xml'
            } else {
                manifest.srcFile 'src/main/AndroidManifest.xml'
            }
        }
}
```
### 主要用到的开源库
* 快速切面编程开源库 
* 喜马拉雅SDK http://open.ximalaya.com/
* fragmentation https://github.com/YoKeyword/Fragmentation
* okhttp https://github.com/square/okhttp 
* retrofit https://github.com/square/retrofit
* rxpermissions 权限 https://github.com/tbruyelle/RxPermissions
* BaseRecyclerViewAdapterHelper https://github.com/CymChad/BaseRecyclerViewAdapterHelper
* loadsir 状态管理 https://github.com/KingJA/LoadSir
* lottie动画 https://github.com/airbnb/lottie-android
* SmartRefreshLayout https://github.com/scwang90/SmartRefreshLayout
* MagicIndicator https://github.com/hackware1993/MagicIndicator
* shape神器BackgroundLibrary https://github.com/JavaNoober/BackgroundLibrary
* 感谢所有开源库的大佬
* mvvm借鉴 https://github.com/geduo83/FlyTour 
* 原型平台 https://www.xiaopiu.com/
* 快速切面编程开源库
* 骨架屏弹性块 
* RxPersistence是基于面向对象设计的快速持久化框架 
* greenDao Converter自动生成器 