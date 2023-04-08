package com.iwdael.methodinjector.injector

import com.android.build.api.instrumentation.ClassContext
import com.iwdael.methodinjector.constant.Class.C_LOG
import com.iwdael.methodinjector.constant.Class.C_STRING_BUILDER
import com.iwdael.methodinjector.constant.Class.C_SYSTEM
import com.iwdael.methodinjector.constant.Class.C_THREAD
import com.iwdael.methodinjector.constant.Log
import com.iwdael.methodinjector.constant.Method.C_APPEND
import com.iwdael.methodinjector.constant.Method.C_CURRENT_THREAD
import com.iwdael.methodinjector.constant.Method.C_CURRENT_TIME_MILLIS
import com.iwdael.methodinjector.constant.Method.C_GET_NAME
import com.iwdael.methodinjector.constant.Method.C_INIT
import com.iwdael.methodinjector.constant.Method.C_TO_STRING
import com.iwdael.methodinjector.properties.Properties
import org.objectweb.asm.Label
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Type.LONG_TYPE


/**
 * @author : iwdael
 * @mail : iwdael@outlook.com
 * @project : https://github.com/iwdael/MethodInjector
 */
class CastInjector constructor(classContext: ClassContext, api: Int, visitor: MethodVisitor, access: Int, name: String?, descriptor: String?, properties: Properties) : Injector(classContext, api, visitor, access, name, descriptor, properties) {
    private val currentTime = newLocal(LONG_TYPE)
    private val castTime = newLocal(LONG_TYPE)
    override fun onMethodEnter2() {
        if (!properties.enableCast) return
        if (properties.methodMatcher.isNotEmpty() && !name.matches(Regex(properties.methodMatcher))) return
        mv.visitLabel(Label())
        mv.visitMethodInsn(INVOKESTATIC, C_SYSTEM, C_CURRENT_TIME_MILLIS, "()J", false)
        mv.visitVarInsn(LSTORE, currentTime)
    }

    override fun onMethodExit(opcode: Int) {
        if (!properties.enableCast) return
        if (properties.methodMatcher.isNotEmpty() && !name.matches(Regex(properties.methodMatcher))) return

        mv.visitLabel(Label())
        mv.visitMethodInsn(INVOKESTATIC, C_SYSTEM, C_CURRENT_TIME_MILLIS, "()J", false)
        mv.visitVarInsn(LLOAD, currentTime)
        mv.visitInsn(LSUB)
        mv.visitVarInsn(LSTORE, castTime)

        mv.visitLabel(Label())
        mv.visitLdcInsn(properties.tagCast)
        mv.visitLdcInsn(Log.SQUARE_TOP)
        mv.visitMethodInsn(INVOKESTATIC, C_LOG, properties.levelCast, "(Ljava/lang/String;Ljava/lang/String;)I", false)
        mv.visitInsn(POP)

        mv.visitLabel(Label())
        mv.visitLdcInsn(properties.tagCast)
        mv.visitTypeInsn(NEW, C_STRING_BUILDER)
        mv.visitInsn(DUP)
        mv.visitMethodInsn(INVOKESPECIAL, C_STRING_BUILDER, C_INIT, "()V", false)
        mv.visitLdcInsn("${Log.SQUARE_LEFT} thread: ")
        mv.visitMethodInsn(INVOKEVIRTUAL, C_STRING_BUILDER, C_APPEND, "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false)
        mv.visitMethodInsn(INVOKESTATIC, C_THREAD, C_CURRENT_THREAD, "()Ljava/lang/Thread;", false)
        mv.visitMethodInsn(INVOKEVIRTUAL, C_THREAD, C_GET_NAME, "()Ljava/lang/String;", false)
        mv.visitMethodInsn(INVOKEVIRTUAL, C_STRING_BUILDER, C_APPEND, "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false)
        mv.visitMethodInsn(INVOKEVIRTUAL, C_STRING_BUILDER, C_TO_STRING, "()Ljava/lang/String;", false)
        mv.visitMethodInsn(INVOKESTATIC, C_LOG, properties.levelCast, "(Ljava/lang/String;Ljava/lang/String;)I", false)
        mv.visitInsn(POP)

        mv.visitLabel(Label())
        mv.visitLdcInsn(properties.tagCast)
        mv.visitLdcInsn(Log.SQUARE_MIDDLE)
        mv.visitMethodInsn(INVOKESTATIC, C_LOG, properties.levelCast, "(Ljava/lang/String;Ljava/lang/String;)I", false)
        mv.visitInsn(POP)


        mv.visitLabel(Label())
        mv.visitLdcInsn(properties.tagCast)
        mv.visitLdcInsn("${Log.SQUARE_LEFT} method: ${className}.${name}(${simpleName}.java:${lineNumber - 1})")
        mv.visitMethodInsn(INVOKESTATIC, C_LOG, properties.levelCast, "(Ljava/lang/String;Ljava/lang/String;)I", false)
        mv.visitInsn(POP)

        mv.visitLabel(Label())
        mv.visitLdcInsn(properties.tagCast)
        mv.visitTypeInsn(NEW, C_STRING_BUILDER)
        mv.visitInsn(DUP)
        mv.visitMethodInsn(INVOKESPECIAL, C_STRING_BUILDER, C_INIT, "()V", false)
        mv.visitLdcInsn("${Log.SQUARE_LEFT} cast  : ")
        mv.visitMethodInsn(INVOKEVIRTUAL, C_STRING_BUILDER, C_APPEND, "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false)
        mv.visitVarInsn(LLOAD, castTime)
        mv.visitMethodInsn(INVOKEVIRTUAL, C_STRING_BUILDER, C_APPEND, "(J)Ljava/lang/StringBuilder;", false)
        mv.visitLdcInsn("ms")
        mv.visitMethodInsn(INVOKEVIRTUAL, C_STRING_BUILDER, C_APPEND, "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false)
        mv.visitMethodInsn(INVOKEVIRTUAL, C_STRING_BUILDER, C_TO_STRING, "()Ljava/lang/String;", false)
        mv.visitMethodInsn(INVOKESTATIC, C_LOG, properties.levelCast, "(Ljava/lang/String;Ljava/lang/String;)I", false)
        mv.visitInsn(POP)


        argumentTypes.forEachIndexed { index, type ->
            mv.visitLabel(Label())
            mv.visitLdcInsn(properties.tagCast)
            mv.visitTypeInsn(NEW, C_STRING_BUILDER)
            mv.visitInsn(DUP)
            mv.visitMethodInsn(INVOKESPECIAL, C_STRING_BUILDER, C_INIT, "()V", false)
            mv.visitLdcInsn("${Log.SQUARE_LEFT} arg ${String.format("%2d", index)}: ")
            mv.visitMethodInsn(INVOKEVIRTUAL, C_STRING_BUILDER, C_APPEND, "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false)
            loadArg(index)
            mv.visitMethodInsn(INVOKEVIRTUAL, C_STRING_BUILDER, C_APPEND, "(${getTypeDescriptor(type)})Ljava/lang/StringBuilder;", false)
            mv.visitMethodInsn(INVOKEVIRTUAL, C_STRING_BUILDER, C_TO_STRING, "()Ljava/lang/String;", false)
            mv.visitMethodInsn(INVOKESTATIC, C_LOG, properties.levelCast, "(Ljava/lang/String;Ljava/lang/String;)I", false)
            mv.visitInsn(POP)
        }

        mv.visitLabel(Label())
        mv.visitLdcInsn(properties.tagCast)
        mv.visitLdcInsn(Log.SQUARE_BOTTOM)
        mv.visitMethodInsn(INVOKESTATIC, C_LOG, properties.levelCast, "(Ljava/lang/String;Ljava/lang/String;)I", false)
        mv.visitInsn(POP)
    }

}