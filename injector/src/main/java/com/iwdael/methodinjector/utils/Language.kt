package com.iwdael.methodinjector.utils

import com.iwdael.methodinjector.entity.Method


/**
 * @author  : iwdael
 * @mail    : iwdael@outlook.com
 * @project : https://github.com/iwdael/MethodInjector
 */
object Language {
    const val CLASS = "class"
    const val METHOD = "method"
    const val THREAD = "thread"
    const val DESCRIPTOR = "descriptor"
    const val COST = "cost"
    const val ARG = "arg"
    const val RETURN = "return"
    const val HASHCODE = "hashCode"

    private val chinese = mapOf(
        CLASS to "类名",
        METHOD to "方法",
        THREAD to "线程",
        DESCRIPTOR to "签名",
        COST to "耗时",
        ARG to "参数",
        RETURN to "返回",
        HASHCODE to "哈希",
    )
    private val english = mapOf(
        CLASS to CLASS,
        METHOD to METHOD,
        THREAD to THREAD,
        DESCRIPTOR to DESCRIPTOR,
        COST to COST,
        ARG to ARG,
        RETURN to RETURN,
        HASHCODE to HASHCODE
    )

    fun get(useEnglish: Boolean, key: String): String {
        return if (useEnglish) english[key]!! else chinese[key]!!
    }

    fun offset(useEnglish: Boolean, vararg args: Method): Int {
        val offset1 = if (useEnglish) {
            english.values.maxByOrNull { it.length }?.length ?: 0
        } else {
            chinese.values.maxByOrNull { it.length }?.length ?: 0
        }
        val offset2 = (args.flatMap { it.args }.maxByOrNull { it.length }?.length ?: 0) + if (useEnglish) english[ARG]!!.length else chinese[ARG]!!.length
        return maxOf(offset1, offset2 + 2)
    }
}