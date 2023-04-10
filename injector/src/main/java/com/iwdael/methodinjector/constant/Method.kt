package com.iwdael.methodinjector.constant

import org.objectweb.asm.Type

/**
 * @author : iwdael
 * @mail : iwdael@outlook.com
 * @project : https://github.com/iwdael/MethodInjector
 */
object Method {

    const val C_APPEND = "append"
    const val C_CURRENT_THREAD = "currentThread"
    const val C_GET_NAME = "getName"
    const val C_HASH_CODE = "hashCode"
    const val C_TO_STRING = "toString"
    const val C_INIT = "<init>"
    const val C_CURRENT_TIME_MILLIS = "currentTimeMillis"

    object TYPE {
        val OBJECT: Type = Type.getType(Any::class.java)
        val STRING: Type = Type.getType(String::class.java)
        val STRING_BUFFER: Type = Type.getType(StringBuffer::class.java)
        val CHAR_SEQUENCE: Type = Type.getType(CharSequence::class.java)
        val CHAR_ARRAY: Type = Type.getType(CharArray::class.java)

        val BOOLEAN: Type = Type.getType(Boolean::class.javaPrimitiveType)
        val CHAR: Type = Type.getType(Char::class.javaPrimitiveType)
        val BYTE: Type = Type.getType(Byte::class.javaPrimitiveType)
        val SHORT: Type = Type.getType(Short::class.javaPrimitiveType)

        val INT: Type = Type.getType(Int::class.javaPrimitiveType)
        val LONG: Type = Type.getType(Long::class.javaPrimitiveType)
        val FLOAT: Type = Type.getType(Float::class.javaPrimitiveType)
        val DOUBLE: Type = Type.getType(Double::class.javaPrimitiveType)
    }
}