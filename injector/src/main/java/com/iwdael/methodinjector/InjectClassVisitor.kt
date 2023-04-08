package com.iwdael.methodinjector

import com.android.build.api.instrumentation.ClassContext
import com.iwdael.methodinjector.entity.Method
import com.iwdael.methodinjector.injector.ChainInjector
import com.iwdael.methodinjector.injector.CostInjector
import com.iwdael.methodinjector.properties.Properties
import com.iwdael.methodinjector.utils.get
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassReader.EXPAND_FRAMES
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.Label
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.commons.AdviceAdapter

/**
 * @author : iwdael
 * @mail : iwdael@outlook.com
 * @project : https://github.com/iwdael/MethodInjector
 */
class InjectClassVisitor(private val context: ClassContext, visitor: ClassVisitor, private val properties: Properties, private val fileName: String, private val asmApi: Int) : ClassVisitor(asmApi, visitor) {
    private val methods = mutableListOf<Method>()

    init {
        val byteCode = visitor.get("symbolTable").get("sourceClassReader").get("classFileBuffer") as ByteArray
        val reader = ClassReader(byteCode)
        reader.accept(GetClassVisitor(asmApi, null), EXPAND_FRAMES)
    }

    override fun visitMethod(access: Int, name: String, descriptor: String, signature: String?, exceptions: Array<String>?): MethodVisitor {
        val visitor = super.visitMethod(access, name, descriptor, signature, exceptions)
        val injector = ChainInjector(context, asmApi, visitor, access, name, descriptor, properties, fileName , getMethod(name, descriptor))
        return CostInjector(context, asmApi, injector, access, name, descriptor, properties, fileName , getMethod(name, descriptor))
    }

    private fun getMethod(name: String, descriptor: String): Method {
        return methods.first { it.name == name && it.descriptor == descriptor }
    }

    inner class GetClassVisitor(api: Int, classVisitor: ClassVisitor?) : ClassVisitor(api, classVisitor) {
        override fun visitMethod(access: Int, name: String?, descriptor: String?, signature: String?, exceptions: Array<out String>?): MethodVisitor {
            val visitMethod = super.visitMethod(access, name, descriptor, signature, exceptions)
            return GetMethodVisitor(api, visitMethod, access, name, descriptor)
        }
    }

    inner class GetMethodVisitor(api: Int, methodVisitor: MethodVisitor?, access: Int, name: String?, descriptor: String?) : AdviceAdapter(api, methodVisitor, access, name, descriptor) {
        private val method = Method(name, 0, descriptor, argumentTypes.size)

        init {
            methods.add(method)
        }


        override fun visitLineNumber(line: Int, start: Label?) {
            super.visitLineNumber(line, start)
            if (method.lineNumber != 0) return
            method.lineNumber = line
        }

        override fun visitLocalVariable(name: String, descriptor: String?, signature: String?, start: Label?, end: Label?, index: Int) {
            super.visitLocalVariable(name, descriptor, signature, start, end, index)
            val offset = if (access and ACC_STATIC == 0) 1 else 0
            if (index !in (0 + offset) until (argumentTypes.size + offset)) return
            method.args.add(name)
        }


    }
}
