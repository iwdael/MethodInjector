package com.iwdael.methodinjector.injector

import com.android.build.api.instrumentation.ClassContext
import com.iwdael.methodinjector.constant.Class.C_LOG
import com.iwdael.methodinjector.constant.Class.C_OBJECT
import com.iwdael.methodinjector.constant.Class.C_STRING_BUILDER
import com.iwdael.methodinjector.constant.Class.C_THREAD
import com.iwdael.methodinjector.constant.Method.C_APPEND
import com.iwdael.methodinjector.constant.Method.C_CURRENT_THREAD
import com.iwdael.methodinjector.constant.Method.C_GET_NAME
import com.iwdael.methodinjector.constant.Method.C_HASH_CODE
import com.iwdael.methodinjector.constant.Method.C_INIT
import com.iwdael.methodinjector.constant.Method.C_TO_STRING
import com.iwdael.methodinjector.properties.Properties
import org.objectweb.asm.Label
import org.objectweb.asm.MethodVisitor
import java.io.File


/**
 * @author : iwdael
 * @mail : iwdael@outlook.com
 * @project : https://github.com/iwdael/MethodInjector
 */
class ChainInjector constructor(classContext: ClassContext, api: Int, visitor: MethodVisitor, access: Int, name: String?, descriptor: String?, properties: Properties, sourceFile: File?) : Injector(classContext, api, visitor, access, name, descriptor, properties, sourceFile) {

    override fun onMethodEnter2() {
        if (!properties.enableChain) return
        if (properties.methodChainMatcher.isNotEmpty() && !descriptor.matches(Regex(properties.methodChainMatcher))) return
        mv.visitLabel(Label())
        mv.visitLdcInsn(properties.tagChain)
        mv.visitTypeInsn(NEW, C_STRING_BUILDER)
        mv.visitInsn(DUP)
        mv.visitMethodInsn(INVOKESPECIAL, C_STRING_BUILDER, C_INIT, "()V", false)
        mv.visitLdcInsn("thread[")
        mv.visitMethodInsn(INVOKEVIRTUAL, C_STRING_BUILDER, C_APPEND, "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false)
        mv.visitMethodInsn(INVOKESTATIC, C_THREAD, C_CURRENT_THREAD, "()Ljava/lang/Thread;", false)
        mv.visitMethodInsn(INVOKEVIRTUAL, C_THREAD, C_GET_NAME, "()Ljava/lang/String;", false)
        mv.visitMethodInsn(INVOKEVIRTUAL, C_STRING_BUILDER, C_APPEND, "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false)
        if (argumentTypes.isEmpty()) mv.visitLdcInsn("] ${className}.${name}(${simpleName}.java:${lineNumber})")
        else mv.visitLdcInsn("] ${className}.${name}(${simpleName}.${sourceExtension}:${lineNumber}) arg0:")
        mv.visitMethodInsn(INVOKEVIRTUAL, C_STRING_BUILDER, C_APPEND, "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false)
        argumentTypes.forEachIndexed { index, type ->
            if (index == 0) {
                loadArg(0)
                mv.visitMethodInsn(INVOKEVIRTUAL, C_STRING_BUILDER, C_APPEND, "(${getTypeDescriptor(type)})Ljava/lang/StringBuilder;", false)
            } else {
                mv.visitLdcInsn(" arg${index}:")
                mv.visitMethodInsn(INVOKEVIRTUAL, C_STRING_BUILDER, C_APPEND, "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false)
                loadArg(index)
                mv.visitMethodInsn(INVOKEVIRTUAL, C_STRING_BUILDER, C_APPEND, "(${getTypeDescriptor(type)})Ljava/lang/StringBuilder;", false)
            }
        }
        if (isStatic) {
            mv.visitLdcInsn(" on hashCode(")
            mv.visitMethodInsn(INVOKEVIRTUAL, C_STRING_BUILDER, C_APPEND, "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false)
            loadThis()
            mv.visitMethodInsn(INVOKEVIRTUAL, C_OBJECT, C_HASH_CODE, "()I", false)
            mv.visitMethodInsn(INVOKEVIRTUAL, C_STRING_BUILDER, C_APPEND, "(I)Ljava/lang/StringBuilder;", false)
            mv.visitLdcInsn(")")
            mv.visitMethodInsn(INVOKEVIRTUAL, C_STRING_BUILDER, C_APPEND, "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false)
        }
        mv.visitMethodInsn(INVOKEVIRTUAL, C_STRING_BUILDER, C_TO_STRING, "()Ljava/lang/String;", false)
        mv.visitMethodInsn(INVOKESTATIC, C_LOG, properties.levelChain, "(Ljava/lang/String;Ljava/lang/String;)I", false)
        mv.visitInsn(POP)
    }
}