package com.iwdael.methodinjector.utils

import java.io.File


/**
 * @author  : iwdael
 * @mail    : iwdael@outlook.com
 * @project : https://github.com/iwdael/MethodInjector
 */
object FileUtils {
    fun listFiles(dir: File, ext: Array<String>): MutableList<File> {
        return if (dir.isFile) {
            listOf(dir).filter { file -> ext.any { file.name.endsWith(".${it}") } }.toMutableList()
        } else {
            dir.listFiles()
                ?.flatMap { listFiles(it, ext) }
                ?.filter { file -> ext.any { file.name.endsWith(".${it}") } }
                ?.toMutableList() ?: mutableListOf()
        }
    }
}

fun fileNameMatched(fileName: String, clsName: String): Boolean {
    val className = clsName.replace(".", File.separator)
    if (fileName.endsWith("$className.kt") || fileName.endsWith("$className.java")) return true
    if (className.indexOf("$") < 0) return false
    val className2 = className.substring(0, className.indexOf("$"))
    if (fileName.endsWith("$className2.kt") || fileName.endsWith("$className2.java")) return true
    return false
}

val File?.isKt get() = this.toString().endsWith(".kt")