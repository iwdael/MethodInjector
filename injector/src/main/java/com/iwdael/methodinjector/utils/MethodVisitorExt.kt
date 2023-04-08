package com.iwdael.methodinjector.utils

import org.objectweb.asm.Opcodes.*
import org.objectweb.asm.Type
import org.objectweb.asm.commons.AdviceAdapter
import org.objectweb.asm.commons.GeneratorAdapter

/**
 * @author  : iwdael
 * @mail    : iwdael@outlook.com
 * @project : https://github.com/iwdael/MethodInjector
 */

fun GeneratorAdapter.visitReturn(opcode: Int) {
    when (opcode) {
        ARETURN, ATHROW -> {
            dup()
        }
        else -> {
            if (opcode == LRETURN || opcode == DRETURN) {
                dup2()
            } else {
                dup()
            }
//            box(returnType)
        }
    }
}