package com.iwdael.methodinjector

import com.android.build.api.instrumentation.FramesComputationMode
import com.android.build.api.instrumentation.InstrumentationScope
import com.android.build.api.variant.AndroidComponentsExtension
import com.iwdael.methodinjector.properties.Property
import com.iwdael.methodinjector.utils.Logger
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * @author : iwdael
 * @mail : iwdael@outlook.com
 * @project : https://github.com/iwdael/MethodInjector
 */
class MethodInjector : Plugin<Project> {
    override fun apply(project: Project) {
        val androidExtension = project.extensions.getByType(AndroidComponentsExtension::class.java)
        project.extensions.create("methodInjector", Property::class.java, project)
        Logger.projectName = project.name
        androidExtension.onVariants(androidExtension.selector().all()) { variant ->
            val property = project.extensions.findByType(Property::class.java)
            Logger.debug = property?.debug?:false
            Logger.log("classNameMatches:${property?.classNameMatches}")
            variant.instrumentation.transformClassesWith(AsmClassFactory::class.java, InstrumentationScope.PROJECT) { params ->
                params.classNameMatches.set(property?.classNameMatches ?: listOf())
            }
            variant.instrumentation.setAsmFramesComputationMode(FramesComputationMode.COMPUTE_FRAMES_FOR_INSTRUMENTED_METHODS)
        }
    }
}