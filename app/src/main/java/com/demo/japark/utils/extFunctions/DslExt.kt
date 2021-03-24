package com.demo.japark.utils.extFunctions

/**
 *  DSL for invoke block
 */

operator fun <T> T.invoke(block: T.() -> Unit) = block()