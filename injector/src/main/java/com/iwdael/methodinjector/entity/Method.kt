package com.iwdael.methodinjector.entity


/**
 * @author  : iwdael
 * @mail    : iwdael@outlook.com
 * @project : https://github.com/iwdael/MethodInjector
 */
data class Method(val name: String?, var lineNumber: Int, val descriptor: String?, val args: MutableList<String> = mutableListOf())