package com.iwdael.methodinjector.properties

import org.gradle.api.Project

/**
 * @author : iwdael
 * @mail : iwdael@outlook.com
 * @project : https://github.com/iwdael/MethodInjector
 */
open class Property constructor(project: Project?) {
    var classNameMatches: List<String>? = null
    var debug: Boolean = false
}