package com.iwdael.methodinjector.utils

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Type
import org.objectweb.asm.commons.AdviceAdapter

/**
 * @author  : iwdael
 * @mail    : iwdael@outlook.com
 * @project : https://github.com/iwdael/MethodInjector
 */

fun MethodVisitor.visitReturn(opcode: Int, returnType: Type) {
    when (opcode) {
        in AdviceAdapter.IRETURN..AdviceAdapter.DRETURN -> {
            if (returnType.size == 1) {
                this.visitInsn(AdviceAdapter.DUP)
            } else {
                this.visitInsn(AdviceAdapter.DUP2)
            }
        }
        AdviceAdapter.ARETURN -> {
            this.visitInsn(AdviceAdapter.DUP)
        }
    }
}