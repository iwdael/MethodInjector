package com.iwdael.methodinjector.injector

import com.android.build.api.instrumentation.ClassContext
import com.iwdael.methodinjector.constant.Method
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
    val currentTime = newLocal(LONG_TYPE)
    val castTime = newLocal(LONG_TYPE)
    override fun onMethodEnter2() {
        if (Method.exclude.contains(name)) return
        if (!properties.enableCast) return
        mv.visitLabel(Label())
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false)
        mv.visitVarInsn(LSTORE, currentTime)
    }

    override fun onMethodExit(opcode: Int) {
        if (Method.exclude.contains(name)) return
        if (!properties.enableCast) return

        mv.visitLabel(Label())
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false)
        mv.visitVarInsn(LLOAD, currentTime)
        mv.visitInsn(LSUB)
        mv.visitVarInsn(LSTORE, castTime)

        mv.visitLabel(Label())
        mv.visitLdcInsn(properties.tagCast)
        mv.visitLdcInsn("--------------------------------------------------------------------------")
        mv.visitMethodInsn(INVOKESTATIC, "android/util/Log", properties.levelCast, "(Ljava/lang/String;Ljava/lang/String;)I", false)
        mv.visitInsn(POP)

        mv.visitLabel(Label())
        mv.visitLdcInsn(properties.tagCast)
        mv.visitTypeInsn(NEW, "java/lang/StringBuilder")
        mv.visitInsn(DUP)
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false)
        mv.visitLdcInsn("| thread: ")
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false)
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/Thread", "currentThread", "()Ljava/lang/Thread;", false)
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Thread", "getName", "()Ljava/lang/String;", false)
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false)
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false)
        mv.visitMethodInsn(INVOKESTATIC, "android/util/Log", properties.levelCast, "(Ljava/lang/String;Ljava/lang/String;)I", false)
        mv.visitInsn(POP)

        mv.visitLabel(Label())
        mv.visitLdcInsn(properties.tagCast)
        mv.visitLdcInsn("| method: ${className}.${name}(${simpleName}.java:${lineNumber})")
        mv.visitMethodInsn(INVOKESTATIC, "android/util/Log", properties.levelCast, "(Ljava/lang/String;Ljava/lang/String;)I", false)
        mv.visitInsn(POP)

        mv.visitLabel(Label())
        mv.visitLdcInsn(properties.tagCast)
        mv.visitTypeInsn(NEW, "java/lang/StringBuilder")
        mv.visitInsn(DUP)
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false)
        mv.visitLdcInsn("| cast  : ")
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false)
        mv.visitVarInsn(LLOAD, castTime)
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(J)Ljava/lang/StringBuilder;", false)
        mv.visitLdcInsn("ms")
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false)
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false)
        mv.visitMethodInsn(INVOKESTATIC, "android/util/Log", properties.levelCast, "(Ljava/lang/String;Ljava/lang/String;)I", false)
        mv.visitInsn(POP)


        argumentTypes.forEachIndexed { index, type ->
            mv.visitLabel(Label())
            mv.visitLdcInsn(properties.tagCast)
            mv.visitTypeInsn(NEW, "java/lang/StringBuilder")
            mv.visitInsn(DUP)
            mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false)
            mv.visitLdcInsn("| arg ${String.format("%2d", index)}: ")
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false)
            loadArg(index)
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(${getTypeDescriptor(type)})Ljava/lang/StringBuilder;", false)
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false)
            mv.visitMethodInsn(INVOKESTATIC, "android/util/Log", properties.levelCast, "(Ljava/lang/String;Ljava/lang/String;)I", false)
            mv.visitInsn(POP)
        }

        mv.visitLabel(Label())
        mv.visitLdcInsn(properties.tagCast)
        mv.visitLdcInsn("--------------------------------------------------------------------------")
        mv.visitMethodInsn(INVOKESTATIC, "android/util/Log", properties.levelCast, "(Ljava/lang/String;Ljava/lang/String;)I", false)
        mv.visitInsn(POP)
    }

}