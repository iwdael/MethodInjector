package com.iwdael.methodinjector.utils

import com.iwdael.methodinjector.properties.InstrumentationProperty
import com.iwdael.methodinjector.properties.Properties

/**
 * @author  : iwdael
 * @mail    : iwdael@outlook.com
 * @project : https://github.com/iwdael/MethodInjector
 */


fun InstrumentationProperty.toProperty(): Properties {
    val properties = Properties(null)
    properties.classNameMatches = classNameMatches.get()
    properties.tagChain = tagChain.get()
    properties.tagCast = tagCast.get()
    properties.enableChain = enableChain.get()
    properties.enableCast = enableCast.get()
    properties.levelChain = checkLevel(levelChain.get())
    properties.levelCast = checkLevel(levelCast.get())
    return properties
}

private fun checkLevel(level: String): String {
    return if (level == "VERBOSE" || level == "DEBUG" || level == "INFO" || level == "WARN" || level == "ERROR") {
        "${level.toLowerCase().first()}"
    } else {
        "d"
    }
}