package com.iwdael.methodinjector

import com.android.build.api.instrumentation.ClassContext
import com.iwdael.methodinjector.injector.CastInjector
import com.iwdael.methodinjector.injector.ChainInjector
import com.iwdael.methodinjector.properties.Properties
import org.gradle.internal.impldep.org.objectweb.asm.Opcodes
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import java.io.File

/**
 * @author : iwdael
 * @mail : iwdael@outlook.com
 * @project : https://github.com/iwdael/MethodInjector
 */
class InjectClassVisitor(private val context: ClassContext, visitor: ClassVisitor, private val properties: Properties, private val sourceFile: File?) : ClassVisitor(Opcodes.ASM5, visitor) {
    override fun visitMethod(access: Int, name: String?, descriptor: String?, signature: String?, exceptions: Array<String>?): MethodVisitor {
        val visitor = super.visitMethod(access, name, descriptor, signature, exceptions)
        val injector = ChainInjector(context, Opcodes.ASM5, visitor, access, name, descriptor, properties, sourceFile)
        return CastInjector(context, Opcodes.ASM5, injector, access, name, descriptor, properties, sourceFile)
    }
}
