package com.iwdael.methodinjector.utils

import com.android.build.api.instrumentation.ClassContext
import com.iwdael.methodinjector.properties.InstrumentationProperty
import com.iwdael.methodinjector.properties.Properties
import java.io.File

/**
 * @author  : iwdael
 * @mail    : iwdael@outlook.com
 * @project : https://github.com/iwdael/MethodInjector
 */


fun InstrumentationProperty.toProperty(): Properties {
    val properties = Properties(null)
    properties.sourceDir = sourceDir.get()
    properties.classChainMatcher = classChainMatcher.get()
    properties.methodChainMatcher = methodChainMatcher.get()
    properties.classCastMatcher = classCastMatcher.get()
    properties.methodCastMatcher = methodCastMatcher.get()
    properties.tagChain = tagChain.get()
    properties.tagCast = tagCast.get()
    properties.enableChain = enableChain.get()
    properties.enableCast = enableCast.get()
    properties.levelChain = checkLevel(levelChain.get())
    properties.levelCast = checkLevel(levelCast.get())
    return properties
}

fun InstrumentationProperty.containJava(classContext: ClassContext): Boolean {
    return sourceFiles.get().any { it.endsWith(classContext.currentClassData.className.replace(".", File.separator) + ".java") }
}


fun InstrumentationProperty.containKt(classContext: ClassContext): Boolean {
    return sourceFiles.get().any { it.endsWith(classContext.currentClassData.className.replace(".", File.separator) + ".kt") }
}

fun InstrumentationProperty.findSourceFile(classContext: ClassContext): File? {
    return sourceFiles.get()
        .filter {
            it.endsWith(classContext.currentClassData.className.replace(".", File.separator) + ".java") ||
            it.endsWith(classContext.currentClassData.className.replace(".", File.separator) + ".kt")
        }
        .map { File(it) }
        .firstOrNull()
}

private fun checkLevel(level: String): String {
    return if (level == "VERBOSE" || level == "DEBUG" || level == "INFO" || level == "WARN" || level == "ERROR") {
        "${level.toLowerCase().first()}"
    } else {
        "d"
    }
}