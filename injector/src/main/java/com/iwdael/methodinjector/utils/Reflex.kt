package com.iwdael.methodinjector.utils

/**
 * @author  : iwdael
 * @mail    : iwdael@outlook.com
 * @project : https://github.com/iwdael/MethodInjector
 */
fun Any.get(field: String): Any = this::class.java.get(this, field)

fun Class<*>.get(obj: Any, field: String): Any {
    return try {
        val tClass = this
        val target = tClass.getDeclaredField(field)
            .apply { isAccessible = true }
            .get(obj)
        target
    } catch (e: Exception) {
        if (superclass == Any::class.java) {
            throw RuntimeException()
        }
        superclass.get(obj, field)
    }
}
