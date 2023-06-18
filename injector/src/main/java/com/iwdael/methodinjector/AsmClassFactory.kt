package com.iwdael.methodinjector

import com.android.build.api.instrumentation.AsmClassVisitorFactory
import com.android.build.api.instrumentation.ClassContext
import com.android.build.api.instrumentation.ClassData
import com.iwdael.methodinjector.properties.InstrumentationProperty
import com.iwdael.methodinjector.utils.*
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.Opcodes

/**
 * @author : iwdael
 * @mail : iwdael@outlook.com
 * @project : https://github.com/iwdael/MethodInjector
 */
abstract class AsmClassFactory : AsmClassVisitorFactory<InstrumentationProperty> {
    override fun createClassVisitor(classContext: ClassContext, nextClassVisitor: ClassVisitor): ClassVisitor {
        Logger.log(classContext.currentClassData.className)
        return InjectClassVisitor(classContext, nextClassVisitor, parameters.get().toProperty(), parameters.get().findSourceFile(classContext)?.name.toString(), instrumentationContext.apiVersion.get().toAsmVersion())
    }

    override fun isInstrumentable(classData: ClassData): Boolean {
        return classData.className.matcher(parameters.get().classMatcher.get())
                && !classData.className.matcher2(parameters.get().classUnMatcher.get())

    }

    /**
     *
    | AGP version | Corresponding ASM version |
    |-------------|---------------------------|
    | 4.2.0 - 7.0 |            ASM7           |
    |    7.1.0+   |            ASM9           |
     */
    private fun Int.toAsmVersion(): Int {
        return if (this < 4) {
            Opcodes.ASM5
        } else if (this in 4 until 7) {
            Opcodes.ASM7
        } else {
            Opcodes.ASM9
        }
    }
}