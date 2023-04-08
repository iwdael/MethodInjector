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

val File?.isKt get() = this.toString().endsWith(".kt")