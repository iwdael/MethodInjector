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
    var classMatcher: String = ""
    var classUnMatcher: String = ""

    var methodChainMatcher: String = ""
    var methodChainInternalMatcher: String = ""
    var methodChainUnMatcher: String = ""
    var methodChainInternalUnMatcher: String = "^(?!.*<init>)(?!.*<clinit>).*\\\$"

    var methodCostMatcher: String = ""
    var methodCostInternalMatcher: String = ""
    var methodCostUnMatcher: String = ""
    var methodCostInternalUnMatcher: String = "^(?!.*<init>)(?!.*<clinit>).*\\\$"
    var tagChain: String = "method-injector"
    var tagCost: String = "method-injector"
    var levelChain: String = "debug"
    var levelCost: String = "debug"
    var enableChain = true
    var enableCost = true
    var useEnglish = true
    var debug: Boolean = false
}