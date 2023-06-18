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
    val sourceFiles: ListProperty<String>

    @get:Internal
    @get:Input
    val sourceDir: Property<String>

    @get:Internal
    @get:Input
    val classMatcher: Property<Any>

    @get:Internal
    @get:Input
    val classUnMatcher: Property<Any>

    @get:Internal
    @get:Input
    val methodChainMatcher: Property<Any>

    @get:Internal
    @get:Input
    val methodChainInternalMatcher: Property<Any>


    @get:Internal
    @get:Input
    val methodChainUnMatcher: Property<Any>

    @get:Internal
    @get:Input
    val methodChainInternalUnMatcher: Property<Any>

    @get:Internal
    @get:Input
    val methodCostMatcher: Property<Any>

    @get:Internal
    @get:Input
    val methodCostUnMatcher: Property<Any>

    @get:Internal
    @get:Input
    val methodCostInternalMatcher: Property<Any>

    @get:Internal
    @get:Input
    val methodCostInternalUnMatcher: Property<Any>

    @get:Internal
    @get:Input
    val tagChain: Property<String>

    @get:Internal
    @get:Input
    val levelChain: Property<String>

    @get:Internal
    @get:Input
    val tagCost: Property<String>

    @get:Internal
    @get:Input
    val levelCost: Property<String>

    @get:Internal
    @get:Input
    val enableChain: Property<Boolean>

    @get:Internal
    @get:Input
    val simpleChain: Property<Boolean>

    @get:Internal
    @get:Input
    val simpleCost: Property<Boolean>

    @get:Internal
    @get:Input
    val enableCost: Property<Boolean>

    @get:Internal
    @get:Input
    val useEnglish: Property<Boolean>


}