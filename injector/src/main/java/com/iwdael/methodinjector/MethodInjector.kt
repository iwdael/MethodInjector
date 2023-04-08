package com.iwdael.methodinjector

import com.android.build.api.instrumentation.FramesComputationMode
import com.android.build.api.instrumentation.InstrumentationScope
import com.android.build.api.variant.AndroidComponentsExtension
import com.iwdael.methodinjector.properties.Properties
import com.iwdael.methodinjector.utils.FileUtils
import com.iwdael.methodinjector.utils.Logger
import com.iwdael.methodinjector.utils.jsonPretty
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File

/**
 * @author : iwdael
 * @mail : iwdael@outlook.com
 * @project : https://github.com/iwdael/MethodInjector
 */
class MethodInjector : Plugin<Project> {
    override fun apply(project: Project) {
        val androidExtension = project.extensions.findByType(AndroidComponentsExtension::class.java)
        project.extensions.create("methodInjector", Properties::class.java, project)
        Logger.projectName = project.name
        androidExtension?.onVariants(androidExtension.selector().all()) { variant ->
            val properties = project.extensions.findByType(Properties::class.java) ?: Properties(null)
            Logger.debug = properties.debug
            Logger.log("properties:\n${properties.jsonPretty}")
            variant.instrumentation.transformClassesWith(AsmClassFactory::class.java, InstrumentationScope.PROJECT) { params ->
                val sourceDir = File(project.projectDir, properties.sourceDir).absolutePath
                val sourceFiles = FileUtils.listFiles(File(sourceDir), arrayOf("java", "kt")).map { it.absolutePath }
                params.sourceDir.set(sourceDir)
                params.sourceFiles.set(sourceFiles)
                params.classMatcher.set(properties.classMatcher)
                params.classUnMatcher.set(properties.classUnMatcher)

                params.methodChainMatcher.set(properties.methodChainMatcher)
                params.methodChainUnMatcher.set(properties.methodChainUnMatcher)
                params.methodChainInternalMatcher.set(properties.methodChainInternalMatcher)
                params.methodChainInternalUnMatcher.set(properties.methodChainInternalUnMatcher)

                params.methodCostMatcher.set(properties.methodCostMatcher)
                params.methodCostUnMatcher.set(properties.methodCostUnMatcher)
                params.methodCostInternalMatcher.set(properties.methodCostInternalMatcher)
                params.methodCostInternalUnMatcher.set(properties.methodCostInternalUnMatcher)


                params.tagChain.set(properties.tagChain)
                params.levelChain.set(properties.levelChain)
                params.tagCost.set(properties.tagCost)
                params.levelCost.set(properties.levelCost)
                params.enableChain.set(properties.enableChain)
                params.enableCost.set(properties.enableCost)
                params.useEnglish.set(properties.useEnglish)
            }
            variant.instrumentation.setAsmFramesComputationMode(FramesComputationMode.COMPUTE_FRAMES_FOR_INSTRUMENTED_METHODS)
        }
    }
}