package com.iwdael.methodinjector.injector

import com.android.build.api.instrumentation.ClassContext
import com.iwdael.methodinjector.constant.Class
import com.iwdael.methodinjector.constant.Class.C_LOG
import com.iwdael.methodinjector.constant.Class.C_STRING_BUILDER
import com.iwdael.methodinjector.constant.Class.C_SYSTEM
import com.iwdael.methodinjector.constant.Class.C_THREAD
import com.iwdael.methodinjector.constant.Log
import com.iwdael.methodinjector.constant.Method.C_APPEND
import com.iwdael.methodinjector.constant.Method.C_CURRENT_THREAD
import com.iwdael.methodinjector.constant.Method.C_CURRENT_TIME_MILLIS
import com.iwdael.methodinjector.constant.Method.C_GET_NAME
import com.iwdael.methodinjector.constant.Method.C_HASH_CODE
import com.iwdael.methodinjector.constant.Method.C_INIT
import com.iwdael.methodinjector.constant.Method.C_TO_STRING
import com.iwdael.methodinjector.entity.Method
import com.iwdael.methodinjector.properties.Properties
import com.iwdael.methodinjector.utils.*
import org.objectweb.asm.Label
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Type.LONG_TYPE


/**
 * @author : iwdael
 * @mail : iwdael@outlook.com
 * @project : https://github.com/iwdael/MethodInjector
 */
class CostInjector constructor(classContext: ClassContext, api: Int, visitor: MethodVisitor, access: Int, name: String, descriptor: String, properties: Properties, fileName: String, method: Method) : Injector(classContext, api, visitor, access, name, descriptor, properties, fileName, method) {
    private val currentTime = newLocal(LONG_TYPE)
    private val castTime = newLocal(LONG_TYPE)

    override fun intercept(): Boolean {
        if (!properties.enableCost) return false
        return name.matcher(properties.methodCostInternalMatcher) &&
                name.matcher(properties.methodCostMatcher) &&
                !name.matcher2(properties.methodCostInternalUnMatcher) &&
                !name.matcher2(properties.methodCostUnMatcher) &&
                (method.argc == method.args.size).apply { if (!this) Logger.error("method:${methodName} args:${method.args}") }
    }

    override fun onMethodEnter() {
        if (!intercept()) return
        mv.visitLabel(Label())
        mv.visitMethodInsn(INVOKESTATIC, C_SYSTEM, C_CURRENT_TIME_MILLIS, "()J", false)
        mv.visitVarInsn(LSTORE, currentTime)
    }

    override fun onMethodExit(opcode: Int) {
        if (!intercept()) return
        val attrOffset = Language.offset(properties.useEnglish, method)
        val header = "%-${attrOffset}s"
        val isReturn = opcode in IRETURN until RETURN

        mv.visitLabel(Label())
        mv.visitMethodInsn(INVOKESTATIC, C_SYSTEM, C_CURRENT_TIME_MILLIS, "()J", false)
        mv.visitVarInsn(LLOAD, currentTime)
        mv.visitInsn(LSUB)
        mv.visitVarInsn(LSTORE, castTime)

        var returnLocal = 0
        if (isReturn) {
            visitReturn(opcode)
            returnLocal = newLocal(returnType)
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/String", "valueOf", "(${returnType.valueOfDescriptor})Ljava/lang/String;", false);
            mv.visitVarInsn(ASTORE, returnLocal)
        }
        mv.visitLabel(Label())
        mv.visitLdcInsn(properties.tagCost)
        mv.visitLdcInsn(Log.SQUARE_TOP)
        mv.visitMethodInsn(INVOKESTATIC, C_LOG, properties.levelCost, "(Ljava/lang/String;Ljava/lang/String;)I", false)
        mv.visitInsn(POP)


        mv.visitLabel(Label())
        mv.visitLdcInsn(properties.tagCost)
        mv.visitLdcInsn("${Log.SQUARE_LEFT} ${String.format(header, Language.get(properties.useEnglish, Language.CLASS))}: $className")
        mv.visitMethodInsn(INVOKESTATIC, C_LOG, properties.levelCost, "(Ljava/lang/String;Ljava/lang/String;)I", false)
        mv.visitInsn(POP)
        if (!properties.simpleCost) {
            mv.visitLabel(Label())
            mv.visitLdcInsn(properties.tagCost)
            mv.visitTypeInsn(NEW, C_STRING_BUILDER)
            mv.visitInsn(DUP)
            mv.visitMethodInsn(INVOKESPECIAL, C_STRING_BUILDER, C_INIT, "()V", false)
            mv.visitLdcInsn("${Log.SQUARE_LEFT} ${String.format(header, Language.get(properties.useEnglish, Language.THREAD))}: ")
            mv.visitMethodInsn(INVOKEVIRTUAL, C_STRING_BUILDER, C_APPEND, "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false)
            mv.visitMethodInsn(INVOKESTATIC, C_THREAD, C_CURRENT_THREAD, "()Ljava/lang/Thread;", false)
            mv.visitMethodInsn(INVOKEVIRTUAL, C_THREAD, C_GET_NAME, "()Ljava/lang/String;", false)
            mv.visitMethodInsn(INVOKEVIRTUAL, C_STRING_BUILDER, C_APPEND, "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false)
            mv.visitMethodInsn(INVOKEVIRTUAL, C_STRING_BUILDER, C_TO_STRING, "()Ljava/lang/String;", false)
            mv.visitMethodInsn(INVOKESTATIC, C_LOG, properties.levelCost, "(Ljava/lang/String;Ljava/lang/String;)I", false)
            mv.visitInsn(POP)
        }

        if (!isStatic && name != "<init>" && name != "<clinit>" && !properties.simpleCost) {
            mv.visitLdcInsn(properties.tagCost)
            mv.visitTypeInsn(NEW, C_STRING_BUILDER)
            mv.visitInsn(DUP)
            mv.visitMethodInsn(INVOKESPECIAL, C_STRING_BUILDER, C_INIT, "()V", false)
            mv.visitLdcInsn("${Log.SQUARE_LEFT} ${String.format(header, Language.get(properties.useEnglish, Language.HASHCODE))}: ")
            mv.visitMethodInsn(INVOKEVIRTUAL, C_STRING_BUILDER, C_APPEND, "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false)
            loadThis()
            mv.visitMethodInsn(INVOKEVIRTUAL, Class.C_OBJECT, C_HASH_CODE, "()I", false)
            mv.visitMethodInsn(INVOKEVIRTUAL, C_STRING_BUILDER, C_APPEND, "(I)Ljava/lang/StringBuilder;", false)
            mv.visitMethodInsn(INVOKEVIRTUAL, C_STRING_BUILDER, C_TO_STRING, "()Ljava/lang/String;", false)
            mv.visitMethodInsn(INVOKESTATIC, C_LOG, properties.levelCost, "(Ljava/lang/String;Ljava/lang/String;)I", false)
            mv.visitInsn(POP)
        }

        mv.visitLabel(Label())
        mv.visitLdcInsn(properties.tagCost)
        mv.visitLdcInsn(Log.SQUARE_MIDDLE)
        mv.visitMethodInsn(INVOKESTATIC, C_LOG, properties.levelCost, "(Ljava/lang/String;Ljava/lang/String;)I", false)
        mv.visitInsn(POP)

        mv.visitLabel(Label())
        mv.visitLdcInsn(properties.tagCost)
        mv.visitLdcInsn("${Log.SQUARE_LEFT} ${String.format(header, Language.get(properties.useEnglish, Language.METHOD))}: ${name}(${fileName}:${curLineNumber})")
        mv.visitMethodInsn(INVOKESTATIC, C_LOG, properties.levelCost, "(Ljava/lang/String;Ljava/lang/String;)I", false)
        mv.visitInsn(POP)


        mv.visitLabel(Label())
        mv.visitLdcInsn(properties.tagCost)
        mv.visitLdcInsn("${Log.SQUARE_LEFT} ${String.format(header, Language.get(properties.useEnglish, Language.DESCRIPTOR))}: $descriptor")
        mv.visitMethodInsn(INVOKESTATIC, C_LOG, properties.levelCost, "(Ljava/lang/String;Ljava/lang/String;)I", false)
        mv.visitInsn(POP)


        method.args.forEachIndexed { index, arg ->
            mv.visitLabel(Label())
            mv.visitLdcInsn(properties.tagCost)
            mv.visitTypeInsn(NEW, C_STRING_BUILDER)
            mv.visitInsn(DUP)
            mv.visitMethodInsn(INVOKESPECIAL, C_STRING_BUILDER, C_INIT, "()V", false)
            mv.visitLdcInsn("${Log.SQUARE_LEFT} ${String.format(header, (Language.get(properties.useEnglish, Language.ARG) + "[" + arg + "]"))}: ")
            mv.visitMethodInsn(INVOKEVIRTUAL, C_STRING_BUILDER, C_APPEND, "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false)
            loadArg(index)
            mv.visitMethodInsn(INVOKEVIRTUAL, C_STRING_BUILDER, C_APPEND, "(${argumentTypes[index].appendDescriptor})Ljava/lang/StringBuilder;", false)
            mv.visitMethodInsn(INVOKEVIRTUAL, C_STRING_BUILDER, C_TO_STRING, "()Ljava/lang/String;", false)
            mv.visitMethodInsn(INVOKESTATIC, C_LOG, properties.levelCost, "(Ljava/lang/String;Ljava/lang/String;)I", false)
            mv.visitInsn(POP)
        }


        if (isReturn) {
            mv.visitLdcInsn(properties.tagCost)
            mv.visitTypeInsn(NEW, C_STRING_BUILDER)
            mv.visitInsn(DUP)
            mv.visitMethodInsn(INVOKESPECIAL, C_STRING_BUILDER, C_INIT, "()V", false)
            mv.visitLdcInsn("${Log.SQUARE_LEFT} ${String.format(header, Language.get(properties.useEnglish, Language.RETURN))}: ")
            mv.visitMethodInsn(INVOKEVIRTUAL, C_STRING_BUILDER, C_APPEND, "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false)
            mv.visitVarInsn(ALOAD, returnLocal)
            mv.visitMethodInsn(INVOKEVIRTUAL, C_STRING_BUILDER, C_APPEND, "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false)
            mv.visitMethodInsn(INVOKEVIRTUAL, C_STRING_BUILDER, C_TO_STRING, "()Ljava/lang/String;", false)
            mv.visitMethodInsn(INVOKESTATIC, C_LOG, properties.levelCost, "(Ljava/lang/String;Ljava/lang/String;)I", false)
            mv.visitInsn(POP)
        }

        mv.visitLabel(Label())
        mv.visitLdcInsn(properties.tagCost)
        mv.visitTypeInsn(NEW, C_STRING_BUILDER)
        mv.visitInsn(DUP)
        mv.visitMethodInsn(INVOKESPECIAL, C_STRING_BUILDER, C_INIT, "()V", false)
        mv.visitLdcInsn("${Log.SQUARE_LEFT} ${String.format(header, Language.get(properties.useEnglish, Language.COST))}: ")
        mv.visitMethodInsn(INVOKEVIRTUAL, C_STRING_BUILDER, C_APPEND, "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false)
        mv.visitVarInsn(LLOAD, castTime)
        mv.visitMethodInsn(INVOKEVIRTUAL, C_STRING_BUILDER, C_APPEND, "(J)Ljava/lang/StringBuilder;", false)
        mv.visitLdcInsn("ms")
        mv.visitMethodInsn(INVOKEVIRTUAL, C_STRING_BUILDER, C_APPEND, "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false)
        mv.visitMethodInsn(INVOKEVIRTUAL, C_STRING_BUILDER, C_TO_STRING, "()Ljava/lang/String;", false)
        mv.visitMethodInsn(INVOKESTATIC, C_LOG, properties.levelCost, "(Ljava/lang/String;Ljava/lang/String;)I", false)
        mv.visitInsn(POP)

        mv.visitLabel(Label())
        mv.visitLdcInsn(properties.tagCost)
        mv.visitLdcInsn(Log.SQUARE_BOTTOM)
        mv.visitMethodInsn(INVOKESTATIC, C_LOG, properties.levelCost, "(Ljava/lang/String;Ljava/lang/String;)I", false)
        mv.visitInsn(POP)
    }

}
