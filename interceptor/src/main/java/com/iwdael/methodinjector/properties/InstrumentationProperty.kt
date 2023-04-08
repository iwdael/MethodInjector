package com.iwdael.methodinjector.properties

import com.android.build.api.instrumentation.InstrumentationParameters
import groovy.transform.Internal
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
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

    @get:Internal
    @get:Input
    val tagChain: Property<String>

    @get:Internal
    @get:Input
    val levelChain: Property<String>

    @get:Internal
    @get:Input
    val tagCast: Property<String>

    @get:Internal
    @get:Input
    val levelCast: Property<String>

    @get:Internal
    @get:Input
    val enableChain: Property<Boolean>

    @get:Internal
    @get:Input
    val enableCast: Property<Boolean>



}