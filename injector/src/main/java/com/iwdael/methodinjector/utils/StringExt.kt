package com.iwdael.methodinjector.utils

/**
 * @author  : iwdael
 * @mail    : iwdael@outlook.com
 * @project : https://github.com/iwdael/MethodInjector
 */

fun String.matcher(pattern: String): Boolean {
    if (pattern.isEmpty()) return true
    return this.matches(Regex(pattern))
}

fun String.unMatcher(pattern: String): Boolean {
    if (pattern.isEmpty()) return false
    return this.matches(Regex(pattern))
}