package com.iwdael.methodinjector.utils

import com.iwdael.methodinjector.constant.Method
import org.objectweb.asm.Type


/**
 * @author  : iwdael
 * @mail    : iwdael@outlook.com
 * @project : https://github.com/iwdael/MethodInjector
 */
internal val Type.appendDescriptor: String
    get() = when (this) {
        Method.TYPE.STRING,
        Method.TYPE.STRING_BUFFER,
        Method.TYPE.CHAR_SEQUENCE,
        Method.TYPE.CHAR_ARRAY,
        Method.TYPE.BOOLEAN,
        Method.TYPE.CHAR,
        Method.TYPE.INT,
        Method.TYPE.LONG,
        Method.TYPE.FLOAT,
        Method.TYPE.DOUBLE -> this
        Method.TYPE.BYTE,
        Method.TYPE.SHORT -> Method.TYPE.INT
        else -> Method.TYPE.OBJECT
    }.descriptor

internal val Type.valueOfDescriptor: String
    get() = when (this) {
        Method.TYPE.CHAR_ARRAY,
        Method.TYPE.BOOLEAN,
        Method.TYPE.CHAR,
        Method.TYPE.INT,
        Method.TYPE.LONG,
        Method.TYPE.FLOAT,
        Method.TYPE.DOUBLE -> this
        Method.TYPE.BYTE,
        Method.TYPE.SHORT -> Method.TYPE.INT
        else -> Method.TYPE.OBJECT
    }.descriptor
