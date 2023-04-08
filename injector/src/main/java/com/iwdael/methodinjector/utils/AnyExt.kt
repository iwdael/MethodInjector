package com.iwdael.methodinjector.utils

import com.google.gson.GsonBuilder

/**
 * @author  : iwdael
 * @mail    : iwdael@outlook.com
 * @project : https://github.com/iwdael/MethodInjector
 */

val Any.jsonPretty get() = GsonBuilder().setPrettyPrinting().create().toJson(this).toString()
val Any.json get() = GsonBuilder().create().toJson(this).toString()


