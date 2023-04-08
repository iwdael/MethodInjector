package com.iwdael.methodinjector.properties

import com.android.build.api.instrumentation.InstrumentationParameters
import groovy.transform.Internal
import org.gradle.api.provider.ListProperty
import org.gradle.api.tasks.Input


/**
 * @author  : iwdael
 * @mail    : iwdael@outlook.com
 * @project : https://github.com/iwdael/MethodInjector
 */
interface InstrumentationProperty : InstrumentationParameters {
    @get:Internal
    @get:Input
    val classNameMatches: ListProperty<String>
}