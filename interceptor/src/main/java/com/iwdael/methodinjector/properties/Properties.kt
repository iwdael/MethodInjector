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
    var classChainMatcher: String = ""
    var classCastMatcher: String = ""
    var methodChainMatcher: String = "^(?!.*<init>)(?!.*<clinit>).*\$"
    var methodCastMatcher: String = "^(?!.*<init>)(?!.*<clinit>).*\$"
    var tagChain: String = "method-injector"
    var tagCast: String = "method-injector"
    var levelChain: String = "debug"
    var levelCast: String = "debug"
    var enableChain = true
    var enableCast = true
    var debug: Boolean = false
}