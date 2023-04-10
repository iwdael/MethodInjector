package com.iwdael.methodinjector.injector

import com.android.build.api.instrumentation.ClassContext
import com.iwdael.methodinjector.constant.Method
import com.iwdael.methodinjector.properties.Properties
import com.iwdael.methodinjector.utils.Logger
import org.objectweb.asm.Label
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Type
import org.objectweb.asm.commons.AdviceAdapter
import java.io.File


/**
 * @author  : iwdael
 * @mail    : iwdael@outlook.com
 * @project : https://github.com/iwdael/MethodInjector
 */
open class Injector(classContext: ClassContext, api: Int, methodVisitor: MethodVisitor?, access: Int, name: String?, descriptor: String?, val properties: Properties, val sourceFile: File?) : AdviceAdapter(api, methodVisitor, access, name, descriptor) {
    val className = classContext.currentClassData.className
    val simpleName = className.substring(className.lastIndexOf(".") + 1)
    val parameterNames = arrayOfNulls<String>(argumentTypes.size)
    val isStatic = (access and ACC_STATIC) == 0
    var lineNumber = 0
    val descriptor = "${name}${methodDesc}"
    val isKt = sourceFile?.name?.endsWith(".kt") ?: false
    val sourceExtension = if (isKt) "kt" else "java"
    private var isReceivedLineNumber = false

    override fun visitLineNumber(line: Int, start: Label?) {
        super.visitLineNumber(line, start)
        lineNumber = line - 1
        if (!isReceivedLineNumber) {
            isReceivedLineNumber = true
            onMethodEnter2()
        }
    }

    override fun visitLocalVariable(name: String?, descriptor: String?, signature: String?, start: Label?, end: Label?, index: Int) {
        super.visitLocalVariable(name, descriptor, signature, start, end, index)
        if (index < parameterNames.size) parameterNames[index] = name
    }

    open fun onMethodEnter2() {

    }

    override fun onMethodEnter() {
        super.onMethodEnter()
        Logger.log("method:${name}")
    }
protected fun getTypeDescriptor(type: Type): String {
    return when (type) {
        Method.TYPE.STRING,
        Method.TYPE.STRING_BUFFER,
        Method.TYPE.CHAR_SEQUENCE,
        Method.TYPE.CHAR_ARRAY,
        Method.TYPE.BOOLEAN,
        Method.TYPE.CHAR,
        Method.TYPE.INT,
        Method.TYPE.LONG,
        Method.TYPE.FLOAT,
        Method.TYPE.DOUBLE -> type
        else -> Method.TYPE.OBJECT
    }.descriptor
}
protected fun getReturnStringValueOfDescriptor(type: Type): String {
    return when (type) {
        Method.TYPE.CHAR_ARRAY,
        Method.TYPE.BOOLEAN,
        Method.TYPE.CHAR,
        Method.TYPE.INT,
        Method.TYPE.LONG,
        Method.TYPE.FLOAT,
        Method.TYPE.DOUBLE -> type
        else -> Method.TYPE.OBJECT
    }.descriptor
}
}
