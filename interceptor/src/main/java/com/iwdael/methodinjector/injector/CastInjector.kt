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
import com.iwdael.methodinjector.utils.visitReturn
import org.objectweb.asm.Label
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Type.LONG_TYPE
import java.io.File


/**
 * @author : iwdael
 * @mail : iwdael@outlook.com
 * @project : https://github.com/iwdael/MethodInjector
 */
class CastInjector constructor(classContext: ClassContext, api: Int, visitor: MethodVisitor, access: Int, name: String?, descriptor: String?, properties: Properties, sourceFile: File?) : Injector(classContext, api, visitor, access, name, descriptor, properties, sourceFile) {
    private val currentTime = newLocal(LONG_TYPE)
    private val castTime = newLocal(LONG_TYPE)
    override fun onMethodEnter2() {
        if (!properties.enableCast) return
        if (properties.methodCastMatcher.isNotEmpty() && !descriptor.matches(Regex(properties.methodCastMatcher))) return
        mv.visitLabel(Label())
        mv.visitMethodInsn(INVOKESTATIC, C_SYSTEM, C_CURRENT_TIME_MILLIS, "()J", false)
        mv.visitVarInsn(LSTORE, currentTime)
    }

    override fun onMethodExit(opcode: Int) {
        if (!properties.enableCast) return
        if (properties.methodCastMatcher.isNotEmpty() && !descriptor.matches(Regex(properties.methodCastMatcher))) return
        val isReturn = opcode in IRETURN until RETURN

        mv.visitLabel(Label())
        mv.visitMethodInsn(INVOKESTATIC, C_SYSTEM, C_CURRENT_TIME_MILLIS, "()J", false)
        mv.visitVarInsn(LLOAD, currentTime)
        mv.visitInsn(LSUB)
        mv.visitVarInsn(LSTORE, castTime)

        var returnLocal = 0
        if (isReturn) {
            mv.visitReturn(opcode, returnType)
            returnLocal = newLocal(returnType)
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/String", "valueOf", "(${getReturnStringValueOfDescriptor(returnType)})Ljava/lang/String;", false);
            mv.visitVarInsn(ASTORE, returnLocal)
        }
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
        mv.visitLdcInsn("${Log.SQUARE_LEFT} thread    : ")
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
        mv.visitLdcInsn("${Log.SQUARE_LEFT} method    : ${className}.${name}(${simpleName}.${sourceExtension}:${lineNumber - 1})")
        mv.visitMethodInsn(INVOKESTATIC, C_LOG, properties.levelCast, "(Ljava/lang/String;Ljava/lang/String;)I", false)
        mv.visitInsn(POP)


        mv.visitLabel(Label())
        mv.visitLdcInsn(properties.tagCast)
        mv.visitLdcInsn("${Log.SQUARE_LEFT} descriptor: $descriptor")
        mv.visitMethodInsn(INVOKESTATIC, C_LOG, properties.levelCast, "(Ljava/lang/String;Ljava/lang/String;)I", false)
        mv.visitInsn(POP)

        mv.visitLabel(Label())
        mv.visitLdcInsn(properties.tagCast)
        mv.visitTypeInsn(NEW, C_STRING_BUILDER)
        mv.visitInsn(DUP)
        mv.visitMethodInsn(INVOKESPECIAL, C_STRING_BUILDER, C_INIT, "()V", false)
        mv.visitLdcInsn("${Log.SQUARE_LEFT} cast      : ")
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
            mv.visitLdcInsn("${Log.SQUARE_LEFT} arg ${String.format("%6d", index)}: ")
            mv.visitMethodInsn(INVOKEVIRTUAL, C_STRING_BUILDER, C_APPEND, "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false)
            loadArg(index)
            mv.visitMethodInsn(INVOKEVIRTUAL, C_STRING_BUILDER, C_APPEND, "(${getTypeDescriptor(type)})Ljava/lang/StringBuilder;", false)
            mv.visitMethodInsn(INVOKEVIRTUAL, C_STRING_BUILDER, C_TO_STRING, "()Ljava/lang/String;", false)
            mv.visitMethodInsn(INVOKESTATIC, C_LOG, properties.levelCast, "(Ljava/lang/String;Ljava/lang/String;)I", false)
            mv.visitInsn(POP)
        }
        if (isReturn) {
            mv.visitLdcInsn(properties.tagCast)
            mv.visitTypeInsn(NEW, C_STRING_BUILDER)
            mv.visitInsn(DUP)
            mv.visitMethodInsn(INVOKESPECIAL, C_STRING_BUILDER, C_INIT, "()V", false)
            mv.visitLdcInsn("${Log.SQUARE_LEFT} return    : ")
            mv.visitMethodInsn(INVOKEVIRTUAL, C_STRING_BUILDER, C_APPEND, "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false)
            mv.visitVarInsn(ALOAD, returnLocal)
            mv.visitMethodInsn(INVOKEVIRTUAL, C_STRING_BUILDER, C_APPEND, "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false)
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
