# MethodInjector

![](https://img.shields.io/badge/platform-android-orange.svg)
![](https://img.shields.io/badge/language-kotlin-yellow.svg)
![](https://jitpack.io/v/com.iwdael/methodinjector.svg)
![](https://img.shields.io/badge/build-passing-brightgreen.svg)
![](https://img.shields.io/badge/license-apache--2.0-green.svg)
![](https://img.shields.io/badge/gradle-7+-green.svg)

MethodInjector是一个gradle plugin，当插件启用时，会在Apk打包流程中，在方法进入或退出的地方添加日志。

## 特点

* 统计方法耗时
* 避免代码入侵
* 记录入参的类型与值
* 记录出参的类型与值

## 使用说明

需要植入代码的App Module中，在build.gradle中添加插件配置

```
methodInjector {
    debug = true
    classMatcher = "com\\.iwdael(.*)"
    tagChain = "chain-inject"
    tagCost = "cast-inject"
    levelChain = "ERROR"
    levelCost = "WARN"
    useEnglish = false
}
```

|          属性          | 必须 |    功能     |       默认值       |
|:--------------------:|:--:|:---------:|:---------------:|
|      sourceDir       |  否 |  源码目录       |     src/main/   |
|      useEnglish      |  否 |  日志是否为英文       |     true   |
|     classMatcher     |  是 |  正则匹配目标类  |       all       |
|    classUnMatcher    |  是 |  正则过滤目标类  |      none       |
|  methodChainMatcher  |  否 | 正则匹配目标方法  |       all       |
| methodChainUnMatcher |  否 | 正则过滤目标方法  |      none       |
|       tagChain       |  否 |   日志TAG   | method-injector |
|      levelChain      |  否 |   日志等级    |      DEBUG      |
|     enableChain      |  否 | 是否启用Chain |      true       |
|  methodCostMatcher   |  否 | 正则匹配目标方法  |       all       |
| methodCostUnMatcher  |  否 | 正则过滤目标方法  |      none       |
|       tagCost        |  否 |   日志TAG   | method-injector |
|      levelCost       |  否 |   日志等级    |      DEBUG      |
|      enableCost      |  否 | 是否启用Chain |      true       |

## 快速引入项目

setting.gradle中添加maven
```
pluginManagement {
    repositories {
        maven { url 'https://www.jitpack.io' }
    }
}
```

rootProject build.gradle
```
buildscript {
    dependencies {
        classpath 'com.iwdael:methodinjector:version'
    }
}
```

app module build.gradle
```
apply plugin: "com.iwdael.methodinjector"
```