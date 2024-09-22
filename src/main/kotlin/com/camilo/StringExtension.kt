package com.camilo


fun String.getPathAsString(): String {
    return object {}.javaClass.classLoader?.getResource(this)?.path ?: ""
}
