在 Google I/O 2017 大会上，Google 推出了新的组件库 - Android Architecture Components，官方文档做的很详细，分别介绍了各个组件的应用场景和使用方式，这无疑会推进 Android MVVM 模式的发展。关于 MVVM 这里不做过多介绍，如果有需要可以自行搜索。

正文
Android Architecture Components 简介
先来看一下 Android Architecture Components 包含哪些内容：

Handling Lifecycles

LiveData

ViewModel

Room Persistence Library

加上之前推出的 Data Binding Library

Data Binding Library
如果想要引用 Components，可参考以下链接：

Adding Components to your Project
注意：如果你无法访问 Google Maven 库，可以使用以下地址代替：
maven { url "https://dl.google.com/dl/android/maven2" }

这里需要特别说明的是，ViewModel 相当于 MVVM 的 VM 层，它和 View 是通过 Data Binding 双向绑定的，ViewModel 不再持有 View 的引用，而是存储UI相关的数据；而且 ViewModel 是生命周期感知的，当创建它的 Activity/Fragment 销毁时，ViewModel 会自动销毁。

MVVMArms 框架图
这里引用 官方 架构图，参考 MVPArms 做了一些修改。 MVVMArms _Architecture

整个框架采用 Dagger 进行依赖注入，并使用了最新的 Dagger.Android，组织好 Module 和 Components，框架会自动注入。具体可参考 demo。

MVVM 组件的封装
Model
框架中，数据的获取是通过 Model 层获取的，在 Model 中持有一个数据管理类对象- RepositoryManager（实现了 IRepositoryManager 接口），分别可以通过 Retrofit 获取网络数据，通过 Room 获取 SQLite 数据库中的持久化数据。具体实现可查看源码链接：

BaseModel
RepositoryManager
Room 的使用需要在 gradle 中添加：

android {
...

    //Room
    javaCompileOptions {
        annotationProcessorOptions {
            arguments = ["room.schemaLocation":
                                 "$projectDir/schemas".toString()]
        }
    }
}
ViewModel
ViewModel 是整个框架比较新颖和重要的部分，BaseViewModel 继承于 Components 中的 AndroidViewModel，并实现了 LifecycleObserver 接口；它持有 Model 的引用，主要的业务逻辑都是在 ViewModel 中实现的；ViewModel 中UI相关的数据通过 LiveData 包装，使得当数据变化时，自动反映到UI上，实现了数据UI。在 xml 布局文件中，通过 Data Binding 绑定对应的 ViewModel。 BaseViewModel 源码如下：

BaseViewModel
View
框架封装了 ArmsActivity 和 ArmsFragment，它们都实现了 Components 中的 LifecycleRegistryOwner 接口来处理生命周期。上面提到在 xml 布局中绑定 ViewModel，而 ViewModel 的创建和生命周期绑定是在 Activity/Fragment 中进行的，具体是通过 Components 中提供的 ViewModelProviders 类完成的。下面是创建 ViewModel 的实例：

//创建ViewModel
mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(WeatherViewModel.class);
mViewModel 是通过泛型封装在 ArmsActivity 和 ArmsFragment 中的；
同样，mViewModelFactory 是通过 Dagger 将 ViewModelFactory 注入在 ArmsActivity 和 ArmsFragment 中的。
ViewModelProviders.of() 是一个重载的方法，通过传入的第一个参数，限定该 ViewModel 与 哪个 Activity/Fragment 进行生命周期绑定。
创建好了 ViewModel 后，在 ArmsActivity 和 ArmsFragment 设置 ViewModel 监听 Lifecyle 的回调：

if (mViewModel != null)
    getLifecycle().addObserver((LifecycleObserver) mViewModel);
同时，需要将上述提到的 xml 中绑定的 ViewModel 进行设置，这就很简单了，示例代码如下：

mBinding.setViewModel(mViewModel);

以下引用谷歌开发者微信公众号的介绍：

Lifecycles: 每个 Android 开发者都应该面对过生命周期问题，即操作系统启动、停止和销毁 Activity。 这意味着开发者需要根据生命周期的不同阶段，有针对性地管理组件状态，比如用于更新用户界面的可观察对象。 生命周期管理（Lifecycles）帮助开发者创建 “可感知生命周期的” 组件，让其自己管理自己的生命周期， 从而减少内存泄露和崩溃的可能性。生命周期库是其他架构组件（如 LiveData）的基础。

LiveData: LiveData 是一款基于观察者模式的可感知生命周期的核心组件。 LiveData 为界面代码 （Observer）的监视对象 （Observable），当 LiveData 所持有的数据改变时， 它会通知相应的界面代码进行更新。同时，LiveData 持有界面代码 Lifecycle 的引用， 这意味着它会在界面代码（LifecycleOwner）的生命周期处于 started 或 resumed 时作出相应更新， 而在 LifecycleOwner 被销毁时停止更新。通过 LiveData，开发者可以方便地构建安全性更高、性能更好的高响应度用户界面。

ViewModel: ViewModel 将视图的数据和逻辑从具有生命周期特性的实体（如 Activity 和 Fragment）中剥离开来。 直到关联的 Activity 或 Fragment 完全销毁时，ViewModel 才会随之消失， 也就是说，即使在旋转屏幕导致 Fragment 被重新创建等事件中，视图数据依旧会被保留。 ViewModels 不仅消除了常见的生命周期问题，而且可以帮助构建更为模块化、更方便测试的用户界面。

Room: 几乎所有 App 都需要在本地储存数据。 自从最早版本开始，Android 就提供了 SQLite，但是直接使用 SQLite是件苦差事。 因此，我们推出了 Room，一款简单好用的对象映射层。它和 SQLite 有一样强大的功能，但是节省了很多重复编码的麻烦事儿。 它的一些功能，如编译时的数据查询验证、内置迁移支持等，让开发者能够更简单地构建健壮的持久层。 而且 Room 可以和 LiveData 集成在一起，提供可观测数据库并感知生命周期的对象。 Room 集简洁、强大和可靠性为一身，在管理本地储存上表现卓越，我们强烈推荐各位开发者试用一下。

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