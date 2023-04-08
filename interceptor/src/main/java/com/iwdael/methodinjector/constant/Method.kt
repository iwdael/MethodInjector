package com.iwdael.methodinjector.constant

import org.objectweb.asm.Type

/**
 * @author : iwdael
 * @mail : iwdael@outlook.com
 * @project : https://github.com/iwdael/MethodInjector
 */
object Method {
    const val android_util_log = "android/util/Log"
    val exclude: List<String> = listOf("<init>", "<clinit>")

    object TYPE {
        val OBJECT: Type = Type.getType(Any::class.java)
        val STRING: Type = Type.getType(String::class.java)
        val STRING_BUFFER: Type = Type.getType(StringBuffer::class.java)
        val CHAR_SEQUENCE: Type = Type.getType(CharSequence::class.java)
        val CHAR_ARRAY: Type = Type.getType(CharArray::class.java)
        val BOOLEAN: Type = Type.getType(Boolean::class.javaPrimitiveType)
        val BOOLEAN_BOX: Type = Type.getType(Boolean::class.java)
        val CHAR: Type = Type.getType(Char::class.javaPrimitiveType)
        val CHAR_BOX: Type = Type.getType(Char::class.java)
        val INT: Type = Type.getType(Int::class.javaPrimitiveType)
        val INT_BOX: Type = Type.getType(Int::class.java)
        val LONG: Type = Type.getType(Long::class.javaPrimitiveType)
        val LONG_BOX: Type = Type.getType(Long::class.java)
        val FLOAT: Type = Type.getType(Float::class.javaPrimitiveType)
        val FLOAT_BOX: Type = Type.getType(Float::class.java)
        val DOUBLE: Type = Type.getType(Double::class.javaPrimitiveType)
        val DOUBLE_BOX: Type = Type.getType(Double::class.java)
    }
}