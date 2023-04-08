package com.iwdael.methodinjector.injector

import com.android.build.api.instrumentation.ClassContext
import com.iwdael.methodinjector.entity.Method
import com.iwdael.methodinjector.properties.Properties
import org.objectweb.asm.Label
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.commons.AdviceAdapter


/**
 * @author  : iwdael
 * @mail    : iwdael@outlook.com
 * @project : https://github.com/iwdael/MethodInjector
 */
abstract class Injector(classContext: ClassContext, api: Int, methodVisitor: MethodVisitor?, access: Int, name: String?, val descriptor: String, val properties: Properties, val fileName: String, val method: Method) : AdviceAdapter(api, methodVisitor, access, name, descriptor) {
    val className = classContext.currentClassData.className
    val simpleName = className.substring(className.lastIndexOf(".") + 1)
    val methodName = "${className}.${name}${descriptor}"
    val isStatic = (access and ACC_STATIC) != 0
    var curLineNumber = 0

    abstract fun intercept(): Boolean
    override fun visitLineNumber(line: Int, start: Label?) {
        super.visitLineNumber(line, start)
        curLineNumber = line
    }
}
