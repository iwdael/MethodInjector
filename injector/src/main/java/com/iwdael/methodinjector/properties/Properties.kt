package com.iwdael.methodinjector.properties

import org.gradle.api.Project
import java.io.File

/**
 * @author : iwdael
 * @mail : iwdael@outlook.com
 * @project : https://github.com/iwdael/MethodInjector
 */
open class Properties constructor(project: Project?) {
    var sourceDir: String = "src${File.separator}main${File.separator}"
    var classMatcher: Any = ""
    var classUnMatcher: Any = ""

    var methodChainMatcher: Any = ""
    var methodChainInternalMatcher: Any = ""
    var methodChainUnMatcher: Any = ""
    var methodChainInternalUnMatcher: Any = "<init>|<clinit>"

    var methodCostMatcher: Any = ""
    var methodCostInternalMatcher: Any = ""
    var methodCostUnMatcher: Any = ""
    var methodCostInternalUnMatcher: Any = "<init>|<clinit>"
    var tagChain: String = "method-injector"
    var tagCost: String = "method-injector"
    var levelChain: String = "debug"
    var levelCost: String = "debug"
    var simpleChain = false
    var simpleCost = false
    var enableChain = true
    var enableCost = true
    var useEnglish = true
    var debug: Boolean = false
}