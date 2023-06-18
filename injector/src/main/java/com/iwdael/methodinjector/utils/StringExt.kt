package com.iwdael.methodinjector.utils

/**
 * @author  : iwdael
 * @mail    : iwdael@outlook.com
 * @project : https://github.com/iwdael/MethodInjector
 */

fun String.matcher(pattern: Any): Boolean {
    if (pattern is String) return this.matcher(pattern)
    if (pattern is List<*>) return this.matcher(pattern as List<String>)
    throw RuntimeException("matcher is not String or List<String>")
}

fun String.matcher(pattern: String): Boolean {
    if (pattern.isEmpty()) return true
    return this.matches(Regex(pattern))
}

fun String.matcher(pattern: List<String>): Boolean {
    for (match in pattern) {
        if (this.contains(match)) return true
    }
    return false
}

fun String.matcher2(pattern: Any): Boolean {
    if (pattern is String) return this.matcher2(pattern)
    if (pattern is List<*>) return this.matcher2(pattern as List<String>)
    throw RuntimeException("matcher is not String or List<String>")
}

fun String.matcher2(pattern: String): Boolean {
    if (pattern.isEmpty()) return false
    return this.matches(Regex(pattern))
}

fun String.matcher2(pattern: List<String>): Boolean {
    for (match in pattern) {
        if (this.contains(match)) return true
    }
    return false
}