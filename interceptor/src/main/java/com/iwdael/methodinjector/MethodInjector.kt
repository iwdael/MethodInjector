package com.iwdael.methodinjector

import com.android.build.api.instrumentation.FramesComputationMode
import com.android.build.api.instrumentation.InstrumentationScope
import com.android.build.api.variant.AndroidComponentsExtension
import com.iwdael.methodinjector.properties.Properties
import com.iwdael.methodinjector.utils.FileUtils
import com.iwdael.methodinjector.utils.Logger
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
        val androidExtension = project.extensions.getByType(AndroidComponentsExtension::class.java)
        project.extensions.create("methodInjector", Properties::class.java, project)
        Logger.projectName = project.name
        androidExtension.onVariants(androidExtension.selector().all()) { variant ->
            val properties = project.extensions.findByType(Properties::class.java) ?: Properties(null)
            Logger.debug = properties.debug
            Logger.log("classChainMatcher:${properties.classChainMatcher}")
            Logger.log("methodChainMatcher:${properties.methodChainMatcher}")
            Logger.log("classCastMatcher:${properties.classCastMatcher}")
            Logger.log("methodChainMatcher:${properties.methodCastMatcher}")
            variant.instrumentation.transformClassesWith(AsmClassFactory::class.java, InstrumentationScope.PROJECT) { params ->
                val sourceDir = File(project.projectDir, properties.sourceDir).absolutePath
                val sourceFiles = FileUtils.listFiles(File(sourceDir), arrayOf("java", "kt")).map { it.absolutePath }
                params.sourceDir.set(sourceDir)
                params.sourceFiles.set(sourceFiles)
                params.classChainMatcher.set(properties.classChainMatcher)
                params.methodChainMatcher.set(properties.methodChainMatcher)
                params.classCastMatcher.set(properties.classCastMatcher)
                params.methodCastMatcher.set(properties.methodCastMatcher)
                params.tagChain.set(properties.tagChain)
                params.levelChain.set(properties.levelChain)
                params.tagCast.set(properties.tagCast)
                params.levelCast.set(properties.levelCast)
                params.enableChain.set(properties.enableChain)
                params.enableCast.set(properties.enableCast)
            }
            variant.instrumentation.setAsmFramesComputationMode(FramesComputationMode.COMPUTE_FRAMES_FOR_INSTRUMENTED_METHODS)
        }
    }
}