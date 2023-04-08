package com.iwdael.methodinjector

import com.android.build.api.instrumentation.AsmClassVisitorFactory
import com.android.build.api.instrumentation.ClassContext
import com.android.build.api.instrumentation.ClassData
import com.iwdael.methodinjector.properties.InstrumentationProperty
import com.iwdael.methodinjector.utils.Logger
import com.iwdael.methodinjector.utils.toProperty
import org.objectweb.asm.ClassVisitor

/**
 * @author : iwdael
 * @mail : iwdael@outlook.com
 * @project : https://github.com/iwdael/MethodInjector
 */
abstract class AsmClassFactory : AsmClassVisitorFactory<InstrumentationProperty> {
    override fun createClassVisitor(context: ClassContext, visitor: ClassVisitor): ClassVisitor {
        Logger.log("transform:${context.currentClassData.className}")
        return InjectClassVisitor(context, visitor, parameters.get().toProperty())
    }

    override fun isInstrumentable(classData: ClassData): Boolean {
        if (parameters.get().classMatcher.get().isEmpty()) return false
        return classData.className.matches(Regex(parameters.get().classMatcher.get()))
    }
}