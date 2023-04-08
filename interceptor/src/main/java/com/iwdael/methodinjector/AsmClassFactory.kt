package com.iwdael.methodinjector

import com.android.build.api.instrumentation.AsmClassVisitorFactory
import com.android.build.api.instrumentation.ClassContext
import com.android.build.api.instrumentation.ClassData
import com.iwdael.methodinjector.properties.InstrumentationProperty
import com.iwdael.methodinjector.utils.Logger
import com.iwdael.methodinjector.utils.containJava
import com.iwdael.methodinjector.utils.findSourceFile
import com.iwdael.methodinjector.utils.toProperty
import org.objectweb.asm.ClassVisitor

/**
 * @author : iwdael
 * @mail : iwdael@outlook.com
 * @project : https://github.com/iwdael/MethodInjector
 */
abstract class AsmClassFactory : AsmClassVisitorFactory<InstrumentationProperty> {
    override fun createClassVisitor(classContext: ClassContext, nextClassVisitor: ClassVisitor): ClassVisitor {
        Logger.log("transform:${classContext.currentClassData.className}")
        return InjectClassVisitor(classContext, nextClassVisitor, parameters.get().toProperty(), parameters.get().findSourceFile(classContext))
    }

    override fun isInstrumentable(classData: ClassData): Boolean {
        if (parameters.get().classChainMatcher.get().isEmpty() && parameters.get().classCastMatcher.get().isEmpty()) return false
        return classData.className.matches(Regex(parameters.get().classChainMatcher.get())) || classData.className.matches(Regex(parameters.get().classCastMatcher.get()))
    }
}