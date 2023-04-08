package com.iwdael.methodinjector

import com.android.build.api.instrumentation.ClassContext
import com.iwdael.methodinjector.injector.ChainInjector
import org.gradle.internal.impldep.org.objectweb.asm.Opcodes
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor

/**
 * @author : iwdael
 * @mail : iwdael@outlook.com
 * @project : https://github.com/iwdael/MethodInjector
 */
class InjectClassVisitor(private val context: ClassContext, visitor: ClassVisitor) : ClassVisitor(Opcodes.ASM9, visitor) {
    override fun visitMethod(access: Int, name: String?, descriptor: String?, signature: String?, exceptions: Array<String>?): MethodVisitor {
        val visitor = super.visitMethod(access, name, descriptor, signature, exceptions)
        return ChainInjector(context, Opcodes.ASM9, visitor, access, name, descriptor)
    }
}